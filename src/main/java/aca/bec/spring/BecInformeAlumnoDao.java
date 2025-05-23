package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecInformeAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecInformeAlumno becInformeAlumno) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BEC_INFORME_ALUMNO"
					+ "(CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO,"
					+ "INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, ID_CCOSTO, USUARIO)"
					+ "VALUES(?, TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'999'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'),TO_NUMBER(?,'99'),"
					+ "TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),?,?, TO_NUMBER(?,'9999999'))";			 
			Object[] parametros = new Object[] {becInformeAlumno.getCodigoPersonal(),becInformeAlumno.getInformeId(),
					becInformeAlumno.getIdEjercicio(),becInformeAlumno.getHoras(),becInformeAlumno.getPuestoId(),
					becInformeAlumno.getTardanzas(),becInformeAlumno.getAusencias(),becInformeAlumno.getFecha(),
					becInformeAlumno.getPuntualidad(),becInformeAlumno.getFuncion(),becInformeAlumno.getTiempo(),
					becInformeAlumno.getIniciativa(),becInformeAlumno.getRelacion(),becInformeAlumno.getRespeto(),
					becInformeAlumno.getProductivo(),becInformeAlumno.getCuidado(),becInformeAlumno.getEstado(),
					becInformeAlumno.getIdCcosto(),becInformeAlumno.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( BecInformeAlumno becInformeAlumno) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_INFORME_ALUMNO"
					+ " SET ID_EJERCICIO = ?, HORAS = TO_NUMBER(?,'999'),"
					+ " PUESTO_ID =  ?, TARDANZAS = TO_NUMBER(?,'99'), AUSENCIAS = TO_NUMBER(?,'99'), FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " PUNTUALIDAD = TO_NUMBER(?,'99'), FUNCION = TO_NUMBER(?,'99'), TIEMPO=TO_NUMBER(?,'99'),"
					+ " INICIATIVA = TO_NUMBER(?,'99'), RELACION = TO_NUMBER(?,'99'), RESPETO = TO_NUMBER(?,'99'),"
					+ " PRODUCTIVO = TO_NUMBER(?,'99'), CUIDADO = TO_NUMBER(?,'99'), ESTADO = ?, ID_CCOSTO = ?, USUARIO= TO_NUMBER(?,'9999999')"
					+ " WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {becInformeAlumno.getIdEjercicio(),becInformeAlumno.getHoras(),becInformeAlumno.getPuestoId(),
					becInformeAlumno.getTardanzas(),becInformeAlumno.getAusencias(),becInformeAlumno.getFecha(),
					becInformeAlumno.getPuntualidad(),becInformeAlumno.getFuncion(),becInformeAlumno.getTiempo(),
					becInformeAlumno.getIniciativa(),becInformeAlumno.getRelacion(),becInformeAlumno.getRespeto(),
					becInformeAlumno.getProductivo(),becInformeAlumno.getCuidado(),becInformeAlumno.getEstado(),
					becInformeAlumno.getIdCcosto(),becInformeAlumno.getUsuario(),becInformeAlumno.getInformeId(),
					becInformeAlumno.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateEstado( String estado, String informeId, String codigoPersonal){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_INFORME_ALUMNO SET ESTADO = ? WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";					
			Object[] parametros = new Object[] {estado,informeId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|updateEstado|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateHoras( String horas, String informeId, String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_INFORME_ALUMNO"
					+ " SET HORAS = ?"
					+ " WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {horas,informeId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|updateEstado|:"+ex);		
		}
		return ok;
	}
		
	public boolean deleteReg( String informeId, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_INFORME_ALUMNO"+ 
					" WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {informeId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
		
	public BecInformeAlumno mapeaRegId( String informeId, String codigoPersonal) {
		BecInformeAlumno becInformeAlumno = new BecInformeAlumno();
		try{
			String comando = "SELECT  * FROM ENOC.BEC_INFORME_ALUMNO  WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {informeId, codigoPersonal};
			becInformeAlumno = enocJdbc.queryForObject(comando, new BecInformeAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|mapeaRegId|:"+ex);
		}
		return becInformeAlumno;
	}
	
	public boolean existeReg( String informeId, String codigoPersonal) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT * FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {informeId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|existeReg|:"+ex);			
		}
		
		return ok;
	}
	
	/*
	 * CUENTA LAS HORAS ACUMULADAS DEL ALUMNO EN EL PUESTO ACTUAL
	 * */
	public String getHorasAcumuladas( String idEjercicio, String idCcosto, String codigoPersonal) {
		String res 				= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(HORAS),0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = ? "+
						" AND CODIGO_PERSONAL||PUESTO_ID IN"+ 
						" (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO " +
						"  WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?" +
						"  AND TO_DATE(now(),'DD/MM/YYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
						" AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {idEjercicio, idEjercicio, idCcosto, codigoPersonal};
			res = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getHorasAcumuladasExcluirActual|:"+ex);
		}		
		return res;
	}
	
	public String getHorasAcumuladasExcluirActual( String idEjercicio, String idCcosto, String codigoPersonal, String informeExcluido) {
		String res 				= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(HORAS),0) AS HORAS"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = ?"+
						" AND CODIGO_PERSONAL||PUESTO_ID IN"+ 
						" (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO " +
						"	WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?" +
						"	AND TO_DATE(now(),'DD/MM/YYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
						" AND CODIGO_PERSONAL = ?"+
						" AND INFORME_ID NOT IN ('"+informeExcluido+"') ";
			Object[] parametros = new Object[] {idEjercicio, idEjercicio, idCcosto, codigoPersonal};
			res = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getHorasAcumuladasExcluirActual|:"+ex);
		}
		
		return res;
	}
	
	/** CUENTA LAS HORAS ACUMULADAS DEL ALUMNO EN EL PUESTO ACTUAL * */
	public String horasEnPuesto( String codigoPersonal, String puestoId) {
		String res 				= "0";
		
		try{
			String comando = "SELECT COALESCE(SUM(HORAS),0) AS HORAS FROM ENOC.BEC_INFORME_ALUMNO"
				+ " WHERE CODIGO_PERSONAL = ?" 
				+ " AND PUESTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			res = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|horasEnPuesto|:"+ex);
		}
		
		return res;
	}
	
	/** CUENTA LAS HORAS ACUMULADAS DEL ALUMNO EN EL PUESTO ACTUAL * */
	public String horasEnPuestoExcluyendo( String codigoPersonal, String puestoId, String informes) {
		String res 				= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(HORAS),0) AS HORAS FROM ENOC.BEC_INFORME_ALUMNO"
				+ " WHERE CODIGO_PERSONAL = ?" 
				+ " AND PUESTO_ID = ?"
				+ " AND INFORME_ID NOT IN ("+informes+")";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			res = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|horasEnPuesto|:"+ex);
		}
		
		return res;
	}
	
	public ArrayList<BecInformeAlumno> getListAll( String orden) {
			
		List<BecInformeAlumno> lista 		= new ArrayList<BecInformeAlumno>();
		
		try{
		String comando = "SELECT * FROM ENOC.BEC_INFORME_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new BecInformeAlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecInformeAlumno>)lista;
	}	
	
	public ArrayList<BecInformeAlumno> getBecInformeAlumnoPorEjercicioIdInformeId( String ejercicioId, String informeId, String orden) {
		
		List<BecInformeAlumno> lista 		= new ArrayList<BecInformeAlumno>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO, "
					+ " INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, VERSION, ID_CCOSTO, USUARIO FROM ENOC.BEC_INFORME_ALUMNO "
					+ "	WHERE ID_EJERCICIO = ? AND INFORME_ID = ? "+orden;	
			lista = enocJdbc.query(comando, new BecInformeAlumnoMapper(), ejercicioId, informeId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getBecInformeAlumnoPorEjercicioIdInformeId|:"+ex);
		}		
		return (ArrayList<BecInformeAlumno>)lista;
	}
	
	public List<BecInformeAlumno> lisInformesPorEjercicio( String ejercicioId, String orden) {		
		List<BecInformeAlumno> lista 		= new ArrayList<BecInformeAlumno>();
		try{
			String comando = " SELECT BIA.CODIGO_PERSONAL, BIA.INFORME_ID, BIA.ID_EJERCICIO, BIA.HORAS, BIA.PUESTO_ID, BIA.TARDANZAS, BIA.AUSENCIAS, BIA.FECHA, BIA.PUNTUALIDAD, BIA.FUNCION, BIA.TIEMPO, "
					+ " BIA.INICIATIVA, BIA.RELACION, BIA.RESPETO, BIA.PRODUCTIVO, BIA.CUIDADO, BIA.ESTADO, BIA.VERSION, BIA.ID_CCOSTO, BIA.USUARIO "
					+ " FROM ENOC.BEC_INFORME_ALUMNO BIA, BEC_INFORME BI"
					+ "	WHERE BIA.ID_EJERCICIO = ? "
					+ " AND BI.INFORME_ID = BIA.INFORME_ID "+orden;	
			lista = enocJdbc.query(comando, new BecInformeAlumnoMapper(), ejercicioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getBecInformeAlumnoPorEjercicioIdInformeId|:"+ex);
		}		
		return (ArrayList<BecInformeAlumno>)lista;
	}
	
	public HashMap<String, BecInformeAlumno> mapInforme( String informeId) {
		HashMap<String, BecInformeAlumno> mapa = new HashMap<String, BecInformeAlumno>();
		List<BecInformeAlumno> lista	 		= new ArrayList<BecInformeAlumno>();	
		try{
			String comando	= " SELECT CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO,"
							+ " INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, VERSION, ID_CCOSTO, USUARIO"
							+ " FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ?";
			lista = enocJdbc.query(comando,new BecInformeAlumnoMapper(), informeId);
			for(BecInformeAlumno informe : lista){				
				mapa.put(informe.getCodigoPersonal(), informe);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapInforme|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, BecInformeAlumno> mapaInformesDepto( String ejercicioId, String deptoId) {
		HashMap<String, BecInformeAlumno> mapa = new HashMap<String, BecInformeAlumno>();
		List<BecInformeAlumno> lista	 		= new ArrayList<BecInformeAlumno>();	
		try{
			String comando	= " SELECT CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, FECHA, PUNTUALIDAD, FUNCION, TIEMPO,"
							+ " INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, VERSION, ID_CCOSTO, USUARIO"
							+ " FROM ENOC.BEC_INFORME_ALUMNO"
							+ " WHERE ID_EJERCICIO = ?"
							+ " AND ID_CCOSTO = ?";
			Object[] parametros = new Object[] {ejercicioId, deptoId};
			lista = enocJdbc.query(comando, new BecInformeAlumnoMapper(),parametros);
			for(BecInformeAlumno informe : lista){				
				mapa.put(informe.getInformeId()+informe.getCodigoPersonal(), informe);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapInforme|:"+ex);
		}		 
		return mapa;
	}
	
	public HashMap<String, String> mapInformeHoras( String informeId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, HORAS AS VALOR FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = "+informeId;
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapInformeHoras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHoras( String idCcosto) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, COALESCE(SUM(HORAS),0) AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO "
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = ?)"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idCcosto);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
					
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHoras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasInformadas( String idCcosto) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, COALESCE(SUM(HORAS),0) AS AS VALOR FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " 	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = ?)"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idCcosto);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.spring.BecInformeAlumnoDao|mapHoras|:"+ex);
		}
		
		return mapa;
	}
	
	
	
	public HashMap<String, String> mapHorasTotales( String idEjercicio, String idCcosto) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||TIPO||PUESTO_ID AS LLAVE, COALESCE(HORAS,0) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?)"+
			    " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO IN('B','I'))";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, idCcosto, idEjercicio);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHoras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> estadoCcosto( String ejercicioId, String informeId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando	= " SELECT ID_CCOSTO||ESTADO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
							+ " FROM ENOC.BEC_INFORME_ALUMNO "
							+ " WHERE INFORME_ID = ? "
							+ " AND CODIGO_PERSONAL||ID_CCOSTO IN (SELECT CODIGO_PERSONAL||ID_CCOSTO FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ?"
							+ " AND (SELECT FECHA_INI FROM ENOC.BEC_INFORME WHERE INFORME_ID = ?) BETWEEN FECHA_INI AND FECHA_FIN)"
							+ " GROUP BY ID_CCOSTO, ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), informeId, ejercicioId, informeId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|estadoCcosto|:"+ex);
		}		
		return mapa;
	}
	
	/* Map del estado de los alumnos registrados en un informe de talleres */
	
	public  HashMap<String, String>mapDeptoEstado( String ejercicioId, String informeId) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT ID_CCOSTO||ESTADO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND INFORME_ID = ?"
					+ " GROUP BY ID_CCOSTO, ESTADO";		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, informeId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapDeptoEstado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> totalPorDepartamento( String ejercicioId, String informeId, String niveles) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = 	" SELECT ID_CCOSTO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"+ 
						" FROM ENOC.BEC_PUESTO_ALUMNO " +
						" WHERE ID_EJERCICIO = ?" +
						" AND NIVEL_ID IN ("+niveles+")" +
						" AND ESTADO = 'C'" +
						" AND (SELECT FECHA_INI FROM ENOC.BEC_INFORME WHERE INFORME_ID = ?) BETWEEN FECHA_INI AND FECHA_FIN"+		 
						" GROUP BY ID_CCOSTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, informeId );	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|totalPorDepartamento|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> totalUniv() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
						+ " FROM ENOC.BEC_PUESTO_ALUMNO"
						+ " WHERE CODIGO_PERSONAL NOT IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE (SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = INSCRITOS.CARRERA_ID) = 1)"
						+ " GROUP BY ID_CCOSTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|totalUniv|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasAlumno( String ejercicioId) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID||INFORME_ID AS LLAVE, COALESCE(HORAS,0) AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE ID_EJERCICIO = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasAlumnoEnPuesto() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID||INFORME_ID AS LLAVE, COALESCE(HORAS,0) AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN"
					+ " 	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " 	WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasAlumnoEnPuesto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasPuestoAlumnoEstado( String codigoPersonal, String puestoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = 	" SELECT INFORME_ID AS LLAVE, COALESCE(HORAS,0)||'@'||ESTADO AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, puestoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasPuestoAlumnoEstado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasPuestoAlumno( String codigoPersonal, String puestoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
	
		try{
			String comando = 	"SELECT INFORME_ID AS LLAVE, COALESCE(HORAS,0) AS VALOR FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasPuestoAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapHorasAlumnoPorMes( String codigoPersonal, String puestoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = 	"SELECT INFORME_ORDEN(INFORME_ID) AS LLAVE, COALESCE(SUM(HORAS),0) AS VALOR"
						+ " FROM ENOC.BEC_INFORME_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PUESTO_ID = ?"
						+ " GROUP BY INFORME_ORDEN(INFORME_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, puestoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasAlumnoPorMes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapHorasPuestoAlumnoMes( String codigoPersonal, String puestoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = 	"SELECT INFORME_ID AS LLAVE, COALESCE(SUM(HORAS),0) AS VALOR"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = ?" +
						" AND PUESTO_ID = ?";		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, puestoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapHorasPuestoAlumnoMes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEstado( String codigoPersonal, String puestoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL||PUESTO_ID||INFORME_ID AS LLAVE, ESTADO AS VALOR"+
						" FROM ENOC.BEC_INFORME_ALUMNO"+
						" WHERE CODIGO_PERSONAL = ?" +
						" AND PUESTO_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),codigoPersonal, puestoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapEstado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEvaluacionAlumno( String codigoAlumno) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL||PUESTO_ID||INFORME_ID AS LLAVE, ((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10 AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapEvaluacionAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEvaluacionPorAlumno( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL||PUESTO_ID||INFORME_ID AS LLAVE, ((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10 AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapEvaluacionAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEvaluacionEnPuesto( String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, SUM(((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10)/COUNT(PUESTO_ID) AS VALOR"
					+ " FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE CODIGO_PERSONAL||PUESTO_ID IN (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapEvaluacionEnPuesto|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEvaluacionAlumnosTotal( String idEjercicio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, SUM(((PUNTUALIDAD+FUNCION+TIEMPO+INICIATIVA+RELACION+RESPETO+PRODUCTIVO+CUIDADO+40)/8)*10)/COUNT(PUESTO_ID) AS VALOR"+
						" FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = ? GROUP BY CODIGO_PERSONAL, PUESTO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapEvaluacionAlumno|:"+ex);
		}
		return mapa;
	}
	
	public List<BecInformeAlumno> getHorasAlumnosInformados( String idEjercicio, String idCcosto, String informeId, String orden) {
		List<BecInformeAlumno> lis = new ArrayList<BecInformeAlumno>();
		String comando					= "";
		
		try{
			comando = " SELECT " +
					" CODIGO_PERSONAL, INFORME_ID, ID_EJERCICIO, HORAS, PUESTO_ID, TARDANZAS, AUSENCIAS, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, " +
					" PUNTUALIDAD, FUNCION, TIEMPO, INICIATIVA, RELACION, RESPETO, PRODUCTIVO, CUIDADO, ESTADO, ID_CCOSTO,USUARIO" +
					" FROM ENOC.BEC_INFORME_ALUMNO " +
					" WHERE ID_EJERCICIO = ?" +
					" AND ID_CCOSTO = ?" +
					" AND INFORME_ID = ? "+orden;			
			lis = enocJdbc.query(comando, new BecInformeAlumnoMapper(), idEjercicio, idCcosto, informeId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|getHorasAlumnosInformados|:"+ex);
		}
		return lis;
	}
	
	public List<BecInformeAlumno> alumnosPromedio( String codigoAlumno, String orden) {
		List<BecInformeAlumno> lis 		= new ArrayList<BecInformeAlumno>();		
		try{
			String comando = " SELECT * FROM ENOC.BEC_INFORME_ALUMNO WHERE CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[] {codigoAlumno};
			lis = enocJdbc.query(comando, new BecInformeAlumnoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|alumnosPromedio|:"+ex);
		}
		
		return lis;
	}
	
	public List<String> alumnosInformes( String idEjercicio, String informes, String estado, String orden) {
		List<String> lis 	= new ArrayList<String>();
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.BEC_INFORME_ALUMNO"
					+ " WHERE INFORME_ID IN ("+informes+")"
					+ " AND ID_EJERCICIO = ? "
					+ " AND ESTADO = ? "+orden;			
			lis = enocJdbc.queryForList(comando,String.class, idEjercicio, estado);	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|alumnosInformes|:"+ex);
		}		
		return lis;
	}
	
	public String saldoAlumnoInforme( String puesto, String codigoPersonal, String informe, String tipo) {
		String res 				= "0";	
		try{
			String comando = " SELECT COALESCE(SUM(IMPORTE),0) FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = ?"					
					+ " AND REFERENCIA LIKE '%"+puesto+"%'"
					+ " AND REFERENCIA LIKE '%"+informe+"%'"
					+ " AND NATURALEZA = ?";			
			res = enocJdbc.queryForObject(comando, String.class, codigoPersonal, tipo);		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|saldoAlumnoInforme|:"+ex);
		}
		return res;
	}	
	
	public String saldoBecaBasica( String puesto, String codigoPersonal, String informe, String tipo, String folio) {
		String res 				= "0";
		String comando			= "";		
		
		try{
			comando = " SELECT COALESCE(SUM(IMPORTE),0) FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = ?"					
					+ " AND REFERENCIA LIKE '%"+puesto+"%'"
					+ " AND REFERENCIA LIKE '%"+informe+"%'"					
					+ " AND REFERENCIA LIKE '%"+folio+"%'"
					+ " AND NATURALEZA = ?";
			
			res = enocJdbc.queryForObject(comando, String.class, codigoPersonal, tipo);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|saldoBecaBasica|:"+ex);
		}
		return res;
	}
	
	public HashMap<String, String> mapPuesto( String informe) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = 	"SELECT CODIGO_PERSONAL||INFORME_ID AS VALOR, PUESTO_ID AS LLAVE FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informe+")";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapPuesto|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalInformes( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = 	"SELECT INFORME_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.BEC_INFORME_ALUMNO GROUP BY INFORME_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeAlumnoDao|mapaTotalInformes|:"+ex);
		}
		return mapa;
	}
	
}
