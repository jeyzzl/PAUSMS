//Clase para la tabla de Alum_Academico
package aca.voto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VotoEventoUtil{
	
	public VotoEvento mapeaRegId( Connection conn, String eventoId ) throws SQLException, IOException{
		VotoEvento fedEvento = new VotoEvento();
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO, POBLACION"
	 				+ " FROM ENOC.VOTO_EVENTO"
	 				+ " WHERE EVENTO_ID = ?"); 
	 		ps.setString(1, eventoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			
	 			fedEvento.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.VotoEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return fedEvento;
 	} 	
		
	public ArrayList<VotoEvento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<VotoEvento> lisEvento= new ArrayList<VotoEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT"
					+ " EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO, POBLACION"
					+ " FROM ENOC.VOTO_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VotoEvento evento = new VotoEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.VotoEventoUtil|getListAll-|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	
	public ArrayList<VotoEvento> getListEventosActuales(Connection conn, String orden ) throws SQLException{
		
		ArrayList<VotoEvento> lisEvento= new ArrayList<VotoEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT"
					+ " EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO, POBLACION"
					+ " FROM ENOC.VOTO_EVENTO"
					+ " WHERE now() BETWEEN FECHA_INI AND FECHA_FIN "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VotoEvento evento = new VotoEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.VotoEventoUtil|getListEventosActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public boolean eventoPorFecha(Connection conn, String eventoId ) throws SQLException{
		
 		PreparedStatement ps 	= null;
 		ResultSet rs			= null;
 		boolean ok = false;
 		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.VOTO_EVENTO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999') AND now() BETWEEN FECHA_INI AND FECHA_FIN");
			
			ps.setString(1, eventoId);			
			rs = ps.executeQuery();
			if (rs.next())
 				ok = true;	
 			else
 				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.VotoEventoUtil|eventoPorFecha|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

}