package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

public class EmpMaestroUtil {
	
	/**
	 * @param conn the connection.
	 */
	public boolean insertReg(Connection conn, EmpMaestro empMaestro ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EMP_MAESTRO"+ 
				"(CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,  F_NACIMIENTO, "+
 				"GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO ) "+
				"VALUES( ?, UPPER(?), UPPER(?), UPPER(?),  TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?)");
			
			ps.setString(1, empMaestro.getCodigoPersonal());
			ps.setString(2, empMaestro.getNombre());
			ps.setString(3, empMaestro.getApellidoPaterno());
			ps.setString(4, empMaestro.getApellidoMaterno());
			ps.setString(5, empMaestro.getFNacimiento());
			ps.setString(6, empMaestro.getGenero());
			ps.setString(7, empMaestro.getEstadocivil());
			ps.setString(8, empMaestro.getTelefono());
			ps.setString(9, empMaestro.getEmail());
			ps.setString(10, empMaestro.getEstado());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EmpMaestro empMaestro ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EMP_MAESTRO "+ 
				" SET F_NACIMIENTO = TO_DATE(?, 'DD/MM/YYYY') , "+
				" NOMBRE = UPPER(?), "+
				" APELLIDO_PATERNO = UPPER(?), "+
				" APELLIDO_MATERNO = UPPER(?), "+
				" GENERO = ?, "+
				" ESTADOCIVIL = ?, "+
				" TELEFONO = ?, "+
				" EMAIL = ?, "+
				" ESTADO = ? "+
				" WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, empMaestro.getFNacimiento());
			ps.setString(2, empMaestro.getNombre());
			ps.setString(3, empMaestro.getApellidoPaterno());
			ps.setString(4, empMaestro.getApellidoMaterno());
			ps.setString(5, empMaestro.getGenero());
			ps.setString(6, empMaestro.getEstadocivil());
			ps.setString(7, empMaestro.getTelefono());
			ps.setString(8, empMaestro.getEmail());
			ps.setString(9, empMaestro.getEstado());			
			ps.setString(10, empMaestro.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EMP_MAESTRO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public EmpMaestro mapeaRegId( Connection conn, String codigoPersonal) throws SQLException{
		EmpMaestro empMaestro = new EmpMaestro();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				" CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+   
				" FROM ENOC.EMP_MAESTRO "+ 
				" WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				empMaestro.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return empMaestro;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.EMP_MAESTRO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.EMP_MAESTRO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CODIGO_PERSONAL)+1 MAXIMO FROM ENOC.EMP_MAESTRO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public String ultimoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CODIGO_PERSONAL) MAXIMO FROM ENOC.EMP_MAESTRO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|ultimoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public static boolean esMaestro(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EMP_MAESTRO WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|esMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public ArrayList<EmpMaestro> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<EmpMaestro> lisMaestro	= new ArrayList<EmpMaestro>();
		Statement st 		                = conn.createStatement();
		ResultSet rs 				        = null;
		String comando                     	= "";
			
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO," +
					"APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.EMP_MAESTRO "+ orden; 
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EmpMaestro emaestro = new EmpMaestro();
				emaestro.mapeaReg(rs);
				lisMaestro.add(emaestro);
			}
				
		}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpMaestroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisMaestro;
	}
		
	public TreeMap<String,EmpMaestro> getTreeAll(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,EmpMaestro> treeEmp	= new TreeMap<String, EmpMaestro>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
				" TELEFONO, EMAIL, ESTADO FROM ENOC.EMP_MAESTRO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EmpMaestro emp = new EmpMaestro();
				emp.mapeaReg(rs);
				llave = emp.getCodigoPersonal();
				treeEmp.put(llave, emp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestro|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeEmp;
	}	

}