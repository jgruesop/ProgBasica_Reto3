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
import java.text.SimpleDateFormat;
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
    String fNac;

    
    ControllerClientes(Empresa empresa, Cliente cliente, InterGestionClientes vista) {
        this.empresa = empresa;
        this.cliente = cliente;
        this.vista = vista;
        vista.btnGuardar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
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
            char genero = ' ';

            if (vista.btnHombre.isSelected()){ genero = 'H'; }
            if(vista.btnMujer.isSelected()) { genero = 'M'; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || vista.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero == ' ';

            if ( comp1 || comp2 ){
                JOptionPane.showMessageDialog(null, "Debe diligenciar todos los campos.");
            } else {                
                //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                sqlPackageDate = new java.sql.Date(vista.txtFechaNac.getDate().getTime());
                /// Da formato a la fecha obtenida en la linea anterior
                fNac = df.format(sqlPackageDate);
                cliente = new Cliente(id, TID, doc, nombre, apel, fNac, genero, tel, dir, email);                        
                boolean res = empresa.agregarCliente(cliente);
                if (res == true) {
                    JOptionPane.showMessageDialog(null, "Datos almacenados exitosamente.");                                
                    obtenerListarClientes();                
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
            char genero = ' ';

            if (vista.btnHombre.isSelected()){ genero = 'H'; }
            if(vista.btnMujer.isSelected()) { genero = 'M'; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || vista.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero == ' ';

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
                    String fNac = df.format(sqlPackageDate);
                    cliente = new Cliente(id, TID, doc, nombre, apel, fNac,  genero, tel,  dir, email);                        
                    boolean res = empresa.modificarCliente(fila, cliente);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Datos Actualizados exitosamente.");    
                        //limpiarTabla(modelo);
                        obtenerListarClientes();                
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
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {                
                int op = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (op == JOptionPane.YES_OPTION) {                    
                    boolean res = empresa.eliminarCliente(fila);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                        obtenerListarClientes();
                        limpiarCampos();
                        habilitarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }                    
                }
                obtenerListarClientes();
                disenoTabla();
                limpiarCampos();
                inhabilitarbotones();
                habilitarCampos();
            }             
        }
        
        if(evt.getSource() == vista.btnCancelar) {
            limpiarCampos();
            habilitarbotonesLimpiar();  
        }
    }
    
    
    /***
     * Metodo para listar los clientes en la tabla
     */
    public void obtenerListarClientes() {       
        
        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Tid");
        modelo.addColumn("Documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Fecha Nacimiento");
        modelo.addColumn("Genero");
        modelo.addColumn("Edad");        
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo electrónico");
        modelo.addColumn("Dirección");
        
        String[] user = new String[11];
        
        for (int i = 0; i < empresa.getClientes().size(); i++) {
            
            user[0] = String.valueOf(empresa.getClientes().get(i).getId());
            user[1] = String.valueOf(empresa.getClientes().get(i).getTID());
            user[2] = String.valueOf(empresa.getClientes().get(i).getDocumento());
            user[3] = String.valueOf(empresa.getClientes().get(i).getNombre());
            user[4] = String.valueOf(empresa.getClientes().get(i).getApellidos());
            user[5] = String.valueOf(empresa.getClientes().get(i).getFechaNacimiento());
            user[6] = String.valueOf(empresa.getClientes().get(i).getGenero());
            user[7] = String.valueOf(cliente.calcularEdad());
            user[8] = String.valueOf(empresa.getClientes().get(i).getTelefono());
            user[9] = String.valueOf(empresa.getClientes().get(i).getEmail());
            user[10] = String.valueOf(empresa.getClientes().get(i).getDireccion());
            modelo.addRow(user);
        }
        vista.tablaClientes.setModel(modelo);
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
        vista.txtTel.setEnabled(false);
        vista.txtDir.setEnabled(false);
        vista.txtEmail.setEnabled(false);
    }

    public void inhabilitarbotones() {
        vista.btnGuardar.setEnabled(true);
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);        
    }

    public void habilitarbotonesLimpiar() {
        obtenerListarClientes();
        disenoTabla();
        vista.btnGuardar.setEnabled(true);
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);        
    }
    public void habilitarbotones() {        
        vista.btnGuardar.setEnabled(false);
        vista.btnActualizar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);        
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
    
    //Método para diseñar las columnas de la tabla Empresa
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