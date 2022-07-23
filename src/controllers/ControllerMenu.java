/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.InterGestionClientes;
import views.InterConsulta;
import views.Menu;
import views.InterUsuario;
import views.InterGestionEmpleados;
import models.Empresa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Q-USER
 */
public class ControllerMenu implements ActionListener{

    private final Menu vista;
    
    private final Empresa empresa;
      

    public ControllerMenu(Menu vista, Empresa empresa) {
        this.vista = vista;           
        this.empresa = empresa;
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
            InterGestionEmpleados ventana = new InterGestionEmpleados();  
            ControllerEmpleados ctrlEmpleado = new ControllerEmpleados(empresa, ventana);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuFormularioCliente) {            
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal          
            InterGestionClientes ventana = new InterGestionClientes();   
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
            InterUsuario ventana = new InterUsuario();
            vista.escritorio.add(ventana);
            ventana.show(); 
        }        
        
        if (evt.getSource() == vista.jMenuSalir) {
            System.exit(0);//Permite salir de la aplicación
        }
    }
    
}
