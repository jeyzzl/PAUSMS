/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class MapaNuevoActividadUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoActividad mapaNuevoActividad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_ACTIVIDAD"+ 
				"(CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
				" DESCRIPCION, CRITERIO, TIPO) "+
				"VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), TO_NUMBER(?, '99'), ?,"+
				" ?, ?, ?)");	
					
			ps.setString(1, mapaNuevoActividad.getCursoId());
			ps.setString(2, mapaNuevoActividad.getUnidadId());
			ps.setString(3, mapaNuevoActividad.getActividadId());
			ps.setString(4, mapaNuevoActividad.getObjetivo());
			ps.setString(5, mapaNuevoActividad.getDescripcion());
			ps.setString(6, mapaNuevoActividad.getCriterio());
			ps.setString(7, mapaNuevoActividad.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoActividad mapaNuevoActividad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_ACTIVIDAD" + 
													 	 " SET OBJETIVO = ?, " +
														 " DESCRIPCION = ?, " +
														 " CRITERIO = ?," +
														 " TIPO = ?" +
														 " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
														 " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
														 " AND ACTIVIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,  mapaNuevoActividad.getObjetivo());
			ps.setString(2,  mapaNuevoActividad.getDescripcion());			
			ps.setString(3,  mapaNuevoActividad.getCriterio());
			ps.setString(4,  mapaNuevoActividad.getTipo());
			ps.setString(5,  mapaNuevoActividad.getCursoId());
			ps.setString(6,  mapaNuevoActividad.getUnidadId());
			ps.setString(7,  mapaNuevoActividad.getActividadId());
			
			if (ps.executeUpdate()== 1){
				ok = true;								
			}else{
				ok = false;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String unidadId, String actividadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
									   " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
									   " AND ACTIVIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,  cursoId);
			ps.setString(2,  unidadId);
			ps.setString(3,  actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoActividad mapeaRegId( Connection conn, String cursoId, String unidadId, String actividadId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaNuevoActividad mapaNuevoActividad = new MapaNuevoActividad();
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
					" DESCRIPCION, CRITERIO, TIPO" +
					" FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
				   	" AND ACTIVIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoActividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return mapaNuevoActividad;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String unidadId, String actividadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
			   		" AND ACTIVIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, actividadId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoId, String unidadId) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ACTIVIDAD_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;
	}
	
	public ArrayList<MapaNuevoActividad> getListUnidad(Connection conn, String cursoId, String unidadId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoActividad> listActividades		= new ArrayList<MapaNuevoActividad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
				" DESCRIPCION, CRITERIO, TIPO" +
				" FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
				" WHERE CURSO_ID = TO_NUMBER('"+cursoId+"', '9999999')" +
				" AND UNIDAD_ID = TO_NUMBER('"+unidadId+"', '99') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoActividad actividades = new MapaNuevoActividad();
				actividades.mapeaReg(rs);
				listActividades.add(actividades);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listActividades;	
	}
}