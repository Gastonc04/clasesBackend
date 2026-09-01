package ar.edu.utn.frc.backend;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 4 (ejercicio 6 del enunciado) - Eliminar los animales por debajo de
 * un peso usando Iterator.remove().
 *
 * <p>No se usa remove() dentro de un for-each: eso lanzaria
 * ConcurrentModificationException.
 */
class Ejercicio4EliminarConIteratorTest {

	private static int eliminarLivianos(final List<Animal> animales, final double pesoMinimo) {

		int eliminados = 0;

		final Iterator<Animal> iterador = animales.iterator();

		while (iterador.hasNext()) {

			final Animal animal = iterador.next();

			if (animal.getPeso() < pesoMinimo) {
				iterador.remove();
				eliminados++;
			}
		}

		return eliminados;
	}

	private static double pesoMinimoDe(final List<Animal> animales) {

		double minimo = Double.MAX_VALUE;

		for (final Animal animal : animales) {
			minimo = Math.min(minimo, animal.getPeso());
		}

		return minimo;
	}

	@Test
	void seEliminanLosDosAnimalesDeMenosDeCienKilos() {
		assertEquals(2, eliminarLivianos(DatosDePrueba.animales(), 100));
	}

	@Test
	void quedanCincoAnimalesEnLaLista() {

		final List<Animal> animales = DatosDePrueba.animales();

		eliminarLivianos(animales, 100);

		assertEquals(5, animales.size());
	}

	@Test
	void elAnimalMasLivianoQueSobreviveAlcanzaElPesoMinimo() {

		final List<Animal> animales = DatosDePrueba.animales();

		eliminarLivianos(animales, 100);

		assertEquals(100.0, pesoMinimoDe(animales), 0.01);
	}

	@Test
	void siNingunoEsLivianoNoSeEliminaNada() {
		assertEquals(0, eliminarLivianos(DatosDePrueba.animales(), 10));
	}

	@Test
	void conUnMinimoMuyAltoSeEliminanTodos() {

		final List<Animal> animales = DatosDePrueba.animales();

		eliminarLivianos(animales, 1000);

		assertEquals(0, animales.size());
	}
}
