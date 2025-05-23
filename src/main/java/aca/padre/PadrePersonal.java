 // Bean de datos personales del padre 
 package  aca.padre;
 
 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.StringTokenizer;

 public class PadrePersonal{
 	private String padreId;
 	private String nombre;
 	private String paterno;
 	private String materno; 	
 	private String correo;
 	private String telefono;
 	private String tipo;
 	
 	public PadrePersonal(){
 		padreId				= "";
 		nombre				= "";
 		paterno				= "";
 		materno				= ""; 		
 		correo				= ""; 		
 		telefono			= "";
 		tipo				= "";
 	} 	

	/**
	 * @return the padreId
	 */
	public String getPadreId() {
		return padreId;
	}



	/**
	 * @param padreId the padreId to set
	 */
	public void setPadreId(String padreId) {
		this.padreId = padreId;
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
	 * @return the paterno
	 */
	public String getPaterno() {
		return paterno;
	}



	/**
	 * @param paterno the paterno to set
	 */
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}



	/**
	 * @return the materno
	 */
	public String getMaterno() {
		return materno;
	}



	/**
	 * @param materno the materno to set
	 */
	public void setMaterno(String materno) {
		this.materno = materno;
	}



	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}



	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}



	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.PADRE_PERSONAL"+ 
 				"(PADRE_ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, TIPO" );
 	 		

 			
 			ps.setString(1, padreId);
 			ps.setString(2, nombre);
 			ps.setString(3, paterno);
 			ps.setString(4, materno);
 			ps.setString(5, correo);
 			ps.setString(6, telefono);
 			ps.setString(7, tipo);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|insertReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.PADRE_PERSONAL "+ 
 				"SET "+				
 				"NOMBRE = ?, "+
 				"PADRE_ID = ?, "+
 				"NOMBRE = ?, "+
 				"PATERNO = ?, "+
 				"MATERNO = ?, "+
 				"CORREO = ?, "+
 				"TELEFONO = ?, "+
 				"TIPO = TO_NUMBER(COALESCE(?, '1'),'99'), "
 				);
 				
 			
 			ps.setString(1, padreId);
 			ps.setString(2, nombre);
 			ps.setString(3, paterno);
 			ps.setString(4, materno);
 			ps.setString(5, correo);
 			ps.setString(6, telefono);
 			ps.setString(7, tipo); 			
 			

 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
/* 	public boolean updateNacionalidad(Connection conn, String codigoPersonal, String nacionalidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL "+ 
				" SET NACIONALIDAD = '"+nacionalidad+"' " +
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	} 	
 	
 	public boolean updateCotejado(Connection conn, String codigoPersonal, String cotejado) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL"+ 
 				" SET COTEJADO = ?"+
 				" WHERE CODIGO_PERSONAL = ? ");
 				
 			ps.setString(1, cotejado);
 			ps.setString(2, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.PadrePersonal|updateCotejado|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
*/ 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.PADRE_PERSONAL "+ 
 				"WHERE PADRE_ID = ? ");
 			ps.setString(1, padreId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		padreId		 		= rs.getString("PADRE_ID");
 		nombre 				= rs.getString("NOMBRE");
 		paterno 			= rs.getString("PATERNO");
 		materno				= rs.getString("MATERNO");
 		correo	 			= rs.getString("CORREO");
 		telefono			= rs.getString("TELEFONO");
 		tipo 				= rs.getString("TIPO");
 	}
 	
 	public void mapeaRegId( Connection conn, String padreId ) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT PADRE_ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, TIPO"+	
	 			" FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ?");
	 		ps.setString(1, padreId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.PADRE_PERSONAL "+ 
 				"WHERE PADRE_ID = ?");
 			ps.setString(1, padreId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg(Connection conn) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null; 		
 		int maximo 				= 1;
 		String padreId			= "P000001";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(MAX(TO_NUMBER(SUBSTR(PADRE_ID,2,6))+1),1) as MAXIMO FROM ENOC.PADRE_PERSONAL");
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				maximo = rs.getInt("MAXIMO");
 				if (String.valueOf(maximo).length() == 1) 
 					padreId = "P00000"+ String.valueOf(maximo);
 				else if (String.valueOf(maximo).length() == 2) 
 					padreId = "P0000"+ String.valueOf(maximo);
 				else if (String.valueOf(maximo).length() == 3) 
 					padreId = "P000"+ String.valueOf(maximo);
 				else if (String.valueOf(maximo).length() == 4) 
 					padreId = "P00"+ String.valueOf(maximo);
 				else if (String.valueOf(maximo).length() == 5) 
 					padreId = "P0"+ String.valueOf(maximo);
 				else if (String.valueOf(maximo).length() == 6) 
 					padreId = "P"+ String.valueOf(maximo); 					
 			}	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return padreId;
 	}
 	
 	public static boolean esPadre( Connection conn, String padreId ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT PADRE_ID FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ? ");
 			ps.setString(1, padreId);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				ok = true;
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|esPadre|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
/* 	public static boolean esInscrito(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				ok = true;
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.PadrePersonal|esInscrito|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}*/


 	
/* 	public static String getFNacimiento(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String fecha 			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");	
 			rs = ps.executeQuery();
 			if (rs.next()){
 				fecha = rs.getString("F_NACIMIENTO");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.PadrePersonal|getFNacimiento|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return fecha;
 	}*/
 	

 	
 	public static String getNombrePadre(Connection conn, String padre_Id, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||PATERNO||' '||MATERNO AS NOMBRE FROM ENOC.PADRE_PERSONAL "+ 
 					"WHERE PADRE_ID = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT PATERNO||' '||MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.PADRE_PERSONAL "+ 
 					"WHERE PADRE_ID = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||PATERNO||' '||MATERNO AS NOMBRE FROM ENOC.PADRE_PERSONAL "+ 
 					"WHERE PADRE_ID = ? ");
 			}			
 			ps.setString(1,padre_Id); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadrePersonal|getNombreAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	

 	
