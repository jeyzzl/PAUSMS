package aca.conva;

import java.sql.*;

public class ConvHistorial {
	private String convalidacionId;
	private String folio;
	private String fecha;
	private String usuario;
	private String estado;
	
	public ConvHistorial(){
		convalidacionId	= "";
		folio			= "";
		fecha			= "";
		usuario			= "";
		estado			= "";
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
	 * @return Returns the convalidacionId.
	 */
	public String getConvalidacionId() {
		return convalidacionId;
	}

	/**
	 * @param convalidacionId The convalidacionId to set.
	 */
	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
	}

	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return Returns the usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		convalidacionId 	= rs.getString("CONVALIDACION_ID");
		folio				= rs.getString("FOLIO");
		fecha 				= rs.getString("FECHA");
		usuario 			= rs.getString("USUARIO");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String convalidacionId, String estado ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, ESTADO "+
				"FROM ENOC.CONV_HISTORIAL WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " + 
				"AND ESTADO = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, estado);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
	}
}