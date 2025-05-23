// CLASE DE LA TABLA COND_ALUMNO

package aca.disciplina;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CondAlumnoUtil {
	
	public boolean insertReg(Connection conn, CondAlumno conducta ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO " +
				"ENOC.COND_ALUMNO(MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
				"EMPLEADO, FECHA, CANTIDAD, COMENTARIO ) " +
				"VALUES(?,?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), " +
				"TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'),TO_NUMBER(?,'999'),? ) ");
			
			ps.setString(1, conducta.getMatricula());
			ps.setString(2, conducta.getPeriodoId());			
			ps.setString(3, conducta.getFolio());
			ps.setString(4, conducta.getIdReporte());
			ps.setString(5, conducta.getIdLugar());			
			ps.setString(6, conducta.getIdJuez());
			ps.setString(7, conducta.getEmpleado());
			ps.setString(8, conducta.getFecha());			
			ps.setString(9, conducta.getCantidad());
			ps.setString(10,conducta.getComentario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CondAlumno conducta ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COND_ALUMNO " + 
				"SET  IDREPORTE= TO_NUMBER(?,999), " +
				"IDLUGAR= TO_NUMBER(?,999), IDJUEZ= TO_NUMBER(?,999), EMPLEADO= ?, " +
				"FECHA= TO_DATE(?,'DD/MM/YYYY'), CANTIDAD= TO_NUMBER(?,999), COMENTARIO= ? " +
				"WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ?");			
			
			ps.setString(4, conducta.getIdReporte());
			ps.setString(5, conducta.getIdLugar());			
			ps.setString(6, conducta.getIdJuez());
			ps.setString(7, conducta.getEmpleado());
			ps.setString(8, conducta.getFecha());			
			ps.setString(9, conducta.getCantidad());
			ps.setString(10,conducta.getComentario());
			ps.setString(1, conducta.getMatricula());
			ps.setString(2, conducta.getPeriodoId());			
			ps.setString(3, conducta.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String matricula, String periodoId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COND_ALUMNO "+ 
				"WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ? ");
			ps.setString(1, matricula);
			ps.setString(2, periodoId);
			ps.setString(3, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CondAlumno mapeaRegId(Connection con, String matricula, String folio, String periodoId) throws SQLException{
		
		CondAlumno conducta = new CondAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT MATRICULA, PERIODO_ID, FOLIO, " +
					"IDREPORTE, IDLUGAR, IDJUEZ, EMPLEADO, TO_CHAR(FECHA, 'DD/MM/YYYY') FECHA, " +
					"CANTIDAD, COMENTARIO FROM ENOC.COND_ALUMNO " + 
					"WHERE MATRICULA = ? AND FOLIO = ? AND PERIODO_ID = ?");				
			ps.setString(1, matricula);
			ps.setString(2, folio);
			ps.setString(3, periodoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				conducta.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return conducta;
	}
	
	public boolean existeReg(Connection conn, String matricula, String periodoId, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ? "); 
			ps.setString(1, matricula);
			ps.setString(2, periodoId);
			ps.setString(3, folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT (MAX(FOLIO)+1) AS MAXIMO FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? " + 
					"AND PERIODO_ID = ? ");
			ps.setString(1, matricula);
			ps.setString(2, periodoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static String getCantDisciplina(Connection conn, String matricula, String tipo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String cantidad		= "x";
		
		try{
			comando = "SELECT COALESCE(SUM(CANTIDAD),0) AS CANTIDAD FROM ENOC.COND_ALUMNO " + 
					"WHERE MATRICULA = '"+matricula+"' AND IDREPORTE  IN(SELECT IDREPORTE " +
							"FROM ENOC.COND_REPORTE WHERE TIPO = '"+tipo+"') "; 
			
			rs = st.executeQuery(comando);			
			if (rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|getCantDisciplina|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public static String getDisciplinaPer(Connection conn, String periodo, String matricula, String tipo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String cantidad		= "x";
		
		try{
			comando = "SELECT COALESCE(SUM(A.CANTIDAD),0) AS CANTIDAD " + 
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R WHERE PERIODO_ID = '"+periodo+"' " + 
					"AND MATRICULA='"+matricula+"' AND R.IDREPORTE = A.IDREPORTE " +  
					"AND R.TIPO = '"+tipo+"' ";
				
			rs = st.executeQuery(comando);			
			if (rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|getDisciplinaPer|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public static String getUltimaFecha(Connection conn, String periodo, String matricula) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		Locale loc = new Locale("es","MX");
		java.text.SimpleDateFormat fecha = new java.text.SimpleDateFormat("dd/MM/yyyy",loc);
		String strFecha			= "";  
		
		try{
			comando = "SELECT MAX(TO_DATE(TO_CHAR(FECHA,'DD/MM/YY'),'DD/MM/YY')) AS FECHA "
					+ " FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = '"+periodo+"' " 
					+ " AND MATRICULA='"+matricula+"'";				 
			rs = st.executeQuery(comando);			
			if (rs.next()){				
				strFecha = fecha.format(rs.getDate("FECHA"));	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|getUltimaFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return strFecha;
	}	
	
	public static String getCantReporte(Connection conn, String matricula, String periodo, String reporte) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String cantidad		= "x";
		
		try{
			comando = " SELECT COALESCE(SUM(CA.CANTIDAD),0) AS CANTIDAD "
					+ " FROM ENOC.COND_ALUMNO CA, ENOC.COND_REPORTE CR WHERE CA.MATRICULA = '"+matricula+"'"
					+ " AND CA.PERIODO_ID = '"+periodo+"' AND CR.IDREPORTE = '"+reporte+"'"
					+ " AND CA.IDREPORTE = CR.IDREPORTE "; 

				
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|getCantReporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}

	public ArrayList<CondAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ," +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO " +
					"FROM ENOC.COND_ALUMNO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getLista(Connection conn, String periodo, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO " +
					"FROM ENOC.COND_ALUMNO " + 
					"WHERE PERIODO_ID = '"+periodo+"' "+
					"AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	
	public ArrayList<String> getListAlumnos(Connection conn, String periodo, String orden ) throws SQLException{
		
		ArrayList<String> lisAlumno 		= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(MATRICULA) AS MATRICULA, ENOC.ALUM_CARRERA_ID(MATRICULA), ENOC.ALUM_NOMBRE(MATRICULA) FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = '"+periodo+"' "+orden; 

			rs = st.executeQuery(comando);
			while (rs.next()){
				lisAlumno.add(rs.getString("MATRICULA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<String> getListAlumnosF(Connection conn, String fechaInicio, String fechaFinal, String orden ) throws SQLException{
		
		ArrayList<String> lisAlumno		= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(MATRICULA) AS MATRICULA FROM ENOC.COND_ALUMNO "+ 
					"WHERE FECHA BETWEEN TO_DATE('"+fechaInicio+"','dd/mm/yyyy') "+
					"AND TO_DATE('"+fechaFinal+"','dd/mm/yyyy') "+orden;
			System.out.println(comando);
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisAlumno.add(rs.getString("MATRICULA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListAlumnosF|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getLista(Connection conn, String periodo, String matricula, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO " +
					"FROM ENOC.COND_ALUMNO " + 
					"WHERE MATRICULA= '"+matricula+"' " +					
					"AND PERIODO_ID = '"+periodo+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
		
	public ArrayList<CondAlumno> getList(Connection conn, String matricula, String periodo, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO " +
					"FROM ENOC.COND_ALUMNO " + 
					"WHERE PERIODO_ID = '"+periodo+"' " +
					"AND MATRICULA = '"+matricula+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getListReporte(Connection conn, String periodo, String reporte, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, A.IDJUEZ, A.EMPLEADO, " +
					"TO_CHAR(A.FECHA,'DD/MM/YYYY') AS FECHA, A.CANTIDAD, A.COMENTARIO " +
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " + 
					"WHERE A.PERIODO_ID = '"+periodo+"' " +
					"AND A.MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
					"AND A.IDREPORTE = '"+reporte+"' " +
					"AND R.IDREPORTE = A.IDREPORTE " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListReporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getListCarrera(Connection conn, String periodo, String carreraId, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{			
			String comando =  "SELECT "+ 
			    "A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, "+ 
			    "A.IDJUEZ, A.EMPLEADO, A.FECHA, A.CANTIDAD, A.COMENTARIO "+
			    "FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE B "+ 
			    "WHERE A.PERIODO_ID = '"+periodo+"' "+
			    "AND B.IDREPORTE = A.IDREPORTE "+
			    "AND A.MATRICULA IN "+
			 	"(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO "+ 
			 	"WHERE CARGA_ID IN "+
			 		"(SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) "+ 
			 	"AND ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) = '"+carreraId+"' ) "+orden;		
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getListTipo(Connection conn, String periodo, String matricula, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno 		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, " +
					"A.IDJUEZ, A.EMPLEADO, TO_CHAR(A.FECHA,'DD/MM/YYYY') AS FECHA, " +
					"A.CANTIDAD, A.COMENTARIO " +
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " + 
					"WHERE A.IDREPORTE = R.IDREPORTE " +
					"AND A.PERIODO_ID = '"+periodo+"' " +
					"AND A.MATRICULA= '"+matricula+"' " +
					"AND TIPO IN ('C','D') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<CondAlumno> getListFechas(Connection conn, String fechaInicio, String fechaFinal, String orden ) throws SQLException{
		
		ArrayList<CondAlumno> lisAlumno		= new ArrayList<CondAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ," +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO "+
					"FROM ENOC.COND_ALUMNO "+ 
					"WHERE FECHA BETWEEN TO_DATE('"+fechaInicio+"','dd/mm/yyyy') "+
					"AND TO_DATE('"+fechaFinal+"','dd/mm/yyyy') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondAlumno alumno = new CondAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getListFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public HashMap<String, Integer> getMapAlumUnidad(Connection conn, String cargaId,  String periodo, String orden) throws SQLException{
		
		HashMap<String, Integer> lisDisciplinaPer = new HashMap<String, Integer>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
						" (SELECT COALESCE(SUM(A.CANTIDAD * (CASE R.TIPO WHEN 'C' THEN 1 ELSE -1 END)),0)" +
							" FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " +
							" WHERE PERIODO_ID = '"+periodo+"'" +
							" AND MATRICULA= ESTADISTICA.CODIGO_PERSONAL" +
							" AND R.IDREPORTE = A.IDREPORTE)" +
						" AS CANTIDAD" +
						" FROM ENOC.ESTADISTICA" +
						" WHERE CARGA_ID IN ('"+cargaId+"')";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplinaPer.put(rs.getString("CODIGO_PERSONAL"), Integer.parseInt(rs.getString("CANTIDAD")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getMapDisciplinaPer|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDisciplinaPer;
	}
	
	public HashMap<String, Integer> getMapDisciplinaPer(Connection conn, String cargaId,  String periodo, String tipo, String orden) throws SQLException{
		
		HashMap<String, Integer> lisDisciplinaPer = new HashMap<String, Integer>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
						" (SELECT COALESCE(SUM(A.CANTIDAD),0)" +
							" FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " +
							" WHERE PERIODO_ID = '"+periodo+"'" +
							" AND MATRICULA= ESTADISTICA.CODIGO_PERSONAL" +
							" AND R.IDREPORTE = A.IDREPORTE" +
							" AND R.TIPO = '"+tipo+"')" +
						" AS CANTIDAD" +
						" FROM ENOC.ESTADISTICA" +
						" WHERE CARGA_ID IN ('"+cargaId+"')";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplinaPer.put(rs.getString("CODIGO_PERSONAL"), Integer.parseInt(rs.getString("CANTIDAD")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getMapDisciplinaPer|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDisciplinaPer;
	}
	
	public HashMap<String, Integer> getMapUnidadesXCarrera(Connection conn, String fechaIni,  String fechaFin, String cargaId) throws SQLException{
		
		HashMap<String, Integer> lisUnidadesCarrera = new HashMap<String, Integer>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT COUNT(DISTINCT(MATRICULA)) AS CANTIDAD, ENOC.ALUM_CARRERA_HISTORICA(MATRICULA,'"+cargaId+"',1) AS CARRERA"
					+ " FROM ENOC.COND_ALUMNO " 
					+ " WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')" 
					+ " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'D')" 
					+ " GROUP BY ENOC.ALUM_CARRERA_HISTORICA(MATRICULA,'"+cargaId+"',1)";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisUnidadesCarrera.put(rs.getString("CARRERA"), Integer.parseInt(rs.getString("CANTIDAD")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getMapUnidadesXCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUnidadesCarrera;
	}
	
public HashMap<String, String> getElogios(Connection conn ) throws SQLException{
		
		HashMap<String, String> lisDisciplinaPer = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MATRICULA, SUM(CANTIDAD) AS TOTAL FROM ENOC.COND_ALUMNO "+
					  " WHERE MATRICULA IN (SELECT MATRICULA FROM ENOC.INT_ALUMNO) "+
					  " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'C')"+
					  " GROUP BY MATRICULA";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplinaPer.put(rs.getString("MATRICULA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getMapDisciplinaPer|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDisciplinaPer;
	}

	public HashMap<String, String> getUnidades(Connection conn ) throws SQLException{
		
		HashMap<String, String> lisDisciplinaPer = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MATRICULA, SUM(CANTIDAD) AS TOTAL FROM ENOC.COND_ALUMNO "+
					  " WHERE MATRICULA IN (SELECT MATRICULA FROM ENOC.INT_ALUMNO) "+
					  " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'D')"+
					  " GROUP BY MATRICULA";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplinaPer.put(rs.getString("MATRICULA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getUnidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDisciplinaPer;
	}

	public ArrayList<String> getPeriodosAlumno(Connection conn, String matricula ) throws SQLException{
		
		ArrayList<String> lisPeriodo 	= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT DISTINCT(PERIODO_ID) FROM COND_ALUMNO WHERE MATRICULA = '"+matricula+"' GROUP BY PERIODO_ID, TIPO_REPORTE(IDREPORTE) ORDER BY 1";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisPeriodo.add(rs.getString("PERIODO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getPeriodosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPeriodo;
	}
	
	public HashMap<String, String> getEvalDisciplinaria(Connection conn, String matricula ) throws SQLException{
		HashMap<String, String> lisDisciplina = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, TIPO_REPORTE(IDREPORTE) AS TIPO_REPORTE,COUNT(CANTIDAD) AS CANTIDAD FROM COND_ALUMNO "
					+ "	WHERE MATRICULA = '"+matricula+"' GROUP BY PERIODO_ID, TIPO_REPORTE(IDREPORTE)";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplina.put(rs.getString("PERIODO_ID")+rs.getString("TIPO_REPORTE"), rs.getString("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumnoUtil|getEvalDisciplinaria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisDisciplina;
	}

}