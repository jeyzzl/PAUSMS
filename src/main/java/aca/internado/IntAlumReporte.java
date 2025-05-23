package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntAlumReporte {
	
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String reporteId;
	private String comentario;
	private String usuario;
	private String cantidad;
	private String dormitorio;
	
	// Constructor
	public IntAlumReporte(){		
		codigoPersonal		= "0";
		folio				= "0";
		fecha				= "";
		reporteId			= "0";
		cantidad			= "0";
		comentario			= "-";
		usuario				= "";
		cantidad			= "";
		dormitorio			= "0";
	}
	

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}


	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}


	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
	public String getDormitorio() {
		return dormitorio;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		folio 			= rs.getString("FOLIO");
		fecha 			= rs.getString("FECHA");
		reporteId 		= rs.getString("REPORTE_ID");
		comentario 		= rs.getString("COMENTARIO");
		usuario 		= rs.getString("USUARIO");
		cantidad 		= rs.getString("CANTIDAD");
		dormitorio		= rs.getString("DORMITORIO");
	}
	
	public void mapeaRegId(Connection con, String CodigoPersonal, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO"
					+ " FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			 
			ps.setString(1, CodigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntAlumReporteUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}