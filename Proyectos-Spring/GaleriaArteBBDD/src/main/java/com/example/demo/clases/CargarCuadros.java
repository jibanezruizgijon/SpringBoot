package com.example.demo.clases;

import com.example.demo.repository.CuadroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CargarCuadros {

	@Bean
	CommandLineRunner iniciarBaseDeDatos(CuadroRepository cuadroRepository) {
		return args -> {

			if (cuadroRepository.count() == 0) {

				List<Cuadro> galeria = new ArrayList<>();

				galeria.add(new Cuadro("Impresión, sol naciente", "Claude Monet", EpocaPintura.Impresionismo,
						"/imagenesLogos/img1.jpg"));
				galeria.add(new Cuadro("Las Meninas", "Diego de Velázquez", EpocaPintura.Barroco,
						"imagenesLogos/img2.jpg"));
				galeria.add(new Cuadro("La Capilla Sixtina", "Miguel Ángel", EpocaPintura.Renacimiento,
						"imagenesLogos/img3.jpg"));
				galeria.add(new Cuadro("El Guernica", "Pablo Picasso", EpocaPintura.Cubismo, "imagenesLogos/img4.jpg"));
				galeria.add(new Cuadro("La noche estrellada", "Vincent van Gogh", EpocaPintura.Postimpresionismo,
						"imagenesLogos/img5.jpg"));
				galeria.add(new Cuadro("El nacimiento de Venus", "Botticelli", EpocaPintura.Renacimiento,
						"imagenesLogos/img6.jpg"));
				galeria.add(new Cuadro("El Jardín de las delicias", "El Bosco", EpocaPintura.Renacimiento,
						"imagenesLogos/img7.jpg"));
				galeria.add(new Cuadro("La joven de la perla", "Johannes Vermeer", EpocaPintura.Barroco,
						"imagenesLogos/img8.jpg"));
				galeria.add(new Cuadro("Composición en rojo, amarillo, azul, blanco y negro", "Piet Mondrian",
						EpocaPintura.Neoplasticismo, "imagenesLogos/img9.jpg"));
				galeria.add(new Cuadro("El grito", "De Munch", EpocaPintura.Expresionismo, "imagenesLogos/img10.png"));
				galeria.add(new Cuadro("El entierro del conde de Orgaz", "El Greco", EpocaPintura.Manierismo,
						"imagenesLogos/img11.jpg"));
				galeria.add(new Cuadro("El gran siglo", "René Magritte", EpocaPintura.Surrealismo,
						"imagenesLogos/img12.jpg"));
				galeria.add(new Cuadro("La libertad guiando al pueblo", "Eugène Delacroix", EpocaPintura.Romanticismo,
						"imagenesLogos/img13.jpg"));
				galeria.add(new Cuadro("La Gran ola de Kanagaza", "Katsushika Hokusai", EpocaPintura.Arte_oriental,
						"imagenesLogos/img14.jpg"));
				galeria.add(new Cuadro("La persistencia de la memoria", "Salvador Dalí", EpocaPintura.Surrealismo,
						"imagenesLogos/img15.jpg"));
				galeria.add(new Cuadro("La última cena", "Leonardo da Vinci", EpocaPintura.Renacimiento,
						"imagenesLogos/img16.jpg"));
				galeria.add(new Cuadro("Los girasoles", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
						"imagenesLogos/img17.jpg"));
				galeria.add(new Cuadro("Saturno devorando a su hijo", "Goya", EpocaPintura.Romanticismo,
						"imagenesLogos/img18.jpg"));
				galeria.add(new Cuadro("Autorretrato", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
						"imagenesLogos/img19.jpg"));
				galeria.add(new Cuadro("La lechera", "Vermeer", EpocaPintura.Barroco, "imagenesLogos/img20.jpg"));
				galeria.add(
						new Cuadro("La ciudad", "de Fernand Léger", EpocaPintura.Cubismo, "imagenesLogos/img22.jpg"));
				galeria.add(new Cuadro("El columpio", "Fragonard", EpocaPintura.Rococo, "imagenesLogos/img23.jpg"));
				galeria.add(new Cuadro("Paseo a orillas del mar", "de Joaquín Sorolla", EpocaPintura.Impresionismo,
						"imagenesLogos/img24.jpg"));

				cuadroRepository.saveAll(galeria);

			}
		};
	}
}