/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Q-USER
 */
public class TipoDirectivo {

    private String Tipo;

    public TipoDirectivo() {
    }

    public TipoDirectivo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the Tipo
     */
    private String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    private void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

}
