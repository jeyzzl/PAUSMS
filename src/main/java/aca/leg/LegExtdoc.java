package aca.leg;

import java.sql.*;

public class LegExtdoc {
	public String codigo;
	public String idDocumento;
	public String fechaVence;
	public String numDocto;
	public String fecha;
	public String fechaTramite;
	public String estado;
	
	public LegExtdoc(){
		codigo			= "";
		idDocumento		= "";
		fechaVence		= "";
		numDocto		= "";
		fecha			= "";
		fechaTramite	= "";
		estado 			= "";		
	}

	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getFechaVence() {
		return fechaVence;
	}

	public void setFechaVence(String fechaVence) {
		this.fechaVence = fechaVence;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}		
	
	/**
	 * @return the numDocto
	 */
	public String getNumDocto() {
		return numDocto;
	}

	/**
	 * @param numDocto the numDocto to set
	 */
	public void setNumDocto(String numDocto) {
		this.numDocto = numDocto;
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
	 * @return the fechaTramite
	 */
	public String getFechaTramite() {
		return fechaTramite;
	}

	/**
	 * @param fechaTramite the fechaTramite to set
	 */
	public void setFechaTramite(String fechaTramite) {
		this.fechaTramite = fechaTramite;
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

	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigo			= rs.getString("CODIGO");
		idDocumento		= rs.getString("IDDOCUMENTO");
		fechaVence		= rs.getString("FECHA_VENCE");
		numDocto		= rs.getString("NUM_DOCTO");
		fecha			= rs.getString("FECHA");
		fechaTramite	= rs.getString("FECHA_TRAMITE");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection conn, String codigo, String idDocumento)	throws SQLException{
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, IDDOCUMENTO," +
					" TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE," +
					" NUM_DOCTO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE," +
					" ESTADO"+
					" FROM ENOC.LEG_EXTDOCTOS " + 
					" WHERE CODIGO = ?" +
					" AND IDDOCUMENTO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, idDocumento);
			rs=ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}