/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class BajaAlumpaso {
	private String bajaId;
	private String pasoId;
	private String fecha;
	private String estado;
	
	public BajaAlumpaso(){
		bajaId	= "";
		pasoId	= "";
		fecha	= "";
		estado	= "";
	}

	/**
	 * @return the bajaId
	 */
	public String getBajaId() {
		return bajaId;
	}

	/**
	 * @param bajaId the bajaId to set
	 */
	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the pasoId
	 */
	public String getPasoId() {
		return pasoId;
	}

	/**
	 * @param pasoId the pasoId to set
	 */
	public void setPasoId(String pasoId) {
		this.pasoId = pasoId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		bajaId	= rs.getString("BAJA_ID");
		pasoId	= rs.getString("PASO_ID");
		fecha	= rs.getString("FECHA");
		estado	= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection conn, String bajaId, String pasoId) throws SQLException{
		
		PreparedStatement ps= null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, PASO_ID," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO" +
					" FROM ENOC.BAJA_ALUMPASO" + 
					" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
					" AND PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, bajaId);
			ps.setString(2, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}