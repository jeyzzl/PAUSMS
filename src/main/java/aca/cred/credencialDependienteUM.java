package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class credencialDependienteUM {
	private String idDependiente;	
	private String idEmpleado;
	private String depNombre;
	private String depApellidos;
	private String empNombre;
	private String empApellidos;	
	private String relacion;
	private String puesto;
	private String departamento;
	private String cotejado;
	private String fecha;
	private String folio;
	private String fActualiza;
	
	
	public credencialDependienteUM(){
		idDependiente 	= "";
		idEmpleado  	= "";
		depNombre		= "";
		depApellidos	= "";
		empNombre  		= "";
		empApellidos 	= "";
		relacion 		= "";
		puesto  		= "";
		departamento	= "";
		cotejado		= "";
		fecha			= "";
		folio			= "";
		fActualiza		= "";
	}	

	
	public String getDepNombre() {
		return depNombre;
	}


	public void setDepNombre(String depNombre) {
		this.depNombre = depNombre;
	}


	public String getDepApellidos() {
		return depApellidos;
	}


	public void setDepApellidos(String depApellidos) {
		this.depApellidos = depApellidos;
	}


	public String getEmpNombre() {
		return empNombre;
	}


	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
	}


	public String getEmpApellidos() {
		return empApellidos;
	}


	public void setEmpApellidos(String empApellidos) {
		this.empApellidos = empApellidos;
	}


	/**
	 * @return the idDependiente
	 */
	public String getIdDependiente() {
		return idDependiente;
	}

	/**
	 * @param idDependiente the idDependiente to set
	 */
	public void setIdDependiente(String idDependiente) {
		this.idDependiente = idDependiente;
	}


	/**
	 * @return the idEmpleado
	 */
	public String getIdEmpleado() {
		return idEmpleado;
	}

	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	/**
	 * @return the relacion
	 */
	public String getRelacion() {
		return relacion;
	}

	/**
	 * @param relacion the relacion to set
	 */
	public void setRelacion(String relacion) {
		this.relacion = relacion;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}


	/**
	 * @return the fActualiza
	 */
	public String getfActualiza() {
		return fActualiza;
	}

	/**
	 * @param fActualiza the fActualiza to set
	 */
	public void setfActualiza(String fActualiza) {
		this.fActualiza = fActualiza;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CRED_DEPENDIENTE(ID_DEPENDIENTE, ID_EMPLEADO, EMP_NOMBRE, EMP_APELLIDOS, RELACION, PUESTO," +
				" DEPARTAMENTO, COTEJADO, FECHA, FOLIO, F_ACTUALIZA, DEP_NOMBRE, DEP_APELLIDOS) " +
				" VALUES(?,?,?,?,?,?,?,?,?,?,now(),?,?)");
			ps.setString(1, idDependiente);
			ps.setString(2, idEmpleado);			
			ps.setString(3, empNombre);
			ps.setString(4, empApellidos);
			ps.setString(5, relacion);			
			ps.setString(6, puesto);
			ps.setString(7, departamento);
			ps.setString(8, cotejado);			
			ps.setString(9, fecha);
			ps.setString(10,folio);
			ps.setString(11,depNombre);
			ps.setString(12,depApellidos);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CRED_DEPENDIENTE" + 
				" SET ID_EMPLEADO= ?, EMP_NOMBRE= ?, EMP_APELLIDOS= ?, RELACION= ?," +
				" PUESTO= ?, DEPARTAMENTO= ?, COTEJADO= ?," +
				" FECHA= ?, FOLIO= ?, F_ACTUALIZA = now(), " +
				" DEP_NOMBRE = ? , DEP_APELLIDOS = ? " +
				" WHERE ID_DEPENDIENTE = ? ");
			ps.setString(1, idEmpleado);
			ps.setString(2, empNombre);
			ps.setString(3, empApellidos);
			ps.setString(4, relacion);
			ps.setString(5, puesto);
			ps.setString(6, departamento);
			ps.setString(7, cotejado);
			ps.setString(8, fecha);
			ps.setString(9, folio);
			ps.setString(10, depNombre);
			ps.setString(11, depApellidos);
			ps.setString(12,idDependiente);
						
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CRED_DEPENDIENTE "+ 
				"WHERE ID_DEPENDIENTE = ? ");
			ps.setString(1, idDependiente);

			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDepentienteUM|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idDependiente 	= rs.getString("ID_DEPENDIENTE");
		idEmpleado		= rs.getString("ID_EMPLEADO");
		empNombre	 	= rs.getString("EMP_NOMBRE");
		empApellidos 	= rs.getString("EMP_APELLIDOS");
		relacion	 	= rs.getString("RELACION");
		puesto	 		= rs.getString("PUESTO");
		departamento 	= rs.getString("DEPARTAMENTO");
		cotejado	 	= rs.getString("COTEJADO");
		fecha			= rs.getString("FECHA");
		folio			= rs.getString("FOLIO");
		fActualiza		= rs.getString("F_ACTUALIZA");
		depNombre		= rs.getString("DEP_NOMBRE");
		depApellidos	= rs.getString("DEP_APELLIDOS");
	}
	
	public void mapeaRegId(Connection con, String idDependiente) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID_DEPENDIENTE, ID_EMPLEADO, EMP_NOMBRE," +
					" EMP_APELLIDOS, RELACION, PUESTO, DEPARTAMENTO, COTEJADO, FECHA, FOLIO," +
					" TO_CHAR(F_ACTUALIZA,'DD/MM/YYYY') AS F_ACTUALIZA, DEP_NOMBRE, DEP_APELLIDOS " +
					" FROM ENOC.CRED_DEPENDIENTE" + 
					" WHERE ID_DEPENDIENTE =?");				
			ps.setString(1, idDependiente);
	
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ? "); 
			ps.setString(1, idDependiente);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT (MAX(FOLIO)+1) AS MAXIMO FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ? "); 
			ps.setString(1, idDependiente);

			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static String getFechaActualiza(Connection conn, String dependienteId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String fecha			= "01/01/2000";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(F_ACTUALIZA,'DD/MM/YYYY'),'01/01/2000') AS FECHA" +
					" FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ? "); 
			ps.setString(1, dependienteId);

			rs = ps.executeQuery();
			if (rs.next())
				fecha = rs.getString("FECHA");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credencialDependienteUM|getFechaActualiza|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	
}