 // Bean de datos personales del alumno 
 package  aca.musica;

 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


 /**
 * @author David Blanco
 *
 */
public class MusiAlumno{
 	private String codigoId;
 	private String nombre;
 	private String apellidoPaterno;
 	private String apellidoMaterno;
 	private String fechaNac;
 	private String institucionId;
 	private String seguro;
 	private String tutor;
 	private String telefono;
 	private String celular;
 	private String email;
 	private String codigoUM;
 	private String comentario;
 	private String empleado;
 	private String religionId;
 	private String codigoUsuario;
 	private String ciudadId;
 	private String estadoId;
 	private String paisId;
 	private String nacionalidad;
 	private String telTrabajo;
 	private String estado;
 	private String genero;
 	
 	public MusiAlumno(){
 		codigoId			= "";
 		nombre				= "";
 		apellidoPaterno		= "";
 		apellidoMaterno		= "";
 		fechaNac			= "";
 		institucionId		= "";
 		seguro				= "";
 		tutor				= "";
 		telefono			= "";
 		celular				= "";
 		email				= "";
 		codigoUM			= "0";
 		comentario			= "";
 		empleado            = "";
 		religionId          = "";
 		codigoUsuario       = "";
 		ciudadId			= "";
 		estadoId			= "";
 		paisId				= "91";
 		nacionalidad		= "91";
 		telTrabajo			= "";
 		estado				= "";
 		genero				= "";
 	
 	}
 
	/**
	 * @return the codigoId
	 */
	public String getCodigoId() {
		return codigoId;
	}

	/**
	 * @param codigoId the codigoId to set
	 */
	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
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
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the fechaNac
	 */
	public String getFechaNac() {
		return fechaNac;
	}

	/**
	 * @param fechaNac the fechaNac to set
	 */
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	/**
	 * @return the institucionId
	 */
	public String getInstitucionId() {
		return institucionId;
	}

