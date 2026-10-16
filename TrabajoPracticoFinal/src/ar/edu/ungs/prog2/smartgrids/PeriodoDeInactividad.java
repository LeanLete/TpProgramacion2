package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PeriodoDeInactividad {
	private LocalDateTime desde;
	private LocalDateTime hasta;

	public PeriodoDeInactividad(LocalDateTime desde) {
		if(desde == null) {
			throw new RuntimeException("La fecha de inicio es obligatoria");
		}
		this.desde = desde;
		this.hasta = null;
	}





	public void cerrar(LocalDateTime hasta) {
		if(hasta == null  ) {
			throw new RuntimeException("La fecha de fin es obligatoria");
		}
		if(hasta.isBefore(desde)) { // compara si hasta es antes que desde
			throw new RuntimeException("La fecha es invalida");
		}
		this.hasta=hasta;
		
	}


	//------Getters-----------
	
	public LocalDateTime getDesde() {
		return desde;
	}


	public LocalDateTime getHasta() {
		return hasta;
	}
	
}
