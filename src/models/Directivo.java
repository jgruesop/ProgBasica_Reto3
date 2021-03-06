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
public class Directivo extends Empleado {

    private Integer categoria;    

//    public Directivo(int id, String TID, String documento, 
//            String nombre, String apellidos, Date fechaNacimiento, String genero, 
//            int edad, Double salario, String subordinado, Integer categoria) {
//        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad, salario, subordinado);
//        this.categoria = categoria;
//    } 

    public Directivo(Integer categoria, int id, String TID, String documento, String nombre, String apellidos, Date fechaNacimiento, String genero, int edad, int idEmpresa, int idEmpleado, Double salario, String subordinado, int idDirectivo) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad, idEmpresa, idEmpleado, salario, subordinado, idDirectivo);
        this.categoria = categoria;
    }

    public Directivo(Integer categoria, String TID, String documento, String nombre, String apellidos, Date fechaNacimiento, String genero, int edad, int idEmpresa, Double salario, String subordinado, int idDirectivo) {
        super(TID, documento, nombre, apellidos, fechaNacimiento, genero, edad, idEmpresa, salario, subordinado, idDirectivo);
        this.categoria = categoria;
    }

    public Directivo(Integer categoria, String TID, String documento, String nombre, String apellidos, Date fechaNacimiento, String genero, int edad) {
        super(TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.categoria = categoria;
    }
    
    
    
    /**
     * @return the categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }
    
}
