package ar.edu.ungs.prog2.smartgrids;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract  class Planta {
	private int id;
	private String nombre;
	private String direccion;
	private double capacidadMaxgen;
	private boolean isActiva;
	private LocalDate fechaPuestaEnFuncionamiento;
	private ArrayList<PeriodoDeInactividad>historialDeInactividad;
	/// private registrosMonitoreo  que estructura conviene usar HashMap.id, registros o fechaHora y Registros objeto
	
	public Planta(int id, String nombre, String direccion, double capacidadMaxgen, boolean isActiva,
			LocalDate fechaPuestaEnFuncionamiento, ArrayList<PeriodoDeInactividad> historialDeInactividad) {
		
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
		
		
		this.id = id;// chequear 
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxgen = capacidadMaxgen;
		this.isActiva = true;
		this.fechaPuestaEnFuncionamiento = fechaPuestaEnFuncionamiento;
		this.historialDeInactividad = new ArrayList<PeriodoDeInactividad>();
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

	public LocalDate getFechaPuestaEnFuncionamiento() {
		return fechaPuestaEnFuncionamiento;
	}

	public List<PeriodoDeInactividad> getHistorialDeInactividad() {
	    return Collections.unmodifiableList(historialDeInactividad);
	}
	
}
