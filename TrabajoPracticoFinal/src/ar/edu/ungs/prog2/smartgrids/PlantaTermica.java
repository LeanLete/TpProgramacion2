package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaTermica extends PlantaNoRenovable {
	
	private static final double MAX_EMISION_CO2 = 1000;// 1 tonelada de co2.
	private double objetivoEmisionCo2PorHora;

	public PlantaTermica( String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento, String tipoCombustible, double capacidadAlmacenamiento,
			double objetivoEmisionCo2PorHora) {
		super( nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento, tipoCombustible,
				capacidadAlmacenamiento);
		
		if (objetivoEmisionCo2PorHora <= 0 || objetivoEmisionCo2PorHora > MAX_EMISION_CO2) {
		    throw new RuntimeException("El objetivo de emisiones de CO2 por hora debe ser mayor a 0 y menor o igual a " + MAX_EMISION_CO2);
		}
		this.objetivoEmisionCo2PorHora = objetivoEmisionCo2PorHora;
	}

	
	
	
	
	
	
	
	//--------------Getters------------
	public double getObjetivoEmisionCo2PorHora() {
		return objetivoEmisionCo2PorHora;
	}

	public static double getMaxEmisionCo2() {
		return MAX_EMISION_CO2;
	}



	@Override
	public double calcularCuotaMinima() {
		
		return getCapacidadMaxgen()/2;
	}

	@Override
	public boolean cumplioCuotaMinima(RegistroMonitoreo registro) {
		RegistroPlantaTermica reg= (RegistroPlantaTermica)registro;
		
		return calcularCuotaMinima() >= reg.getEnergiaGenerada() &&  reg.getCantCo2GeneradoPorHora()< objetivoEmisionCo2PorHora ;
		 
	}
	
	
	
}
