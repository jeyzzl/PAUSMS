/**
 * 
 */
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class FinPeriodo {
	private String periodoId;
	private String fechaIni;
	private String fechaFin;
	private String cargas;
	private String modalidades;
	private String mensaje;
	private String estado;
	
	public FinPeriodo(){
		periodoId	= "";
		fechaIni	= "";
		fechaFin	= "";
		cargas		= "";
		modalidades	= "";
		mensaje		= "";
		estado		= "";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		fechaIni		= rs.getString("FECHA_INI");
		fechaFin		= rs.getString("FECHA_FIN");
		cargas			= rs.getString("CARGAS");
		modalidades		= rs.getString("MODALIDADES");
		estado			= rs.getString("ESTADO");
		mensaje			= rs.getString("MENSAJE");
		periodoId		= rs.getString("PERIODO_ID");
	}
	
	public void mapeaRegId(Connection con, String periodoId) throws SQLException{	
		 
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT PERIODO_ID, " +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" MODALIDADES, CARGAS, ESTADO, MENSAJE"+				
					" FROM ENOC.FIN_PERIODO" + 
					" WHERE PERIODO_ID =  '"+periodoId+"' ");
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
}