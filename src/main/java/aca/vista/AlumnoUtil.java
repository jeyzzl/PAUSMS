// Clase para la vista ALUMNO
package  aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumnoUtil{
	
	public ArrayList<Alumno> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<Alumno> lisAlumno	= new ArrayList<Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<Alumno> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Alumno> lisAlumno	= new ArrayList<Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+
			"WHERE NOMBRE LIKE UPPER('"+nombre+"%') "+
			"AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
			"AND APELLIDO_MATERNO LIKE UPPER('"+materno+"%') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<Alumno> getListCumple(Connection conn, String mes, String dia, String orden ) throws SQLException{
		
		ArrayList<Alumno> lisAlumno	= new ArrayList<Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT "+
			"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+
				"WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '"+mes+"' ";					
			if (!dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+dia+"' ";				
			}	
			comando = comando + " "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getListCumple|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public HashMap<String, Alumno> getMapaAll(Connection conn, String condiciones) throws SQLException{
		HashMap<String, Alumno> mapaAlumnos	= new HashMap<String, Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT * FROM ENOC.ALUMNO "+condiciones;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				mapaAlumnos.put(alumno.getCodigoPersonal(), alumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getMapaAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaAlumnos;
	}
	
	public HashMap<String, Alumno> getMapaAlumnosEnlinea(Connection conn, String condiciones) throws SQLException{
		HashMap<String, Alumno> mapaAlumnos	= new HashMap<String, Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT B.CODIGO_PERSONAL, B.NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.NOMBRE_LEGAL, B.COTEJADO,"+
					" TO_CHAR(B.F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
					" B.SEXO, B.ESTADO_CIVIL, B.RELIGION_ID, B.PAIS_ID, B.ESTADO_ID, B.CIUDAD_ID, B.NACIONALIDAD,"+
					" B.CURP, B.MODALIDAD_ID, B.CLAS_FIN, B.RESIDENCIA_ID, B.PLAN_ID, B.CARRERA_ID "+
					" FROM ENOC.ALUM_ENLINEA A INNER JOIN ENOC.ALUMNO B" +
					" ON A.CODIGO_PERSONAL=B.CODIGO_PERSONAL "+condiciones;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				mapaAlumnos.put(alumno.getCodigoPersonal(), alumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getMapaAlumnosEnlinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaAlumnos;
	}
	
	public String getNombre(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			if ( orden.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					"FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					"FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	/*public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "camina_con_dios");
			
			Statement st 		= Conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";
			String Nombre		= "CAR";
			String Paterno		= "G";
			String Materno 		= "G";
			String Orden 		= "1";
			
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "+
				"F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, "+
				"NACIONALIDAD, DIRECCION, TELEFONO, EMAIL, CURP, ESTADO, COTEJADO "+
				"FROM ENOC.ALUM_PERSONAL "+ 
				"WHERE NOMBRE LIKE UPPER('%"+Nombre+"%') "+
				"AND APELLIDO_PATERNO LIKE UPPER('%"+Paterno+"%') "+
				"AND APELLIDO_MATERNO LIKE UPPER('%"+Materno+"%') "+
				"ORDER BY "+Orden;
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				System.out.println(rs.getString("Codigo_personal")+" : "+rs.getString("Nombre"));
			}			
			
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			
			Conn.commit();
			Conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}	*/
}