package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaHidraulica extends PlantaRenovable {
	
	private static final int MAX_TURBINA = 34;
	private int cantidadTurbinas;
	private double capacidadEmbalse;
	
	public PlantaHidraulica(int id, String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantidadTurbinas, double capacidadEmbalse)  {
		super(nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if(cantidadTurbinas <= 0 || cantidadTurbinas > MAX_TURBINA) {
			throw new RuntimeException("La cantidad de turbinas tiene que estar entre 1 y "+ MAX_TURBINA);
		}
		if(capacidadEmbalse <= 0 ) {
			throw new RuntimeException("La capacidad de embalse tiene que ser mayor a cero");
			
		}
		
		this.cantidadTurbinas = cantidadTurbinas;
		this.capacidadEmbalse = capacidadEmbalse;
		
		
	}
//------------Getters-----------------
	
	

	public static int getMaxTurbina() {
		return MAX_TURBINA;
	}

	public int getCantidadTurbinas() {
		return cantidadTurbinas;
	}

	public double getCapacidadEmbalse() {
		return capacidadEmbalse;
	}
	
	
	
}
