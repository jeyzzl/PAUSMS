package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaUnidadActividad {
	private String cursoCargaId;	
	private String actividadId;
	private String actividadNombre;
	private String comentario;
	private String valor;
	private String orden;
		
	public CargaUnidadActividad(){
	    cursoCargaId       = "";	
		actividadId        = "";
		actividadNombre    = "";
		comentario         = "";
		valor              = "";
		orden              = "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getActividadId() {
		return actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getActividadNombre() {
		return actividadNombre;
	}

	public void setActividadNombre(String actividadNombre) {
		this.actividadNombre = actividadNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		actividadId 		= rs.getString("ACTIVIDAD_ID");
		actividadNombre 	= rs.getString("ACTIVIDAD_NOMBRE");
		comentario 			=  rs.getString("COMENTARIO");
		valor 			    =  rs.getString("VALOR");
		orden 			    =  rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String actividadId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, " +
					" ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? AND ACTIVIDAD_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, actividadId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}