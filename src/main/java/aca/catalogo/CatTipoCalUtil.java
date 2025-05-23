package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatTipoCalUtil {
	
	public boolean insertReg(Connection conn, CatTipoCal tipocal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_TIPOCAL(TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO) "+
				"VALUES( ?, ?, ?)");			
			ps.setString(1, tipocal.getTipoCalId());
			ps.setString(2, tipocal.getNombreTipoCal());
			ps.setString(3, tipocal.getNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatTipoCal tipocal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_TIPOCAL "+ 
				"SET NOMBRE_TIPOCAL = ?, NOMBRE_CORTO = ? WHERE TIPOCAL_ID = ? ");
			ps.setString(1, tipocal.getNombreTipoCal());
			ps.setString(2, tipocal.getNombre());
			ps.setString(3, tipocal.getTipoCalId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String tipocalId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_TIPOCAL "+ 
				"WHERE TIPOCAL_ID = ?");
			ps.setString(1, tipocalId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatTipoCal mapeaRegId( Connection conn, String tipoCalId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		CatTipoCal tipocal = new CatTipoCal();
		
		try{
			ps = conn.prepareStatement("SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO "+
				"FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"); 
			ps.setString(1,tipoCalId);
			rs = ps.executeQuery();
			if (rs.next()){
				tipocal.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return tipocal;
	}
	
	public boolean existeReg(Connection conn, String tipocalId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"); 
			ps.setString(1,tipocalId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreTipoCal(Connection conn, String tipoCal) throws SQLException{		
		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String nombreTipoCal		= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"); 
			ps.setString(1,tipoCal);
			rs = ps.executeQuery();
			if (rs.next())
				nombreTipoCal = rs.getString("NOMBRE_TIPOCAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|getNombreTipoCal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreTipoCal;
	}
	
	public static String getNombreCorto(Connection conn, String tipoCal) throws SQLException{		
		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String nombreTipoCal		= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"); 
			ps.setString(1,tipoCal);
			rs = ps.executeQuery();
			if (rs.next())
				nombreTipoCal = rs.getString("NOMBRE_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreTipoCal;
	}
		
	public ArrayList<CatTipoCal> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatTipoCal> lisTipocal	= new ArrayList<CatTipoCal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO "+
				"FROM ENOC.CAT_TIPOCAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatTipoCal tipocal = new CatTipoCal();
				tipocal.mapeaReg(rs);
				lisTipocal.add(tipocal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCalUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipocal;
	}

	public HashMap<String, CatTipoCal> getMapTipoCal(Connection conn, String orden) throws SQLException{		
		
		HashMap<String, CatTipoCal> lisTipoCal = new HashMap<String, CatTipoCal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =  "SELECT * FROM ENOC.CAT_TIPOCAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatTipoCal tipoCal = new CatTipoCal();
				tipoCal.mapeaReg(rs);
				lisTipoCal.put(tipoCal.getTipoCalId(), tipoCal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCalUtil|getMapTipoCal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipoCal;
	}
	
	public HashMap<String,CatTipoCal> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatTipoCal> map = new HashMap<String,CatTipoCal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_TIPOCAL"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatTipoCal obj = new CatTipoCal();
				obj.mapeaReg(rs);
				llave = obj.getNombreTipoCal();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCalUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}