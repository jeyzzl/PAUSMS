package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfeTipoBeca {
	private String id;
	private String descripcion;
	private String diezma;
	private String porcentaje;
	private String esDelAlumno	;
	private String soloPostgrado;
	private String version;
	private String idCcosto;
	private String idCtamayor;
	private String idEjercicio;
	private String tipoCuenta;
	private String tope;
	
	// Constructor
	public AfeTipoBeca(){		
		id 				= "";
		descripcion   	= "";
		diezma			= "";
		porcentaje 		= "";
		esDelAlumno		= "";
		soloPostgrado	= "";
		version	    	= "";
		idCcosto		= "";
		idCtamayor   	= "";
		idEjercicio		= "";
		tipoCuenta 		= "";
		tope 			= "";
	}
	
	
	

	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




	public String getDiezma() {
		return diezma;
	}




	public void setDiezma(String diezma) {
		this.diezma = diezma;
	}




	public String getPorcentaje() {
		return porcentaje;
	}




	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}




	public String getEsDelAlumno() {
		return esDelAlumno;
	}




	public void setEsDelAlumno(String esDelAlumno) {
		this.esDelAlumno = esDelAlumno;
	}




	public String getSoloPostgrado() {
		return soloPostgrado;
	}




	public void setSoloPostgrado(String soloPostgrado) {
		this.soloPostgrado = soloPostgrado;
	}




	public String getVersion() {
		return version;
	}




	public void setVersion(String version) {
		this.version = version;
	}




	public String getIdCcosto() {
		return idCcosto;
	}




	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}




	public String getIdCtamayor() {
		return idCtamayor;
	}




	public void setIdCtamayor(String idCtamayor) {
		this.idCtamayor = idCtamayor;
	}




	public String getIdEjercicio() {
		return idEjercicio;
	}




	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}




	public String getTipoCuenta() {
		return tipoCuenta;
	}




	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}



	public String getTope() {
		return tope;
	}




	public void setTope(String tope) {
		this.tope = tope;
	}




	public void mapeaReg(ResultSet rs ) throws SQLException{
		id 				= rs.getString("ID");
		descripcion   	= rs.getString("DESCRIPCION");
		diezma			= rs.getString("DIEZMA");
		porcentaje		= rs.getString("PORCENTAJE");
		esDelAlumno		= rs.getString("ES_DEL_ALUMNO");
		soloPostgrado	= rs.getString("SOLO_POSTGRADO");
		version	    	= rs.getString("VERSION");
		idCcosto		= rs.getString("ID_CCOSTO");
		idCtamayor   	= rs.getString("ID_CTAMAYOR");
		idEjercicio 	= rs.getString("ID_EJERCICIO");
		tipoCuenta		= rs.getString("TIPO_CUENTA");
		tope 			= rs.getString("TOPE");
	}
	
	public void mapeaRegId(Connection con, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID,DESCRIPCION, DIEZMA, PORCENTAJE, "+
					"ES_DEL_ALUMNO,SOLO_POSTGRADO, VERSION, ID_CCOSTO, ID_CTAMAYOR, ID_EJERCICIO, TIPO_CUENTA, TOPE "+
					"FROM NOE.AFE_TIPOBECA WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}