// Clase Util para la tabla de Prerrequisitos
package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class ElectivaUtil{
	
	public boolean insertReg(Connection conn, MapaCursoElec mapaCursoElec ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_CURSO_ELEC"+ 
				"(CURSO_ID, FOLIO, CURSO_ELEC,CURSO_NOMBRE ) VALUES( ?, TO_NUMBER(?,'9999'), ?, ? )");	
					
			ps.setString(1, mapaCursoElec.getCursoId());
			ps.setString(2, mapaCursoElec.getFolio());	
			ps.setString(3, mapaCursoElec.getCursoElec());			
			ps.setString(4, mapaCursoElec.getCursoNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE  CURSO_ID=? AND FOLIO = TO_NUMBER(?,'9999') ");
			ps.setString(1, cursoId);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|deletetReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaCursoElec mapeaRegId( Connection conn, String cursoId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaCursoElec mapaCursoElec = new MapaCursoElec();
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID = ? AND FOLIO = TO_NUMBER(?,'9999') ");
				ps.setString(1, cursoId);
				ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaCursoElec.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return mapaCursoElec;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String cursoElec) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID = ?  AND CURSO_ELEC = ?");
			ps.setString(1, cursoId);
			ps.setString(2, cursoElec);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg2(Connection conn, String cursoId, String folio) throws SQLException{
		boolean 		  ok 		= false;
		ResultSet 		  rs		= null;
		PreparedStatement ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID=? AND FOLIO = TO_NUMBER(?,'9999')");
			ps.setString(1, cursoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FOLIO)+1 MAXIMO FROM ENOC.MAPA_CURSO_ELEC WHERE CURSO_ID = ? "); 
			ps.setString(1,  cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			if(maximo==null){maximo="1";}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public boolean updateReg(Connection conn, String cursoNombre, String cursoId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_CURSO_ELEC" + 
													 	 " SET CURSO_NOMBRE = ?" +
														 " WHERE FOLIO = TO_NUMBER(?, '9999')" +
														 " AND CURSO_ID = ?");
			
			ps.setString(1,  cursoNombre);
			ps.setString(2,  folio);
			ps.setString(3,  cursoId);
			
			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public ArrayList<MapaCursoElec> getLista(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCursoElec> lisElectiva	= new ArrayList<MapaCursoElec>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CURSO_ID,FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
					"WHERE ENOC.CURSO_PLAN(CURSO_ID) = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoElec electiva = new MapaCursoElec();
				electiva.mapeaReg(rs);
				lisElectiva.add(electiva);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisElectiva;
	}
		
	public ArrayList<MapaCursoElec> getLista2(Connection conn, String cursoId, String orden ) throws SQLException{
		
		ArrayList<MapaCursoElec> lisElectiva	= new ArrayList<MapaCursoElec>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando				= "";
		
		try{
			comando = "SELECT CURSO_ID,FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
					"WHERE CURSO_ID = '"+cursoId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoElec electiva = new MapaCursoElec();
				electiva.mapeaReg(rs);
				lisElectiva.add(electiva);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|getLista2|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisElectiva;
	}

	public ArrayList<MapaCursoElec> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaCursoElec> lisElectiva	= new ArrayList<MapaCursoElec>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
			
		try{
			comando = "SELECT CURSO_ID,FOLIO, CURSO_ID_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoElec electiva = new MapaCursoElec();
				electiva.mapeaReg(rs);
				lisElectiva.add(electiva);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisElectiva;
	}
}