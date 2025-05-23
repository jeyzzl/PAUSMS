package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoPlanDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoPlan plan ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_PLAN(CURSO_CARGA_ID, ESTUDIOS, OCUPACION, LUGAR," +
				" HORARIO, OFICINA,TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO) "+
				"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			Object[] parametros = new Object[] {plan.getCursoCargaId(), plan.getEstudios(), plan.getOcupacion(),
			plan.getLugar(), plan.getHorario(), plan.getOficina(), plan.getTelefono(), plan.getTiempo(),
			plan.getAtencion(), plan.getCorreo(), plan.getDescripcion(), plan.getPerspectiva(), plan.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoPlan plan ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_PLAN "+ 
				" SET ESTUDIOS = ?, " +
				" OCUPACION = ?," +
				" LUGAR = ?," +
				" HORARIO = ?," +
				" OFICINA = ?," +
				" TELEFONO = ?," +
				" TIEMPO = ?, " +
				" ATENCION = ?," +
				" CORREO = ? , " +
				" DESCRIPCION = ?," +
				" PERSPECTIVA = ?," +
				" ESTADO = ? "+
				" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {plan.getEstudios(), plan.getOcupacion(), plan.getLugar(),
			plan.getHorario(), plan.getOficina(), plan.getTelefono(), plan.getTiempo(), plan.getAtencion(),
			plan.getCorreo(), plan.getDescripcion(), plan.getPerspectiva(), plan.getEstado(), plan.getCursoCargaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean updateEstado( String cursoCargaId, String estado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_PLAN "+ 
				" SET ESTADO = ? " +
				" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {estado, cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_PLAN "+ 
				"WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	
	public CargaGrupoPlan mapeaRegId(  String cursoCargaId) {
		
		CargaGrupoPlan objeto = new CargaGrupoPlan();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, ESTUDIOS, OCUPACION, LUGAR," +
					" HORARIO, OFICINA, TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO "+
				    " FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoPlanMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeDatos( String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getEstado( String cursoCargaId) {
		String estado = "A";
		
		try{
			String comando = "SELECT COALESCE(ESTADO,'A') AS ESTADO FROM ENOC.CARGA_GRUPO_PLAN " + 
					" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			estado = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|getSumActividades|:"+ex);
		}
		return estado;
	}
	
	public List<CargaGrupoPlan> getListAll( String orden ) {
		
		List<CargaGrupoPlan> lista = new ArrayList<CargaGrupoPlan>();
		
		try{
			String comando = " SELECT CURSO_CARGA_ID, ESTUDIOS,OCUPACION, LUGAR, HORARIO," +
					  " OFICINA, TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO FROM ENOC.CARGA_GRUPO_PLAN "+ orden;
			lista = enocJdbc.query(comando, new CargaGrupoPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaGrupoPlan> getListPlanes( String cargaId, String orden ) {
		
		List<CargaGrupoPlan> lista = new ArrayList<CargaGrupoPlan>();
		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_GRUPO_PLAN WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +orden;
			lista = enocJdbc.query(comando, new CargaGrupoPlanMapper(), cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoPlanDao|getListPlanes|:"+ex);
		}
		return lista;

	}
	
}