package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusiMaestro {

 	private String maestroId;
 	private String nombre;
 	private String apellidoPaterno;
 	private String apellidoMaterno;
 	private String fNacimiento;
 	private String telefono;
 	private String celular;
 	private String correo;
 	
 	
 	public MusiMaestro(){
 		maestroId			= "";
 		nombre				= "";
 		apellidoPaterno		= "";
 		apellidoMaterno		= "";
 		fNacimiento			= "";
 		telefono			= "";
 		celular				= "";
 		correo				= "";
 	
 	
 	}
 
	public String getMaestroId() {
		return maestroId;
	}

	public void setMaestroId(String maestroId) {
		this.maestroId = maestroId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getFNacimiento() {
		return fNacimiento;
	}

	public void setFNacimiento(String nacimiento) {
		fNacimiento = nacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_MAESTRO"+ 
 				"(MAESTRO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, F_NACIMIENTO, "+
 				" TELEFONO, CELULAR, CORREO) "+
 				"VALUES( ?, ?, ?, ?,TO_DATE(?,'DD/MM/YYYY'), "+
 				" ?, ?, ? )");
 			ps.setString(1, maestroId);
 			ps.setString(2, nombre.toUpperCase());
 			ps.setString(3, apellidoPaterno.toUpperCase());
 			ps.setString(4, apellidoMaterno.toUpperCase());
 			ps.setString(5, fNacimiento);
  			ps.setString(6, telefono);
 			ps.setString(7,celular);
 			ps.setString(8,correo); 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.MusiAlumno|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		Statement st 		= conn.createStatement(); 		
 		String comando = "";
 		boolean ok = false;
 		
 		try{
 			comando = "UPDATE ENOC.MUSI_MAESTRO"+			 
 				" SET NOMBRE = '"+nombre+"',"+
 				" APELLIDO_PATERNO = '"+apellidoPaterno+"',"+
 				" APELLIDO_MATERNO = '"+apellidoMaterno+"',"+
 				" F_NACIMIENTO = TO_DATE('"+fNacimiento+"','DD/MM/YYYY'),"+
  				" TELEFONO = '"+telefono+"',"+
 				" CELULAR = '"+celular+"',"+
 				" CORREO = '"+correo+"'"+ 
 				" WHERE MAESTRO_ID = '"+maestroId+"'";
			if (st.executeUpdate(comando)==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiAlumno|updateReg|:"+ex);		
 		}finally{
 			try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_MAESTRO "+ 
 				"WHERE MAESTRO_ID = ? ");
 			ps.setString(1, maestroId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiAlumno|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		maestroId 			= rs.getString("MAESTRO_ID");
 		nombre 				= rs.getString("NOMBRE");
 		apellidoPaterno 	= rs.getString("APELLIDO_PATERNO");
 		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
 		fNacimiento 	    = rs.getString("F_NACIMIENTO");
 		telefono 			= rs.getString("TELEFONO");
 		celular				= rs.getString("CELULAR");
 		correo 				= rs.getString("CORREO");
 	}
  	
 	public void mapeaRegId( Connection conn, String maestroId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
 		ps = conn.prepareStatement("SELECT "+
	 			" MAESTRO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
	 			" TELEFONO, CELULAR, CORREO "+
	 			" FROM ENOC.MUSI_MAESTRO WHERE MAESTRO_ID = ? "); 
	 		ps.setString(1, maestroId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMaestro|mapeaRegId|:"+ex);
			ex.printStackTrace();
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_MAESTRO "+ 
 				"WHERE MAESTRO_ID = ?");
 			ps.setString(1, maestroId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiAlumno|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	
	public static String getNombre(Connection conn, String maestroId, String opcion) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			if ( opcion.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					"FROM ENOC.MUSI_MAESTRO WHERE MAESTRO_ID = '"+maestroId+"'"; 
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					"FROM ENOC.MUSI_MAESTRO WHERE MAESTRO_ID = '"+maestroId+"'"; 
			}	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumno|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(MAESTRO_ID)+1,1) MAXIMO FROM ENOC.MUSI_MAESTRO"); 
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
	
}