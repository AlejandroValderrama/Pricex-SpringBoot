package com.api.pricex.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.pricex.models.*;

/**
 * @autor Alejandro Valderrama
 */
@Repository
public interface juegoRepository extends JpaRepository <Juego, JuegoId>{
    
   /**
     * Devuelve todos los juegos de la BD de una determinada consola.
     * @param consola
     * @return Lista de juegos.
    */
   List<Juego> findByJuegoIdConsola(String consola);


   /**
     * Devuelve de la BD todos los juegos que coincidan con el nombre pasado por parámetro.
     * @param nombre
     * @return Lista de juegos.
    */
   List<Juego> findByJuegoIdNombreLike(String nombre);

}
