/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.InterGestionClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Q-USER
 */
public class ControllerClientes implements ActionListener {

    private Empresa empresa;    
    private Cliente cliente ;  
    private InterGestionClientes vista;
    java.sql.Date sqlPackageDate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel modelo;    //Modelo por defecto de la Tabla
    
    ControllerClientes(Empresa empresa, InterGestionClientes vista ) {
        this.vista = vista;
        this.empresa = empresa;
        vista.btnGuardar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.tablaClientes.getTableHeader().setReorderingAllowed(false);//Bloquea el movimiento de las columnas, e impide imvertir la información.
        inhabilitarbotones();
        inhabilitarCampos();
        ListarClientes();
        disenoTabla();
    }
   
    @Override
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == vista.btnGuardar) {
            int id = 0;
            String nombre = vista.txtNombre.getText().toUpperCase();
            String doc = vista.txtDoc.getText();
            String apel = vista.txtApellido.getText().toUpperCase();
            String TID = vista.boxTID.getSelectedItem().toString();
            String tel = vista.txtTel.getText();
            String dir = vista.txtDir.getText().toUpperCase();
            String email = vista.txtEmail.getText();                
            String genero = "";
            int edad = 0;
            if (vista.btnHombre.isSelected()){ genero = "H"; }
            if(vista.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || vista.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero.equals("");

            if ( comp1 || comp2 ){                
                JOptionPane.showMessageDialog(null, "Debe seleccionar un clientes de la tabla.");
            } else {                
                //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                sqlPackageDate = new java.sql.Date(vista.txtFechaNac.getDate().getTime());
                /// Da formato a la fecha obtenida en la linea anterior
                Date fNac = sqlPackageDate;
                cliente = new Cliente(id, TID, doc, nombre, apel, fNac, genero, edad, tel, dir, email);                        
                boolean res = empresa.agregarCliente(cliente);
                if (res == true) {
                    JOptionPane.showMessageDialog(null, "Datos almacenados exitosamente.");                                
                    ListarClientes();                
                    disenoTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                }
            }
        }
        
        if (evt.getSource() == vista.btnActualizar) {            
            int id = 0;
            String nombre = vista.txtNombre.getText().toUpperCase();
            String doc = vista.txtDoc.getText();
            String apel = vista.txtApellido.getText().toUpperCase();
            String TID = vista.boxTID.getSelectedItem().toString();
            String tel = vista.txtTel.getText();
            String dir = vista.txtDir.getText().toUpperCase();
            String email = vista.txtEmail.getText();   
            String genero = "";
            int edad = 0;    
            if (vista.btnHombre.isSelected()){ genero = "H"; }
            if(vista.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || vista.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero.equals("");

            int fila = vista.tablaClientes.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La información no registra ningún cambio.");
                } else {      
                    //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                    java.sql.Date sqlPackageDate = new java.sql.Date(vista.txtFechaNac.getDate().getTime());
                    /// Da formato a la fecha obtenida en la linea anterior
                    Date fNac = sqlPackageDate;
                    cliente = new Cliente(id, TID, doc, nombre, apel, fNac,  genero, edad, tel,  dir, email);                        
                    boolean res = empresa.modificarCliente(fila, cliente);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Datos Actualizados exitosamente.");    
                        //limpiarTabla(modelo);
                        ListarClientes();                
                        disenoTabla();
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }
                }
            }
        }
        
        if (evt.getSource() == vista.btnEliminar) {
            inhabilitarCampos();
            int fila = vista.tablaClientes.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {                
                int op = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (op == JOptionPane.YES_OPTION) {    
                    String idCliente = vista.tablaClientes.getValueAt(fila, 0).toString();
                    boolean res = empresa.eliminarCliente(idCliente);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                        ListarClientes();
                        disenoTabla();
                        limpiarCampos();
                        inhabilitarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }                    
                }
                ListarClientes();
                disenoTabla();
                limpiarCampos();
                inhabilitarbotones();
                inhabilitarCampos();
            }             
        }
    }
    
    
    /***
     * Metodo para listar los clientes en la tabla
     */
    public void ListarClientes() {       
        
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        listaClientes = empresa.obtenerClientes();
        
        Object[][] matriz = new Object[listaClientes.size()][11];            
        modelo = (DefaultTableModel) vista.tablaClientes.getModel();            
        
        for (int i = 0; i < listaClientes.size(); i++){
            matriz[i][0] = String.valueOf(listaClientes.get(i).getId());
            matriz[i][1] = String.valueOf(listaClientes.get(i).getTID());
            matriz[i][2] = String.valueOf(listaClientes.get(i).getDocumento());
            matriz[i][3] = String.valueOf(listaClientes.get(i).getNombre());
            matriz[i][4] = String.valueOf(listaClientes.get(i).getApellidos());
            matriz[i][5] = String.valueOf(listaClientes.get(i).getFechaNacimiento());
            matriz[i][6] = String.valueOf(listaClientes.get(i).getGenero());
            matriz[i][7] = String.valueOf(listaClientes.get(i).getEdad());
            matriz[i][8] = String.valueOf(listaClientes.get(i).getTelefono());
            matriz[i][9] = String.valueOf(listaClientes.get(i).getEmail());
            matriz[i][10] = String.valueOf(listaClientes.get(i).getDireccion());                               
        }            
        vista.tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
                "Id", "Tid", "Documento", "Nombres", "Apellidos", "Fecha Nacimiento",
                "Genero", "Edad", "Teléfono","Correo electrónico", "Dirección"
            }
        ));  
    }
    
    public static void obtenerDatosTabla() {
        int fila = InterGestionClientes.tablaClientes.getSelectedRow();        
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro de la tabla");
            InterGestionClientes.btnActualizar.setEnabled(false);
            InterGestionClientes.btnEliminar.setEnabled(false);            
        } else {          
            habilitarbotones();
            String TID = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 1);
            String doc = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 2);
            String nom = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 3);
            String apel = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 4);            
            String fNac = (String) InterGestionClientes.tablaClientes.getValueAt(fila, 5);            
            String genero = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 6);
            String tel =  (String)InterGestionClientes.tablaClientes.getValueAt(fila, 8);            
            String mail = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 9);
            String dir = (String)InterGestionClientes.tablaClientes.getValueAt(fila, 10);            
            
            InterGestionClientes.boxTID.setSelectedItem(TID);
            InterGestionClientes.txtDoc.setText(doc);
            InterGestionClientes.txtNombre.setText(nom);       
            InterGestionClientes.txtApellido.setText(apel); 
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-mm-dd").parse(fNac);
            } catch (ParseException ex) {
                Logger.getLogger(InterGestionClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            InterGestionClientes.txtFechaNac.setDate(date);
            if ("H".equals(genero)) {
                InterGestionClientes.btnHombre.setSelected(true);
            } else {
                InterGestionClientes.btnMujer.setSelected(true);
            }
            InterGestionClientes.txtTel.setText(tel);
            InterGestionClientes.txtDir.setText(dir);
            InterGestionClientes.txtEmail.setText(mail);
        }
    }
    
     public void habilitarCampos() {
        vista.boxTID.setEnabled(true);
        vista.txtDoc.setEnabled(true);
        vista.txtNombre.setEnabled(true);       
        vista.txtApellido.setEnabled(true); 
        vista.txtFechaNac.setEnabled(true);
        vista.btnHombre.setEnabled(true);
        vista.btnMujer.setEnabled(true);
        vista.txtTel.setEnabled(true);
        vista.txtDir.setEnabled(true);
        vista.txtEmail.setEnabled(true);     
    }

    public void inhabilitarCampos() {
        vista.boxTID.setEnabled(false);
        vista.txtDoc.setEnabled(false);
        vista.txtNombre.setEnabled(false);       
        vista.txtApellido.setEnabled(false); 
        vista.txtFechaNac.setEnabled(false);
        vista.btnHombre.setEnabled(false);
        vista.btnMujer.setEnabled(false);
        vista.txtTel.setEnabled(true);
        vista.txtDir.setEnabled(true);
        vista.txtEmail.setEnabled(true);
    }

    public void inhabilitarbotones() {
        vista.btnGuardar.setEnabled(true);
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);        
    }

    public void habilitarbotonesLimpiar() {
        ListarClientes();
        disenoTabla();
        vista.btnGuardar.setEnabled(true);
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);        
    }
    public static void habilitarbotones() {        
        InterGestionClientes.btnGuardar.setEnabled(false);
        InterGestionClientes.btnActualizar.setEnabled(true);
        InterGestionClientes.btnEliminar.setEnabled(true);        
    }
    
    /**
     * Metodo para limpiar pantalla
     */
    public void limpiarCampos(){
        vista.txtDoc.setText("");
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.boxTID.setSelectedItem("Seleccione...");
        vista.txtTel.setText("");
        vista.txtDir.setText("");
        vista.txtEmail.setText("");
        vista.txtFechaNac.setDate(null);         
        vista.radioGenero.clearSelection();
    }
    
    //Método para diseñar las columnas de la tabla Cliente
    void disenoTabla() {
        //Redimensionar el tamaño de las columnas de la tabla.        
        vista.tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
        //.
        vista.tablaClientes.getColumnModel().getColumn(1).setMaxWidth(30);
        vista.tablaClientes.getColumnModel().getColumn(1).setMinWidth(30);
        vista.tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(30);
        //.
        vista.tablaClientes.getColumnModel().getColumn(2).setMaxWidth(120);
        vista.tablaClientes.getColumnModel().getColumn(2).setMinWidth(120);
        vista.tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(120);
        //.De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(3).setMaxWidth(155);
        vista.tablaClientes.getColumnModel().getColumn(3).setMinWidth(155);
        vista.tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(155);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(4).setMaxWidth(155);
        vista.tablaClientes.getColumnModel().getColumn(4).setMinWidth(155);
        vista.tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(155);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(5).setMaxWidth(120);
        vista.tablaClientes.getColumnModel().getColumn(5).setMinWidth(120);
        vista.tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(120);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(6).setMaxWidth(60);
        vista.tablaClientes.getColumnModel().getColumn(6).setMinWidth(60);
        vista.tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(60);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(7).setMaxWidth(50);
        vista.tablaClientes.getColumnModel().getColumn(7).setMinWidth(50);
        vista.tablaClientes.getColumnModel().getColumn(7).setPreferredWidth(50);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(8).setMaxWidth(130);
        vista.tablaClientes.getColumnModel().getColumn(8).setMinWidth(130);
        vista.tablaClientes.getColumnModel().getColumn(8).setPreferredWidth(130);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(9).setMaxWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(9).setMinWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(9).setPreferredWidth(0);
        //De este modo se oculta la columna 
        vista.tablaClientes.getColumnModel().getColumn(9).setMaxWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(9).setMinWidth(0);
        vista.tablaClientes.getColumnModel().getColumn(9).setPreferredWidth(0);

    }
}
