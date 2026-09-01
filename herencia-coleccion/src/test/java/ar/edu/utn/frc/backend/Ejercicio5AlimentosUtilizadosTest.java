package ar.edu.utn.frc.backend;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 5 (ejercicio 10 del enunciado) - Set<Alimento> con todos los alimentos usados en el establecimiento,
 * a partir de las dietas asignadas. Sin repetidos.
 */
class Ejercicio5AlimentosUtilizadosTest {

	private static Set<Alimento> alimentosUtilizados(final List<Animal> animales) {

		final Set<Alimento> alimentos = EnumSet.noneOf(Alimento.class);

		for (final Animal animal : animales) {

			if (!animal.tieneDieta()) {
				continue;
			}

			for (final Racion racion : animal.getDieta().getRaciones()) {
				alimentos.add(racion.getAlimento());
			}
		}

		return alimentos;
	}

	@Test
	void elEstablecimientoUsaLosCincoAlimentos() {

		assertEquals(
			EnumSet.of(Alimento.MAIZ, Alimento.SORGO, Alimento.TRIGO, Alimento.SOJA, Alimento.FORRAJE),
			alimentosUtilizados(DatosDePrueba.animales()));
	}

	@Test
	void elMaizNoSeRepiteAunqueLoUsenVariasDietas() {
		assertEquals(5, alimentosUtilizados(DatosDePrueba.animales()).size());
	}

	@Test
	void unAnimalSinDietaNoAportaAlimentos() {
		assertEquals(0, alimentosUtilizados(List.of(DatosDePrueba.bovinoHereford())).size());
	}
}
