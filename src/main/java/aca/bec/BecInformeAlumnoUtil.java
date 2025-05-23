package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecInformeAlumnoUtil {
	
	public boolean insertReg(Connection conn, BecInformeAlumno becInformeAlumno) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_INFORME_ALUMNO"+ 
				"(CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO,"+
				" INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, ID_CCOSTO, USUARIO)"+
				" VALUES(?, TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'999'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'),TO_NUMBER(?,'99'),"+
				" TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),?,?, TO_NUMBER(?,'9999999'))");
					
			ps.setString(1, becInformeAlumno.getCodigoPersonal());
			ps.setString(2, becInformeAlumno.getInformeId());
			ps.setString(3, becInformeAlumno.getIdEjercicio());
			ps.setString(4, becInformeAlumno.getHoras());
			ps.setString(5, becInformeAlumno.getPuestoId());
			ps.setString(6, becInformeAlumno.getTardanzas());
			ps.setString(7, becInformeAlumno.getAusencias());
			ps.setString(8, becInformeAlumno.getFecha());
			ps.setString(9, becInformeAlumno.getPuntualidad());
			ps.setString(10, becInformeAlumno.getFuncion());
			ps.setString(11, becInformeAlumno.getTiempo());
			ps.setString(12, becInformeAlumno.getIniciativa());
			ps.setString(13, becInformeAlumno.getRelacion());
			ps.setString(14, becInformeAlumno.getRespeto());
			ps.setString(15, becInformeAlumno.getProductivo());
			ps.setString(16, becInformeAlumno.getCuidado());
			ps.setString(17, becInformeAlumno.getEstado());
			ps.setString(18, becInformeAlumno.getIdCcosto());
			ps.setString(19, becInformeAlumno.getUsuario());
			 
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn, BecInformeAlumno becInformeAlumno) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_INFORME_ALUMNO"+ 
				" SET ID_EJERCICIO = ?, HORAS = TO_NUMBER(?,'999'),"+
				" PUESTO_ID =  ?, TARDANZAS = TO_NUMBER(?,'99'), AUSENCIAS = TO_NUMBER(?,'99'), FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" PUNTUALIDAD = TO_NUMBER(?,'99'), FUNCION = TO_NUMBER(?,'99'), TIEMPO=TO_NUMBER(?,'99'),"+
				" INICIATIVA = TO_NUMBER(?,'99'), RELACION = TO_NUMBER(?,'99'), RESPETO = TO_NUMBER(?,'99'),"+
				" PRODUCTIVO = TO_NUMBER(?,'99'), CUIDADO = TO_NUMBER(?,'99'), ESTADO = ?, ID_CCOSTO = ?, USUARIO= TO_NUMBER(?,'9999999')"+
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, becInformeAlumno.getIdEjercicio());
			ps.setString(2, becInformeAlumno.getHoras());
			ps.setString(3, becInformeAlumno.getPuestoId());
			ps.setString(4, becInformeAlumno.getTardanzas());
			ps.setString(5, becInformeAlumno.getAusencias());
			ps.setString(6, becInformeAlumno.getFecha());
			ps.setString(7, becInformeAlumno.getPuntualidad());
			ps.setString(8, becInformeAlumno.getFuncion());
			ps.setString(9, becInformeAlumno.getTiempo());
			ps.setString(10, becInformeAlumno.getIniciativa());
			ps.setString(11, becInformeAlumno.getRelacion());
			ps.setString(12, becInformeAlumno.getRespeto());
			ps.setString(13, becInformeAlumno.getProductivo());
			ps.setString(14, becInformeAlumno.getCuidado());
			ps.setString(15, becInformeAlumno.getEstado());
			ps.setString(16, becInformeAlumno.getIdCcosto());
			ps.setString(17, becInformeAlumno.getUsuario());
			ps.setString(18, becInformeAlumno.getInformeId());
			ps.setString(19, becInformeAlumno.getCodigoPersonal());
			
			
		
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateEstado(Connection conn, String estado, String informeId, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_INFORME_ALUMNO"+ 
				" SET ESTADO = ?"+
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			
			ps.setString(1, estado);
			ps.setString(2, informeId);			
			ps.setString(3, codigoPersonal);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|updateEstado|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateHoras(Connection conn, String horas, String informeId, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_INFORME_ALUMNO"+ 
				" SET HORAS = ?"+
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			
			ps.setString(1, horas);
			ps.setString(2, informeId);			
			ps.setString(3, codigoPersonal);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|updateEstado|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public boolean deleteReg(Connection conn, String informeId, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_INFORME_ALUMNO"+ 
					" WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, informeId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecInformeAlumno mapeaRegId(Connection conn, String informeId, String codigoPersonal) throws SQLException{
		BecInformeAlumno becInformeAlumno = new BecInformeAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_INFORME_ALUMNO  WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, informeId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becInformeAlumno.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becInformeAlumno;
	}
	
	public boolean existeReg(Connection conn, String informeId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, informeId);
			ps.setString(2, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	/*
	 * CUENTA LAS HORAS ACUMULADAS DEL ALUMNO EN EL PUESTO ACTUAL
	 * */
	public static String getHorasAcumuladas(Connection conn, String idEjercicio, String idCcosto, String codigoPersonal) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(HORAS),0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+
						" AND CODIGO_PERSONAL||PUESTO_ID IN"+ 
						"  (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO " +
						"   WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"'" +
						"	AND TO_DATE(now(),'DD/MM/YYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
						" AND CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			
			rs = ps.executeQuery();
			if (rs.next()){
				res = rs.getString("HORAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|getHorasAcumuladasExcluirActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}	
	
	
	public static String getHorasAcumuladasExcluirActual(Connection conn, String idEjercicio, String idCcosto, String codigoPersonal, String informeExcluido) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(HORAS),0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+
						" AND CODIGO_PERSONAL||PUESTO_ID IN"+ 
						" (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO " +
						"	WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"'" +
						"	AND TO_DATE(now(),'DD/MM/YYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
						" AND CODIGO_PERSONAL = '"+codigoPersonal+"'"+
						" AND INFORME_ID NOT IN ('"+informeExcluido+"') ");
			
			rs = ps.executeQuery();
			if (rs.next()){
				res = rs.getString("HORAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|getHorasAcumuladasExcluirActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	/*
	 * CUENTA LAS HORAS ACUMULADAS DEL ALUMNO EN EL PUESTO ACTUAL
	 * */
	public static String horasEnPuesto(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(HORAS),0) AS HORAS FROM ENOC.BEC_INFORME_ALUMNO"
				+ " WHERE CODIGO_PERSONAL = ?" 
				+ " AND PUESTO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, puestoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				res = rs.getString("HORAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|horasEnPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	
	public ArrayList<BecInformeAlumno> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecInformeAlumno> lis 		= new ArrayList<BecInformeAlumno>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_INFORME_ALUMNO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInformeAlumno obj= new BecInformeAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}	
	
	public ArrayList<BecInformeAlumno> getBecInformeAlumnoPorEjercicioIdInformeId(Connection conn, String ejercicioId, String informeId, String orden) throws SQLException{
		
		ArrayList<BecInformeAlumno> lis 		= new ArrayList<BecInformeAlumno>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO, "
					+ " INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, VERSION, ID_CCOSTO, USUARIO FROM ENOC.BEC_INFORME_ALUMNO "
					+ "	WHERE ID_EJERCICIO = '"+ejercicioId+"' AND INFORME_ID = '"+informeId+"' "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInformeAlumno obj= new BecInformeAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|getBecInformeAlumnoPorEjercicioIdInformeId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lis;
	}
	
	public static HashMap<String, BecInformeAlumno> mapInforme(Connection conn, String informeId) throws SQLException{
		HashMap<String, BecInformeAlumno> mapa = new HashMap<String, BecInformeAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, HORAS FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = "+informeId;
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecInformeAlumno obj= new BecInformeAlumno();
				obj.mapeaReg(rs);
				mapa.put(rs.getString("CODIGO_PERSONAL"), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapInforme|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapInformeHoras(Connection conn, String informeId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, HORAS FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = "+informeId;
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapInformeHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHoras(Connection conn, String idCcosto) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, COALESCE(SUM(HORAS),0) AS HORAS"
					+ " FROM ENOC.BEC_INFORME_ALUMNO WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " 	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = '"+idCcosto+"')"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"),rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasInformadas(Connection conn, String idCcosto) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, COALESCE(SUM(HORAS),0) AS HORAS FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " 	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = '"+idCcosto+"')"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"),rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	
	
	public static HashMap<String, String> mapHorasTotales(Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	" SELECT CODIGO_PERSONAL, TIPO, PUESTO_ID, COALESCE(HORAS,0) AS HORAS"+ 
					    " FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL||PUESTO_ID IN "+ 
					    " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"')"+
					    " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ACUERDO IN('B','I'))";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL") + rs.getString("TIPO") + rs.getString("PUESTO_ID"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> estadoCcosto(Connection conn, String ejercicioId, String informeId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	" SELECT ID_CCOSTO, ESTADO, COUNT(CODIGO_PERSONAL) AS NUMERO"+
					    " FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = '"+informeId+"' " +
					    "AND CODIGO_PERSONAL||ID_CCOSTO IN "+ 
					    "  (SELECT CODIGO_PERSONAL||ID_CCOSTO FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+ejercicioId+"' " +
					    "   AND (SELECT FECHA_INI FROM ENOC.BEC_INFORME WHERE INFORME_ID = "+informeId+") BETWEEN FECHA_INI AND FECHA_FIN)" +
					    " GROUP BY ID_CCOSTO, ESTADO";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("ID_CCOSTO") + rs.getString("ESTADO"), rs.getString("NUMERO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|estadoCcosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	/* Map del estado de los alumnos registrados en un informe de talleres */
	public static HashMap<String, String>mapDeptoEstado(Connection conn, String ejercicioId, String informeId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT ID_CCOSTO, ESTADO, COUNT(CODIGO_PERSONAL) AS NUMERO FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' AND INFORME_ID = '"+informeId+"'"
					+ " GROUP BY ID_CCOSTO, ESTADO";					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("ID_CCOSTO") + rs.getString("ESTADO"), rs.getString("NUMERO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapDeptoEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> totalPorDepartamento(Connection conn, String ejercicioId, String informeId, String niveles) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	" SELECT ID_CCOSTO, COUNT(CODIGO_PERSONAL) AS NUMERO"+ 
						" FROM ENOC.BEC_PUESTO_ALUMNO " +
						" WHERE ID_EJERCICIO = '"+ejercicioId+"'" +
						" AND NIVEL_ID IN ("+niveles+")" +
						" AND ESTADO = 'C'" +
						" AND (SELECT FECHA_INI FROM ENOC.BEC_INFORME WHERE INFORME_ID = "+informeId+") BETWEEN FECHA_INI AND FECHA_FIN"+		 
						" GROUP BY ID_CCOSTO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("ID_CCOSTO"), rs.getString("NUMERO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|totalPorDepartamento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> totalUniv(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT ID_CCOSTO, COUNT(CODIGO_PERSONAL) AS NUMERO"+ 
						" FROM ENOC.BEC_PUESTO_ALUMNO "+
						" WHERE CODIGO_PERSONAL NOT IN "+
						"(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE (SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = INSCRITOS.CARRERA_ID) = 1)"+ 
						" GROUP BY ID_CCOSTO";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("ID_CCOSTO"), rs.getString("NUMERO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|totalUniv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasAlumno(Connection conn, String ejercicioId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID, COALESCE(HORAS,0) AS HORAS"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID")+rs.getString("INFORME_ID"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasAlumnoEnPuesto(Connection conn ) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID, COALESCE(HORAS,0) AS HORAS"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " 	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " 	WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID")+rs.getString("INFORME_ID"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasAlumnoEnPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasPuestoAlumnoEstado(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	" SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID, COALESCE(HORAS,0) AS HORAS, ESTADO "+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
						" AND PUESTO_ID = '"+puestoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("INFORME_ID"), rs.getString("HORAS")+"@"+rs.getString("ESTADO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasPuestoAlumnoEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasPuestoAlumno(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID, COALESCE(HORAS,0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
						" AND PUESTO_ID = '"+puestoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("INFORME_ID"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasPuestoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasAlumnoPorMes(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT INFORME_ORDEN(INFORME_ID) AS MES, COALESCE(SUM(HORAS),0) AS HORAS"
						+ " FROM ENOC.BEC_INFORME_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
						+ " AND PUESTO_ID = '"+puestoId+"'"
						+ " GROUP BY INFORME_ORDEN(INFORME_ID)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MES"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasAlumnoPorMes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapHorasPuestoAlumnoMes(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL, PUESTO_ID, ORDEN, COALESCE(SUM(HORAS),0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
						" AND PUESTO_ID = '"+puestoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("INFORME_ID"), rs.getString("HORAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapHorasPuestoAlumnoMes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapEstado(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID,ESTADO"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
						" AND PUESTO_ID = '"+puestoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("INFORME_ID"), rs.getString("ESTADO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapEvaluacionAlumno(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL, PUESTO_ID, INFORME_ID,"+
						" ((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10 AS PROMEDIO"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
						" AND PUESTO_ID = '"+puestoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("INFORME_ID"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapEvaluacionAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapEvaluacionEnPuesto(Connection conn, String fecha) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, SUM(((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10)/COUNT(PUESTO_ID) AS PROMEDIO"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapEvaluacionEnPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapEvaluacionAlumnosTotal(Connection conn, String idEjercicio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	   " SELECT CODIGO_PERSONAL, PUESTO_ID , ID_EJERCICIO, SUM(((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10)/COUNT(PUESTO_ID) AS PROMEDIO"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' GROUP BY CODIGO_PERSONAL, PUESTO_ID, ID_EJERCICIO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapEvaluacionAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public ArrayList<BecInformeAlumno> getHorasAlumnosInformados(Connection conn, String idEjercicio, String idCcosto, String informeId, String orden) throws SQLException{
		
		ArrayList<BecInformeAlumno> lis = new ArrayList<BecInformeAlumno>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT " +
					" CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, " +
					" PUNTUALIDAD, FUNCION, TIEMPO, INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, ID_CCOSTO,USUARIO" +
					" FROM ENOC.BEC_INFORME_ALUMNO " +
					" WHERE ID_EJERCICIO ='"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+idCcosto+"'" +
					" AND INFORME_ID = '"+informeId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInformeAlumno obj= new BecInformeAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|getHorasAlumnosInformados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInformeAlumno> alumnosPromedio(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<BecInformeAlumno> lis 		= new ArrayList<BecInformeAlumno>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_INFORME_ALUMNO WHERE CODIGO_PERSONAL ='"+codigoPersonal+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecInformeAlumno obj= new BecInformeAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|alumnosPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lis;
	}
	
	public ArrayList<String> alumnosInformes(Connection conn, String idEjercicio, String informes, String estado, String orden) throws SQLException{
		
		ArrayList<String> lis 			= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE INFORME_ID IN ("+informes+")"
					+ " AND ID_EJERCICIO = '"+idEjercicio+"' "
					+ " AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|alumnosInformes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static String saldoAlumnoInforme(Connection conn, String puesto, String codigoPersonal, String informe, String tipo) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		Statement st 					= conn.createStatement();		
		String comando					= "";
		
		
		try{
			comando = "SELECT COALESCE(SUM(IMPORTE),0) AS TOTAL FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = '"+codigoPersonal+"'"					
					+ " AND REFERENCIA LIKE '%"+puesto+"%'"
					+ " AND REFERENCIA LIKE '%"+informe+"%'"
					+ " AND NATURALEZA = '"+tipo+"'";
			
			rs = st.executeQuery(comando);		
			//System.out.println(comando);
			if (rs.next()){
				res = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|saldoAlumnoInforme|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}	
	
	public static String saldoBecaBasica(Connection conn, String puesto, String codigoPersonal, String informe, String tipo, String folio) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		Statement st 					= conn.createStatement();		
		String comando					= "";		
		
		try{
			comando = " SELECT COALESCE(SUM(IMPORTE),0) AS TOTAL FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = '"+codigoPersonal+"'"					
					+ " AND REFERENCIA LIKE '%"+puesto+"%'"
					+ " AND REFERENCIA LIKE '%"+informe+"%'"					
					+ " AND REFERENCIA LIKE '%"+folio+"%'"
					+ " AND NATURALEZA = '"+tipo+"'";
			
			
			rs = st.executeQuery(comando);			
			if (rs.next()){
				res = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|saldoBecaBasica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public static HashMap<String, String> mapPuesto(Connection conn, String informe) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL, INFORME_ID, PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informe+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("INFORME_ID") , rs.getString("PUESTO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumnoUtil|mapPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
}
