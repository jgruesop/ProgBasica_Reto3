/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.FrmLogin;
import views.FrmSelectEmpresa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Q-USER
 */
public class ControllerLogin implements ActionListener {

    private final FrmLogin vista;
    
    /**
     *
     * @param vista
     */
    public ControllerLogin(FrmLogin vista) {
        this.vista = vista;
        vista.btn_inicio.addActionListener(this);
        vista.btn_cerrar.addActionListener(this);    
        vista.setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {        
     
        if (evt.getSource() == vista.btn_inicio) {
            acceso();
        }
        
        if (evt.getSource() == vista.btn_cerrar) {
            System.exit(0);//Permite salir de la aplicación            
        }
    }
    
    private void acceso(){      
        vista.dispose(); // Permite cerrar cualquier ventana abierta actualmente 
        FrmSelectEmpresa ventana = new FrmSelectEmpresa();                        
        ventana.show();        
    }
    
//    void limpiarCampos(){
//        vista.txtUser.setText("");
//        vista.txtPass.setText("");
//        vista.txtUser.requestFocus();
//    }    
}
