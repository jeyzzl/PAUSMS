// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.vista.spring.MaestrosDao;

@Component
public class CargaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.catalogo.spring.CatPeriodoDao periodoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	public boolean insertReg( Carga carga ) {		
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA("
				+ " CARGA_ID, NOMBRE_CARGA, F_CREADA, PERIODO, CICLO, F_INICIO,"
				+ " F_FINAL, F_EXTRA, NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, "
				+ "FIN_SERVICIOS, EVALUA, PRIORIDAD) "+
				"VALUES( ?, ?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, "+
				"TO_NUMBER(?,'99'), "+		
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_NUMBER(?,'9999'), "+
				"?,?,TO_NUMBER(?,'99'),"+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?,"+ "TO_NUMBER(?,'99')) ";
			Object[] parametros = new Object[] {
				carga.getCargaId(),carga.getNombreCarga(),carga.getFCreada(), carga.getPeriodo(),carga.getCiclo(),carga.getFInicio(),
				carga.getFFinal(),carga.getFExtra(),carga.getNumCursos(), carga.getEstado(),carga.getTipoCarga(),carga.getSemanas(),
				carga.getFinServicios(), carga.getEvalua(), carga.getPrioridad()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( Carga carga ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA"
				+ " SET NOMBRE_CARGA = ?,"
				+ " F_CREADA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " PERIODO = ?,"
				+ " CICLO = TO_NUMBER(?,'99'),"
				+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
				+ " F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
				+ " F_EXTRA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " NUM_CURSOS = TO_NUMBER(?,'9999'),"
				+ " ESTADO = ?,"
				+ " TIPOCARGA = ?,"
				+ " SEMANAS = TO_NUMBER(?,'99'),"
				+ " FIN_SERVICIOS = TO_DATE(?,'DD/MM/YYYY'),"
				+ " EVALUA = ?,"
				+ " PRIORIDAD = TO_NUMBER(?,'99'),"
				+ " BLOQUEO = TO_NUMBER(?, '9')"
				+ " WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {
					carga.getNombreCarga(),carga.getFCreada(),
					carga.getPeriodo(),carga.getCiclo(),carga.getFInicio(),
					carga.getFFinal(),carga.getFExtra(),carga.getNumCursos(),
					carga.getEstado(),carga.getTipoCarga(),carga.getSemanas(),
					carga.getFinServicios(), carga.getEvalua(), carga.getPrioridad(),carga.getBloqueo(),
					carga.getCargaId()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateFinServicios( String cargaId, String finServicios ) {
		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA "+ 
				" SET FIN_SERVICIOS =  TO_DATE(?,'DD/MM/YYYY') "+				
				" WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {finServicios,cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|updateFinServicios|:"+ex);
		}
		
		return ok;
	}

