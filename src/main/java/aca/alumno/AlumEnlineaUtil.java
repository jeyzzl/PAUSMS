package aca.alumno;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumEnlineaUtil {
	
	public boolean insertReg(Connection conn, AlumEnlinea alumEnlinea) throws Exception{
		PreparedStatement ps 	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_ENLINEA"+ 
 				"(CODIGO_PERSONAL, CARGA_ID," +
 				" USUARIO, SOLICITUD, COMENTARIOS, FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR)"+ 
 				" VALUES(?,?,?,?,?,now(),?,?,?)");	
 			
 			ps.setString(1, alumEnlinea.getCodigoPersonal());
 			ps.setString(2, alumEnlinea.getCargaId());
 			ps.setString(3, alumEnlinea.getUsuario());
 			ps.setString(4, alumEnlinea.getSolicitud());
 			ps.setString(5, alumEnlinea.getComentarios());
 			ps.setString(6, alumEnlinea.getEstado());
 			ps.setString(7, alumEnlinea.getResidenciaId());
 			ps.setString(8, alumEnlinea.getCoordinador());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumEnlinea alumEnlinea) throws Exception{ 		
 		PreparedStatement ps 	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ENLINEA"+ 
 				" SET USUARIO = ?, " +
 				" SOLICITUD = ?, " +
 				" COMENTARIOS = ?, " +
 				" ESTADO = ?, " +
 				" RESIDENCIA_ID = ?, " +
 				" COORDINADOR = ? " +
 				" WHERE CODIGO_PERSONAL = ? " +
 				" AND CARGA_ID = ? ");
 				
 			ps.setString(1, alumEnlinea.getUsuario());
 			ps.setString(2, alumEnlinea.getSolicitud());
 			ps.setString(3, alumEnlinea.getComentarios());
 			ps.setString(4, alumEnlinea.getEstado());
 			ps.setString(5, alumEnlinea.getResidenciaId());
 			ps.setString(6, alumEnlinea.getCoordinador());
 			ps.setString(7, alumEnlinea.getCodigoPersonal()); 			
 			ps.setString(8, alumEnlinea.getCargaId());
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false; 			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|updateReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	 	
 	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_ENLINEA"+ 
 				" WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ");
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, cargaId);			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public AlumEnlinea mapeaRegId( Connection conn, String codigoPersonal, String cargaId ) throws SQLException, IOException{
 		AlumEnlinea alumEnlinea = new AlumEnlinea();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
	 			" FROM ENOC.ALUM_ENLINEA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?"); 
	 		ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			alumEnlinea.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return alumEnlinea;
 	}
	
 	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ENLINEA"+ 
 				" WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?");
 			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|existeReg|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public String getStatusFinanciero(Connection conn, String matricula) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String status = "A";
		
		try{
			ps = conn.prepareStatement("SELECT DISTINCT(STATUS) FROM NOE.FES_COBRO_ONLINE_SEC WHERE MATRICULA = ? AND STATUS = 'I'"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				status = rs.getString("STATUS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getStatusFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return status;
	}
 	
	public static ArrayList<AlumEnlinea> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEnlinea> lisAlumno	= new ArrayList<AlumEnlinea>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEnlinea alumno = new AlumEnlinea();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public static ArrayList<AlumEnlinea> getListAlumno(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<AlumEnlinea> lisAlumno	= new ArrayList<AlumEnlinea>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA WHERE CODIGO_PERSONAL='"+codigoPersonal+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEnlinea alumno = new AlumEnlinea();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public static ArrayList<AlumEnlinea> getListCargas(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<AlumEnlinea> lisAlumno	= new ArrayList<AlumEnlinea>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA WHERE CARGA_ID='"+cargaId+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEnlinea alumno = new AlumEnlinea();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public ArrayList<AlumEnlinea> getListaAlumnosCoordinador(Connection conn, String carrerasCoordinador, String orden) throws SQLException{
		ArrayList<AlumEnlinea> lisAlumno	= new ArrayList<AlumEnlinea>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT A.CODIGO_PERSONAL, A.CARGA_ID, A.USUARIO, A.SOLICITUD, A.COMENTARIOS, TO_CHAR(A.FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, A.ESTADO, A.RESIDENCIA_ID, A.COORDINADOR" +
						" FROM ENOC.ALUM_ENLINEA A INNER JOIN ALUMNO B" +
						" ON A.CODIGO_PERSONAL=B.CODIGO_PERSONAL" +
						" WHERE B.CARRERA_ID IN ("+carrerasCoordinador+") "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEnlinea alumno = new AlumEnlinea();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListaAlumnosCoordinador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public HashMap<String, String> getMapaCantidadAlumnosPorSolicitudFacultad(Connection conn, String cargaId, String carreras) throws SQLException{
		HashMap<String, String> mapAlumno			= new HashMap<String, String>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		boolean tieneCarreras = !carreras.equals("todas");
		try{
			comando = "SELECT ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL)) AS FACULTAD_ID," +
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='E' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS E," +
					
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='A' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS A," +
					
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='N' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS N," +
					
					" (SELECT COUNT(DISTINCT(MATRICULA)) FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"	WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA))" +
					" 	AND B.MATRICULA NOT IN" +
						" (SELECT MATRICULA FROM MATEO.FES_CCOBRO C" +
							" WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S' "+
							" AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(C.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))) AS C," +
					
					" (SELECT COUNT(DISTINCT(MATRICULA)) FROM MATEO.FES_CCOBRO B" +
					"	WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA))) AS I" +
					
					" FROM ENOC.ALUM_ENLINEA Z WHERE CARGA_ID='"+cargaId+"' "+(tieneCarreras?"AND (ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL)) IN ("+carreras+")":"") +
					" GROUP BY ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL))";
			//System.out.println(comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapAlumno.put(rs.getString("FACULTAD_ID"), rs.getString("E")+"~"+rs.getString("A")+"~"+rs.getString("N")+"~"+rs.getString("C")+"~"+rs.getString("I"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getMapaCantidadAlumnosPorSolicitudFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		return mapAlumno;
	}
}
