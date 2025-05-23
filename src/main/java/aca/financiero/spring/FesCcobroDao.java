package aca.financiero.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FesCcobroDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FesCcobro cobro) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO MATEO.FES_CCOBRO(MATRICULA,CARGA_ID,BLOQUE,NOMBRE,MODALIDAD_ID,MODALIDAD,"
					+ " TALUMNO_ID,TALUMNO,SEMESTRE,FECHA,"
					+ " FORMAPAGO,RELIGION,NACIONALIDAD,RESIDENCIA,"
					+ " FACULTAD_ID,FACULTAD,CARRERA_ID,CARRERA,PLAN_ID,NOMBRE_PLAN,"
					+ " GRADO,INSCRITO,FOLIO, DORMITORIO) "
					+ " VALUES (?, ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), ?,"
					+ " TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'),"
					+ " ?, ?, ?, ?,"
					+ " ?, ?, ?, ?, ?, ?,"
					+ " TO_NUMBER(?,'99'), ?, ?, ?)";			
			Object[] parametros = new Object[] {
				cobro.getMatricula(), cobro.getCargaId(), cobro.getBloque(), cobro.getNombre(), cobro.getModalidadId(), cobro.getModalidad(),
				cobro.gettAlumnoId(), cobro.gettAlumno(), cobro.getSemestre(), cobro.getFecha(), 
				cobro.getFormaPago(), cobro.getReligion(), cobro.getNacionalidad(), cobro.getResidencia(),
				cobro.getFacultadId(), cobro.getFacultad(), cobro.getCarreraId(), cobro.getCarrera(), cobro.getPlanId(), cobro.getNombrePlan(),
				cobro.getGrado(), cobro.getInscrito(), cobro.getFolio(), cobro.getDormitorio()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch (Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( FesCcobro cobro ) {
		boolean ok = false;		
		try{
			String comando ="UPDATE MATEO.FESCCOBRO"
					+ " SET NOMBRE = ?,"
					+ " MODALIDAD_ID = TO_NUMBER(?,'99')"
					+ " MODALIDAD = ?,"
					+ " TALUMNO_ID = TO_NUMBER(?,'99')"
					+ " TALUMNO = ?"
					+ " SEMESTRE = TO_NUMBER(?,'99')"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY')"
					+ " FACULTAD_ID = ?"
					+ " RELIGION = ?"
					+ " NACIONALIDAD = ?"
					+ " RESIDENCIA = ?"
					+ " FACULTAD_ID = ?"
					+ " FACULTAD = ?"
					+ " CARRERA_ID = ?"
					+ " CARRERA = ?"
					+ " PLAN_ID = ?"
					+ " NOMBRE_PLAN = ?"
					+ " GRADO = TO_NUMBER(?,'99')"
					+ " INSCRITO = ?"
					+ " FOLIO = ?"
					+ " DORMITORIO = ?"
					+ " MATRICULA = ?"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { 
				cobro.getNombre(), cobro.getModalidadId(), cobro.getModalidad(), cobro.gettAlumnoId(), cobro.gettAlumno(),
				cobro.getSemestre(), cobro.getFecha(), cobro.getFormaPago(), cobro.getReligion(), cobro.getNacionalidad(),
				cobro.getResidencia(), cobro.getFacultadId(), cobro.getFacultad(), cobro.getCarreraId(), cobro.getCarrera(),
				cobro.getPlanId(), cobro.getNombrePlan(), cobro.getGrado(), cobro.getInscrito(), cobro.getFolio(), cobro.getDormitorio(),
				cobro.getMatricula(), cobro.getCargaId(), cobro.getBloque()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public FesCcobro mapeaRegId( String codigoPersonal, String cargaId, String bloqueId ){
		
		FesCcobro objeto = new FesCcobro();		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CCOBRO" 
				+ " WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD,"
						+ " TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'YYYY-MM-DD') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID,"
						+ " FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO"
						+ " FROM MATEO.FES_CCOBRO" 
						+ " WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = TO_NUMBER(?,'99')";
				objeto = enocJdbc.queryForObject(comando, new FesCcobroMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	 
	public String getFechaInscAnt( String codigoPersonal, String fecha) {
		
		String fechaInsc 		= "";		
		try{
			String comando ="SELECT COALESCE(TO_CHAR(MAX(FECHA),'DD/MM/YYYY'), '"+fecha+"') FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = ? AND INSCRITO = 'S' AND FECHA != TO_DATE(?, 'DD/MM/YYYY')";			
			Object[] parametros = new Object[] { codigoPersonal, fecha};
			fechaInsc = enocJdbc.queryForObject(comando, String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|getFechaInsc|:"+ex);
		}		
		return fechaInsc;
	}
	
	public String getCarreraAnt( String codigoPersonal, String fecha) {				
		String fechaInsc 		= "";		
		
		try{
			String comando ="SELECT CARRERA_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND INSCRITO = 'S' AND FECHA = TO_DATE(?, 'DD/MM/YYYY')";
			Object[] parametros = new Object[] { codigoPersonal, fecha};			
			fechaInsc = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|getCarreraAnt|:"+ex);
		}
		
		return fechaInsc;
	}	
	
	public List<FesCcobro> lisCalculosAlumno( String codigoPersonal, String orden) {
		
		List<FesCcobro> lista 	= new ArrayList<FesCcobro>();		
		try{			
			String comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD,"
					+ " TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'YYYY-MM-DD') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID,"
					+ " FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO"
					+ " FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = ?"
					+ " AND MATRICULA||CARGA_ID||BLOQUE IN (SELECT DISTINCT(CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?) "
	 				+ orden;
			Object[] parametros = new Object[] { codigoPersonal, codigoPersonal};
			lista = enocJdbc.query(comando, new FesCcobroMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisCalculosAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<FesCcobro> lisBajasPorFecha( String fechaIni, String fechaFin, String orden) {
		
		List<FesCcobro> lista 	= new ArrayList<FesCcobro>();		
		try{			
			String comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD,"
					+ " TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'YYYY-MM-DD') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID,"
					+ " FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO"
					+ " FROM MATEO.FES_CCOBRO"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') AND INSCRITO = 'S'"
					+ " AND MATRICULA||CARGA_ID||BLOQUE NOT IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I')"
	 				+ orden;
			Object[] parametros = new Object[] { fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new FesCcobroMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisCalculosAlumno|:"+ex);
		}	
		return lista;
	}
	
	/* BUSCA LAS CARGAS ACADEMICAS DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public List<String> lisCargasIngreso(String facultadId, String orden){
		List<String> lista 	= new ArrayList<String>();		
		try{			
			String comando = "	SELECT DISTINCT(CARGA_ID) AS CARGA_ID FROM MATEO.FES_CCOBRO MCC"
				+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?)"
				+ "	AND INSCRITO = 'S'"
				+ "	AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)"
				+ "	AND SUBSTR(CARGA_ID,1,2) > '04' "+orden;
			lista = enocJdbc.queryForList(comando, String.class, facultadId);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisCargasIngreso|:"+ex);
		}
		return lista;
	}
	
	/* BUSCA LAS CARRERAS POR LA CARGA ACADEMICA DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public List<String> lisCarrerasPorCarga(String cargaId, String facultadId, String orden){
		List<String> lista 	= new ArrayList<String>();
		try{			
			String comando = "SELECT DISTINCT(CARRERA_ID) AS CARRERA_ID FROM MATEO.FES_CCOBRO MCC"
				+ " WHERE CARGA_ID = ?"
				+ " AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?)"
				+ " AND INSCRITO = 'S'"
				+ " AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)"
				+ " AND SUBSTR(CARGA_ID,1,2) > '04' "+orden;
			lista = enocJdbc.queryForList(comando, String.class, cargaId, facultadId);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisCarrerasPorCarga|:"+ex);
		}
		return lista;
	}
	
	/* BUSCA LOS PLANES DE LAS CARRERAS POR LA CARGA ACADEMICA DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public List<aca.Mapa> lisPlanPorCarga(String cargaId, String facultadId, String orden){
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT DISTINCT(PLAN_ID) AS LLAVE, CARRERA_ID AS VALOR FROM MATEO.FES_CCOBRO MCC"
				+ " WHERE CARGA_ID = ?"
				+ " AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?)"
				+ " AND INSCRITO = 'S'"
				+ " AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)"
				+ " AND SUBSTR(CARGA_ID,1,2) > '04' "+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, facultadId);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisPlanPorCarga|:"+ex);
		}
		return lista;
	}
	
	public List<aca.Mapa> lisPagosPendientes(String codigoAlumno){
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT CARGA_ID||BLOQUE AS LLAVE, SUM(IMPORTE * CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END) AS VALOR"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE MATRICULA = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND APLICA_EN = 'T'"
					+ " GROUP BY CARGA_ID, BLOQUE ORDER BY CARGA_ID, BLOQUE";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|lisPagosPendientes|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, FesCcobro> mapInscritosCargas( String cargas ){
		HashMap<String, FesCcobro> mapa		= new HashMap<String, FesCcobro>();
		List<FesCcobro> lista 				= new ArrayList<FesCcobro>();
		try{			
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE CARGA_ID IN('"+cargas+"')"+
 					" AND INSCRITO = 'S'";
			
			lista = enocJdbc.query(comando, new FesCcobroMapper());
			for (FesCcobro cobro : lista) {
				mapa.put(cobro.getMatricula()+cobro.getCargaId()+cobro.getBloque(), cobro);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|getMapInscritosCargas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, FesCcobro> mapaAlumnosEnLinea( ){
		HashMap<String, FesCcobro> mapa		= new HashMap<String, FesCcobro>();
		List<FesCcobro> lista 				= new ArrayList<FesCcobro>();
		try{			
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE CARGA_ID IN(SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')";			
			lista = enocJdbc.query(comando, new FesCcobroMapper());
			for (FesCcobro cobro : lista) {
				mapa.put(cobro.getMatricula()+cobro.getCargaId()+cobro.getBloque(), cobro);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|getMapInscritosCargas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCalculos( String informeId ){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA AS LLAVE, FOLIO AS VALOR FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN "
					+ " 	(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ALPUESTO_PUESTO_ID IN"
					+ " 		(SELECT PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ?))";
			Object[] parametros = new Object[] { informeId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapaCalculos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaMovimientos( String fechaIni, String fechaFin ){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT COBRO.CARRERA_ID||MOV.TIPOMOV AS LLAVE, SUM(COALESCE(IMPORTE,0)) AS VALOR FROM MATEO.FES_CCOBRO COBRO, MATEO.FES_CC_MOVIMIENTO MOV"
					+ " WHERE COBRO.FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND COBRO.INSCRITO='S'"
					+ " AND MOV.MATRICULA = COBRO.MATRICULA"
					+ " AND MOV.CARGA_ID = COBRO.CARGA_ID"
					+ " AND MOV.BLOQUE = COBRO.BLOQUE"					
					+ " GROUP BY COBRO.CARRERA_ID||MOV.TIPOMOV";
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapaMovimientos|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, String> mapaResidencia(){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, RESIDENCIA AS VALOR"
					+ " FROM MATEO.FES_CCOBRO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE SYSDATE BETWEEN F_INICIO AND F_FINAL)"
					+ " AND INSCRITO = 'S'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapaResidencia|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String,String> mapaMovimientosAlumno(String fechaIni, String fechaFin){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT COBRO.MATRICULA||COBRO.CARGA_ID||COBRO.BLOQUE||MOV.TIPOMOV AS LLAVE, SUM(COALESCE(IMPORTE,0)) AS VALOR FROM MATEO.FES_CCOBRO COBRO, MATEO.FES_CC_MOVIMIENTO MOV"
					+ " WHERE COBRO.FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND COBRO.INSCRITO='S'"
					+ " AND MOV.MATRICULA = COBRO.MATRICULA"
					+ " AND MOV.CARGA_ID = COBRO.CARGA_ID"
					+ " AND MOV.BLOQUE = COBRO.BLOQUE"					
					+ " GROUP BY COBRO.MATRICULA||COBRO.CARGA_ID||COBRO.BLOQUE||MOV.TIPOMOV";
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaMovimientosAlumno|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosAlumno(String cargaId, String estado){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA||BLOQUE AS LLAVE, SUM(CREDITOS) AS VALOR FROM MATEO.FES_CC_MATERIA" 
					+ " WHERE CARGA_ID = ?"
					+ " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = ?)"
					+ " GROUP BY MATRICULA||BLOQUE";
			Object[] parametros = new Object[] { cargaId, cargaId, estado };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaCreditosAlumno|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosAlumno(String codigoPersonal){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT CARGA_ID||BLOQUE AS LLAVE, SUM(CREDITOS) AS VALOR FROM MATEO.FES_CC_MATERIA" 
					+ " WHERE MATRICULA = ?"					
					+ " GROUP BY CARGA_ID||BLOQUE";
			Object[] parametros = new Object[] { codigoPersonal };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaCreditosAlumno|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaModalidades(String cargaId, String planId){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA AS LLAVE, MODALIDAD_ID AS VALOR FROM MATEO.FES_CCOBRO"
					+ " WHERE CARGA_ID = ? AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE PLAN_ID = ?)";
			Object[] parametros = new Object[] {cargaId,planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaModalidades|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, FesCcobro> mapaFesCobroPorMatricula(String codigoPersonal){
		HashMap<String, FesCcobro> mapa		= new HashMap<String, FesCcobro>();
		List<FesCcobro> lista 				= new ArrayList<FesCcobro>();
		try{			
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE MATRICULA = ? ";
			
			lista = enocJdbc.query(comando, new FesCcobroMapper(), new Object[] {codigoPersonal});
			for (FesCcobro cobro : lista) {
				mapa.put(cobro.getCargaId()+cobro.getBloque(), cobro);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapaFesCobroPorMatricula|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, FesCcobro> mapaCalculosPorCarga(String cargaId){
		HashMap<String, FesCcobro> mapa		= new HashMap<String, FesCcobro>();
		List<FesCcobro> lista 				= new ArrayList<FesCcobro>();
		try{			
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE CARGA_ID = ? ";			
			lista = enocJdbc.query(comando, new FesCcobroMapper(), new Object[] {cargaId});
			for (FesCcobro cobro : lista) {
				mapa.put(cobro.getMatricula()+cobro.getBloque(), cobro);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcobroDao|mapaCalculosPorCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPagoInicial(String codigoPersonal){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT CARGA_ID||BLOQUE AS LLAVE, SUM(IMPORTE * CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END) AS VALOR"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE MATRICULA = ? AND APLICA_EN = 'T'"
					+ " GROUP BY CARGA_ID, BLOQUE";
			Object[] parametros = new Object[] { codigoPersonal };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaPagoInicial|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPagosEnCarga(String cargaId){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA||BLOQUE AS LLAVE, SUM(IMPORTE*CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END) AS VALOR"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE CARGA_ID = ?"
					+ " AND APLICA_EN = 'T'"
					+ " GROUP BY MATRICULA||BLOQUE";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaPagosEnCarga|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPagoInicial(){
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, SUM(IMPORTE*CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END) AS VALOR"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE CARGA_ID IN( SELECT CARGA_ID FROM CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND APLICA_EN = 'T'"
					+ " GROUP BY MATRICULA||CARGA_ID||BLOQUE";
			//Object[] parametros = new Object[] { codigoPersonal };
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCCobroDao|mapaPagoInicial|:"+ex);
		}		
		return mapa;
	}

}