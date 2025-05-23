package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class credencialEmpleadoUM {
	private String id;	
	private String clave;
	private String nombre;
	private String apellidos;	
	private String puesto;
	private String departamento;
	private String status;
	private String cotejado;
	private String imprimir;

	
	public credencialEmpleadoUM(){
		id 				= "";
		clave  			= "";
		nombre	  		= "";
		apellidos 		= "";
		puesto  		= "";
		departamento	= "";
		status	= "";
		cotejado  		= "";
		imprimir	= "";
		
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
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return puesto;
	}

	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cotejado
	 */
	public String getCotejado() {
		return cotejado;
	}

	/**
	 * @param cotejado the cotejado to set
	 */
	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}

	/**
	 * @return the imprimir
	 */
	public String getImprimir() {
		return imprimir;
	}

	/**
	 * @param imprimir the imprimir to set
	 */
	public void setImprimir(String imprimir) {
		this.imprimir = imprimir;
	}

	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CRED_EMPLEADO(ID, CLAVE, NOMBRE, APELLIDOS, PUESTO, DEPARTAMENTO, " +
				"STATUS, COTEJADO, IMPRIMIR ) " +
				"VALUES(TO_NUMBER(?,'999'),?,?,?,?,?,?,?,? ) ");
			ps.setString(1, id);
			ps.setString(2, clave);			
			ps.setString(3, nombre);
			ps.setString(4, apellidos);
			ps.setString(5, puesto);			
			ps.setString(6, departamento);
			ps.setString(7, status);
			ps.setString(8, cotejado);			
			ps.setString(9, imprimir);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialEmpleadoUM|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CRED_EMPLEADO " + 
				"SET  CLAVE= ?, NOMBRE=?, APELLIDOS=? " +
				"PUESTO= ?, DEPARTAMENTO= ?, EMPLEADO= ?, " +
				"STATUS= ?, IMPRIMIR= ?, COTEJADO= ? " +
				"WHERE ID= TO_NUMBER(?,999) ");
			ps.setString(1, clave);
			ps.setString(2, nombre);
			ps.setString(3, apellidos);
			ps.setString(4, puesto);
			ps.setString(5, departamento);
			ps.setString(6, status);
			ps.setString(7, imprimir);
			ps.setString(8, cotejado);
			ps.setString(9, id);
			
						
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialEmpleadoUM|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COND_ALUMNO "+ 
				"WHERE ID = TO_NUMBER(?,999)  ");
			ps.setString(1, id);

			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialEmpleadoUM|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id 				= rs.getString("ID");
		clave			= rs.getString("CLAVE");
		nombre	 		= rs.getString("NOMBRE");
		apellidos 		= rs.getString("APELLIDOS");
		puesto	 		= rs.getString("PUESTO");
		departamento	= rs.getString("DEPARTAMENTO");
		status 			= rs.getString("STATUS");
		imprimir	 	= rs.getString("IMPRIMIR");
		cotejado		= rs.getString("COTEJADO");

	}
	
	public void mapeaRegId(Connection con, String matricula) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID, CLAVE, NOMBRE, " +
					"APELLIDOS, PUESTO, DEPARTAMENTO, STATUS, , " +
					"IMPRIMIR, COTEJADO " +
					"WHERE ID = TO_NUMBER(?,999) ");				
			ps.setString(1, id);
	
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CondAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CRED_EMPLEADO WHERE ID =TO_NUMBER(?,999)  "); 
			ps.setString(1, id);

			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CondAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula, String periodoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(FOLIO)+1) AS MAXIMO FROM ENOC.CRED_EMPLEADO WHERE ID = TO_NUMBER(?,999) "  ); 
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialEmpleadoUM|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	

}
	
	

  