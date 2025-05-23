package aca.exa;

import java.sql.*;
import java.util.ArrayList;

public class ExaEventoUtil{
	
	public boolean insertReg(Connection conn, ExaEvento exaEvento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_EVENTO(EVENTO_ID, NOMBRE, LUGAR, FECHAEVENTO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEVENTO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaEvento.getEventoId());
			ps.setString(2, exaEvento.getNombre());
			ps.setString(3, exaEvento.getLugar());
			ps.setString(4, exaEvento.getFechaEvento());
			ps.setString(5, exaEvento.getFechaAct());
			ps.setString(6, exaEvento.getEliminado());
			ps.setString(7, exaEvento.getIdEvento());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean update(Connection conn, ExaEvento exaEvento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_EVENTO"+ 
				" SET NOMBRE = ?, " +
				" LUGAR = ?, " +
				" FECHAEVENTO = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHAACTUALIZACION = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				" ELIMINADO = TO_NUMBER(?,'9') , " +
				" IDEVENTO = ? " +
				" WHERE EVENTO_ID = TO_NUMBER(?,'99999999')");
			
			ps.setString(1, exaEvento.getNombre());
			ps.setString(2, exaEvento.getLugar());
			ps.setString(3, exaEvento.getFechaEvento());
			ps.setString(4, exaEvento.getFechaAct());
			ps.setString(5, exaEvento.getEliminado());
			ps.setString(6, exaEvento.getIdEvento());
			ps.setString(7, exaEvento.getEventoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean eliminar(Connection conn, String eventoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_EVENTO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE EVENTO_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaEvento mapeaRegIdEvento( Connection conn, String eventoId) throws SQLException{
		ExaEvento exaEvento = new ExaEvento();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID, NOMBRE, LUGAR, " +
					" TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTO "+
				"FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,eventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEvento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEvento;
	}
	
	public boolean existeReg(Connection conn, String eventoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1, eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(EVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_EVENTO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ExaEvento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean tieneAlumnos(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.EXA_EVENTO WHERE EVENTO_ID IN " +
					" (SELECT EVENTO_ID FROM ENOC.EXA_ALUMEVENTO WHERE ELIMINADO != 1) "); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|tieneAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public static String getNombre(Connection conn, String eventoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre 			= "x";
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = ?");
			ps.setString(1,eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public ArrayList<ExaEvento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ExaEvento> lis		= new ArrayList<ExaEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, NOMBRE, LUGAR, TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO,FECHAEVENTO AS FECHA, FECHAACTUALIZACION, ELIMINADO, IDEVENTO " +
					" FROM ENOC.EXA_EVENTO "+
					" WHERE ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEvento evento = new ExaEvento();
				evento.mapeaReg(rs);
				lis.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<ExaEvento> getEventosAlumnos(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<ExaEvento> lis	= new ArrayList<ExaEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, NOMBRE, LUGAR, TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTO " +
					" FROM ENOC.EXA_EVENTO " +
					" WHERE EVENTO_ID IN (SELECT IDEVENTO FROM ENOC.EXA_ALUMEVENTO WHERE MATRICULA = '"+codigoPersonal+"')"+
					" AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEvento evento = new ExaEvento();
				evento.mapeaReg(rs);
				lis.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|getEventosAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
		
}