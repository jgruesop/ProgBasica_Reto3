/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.FrmEmpleados;
import models.*;
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
public class ControllerEmpleados implements ActionListener {

    private Empresa empresa;
    private Empleado empleado;
    private Directivo directivo;
    private java.sql.Date sqlPackageDate;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultTableModel modelo;    //Modelo por defecto de la Tabla
    private boolean res = false;
    private FrmEmpleados vista;
    

    public ControllerEmpleados(Empresa empresa, FrmEmpleados vista) {
        this.empresa = empresa;        
        this.vista = vista;
        FrmEmpleados.btnGuardar2.addActionListener(this);
        FrmEmpleados.btnActualizar2.addActionListener(this);
        FrmEmpleados.btnEliminar2.addActionListener(this);  
        vista.btnBuscar.addActionListener(this);
        FrmEmpleados.btnCancelar2.addActionListener(this);        
        FrmEmpleados.tablaEmpleados.getTableHeader().setReorderingAllowed(false);//Bloquea el movimiento de las columnas, e impide imvertir la información.        
        inhabilitarbotones();                
        listarEmpleados();
        disenoTabla();
    }
     
    @Override
    public void actionPerformed(ActionEvent evt) {
        
        if (evt.getSource() == FrmEmpleados.btnGuardar2) {            
            String tid = FrmEmpleados.boxTID.getSelectedItem().toString();        
            String doc = FrmEmpleados.txtDoc.getText();
            String apel = FrmEmpleados.txtApellido.getText().toUpperCase();
            String nombre = FrmEmpleados.txtNombre.getText().toUpperCase();            
            String genero = "";            
            String subor = "";            
            int idDir = FrmEmpleados.cboxDir.getSelectedIndex();
            int idEmpresa = 0;            
            int edad = 0;
            if (FrmEmpleados.checkSubordinado.isSelected()) { subor = "SI"; }
            if (FrmEmpleados.btnHombre.isSelected()) { genero = "H"; }
            if (FrmEmpleados.btnMujer.isSelected()) { genero = "M"; }                   
    
            // Compara si todos los campos estan vacios
            boolean comp1 = tid.equals("Seleccione...") || nombre.equals("") || apel.equals(""); 
            boolean comp2 = genero.equals("") || FrmEmpleados.txtFechaNac.getDate() == null || FrmEmpleados.txtSalario.getText().isEmpty();                   
            if (comp1 || comp2) {
                JOptionPane.showMessageDialog(null, "Faltan campos por diligenciar.");                
            } else {     
                if (FrmEmpleados.checkSubordinado.isSelected() && FrmEmpleados.checkDirectivo.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Un empleado directivo no puede ser subordinado ");                
                } else {                    
                    Double salario = Double.parseDouble(FrmEmpleados.txtSalario.getText());                                          
                    if (salario <= 0.0 ) {
                        JOptionPane.showMessageDialog(null, "Valor del salario no permitido ");             
                    } else {
                        if (FrmEmpleados.checkSubordinado.isSelected() && FrmEmpleados.checkDirectivo.isSelected()) {
                            JOptionPane.showMessageDialog(null, "Un empleado solo puede ser subordinado o directivo.");
                        } else {
                            if (!FrmEmpleados.checkSubordinado.isSelected() && !FrmEmpleados.checkDirectivo.isSelected()) {
                                JOptionPane.showMessageDialog(null, "Un empleado debe puede ser subordinado o directivo.");
                            }else {
                                // Determina si la casilla directivo fue seleccionada            
                                if (FrmEmpleados.checkDirectivo.isSelected() && FrmEmpleados.cboxDir.getSelectedIndex() == 0 ||
                                        FrmEmpleados.checkSubordinado.isSelected() && FrmEmpleados.cboxDir.getSelectedIndex() == 0) { 
                                    JOptionPane.showMessageDialog(null, "Debe seleccionar un directivo diferente de cero");                                                                
                                }else {  
                                    boolean respuesta = empresa.existeDirectivo(idDir);                                    
                                    if (respuesta == false && FrmEmpleados.checkDirectivo.isSelected()) {
                                        JOptionPane.showMessageDialog(null, "Ya existe un directivo en esa categoria");
                                    } else {
                                        //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                                        sqlPackageDate = new java.sql.Date(FrmEmpleados.txtFechaNac.getDate().getTime());
                                        /// Da formato a la fecha obtenida en la linea anterior
                                        Date fNac = sqlPackageDate;    
                                        //FrmEmpleados.txtCategoria.setEnabled(false);
                                        empleado = new Empleado(tid, doc, nombre, apel, fNac, genero, edad, idEmpresa, salario, subor, idDir);
                                        boolean res1 = empresa.duplicados(empleado);
                                        if (res1 == true) {
                                            JOptionPane.showMessageDialog(null, "Ya existe un empleado con el documento " + doc);                                
                                        } else {
                                            res = empresa.agregarEmpleado(empresa, empleado);
                                            if (res == true) {
                                                JOptionPane.showMessageDialog(null, "Datos almacenados exitosamente.");
                                                listarEmpleados();
                                                disenoTabla();
                                                limpiarCampos();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                                            }
                                    }
                                    }                                                                                                             
                                }
                            }
                        }                           
                    }   
                }
            }
        }
        
        if (evt.getSource() == FrmEmpleados.btnActualizar2) {    
            
            int fila = FrmEmpleados.tablaEmpleados.getSelectedRow();
            int idPersona = Integer.parseInt((String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 0));
            int idEmpresa = empresa.getId();
            int idEmpleado = Integer.parseInt((String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 9));            
            int idDir = FrmEmpleados.cboxDir.getSelectedIndex();
            String tid = FrmEmpleados.boxTID.getSelectedItem().toString();        
            String doc = FrmEmpleados.txtDoc.getText();
            String nombre = FrmEmpleados.txtNombre.getText().toUpperCase();            
            String apel = FrmEmpleados.txtApellido.getText().toUpperCase();            
            String genero = "";
            String subor = " ";            
            int edad  = 0;                                    

            if (FrmEmpleados.checkSubordinado.isSelected()) { subor = "SI"; }
            if (FrmEmpleados.btnHombre.isSelected()) { genero = "H"; }
            if (FrmEmpleados.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = tid.equals("Seleccione...") || nombre.equals("") || apel.equals("");
            boolean comp2 = genero.equals("") || FrmEmpleados.txtFechaNac.getDate() == null || FrmEmpleados.txtSalario.getText().isEmpty();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La información no registra ningún cambio.");
                } else { 
                    Double salario = Double.parseDouble(FrmEmpleados.txtSalario.getText());                   
                    if (salario <= 0.0 ) {
                        JOptionPane.showMessageDialog(null, "Valor del salario no permitido ");                
                    } else {
                        if (FrmEmpleados.checkSubordinado.isSelected() && FrmEmpleados.checkDirectivo.isSelected()) {
                            JOptionPane.showMessageDialog(null, "Un empleado subordinado no puede ser directivo.");
                        } else {
                            if (!FrmEmpleados.checkSubordinado.isSelected() && !FrmEmpleados.checkDirectivo.isSelected()) {
                                JOptionPane.showMessageDialog(null, "Un empleado debe puede ser subordinado o directivo.");
                            }else {
                                // Determina si la casilla directivo fue seleccionada            
                                if (FrmEmpleados.checkDirectivo.isSelected() && FrmEmpleados.cboxDir.getSelectedIndex() == 0 ||
                                            FrmEmpleados.checkSubordinado.isSelected() && FrmEmpleados.cboxDir.getSelectedIndex() == 0) { 
                                    JOptionPane.showMessageDialog(null, "Debe seleccionar un directivo diferente de cero");                                   
                                } else {                                                                       
                                    boolean respuesta = empresa.existeDirectivo(idDir);                                    
                                    if (respuesta == false && FrmEmpleados.checkDirectivo.isSelected()) {
                                        JOptionPane.showMessageDialog(null, "No es permitido hacer cambios a un directivo");
                                    } else {
                                        idDir = FrmEmpleados.cboxDir.getSelectedIndex();
                                        //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                                        java.sql.Date sqlPackageDate = new java.sql.Date(FrmEmpleados.txtFechaNac.getDate().getTime());
                                        /// Da formato a la fecha obtenida en la linea anterior
                                        Date fNac = sqlPackageDate;                                    
                                        empleado = new Empleado(idPersona, tid, doc, nombre, apel, fNac, genero, edad, idEmpresa, idEmpleado, salario, subor, idDir);
                                        boolean res = empresa.modificarEmpleado(empleado, empresa);                   
                                        if (res == true) {
                                            JOptionPane.showMessageDialog(null, "Datos Actualizados exitosamente.");
                                            listarEmpleados();
                                            disenoTabla();
                                            limpiarCampos();
                                            inhabilitarCampos();
                                            inhabilitarbotones();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                                        }
                                    }                                                                       
                                }
                            }                            
                        }                        
                    }                    
                }
            }
        }
        
