/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Cliente;
import models.Empresa;
import views.FrmClientes;
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

    private final Empresa empresa;    
    private Cliente cliente ;  
    private final FrmClientes vista;
    private java.sql.Date sqlPackageDate;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultTableModel modelo;    //Modelo por defecto de la Tabla
    
    /**
     *
     * @param empresa
     * @param vista
     */
    public ControllerClientes(Empresa empresa, FrmClientes vista ) {
        this.vista = vista;
        this.empresa = empresa;
        FrmClientes.btnGuardar.addActionListener(this);
        FrmClientes.btnActualizar.addActionListener(this);
        FrmClientes.btnCancelar.addActionListener(this);
        FrmClientes.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        FrmClientes.tablaClientes.getTableHeader().setReorderingAllowed(false);//Bloquea el movimiento de las columnas, e impide imvertir la información.
        inhabilitarbotones();        
        listarClientes();
        disenoTabla();
    }
   
    @Override
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == FrmClientes.btnGuardar) {
            int idCliente = 0;   
            int idEmpresa = empresa.getId();
            String nombre = FrmClientes.txtNombre.getText().toUpperCase();
            String doc = FrmClientes.txtDoc.getText();
            String apel = FrmClientes.txtApellido.getText().toUpperCase();
            String TID = FrmClientes.boxTID.getSelectedItem().toString();
            String tel = FrmClientes.txtTel.getText();
            String dir = FrmClientes.txtDir.getText().toUpperCase();
            String email = FrmClientes.txtEmail.getText();                            
            String genero = "";
            int edad = 0;
            if (FrmClientes.btnHombre.isSelected()){ genero = "H"; }
            if(FrmClientes.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || FrmClientes.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero.equals("");

            if ( comp1 || comp2 ){                
                JOptionPane.showMessageDialog(null, "Faltan datos por ingresar.");
            } else {                
                //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                sqlPackageDate = new java.sql.Date(FrmClientes.txtFechaNac.getDate().getTime());
                /// Da formato a la fecha obtenida en la linea anterior
                Date fNac = sqlPackageDate;
                cliente = new Cliente(TID, doc, nombre, apel, (java.sql.Date) fNac, genero, edad, idCliente, tel, dir, email, idEmpresa);
                boolean res = empresa.duplicados(cliente);
                if (res == true) {
                    JOptionPane.showMessageDialog(null, "Ya existe un cliente con el documento " + doc);                                
                }else {
                    boolean res1 = empresa.agregarCliente(cliente, empresa);
                    if (res1 == true) {
                        JOptionPane.showMessageDialog(null, "Datos almacenados exitosamente.");                                
                        listarClientes();                
                        disenoTabla();
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }
                }
                
            }
        }
        
        if (evt.getSource() == FrmClientes.btnActualizar) {             
            int fila = FrmClientes.tablaClientes.getSelectedRow();
            int idPersona = Integer.parseInt((String)FrmClientes.tablaClientes.getValueAt(fila, 0));            
            int idEmpresa = empresa.getId();
            String nombre = FrmClientes.txtNombre.getText().toUpperCase();
            String doc = FrmClientes.txtDoc.getText();
            String apel = FrmClientes.txtApellido.getText().toUpperCase();
            String TID = FrmClientes.boxTID.getSelectedItem().toString();
            String tel = FrmClientes.txtTel.getText();
            String dir = FrmClientes.txtDir.getText().toUpperCase();
            String email = FrmClientes.txtEmail.getText();               
            String genero = "";
            int edad = 0;    
            if (FrmClientes.btnHombre.isSelected()){ genero = "H"; }
            if(FrmClientes.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || FrmClientes.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero.equals("");

            
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La información no registra ningún cambio.");
                } else {      
                    int idCliente = Integer.parseInt((String)FrmClientes.tablaClientes.getValueAt(fila, 8));  
                    //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                    java.sql.Date sqlPackageDate = new java.sql.Date(FrmClientes.txtFechaNac.getDate().getTime());
                    /// Da formato a la fecha obtenida en la linea anterior
                    Date fNac = sqlPackageDate;
                    cliente = new Cliente(idPersona, TID, doc, nombre, apel, fNac, genero, edad, idCliente, tel,  dir, email, idEmpresa);                        
                    boolean res = empresa.modificarCliente(cliente, empresa);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Datos Actualizados exitosamente.");                            
                        listarClientes();                
                        disenoTabla();
                        limpiarCampos();
                        habilitarCampos();
                        inhabilitarbotones(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }
                }
            }
        }
        
        if (evt.getSource() == FrmClientes.btnEliminar) {            
            int fila = FrmClientes.tablaClientes.getSelectedRow();
            int idPersona = Integer.parseInt((String)FrmClientes.tablaClientes.getValueAt(fila, 0));             
            int idEmpresa = empresa.getId();
            String nombre = FrmClientes.txtNombre.getText().toUpperCase();
            String doc = FrmClientes.txtDoc.getText();
            String apel = FrmClientes.txtApellido.getText().toUpperCase();
            String TID = FrmClientes.boxTID.getSelectedItem().toString();
            String tel = FrmClientes.txtTel.getText();
            String dir = FrmClientes.txtDir.getText().toUpperCase();
            String email = FrmClientes.txtEmail.getText();               
            String genero = "";
            int edad = 0;    
            if (FrmClientes.btnHombre.isSelected()){ genero = "H"; }
            if(FrmClientes.btnMujer.isSelected()) { genero = "M"; } 
            
            // Compara si todos los campos estan vacios
            boolean comp1 = nombre.equals("") || apel.equals("") || TID.equals("Seleccione...") || FrmClientes.txtFechaNac.getDate() == null ;
            boolean comp2 = tel.equals("") ||  dir.equals("") || email.equals("") || genero.equals("");

            
            if (fila < 0) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {   
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La acción seleccionada no tiene efecto.");
                } else {      
                    int op = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (op == JOptionPane.YES_OPTION) { 
                        int idCliente = Integer.parseInt((String)FrmClientes.tablaClientes.getValueAt(fila, 8)); 
                        //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                        java.sql.Date sqlPackageDate = new java.sql.Date(FrmClientes.txtFechaNac.getDate().getTime());
                        /// Da formato a la fecha obtenida en la linea anterior
                        Date fNac = sqlPackageDate;
                        cliente = new Cliente(idPersona, TID, doc, nombre, apel, fNac, genero, edad, idCliente, tel,  dir, email, idEmpresa);                        
                        boolean res = empresa.eliminarCliente(idCliente, cliente, empresa);
                        if (res == true) {
                            JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                            listarClientes();
                            disenoTabla();
                            limpiarCampos();
                            habilitarCampos();
                            inhabilitarbotones();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                        }                    
                    }
                }              
            }             
        }
        
         if (evt.getSource() == vista.btnBuscar) {
             buscarClientes();
             disenoTabla();
         }
        
        if (evt.getSource() == FrmClientes.btnCancelar) {            
            listarClientes();
            disenoTabla();  
            inhabilitarbotones();  
            limpiarCampos(); 
            habilitarCampos();                       
        }        
        
    }
    
    
    /***
     * Metodo para listar los clientes en la tabla
     */
    public void listarClientes() {       
        
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        listaClientes = empresa.obtenerClientes();
        
        Object[][] matriz = new Object[listaClientes.size()][12];            
        modelo = (DefaultTableModel) FrmClientes.tablaClientes.getModel();                    
        
        for (int i = 0; i < listaClientes.size(); i++){
            matriz[i][0] = String.valueOf(listaClientes.get(i).getId());
            matriz[i][1] = String.valueOf(listaClientes.get(i).getTID());
            matriz[i][2] = String.valueOf(listaClientes.get(i).getDocumento());
            matriz[i][3] = String.valueOf(listaClientes.get(i).getNombre());
            matriz[i][4] = String.valueOf(listaClientes.get(i).getApellidos());
            matriz[i][5] = String.valueOf(listaClientes.get(i).getFechaNacimiento());
            matriz[i][6] = String.valueOf(listaClientes.get(i).getGenero());
            matriz[i][7] = String.valueOf(listaClientes.get(i).getEdad());
            matriz[i][8] = String.valueOf(listaClientes.get(i).getIdCliente());
            matriz[i][9] = String.valueOf(listaClientes.get(i).getTelefono());
            matriz[i][10] = String.valueOf(listaClientes.get(i).getDireccion());
            matriz[i][11] = String.valueOf(listaClientes.get(i).getEmail());               
        }            
        FrmClientes.tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
                "Id", "Tid", "Documento", "Nombres", "Apellidos", "Fecha Nacimiento",
                "Genero", "Edad", "IdCliente", "Teléfono", "Dirección","Correo electrónico"
                
            }
        ));  
    }
    
    public static void obtenerDatosTabla() {
        int fila = FrmClientes.tablaClientes.getSelectedRow();        
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro de la tabla");
            FrmClientes.btnActualizar.setEnabled(false);
            FrmClientes.btnEliminar.setEnabled(false);            
        } else {          
            habilitarbotones();
            inhabilitarCampos();
            FrmClientes.txtBuscar.setText("");
            String TID = (String)FrmClientes.tablaClientes.getValueAt(fila, 1);
            String doc = (String)FrmClientes.tablaClientes.getValueAt(fila, 2);
            String nom = (String)FrmClientes.tablaClientes.getValueAt(fila, 3);
            String apel = (String)FrmClientes.tablaClientes.getValueAt(fila, 4);            
            String fNac = (String) FrmClientes.tablaClientes.getValueAt(fila, 5);            
            String genero = (String)FrmClientes.tablaClientes.getValueAt(fila, 6);
            String tel =  (String)FrmClientes.tablaClientes.getValueAt(fila, 9);            
            String mail = (String)FrmClientes.tablaClientes.getValueAt(fila, 11);
            String dir = (String)FrmClientes.tablaClientes.getValueAt(fila, 10);                         
            
            FrmClientes.boxTID.setSelectedItem(TID);
            FrmClientes.txtDoc.setText(doc);
            FrmClientes.txtNombre.setText(nom);       
            FrmClientes.txtApellido.setText(apel); 
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-mm-dd").parse(fNac);
            } catch (ParseException ex) {
                Logger.getLogger(FrmClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            FrmClientes.txtFechaNac.setDate(date);
            if ("H".equals(genero)) {
                FrmClientes.btnHombre.setSelected(true);
            } else {
                FrmClientes.btnMujer.setSelected(true);
            }
            FrmClientes.txtTel.setText(tel);
            FrmClientes.txtDir.setText(dir);
            FrmClientes.txtEmail.setText(mail);            
        }
    }
    
    public void buscarClientes() {       
        
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        listaCliente = empresa.buscarCliente(empresa);
        
         if (vista.txtBuscar.getText().equals("Buscar NIT") || listaCliente.isEmpty() || vista.txtBuscar.getText().equals("")) {
             JOptionPane.showMessageDialog(null, "No se encontró ningún resultado");
             listarClientes();
        } else {
            Object[][] matriz = new Object[listaCliente.size()][12];            
            modelo = (DefaultTableModel) FrmClientes.tablaClientes.getModel();            

            for (int i = 0; i < listaCliente.size(); i++){
                matriz[i][0] = String.valueOf(listaCliente.get(i).getId());
                matriz[i][1] = String.valueOf(listaCliente.get(i).getTID());
                matriz[i][2] = String.valueOf(listaCliente.get(i).getDocumento());
                matriz[i][3] = String.valueOf(listaCliente.get(i).getNombre());
                matriz[i][4] = String.valueOf(listaCliente.get(i).getApellidos());
                matriz[i][5] = String.valueOf(listaCliente.get(i).getFechaNacimiento());
                matriz[i][6] = String.valueOf(listaCliente.get(i).getGenero());
                matriz[i][7] = String.valueOf(listaCliente.get(i).getEdad());   
                matriz[i][8] = String.valueOf(listaCliente.get(i).getIdCliente());
                matriz[i][9] = String.valueOf(listaCliente.get(i).getTelefono());
                matriz[i][10] = String.valueOf(listaCliente.get(i).getDireccion());
                matriz[i][11] = String.valueOf(listaCliente.get(i).getEmail()); 
            }            
            FrmClientes.tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String [] {
                    "Id", "Tid", "Documento", "Nombres", "Apellidos", "Fecha Nacimiento",
                    "Genero", "Edad", "IdCliente", "Teléfono","Correo electrónico", "Dirección"
                }
            ));
            
        }
    }
    
    public static void habilitarCampos() {
        FrmClientes.boxTID.setEnabled(true);
        FrmClientes.txtDoc.setEnabled(true);  
    }

    private static void inhabilitarCampos() {
        FrmClientes.boxTID.setEnabled(false);
        FrmClientes.txtDoc.setEnabled(false);
    }

    private void inhabilitarbotones() {
        FrmClientes.btnGuardar.setEnabled(true);
        FrmClientes.btnActualizar.setEnabled(false);
        FrmClientes.btnEliminar.setEnabled(false);        
    }

    
    private static void habilitarbotones() {        
        FrmClientes.btnGuardar.setEnabled(false);
        FrmClientes.btnActualizar.setEnabled(true);
        FrmClientes.btnEliminar.setEnabled(true);        
    }
    
    /**
     * Metodo para limpiar pantalla
     */
    private void limpiarCampos(){
        FrmClientes.txtDoc.setText("");
        FrmClientes.txtNombre.setText("");
        FrmClientes.txtApellido.setText("");
        FrmClientes.boxTID.setSelectedItem("Seleccione...");
        FrmClientes.txtTel.setText("");
        FrmClientes.txtDir.setText("");
        FrmClientes.txtEmail.setText("");
        FrmClientes.txtBuscar.setText("");
        FrmClientes.txtFechaNac.setDate(null);         
        vista.radioGenero.clearSelection();
    }
    
    //Método para diseñar las columnas de la tabla Cliente
    void disenoTabla() {
        //Redimensionar el tamaño de las columnas de la tabla.        
        FrmClientes.tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
        //.
        FrmClientes.tablaClientes.getColumnModel().getColumn(1).setMaxWidth(30);
        FrmClientes.tablaClientes.getColumnModel().getColumn(1).setMinWidth(30);
        FrmClientes.tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(30);
        //.
        FrmClientes.tablaClientes.getColumnModel().getColumn(2).setMaxWidth(120);
        FrmClientes.tablaClientes.getColumnModel().getColumn(2).setMinWidth(120);
        FrmClientes.tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(120);
        //.De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(3).setMaxWidth(155);
        FrmClientes.tablaClientes.getColumnModel().getColumn(3).setMinWidth(155);
        FrmClientes.tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(155);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(4).setMaxWidth(155);
        FrmClientes.tablaClientes.getColumnModel().getColumn(4).setMinWidth(155);
        FrmClientes.tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(155);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(5).setMaxWidth(120);
        FrmClientes.tablaClientes.getColumnModel().getColumn(5).setMinWidth(120);
        FrmClientes.tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(120);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(6).setMaxWidth(60);
        FrmClientes.tablaClientes.getColumnModel().getColumn(6).setMinWidth(60);
        FrmClientes.tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(60);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(7).setMaxWidth(50);
        FrmClientes.tablaClientes.getColumnModel().getColumn(7).setMinWidth(50);
        FrmClientes.tablaClientes.getColumnModel().getColumn(7).setPreferredWidth(50);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(8).setMaxWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(8).setMinWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(8).setPreferredWidth(0);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(9).setMaxWidth(130);
        FrmClientes.tablaClientes.getColumnModel().getColumn(9).setMinWidth(130);
        FrmClientes.tablaClientes.getColumnModel().getColumn(9).setPreferredWidth(130);
        //De este modo se oculta la columna 
        FrmClientes.tablaClientes.getColumnModel().getColumn(10).setMaxWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(10).setMinWidth(0);
        FrmClientes.tablaClientes.getColumnModel().getColumn(10).setPreferredWidth(0);
    }
}
