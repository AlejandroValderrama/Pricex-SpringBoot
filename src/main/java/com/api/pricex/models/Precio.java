package com.api.pricex.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Alejandro Valderrama
 */
@Entity
@Table(name = "Precio")
public class Precio {
    
    @EmbeddedId
    @Column(unique = true, nullable = false)
    private PrecioId precioId;

    @Column(name = "precio")
    private Double precio;


    public Precio() {
    }


    public Precio(PrecioId precioId, Double precio) {
        this.precioId = precioId;
        this.precio = precio;
    }


    
    /** 
     * Devuelve el id del precio (Fecha, Nombre, Consola).
     * @return PrecioId
     */
    public PrecioId getPrecioId() {
        return precioId;
    }


    
    /** 
     * Devuelve el precio del juego.
     * @return Double
     */
    public Double getPrecio() {
        return precio;
    }


    
    /** 
     * Modifica el id del precio (Fecha, Nombre, Consola).
     * @param precioId
     */
    public void setPrecioId(PrecioId precioId) {
        this.precioId = precioId;
    }


    
    /** 
     * Modifica el precio del juego.
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((precio == null) ? 0 : precio.hashCode());
        result = prime * result + ((precioId == null) ? 0 : precioId.hashCode());
        return result;
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
            
        Precio other = (Precio) obj;
        
        if (precioId == null) {
            if (other.precioId != null)
                return false;
        } else if (!precioId.equals(other.precioId))
            return false;
        return true;
    }


    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Precio [precio=" + precio + ", precioId=" + precioId + "]";
    }

    
}
