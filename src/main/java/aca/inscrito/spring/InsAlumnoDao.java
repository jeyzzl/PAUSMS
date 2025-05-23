package aca.inscrito.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InsAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(InsAlumno objeto){
		boolean ok = false;		
		try{
			String comando = " INSERT INTO ENOC.INS_ALUMNO(CODIGO_PERSONAL, CARGA_ID, PLAN_ID, CICLO, MATERIAS) VALUES (?,?,?,TO_NUMBER(?,'99'),TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(), objeto.getCargaId(), objeto.getPlanId(), objeto.getCiclo(), objeto.getMaterias()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg(InsAlumno objeto) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INS_ALUMNO "
					+ " SET MATERIAS = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {objeto.getMaterias(),objeto.getCodigoPersonal(),objeto.getCargaId(),objeto.getPlanId(),objeto.getCiclo()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String planId, String ciclo){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.INS_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') ";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,planId,ciclo};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId, String planId, String ciclo) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INS_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, planId,ciclo};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public InsAlumno mapeaRegId( String codigoPersonal, String cargaId, String planId, String ciclo ) {
		
		InsAlumno becAcceso = new InsAlumno();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PLAN_ID, CICLO, MATERIAS" +
					" FROM ENOC.INS_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') "; 
			Object[] parametros = new Object[] {codigoPersonal, cargaId, planId,ciclo};
			becAcceso = enocJdbc.queryForObject(comando, new InsAlumnoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|mapeaRegId|:"+ex);
		}
		
		return becAcceso;
	}
	
	public ArrayList<InsAlumno> lisTodos(String orden) {
		
		List<InsAlumno> lista = new ArrayList<InsAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PLAN_ID, CICLO, MATERIAS FROM ENOC.INS_ALUMNO "+orden;	
			lista = enocJdbc.query(comando, new InsAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|lisTodos|:"+ex);
		}
		
		return (ArrayList<InsAlumno>)lista;
	}
	
	public ArrayList<InsAlumno> lisBuscar(String cargaId) {		
		List<InsAlumno> lista = new ArrayList<InsAlumno>();		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL, SUBSTR(KCA.CURSO_CARGA_ID,1,6) AS CARGA_ID, MC.PLAN_ID, MC.CICLO, COUNT(KCA.CURSO_ID) AS MATERIAS"
					+ " FROM ENOC.KRDX_CURSO_ACT KCA, ENOC.MAPA_CURSO MC"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " GROUP BY KCA.CODIGO_PERSONAL, SUBSTR(KCA.CURSO_CARGA_ID,1,6), MC.PLAN_ID, MC.CICLO";	
			lista = enocJdbc.query(comando, new InsAlumnoMapper(), cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|lisBuscar|:"+ex);
		}
		
		return (ArrayList<InsAlumno>)lista;
	}
	
	public ArrayList<InsAlumno> lisPorCarga(String cargas, String orden) {
		
		List<InsAlumno> lista = new ArrayList<InsAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PLAN_ID, CICLO, MATERIAS FROM ENOC.INS_ALUMNO WHERE CARGA_ID IN("+cargas+") "+orden;	
			lista = enocJdbc.query(comando, new InsAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|lisPorCarga|:"+ex);
		}
		
		return (ArrayList<InsAlumno>)lista;
	}
	
	public HashMap<String, String> mapaPorCarga(){		
		HashMap<String,String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR  FROM ENOC.INS_ALUMNO GROUP BY CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.inscrito.spring.InsAlumnoDao|mapTodos|:"+ex);
		}
		
		return mapa;
	}
}