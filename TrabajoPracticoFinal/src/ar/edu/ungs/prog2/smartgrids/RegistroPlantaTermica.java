package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroPlantaTermica extends RegistroMonitoreo{
	private double cantCo2GeneradoPorHora;
	private double cantCombustible;
	
	public RegistroPlantaTermica(LocalDateTime fechaHora, double energiaGeneradaPorHora, int cantIncidencias,
			double cantCo2GeneradoPorHora, double cantCombustible) {
		super(fechaHora, energiaGeneradaPorHora, cantIncidencias);
		
		if(cantCo2GeneradoPorHora<0) {
			throw new RuntimeException("La cantidad de Co2 generado debe ser mayor a cero");
		}
		if(cantCombustible<0) {
			throw new RuntimeException("La cantidad de combustible  debe ser mayor a cero");
		}
		
		this.cantCo2GeneradoPorHora = cantCo2GeneradoPorHora;
		this.cantCombustible = cantCombustible;
	}

	//------------Getters-----------------------------
	public double getCantCo2GeneradoPorHora() {
		return cantCo2GeneradoPorHora;
	}

	public double getCantCombustible() {
		return cantCombustible;
	}
	
	
	
	
}
