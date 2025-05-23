package aca.edo.spring;

public class EdoMaestroPreg {

	private String edoId;
	private String preguntaId;
	private String pregunta;
	private String tipo;
	private String orden;
	private String areaId;
	private String comentario1;
	private String comentario2;
	private String comentario3;
	private String comentario4;
	
	public EdoMaestroPreg(){
		edoId 		= "";
		preguntaId 	= "";
		pregunta 	= "";
		tipo 		= "";
		orden 		= "";
		areaId		= "";
		comentario1	= "";
		comentario2	= "";
		comentario3	= "";
		comentario4	= "";
	}
	
	public String getEdoId() {
		return edoId;
	}

	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getComentario1() {
		return comentario1;
	}

	public void setComentario1(String comentario1) {
		this.comentario1 = comentario1;
	}

	public String getComentario2() {
		return comentario2;
	}

	public void setComentario2(String comentario2) {
		this.comentario2 = comentario2;
	}

	public String getComentario3() {
		return comentario3;
	}

	public void setComentario3(String comentario3) {
		this.comentario3 = comentario3;
	}

	public String getComentario4() {
		return comentario4;
	}

	public void setComentario4(String comentario4) {
		this.comentario4 = comentario4;
	}
	
}