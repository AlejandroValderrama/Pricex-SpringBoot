package com.api.pricex.models;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @autor Alejandro Valderrama
 */
@Embeddable
public class JuegoId implements Serializable {
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "consola", nullable = false)
    private String consola;

    public JuegoId() {

    }

    public JuegoId(String nombre, String consola) {
        this.nombre = nombre;
        this.consola = consola;
    }

    
    /** 
     * Devuelve el nombre del juego.
     * @return String
     */
    public String getNombre() {
        return this.nombre;
    }

    
    /** 
     * Devuelve la consola del juego.
     * @return String
     */
    public String getConsola() {
        return this.consola;
    }

    
    /** 
     * Modifica el nombre del juego.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    /** 
     * Modifica la consola del juego.
     * @param consola
     */
    public void setConsola(String consola) {
        this.consola = consola;
    }

    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((consola == null) ? 0 : consola.hashCode());
        return result;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return nombre +", "+ consola ;
    }

    
    /** 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JuegoId other = (JuegoId) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (consola == null) {
            if (other.consola != null)
                return false;
        } else if (!consola.equals(other.consola))
            return false;
        return true;
    }

}
