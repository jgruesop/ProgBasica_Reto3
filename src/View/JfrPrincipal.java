/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.*;

/**
 *
 * @author Q-USER
 */
public class JfrPrincipal extends javax.swing.JFrame {
    
    private Empresa empresa;
    
    
    /**
     * Creates new form JfrPrincipal
     */
    public JfrPrincipal() {
        if (empresa == null) {
            this.empresa = new Empresa("","","","","","");
        }else {
            this.empresa = new Empresa(empresa.getNombre(), empresa.getNIT(), 
                empresa.getTelfono(), empresa.getDireccion(), empresa.getEmail(), 
                empresa.getActEconomica());
        }
        initComponents();
        this.setExtendedState(JfrPrincipal.MAXIMIZED_BOTH);//Permite ejecutar el formulario principal Maximizado
        this.setLocationRelativeTo(null);//Permite ubicar el formulario en el centro de la pantalla
               
    }

     public JfrPrincipal(Empresa empresa) {
        this.empresa = new Empresa(empresa.getNombre(), empresa.getNIT(), 
                empresa.getTelfono(), empresa.getDireccion(), empresa.getEmail(), 
                empresa.getActEconomica());
        initComponents();
       
        this.empresa = empresa;
        
        this.setExtendedState(JfrPrincipal.MAXIMIZED_BOTH);//Permite ejecutar el formulario principal Maximizado
        this.setLocationRelativeTo(null);//Permite ubicar el formulario en el centro de la pantalla
               
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        escritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuInicio = new javax.swing.JMenu();
        menuFormulariioEmpleado = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuFormularioCliente = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuConsultasEmpresa = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuAdministrarEmpresa = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Principal");

        escritorio.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 892, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jMenuBar1.setBorder(null);
        jMenuBar1.setBorderPainted(false);
        jMenuBar1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jMenuInicio.setText("   Inicio   ");
        jMenuInicio.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        menuFormulariioEmpleado.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuFormulariioEmpleado.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        menuFormulariioEmpleado.setText("Formulario empleado");
        menuFormulariioEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFormulariioEmpleadoActionPerformed(evt);
            }
        });
        jMenuInicio.add(menuFormulariioEmpleado);
        jMenuInicio.add(jSeparator1);

        menuFormularioCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuFormularioCliente.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        menuFormularioCliente.setText("Formulario cliente");
        menuFormularioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFormularioClienteActionPerformed(evt);
            }
        });
        jMenuInicio.add(menuFormularioCliente);
        jMenuInicio.add(jSeparator3);

        menuConsultasEmpresa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuConsultasEmpresa.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        menuConsultasEmpresa.setText("Consultas");
        menuConsultasEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsultasEmpresaActionPerformed(evt);
            }
        });
        jMenuInicio.add(menuConsultasEmpresa);
        jMenuInicio.add(jSeparator4);

        menuAdministrarEmpresa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuAdministrarEmpresa.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        menuAdministrarEmpresa.setText("Administrar");
        menuAdministrarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdministrarEmpresaActionPerformed(evt);
            }
        });
        jMenuInicio.add(menuAdministrarEmpresa);
        jMenuInicio.add(jSeparator5);

        jMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuSalir.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenuInicio.add(jMenuSalir);

        jMenuBar1.add(jMenuInicio);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        System.exit(0);//Permite salir de la aplicación
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void menuAdministrarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdministrarEmpresaActionPerformed
        this.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
        this.escritorio.repaint();  // Permite limpiar la ventana principal              
        InterListarEmpresa ventana = new InterListarEmpresa(empresa);
        escritorio.add(ventana);
        ventana.show(); 
    }//GEN-LAST:event_menuAdministrarEmpresaActionPerformed

    private void menuConsultasEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsultasEmpresaActionPerformed
        this.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
        this.escritorio.repaint();  // Permite limpiar la ventana principal              
        InterConsultasEmpresa ventana = new InterConsultasEmpresa(empresa);
        escritorio.add(ventana);
        ventana.show(); 
    }//GEN-LAST:event_menuConsultasEmpresaActionPerformed

    private void menuFormulariioEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFormulariioEmpleadoActionPerformed
        this.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
        this.escritorio.repaint();  // Permite limpiar la ventana principal              
        InterGestionEmpleados ventana = new InterGestionEmpleados(empresa);
        escritorio.add(ventana);
        ventana.show(); 
    }//GEN-LAST:event_menuFormulariioEmpleadoActionPerformed

    private void menuFormularioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFormularioClienteActionPerformed
        this.escritorio.removeAll(); // Permite cerrar cualquier ventana abierta
        this.escritorio.repaint();  // Permite limpiar la ventana principal              
        InterGestionPersonas ventana = new InterGestionPersonas(empresa);             
        escritorio.add(ventana);
        ventana.show(); 
    }//GEN-LAST:event_menuFormularioClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JfrPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuInicio;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem menuAdministrarEmpresa;
    private javax.swing.JMenuItem menuConsultasEmpresa;
    private javax.swing.JMenuItem menuFormulariioEmpleado;
    private javax.swing.JMenuItem menuFormularioCliente;
    // End of variables declaration//GEN-END:variables
}