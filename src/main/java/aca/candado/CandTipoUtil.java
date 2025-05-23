// Clase para la tabla de CandTipo
package aca.candado;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CandTipoUtil{
	
	public boolean insertReg(Connection conn, CandTipo tipo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAND_TIPO"+ 
					"(TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO) "+
					"VALUES(?,?,?,?,?)");
			ps.setString(1, tipo.getTipoId());
			ps.setString(2, tipo.getNombreTipo());
			ps.setString(3, tipo.getResponsable());
			ps.setString(4, tipo.getLugar());
			ps.setString(5, tipo.getTelefono());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CandTipo tipo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAND_TIPO "+ 
				"SET NOMBRE_TIPO = ?, RESPONSABLE= ?, LUGAR = ?, TELEFONO=? "+
				"WHERE TIPO_ID = ?");			
			ps.setString(1, tipo.getNombreTipo());
			ps.setString(2, tipo.getResponsable());
			ps.setString(3, tipo.getLugar());
			ps.setString(4, tipo.getTelefono());
			ps.setString(5, tipo.getTipoId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String tipoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAND_TIPO WHERE TIPO_ID = ?"); 
			ps.setString(1,tipoId);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public CandTipo mapeaRegId(Connection conn, String tipoId) throws SQLException{
		
		CandTipo tipo = new CandTipo();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT "+
				"TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO "+				
				"FROM ENOC.CAND_TIPO WHERE TIPO_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,tipoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				tipo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public boolean existeReg(Connection conn, String tipoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_TIPO WHERE TIPO_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1, tipoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean esResponsable(Connection conn, String codigoPersonal) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_TIPO "+ 
				"WHERE RESPONSABLE LIKE '%-'||?||'-%'");
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|esResponsable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esResponsable(Connection conn, String tipoId, String codigoPersonal) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_TIPO "+ 
				"WHERE TIPO_ID = ? "+
				"AND RESPONSABLE LIKE '%-'||?||'-%'");
			ps.setString(1, tipoId);
			ps.setString(2, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|esResponsable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreTipo(Connection conn, String tipoId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPO "+
				"FROM ENOC.CAND_TIPO WHERE TIPO_ID = ? "); 
			ps.setString(1, tipoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				nombre = rs.getString("NOMBRE_TIPO");
			}else{
				nombre = "x";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|getNombreTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public static String getSolucion(Connection conn, String tipoId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String solucion			= "";
		
		try{
			ps = conn.prepareStatement("SELECT LUGAR||' '||TELEFONO AS SOLUCION "+
				"FROM ENOC.CAND_TIPO WHERE TIPO_ID = ? "); 
			ps.setString(1, tipoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				solucion = rs.getString("SOLUCION");
			}else{
				solucion = "x";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|getSolucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return solucion;
	}
	
	public static String getLugar(Connection conn, String tipoId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String solucion			= "";
		
		try{
			ps = conn.prepareStatement("SELECT LUGAR AS SOLUCION "+
				"FROM ENOC.CAND_TIPO WHERE TIPO_ID = ? "); 
			ps.setString(1, tipoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				solucion = rs.getString("SOLUCION");
			}else{
				solucion = "x";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|getSolucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return solucion;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(TIPO_ID)+1 MAXIMO FROM ENOC.CAND_TIPO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuenta|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	
	// Regresa un listor con todos los elementos de la tabla
	public ArrayList<CandTipo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CandTipo> lisTipo 	= new ArrayList<CandTipo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO FROM ENOC.CAND_TIPO"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandTipo tipo = new CandTipo();
				tipo.mapeaReg(rs);
				lisTipo.add(tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.TipoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipo;
	}
	
	// Regresa un listor con todos los elementos de la tabla
	public ArrayList<CandTipo> getLista(Connection conn, String orden ) throws SQLException{
			
		ArrayList<CandTipo> lisTipo 	= new ArrayList<CandTipo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
			
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO FROM ENOC.CAND_TIPO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandTipo tipo = new CandTipo();
				tipo.mapeaReg(rs);
				lisTipo.add(tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.TipoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipo;
	}
	
	// Regresa un listor con los modulos de candado a los que un usuario tiene acceso
	public ArrayList<CandTipo> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CandTipo> lisTipo 	= new ArrayList<CandTipo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO "+
				"FROM ENOC.CAND_TIPO "+ 
				"WHERE RESPONSABLE LIKE '%-'||'"+codigoPersonal+"'||'-%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandTipo tipo = new CandTipo();
				tipo.mapeaReg(rs);
				lisTipo.add(tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.TipoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipo;
	}	
	
	public HashMap<String,CandTipo> mapTipo(Connection conn) throws SQLException{
		
		HashMap<String,CandTipo> map = new HashMap<String,CandTipo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO FROM ENOC.CAND_TIPO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CandTipo obj = new CandTipo();
				obj.mapeaReg(rs);
				llave = obj.getTipoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.TipoUtil|mapTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}