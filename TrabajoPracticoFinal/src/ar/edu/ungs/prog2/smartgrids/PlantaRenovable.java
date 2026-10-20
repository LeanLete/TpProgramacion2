package ar.edu.ungs.prog2.smartgrids;


import java.time.LocalDateTime;

public abstract class PlantaRenovable extends Planta {
		private String fuentePrimaria;
		private double eficienciaPromedio;
	
		
		
	
	public PlantaRenovable( String nombre, String direccion, double capacidadMaxgen,
		LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio) {
		super( nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento);
		
		if(fuentePrimaria ==null) {
			throw new RuntimeException("La fuente primaria es obligatoria");
		}
		if(eficienciaPromedio<0 && eficienciaPromedio >1) {
			throw new RuntimeException("La eficiencia promedio  tiene que estar entre 0 y 1");
		}
		this.fuentePrimaria = fuentePrimaria;
		this.eficienciaPromedio = eficienciaPromedio;
}
	
	
	/////------Getters------
	public String getFuentePrimaria() {
		return fuentePrimaria;
	}


	public double getEficienciaPromedio() {
		return eficienciaPromedio;
	}
	
	
	
	
	@Override
	public double calcularCuotaMinima() {
	
		return getCapacidadMaxgen() * eficienciaPromedio;
	}
	


	@Override
	public boolean cumplioCuotaMinima(RegistroMonitoreo registro) {
		return registro.getEnergiaGenerada()>= calcularCuotaMinima();	}

		
	public boolean esRenovable(){
		return true;
	}
		
		
}

