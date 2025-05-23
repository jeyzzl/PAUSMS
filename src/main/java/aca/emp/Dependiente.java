package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dependiente {	
	private String empleadoId;
	private String id;	
	private String nombre;	
	private String bday;
	private String relacionId;
	private String matricula;	
	
	public Dependiente(){
		empleadoId 		= "";
		id           	= "";		
		nombre      	= "";		
		bday			= "";		
		relacionId      = "0";
		matricula    = "";
	}
	
	
	/**
	 * @return the empleadoId
	 */
	public String getEmpleadoId() {
		return empleadoId;
	}

	/**
	 * @param empleadoId the empleadoId to set
	 */
	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the bday
	 */
	public String getBday() {
		return bday;
	}

	/**
	 * @param bday the bday to set
	 */
	public void setBday(String bday) {
		this.bday = bday;
	}

	/**
	 * @return the relacionId
	 */
	public String getRelacionId() {
		return relacionId;
	}

	/**
	 * @param relacionId the relacionId to set
	 */
	public void setRelacionId(String relacionId) {
		this.relacionId = relacionId;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	
	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	

	/**
	 * @param conn the connection.
	 */
	public void mapeaReg(ResultSet rs ) throws SQLException{
		empleadoId      = rs.getString("EMPLEADO_ID");
		id	            = rs.getString("ID");		
		nombre       	= rs.getString("NOMBRE");		
		bday 			= rs.getString("BDAY");		
		relacionId	    = rs.getString("RELACION_ID");
		matricula   	= rs.getString("MATRICULA");
	}	
	
	public void mapeaRegId( Connection conn, String empleadoId, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"EMPLEADO_ID, ID, NOMBRE, BDAY, RELACION_ID, MATRICULA " +
				"FROM ARON.EMPLEADO "+
				"WHERE EMPLEADO_ID = ?" +
				"AND ID = ?");
			ps.setString(1, empleadoId);
			ps.setString(2, id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.Dependiente|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?");
			ps.setString(1, empleadoId);
			ps.setString(2, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.Dependiente|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeDependiente(Connection conn, String empleadoId, String id) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?");
			ps.setString(1, empleadoId);
			ps.setString(2, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.Dependiente|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombre(Connection conn, String empleadoId, String id) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?");
			ps.setString(1, empleadoId);
			ps.setString(2, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.Dependiente|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

}