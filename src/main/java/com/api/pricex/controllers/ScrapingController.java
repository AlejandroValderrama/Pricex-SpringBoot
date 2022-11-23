package com.api.pricex.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.api.pricex.interfaces.IJuegoService;
import com.api.pricex.models.*;
import com.api.utils.ScrapingUtils;
import com.microsoft.playwright.*;

/**
 * @autor Alejandro Valderrama
 */
@Controller
@RequestMapping
public class ScrapingController {

    @Autowired
    private IJuegoService service;

    
	/** 
	 * Actualiza los precios de todos los juegos de una determinada consola. Además guarda los juegos nuevos.
	 * @param consola Consola a la que pertenece los juegos.
	 * @return redirige a la raiz.
	 */
	@GetMapping("/updatePrecios/{consola}")
    public String updatePrecios(@PathVariable String consola) {

        try (Playwright playwright = Playwright.create()) {
			//Abre una ventana de incognito.
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50).setTimeout(3000));
			BrowserContext browserContext = browser.newContext();
			Page page = browserContext.newPage();

			//Abre una segunda ventana de incognito.
			Browser browser2 = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500).setTimeout(3000));
			BrowserContext browserContext2 = browser2.newContext();
			Page page2 = browserContext2.newPage();

			ScrapingUtils scrap = new ScrapingUtils(page);

			List<String> urls = scrap.urlsCex(consola);

			if(!urls.isEmpty()) {

				for (String url : urls) {
					page.navigate(url);
					scrap.desplegarLista(page);
					List<String> titulos = scrap.getTitulos(page);
					List<Double> precios = scrap.getPrecios(page);
	
					save(titulos, precios, consola, page, page2);
				}	
			}

			scrap.cerrar(browser, browserContext);
            scrap.cerrar(browser2, browserContext2);
			
		} catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


	
	/** 
	 * Actualiza las imagenes los juegos de una determinada consola si han sido modificadas en la web original.
	 * @param consola Consola a la que pertenece los juegos.
	 * @return redirige a la raiz.
	 */
	@GetMapping("/updateImagenes/{consola}")
	public String updateImagenes(@PathVariable String consola) {

		try (Playwright playwright = Playwright.create()) {
			//Abre una ventana de incognito.
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100).setTimeout(3000));
			BrowserContext browserContext = browser.newContext();
			Page page = browserContext.newPage();

			ScrapingUtils scrap = new ScrapingUtils(page);

			List<String> urls = scrap.urlsCex(consola);
			List<Juego> juegosBD = service.getAllJuego(consola);
			
			if(!urls.isEmpty()){

				for (String url : urls) {
					page.navigate(url);
					scrap.desplegarLista(page);
					List<String> titulos = scrap.getTitulos(page);
					System.out.println("Loading imagenes Small...");
					List<String> imagesS = scrap.getImagesS(page);
					System.out.println("Loading urls...");
					List<String> urlFicha = scrap.getUrls(page);
					String imgL;
					
	
					for(int i=0; i<titulos.size(); i++) {
						if(!titulos.get(i).equalsIgnoreCase("Shaun White Skateboarding")) {
							JuegoId juegoId = new JuegoId(titulos.get(i), consola);
							Juego juego = new Juego(juegoId);

							if(juegosBD.contains(juego)) { //Comprobar si el juego existe en la BD.
								juego = service.getJuego(juegoId).get();
								
								page.navigate(imagesS.get(i)); 
								String imagenUrl = page.url(); //Abro la imagen pequeña del juego en el navegador y obtengo su url.
			
								//Compruebo que la imagen no sea la Default de Cex y que sea distitan a la que ya tengo en la BD.
								if(!imagenUrl.equalsIgnoreCase("https://es.static.webuy.com/images/default/defimg1_s.jpg") && !imagesS.get(i).equalsIgnoreCase(juego.getImagenS())) {
									//Abro una segunda página de incognito.
									Browser browser2 = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000).setTimeout(3000));
									BrowserContext browserContext2 = browser2.newContext();
									Page page2 = browserContext2.newPage();
									
									juego.setImagenS(imagesS.get(i)); //Actualizo la imagen pequeña.
									imgL = scrap.getImagenL(page2, urlFicha.get(i)); //Abro en la segunda página la ficha del juego para obtener y actualizar la imagen grande.
									juego.setImagenM(imgL);
									service.saveJuego(juego); //Actualizo el juego en la BD.
									System.out.println("Juego Actualizado: " + juego.getJuegoId());
									scrap.cerrar(browser2, browserContext2);
								}
							}
						}
						
					}
				}
			}

			scrap.cerrar(browser, browserContext);

		} catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
    
    
	/** 
	 * Guarda los precios de todos los juegos de una determinada consola y los juegos nuevos si los hubiese.
	 * @param titulos Lista de los titulos de los juegos mostrados por el navegador.
	 * @param precios Lista de los precios de los juegos mostrados por el navegador.
	 * @param consola Consola a la que pertenece los juegos.
	 * @param page Página para navegar y obtener el listado de los juegos.
	 * @param page2 Página para navegar.
	 * @return redirige a la raiz.
	 */
	@PostMapping
    public String save(List<String> titulos, List<Double> precios, String consola, Page page, Page page2) {
		ScrapingUtils scrap = new ScrapingUtils(page);
        Date dt = new Date();
        List<String> urls = new ArrayList<>();
		List<String> imagesS = new ArrayList<>();
        List<Juego> juegosBD = service.getAllJuego(consola); //Obtengo todos los juegos de la BD.
        String imgL;

        for(int i=0; i<titulos.size(); i++) {

            JuegoId jid = new JuegoId(titulos.get(i), consola);
            Juego j = new Juego(jid);

            if(!juegosBD.contains(j)) { //Compruebo que el juego no exista en mi BD.

				if(imagesS.isEmpty()) 
					imagesS = scrap.getImagesS(page);

				page2.navigate(imagesS.get(i)); //Abro la imagen pequeña en la segunda página y guardo su url.
				String urlImgS = page2.url();

				if(urlImgS.equalsIgnoreCase("https://es.static.webuy.com/images/default/defimg1_s.jpg")) { //Compruebo si la imagen pequeña es la Default de Cex.
					j = scrap.imagenesDefault(j, consola);
				} else {

					if(urls.isEmpty())
						urls = scrap.getUrls(page);

					imgL = scrap.getImagenL(page2, urls.get(i));
					j.setImagenS(imagesS.get(i));
					j.setImagenM(imgL);
				}

                service.saveJuego(j);
				System.out.println("Juego guardado: "+ j.getJuegoId());
            }

            PrecioId pid = new PrecioId(dt, j);
            Precio p = new Precio(pid, precios.get(i));
            service.savePrecio(p);
        }

        return "redirect:/";
    }
    
}
