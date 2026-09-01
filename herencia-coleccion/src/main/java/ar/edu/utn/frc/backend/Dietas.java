package ar.edu.utn.frc.backend;

import java.util.List;

public final class Dietas {

	public static final Dieta BALANCEADO_BOVINO = new Dieta(
		"Balanceado bovino",
		"Dieta base para bovinos en crecimiento",
		List.of(
			new Racion(Alimento.MAIZ, 40),
			new Racion(Alimento.SOJA, 30),
			new Racion(Alimento.FORRAJE, 30)));

	public static final Dieta ENGORDE_PORCINO = new Dieta(
		"Engorde porcino",
		"Alta proporcion de grano para terminacion",
		List.of(
			new Racion(Alimento.MAIZ, 50),
			new Racion(Alimento.SOJA, 30),
			new Racion(Alimento.TRIGO, 20)));

	public static final Dieta MIXTO_SORGO = new Dieta(
		"Mixto con sorgo",
		"Incorpora sorgo para bajar el costo de la racion",
		List.of(
			new Racion(Alimento.MAIZ, 40),
			new Racion(Alimento.SORGO, 20),
			new Racion(Alimento.SOJA, 20),
			new Racion(Alimento.FORRAJE, 20)));

	public static final Dieta PASTORIL_OVINO = new Dieta(
		"Pastoril ovino",
		"Base forrajera con suplemento de grano",
		List.of(
			new Racion(Alimento.FORRAJE, 60),
			new Racion(Alimento.MAIZ, 25),
			new Racion(Alimento.TRIGO, 15)));

	public static List<Dieta> catalogo() {
		return List.of(BALANCEADO_BOVINO, ENGORDE_PORCINO, MIXTO_SORGO, PASTORIL_OVINO);
	}

	private Dietas() {
	}
}
