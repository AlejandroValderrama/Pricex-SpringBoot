package com.api.utils;

import java.util.*;

import com.api.pricex.models.*;
import com.microsoft.playwright.*;

/**
 * @autor Alejandro Valderrama
 */
public class ScrapingUtils {

    public ScrapingUtils(Page page) {

    }

	
	/** 
	 * Obtiene el listado de todos los títulos de los juegos mostrados en la página.
	 * @param page Página de navegador.
	 * @return Lista de títulos.
	 */
	public List<String> getTitulos(Page page) {
		Locator lista = page.locator("div.desc h1 a span.ais-Highlight");
		List<String> titulos = lista.allTextContents();
		String titulo;

		for(int i=0; i<titulos.size(); i++){
			titulo = titulos.get(i).replace("//", "");
			titulo = titulo.replace("w/", "");
			titulo = titulo.replace("W/", "");
			titulo = titulo.replace("/", "|");
			titulos.set(i, titulo);
		}

		System.out.println("Get titulos");
		return titulos;
	}

	
	/** 
	 * Obtiene el listado de todos los precios de los juegos mostrados en la página.
	 * @param page Página de navegador.
	 * @return Lista de precios.
	 */
	public List<Double> getPrecios(Page page) {
		List<Double> numeros = new ArrayList<>();

		Locator lista = page.locator("div.priceTxt");
		List<String> precios = lista.allTextContents();
		numeros = pasarDouble(precios);

		System.out.println("Get precios");
		return numeros;
	}

	
	/** Depura, limpia y pasa el listado de precios en formato String a Double.
	 * @param precios Listado de precios de los juegos en formato String.
	 * @return List<Double> Listado de precios de los juegos.
	 */
	public List<Double> pasarDouble(List<String> precios) {
		List<Double> numeros = new ArrayList<>();
		String numero = "";
		double num;

		for (String precio : precios) {
			if(precio.contains("Vendemos")) {
				numero = precio.substring(9, precio.length()-1);
				num = Double.parseDouble(numero.replace(",", "."));
				numeros.add(num);
			}
		}
		return numeros;
	}

	
	/** 
	 * Obtiene el listado de todas las urls de las imagenes pequeñas de los juegos mostrados en la página.
	 * @param page Página de navegador.
	 * @return List<String> Listado urls de las imagenes pequeñas.
	 */
	public List<String> getImagesS(Page page) {
		List<String> imgs = new ArrayList<>();
		Locator lista = page.locator("div.thumb a img");

		for (int i=0; i<lista.count(); i++) 
			imgs.add(lista.nth(i).getAttribute("src"));

		System.out.println("Get Imagenes Small");

		return imgs;
	}

	
	/** 
	 * Obtiene el listado de todas las urls que redirigen a las fichas individuales de los juegos.
	 * @param page Página de navegador.
	 * @return List<String> Listado urls.
	 */
	public List<String> getUrls(Page page) {
		List<String> urls = new ArrayList<>();
		Locator lista = page.locator("div.thumb a");

		for (int i=0; i<lista.count(); i++) {
			urls.add(lista.nth(i).getAttribute("href"));
		}

		System.out.println("Get Urls");
		return urls;
	}

	
	/** 
	 * Obtiene la url de la imagen grande de la ficha individual del juego
	 * @param page Página de navegador.
	 * @param url Dirección para abrir en la página la ficha del juego.
	 * @return String Url de la imagen grande del juego.
	 */
	public String getImagenL(Page page, String url) {
		String root = "https://es.webuy.com/";
        String imgL;

		page.navigate(root + url);
		imgL = page.locator("div.productImg img").getAttribute("src");

		return imgL;
	}
    

    
	/** 
	 * Hace scroll hacia abajo ne la página del navegador para desplegar toda la lista completa de juegos.
	 * @param page Página de navegador.
	 */
	public void desplegarLista(Page page) {
		String fin = page.locator("div#showmore").getAttribute("style");

		for (int i=0; i<100; i++) {
			page.mouse().wheel(0, 15000);
			fin = page.querySelector("div#showmore").getAttribute("style");
			if(fin != null)
				break;
		}
	}

	
	/** 
	 * Cierra el navegador.
	 * @param browser
	 * @param browserContext
	 */
	public void cerrar(Browser browser, BrowserContext browserContext ) {
		browserContext.close();
		browser.close();
	}


