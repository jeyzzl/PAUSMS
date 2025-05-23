// Clase para la tabla de AlumPersonal
package aca.alumno;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import aca.util.Fecha;

public class AlumUtil{
	
	public boolean insertReg(Connection conn, AlumPersonal alumno ) throws Exception{
		
 		PreparedStatement ps = null;
 		boolean ok = false;
 		try{ 			
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_PERSONAL"+ 
 				"(CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "+
 				"F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, "+
 				"NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA,TELEFONO) "+
 				"VALUES( ?, ?, ?, ?, ?, "+
 				"TO_DATE(?,'DD/MM/YYYY'), "+
 				"?, "+
 				"?, "+
 				"TO_NUMBER(COALESCE(?, '1'),'99'), "+
 				"?, "+
 				"TO_NUMBER(COALESCE(?, '91'),'999'), "+
 				"TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				"TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				"TO_NUMBER(COALESCE(?, '91'),'999'), "+
 				"?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?,?)");
 			ps.setString(1, alumno.getCodigoPersonal());
 			ps.setString(2, alumno.getNombre());
 			ps.setString(3, alumno.getApellidoPaterno());
 			ps.setString(4, alumno.getApellidoMaterno());
 			ps.setString(5, alumno.getNombreLegal());
 			ps.setString(6, alumno.getFNacimiento());
 			ps.setString(7, alumno.getSexo());
 			ps.setString(8, alumno.getEstadoCivil());
 			ps.setString(9, alumno.getReligionId());
 			ps.setString(10, alumno.getBautizado());
 			ps.setString(11, alumno.getPaisId());
 			ps.setString(12, alumno.getEstadoId());
 			ps.setString(13, alumno.getCiudadId());
 			ps.setString(14, alumno.getNacionalidad());
 			ps.setString(15, alumno.getEmail());			
 			ps.setString(16, alumno.getCurp());
 			ps.setString(17, alumno.getEstado());
 			ps.setString(18, alumno.getCotejado());
 			ps.setString(19, alumno.getCodigoSe());
 			ps.setString(20, alumno.getFcreado());
 			ps.setString(21, alumno.getUsAlta());
 			ps.setString(22, alumno.getTelefono());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|insertReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { } 			
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumPersonal alumno ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL "+ 
 				"SET "+				
 				"NOMBRE = ?, "+
 				"APELLIDO_PATERNO = ?, "+
 				"APELLIDO_MATERNO = ?, "+
 				"NOMBRE_LEGAL = ?, "+
 				"F_NACIMIENTO = TO_DATE(?,'DD/MM/YYYY'), "+
 				"SEXO = ?, "+
 				"ESTADO_CIVIL = ?, "+
 				"RELIGION_ID = TO_NUMBER(COALESCE(?, '1'),'99'), "+
 				"BAUTIZADO = ?, "+
 				"PAIS_ID = TO_NUMBER(COALESCE(?, '0'),'999'), "+
 				"ESTADO_ID = TO_NUMBER(COALESCE(?, '0'),'999'), "+
 				"CIUDAD_ID = TO_NUMBER(COALESCE(?, '0'),'999'), "+
 				"NACIONALIDAD = TO_NUMBER(COALESCE(?, '0'),'999'), " +
 				"EMAIL = ?, "+
 				"CURP = ?, "+
 				"ESTADO = ?, "+
 				"COTEJADO = ?, " +
 				"CODIGO_SE = ?, " + 	
 				"TELEFONO = ? " + 	
 				"WHERE CODIGO_PERSONAL = ? ");
 				
 			ps.setString(1, alumno.getNombre()); 			
 			ps.setString(2, alumno.getApellidoPaterno());
 			ps.setString(3, alumno.getApellidoMaterno());
 			ps.setString(4, alumno.getNombreLegal());
 			ps.setString(5, alumno.getFNacimiento());
 			ps.setString(6, alumno.getSexo());
 			ps.setString(7, alumno.getEstadoCivil());
 			ps.setString(8, alumno.getReligionId());
 			ps.setString(9, alumno.getBautizado());
 			ps.setString(10, alumno.getPaisId());
 			ps.setString(11, alumno.getEstadoId());
 			ps.setString(12, alumno.getCiudadId());
 			ps.setString(13, alumno.getNacionalidad());
 			ps.setString(14, alumno.getEmail());
 			ps.setString(15, alumno.getCurp());
 			ps.setString(16, alumno.getEstado());
 			ps.setString(17, alumno.getCotejado());
 			ps.setString(18, alumno.getCodigoSe());
 			ps.setString(19, alumno.getTelefono());
 			ps.setString(20, alumno.getCodigoPersonal()); 			
 			
