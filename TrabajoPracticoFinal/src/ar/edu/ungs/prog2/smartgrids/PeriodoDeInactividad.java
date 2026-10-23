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




	// Incluye la fecha  si no es anterior a desde (desde inclusivo) y,
    // o el periodo sigue abierto (hasta == null), o el momento es anterior a hasta
    // (hasta exclusivo). El chequeo de null va primero para no llamar
    // isBefore(null), que lanzaría NullPointerException.
	public boolean incluye(LocalDateTime fechaHora) {
	
		
        if(!fechaHora.isBefore(desde) && (hasta==null || fechaHora.isBefore(hasta) )){
			return true;
		}
		return false;
    }

	//------Getters-----------
	
	public LocalDateTime getDesde() {
		return desde;
	}


	public LocalDateTime getHasta() {
		return hasta;
	}





	@Override
	public String toString() {
		if(hasta==null){
			return "PeriodoDeInactividad [desde=" + desde + ", sigue inactiva]";
		}
		return "PeriodoDeInactividad [desde=" + desde + ", hasta=" + hasta + "]";
	}





    
	
}
