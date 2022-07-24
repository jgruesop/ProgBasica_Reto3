/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.FrmEmpleados;
import models.Directivo;
import models.Empresa;
import models.Empleado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        this.empleado = empleado;
        this.directivo = directivo;
        this.vista = vista;
        vista.btnGuardar2.addActionListener(this);
        vista.btnActualizar2.addActionListener(this);
        vista.btnCancelar2.addActionListener(this);
        vista.btnEliminar2.addActionListener(this);        
    }
     
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == vista.btnGuardar2) {
            int id = 0;
            String nombre = vista.txtNombre.getText().toUpperCase();
            String doc = vista.txtDoc.getText();
            String apel = vista.txtApellido.getText().toUpperCase();
            String TID = vista.boxTID.getSelectedItem().toString();        
            String genero = "";
            String subor = " ";
            Integer categoria = 0;
            int edad = 0;
            if (vista.checkSubordinado.isSelected()) { subor = "SI"; }
            if (vista.btnHombre.isSelected()) { genero = "H"; }
            if (vista.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = TID.equals("Seleccione...") || nombre.equals("") || apel.equals("") || vista.txtCategoria.getText().equals("");
            boolean comp2 = genero.equals("") || vista.txtFechaNac.getDate() == null || vista.txtSalario.getText().isEmpty();

            if (comp1 || comp2) {
                JOptionPane.showMessageDialog(null, "Faltan campos por diligenciar.");
            } else {
                if (!vista.txtCategoria.getText().equals("0")){
                    categoria = Integer.parseInt(vista.txtCategoria.getText());
                }
                //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                sqlPackageDate = new java.sql.Date(vista.txtFechaNac.getDate().getTime());
                /// Da formato a la fecha obtenida en la linea anterior
                Date fNac = sqlPackageDate;
                Double salario = Double.parseDouble(vista.txtSalario.getText());
                // Determina si la casilla directivo fue seleccionada            
                if (vista.checkDirectivo.isSelected()) {
                    directivo = new Directivo(id, TID, doc, nombre, apel, fNac, genero , edad, salario, subor, categoria);
                    res = empresa.agregarEmpleado(directivo);
                } else {
                    vista.txtCategoria.setEnabled(false);
                    empleado = new Empleado(id, TID, doc, nombre, apel, fNac, genero, edad, salario, subor);
                    res = empresa.agregarEmpleado(empleado);
                }

                if (res == true) {
                    JOptionPane.showMessageDialog(null, "Datos almacenados exitosamente.");
                    obtenerListarEmpleados();
                    disenoTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                }
            }
        }
        
        if (evt.getSource() == vista.btnActualizar2) {
            int id = 0;
            String nombre = vista.txtNombre.getText().toUpperCase();
            String doc = vista.txtDoc.getText();
            String apel = vista.txtApellido.getText().toUpperCase();
            String TID = vista.boxTID.getSelectedItem().toString();        
            String genero = "";
            String subor = " ";
            Integer categoria = 0;
            int edad  = 0;

            if (vista.checkSubordinado.isSelected()) { subor = "SI"; }
            if (vista.btnHombre.isSelected()) { genero = "H"; }
            if (vista.btnMujer.isSelected()) { genero = "M"; }

            // Compara si todos los campos estan vacios
            boolean comp1 = TID.equals("Seleccione...") || nombre.equals("") || apel.equals("") || vista.txtCategoria.getText().equals("");
            boolean comp2 = genero.equals("") || vista.txtFechaNac.getDate() == null || vista.txtSalario.getText().isEmpty();


            int fila = vista.tablaEmpleados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {
                if (comp1 || comp2) {
                    JOptionPane.showMessageDialog(null, "La información no registra ningún cambio.");
                } else {
                     if (!vista.txtCategoria.getText().equals("0")){
                        categoria = Integer.parseInt(vista.txtCategoria.getText());
                    }
                    //Permite obtener solo la fecha 1900/01/01 desde un JDatechooser
                    java.sql.Date sqlPackageDate = new java.sql.Date(vista.txtFechaNac.getDate().getTime());
                    /// Da formato a la fecha obtenida en la linea anterior
                    Date fNac = sqlPackageDate;
                    Double salario = Double.parseDouble(vista.txtSalario.getText());
                    // Determina si la casilla directivo fue seleccionada
                    if (vista.checkDirectivo.isSelected()) {
                        directivo = new Directivo(id, TID, doc, nombre, apel, fNac, genero, edad, salario, subor, categoria);
                        res = empresa.modificarEmpleado(fila, empleado);
                    } else {
                        empleado = new Empleado(id, TID, doc, nombre, apel, fNac, genero, edad, salario, subor);
                        res = empresa.modificarEmpleado(fila, empleado);
                    }
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Datos Actualizados exitosamente.");
                        obtenerListarEmpleados();
                        disenoTabla();
                        limpiarCampos();
                        inhabilitarCampos();
                        habilitarbotones();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }
                }
            }
        }
        
        if (evt.getSource() == vista.btnEliminar2) {
            inhabilitarCampos();
            int fila = vista.tablaEmpleados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccionar el registro de la tabla");
            } else {           
                int op = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar "
                    + "el registro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (op == JOptionPane.YES_OPTION) {
                    boolean res = empresa.eliminarEmpleado(fila);
                    if (res == true) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                        obtenerListarEmpleados();
                        limpiarCampos();
                        habilitarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en el proceso de almacenamiento.");
                    }
                }
                obtenerListarEmpleados();
                disenoTabla();
                limpiarCampos();
                inhabilitarbotones();
                habilitarCampos();
            }
        }
        
        if (evt.getSource() == vista.btnCancelar2) {
            habilitarbotonesLimpiar();
            habilitarCampos();
            limpiarCampos();
        }
    }

    /* FORMA DE SOBRESCRIBIR EL METODO DEL KEYTYPE PARA ESCUCHAR UN TEXTBOX
    private void type(){
        vista.txtSalario.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });              
    }*/
    
      
    
  /**********************************************************************************************************/  
    
       
    /**
     * *
     * Metodo para listar los clientes en la tabla
     */
    public void obtenerListarEmpleados() {

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Tid");
        modelo.addColumn("Documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Fecha Nacimiento");
        modelo.addColumn("Genero");
        modelo.addColumn("Edad");
        modelo.addColumn("Salario Neto");
        modelo.addColumn("Directivo");
        modelo.addColumn("Subordinado");
        modelo.addColumn("Categoria");

        String[] user = new String[12];

        for (int i = 0; i < empresa.getEmpleados().size(); i++) {

            user[0] = String.valueOf(empresa.getEmpleados().get(i).getId());
            user[1] = String.valueOf(empresa.getEmpleados().get(i).getTID());
            user[2] = String.valueOf(empresa.getEmpleados().get(i).getDocumento());
            user[3] = String.valueOf(empresa.getEmpleados().get(i).getNombre());
            user[4] = String.valueOf(empresa.getEmpleados().get(i).getApellidos());
            user[5] = String.valueOf(empresa.getEmpleados().get(i).getFechaNacimiento());
            user[6] = String.valueOf(empresa.getEmpleados().get(i).getGenero());
            user[7] = String.valueOf(empresa.getEmpleados().get(i));
            user[8] = String.valueOf(empresa.getEmpleados().get(i).getSalario());
            user[9] = String.valueOf(empresa.getEmpleados().get(i).getSubordinado());
            if (vista.txtCategoria.getText().equals("") || vista.txtCategoria.getText().equals("0")) {
                
            } else {
                user[10] = String.valueOf(directivo.getCategoria());
            }
            modelo.addRow(user);
        }
        vista.tablaEmpleados.setModel(modelo);
    }

    public void habilitarCampos() {
        vista.boxTID.setEnabled(true);
        vista.txtDoc.setEnabled(true);
        vista.txtNombre.setEnabled(true);
        vista.txtApellido.setEnabled(true);
        vista.txtFechaNac.setEnabled(true);
        vista.btnHombre.setEnabled(true);
        vista.btnMujer.setEnabled(true);
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
        vista.txtCategoria.setEnabled(false);
    }

    public void inhabilitarbotones() {
        vista.btnGuardar2.setEnabled(true);
        vista.btnActualizar2.setEnabled(false);
        vista.btnEliminar2.setEnabled(false);
    }

    public void habilitarbotonesLimpiar() {
        obtenerListarEmpleados();
        disenoTabla();
        vista.btnGuardar2.setEnabled(true);
        vista.btnActualizar2.setEnabled(false);
        vista.btnEliminar2.setEnabled(false);
    }

    public void habilitarbotones() {
        vista.btnGuardar2.setEnabled(false);
        vista.btnActualizar2.setEnabled(true);
        vista.btnEliminar2.setEnabled(true);
    }

    /**
     * Metodo para limpiar pantalla
     */
    public void limpiarCampos() {
        vista.txtDoc.setText("");
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.boxTID.setSelectedItem("Seleccione...");
        vista.txtTel.setText("");
        vista.txtDir.setText("");
        vista.txtEmail.setText("");
        vista.txtFechaNac.setDate(null);
        vista.radioGenero.clearSelection();
        vista.txtSalario.setText("");
        vista.checkSubordinado.setSelected(false);
        vista.checkDirectivo.setSelected(false);
        vista.txtCategoria.setText("0");
        vista.txtCategoria.setEnabled(false);
    }

    //Método para diseñar las columnas de la tabla Empresa
    void disenoTabla() {
        //Redimensionar el tamaño de las columnas de la tabla.        
        vista.tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(0);
        //.
        vista.tablaEmpleados.getColumnModel().getColumn(1).setMaxWidth(30);
        vista.tablaEmpleados.getColumnModel().getColumn(1).setMinWidth(30);
        vista.tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(30);
        //.
        vista.tablaEmpleados.getColumnModel().getColumn(2).setMaxWidth(120);
        vista.tablaEmpleados.getColumnModel().getColumn(2).setMinWidth(120);
        vista.tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(120);
        //.De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(3).setMaxWidth(155);
        vista.tablaEmpleados.getColumnModel().getColumn(3).setMinWidth(155);
        vista.tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(155);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(4).setMaxWidth(155);
        vista.tablaEmpleados.getColumnModel().getColumn(4).setMinWidth(155);
        vista.tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(155);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(5).setMaxWidth(120);
        vista.tablaEmpleados.getColumnModel().getColumn(5).setMinWidth(120);
        vista.tablaEmpleados.getColumnModel().getColumn(5).setPreferredWidth(120);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(6).setMaxWidth(60);
        vista.tablaEmpleados.getColumnModel().getColumn(6).setMinWidth(60);
        vista.tablaEmpleados.getColumnModel().getColumn(6).setPreferredWidth(60);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(7).setMaxWidth(50);
        vista.tablaEmpleados.getColumnModel().getColumn(7).setMinWidth(50);
        vista.tablaEmpleados.getColumnModel().getColumn(7).setPreferredWidth(50);
        //.
        vista.tablaEmpleados.getColumnModel().getColumn(8).setMaxWidth(130);
        vista.tablaEmpleados.getColumnModel().getColumn(8).setMinWidth(130);
        vista.tablaEmpleados.getColumnModel().getColumn(8).setPreferredWidth(130);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(9).setMaxWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(9).setMinWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(9).setPreferredWidth(0);
        //De este modo se oculta la columna 
        vista.tablaEmpleados.getColumnModel().getColumn(9).setMaxWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(9).setMinWidth(0);
        vista.tablaEmpleados.getColumnModel().getColumn(9).setPreferredWidth(0);

    }
    
}
