package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

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
		return registro instanceof RegistroPlantaEolica;//responde si el registro es 
														//de tipo PlantaEolica
			
		

		
	}

	public Map<Integer,Double> horasSinVelocidadMinimaPorPlantaEolica(){
		Map<Integer,Double>horas=new TreeMap<>();// el treeemap nos ordena las las horas.(ordena las claves)busqueda O(log(n))
		Double []max=maximaVelocidadPorHora(); // guarda el array con las maximasvelocidades de viento

		for(int hora=0;hora<max.length;hora++){
			if(max[hora] !=null && max[hora]<velocidadMinimaViento ){ //si no esta vacio y esa velocidad es menor
																	//  ala velocidad minima del viento lo agrego al mapa
				horas.put(hora, max[hora]);
			}
		}

		return horas;
	}

	public Double[] maximaVelocidadPorHora(){
		Double[] max= new Double[24];//el arreglo comienza en null.
		
		for (RegistroMonitoreo r : getRegistros().values()) {// recorro los registros
			RegistroPlantaEolica reg=(RegistroPlantaEolica) r;// tengo que castear todos los registros.

			int hora=r.getFechaHora().getHour(); //obtengo la hora del registro de 0 a 23
			double velocidad=reg.getVelocidadViento();// velocidad del viento del registro
			if(max[hora]==null || velocidad>max[hora] ){// si es nulo o la velocidad es mayor  
				max[hora]=velocidad;// se queda con la maxima velocidad del viento
			}
			

		}
		return max;
	}


	
	
	
	
	

}
