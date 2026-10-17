package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroPlantaEolica extends RegistroMonitoreo {
	

	private double velocidadViento;
	
	public RegistroPlantaEolica(LocalDateTime fechaHora, double energiaGeneradaPorHora, int cantIncidencias,
			double velocidadViento) {
		super(fechaHora, energiaGeneradaPorHora, cantIncidencias);
		
		if(velocidadViento<0) {
			throw new RuntimeException("La velocidad del viento debe ser mayor a cero");
		}
		this.velocidadViento = velocidadViento;
	}

	
	public double getVelocidadViento() {
		return velocidadViento;
	}

	
	
	
}