	public boolean updateFechaInternado(String cargaId, String iniInternado, String finInternado) {
		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA SET INI_INTERNADO = TO_DATE(?,'DD/MM/YYYY'), FIN_INTERNADO = TO_DATE(?,'DD/MM/YYYY') WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {iniInternado,finInternado,cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|updateFechaInternado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String cargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public Carga mapeaRegId(String cargaId ) {		
		Carga carga = new Carga();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT CARGA_ID, NOMBRE_CARGA,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, NUM_CURSOS, ESTADO,"
					+ " TIPOCARGA, SEMANAS, FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN,"
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO,"
					+ " SECUENCIA,"
					+ " BLOQUEO"
					+ " FROM ENOC.CARGA WHERE CARGA_ID = ?";
				carga = enocJdbc.queryForObject(comando, new CargaMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|mapeaRegId|:"+ex);
		}		
		return carga;
	}
	
	public boolean existeReg( String cargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoCurso( String cargaId) {		
		
		int  maximo 		= 0;
		String cursoCargaId = cargaId+"-0001";
		
		try{
			String comando = "SELECT COALESCE(NUM_CURSOS+1,0) AS MAXIMO FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando,Integer.class,parametros);
				if (String.valueOf(maximo).length()==1) cursoCargaId = cargaId + "-" + "000"+String.valueOf(maximo);
				if (String.valueOf(maximo).length()==2) cursoCargaId = cargaId + "-" + "00"+String.valueOf(maximo);
				if (String.valueOf(maximo).length()==3) cursoCargaId = cargaId + "-" + "0"+String.valueOf(maximo);
				if (String.valueOf(maximo).length()==4) cursoCargaId = cargaId + "-" + String.valueOf(maximo);		
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|maximoCurso|:"+ex);
		}
		
		return cursoCargaId;
	}
	
	public boolean updateCurso( String cargaId ) { 
		boolean ok 				= false;		
		
		try{
			String comando = "UPDATE ENOC.CARGA SET NUM_CURSOS = NUM_CURSOS + 1 "+
				"WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|updateCurso|:"+ex);		 
		}
		
		return ok;
	}
	
	public String getNombreCarga( String cargaId ) {
				
		String  nombre	 		= "VACIO";
		
		try{
			String comando = "SELECT NOMBRE_CARGA FROM ENOC.CARGA WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {cargaId};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getNombreCarga|:"+ex);		
		}
		
		return nombre;
	}
	
	public String getCargasPeriodo( String periodo ) {
		
		List<String> lista = new ArrayList<String>();
		String  cargas 		= "";
		int row 			= 1;		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ? ORDER BY CARGA_ID";
			Object[] parametros = new Object[] {periodo};
			lista = enocJdbc.queryForList(comando, String.class, parametros);		
			for(String carga : lista){
				if (row>1) cargas += ",";
				cargas += "'"+carga+"'";
				row++;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargasPeriodo|:"+ex);
		}
		
		return cargas;
	}
	
	public String getCargasActivas( String fecha ) {
		
		List<String> lista = new ArrayList<String>();
		String  cargas 		= "";
		int row 			= 1;
		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA" + 
				" WHERE TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL" +
				" ORDER BY CARGA_ID";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String carga : lista){		
				if (row>1) cargas += ",";
				cargas += "'"+carga+"'";
				row++;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargasActivas|:"+ex);
		}
		
		return cargas;
	}
	
	public String getFinServicios( String cargaId ) {				
		String  fecha	 		= "X";		
		try{
			String comando = "SELECT COALESCE(TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY'),'X') AS FIN_SERVICIOS "+
				" FROM ENOC.CARGA WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {fecha};
			fecha = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getFinServicios|:"+ex);		
		}		
		return fecha;
	}
	
