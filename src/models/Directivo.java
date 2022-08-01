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

    public int idDir;
    private int categoria;    

    
    public Directivo(int categoria) {
        this.categoria = categoria;
    }

    public Directivo(int idDir, int categoria) {
        this.idDir = idDir;
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

    /**
     * @return the idDir
     */
    public int getIdDir() {
        return idDir;
    }

    /**
     * @param idDir the idDir to set
     */
    public void setIdDir(int idDir) {
        this.idDir = idDir;
    }
    
}
