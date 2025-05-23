// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;

public class CargaPermisoUtil{
	
	public boolean insertReg(Connection conn, CargaPermiso permiso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_PERMISO"+ 
				"(CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO ) VALUES( ?, ?, ?, ? )");
			ps.setString(1, permiso.getCargaId());
			ps.setString(2, permiso.getCarreraId());
			ps.setString(3, permiso.getRecuperacion());
			ps.setString(4, permiso.getUsuario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn, CargaPermiso permiso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_PERMISO"+ 
				" SET RECUPERACION = ?, USUARIO = ?" +
				" WHERE CARGA_ID = ?" +
				" AND CARRERA_ID = ?");
			ps.setString(1, permiso.getRecuperacion());
			ps.setString(2, permiso.getUsuario());
			ps.setString(3, permiso.getCargaId());			
			ps.setString(4, permiso.getCarreraId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cargaId, String carreraId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public CargaPermiso mapeaRegId( Connection conn, String cargaId, String carreraId ) throws SQLException{
		
		CargaPermiso permiso = new CargaPermiso();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO "+
				"FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				permiso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return permiso;
	}
	
	public boolean existeReg(Connection conn, String cargaId, String carreraId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esCarreraRecuperacion(Connection conn, String cargaId, String carreraId) throws SQLException{
		PreparedStatement 	ps		= null;
		ResultSet 			rs		= null;		
		boolean 			ok 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT RECUPERACION FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if ( rs.next()){
				if (rs.getString("RECUPERACION").equals("S")){
					ok = true;
				}							
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|esCarreraRecuperacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean carreraPermitida(Connection conn, String cargaId, String carreraId) throws SQLException{
		PreparedStatement 	ps		= null;
		ResultSet 			rs		= null;		
		boolean 			ok 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if ( rs.next()){				
					ok = true;											
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|carreraPermitida|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
		
	public ArrayList<CargaPermiso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaPermiso> lisPermiso		= new ArrayList<CargaPermiso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO "+
			"FROM ENOC.CARGA_PERMISO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaPermiso permiso = new CargaPermiso();
				permiso.mapeaReg(rs);
				lisPermiso.add(permiso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermisoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPermiso;
	}
	
	public ArrayList<CargaPermiso> getLista(Connection Conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaPermiso> lisPermiso		= new ArrayList<CargaPermiso>();
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO "+
				"FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaPermiso permiso = new CargaPermiso();
				permiso.mapeaReg(rs);
				lisPermiso.add(permiso);
			}
			
		}catch(Exception ex){
			System.out.println("Error- aca.carga.CargaPermisoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPermiso;
	}
	
	public ArrayList<CargaPermiso> getListaSinPermiso(Connection Conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaPermiso> lisPermiso		= new ArrayList<CargaPermiso>();
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;		
		try{
			String comando = "SELECT '"+cargaId+"' AS carga_Id, CARRERA_ID, 'N' AS RECUPERACION, '0' AS USUARIO "+
				"FROM ENOC.CAT_CARRERA "+ 
				"WHERE CARRERA_ID NOT IN "+
					"(SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE carga_Id = '"+cargaId+"') "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaPermiso permiso = new CargaPermiso();
				permiso.mapeaReg(rs);
				lisPermiso.add(permiso);
			}			
		}catch(Exception ex){
			System.out.println("Error- aca.carga.CargaPermisoUtil|getListaSinPermiso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPermiso;
	}	
}