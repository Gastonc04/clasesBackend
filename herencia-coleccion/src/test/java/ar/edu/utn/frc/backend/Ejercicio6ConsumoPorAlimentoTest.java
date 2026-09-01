package ar.edu.utn.frc.backend;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 6 (ejercicio 11 del enunciado) - Map<Alimento, Double> con los kg diarios necesarios de cada
 * alimento: se recorren los animales, se pide su plan dietario y se acumula.
 */
class Ejercicio6ConsumoPorAlimentoTest {

	private static Map<Alimento, Double> consumoPorAlimento(final List<Animal> animales) {

		final Map<Alimento, Double> consumo = new EnumMap<>(Alimento.class);

		for (final Animal animal : animales) {
			for (final Map.Entry<Alimento, Double> entrada : animal.generarPlanDietario().entrySet()) {
				consumo.merge(entrada.getKey(), entrada.getValue(), Double::sum);
			}
		}

		return consumo;
	}

	@Test
	void acumulaLosKilogramosDiariosDeCadaAlimento() {

		assertEquals(
			Map.of(
				Alimento.MAIZ, 919.50,
				Alimento.SORGO, 126.00,
				Alimento.TRIGO, 135.90,
				Alimento.SOJA, 567.00,
				Alimento.FORRAJE, 417.60),
			DatosDePrueba.dosDecimales(consumoPorAlimento(DatosDePrueba.animales())));
	}

	@Test
	void elTotalRepartidoCoincideConElConsumoDelEstablecimiento() {

		double total = 0;

		for (final Double kilogramos : consumoPorAlimento(DatosDePrueba.animales()).values()) {
			total += kilogramos;
		}

		assertEquals(2166.0, total, 0.01);
	}

	@Test
	void unAnimalSinDietaNoAcumulaNada() {
		assertEquals(0, consumoPorAlimento(List.of(DatosDePrueba.bovinoHereford())).size());
	}
}
