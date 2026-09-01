package ar.edu.utn.frc.backend;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 1 (ejercicio 2 del enunciado) - Recorrer una List<Animal> con
 * for-each y obtener el consumo diario de cada uno.
 *
 * <p>El mismo bucle produce tres resultados distintos segun la especie sin
 * preguntar nunca por el tipo concreto: no hay instanceof ni casteos, solo la
 * llamada polimorfica a calcularConsumoDiario().
 */
class Ejercicio1ConsumoDiarioPolimorficoTest {

	private static List<Double> consumosDe(final List<Animal> animales) {

		final List<Double> consumos = new ArrayList<>();

		for (final Animal animal : animales) {
			consumos.add(animal.calcularConsumoDiario());
		}

		return consumos;
	}

	@Test
	void cadaAnimalResuelveSuConsumoSinPreguntarPorElTipo() {

		assertEquals(
			List.of(450.0, 630.0, 390.0, 200.0, 280.0, 150.0, 66.0),
			DatosDePrueba.dosDecimales(consumosDe(DatosDePrueba.animales())));
	}

	@Test
	void elBovinoConsumeUnaVezYMediaSuPeso() {
		assertEquals(450.0, DatosDePrueba.bovinoHereford().calcularConsumoDiario(), 0.01);
	}

	@Test
	void elPorcinoConsumeElDobleDeSuPeso() {
		assertEquals(200.0, DatosDePrueba.porcinoDuroc().calcularConsumoDiario(), 0.01);
	}

	@Test
	void elOvinoAplicaSuPropioFactorDeConsumo() {
		assertEquals(66.0, DatosDePrueba.ovinoMerino().calcularConsumoDiario(), 0.01);
	}
}
