package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaEnlinea {
	private String cargaId;
	private String nombre;
	private String descripcion;
	private String fInicio;
	private String fFinal;	
	private String estado;
	
	public CargaEnlinea(){
		cargaId			= "";
		nombre			= "";
		descripcion		= "";
		fInicio			= "";
		fFinal			= "";
		estado			= "";		
	}
	
	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getFFinal() {
		return fFinal;
	}

	public void setFFinal(String final1) {
		fFinal = final1;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId 				= rs.getString("CARGA_ID");
		nombre	 				= rs.getString("NOMBRE");
		descripcion		 		= rs.getString("DESCRIPCION");
		fInicio	 				= rs.getString("F_INICIO");
		fFinal 					= rs.getString("F_FINAL");
		estado	 				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String cargaId) throws SQLException{		

		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CARGA_ID, NOMBRE, DESCRIPCION, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"ESTADO "+						
				"FROM ENOC.CARGA_ENLINEA WHERE CARGA_ID = ? ");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlinea|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	
	}
		
}