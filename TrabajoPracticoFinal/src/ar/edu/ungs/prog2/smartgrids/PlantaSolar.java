package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaSolar extends PlantaRenovable {
	private static final int MAX_PANELES = 100000;
	private double superficieCubierta;
	private int cantPaneles;
	private double horasRadiacionDiaria;
	
	

	public PlantaSolar(int id, String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			double superficieCubierta, int cantPaneles, double horasRadiacionDiaria) {
		
		super(id, nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if(superficieCubierta<=0) {
			throw new RuntimeException("La superficie cubierta no puede ser cero ni menor a cero");
		}
		if(cantPaneles<=0 || cantPaneles>MAX_PANELES) {
			throw new RuntimeException("La cantidad de paneles tiene que ser mayor a cero y menor a "+ MAX_PANELES);
		}
		if(horasRadiacionDiaria <= 0 || horasRadiacionDiaria >=24) {
			throw new RuntimeException("La cantidad de horas de radiacion tiene que ser mayor a cero y menor a 24");
		}
		
		this.superficieCubierta = superficieCubierta;
		this.cantPaneles = cantPaneles;
		this.horasRadiacionDiaria = horasRadiacionDiaria;
	}

	//-------Getters-----

	public static int getMaxPaneles() {
		return MAX_PANELES;
	}



	public double getSuperficieCubierta() {
		return superficieCubierta;
	}



	public int getCantPaneles() {
		return cantPaneles;
	}



	public double getHorasRadiacionDiaria() {
		return horasRadiacionDiaria;
	}
	
	@Override
	public double calcularCuotaMinima() {
		
		return super.calcularCuotaMinima()*horasRadiacionDiaria/24;
	}
	
	
	
	
	
}
