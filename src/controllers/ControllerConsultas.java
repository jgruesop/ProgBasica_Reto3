/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Empresa;
import views.*;

/**
 *
 * @author Q-USER
 */
public class ControllerConsultas implements ActionListener {

    Empresa empresa;
    FrmConsulta vista;
    
    public ControllerConsultas(FrmConsulta vista, Empresa empresa) {
        this.empresa = empresa;             
        vista.btnSalir.addActionListener(this);        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent evt) {        
        if (evt.getSource() == vista.btnSalir) {
             vista.dispose();
        }
    }
    
}
