package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecAccesoUtil {
	
	public boolean insertReg(Connection conn, BecAcceso becAcceso) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_ACCESO"+ 
				"(CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO," +
				" FECHA, USUARIO)"+
				" VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? )");
					
			ps.setString(1,  becAcceso.getCodigoPersonal());
			ps.setString(2,  becAcceso.getIdEjercicio());
			ps.setString(3,  becAcceso.getIdCcosto());
			ps.setString(4,  becAcceso.getFecha());
			ps.setString(5,  becAcceso.getUsuario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecAcceso becAcceso) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACCESO"+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" USUARIO = ? "+				
				" WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? ");
			
			ps.setString(1,  becAcceso.getFecha());
			ps.setString(2,  becAcceso.getUsuario());
			ps.setString(3,  becAcceso.getCodigoPersonal());
			ps.setString(4,  becAcceso.getIdEjercicio());
			ps.setString(5,  becAcceso.getIdCcosto());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String idEjercicio, String idCcosto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_ACCESO"+ 
				" WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? ");
			
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  idCcosto);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String idEjercicio, String idCcosto) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? "); 
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  idCcosto);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public BecAcceso mapeaRegId(Connection conn, String idEjercicio, String idCcosto, String codigoPersonal ) throws SQLException{
		BecAcceso becAcceso = new BecAcceso();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO " +
					" FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? "); 
			
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  idCcosto);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becAcceso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becAcceso;
	}
	
	public static String getUsuarioCentrosCosto(Connection conn, String idEjercicio, String codigoPersonal) throws SQLException{
		PreparedStatement ps 		= null;
		ResultSet rs 				= null;
		StringBuilder centrosCosto	= new StringBuilder();
		
		try{
			ps = conn.prepareStatement("SELECT ID_CCOSTO FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ?");
			
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			
			rs = ps.executeQuery();
			while(rs.next()){
					centrosCosto.append("-"+rs.getString("ID_CCOSTO"));	
			}
			centrosCosto.append("-");
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|getUsuarioCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return centrosCosto.toString();
	}
	
	public static boolean esUsuarioBecas(Connection conn, String idEjercicio, String codigoPersonal) throws SQLException{
		PreparedStatement ps 		= null;
		ResultSet rs 				= null;
		boolean ok 					= false;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  codigoPersonal);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ok = true;		
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|esUsuarioBecas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<BecAcceso> getListAll(Connection conn, String orden) throws SQLException{			
		ArrayList<BecAcceso> lis 		= new ArrayList<BecAcceso>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_ACCESO "+orden;	
			
			rs = st.executeQuery(comando);
			while (rs.next()){
							
			BecAcceso obj= new BecAcceso();
			obj.mapeaReg(rs);
			lis.add(obj);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lis;
	}
		
	public ArrayList<BecAcceso> getListUsuario(Connection conn, String codigo, String orden) throws SQLException{
		ArrayList<BecAcceso> lis 		= new ArrayList<BecAcceso>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = '"+codigo+"' "+orden;
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcceso obj= new BecAcceso();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
		
	public ArrayList<BecAcceso> getListDepartamento(Connection conn, String ejercicioId, String ccosto, String orden) throws SQLException{	
		ArrayList<BecAcceso> lis 		= new ArrayList<BecAcceso>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
			
		try{
			comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO" +
					" FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = '"+ejercicioId+"'  AND ID_CCOSTO = '"+ccosto+"' " +orden;
				
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				BecAcceso obj= new BecAcceso();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lis;
	}
		
	public HashMap<String, String> getAccesoDepto(Connection conn, String ejercicioId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String depto 			= "x";
		StringBuffer usuarios	= new StringBuffer();
		int row 				= 0;
		try{
			comando = "SELECT ID_CCOSTO, CODIGO_PERSONAL FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = '"+ejercicioId+"' ORDER BY ID_CCOSTO";
			rs = st.executeQuery(comando);
			while (rs.next()){					
				if ( !depto.equals(rs.getString("ID_CCOSTO")) && !depto.equals("x")){
					// grabar elemento en el mapa
					mapa.put(depto, usuarios.toString());						
					row = 0;
					usuarios.delete(0,usuarios.length());						
				}
				// construir la lista de usuarios						
				if (row>0) usuarios.append(",");
				usuarios.append(rs.getString("CODIGO_PERSONAL"));
				depto = rs.getString("ID_CCOSTO");					
				row++;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.BecAccesoUtil|getAccesoDepto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
		
}
