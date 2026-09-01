package ar.edu.utn.frc.backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 8 (ejercicios 13 y 14 del enunciado) - Ordenar los animales con
 * Comparator, por peso (ascendente y descendente) y por energia diaria.
 *
 * <p>Siempre se ordena una copia: la coleccion recibida conserva su orden.
 * El orden por energia depende de la dieta asignada a cada animal, asi que
 * combina Animal + Dieta y se resuelve por polimorfismo.
 */
class Ejercicio8OrdenarConComparatorTest {

	private static final Comparator<Animal> POR_PESO = Comparator.comparingDouble(Animal::getPeso);

	private static final Comparator<Animal> POR_ENERGIA =
		Comparator.comparingDouble(Animal::calcularEnergiaTotalDiaria);

	private static List<Animal> ordenar(final List<Animal> animales, final Comparator<Animal> criterio) {

		final List<Animal> copia = new ArrayList<>(animales);

		copia.sort(criterio);

		return copia;
	}

	private static List<Double> pesosDe(final List<Animal> animales) {

		final List<Double> pesos = new ArrayList<>();

		for (final Animal animal : animales) {
			pesos.add((double) animal.getPeso());
		}

		return DatosDePrueba.dosDecimales(pesos);
	}

	private static List<Double> energiasDe(final List<Animal> animales) {

		final List<Double> energias = new ArrayList<>();

		for (final Animal animal : animales) {
			energias.add(animal.calcularEnergiaTotalDiaria());
		}

		return DatosDePrueba.dosDecimales(energias);
	}

	@Test
	void ordenaDeMenorAMayorPeso() {

		assertEquals(
			List.of(55.0, 75.0, 100.0, 140.0, 260.0, 300.0, 420.0),
			pesosDe(ordenar(DatosDePrueba.animales(), POR_PESO)));
	}

	@Test
	void ordenaDeMayorAMenorPeso() {

		assertEquals(
			List.of(420.0, 300.0, 260.0, 140.0, 100.0, 75.0, 55.0),
			pesosDe(ordenar(DatosDePrueba.animales(), POR_PESO.reversed())));
	}

	@Test
	void laListaRecibidaConservaSuOrdenOriginal() {

		final List<Animal> animales = DatosDePrueba.animales();

		ordenar(animales, POR_PESO);

		assertEquals(
			List.of(300.0, 420.0, 260.0, 100.0, 140.0, 75.0, 55.0),
			pesosDe(animales));
	}

	@Test
	void ordenaPorEnergiaDejandoPrimeroAlQueMenosNecesita() {

		assertEquals(
			List.of(166.98, 486.0, 648.0, 907.2, 1115.4, 1287.0, 1902.6),
			energiasDe(ordenar(DatosDePrueba.animales(), POR_ENERGIA)));
	}
}
