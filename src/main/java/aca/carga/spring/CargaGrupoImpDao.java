// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoImpDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoImp imp ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_IMP"
					+ " (GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID, FECHA,GRUPO)"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?,"
					+ " TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {imp.getGrupoId(), imp.getCursoId(), imp.getMaestro(),
			imp.getAlumnos(), imp.getCursoCargaId(), imp.getFecha(), imp.getGrupo()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoImp imp ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_IMP"
					+ " SET CURSO_ID = ?,"
					+ " MAESTRO = ?,"
					+ " ALUMNOS = ?,"
					+ " CURSO_CARGA_ID = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " GRUPO = ? "
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {imp.getCursoId(), imp.getMaestro(), imp.getAlumnos(),
			imp.getCursoCargaId(), imp.getFecha(), imp.getGrupo(), imp.getGrupoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoImp mapeaRegId(  String grupoId ) {
		
		CargaGrupoImp objeto = new CargaGrupoImp();
		
		try{
			String comando = "SELECT"
					+ " GRUPO_ID,"
					+ " CURSO_ID,"
					+ " MAESTRO,"
					+ " ALUMNOS,"
					+ " CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " GRUPO "
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoImpMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String grupoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_IMP "+ 
				"WHERE GRUPO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getMaestro( String grupoId ) {
		
		String maestro = "0000000";
		
		try{
			String comando = "SELECT MAESTRO FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId};
			maestro = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|getMaestro|:"+ex);
		}
		return maestro;
	}
	
	public String getFecha( String grupoId ) {
		
		String fecha = "";		
		
		try{
			String comando = "SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = ?";
			Object[] parametros = new Object[] {grupoId};
			fecha = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|getFecha|:"+ex);
		}
		return fecha;
	}
		
	public List<CargaGrupoImp> getListAll( String orden ) {
		
		List<CargaGrupoImp> lista = new ArrayList<CargaGrupoImp>();
		
		try{
			String comando = " SELECT GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, GRUPO"
					+ " FROM ENOC.CARGA_GRUPO_IMP "+ orden; 
			lista = enocJdbc.query(comando, new CargaGrupoImpMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoImp> getListPlan( String planId, String orden ) {
			
		List<CargaGrupoImp> lista = new ArrayList<CargaGrupoImp>();
		
		try{
			String comando = " SELECT GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, GRUPO"
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE ENOC.CURSO_PLAN(CURSO_ID) = ? "+ orden; 
			lista = enocJdbc.query(comando, new CargaGrupoImpMapper(), planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoImpDao|getListBloques|:"+ex);
		}
		return lista;
	}
	
		
}