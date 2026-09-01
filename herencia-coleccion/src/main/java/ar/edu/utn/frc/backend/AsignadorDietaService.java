package ar.edu.utn.frc.backend;

/**
 * Servicio que asigna una plantilla de dieta a un animal (el servicio recibe el
 * animal y la dieta a asignar).
 *
 * <p>ESQUELETO A COMPLETAR. La clase ya declara que implementa AsignadorDieta y
 * ya tiene la firma correcta del metodo; falta el cuerpo.
 */
public class AsignadorDietaService implements AsignadorDieta {

	@Override
	public void asignarDieta(final Animal animal, final Dieta dieta) {

		// TODO 1: si animal es null, lanzar IllegalArgumentException con un mensaje claro.
		// TODO 2: si dieta es null, lanzar IllegalArgumentException con un mensaje claro.
		// TODO 3: asignarle la dieta al animal usando su setter.
		//
		// Sin el paso 3 no pasa ningun test: DatosDePrueba usa este servicio para
		// darle una dieta a los siete animales del establecimiento.

		throw new UnsupportedOperationException("TODO: implementar asignarDieta");
	}
}
