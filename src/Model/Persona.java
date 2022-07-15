/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Jhonatan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Persona {

    //Atributos
    private int id;
    private String TID;
    private String documento;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;    
    private char genero;          

    //Constructor
    public Persona(int id, String TID, String documento, String nombre, String apellidos,
            String fechaNacimiento, char genero ) {
        this.id = id;
        this.TID = TID;     
        this.documento = documento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;        
        this.genero = genero;        
    }
   

    //Modificadores ***************
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the genero
     */
    public char getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(char genero) {
        this.genero = genero;
    }

    /**
     * @return the TID
     */
    public String getTID() {
        return TID;
    }

    /**
     * @param TID the TID to set
     */
    public void setTID(String TID) {
        this.TID = TID;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    //Metodos *********************
    /**
     * Calcuala da edad del cliente y empleado
     *
     * @return int = edad
     */
    public int calcularEdad() {
        LocalDate hoy = LocalDate.now();          
        return Period.between(getFechaNacimientoFormateada(), hoy).getYears();
    }

    private LocalDate getFechaNacimientoFormateada() {
        return LocalDate.parse(getFechaNacimiento());
    }

}
