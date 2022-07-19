/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Q-USER
 */
public class ControllerMenu implements ActionListener{

    private Menu vista;
    
    private Empresa empresa;
    
    private Empleado empleado;
    
    private Directivo directivo;
    
    

    public ControllerMenu(Menu vista, Empresa empresa) {
        this.vista = vista;   
        this.empresa = new Empresa("Harineras del Valle","90014802105", "3185548774",
                "Kra 35 # 25 35","jhonper86@gmail.com","Producción de harinas");
        vista.jMenuInicio.addActionListener(this);
        vista.menuFormularioEmpleado.addActionListener(this);
        vista.menuFormularioCliente.addActionListener(this);
        vista.menuConsultasEmpresa.addActionListener(this);
        vista.menuConfigUsuario.addActionListener(this);
        vista.jMenuSalir.addActionListener(this);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == vista.menuFormularioEmpleado) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal              
            InterGestionEmpleados ventana = new InterGestionEmpleados(empresa);  
            ControllerEmpleados ctrlEmpleado = new ControllerEmpleados(empresa, ventana, empleado, directivo);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuFormularioCliente) {            
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal          
            InterGestionClientes ventana = new InterGestionClientes(empresa);   
            ControllerClientes ctrlCliente = new ControllerClientes(empresa, ventana);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuConsultasEmpresa) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal             
            InterConsulta ventana = new InterConsulta(empresa);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuConfigUsuario) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal             
            InterUsuario ventana = new InterUsuario(empresa);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.jMenuSalir) {
            System.exit(0);//Permite salir de la aplicación
        }
    }
    
}
