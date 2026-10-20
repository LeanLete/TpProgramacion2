package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreo {
	private static int proximoId = 1;// compartido or todos los registros
	private final int id;
	private int idPlanta;//chequear
	private LocalDateTime fechaHora;
	private double  energiaGenerada;
	private int cantIncidencias;
	public RegistroMonitoreo( LocalDateTime fechaHora, double energiaGenerada,
			int cantIncidencias) {
		
		if(energiaGenerada<0) {
			throw new RuntimeException("La energia generada por hora debe ser mayor a cero");
		}
		if(cantIncidencias<0) {
			throw new RuntimeException("La cantidad de incidencias debe ser mayor a cero");
		}
		if(fechaHora ==null) {
			throw new RuntimeException("La fecha y hora de los registros son obligatorios");
		}
		
		this.id = proximoId++;
		this.fechaHora = fechaHora;
		this.energiaGenerada = energiaGenerada;
		this.cantIncidencias = cantIncidencias;
	}
	
	//---------Getters--------------
	public int getId() {
		return id;
	}
	public int getIdPlanta() {
		return idPlanta;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public double getEnergiaGenerada() {
		return energiaGenerada;
	}
	public int getCantIncidencias() {
		return cantIncidencias;
	}

    
	
	
	
}
