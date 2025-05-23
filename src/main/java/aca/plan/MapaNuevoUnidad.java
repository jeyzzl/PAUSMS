/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class MapaNuevoUnidad {
	private String cursoId;
	private String unidadId;
	private String nombre;
	private String tiempo;
	private String temas;
	private String accionDocente;
	private String orden;
	
	public MapaNuevoUnidad(){
		cursoId				= "";
		unidadId			= "";
		nombre				= "";
		tiempo				= "";
		temas				= "";
		accionDocente		= "";
		orden 				= "0";
	}
	
	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getTemas() {
		return temas;
	}

	public void setTemas(String temas) {
		this.temas = temas;
	}

	public String getAccionDocente() {
		return accionDocente;
	}

	public void setAccionDocente(String accionDocente) {
		this.accionDocente = accionDocente;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		cursoId				= rs.getString("CURSO_ID")==null?"0":rs.getString("CURSO_ID");
		unidadId			= rs.getString("UNIDAD_ID")==null?"0":rs.getString("UNIDAD_ID");
		nombre				= rs.getString("NOMBRE")==null?"-":rs.getString("NOMBRE");
		tiempo				= rs.getString("TIEMPO")==null?"-":rs.getString("TIEMPO");		
		temas 				= rs.getString("TEMAS")==null?"-":rs.getString("TEMAS");					
		accionDocente		= rs.getString("ACCION_DOCENTE")==null?"-":rs.getString("ACCION_DOCENTE");
		orden				= rs.getString("ORDEN")==null?"0":rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String unidadId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN"
					+ " FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}