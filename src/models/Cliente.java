/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.util.Date;


/**
 *
 * @author Jhonatan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Cliente extends Persona {
    
    private int idCliente;    
    private String telefono;
    private String email;
    private String direccion;

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
     * @param idCliente
     * @param telefono
     * @param direccion
     * @param email 
     */
    public Cliente( int id, String TID, String documento, String nombre, String apellidos, 
            Date fechaNacimiento, String genero, int edad, int idCliente, String telefono, 
            String direccion, String email) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;        
    }

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
     * @param idCliente
     * @param telefono
     * @param direccion 
     */
    public Cliente( int id, String TID, String documento, String nombre, 
            String apellidos, Date fechaNacimiento, String genero, int edad, 
            int idCliente, String telefono, String direccion) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;        
    }  
    
    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    } 

    /**
     * @return the id
     */    
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param id the id to set
     */
    public void setIdCliente(int id) {
        this.idCliente = id;
    }    
}
