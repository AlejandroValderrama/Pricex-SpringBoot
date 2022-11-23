package com.api.pricex.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.pricex.models.*;


/**
 * @author Alejandro Valderrama
 */
@Repository
public interface precioRepository extends JpaRepository<Precio, PrecioId> {
    
    /**
     * Devuelve todos los precios de un determinado juego pasado por parámetro.
     * @param juego
     * @return Lista de precios.
     */
    List<Precio> findByPrecioIdJuego(Juego juego);


    /**
     * Devuelve el ultimo precio de todos los juegos de una determinada consola indicado por parámetro, ordenado por nombre descendente.
     * @param consola Consola de los juegos.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT j.* FROM precio j, (SELECT MAX(fecha) as fecha, juego_nombre, juego_consola FROM precio GROUP BY juego_nombre, juego_consola) t1  WHERE j.fecha = t1.fecha AND j.juego_nombre = t1.juego_nombre AND j.juego_consola = t1.juego_consola AND j.juego_consola = :consola ",
        countQuery = "SELECT COUNT(*) FROM precio j, (SELECT MAX(fecha) as fecha, juego_nombre, juego_consola FROM precio GROUP BY juego_nombre, juego_consola) t1 WHERE j.fecha = t1.fecha AND j.juego_nombre = t1.juego_nombre AND j.juego_consola = t1.juego_consola AND j.juego_consola = :consola",
        nativeQuery = true)
    Page<Precio> juegosUltimoPrecio(@Param("consola") String consola, Pageable pageable);



    /**
     * Devuelve todos los juegos que han bajado de precio ordenados por nombre descendente. 
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio",
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio",
        nativeQuery = true)
    Page<Precio> juegosBajanPrecioOrdenNombreDesc(Pageable pageable);


    /**
    * Devuelve todos los juegos que han bajado de precio ordenados por nombre ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio ORDER BY p3.juego_nombre DESC",
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio",
        nativeQuery = true)
    Page<Precio> juegosBajanPrecioOrdenNombreAsc(Pageable pageable);


    /**
     * Devuelve todos los juegos que han bajado de precio ordenados por precio ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio ORDER BY p3.precio",
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio",
        nativeQuery = true)
    Page<Precio> juegosBajanPrecioOrdenPrecioAsc(Pageable pageable);


    /**
     * Devuelve todos los juegos que han bajado de precio ordenados por precio descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio ORDER BY p3.precio DESC",
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio",
        nativeQuery = true)
    Page<Precio> juegosBajanPrecioOrdenPrecioDesc(Pageable pageable);


    /**
     * Devuelve todos los juegos que han subido de precio ordenados por nombre descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio", 
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio",
        nativeQuery = true)
    Page<Precio> juegosSubenPrecioOrdenNombreDesc(Pageable pageable);
    

    /**
     * Devuelve todos los juegos que han subido de precio ordenados por nombre ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
   @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio ORDER BY p3.juego_nombre DESC", 
       countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio",
       nativeQuery = true)
   Page<Precio> juegosSubenPrecioOrdenNombreAsc(Pageable pageable);


    /**
     * Devuelve todos los juegos que han subido de precio ordenados por precio ascendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio ORDER BY p3.precio", 
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio",
        nativeQuery = true)
    Page<Precio> juegosSubenPrecioOrdenPrecioAsc(Pageable pageable);


    /**
     * Devuelve todos los juegos que han subido de precio ordenados por precio descendente.
     * @param pageable
     * @return Lista de precios paginada.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio ORDER BY p3.precio DESC", 
        countQuery = "SELECT COUNT(*) FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio",
        nativeQuery = true)
    Page<Precio> juegosSubenPrecioOrdenPrecioDesc(Pageable pageable);


    /**
     * Devuelve 12 juegos que han bajado de precio elegidos de manera aleatoria.
     * @return Lista de precios.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio > p3.precio ORDER BY RAND() LIMIT 10",
        nativeQuery = true)
    List<Precio> juegosBajanPrecioRandom();



    /**
     * Devuelve 12 juegos que han subido de precio elegidos de manera aleatoria.
     * @return Lista de precios.
     */
    @Query(value= "SELECT p3.* FROM  (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio WHERE fecha < ( SELECT MAX(fecha) AS fecha FROM precio ORDER BY fecha DESC ))) p2, (SELECT * FROM precio WHERE  fecha = (SELECT MAX(fecha) AS fecha FROM precio  ORDER BY fecha DESC)) p3 WHERE p2.juego_consola = p3.juego_consola AND p2.juego_nombre = p3.juego_nombre AND p2.precio < p3.precio ORDER BY RAND() LIMIT 10",
        nativeQuery = true)
    List<Precio> juegosSubenPrecioRandom();
    
}
