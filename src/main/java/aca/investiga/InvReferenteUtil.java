package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class InvReferenteUtil {
	
	public boolean insertReg(Connection conn, InvReferente ref ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_REFERENTE(CODIGO_ID, CARRERA_ID) "
					+ "VALUES(?, ?)"); 
			
			ps.setString(1, ref.getCodigoId());
			ps.setString(2, ref.getCarreraId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvReferente|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, InvReferente ref ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_REFERENTE WHERE CODIGO_ID = ? AND CARRERA_ID = ? "); 
			ps.setString(1, ref.getCodigoId());
			ps.setString(2, ref.getCarreraId());
		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvReferente|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigoId, String carreraId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_REFRENTE WHERE CODIGO_ID = ? AND CARRERA_ID = ? "); 
			
			ps.setString(1,codigoId);
			ps.setString(2,carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvReferente|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getCarrera(Connection conn, String codigoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID "+
					"FROM ENOC.INV_REFERENTE WHERE CODIGO_ID = ?"); 
			ps.setString(1, codigoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("CARRERA_ID");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvReferente|getCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	

	public ArrayList<InvReferente> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<InvReferente> listProyectos	= new ArrayList<InvReferente>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_ID, CARRERA_ID FROM ENOC.INV_REFERENTE "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvReferente proyecto = new InvReferente();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvReferenteUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}	
}