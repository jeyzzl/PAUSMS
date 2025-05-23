// Bean del Catalogo de Estados
package  aca.catalogo.spring;

import java.sql.*;

public class CatEstado{
	private String paisId;
	private String estadoId;
	private String nombreEstado;
	private String corto;
	private String sepId;
	private String sepCorto;
	private String semaforo;
	
	public CatEstado(){
		paisId 			= "";
		estadoId 		= "";
		nombreEstado	= "";
		corto 			= "-";
		sepId			= "-";
		sepCorto		= "-";
		semaforo		= "-";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getNombreEstado(){
		return nombreEstado;
	}
	
	public void setNombreEstado( String nombreEstado){
		this.nombreEstado = nombreEstado;
	}
	
	public String getCorto() {
		return corto;
	}

	public void setCorto(String corto) {
		this.corto = corto;
	}
	
	public String getSepId() {
		return sepId;
	}

	public void setSepId(String sepId) {
		this.sepId = sepId;
	}

	public String getSepCorto() {
		return sepCorto;
	}

	public void setSepCorto(String sepCorto) {
		this.sepCorto = sepCorto;
	}

	public String getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(String semaforo) {
		this.semaforo = semaforo;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 			= rs.getString("PAIS_ID");
		estadoId	 	= rs.getString("ESTADO_ID");
		nombreEstado 	= rs.getString("NOMBRE_ESTADO");
		corto 			= rs.getString("CORTO");
		sepId 			= rs.getString("SEP_ID");
		sepCorto		= rs.getString("SEP_CORTO");
		semaforo		= rs.getString("SEMAFORO");
	}

}