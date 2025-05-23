/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class MapaNuevoCursoUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoCurso mapaNuevoCurso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_CURSO"+ 
				"(PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, HSS, HAS, PROYECTO) "+
				"VALUES( ?, TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), ?,"+
				" ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), ?," +
				" ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99'), TO_NUMBER(?, '9999'), TO_NUMBER(?, '99.9999'), ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, TO_NUMBER(?, '9')," +
				" ?, ?, TO_NUMBER(?, '9999'),TO_NUMBER(?, '9999'), ?)");	
					
			ps.setString(1, mapaNuevoCurso.getPlanId());
			ps.setString(2, mapaNuevoCurso.getCursoId());
			ps.setString(3, mapaNuevoCurso.getVersionId());
			ps.setString(4, mapaNuevoCurso.getClave());
			ps.setString(5, mapaNuevoCurso.getNombre());
			ps.setString(6, mapaNuevoCurso.getCiclo());
			ps.setString(7, mapaNuevoCurso.getFCreada());
			ps.setString(8, mapaNuevoCurso.getCodigoMaestro());
			ps.setString(9, mapaNuevoCurso.getUbicacion());
			ps.setString(10, mapaNuevoCurso.getSeriacion());
			ps.setString(11, mapaNuevoCurso.getHst());
			ps.setString(12, mapaNuevoCurso.getHsp());
			ps.setString(13, mapaNuevoCurso.getThs());
			ps.setString(14, mapaNuevoCurso.getHt());
			ps.setString(15, mapaNuevoCurso.getCreditos());
			ps.setString(16, mapaNuevoCurso.getDescripcion());
			ps.setString(17, mapaNuevoCurso.getBibliografia());
			ps.setString(18, mapaNuevoCurso.getCompetencia());
			ps.setString(19, mapaNuevoCurso.getMediosRecursos());
			ps.setString(20, mapaNuevoCurso.getEeDiagnostica());
			ps.setString(21, mapaNuevoCurso.getEeFormativa());
			ps.setString(22, mapaNuevoCurso.getEeSumativa());
			ps.setString(23, mapaNuevoCurso.getEscala());
			ps.setString(24, mapaNuevoCurso.getEstado());
			ps.setString(25, mapaNuevoCurso.getHei());
			ps.setString(26, mapaNuevoCurso.getHfd());
			ps.setString(27, mapaNuevoCurso.getHss());
			ps.setString(28, mapaNuevoCurso.getHas());
			ps.setString(29, mapaNuevoCurso.getProyecto());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoCurso mapaNuevoCurso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_CURSO" + 
					" SET CLAVE = ?," +
					" NOMBRE = ?, " +
					" CICLO = TO_NUMBER(?, '99'), " +
					" F_CREADA = TO_DATE(?, 'DD/MM/YYYY'), " +
					" CODIGO_MAESTRO = ?," +
					" UBICACION = ?," +
					" SERIACION = ?," +
					" HST = TO_NUMBER(?, '99')," +
					" HSP = TO_NUMBER(?, '99')," +
					" THS = TO_NUMBER(?, '99')," +
					" HT = TO_NUMBER(?, '9999')," +
					" CREDITOS = TO_NUMBER(?, '99.9999')," +
					" DESCRIPCION = ?," +
					" BIBLIOGRAFIA = ?," +
					" COMPETENCIA = ?," +
					" MEDIOS_RECURSOS = ?," +
					" EE_DIAGNOSTICA = ?," +
					" EE_FORMATIVA = ?," +
					" EE_SUMATIVA = ?," +
					" ESCALA = ?," +
					" ESTADO = TO_NUMBER(?, '9')," +
					" HEI = ?," +
					" HFD = ?," +
					" HSS = TO_NUMBER(?,'9999'),"+
					" HAS = TO_NUMBER(?,'9999'),"+
					" PROYECTO = ? "+
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, mapaNuevoCurso.getClave());
			ps.setString(2, mapaNuevoCurso.getNombre());			
			ps.setString(3, mapaNuevoCurso.getCiclo());
			ps.setString(4, mapaNuevoCurso.getFCreada());
			ps.setString(5, mapaNuevoCurso.getCodigoMaestro());
			ps.setString(6, mapaNuevoCurso.getUbicacion());
			ps.setString(7, mapaNuevoCurso.getSeriacion());
			ps.setString(8, mapaNuevoCurso.getHst());
			ps.setString(9, mapaNuevoCurso.getHsp());
			ps.setString(10, mapaNuevoCurso.getThs());
			ps.setString(11, mapaNuevoCurso.getHt());			
			ps.setString(12, mapaNuevoCurso.getCreditos());
			ps.setString(13, mapaNuevoCurso.getDescripcion());
			ps.setString(14, mapaNuevoCurso.getBibliografia());
			ps.setString(15, mapaNuevoCurso.getCompetencia());
			ps.setString(16, mapaNuevoCurso.getMediosRecursos());
			ps.setString(17, mapaNuevoCurso.getEeDiagnostica());
			ps.setString(18, mapaNuevoCurso.getEeFormativa());
			ps.setString(19, mapaNuevoCurso.getEeSumativa());
			ps.setString(20, mapaNuevoCurso.getEscala());
			ps.setString(21, mapaNuevoCurso.getEstado());
			ps.setString(22, mapaNuevoCurso.getHei());
			ps.setString(23, mapaNuevoCurso.getHfd());
			ps.setString(24, mapaNuevoCurso.getHss());
			ps.setString(25, mapaNuevoCurso.getHas());
			ps.setString(26, mapaNuevoCurso.getProyecto());
			ps.setString(27, mapaNuevoCurso.getCursoId());
			ps.setString(28, mapaNuevoCurso.getPlanId());
			ps.setString(29, mapaNuevoCurso.getVersionId());
			
			if (ps.executeUpdate()== 1){
				ok = true;								
			}else{
				ok = false;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String planId, String versionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE CURSO_ID = ?" +
				" AND PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, planId);
			ps.setString(3, versionId);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoCurso mapeaRegId( Connection conn, String cursoId, String planId, String versionId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MapaNuevoCurso mapaNuevoCurso = new MapaNuevoCurso();
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
					" NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO," +
					" UBICACION, SERIACION, HST, HSP," +
					" THS, HT, CREDITOS, DESCRIPCION," +
					" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
					" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
					" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
					" FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, planId);
			ps.setString(3, versionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoCurso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaNuevoCurso;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String planId, String versionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, planId);
			ps.setString(3, versionId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}

	public boolean existeClave(Connection conn, String clave, String planId, String versionId, String cursoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CLAVE LIKE ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND CURSO_ID != TO_NUMBER(?, '9999999')");
			
			ps.setString(1, "%"+clave.trim()+"%");
			ps.setString(2, planId);
			ps.setString(3, versionId);
			ps.setString(4, cursoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|existeClave|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CURSO_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_CURSO"); 
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;
	}
	
	public static String porcentajeEnEstado(Connection conn, String planId, String versionId, String estado) throws SQLException{
		String porcentaje	 	= "Invalido";
		int cantidad			= 0;
		int total				= 0;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(NOMBRE) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND ESTADO = TO_NUMBER(?, '9')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			ps.setString(3, estado);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				cantidad = rs.getInt("CANTIDAD");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|porcentajeEnEstado(Primer query)|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(NOMBRE) AS TOTAL FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				total = rs.getInt("TOTAL");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|porcentajeEnEstado(Segundo query)|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		if(cantidad == 0){
			porcentaje = "0";
		}else if(total == 0){
			porcentaje = "Invalido";
		}else{
			porcentaje = String.valueOf((cantidad/(float)total*100));
			if(porcentaje.indexOf(".") != -1){
				porcentaje = porcentaje.substring(0, porcentaje.indexOf(".")+2);
			}
		}		
		return porcentaje;
	}
	
	public static String getMateriasTotales(Connection conn, String planId, String versionId) throws SQLException{
		String cantidad			= "X";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_ID) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getMateriasTotales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return cantidad;
	}
	
	public static String getMateriasConUnidades(Connection conn, String planId, String versionId) throws SQLException{
		String cantidad			= "X";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_ID) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND (SELECT COUNT(UNIDAD_ID) FROM ENOC.MAPA_NUEVO_UNIDAD" + 
						" WHERE CURSO_ID = ENOC.MAPA_NUEVO_CURSO.CURSO_ID) > 0");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getMateriasConUnidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return cantidad;
	}
	
	public static int getHorasTotales(Connection conn, int cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int hT = 0; 
		try{
			ps = conn.prepareStatement("SELECT HT FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"); 
			ps.setInt(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				hT = rs.getInt("HT");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getHorasTotales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return hT;
	}
	
	public static int getHfd(Connection conn, int cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int hfd = 0; 
		try{
			ps = conn.prepareStatement("SELECT HFD FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"); 
			ps.setInt(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				hfd = rs.getInt("HFD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getHfd|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return hfd;
	}
	
	public static int getIdioma(Connection conn, int cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idioma = 0; 
		try{
			ps = conn.prepareStatement("SELECT IDIOMA FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"); 
			
			ps.setInt(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				idioma = rs.getInt("IDIOMA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getIdioma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return idioma;
	}
	
	public static String getNuevoCursoId(Connection conn, String cursoId, String version ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String curso = "0"; 
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID||TRIM(CLAVE) = ? AND VERSION_ID = ?"); 
			
			ps.setString(1, cursoId);
			ps.setString(2, version);
			
			rs = ps.executeQuery();
			if (rs.next()){
				curso = rs.getString("CURSO_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getNuevoCursoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return curso;
	}
	
	public static String getMaxVersionCurso(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String curso = "0"; 
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(VERSION_ID),1) AS MAXVER FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID||TRIM(CLAVE) = ?"); 
			
			ps.setString(1, cursoId);		
			rs = ps.executeQuery();
			if (rs.next()){
				curso = rs.getString("MAXVER");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getMaxVersionCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return curso;
	}
	
	public static String getMaestros(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String maestros = ""; 
		try{
			ps = conn.prepareStatement("SELECT CODIGO_MAESTRO FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"); 
			ps.setString(1, cursoId);		
			rs = ps.executeQuery();
			if (rs.next()){
				maestros = rs.getString("CODIGO_MAESTRO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maestros;
	}
	
	public ArrayList<MapaNuevoCurso> getListPlan(Connection conn, String planId, String versionId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoCurso> listCurso		= new ArrayList<MapaNuevoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
				" FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE PLAN_ID = '"+planId+"'" +
				" AND VERSION_ID = TO_NUMBER('"+versionId+"', '999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoCurso curso = new MapaNuevoCurso();
				curso.mapeaReg(rs);
				listCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return listCurso;	
	}
	
	public ArrayList<MapaNuevoCurso> getListPlanMaestro(Connection conn, String planId, String versionId, String codigoMaestro, String orden) throws SQLException{
		
		ArrayList<MapaNuevoCurso> listCurso		= new ArrayList<MapaNuevoCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
				" FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE PLAN_ID = '"+planId+"'" +
				" AND VERSION_ID = '"+versionId+"'" +
				" AND CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoCurso curso = new MapaNuevoCurso();
				curso.mapeaReg(rs);
				listCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getListPlanMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return listCurso;	
	}
	
	public static String getNombre(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String curso = "0"; 
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, cursoId);		
			rs = ps.executeQuery();
			if (rs.next()){
				curso = rs.getString("NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return curso;
	}
	
	public static String cortaFrase( String frase, int limite ){
		frase = frase.replaceAll("\n", " ");
		String nuevaFrase = "";
		
		if (frase.length() > limite){
			String[] arreglo = frase.split(" ");
			
			if (limite == 61) System.out.println("Frase:"+frase);
			boolean inicio = true;
			int largo = 0;
			for (int z=0; z < arreglo.length; z++){
				largo = largo + arreglo[z].length();
				if (largo < limite){					
					if (arreglo[z].equals("-")){
						if (largo > arreglo[z].length() || inicio == false)
							nuevaFrase = nuevaFrase + "\n" + arreglo[z];
						else
							nuevaFrase = nuevaFrase + " " + arreglo[z];
						largo = 0;
					}else{
						nuevaFrase = nuevaFrase + " " + arreglo[z];
					}
				}else{		
					nuevaFrase = nuevaFrase + "\n" + arreglo[z];
					largo = 0;
				}
				if (limite == 61) System.out.println("Largo:"+largo+":"+arreglo[z]);
				inicio = false;
			}
		}else{
			nuevaFrase = frase;
		}
		if (limite == 61) System.out.println("Frase:"+nuevaFrase);
		return nuevaFrase;
	}
}