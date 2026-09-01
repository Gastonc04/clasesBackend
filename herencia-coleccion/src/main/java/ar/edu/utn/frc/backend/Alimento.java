package ar.edu.utn.frc.backend;

public enum Alimento {
	MAIZ(3.40),
	SORGO(3.30),
	TRIGO(3.20),
	SOJA(3.00),
	FORRAJE(2.00);

	private final double energiaMcalKg;

	Alimento(final double aEnergiaMcalKg) {
		energiaMcalKg = aEnergiaMcalKg;
	}

	public double getEnergiaMcalKg() {
		return energiaMcalKg;
	}
}
