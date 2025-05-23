package aca.cultural;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import aca.cultural.CompEvento;

public class CompEventoUtil{
	
	public boolean insertRegByte(Connection conn, CompEvento compEvento ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.COMP_EVENTO"+ 
				"(EVENTO_ID, EVENTO_NOMBRE, FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO) "+
				"VALUES( TO_NUMBER(?,'99'),?, TO_DATE(?, 'DD/MM/YYYY HH24:MI'), ?, TO_NUMBER(?,'999'),?,?)");
			
			ps.setString(1, compEvento.getEventoId());
			ps.setString(2, compEvento.getEventoNombre());			
			ps.setString(3, compEvento.getFecha());
			ps.setString(4, compEvento.getDescripcion());
			ps.setString(5, compEvento.getCapacidad());
			ps.setString(6, compEvento.getLugar());
			ps.setString(7, compEvento.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|insertRegByte|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CompEvento compEvento ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COMP_EVENTO "+ 
				"SET EVENTO_NOMBRE = ?, "+
				"FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), "+
				"DESCRIPCION = ?, "+
				"CAPACIDAD = TO_NUMBER(?,'999'),"+
				"LUGAR = ?,"+
				"ESTADO = ?"+
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, compEvento.getEventoNombre());			
			ps.setString(2, compEvento.getFecha());
			ps.setString(3, compEvento.getDescripcion());
			ps.setString(4, compEvento.getCapacidad());
			ps.setString(5, compEvento.getLugar());
			ps.setString(6, compEvento.getEstado());
			ps.setString(7, compEvento.getEventoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String eventoId ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') ");
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteTodo(Connection conn,String eventos ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_EVENTO WHERE EVENTO_ID IN ("+eventos+") ");
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|deleteTodo|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CompEvento mapeaRegId( Connection conn, String eventoId) throws SQLException{
		CompEvento compEvento = new CompEvento();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO"+
			" FROM ENOC.COMP_EVENTO"+ 
			" WHERE EVENTO_ID = TO_NUMBER(?,'99')");
		ps.setString(1, eventoId);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			compEvento.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return compEvento;
	}

	public boolean existeReg(Connection conn, String eventoId) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COMP_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT TO_CHAR((MAX(TO_NUMBER(EVENTO_ID,'99')+1))) AS MAXIMO FROM ENOC.COMP_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,99) "  ); 
			ps.setString(1, eventoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(EVENTO_ID)+1 MAXIMO FROM ENOC.COMP_EVENTO"); 
			//ps.setString(1,  eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;		
	}
	
	public ArrayList<CompEvento> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CompEvento> lis		= new ArrayList<CompEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
					  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CompEvento obj = new CompEvento();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<CompEvento> getListActivos(Connection conn, String orden) throws SQLException{
				
				ArrayList<CompEvento> lis		= new ArrayList<CompEvento>();
				Statement st 		= conn.createStatement();
				ResultSet rs 		= null;
				String comando		= "";
			
			try{
				comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
						  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO WHERE ESTADO = '1' "+orden;	 
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CompEvento obj = new CompEvento();
					obj.mapeaReg(rs);
					lis.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.cultural.CompEventoUtil|getListActivos|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lis;
		}
	
	public static HashMap <String,CompEvento> getMapEvento(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CompEvento> map = new HashMap<String,CompEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		
		try{
			comando = " SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
					  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO "+orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CompEvento obj = new CompEvento();
				obj.mapeaReg(rs);
				map.put(obj.getEventoId(), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|getMapEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<CompEvento> getListReservados(Connection conn, String orden, String eventoId) throws SQLException{
			
			ArrayList<CompEvento> lista 		= new ArrayList<CompEvento>();
			Statement st 						= conn.createStatement();
			ResultSet rs 						= null;		
			String comando						= "";
			
			try{
				comando = "SELECT "+
						" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO"+
						" FROM ENOC.COMP_EVENTO"+ 
						" WHERE EVENTO_ID IN (SELECT '"+eventoId+"' FROM ENOC.COMP_REGISTRO) "+orden;
				rs = st.executeQuery(comando);
				while(rs.next()){
					CompEvento obj = new CompEvento();
					obj.mapeaReg(rs);
					lista.add(obj);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.cultural.CompEventoUtil|getListReservados| "+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return lista;
		}
	
}