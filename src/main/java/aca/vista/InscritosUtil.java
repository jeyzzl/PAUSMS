// Clase para la vista ALUMNO
package  aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;
import aca.util.Fecha;

import java.util.HashMap;;


public class InscritosUtil{
	
	
	public Inscritos mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;	 
		Inscritos inscrito 		= new Inscritos();
		
		try{
			ps = conn.prepareStatement("SELECT "+
				" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
				" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO," +
				" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"+
				" FROM ENOC.INSCRITOS"+
				" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				inscrito.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return inscrito;
		
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INSCRITOS "+
				" WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean inscrito(Connection conn, String codigoPersonal) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "+
				" WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getCarreraId(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String carrera		= "x";
		
		try{
			comando = "SELECT CARRERA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "; 
				
			rs = st.executeQuery(comando);
			if (rs.next()){
				carrera = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	public static String getEdad(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String edad			= "x";
		
		try{
			comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS EDAD " +
					" FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL ='"+codigoPersonal+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				edad = rs.getString("EDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return edad;
	}
	public static String getMexicanos(Connection conn) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nacion		= "x";
		
		try{
			comando = "SELECT COUNT(NACIONALIDAD) NACIONALIDAD FROM ENOC.INSCRITOS " +
					" WHERE RESIDENCIA_ID = 'I' " +
					" AND DORMITORIO IN('1','2','3','4') AND NACIONALIDAD= '91'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nacion = rs.getString("NACIONALIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Inscritos|getMexicanos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return nacion;
	}
	
	public static String getMexicanosUM(Connection conn) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nacion		= "x";
		
		try{
			comando = "SELECT COUNT(NACIONALIDAD) NACIONALIDAD FROM ENOC.INSCRITOS " +
					" WHERE RESIDENCIA_ID = 'I' " +
					" AND DORMITORIO IN('1','2','3','4') AND NACIONALIDAD= '91'" +
					" AND MODALIDAD_ID IN (1,2,3,4)"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nacion = rs.getString("NACIONALIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMexicanosUM|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nacion;
	}
	
	public static String getTipoAlum(Connection conn, String tipo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	tipoAlum	= "x";
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS" + 
					" WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4')" +
					" AND TIPOALUMNO_ID = '"+tipo+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				tipoAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getTipoAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipoAlum;
	}
	
	public static String getTipoAlumUM(Connection conn, String tipo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	tipoAlum	= "X";
		
		try{
			comando = "SELECT  COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS" + 
					" WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4')" +
					" AND TIPOALUMNO_ID = '"+tipo+"'" +
					" AND MODALIDAD_ID IN (1,2,3,4)"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				tipoAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getTipoAlumUM|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipoAlum;
	}
	
	public static String getInscritosFacultad(Connection conn, String facultadId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "x";
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.INSCRITOS" +
					" WHERE CARRERA_ID IN"+
					" (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("CODIGO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getInscritosFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}

	public static String getInscritosCarrera(Connection conn, String carreraId) throws SQLException{
	
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "x";
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS CODIGO" +
					  " FROM ENOC.INSCRITOS" +
					  " WHERE CARRERA_ID = '"+carreraId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("CODIGO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getInscritosCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}	
	
	
	public static String fechaNac(Connection conn, String codigoPersonal) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String fechaN			= "X"; 
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO FROM ENOC.INSCRITOS "+
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				fechaN = rs.getString("F_NACIMIENTO");
			
		}catch(Exception ex){
			System.out.println("Error: aca.vista.InscritosUtil||fechaNac "+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fechaN;
	}
	
	public static String getAlumFormaPago(Connection conn, String codigoPersonal) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String formaPago		= "C"; 
		
		try{
			ps = conn.prepareStatement("SELECT FORMAPAGO FROM MATEO.FES_CCOBRO WHERE MATRICULA = ?" +
				" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL= ?)"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				formaPago = rs.getString("FORMAPAGO");
			
		}catch(Exception ex){
			System.out.println("Error: aca.vista.InscritosUtil|"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return formaPago;
	}
	
	public ArrayList<Inscritos> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID), "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAllPorFecha(Connection conn, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAllPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAllPorFechas(Connection conn, String modalidad, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS " +
			" WHERE MODALIDAD_ID IN ("+modalidad+") "+
			" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAllPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListNotServAlumno(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID AS SALDO "+
			" FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL NOT IN " +
			" (SELECT CODIGO_PERSONAL FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.SERV_EVENTO WHERE now() BETWEEN FECHA_INICIO AND FECHA_FINAL)) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public TreeMap<String,Inscritos> getMapAll(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,Inscritos> Inscritos	= new TreeMap<String,Inscritos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				//System.out.println(rs.getString("CODIGO_PERSONAL"));
				Inscritos ins = new Inscritos();
				ins.mapeaReg(rs);
				llave = ins.getCodigoPersonal();
				Inscritos.put(llave, ins);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return Inscritos;
	}
	
	public ArrayList<Inscritos> getListDesercion(Connection conn, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		// En este query traemos la clave de la facultad y la enviamos al Bean através del campo CURP.
		// También el grado se usa en usuario y el semestre se usa en la clave
		try{
			comando = "SELECT"+ 
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+ 
				" CARGA_ID,BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"+
				" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
				" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				" ENOC.FACULTAD(CARRERA_ID) AS CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
				" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, TIPOALUMNO_ID"+
			" FROM ENOC.INSCRITOS"+
			" UNION"+
				" SELECT A.CODIGO_PERSONAL AS CODIGO_PERSONAL, 'XXXXXX', 1, B.NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.NOMBRE_LEGAL,'N' AS COTEJADO,"+
				" '01/01/2000' AS F_NACIMIENTO, B.SEXO,'-' AS ESTADO_CIVIL,B.RELIGION_ID," +
				" B.PAIS_ID,0 AS ESTADO_ID, B.CIUDAD_ID , B.NACIONALIDAD, ENOC.FACULTAD(CARRERA_ID) AS CURP," +
				" 1 AS MODALIDAD_ID, 1 AS CLAS_FIN, C.RESIDENCIA_ID,'-' AS DORMITORIO, A.PLAN_ID,A.CARRERA_ID,0 AS SALDO, "+
				" '01/01/2000' AS F_INSCRIPCION, C.TIPO_ALUMNO AS TIPO_ALUMNO"+
				" FROM ENOC.ALUM_EGRESO A, ENOC.ALUM_PERSONAL B, ENOC.ALUM_ACADEMICO C"+
				" WHERE A.CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"+
				" AND B.CODIGO_PERSONAL = A.CODIGO_PERSONAL"+
				" AND C.CODIGO_PERSONAL=B.CODIGO_PERSONAL"+
				" AND A.EVENTO_ID IN(14,15) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDesercion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAllUM(Connection conn, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID),"+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAllUM|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAllModalidades(Connection conn, String modalidades, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAllModalidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAllModalidadesPorFechas(Connection conn, String modalidades, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+") "+
			" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAllModalidadesPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS "+
			" WHERE NOMBRE LIKE UPPER('"+nombre+"%') "+
			" AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
			" AND APELLIDO_MATERNO LIKE UPPER('"+materno+"%') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisInscritos;
	}
	
	public ArrayList<ArrayList<String>> getPrimerIngreso(Connection conn, String orden ) throws SQLException{
		ArrayList<ArrayList<String>> lisInscritos	= new ArrayList<ArrayList<String>>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "	SELECT DISTINCT(I.CODIGO_PERSONAL) AS CODIGO_PERSONAL," +
					"		I.CARGA_ID, I.NOMBRE, I.APELLIDO_PATERNO, I.APELLIDO_MATERNO," +
					"		I.SEXO, I.RESIDENCIA_ID, I.PLAN_ID, I.NACIONALIDAD,"+ 
					"		ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID, I.CARRERA_ID, " +
					"		(SELECT NOMBRE_CORTO FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN" +
					"			(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID)) AS FACULTAD," +
					"		(SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID) AS CARRERA," +
					"		(SELECT NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID IN" +
					"			(SELECT TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = I.CODIGO_PERSONAL)) AS TIPO," +
					"		(SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = I.MODALIDAD_ID) AS MODALIDAD," +
					"		(SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = I.PAIS_ID) AS PAIS," +
					"		(SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE ESTADO_ID = I.ESTADO_ID AND PAIS_ID = I.PAIS_ID) AS ESTADO" +
					"	FROM ENOC.INSCRITOS I WHERE" +
					"		(SELECT TO_CHAR(A.FECHA, 'DD/MM/YYYY') FROM MATEO.FES_CCOBRO A WHERE A.MATRICULA=I.CODIGO_PERSONAL AND A.CARGA_ID=I.CARGA_ID" +
					"			AND A.BLOQUE=I.BLOQUE_ID AND A.INSCRITO='S')=" +
					"		(SELECT TO_CHAR(MIN(M.FECHA), 'DD/MM/YYYY') FROM MATEO.FES_CCOBRO M WHERE M.MATRICULA=I.CODIGO_PERSONAL" +
					"			AND M.INSCRITO='S') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("FACULTAD"));
				arr.add(rs.getString("CARRERA"));
				arr.add(rs.getString("CODIGO_PERSONAL"));
				arr.add(rs.getString("NOMBRE")+" "+rs.getString("APELLIDO_PATERNO")+" "+rs.getString("APELLIDO_MATERNO"));
				arr.add(rs.getString("PLAN_ID"));
				arr.add(rs.getString("CARGA_ID"));
				arr.add(rs.getString("RESIDENCIA_ID"));
				arr.add(rs.getString("MODALIDAD"));
				arr.add(rs.getString("TIPO"));
				arr.add(rs.getString("SEXO"));
				arr.add(rs.getString("NACIONALIDAD"));
				arr.add(rs.getString("PAIS"));
				arr.add(rs.getString("ESTADO"));
				lisInscritos.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListCumple(Connection conn, String mes, String dia, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos		= new ArrayList<Inscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			
			comando = "SELECT "+
			" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS "+
			" WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '"+mes+"' ";					
			if (!dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+dia+"' ";				
			}	
			comando = comando + " "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListCumple|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisInscritos;
	}
	
	
	public ArrayList<Inscritos> getListCumpleSem(Connection conn, String Orden ) throws SQLException{
		
		ArrayList<Inscritos> lisPersonal			= new ArrayList<Inscritos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		Fecha fecha					= new aca.util.Fecha();	
		ArrayList <String>fechas		= fecha.getSemanaActual();
		String fechaTemp 			= "";	
		
		try{
			for(int j = 0; j<7;j++){
				fechaTemp	= (String)fechas.get(j);
				
				comando = " SELECT "+
						  " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID, " +
						  " NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
						  " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
						  " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
						  " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "+
						  " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
						  " FROM ENOC.INSCRITOS "+
						  " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,5)= '"+fechaTemp.substring(0,5)+"' ";
				
				comando = comando + " "+Orden;
				rs = st.executeQuery(comando);
				while (rs.next()){				
					Inscritos inscritos = new Inscritos();
					inscritos.mapeaReg(rs);
					lisPersonal.add(inscritos);
				}				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListCumpleSem|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisPersonal;
	}
	
	public ArrayList<String> getListCumpleSemana(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisPersonal	= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		Fecha fecha						= new aca.util.Fecha();	
		ArrayList <String>fechas		= fecha.getSemanaActual();
		String fechaTemp 				= "";	
		
		try{
			for(int j = 0; j<7;j++){
				fechaTemp	= (String)fechas.get(j);
				
				comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, F_NACIMIENTO, CARRERA_ID, APELLIDO_PATERNO FROM ENOC.INSCRITOS"+
						  " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,5)= '"+fechaTemp.substring(0,5)+"' "+orden;	
				
				rs = st.executeQuery(comando);
				while (rs.next()){					
					lisPersonal.add(rs.getString("CODIGO_PERSONAL"));
				}				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListCumpleSemana|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisPersonal;
	}	
	
	public ArrayList<Inscritos> getListDisciplina(Connection conn, String periodo, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos 	= new ArrayList<Inscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, "+
					" BLOQUE_ID , NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, "+ 
					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, SEXO, ESTADO_CIVIL, "+
					" RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, CURP, "+
					" MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, F_INSCRIPCION, TIPOALUMNO_ID "+ 
					" FROM ENOC.COND_ALUMNO , ENOC.INSCRITOS  WHERE MATRICULA = CODIGO_PERSONAL " + 
					" AND PERIODO_ID = '"+periodo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDisciplina|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisInscritos;
	}
	public String getNombre(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			if ( orden.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					" FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					" FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return nombre;
	}	
	
	public String getCuentaAlum(Connection conn, String idMentor, String periodoId, String facultadId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String alumno		= "x";
		
		try{
			comando = "SELECT COUNT(I.CODIGO_PERSONAL) ALUMNOS"+
					" FROM ENOC.INSCRITOS I, ENOC.MENT_ALUMNO MA"+ 
					" WHERE ENOC.FACULTAD(I.CARRERA_ID)= '"+facultadId+"' " +
					" AND MA.CODIGO_PERSONAL = I.CODIGO_PERSONAL" +
					" AND MA.PERIODO_ID = '"+periodoId+"'"+
					" AND MA.MENTOR_ID = '"+idMentor+"' AND MA.STATUS = 'A'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				alumno = rs.getString("ALUMNOS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMentorAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return alumno;
	}	
		
	public ArrayList<Inscritos> getListAlumSinMentor(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
			" CARGA_ID, MODALIDAD_ID,BLOQUE_ID,NOMBRE_LEGAL,COTEJADO,F_NACIMIENTO," +
			" SEXO,ESTADO_CIVIL,RELIGION_ID,PAIS_ID,ESTADO_ID,CIUDAD_ID,NACIONALIDAD,CURP," +
			" CLAS_FIN,PLAN_ID,RESIDENCIA_ID, DORMITORIO, CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " +
			" FROM ENOC.INSCRITOS " +
			" WHERE CODIGO_PERSONAL NOT IN " +
			"	(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID ='"+periodoId+"' AND STATUS = 'A') "+orden; 
					
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAlumSinMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	// Lista de alumnos sin mentor en una facultad
	public ArrayList<Inscritos> getListAlumSinMentor(Connection conn, String periodoId, String fecha, String facultadId, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " CARGA_ID, MODALIDAD_ID,BLOQUE_ID,NOMBRE_LEGAL,COTEJADO,F_NACIMIENTO,"
					+ " SEXO,ESTADO_CIVIL,RELIGION_ID,PAIS_ID,ESTADO_ID,CIUDAD_ID,NACIONALIDAD,CURP,"
					+ " CLAS_FIN,PLAN_ID,RESIDENCIA_ID, DORMITORIO, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'"
					+ " AND CODIGO_PERSONAL NOT IN"
					+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO "
					+ "		WHERE PERIODO_ID ='"+periodoId+"' "
					+ "		AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN ENOC.MENT_ALUMNO.FECHA_INICIO AND ENOC.MENT_ALUMNO.FECHA_FINAL "
					+ "		AND STATUS = 'A') "+orden; 
					
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAlumSinMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	// Lista de alumnos sin mentor en una facultad
		public ArrayList<Inscritos> getListAlumSinMentor(Connection conn, String periodoId, String facultadId, String orden ) throws SQLException{
			
			ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";
			
			try{
				comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
						+ " CARGA_ID, MODALIDAD_ID,BLOQUE_ID,NOMBRE_LEGAL,COTEJADO,F_NACIMIENTO,"
						+ " SEXO,ESTADO_CIVIL,RELIGION_ID,PAIS_ID,ESTADO_ID,CIUDAD_ID,NACIONALIDAD,CURP,"
						+ " CLAS_FIN,PLAN_ID,RESIDENCIA_ID, DORMITORIO, CARRERA_ID, SALDO, "
						+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
						+ " FROM ENOC.INSCRITOS"
						+ " WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'"
						+ " AND CODIGO_PERSONAL NOT IN"
						+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID ='"+periodoId+"' AND STATUS = 'A') "+orden; 
						
				rs = st.executeQuery(comando);
				while (rs.next()){
						
					Inscritos inscritos = new Inscritos();
					inscritos.mapeaReg(rs);
					lisInscritos.add(inscritos);					
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.vista.InscritosUtil|getListAlumSinMentor|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisInscritos;
		}
	
	public ArrayList<Inscritos> getListFAC(Connection conn, String orden, String carreraId ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN (1,4,5) AND CARRERA_ID = '"+carreraId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListFAC|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListDormitorio(Connection conn, String dormitorio, String cargas, String modalidades, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND DORMITORIO = '"+dormitorio+"'"
					+ " AND RESIDENCIA_ID = 'I' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListDormitorioPorFecha(Connection conn, String dormitorioId, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = '"+dormitorioId+"' "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorioPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListDormitorioPorFechaInscritos(Connection conn, String dormitorioId, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = '"+dormitorioId+"' "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = '"+dormitorioId+"')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorioPorFechaInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListDormitorio(Connection conn, String dormitorio, String cargas, String modalidades, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND DORMITORIO = '"+dormitorio+"'"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND RESIDENCIA_ID = 'I' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> lisInternadoCalificado(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin, String fechaInternado, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"		
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND CODIGO_PERSONAL IN "
					+ "		(SELECT MATRICULA FROM NOE.COM_AUTORIZACION WHERE CARGA_ID IN ("+cargas+") AND NUM_COMIDAS > 0 "
					+ "		AND TO_DATE('"+fechaInternado+"','DD/MM/YYYY') BETWEEN FECHA_INICIAL AND FECHA_FINAL"
					+ " 	AND BLOQUE = ENOC.INSCRITOS.BLOQUE_ID) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListDormitorioModalidad(Connection conn, String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO," 
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS " 
					+ " WHERE MODALIDAD_ID IN ("+modalidades+") " 
					+ " AND DORMITORIO = '"+dormitorio+"'" 
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND RESIDENCIA_ID = 'I' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListSinDormitorio(Connection conn, String cargas, String modalidades, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND DORMITORIO NOT IN ('1','2','3','4')"
					+ " AND RESIDENCIA_ID = 'I' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListSinDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}

	public ArrayList<Inscritos> getInternosPorGenero(Connection conn, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos			= new ArrayList<Inscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " +					
			" FROM ENOC.INSCRITOS " +
			" WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) " + 
			" AND MODALIDAD_ID IN (1,4,5) AND RESIDENCIA_ID = 'I' "+orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);						
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getInternosPorGenero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}

	public ArrayList<Inscritos> getInternosPorGeneroModalidad(Connection conn, String modalidades, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos			= new ArrayList<Inscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " +					
			" FROM ENOC.INSCRITOS " +
			" WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) " + 
			" AND MODALIDAD_ID IN ("+modalidades+") AND RESIDENCIA_ID = 'I' "+
			" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);						
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getInternosPorGenero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListExternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+
			" CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"+
			" SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"+
			" CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"+
			" FROM ENOC.INSCRITOS" +
			" WHERE RESIDENCIA_ID = 'E' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getExternosPorFechas(Connection conn, String fechaIni, String fechaFin, String modalidad, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS" 
					+ " WHERE RESIDENCIA_ID = 'E' AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "
					+ " AND MODALIDAD_ID IN("+modalidad+") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getExternosPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	/* Revisar posible error en mapeaReg */
	public ArrayList<Inscritos> getListInscritosExternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE, " +
					" COALESCE((SELECT B.RAZON FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS RAZON, " + 
					" COALESCE((SELECT B.TUT_NOMBRE FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS TUTOR, " + 
					" COALESCE((SELECT B.COLONIA FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS COLONIA, " + 
					" COALESCE((SELECT B.CALLE||' '||CASE NUMERO WHEN 's/n' THEN NUMERO ELSE '# '||NUMERO END FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS CALLE, " + 
					" COALESCE((SELECT B.TEL_NUMERO FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS TELEFONO, " + 
					" COALESCE((SELECT ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL)) FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS FACULTAD, " + 
					" COALESCE((SELECT ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS CARRERA, " + 
					" SEXO " +
					" FROM ENOC.INSCRITOS A " +
					" WHERE RESIDENCIA_ID = 'E' " +
					" ORDER BY FACULTAD, CARRERA, NOMBRE";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListExtSinRegModalidad(Connection conn, String modalidades, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<Inscritos> lista			= new ArrayList<Inscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'E'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM ENOC.RES_DATOS)"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden;			
			
			rs = st.executeQuery(comando);			
			while (rs.next()){			
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lista.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ExternosUtil|getListExtSinRegModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}	
	
	public ArrayList<Inscritos> getAlumnoCarrera(Connection conn, String carreraId, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE CARRERA_ID = '"+carreraId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getAlumnoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getAlumnos(Connection conn,  String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID, ENOC.FACULTAD(CARRERA_ID) "+
			" FROM ENOC.INSCRITOS "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getAlumnoPais(Connection conn, String paisId, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "+
			" FROM ENOC.INSCRITOS WHERE PAIS_ID = '"+paisId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getAlumnoPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListUbicacion(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
			" CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"+
			" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID, ENOC.FACULTAD(CARRERA_ID) "+
			" FROM ENOC.INSCRITOS WHERE MODALIDAD_ID = '1' AND CODIGO_PERSONAL IN " +
			" (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_UBICACION ) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<ArrayList<String>> getListaReporte2(Connection conn, String orden) throws SQLException{
		ArrayList<ArrayList<String>> lisInscritos	= new ArrayList<ArrayList<String>>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "	SELECT DISTINCT(I.CODIGO_PERSONAL) AS CODIGO_PERSONAL," +
					"	I.CARGA_ID, I.NOMBRE, I.APELLIDO_PATERNO, I.APELLIDO_MATERNO," +
					"	I.SEXO, I.RESIDENCIA_ID," +
					"	(SELECT NOMBRE_CORTO FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN" +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID)) AS FACULTAD," +
					"	(SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID) AS CARRERA," +
					"	(SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = I.RELIGION_ID) AS RELIGION," +
					"	(SELECT NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID IN" +
					"		(SELECT TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = I.CODIGO_PERSONAL)) AS TIPO," +
					"	(SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = I.MODALIDAD_ID) AS MODALIDAD," +
					"	(SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = I.PAIS_ID) AS PAIS," +
					"	(SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE ESTADO_ID = I.ESTADO_ID AND PAIS_ID = I.PAIS_ID) AS ESTADO" +
					"	FROM ENOC.INSCRITOS I "+orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("FACULTAD"));
				arr.add(rs.getString("CARRERA"));
				arr.add(rs.getString("CARGA_ID"));
				arr.add(rs.getString("CODIGO_PERSONAL"));
				arr.add(rs.getString("NOMBRE"));
				arr.add(rs.getString("APELLIDO_PATERNO")+" "+rs.getString("APELLIDO_MATERNO"));
				arr.add(rs.getString("SEXO"));
				arr.add(rs.getString("RESIDENCIA_ID"));
				arr.add(rs.getString("RELIGION"));
				arr.add(rs.getString("TIPO"));
				arr.add(rs.getString("MODALIDAD"));
				arr.add(rs.getString("PAIS"));
				arr.add(rs.getString("ESTADO"));
				lisInscritos.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListaReporte2|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<Inscritos> getListAlumnos(Connection conn,String cursoClave, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisInscritos	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT *" +
					" FROM ENOC.INSCRITOS " +
					" WHERE CODIGO_PERSONAL IN " +
					" (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE  (SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) = '"+cursoClave+"' " +
					" AND SUBSTR(CURSO_CARGA_ID,1,6)=INSCRITOS.CARGA_ID) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisInscritos.add(inscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	// Numero de alumnos inscritos en cada paxxs
	public ArrayList<String> getPaisesRepresentados(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisInscritos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PAIS_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.INSCRITOS GROUP BY PAIS_ID "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisInscritos.add(rs.getString("PAIS_ID")+"%"+rs.getString("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getPaisesRepresentados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscritos;
	}
	
	public ArrayList<String> getListaCarreraPorCiclo(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin, String orden) throws SQLException{
		ArrayList<String> lista = new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT DISTINCT(A.CARRERA_ID) AS CARRERA, ENOC.FACULTAD(CARRERA_ID) FROM ENOC.INSCRITOS A, ENOC.ALUM_PLAN B" +
					" WHERE B.CODIGO_PERSONAL = A.CODIGO_PERSONAL AND A.CARGA_ID IN ("+cargas+")" +
					" 	AND A.MODALIDAD_ID IN ("+modalidades+") " +
					" 	AND B.PLAN_ID = A.PLAN_ID AND B.ESTADO = '1'" +
					"   AND A.F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
					" GROUP BY A.CARRERA_ID "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				lista.add(rs.getString("CARRERA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getListaCarreraPorCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public HashMap<String, String> getMapaCantidadPorCiclo(Connection conn, String cargas, String modalidades, String estado) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CARRERA_ID, CICLO, COUNT(*) AS CANTIDAD FROM ALUM_ESTADO"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"					
					+ " AND ESTADO = '"+estado+"'"
					+ " GROUP BY CARRERA_ID, CICLO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CARRERA_ID")+"->"+rs.getString("CICLO"), rs.getString("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapaCantidadPorCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String,String> getMapTipo(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,String> Inscritos	= new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO " +
					" FROM ENOC.ALUM_ACADEMICO " +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Inscritos.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TIPO_ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return Inscritos;
	}
	
	public HashMap<String,String> mapAlumnoCarrera( Connection conn ) throws SQLException{
		
		HashMap<String,String> Inscritos	= new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARRERA_ID FROM ENOC.INSCRITOS "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Inscritos.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|mapAlumnoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return Inscritos;
	}
	
	public static HashMap<String, String> getMapaInscritos(Connection conn, String modalidades) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+")";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("F_INSCRIPCION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapaInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapInscritoUltimaFecha(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, TO_CHAR(MAX(F_INSCRIPCION),'DD/MM/YYYY') AS F_INSCRIPCION FROM ENOC.INSCRITOS GROUP BY CODIGO_PERSONAL";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("F_INSCRIPCION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|mapInscritoUltimaFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	} 
	
	public static HashMap<String, String> getMapaInscritos(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION FROM ENOC.INSCRITOS";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("F_INSCRIPCION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapaInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapaInscDormiEntreFechas(Connection conn, String residencia, String fechaIni, String fechaFin, String modalidades) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT DORMITORIO, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = '"+residencia+"'"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " GROUP BY DORMITORIO";			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("DORMITORIO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|mapaInscDormiEntreFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> getMapaInscritosPrepa(Connection conn, String modalidades) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "
					+ " WHERE (SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = INSCRITOS.CARRERA_ID) = 1 "
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND CARGA_ID||BLOQUE_ID IN (SELECT CARGA_ID||BLOQUE_ID FROM CARGA_BLOQUE WHERE NOW() BETWEEN F_INICIO AND F_FINAL)";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|getMapaInscritosPrepa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}	
	
	public HashMap<String, String> mapInscritosEnPais(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT PAIS_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS GROUP BY PAIS_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("PAIS_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.InscritosUtil|mapInscritosEnPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public ArrayList<Inscritos> getInscritosSeguro(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Inscritos> lisEvento	= new ArrayList<Inscritos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT"
					+ " CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				Inscritos inscritos = new Inscritos();
				inscritos.mapeaReg(rs);
				lisEvento.add(inscritos);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Inscritos|getInscritosSeguro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisEvento;
	}
	
}