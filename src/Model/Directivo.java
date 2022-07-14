/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author Jhonatan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Directivo extends Empleado {

    private int categoria;    

    public Directivo(int categoria, char directivo, int id, String TID, 
            String documento, String nombre, String apellidos, String fechaNacimiento, 
            char genero, Double salario, Boolean subordinado) {
        super(directivo, id, TID, documento, nombre, apellidos, fechaNacimiento, genero, salario, subordinado);
        this.categoria = categoria;
    }
    
    /**
     * @return the categoria
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
}
