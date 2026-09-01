package ar.edu.utn.frc.backend;

import java.util.Map;

public interface Alimentable {

	double calcularConsumoDiario();

	Map<Alimento, Double> generarPlanDietario();

	double calcularEnergiaTotalDiaria();
}
