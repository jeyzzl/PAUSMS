// Bean del Catalogo Cargas
package  aca.carga;

import java.sql.*;

public class Carga{
	private String cargaId;
	private String nombreCarga;
	private String fCreada;
	private String periodo;
	private String ciclo;
	private String fInicio;
	private String fFinal;
	private String fExtra;
	private String numCursos;
	private String estado;
	private String tipoCarga;
	private String semanas;
	private String evalua;
	
	public Carga(){
		cargaId			= "";
		nombreCarga		= "";
		fCreada			= "";
		periodo			= "";
		ciclo			= "";
		fInicio			= "";
		fFinal			= "";
		fExtra			= "";
		numCursos		= "";
		estado			= "";
		tipoCarga		= "O";
		semanas			= "16";
		evalua			= "S";
	}
	
	public String getCargaId(){
		return cargaId;
	}
	
	public void setCargaId( String cargaId){
		this.cargaId = cargaId;
	}	
	
	public String getNombreCarga(){
		return nombreCarga;
	}
	
	public void setNombreCarga( String nombreCarga){
		this.nombreCarga = nombreCarga;
	}	
	
	public String getFCreada(){
		return fCreada;
	}
	
	public void setFCreada( String fCreada){
		this.fCreada = fCreada;
	}
	
	public String getPeriodo(){
		return periodo;
	}
	
	public void setPeriodo( String periodo){
		this.periodo = periodo;
	}
	
	public String getCiclo(){
		return ciclo;
	}
	
	public void setCiclo( String ciclo){
		this.ciclo = ciclo;
	}
	
	public String getFInicio(){
		return fInicio;
	}
	
	public void setFInicio( String fInicio){
		this.fInicio = fInicio;
	}
	
	public String getFFinal(){
		return fFinal;
	}
	
	public void setFFinal( String fFinal){
		this.fFinal = fFinal;
	}
	
	public String getFExtra(){
		return fExtra;
	}
	
	public void setFExtra( String fExtra){
		this.fExtra = fExtra;
	}
	
	public String getNumCursos(){
		return numCursos;
	}
	
	public void setNumCursos( String numCursos){
		this.numCursos = numCursos;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado( String estado){
		this.estado = estado;
	}	

	/**
	 * @return the tipoCarga
	 */
	public String getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga the tipoCarga to set
	 */
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	/**
	 * @return the semanas
	 */
	public String getSemanas() {
		return semanas;
	}

	/**
	 * @param semanas the semanas to set
	 */
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}
	

	public String getEvalua() {
		return evalua;
	}

	public void setEvalua(String evalua) {
		this.evalua = evalua;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId 		= rs.getString("CARGA_ID");
		nombreCarga 	= rs.getString("NOMBRE_CARGA");
		fCreada			= rs.getString("F_CREADA");
		periodo	 		= rs.getString("PERIODO");
		ciclo 			= rs.getString("CICLO");
		fInicio			= rs.getString("F_INICIO");
		fFinal 			= rs.getString("F_FINAL");
		fExtra 			= rs.getString("F_EXTRA");
		numCursos		= rs.getString("NUM_CURSOS");
		estado 			= rs.getString("ESTADO");
		tipoCarga 		= rs.getString("TIPOCARGA");
		semanas 		= rs.getString("SEMANAS");
		evalua			= rs.getString("EVALUA");
	}
	
	public void mapeaRegId( Connection conn, String cargaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID, NOMBRE_CARGA, "+
				"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA WHERE CARGA_ID = ? "); 
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		

	}

}