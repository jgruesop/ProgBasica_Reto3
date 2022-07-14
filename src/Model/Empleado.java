package Model;

import java.time.LocalDate;
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
    private Boolean subordinado;
    public char directivo;
    
    //Contructor

    public Empleado( char directivo, int id, String TID, String documento, 
            String nombre, String apellidos, String fechaNacimiento, char genero,
            Double salario, Boolean subordinado) {
        super(id, TID, documento, nombre, apellidos, fechaNacimiento, genero);
        this.salario = salario;
        this.subordinado = subordinado;
        this.directivo = directivo = 'N';
    }
    
   
    
    // Modificadores  *************************************
    /**
     * @return the salario
     */
    private Double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    private void setSalario(Double salario) {
        this.salario = salario;
    }

    /**
     * @return the subordinado
     */
    private Boolean getSubordinado() {
        return subordinado;
    }

    /**
     * @param subordinado the subordinado to set
     */
    private void setSubordinado(Boolean subordinado) {
        this.subordinado = subordinado;
    }

    /**
     * @return the directivo
     */
    public char getDirectivo() {
        return directivo;
    }

    /**
     * @param directivo the directivo to set
     */
    public void setDirectivo(char directivo) {
        this.directivo = directivo;
    }

    
}
