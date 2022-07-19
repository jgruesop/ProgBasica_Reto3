/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Q-USER
 */
public class Login extends Persona{
    
    public String usuario;
    public String pass;

    
    /**
     * 
     * @param id
     * @param TID
     * @param documento
     * @param nombre
     * @param apellidos
     * @param fechaNacimiento
     * @param genero
     * @param edad
     * @param usuario
     * @param pass 
     */
    public Login(int id, String TID, String documento, String nombre, String apellidos, Date fechaNacimiento, String genero, int edad, String usuario, String pass) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.usuario = usuario;
        this.pass = pass;
    }

 
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    
}
