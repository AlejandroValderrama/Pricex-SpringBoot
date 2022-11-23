package com.api.pricex.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author Alejandro Valderrama
 */
@Embeddable
public class PrecioId implements Serializable {

    
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne(cascade={CascadeType.REFRESH})
    private Juego juego;

    
    public PrecioId() {

    }


    public PrecioId(Date fecha, Juego juego) {
        this.fecha = fecha;
        this.juego = juego;
    }


    
    /** 
     * Devuelve la fecha del precio del juego.
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }


    
    /** 
     * Devuelve el juego.
     * @return Juego
     */
    public Juego getJuego() {
        return juego;
    }


    
    /** 
     * Modifica la fecha del precio del juego
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    
    /** 
     * Modifica el juego.
     * @param juego
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
    }


    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
        result = prime * result + ((juego == null) ? 0 : juego.hashCode());
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
        PrecioId other = (PrecioId) obj;
        if (fecha == null) {
            if (other.fecha != null)
                return false;
        } else if (!fecha.equals(other.fecha))
            return false;
        if (juego == null) {
            if (other.juego != null)
                return false;
        } else if (!juego.equals(other.juego))
            return false;
        return true;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "PrecioId [fecha=" + fecha + ", juego=" + juego + "]";
    }
    
}
