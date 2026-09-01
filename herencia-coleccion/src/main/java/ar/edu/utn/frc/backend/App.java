package ar.edu.utn.frc.backend;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

/**
 * Demo minima del modelo. Los ejercicios del apunte estan resueltos como unit
 * tests en src/test/java, no aca.
 */
public class App {

	private static final AsignadorDieta ASIGNADOR = new AsignadorDietaService();

	public static void main(final String[] args) {

		final Bovino primerCaso = new Bovino(300f, LocalDate.of(2023, 5, 10), Sexo.HEMBRA, RazaBovino.HEREFORD);

		ASIGNADOR.asignarDieta(primerCaso, Dietas.BALANCEADO_BOVINO);

		mostrarResumen(primerCaso);

		final Bovino segundoCaso = new Bovino(200f, LocalDate.of(2024, 1, 15), Sexo.MACHO, RazaBovino.ANGUS);

		ASIGNADOR.asignarDieta(segundoCaso, Dietas.MIXTO_SORGO);

		mostrarResumen(segundoCaso);

		final Porcino sinDieta = new Porcino(80f, LocalDate.of(2025, 3, 1), Sexo.MACHO, RazaPorcino.KUNEKUNE);

		mostrarResumen(sinDieta);
	}

	private static void mostrarResumen(final Animal animal) {

		System.out.println();
		System.out.println("=======================================");
		System.out.println("Animal: " + animal.getEspecie().getNombre() + " " + animal.getRaza());
		System.out.printf(Locale.US, "Peso: %.2f kg%n", animal.getPeso());

		if (!animal.tieneDieta()) {
			System.out.println("Sin dieta asignada.");
			return;
		}

		System.out.println("Dieta: " + animal.getDieta().getNombre());
		System.out.println();
		System.out.println("PLAN DIETARIO DIARIO");
		System.out.println();

		final Map<Alimento, Double> plan = animal.generarPlanDietario();

		for (final Map.Entry<Alimento, Double> entrada : plan.entrySet()) {

			final double kilogramos = entrada.getValue();

			System.out.printf(
				Locale.US,
				"%-10s %10.2f kg %10.2f Mcal%n",
				entrada.getKey(),
				kilogramos,
				kilogramos * entrada.getKey().getEnergiaMcalKg());
		}

		System.out.println();
		System.out.printf(
			Locale.US,
			"%-10s %10.2f kg %10.2f Mcal%n",
			"TOTAL",
			animal.calcularConsumoDiario(),
			animal.calcularEnergiaTotalDiaria());
		System.out.println();
		System.out.printf(
			Locale.US,
			"Densidad energetica: %.2f Mcal/kg%n",
			animal.getDieta().calcularDensidadEnergetica());
	}
}
