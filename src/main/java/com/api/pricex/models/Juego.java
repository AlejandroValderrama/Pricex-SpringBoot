package com.api.pricex.models;

import javax.persistence.*;

/**
 * @autor Alejandro Valderrama
 */
@Entity
@Table(name = "Juego")
public class Juego {
    
    @EmbeddedId
    private JuegoId juegoId;

    @Column(name= "imagenS")
    private String imagenS;

    @Column(name= "imagenM")
    private String imagenM;

    public Juego() {
    }

    public Juego(JuegoId juegoId) {
        this.juegoId = juegoId;
    }

    /** 
     * Devuelve el id del Juego (Título y Consola).
     * @return JuegoId
     */
    public JuegoId getJuegoId() {
        return juegoId;
    }

    
    /** 
     * Devuelve la imagen pequeña.
     * @return String
     */
    public String getImagenS() {
        return imagenS;
    }

    
    /** 
     * Devuelve la imagen grande.
     * @return String
     */
    public String getImagenM() {
        return imagenM;
    }

    
    /** 
     * Modifica el id del juego (Título y Consola).
     * @param juegoId
     */
    public void setJuegoId(JuegoId juegoId) {
        this.juegoId = juegoId;
    }

    
    /** 
     * Modifica la imagen pequeña.
     * @param imagenS
     */
    public void setImagenS(String imagenS) {
        this.imagenS = imagenS;
    }

    
    /** 
     * Modifica la imagen grande.
     * @param imagenM
     */
    public void setImagenM(String imagenM) {
        this.imagenM = imagenM;
    }

    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imagenM == null) ? 0 : imagenM.hashCode());
        result = prime * result + ((imagenS == null) ? 0 : imagenS.hashCode());
        result = prime * result + ((juegoId == null) ? 0 : juegoId.hashCode());
        return result;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Juego [imagenM=" + imagenM + ", imagenS=" + imagenS + ", juegoId=" + juegoId + "]";
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
        Juego other = (Juego) obj;
        if (juegoId == null) {
            if (other.juegoId != null)
                return false;
        } else if (!juegoId.equals(other.juegoId))
            return false;
        return true;
    }

}
