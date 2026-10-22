package ar.edu.ungs.prog2.smartgrids;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract  class Planta {
	private static int proximoId = 1;   // compartido por todas las plantas
	private final int id;
	private String nombre;
	private String direccion;
	private double capacidadMaxgen;
	private boolean isActiva;
	private LocalDateTime fechaPuestaEnFuncionamiento;
	private ArrayList<PeriodoDeInactividad>historialDeInactividad;
	private HashMap<LocalDateTime, RegistroMonitoreo> registros;
	
	
	
	//La planta nace activa y con el historial vacio.
	public Planta(String nombre, String direccion, double capacidadMaxgen,
			LocalDateTime fechaPuestaEnFuncionamiento) {
		
		if(nombre==null || nombre.isEmpty()) {
			throw new RuntimeException("El nombre es un campo obligatorio no debe estar vacio");
		}
		if(direccion==null || direccion.isEmpty()) {
			throw new RuntimeException("La direccion es un campo obligatorio no debe estar vacio");
		}
		if(capacidadMaxgen<=0) {
			throw new RuntimeException("La capacidad maxima generada debe ser mayor a cero");
		}
		if(fechaPuestaEnFuncionamiento == null) {
			throw new RuntimeException("La fecha de Puesta en funcionamiento no puede estar vacia");
		}
		
		
		
		this.id = proximoId++;   // asigna el próximo ID y lo incrementa ,arranca en 1.
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxgen = capacidadMaxgen;
		this.isActiva = true;
		this.fechaPuestaEnFuncionamiento = fechaPuestaEnFuncionamiento;
		this.historialDeInactividad = new ArrayList<PeriodoDeInactividad>();
		this.registros= new HashMap<LocalDateTime, RegistroMonitoreo>();
	}
	
	public abstract double calcularCuotaMinima();
	
	
	public abstract boolean cumplioCuotaMinima(RegistroMonitoreo registros);
	// recibe el registro para saber que hora evaluar
	
	
	public void actualizarEstado(boolean nuevoEstado,LocalDateTime fechaHora) {
		if(nuevoEstado== isActiva) {
			return; // no hay cambio de estado, no se hace nada
		}
		
		if(fechaHora == null) {
			throw new RuntimeException("La fecha y la hora son obligatorios");
			
		}
		if(nuevoEstado) {
			historialDeInactividad.get(historialDeInactividad.size()-1).cerrar(fechaHora);
		}
		else{
			PeriodoDeInactividad nuevoRegistro= new PeriodoDeInactividad(fechaHora);
			historialDeInactividad.add(nuevoRegistro);
		}
		isActiva =nuevoEstado;
	}
	
	public void agregaRegistroMonitoreo(RegistroMonitoreo registro) {
		
		if(registro==null) {
			throw new RuntimeException("El registro de monitoreo es obligatorio");
		}
		LocalDateTime fechaHora= registro.getFechaHora();
		if(fechaHora.isBefore(fechaPuestaEnFuncionamiento)) {
			throw new RuntimeException("La fecha y hora no puede ser anterior a la puesta en funcionamiento");
		}
		if(registros.containsKey(fechaHora)) {
			throw new RuntimeException("Ya existe un registro para esa fecha y hora");
		}
		
		if(!isActiva) {
			throw new RuntimeException("La planta esta inactiva, no se puede registrar monitoreo");
		}
		if(!(aceptarRegistroMonitoreo(registro))) {
			throw new RuntimeException("El tipo de registro de monitoreo no cumple con la planta");
		}
		
		registros.put(fechaHora, registro);
	}
	
	
	

	
	public boolean aceptarRegistroMonitoreo(RegistroMonitoreo registro) {
		if(registro==null){
			throw new RuntimeException("El registro de Monitoreo es obligatorio");
		}
		return registro.getClass()== RegistroMonitoreo.class;// si la clase real(RegistroPlantaEolica) 
								// es una subclase de Registro monitoreo retorna false.
								//compara si son de la misma clase.
		
		
	}



	//---------------Getters---------
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public double getCapacidadMaxgen() {
		return capacidadMaxgen;
	}

	public boolean isActiva() {
		return isActiva;
	}

	public LocalDateTime getFechaPuestaEnFuncionamiento() {
		return fechaPuestaEnFuncionamiento;
	}

	public List<PeriodoDeInactividad> getHistorialDeInactividad() {
	    return Collections.unmodifiableList(historialDeInactividad);
	}
	public Map<LocalDateTime, RegistroMonitoreo> getRegistros() {
    return Collections.unmodifiableMap(registros);
}

	public double totalEnergiaGenerada(){
		double suma=0.0;
		for (RegistroMonitoreo r : registros.values()) {
			suma+= r.getEnergiaGenerada();
		}
		return suma;
	}

	public int[] cantIncidenciaPorhora(int[] incidencias){
		
		for (RegistroMonitoreo r : registros.values()) { // recorre todos los registros de la planta
			int h=r.getFechaHora().getHour();     // obtiene la hora de ese registro
			incidencias[h]+=r.getCantIncidencias(); // en la posicion h del arreglo suma las incidencias de ese registro 
													//s si dos registros tienen la misma hora se suman las inccidencias
		}
		
	
		
		return incidencias;

	}


	  public int[] enCuantoCumplio(String fechaDesde, String fechaHasta, int[] enCuantosDiasCumplio){
			LocalDate desde = LocalDate.parse(fechaDesde);
			LocalDate hasta = LocalDate.parse(fechaHasta);
			
			if(desde.isAfter(hasta)){// si desde es posterior a hasta
				throw new RuntimeException("La primer fecha tiene que ser anterior ala segunda ");
			}


			for(RegistroMonitoreo r :registros.values() ){
					int h=r.getFechaHora().getHour();
					LocalDate dia = r.getFechaHora().toLocalDate();
																    // dia>= desde  y   dia<= hasta
					if(!dia.isBefore(desde)&& !dia.isAfter(hasta)){ //el dia no es anterior a desde y el dia no es posterior a  hasta.
																    // es para que tome las fechas cuandos on iguales.	
						if(cumplioCuotaMinima(r)){
							enCuantosDiasCumplio[h]+= 1;
						}
					}		
					
			}
		return enCuantosDiasCumplio;
	}
	
	public abstract boolean esRenovable();

	public String  getFuentePrimaria(){ //valor pord efecto para las plantas no renovables
			return null;
	}

	public boolean cumplio(ArrayList<String>plantasQueNoCumplieron){
		for (RegistroMonitoreo r : registros.values()) {
			if((cumplioCuotaMinima(r))){
				return true;
			}
		}
		return false;// si no ahy registros el for no entra y devuelve false.
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append(nombre);
	    sb.append(" (");
	    if (isActiva) {
	        sb.append("ACTIVA");
	    } else {
	        sb.append("INACTIVA");
	    }
	    sb.append("): ");
	    sb.append(fechaPuestaEnFuncionamiento.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));//agrega los segundos
	    return sb.toString();
	}

	

  

	
	
	
	
}
