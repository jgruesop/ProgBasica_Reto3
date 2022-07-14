/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import View.JfrPrincipal;

/**
 *
 * @author Jhonatan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
              
       JfrPrincipal jfrPrincipal = new JfrPrincipal();
       jfrPrincipal.setTitle("Principal");
       jfrPrincipal.setLocationRelativeTo(null);
       jfrPrincipal.setVisible(true);       
        
    }

}
