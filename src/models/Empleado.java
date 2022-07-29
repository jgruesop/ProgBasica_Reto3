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
    private int idEmpleado;
    public int idEmpresa;
    public int idDirectivo;
    private Double salario;
    private String subordinado;    
    
    //Contructor

    public Empleado( int id, String TID, String documento, 
            String nombre, String apellidos, Date fechaNacimiento, String genero, int edad,
            int idEmpresa, int idEmpleado, Double salario, String subordinado, int idDirectivo ) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.salario = salario;
        this.subordinado = subordinado;        
        this.idEmpleado = idEmpleado;        
        this.idEmpresa = idEmpresa;
        this.idDirectivo = idDirectivo;
    }
   
    public Empleado(String TID, String documento, 
            String nombre, String apellidos, Date fechaNacimiento, String genero, int edad,
            int idEmpresa, Double salario, String subordinado, int idDirectivo ) {
        super(TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);
        this.salario = salario;
        this.subordinado = subordinado;                
        this.idEmpresa = idEmpresa;
        this.idDirectivo = idDirectivo;
    }
    
    public Empleado(String TID, String documento, 
            String nombre, String apellidos, Date fechaNacimiento, String genero, int edad) {
        super(TID, documento, nombre, apellidos, fechaNacimiento, genero, edad);        
    }
    
   
    
    // Modificadores  *************************************
    
    /**
     * 
     * @return idEmpleado
     */
     public int getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * 
     * @param idEmpleado 
     */ 
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
    /**
     * @return the salario
     */
    public Double getSalario() {
        return salario;
    }

    /**
     * @param salario set
     */
    public void setSalario(Double salario) {
        this.salario = salario;
    }

    /**
     * @return subordinado
     */
    public String getSubordinado() {
        return subordinado;
    }

    /**
     * @param the subordinado to set
     */
    public void setSubordinado(String subordinado) {
        this.subordinado = subordinado;
    }    

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the idDirectivo
     */
    public int getIdDirectivo() {
        return idDirectivo;
    }

    /**
     * @param idDirectivo the idDirectivo to set
     */
    public void setIdDirectivo(int idDirectivo) {
        this.idDirectivo = idDirectivo;
    }
   
}
