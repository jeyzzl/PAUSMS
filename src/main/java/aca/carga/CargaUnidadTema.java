package aca.carga;

import java.sql.*;

public class CargaUnidadTema {
	private String cursoCargaId;
	private String temaId;	
	private String temaNombre;
	private String fecha;
	private String orden;

	public CargaUnidadTema(){
		cursoCargaId = "";
		temaId		 = "";
		temaNombre   = "";
		fecha	     = "";
		orden        = "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the temaId
	 */
	public String getTemaId() {
		return temaId;
	}

	/**
	 * @param temaId the temaId to set
	 */
	public void setTemaId(String temaId) {
		this.temaId = temaId;
	}

	/**
	 * @return the temaNombre
	 */
	public String getTemaNombre() {
		return temaNombre;
	}

	/**
	 * @param temaNombre the temaNombre to set
	 */
	public void setTemaNombre(String temaNombre) {
		this.temaNombre = temaNombre;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		temaId	 			= rs.getString("TEMA_ID");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		temaNombre			= rs.getString("TEMA_NOMBRE");
		fecha				= rs.getString("FECHA");
		orden				= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String temaId) throws SQLException{
		CargaUnidadTema cargaUnidadTema = new CargaUnidadTema();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, " +
					" ORDEN FROM ENOC.CARGA_UNIDAD_TEMA WHERE CURSO_CARGA_ID = ? AND TEMA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,temaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				cargaUnidadTema.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
}