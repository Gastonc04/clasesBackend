package ar.edu.utn.frc.backend;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Ejercicio 3 (ejercicio 4 del enunciado) - Determinar el animal de mayor peso
 * recorriendo la coleccion y quedandose con el maximo parcial.
 */
class Ejercicio3AnimalMasPesadoTest {

	private static Animal buscarMasPesado(final List<Animal> animales) {

		Animal masPesado = null;

		for (final Animal animal : animales) {
			if (masPesado == null || animal.getPeso() > masPesado.getPeso()) {
				masPesado = animal;
			}
		}

		return masPesado;
	}

	@Test
	void elAnimalMasPesadoDelEstablecimientoPesaCuatrocientosVeinteKilos() {
		assertEquals(420.0, buscarMasPesado(DatosDePrueba.animales()).getPeso(), 0.01);
	}

	@Test
	void conUnSoloAnimalDevuelveEseAnimal() {
		assertEquals(55.0, buscarMasPesado(List.of(DatosDePrueba.ovinoMerino())).getPeso(), 0.01);
	}

	@Test
	void conUnaListaVaciaNoHayAnimalMasPesado() {
		assertNull(buscarMasPesado(List.of()));
	}
}