        if (evt.getSource() == FrmEmpleados.btnEliminar2) {
            inhabilitarCampos();
            int fila = FrmEmpleados.tablaEmpleados.getSelectedRow();
            int idPersona = Integer.parseInt((String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 0));
            int idEmpresa = empresa.getId();
            int idEmpleado = Integer.parseInt((String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 9));            
            int idDir = Integer.parseInt((String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 12));
            String tid = FrmEmpleados.boxTID.getSelectedItem().toString();        
            String doc = FrmEmpleados.txtDoc.getText();
            String nombre = FrmEmpleados.txtNombre.getText().toUpperCase();            
            String apel = FrmEmpleados.txtApellido.getText().toUpperCase();            
            String genero = "";
            String subor = " ";            
            int edad  = 0;                                    

            if (FrmEmpleados.checkSubordinado.isSelected()) { subor = "SI"; }
            if (FrmEmpleados.btnHombre.isSelected()) { genero = "H"; }
            if (FrmEmpleados.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = tid.equals("Seleccione...") || nombre.equals("") || apel.equals("");
            boolean comp2 = genero.equals("") || FrmEmpleados.txtFechaNac.getDate() == null || FrmEmpleados.txtSalario.getText().isEmpty();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La acción seleccionada no tiene efecto.");
                } else {            
                    int op = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?",
                            "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (op == JOptionPane.YES_OPTION) {
                        //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                        java.sql.Date sqlPackageDate = new java.sql.Date(FrmEmpleados.txtFechaNac.getDate().getTime());
                        /// Da formato a la fecha obtenida en la linea anterior
                        Date fNac = sqlPackageDate;
                        Double salario = Double.parseDouble(FrmEmpleados.txtSalario.getText());                   
                        empleado = new Empleado(idPersona, tid, doc, nombre, apel, fNac, genero, edad, idEmpresa, idEmpleado, salario, subor, idDir);
                        boolean res = empresa.eliminarEmpleado(fila, empleado, empresa);
                        if (res == true) {
                            JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                            listarEmpleados();
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
            limpiarCampos2();  
            buscarEmpleados();            
            disenoTabla();
        }
        
        if (evt.getSource() == FrmEmpleados.btnCancelar2) {
            listarEmpleados();
            disenoTabla();
            inhabilitarbotones();
            habilitarCampos();
            limpiarCampos();            
        }
    }
       
    
     public static void obtenerDatosTabla() {
        int fila = FrmEmpleados.tablaEmpleados.getSelectedRow();        
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionadao ningún registro de la tabla");
            FrmEmpleados.btnActualizar2.setEnabled(false);
            FrmEmpleados.btnEliminar2.setEnabled(false);            
        } else {                      
            habilitarbotones();   
            inhabilitarCampos();
            String TID = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 1);
            String doc = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 2);
            String nom = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 3);
            String apel = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 4);            
            String fNac = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 5);            
            String genero = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 6);                                                     
            String salario = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 10);            
            String subordinado = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 11);            
            String directivo = (String)FrmEmpleados.tablaEmpleados.getValueAt(fila, 12);            


            FrmEmpleados.boxTID.setSelectedItem(TID);
            FrmEmpleados.txtDoc.setText(doc);
            FrmEmpleados.txtNombre.setText(nom);       
            FrmEmpleados.txtApellido.setText(apel); 
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-mm-dd").parse(fNac);
            } catch (ParseException ex) {
                Logger.getLogger(FrmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
            }
            FrmEmpleados.txtFechaNac.setDate(date);
            if ("H".equals(genero)) {
                FrmEmpleados.btnHombre.setSelected(true);
            } else {
                FrmEmpleados.btnMujer.setSelected(true);
            }

            FrmEmpleados.txtSalario.setText(salario);            
            
            if (subordinado.equals("SI")) { 
                FrmEmpleados.checkSubordinado.setSelected(true); 
                FrmEmpleados.checkDirectivo.setSelected(false);   
                FrmEmpleados.cboxDir.setSelectedIndex(Integer.parseInt(directivo));
            } else { 
                FrmEmpleados.checkSubordinado.setSelected(false); 
                FrmEmpleados.checkDirectivo.setSelected(true);                
                FrmEmpleados.cboxDir.setSelectedIndex(Integer.parseInt(directivo));
            }            
//            if (subordinado.equals("SI")) { 
//                FrmEmpleados.checkDirectivo.setSelected(false);   
//                FrmEmpleados.cboxDir.setSelectedIndex(Integer.parseInt(directivo));
//            } else { 
//                FrmEmpleados.checkDirectivo.setSelected(true);                
//                FrmEmpleados.cboxDir.setSelectedIndex(Integer.parseInt(directivo));
//            }  
        }
    }
     
    /***
     * Metodo para listar los empleado en la tabla
     */
    public void listarEmpleados() {       
        
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados = empresa.obtenerEmpleado(empresa);
        
        Object[][] matriz = new Object[listaEmpleados.size()][13];            
        modelo = (DefaultTableModel) FrmEmpleados.tablaEmpleados.getModel();                    
        
        for (int i = 0; i < listaEmpleados.size(); i++){
            matriz[i][0] = String.valueOf(listaEmpleados.get(i).getId());
            matriz[i][1] = String.valueOf(listaEmpleados.get(i).getTID());
            matriz[i][2] = String.valueOf(listaEmpleados.get(i).getDocumento());
            matriz[i][3] = String.valueOf(listaEmpleados.get(i).getNombre());
            matriz[i][4] = String.valueOf(listaEmpleados.get(i).getApellidos());
            matriz[i][5] = String.valueOf(listaEmpleados.get(i).getFechaNacimiento());
            matriz[i][6] = String.valueOf(listaEmpleados.get(i).getGenero());
            matriz[i][7] = String.valueOf(listaEmpleados.get(i).getEdad());
            matriz[i][8] = String.valueOf(listaEmpleados.get(i).getIdEmpresa());               
            matriz[i][9] = String.valueOf(listaEmpleados.get(i).getIdEmpleado());               
            matriz[i][10] = String.valueOf(listaEmpleados.get(i).getSalario());               
            matriz[i][11] = String.valueOf(listaEmpleados.get(i).getSubordinado());               
            matriz[i][12] = String.valueOf(listaEmpleados.get(i).getIdDirectivo());               
            
        }            
        FrmEmpleados.tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
                "IdPersona", "Tid", "Documento", "Nombres", "Apellidos", "Fecha Nacimiento",
                "Genero", "Edad", "IdEmpresa", "IdEmpleado", "Salario Neto", "Directivo", 
                "Subordinado", "IdDirectivo"
                
            }
        ));  
    }    

    //Metod para buscar empleados
    private void buscarEmpleados() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados = empresa.buscarEmpleados(empresa);
        
        if (FrmEmpleados.txtBuscar.getText().equals("") || listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún resultado");
            listarEmpleados();
        } else {
            Object[][] matriz = new Object[listaEmpleados.size()][13];            
            modelo = (DefaultTableModel) FrmEmpleados.tablaEmpleados.getModel();                    

            for (int i = 0; i < listaEmpleados.size(); i++){
                matriz[i][0] = String.valueOf(listaEmpleados.get(i).getId());
                matriz[i][1] = String.valueOf(listaEmpleados.get(i).getTID());
                matriz[i][2] = String.valueOf(listaEmpleados.get(i).getDocumento());
                matriz[i][3] = String.valueOf(listaEmpleados.get(i).getNombre());
                matriz[i][4] = String.valueOf(listaEmpleados.get(i).getApellidos());
                matriz[i][5] = String.valueOf(listaEmpleados.get(i).getFechaNacimiento());
                matriz[i][6] = String.valueOf(listaEmpleados.get(i).getGenero());
                matriz[i][7] = String.valueOf(listaEmpleados.get(i).getEdad());
                matriz[i][8] = String.valueOf(listaEmpleados.get(i).getIdEmpresa());               
                matriz[i][9] = String.valueOf(listaEmpleados.get(i).getIdEmpleado());               
                matriz[i][10] = String.valueOf(listaEmpleados.get(i).getSalario());               
                matriz[i][11] = String.valueOf(listaEmpleados.get(i).getSubordinado());               
                matriz[i][12] = String.valueOf(listaEmpleados.get(i).getIdDirectivo());               

            }            
            FrmEmpleados.tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String [] {
                    "IdPersona", "Tid", "Documento", "Nombres", "Apellidos", "Fecha Nacimiento",
                    "Genero", "Edad", "IdEmpresa", "IdEmpleado", "Salario Neto", "Directivo", 
                    "Subordinado", "IdDirectivo"

                }
            ));
        }        
    }
     
    public void habilitarCampos() {
        FrmEmpleados.boxTID.setEnabled(true);
        FrmEmpleados.txtDoc.setEnabled(true);        
    }

    public static void inhabilitarCampos() {
        FrmEmpleados.boxTID.setEnabled(false);
        FrmEmpleados.txtDoc.setEnabled(false);        
    }

    public void inhabilitarbotones() {
        FrmEmpleados.btnGuardar2.setEnabled(true);
        FrmEmpleados.btnActualizar2.setEnabled(false);
        FrmEmpleados.btnEliminar2.setEnabled(false);
    }

    public void habilitarbotonesLimpiar() {
        listarEmpleados();
        disenoTabla();
        FrmEmpleados.btnGuardar2.setEnabled(true);
        FrmEmpleados.btnActualizar2.setEnabled(false);
        FrmEmpleados.btnEliminar2.setEnabled(false);
    }

    public static void habilitarbotones() {
        FrmEmpleados.btnGuardar2.setEnabled(false);
        FrmEmpleados.btnActualizar2.setEnabled(true);
        FrmEmpleados.btnEliminar2.setEnabled(true);
    }

    /**
     * Metodo para limpiar pantalla
     */
    public void limpiarCampos() {
        FrmEmpleados.txtDoc.setText("");
        FrmEmpleados.txtNombre.setText("");
        FrmEmpleados.txtApellido.setText("");
        FrmEmpleados.boxTID.setSelectedItem("Seleccione...");      
        FrmEmpleados.txtFechaNac.setDate(null);
        vista.radioGenero.clearSelection();
        FrmEmpleados.txtSalario.setText("");
        FrmEmpleados.checkSubordinado.setSelected(false);
        FrmEmpleados.checkDirectivo.setSelected(false);
        FrmEmpleados.cboxDir.setSelectedIndex(0);
        FrmEmpleados.txtBuscar.setText("");
    }
    
    public void limpiarCampos2() {
        FrmEmpleados.txtDoc.setText("");
        FrmEmpleados.txtNombre.setText("");
        FrmEmpleados.txtApellido.setText("");
        FrmEmpleados.boxTID.setSelectedItem("Seleccione...");      
        FrmEmpleados.txtFechaNac.setDate(null);
        vista.radioGenero.clearSelection();
        FrmEmpleados.txtSalario.setText("");
        FrmEmpleados.checkSubordinado.setSelected(false);
        FrmEmpleados.checkDirectivo.setSelected(false);
        FrmEmpleados.cboxDir.setSelectedIndex(0);        
    }

    //Método para diseñar las columnas de la tabla Empleado
    void disenoTabla() {
        //Redimensionar el tamaño de las columnas de la tabla.        
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(0);
        //.
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(1).setMaxWidth(30);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(1).setMinWidth(30);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(30);
        //.
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(2).setMaxWidth(120);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(2).setMinWidth(120);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(120);
        //.De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(3).setMaxWidth(155);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(3).setMinWidth(155);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(155);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(4).setMaxWidth(155);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(4).setMinWidth(155);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(155);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(5).setMaxWidth(120);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(5).setMinWidth(120);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(5).setPreferredWidth(120);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(6).setMaxWidth(60);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(6).setMinWidth(60);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(6).setPreferredWidth(60);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(7).setMaxWidth(50);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(7).setMinWidth(50);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(7).setPreferredWidth(50);
        //.
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(8).setMaxWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(8).setMinWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(8).setPreferredWidth(0);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(9).setMaxWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(9).setMinWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(9).setPreferredWidth(0);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(10).setMaxWidth(135);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(10).setMinWidth(135);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(10).setPreferredWidth(135);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(11).setMaxWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(11).setMinWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(11).setPreferredWidth(0);
        //De este modo se oculta la columna 
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(12).setMaxWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(12).setMinWidth(0);
        FrmEmpleados.tablaEmpleados.getColumnModel().getColumn(12).setPreferredWidth(0);        
    }
}
