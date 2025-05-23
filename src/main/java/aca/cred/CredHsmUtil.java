package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CredHsmUtil {
	
	public boolean insertReg(Connection conn, CredHsm hsm) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CRED_HSM"+ 
				"(CLAVE, NOMBRE, AREA, FONDO, ESTADO) "+
				"VALUES( ?,?,?,?,?)");
			ps.setString(1, hsm.getClave());
			ps.setString(2, hsm.getNombre());
			ps.setString(3, hsm.getArea());
			ps.setString(4, hsm.getFondo());
			ps.setString(5, hsm.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CredHsm hsm ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CRED_HSM"+ 
				" SET"+
				" NOMBRE = ?,"+
				" AREA = ?,"+
				" FONDO = ?," +
				" ESTADO = ?"+
				" WHERE CLAVE = ? ");
			ps.setString(1, hsm.getNombre());
			ps.setString(2, hsm.getArea());
			ps.setString(3, hsm.getFondo());
			ps.setString(4, hsm.getEstado());
			ps.setString(5, hsm.getClave());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String clave ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ");
			ps.setString(1, clave);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CredHsm mapeaRegId( Connection conn, String clave) throws SQLException{
		
		CredHsm hsm = new CredHsm();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CLAVE, NOMBRE, AREA, FONDO, ESTADO " +
				"FROM ENOC.CRED_HSM WHERE CLAVE = ? "); 
			ps.setString(1, clave);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				hsm.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return hsm;
	}
	
	public boolean existeReg(Connection conn, String clave) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CLAVE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ");
			ps.setString(1, clave);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maxReg(Connection conn, String prefijo) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo 			= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE(MAX(TO_NUMBER(CLAVE,'9999999')+1),9899001)) AS MAXIMO FROM ENOC.CRED_HSM"+ 
					" WHERE clave like '"+prefijo+"%'");			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombre(Connection conn, String clave) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ");
			ps.setString(1, clave);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CredHsm> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CredHsm> lista			= new ArrayList<CredHsm>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CLAVE, NOMBRE, AREA, FONDO, ESTADO FROM ENOC.CRED_HSM "+orden; 

			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CredHsm hsm = new CredHsm();
				hsm.mapeaReg(rs);
				lista.add(hsm);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsmUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public ArrayList<CredHsm> getListClinica(Connection conn, String clinica, String orden) throws SQLException{
		
		ArrayList<CredHsm> lista			= new ArrayList<CredHsm>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CLAVE, NOMBRE, AREA, FONDO, ESTADO FROM ENOC.CRED_HSM WHERE FONDO = '"+clinica+"' " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CredHsm hsm = new CredHsm();
				hsm.mapeaReg(rs);
				lista.add(hsm);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.getListClinica|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
}