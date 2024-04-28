/*

 */
package modelo;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection conn = null; // Objeto para la conexion
    Statement stmt = null;// Objeto para ejecutar la consulta
    ResultSet rs = null; // Objeto para recuperar los resultados de la consulta
    String mensaje = "";
        
    // datos de conexion 
    String bd = "lab4";
    String login = "root";
    String password = "";
    String host = "192.168.0.10";
    
    public Conexion() {
        conn = null;
        stmt = null;
        rs = null;
        mensaje = "";
    }
    
    public String getMensaje() { 
    	return mensaje; 
    }
    
    public boolean conectarMySQL() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + bd, login, password);
        } catch (ClassNotFoundException e) { 
        	mensaje = "No se encuentra la referencia del conector de MySQL.";
        	return false;
        }catch (SQLException ex) {
        	mensaje = "Error al tratar de conectar con la base de datos '" + bd + "'.";
                System.out.println(ex.getMessage());
        	return false;
        }
        return true;
    }

    public boolean conectarPostgres() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":5432/" + bd, login, password);
        } catch (ClassNotFoundException ex) {
        	mensaje = "No se encuentra la referencia del conector de PostgreSQL.";
        	return false;
        }
        catch (SQLException ex) {
        	mensaje = "Error al tratar de conectar con la base de datos '" + bd + "'.";
        	return false;
        }
        return true;
    }
        
    public boolean desconectar() {
        try {
            conn.close();
            return true;
        } catch (SQLException sqle) {
        	mensaje = "Error al tratar de conectar con la base de datos '" + bd + "'.";
        	return false;
        }
    }

    public boolean insertar(String tabla, String datos[]) {
        String sql = "INSERT INTO " + tabla + " VALUES('";
        for (String campo : datos) {
            sql += campo + "','";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ");";	
        return actualizar(sql);
    }
    
    public boolean insertar(String tabla, ArrayList<String> datos) {
        String sql = "INSERT INTO " + tabla + " VALUES('";
        for (String campo : datos) {
            sql += campo + "','";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ");";	
        return actualizar(sql);
    }
    
    public boolean actualizar(String tabla, String nomCampos[], ArrayList<String> datos, String condicion) {
    	String sql = "UPDATE " + tabla + " SET ";
    	int i = 0;
        for (String campo : nomCampos) {
            sql += campo + " = '" + datos.get(i) + "', ";
            i++;
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += " " + condicion + ";";
        //System.out.println(sql);
        return actualizar(sql);
    }   
    
    public boolean actualizar(String sql) {        
        stmt = null; // Objeto para ejecutar la consulta
        try {
            stmt = conn.createStatement();
            int resultado = stmt.executeUpdate(sql);
            stmt.close();
            return true;
        } catch (SQLException sqle) {
        	mensaje = "Error al realizar la actualización.\n" + sqle;
           	return false;
        }
    }
    
    public String[] consultaFila(String tabla, String campo, String valor) {
        stmt = null; // Objeto para ejecutar la consulta
        rs = null; // Objeto para recuperar los resultados de la consulta
        String fila[] = null;
       
        try {
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery("SELECT * FROM " + tabla + " WHERE " + campo + " = '" + valor + "'");
                int c = getSizeQuery(rs); // obtener cantidad de registros de la consulta                	
                if (c > 0) {//si la cantidad de filas (registros) resultantes de la consulta es mayor a cero, es porque el valor a buscar existe
                	
                    int cantColumnas = rs.getMetaData().getColumnCount();//obtener cantidad de columnas (campos) resultantes de la consulta									
                    fila = new String[cantColumnas];//dar tama�o al array dependiendo de la cantidad de columnas (campos) resultantes de la consulta

                    while (rs.next()) {//recorrer la consulta
                        for (int i = 1; i <= cantColumnas; i++) {
                            fila[i - 1] = rs.getString(i);//y llenar el array String con los campos a retornar
                        }
                        break;
                    }
                } else mensaje = "No hay registros que cumplan la condición.";
                cerrarConsulta();
        } catch (SQLException sqle) { 
        	mensaje = "Error al realizar la consulta.\n" + sqle;
        }
        
        return fila;
    }
    
    public String[][] consultaMatriz(String sql) {
        stmt = null; // Objeto para ejecutar la consulta
        rs = null; // Objeto para recuperar los resultados de la consulta
        String matrizRegistros[][] = null;
        
       try {
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery(sql);
                int canFilas = getSizeQuery(rs);
                if(canFilas > 0){
                    int canColumnas = rs.getMetaData().getColumnCount();
                    matrizRegistros = new String[canFilas][canColumnas];
                    int f = 0;
                    while (rs.next()) {
                        for (int c = 0; c < canColumnas; c++) {
                            matrizRegistros[f][c] = rs.getString(c + 1);
                        }
                        f++;
                    }
                } else mensaje = "No hay registros que cumplan con la condición.";
                cerrarConsulta();
        } catch (SQLException sqle) { 
        	mensaje = "Error al ejecutar la consulta.\n" + sqle;
        }
        return matrizRegistros;
    }

    public int getSizeQuery(ResultSet rs) {
        int cantFilas = -1;
        try {
            rs.last(); //Desplazar el puntero de lectura a la ultima fila (registro)
            cantFilas = rs.getRow(); //Calcular la cantidad de filas (registros) que arroja la consulta
            rs.beforeFirst(); //Desplazar el puntero de lectura a la primera fila (registro)
        } catch (SQLException sqle) { }
        return cantFilas;
    }

    public void cerrarConsulta() {
        try {
            rs.close(); //Cerrar el objeto que recupero los resultados de la consulta				
            stmt.close();//Cerrar el objeto que ejecuto la consulta
        } catch (SQLException sqle) { }
    }
    
    public ResultSet consulta(String sql) {
        stmt = null; // Objeto para ejecutar la consulta
        rs = null; // Objeto para recuperar los resultados de la consulta
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
        } catch (SQLException sqle) { }
        return rs;
    }
    
    public int contar(String tabla, String sql) {
        int cont = -1;
        ResultSet rs = consulta("SELECT COUNT(*) FROM " + tabla + " WHERE " + sql);
        try {
            while (rs.next()) {
                cont = Integer.parseInt(rs.getString(1));
            }
            cerrarConsulta();
        } catch (SQLException sqle) { }
        return cont;
    }

    // contar cuantos registros tiene una tabla
    public int contar(String tabla) {
        int cont = -1;
        ResultSet rs = consulta("SELECT COUNT(*) FROM " + tabla);
        try {
            while (rs.next()) {
                cont = Integer.parseInt(rs.getString(1));
            }
            cerrarConsulta();
        } catch (SQLException sqle) { }
        return cont;
    }

    public int sumar(String tabla, String campo) {
        int cont = -1;
        ResultSet rs = consulta("SELECT sum(" + campo + ") FROM " + tabla);
        try {
            while (rs.next()) {
                cont = Integer.parseInt(rs.getString(1));
            }
            cerrarConsulta();
        } catch (SQLException sqle) { }
        return cont;
    }
}
