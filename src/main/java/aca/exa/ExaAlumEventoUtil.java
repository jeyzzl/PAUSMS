package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaAlumEventoUtil {
	
	public boolean insertReg(Connection conn, ExaAlumEvento exaAlumEvento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_ALUMEVENTO(ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? , TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaAlumEvento.getAlumEventoId());
			ps.setString(2, exaAlumEvento.getMatricula());
			ps.setString(3, exaAlumEvento.getIdEvento());
			ps.setString(4, exaAlumEvento.getFechaActualizacion());
			ps.setString(5, exaAlumEvento.getEliminado());
			ps.setString(6, exaAlumEvento.getIdEventoAsistido());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String alumEventoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_ALUMEVENTO"+ 
				" SET ELIMINADO = '1' "+
				" WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999') ");
		
			ps.setString(1, alumEventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaAlumEvento mapeaRegId( Connection conn, String alumEventoId) throws SQLException{
		ExaAlumEvento exaAlumEvento = new ExaAlumEvento();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO "+
				"FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,alumEventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaAlumEvento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaAlumEvento;
	}
	
	public boolean existeReg(Connection conn, String alumEventoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1, alumEventoId);		
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeAlumno(Connection conn, String eventoId, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_ALUMEVENTO WHERE IDEVENTO ='"+eventoId+"' AND MATRICULA = '"+matricula+"' AND ELIMINADO = 0 "); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ALUMEVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_ALUMEVENTO WHERE ELIMINADO !=1 AND MATRICULA = ?");
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ALUMEVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_ALUMEVENTO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaAlumEvento> getAlumnosEvento(Connection conn, String idevento, String orden) throws SQLException{
		
		ArrayList<ExaAlumEvento> list		= new ArrayList<ExaAlumEvento>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO " +
					" FROM ENOC.EXA_ALUMEVENTO" +
					" WHERE IDEVENTO = '"+idevento+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaAlumEvento obj = new ExaAlumEvento();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|getAlumnosEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<ExaAlumEvento> getAlumEvento(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaAlumEvento> list		= new ArrayList<ExaAlumEvento>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO " +
					" FROM ENOC.EXA_ALUMEVENTO" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaAlumEvento obj = new ExaAlumEvento();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|getAlumEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
