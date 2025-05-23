// Clase para la tabla de Alum_ESTADO
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class EstadoUtil{
	
	public boolean insertReg(Connection conn, AlumEstado alumEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_ESTADO"+ 
				"(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?,TO_NUMBER(?,'99'), TO_NUMBER(?,'99'),TO_NUMBER(?,'99'))");
					
			ps.setString(1, alumEstado.getCodigoPersonal());
			ps.setString(2, alumEstado.getCargaId());
			ps.setString(3, alumEstado.getBloqueId());
			ps.setString(4, alumEstado.getEstado());
			ps.setString(5, alumEstado.getModalidadId());
			ps.setString(6, alumEstado.getTipoalumnoId());
			ps.setString(7, alumEstado.getFacultadId());
			ps.setString(8, alumEstado.getCarreraId());
			ps.setString(9, alumEstado.getPlanId());
			ps.setString(10, alumEstado.getFecha());
			ps.setString(11, alumEstado.getResidenciaId());
			ps.setString(12, alumEstado.getDormitorio());
			ps.setString(13, alumEstado.getCiclo());
			ps.setString(14, alumEstado.getGrado());
			ps.setString(15, alumEstado.getClasFin());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumEstado alumEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ESTADO "+ 
				" SET ESTADO = ?, "+
				" MODALIDAD_ID =  TO_NUMBER(?,'99'),"+
				" TIPOALUMNO_ID = TO_NUMBER(?,'99'),"+
				" FACULTAD_ID = ?,"+
				" CARRERA_ID = ?,"+
				" PLAN_ID = ?,"+
				" FECHA = TO_DATE(?, 'DD/MM/YYYY'),"+
				" RESIDENCIA_ID = ?,"+
				" DORMITORIO = TO_NUMBER(?,'99'),"+
				" CICLO = TO_NUMBER(?,'99'),"+
				" GRADO = TO_NUMBER(?,'99'),"+
				" CLAS_FIN = TO_NUMBER(?,'99')"+
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CARGA_ID = ? "+
				" AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, alumEstado.getEstado());
			ps.setString(2, alumEstado.getModalidadId());
			ps.setString(3,  alumEstado.getTipoalumnoId());
			ps.setString(4, alumEstado.getFacultadId());
			ps.setString(5, alumEstado.getCarreraId());
			ps.setString(6, alumEstado.getPlanId());
			ps.setString(7, alumEstado.getFecha());
			ps.setString(8, alumEstado.getResidenciaId());
			ps.setString(9, alumEstado.getDormitorio());
			ps.setString(10, alumEstado.getCiclo());
			ps.setString(11, alumEstado.getGrado());
			ps.setString(12, alumEstado.getClasFin());
			ps.setString(13, alumEstado.getCodigoPersonal());
			ps.setString(14, alumEstado.getCargaId());
			ps.setString(15, alumEstado.getBloqueId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;

		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId, String bloqueId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, bloqueId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|deletetReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumEstado mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws SQLException{
		
		AlumEstado alumEstado = new AlumEstado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN "+
				"FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumEstado;
	}
	
	public AlumEstado mapeaRegId( Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
		AlumEstado alumEstado = new AlumEstado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, COALESCE(MODALIDAD_ID,0) AS MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN "+
				"FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID IN (?) ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
			rs = ps.executeQuery();
			if (rs.next()){				
				alumEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumEstado;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getCarga(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	carga		= "x";
		
		try{
			comando = "SELECT CARGA_ID FROM ENOC.ALUM_ESTADO " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND ESTADO = 'I' " +
					"AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA " + 
					"WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL)"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				carga = rs.getString("CARGA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carga;
	}
	
	public static String getFacultad(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	facu		= "x";
		
		try{
			comando = " SELECT DISTINCT(FACULTAD_ID) FROM ENOC.ALUM_ESTADO "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				facu = rs.getString("FACULTAD_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return facu;
	}
	
	public static String getCarrera(Connection conn, String codigoPersonal) throws SQLException{
			
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			String 	carrera		= "x";
			
			try{
				comando = " SELECT DISTINCT(CARRERA_ID) FROM ENOC.ALUM_ESTADO "
						+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
						+ " AND CARRERA_ID != '00000'";
				rs = st.executeQuery(comando);
				if (rs.next()){
					carrera = rs.getString("CARRERA_ID");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.EstadoUtil|getCarrera|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return carrera;
		}
	
	public static String getCarreraEnFecha(Connection conn, String codigoPersonal, String fecha) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	carga		= "x";
		
		try{
			comando = " SELECT DISTINCT(CARRERA_ID) FROM ENOC.ALUM_ESTADO "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND CARGA_ID IN "
					+ "		(SELECT CARGA_ID FROM ENOC.CARGA "
					+ "		WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL"
					+ "		AND CARRERA_ID != '00000')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				carga = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getCarreraEnFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carga;
	}
	
	public static String getAlumComidas(Connection conn, String codigo, String carga, int bloque) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "x";
		
		try{
			comando = "SELECT ALUM_COMIDAS('"+codigo+"','"+carga+"',"+bloque+") AS COMIDAS" +
					  " FROM ENOC.ALUM_ESTADO" + 
					  " WHERE CODIGO_PERSONAL = '"+codigo+"'"+
					  " AND CARGA_ID = '"+carga+"'"+
					  " AND BLOQUE_ID = '"+bloque+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("CODIGO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getAlumComidas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}
	
	public static TreeMap<String,AlumEstado> getMapAllInscritos(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,AlumEstado> Inscritos	= new TreeMap<String,AlumEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_ESTADO"+ 
					" WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)  "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				//System.out.println(rs.getString("CODIGO_PERSONAL"));
				AlumEstado ins = new AlumEstado();
				ins.mapeaReg(rs);
				llave = ins.getCodigoPersonal();
				Inscritos.put(llave, ins);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getMapAllInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return Inscritos;
	}
	
	public static String getResidencia(Connection conn, String codigoPersonal) throws SQLException{
			
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			String 	residencia	= "x";
			
			try{
				comando = "SELECT RESIDENCIA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
				rs = st.executeQuery(comando);
				if (rs.next()){
					residencia = rs.getString("RESIDENCIA_ID");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.EstadoUtil|getInterno|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return residencia;
		}
	
	public static String getDormitorio(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	dormi		= "x";
		
		try{
			comando = "SELECT DORMITORIO FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				dormi = rs.getString("DORMITORIO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dormi;
	}
	
	public static String getInterno(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	residencia	= "x";
		
		try{
			comando = "SELECT RESIDENCIA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
					  " AND CARGA_ID = '"+cargaId+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				residencia = rs.getString("RESIDENCIA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return residencia;
	}
	
	public static String cargaCreditos(Connection conn, String carga, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "0";
		
		try{
			comando = " SELECT COALESCE(SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID)),0) AS TOTAL FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = '"+carga+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = 'I'"
					+ " AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') "
					+ " 	AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|cargaCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}
	
	public static String calculoCreditos(Connection conn, String carga, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "0";
		
		try{
			comando = " SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = '"+carga+"'"
					+ " AND MATRICULA||CARGA_ID||BLOQUE IN"
					+ " 	(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO "
					+ "		WHERE CARGA_ID = '"+carga+"' AND INSCRITO = 'S'"
					+ " 	AND MODALIDAD_ID IN ("+modalidades+")"
					+ "		AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') "
					+ " 	AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') )"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|calculoCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}
	
	public static String numAlumEst(Connection conn, String carga, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "0";
		
		try{
			comando = " SELECT COUNT(*) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+carga+"'"					
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') "
					+ " AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|numAlumEst|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}

	public static String numAlumCalculo(Connection conn, String carga, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String 	cantAlum	= "0";
		
		try{
			comando = " SELECT COUNT(*) AS TOTAL FROM MATEO.FES_CCOBRO"
					+ " WHERE CARGA_ID = '"+carga+"' "
					+ " AND INSCRITO = 'S'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') "
					+ " AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cantAlum = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|numAlumCalculo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantAlum;
	}
	
	public static boolean getAlumnoInscrito(Connection conn, String matricula, String carga) throws SQLException{
		boolean	encontro		= false;
		PreparedStatement ps 	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT * FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CARGA_ID = ? "
					+ " AND ESTADO = 'I'"); 
			ps.setString(1, matricula);
			ps.setString(2, carga);

			if (ps.executeUpdate() == 1)
				encontro = true;
			else
				encontro = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getAlumnoInscrito|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return encontro;
	}
	
	public static String getAlumEstado(Connection conn, String matricula, String carga, String bloque) throws SQLException{		
		PreparedStatement ps 	= null;
		ResultSet rs			= null;
		String estado = "-";
		
		try{
			ps = conn.prepareStatement(" SELECT COALESCE(ESTADO,'M') AS ESTADO FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = ?"); 
			ps.setString(1, matricula);
			ps.setString(2, carga);
			ps.setString(3, bloque);
			
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("ESTADO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getAlumEstado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return estado;
	}
	
	public String geCreditosPorCarga(Connection conn, String matricula, String carga, String estado) throws SQLException{		
		PreparedStatement ps 	= null;
		ResultSet rs			= null;
		String total 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM MATEO.FES_CC_MATERIA "
					+ " WHERE MATRICULA||CARGA_ID IN "
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID IN (?) AND ESTADO IN(?)) "
					+ " GROUP BY MATRICULA, CARGA_ID "); 
			ps.setString(1, matricula);
			ps.setString(2, carga);
			ps.setString(3, estado);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getString("TOTAL");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|geCreditosPorCarga|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return total;
	}
		
	public ArrayList<AlumEstado> getLista(Connection Conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"+
				" FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> getLista(Connection Conn, String cargas, String modalidades, String estado,String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado alumEstado = new AlumEstado();
				alumEstado.mapeaReg(rs);
				lisEstado.add(alumEstado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> getLista(Connection Conn, String cargas, String modalidades, String estado, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"					
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado alumEstado = new AlumEstado();
				alumEstado.mapeaReg(rs);
				lisEstado.add(alumEstado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}

	public ArrayList<AlumEstado> getListaPorFecha(Connection Conn, String cargas, String modalidades, String fechaIni, String fechaFin, String estado, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"				
					+ " AND ESTADO = '"+estado+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado alumEstado = new AlumEstado();
				alumEstado.mapeaReg(rs);
				lisEstado.add(alumEstado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getListaPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> lisHistoricoDeBajas(Connection Conn, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"+
				" FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|lisHistoricoDeBajas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}	
	
	public ArrayList<String> getListCargasNoVigentes(Connection Conn, String codigoPersonal, String fechaInicio, String orden ) throws SQLException{
		
		ArrayList<String> list = new ArrayList<String>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND (SELECT F_INICIO FROM ENOC.CARGA WHERE CARGA_ID = ENOC.ALUM_ESTADO.CARGA_ID) > TO_DATE('"+fechaInicio+"','DD/MM/YYYY')"
					+ " AND ESTADO = 'I' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				list.add(rs.getString("CARGA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getListCargasNoVigentes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<AlumEstado> getLista(Connection Conn, String codigoPersonal) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> getLista(Connection Conn, String cargaId, String planId, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND ALUM_PLAN_HISTORICO(CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID) = '"+planId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado		= new ArrayList<AlumEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public static HashMap<String,String> getMapInscritos(Connection conn, String cargaId, String orden ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL, CARRERA_ID" +
					" FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId+"' AND ESTADO = 'I' "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA_ID") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapPlan;
	}
	
	public static HashMap<String,String> getMapInscritosActualmente(Connection conn) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL, CARRERA_ID" +
					" FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I' AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)  ";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA_ID") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getMapInscritosActualmente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapPlan;
	}
	
	public static HashMap<String, String> getMapInscritoFecha(Connection conn, String fecha) throws SQLException{
	
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I' "
					+ " AND CARGA_ID IN ( SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getMapInscritoFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public static HashMap<String, String> mapTipoAlumno(Connection conn, String fecha) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, TIPOALUMNO_ID FROM ENOC.ALUM_ESTADO"
					+ " WHERE ESTADO = 'I' "
					+ " AND CARGA_ID IN ( SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("TIPOALUMNO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapTipoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapAlumCargaCreditos(Connection conn) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ " CODIGO_PERSONAL, CARGA_ID, ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID) AS TOTAL_CREDITOS"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND ESTADO = 'I'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CARGA_ID"),rs.getString("TOTAL_CREDITOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapAlumCargaCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapAlumCargaModalidadCreditos(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ " CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID, ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID) AS TOTAL_CREDITOS"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = 'I'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CARGA_ID")+rs.getString("MODALIDAD_ID"),rs.getString("TOTAL_CREDITOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapAlumCargaCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapAlumPromCargasyModalidades(Connection conn, String cargas, String modalidades, String estado) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CARGA_ID, CODIGO_PERSONAL, ENOC.ALUM_PROMEDIO_CARGA(CODIGO_PERSONAL,CARGA_ID,PLAN_ID) AS PROMEDIO"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = '"+estado+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CARGA_ID")+rs.getString("CODIGO_PERSONAL"),rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapAlumPromCargasyModalidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<AlumEstado> getListaCargaInscrito(Connection Conn, String codigoPersonal) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado 		= new ArrayList<AlumEstado>();
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) AND ESTADO ='I'"; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|getListaCargaInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<AlumEstado> filtroAlumnos(Connection Conn, String modalidad, String facultad, String carrera_id, String residencia, String dormitorio, String grado) throws SQLException{
		
		ArrayList<AlumEstado> lisEstado = new ArrayList<AlumEstado>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) AND ESTADO ='I'";
			if (!modalidad.equals("0")){
				comando = comando + " AND MODALIDAD_ID IN ("+modalidad+")";
			}
			
			if (!facultad.equals("000")){
				comando = comando + " AND FACULTAD_ID = '"+facultad+"'";
			}
			
			if (!carrera_id.equals("00000")){
				comando = comando + " AND CARRERA_ID = '"+carrera_id+"'";
			}
			
			if (!residencia.equals("0")){
				comando = comando + " AND RESIDENCIA_ID = '"+residencia+"'";
			}
			
			if (!dormitorio.equals("0")){
				comando = comando + " AND DORMITORIO = '"+dormitorio+"'";
			}
			
			if (!grado.equals("0")){
				comando = comando + " AND GRADO = "+grado+" ";
			}
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEstado estado = new AlumEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|filtroAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public static HashMap<String, String> mapGradoyCiclo(Connection conn, String cargas, String estado) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CARGA_ID, CODIGO_PERSONAL, GRADO||','||CICLO AS DATO"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"	
					+ " AND ESTADO = '"+estado+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CARGA_ID")+rs.getString("CODIGO_PERSONAL"), rs.getString("DATO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapGradoyCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}


}