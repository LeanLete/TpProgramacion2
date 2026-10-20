package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaNoRenovable extends Planta {
	
	private	String  tipoCombustible;
	private double capacidadAlmacenamiento;
	

	public PlantaNoRenovable( String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento, String tipoCombustible, double capacidadAlmacenamiento) {
		super( nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento);
		
		if(tipoCombustible == null || tipoCombustible.isEmpty()) {
			throw new RuntimeException("El tipo de combustible es obligatorio");
			
		}
		
		if(capacidadAlmacenamiento <= 0) {
			throw new RuntimeException("La capacidad de almacenamiento no puede ser cero ni un numero negativo");
			
		}
		this.tipoCombustible = tipoCombustible;
		this.capacidadAlmacenamiento = capacidadAlmacenamiento;
	}


	public boolean esRenovable(){
		return false;
	}
		

	////---------Getters-----
	public String getTipoCombustible() {
		return tipoCombustible;
	}


	public double getCapacidadAlmacenamiento() {
		return capacidadAlmacenamiento;
	}
	
	
	
}
