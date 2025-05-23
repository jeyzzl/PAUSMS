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
public class MapaNuevoUnidadUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoUnidad mapaNuevoUnidad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_UNIDAD"
					+ " (CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN)"
					+ " VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), ?, ?, ?, ?, TO_NUMBER(?,'99.99'))");
					
			ps.setString(1, mapaNuevoUnidad.getCursoId());
			ps.setString(2, mapaNuevoUnidad.getUnidadId());
			ps.setString(3, mapaNuevoUnidad.getNombre());
			ps.setString(4, mapaNuevoUnidad.getTiempo());
			ps.setString(5, mapaNuevoUnidad.getTemas());
			ps.setString(6, mapaNuevoUnidad.getAccionDocente());
			ps.setString(7, mapaNuevoUnidad.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoUnidad mapaNuevoUnidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_UNIDAD"
					+ " SET NOMBRE = ?,"
					+ " TIEMPO = ?,"
					+ " TEMAS = ?,"
					+ " ACCION_DOCENTE = ?,"
					+ " ORDEN = TO_NUMBER(?,'99.99')"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')");			
			ps.setString(1, mapaNuevoUnidad.getNombre());
			ps.setString(2, mapaNuevoUnidad.getTiempo());			
			ps.setString(3, mapaNuevoUnidad.getTemas());
			ps.setString(4, mapaNuevoUnidad.getAccionDocente());
			ps.setString(5, mapaNuevoUnidad.getOrden());
			ps.setString(6, mapaNuevoUnidad.getCursoId());
			ps.setString(7, mapaNuevoUnidad.getUnidadId());
			
			if (ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String unidadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		//System.out.println("Borrando la unidad:"+cursoId+":"+unidadId);
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_UNIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
									   " AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegCurso(Connection conn, String cursoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_UNIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, cursoId);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|deleteRegCurso|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoUnidad mapeaRegId( Connection conn, String cursoId, String unidadId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaNuevoUnidad mapaNuevoUnidad = new MapaNuevoUnidad();
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN"
					+ " FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoUnidad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return mapaNuevoUnidad;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String unidadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoId) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(UNIDAD_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String numUnidades(Connection conn, String cursoId) throws SQLException{
		String cantidad		 	= "-";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(UNIDAD_ID) AS NUM FROM ENOC.MAPA_NUEVO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				cantidad = rs.getString("NUM")==null?"0":rs.getString("NUM");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|numUnidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public static String getNombre(Connection conn, String cursoId, String unidadId) throws SQLException{
		String cantidad		 	= "-";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999') AND UNIDAD_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				cantidad = rs.getString("NOMBRE")==null?"0":rs.getString("NOMBRE");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public ArrayList<MapaNuevoUnidad> getListCurso(Connection conn, String cursoId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoUnidad> listUnidades		= new ArrayList<MapaNuevoUnidad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN"
					+ " FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER('"+cursoId+"', '9999999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoUnidad unidades = new MapaNuevoUnidad();
				unidades.mapeaReg(rs);
				listUnidades.add(unidades);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoUnidadUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listUnidades;
	}
}