/* 	
 	public static boolean getValidaUsuario(Connection conn, String usuario) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		boolean valida	 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT USUARIO FROM ENOC.ALUM_PERSONAL "+ 
 				"WHERE USUARIO = ? "); 					
 			ps.setString(1,usuario);
 			rs = ps.executeQuery(); 
 			if (rs.next()){ 				 
 				valida = true;
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.PadrePersonal|getValidaUsuario|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return valida;
 	}
 	
 	public static boolean getValidaClave(Connection conn, String usuario, String clave) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		boolean valida	 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT CLAVE FROM ENOC.ALUM_PERSONAL "+ 
 				"WHERE USUARIO = ? AND CLAVE = ?");				
 			ps.setString(1,usuario);
 			ps.setString(2,clave);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				if (rs.getString("CLAVE").equals(clave))
 					valida = true;
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.PadrePersonal|getValidaClave|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return valida;
 	}	
*/
 	
 	public static String getNombreCorto(Connection conn, String padreId) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT NOMBRE, PATERNO, COALESCE(MATERNO,'.') AS MATERNO" +
 					" FROM ENOC.PATERNO_PERSONAL" + 
 					" WHERE PADRE_ID = ?");
 			ps.setString(1, padreId);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				nombre = rs.getString("NOMBRE") + " ";
 				apellido = rs.getString("PATERNO")+" "+rs.getString("MATERNO").substring(0,1)+".";
 			}
 			
 			if(!nombre.equals("") && !apellido.equals("")){
 				StringTokenizer token = new StringTokenizer(nombre," ");
 				temp = token.nextToken();
 				
 				if(!temp.equals("")){
 					nombre = temp;
 					if(token.countTokens() >= 1){
 						temp = token.nextToken();
 						nombre += " " + temp.charAt(0) + ".";
 					}
 				}
 				nombre += " " + apellido;
 			}

 		}catch(Exception ex){
 			System.out.println("Error  - aca.padre.PadrePersonal|getNombreCorto|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 	public static String getNombreMuyCorto(Connection conn, String padreId) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT NOMBRE, PATERNO, COALESCE(MATERNO,'.') AS MATERNO" +
 					" FROM ENOC.PADRE_PERSONAL" + 
 					" WHERE PADRE_ID = ?");
 			ps.setString(1, padreId);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				nombre = rs.getString("NOMBRE") + " ";
 				apellido = rs.getString("PATERNO");
 			}
 			
 			if(!nombre.equals("") && !apellido.equals("")){
 				StringTokenizer token = new StringTokenizer(nombre," ");
 				temp = token.nextToken();
 				
 				if(!temp.equals("")){
 					nombre = temp;
 					if(token.countTokens() >= 1){
 						temp = token.nextToken();
 						nombre += " " + temp.charAt(0) + ".";
 					}
 				}
 				nombre += " " + apellido;
 			}

 		}catch(Exception ex){
 			System.out.println("Error  - aca.padre.PadrePersonal|getNombreMuyCorto|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 }