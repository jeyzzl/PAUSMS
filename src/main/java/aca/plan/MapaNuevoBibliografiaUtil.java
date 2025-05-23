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
public class MapaNuevoBibliografiaUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoBibliografia mapaNuevoBibliografia) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				"(CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
				" REFERENCIA)"+
				" VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '999'), ?, ?," +
				" ?)");	
					
			ps.setString(1, mapaNuevoBibliografia.getCursoId());
			ps.setString(2, mapaNuevoBibliografia.getBibliografiaId());
			ps.setString(3, mapaNuevoBibliografia.getBibliografia());
			ps.setString(4, mapaNuevoBibliografia.getTipo());
			ps.setString(5, mapaNuevoBibliografia.getReferencia());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoBibliografia mapaNuevoBibliografia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
													 	 " SET BIBLIOGRAFIA = ?," +
													 	 " TIPO = ?," +
													 	 " REFERENCIA = ?" +
														 " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
														 " AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1,  mapaNuevoBibliografia.getBibliografia());
			ps.setString(2,  mapaNuevoBibliografia.getTipo());
			ps.setString(3,  mapaNuevoBibliografia.getReferencia());
			ps.setString(4,  mapaNuevoBibliografia.getCursoId());
			ps.setString(5,  mapaNuevoBibliografia.getBibliografiaId());
			
			if (ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String bibliografiaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
									   " AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, bibliografiaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegCurso(Connection conn, String cursoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
									   " WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, cursoId);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|deleteRegCurso|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoBibliografia mapeaRegId( Connection conn, String cursoId, String bibliografiaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaNuevoBibliografia mapaNuevoBibliografia = new MapaNuevoBibliografia();
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
					" REFERENCIA" +
					" FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, bibliografiaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoBibliografia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return mapaNuevoBibliografia;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String bibliografiaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, bibliografiaId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(BIBLIOGRAFIA_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;
	}
	
	public ArrayList<MapaNuevoBibliografia> getListCurso(Connection conn, String cursoId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoBibliografia> listBibliografia		= new ArrayList<MapaNuevoBibliografia>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
					" REFERENCIA" +
				" FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
				" WHERE CURSO_ID = TO_NUMBER('"+cursoId+"', '9999999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoBibliografia bibliografia = new MapaNuevoBibliografia();
				bibliografia.mapeaReg(rs);
				listBibliografia.add(bibliografia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listBibliografia;	
	}
}