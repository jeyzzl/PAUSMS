// Clase para la tabla de Alum_Academico
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AcademicoUtil{
	
	public boolean insertReg(Connection conn, AlumAcademico academico ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (academico.getResidenciaId()==null || academico.getResidenciaId().equals("")|| academico.getResidenciaId().equals(" ")) academico.setResidenciaId("I"); 
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_ACADEMICO"+ 
				"(CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				"RESIDENCIA_ID, DORMITORIO, F_SOLICITUD, F_ADMISION, F_ALTA, "+
				"MODALIDAD_ID, EXTENSION_ID, PREPA_LUGAR) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99'), "+				
				"?, "+
				"TO_NUMBER(?,'999'), "+
				"?, "+
				"?, "+				
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+				
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99'),"+
				"TO_NUMBER(?,'99'))");					
			ps.setString(1, academico.getCodigoPersonal());
			ps.setString(2, academico.getTipoAlumno());
			
			AlumPersonal alumno 	= new AlumPersonal();
			AlumUtil alumnoU 		= new AlumUtil();			
			alumno = alumnoU.mapeaRegId(conn, academico.getCodigoPersonal());
			if(alumno.getReligionId().equals("1")) ps.setString(3,"1");
			else  ps.setString(3, "2");
			
			ps.setString(4, academico.getObrero());
			ps.setString(5, academico.getObreroInstitucion());
			ps.setString(6, academico.getResidenciaId());
			ps.setString(7, academico.getDormitorio());
			ps.setString(8, academico.getFSolicitud());
			ps.setString(9, academico.getFAdmision());
			ps.setString(10, academico.getFAlta());
			ps.setString(11, academico.getModalidadId());
			ps.setString(12, academico.getExtensionId());
			ps.setString(13, academico.getPrepaLugar());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumAcademico academico ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ACADEMICO "+ 
				"SET "+
				"TIPO_ALUMNO = TO_NUMBER(?,'99'), "+
				"CLAS_FIN = TO_NUMBER(?,'99'), "+
				"OBRERO = ?, "+
				"OBRERO_INSTITUCION = TO_NUMBER(?,'999'), "+
				"RESIDENCIA_ID = ?, "+
				"DORMITORIO = ?, "+
				"F_SOLICITUD = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_ADMISION = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_ALTA = TO_DATE(?,'DD/MM/YYYY'), "+
				"MODALIDAD_ID = TO_NUMBER(?,'99'), "+
				"EXTENSION_ID = TO_NUMBER(?,'99'), "+
				"PREPA_LUGAR = TO_NUMBER(?,'99') "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, academico.getTipoAlumno());

			AlumPersonal alumno = new AlumPersonal();
			AlumUtil alumnoU = new AlumUtil();
			alumno = alumnoU.mapeaRegId(conn,academico.getCodigoPersonal());			
			if(alumno.getReligionId().equals("1"))
				ps.setString(2, "1");
			else
				ps.setString(2, "2");
			
			ps.setString(3, academico.getObrero());
			ps.setString(4, academico.getObreroInstitucion());
			ps.setString(5, academico.getResidenciaId());
			ps.setString(6, academico.getDormitorio());
			ps.setString(7, academico.getFSolicitud());
			ps.setString(8, academico.getFAdmision());
			ps.setString(9, academico.getFAlta());
			ps.setString(10, academico.getModalidadId());
			ps.setString(11, academico.getExtensionId());
			ps.setString(12, academico.getPrepaLugar());
			ps.setString(13, academico.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateResidencia(Connection conn, String codigoPersonal, String residenciaId, String dormitorio, String requerido ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ACADEMICO "+ 
				"SET RESIDENCIA_ID = ?, DORMITORIO = ?, REQUERIDO = ? WHERE CODIGO_PERSONAL = ?");			
			ps.setString(1, residenciaId);
			ps.setString(2, dormitorio);
			ps.setString(3, requerido);
			ps.setString(4, codigoPersonal);			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean updateClasFin(Connection conn, String academico, String cf) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ALUM_academico "+
				"SET "+				
				"clas_fin = ? "+
				"WHERE CODIGO_PERSONAL = ? ");
				
			ps.setString(1,cf);
			ps.setString(2,academico);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|updateClasFin|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	
	public boolean updateResidencia(Connection conn,  String codigoPersonal, String Res) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ACADEMICO "+ 
				"SET "+				
				"RESIDENCIA_ID = ? "+
				"WHERE CODIGO_PERSONAL = ? ");
				
			ps.setString(1,Res);
			ps.setString(2,codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|updateResidencia|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumAcademico mapeaRegId( Connection conn, String Codigo_personal ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		AlumAcademico academico = new AlumAcademico();
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, TIPO_ALUMNO, COALESCE(CLAS_FIN,1) CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				"COALESCE(RESIDENCIA_ID,'I') RESIDENCIA_ID, DORMITORIO, "+
				"COALESCE(TO_CHAR(F_SOLICITUD,'DD/MM/YYYY'),'01/01/1900') AS F_SOLICITUD, "+
				"COALESCE(TO_CHAR(F_ADMISION,'DD/MM/YYYY'),'01/01/1900') AS F_ADMISION, "+
				"COALESCE(TO_CHAR(F_ALTA,'DD/MM/YYYY'),'01/01/1900') F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, "+
				"EXTENSION_ID, PREPA_LUGAR, REQUERIDO "+
				"FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				academico.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|mapeaRegId|:"+ex);
		}finally{
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
		return academico;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getTipoAlumno(Connection conn) throws SQLException{		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= "";
		String codigoPersonal   = "";
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPO FROM ENOC.ALUM_ACADEMICO A, ENOC.CAT_TIPOALUMNO B "+ 
									"WHERE CODIGO_PERSONAL = ? AND A.TIPO_ALUMNO = B.TIPO_ID");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString(1);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getTipoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getClasFinAlumno(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	clasFin			= "2"; 
		try{
			ps = conn.prepareStatement("SELECT RELIGION_ID FROM ENOC.ALUM_PERSONAL "+ 
				"WHERE CODIGO_PERSONAL = ? AND RELIGION_ID = 1");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				clasFin = "1";			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getClasFinAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return clasFin;
	}
	
	public static String getResidencia(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	res				= ""; 
		try{
			ps = conn.prepareStatement("SELECT RESIDENCIA_ID FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				res= rs.getString("RESIDENCIA_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getResidencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public static String getRequerido(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	res				= ""; 
		try{
			ps = conn.prepareStatement("SELECT REQUERIDO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);			
			rs = ps.executeQuery();
			if (rs.next())
				res= rs.getString("REQUERIDO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getRequerido|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public static String getDormi(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	res				= ""; 
		try{
			ps = conn.prepareStatement("SELECT COALESCE(DORMITORIO,'X') AS DORMI FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				res= rs.getString("DORMI");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getDormi|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public static String getModalidad(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	modalidad		= "x"; 
		try{
			ps = conn.prepareStatement("SELECT ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				modalidad	= rs.getString("MODALIDAD");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return modalidad;
	}
	
	public static String getModalidadId(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	modalidad		= "0"; 
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				modalidad	= rs.getString("MODALIDAD_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getModalidadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return modalidad;
	}
	
	public static String getTipoAlumno(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPO FROM ENOC.ALUM_ACADEMICO A, ENOC.CAT_TIPOALUMNO B "+ 
									"WHERE CODIGO_PERSONAL = ? AND A.TIPO_ALUMNO = B.TIPO_ID");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString(1);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getTipoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getTipoAlumnoId(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString(1);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getTipoAlumnoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getCantidadCalculoCobro(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	total			= "Error. Tendras que inscribirte personalmente o intentar luego"; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CASE NATURALEZA WHEN 'D' THEN IMPORTE*-1 WHEN 'C' THEN IMPORTE END) AS TOTAL" +
					" FROM MATEO.FES_CC_MOVIMIENTO" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND TIPOMOV = 23");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getString("TOTAL");
			
			if(Float.parseFloat(total) <= 0)//Si sale deuda se pasa a positivo ya que es la cantidad que se va a pagar
				total = String.valueOf(Float.parseFloat(total)*-1);
			else
				total = "Nada";
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getCantidadCalculoCobro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public String getSemestre(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	semestre		= "0"; 
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SEMESTRE,0) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())	semestre = rs.getString(1);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getSemestre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return semestre;
	}
		
	public ArrayList<AlumAcademico> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumAcademico> lisAcademico= new ArrayList<AlumAcademico>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				"OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, DORMITORIO, "+
				"F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, " +
				"EXTENSION_ID, PREPA_LUGAR, REQUERIDO "+
				"FROM ENOC.ALUM_ACADEMICO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumAcademico academico = new AlumAcademico();
				academico.mapeaReg(rs);
				lisAcademico.add(academico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcademico;
	}
	
	public ResultSet getInscritos(Connection conn, String script ) throws SQLException{
		
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		
		try{			
			rs = st.executeQuery(script);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getInscritos|:"+ex);
		}finally{
			//Aqui no se debe cerrar ni el statement ni el resultset
		}
		return rs;
	}
	
	public HashMap<String,AlumAcademico> getMapAcademico(Connection conn) throws SQLException{
		
		HashMap<String,AlumAcademico> map		= new HashMap<String,AlumAcademico>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				"OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, DORMITORIO, "+
				"F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, " +
				"EXTENSION_ID, PREPA_LUGAR, REQUERIDO "+
				"FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				AlumAcademico alum = new AlumAcademico();
				alum.mapeaReg(rs);
				
				map.put(alum.getCodigoPersonal(), alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapAcademico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,AlumAcademico> mapAcademicoCargasyModalidades(Connection conn, String cargas, String modalidades) throws SQLException{
		
		HashMap<String,AlumAcademico> map		= new HashMap<String,AlumAcademico>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION,"
					+ " OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD,"
					+ " EXTENSION_ID, PREPA_LUGAR, REQUERIDO"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+"))";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				AlumAcademico alum = new AlumAcademico();
				alum.mapeaReg(rs);
				
				map.put(alum.getCodigoPersonal(), alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapAcademico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	
	public HashMap<String, String> getMapDormi(Connection conn, String condicion) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, DORMITORIO FROM ENOC.ALUM_ACADEMICO "+condicion;			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("DORMITORIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapDormi|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> getMapModalidad(Connection conn, String condicion) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, MODALIDAD_ID FROM ENOC.ALUM_ACADEMICO "+condicion;
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("MODALIDAD_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> getMapTipoAlumno(Connection conn, String condicion) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO "+condicion;
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("TIPO_ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapTipoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public  HashMap<String, String> getTipoAlumnoSinMentor(Connection conn, String periodo) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO"+
					  " WHERE CODIGO_PERSONAL IN"+ 
					  " (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO"+
					  " WHERE ESTADO = 'I'" +
					  " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL ))"+
					  " AND CODIGO_PERSONAL NOT IN(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID ='"+periodo+"' AND STATUS = 'A')";

			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("TIPO_ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getTipoAlumnoSinMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,AlumAcademico> mapInternos(Connection conn, String dormitorioId) throws SQLException{
		
		HashMap<String,AlumAcademico> map		= new HashMap<String,AlumAcademico>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ "	CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION,"
					+ " OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD,"
					+ " EXTENSION_ID, PREPA_LUGAR, REQUERIDO"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = "+dormitorioId+")";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				AlumAcademico alum = new AlumAcademico();
				alum.mapeaReg(rs);				
				map.put(alum.getCodigoPersonal(), alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getMapAcademico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}