/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccesoUtil {
	
	public boolean insertReg(Connection conn, Acceso acceso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_ACCESO(CODIGO_PERSONAL,DORMITORIO_ID, ROL, PASILLO) VALUES(?,?,?,?)"); 
			ps.setString(1, acceso.getCodigoPersonal());
			ps.setString(2, acceso.getDormitorioId());
			ps.setString(3, acceso.getRol());
			ps.setString(4, acceso.getPasillo());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, Acceso acceso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_ACCESO SET ROL = ?, PASILLO = ? WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?");			 
			ps.setString(1, acceso.getRol());
			ps.setString(2, acceso.getPasillo());
			ps.setString(3, acceso.getDormitorioId());
			ps.setString(4, acceso.getCodigoPersonal());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String dormitorioId, String codigoPersonal, String rol ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ? AND ROL = ?"); 
			ps.setString(1, dormitorioId);			
			ps.setString(2, codigoPersonal);
			ps.setString(3, rol);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Acceso mapeaRegId(Connection con, String dormitorioId, String codigoPersonal, String rol) throws SQLException{
		Acceso acceso = new Acceso();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ? AND ROL = ?"); 
			ps.setString(1,dormitorioId);
			ps.setString(2,codigoPersonal);
			ps.setString(3,rol);
			rs = ps.executeQuery();
			
			if(rs.next()){
				acceso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return acceso;
	}
	
	public static int tieneAcceso(Connection conn, String codigoPersonal) throws Exception {
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando		= "";
		int numero 			= -1;
		
		try{
			comando = "SELECT DORMITORIO_ID FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				numero = rs.getInt("dormitorio_id");
			}else{				
				comando = "SELECT DORMITORIO_ID FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = '"+codigoPersonal+"'"; 
				rs2 = st.executeQuery(comando);
				if (rs2.next()){
					numero = rs2.getInt("dormitorio_id");
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|tieneAcceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numero;		
	}
	public static boolean esPreceptor(Connection conn, String codigoPersonal) throws Exception {
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando		= "";
		boolean ok = false;
		
		try{
			comando = "SELECT * FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' and ROL = 'P'"; 
			rs = st.executeQuery(comando);
			if (rs.next())	ok=true;
			else{
				comando = "SELECT * FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = '"+codigoPersonal+"'"; 
				rs2 = st.executeQuery(comando);
				if (rs2.next()) ok=true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|esPreceptor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;		
	}
	public static boolean esMonitor(Connection conn, String codigoPersonal) throws Exception {
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok = false;
		
		try{
			comando = "SELECT * FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' and ROL = 'M'"; 
			rs = st.executeQuery(comando);
			if (rs.next())	ok=true;
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|esMonitor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;		
	}

}