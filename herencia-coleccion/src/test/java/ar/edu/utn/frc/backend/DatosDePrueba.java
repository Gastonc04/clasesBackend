package ar.edu.utn.frc.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class DatosDePrueba {

	private static final AsignadorDieta ASIGNADOR = new AsignadorDietaService();

	private DatosDePrueba() {
	}

	static List<Animal> animales() {

		final List<Animal> animales = new ArrayList<>();

		animales.add(conDieta(bovinoHereford(), Dietas.BALANCEADO_BOVINO));
		animales.add(conDieta(angusPesado(), Dietas.MIXTO_SORGO));
		animales.add(conDieta(angusLiviano(), Dietas.BALANCEADO_BOVINO));
		animales.add(conDieta(porcinoDuroc(), Dietas.ENGORDE_PORCINO));
		animales.add(conDieta(porcinoHampshire(), Dietas.ENGORDE_PORCINO));
		animales.add(conDieta(porcinoDurocLiviano(), Dietas.ENGORDE_PORCINO));
		animales.add(conDieta(ovinoMerino(), Dietas.PASTORIL_OVINO));

		return animales;
	}

	static Bovino bovinoHereford() {
		return new Bovino(300f, LocalDate.of(2023, 5, 10), Sexo.HEMBRA, RazaBovino.HEREFORD);
	}

	private static Bovino angusPesado() {
		return new Bovino(420f, LocalDate.of(2022, 8, 2), Sexo.MACHO, RazaBovino.ANGUS);
	}

	private static Bovino angusLiviano() {
		return new Bovino(260f, LocalDate.of(2024, 1, 20), Sexo.HEMBRA, RazaBovino.ANGUS);
	}

	static Porcino porcinoDuroc() {
		return new Porcino(100f, LocalDate.of(2024, 6, 5), Sexo.MACHO, RazaPorcino.DUROC);
	}

	private static Porcino porcinoHampshire() {
		return new Porcino(140f, LocalDate.of(2024, 2, 18), Sexo.HEMBRA, RazaPorcino.HAMPSHIRE);
	}

	private static Porcino porcinoDurocLiviano() {
		return new Porcino(75f, LocalDate.of(2025, 1, 9), Sexo.MACHO, RazaPorcino.DUROC);
	}

	static Ovino ovinoMerino() {
		return new Ovino(55f, LocalDate.of(2024, 9, 12), Sexo.HEMBRA, RazaOvino.MERINO);
	}

	static Bovino casoTestigo() {
		return conDieta(bovinoHereford(), Dietas.BALANCEADO_BOVINO);
	}

	private static <A extends Animal> A conDieta(final A animal, final Dieta dieta) {
		ASIGNADOR.asignarDieta(animal, dieta);
		return animal;
	}

	static double dosDecimales(final double valor) {
		return Math.round(valor * 100.0) / 100.0;
	}

	static List<Double> dosDecimales(final List<Double> valores) {

		final List<Double> redondeados = new ArrayList<>();

		for (final Double valor : valores) {
			redondeados.add(dosDecimales(valor));
		}

		return redondeados;
	}

	static Map<Alimento, Double> dosDecimales(final Map<Alimento, Double> valores) {

		final Map<Alimento, Double> redondeados = new EnumMap<>(Alimento.class);

		for (final Map.Entry<Alimento, Double> entrada : valores.entrySet()) {
			redondeados.put(entrada.getKey(), dosDecimales(entrada.getValue()));
		}

		return redondeados;
	}
}