	/**
	 * @param institucionId the institucionId to set
	 */
	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}

	/**
	 * @return the seguro
	 */
	public String getSeguro() {
		return seguro;
	}

	/**
	 * @param seguro the seguro to set
	 */
	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}

	/**
	 * @return the tutor
	 */
	public String getTutor() {
		return tutor;
	}

	/**
	 * @param tutor the tutor to set
	 */
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the codigoUM
	 */
	public String getCodigoUM() {
		return codigoUM;
	}

	/**
	 * @param codigoUM the codigoUM to set
	 */
	public void setCodigoUM(String codigoUM) {
		this.codigoUM = codigoUM;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the empleado
	 */
	public String getEmpleado() {
		return empleado;
	}

	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	/**
	 * @return the religionId
	 */
	public String getReligionId() {
		return religionId;
	}

	/**
	 * @param religionId the religionId to set
	 */
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getTelTrabajo() {
		return telTrabajo;
	}

	public void setTelTrabajo(String telTrabajo) {
		this.telTrabajo = telTrabajo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_ALUMNO"
 					+ " (CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, "
 					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, "
 					+ " EMPLEADO, RELIGION_ID, CODIGO_USUARIO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO) "
 					+ " VALUES( ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, ?, ? , ?, "
 					+ " TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?)");
 			ps.setString(1, codigoId);
 			ps.setString(2, nombre.toUpperCase());
 			ps.setString(3, apellidoPaterno.toUpperCase());
 			ps.setString(4, apellidoMaterno.toUpperCase());
 			ps.setString(5, fechaNac);
 			ps.setString(6, institucionId);
 			ps.setString(7, seguro);
 			ps.setString(8, tutor);
 			ps.setString(9, telefono);
 			ps.setString(10,celular);
 			ps.setString(11,email);
 			ps.setString(12,codigoUM);
 			ps.setString(13,comentario);
 			ps.setString(14,empleado);
 			ps.setString(15,religionId);
 			ps.setString(16,codigoUsuario);
 			ps.setString(17,ciudadId);
 			ps.setString(18,estadoId);
 			ps.setString(19,paisId);
 			ps.setString(20,nacionalidad);
 			ps.setString(21,telTrabajo);
 			ps.setString(22,estado);
 			ps.setString(23,genero);
 			
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
 			comando = "UPDATE ENOC.MUSI_ALUMNO"+			 
 				" SET NOMBRE = '"+nombre+"',"+
 				" APELLIDO_PATERNO = '"+apellidoPaterno+"',"+
 				" APELLIDO_MATERNO = '"+apellidoMaterno+"',"+
 				" FECHA_NAC = TO_DATE('"+fechaNac+"','DD/MM/YYYY'),"+
 				" INSTITUCION_ID = TO_NUMBER('"+institucionId+"','99'),"+
 				" SEGURO = '"+seguro+"',"+
 				" TUTOR = '"+tutor+"',"+
 				" TELEFONO = '"+telefono+"',"+
 				" CELULAR = '"+celular+"',"+
 				" EMAIL = '"+email+"',"+
 				" CODIGO_UM = '"+codigoUM+"',"+
 				" COMENTARIO = '"+comentario+"'," +
 				" EMPLEADO = '"+empleado+"'," + 		
 				" RELIGION_ID = "+religionId+"," + 
 				" CODIGO_USUARIO = '"+codigoUsuario+"'," + 
 				" CIUDAD_ID = "+ciudadId+"," + 
 				" ESTADO_ID = "+estadoId+"," + 
 				" PAIS_ID = "+paisId+"," + 
 				" NACIONALIDAD = '"+nacionalidad+"'," + 
 				" TEL_TRABAJO = '"+telTrabajo+"'," + 
 				" ESTADO = '"+estado+"'," + 
 				" GENERO = '"+genero+"'" +
 				" WHERE CODIGO_ID = '"+codigoId+"'";
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
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_ALUMNO "+ 
 				"WHERE CODIGO_ID = ? ");
 			ps.setString(1, codigoId);
 			
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
 		codigoId 			= rs.getString("CODIGO_ID");
 		nombre 				= rs.getString("NOMBRE");
 		apellidoPaterno 	= rs.getString("APELLIDO_PATERNO");
 		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
 		fechaNac 			= rs.getString("FECHA_NAC");
 		institucionId		= rs.getString("INSTITUCION_ID");
 		seguro 				= rs.getString("SEGURO");
 		tutor 				= rs.getString("TUTOR");
 		telefono 			= rs.getString("TELEFONO");
 		celular				= rs.getString("CELULAR");
 		email 				= rs.getString("EMAIL");
 		codigoUM			= rs.getString("CODIGO_UM");
 		comentario			= rs.getString("COMENTARIO");
 		empleado			= rs.getString("EMPLEADO");
 		religionId			= rs.getString("RELIGION_ID");
 		codigoUsuario		= rs.getString("CODIGO_USUARIO");
 		ciudadId 			= rs.getString("CIUDAD_ID");
 		estadoId			= rs.getString("ESTADO_ID");
 		paisId				= rs.getString("PAIS_ID");
 		nacionalidad		= rs.getString("NACIONALIDAD");
 		telTrabajo			= rs.getString("TEL_TRABAJO");
 		estado				= rs.getString("ESTADO");
 		genero				= rs.getString("GENERO");
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, "
	 				+ " APELLIDO_MATERNO, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') FECHA_NAC, "
	 				+ " INSTITUCION_ID,SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, "
	 				+ " COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO, CIUDAD_ID, "
	 				+ " ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
	 				+ " FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ? ");
	 		ps.setString(1, codigoId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumno|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ?");
 			ps.setString(1, codigoId);
 			
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
 	
 	public String maximoReg(Connection conn) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;		
		String year				= "";
		String maximo			= "";
		
		try{
			year = aca.util.Fecha.getHoy().substring(8,10);
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SUBSTR(CODIGO_ID,3,3))+1,1) AS MAXIMO FROM ENOC.MUSI_ALUMNO"); 
			rs = ps.executeQuery();
			if (rs.next()){
				maximo = rs.getString("MAXIMO");
				if (maximo.length()==1) maximo = year+"00"+maximo;
				if (maximo.length()==2) maximo = year+"0"+maximo;
				if (maximo.length()==3) maximo = year+maximo;
			}else{
				maximo = year+"001";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumno|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
 	
 	public String maximoReg(Connection conn, String year) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;		
		String maximo			= "";
		
		try{			
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SUBSTR(CODIGO_ID,3,3))+1,1) AS MAXIMO "
					+ " FROM ENOC.MUSI_ALUMNO WHERE SUBSTR(CODIGO_ID,1,2) = ?"); 
			ps.setString(1, year);
			rs = ps.executeQuery();
			if (rs.next()){
				maximo = rs.getString("MAXIMO");
				if (maximo.length()==1) maximo = year+"00"+maximo;
				if (maximo.length()==2) maximo = year+"0"+maximo;
				if (maximo.length()==3) maximo = year+maximo;
			}else{
				maximo = year+"001";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumno|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
 	
	public static String getNombre(Connection conn, String codigoId, String opcion) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			if ( opcion.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "
						+ " FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = '"+codigoId+"'"; 
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "
						+ " FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = '"+codigoId+"'"; 
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
	
	public static String getNombreDividido(Connection conn, String codigoId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT NOMBRE||','||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "
					+ " FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = '"+codigoId+"'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumno|getNombreDividido|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public  String getEdad(Connection conn ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String edad 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT FLOOR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), FECHA_NAC)/12) AS EDAD"
					+ " FROM ENOC.MUSI_ALUMNO"
					+ " WHERE CODIGO_ID = ?");			
			ps.setString(1, codigoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				edad = rs.getString("EDAD");
			
		}catch(Exception ex){
			System.out.println("Error - adm.musica.MusiAlumno|getEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return edad;
	}
	
	public static String getInstitucion(Connection conn, String codigoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String institucion 		= "1";
		
		try{
			ps = conn.prepareStatement("SELECT INSTITUCION_ID "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID = ?");
			ps.setString(1, codigoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				institucion = rs.getString("INSTITUCION_ID");
			
		}catch(Exception ex){
			System.out.println("Error - adm.musica.MusiAlumno|getInstituicion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return institucion;
	}
	
	public static String getTelefono(Connection conn, String codigoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String telefono 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TELEFONO,'-') "
					+ " AS TELEFONO FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID = ?");
			ps.setString(1, codigoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				telefono = rs.getString("TELEFONO");
			
		}catch(Exception ex){
			System.out.println("Error - adm.musica.MusiAlumno|getTelefono|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return telefono;
	}
 	
	public static String getCelular(Connection conn, String codigoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String celular 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(CELULAR,'-') "
					+ " AS CELULAR FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID = ?");
			ps.setString(1, codigoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				celular = rs.getString("CELULAR");
			
		}catch(Exception ex){
			System.out.println("Error - adm.musica.MusiAlumno|getCelular|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return celular;
	}
 }