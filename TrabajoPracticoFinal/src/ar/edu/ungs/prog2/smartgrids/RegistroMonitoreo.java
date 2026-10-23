package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreo {
	private static int proximoId = 1;// compartido por todos los registros
	private final int id;
	private LocalDateTime fechaHora;
	private double  energiaGeneradaPorHora;
	private int cantIncidencias;

	public RegistroMonitoreo( LocalDateTime fechaHora, double energiaGeneradaPorHora,
			int cantIncidencias) {
		
		if(energiaGeneradaPorHora<0) {
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
		this.energiaGeneradaPorHora = energiaGeneradaPorHora;
		this.cantIncidencias = cantIncidencias;
	}
	
	//---------Getters--------------
	public int getId() {
		return id;
	}
	
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public double getEnergiaGenerada() {
		return energiaGeneradaPorHora;
	}
	public int getCantIncidencias() {
		return cantIncidencias;
	}

	@Override
	public String toString() {
		return "RegistroMonitoreo [id=" + id + ", fechaHora=" + fechaHora + ", energiaGenerada=" + energiaGeneradaPorHora
				+ ", cantIncidencias=" + cantIncidencias + "]";
	}

    
	
	
	
}
