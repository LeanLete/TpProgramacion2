package ar.edu.ungs.prog2.smartgrids;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
		
		
		this.id = proximoId++;   // asigna el próximo ID y lo incrementa
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
			throw new RuntimeException("La planta ya se encuentra en ese estado");
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
