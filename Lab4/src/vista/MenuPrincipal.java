/*

 */
package vista;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame{
    
    public JLabel jlTitulo;
    public JButton bSalir, bE1, bE2, bE3, bE4, bAcerca;
    public ImageIcon imE1, imE2, imE3, imE4, imInfo;
    
    public MenuPrincipal(){
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(19,20,42));
        setLayout(null);
        setResizable(false);
        
        crearGUI();
        
        setVisible(true);
    }

    private void crearGUI() {
        
//        ControlEstadisiticas controlador = new ControlEstadisiticas(this);
        
        jlTitulo = Componentes.labelTitulo(240, 40, 300, 50, "Menu Estadisticas");        
        add(jlTitulo);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 0, 26));
        panel.setBounds(180, 130, 420, 300);
        
        imE1 = new ImageIcon(getClass().getResource("../icono/est1.png"));
        bE1 = Componentes.boton(20, 40, 180, 40, "Estadistica 1", imE1); // Cual PROGRAMA tiene mejor definitiva en c1,c2,c3
        panel.add(bE1);
        
        imE2 = new ImageIcon(getClass().getResource("../icono/est2.png"));
        bE2 = Componentes.boton(220, 40, 180, 40, "Estadistica 2", imE2); // Cual PROGRAMA tiene mejor definitiva
        panel.add(bE2);
        
        imE3 = new ImageIcon(getClass().getResource("../icono/est3.png"));
        bE3 = Componentes.boton(20, 100, 180, 40, "Estadistica 3", imE3); // Cuantos ESTUDIANTES pierden, habilitan, aprueban semestre
        panel.add(bE3);
        
        imE4 = new ImageIcon(getClass().getResource("../icono/est4.png"));
        bE4 = Componentes.boton(220, 100, 180, 40, "Estadistica 4", imE4); // Cual ESTUDIANTE tiene mejor definitiva
        panel.add(bE4);
        
        imInfo = new ImageIcon(getClass().getResource("../icono/info.png"));
        bAcerca = Componentes.boton(140, 220, 150, 40, "Acerca de...", imInfo); // jDialog icono -> admins
        panel.add(bAcerca);
        
        add(panel);
        
        bSalir = Componentes.boton(350, 480, 90, 30, "Salir", null);
        bSalir.setBackground(Color.LIGHT_GRAY);
//        bSalir.addActionListener(controlador);
        add(bSalir);
    }  
    
}
