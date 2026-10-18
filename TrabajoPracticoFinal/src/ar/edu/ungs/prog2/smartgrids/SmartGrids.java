package ar.edu.ungs.prog2.smartgrids;


import java.time.LocalDateTime;
import java.util.ArrayList;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double velocidadViento) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double co2Emitido, double combustibleUtilizado) {
		// TODO Auto-generated method stub

	}

	@Override
	public double totalGenerado(String nombre) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] incidenciasPorHora(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> plantasRenovablesPorFuente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasQueNuncaCumplieronCuota() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int limpiarMonitoreosSinDatos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		// TODO Auto-generated method stub
		return null;
	}

}
