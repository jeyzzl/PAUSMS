// Bean del Catalogo TipoCurso
package  aca.catalogo.spring;

public class CatTipoCurso{
	private String tipoCursoId;	
	private String nombreTipoCurso;
	private String corto;
	
	public CatTipoCurso(){
		tipoCursoId 	= "";
		nombreTipoCurso	= "";
		nombreTipoCurso	= "";
	}
	
	public String getTipoCursoId(){
		return tipoCursoId;
	}
	
	public void setTipoCursoId( String tipoCursoId){
		this.tipoCursoId = tipoCursoId;
	}
	
	public String getNombreTipoCurso(){
		return nombreTipoCurso;
	}
	
	public void setNombreTipoCurso( String nombreTipoCurso){
		this.nombreTipoCurso = nombreTipoCurso;
	}

	public String getCorto() {
		return corto;
	}

	public void setCorto(String corto) {
		this.corto = corto;
	}
	
}