	public String getPeriodoId( String cargaId ) {
		
		String  periodo = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID = ?";
				periodo = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getPeriodoId|:"+ex);		
		}		
		return periodo;
	}
	
	public String getCargasSemestre(String periodo1, String periodo2) {
		
		List<String> lista 	= new ArrayList<String>();
		String  cargas 		= "";
		int row 			= 1;		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA " + 
					" WHERE PERIODO IN(?,?)"+
					" AND SUBSTR(CARGA_ID,5,2) IN ('1A','1C','2A','2B')" +
					" AND F_INICIO >= TO_DATE (TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')" +
					" ORDER BY SUBSTR(CARGA_ID,5,1),CARGA_ID";
			Object[] parametros = new Object[] {periodo1, periodo2};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String carga : lista){
				if (row>1) cargas += ",";
				cargas += "'"+carga+"'";
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargasSemestre|:"+ex);
		}
		
		return cargas;
	}
	
	public String getCargasTetra(String periodo1, String periodo2) {
		
		List<String> lista = new ArrayList<String>();
		String  cargas 		= "";
		int row 			= 1;		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA " + 
					" WHERE PERIODO IN (?,?)" +
					" AND SUBSTR(CARGA_ID,5,2) IN ('3A','3B','3C')" +
					" AND F_INICIO >= TO_DATE (TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')" +
					" ORDER BY SUBSTR(CARGA_ID,5,1),CARGA_ID";
			Object[] parametros = new Object[] {periodo1, periodo2};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String carga : lista){
				if (row>1) cargas += ",";
				cargas += "'"+carga+"'";
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargasTetra|:"+ex);
		}
		
		return cargas;
	}
		
	public String getMejorCarga( String codigoPersonal ) {
		
		String carga 			= "XXXXXX";
		String comando 			= "";		
				
		try{	
			Object[] parametros = new Object[] {codigoPersonal};
			if (codigoPersonal != null && maestrosDao.existeReg(codigoPersonal)){
				comando = " SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID NOT LIKE '2%'";				
				if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
					comando = " SELECT COALESCE(MAX(CARGA_ID),'XXXXXX') AS CARGA_ID FROM ENOC.CARGA_GRUPO " + 
							" WHERE CODIGO_PERSONAL = ? " +
							" AND CARGA_ID NOT LIKE '2%'";
					carga = enocJdbc.queryForObject(comando,String.class,parametros);
					if (carga.equals("XXXXXX")){						
						comando = "SELECT CARGA_ID FROM ENOC.CARGA "+ 
								"WHERE TO_DATE(TO_CHAR(now(),'DD-MM-YY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL "+
								"AND SUBSTR(CARGA_ID,6,1) IN ('A','B','C','D') ORDER BY CARGA_ID";
						carga = enocJdbc.queryForObject(comando,String.class);
					}
				}else{
					carga = "XXXXXX";
				}	
			}else{
				comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT" + 
						" WHERE CODIGO_PERSONAL = ?" +
						" AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL)";
				if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
					comando = "SELECT COALESCE(MIN(DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6))),'XXXXXX') AS CARGA_ID " +
							" FROM ENOC.KRDX_CURSO_ACT" + 
							" WHERE CODIGO_PERSONAL = ?" +
							" AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL)";
					carga = enocJdbc.queryForObject(comando,String.class,parametros);
					if (carga.equals("XXXXXX")){
						comando = "SELECT COALESCE(MAX(SUBSTR(CURSO_CARGA_ID,1,6)),'XXXXX') AS CARGA_ID FROM ENOC.KRDX_CURSO_ACT " + 
								"WHERE CODIGO_PERSONAL = ? " +
								"AND CURSO_CARGA_ID NOT LIKE '2%'";
						carga = enocJdbc.queryForObject(comando,String.class,parametros);
					}
					
				}else{
					carga = "XXXXXX";
				}								
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getMejorCarga|:"+codigoPersonal+":"+ex);		
		}
		
		return carga;
	}
	
	public List<String> getRangoCarga( String cargaId ) {
		
		List<String> lisFechas	= new ArrayList<String>();		
		String comando 			= "";
		try{			
			comando = "SELECT TO_CHAR(F_INICIO,'MM') AS F_INICIO FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lisFechas.add(enocJdbc.queryForObject(comando,String.class,parametros));
			comando = "SELECT TO_CHAR(F_FINAL, 'MM') AS F_FINAL FROM ENOC.CARGA WHERE CARGA_ID = ?";
			lisFechas.add(enocJdbc.queryForObject(comando,String.class,parametros));
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getRangoCarga|:"+ex);		
		}
		
		return lisFechas;
	}
	
	public String getFInicio( String cargaId ) {
				
		String fecha	= "";	
		try{
			
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ? ";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO FROM ENOC.CARGA WHERE CARGA_ID = ? ";
				fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getFInicio|:"+ex);		
		}
		
		return fecha;
	}
	
	public boolean esCargaActiva( String carga ) {		
			
		boolean esActiva 		= false;
		String hoy				= aca.util.Fecha.getHoy();
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ? AND TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL";
			Object[] parametros = new Object[] {carga, hoy};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				esActiva = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargasActivas|:"+ex);
		}
		
		return esActiva;
	}
	
	public String getPeriodo( String cargaId ) {
		
		String periodo 		= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID= ? ";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >=1) {
				comando = "SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID= ? ";
				periodo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getPeriodo|:"+ex);
		}
		
		return periodo;
	}
	
	public String getSemanas( String cargaId ){		
		String semanas 			= "0";		
		try{
			String comando = "SELECT COUNT(SEMANAS) FROM ENOC.CARGA WHERE CARGA_ID= ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT SEMANAS FROM ENOC.CARGA WHERE CARGA_ID= ?";
				semanas = enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getSemanas|:"+ex);
		}		
		return semanas;
	}
	
	public String getCiclo( String cargaId ) {
		
		String ciclo	= "0";		
		try{
			String comando = "SELECT CICLO FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			ciclo = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCiclo|:"+ex);
		}
		
		return ciclo;
	}
	
	public boolean esCargaPasada( String cargaId ) {
		
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ? AND now() >= F_FINAL";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|esCargaPasada|:"+ex);
		}
		
		return ok;
	}
	
	public String evaluaCarga( String cargaId ) {
		
		String	evalua			= "";		
		try{
			String comando = "SELECT EVALUA FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			evalua = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|evaluaCarga|:"+ex);
		}
		
		return evalua;
	}
	
	public List<Carga> getListAll( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		try{
			String comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS,"
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS,"
					+ " EVALUA, PRIORIDAD, ORDEN,"
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA "+orden; 
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListAllActivas( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		try{
			String comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO," 
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
				+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA,"
				+ " PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
				+ " FROM ENOC.CARGA"
				+ " WHERE ESTADO = '1' "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAllActivas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListPeriodoActual( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		String comando			= "";
		String periodo			= "";
		String periodoAnt		= "";
		String periodoSig		= "";
		
		int year1=0, year2=0;
		
		try{
			periodo 	= periodoDao.getPeriodo();			
			year1		= Integer.parseInt(periodo.substring(0,2))-1;
			year2		= Integer.parseInt(periodo.substring(2,4))-1;
			if ( String.valueOf(year1).length()==1) periodoAnt = "0"+String.valueOf(year1); else periodoAnt = String.valueOf(year1);
			if ( String.valueOf(year2).length()==1) periodoAnt += "0"+String.valueOf(year2); else periodoAnt +=String.valueOf(year2);
			
			year1		= Integer.parseInt(periodo.substring(0,2))+1;
			year2		= Integer.parseInt(periodo.substring(2,4))+1;
			if ( String.valueOf(year1).length()==1) periodoSig = "0"+String.valueOf(year1); else periodoSig = String.valueOf(year1);
			if ( String.valueOf(year2).length()==1) periodoSig += "0"+String.valueOf(year2); else periodoSig +=String.valueOf(year2);
			
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS,"
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS,"
					+ " EVALUA, PRIORIDAD, ORDEN,"
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO,"
					+ " SECUENCIA,"
					+ " BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE PERIODO IN ('"+periodoAnt+"','"+periodo+"','"+periodoSig+"')"
					+ " AND ESTADO = '1' "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPeriodoActual|:"+ex);
		}		
		return lista;
	}
	
	public List<Carga> getListPeriodo( String periodo, String orden ) {		
		List<Carga> lista	= new ArrayList<Carga>();	
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS,"
					+ " EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, "
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO,"
					+ " SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE PERIODO = ? "+orden;
			lista = enocJdbc.query(comando, new CargaMapper(), periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPeriodo|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> listPeriodoAndEstado( String periodo, String estado, String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		String comando	= "";		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, "
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE PERIODO = ?"
					+ " AND ESTADO = ? "+orden;
			Object[] parametros = new Object[] {periodo, estado};
			lista = enocJdbc.query(comando, new CargaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|listPeriodoAndEstado|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListAptitud( String orden ) {		
		List<Carga> lista	= new ArrayList<Carga>();
		String comando	= "";		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, "
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, "
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, "
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE CARGA_ID IN (SELECT DISTINCT(CARGAS) FROM ENOC.APFISICA_GRUPO) "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPeriodo|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListCargasActivas( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		String comando	= "";		
		try{
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN,"
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO,"
					+ " SECUENCIA,"
					+ " BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE NOW() BETWEEN F_INICIO AND F_FINAL "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListCargasActivas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListCargasPasadas( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, "
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, "
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, "
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA" 
					+ " WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') >= F_FINAL "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListCargasActivas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListCargas( String cargas ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		String comando	= "";
		
		try{
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, "
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, "
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, "
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE CARGA_ID IN ("+cargas+")";
			lista = enocJdbc.query(comando, new CargaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListCargas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListMaestro( String codigoPersonal ) {		
		List<Carga> lista	= new ArrayList<Carga>();	
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
				+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, "
				+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN,"
				+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
				+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
				+ " FROM ENOC.CARGA " 
				+ " WHERE CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)" 
				+ " ORDER BY ORDEN DESC";
			lista = enocJdbc.query(comando, new CargaMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListMaestro|:"+ex);
		}
		
		return lista;		
	}
	
	public List<Carga> getListNoMaestro( String codigoPersonal ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
				+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS,"
				+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, "
				+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
				+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
				+ " FROM ENOC.CARGA " 
				+ " WHERE CARGA_ID NOT IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)" 
				+ " ORDER BY ORDEN DESC";
			lista = enocJdbc.query(comando, new CargaMapper(), codigoPersonal);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListNoMaestro|:"+ex);
		}
		
		return lista;
	}

	public List<Carga> getListCargasAlumno( String codigoPersonal, String orden) {		
		List<Carga> lisCarga	= new ArrayList<Carga>();
		List<String> lista		= new ArrayList<String>();
		try{
			String comando = 
				" SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL= ? AND ESTADO = 'I'" +
				" UNION" +
				" SELECT DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6)) AS CARGA FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL= ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, codigoPersonal);			
			for(String c : lista){			
				Carga carga = new Carga();
				carga = mapeaRegId(c);
				lisCarga.add(carga);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAlumno|:"+ex);
		}		
		return lisCarga;
	}
	
	public List<Carga> getCargasAlumnoConPatrocinador(String codigoPersonal, String orden) {		
		List<Carga> lista = new ArrayList<Carga>();		
		try{
			String comando =
					"SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, " +
				    "TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, " +
				    "TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, " +
					"PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO" +
					"FROM ENOC.CARGA " +
					"WHERE CARGA_ID IN (SELECT CARGA_ID FROM ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? ) " ;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new CargaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargasAlumnoConPatrocinador|:"+ex);
		}
		return lista;
	}
	
	public ArrayList<Carga> getListAlumno( String codigoPersonal ) {
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		List<String> lista			= new ArrayList<String>();
		String comando	= "";
	
		try{
			comando = "SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUMNO_CURSO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND NOT CARGA_ID = '000000' ORDER BY CARGA_ID DESC";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String c : lista){			
				Carga carga = new Carga();
				carga = mapeaRegId(c);
				lisCarga.add(carga);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAlumno|:"+ex);
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> lisAlumno( String codigoPersonal, String orden ) {
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		List<String> lista			= new ArrayList<String>();
		String comando	= "";
	
		try{
			comando = "SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUMNO_CURSO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND NOT CARGA_ID = '000000' ORDER BY CARGA_ID DESC";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String c : lista){			
				Carga carga = new Carga();
				carga = mapeaRegId(c);
				lisCarga.add(carga);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAlumno|:"+ex);
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListAlumno( String codigoPersonal, String orden) {
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		List<String> lista			= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND NOT CARGA_ID = '000000' "+orden;
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal);
			for(String c : lista){			
				Carga carga = new Carga();
				carga = mapeaRegId(c);
				lisCarga.add(carga);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListAlumno|:"+ex);
		}
	
		return lisCarga;
	}
	
	public List<Carga> lisCargasActivasAlumno( String codigoPersonal, String orden){
		List<Carga> lista	= new ArrayList<Carga>();		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO FROM ENOC.CARGA"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ?)"
					+ " AND TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CargaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisCargasActivasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<Carga> lisTodas( String orden) {		
		List<Carga> lista	= new ArrayList<Carga>();
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS,"
					+ " TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN,"
					+ " TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO,"
					+ " TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA "+orden;
			lista = enocJdbc.query(comando, new CargaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisTodas|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisCargas( String tipo, String letras, String orden) {		
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,1) IN ("+tipo+") AND SUBSTR(CARGA_ID,6,1) IN ("+letras+") "+orden;
			lista = enocJdbc.queryForList(comando, String.class);		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisCargas|:"+ex);
		}		
		return lista;
	}
	
	public List<Carga> lisCargasHoras( String codigoPersonal, String orden) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
	
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA "
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CargaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisCargasHoras|:"+ex);
		}
		
		return lista;
	}
	
	public String getNombre( String cargaId ) {		
		String nombre	= "";		
		try{
			String comando = "SELECT COALESCE(NOMBRE_CARGA,'X') FROM ENOC.CARGA WHERE CARGA_ID = ?";
			nombre = enocJdbc.queryForObject(comando,String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getActual( String fecha ) {
		List<String> lista 		= new ArrayList<String>();
		List<Carga> lisCargas 	= new ArrayList<Carga>();
		String comando			= "";
		String cargaId			= "xxxxxx";		
		try{
			comando = " SELECT COUNT(*) FROM ENOC.CARGA"
					+ " WHERE TO_DATE(?,'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL"
					+ " AND SUBSTR(CARGA_ID,6,1) IN ('A','B','C','D')"
					+ " AND SUBSTR(CARGA_ID,5,1) IN ('1','2','3','4','5')"
					+ " ORDER BY PRIORIDAD,CARGA_ID";
			if (enocJdbc.queryForObject(comando,Integer.class, fecha) >= 1) {
				comando = " SELECT COALESCE(CARGA_ID,'xxxxxx') FROM ENOC.CARGA"
						+ " WHERE TO_DATE(?,'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL"						
						+ " AND SUBSTR(CARGA_ID,6,1) IN ('A','B','C','D')"
						+ " AND SUBSTR(CARGA_ID,5,1) IN ('1','2','3','4','5')"						
						+ " ORDER BY PRIORIDAD,CARGA_ID";
				lista = enocJdbc.queryForList(comando,String.class, fecha);
				cargaId = lista.get(0);
			}else{
				lisCargas 	= this.getListAll("ORDER BY CARGA_ID");
				if (lisCargas.size()>=1) cargaId 	= lisCargas.get(0).getCargaId();
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getAcual|:"+ex+":"+fecha);
		}		
		return cargaId;
	}	
	
	public List<Carga> getListMaestroPeriodo( String codigoPersonal, String periodo) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		try{
			String comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA"
					+ " WHERE PERIODO = ?"
					+ " AND ( CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? OR CODIGO_OTRO = ?) OR"
					+ " 	CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE CODIGO_PERSONAL = ?) )"
					+ " ORDER BY CARGA_ID";
			lista = enocJdbc.query(comando, new CargaMapper(), periodo, codigoPersonal, codigoPersonal, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListMaestroPeriodo|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de cargas donde el maestro imparte clases 
	public List<String> getListMaestroPeriodoCargas( String codigoPersonal, String periodo) {
		
		List<String> lista	= new ArrayList<String>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID FROM ENOC.CARGA "
					+ " WHERE PERIODO = ?"
					+ " AND CARGA_ID IN "
					+ "		(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)"
					+ " ORDER BY CARGA_ID";
			lista = enocJdbc.queryForList(comando, String.class, periodo, codigoPersonal);			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListMaestroPeriodo|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListMaestroPeriodoCargas( String codigoPersonal, String periodo, String cargas) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN "+
					"(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)" + 
				" AND PERIODO = ? " +
				" AND CARGA_ID IN ('"+cargas+"') " +
				" ORDER BY CARGA_ID";
			lista = enocJdbc.query(comando, new CargaMapper(), codigoPersonal, periodo);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListMaestroPeriodoCargas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListMaestroPorCargas( String cargaId) {
		
		List<Carga> lista	= new ArrayList<Carga>();
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN "+
					"("+cargaId+")" +
				" ORDER BY CARGA_ID";
			lista = enocJdbc.query(comando, new CargaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListMaestroPeriodo|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> lisCargasConMaestros( String orden) {
		
		List<Carga> lista	= new ArrayList<Carga>();
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN ( SELECT CARGA_ID FROM ENOC.EMP_HORAS) " + orden;
			lista = enocJdbc.query(comando, new CargaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisCargasConMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> lisCargasDelMaestro(String codigoEmpleado, String orden) {
		List<Carga> lista	= new ArrayList<Carga>();
		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
					"FROM ENOC.CARGA "+ 
					"WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ?)" + orden;
			
			Object[] parametros = new Object[]{codigoEmpleado};
			
			lista = enocJdbc.query(comando, new CargaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|lisCargasDelMaestro|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListPorFecha( String fecha, String orden) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"+
				" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"+
				" FROM ENOC.CARGA"+ 
				" WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN ENOC.CARGA.F_INICIO AND ENOC.CARGA.F_FINAL "+orden;
			lista = enocJdbc.query(comando, new CargaMapper(), fecha);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPorFecha|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getCargaAlumProceso( String orden) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
				" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"+
				" FROM ENOC.CARGA"+ 
				" WHERE CARGA_ID IN" +
				"	(SELECT SUBSTR(CURSO_CARGA_ID,1,6) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('M','C')) "+orden; 
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getCargaAlumProceso|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListCargaActual( String orden ) {  
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"+
					" FROM ENOC.CARGA " + 
					" WHERE PERIODO = TO_CHAR(now(),'YYYY') " +orden;
			lista = enocJdbc.query(comando, new CargaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListCargaActual|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListCargaPeriodo( String periodo, String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA WHERE PERIODO = ? "+orden;
			Object[] parametros = new Object[] {periodo};
			lista = enocJdbc.query(comando, new CargaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListCargaSemPas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Carga> getListSemestre( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
					"FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,2) IN('1A','1B','1C','2A', '2B', '2C') "+orden; 
			lista = enocJdbc.query(comando, new CargaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListSem|:"+ex);
		}
		
		return lista;

	}
	
	
	public List<Carga> getListTetra( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
					"FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,1)= '3' "+orden; 
			lista = enocJdbc.query(comando, new CargaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListSem|:"+ex);
		}
		
		return lista;

	}
	
	public List<Carga> getListPlanCurso( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
					"FROM ENOC.CARGA WHERE CARGA_ID IN "+ 
					"(SELECT DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6)) FROM ENOC.CARGA_GRUPO_PLAN)" +orden;
			lista = enocJdbc.query(comando, new CargaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPlanCurso|:"+ex);
		}
		
		return lista;

	}
	
	public List<Carga> AllInCargaGrupo( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO "+
					"FROM ENOC.CARGA WHERE CARGA_ID IN "+ 
					"(SELECT CARGA_ID FROM ENOC.CARGA_GRUPO)" +orden;
			lista = enocJdbc.query(comando, new CargaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|AllInCargaGrupo|:"+ex);
		}
		
		return lista;

	}
		
	public List<Carga> cargas( String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();
		try{
			String comando = "SELECT * FROM ENOC.CARGA"
					+ " WHERE SUBSTR(CARGA_ID,5,2) = '1A'"
					+ " AND TO_DATE(TO_CHAR(F_INICIO,'YYYY'),'YYYY') <= TO_DATE(TO_CHAR(now(),'YYYY'),'YYYY')-5 ORDER BY CARGA_ID" +orden;
			lista = enocJdbc.query(comando, new CargaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|cargas|:"+ex);
		}
		
		return lista;

	}
	
	public List<String> getPeriodos( String orden ) {
		
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(PERIODO) AS PERIODO FROM ENOC.CARGA WHERE  ESTADO = '1' AND TO_NUMBER(PERIODO,'9999') > 0102 "+orden;
			lista = enocJdbc.queryForList(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getPeriodos|:"+ex);
		}
		
		return lista;

	}
	
	public int numMaestrosPorCarga( String cargaId ) {
		
		int count 					= 0;		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?  AND TRIM (CODIGO_PERSONAL) IS NOT NULL";
			count = enocJdbc.queryForObject(comando, Integer.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|cargas|:"+ex);
		}
		
		return count;

	}
	
	public int [] countMyF( String cargaId ) {
		
		int[] mascFem				= {0, 0};		
		String comando				= "";
		
		try{
			comando = " SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.MAESTROS"
					+ " WHERE GENERO = 'M'"
					+ " AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)";
			mascFem [0] = enocJdbc.queryForObject(comando, Integer.class, cargaId);
			
			comando = " SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.MAESTROS"
					+ " WHERE GENERO = 'F'"
					+ " AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)";
			mascFem [1] = enocJdbc.queryForObject(comando, Integer.class, cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|countMyF|:"+ex);
		}
		
		return mascFem;

	}
	
	public int promEdad( String cargaId ) {		
		int promedio		= 0;		
		try{
			String comando = "SELECT COALESCE(SUM(TO_NUMBER(ENOC.EMP_EDAD(CODIGO_PERSONAL),'9999')*COUNT(CODIGO_PERSONAL)) / SUM(COUNT(CODIGO_PERSONAL)), 0) AS PROMEDIO"+
					" FROM ENOC.MAESTROS"+
					" WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL)"+
					" FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)"+
					" AND (ENOC.EMP_EDAD(CODIGO_PERSONAL)) IS NOT NULL"+
					" GROUP BY ENOC.EMP_EDAD(CODIGO_PERSONAL)"+
					" ORDER BY 1";
			promedio = enocJdbc.queryForObject(comando, Integer.class, cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|promEdad|:"+ex);
		}
		
		return promedio;

	}
	
	public List<Carga> getListPlan( String planId, String orden ) {
		
		List<Carga> lista	= new ArrayList<Carga>();	
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"+
					" FROM ENOC.CARGA"+
					"  WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE ENOC.CURSO_PLAN(CURSO_ID)= ?)) "+orden; 
			lista = enocJdbc.query(comando, new CargaMapper(), planId);		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|getListPlan|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> cargasMaestro( String periodoId, String codigoPersonal, String orden ) {
		
		List<String> lista	= new ArrayList<String>();	
		try{
			String comando = "SELECT CARGA_ID||'@@'||NOMBRE_CARGA FROM ENOC.CARGA WHERE PERIODO = ? AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?) "+orden; 
			lista = enocJdbc.queryForList(comando, String.class, periodoId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|cargasMaestro|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, Carga> mapaCargas( ) {
		
		HashMap<String, Carga> mapa = new HashMap<String, Carga>();
		List<Carga> lista	= new ArrayList<Carga>();		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY') AS FIN_SERVICIOS, EVALUA, "
					+ " PRIORIDAD, ORDEN, TO_CHAR(INI_INTERNADO,'DD/MM/YYYY') AS INI_INTERNADO, TO_CHAR(FIN_INTERNADO,'DD/MM/YYYY') AS FIN_INTERNADO, SECUENCIA, BLOQUEO"
					+ " FROM ENOC.CARGA";			
			lista = enocJdbc.query(comando,new CargaMapper());
			for(Carga carga : lista){
				mapa.put(carga.getCargaId(), carga);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|mapaCargas|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaPrimerDia( ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, (SELECT COALESCE(MIN(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')),'2000/01/01') FROM ENOC.ESTADISTICA WHERE CARGA_ID = ENOC.CARGA.CARGA_ID) AS VALOR FROM ENOC.CARGA";		
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|mapaPrimerDia|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaUltimoDia( ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, (SELECT COALESCE(MAX(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')),'2000/01/01') FROM ENOC.ESTADISTICA WHERE CARGA_ID = ENOC.CARGA.CARGA_ID) AS VALOR FROM ENOC.CARGA";		
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaDao|mapaUltimoDia|:"+ex);
		}

		return mapa;
	}
}