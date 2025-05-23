//Beans para la tabla Res_Comentario
package aca.residencia.spring;

public class ResComentario {
	private String folio;
	private String codigoPersonal;
	private String residenciaId;
	private String comentario;
	private String comentarioB;
	
	public ResComentario(){		
		folio			= "0";
		codigoPersonal	= "0";
		residenciaId	= "I";
		comentario 		= "";
		comentarioB 	= "";
	}
	
	
	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public String getResidenciaId() {
		return residenciaId;
	}


	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}


	public String getComentarioB() {
		return comentarioB;
	}
	public void setComentarioB(String comentarioB) {
		this.comentarioB = comentarioB;
	}

	
	
}