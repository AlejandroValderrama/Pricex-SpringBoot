package com.api.pricex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.api.pricex.interfaces.IJuegoService;
import com.api.pricex.models.*;
import com.google.gson.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @autor Alejandro Valderrama
 */
@Controller
@RequestMapping
public class Controlador {
    
    @Autowired
    private IJuegoService service;

    
	/** 
	 * 
	 * @param model
	 * @return String
	 */
	@GetMapping("/")
    public String index(Model model) {
		List<Precio> bajan = service.getJuegosBajanPrecioRandom();
		List<Precio> suben = service.getJuegosSubenPrecioRandom();
		model.addAttribute("bajan", bajan);
		model.addAttribute("suben", suben);
        return "index";
    }

	
	/** 
	 * @return String
	 */
	@GetMapping(value= {"/listar/bajan", "/listar/bajan/{peticion}"})
    public String listaBajan(@RequestParam Map<String, Object> params, Model model, @PathVariable(required = false) String peticion) {
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) -1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 50);
        Page<Precio> pageJuego = null;
		String opcion = "";


		if(peticion == null || peticion.equalsIgnoreCase("OrderBy=Name_Desc")){
        	pageJuego = service.getJuegosBajanPrecioPorNombreDesc(pageRequest);
			opcion = "1";
		}else if(peticion.equalsIgnoreCase("OrderBy=Name_Asc")) {
			pageJuego = service.getJuegosBajanPrecioPorNombreAsc(pageRequest);
			opcion = "2";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Desc")) {
			pageJuego = service.getJuegosBajanPrecioPorPrecioDesc(pageRequest);
			opcion = "3";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Asc")) {
			pageJuego = service.getJuegosBajanPrecioPorPrecioAsc(pageRequest);
			opcion = "4";
		}

		int totalPage = pageJuego.getTotalPages();

        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

		String texto = "Bajan";
		int cantidad = (int) pageJuego.getTotalElements();

        model.addAttribute("precios", pageJuego.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
		model.addAttribute("opcion", opcion);
		model.addAttribute("consola", texto);
		model.addAttribute("cantidad", cantidad);

        return "listar";
    }


	/** 
	 * @return String
	 */
	@GetMapping(value= {"/listar/suben", "/listar/suben/{peticion}"})
    public String listaSuben(@RequestParam Map<String, Object> params, Model model, @PathVariable(required = false) String peticion) {

        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) -1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 50);
        Page<Precio> pageJuego = null;
		String opcion = "";


		if(peticion == null || peticion.equalsIgnoreCase("OrderBy=Name_Desc")){
        	pageJuego = service.getJuegosSubenPrecioPorNombreDesc(pageRequest);
			opcion = "1";
		}else if(peticion.equalsIgnoreCase("OrderBy=Name_Asc")) {
			pageJuego = service.getJuegosSubenPrecioPorNombreAsc(pageRequest);
			opcion = "2";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Desc")) {
			pageJuego = service.getJuegosSubenPrecioPorPrecioDesc(pageRequest);
			opcion = "3";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Asc")) {
			pageJuego = service.getJuegosSubenPrecioPorPrecioAsc(pageRequest);
			opcion = "4";
		}

		int totalPage = pageJuego.getTotalPages();

        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

		String texto = "Suben";
		int cantidad = (int) pageJuego.getTotalElements();

        model.addAttribute("precios", pageJuego.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
		model.addAttribute("opcion", opcion);
		model.addAttribute("consola", texto);
		model.addAttribute("cantidad", cantidad);
        return "listar";
    }

	
	/** 
	 * @param consola
	 * @param peticion
	 * @param params
	 * @param model
	 * @return String
	 */
	@GetMapping(value = {"/listar/{consola}", "/listar/{consola}/{peticion}"})
    public String findAllOrderByNameAsc(@PathVariable String consola, @PathVariable(required = false) String peticion, @RequestParam Map<String, Object> params, Model model) {
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) -1) : 0;
		
		Sort sort = null;
		Pageable pageable = null;

		Page<Precio> pageJuego = null;
		String opcion = "";


		if(peticion == null || peticion.equalsIgnoreCase("OrderBy=Name_Desc")){
			sort = Sort.by(Sort.Direction.ASC, "juego_nombre");
			pageable = PageRequest.of(page, 60, sort);
			pageJuego = service.getAllJuegoLastPrecio(consola, pageable);
			opcion = "1";
		}else if(peticion.equalsIgnoreCase("OrderBy=Name_Asc")) {
			sort = Sort.by(Sort.Direction.DESC, "juego_nombre");
			pageable = PageRequest.of(page, 60, sort);
			pageJuego = service.getAllJuegoLastPrecio(consola, pageable);
			opcion = "2";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Desc")) {
			sort = Sort.by(Sort.Direction.DESC, "precio");
			pageable = PageRequest.of(page, 60, sort);
			pageJuego = service.getAllJuegoLastPrecio(consola, pageable);
			opcion = "3";
		}else if(peticion.equalsIgnoreCase("OrderBy=Price_Asc")) {
			sort = Sort.by(Sort.Direction.ASC, "precio");
			pageable = PageRequest.of(page, 60, sort);
			pageJuego = service.getAllJuegoLastPrecio(consola, pageable);
			opcion = "4";
		}
        
        int totalPage = pageJuego.getTotalPages();

        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

		

		int cantidad = (int) pageJuego.getTotalElements();

        model.addAttribute("precios", pageJuego.getContent());
        model.addAttribute("consola", consola);
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
		model.addAttribute("opcion", opcion);
		model.addAttribute("consola", consola);
		model.addAttribute("cantidad", cantidad);
        return "listar";
    }

	
	/** 
	 * @param model
	 * @param consola
	 * @param nombre
	 * @return String
	 */
	@GetMapping("/ficha/{consola}/{nombre}")
	public String ficha(Model model, @PathVariable String consola, @PathVariable String nombre) {
		JuegoId id = new JuegoId(nombre, consola);
		Juego juego = service.getJuego(id).get();
		model.addAttribute("juego", juego);
		return "ficha";
	}

	
	/** 
	 * @param model
	 * @param consola
	 * @param nombre
	 * @return String
	 */
	@RequestMapping("/ficha/{consola}/{nombre}/getData")
	@ResponseBody
	public String getData(Model model, @PathVariable String consola, @PathVariable String nombre) {
		JuegoId id = new JuegoId(nombre, consola);
		Juego juego = service.getJuego(id).get();
		List<Precio> precios = service.getAllPrecio(juego);
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArrayPrecio = new JsonArray();
		JsonArray jsonArrayFecha = new JsonArray();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YY");

		precios.forEach(data -> {
			jsonArrayPrecio.add(data.getPrecio());
			jsonArrayFecha.add(formato.format(data.getPrecioId().getFecha()).toString());
		});

		jsonObject.add("precio", jsonArrayPrecio);
		jsonObject.add("fecha", jsonArrayFecha);

		return jsonObject.toString();
	}

	
	/** 
	 * @param nombre
	 * @return String
	 */
	@RequestMapping("/getSearch/{nombre}")
	@ResponseBody
	public String getSearch(@PathVariable String nombre) {
		List<Juego> juegos = service.findJuegos(nombre);

		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArrayJuego = new JsonArray();
		JsonArray jsonArrayConsola = new JsonArray();

		juegos.forEach(data -> {
			jsonArrayJuego.add(data.getJuegoId().getNombre());
			jsonArrayConsola.add(data.getJuegoId().getConsola());
		});

		jsonObject.add("nombre", jsonArrayJuego);
		jsonObject.add("consola", jsonArrayConsola);

		return jsonObject.toString();
	}
}
