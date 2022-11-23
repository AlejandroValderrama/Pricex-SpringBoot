package com.api.pricex.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.pricex.models.*;

/**
 * @autor Alejandro Valderrama
 */
public interface IJuegoService {

    public List<Juego> getAllJuego(String consola);
    public Optional<Juego> getJuego(JuegoId juegoid);
    public int saveJuego(Juego j);
    public int savePrecio(Precio p);
    public List<Precio> getAllPrecio(Juego juego);
    public Page<Precio> getAllJuegoLastPrecio(String consola, Pageable pageable);
    public List<Juego> findJuegos(String nombre);


    public Page<Precio> getJuegosBajanPrecioPorNombreDesc(Pageable pageable);
    public Page<Precio> getJuegosBajanPrecioPorNombreAsc(Pageable pageable);
    public Page<Precio> getJuegosBajanPrecioPorPrecioDesc(Pageable pageable);
    public Page<Precio> getJuegosBajanPrecioPorPrecioAsc(Pageable pageable);

    public Page<Precio> getJuegosSubenPrecioPorNombreDesc(Pageable pageable);
    public Page<Precio> getJuegosSubenPrecioPorNombreAsc(Pageable pageable);
    public Page<Precio> getJuegosSubenPrecioPorPrecioDesc(Pageable pageable);
    public Page<Precio> getJuegosSubenPrecioPorPrecioAsc(Pageable pageable);

    public List<Precio> getJuegosBajanPrecioRandom();
    public List<Precio> getJuegosSubenPrecioRandom();
}
