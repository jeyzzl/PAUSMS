package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanMapper;

@Component
public class CargaAlumnoDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaAlumno alumno ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_ALUMNO "
					+ " (CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT) "
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {alumno.getCodigoPersonal(), alumno.getCargaId(), alumno.getBloqueId(), 
					alumno.getPlanId(), alumno.getFecha(), alumno.getEstado(), alumno.getGrupo(), alumno.getConfirmar(),
					alumno.getPago(), alumno.getModo(), alumno.getBeca(), alumno.getCalculo(), alumno.getComentario(), 
					alumno.getIngreso(),alumno.getMat()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( CargaAlumno alumno ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_ALUMNO"
					+ " SET FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " ESTADO = ?,"
					+ " GRUPO = ?,"
					+ " CONFIRMAR = ?,"
					+ " PAGO = ?,"
					+ " MODO = ?,"
					+ " BECA = ?,"
					+ " CALCULO = ?,"					
					+ " COMENTARIO = ?, "
					+ " INGRESO = ?"	
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND PLAN_ID = ?";
			Object[] parametros = new Object[] {alumno.getFecha(), alumno.getEstado(),alumno.getGrupo(),alumno.getConfirmar(), alumno.getPago(), alumno.getModo(), alumno.getBeca(),
					alumno.getCalculo(), alumno.getComentario(), alumno.getIngreso(), alumno.getCodigoPersonal(), alumno.getCargaId(), alumno.getBloqueId(), alumno.getPlanId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean updatePago( String codigoPersonal, String cargaId, String bloqueId, String pago ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_ALUMNO SET PAGO = ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {pago, codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|updatePago|:"+ex);
		}
		return ok;
	}
	
	public boolean updateModo( String codigoPersonal, String cargaId, String bloqueId, String modo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_ALUMNO SET MODO = ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {modo, codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|updateModo|:"+ex);			
		}
		return ok;
	}
	
	public boolean confirmarMaterias( String codigoPersonal, String cargaId, String bloqueId, String confirmar ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_ALUMNO SET MAT = ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {confirmar, codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|confirmarMaterias|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cargaId, String bloqueId, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId, planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaAlumno mapeaRegId( String codigoPersonal, String cargaId, String bloqueId ) {
		
		CargaAlumno objeto = new CargaAlumno();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
						+ " FROM ENOC.CARGA_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				objeto = enocJdbc.queryForObject(comando, new CargaAlumnoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String bloqueId, String planId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? "
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getPlanId( String codigoPersonal, String cargaId, String bloqueId) {
		String plan = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT PLAN_ID FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				plan = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeReg|:"+ex);
		}
		return plan;
	}
	
	public String getIngreso( String codigoPersonal, String cargaId, String bloqueId) {
		String ingreso = "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT INGRESO FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				ingreso = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getIngreso|:"+ex);
		}
		return ingreso;
	}
	
	public String getEstado( String codigoPersonal, String cargaId, String bloqueId) {
		String plan = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT ESTADO FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				plan = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getEstado|:"+ex);
		}
		return plan;
	}

	public boolean existeCarga( String codigoPersonal, String cargaId) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeCarga|:"+ex);
		}
		return ok;
	}
	
	public int pendientesDeConfirmar( String codigoPersonal) {
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND CONFIRMAR = 'N'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL)";
			Object[] parametros = new Object[] {codigoPersonal};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeCarga|:"+ex);
		}
		return total;
	}
	
	public String getMejorBloque( String codigoPersonal, String cargaId) {
		String bloque = "0";		
		try{
			String comando = "SELECT COUNT(BLOQUE_ID) FROM CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND ROWNUM = 1 ORDER BY FECHA DESC";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT BLOQUE_ID FROM CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND ROWNUM = 1 ORDER BY FECHA DESC";
				bloque 	= enocJdbc.queryForObject(comando,String.class,parametros);  
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|existeCarga|:"+ex);
		}
		return bloque;
	}
	
	public String getCargaNombre(String cargaId) {
		String cargaNombre = "-";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT NOMBRE_CARGA FROM ENOC.CARGA WHERE CARGA_ID = ?";
				cargaNombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargaNombre|:"+ex);
		}
		return cargaNombre;
	}
	
	public List<CargaAlumno> getListAll( String orden ) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new CargaAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> getCargasAlumno(String codigoPersonal) {		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? ORDER BY CARGA_ID DESC";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> getCargasAlumnoConPatrocinador(String codigoPersonal, String orden) {		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = 	"SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT " +
				    			"FROM ENOC.CARGA_ALUMNO " +
				    			"WHERE CARGA_ID IN (SELECT CARGA_ID FROM ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? ) " + orden ;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargasAlumnoConPatrocinador|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> getAlumnosCarga(String cargaId) {		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ? ORDER BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getAlumnosCarga|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisCargasActivas(String codigoPersonal) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisCargasActivasPorModo(String codigoPersonal, String estados, String modo){
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ? AND ESTADO IN ("+estados+") AND MODO = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			Object[] parametros = new Object[] {codigoPersonal, modo};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getCargasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisAlumnosConMaterias(String cargaId) {		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM CARGA_ALUMNO" 
					+ " WHERE CARGA_ID = ?" 
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID = 'M')"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ?)"
					+ " ORDER BY CARRERA(PLAN_ID)";			
			Object[] parametros = new Object[] {cargaId,cargaId,cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisAlumnosConMaterias|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisAlumnosConCalculo(String cargaId) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM ENOC.CARGA_ALUMNO" 
					+ " WHERE CARGA_ID = ?" 
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID = 'M')"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'S')"
					+ " ORDER BY CARRERA(PLAN_ID)";			
			Object[] parametros = new Object[] {cargaId,cargaId,cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisAlumnosConCalculo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisCargasEnLinea(String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PLAN_ID, FECHA, ESTADO, BLOQUE_ID, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID NOT IN"
					+ " (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO"
					+ "  WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ "  AND INSCRITO = 'S'"
					+ " )";			
			lista = enocJdbc.query(comando,new CargaAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisAlumnosConCalculo|:"+ex);
		}
		return lista;
	}

	public List<String> lisMatriculasCargasEnLinea(String orden) {
		
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID NOT IN"
					+ " (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO"
					+ "  WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ "  AND INSCRITO = 'S'"
					+ " )";			
			lista = enocJdbc.queryForList(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisMatriculasCargasEnLinea|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisCargasAlumno(String codigoAlumno, String orden){
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? "+ orden;
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisCargasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisCargasAlumno(String codigoAlumno, String year, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND ( TO_CHAR(FECHA,'YYYY')= ? OR CARGA_ID IN(SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A'))"+ orden;
			Object[] parametros = new Object[] {codigoAlumno, year};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisCargasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnMaterias(String cargaId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN("+bloques+")"
					+ " AND CONFIRMAR = 'N'"
					+ " AND CALCULO = 'N'"
					+ " AND PLAN_ID != '0'"		
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')" 
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID NOT IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ?)"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " " + orden;
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnMaterias|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnMaterias(String cargaId, String facultadId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT "
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND CONFIRMAR = 'N'"
					+ " AND CALCULO = 'N'"
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')" 
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " " + orden;			
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnMaterias|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnCalculo(String cargaId, String facultadId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT "
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CONFIRMAR = 'N'"
					+ " AND CALCULO = 'S'"
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')" 
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " " + orden;
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnCalculo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnCalculo(String cargaId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN("+bloques+")"
					+ " AND CONFIRMAR = 'N'"
					+ " AND CALCULO = 'S'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " " + orden;
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnCalculo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnConfirmar(String cargaId, String facultadId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CONFIRMAR = 'S'"
					+ " AND CALCULO = 'S'"
//					+ " AND PAGO = 'N'"
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " "	+ orden;
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnCalculo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnConfirmar(String cargaId, String bloques, String orden) {
		
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CONFIRMAR = 'S'"
					+ " AND CALCULO = 'S'"
//					+ " AND PAGO = 'N'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " "	+ orden;
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnCalculo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisTotal(String cargaId, String facultadId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ?)"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ?)"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " "	+ orden;
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisTotal|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisTotal(String cargaId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
				+ " FROM ENOC.CARGA_ALUMNO "
				+ " WHERE CARGA_ID = ? "
				+ " AND BLOQUE_ID IN("+bloques+")"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ?)"
//				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ?)"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
				+ " "+orden;
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisTotal|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnPago(String cargaId, String facultadId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CONFIRMAR = 'S'"
					+ " AND PAGO = 'S'"
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " "	+ orden;
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnPago|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisEnPago(String cargaId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CONFIRMAR = 'S'"
					+ " AND PAGO = 'S'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " "	+ orden;
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisEnPago|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisInscritos(String cargaId, String facultadId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "				
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')" + orden;
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'S') "	+ orden;
			Object[] parametros = new Object[] {cargaId, facultadId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisInscritos|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAlumno> lisInscritos(String cargaId, String bloques, String orden) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO "
					+ " WHERE CARGA_ID = ? "				
					+ " AND BLOQUE_ID IN("+bloques+") "
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')" + orden;
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'S') "	+ orden;
			Object[] parametros = new Object[] {cargaId, cargaId};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|lisInscritos|:"+ex);
		}
		return lista;
	}	
	
	public List<CargaAlumno> getListaPlanesAlumno(String codigoPersonal, String estado) {
		List<CargaAlumno> lista = new ArrayList<CargaAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, PLAN_ID, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT"
					+ " FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO IN ("+estado+")";
//					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new CargaAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|getListaPlanesAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, CargaAlumno> mapaCargasAlumno(String cargas) {
		
		HashMap<String, CargaAlumno> mapa 	= new HashMap<String,CargaAlumno>();
		List<CargaAlumno> lista				= new ArrayList<CargaAlumno>();	
		cargas = cargas.replace("''", "'");
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, GRUPO, CONFIRMAR, PAGO, MODO, BECA, CALCULO, COMENTARIO, INGRESO, MAT" 
					+ " FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID IN("+cargas+")";			
			lista = enocJdbc.query(comando, new CargaAlumnoMapper());			
			for(CargaAlumno alumno : lista){
				mapa.put(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId(), alumno);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaCargasAlumno|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnoPorFacultadEnCarga(String cargaId, String bloques) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(PLAN_ID) AS VALOR"
					+ " FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN ("+bloques+")"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCurso|mapaAlumnoPorFacultadEnCarga|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaAlumnosEnCarga(String cargaId, String bloques, String confirmar) {
		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN ("+bloques+")"
					+ " AND CONFIRMAR = ?"
					+ " AND CALCULO = 'N'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M' )"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID NOT IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ?)"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";			
			Object[] parametros = new Object[] {cargaId, confirmar, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCalculo(String cargaId, String bloques, String confirmar){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN ("+bloques+")"					
					+ " AND CONFIRMAR = ?"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";
			Object[] parametros = new Object[] {cargaId, confirmar, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCalculo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCalculo(String cargaId, String bloques){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN ("+bloques+")"					
					+ " AND CONFIRMAR = 'N'"
					+ " AND CALCULO = 'S'"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M' )"
//					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCalculo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCargaConfirmada(String cargaId, String bloques) {
		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
				+ " WHERE CARGA_ID = ?"
				+ " AND BLOQUE_ID IN ("+bloques+")"	
				+ " AND CONFIRMAR = 'S'"
				+ " AND CALCULO = 'S'"
//				+ " AND PAGO = 'N'"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'M')"
//				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ? AND TIPOCAL_ID = 'M')"
				+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";			
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCargaConfirmada|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaConfirmadas(String codigoAlumno) {
		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID, CONFIRMAR FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ?";							
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCargaConfirmada|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCargaPagada(String cargaId, String bloques) {		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
				+ " WHERE CARGA_ID = ?"
				+ " AND BLOQUE_ID IN ("+bloques+")"
				+ " AND CONFIRMAR = 'S'"
				+ " AND PAGO = 'S'"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"			
				+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";			
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCargaPagada|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosInscritos(String cargaId, String bloques) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
				+ " WHERE CARGA_ID = ?"
				+ " AND BLOQUE_ID IN ("+bloques+")"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')"
//				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'S')"
				+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";			
			Object[] parametros = new Object[] {cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCarreraDeAlumnos() {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA(PLAN_ID) AS VALOR FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_AGENDA)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaCarreraDeAlumnos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaModoCargaAlumnos(String cargaId, String bloques) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID AS LLAVE, MODO AS VALOR FROM ENOC.CARGA_ALUMNO"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID IN ("+bloques+")"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'S')";
			Object[] parametros = new Object[] {cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaModoCargaAlumnos|:"+ex);
		}
		
		return mapa;
	}

}