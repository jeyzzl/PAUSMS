package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntReporte {
	
	private String reporteId;
	private String reporteNombre;
	

	/**
	 * @return the reporteId
	 */
	public String getReporteId() {
		return reporteId;
	}


	/**
	 * @param reporteId the reporteId to set
	 */
	public void setReporteId(String reporteId) {
		this.reporteId = reporteId;
	}


	/**
	 * @return the reporteNombre
	 */
	public String getReporteNombre() {
		return reporteNombre;
	}


	/**
	 * @param reporteNombre the reporteNombre to set
	 */
	public void setReporteNombre(String reporteNombre) {
		this.reporteNombre = reporteNombre;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		reporteId 			= rs.getString("REPORTE_ID");
		reporteNombre 		= rs.getString("REPORTE_NOMBRE");		
	}
	
	public void mapeaRegId(Connection con, String reporteId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')");
			 
			ps.setString(1, reporteId);			
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}