package models;

import java.util.Date;

/**
 *
 * @author Jhonatan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Empleado extends Persona {
    
    //Atributos
    private Double salario;
    private String subordinado;    
    
    //Contructor

    public Empleado( int id, String TID, String documento, 
            String nombre, String apellidos, Date fechaNacimiento, String genero, int edad,
            Double salario, String subordinado ) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.salario = salario;
        this.subordinado = subordinado;        
    }
    
   
    
    // Modificadores  *************************************
    /**
     * @return the salario
     */
    public Double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(Double salario) {
        this.salario = salario;
    }

    /**
     * @return the subordinado
     */
    public String getSubordinado() {
        return subordinado;
    }

    /**
     * @param subordinado the subordinado to set
     */
    public void setSubordinado(String subordinado) {
        this.subordinado = subordinado;
    }
}