 			AcademicoUtil alumAca = new AcademicoUtil();
 			if(alumno.getReligionId().equals("1")) 
 				alumAca.updateClasFin(conn,alumno.getCodigoPersonal(),"1");
 			else 
 				alumAca.updateClasFin(conn,alumno.getCodigoPersonal(),"2");
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	public boolean updateNacionalidad(Connection conn, String codigoPersonal, String nacionalidad ) throws Exception{
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
			System.out.println("Error - aca.alumno.AlumUtil|updateNacionalidad|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
 	
 	public boolean updateCodigoSe(Connection conn, String codigoPersonal, String codigo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL "+ 
				" SET CODIGO_SE = '"+codigo+"' " +
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|updateCodigoSe|:"+ex);	 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
 	
 	public boolean updateCredencial(Connection conn, String codigoPersonal, String codigo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL "+ 
				" SET CREDENCIAL = '"+codigo+"' " +
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|updateCredencial|:"+ex);	 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
 	
 	public static String getCodigoDecimal(Connection conn, String codigoPersonal, String exadecimal) throws SQLException{
 		
 		PreparedStatement ps	= null; 		
 		ResultSet 		rs		= null;
 		String codigo			= "-";
 		
 		try{
 			ps = conn.prepareStatement("SELECT TO_CHAR(TO_NUMBER(SUBSTR('"+exadecimal+"',5,6),'XXXXXXXXXX'),'0000000000') AS RFID FROM DUAL");						
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				codigo = rs.getString("RFID");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getCodigoDecimal|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return codigo;
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
 			System.out.println("Error - aca.alumno.AlumUtil|updateCotejado|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_PERSONAL "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	} 	
 	
 	public AlumPersonal mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		
 		PreparedStatement ps 	= null;
 		ResultSet rs 			= null;
 		AlumPersonal alumno 	= new AlumPersonal();
 		
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
	 			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
	 			" COALESCE(SEXO, 'M') SEXO, COALESCE(ESTADO_CIVIL, 'S') ESTADO_CIVIL,"+
	 			" COALESCE(RELIGION_ID, 1) RELIGION_ID, COALESCE(BAUTIZADO,'S') BAUTIZADO,"+
	 			" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"+
	 			" EMAIL, CURP, COALESCE(ESTADO,'A') ESTADO, COALESCE(COTEJADO,'N') COTEJADO,"+
	 			" CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, CREDENCIAL"+
	 			" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			alumno.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return alumno;
 	}

 	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PERSONAL "+ 
 				"WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true; 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean existeReg(String codigoPersonal) throws SQLException{
 		
 		Connection conn 		= null;
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean 		ok 		= false; 		
 		try{
 			aca.conecta.Conectar conEnoc = new aca.conecta.Conectar();
			conn = conEnoc.conEnoc();
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
	 		try { conn.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static boolean existeAlumno(Connection conn, String codigoPersonal) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg(Connection conn, String Tipo) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String maximo			= "";
 		String anio				= Fecha.getHoy().substring(8,10);
 		
 		try{
 			if (Tipo.equals("UM")){
 				ps = conn.prepareStatement("SELECT COALESCE(MAX(CODIGO_PERSONAL)+1, 1"+anio+"0001) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,3) = '1"+anio+"'");
 				/*System.out.println("SELECT COALESCE(MAX(CODIGO_PERSONAL)+1,'1"+anio+"0001') AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 	 					"WHERE SUBSTR(CODIGO_PERSONAL,1,3) = '1"+anio+"' " +
 	 					"AND CODIGO_PERSONAL < '1085000'");*/
 			}else if (Tipo.equals("Empleado")){
 				ps = conn.prepareStatement("SELECT MAX(CODIGO_PERSONAL)+1 MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,1) = '9' ");
 			}else if(Tipo.equals("Tabasco")){
 				ps = conn.prepareStatement("SELECT COALESCE(MAX(CODIGO_PERSONAL)+1, 2"+anio+"0001) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 	 					"WHERE SUBSTR(CODIGO_PERSONAL,1,3) = '2"+anio+"'");
 			}else{	
 				ps = conn.prepareStatement("SELECT MAX(CODIGO_PERSONAL)+1 MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,1) = 'P' ");
 			}			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			else
 				maximo = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
 	
 	public static boolean esInscrito( Connection conn, String codigoPersonal ) throws SQLException{
 		
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
 			System.out.println("Error - aca.alumno.AlumUtil|esInscrito|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static int numComidas(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		int n 					= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT NUM_COMIDAS FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?");				 
 			ps.setString(1,codigoPersonal);
 			ps.setString(2,cargaId);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				n = rs.getInt(1);
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|numComidas|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return n;
 	}
 	
 	public static int getEdad(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		int n 					= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12 FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?");		 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				n = rs.getInt(1);
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getEdad|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return n;
 	}
 	
 	public static String getFNacimiento(Connection conn, String codigoPersonal) throws SQLException{
 		
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
 			System.out.println("Error - aca.alumno.AlumUtil|getFNacimiento|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return fecha;
 	}
 	
 	public static String tipoComidas(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String comidas="";
 		
 		try{
 			ps = conn.prepareStatement("SELECT TIPO_COMIDA FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?"); 
 			ps.setString(1,codigoPersonal);
 			ps.setString(2,cargaId);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				comidas = rs.getString(1);
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|tipoComidas|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return comidas;
 	}
 	
 	public static String getNombreAlumno(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getNombreAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 	public static String getAlumno(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else if (opcion.equals("APELLIDOS")){
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE CODIGO_PERSONAL = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 	public static boolean validarCurp(String curp){
 		if (curp==null) curp = "-";
 		curp = curp.toUpperCase().trim();
 		return curp.matches("[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z,0-9]{2}");
	}
 	
 	
 	public static String getFechaIngreso(Connection conn, String codigoPersonal, String planId) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String fecha	 		= "01/01/1900";
 		
 		try{
 			ps = conn.prepareStatement("SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA" +
 									   " FROM ENOC.ALUM_PLAN" + 
 									   " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
 									   " AND PLAN_ID= '"+planId+"'");				
 			rs = ps.executeQuery();
 			if (rs.next()){
 				fecha = rs.getString("FECHA");
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getFechaIngreso|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return fecha;
 	}
 	
 	public static String getNombreCorto(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT NOMBRE, APELLIDO_PATERNO, COALESCE(APELLIDO_MATERNO,'.') AS MATERNO" +
 					" FROM ENOC.ALUM_PERSONAL" + 
 					" WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				nombre = rs.getString("NOMBRE") + " ";
 				apellido = rs.getString("APELLIDO_PATERNO")+" "+rs.getString("MATERNO").substring(0,1)+".";
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
 			System.out.println("Error  - aca.alumno.AlumUtil|getNombreCorto|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 	public static String getNombreMuyCorto(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT NOMBRE, APELLIDO_PATERNO, COALESCE(APELLIDO_MATERNO,'.') AS MATERNO" +
 					" FROM ENOC.ALUM_PERSONAL" + 
 					" WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				nombre = rs.getString("NOMBRE") + " ";
 				apellido = rs.getString("APELLIDO_PATERNO");
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
 			System.out.println("Error  - aca.alumno.AlumUtil|getNombreMuyCorto|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
 	
 	public static String getCotejado(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String cotejado	 		= ""; 		
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(COTEJADO,'N') AS COTEJADO" +
 					" FROM ENOC.ALUM_PERSONAL" + 
 					" WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				cotejado = rs.getString("COTEJADO");
 			} 			

 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.AlumUtil|getCotejado|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cotejado;
 	}
 	
 	public static String getCurp(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		String curp	 			= ""; 		
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(CURP,'-') AS CURP" +
 					" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
 			ps.setString(1, codigoPersonal);
 			rs = ps.executeQuery();
 			
 			if (rs.next()){
 				curp = rs.getString("CURP");
 			} 			

 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.AlumUtil|getCurp|: "+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return curp;
 	}
 	
 	public static String getPaisId(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet rs			= null;
 		PreparedStatement ps	= null; 		
 		String paisId			= "0";
 		
 		try{
 			ps = conn.prepareStatement("SELECT PAIS_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'");	 
 			rs = ps.executeQuery();
 			if (rs.next()){
 				paisId = rs.getString("PAIS_ID");
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getPaisId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return paisId;
 	}
 	
 	public static String getEstadoId(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet rs			= null;
 		PreparedStatement ps	= null; 		
 		String estado			= "0";
 		
 		try{
 			ps = conn.prepareStatement("SELECT ESTADO_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'");	 
 			rs = ps.executeQuery();
 			if (rs.next()){
 				estado = rs.getString("ESTADO_ID");
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getEstadoId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return estado;
 	}
 	
 	public static String getNacionalidad(Connection conn, String codigoPersonal) throws SQLException{

 		ResultSet rs			= null;
 		PreparedStatement ps	= null; 		
 		String paisId			= "91";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(NACIONALIDAD,91) AS NACIONALIDAD FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'");		 
 			rs = ps.executeQuery();
 			if (rs.next()){
 				paisId = rs.getString("NACIONALIDAD");
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getNacionalidad|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return paisId;
 	}

 	public static String getPromedioFinal(Connection conn, String matricula, String planId) throws SQLException{

 		ResultSet rs			= null;
 		PreparedStatement ps	= null; 		
 		String promedio			= "X";
 		
 		try{
 			ps = conn.prepareStatement("SELECT ALUM_PROMEDIO(CODIGO_PERSONAL, '"+planId+"') AS PROMEDIO" +
 					" FROM ENOC.ALUM_PERSONAL" + 
 					" WHERE CODIGO_PERSONAL = '"+matricula+"'");				
 			rs = ps.executeQuery();
 			if (rs.next()){
 				promedio = rs.getString("PROMEDIO");
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getPromedioFinal|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return promedio;
 	}	

 	public static String getCargasAlumno(Connection conn, String fecha, String codigoPersonal ) throws Exception{
 		PreparedStatement ps	 = null;
 		ResultSet rs			= null;		
		String cargas 			= "X";
		int row 				= 1;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA" + 
				" WHERE TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL" +
				" AND CARGA_ID IN " +
				"	(SELECT CARGA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND ESTADO='I')" + 
				" ORDER BY CARGA_ID");
			ps.setString(1, fecha);
			ps.setString(2, codigoPersonal);
			
			rs= ps.executeQuery();
			while (rs.next()){ 
				if (cargas.equals("X")) cargas="";
				if (row>1) cargas += ",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getCargasAlumno|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return cargas;
	} 	
 	
 	public static String getAronPais(Connection conn, String codigoPersonal ) throws Exception{
 		PreparedStatement ps	= null;
 		ResultSet rs			= null;		
		String rfc 				= "X";
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ARON.PAIS " +
					"WHERE ID = (SELECT ID_PAIS FROM ARON.ESTADO " +
					"	WHERE ID = (SELECT C.ID_ESTADO FROM ARON.CIUDAD C " +
					"		WHERE ID = (SELECT ID_CIUDAD FROM ARON.EMPLEADOPERSONALES " +
					"			WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?))))");
			
			ps.setString(1, codigoPersonal);
			
			rs= ps.executeQuery();
			if(rs.next()){ 
				rfc = rs.getString("RFC");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getRFC|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return rfc;
	}
 	
 	public static String codigoSe( Connection conn, String codigoPersonal ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String codigoSe			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT CODIGO_SE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ");				 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				codigoSe = rs.getString("CODIGO_SE");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|codigoSe|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return codigoSe;
 	}
 	
 	public static String getGenero( Connection conn, String codigoPersonal ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String codigoSe			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT SEXO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "); 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				codigoSe = rs.getString("SEXO");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getGenero|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return codigoSe;
 	}
 	
 	public static String getReligion( Connection conn, String codigoPersonal ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String codigoSe			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT RELIGION_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "); 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				codigoSe = rs.getString("RELIGION_ID");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|getReligion|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return codigoSe;
 	}
 	
 	public static String esColportor( Connection conn, String codigoPersonal ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String tipo				= "A";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(DISCRIMINATOR_COL,'A') AS COLPORTOR FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				tipo = rs.getString("COLPORTOR");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|esColportor|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return tipo;
 	}
 	
 	public static boolean esVocal(char c){
        if( c=='A' || c=='E' || c=='I' || c=='O' || c =='U' || 
        	c=='Á' || c=='É' || c=='Í' || c=='Ó' || c =='Ú'
        		
        ){
            return true;
        }else{
            return false;
        }
    }
 	
 	public static String validaDatosDeCurp( String curp, String nombre, String paterno, String materno, String fecha, String genero, String paisId, String estadoId ) throws SQLException{
 		
 		String resultado	= "";
 		String curpDeDatos 	= ""; 
 		
 		try{
 			// Reemplazar caracteres especiales
 			paterno.replace("Ñ", "X").replace("-", "X").replace(".", "X").replace("/","X").replace("Ü","U");
 			paterno.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			
 			// QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String arrayPaterno[] = paterno.split(" ");
 			if (arrayPaterno.length > 1){
 				paterno = "";
 				for (int j = 0; j < arrayPaterno.length;j++){
 					if (!arrayPaterno[j].equals("DA")&&!arrayPaterno[j].equals("DAS")&&!arrayPaterno[j].equals("DE")&&!arrayPaterno[j].equals("DEL")&&
 						!arrayPaterno[j].equals("DER")&&!arrayPaterno[j].equals("DI")&&!arrayPaterno[j].equals("DIE")&&!arrayPaterno[j].equals("DD")&&
 						!arrayPaterno[j].equals("EL")&&!arrayPaterno[j].equals("LA")&&!arrayPaterno[j].equals("LOS")&&!arrayPaterno[j].equals("LAS")&&
 						!arrayPaterno[j].equals("LE")&&!arrayPaterno[j].equals("LES")&&!arrayPaterno[j].equals("MAC")&&!arrayPaterno[j].equals("MC")&&
 						!arrayPaterno[j].equals("VAN")&&!arrayPaterno[j].equals("VON")&&!arrayPaterno[j].equals("Y")
 						){
 						paterno += arrayPaterno[j];
 					}
 				}
 			}
 			
 			if (paterno.length() > 1){
 				// Primer caracter
 				curpDeDatos += paterno.substring(0,1);
 				// Segundo caracter (vocal interna)
 				for(int i=1; i <= paterno.length()-1; i++){
 	                if( esVocal(paterno.charAt(i)) ){ 
 	                    curpDeDatos += paterno.charAt(i);
 	                    break;
 	                }
 	            }
 				if (curpDeDatos.length()==1){
 					curpDeDatos += "X";
 				}
 			}
 			
 			materno.replace("Ñ", "X").replace("-", "X").replace(".", "X").replace("/","X").replace("Ü","U");
 			materno.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			materno = materno.trim();
 			
 			// QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String arrayMaterno[] = materno.split(" ");
 			if (arrayMaterno.length > 1){
 				materno = "";
 				for (int j = 0; j < arrayMaterno.length;j++){
 					if (!arrayMaterno[j].equals("DA")&&!arrayMaterno[j].equals("DAS")&&!arrayMaterno[j].equals("DE")&&!arrayMaterno[j].equals("DEL")&&
 						!arrayMaterno[j].equals("DER")&&!arrayMaterno[j].equals("DI")&&!arrayMaterno[j].equals("DIE")&&!arrayMaterno[j].equals("DD")&&
 						!arrayMaterno[j].equals("EL")&&!arrayMaterno[j].equals("LA")&&!arrayMaterno[j].equals("LOS")&&!arrayMaterno[j].equals("LAS")&&
 						!arrayMaterno[j].equals("LE")&&!arrayMaterno[j].equals("LES")&&!arrayMaterno[j].equals("MAC")&&!arrayMaterno[j].equals("MC")&&
 						!arrayMaterno[j].equals("VAN")&&!arrayMaterno[j].equals("VON")&&!arrayMaterno[j].equals("Y")
 						){
 						materno += arrayMaterno[j];
 					}
 				}
 			}			
 			
 			// Tercer caracter
 			if (materno.length() > 1){
 				curpDeDatos += materno.substring(0,1);
 			}else{ 				
 				curpDeDatos += "X";
 				materno = "XXXX";
 			}
 			
 			nombre.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			
 			//QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String arrayNombre[] = nombre.split(" ");
 			if (arrayNombre.length > 1){
 				nombre = "";
 				for (int j = 0; j < arrayNombre.length;j++){
 					if (!arrayNombre[j].equals("DA")&&!arrayNombre[j].equals("DAS")&&!arrayNombre[j].equals("DE")&&!arrayNombre[j].equals("DEL")&&
 						!arrayNombre[j].equals("DER")&&!arrayNombre[j].equals("DI")&&!arrayNombre[j].equals("DIE")&&!arrayNombre[j].equals("DD")&&
 						!arrayNombre[j].equals("EL")&&!arrayNombre[j].equals("LA")&&!arrayNombre[j].equals("LOS")&&!arrayNombre[j].equals("LAS")&&
 						!arrayNombre[j].equals("LE")&&!arrayNombre[j].equals("LES")&&!arrayNombre[j].equals("MAC")&&!arrayNombre[j].equals("MC")&&
 						!arrayNombre[j].equals("VAN")&&!arrayNombre[j].equals("VON")&&!arrayNombre[j].equals("Y")
 						){
 						nombre += arrayNombre[j]+" ";
 					} 
 				}
 			}
 			
 			nombre = nombre.trim();		 		
 			arrayNombre = nombre.split(" ");
 			if (arrayNombre.length > 1){
 				int pos = nombre.indexOf(" ");
 				if (arrayNombre[0].equals("JOSE") || arrayNombre[0].equals("MARIA")||
 		 			arrayNombre[0].equals("MA") || arrayNombre[0].equals("MA.")||
 		 			arrayNombre[0].equals("J") || arrayNombre[0].equals("J.")
 		 		){						
 		 			nombre = nombre.substring(pos).trim();
 				}				 									
 			}
 			
 			// Cuarto caracter
 			curpDeDatos += nombre.substring(0,1);
 			
 			// Agregar caracteres 5,6 
 			curpDeDatos += fecha.substring(8,10);
 			
 			// Agregar caracteres 7,8 
 			curpDeDatos += fecha.substring(3,5);
 			
 			// Agregar caracteres 9,10 
 			curpDeDatos += fecha.substring(0,2);
 			
 			// Agregar caracter 11 
 			curpDeDatos += genero;
			
 			 			
 			if (!paisId.equals("91")){
 				curpDeDatos += "NE";
 			}else if (!estadoId.equals("0")){
 				int estado = Integer.parseInt(estadoId);
 				switch(estado){
 				case 1: curpDeDatos += "AS"; break;
 				case 2: curpDeDatos += "BC"; break;
 				case 3: curpDeDatos += "BS"; break;
 				case 4: curpDeDatos += "CC"; break;
 				case 5: curpDeDatos += "CL"; break;
 				case 6: curpDeDatos += "CM"; break;
 				case 7: curpDeDatos += "CS"; break;
 				case 8: curpDeDatos += "CH"; break;
 				case 9: curpDeDatos += "DF"; break;
 				case 10: curpDeDatos += "DG"; break;
 				case 11: curpDeDatos += "GT"; break;
 				case 12: curpDeDatos += "GR"; break;
 				case 13: curpDeDatos += "HG"; break;
 				case 14: curpDeDatos += "JC"; break;
 				case 15: curpDeDatos += "MC"; break; 				
 				case 16: curpDeDatos += "MN"; break;
 				case 17: curpDeDatos += "MS"; break;
 				case 18: curpDeDatos += "NT"; break;
 				case 19: curpDeDatos += "NL"; break;
 				case 20: curpDeDatos += "OC"; break;
 				case 21: curpDeDatos += "PL"; break;
 				case 22: curpDeDatos += "QT"; break;
 				case 23: curpDeDatos += "QR"; break; 				
 				case 24: curpDeDatos += "SP"; break;
 				case 25: curpDeDatos += "SL"; break;
 				case 26: curpDeDatos += "SR"; break;
 				case 27: curpDeDatos += "TC"; break;
 				case 28: curpDeDatos += "TS"; break;
 				case 29: curpDeDatos += "TL"; break;
 				case 30: curpDeDatos += "VZ"; break;
 				case 31: curpDeDatos += "YN"; break;
 				case 32: curpDeDatos += "ZS"; break; 				
 				} 				
 			}else {
 				// Estado igual a cero
 				curpDeDatos += "XX";
 			}
 			
 			// Caracter 14 
 			boolean ok = false;
			for(int i=1; i <= paterno.length()-1; i++){
				if( !esVocal(paterno.charAt(i)) ){
					if (paterno.charAt(i)!='Ñ')
						curpDeDatos += paterno.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;	                
	            }
	        }
			if (!ok) curpDeDatos += "X";
			
			// Caracter 15 
			ok = false;
			for(int i=1; i <= materno.length()-1; i++){
				if( !esVocal(materno.charAt(i)) ){
					if (materno.charAt(i)!='Ñ')
						curpDeDatos += materno.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;
	            }				
	        }
			if (!ok) curpDeDatos += "X";
			
			// Caracter 16
			ok = false;
			for(int i=1; i <= nombre.length()-1; i++){
				if( !esVocal(nombre.charAt(i)) ){ 
					if (nombre.charAt(i)!='Ñ')
						curpDeDatos += nombre.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;	               
	            }
	        }
			if (!ok) curpDeDatos += "X";
			
			// Iniciales del nombre
			if (curp.length() >= 4 && curpDeDatos.length() >= 4){
			
				if (curp.substring(0,4).equals(curpDeDatos.substring(0,4))){
					resultado = "1";
				}else{
					resultado = "0";
				}
			}else{
				resultado += "0";
			}
			
			// Fecha nacimiento
			if (curp.length() >= 10 && curpDeDatos.length() >= 10){
	 			 
				if (curp.substring(4,10).equals(curpDeDatos.substring(4,10))){				
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Genero
			if (curp.length() >= 11 && curpDeDatos.length() >= 11){				
				if (curp.substring(10,11).equals(curpDeDatos.substring(10,11))){				
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Estado de origen
			if (curp.length() >= 13 && curpDeDatos.length() >= 13){
			
				if (curp.substring(11,13).equals(curpDeDatos.substring(11,13))){			
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Consonantes internas 
			if (curp.length() >= 16 && curpDeDatos.length() >= 16){
				if (curp.substring(13,16).equals(curpDeDatos.substring(13,16))){
					resultado += "1";
				}else{			
					resultado += "0";
				}
			}else {
				resultado += "0";
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|validaDatosDeCurp|:"+curp+":"+ex);
 		}finally{
 			
 		}
 		
 		return resultado;
 	} 
 	
 	
	
	public ArrayList<AlumPersonal> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisAlumno	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"+
				" NOMBRE_LEGAL, F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"+
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"+
				" CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL" +
				" FROM ENOC.ALUM_PERSONAL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal alumno = new AlumPersonal();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<AlumPersonal> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
			" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"+
			" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL" +
			" FROM ENOC.ALUM_PERSONAL"+ 
			" WHERE NOMBRE LIKE UPPER('"+nombre+"%')"+
			" AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%')"+
			" AND APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public ArrayList<AlumPersonal> getListaActas(Connection conn, String grupoId, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ "	F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE INSTR((SELECT ALUMNOS FROM ENOC.CARGA_GRUPO_IMP WHERE GRUPO_ID = "+grupoId+"),'-'||CODIGO_PERSONAL||'-') > 0 "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getListaActas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public ArrayList<AlumPersonal> getListAlum(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
			" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"+
			" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL"+
			" FROM ENOC.ALUM_PERSONAL"+ 
			" WHERE NOMBRE LIKE UPPER('"+nombre+"%')"+
			" AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%')"+
			" AND APELLIDO_MATERNO LIKE UPPER('%"+materno+"%')"+
			" AND SUBSTR(CODIGO_PERSONAL,1,2) NOT IN ('97','98') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public ArrayList<AlumPersonal> getListAlumTodo(Connection conn, String nombreCompleto, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
			" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"+
			" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL"+
			" FROM ENOC.ALUM_PERSONAL"+ 
			" WHERE UPPER(NOMBRE)||' '||UPPER(APELLIDO_PATERNO)||' '||UPPER(APELLIDO_MATERNO) LIKE '%"+nombreCompleto.toUpperCase()+"%'"+
			" AND SUBSTR(CODIGO_PERSONAL,1,2) NOT IN ('97','98') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	// Metodo Depreciado se sustituye en la clase InscritosUtil  
	public ArrayList<AlumPersonal> getListCumple(Connection conn, String Mes, String Dia, String Orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "+
				" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, "+
				" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL, "+
				" ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) AS CARRERA_ID "+
				" FROM ENOC.ALUM_PERSONAL "+ 
				" WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '"+Mes+"' ";					
			if (!Dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+Dia+"' ";				
			}	
			comando = comando + " "+Orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getListCumple|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	// Metodo Depreciado se sustituye en la clase InscritosUtil  
	public ArrayList<AlumPersonal> listGraduandos(Connection conn, String evento, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "+
				" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, "+
				" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL"+				
				" FROM ENOC.ALUM_PERSONAL "+ 
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = "+evento+") "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|listGraduandos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public String edadEntreFechas(Connection conn, String codigoPersonal, String fechaGra, String fechaNac ) throws SQLException{
			
			String meses				= "";
			Statement st 				= conn.createStatement();
			ResultSet rs 				= null;
			String comando				= "";
			
			try{
				
				comando = " SELECT TO_CHAR(MONTHS_BETWEEN (TO_DATE('"+fechaGra+"','DD/MM/YYYY'), TO_DATE('"+fechaNac+"','DD/MM/YYYY') ),'9999.99') AS MESES "
						+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
				
				rs = st.executeQuery(comando);
				while (rs.next()){				
					meses = rs.getString("MESES");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.AlumUtil|edadEntreFechas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}			
			return meses;
		}
	
	public String getNombre(Connection conn, String codigoPersonal, String Orden) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "x";
		
		try{
			if ( Orden.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					"FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					"FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			}	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	
	public String getPlanActivo(Connection conn, String codigo_alumno)throws SQLException{
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String planId			= "Vacio";
		
		try{
			comando = "SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+codigo_alumno+"' AND ESTADO = '1'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				planId = rs.getString("PLAN_ID");
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getPlanActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return planId;
		
	}
	
	public String getCarreraId(Connection conn, String planId)throws SQLException{
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String carreraId		= "Vacio";
		
		try{
			comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = '"+planId+"' "; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				carreraId = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getCarreraId|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carreraId;
		
	}
	
	public String getCarrera(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT ENOC.NOMBRE_CARRERA(ENOC.CARRERA(ENOC.ALUM_PLAN_ID('"+codigoPersonal+"'))) CARRERA FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("CARRERA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	

	public String getCarrera(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "x";
		
		try{			
			comando = "SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ENOC.CARRERA('"+planId+"')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE_CARRERA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
	
	public String getCarreraIdCodigo(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String carrera		= "x";
		
		try{
			comando = "SELECT B.CARRERA_ID "+
					"FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "+ 
					"WHERE A.CODIGO_PERSONAL = '"+codigoPersonal+"' "+ 
					"AND A.ESTADO = '1' "+
					"AND B.PLAN_ID = A.PLAN_ID";
			rs = st.executeQuery(comando);
			if (rs.next()){
				carrera = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getCarreraIdCodigo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public ArrayList<String> getEmpleadosPorCoodinador(Connection conn,String codigo) throws SQLException{
		
	    ArrayList<String> empleados	= new ArrayList<String>();	    
	    PreparedStatement ps 		= null;
	    PreparedStatement ps2 		= null;
	    ResultSet rs				= null;
	    ResultSet rs2				= null;
	    boolean dir					= false;

	    try {
	        ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?"); 
    	    ps.setString(1,codigo);
    	    rs = ps.executeQuery();
    	    if(rs.next()) 
    	    	dir = true;
	    
		    String comando = "";
		    if (dir){
		    	comando = "SELECT CLAVE,NOMBRE,APPATERNO,APMATERNO FROM ARON.EMPLEADO"+ 
		    		" WHERE ID IN "+
		    		" (SELECT ID FROM ARON.EMPLEADO_PUESTO "+ 
		    		" WHERE ID_CCOSTO LIKE CONCAT((SELECT SUBSTR(ID_CCOSTO,1,9) FROM ARON.EMPLEADO_PUESTO WHERE CLAVE=?),'%') "+
		    		" ) "+
		    		" ORDER BY NOMBRE";
		    }else{
		    	comando ="SELECT CLAVE,NOMBRE,APPATERNO,APMATERNO FROM ARON.EMPLEADO"+
		    			" WHERE ID IN "+
		    				"(SELECT ID FROM ARON.EMPLEADO_PUESTO "+ 
		    				" WHERE ID_CCOSTO IN (SELECT ID_CCOSTO FROM ARON.EMPLEADO_PUESTO WHERE CLAVE=?)"+
		    				")"+
		    			" ORDER BY NOMBRE";
		    }
		    
		    ps2 = conn.prepareStatement(comando);
    	    ps2.setString(1,codigo);
    	    rs2 = ps2.executeQuery();
    	    while(rs.next()){
    	        empleados.add(rs2.getString(1));
    	        empleados.add(rs2.getString(2)+" "+rs2.getString(3)+" "+rs2.getString(4));
    	    }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { ps.close(); } catch (Exception ignore) { }
            if (ps2!=null) ps2.close();
            try { rs.close(); } catch (Exception ignore) { }
            if (rs2!=null) rs2.close();            
        }
	    return empleados;
	}
	
	public ArrayList<AlumPersonal> getListIguales(Connection conn, String nombre, String paterno, String materno) throws SQLException{
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		char cNomb			= '0';
		char cPat			= '0';
		//char cMat			= '0';
		//char cTemp		= '0';
		String sNomb		= "";
		String sPat			= "";
		String sMat			= "";
		int iSuma			= 0;
		int iCont			= 0;
		int iTamanio		= 0;
		
		nombre=nombre.toUpperCase();
		paterno=paterno.toUpperCase();
		materno=materno.toUpperCase();
		
		cNomb = nombre.charAt(0);
		cPat = paterno.charAt(0);
		
		if (cNomb != '0' & cPat != '0')
		{
			try{
				comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE SUBSTR(NOMBRE,1,1) = '"+ 
						  cNomb +"' AND " +
						  "SUBSTR(APELLIDO_PATERNO,1,1) = '"+ cPat +"'";
				
				rs = st.executeQuery(comando);
				while (rs.next())
				{
					sNomb = rs.getString("NOMBRE");
					sPat = rs.getString("APELLIDO_PATERNO");
					sMat = rs.getString("APELLIDO_MATERNO");
					int i = 0;
					iSuma = 0;
					for(i = 0; i < nombre.length() & i < sNomb.length();i++)
					{
						if(nombre.charAt(i) == sNomb.charAt(i))
						{
							iSuma++;
						}
					}
					if(nombre.length() < sNomb.length())
						iTamanio = sNomb.length();
					else
						iTamanio = nombre.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					iSuma = 0;
					for(i = 0; i < paterno.length() & i < sPat.length();i++)
					{
						if(paterno.charAt(i) == sPat.charAt(i))
						{
							iSuma++;
						}
					}
					if(paterno.length() < sPat.length())
						iTamanio = sPat.length();
					else
						iTamanio = paterno.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					iSuma = 0;
					for(i = 0; i < materno.length() & i < sMat.length();i++)
					{
						if(materno.charAt(i) == sMat.charAt(i))
						{
							iSuma++;
						}
					}
					if(materno.length() < sMat.length())
						iTamanio = sMat.length();
					else
						iTamanio = materno.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					if(iCont >= 2)
					{
						AlumPersonal personal = new AlumPersonal();
						personal.mapeaReg(rs);
						lisPersonal.add(personal);
					}
					iCont = 0;
					
					
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.AlumUtil|getListIguales|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		}
		return lisPersonal;
	}
	
	public String getVencimientoFM3(Connection conn,String codigo) throws SQLException{
	    String fecha="01/01/2000";
	    PreparedStatement ps =null;
	    ResultSet rs=null;
	    
	    try {
	        ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(FECHA_VENCE,'DD/MM/YYYY'),'01/01/2000') AS VENCIMIENTO "+
				"FROM ENOC.LEG_EXTDOCTOS "+ 
				"WHERE CODIGO = ? " +
				"AND IDDOCUMENTO = 2 ");
    	    ps.setString(1,codigo);
    	    rs=ps.executeQuery();
    	    if(rs.next())fecha=rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null)ps.close();
            if(rs!=null)rs.close();
        }
	    return fecha;
	}
	
	public boolean esExtrangero(Connection conn,String codigo) throws SQLException{
	    int nacionalidad=0;
	    boolean ext=false;
	    PreparedStatement ps =null;
	    ResultSet rs=null;	    

	    try {
	        ps = conn.prepareStatement("SELECT NACIONALIDAD FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL =?"); 
    	    ps.setString(1,codigo);
    	    rs=ps.executeQuery();
    	    if(rs.next())nacionalidad=rs.getInt(1);
    	    if(nacionalidad!=91)ext=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ps.close();
            rs.close();
        }
	    return ext;
	}
	
	public double getSaldo(Connection conn,String codigo) throws SQLException{
	    PreparedStatement ps =null;
	    ResultSet rs=null;
	    String sql="";
	    double saldo=0;
	    try {
	        sql = "SELECT" +
	        	" COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END ),0) AS SALDO" +
	        	" FROM MATEO.CONT_MOVIMIENTO" +
	        	" WHERE ID_AUXILIARM=?" +
	        	" AND ID_EJERCICIO ='001-'||TO_CHAR(now(),'YYYY')" +
	        	" AND ID_CTAMAYORM IN('1.1.04.01','1.1.04.29','1.1.04.30')" +
	        	" AND TIPO_CUENTA = 'B'";
	        ps = conn.prepareStatement(sql);
    	    ps.setString(1,codigo);
    	    rs=ps.executeQuery();
    	    if(rs.next()) 
				saldo=rs.getDouble("SALDO");    	    
    	    
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ps.close();
            rs.close();
        }
	    return saldo;
	}
	
	public boolean insertAlumSaldo(Connection conn,String codigo,String ejercicio,double saldo) throws SQLException{   
	    PreparedStatement ps =null;
	    ResultSet rs=null;
		boolean res = false; 
	    
	    try {	        
	        ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_SALDO(CODIGO_PERSONAL, EJERCICIO_ID, SALDO) VALUES(?,?,?)"); 
    	    ps.setString(1,codigo);
    	    ps.setString(2,ejercicio);
			ps.setDouble(3,saldo);
    	    if (ps.executeUpdate()==1){
				res = true;			
    	    }    	        	    
    	    
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
            try { rs.close(); } catch (Exception ignore) { }
        }
	    return res;
	}
	
	public ArrayList<String> getListAlumnos(Connection conn) throws SQLException{   
	    PreparedStatement ps =null;
	    ResultSet rs=null;
		ArrayList<String> lisAlum = new ArrayList<String>();		    
	    try {
			ps= conn.prepareStatement("SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.ESTADISTICA");
			rs = ps.executeQuery();
			while (rs.next()){				 
				lisAlum.add(rs.getString("CODIGO_PERSONAL"));
			}         	    
    	    
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
            try { rs.close(); } catch (Exception ignore) { }
        }
	    return lisAlum;
	}
	
	public ArrayList<AlumPersonal> getAlumnosPat(Connection conn, String nombre, String paterno, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL," +
					" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID," +
					" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL" +
					" FROM ENOC.ALUM_PERSONAL WHERE NOMBRE LIKE '"+nombre.charAt(0)+"%' OR APELLIDO_PATERNO LIKE '"+paterno.charAt(0)+"%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public ArrayList<AlumPersonal> getAlumnos(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL," +
					" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID," +
					" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL" +
					" FROM ENOC.ALUM_PERSONAL WHERE NOMBRE LIKE '"+nombre.charAt(0)+"%' OR APELLIDO_PATERNO LIKE '"+paterno.charAt(0)+"%' OR APELLIDO_MATERNO LIKE '"+materno.charAt(0)+"%' "+orden;		 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	
	public static ArrayList<AlumPersonal> BuscaDuplicados(Connection conn, String name, String aPaterno, String aMaterno, int porc) throws SQLException{
		Statement st 		= null;
		ResultSet rs 		= null;
		ArrayList<AlumPersonal> lisTmp			= new ArrayList<AlumPersonal>();
		try{
		
			st 		= conn.createStatement();			
			
			aca.alumno.AlumUtil alumU = new aca.alumno.AlumUtil();
			ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();	
			
			//Declaracion de Variables					
			String sNomb		= "";
			String sPat			= "";
			String sMat			= "";
			int iSuma			= 0;
			int iCont			= 0;
			int iTamanio		= 0;		
			
			//Asignacion de los parametros
			String nombre		= name.toUpperCase();
			String paterno		= aPaterno.toUpperCase();
			String materno		= aMaterno.toUpperCase();			
			int k=0, i=0/*, row= 0*/; 			
			
			if(materno.length() == 0){
				lisPersonal = alumU.getAlumnosPat(conn, nombre, paterno, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
			}
				
			if(materno.length() != 0){
				lisPersonal = alumU.getAlumnos(conn, nombre, paterno, materno, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
			}
			//lisPersonal = alumU.getListAll(Conn, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");						
				
			//aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal)lisPersonal.get(row);
										
			for (k=1;k<lisPersonal.size();k++){
				aca.alumno.AlumPersonal alumno2 = (aca.alumno.AlumPersonal)lisPersonal.get(k);
				
				sNomb = alumno2.getNombre().toUpperCase();
				sPat = alumno2.getApellidoPaterno().toUpperCase();
				sMat = alumno2.getApellidoMaterno().toUpperCase();						
			
				iSuma = 0;						
				// Verifica la similitud del nombre
				for(i = 0; i < nombre.length() & i < sNomb.length();i++){						
					if(nombre.charAt(i) == sNomb.charAt(i)){
						iSuma++;
					}
				}
				if(nombre.length() < sNomb.length())
					iTamanio = sNomb.length();
				else
					iTamanio = nombre.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;
					
				// Verifica la similitud del apellido paterno
				iSuma = 0;
				for(i = 0; i < paterno.length() & i < sPat.length();i++){
					if(paterno.charAt(i) == sPat.charAt(i)){
						iSuma++;
					}
				}
				if(paterno.length() < sPat.length())
					iTamanio = sPat.length();
				else
					iTamanio = paterno.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;
				
				// Verifica la similitud del apellido materno
					
				iSuma = 0;
				for(i = 0; i < materno.length() & i < sMat.length();i++){
					if(materno.charAt(i) == sMat.charAt(i)){
						iSuma++;
					}
				}
				if(materno.length() < sMat.length())
					iTamanio = sMat.length();
				else
					iTamanio = materno.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;	
				
				if(iCont >= 2){
					lisTmp.add(lisPersonal.get(k));
				}
				
				iCont = 0;
			}
		
			lisPersonal.remove(0);
			//System.out.println("Tamano del listor:"+lisPersonal.size());				
		}catch(Exception e){
			System.out.println("Error en busca duplicados: "+e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisTmp;
	}
	
	public static boolean esNuevoIngreso(Connection conn,String codigo, String planId) throws SQLException{
	    boolean ok = false;
	    PreparedStatement ps =null;
	    ResultSet rs=null;	    

	    try {
	        ps = conn.prepareStatement("SELECT CODIGO_PERSONAL  FROM ENOC.KRDX_CURSO_ACT"+ 
	        		" WHERE CODIGO_PERSONAL = ?" +
	        		" AND ENOC.CURSO_PLAN(CURSO_ID)= ?" +
	        		" AND TIPOCAL_ID IN ('1','2')");
    	    ps.setString(1,codigo);
    	    ps.setString(2,planId);    	    
    	    
    	    rs=ps.executeQuery();
    	    if(!rs.next())
    	    	ok = true; 
    	    
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ps.close();
            rs.close();
        }
	    return ok;
	}
	
	public HashMap<String, AlumPersonal> getMapAllCarga(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ('"+cargaId+"'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, AlumPersonal> mapMentorContacto(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = '"+periodoId+"')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	// Map de alumnos inscritos en cargas
	public HashMap<String, AlumPersonal> mapInscritosEnCargas(Connection conn, String cargas) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO IN ('I','3') AND CARGA_ID IN ("+cargas+"))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapInscritosEnCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, AlumPersonal> getMapAllCargaAlumEstado(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO IN ('I','3') AND CARGA_ID IN ('"+cargaId+"'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapAllCargaAlumEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
public HashMap<String, AlumPersonal> getMapProced(Connection conn) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PAIS_ID, ESTADO_ID" +
					" FROM ENOC.ALUM_PERSONAL";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapProced|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, AlumPersonal> getMapInscritos(Connection conn) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, " +
					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
					" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, AlumPersonal> getMapInscritos(Connection conn, String cargas, String fechaIni, String fechaFinal) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, " +
					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
					" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN "
					+ "	(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFinal+"','DD/MM/YYYY')"
					+ " AND CARGA_ID IN ("+cargas+"))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, AlumPersonal> getMapRotaciones(Connection conn) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapRotaciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<AlumPersonal> getAlumnosCreados(Connection conn, String fechaInicio, String fechaFinal, String orden ) throws SQLException{
		
		ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
					" NOMBRE_LEGAL, F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, "+
					" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, "+
					" CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL, TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA FROM ENOC.ALUM_PERSONAL " +
					" WHERE F_CREADO >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY') " +
					" AND F_CREADO <= TO_DATE('"+fechaFinal+"','DD/MM/YYYY') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaRegCompleto(rs);
				lisPersonal.add(personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getAlumnosCreados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public HashMap<String, AlumPersonal> getMapAlumnosIngreso(Connection conn) throws SQLException{
		
		HashMap<String, AlumPersonal> list		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.VIG_INGRESO)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapAlumnosIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public static HashMap<String, String> getMapEdad(Connection conn, String cargas) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, COALESCE(TO_CHAR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12,'99'),'0') AS EDAD " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+"))";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("EDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> mapAlumnosMateria(Connection conn, String cursoCargaId ) throws SQLException{
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID ='"+cursoCargaId+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> mapAlumnosPlan(Connection conn, String planId ) throws SQLException{
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE " +
					" FROM ENOC.ALUM_PERSONAL AP" +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE PLAN_ID = '"+planId+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapGraduandos(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL,"
					+ " TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = "+eventoId+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapGraduandos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnCargas(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargaId+"))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosEnCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnCargasyModalidades(Connection conn, String cargaId, String modalidades) throws SQLException{
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, CREDENCIAL,"
					+ " TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA"
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargaId+") AND MODALIDAD_ID IN ("+modalidades+"))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosEnCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de nombres de alumnos con descuentos 
	public HashMap<String, String> mapAlumDescuento(Connection conn) throws SQLException{
		
		HashMap<String, String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS ALUMNO FROM ENOC.ALUM_PERSONAL"+
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_DESCUENTO)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumDescuento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de nombres de alumnos en Ment_alumno 
	public HashMap<String, String> mapMentAlumno(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS ALUMNO FROM ENOC.ALUM_PERSONAL"+
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodo+"')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapMentAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapBecadosFecha(Connection conn, String fecha) throws SQLException{
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN "+
						"(SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapBecadosFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de edad de los inscritos
	public HashMap<String, String> mapEdadInscritos(Connection conn) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, TO_CHAR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12,'99999.99') AS EDAD FROM ENOC.ALUM_PERSONAL "+
					" WHERE CODIGO_PERSONAL IN ( SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS )";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("EDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|MapEdadInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	/*
	 * 
	 **/
	public static HashMap<String,aca.alumno.AlumPersonal> mapAlumnosDiferidos(Connection conn) throws SQLException{
		
		HashMap<String,aca.alumno.AlumPersonal> map = new HashMap<String,aca.alumno.AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosDiferidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,aca.alumno.AlumPersonal> mapAlumnosBecados(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,aca.alumno.AlumPersonal> map = new HashMap<String,aca.alumno.AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+ejercicioId+"')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosBecados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,aca.alumno.AlumPersonal> mapAlumnosInformes(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,aca.alumno.AlumPersonal> map = new HashMap<String,aca.alumno.AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = '"+ejercicioId+"')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosBecados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, AlumPersonal> mapAlumnosInternos(Connection conn, String dormitorioId) throws SQLException{
		
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = "+dormitorioId+")";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, AlumPersonal> mapAlumnosConExtra(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID IN "
					+ "			(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND NOTA_EXTRA IS NOT NULL AND F_EXTRA IS NOT NULL))";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPersonal personal = new AlumPersonal();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapAlumnosConExtra|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}