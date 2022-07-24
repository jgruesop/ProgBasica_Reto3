/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.FrmClientes;
import views.FrmConsulta;
import views.FrmMenu;
import views.FrmInterUsuario;
import views.FrmEmpleados;
import models.Empresa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.FrmSelectEmpresa;


/**
 *
 * @author Q-USER
 */
public class ControllerMenu implements ActionListener{

    private final FrmMenu vista;
    
    private final Empresa empresa;
      

    public ControllerMenu(FrmMenu vista, Empresa empresa) {
        this.vista = vista;           
        this.empresa = empresa;
        vista.jMenuInicio.addActionListener(this);
        vista.menuFormularioEmpleado.addActionListener(this);
        vista.menuFormularioCliente.addActionListener(this);
        vista.menuConsultasEmpresa.addActionListener(this);
        vista.menuConfigUsuario.addActionListener(this);        
        vista.jMenuSalir.addActionListener(this);
        vista.setExtendedState(FrmMenu.MAXIMIZED_BOTH);//Permite ejecutar el formulario principal Maximizado
        vista.setLocationRelativeTo(null);//Permite ubicar el formulario en el centro de la pantalla  
        vista.setTitle(empresa.getNombre().toUpperCase()
                + "    - NIT: " + empresa.getNIT());
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == vista.menuFormularioEmpleado) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal              
            FrmEmpleados ventana = new FrmEmpleados();  
            ControllerEmpleados ctrlEmpleado = new ControllerEmpleados(empresa, ventana);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuFormularioCliente) {            
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal          
            FrmClientes ventana = new FrmClientes();   
            ControllerClientes ctrlCliente = new ControllerClientes(empresa, ventana);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuConsultasEmpresa) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal             
            FrmConsulta ventana = new FrmConsulta(empresa);
            vista.escritorio.add(ventana);
            ventana.show(); 
        }
        
        if (evt.getSource() == vista.menuConfigUsuario) {
            vista.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
            vista.escritorio.repaint();  // Permite limpiar la ventana principal             
            FrmInterUsuario ventana = new FrmInterUsuario();
            vista.escritorio.add(ventana);
            ventana.show(); 
        }        
        
        if (evt.getSource() == vista.jMenuSalir) {
            //System.exit(0);//Permite salir de la aplicación
             vista.dispose(); // Permite cerrar cualquier ventana abierta actualmente 
            FrmSelectEmpresa ventana = new FrmSelectEmpresa();                        
            ventana.show();  
        }
    }
    
}
