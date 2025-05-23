//Clase para la tabla de Alum_Academico
package aca.graduacion;

import java.sql.*;
import java.util.ArrayList;

public class AlumEventoUtil{
	
	public boolean insertReg(Connection conn, AlumEvento evento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_EVENTO"+ 
				"(EVENTO_ID, NOMBRE_EVENTO, FECHA_EVENTO)"+
				" VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'))");
					
			ps.setInt(1, Integer.parseInt(evento.getEventoId()));
			ps.setString(2, evento.getNombreEvento());
			ps.setString(3, evento.getFechaEvento());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEvento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumEvento evento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_EVENTO "+ 
				"SET"+
				" EVENTO_ID = ?,"+
				" NOMBRE_EVENTO = ?,"+
				" FECHA_EVENTO = TO_DATE(?,'DD/MM/YYYY')"+
				" WHERE EVENTO_ID = ? ");
			ps.setInt(1, Integer.parseInt(evento.getEventoId()));
			ps.setString(2, evento.getNombreEvento());
			ps.setString(3, evento.getFechaEvento());
			ps.setInt(4, Integer.parseInt(evento.getEventoId()));
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEvento|updateReg|:"+ex);		
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
			System.out.println("Error - aca.graduacion.AlumEvento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumEvento mapeaRegId( Connection conn, String evento_Id ) throws SQLException{
		
		AlumEvento evento = new AlumEvento();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT"+
				" EVENTO_ID, NOMBRE_EVENTO, FECHA_EVENTO"+
				" FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = ?"); 
			ps.setInt(1, Integer.parseInt(evento_Id));
			
			rs = ps.executeQuery();
			if (rs.next()){
				evento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEvento|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evento;
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
			System.out.println("Error - aca.graduacion.AlumEvento|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(EVENTO_ID)+1,1) AS MAXIMO"+
				" FROM ENOC.ALUM_EVENTO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = String.valueOf(rs.getInt("MAXIMO"));			
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEvento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
		
	public ArrayList<AlumEvento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEvento> lisEvento= new ArrayList<AlumEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+
				" EVENTO_ID, NOMBRE_EVENTO, FECHA_EVENTO"+
				" FROM ENOC.ALUM_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEvento evento = new AlumEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
}