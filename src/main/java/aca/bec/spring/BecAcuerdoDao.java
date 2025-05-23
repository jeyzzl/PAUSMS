package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecAcuerdoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecAcuerdo becAcuerdo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_ACUERDO"+ 
				"(FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO )"+
				" VALUES( TO_NUMBER(?, '999'), ?, ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?,'999999.99'), " +
				" TO_NUMBER(?,'999999.99'), TO_NUMBER(?,'999999.99'), ?, TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?,'99999999.99'), ?, ?, TO_NUMBER(?, '9999'), ?, ? )";
			Object[] parametros = new Object[] {becAcuerdo.getFolio(), becAcuerdo.getCodigoPersonal(), becAcuerdo.getIdEjercicio(),
					becAcuerdo.getTipo(), becAcuerdo.getFecha(), becAcuerdo.getMatricula(), becAcuerdo.getEnsenanza(), becAcuerdo.getInternado(),
					becAcuerdo.getValor(), becAcuerdo.getVigencia(), becAcuerdo.getEstado(), becAcuerdo.getPromesa(), becAcuerdo.getIdCcosto(),
					becAcuerdo.getPuestoId(), becAcuerdo.getHoras(), becAcuerdo.getTipoadicional(), becAcuerdo.getUsuario()};			
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( BecAcuerdo becAcuerdo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO"+ 
				" SET ID_EJERCICIO = ?, " +
				" TIPO = TO_NUMBER(?, '99'), FECHA = TO_DATE(?, 'DD/MM/YYYY'), MATRICULA = TO_NUMBER(?,'999999.99'), ENSENANZA = TO_NUMBER(?,'999999.99')," +
				" INTERNADO = TO_NUMBER(?,'999999.99'), VALOR = ?, VIGENCIA = TO_DATE(?, 'DD/MM/YYYY'), ESTADO = ?, PROMESA = TO_NUMBER(?,'99999999.99')," +
				" ID_CCOSTO = ?, PUESTO_ID = ?, HORAS = TO_NUMBER(?, '9999'), TIPOADICIONAL = ?, USUARIO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {becAcuerdo.getIdEjercicio(), becAcuerdo.getTipo(), becAcuerdo.getFecha(), becAcuerdo.getMatricula(),
					becAcuerdo.getEnsenanza(), becAcuerdo.getInternado(), becAcuerdo.getValor(), becAcuerdo.getVigencia(),
					becAcuerdo.getEstado(), becAcuerdo.getPromesa(), becAcuerdo.getIdCcosto(), becAcuerdo.getPuestoId(),
					becAcuerdo.getHoras(), becAcuerdo.getTipoadicional(), becAcuerdo.getUsuario(), becAcuerdo.getFolio(), becAcuerdo.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateHoras( String horas, String folio, String codigoPersonal) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO SET  HORAS = ? WHERE FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {horas, folio, codigoPersonal};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updateHoras|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateHorasDelPuesto( String codigoPersonal, String puestoId, String horas) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO SET HORAS = ? WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";						
			if (enocJdbc.update(comando, horas, codigoPersonal, puestoId)==1) {
				ok = true;			
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updateHorasDelPuesto|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEstadoDelAcuerdo( String codigoAlumno, String puestoId, String estado) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO SET ESTADO = ? WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";						
			if (enocJdbc.update(comando, estado, codigoAlumno, puestoId)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updateEstadoDelAcuerdo|:"+ex);
		}		
		return ok;
	}
	
	public boolean updatePuestoId( String puestoId, String folio, String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO SET PUESTO_ID = ? WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {puestoId, folio, codigoPersonal};			
			if (enocJdbc.update(comando, parametros)==1) {
				ok = true;			
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updatePuestoId|:"+ex);		
		}		
		return ok;
	}
	
	
	public boolean updateCcosto( String idCosto, String folio, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_ACUERDO"+ 
				" SET  ID_CCOSTO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {idCosto, folio, codigoPersonal};
			
			if (enocJdbc.update(comando, parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|updateCcosto|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String folio, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_ACUERDO WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {folio, codigoPersonal};			
			if (enocJdbc.update(comando, parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public BecAcuerdo mapeaRegId( String folio, String codigoPersonal) {
		BecAcuerdo becAcuerdo = new BecAcuerdo();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, "
						+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, "
						+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, "
						+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
						+ " FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
				becAcuerdo = enocJdbc.queryForObject(comando, new BecAcuerdoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapeaRegId|:"+ex);
		}
		
		return becAcuerdo;
	}
	
	public BecAcuerdo mapeaRegPuestoId( String folio, String codigoPersonal, String puestoId) {
		BecAcuerdo becAcuerdo = new BecAcuerdo();

		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, "
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, "
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
					+ " FROM ENOC.BEC_ACUERDO WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? AND PUESTO_ID = ?"; 
			Object[] parametros = new Object[] {folio, codigoPersonal, puestoId};
			becAcuerdo = enocJdbc.queryForObject(comando, new BecAcuerdoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapeaRegPuestoId|:"+ex);
		}
		
		return becAcuerdo;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeTipo( String codigoPersonal, String idEjercicio, String tipo, String idCcosto, String puestoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND TIPO = ? AND ID_CCOSTO = ? AND PUESTO_ID = ? ";
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio, tipo, idCcosto, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String maximoReg( String codigoPersonal) {		
 		String maximo = "1"; 		
 		try{
 			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class,parametros);
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public String getFolio( String codigoPersonal, String idEjercicio, String tipo, String idCcosto, String puestoId) {		
		String folio			= "0";		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND ID_CCOSTO = ? AND PUESTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio, tipo, idCcosto, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT COALESCE(FOLIO,0) AS FOLIO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND ID_CCOSTO = ? AND PUESTO_ID = ? ";
				folio = enocJdbc.queryForObject(comando, String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getFolio|:"+ex);
		}
		
		return folio;
	}
 	
	public String getBecaBasicaUsado( String idEjercicio, String tipo, String tipoAdicional, String idCcosto, String fechaPuesto, String periodoId ) {
		String usado			= "0";
		
		try{
			String comando = "SELECT COALESCE(SUM(ENSENANZA*HORAS), 0) AS USADO "
					+ " FROM ENOC.BEC_ACUERDO A WHERE TIPO = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ?"
					+ " AND (SELECT COUNT(*) FROM ENOC.BEC_ACUERDO WHERE PUESTO_ID = A.PUESTO_ID AND TIPO NOT IN("+tipo+","+tipoAdicional+") ) = 0"
					+ " AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI "
					+ " AND FECHA_FIN AND ID_CCOSTO = ? AND TIPO = 'B' AND PERIODO_ID = ?)";
			Object[] parametros = new Object[] {tipo, idEjercicio, idCcosto, idEjercicio, fechaPuesto, idCcosto, periodoId};
			usado = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getBecaBasicaUsado|:"+ex);
		}
		
		return usado;
	}
	
	public String getBecaInstitucionalUsado( String idEjercicio, String tipo, String idCcosto, String fechaPuesto, String periodoId ) {
		String usado			= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(ENSENANZA*HORAS), 0) AS USADO"
					+ " FROM ENOC.BEC_ACUERDO A"
					+ " WHERE TIPO = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ?"					
					+ " AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND TO_DATE(?, 'DD/MM/YYYY')"
					+ " BETWEEN FECHA_INI AND FECHA_FIN AND ID_CCOSTO = ? AND TIPO = 'I' AND PERIODO_ID = ? )";
			Object[] parametros = new Object[] {tipo,idEjercicio,idCcosto,idEjercicio,fechaPuesto,idCcosto,periodoId};	
			usado = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getBecaBasicaUsado|:"+ex);
		}
		
		return usado;
	}
	
	public String getBecaAdicionalUsado( String idEjercicio, String tipo, String idCcosto, String fechaPuesto, String periodoId ) {
		String usado			= "0";
			
		try{
			String comando = " SELECT " +
				" COALESCE( SUM( " +
								" CASE" +
								" WHEN TIPOADICIONAL = 'D' THEN ENSENANZA" +
								" WHEN TIPOADICIONAL != 'D' THEN ((ENSENANZA/100)*PROMESA)" +
								" END " +
				" ), 0) AS USADO" +
				" FROM ENOC.BEC_ACUERDO WHERE TIPO = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ?" +
				" AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_CCOSTO = ? AND PERIODO_ID = ? )";
			usado = enocJdbc.queryForObject(comando, String.class, tipo, idEjercicio, idCcosto, idEjercicio, fechaPuesto,idCcosto,periodoId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getBecaBasicaUsado|:"+ex);
		}
		
		return usado;
	}
	
	public boolean existeAcuerdoMateo( String codigoPersonal, String folio ) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ? AND ACUERDO_FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|existeAcuerdoMateo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAcuerdoMateo( String codigoPersonal, String folio, String puestoId ) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ? AND ACUERDO_FOLIO = ? AND ALPUESTO_PUESTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, folio, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|existeAcuerdoMateo|:"+ex);
		}
		
		return ok;
	}
	
	public int getNumAcuerdosAsociados( String idEjercicio, String codigoPersonal, String puestoId, String tipos) {
		
		int total = 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?"
					+ " AND TIPO NOT IN("+tipos+") "
					+ " AND ESTADO IN('A','I') ";
			if (enocJdbc.queryForObject(comando, Integer.class, idEjercicio, codigoPersonal, puestoId) >= 1) {
				total = enocJdbc.queryForObject(comando, Integer.class, idEjercicio, codigoPersonal, puestoId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getNumAcuerdosAsociados|:"+ex);
		}
		
		return total;
	}
	
	public String getAcuerdoBasico( String ejercicioId, String codigo, String puesto) {		
		String tipo				= "0";		
		try{
			String comando = "SELECT TIPO FROM ENOC.BEC_ACUERDO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?"
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO"
					+ " WHERE ID_EJERCICIO = ? AND ACUERDO IN ('B','I'))";		
			tipo = enocJdbc.queryForObject(comando, String.class, codigo, puesto, ejercicioId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdoBasico|:"+ex);
		}
		
		return tipo;
	}
	
	public String getFolioAcuerdoBasico( String ejercicioId, String codigo, String puesto) {		
		String folio = "0";					
		try{
			String comando = "SELECT FOLIO FROM ENOC.BEC_ACUERDO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?"
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO"
					+ " WHERE ID_EJERCICIO = ? AND ACUERDO IN ('B','I'))";			
			if (enocJdbc.queryForObject(comando, Integer.class, codigo, puesto, ejercicioId) >= 1) {
				folio = enocJdbc.queryForObject(comando, String.class, codigo, puesto, ejercicioId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getFolioAcuerdoBasico|:"+ex);
		}
		
		return folio;
	}
	
	public String getPromesaAlumno( String codigo, String puesto) {	
		
		String tipo	= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(PROMESA), 0) AS PROMESA FROM ENOC.BEC_ACUERDO"
					+ " WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";
			tipo = enocJdbc.queryForObject(comando, String.class, codigo, puesto);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getPromesaAlumno|:"+ex);
		}		
		return tipo;
	}
	
	public String getPorMatricula( String codigo, String puesto) {	
		
		String tipo				= "0";		
		try{
			String comando = "SELECT COALESCE(SUM(MATRICULA), 0) AS TOTAL FROM ENOC.BEC_ACUERDO"
					+ " WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";
			tipo = enocJdbc.queryForObject(comando, String.class, codigo, puesto);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getPorMatricula|:"+ex);
		}
		
		return tipo;
	}
	
	
	public ArrayList<BecAcuerdo> getListAll( String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getList( String idEjercicio, String fecha, String estado, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, COALESCE(PUESTO_ID,'-') AS PUESTO_ID,"
					+ " HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND ESTADO = ? AND CODIGO_PERSONAL"
					+ " IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN) " +orden;			
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, estado, fecha);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getListPuestoId( String idEjercicio, String puestoId, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO WHERE PUESTO_ID = ? AND ID_EJERCICIO = ? "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), puestoId, idEjercicio);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListPuestoId|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getListTipo( String idEjercicio, String fecha, String tipo, String estado, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();	
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND ESTADO = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE TO_DATE('?','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"
					+ " AND TIPO = TO_NUMBER(?,'99') "+orden;
			Object[] parametros = new Object[] {idEjercicio, estado, fecha, tipo };
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListTipo|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getListTipo( String idEjercicio, String tipo, String orden) {		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND TIPO = TO_NUMBER(?,'99') "+orden;
			Object[] parametros = new Object[] {idEjercicio, tipo};
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListTipo|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getListTipoyEstado( String idEjercicio, String tipo, String estado, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND TIPO = TO_NUMBER(?,'99')"
					+ " AND ESTADO = ? "+orden;
			Object[] parametros = new Object[] {idEjercicio, tipo, estado};
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListTipoyEstado|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> lisTipoyEstado( String idEjercicio, String tipo, String estado, String vigente, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND TIPO = TO_NUMBER(?,'99')"
					+ " AND ESTADO = ?";
			if (vigente.equals("S")) 
				comando += " AND TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') <=  ENOC.BEC_ACUERDO.VIGENCIA "+orden;
			else
				comando += " "+orden;
					
			Object[] parametros = new Object[] {idEjercicio, tipo, estado};
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListTipoyEstado|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumno( String idEjercicio, String codigoPersonal, String tipos, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();		
		try{			
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ? "
					+ " AND TIPO NOT IN("+tipos+") AND ESTADO IN ('A','I') "+orden;	
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumno|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosVigentesAlumno( String idEjercicio, String codigoPersonal, String tipos, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, "
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, "
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO "
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND CODIGO_PERSONAL = ? "
					+ " AND now() <= VIGENCIA"
					+ " AND TIPO NOT IN("+tipos+") "
					+ " AND ESTADO IN ('A','I') "+orden;	
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosVigentesAlumno|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
		
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumno( String idEjercicio, String codigoPersonal, String orden) {
		
		List<BecAcuerdo> lista		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? "
					+ "AND CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumno|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumnoVigentes( String idEjercicio, String codigoPersonal, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ?"
					+ " AND (SELECT FECHA_INI FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = BEC_ACUERDO.PUESTO_ID) <= now()"
					+ " AND (SELECT FECHA_FIN FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = BEC_ACUERDO.PUESTO_ID) >= now() "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumnoVigentes|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getContratoAlumnosVigentes(String tipo, String idEjercicio, String orden) {
		
		List<BecAcuerdo> lista 	= new ArrayList<BecAcuerdo>();	
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO WHERE PUESTO_ID IN "
					+ "(SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE "
					+ "TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " BETWEEN FECHA_INI AND FECHA_FIN AND ID_EJERCICIO = ? "
					+ "AND PUESTO_ID IN (SELECT PUESTO_ID FROM ENOC.BEC_ACUERDO WHERE TIPO = ?)) "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, tipo);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getContratoAlumnosVigentes|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumnoPuesto( String idEjercicio, String codigoPersonal, String puestoId, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, "
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
					+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
					+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND CODIGO_PERSONAL = ? "
					+ " AND PUESTO_ID = ? "+orden;		
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, codigoPersonal, puestoId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumnoPuesto|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public BecAcuerdo getAcuerdoAlumno( String codigo, String tipo, String puesto) {
		BecAcuerdo obj 					= new BecAcuerdo();		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND TIPO = ? AND PUESTO_ID = ?";			
			obj = enocJdbc.queryForObject(comando, new BecAcuerdoMapper(), codigo, tipo, puesto);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdoAlumno|:"+ex);
		}		
		return obj;
	}	
	
	public String getCantidadAcuerdosFinanciero( String idEjercicio, String codigoPersonal, String puestoId, String tipoId, String orden) {
		
		String cantidad 				= "0";		
		try{
			String comando = "SELECT COUNT(*) AS CONT FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " AND MATRICULA = ? "
					+ " AND ALPUESTO_PUESTO_ID = ? "
					+ " AND TIPO_ID = ? "+orden;	
			cantidad = enocJdbc.queryForObject(comando, String.class, idEjercicio, codigoPersonal, puestoId, tipoId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getCantidadAcuerdosFinanciero|:"+ex);
		}
		
		return cantidad;
	}
	
	public ArrayList<String> getAcuerdosAlumnoFinanciero( String idEjercicio, String codigoPersonal, String puestoId, String orden) {
		
		List<String> lista 			= new ArrayList<String>();		
		// EL CAMPPO: TOTAL_BECA_ADIC concentra la beca adicional disminuyendo la beca basica en los acuerdos
		try{
			String comando = "SELECT TIPO_NOMBRE||'@@'||CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END||'@@'||TIPO_ID AS ACUERDO" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS" +
					" WHERE ACUERDO_EJERCICIO_ID = ?  " +
					" AND MATRICULA = ? " +
					" AND ALPUESTO_PUESTO_ID = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, idEjercicio, codigoPersonal, puestoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumnoFinanciero|:"+ex);
		}
		
		return (ArrayList<String>)lista;
	}
	
	public ArrayList<String> getAcuerdoDiscrepanciasEnHoras( String idEjercicio, String codigoPersonal, String puestoId, String orden) {
		
		List<String> lista 			= new ArrayList<String>();		
		try{
			String comando = "SELECT TIPO_NOMBRE||'@@'||CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END||'@@'||TIPO_ID AS ACUERDO" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?  " +
					" AND MATRICULA = ? " +
					" AND ALPUESTO_PUESTO_ID = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class,idEjercicio, codigoPersonal, puestoId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getAcuerdosAlumnoFinanciero|:"+ex);
		}
		
		return (ArrayList<String>)lista;
	}
	
	public String getHorasConv( String codigoPersonal, String puestoId) {
		
		String horas 				= "0";		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.BEC_ACUERDO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?"
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('B','I'))";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal, puestoId) >= 1){
				comando = " SELECT COALESCE(HORAS,0) AS HORAS FROM ENOC.BEC_ACUERDO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PUESTO_ID = ?"
						+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('B','I'))";
				horas = enocJdbc.queryForObject(comando, String.class, codigoPersonal, puestoId);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getHorasConv|:"+ex);
		}
		
		return horas;
	}
	
	
	public ArrayList<BecAcuerdo> getDiscrepanciasEnHoras( String idEjercicio, String orden) {
		
		List<BecAcuerdo> lista 		= new ArrayList<BecAcuerdo>();
		
		/* LAS HORAS DE LA BECA BASICA SE PUSIERON EN EL CAMPO "VALOR" */
		/* EL TIPO DE LA BECA BASICA SE PUSO EN EL CAMPO DE "TIPOADICIONAL" */
		try{
			String comando = " SELECT "
							+ "	FOLIO, "
							+ "	CODIGO_PERSONAL, "
							+ "	ID_EJERCICIO, "
							+ "	TIPO, "
							+ "	TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
							+ "	MATRICULA, "
							+ "	ENSENANZA, "
							+ "	INTERNADO, "
							+ " (SELECT HORAS FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) AS VALOR, "
							+ " TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, "
							+ "	ESTADO, "
							+ " PROMESA, "
							+ "	ID_CCOSTO, "
							+ "	PUESTO_ID, "
							+ "	HORAS, "
							+ "(SELECT TIPO FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) AS TIPOADICIONAL, "
							+ " USUARIO"
					+ " FROM ENOC.BEC_ACUERDO BA WHERE ID_EJERCICIO = ? AND TIPO NOT IN (1,3)"
					+ " AND HORAS != (SELECT HORAS FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) "+orden;
			
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio, idEjercicio, idEjercicio, idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getDiscrepanciasEnHoras|:"+ex);
		}
		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> acuerdosInformesContabilizados( String idEjercicio, String informes, String orden) {
		
		List<BecAcuerdo> lista		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND CODIGO_PERSONAL||PUESTO_ID IN "
					+ " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informes+") AND ESTADO = '3')"
					+ " AND ESTADO = 'I'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID||FOLIO IN"
					+ " (SELECT MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS) "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListAll|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> lisAcuerdosEnInforme( String idEjercicio, String informes, String orden) {
		
		List<BecAcuerdo> lista		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND CODIGO_PERSONAL||PUESTO_ID IN "
					+ " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informes+") AND ESTADO IN ('2','3'))"
					+ " AND ESTADO = 'I'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID||FOLIO IN"
					+ " (SELECT MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS) "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|getListAll|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public ArrayList<BecAcuerdo> lisAsociados( String idEjercicio, String tipos, String orden) {
		List<BecAcuerdo> lista		= new ArrayList<BecAcuerdo>();		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO,"
				+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA,"
				+ " INTERNADO, VALOR, TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA,"
				+ " ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO"
				+ " FROM ENOC.BEC_ACUERDO "
				+ " WHERE ID_EJERCICIO = ?"
				+ " AND PUESTO_ID != '0'"
				+ " AND TIPO NOT IN ("+tipos+") "
				+ " AND ESTADO IN ('A','I') "+orden;
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|lisAsociados|:"+ex);
		}		
		return (ArrayList<BecAcuerdo>)lista;
	}
	
	public HashMap<String, String> mapPorMatricula( String ejercicioId, String tipo) {
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, SUM(MATRICULA) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = ?)"
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = ?)"
					+ " AND PUESTO_ID IS NOT NULL "
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, ejercicioId, ejercicioId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapPorMatricula|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAcuerdosFinancieros( String ejercicioId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID||TIPO_ID AS LLAVE, COUNT(MATRICULA) AS VALOR "
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " GROUP BY MATRICULA,ALPUESTO_PUESTO_ID,TIPO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAcuerdosFinancieros|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapPorEnsenanza( String ejercicioId, String tipo) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();	
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{			
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, SUM(ENSENANZA) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = ?)"					
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = ?)"
					+ " AND PUESTO_ID IS NOT NULL "
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, ejercicioId, ejercicioId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapPorEnsenanza|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapPorInternado( String ejercicioId, String tipo) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = " SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, SUM(INTERNADO) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = ?)"					
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = ?)"
					+ " AND PUESTO_ID IS NOT NULL"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),ejercicioId,ejercicioId,ejercicioId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapPorInternado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalAcuerdos( String ejercicioId){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT TIPO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? GROUP BY TIPO";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaTotalAcuerdos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAcuerdosEnEjercicio( String ejercicioId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT MATRICULA||ACUERDO_FOLIO||ALPUESTO_PUESTO_ID AS LLAVE, ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO AS VALOR FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = ?";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAcuerdosEnEjercicio|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAcuerdosEnDepto( String ejercicioId, String deptoId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CODIGO_PERSONAL||PUESTO_ID||TIPO AS LLAVE, FOLIO AS VALOR FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
			Object[] parametros = new Object[] {ejercicioId, deptoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAcuerdosEnDepto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, BecAcuerdo> mapaAcuerdos( String ejercicioId){
		HashMap<String, BecAcuerdo> mapa = new HashMap<String, BecAcuerdo>();
		List<BecAcuerdo> lista			= new ArrayList<BecAcuerdo>();		
		try{			
			String comando = "SELECT CODIGO_PERSONAL,TIPO,FECHA,MATRICULA,ENSENANZA,INTERNADO,VALOR,VIGENCIA,ESTADO,FOLIO,ID_EJERCICIO,PROMESA,ID_CCOSTO,PUESTO_ID,HORAS,TIPOADICIONAL,USUARIO"
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new BecAcuerdoMapper(), parametros);
			for(BecAcuerdo objeto :lista){
				mapa.put(objeto.getCodigoPersonal()+objeto.getFolio(), objeto);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAcuerdos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaAsociados( String ejercicioId, String tipos){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CODIGO_PERSONAL||PUESTO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND TIPO NOT IN ("+tipos+")"
					+ " GROUP BY CODIGO_PERSONAL,PUESTO_ID";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto :lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAsociados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaVigentes( String ejercicioId, String tipos){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND VIGENCIA >= TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND TIPO NOT IN ("+tipos+")"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto :lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapaAsociados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Float> mapaTotalPorPuesto(String ejercicioId, String periodoId){		
		HashMap<String, Float> mapa = new HashMap<String, Float>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{			
			String comando = " SELECT TIPO AS LLAVE, SUM(ENSENANZA*HORAS) AS VALOR"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND VALOR = 'C'"
					+ " AND PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO_ALUMNO WHERE PERIODO_ID = ? AND ESTADO = 'C')"				
					+ " GROUP BY TIPO";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, periodoId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), Float.valueOf(map.getValor()));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapTotalPorPueto|:"+ex);
		}
		return mapa;
	}
	
}