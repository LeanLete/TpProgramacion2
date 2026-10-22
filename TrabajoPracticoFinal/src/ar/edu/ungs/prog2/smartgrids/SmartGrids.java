package ar.edu.ungs.prog2.smartgrids;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SmartGrids implements ISmartGrids {
	
	LinkedHashMap<String, Planta> plantas;




	public SmartGrids() {
		plantas=new LinkedHashMap<String, Planta>(); // LinkedHasmap para respetar el orden de insercion.
	}


	private String normalizar(String nombre) {
		if (nombre ==null) {
			throw new RuntimeException("El campo del nombre es obligatorio");
		}
		return nombre.toLowerCase().replace(" ", "");	
				}
	/// pasa a minuscula el nombre y elimina los espacios
	
	
	private Planta  buscarPlanta(String nombre) {
		Planta planta = plantas.get(normalizar(nombre));
		if(planta== null) {
			throw new RuntimeException("El nombre "+ nombre +" no existe ");
		}
			return planta;
		}
	// busca la planta por nombre.
	
	private void guardarPlanta(Planta planta) {
		String clave= normalizar(planta.getNombre());
		if(plantas.containsKey(clave)) {
			throw new RuntimeException("La Planta"+planta.getNombre()+ " ya existe");
		}
		plantas.put(clave, planta);
		
	}
	// guarda la planta 
	
	
	@Override
	public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {
		
		Planta solar= new PlantaSolar(nombre, direccion, capacidadMaxima, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, superficieCubierta, cantidadPaneles, horasRadiacionDiaria);
		guardarPlanta(solar);
	}

	@Override
	public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
		
		Planta eolica= new PlantaEolica(nombre, direccion, capacidadMaxima, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadAerogeneradores,  alturaTorres,  velocidadMinimaViento);
		guardarPlanta(eolica);

	}

	@Override
	public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantidadTurbinas, double capacidadEmbalse) {
		Planta hidraulica= new PlantaHidraulica(cantidadTurbinas, nombre, direccion, capacidadMaxima,  LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadTurbinas, capacidadEmbalse);
		guardarPlanta(hidraulica);
	}

	@Override
	public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora,
			double capacidadAlmacenamientoCombustible) {
		Planta termica= new PlantaTermica(nombre, direccion, capacidadMaxima,LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento) , tipoCombustible, capacidadAlmacenamientoCombustible, objetivoEmisionesCo2PorHora);
		guardarPlanta(termica);
	}

	@Override
	public List<String> consultarPlantas() {
		List<String>lista= new ArrayList<String>();
		for(Planta p:plantas.values()) {
			lista.add(p.toString());
		}
		return lista; 
		
	}// devuelve una lista con el detalle de cada planta

	@Override
	public double capacidadMinimaCuota(String nombre) {
		Planta planta = buscarPlanta(nombre);// sino existe se lanza la excepcion
		return planta.calcularCuotaMinima();
	}// devuelve la capacidad minima de cuota de la planta por nombre,
	//  si no existe lanza una excepcion.

	@Override
	public void activarPlanta(String nombre, String fechaHora) {
		Planta planta = buscarPlanta(nombre);
		planta.actualizarEstado(true, LocalDateTime.parse(fechaHora));

	}// actualiza el estado de la planta por nombre y fechaHora,
	//  si ya estaba activa no hace nada, si estaba inactiva la activa 
	// y cierra el periodo de inactividad.

	@Override
	public void desactivarPlanta(String nombre, String fechaHora) {
		Planta planta = buscarPlanta(nombre);
		planta.actualizarEstado(false, LocalDateTime.parse(fechaHora));

	}// actualiza el estado de la planta por nombre y fechaHora,
	//  si ya estaba inactiva no hace nada, si estaba activa la desactiva
	// y abre un nuevo periodo de inactividad.

	@Override
	public boolean estaActiva(String nombre) {
		Planta planta = buscarPlanta(nombre);
		return planta.isActiva();
	}// consulta el estado de la planta por nombre, devuelve true si esta activa,
	 // false si esta inactiva.

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
		Planta planta = buscarPlanta(nombre);
		RegistroMonitoreo registro = new RegistroMonitoreo(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias);
		planta.agregaRegistroMonitoreo(registro); 

	}// busca la planta por nombre, crea un registro de monitoreo 
	// y lo agrega a la planta.

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double velocidadViento) {
		Planta planta = buscarPlanta(nombre);
		RegistroPlantaEolica registro = new RegistroPlantaEolica(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias,velocidadViento);
		planta.agregaRegistroMonitoreo(registro); 

	}// busca la planta por nombre, crea un registro eolica y lo agrega al mapa de registros.

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double co2Emitido, double combustibleUtilizado) {
		Planta planta = buscarPlanta(nombre);
		RegistroPlantaTermica registro = new RegistroPlantaTermica(LocalDateTime.parse(fechaHora), energiaGenerada, incidencias,co2Emitido,combustibleUtilizado);
		planta.agregaRegistroMonitoreo(registro); 

	}

	@Override
	public double totalGenerado(String nombre) {
		
		Planta planta = buscarPlanta(nombre);
		
		return planta.totalEnergiaGenerada();
	}

	@Override
	public int[] incidenciasPorHora(String nombre) {
		int[] incidencias=new int[24];// arreglo de 24 posiciones 
		
		Planta planta=buscarPlanta(nombre);
		return planta.cantIncidenciaPorhora(incidencias);
	}
	//suma la cantidad de incidencias acumuladas por hora de todos
	//  los registros sin importar el dia;


	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		int[] enCuantosDiasCumplio=new int[24];// arreglo de 24 posiciones

		return buscarPlanta(nombre).enCuantoCumplio(fechaDesde,fechaHasta,enCuantosDiasCumplio);
	}

	@Override
	public Map<String, Integer> plantasRenovablesPorFuente() {
		 Map <String,Integer> plantasRenovablesFuente= new  HashMap<>();
		for(Planta p: plantas.values()){
			if(p.esRenovable()){
				String fuente=p.getFuentePrimaria();
				plantasRenovablesFuente.put(fuente,plantasRenovablesFuente.getOrDefault(fuente, 0)+1 );
				
			}//recorre las plantas del mapa se queda con las renovables y la agrega
			// si ya existe la fuente y la suma.
				
		}
		return plantasRenovablesFuente;
	}

	@Override
	public List<String> plantasQueNuncaCumplieronCuota() {
		ArrayList<String>plantasQueNoCumplieron=  new ArrayList<String>();
		for (Planta p : plantas.values()) {
			if(!p.cumplio(plantasQueNoCumplieron)){
					plantasQueNoCumplieron.add(p.getNombre());
			}
		}
		return plantasQueNoCumplieron;
		
	}

	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		StringBuilder sb=new  StringBuilder();
		
		sb.append("Plantas Eolicas: Horas con poco viento\n");

		for (Planta p : plantas.values()) {
			if(p instanceof PlantaEolica){
				PlantaEolica eolica =(PlantaEolica)p;
				sb.append(formatearPlanta(eolica));
			}
		}
		return sb.toString();
	}
	

	private String formatearPlanta(PlantaEolica eolica) {
		StringBuilder sb=new  StringBuilder();
		sb.append("-");
		sb.append(eolica.getNombre());
		sb.append(": ");
		sb.append(eolica.getVelocidadMinimaViento());
		sb.append("m/s\n");

		for (Map.Entry<Integer,Double> e  : eolica.horasSinVelocidadMinimaPorPlantaEolica().entrySet()) {
			
			if(e.getKey()>=0 && e.getKey()<10 ){ // los numeros salian 1 2 3y necesitabamos 01,02,03
									 
				sb.append("      0");
			}
			sb.append(e.getKey()+": "+ e.getValue()+"m/s\n");
		}

		return sb.toString();
		
	}


	@Override
	public int limpiarMonitoreosSinDatos() {
		int cantRegistrosBorrados=0;
		for (Planta p : plantas.values()) {
			cantRegistrosBorrados+=  p.borrarRegistros();
		}
		return cantRegistrosBorrados;
		
	}

	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		ArrayList<Planta> lista= new ArrayList<>(plantas.values());// lista de las plantas
		
		Collections.sort(lista,new ComparadorPorEnergia()); // ordena usando el criterio del comparador, sort recibe una lista y un objeto comparador
		ArrayList<String> nuevaLista= new ArrayList<>();// lista vacia 
		for (Planta p : lista) {
			nuevaLista.add(p.getNombre()+": "+p.totalEnergiaGen());// agrego el string concatenando eso datos

		}

		return nuevaLista;
	}

	private static class ComparadorPorEnergia implements Comparator<Planta> {// clase anidada no esta instanciada
																			// no puede usar los objetos de smart grids	

		@Override
		public int compare(Planta a, Planta b) {
			return Double.compare(b.totalEnergiaGen(), a.totalEnergiaGen());// b primero y a , mayor a menor 
																			
		}
	
		
	}

	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		// TODO Auto-generated method stub
		return null;
	}

}
