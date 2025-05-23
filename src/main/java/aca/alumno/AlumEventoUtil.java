//Clase para la tabla de Alum_Academico
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumEventoUtil{
		
	public boolean insertReg(Connection conn, AlumEvento alumEvento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_EVENTO"+ 
				"(EVENTO_ID, EVENTO_NOMBRE, FECHA)"+
				" VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'))");
					
			ps.setInt(1, Integer.parseInt(alumEvento.getEventoId()));
			ps.setString(2, alumEvento.getEventoNombre());
			ps.setString(3, alumEvento.getFecha());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumEvento alumEvento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_EVENTO "+ 
				"SET"+
				" EVENTO_ID = ?,"+
				" EVENTO_NOMBRE = ?,"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY')"+
				" WHERE EVENTO_ID = ? ");
			ps.setInt(1, Integer.parseInt(alumEvento.getEventoId()));
			ps.setString(2, alumEvento.getEventoNombre());
			ps.setString(3, alumEvento.getFecha());
			ps.setInt(4, Integer.parseInt(alumEvento.getEventoId()));
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String eventoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_EVENTO"+ 
				" WHERE EVENTO_ID = ?");
			ps.setInt(1, Integer.parseInt(eventoId));
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumEvento mapeaRegId( Connection conn, String eventoId ) throws SQLException{
		
		AlumEvento alumEvento = new AlumEvento();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
				" FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = ?"); 
			ps.setInt(1, Integer.parseInt(eventoId));
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumEvento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumEvento;
	}
	
	public boolean existeReg(Connection conn, String eventoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EVENTO "+ 
				"WHERE EVENTO_ID = ?");
			ps.setInt(1, Integer.parseInt(eventoId));
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(EVENTO_ID)+1, 1) AS MAXIMO"+
				" FROM ENOC.ALUM_EVENTO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = String.valueOf(rs.getInt("MAXIMO"));			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public static String getEventoNombre(Connection conn, String eventoId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String eventoNombre		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT EVENTO_NOMBRE FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = ?"); 
			ps.setString(1, eventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				eventoNombre = rs.getString("EVENTO_NOMBRE");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|getEventoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return eventoNombre;
	}
	
	public ArrayList<AlumEvento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEvento> lisEvento= new ArrayList<AlumEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
				" FROM ENOC.ALUM_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEvento evento = new AlumEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		return lisEvento;
	}
	
	public HashMap<String,String> getMapEventos(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT "+
				" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
				" FROM ENOC.ALUM_EVENTO  "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("EVENTO_ID"), rs.getString("FECHA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|getMapEventos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
}