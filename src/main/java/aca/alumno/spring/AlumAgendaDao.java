package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumAgendaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumAgenda agenda ){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.ALUM_AGENDA(CODIGO_PERSONAL,ENTREGADO, FECHA, CARGA_ID, BLOQUE_ID) VALUES( ?, ?, SYSDATE, ?, TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {agenda.getCodigoPersonal(),agenda.getEntregado() };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|insertReg|:"+ex);			

		}
		return ok;
	}	
	
	public boolean updateReg(AlumAgenda agenda ){
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.ALUM_AGENDA SET ENTREGADO = ?, CARGA_ID = ?, BLOQUE_ID = TO_NUMBER(?,'99'), FECHA = SYSDATE WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{
					agenda.getEntregado(), agenda.getCargaId(), agenda.getBloqueId(), agenda.getCodigoPersonal()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateEntregado(String codigoPersonal, String entregado){
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.ALUM_AGENDA SET ENTREGADO = ?, FECHA = SYSDATE WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{ entregado, codigoPersonal };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ALUM_AGENDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros) == 1){
 				ok = true;
 			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean reiniciaEntregados( String entregado ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ALUM_AGENDA SET ENTREGADO = '0' WHERE ENTREGADO = ?";
			Object[] parametros = new Object[] {entregado};
 			if (enocJdbc.update(comando, parametros) == 1){
 				ok = true;
 			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteTraspaso(){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ALUM_AGENDA_TRASPASO";			
 			if (enocJdbc.update(comando) >= 1){
 				ok = true;
 			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|deleteTraspaso|:"+ex);	
		}
		return ok;
	}
	
	public boolean copiarDatos(){
		boolean 		ok 		= false;
		try{
			String comando = "INSERT INTO ALUM_AGENDA_TRASPASO(CODIGO_PERSONAL, ENTREGADO, FECHA, CARGA_ID, BLOQUE_ID)"
					+ " SELECT CODIGO_PERSONAL, ENTREGADO, FECHA, CARGA_ID, BLOQUE_ID FROM ALUM_AGENDA";		
 			if (enocJdbc.update(comando) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|copiarDatos|:"+ex);
		}
		return ok;
	}
	
	public AlumAgenda mapeaRegId(String codigoPersonal ){
		AlumAgenda agenda = new AlumAgenda();
		try{
			String comando = "SELECT CODIGO_PERSONAL, ENTREGADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.ALUM_AGENDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			agenda = enocJdbc.queryForObject(comando, new AlumAgendaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|mapeaRegId|:"+ex);	
		}
		return agenda;
	}
	
	public boolean actualizarListado(String cargaId, String bloqueId){
		boolean 		ok 		= false;
		try{
			String comando = "INSERT INTO ALUM_AGENDA(CODIGO_PERSONAL, ENTREGADO, FECHA, CARGA_ID, BLOQUE_ID)"
					+ " SELECT CODIGO_PERSONAL, '0', SYSDATE, CARGA_ID, BLOQUE_ID FROM INSCRITOS "
					+ " WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_AGENDA_TRASPASO)";
			Object[] parametros = new Object[] {cargaId, bloqueId};
 			if (enocJdbc.update(comando, parametros) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|actualizarListado|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_AGENDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|existeReg|:"+ex);	
		}
		return ok;
	}
	
	public List<AlumAgenda> lisTodos( String orden ){
		
		List<AlumAgenda> lista= new ArrayList<AlumAgenda>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ENTREGADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.ALUM_AGENDA "+orden;			
			lista = enocJdbc.query(comando, new AlumAgendaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|lisTodos|:"+ex);
		}				
		return lista;
	}
	
	public List<AlumAgenda> lisPorCarrera( String carreraId, String orden ){
		
		List<AlumAgenda> lista= new ArrayList<AlumAgenda>();		
		try{
			String comando = "SELECT AA.CODIGO_PERSONAL, AA.ENTREGADO, TO_CHAR(AA.FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, AA.CARGA_ID, AA.BLOQUE_ID"
					+ " FROM ENOC.ALUM_AGENDA AA, ENOC.CARGA_ALUMNO CA"
					+ " WHERE CA.CODIGO_PERSONAL = AA.CODIGO_PERSONAL"
					+ " AND CA.CARGA_ID = AA.CARGA_ID"
					+ " AND CA.BLOQUE_ID = AA.BLOQUE_ID"
					+ " AND (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = CA.PLAN_ID) = ? "+orden;
			lista = enocJdbc.query(comando, new AlumAgendaMapper(), carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|lisPorCarrera|:"+ex);
		}				
		return lista;
	}
	
	public List<AlumAgenda> lisNuevos( String orden ){
		
		List<AlumAgenda> lista = new ArrayList<AlumAgenda>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, ENTREGADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.ALUM_AGENDA WHERE ENTREGADO = '0' "+orden;			
			lista = enocJdbc.query(comando, new AlumAgendaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|lisNuevos|:"+ex);
		}				
		return lista;
	}
	
	public List<AlumAgenda> lisNuevosPorCarrera(String carreraId, String orden ){
		
		List<AlumAgenda> lista = new ArrayList<AlumAgenda>();		
		try{
			String comando = "SELECT AA.CODIGO_PERSONAL, AA.ENTREGADO, TO_CHAR(AA.FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, AA.CARGA_ID, AA.BLOQUE_ID"
					+ " FROM ENOC.ALUM_AGENDA AA, ENOC.CARGA_ALUMNO CA"
					+ " WHERE CA.CODIGO_PERSONAL = AA.CODIGO_PERSONAL"
					+ " AND CA.CARGA_ID = AA.CARGA_ID"
					+ " AND CA.BLOQUE_ID = AA.BLOQUE_ID"
					+ " AND (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = CA.PLAN_ID) = ?"
					+ " AND AA.ENTREGADO = '0' "+orden;
			Object[] parametros = new Object[] {carreraId};
			lista = enocJdbc.query(comando, new AlumAgendaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|lisNuevosPorCarrera|:"+ex);
		}				
		return lista;
	}
	
	public HashMap<String, AlumAgenda> mapaTodos(){
		HashMap<String, AlumAgenda> mapa = new HashMap<String, AlumAgenda>();
		List<AlumAgenda> lista= new ArrayList<AlumAgenda>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, ENTREGADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.ALUM_AGENDA"; 
			lista = enocJdbc.query(comando, new AlumAgendaMapper());
			for (AlumAgenda asesor : lista){
				mapa.put(asesor.getCodigoPersonal(), asesor);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|mapaTodos|:"+ex);	
		}
		return mapa;
	}
	
}