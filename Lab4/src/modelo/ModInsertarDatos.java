/*

 */
package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ModInsertarDatos {
    
    public ArrayList<ArrayList<String>> listaEstudiantes;
    
    public ModInsertarDatos(){
        
        listaEstudiantes = new ArrayList<>();
        
    }
    
    public void leerArchivo(){
        
        FileReader reader = null;
        try {
            String linea = "";
            reader = new FileReader("src/modelo/notas.csv");
            BufferedReader buffer = new BufferedReader(reader);
            
            while((linea = buffer.readLine()) != null){
//                System.out.println(linea);
                ArrayList<String> estudiante = leerLinea(linea);
                listaEstudiantes.add(estudiante);
               
            }
            
            reader.close();
            conectarMySQL();

        } catch (IOException ex) {
            Logger.getLogger(ModInsertarDatos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public ArrayList<String> leerLinea(String linea){
        
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add((listaEstudiantes.size()+1) + "");
        String[] split = linea.split(";");
        for(String dato : split){
            
            arrayList.add(dato);
        }
        return arrayList;
    }
    
    public void conectarMySQL(){
        
        Conexion con = new Conexion();
        
        if(con.conectarMySQL()){
            if(con.contar("estudiantes") > 0){
                System.out.println("HahHAhaahHa");
                return;
            }
            for(ArrayList<String> estudiante : listaEstudiantes){
                if(con.insertar("estudiantes", estudiante)){
                    
                }else{
                JOptionPane.showMessageDialog(null, con.getMensaje());
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, con.getMensaje());
        }       
    }
    
}
