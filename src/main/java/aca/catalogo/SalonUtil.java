// Clase Util para la tabla de Cat_Carrera
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SalonUtil{
	
	public boolean insertReg(Connection conn, CatSalon salon ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_SALON(EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO)"
					+ " VALUES( ?, ?, ?, TO_NUMBER(?,'999'), ?)");
			ps.setString(1, salon.getEdificioId());
			ps.setString(2, salon.getSalonId());
			ps.setString(3, salon.getNombreSalon());
			ps.setString(4, salon.getNumAlumnos());
			ps.setString(5, salon.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatSalon salon ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_SALON" 
				+" SET EDIFICIO_ID = ?,"
				+" NOMBRE_SALON = ?,"
				+" NUM_ALUMNOS = TO_NUMBER(?,'999'),"
				+" ESTADO = ?"
				+" WHERE SALON_ID = ? ");
			
			ps.setString(1, salon.getEdificioId());
			ps.setString(2, salon.getNombreSalon());
			ps.setString(3, salon.getNumAlumnos());
			ps.setString(4, salon.getEstado());
			ps.setString(5, salon.getSalonId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String salonId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_SALON WHERE SALON_ID = ? ");
			ps.setString(1, salonId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|deleteReg|: "+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatSalon mapeaRegId( Connection conn, String salonId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatSalon salon 			= new CatSalon(); 
		 
		try{
			ps = conn.prepareStatement("SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO"
					+ " FROM ENOC.CAT_SALON WHERE SALON_ID = ?"); 
			ps.setString(1, salonId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				salon.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return salon;
	}
	
	public boolean existeReg(Connection conn, String salonId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_SALON WHERE SALON_ID = ?"); 
			ps.setString(1, salonId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String edificioId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(TO_NUMBER(SUBSTR(SALON_ID,4,3),'999')+1),1) AS MAXIMO FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ?"); 
			ps.setString(1, edificioId);
			rs = ps.executeQuery();
			if (rs.next()){			
				if (rs.getString("MAXIMO").length()==1) maximo = "00"+rs.getString("MAXIMO");
				if (rs.getString("MAXIMO").length()==2) maximo = "0"+rs.getString("MAXIMO");
				if (rs.getString("MAXIMO").length()==3) maximo = rs.getString("MAXIMO");
				maximo = edificioId + "-" + maximo;				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public static String nombreSalon(Connection conn, String salonId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		String nombreSalon = "";
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_SALON FROM ENOC.CAT_SALON WHERE SALON_ID = ?");
			ps.setString(1, salonId);
			rs = ps.executeQuery();
			
			if (rs.next())
			nombreSalon = rs.getString("NOMBRE_SALON");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|nombreSalon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreSalon;
	}	
	
	public static String getEstado(Connection conn, String salonId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		String nombreSalon = "";
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.CAT_SALON WHERE SALON_ID = ?");
			ps.setString(1, salonId);
			rs = ps.executeQuery();
			
			if (rs.next())
			nombreSalon = rs.getString("ESTADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreSalon;
	}
	
	public static boolean existeEdificio(Connection conn, String facultadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.existeEdificio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public ArrayList<CatSalon> getLista(Connection conn, String edificioId, String orden ) throws SQLException{
		
		ArrayList<CatSalon> lisSalon	= new ArrayList<CatSalon>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO "+
			"FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = '"+edificioId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatSalon salon = new CatSalon();
				salon.mapeaReg(rs);
				lisSalon.add(salon);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
	}
	
	public ArrayList<CatSalon> getListaActivos(Connection conn, String edificioId, String orden ) throws SQLException{
		
		ArrayList<CatSalon> lisSalon		= new ArrayList<CatSalon>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO "
			+ " FROM ENOC.CAT_SALON "
			+ " WHERE EDIFICIO_ID = '"+edificioId+"'"
			+ " AND ESTADO = 'A' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatSalon salon = new CatSalon();
				salon.mapeaReg(rs);
				lisSalon.add(salon);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
	}
	
	public ArrayList<CatSalon> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatSalon> lisSalon		= new ArrayList<CatSalon>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO "+
				"FROM ENOC.CAT_SALON "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatSalon salon = new CatSalon();
				salon.mapeaReg(rs);
				lisSalon.add(salon);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
	}
	
	public HashMap<String,CatSalon> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatSalon> map = new HashMap<String,CatSalon>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO "+
				"FROM ENOC.CAT_SALON  "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatSalon obj = new CatSalon();
				obj.mapeaReg(rs);
				llave = obj.getSalonId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.SalonUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}