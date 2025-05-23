
package aca.matricula;

import java.sql.*;
import java.util.ArrayList;

public class MatriculaUtil{	
	
	public static ArrayList<String> InscritosPorCarga(Connection Conn, String cargaId, String orden) throws SQLException{
		ArrayList<String> lista		= new ArrayList<String>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		try{	
			comando	=	"SELECT FACULTAD_ID, CARRERA_ID, SEXO, NACIONALIDAD, RELIGION_ID, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, CRED_ALTA, " +
					" CRED_BAJA, ESTADO, CODIGO_PERSONAL " +								
				"FROM ENOC.ESTADISTICA " +
				"WHERE CARGA_ID='"+cargaId+"' " +orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				String objeto = "";
				objeto = rs.getString("FACULTAD_ID")+"@"+rs.getString("CARRERA_ID")+"@"+rs.getString("SEXO")+"@"+rs.getString("NACIONALIDAD")+"@" +
						rs.getString("RELIGION_ID")+"@"+rs.getString("MODALIDAD_ID")+"@"+rs.getString("CLAS_FIN")+"@"+rs.getString("RESIDENCIA_ID")+"@"+
						rs.getString("CRED_ALTA")+"@"+rs.getString("CRED_BAJA")+"@"+rs.getString("CODIGO_PERSONAL");
				
				
				lista.add(objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|InscritosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public static ArrayList<String> InscritosPorCargas(Connection Conn, String cargaId, String modalidadId,String fechaIni, String fechaFin, String orden) throws SQLException{
		ArrayList<String> lista		= new ArrayList<String>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		try{	
			comando	=	"SELECT FACULTAD_ID, CARRERA_ID, SEXO, NACIONALIDAD, RELIGION_ID, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, CRED_ALTA, " +
					" CRED_BAJA, ESTADO, CODIGO_PERSONAL " +								
					" FROM ENOC.ESTADISTICA " +
					" WHERE CARGA_ID IN ("+cargaId+") AND MODALIDAD_ID IN ("+modalidadId+") "+
					" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') " +orden;							
			rs = st.executeQuery(comando);
			while (rs.next()){
				String objeto = "";
				objeto = rs.getString("FACULTAD_ID")+"@"+rs.getString("CARRERA_ID")+"@"+rs.getString("SEXO")+"@"+rs.getString("NACIONALIDAD")+"@" +
						rs.getString("RELIGION_ID")+"@"+rs.getString("MODALIDAD_ID")+"@"+rs.getString("CLAS_FIN")+"@"+rs.getString("RESIDENCIA_ID")+"@"+
						rs.getString("CRED_ALTA")+"@"+rs.getString("CRED_BAJA")+"@"+rs.getString("CODIGO_PERSONAL");
				
				
				lista.add(objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|InscritosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	// TODOS LOS CURSOS DISPONIBLES QUE PUEDE TOMAR UN ALUMNO
	public ArrayList<String> getListCursosDisp(Connection conn, String matricula, String carga, String planId, String modalidadId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = " SELECT"
					+ " CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID,"
					+ " A.CARRERA_ID,"
					+ " A.CICLO,"
					+ " A.CURSO_ID,"
					+ " A.CURSO_CARGA_ID,"
					+ " A.NOMBRE_CURSO,"
					+ " COALESCE(A.GRUPO,' ') AS GRUPO,"
					+ " TRIM(COALESCE(A.GRUPO,'X')) AS LETRA,"
					+ " COALESCE(EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR,"
					+ " ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA,"
					+ " ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA,"
					+ " (COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS,"
					+ " A.CREDITOS,"
					+ " ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD,"
					+ " COALESCE(TO_CHAR(A.BLOQUE_ID),'X') AS BLOQUE,"
					+ " A.OPTATIVA AS OPTATIVA"
					+ " FROM ENOC.CARGA_ACADEMICA A, CARGA B"
					+ " WHERE B.CARGA_ID = '"+carga+"'"
					+ " AND A.CARGA_ID = B.CARGA_ID"
					+ " AND B.ESTADO = '1'"
					+ " AND A.MODALIDAD_ID in ("+modalidadId+")"
					+ " AND A.CURSO_ID IN("
					+ " 	SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"'"
					+ "		AND NOT CURSO_ID IN ("
							+ " SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+matricula+"' AND TIPOCAL_ID IN ('1','M','I')"
					+ "		)"
					+ " 	AND NOT CURSO_ID IN ("
					+ "			SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL  = '"+matricula+"'"
					+ " 		AND PROCESO_ID IN ('C','A','T','R')"
					+ " 		AND EST_MAT != 'N')"
					+ ")"
					+ " ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			//System.out.println(comando);
			rs = st.executeQuery(comando);			
			
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				String tieneHorario="no";
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId = rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = "si";
				}			
			   
					 
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario;
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDisp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	// TODOS LOS CURSOS DISPONIBLES QUE PUEDE TOMAR UN ALUMNO PARA BAJAS
	public ArrayList<String> getListCursosDispBaja(Connection conn, String matricula, String carga, String planId, String modalidadId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = " SELECT " 
					+ " CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, "
					+ " A.CARRERA_ID, A.CICLO, A.CURSO_ID, A.CURSO_CARGA_ID, A.NOMBRE_CURSO, "
					+ " COALESCE(A.GRUPO,' ') AS GRUPO, "
					+ " TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "
					+ " COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "
					+ " ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "
					+ " ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "
					+ " (COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "
					+ " A.CREDITOS, A.ESTADO, ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "
					+ " COALESCE(A.BLOQUE_ID, 0) AS BLOQUE, "
					+ " A.OPTATIVA AS OPTATIVA "
					+ " FROM ENOC.CARGA_ACADEMICA A, ENOC.CARGA B "
					+ " WHERE B.CARGA_ID = '"+carga+"' "
					+ " AND A.CARGA_ID = B.CARGA_ID "
					+ " AND B.ESTADO = '1' "
					+ " AND A.MODALIDAD_ID in ("+modalidadId+") "
					+ " AND (A.CURSO_ID IN ("
					+ " SELECT CURSO_ID FROM ENOC.MAPA_CURSO"
					+ " WHERE PLAN_ID = '"+planId+"' "
					+ " AND NOT CURSO_ID IN ( "
					+ " SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = '"+matricula+"' "
					+ " AND TIPOCAL_ID IN ('1','M','I') "
					+ " AND CURSO_ID NOT IN( SELECT CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPO = 'B' AND CODIGO_PERSONAL='"+matricula+"'))"
					+ " AND NOT CURSO_ID IN ("
					+ " SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL  = '"+matricula+"' "
					+ " AND PROCESO_ID IN ('C','A','T','R') "
					+ " AND EST_MAT != 'N')) "
					+ " AND A.CURSO_CARGA_ID NOT IN( SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL='"+matricula+"'))"
					+ " ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			//System.out.println(comando);
			rs = st.executeQuery(comando);				
			
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				String tieneHorario="no";
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId = rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = "si";
				}						 
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario+"~"+
							rs.getString("ESTADO");
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDispBaja|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	public ArrayList<String> getListCursosDispBaja(Connection conn, String matricula, String carga, String bloqueId, String planId, String modalidadId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = " SELECT " 
					+ " CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, "
					+ " A.CARRERA_ID, A.CICLO, A.CURSO_ID, A.CURSO_CARGA_ID, A.NOMBRE_CURSO, "
					+ " COALESCE(A.GRUPO,' ') AS GRUPO, "
					+ " TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "
					+ " COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "
					+ " ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "
					+ " ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "
					+ " (COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "
					+ " A.CREDITOS, A.ESTADO, ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "
					+ " COALESCE(A.BLOQUE_ID, 0) AS BLOQUE, "
					+ " A.OPTATIVA AS OPTATIVA "
					+ " FROM ENOC.CARGA_ACADEMICA A, ENOC.CARGA B "
					+ " WHERE B.CARGA_ID = '"+carga+"' "
					+ " AND BLOQUE_ID = '"+bloqueId+"'"
					+ " AND A.CARGA_ID = B.CARGA_ID "
					+ " AND B.ESTADO = '1' "
					+ " AND A.MODALIDAD_ID in ("+modalidadId+") "
					+ " AND (A.CURSO_ID IN ("
					+ " SELECT CURSO_ID FROM ENOC.MAPA_CURSO"
					+ " WHERE PLAN_ID = '"+planId+"' "
					+ " AND NOT CURSO_ID IN ( "
					+ " SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = '"+matricula+"' "
					+ " AND TIPOCAL_ID IN ('1','M','I') "
					+ " AND CURSO_ID NOT IN( SELECT CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPO = 'B' AND CODIGO_PERSONAL='"+matricula+"'))"
					+ " AND NOT CURSO_ID IN ("
					+ " SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL  = '"+matricula+"' "
					+ " AND PROCESO_ID IN ('C','A','T','R') "
					+ " AND EST_MAT != 'N')) "
					+ " AND A.CURSO_CARGA_ID NOT IN( SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL='"+matricula+"'))"
					+ " ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			rs = st.executeQuery(comando);				
			
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				String tieneHorario="no";
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId = rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = "si";
				}						 
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario+"~"+
							rs.getString("ESTADO");
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDispBaja|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	// TODOS LOS CURSOS EXISTENTES PARA UN ALUMNO POR CURSO_ID
	public ArrayList<String> getListCursosDispPorCursoId(Connection conn, String matricula, String carga, String planId, String modalidadId, String cursoId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = "SELECT " +
			"CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, " +			
			"A.CARRERA_ID, "+
			"A.CICLO, " +
			"A.CURSO_ID, " +
			"A.CURSO_CARGA_ID, " +
			"A.NOMBRE_CURSO, "+
			"COALESCE(A.GRUPO,' ') AS GRUPO, "+
			"TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "+
			"COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "+
			"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "+
			"ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "+
			"COALESCE(A.HH,0) AS HORAS, "+
			"A.CREDITOS, " +
			"ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "+
			"COALESCE(TO_CHAR(A.BLOQUE_ID),'X') AS BLOQUE, " +
			"A.OPTATIVA AS OPTATIVA "+
			"FROM ENOC.CARGA_ACADEMICA A, CARGA B "+ 
			"WHERE B.CARGA_ID = '"+carga+"' "+
			"AND A.CARGA_ID = B.CARGA_ID "+
			"AND B.ESTADO = '1' "+
			"AND A.MODALIDAD_ID in ("+modalidadId+") "+
			"AND A.CURSO_ID = '"+cursoId+"' " +
			"AND A.CURSO_ID IN ("+
				"SELECT CURSO_ID FROM ENOC.MAPA_CURSO "+ 
				"WHERE PLAN_ID = '"+planId+"' " +
				"AND NOT CURSO_ID IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = '"+matricula+"' " +
					"AND TIPOCAL_ID IN ('1','M','I')"+
				")"+
				"AND NOT CURSO_ID IN (" +
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+
				")"+				
			") " +
			"ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			rs = st.executeQuery(comando);				
		    
			while (rs.next()){				
								
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				boolean tieneHorario=false;
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId=rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = true;
				}
					 
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario;
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDispPorCursoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	// TODOS LOS CURSOS EXISTENTES PARA UN ALUMNO POR CURSO_ID CUANDO YA ESTA INSCRITO O TIENE MATERIAS ASIGNADAS
	public ArrayList<String> getListCursosDispAsignados(Connection conn, String matricula, String carga, String planId, String modalidadId, String cursoId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = "SELECT " +
			"CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, " +			
			"A.CARRERA_ID, "+
			"A.CICLO, " +
			"A.CURSO_ID, " +		
			"A.CURSO_CARGA_ID, " +
			"A.NOMBRE_CURSO, "+
			"COALESCE(A.GRUPO,' ') AS GRUPO, "+
			"TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "+
			"COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "+
			"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "+
			"ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "+
			"(COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "+
			"A.CREDITOS, " +
			"ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "+
			"COALESCE(TO_CHAR(A.BLOQUE_ID),'X') AS BLOQUE, " +
			"A.OPTATIVA AS OPTATIVA "+
			"FROM ENOC.CARGA_ACADEMICA A, CARGA B "+ 
			"WHERE B.CARGA_ID = '"+carga+"' "+
			"AND A.CARGA_ID = B.CARGA_ID "+
			"AND B.ESTADO = '1' "+
			"AND A.MODALIDAD_ID in ("+modalidadId+") "+
			"AND A.CURSO_ID = '"+cursoId+"' " +
			"AND A.CURSO_ID IN ("+
				"SELECT CURSO_ID FROM ENOC.MAPA_CURSO "+ 
				"WHERE PLAN_ID = '"+planId+"' " +
				"AND NOT CURSO_ID IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = '"+matricula+"' " +
					"AND TIPOCAL_ID IN ('1')"+
				")"+
				"AND NOT CURSO_ID IN (" +
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+
				")"+				
			") " +
			"ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			//System.out.println(comando);
			rs = st.executeQuery(comando);	
		    
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				boolean tieneHorario=false;
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId=rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = true;
				}
				
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario;
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDispAsignados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	// TODOS LOS CURSOS EXISTENTES PARA UN ALUMNO
	public ArrayList<String> getListCursos(Connection conn, String matricula, String carga, String planId, String modalidadId) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = "SELECT " +
			"CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, " +			
			"A.CARRERA_ID, "+
			"A.CICLO, " +
			"A.CURSO_ID, " +		
			"A.CURSO_CARGA_ID, " +
			"A.NOMBRE_CURSO, "+
			"COALESCE(A.GRUPO,' ') AS GRUPO, "+
			"TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "+
			"COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "+
			"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "+
			"ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "+
			"(COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "+
			"A.CREDITOS, " +
			"ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "+
			"COALESCE(TO_CHAR(A.BLOQUE_ID),'X') AS BLOQUE, " +
			"A.OPTATIVA AS OPTATIVA "+
			"FROM ENOC.CARGA_ACADEMICA A, CARGA B "+ 
			"WHERE B.CARGA_ID = '"+carga+"' "+
			"AND A.CARGA_ID = B.CARGA_ID "+
			"AND B.ESTADO = '1' "+
			"AND A.MODALIDAD_ID in ("+modalidadId+") "+
			"AND A.CURSO_ID IN ("+
				"SELECT CURSO_ID FROM ENOC.MAPA_CURSO "+ 
				"WHERE PLAN_ID = '"+planId+"' " +
				"AND NOT CURSO_ID IN (" +
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+
				")"+				
			") " +
			"ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			//System.out.println(comando);
			rs = st.executeQuery(comando);		
			
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				String tieneHorario	= "no";
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId		= rs.getString("CURSO_CARGA_ID");
			    sql="SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = "si";
				}
				
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+
							tieneHorario;
					//System.out.println(cadena);						
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	// CURSOS DISPONIBLES PARA UN ALUMNO FILTRADOS POR GRUPO
	public ArrayList<String> getListCursosDisp(Connection conn, String matricula,String carga, String planId, String modalidadId, String grupo) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2 		= null;
		
		String comando	= "", sql="", cursoCargaId="";
		String cadena = "";
		try{
			
			comando = "SELECT " +
			"CASE a.PLAN_ID WHEN '"+planId+"' THEN '1' ELSE '2' END AS PLAN_ID, " +			
			"A.CARRERA_ID, "+
			"A.CICLO, " +
			"A.CURSO_ID, " +
			"A.CURSO_CARGA_ID, " +
			"A.NOMBRE_CURSO, "+
			"COALESCE(A.GRUPO,' ') AS GRUPO, "+
			"TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "+
			"COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "+
			"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "+
			"ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "+
			"(COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "+
			"A.CREDITOS, " +
			"ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "+
			"COALESCE(TO_CHAR(A.BLOQUE_ID),'X') AS BLOQUE, " +
			"A.OPTATIVA AS OPTATIVA "+			
			"FROM ENOC.CARGA_ACADEMICA A, CARGA B "+ 
			"WHERE B.CARGA_ID = '"+carga+"' "+
			"AND A.CARGA_ID = B.CARGA_ID "+
			"AND B.ESTADO = '1' "+
			"AND A.GRUPO = '"+grupo+"'"+
			"AND A.MODALIDAD_ID in ("+modalidadId+") "+
			"AND A.CURSO_ID IN ("+
				"SELECT CURSO_ID FROM ENOC.MAPA_CURSO "+ 
				"WHERE PLAN_ID = '"+planId+"' " +
				"AND NOT CURSO_ID IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = '"+matricula+"' " +
					"AND TIPOCAL_ID IN ('1','M','I')"+
				")"+
				"AND NOT CURSO_ID IN (" +
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+ 
				")"+				
			") " +
			"ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";
			//System.out.println(comando);
			rs = st.executeQuery(comando);	
			
			while (rs.next()){
				
				// Verifica la cantidad de periodos registrados en la materia y determina si cumple con el horario
				String tieneHorario	= "no";
				int horasRequeridas = Integer.parseInt(rs.getString("HORAS"));
				int horasMateria	= 0;
			    cursoCargaId		= rs.getString("CURSO_CARGA_ID");
			    sql	= "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
				rs2 = st2.executeQuery(sql);
				if(rs2.next()){
					horasMateria = rs2.getInt("NUMHORAS");
				    if (horasMateria >= horasRequeridas) tieneHorario = "si";
				}
				
				cadena = 	rs.getString("PLAN_ID")+"~"+
							rs.getString("CARRERA_ID")+"~"+
							rs.getString("CICLO")+"~"+
							rs.getString("CURSO_ID")+"~"+
							rs.getString("CURSO_CARGA_ID")+"~"+
							rs.getString("NOMBRE_CURSO")+"~"+
							rs.getString("GRUPO")+"~"+
							rs.getString("LETRA")+"~"+
							rs.getString("PROFESOR")+"~"+
							rs.getString("CARRERA")+"~"+
							rs.getString("L_CARRERA")+"~"+
							rs.getString("HORAS")+"~"+
							rs.getString("CREDITOS")+"~"+
							rs.getString("MODALIDAD")+"~"+
							rs.getString("BLOQUE")+"~"+
							rs.getString("OPTATIVA")+"~"+							
							tieneHorario;
				//System.out.println(cadena);						
				lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosDisp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return lisCursos;
	}
	
	public ArrayList<String> getListCursosMat(Connection conn, String matricula,String carga, String planId, String modalidadId) throws SQLException{		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String cadena 		= "";
		
		try{	
		
		comando	=	"select	curso_carga_id, curso_id, COALESCE(grupo,' ') grupo, ENOC.NOMBRE_CARRERA_CORTO(carrera_id) eap, "+
				"ENOC.EMP_APELLIDO(maestro) profesor, ciclo, creditos, COALESCE(horario,'0') horario, "+				
				"(COALESCE(ht,0)+COALESCE(hp,0)) hr, nombre_curso, tipocal_id,  ENOC.MODALIDAD_NOMBRE(modalidad_id) modalidad, "+
				"COALESCE(to_char(bloque_id),'X') bloque, OPTATIVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"where codigo_personal = '"+matricula+"' "+
				"and carga_id = '"+carga+"' "+
				"and tipocal_id in('M','I','1') "+
				"order by ciclo, nombre_curso";
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("curso_carga_id")+"~"+
								rs.getString("curso_id")+"~"+
								rs.getString("grupo")+"~"+
								rs.getString("eap")+"~"+
								rs.getString("profesor")+"~"+
								rs.getString("ciclo")+"~"+
								rs.getString("creditos")+"~"+
								rs.getString("horario")+"~"+
								rs.getString("hr")+"~"+
								rs.getString("nombre_curso")+"~"+
								rs.getString("tipocal_id")+"~"+
								rs.getString("MODALIDAD")+"~"+
								rs.getString("BLOQUE")+"~"+
								rs.getString("OPTATIVA");
					lisCursos.add(cadena);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|getListCursosMat|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCursos;
	}
	
	public void bajaTotal(Connection conn, String matricula,String carga) throws SQLException{		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		String comando	= "";
		String cadena = "";
		String cursoCargaId = "";
		try{	
		
		comando	=	"select	curso_carga_id, curso_id, COALESCE(grupo,' ') grupo, ENOC.NOMBRE_CARRERA_CORTO(carrera_id) eap, "+
				"EMP_APELLIDO(maestro) profesor, ciclo, creditos, "+				
				"(COALESCE(ht,0)+COALESCE(hp,0)) hr, nombre_curso, tipocal_id,  ENOC.MODALIDAD_NOMBRE(modalidad_id) modalidad, "+
				"COALESCE(to_char(bloque_id),'X') bloque "+
				"FROM ENOC.ALUMNO_CURSO "+
				"where codigo_personal = '"+matricula+"' "+
				"and carga_id = '"+carga+"' "+
				"and tipocal_id in('M','I','1') "+
				"order by ciclo, nombre_curso";
			rs = st.executeQuery(comando);
			while (rs.next()){
					cursoCargaId = 	rs.getString("curso_carga_id");
					lisCursos.add(cadena);
					comando = "update ENOC.krdx_curso_act set tipocal_id ='3',tipo='B' "+ 
								"where codigo_personal = '"+matricula+"' "+
								"and curso_carga_id ='"+cursoCargaId+"'";
					st2.execute(comando);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|bajaTotal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
			if (st2!=null) st2.close();
		}
	}
	
	public void bajaTotalCoordinador(Connection conn, String matricula,String carga) throws SQLException{
		
		ArrayList<String> lisCursos	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		String comando		= "";
		String cadena 		= "";
		String cursoCargaId = "";
		try{	
		
			comando	= 	"SELECT	CURSO_CARGA_ID, CURSO_ID, COALESCE(GRUPO,' ') AS GRUPO, ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS EAP,"+
			" EMP_APELLIDO(MAESTRO)  AS PROFESOR, CICLO, CREDITOS, "+
			" (COALESCE(HT,0)+COALESCE(HP,0)) AS HR, NOMBRE_CURSO, TIPOCAL_ID,  ENOC.MODALIDAD_NOMBRE(MODALIDAD_ID) AS MODALIDAD,"+
			" COALESCE(TO_CHAR(BLOQUE_ID),'X') AS BLOQUE"+
			" FROM ENOC.ALUMNO_CURSO"+
			" WHERE CODIGO_PERSONAL = '"+matricula+"'"+
			" AND CARGA_ID = '"+carga+"'"+
			" AND TIPOCAL_ID IN('M','I','1')"+
			" ORDER BY CICLO, NOMBRE_CURSO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				cursoCargaId = 	rs.getString("CURSO_CARGA_ID");
				lisCursos.add(cadena);
				comando = "UPDATE ENOC.KRDX_CURSO_ACT SET TIPO = 'B' "+ 
							"WHERE CODIGO_PERSONAL = '"+matricula+"' "+
							"AND CURSO_CARGA_ID ='"+cursoCargaId+"'";
				st2.execute(comando);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|bajaTotalCoordinador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
			if (st2!=null) st2.close();
		}
	}
	
	public boolean preCursosAprobados(Connection conn, String matricula, String planId, String cursoId) throws SQLException{
		
		boolean OK	= false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		Statement st2 		= conn.createStatement();
		ResultSet rs2		= null;
		String comando	= "";
		String comando2	= "";
		try{
			
		comando	=	"SELECT B.CURSO_ID, B.CURSO_ID_PRE CURSO_ID_PRE " +
				"FROM ENOC.MAPA_CURSO A, ENOC.MAPA_CURSO_PRE B "+ 
				"WHERE A.CURSO_ID = B.CURSO_ID " +
				"AND A.PLAN_ID = '"+planId+"' " +
				"AND A.CURSO_ID = '"+cursoId+"' "+
				"AND NOT B.CURSO_ID_PRE IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND TIPOCAL_ID = '1' "+ 
					" )" +
				"AND NOT B.CURSO_ID_PRE IN( "+
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+				
					" )";			
			rs = st.executeQuery(comando);
			//preCursosAprobados(conn,matricula,planId,rs.getString("CURSO_ID_PRE"));
			if (!rs.next()){	
				comando2=	"SELECT B.CURSO_ID, B.CURSO_ID_PRE CURSO_ID_PRE " +
						"FROM ENOC.MAPA_CURSO A, ENOC.MAPA_CURSO_PRE B "+ 
						"WHERE A.CURSO_ID = B.CURSO_ID " +
						"AND A.PLAN_ID = '"+planId+"' " +
						"AND A.CURSO_ID = '"+cursoId+"' ";				
				rs2 = st2.executeQuery(comando2);
				boolean entro = false;
				while(rs2.next()){
					OK=preCursosAprobados(conn,matricula,planId,rs2.getString("CURSO_ID_PRE"));
					entro = true;
				}
				if(entro == false)
					OK = true;
			}else{
				OK = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|preCursosAprobados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (st2!=null) st2.close();
		}
		
		return OK;
	}
	
	public String preCursoFalta(Connection conn, String matricula, String cursoId) throws SQLException{
				
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String preCurso		= "";
		try{
			
		comando	="SELECT CURSO_ID_PRE " +
				"FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = '"+cursoId+"' "+
				"AND NOT CURSO_ID_PRE IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO " +
					"WHERE CODIGO_PERSONAL  = '"+matricula+"' "+
					"AND TIPOCAL_ID = '1' "+
					" )";
			rs = st.executeQuery(comando);
			if (rs.next()){
				preCurso = rs.getString("CURSO_ID_PRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|preCursoFalta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return preCurso;
	}
	
	public int totalInscritos(Connection conn, String cargaId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"select count(distinct codigo_personal) total FROM ENOC.ESTADISTICA where carga_Id = '"+cargaId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("total");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public int totalInscritosFac(Connection conn, String cargaId,String facultadId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"select count(distinct codigo_personal) total FROM ENOC.ESTADISTICA where carga_Id = '"+cargaId+"' and ENOC.FACULTAD(carrera_id)='"+facultadId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("total");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalInscritosFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public int totalCCobro(Connection conn, String cargaId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"select count(matricula) num_cobros from mateo.fes_ccobro "+
						"Where carga_Id = '"+cargaId+"' and inscrito = 'N' "+
						"AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID, 1, 6) = '"+cargaId+"' " + 
						"AND TIPOCAL_ID = 'M')";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cobros");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCCObro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public int totalCCobroFac(Connection conn, String cargaId, String facultadId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"select count(matricula) num_cobros from mateo.fes_ccobro Where carga_Id = '"+cargaId+"' and inscrito = 'N' and facultad_id='"+facultadId+"' "+ 
			"AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID, 1, 6) = '"+cargaId+"' " + 
			"AND TIPOCAL_ID = 'M')";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cobros");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCCobroFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public int totalCarga(Connection conn, String cargaId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=		"select count(distinct(codigo_personal)) num_cargas from ENOC.krdx_curso_act where substr(curso_carga_Id,1,6) = '"+cargaId+"' "+ 
										"and codigo_personal not in (Select matricula from mateo.fes_ccobro Where carga_Id = '"+cargaId+"') "+ 
										"and tipocal_id = 'M'";		
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cargas");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}	
	public int totalCargaFac(Connection conn, String cargaId, String facultadId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=		"select count(distinct(codigo_personal)) num_cargas from ENOC.krdx_curso_act where substr(curso_carga_Id,1,6) = '"+cargaId+"' "+ 
										"and codigo_personal not in (Select matricula from mateo.fes_ccobro Where carga_Id = '"+cargaId+"') "+ 
										"and tipocal_id = 'M' and ENOC.alumno_fac_id(codigo_personal) = '"+facultadId+"'";		
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cargas");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCargaFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}	
	
	public int[] estadistica(Connection conn, String cargaId) throws SQLException{
		int ret[] = {0,0,0,0,0,0,0,0,0};
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{	
			comando	=	"Select distinct codigo_personal,residencia_id, sexo, nacionalidad, clas_fin FROM ENOC.ESTADISTICA Where carga_Id = '"+cargaId+"'";									 
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 		ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 		ret[6]++; else ret[7]++;								
			}
			comando	=	"Select sum(ENOC.creditos(curso_id)) as creditos from ENOC.krdx_curso_act where substr(curso_carga_Id,1,6)= '"+cargaId+"' "+ 
						"and tipocal_id != 'M' and tipocal_id != '3'";		
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("creditos");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadistica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		return ret;
	}	
	public int[] facultadEstadistica(Connection conn, String cargaId, String facultadId) throws SQLException{
		int ret[] = {0,0,0,0,0,0,0,0,0};
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, " +
						"RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN " +
						"FROM ENOC.ESTADISTICA " +
						"WHERE CARGA_ID = '"+cargaId+"' " +
						"AND ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"'";									
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 		ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 		ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.CREDITOS(CURSO_ID)) AS CREDITOS " +
						"FROM ENOC.KRDX_CURSO_ACT " + 
						"WHERE SUBSTR(CURSO_CARGA_ID,1,6)= '"+cargaId+"' "+
						"AND TIPOCAL_ID NOT IN ('M','3') " +						
						"AND ALUMNO_FAC_ID(CODIGO_PERSONAL) = '"+facultadId+"'";	
			rs2 = st.executeQuery(comando);
			if (rs2.next()){
				ret[8] = rs2.getInt("creditos");
			}else{
				ret[8] = 0;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|facultadEstadistica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return ret;
	}	
	public ArrayList<String> estadisticaFac(Connection conn, String cargaId) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	=	"SELECT ENOC.FACULTAD_NOMBRE(FACULTAD_ID) AS NOMBRE_FACULTAD,"+
						"FACULTAD_TITULO(FACULTAD_ID) AS TITULO_FACULTAD,"+
						"FACULTAD_ID AS FACULTAD_ID,"+
	  					"INSCRITOS_FAC(FACULTAD_ID,'"+cargaId+"') AS INSCRITOS "+
						"FROM ENOC.ESTADISTICA "+
						"WHERE CARGA_ID='"+cargaId+"' "+						
						"GROUP BY FACULTAD_ID "+
						"ORDER BY 4";
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("nombre_facultad")+"~"+
								rs.getString("titulo_facultad")+"~"+
								rs.getString("facultad_id")+"~"+
								rs.getString("inscritos");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFac;
	}		
	public ArrayList<String> estadisticabFac(Connection Conn, String cargaId) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	=	"SELECT ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)) AS NOMBRE_FACULTAD,"+
							"FACULTAD_TITULO(ENOC.FACULTAD(CARRERA_ID)) AS TITULO_FACULTAD,"+
							"ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID,"+
	  						"INSCRITOS_FAC_TOT(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS INSCRITOS,"+
	  						"BAJAS_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS BAJAS,"+
	  						"INTERNOS_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS INTERNOS,"+
	  						"HOMBRES_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS HOMBRES,"+
	  						"MEXICANOS_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS MEXICANOS,"+
	  						"ACFE_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS ACFE,"+
	  						"PRESENCIALES_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS PRESENCIALES,"+
	  						"UNID_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS UNID,"+
	  						"ONLINE_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS ON_LINE,"+								
	  						"NOCTURNA_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS NOCTURNA,"+
	  						"EXTENSION_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS EXTENSION,"+								
	  						"CREDITOS_FAC(ENOC.FACULTAD(CARRERA_ID),'"+cargaId+"') AS CREDITOS "+
						"FROM ENOC.ESTADISTICA "+
						"WHERE CARGA_ID='"+cargaId+"' "+
						"GROUP BY ENOC.FACULTAD(CARRERA_ID) "+
						"ORDER BY 3"; 
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("NOMBRE_FACULTAD")+"~"+
								rs.getString("TITULO_FACULTAD")+"~"+
								rs.getString("FACULTAD_ID")+"~"+
								rs.getString("INSCRITOS")+"~"+
								rs.getString("BAJAS")+"~"+
								rs.getString("INTERNOS")+"~"+
								rs.getString("HOMBRES")+"~"+
								rs.getString("MEXICANOS")+"~"+
								rs.getString("ACFE")+"~"+
								rs.getString("PRESENCIALES")+"~"+
								rs.getString("UNID")+"~"+
								rs.getString("ON_LINE")+"~"+
								rs.getString("NOCTURNA")+"~"+
								rs.getString("EXTENSION")+"~"+
								rs.getString("CREDITOS");
					lisFac.add(cadena);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFac;
	}
	
	public String bajasCarrera(Connection Conn, String cargaId, String carreraId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", bajas="0";
		try{	
			comando	=	"SELECT BAJAS_CARR(CARRERA_ID, '"+cargaId+"') AS BAJAS " +				
				"FROM ENOC.CAT_CARRERA " + 
				"WHERE CARRERA_ID='"+carreraId+"' ";									
			rs = st.executeQuery(comando);
			if (rs.next()){
				bajas = rs.getString("BAJAS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|bajasCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return bajas;
	}
	
	public ArrayList<String> estadisticaCarr(Connection Conn, String cargaId) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="", inscritos="", bajas = "";		
		try{	
			comando	=	"SELECT CARRERA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, " +
				"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS NOMBRE " +								
				"FROM ENOC.ESTADISTICA " +
				"WHERE CARGA_ID='"+cargaId+"' " +
				"GROUP BY CARRERA_ID " +
				"ORDER BY 2, ENOC.CARRERA_NIVEL(CARRERA_ID),3";							
			rs = st.executeQuery(comando);
			while (rs.next()){
				inscritos	= String.valueOf(this.getInscritosxCarrera(Conn,cargaId,rs.getString("CARRERA_ID")));				 
				bajas 		= this.bajasCarrera(Conn,cargaId,rs.getString("CARRERA_ID"));
				
				cadena = 	rs.getString("CARRERA_ID")+"~"+
							rs.getString("FACULTAD")+"~"+
							rs.getString("NOMBRE")+"~"+
							inscritos+"~"+
							bajas;
				lisFac.add(cadena);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaCarr|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFac;
	}			
	public ArrayList<String> estadisticaCarrera(Connection Conn, String cargaId, String facultadId) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	=	"SELECT CARRERA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, " +
				"ENOC.NOMBRE_CARRERA(CARRERA_ID) AS NOMBRE, " +
				"COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL "+
				"FROM ENOC.ESTADISTICA " +
				"WHERE CARGA_ID='"+cargaId+"' " +
				"AND ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' " +
				"GROUP BY CARRERA_ID " +
				"ORDER BY 4 DESC";									
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("carrera_id")+"~"+
											rs.getString("facultad")+"~"+
											rs.getString("nombre")+"~"+
											rs.getString("total");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFac;
	}			
	
	public int[] estadisticaCarrDetalle(Connection Conn, String cargaId, String s_carrera_id) throws SQLException{
		int ret[]= new int[14];
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, " +
				"RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN, MODALIDAD_ID "+
				"FROM ENOC.ESTADISTICA " +
				"WHERE CARGA_ID = '"+cargaId+"' AND " +
				"CARRERA_ID='"+s_carrera_id+"'";									
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)				ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1"))		ret[6]++; else ret[7]++;								
				
				if (rs.getString("modalidad_id").equals("1"))
					ret[8]++; 
				else if(rs.getString("modalidad_id").equals("2"))
					ret[9]++;								
				else if(rs.getString("modalidad_id").equals("5")) 
					ret[10]++;
				else if(rs.getString("modalidad_id").equals("4"))
					ret[11]++;
				else if(rs.getString("modalidad_id").equals("3")||rs.getString("modalidad_id").equals("7")||
						rs.getString("modalidad_id").equals("8")||rs.getString("modalidad_id").equals("9")||
						rs.getString("modalidad_id").equals("10"))
					ret[12]++;
			}
			comando	=	"Select creditos_carr(carrera_id,'"+cargaId+"') creditos from ENOC.cat_carrera where carrera_id='"+s_carrera_id+"'"; 
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[13] = rs2.getInt("creditos");		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaCarrDetalle|:"+ex);
		}finally{
			if (rs2!=null) rs2.close();
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }			
		}
		return ret;
	}	
	public int[] estadisticaCarrDetalle2(Connection Conn, String cargaId, String s_carrera_id) throws SQLException{
		int ret[] = new int[12];
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"Select count(residencia_id) residencia "+
									"FROM ENOC.ESTADISTICA Where carga_Id = '"+cargaId+"' and carrera_id='"+s_carrera_id+"'"+ 
									"group by residencia_id	";			
			rs = st.executeQuery(comando);			
			rs.next();ret[0]=rs.getInt("residencia");
			rs.next();ret[1]=rs.getInt("residencia");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|estadisticaCarrDetalle2|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return ret;
	}
	
	
	public int getCreditosVendidosxFacultad(Connection conn,String carga,String facultad) throws SQLException{
	    int creditos=0;
	    PreparedStatement ps =null;
	    ResultSet rs=null;
	    
	    try {
	        ps = conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS CREDITOS"+ 
					" FROM  MATEO.FES_CC_MATERIA"+ 
					" WHERE CARGA_ID = ?"+
					" AND FACULTAD_ID = ?"+
					" AND MATRICULA IN("+ 
						" SELECT MATRICULA "+
						" FROM MATEO.FES_CCOBRO "+
						" WHERE CARGA_ID = MATEO.FES_CC_MATERIA.CARGA_ID "+ 
						" AND FACULTAD_ID = MATEO.FES_CC_MATERIA.FACULTAD_ID "+
						" AND INSCRITO = 'S')");
    	    ps.setString(1,carga);
    	    ps.setString(2,facultad);
    	    rs=ps.executeQuery();
    	    if(rs.next())creditos=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            ps.close();
            rs.close();
        }
	    return creditos;
	}

	public int getCreditosVendidosxCarrera(Connection conn,String carga,String carrera) throws SQLException{
	    int creditos=0;
	    PreparedStatement ps =null;
	    ResultSet rs=null;
	    
	    try {
	        ps = conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS CREDITOS"+ 
					" FROM  MATEO.FES_CC_MATERIA"+ 
					" WHERE CARGA_ID = ?"+
					" AND CARRERA_ID = ?"+
					" AND MATRICULA IN("+ 
						" SELECT MATRICULA "+
						" FROM MATEO.FES_CCOBRO "+
						" WHERE CARGA_ID = MATEO.FES_CC_MATERIA.CARGA_ID "+ 
						" AND CARRERA_ID = MATEO.FES_CC_MATERIA.CARRERA_ID "+
						" AND INSCRITO = 'S')");
    	    ps.setString(1,carga);
    	    ps.setString(2,carrera);
    	    rs=ps.executeQuery();
    	    if(rs.next())creditos=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            ps.close();
            rs.close();
        }
	    return creditos;
	}
	
	public int getInscritosxCarrera(Connection conn,String carga,String carrera) throws SQLException{
	    int inscritos=0;
	    PreparedStatement ps =null;
	    ResultSet rs=null;	    
	    try {
	        ps = conn.prepareStatement("SELECT INSCRITOS_CARR('"+carrera+"','"+carga+"') AS INSCRITOS FROM ENOC.CAT_CARRERA WHERE CARRERA_ID =?"); 
    	    ps.setString(1,carrera);
    	    rs=ps.executeQuery();
    	    if(rs.next()) inscritos=rs.getInt("INSCRITOS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            ps.close();
            rs.close();
        }
	    return inscritos;
	}
	
	// Este mxxtodo verifica si choca el horario de la materia elegida con las materias asignadas de un alumno en la carga y bloque en que se estxx inscribiendo
	public String revisarChoqueDeHorarioPorAlumnoMateria(Connection conn, String codigoPersonal, String cargaId, String cursoCargaIdElegida, String bloqueId) throws SQLException{
		String materiaMal = "ok";
		
		
		// Lista de materias del alumno en la carga y bloque en que se inscribe 
		ArrayList<aca.objeto.Hora> lisHorasAlumno = aca.objeto.Hora.listaHorasDelAlumno(conn, codigoPersonal, cargaId, "'M','I'", bloqueId);
		if(!lisHorasAlumno.isEmpty()){		 
		
			ArrayList<aca.objeto.Hora> lisHorasMateria = aca.objeto.Hora.getListaHorasDeMateria(conn, cursoCargaIdElegida);

	/*------------------- Verifica si choca algxxn periodo de la materia elegida con alguno de las materias asignadas ----------------*/		
			for(aca.objeto.Hora horasAlumno : lisHorasAlumno){
				
				int inicioAlumno 	= Integer.parseInt(horasAlumno.getInicio());
				int finAlumno 		= Integer.parseInt(horasAlumno.getFin());
				
				for (aca.objeto.Hora horasMateria : lisHorasMateria){
					int inicioMateria 	= Integer.parseInt(horasMateria.getInicio());
					int finMateria 		= Integer.parseInt(horasMateria.getFin());
					if ( 
						(inicioMateria >= inicioAlumno && inicioMateria < finAlumno) || 
						(finMateria > inicioAlumno && finMateria <= finAlumno) ||
						(inicioMateria <= inicioAlumno && finMateria >= finAlumno)
						)
					{
						materiaMal = horasAlumno.getCursoCargaId();
						break;
					}
				}				
			}
		}
		return materiaMal;
	}	
}