	/** 
	 * Otorga imagenes por defecto personalizadas al juego si este no tiene las suyas propias.
	 * @param juego Juego al que se le añadiran las imagenes por defecto.
	 * @param consola Consola a la que pertenece el juego.
	 * @return Juego
	 */
	public Juego imagenesDefault(Juego juego, String consola) {

		switch(consola) {
			case "PS2":
					juego.setImagenS("https://live.staticflickr.com/65535/52412863631_d9dea93ddb_o.jpg");
					juego.setImagenM("https://live.staticflickr.com/65535/52412863661_773d8e3e31_o.jpg");
					break;
			case "PS3":
					juego.setImagenS("https://live.staticflickr.com/65535/52413379668_0493e8cb3c_o.jpg");
					juego.setImagenM("https://live.staticflickr.com/65535/52413150514_84e9a4358f_o.jpg");
					break;
			case "PS4": 
					juego.setImagenS("https://live.staticflickr.com/65535/52413306335_6120194d67_o.jpg");
					juego.setImagenM("https://live.staticflickr.com/65535/52413306345_75ea28dec1_o.jpg");
					break;
			case "PS5": 
					juego.setImagenS("https://live.staticflickr.com/65535/52412353982_43a02c8168_o.jpg");
					juego.setImagenM("https://live.staticflickr.com/65535/52413306315_58c141cf88_o.jpg");
					break;
			}

		return juego;
	}


	
	/** 
	 * Devuelve las urls donde se buscaran los juegos de una determinada consola y obtener sus datos.
	 * @param consola Consola indica para buscar juegos.
	 * @return List<String> Listado de urls.
	 */
	public List<String> urlsCex(String consola) {
		List<String> urls = new ArrayList<String>();

		switch(consola) {
			case "PS2":
				urls.add("https://es.webuy.com/search?categoryIds=824&categoryName=playstation2-juegos&price=%3A4&sortBy=prod_cex_es_price_asc"); // 0 - 4
				urls.add("https://es.webuy.com/search?categoryIds=824&categoryName=playstation2-juegos&price=4%3A7&sortBy=prod_cex_es_price_asc"); // 4 - 6
				urls.add("https://es.webuy.com/search?categoryIds=824&categoryName=playstation2-juegos&price=6%3A12&sortBy=prod_cex_es_price_asc"); // 6 -12
				urls.add("https://es.webuy.com/search?categoryIds=824&categoryName=playstation2-juegos&price=11%3A&sortBy=prod_cex_es_price_asc"); // 12 - max
				break;

			case "PS3":
				urls.add("https://es.webuy.com/search?categoryIds=821&categoryName=ps3-juegos&price=%3A9&sortBy=prod_cex_es_price_asc"); // 0 - 8
				urls.add("https://es.webuy.com/search?categoryIds=821&categoryName=ps3-juegos&price=8%3A20&sortBy=prod_cex_es_price_asc"); // 8 - 20
				urls.add("https://es.webuy.com/search?categoryIds=821&categoryName=ps3-juegos&price=19%3A&sortBy=prod_cex_es_price_asc"); // 20 - max

				break;
				
			case "PS4":
				urls.add("https://es.webuy.com/search/?categoryIds=1001&categoryName=ps4-juegos&price=%3A11&sortBy=prod_cex_es_price_asc"); // 0 - 10
				urls.add("https://es.webuy.com/search/?categoryIds=1001&categoryName=ps4-juegos&price=10%3A17&sortBy=prod_cex_es_price_asc"); // 10 - 16
				urls.add("https://es.webuy.com/search/?categoryIds=1001&categoryName=ps4-juegos&price=16%3A30&sortBy=prod_cex_es_price_asc"); // 16 - 30
				urls.add("https://es.webuy.com/search/?categoryIds=1001&categoryName=ps4-juegos&price=29%3A&sortBy=prod_cex_es_price_asc"); // 30 - max
				break;

			case "PS5":
				urls.add("https://es.webuy.com/search/?categoryIds=1079&categoryName=ps5-juegos&price=%3A11&sortBy=prod_cex_es_price_asc"); // 0 - 10
				urls.add("https://es.webuy.com/search/?categoryIds=1079&categoryName=ps5-juegos&price=10%3A17&sortBy=prod_cex_es_price_asc"); // 10 - 16
				urls.add("https://es.webuy.com/search/?categoryIds=1079&categoryName=ps5-juegos&price=16%3A30&sortBy=prod_cex_es_price_asc"); // 16 - 30
				urls.add("https://es.webuy.com/search/?categoryIds=1079&categoryName=ps5-juegos&price=29%3A&sortBy=prod_cex_es_price_asc"); // 30 - max
				break;
		}

		return urls;
	}


}
