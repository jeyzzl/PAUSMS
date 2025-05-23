// CLASE DE LA TABLA COND_JUEZ

package aca.disciplina;

import java.sql.*;
import java.util.ArrayList;

public class CondJuezUtil {
	
	public boolean insertReg(Connection conn, CondJuez juez ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.COND_JUEZ(IDJUEZ, NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, juez.getIdJuez());
			ps.setString(2, juez.getNombre());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CondJuez juez ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COND_JUEZ "+ 
				"SET NOMBRE = ? "+
				"WHERE IDJUEZ = TO_NUMBER(?,'999')");
			ps.setString(1, juez.getNombre());
			ps.setString(2, juez.getIdJuez());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String idJuez ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COND_JUEZ "+ 
				"WHERE IDJUEZ = TO_NUMBER(?,'999')");
			ps.setString(1, idJuez);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CondJuez mapeaRegId(Connection con, String idJuez) throws SQLException{
		
		CondJuez juez = new CondJuez();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ " + 
					"WHERE IDJUEZ = TO_NUMBER(?,'999') ");				
			ps.setString(1,idJuez);
			rs = ps.executeQuery();
			
			if(rs.next()){
				juez.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return juez;
	}
	
	public boolean existeReg(Connection conn, String idJuez) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COND_JUEZ WHERE IDJUEZ = TO_NUMBER(?,'999') "); 
			ps.setString(1, idJuez);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(IDJUEZ)+1 MAXIMO FROM ENOC.COND_JUEZ"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String nombreJuez(Connection conn, String idJuez) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nombre 			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.COND_JUEZ WHERE IDJUEZ = TO_NUMBER(?,'99')"); 
			ps.setString(1,idJuez);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|nombreJuez|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CondJuez> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CondJuez> lisJuez 			= new ArrayList<CondJuez>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondJuez juez = new CondJuez();
				juez.mapeaReg(rs);
				lisJuez.add(juez);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuezUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisJuez;
	}
	
	public ArrayList<CondJuez> getLista(Connection conn, String idJuez, String orden ) throws SQLException{
		
		ArrayList<CondJuez> lisJuez 			= new ArrayList<CondJuez>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ WHERE IDJUEZ= '"+idJuez+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondJuez juez = new CondJuez();
				juez.mapeaReg(rs);
				lisJuez.add(juez);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuezUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisJuez;
	}
}