package com.api.pricex.services;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.pricex.interfaces.IJuegoService;
import com.api.pricex.models.*;
import com.api.pricex.repositories.*;

/**
 * @autor Alejandro Valderrama
 */
@Service
public class JuegoService implements IJuegoService {

    @Autowired
    private juegoRepository dataJ;

    @Autowired
    private precioRepository dataP;

    
    /** 
     * Devuelve todos los juegos de la BD de una determinada consola.
     * @param consola
     * @return Lista de juegos.
     */
    @Override
    public List<Juego> getAllJuego(String consola) {
        return (List<Juego>) dataJ.findByJuegoIdConsola(consola);
    }

    
    /** 
     * Devuelve un juego de la BD pasando su id por parámetro.
     * @param juegoId
     * @return Juego
     */
    @Override
    public Optional<Juego> getJuego(JuegoId juegoId) {
        return dataJ.findById(juegoId);
    }

    
    /** 
     * Guarda en la BD un juego pasado por parámetro.
     * @param j Juego
     * @return Número entero. 1 si se ha guardado y 0 si no.
     */
    @Override
    public int saveJuego(Juego j) {
        int res = 0;
        Juego juego = dataJ.save(j);

        if(!juego.equals(null)){
            res = 1;
        }
        
        return res;
    }

    
    /** 
     * Guarda en la BD un precio de un juego pasado por parámetro.
     * @param p Precio.
     * @return Número entero. 1 si se ha guardado y 0 si no.
     */
    @Override
    public int savePrecio(Precio p) {
        int res = 0;
        Precio precio = dataP.save(p);

        if(!precio.equals(null)){
            res = 1;
        }
        
        return res;
    }
    
    
    /** 
     * Devuelve todos los precios de la BD de un juego pasado por parámetro.
     * @param juego
     * @return Lista de todos los precios de un juego.
     */
    @Override
    public List<Precio> getAllPrecio(Juego juego) {
        List<Precio> precios = (List<Precio>)dataP.findByPrecioIdJuego(juego);
        return precios;
    }

    /**
     * Devuelve de la BD todos los juegos que coincidan con el nombre pasado por parámetro.
     * @param nombre
     * @return Lista de juegos.
     */
    @Override
    public List<Juego> findJuegos(String nombre) {
        return dataJ.findByJuegoIdNombreLike("%"+nombre+"%");
    }

    
    /** 
     * Devuelve el último precio de todos los juegos de una determinada consola pasada por parámetro.
     * @param consola
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getAllJuegoLastPrecio(String consola, Pageable pageable) {
        return dataP.juegosUltimoPrecio(consola, pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han bajado de precio ordenados por nombre descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosBajanPrecioPorNombreDesc(Pageable pageable) {
        return dataP.juegosBajanPrecioOrdenNombreDesc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han bajado de precio ordenados por nombre ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosBajanPrecioPorNombreAsc(Pageable pageable) {
        return dataP.juegosBajanPrecioOrdenNombreAsc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han bajado de precio ordenados por precio descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosBajanPrecioPorPrecioDesc(Pageable pageable) {
        return dataP.juegosBajanPrecioOrdenPrecioDesc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han bajado de precio ordenados por precio ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosBajanPrecioPorPrecioAsc(Pageable pageable) {
        return dataP.juegosBajanPrecioOrdenPrecioAsc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han subido de precio ordenados por nombre descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosSubenPrecioPorNombreDesc(Pageable pageable) {
        return dataP.juegosSubenPrecioOrdenNombreDesc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han subido de precio ordenados por nombre ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosSubenPrecioPorNombreAsc(Pageable pageable) {
        return dataP.juegosSubenPrecioOrdenNombreAsc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han subido de precio ordenados por precio descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosSubenPrecioPorPrecioDesc(Pageable pageable) {
        return dataP.juegosSubenPrecioOrdenPrecioDesc(pageable);
    }

    
    /** 
     * Devuelve todos los juegos que han subido de precio ordenados por precio ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Override
    public Page<Precio> getJuegosSubenPrecioPorPrecioAsc(Pageable pageable) {
        return dataP.juegosSubenPrecioOrdenPrecioAsc(pageable);
    }


    
    /** 
     * Devuelve 12 juegos que han bajado de precio elegidos de manera aleatoria.
     * @return Lista de precios.
     */
    @Override
    public List<Precio> getJuegosBajanPrecioRandom() {
        return dataP.juegosBajanPrecioRandom();
    }


    
    /** 
     * Devuelve 12 juegos que han subido de precio elegidos de manera aleatoria.
     * @return Lista de precios.
     */
    @Override
    public List<Precio> getJuegosSubenPrecioRandom() {
        return dataP.juegosSubenPrecioRandom();
    }
    
}
