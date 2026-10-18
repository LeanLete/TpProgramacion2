package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaEolica extends PlantaRenovable{
	private static final int MAX_AEROGENERADORES = 1000;
	private static final int MAX_ALTURA_TORRES = 300;
	private static final int MIN_VIENTO = 3;
	private static final int MAX_VIENTO = 25;
	
	private int cantAerogeneradores;
	private double alturaTorres;
	private double velocidadMinimaViento;
	
	public PlantaEolica( String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
		
		super( nombre, direccion, capacidadMaxgen, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if(cantAerogeneradores <=0 ||  cantAerogeneradores > MAX_AEROGENERADORES) {
			throw new RuntimeException("La cantidad de aerogeneradores debe estar entre 1 y " + MAX_AEROGENERADORES);
		}
		if(alturaTorres<=0 || alturaTorres> MAX_ALTURA_TORRES) {
			throw new RuntimeException("La altura de las torres tienen que estar entre 1 y "+ MAX_ALTURA_TORRES+" metros");
		}
		
		if(velocidadMinimaViento<MIN_VIENTO || velocidadMinimaViento>MAX_VIENTO ) {
			throw new RuntimeException("La velocidad minima del viento debe estar entre " + MIN_VIENTO + " y " + MAX_VIENTO + " m/s");
		}
		
		
		this.cantAerogeneradores = cantAerogeneradores;
		this.alturaTorres = alturaTorres;
		this.velocidadMinimaViento = velocidadMinimaViento;
	}

	
	//--------- Getters --------------
	
	public static int getMaxAerogeneradores() {
		return MAX_AEROGENERADORES;
	}

	public static int getMaxAlturaTorres() {
		return MAX_ALTURA_TORRES;
	}

	public static int getMinViento() {
		return MIN_VIENTO;
	}

	public static int getMaxViento() {
		return MAX_VIENTO;
	}

	public int getCantAerogeneradores() {
		return cantAerogeneradores;
	}

	public double getAlturaTorres() {
		return alturaTorres;
	}

	public double getVelocidadMinimaViento() {
		return velocidadMinimaViento;
	}


	@Override
	public boolean aceptarRegistroMonitoreo(RegistroMonitoreo registro) {
		if(registro ==null){
			throw new RuntimeException("El registro es obligatorio");
		}
		if( registro instanceof RegistroPlantaEolica){
			RegistroPlantaEolica registroEolica = (RegistroPlantaEolica)registro;
			double velocidadDelViento =registroEolica.getVelocidadViento();
			return velocidadDelViento>= velocidadMinimaViento; 
		}
		return false;
		

		
	}
	
	
	
	
	

}
