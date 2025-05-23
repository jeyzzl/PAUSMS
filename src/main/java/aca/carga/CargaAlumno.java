//Bean del Catalogo Cargas
package  aca.carga;

import java.sql.*;

public class CargaAlumno{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String planId;
	private String fecha;
	private String estado;
	
	public CargaAlumno(){
		codigoPersonal	= "";
		cargaId			= "";
		bloqueId		= "0";
		planId			= "";
		fecha			= "";
		estado			= "1";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	public String getBloqueId() {
		return bloqueId;
	}
	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		cargaId		 	= rs.getString("CARGA_ID");
		bloqueId		= rs.getString("BLOQUE_ID");
		planId	 		= rs.getString("PLAN_ID");
		fecha			= rs.getString("FECHA");
		estado 			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NOMRE, EVALUCION_ID, "+
				"ACTIVIDAD_ID, USUARIO_ORIGEN,USUARIO_DESTINO, COMENTARIO, RUTA, ESTADO "+
				"FROM ENOC.CARGA_GRUPO_ARCHIVO WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}
