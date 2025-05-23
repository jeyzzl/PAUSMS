// Bean del Catalogo de Horarios
package  aca.catalogo.spring;

public class CatHorarioAcceso{
	private String horarioId;
	private String codigoPersonal;

		
	public CatHorarioAcceso(){
		horarioId		= "0";
		codigoPersonal	= "-";
	}


	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	
}