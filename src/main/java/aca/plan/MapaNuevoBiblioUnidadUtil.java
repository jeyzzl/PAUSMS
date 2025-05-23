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
public class MapaNuevoBiblioUnidadUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_BIBLIO_UNIDAD"+ 
				"(CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
				" ESPECIFICACION) "+
				"VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), TO_NUMBER(?, '999'), TO_NUMBER(?, '99')," +
				" ?)");	
					
			ps.setString(1, mapaNuevoBiblioUnidad.getCursoId());
			ps.setString(2, mapaNuevoBiblioUnidad.getUnidadId());
			ps.setString(3, mapaNuevoBiblioUnidad.getBibliografiaId());
			ps.setString(4, mapaNuevoBiblioUnidad.getId());
			ps.setString(5, mapaNuevoBiblioUnidad.getEspecificacion());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
													 	 " SET ESPECIFICACION = ?" +
														 " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
														 " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
														 " AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
														 " AND ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, mapaNuevoBiblioUnidad.getEspecificacion());
			ps.setString(2, mapaNuevoBiblioUnidad.getCursoId());
			ps.setString(3, mapaNuevoBiblioUnidad.getUnidadId());
			ps.setString(4, mapaNuevoBiblioUnidad.getBibliografiaId());
			ps.setString(5, mapaNuevoBiblioUnidad.getId());
			
			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
									   " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
									   " AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
									   " AND ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, mapaNuevoBiblioUnidad.getCursoId());
			ps.setString(2, mapaNuevoBiblioUnidad.getUnidadId());
			ps.setString(3, mapaNuevoBiblioUnidad.getBibliografiaId());
			ps.setString(4, mapaNuevoBiblioUnidad.getId());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String bibliografiaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
									   " AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1,  cursoId);
			ps.setString(2,  bibliografiaId);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg(cursoId, bibliografiaId)|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1,  cursoId);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg(cursoId)|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoBiblioUnidad mapeaRegId( Connection conn, String cursoId, String unidadId, String bibliografiaId, String id) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad = new MapaNuevoBiblioUnidad();
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
					" ESPECIFICACION" +
					" FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')");
				
				ps.setString(1,  cursoId);
				ps.setString(2,  unidadId);
				ps.setString(3,  bibliografiaId);
				ps.setString(4,  id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoBiblioUnidad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaNuevoBiblioUnidad;
	}
	
	public boolean existeReg(Connection conn, MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, mapaNuevoBiblioUnidad.getCursoId());
			ps.setString(2, mapaNuevoBiblioUnidad.getUnidadId());
			ps.setString(3, mapaNuevoBiblioUnidad.getBibliografiaId());
			ps.setString(4, mapaNuevoBiblioUnidad.getId());
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoId, String unidadId, String bibliografiaId ) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1,  cursoId);
			ps.setString(2,  unidadId);
			ps.setString(3,  bibliografiaId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;
	}
	
	public ArrayList<MapaNuevoBiblioUnidad> getListCursoUnidad(Connection conn, String cursoId, String unidadId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoBiblioUnidad> listBibliografia		= new ArrayList<MapaNuevoBiblioUnidad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
					" ESPECIFICACION" +
				" FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
				" WHERE CURSO_ID = TO_NUMBER('"+cursoId+"', '9999999')" +
				" AND UNIDAD_ID = TO_NUMBER('"+unidadId+"', '99') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoBiblioUnidad bibliografia = new MapaNuevoBiblioUnidad();
				bibliografia.mapeaReg(rs);
				listBibliografia.add(bibliografia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|getListCursoUnidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listBibliografia;	
	}
}