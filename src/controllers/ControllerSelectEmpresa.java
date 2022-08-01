/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Empresa;
import static models.Empresa.buscarEmpresa;
import static models.Empresa.obtenerEmpresas;
import views.*;
import static views.FrmSelectEmpresa.txtBuscar;


/**
 *
 * @author Q-USER
 */
public class ControllerSelectEmpresa implements ActionListener {

    private FrmSelectEmpresa vista;
    private DefaultTableModel modelo;    //Modelo por defecto de la Tabla
    private Empresa empresa;
    

    public ControllerSelectEmpresa(FrmSelectEmpresa vista) {
        this.vista = vista;    
        ListarEmpresa();
        disenoTabla();
        vista.lblBuscar.setVisible(false);
        vista.btnAceptar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnVerDatos.addActionListener(this);
        vista.setLocationRelativeTo(null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == vista.btnAceptar) {
            int fila = vista.tablaEmpresas.getSelectedRow();
        
            if (fila < 0) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una empresa.");
            } else {
                int id = Integer.valueOf((String)vista.tablaEmpresas.getValueAt(fila, 0));            
                String NIT = (String)vista.tablaEmpresas.getValueAt(fila, 1);
                String nombre = (String)vista.tablaEmpresas.getValueAt(fila, 2);
                String telefono = (String)vista.tablaEmpresas.getValueAt(fila, 3);
                String direccion = (String)vista.tablaEmpresas.getValueAt(fila, 4);
                String email = (String)vista.tablaEmpresas.getValueAt(fila, 5);
                String actEconomica = (String)vista.tablaEmpresas.getValueAt(fila, 6);
                Empresa empresa = new Empresa(id, NIT, nombre, telefono, direccion, email, actEconomica);                
                FrmMenu ventana = new FrmMenu(empresa);                        
                ventana.show(); 
                vista.dispose();
            }
        }
        
        if (evt.getSource() == vista.btnBuscar) {
            buscarNit();
            disenoTabla();            
        }
        
        if (evt.getSource() == vista.btnVerDatos) {
            verInfo();
        }
    }

    private void ListarEmpresa() {
        ArrayList<Empresa> listaEmpresas = new ArrayList<>();
        
        listaEmpresas = obtenerEmpresas();
        
        Object[][] matriz = new Object[listaEmpresas.size()][7];            
        modelo = (DefaultTableModel) vista.tablaEmpresas.getModel();            
        
        for (int i = 0; i < listaEmpresas.size(); i++){
            matriz[i][0] = String.valueOf(listaEmpresas.get(i).getId());
            matriz[i][1] = String.valueOf(listaEmpresas.get(i).getNIT());
            matriz[i][2] = String.valueOf(listaEmpresas.get(i).getNombre());
            matriz[i][3] = String.valueOf(listaEmpresas.get(i).getTelfono());
            matriz[i][4] = String.valueOf(listaEmpresas.get(i).getDireccion());
            matriz[i][5] = String.valueOf(listaEmpresas.get(i).getEmail());
            matriz[i][6] = String.valueOf(listaEmpresas.get(i).getActEconomica());
                                             
        }            
        vista.tablaEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
                "Id", "NIT", "Nombre", "Teléfono", "Dirección", "Email",
                "Actividad Económica"
            }
        )); 
    }

    private  void buscarNit() {
        ArrayList<Empresa> listaEmpresa = new ArrayList<>();
        
        listaEmpresa = buscarEmpresa();        
         
        if (txtBuscar.getText().equals("Buscar NIT") || listaEmpresa.isEmpty() || txtBuscar.getText().equals("")) {
             JOptionPane.showMessageDialog(null, "No se encontró ningún resultado");
             ListarEmpresa();
        } else {
            Object[][] matriz = new Object[listaEmpresa.size()][7];            
            modelo = (DefaultTableModel) vista.tablaEmpresas.getModel();            

            for (int i = 0; i < listaEmpresa.size(); i++){
                matriz[i][0] = String.valueOf(listaEmpresa.get(i).getId());
                matriz[i][1] = String.valueOf(listaEmpresa.get(i).getNIT());
                matriz[i][2] = String.valueOf(listaEmpresa.get(i).getNombre());
                matriz[i][3] = String.valueOf(listaEmpresa.get(i).getTelfono());
                matriz[i][4] = String.valueOf(listaEmpresa.get(i).getDireccion());
                matriz[i][5] = String.valueOf(listaEmpresa.get(i).getEmail());
                matriz[i][6] = String.valueOf(listaEmpresa.get(i).getActEconomica());

            }            
            vista.tablaEmpresas.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String [] {
                    "Id", "NIT", "Nombre", "Teléfono", "Dirección", "Email",
                    "Actividad Económica"
                }
            ));  
        }
    }
    
    private void verInfo() {
       int fila = vista.tablaEmpresas.getSelectedRow();
       
       if (fila < 0) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una empresa.");
        } else {
           int id = Integer.valueOf((String)vista.tablaEmpresas.getValueAt(fila, 0));            
            String NIT = (String)vista.tablaEmpresas.getValueAt(fila, 1);
            String nombre = (String)vista.tablaEmpresas.getValueAt(fila, 2);
            String telefono = (String)vista.tablaEmpresas.getValueAt(fila, 3);
            String direccion = (String)vista.tablaEmpresas.getValueAt(fila, 4);
            String email = (String)vista.tablaEmpresas.getValueAt(fila, 5);
            String actEconomica = (String)vista.tablaEmpresas.getValueAt(fila, 6);
            Empresa empresa = new Empresa(id, NIT, nombre, telefono, direccion, email, actEconomica);
            FrmInfoEmpresa ventana = new FrmInfoEmpresa(empresa);                        
            ventana.show();             
       }
    }     
    
     //Método para diseñar las columnas de la tabla Cliente
    void disenoTabla() {
        //Redimensionar el tamaño de las columnas de la tabla.        
        vista.tablaEmpresas.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(0).setPreferredWidth(0);
        //.
        vista.tablaEmpresas.getColumnModel().getColumn(1).setMaxWidth(205);
        vista.tablaEmpresas.getColumnModel().getColumn(1).setMinWidth(205);
        vista.tablaEmpresas.getColumnModel().getColumn(1).setPreferredWidth(205);
        //.
        vista.tablaEmpresas.getColumnModel().getColumn(2).setMaxWidth(280);
        vista.tablaEmpresas.getColumnModel().getColumn(2).setMinWidth(280);
        vista.tablaEmpresas.getColumnModel().getColumn(2).setPreferredWidth(280);
        //.De este modo se oculta la columna 
        vista.tablaEmpresas.getColumnModel().getColumn(3).setMaxWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(3).setMinWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(3).setPreferredWidth(0);
        //De este modo se oculta la columna 
        vista.tablaEmpresas.getColumnModel().getColumn(4).setMaxWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(4).setMinWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(4).setPreferredWidth(0);
        //De este modo se oculta la columna 
        vista.tablaEmpresas.getColumnModel().getColumn(5).setMaxWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(5).setMinWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(5).setPreferredWidth(0);
        //De este modo se oculta la columna 
        vista.tablaEmpresas.getColumnModel().getColumn(6).setMaxWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(6).setMinWidth(0);
        vista.tablaEmpresas.getColumnModel().getColumn(6).setPreferredWidth(0);      
    }
    
}
