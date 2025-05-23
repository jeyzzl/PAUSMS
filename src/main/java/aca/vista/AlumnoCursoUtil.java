// Clase Util para la tabla de Carga
package aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

import aca.kardex.KrdxAlumnoEval;
import aca.plan.MateriaVO;

public class AlumnoCursoUtil{
	
	
	public AlumnoCurso mapeaRegId( Connection conn, String codigoPersonal, String cursoCargaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"COALESCE(TO_CHAR(CICLO,'99'),'0') AS CICLO, "+
				"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
				"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
				"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
				"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
				"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
				"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
				"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION," +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()) 
				alumnoCurso.mapeaReg(rs);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumnoCurso;
	}
	
	public AlumnoCurso mapeaCursoAprobado( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs 	= null;
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		try{
			ps = conn.prepareStatement("SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"COALESCE(TO_CHAR(CICLO,'99'),'0') AS CICLO, "+
					"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
					"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
					"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
					"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
					"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
					"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
					"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
					"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+					
					"CORRECCION," +
					"OPTATIVA," +
					"NOTA_CONVA "+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CURSO_ID = ? "+			
					" AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				alumnoCurso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapeaCursoAprobado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumnoCurso;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cursoCargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean tieneCurso(Connection conn, String codigoPersonal, String cursoId, String tipoCalId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_ID = ?"
					+ " AND TIPOCAL_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			ps.setString(3, tipoCalId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeCodigoPersonal(Connection conn, String codigoPersonal) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|existeCodigoPersonal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Obtiene el promedio de un alumno en una carga (Omite componentes y remediales).
	// Considera para el calculo notas AC, NA y RA.
	public static double promedioAlumnoCarga(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		
		double	promedio 		= -1;
		float		creditos	= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		String 	tipocalId		= "";
		String cursoCargaId		= "";
		String estado 			= ""; 
		
		float sumaCreditos		= 0;
		float sumaNotasXCreditos	= 0;
		
		try{	
			
			ps = conn.prepareStatement("SELECT "+
						"CURSO_CARGA_ID, "+
						"ENOC.CURSO_NOMBRE(CURSO_ID) AS CURSO,"+
						"CREDITOS, "+				
						"NOTA, "+
						"NOTA_EXTRA, "+
						"TIPOCAL_ID, "+
						"NOTA_APROBATORIA "+
						"FROM ENOC.ALUMNO_CURSO "+
						"WHERE CODIGO_PERSONAL = ? "+
						"AND CARGA_ID = ? "+
						"AND TIPOCAL_ID IN ('1','2','4','7','I') "+
						"AND CREDITOS > 0");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
				
			rs = ps.executeQuery();
			while (rs.next()){
				cursoCargaId= rs.getString("CURSO_CARGA_ID");
				creditos	= rs.getFloat("CREDITOS");
				nota		= rs.getInt("NOTA");
				notaExtra	= rs.getInt("NOTA_EXTRA");
				tipocalId	= rs.getString("TIPOCAL_ID");
				notaAC		= rs.getInt("NOTA_APROBATORIA");
				estado 		= aca.carga.CargaGrupoUtil.getEstado(conn, cursoCargaId);
				
				// if la materia no esta cerrada calcula la nota de las estrategias
				if( estado.equals("0") || estado.equals("1") || estado.equals("2")){
					nota = Integer.parseInt(String.valueOf(KrdxAlumnoEval.getAlumnoPromedio(conn, cursoCargaId, codigoPersonal)));
					if (nota!=0){
						sumaCreditos += creditos;
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}else{
					sumaCreditos += creditos;
					if ( nota < notaAC && tipocalId.equals("1") ){
						sumaNotasXCreditos	+= notaExtra*creditos;
					}else if (tipocalId.equals("2") ){
						if (notaExtra!=0)
							sumaNotasXCreditos	+= notaExtra*creditos;
						else 
							sumaNotasXCreditos	+= nota*creditos;
					}else{
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}				
				
			}
			
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|promedioAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	// Obtiene el promedio de un alumno en una carga (Omite componentes y remediales). 
	public static double promedioAlumnoCarga(Connection conn, String codigoPersonal, String cargaId, String tipoCal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		
		double	promedio 		= -1;
		float		creditos	= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		String 	tipocalId		= "";
		String cursoCargaId		= "";
		String estado 			= ""; 
		
		float sumaCreditos		= 0;
		float sumaNotasXCreditos	= 0;
		
		try{	
			
			ps = conn.prepareStatement("SELECT "+
						"CURSO_CARGA_ID, "+
						"CURSO_NOMBRE(CURSO_ID) AS CURSO,"+
						"CREDITOS, "+				
						"NOTA, "+
						"NOTA_EXTRA, "+
						"TIPOCAL_ID, "+
						"NOTA_APROBATORIA "+
						"FROM ENOC.ALUMNO_CURSO "+
						"WHERE CODIGO_PERSONAL = ? "+
						"AND CARGA_ID = ? "+
						"AND TIPOCAL_ID IN (?) "+
						"AND CREDITOS > 0");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, tipoCal);
				
			rs = ps.executeQuery();
			while (rs.next()){
				cursoCargaId= rs.getString("CURSO_CARGA_ID");
				creditos	= rs.getFloat("CREDITOS");
				nota		= rs.getInt("NOTA");
				notaExtra	= rs.getInt("NOTA_EXTRA");
				tipocalId	= rs.getString("TIPOCAL_ID");
				notaAC		= rs.getInt("NOTA_APROBATORIA");
				estado 		= aca.carga.CargaGrupoUtil.getEstado(conn, cursoCargaId);
				
				// if la materia no esta cerrada calcula la nota de las estrategias
				if( estado.equals("0") || estado.equals("1") || estado.equals("2")){
					nota = Integer.parseInt(String.valueOf(KrdxAlumnoEval.getAlumnoPromedio(conn, cursoCargaId, codigoPersonal)));
					if (nota!=0){
						sumaCreditos += creditos;
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}else{
					sumaCreditos += creditos;
					if ( nota < notaAC && tipocalId.equals("1") ){
						sumaNotasXCreditos	+= notaExtra*creditos;
					}else if (tipocalId.equals("2") ){
						if (notaExtra!=0)
							sumaNotasXCreditos	+= notaExtra*creditos;
						else 
							sumaNotasXCreditos	+= nota*creditos;
					}else{
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}				
				
			}
			
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|promedioAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
//	Obtiene el promedio de un alumno en el plan de estudios (Omite componentes y remediales).
//  Incluye solo materias aprobadas.. 	
	public static double promedioAlumno(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		
		double	promedio 		= 0;
		float		creditos		= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		
		float sumaCreditos		= 0;
		int sumaNotasXCreditos	= 0;
		
		try{
			ps = conn.prepareStatement("SELECT "+
				"CREDITOS, "+				
				"NOTA, "+
				"NOTA_EXTRA, "+
				"TIPOCAL_ID, "+
				"NOTA_APROBATORIA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) IN (1,2,7) "+
				"AND TIPOCAL_ID = '1' "+
				"AND CONVALIDACION IN ('N','I') "+
				"AND CREDITOS > 0");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);	
			
			rs = ps.executeQuery();
			while (rs.next()){
				creditos	= rs.getFloat("CREDITOS");
				nota		= rs.getInt("NOTA");
				notaExtra	= rs.getInt("NOTA_EXTRA");
				notaAC		= rs.getInt("NOTA_APROBATORIA");
				
				sumaCreditos += creditos;
				
				if ( nota < notaAC ){					 
					sumaNotasXCreditos	+= notaExtra*creditos;
				}else{
					sumaNotasXCreditos	+= nota*creditos;
				}	
			}
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|promedioAlumno|:"+ex); 
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return promedio;
	}
	
	public float creditosEnPlan( Connection conn, String codigoPersonal, String planId, String tipoCalId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT "+
				"SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, tipoCalId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("creditos");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|creditosAprobados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float creditosAprobados( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT "+
				"SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);	
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|creditosAprobados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}	
	
	public static int materiasAlumno( Connection conn, String codigoPersonal, String planId, String tipoCurso) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int nMaterias			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(*) AS MATERIAS " +
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = TO_NUMBER(?,'99') "+
				"AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, tipoCurso);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				nMaterias = rs.getInt("MATERIAS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|materiasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nMaterias;
	}
	
	public static int matAlumReprobadas( Connection conn, String codigoPersonal, String planId, String tipoCurso) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int nMaterias			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(*) AS MATERIAS " +
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = TO_NUMBER(?,'99') "+
				"AND TIPOCAL_ID IN ('2','4')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, tipoCurso);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				nMaterias = rs.getInt("MATERIAS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|materiasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nMaterias;
	}
	
	public int obtenTipoCurso( Connection conn, String cursoId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int tipoCurso			= 0;
		
		try{
			ps 	= conn.prepareStatement("select tipocurso_id from ENOC.mapa_curso where curso_id=?"); 
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next()){		
				tipoCurso = rs.getInt(1);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|obtenTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipoCurso;
	}
	
	public static boolean yaLlevoLaMateria( Connection conn, String codigoPersonal, String cursoId) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		boolean ok				= false;
		
		try{
			ps 	= conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO" +
										" WHERE CODIGO_PERSONAL = ?" +
										" AND CURSO_ID = ?" +
										" AND TIPOCAL_ID IN ('2','3','4','5','6')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			rs = ps.executeQuery();
			if (rs.next()){		
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|yaLlevoLaMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esMateriaAc( Connection conn, String codigoPersonal, String cursoId) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		boolean ok				= false;
		
		try{
			ps 	= conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO" +
										" WHERE CODIGO_PERSONAL = ?" +
										" AND CURSO_ID = ?" +
										" AND TIPOCAL_ID IN ('1')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			rs = ps.executeQuery();
			if (rs.next()){		
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|esMateriaAc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static float numCredOptaCiclo( Connection conn, String codigoPersonal, String planId, String ciclo) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float creditos			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS CRED FROM ENOC.ALUMNO_CURSO" +
										" WHERE CODIGO_PERSONAL = ?" +
										" AND PLAN_ID = ?" +
										" AND CICLO = ?" +
										" AND TIPOCAL_ID IN ('I','1')" +
										" AND ENOC.TIPOCURSO_ID(CURSO_ID) IN (2,7)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, ciclo);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				creditos = rs.getFloat("CRED");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|esMateriaAc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static float getCreditosPorTipoCurso( Connection conn, String codigoPersonal, String planId, String tiposCal, String tiposCurso) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"SUM(CREDITOS) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND TRIM(PLAN_ID) = ? AND TIPOCAL_ID IN ("+tiposCal+") " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE TRIM(PLAN_ID) = ? AND TIPOCURSO_ID IN ("+tiposCurso+"))");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCreditosPorTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float getCrObliAC( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"SUM(CREDITOS) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = '1' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (1,5,8))");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCrObliAC|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float getCrElecAC( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"SUM(CREDITOS) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = '1' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (2,7))");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCrElecAC|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float getCreditosActuales( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT "+
				"SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND TIPOCAL_ID = 'I'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);	
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCreditosActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float getCrElecActuales( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"SUM(CREDITOS) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = 'I' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (2,7))");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCrElecActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
		
	public static float getCrObliActuales( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"SUM(CREDITOS) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = 'I' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (1,5))");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCrObliActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static int getComponentesAct( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int nCreditos			= 0;
		try{
			ps 	= conn.prepareStatement("SELECT " +
					"COUNT(CURSO_ID)AS COMPONENTES " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = 'I' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID = '3')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, planId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nCreditos = rs.getInt("COMPONENTES");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getComponentesAct|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float creditosDelCiclo( Connection conn, String codigoPersonal, String cargaId) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float creditos			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID LIKE ?||'%'" +
					" AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId.substring(0, 5));
			
			rs = ps.executeQuery();
			if (rs.next()){
				if(rs.getFloat("CREDITOS") >= creditos)
					creditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|terminoCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static boolean terminoCiclo( Connection conn, String codigoPersonal, String cargaId, float creditos) throws SQLException, Exception{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		boolean ok				= false;
		
		try{
			ps 	= conn.prepareStatement("SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID LIKE ?||'%'" +
					" AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId.substring(0, 5));
			
			rs = ps.executeQuery();
			if (rs.next()){
				if(rs.getFloat("CREDITOS") >= creditos)
					ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|terminoCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static float getCreditosPermitidosXPeriodo(Connection conn, String codigoPersonal, String cargaId) throws SQLException, Exception{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		float creditos			= -1;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(DISTINCT(CARGA_ID)) * CASE ENOC.ALUMNO_FAC_ID(?) WHEN '107' THEN 59 ELSE 44 END AS CREDITOS"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID LIKE ?||'%'"
					+ " AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TIPOCARGA = 'O')"); 
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, codigoPersonal);
			ps.setString(3, cargaId.substring(0, 5));
			
			rs = ps.executeQuery();
			if (rs.next()){
				creditos = rs.getFloat("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCreditosPermitidosXPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static int getNumExtras(Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int numExtras			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS EXTRAS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?" +
					" AND (NOTA BETWEEN NOTA_APROBATORIA-10 AND NOTA_APROBATORIA-1)"+
					" AND NOTA_EXTRA > 0");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numExtras = rs.getInt("EXTRAS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumExtras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numExtras;
	}
	
	public static int numMatEnPlan(Connection conn, String codigoPersonal, String planId, String tipocalId) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int total		= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND TIPOCAL_ID IN (?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, tipocalId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getInt("TOTAL");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|numMatEnPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public static String getUltimaCarga(Connection conn, String codigoPersonal) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String carga			= "xxxxxx";
		
		try{
			ps 	= conn.prepareStatement("SELECT COALESCE(MAX(REPLACE(CARGA_ID,'2002-1','0102A')),'000000') AS CARGA" +
					" FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				carga = rs.getString("CARGA");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getUltimaCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carga;
	}
	
	public static String getUltimaCargaEstado(Connection conn, String codigoPersonal) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String carga			= "xxxxxx";
		
		try{
			ps 	= conn.prepareStatement("SELECT COALESCE(MAX(REPLACE(CARGA_ID,'2002-1','0102A')),'000000') AS CARGA" +
					" FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				carga = rs.getString("CARGA");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getUltimaCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carga;
	}
	
	public static int getNumMatTipoCal( Connection conn, String codigoPersonal, String cargaId, String tipoCal) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int numMat			= 0;		
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUMMAT " +
			"FROM ENOC.ALUMNO_CURSO "+
			"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
			"AND CARGA_ID = '"+cargaId+"' "+
			"AND TIPOCAL_ID IN("+tipoCal+")";
			
			rs = st.executeQuery(comando);			
			if (rs.next()){		
				numMat 	= rs.getInt("NUMMAT");				
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumMatTipoCal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}	
	
	public static int getNumMatPendientes( Connection conn, String codigoPersonal, String planId, String ciclo) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int numMat			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_ID) AS NUMMAT FROM ENOC.MAPA_CURSO" + 
					" WHERE PLAN_ID = '"+planId+"'"+ 
					" AND CICLO = '"+ciclo+"'"+
					" AND TIPOCURSO_ID IN (1,2,3,5,7,8)"+ 
					" AND CURSO_ID NOT IN " +
					"	(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' AND TIPOCAL_ID IN ('1','I'))";
			
			rs = st.executeQuery(comando);	
			if (rs.next()){		
				numMat 	= rs.getInt("NUMMAT");		
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumMatPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int getNumReprobadas( Connection conn, String codigoPersonal, String cargaId, String carrera) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int num			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS NUM FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = '"+carrera+"'  " +
					" AND TIPOCAL_ID IN ('2','4') " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6)" +
					" AND CODIGO_PERSONAL='"+codigoPersonal+"'";
			
			rs = st.executeQuery(comando);	
			if (rs.next()){		
				num 	= rs.getInt("NUM");		
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumReprobadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static int totMatPorAlumno( Connection conn, String codigoPersonal, String cargaId, String carrera, String tipoCal) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int num			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS TOTAL FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = '"+carrera+"'  " +
					" AND TIPOCAL_ID IN ("+tipoCal+")" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6)" +
					" AND CODIGO_PERSONAL='"+codigoPersonal+"'";
			
			rs = st.executeQuery(comando);	
			if (rs.next()){		
				num 	= rs.getInt("TOTAL");		
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumReprobadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static int getTotalMat( Connection conn, String cargaId, String carrera) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int num				= 0;		
		
		try{
			comando = " SELECT COUNT(*) AS NUMMAT" +
					" FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = '"+carrera+"' " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6) ";
			
			rs = st.executeQuery(comando);	
			if (rs.next()){		
				num 	= rs.getInt("NUMMAT");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getTotalMat|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static int getTotalReprobadas(Connection conn, String cargaId, String carrera) throws SQLException, Exception{
		
		Statement st 		= conn.createStatement();
		ResultSet rs		= null;
		String comando 		= "";		
		int num				= 0;		
		
		try{
			comando = " SELECT COUNT(*) AS NUMMAT " +
					" FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = '"+carrera+"' " +
					" AND TIPOCAL_ID IN ('2','4')" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6) ";
			
			rs = st.executeQuery(comando);	
			if (rs.next()){		
				num 	= rs.getInt("NUMMAT");		
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getTotalReprobadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static String getNumCreditos(Connection conn, String codigoPersonal, String planId, String cargasNoVigentes) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String creditos			= "";
		
		try{
			ps 	= conn.prepareStatement("SELECT COALESCE( SUM(CREDITOS) , 0 ) AS CREDITOS FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "
					+ " AND PLAN_ID = '"+planId+"' "
					+ " AND TIPOCAL_ID IN('I','1') "+cargasNoVigentes );
			
			rs = ps.executeQuery();
			if (rs.next()){
				creditos = rs.getString("CREDITOS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static String getNotaCurso(Connection conn, String codigoPersonal, String cursoId, String tipocal, String orden) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nota				= "0";
		
		try{
			ps 	= conn.prepareStatement("SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND CURSO_ID = '"+cursoId+"'"
					+ " AND TIPOCAL_ID = '"+tipocal+"'"
					+ " AND ROWNUM = 1");
			
			rs = ps.executeQuery();
			if (rs.next()){
				nota = rs.getString("NOTA");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNotaCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nota;
	}
	
	public HashMap<String, String> mapCalificacionesInscritos(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		ResultSet rs 		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL,PLAN_ID,CICLO,"
									 + " SUM( (CASE COALESCE(NOTA_EXTRA,0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END) * CREDITOS )/SUM(CREDITOS) AS CALIFICACION"
									 + " FROM ENOC.ALUMNO_CURSO"
									 + " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')"
									 + " AND TIPOCAL_ID = '1'"									 
									 + " AND CREDITOS > 0"
									 + " AND CONVALIDACION IN ('N','I')"
									 + " GROUP BY CODIGO_PERSONAL,PLAN_ID,CICLO");
			ps.setString(1, cargaId);				
			rs= ps.executeQuery();
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PLAN_ID")+rs.getString("CICLO"), rs.getString("CALIFICACION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapCalificacionesInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public ArrayList<String> getListaAlumReprobon(Connection conn, String cargaId, String incidencias, String orden) throws SQLException{
		ArrayList<String> lisAlumno = new ArrayList<String>();
		PreparedStatement ps			= null;
		ResultSet rs 		= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CURSO_ID, COUNT(*) FROM ENOC.ALUMNO_CURSO"
					+ " WHERE TIPOCAL_ID IN ('2','4')"					
					+ " AND CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN (?))"
					+ " GROUP BY CODIGO_PERSONAL, CURSO_ID"
					+ " HAVING COUNT(*) > TO_NUMBER(?,'9') "+orden);
			ps.setString(1, cargaId);
			ps.setString(2, incidencias);
			
			rs = ps.executeQuery();		
			while(rs.next()){				
				lisAlumno.add(rs.getString("CODIGO_PERSONAL")+"&&"+rs.getString("CURSO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListaAlumReprobon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisAlumno;
	}
	
	public HashMap<String, String> mapAlumReprobon(Connection conn, String cargaId, String incidencias) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		ResultSet rs 		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CURSO_ID, COUNT(*) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE TIPOCAL_ID IN ('2','4')"					
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN (?))"
					+ " GROUP BY CODIGO_PERSONAL, CURSO_ID"
					+ " HAVING COUNT(*) > TO_NUMBER(?,'9') ");

			ps.setString(1, cargaId);
			ps.setString(2, incidencias);
			rs= ps.executeQuery();
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_ID"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumReprobon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public int getListesTipo(Connection conn, String codigoPersonal, String cursoCargaId, String tipoCalId) throws SQLException{
		int lises=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "select count(*) FROM ENOC.ALUMNO_CURSO "+
					  "where codigo_personal = '"+codigoPersonal+"' "+
					  "and curso_carga_id= '"+cursoCargaId+"' "+
		 			  "and tipocal_id= '"+tipoCalId+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()) lises = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListesTipo|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lises;
	}
	
	public static int[] getArrayComponente(Connection conn, String codigoPersonal, String planId, String tipoComp) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int comp[] 			= {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		String nombreCurso 	= "";
		
		try{
			comando = "SELECT CURSO_ID, NOMBRE_CURSO FROM ENOC.ALUMNO_CURSO"+
					  " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
					  " AND PLAN_ID= '"+planId+"'" +
					  " AND TIPOCAL_ID = '1'";
			if (tipoComp.equals("SC")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COSM','COSC')";
			}else if (tipoComp.equals("AF")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COAF','COSA')";
			}else if (tipoComp.equals("TM")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COTM','COTR')";
			}else if (tipoComp.equals("LC")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('CLOC','COLC')";
			}			

			rs = st.executeQuery(comando);
			while (rs.next()){
				nombreCurso = rs.getString("NOMBRE_CURSO")+"*";
				
				if (nombreCurso.trim().indexOf(" I*") != -1){ comp[0]=1; }
				if (nombreCurso.trim().indexOf(" II*") != -1){ comp[1]=1; }
				if (nombreCurso.trim().indexOf(" III*") != -1 ){ comp[2]=1; }
				if (nombreCurso.trim().indexOf(" IV*") != -1 ){ comp[3]=1; }
				if (nombreCurso.trim().indexOf(" V*") != -1 ){ comp[4]=1; }
				if (nombreCurso.trim().indexOf(" VI*") != -1 ){ comp[5]=1; }
				if (nombreCurso.trim().indexOf(" VII*") != -1 ){ comp[6]=1; }
				if (nombreCurso.trim().indexOf(" VIII*") != -1 ){ comp[7]=1; }
				if (nombreCurso.trim().indexOf(" IX*") != -1 ){ comp[8]=1; }
				if (nombreCurso.trim().indexOf(" X*") != -1 ){ comp[9]=1; }
				if (nombreCurso.trim().indexOf(" XI*") != -1 ){ comp[10]=1; }
				if (nombreCurso.trim().indexOf(" XII*") != -1 ){ comp[11]=1; }
				if (nombreCurso.trim().indexOf(" XIII*") != -1 ){ comp[12]=1; }
				if (nombreCurso.trim().indexOf(" XIV*") != -1 ){ comp[13]=1; }
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListesTipo|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return comp;
	}
	
	public ArrayList<AlumnoCurso> getListTipo(Connection conn, String codigoPersonal, String planId, String tipoCursoId) throws SQLException{
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando ="SELECT " +
				"CODIGO_PERSONAL, " +
				"CARGA_ID, " +
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, " +
				"CURSO_CARGA_ID, " +
				"CARRERA_ID, " +
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, " +
				"PLAN_ID, " +
				"CURSO_ID, " +
				"NOMBRE_CURSO, " +
				"TO_CHAR(CICLO,'99') AS CICLO, " +
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, " +
				"TO_CHAR(HT,'99') AS HT, " +
				"TO_CHAR(HP,'99') AS HP, " +
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, " +
				"CURSO_ID2, " +
				"NOMBRE_CURSO2, " +
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, " +
				"TO_CHAR(HT2,'99') AS HT2, " +
				"TO_CHAR(HP2,'99') AS HP2, " +
				"TO_CHAR(NOTA,'999') AS NOTA, " +
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
				"TIPOCAL_ID, " +
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"CONVALIDACION, " +
				"TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"GRUPO, " +
				"MAESTRO, " +
				"ESTADO, " +				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
				"AND PLAN_ID='"+planId+"' AND CURSO_ID IN (SELECT CURSO_ID " +
					"FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID = '"+tipoCursoId+"') " + 
				"AND TIPOCAL_ID NOT IN ('1','M','I')"; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getMatAproPorPlan(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		ArrayList<AlumnoCurso> lisMaterias		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando ="SELECT " +
				"CODIGO_PERSONAL, " +
				"CARGA_ID, " +
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, " +
				"CURSO_CARGA_ID, " +
				"CARRERA_ID, " +
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, " +
				"PLAN_ID, " +
				"CURSO_ID, " +
				"NOMBRE_CURSO, " +
				"TO_CHAR(CICLO,'99') AS CICLO, " +
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, " +
				"TO_CHAR(HT,'99') AS HT, " +
				"TO_CHAR(HP,'99') AS HP, " +
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, " +
				"CURSO_ID2, " +
				"NOMBRE_CURSO2, " +
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, " +
				"TO_CHAR(HT2,'99') AS HT2, " +
				"TO_CHAR(HP2,'99') AS HP2, " +
				"TO_CHAR(NOTA,'999') AS NOTA, " +
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
				"TIPOCAL_ID, " +
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"CONVALIDACION, " +
				"TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"GRUPO, " +
				"MAESTRO, " +
				"ESTADO, " +				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
				"AND PLAN_ID='"+planId+"' "+
				"AND TIPOCAL_ID = '1' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisMaterias.add(ac);
			}		
		}catch(Exception ex){	
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getMatAproPorPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lisMaterias;
	}
	
	public ArrayList<String> getPlanAalumnoPorTipo(Connection conn, String codigoPersonal, String tipoCal) throws SQLException{
		ArrayList<String> lisPlan		= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT DISTINCT(PLAN_ID) AS PLAN "
				    + " FROM ENOC.ALUMNO_CURSO "
				    + " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "
				    + " AND TIPOCAL_ID = '"+tipoCal+"'"; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisPlan.add(rs.getString("PLAN"));
			}
		}catch(Exception ex){
			
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getPlanAalumnoPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public AlumnoCurso getCursoPorIdyAlumno(Connection conn, String codigoPersonal, String cursoId, String tipos) throws SQLException{
		AlumnoCurso curso		= new AlumnoCurso();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CURSO_ID = '"+cursoId+"' AND TIPOCAL_ID IN ("+tipos+")";
			rs = st.executeQuery(comando);
			while (rs.next()){
				curso.mapeaReg(rs);
			}
		}catch(Exception ex){
			
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getCursoPorIdyAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return curso;
	}
	
	public int getNumAlumnos(Connection conn, String cursoCargaId ) throws SQLException{
		
		int num=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUM FROM ENOC.ALUMNO_CURSO" +
			  " WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
			  " AND TIPOCAL_ID != 'M'";
			rs = st.executeQuery(comando);
			if (rs.next()) num = rs.getInt(1);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return num;
	}
	
	public String getPromedioCiclo(Connection conn, String codigoPersonal, String ciclo ) throws SQLException{
		
		String promedio		= "";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT TO_CHAR( ROUND(SUM(NOTA*CREDITOS) / CASE SUM(CREDITOS) WHEN 0 THEN 1 ELSE SUM(CREDITOS) END,2), '999.99' ) AS PONDERADO "+
					  "FROM ENOC.ALUMNO_CURSO " +
					  "WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					  "AND CICLO = TO_NUMBER('"+ciclo+"') "+
					  "AND TIPOCAL_ID = '1'";
			rs = st.executeQuery(comando);
			if (rs.next()) promedio = rs.getString("ponderado");			
		}catch(Exception ex){
			System.out.println("Error getNumAlumnos: "+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return promedio;
	}
	
	// Este metodo no esta recomendado para utilizarlo en algxxxn reporte de consulta a
	// menos que se considere y este conciente del tiempo requerido para este proceso.
	public ArrayList<AlumnoCurso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO " +
				"WHERE CARGA_ID = '"+cargaId+"' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListCargayCarrera(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO " +
				"WHERE CARGA_ID = '"+cargaId+"' "+
				"AND CARRERA_ID = '"+carreraId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}	
	
	public ArrayList<AlumnoCurso> getListCargaconExtra(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL," +
					" CARGA_ID," +
					" TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID," +
					" CURSO_CARGA_ID, " +
					" CARRERA_ID," +
					" TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, " +
					" PLAN_ID, " +
					" CURSO_ID, " +
					" NOMBRE_CURSO," +
					" TO_CHAR(CICLO,'99') AS CICLO, " +
					" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, " +
					" TO_CHAR(HT,'99') AS HT, " +
					" TO_CHAR(HP,'99') AS HP," +
					" TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, " +
					" CURSO_ID2, " +
					" NOMBRE_CURSO2, " +
					" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, " +
					" TO_CHAR(HT2,'99') AS HT2," +
					" TO_CHAR(HP2,'99') AS HP2, " +
					" TO_CHAR(NOTA,'999') AS NOTA," +
					" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
					" TIPOCAL_ID, " +
					" TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, " +
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					" CONVALIDACION," +
					" TITULO," +
					" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" GRUPO," +
					" MAESTRO," +
					" ESTADO, " +					
					" CORRECCION, " +
					" OPTATIVA, " +
					" NOTA_CONVA " +
					" FROM ENOC.ALUMNO_CURSO " +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" AND NOTA_EXTRA > 0 " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<String> getListCargaconExtraP(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<String> lisCurso		= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL," +
					" (SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ALUMNO_CURSO.CARRERA_ID) AS CARRERA," +
					" NOMBRE_CURSO," +
					" TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, " +
					" TO_CHAR(NOTA,'999') AS NOTA," +
					" FROM ENOC.ALUMNO_CURSO " +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" AND NOTA_EXTRA > 0 " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisCurso.add(rs.getString("CODIGO_PERSONAL"));
				lisCurso.add(rs.getString("CARRERA"));
				lisCurso.add(rs.getString("NOMBRE_CURSO"));
				lisCurso.add(rs.getString("NOTA_EXTRA"));
				lisCurso.add(rs.getString("NOTA"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListAlumno(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(COALESCE(NOTA_EXTRA,0),'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListCurso(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso			= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListAlumnoCarga( Connection conn, String codigoPersonal, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND CARGA_ID = '"+cargaId+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListAlumnoCarga( Connection conn, String codigoPersonal, String cargaId, String bloqueId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND CARGA_ID = '"+cargaId+"' "+
				"AND BLOQUE_ID = '"+bloqueId+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListAlumnoCargaReprobados( Connection conn, String codigoPersonal, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, CURSO_CARGA_ID, CARRERA_ID, TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, PLAN_ID, "
					+ " CURSO_ID,NOMBRE_CURSO, TO_CHAR(CICLO,'99') AS CICLO, TO_CHAR(CREDITOS,'99.99') AS CREDITOS, TO_CHAR(HT,'99') AS HT, TO_CHAR(HP,'99') AS HP, "
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, CURSO_ID2, NOMBRE_CURSO2, TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, TO_CHAR(HT2,'99') AS HT2, "
					+ " TO_CHAR(HP2,'99') AS HP2, NOTA, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, TIPOCAL_ID, COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, CONVALIDACION, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, GRUPO, MAESTRO, ESTADO, "
					+ " CORRECCION, OPTATIVA,NOTA_CONVA FROM ENOC.ALUMNO_CURSO  "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID IN ("+cargaId+") AND NOTA < NOTA_APROBATORIA AND TIPOCAL_ID IN('2','4') ";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumnoCargaReprobados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getListAlumnoCargaBloque( Connection conn, String codigoPersonal, String cargaId, String bloqueId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = '"+bloqueId+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getComponentesAlumno( Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND PLAN_ID = '"+planId+"' "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = 3 AND TIPOCAL_ID = '1' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getComponentesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<MateriaVO> getComponentesFaltantesAlumno(Connection conn, String codigoPersonal,String planId) throws SQLException{
		
		ArrayList<MateriaVO> componentes		= new ArrayList<MateriaVO>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, COALESCE(HT,0) AS HT,"+ 
			    	" COALESCE(HP,0) AS HP, NOTA_APROBATORIA, TIPOCURSO_ID, ESTADO "+ 
			    	" FROM ENOC.MAPA_CURSO"+ 
			    	" WHERE PLAN_ID = '"+planId+"'"+ 
			    	" AND TIPOCURSO_ID=3"+
			    	" AND CURSO_ID NOT IN(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
			    	" AND TIPOCAL_ID='1' AND ENOC.TIPOCURSO_ID(CURSO_ID)=3)"+
			    	" ORDER BY NOMBRE_CURSO ASC";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MateriaVO materia = new MateriaVO();
				materia.setPlan_id(rs.getString(1));
				materia.setCurso_id(rs.getString(2));
				materia.setNombre_curso(rs.getString(3));
				materia.setCiclo(rs.getInt(4));
				materia.setCreditos(rs.getFloat(5));
				materia.setHt(rs.getInt(6));
				materia.setHp(rs.getInt(7));
				materia.setNotaAprobatoria(rs.getInt(8));
				materia.setTipoCursoId(rs.getInt(9));
				materia.setEstado(rs.getString(10));
				componentes.add(materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getComponentesFaltantesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return componentes;
	}
	
	public TreeMap<String,String> getMapAlumPromCarga(Connection conn, String carga ) throws SQLException{
		
		TreeMap<String,String> treeProm	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT"+
			" DISTINCT(A.CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+
			" ENOC.ALUM_PROMEDIO_CARGA(A.CODIGO_PERSONAL,A.CARGA_ID, A.PLAN_ID) AS PROMEDIO"+
			" FROM ENOC.ALUM_ESTADO A"+ 
			" WHERE CARGA_ID = '"+carga+"'"+
			" AND ESTADO = 'I'";			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				treeProm.put( rs.getString("CODIGO_PERSONAL"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapAlumPromCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeProm;
	}
	
	
	public ArrayList<AlumnoCurso> getListAlumnosCarga( Connection conn, String codigoPersonal, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND CARGA_ID = '"+cargaId+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListAlumnoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> lisAlumnosCargayCarrera( Connection conn, String cargaId, String carreraId, String tipoCal, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				" TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" TO_CHAR(CICLO,'99') AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" TO_CHAR(HT,'99') AS HT, "+
				" TO_CHAR(HP,'99') AS HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' "+
				" AND CARRERA_ID = '"+carreraId+"' "+
				" AND TIPOCAL_ID IN ("+tipoCal+") "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getComponentesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<AlumnoCurso> getReprobadosCargas( Connection conn, String cursoCargaId, String carreraId, String orden ) throws SQLException{
		
		ArrayList<AlumnoCurso> lisCurso		= new ArrayList<AlumnoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				" TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" TO_CHAR(CICLO,'99') AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" TO_CHAR(HT,'99') AS HT, "+
				" TO_CHAR(HP,'99') AS HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CURSO_CARGA_ID LIKE '"+cursoCargaId+"%' "+
				" AND CARRERA_ID = '"+carreraId+"' "+
				" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN (3,4,5) AND TIPOCAL_ID IN ('2','4') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();
				ac.mapeaReg(rs);
				lisCurso.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getComponentesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public static int numReprobadosxCarga(Connection conn,String cursoCargaId, String carreraId) throws SQLException{
		int lises=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = " SELECT COUNT (DISTINCT(CODIGO_PERSONAL)) FROM ENOC.ALUMNO_CURSO " +
					" WHERE CURSO_CARGA_ID LIKE '"+cursoCargaId+"%' " +
					" AND CARRERA_ID = '"+carreraId+"' " +
					" AND TIPOCAL_ID IN ('2','4') " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID)NOT IN(3,4,5)";
			rs = st.executeQuery(comando);
			if (rs.next()) lises = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|numReprobadosxCarga|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lises;
	}
	
	public static int getNumMaterias(Connection conn,String codigoPersonal, String planId, String tipos) throws SQLException{		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int total 			= 0;
		
		try{
			comando = " SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND PLAN_ID = '"+planId+"'"
					+ " AND TIPOCAL_ID IN ("+tipos+")";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getNumMaterias|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return total;
	}
	
	public TreeMap<String,String> getMapNotas(Connection conn, String cargaId, String carreraId ) throws SQLException{
			
		TreeMap<String,String> treeProm	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, CURSO_ID, NOTA FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID='"+cargaId+"' AND CARRERA_ID='"+carreraId+"' ";			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				treeProm.put( rs.getString("CODIGO_PERSONAL")+"|"+rs.getString("CURSO_ID"), rs.getString("NOTA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getMapNota|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeProm;
	}
	
	public ArrayList<ArrayList<String>> getListaRemediales(Connection conn, String orden) throws SQLException{
		ArrayList<ArrayList<String>> lisCurso = new ArrayList<ArrayList<String>>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "	SELECT CODIGO_PERSONAL," +
					"	(SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = A.CURSO_ID) AS CURSO_CLAVE," +
					"	(SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = A.TIPOCAL_ID) AS NOMBRE_TIPOCAL," +
					"	(SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = A.CURSO_ID) AS PLAN_ID" +
					"	FROM ENOC.KRDX_CURSO_ACT A WHERE A.CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
					"	UNION" +
					"	SELECT CODIGO_PERSONAL," +
					"	(SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = B.CURSO_ID) AS CURSO_CLAVE," +
					"	(SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = B.TIPOCAL_ID) AS NOMBRE_TIPOCAL," +
					"	(SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = B.CURSO_ID) AS PLAN_ID" +
					"	FROM ENOC.KRDX_CURSO_IMP B" +
					"	WHERE B.CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("CODIGO_PERSONAL"));
				arr.add(rs.getString("CURSO_CLAVE"));
				arr.add(rs.getString("NOMBRE_TIPOCAL"));
				arr.add(rs.getString("PLAN_ID"));
				lisCurso.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListaRemediales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	
	public ArrayList<String> getListaAlumPlan(Connection conn, String planId, String orden) throws SQLException{
		ArrayList<String> lisAlumno = new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "	SELECT" +
					" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, " +
					" ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS APELLIDO_NOMBRE" +
					" FROM ENOC.KRDX_CURSO_ACT KCA" +
					" WHERE (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID=KCA.CURSO_ID) = '"+planId+"'" +
					" UNION" +
					" SELECT" +
					" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL," +
					" ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS APELLIDO_NOMBRE" +
					" FROM ENOC.KRDX_CURSO_IMP KCI" +
					" WHERE (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID=KCI.CURSO_ID) = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				lisAlumno.add(rs.getString("CODIGO_PERSONAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListaAlumPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisAlumno;
	}
	
	public ArrayList<String> getListaAlumPlan(Connection conn, String cargaId, String planId, String orden) throws SQLException{
		ArrayList<String> lisAlumno = new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "	SELECT" +
					" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL," +
					" ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS APELLIDO_NOMBRE" +
					" FROM ENOC.KRDX_CURSO_ACT KCA" +
					" WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = KCA.CURSO_CARGA_ID) = '"+cargaId+"' " +
					" AND (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCA.CURSO_ID) = '"+planId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				lisAlumno.add(rs.getString("CODIGO_PERSONAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getListaAlumPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisAlumno;
	}
	
	public HashMap<String,String> getMapCreditos(Connection conn, String planId, String codigoPersonal ) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT CICLO, SUM(CREDITOS) AS CREDITOS"
					+ " FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND PLAN_ID = '"+planId+"'"
					+ " AND TIPOCAL_ID = '1'"
					+ " GROUP BY PLAN_ID, CICLO ";
			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("CICLO"), rs.getString("CREDITOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getMapCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapCredPorCarga(Connection conn, String codigoPersonal, String tipoCalId) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT CARGA_ID, BLOQUE_ID, SUM(CREDITOS) AS TOTAL FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND TIPOCAL_ID IN ("+tipoCalId+")"
					+ " GROUP BY CARGA_ID, BLOQUE_ID";	
			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("CARGA_ID")+rs.getString("BLOQUE_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|getMapCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// 
	public HashMap<String,String> mapAlumPromCarga(Connection conn, String carga ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT"+
			" DISTINCT(A.CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+
			" ENOC.ALUM_PROMEDIO_CARGA(A.CODIGO_PERSONAL,A.CARGA_ID, A.PLAN_ID) AS PROMEDIO"+
			" FROM ENOC.ALUM_ESTADO A"+ 
			" WHERE CARGA_ID = '"+carga+"'"+
			" AND ESTADO = 'I'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("CODIGO_PERSONAL"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumPromCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public static HashMap<String,String> mapAlumPorMaestro(Connection conn, String cargaId, String estado) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT MAESTRO, COUNT(CODIGO_PERSONAL) AS TOTAL "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ("+estado+")"
					+ " GROUP BY MAESTRO";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("MAESTRO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapAlumPorMaestro(Connection conn, String cargas, String modalidades, String estados) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT MAESTRO, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND MODALIDAD_ID IN("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("MAESTRO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapAlumPorMaestroyMateria(Connection conn, String cargas, String modalidades, String estados) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT MAESTRO, CURSO_CARGA_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND MODALIDAD_ID IN("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO, CURSO_CARGA_ID";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("MAESTRO")+rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> mapAlumPorMateria(Connection conn, String cargaId, String estado) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT CURSO_CARGA_ID, COUNT(CODIGO_PERSONAL) AS TOTAL "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ("+estado+")"
					+ " GROUP BY CURSO_CARGA_ID";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapAlumPorMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapCreditosPorMaestro(Connection conn, String cargas, String modalidades, String estados) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = " SELECT MAESTRO, ENOC.CARRERA_NIVEL(CARRERA_ID) AS NIVEL, SUM(CREDITOS) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO, ENOC.CARRERA_NIVEL(CARRERA_ID)";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("MAESTRO")+rs.getString("NIVEL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoCursoUtil|mapCreditosPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

	
	
/*	 
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Statement st 		= Conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";			
			
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"COALESCE(TO_CHAR(CREDITOS,'99'),'0') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(COALESCE(NOTA_EXTRA,0),'999') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+				
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO "+				
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '0990920' order by ciclo";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumnoCurso ac = new AlumnoCurso();				
				ac.mapeaReg(rs);
				System.out.println(ac.getCodigoPersonal()+" "+ac.getCreditos()+" "+ac.getCiclo()+": "+ac.getNota()+": "+ac.getNotaExtra()+": "+ac.getTipoCalId());
			}
			
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			
			Conn.commit();
			Conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
*/
}