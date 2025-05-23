//Clase  para la tabla Materias_Insc
package aca.cred;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CredEventoUtil{	
	
	public boolean insertReg(Connection conn, CredEvento evento ) throws Exception{
		
		PreparedStatement ps = null;
		boolean ok = false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CRED_EVENTO(EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL) " +
				"VALUES(TO_NUMBER(?,'999'),?,?) ");
			
			ps.setString(1, evento.getEventoId());
			ps.setString(2, evento.getEventoNombre());			
			ps.setString(3, evento.getCodigoInicial());
					
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CredEvento evento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CRED_EVENTO " + 
				"SET  EVENTO_NOMBRE= ?, CODIGO_INICIAL=? " +
				"WHERE EVENTO_ID= TO_NUMBER(?,999) ");
			ps.setString(1, evento.getEventoNombre());
			ps.setString(2, evento.getCodigoInicial());
			ps.setString(3, evento.getEventoId());
			
						
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String eventoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CRED_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,999)  ");
			
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CredEvento mapeaRegId(Connection con, String eventoId) throws SQLException{
		
		CredEvento evento = new CredEvento();		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL FROM ENOC.CRED_EVENTO " +					
					"WHERE EVENTO_ID = TO_NUMBER(?,999) ");				
			ps.setString(1, eventoId);
	
			rs = ps.executeQuery();
			
			if(rs.next()){
				evento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,999)  "); 
			ps.setString(1, eventoId);

			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String eventoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR((MAX(TO_NUMBER(CODIGO_PERSONAL,'9999999')+1)) AS MAXIMO FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,999) "  ); 
			ps.setString(1, eventoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public ArrayList<CredEvento> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CredEvento> credEvento		= new ArrayList<CredEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL" +
					" FROM ENOC.CRED_EVENTO "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				CredEvento credEventoU = new CredEvento();
				credEventoU.mapeaReg(rs);
				credEvento.add(credEventoU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return credEvento;
	}
	
	public ArrayList<CredEvento> getListEvento(Connection conn, String eventoId) throws SQLException{
		
		ArrayList<CredEvento> credEvento		= new ArrayList<CredEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL" +
					" FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = "+eventoId;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				CredEvento credEventoU = new CredEvento();
				credEventoU.mapeaReg(rs);
				credEvento.add(credEventoU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return credEvento;
	}
	
	public HashMap<String,CredEvento> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CredEvento> map = new HashMap<String,CredEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL "+
				"FROM ENOC.CRED_EVENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CredEvento obj = new CredEvento();
				obj.mapeaReg(rs);
				llave = obj.getEventoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}