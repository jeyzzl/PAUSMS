package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoCompetenciaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoCompetencia comp ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_COMPETENCIA(CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION ) "+
				"VALUES( ?, ?, ? ) ";
			Object[] parametros = new Object[] {comp.getCursoCargaId(),
			comp.getCompetenciaId(), comp.getDescripcion()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCompetenciaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoCompetencia comp ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_COMPETENCIA "+ 
				" SET DESCRIPCION = ? " +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {comp.getDescripcion(),
			comp.getCursoCargaId(), comp.getCompetenciaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaId, String competenciaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_COMPETENCIA "+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND COMPETENCIA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId, competenciaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoCompetencia mapeaRegId(  String cursoCargaId, String competenciaId) {
		
		CargaGrupoCompetencia objeto = new CargaGrupoCompetencia();
		
		try{
			String comando = "SELECT COMPETENCIA_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE CURSO_CARGA_ID = ? AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,competenciaId};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoCompetenciaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String competenciaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE CURSO_CARGA_ID = ? AND COMPETENCIA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId,competenciaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|existeReg|:"+ex);
		}
		return ok;
	}
	
	public int getNumCompetencias( String cursoCargaId) {
		int totalComp = 0;
		
		try{
			String comando = "SELECT COUNT(COMPETENCIA_ID) AS COMPETENCIA FROM ENOC.CARGA_GRUPO_COMPETENCIA " + 
					" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				totalComp = enocJdbc.queryForObject(comando, Integer.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|getSumActividades|:"+ex);
		}
		return totalComp;
	}
	
	public List<CargaGrupoCompetencia> getListAll( String orden ) {
		
		List<CargaGrupoCompetencia> lista = new ArrayList<CargaGrupoCompetencia>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA "+ orden;
			lista = enocJdbc.query(comando, new CargaGrupoCompetenciaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
		}
		return lista;
	}

	public List<CargaGrupoCompetencia> getListCompetencias(String cursoCargaId, String orden ) {
		
		List<CargaGrupoCompetencia> lista = new ArrayList<CargaGrupoCompetencia>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA " + 
					" WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new CargaGrupoCompetenciaMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CompetenciaUtil|getListCompetencias|:"+ex);
		}
		return lista;
	}
	
	public String getNombreCompetencia( String id ) {
		
		String nombre = "";		
		try{
			String comando = "SELECT DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE COMPETENCIA_ID = ?";
			nombre = enocJdbc.queryForObject(comando,String.class, id);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CompetenciaUtil|getNombreReligion|:"+ex);
		}
		return nombre;
	}	

}