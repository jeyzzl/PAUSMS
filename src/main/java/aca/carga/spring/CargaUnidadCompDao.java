package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadCompDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaUnidadComp comp ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD_COMP(CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID )"
					+ " VALUES( ?, ?, ? ) ";
			Object[] parametros = new Object[] {comp.getCursoCargaId(),
			comp.getUnidadId(), comp.getCompetenciaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaUnidadComp comp ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD_COMP "+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND UNIDAD_ID = ?" +
				" AND COMPETENCIA_ID = ? ";
			Object[] parametros = new Object[] {comp.getCursoCargaId(),
			comp.getUnidadId(), comp.getCompetenciaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public CargaUnidadComp mapeaRegId(  String cursoCargaId, String unidadId, String competenciaId) {
		
		CargaUnidadComp objeto = new CargaUnidadComp();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID "+
				"FROM ENOC.CARGA_UNIDAD_COMP WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ? AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, unidadId, competenciaId};
			objeto = enocJdbc.queryForObject(comando, new CargaUnidadCompMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String unidadId, String competenciaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_UNIDAD_COMP WHERE CURSO_CARGA_ID = ? " + 
					" AND UNIDAD_ID = ?" +
					" AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, unidadId, competenciaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public int numUnidadComp( String cursoCargaId, String competenciaId) {
		int numComp = 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMCOMP" +
					" FROM ENOC.CARGA_UNIDAD_COMP" + 
					" WHERE CURSO_CARGA_ID = ?" +				
					" AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, competenciaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numComp = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|existeUnidadComp|:"+ex);
		}
		return numComp;
	}
	
	public boolean deleteReg( String cursoCargaId, String unidadId, String competenciaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD_COMP "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND UNIDAD_ID = ?" +
				" AND COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, unidadId, competenciaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public List<CargaUnidadComp> getListAll( String orden ) {
			
			List<CargaUnidadComp> lista = new ArrayList<CargaUnidadComp>();
			
			try{
				String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID FROM ENOC.CARGA_UNIDAD_COMP "+ orden;
				lista = enocJdbc.query(comando, new CargaUnidadCompMapper());
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|getListAll|:"+ex);
			}
			return lista;
		}
	
	public List<CargaUnidadComp> getListCompetencias(String cursoCargaId, String orden ) {
		
		List<CargaUnidadComp> lista = new ArrayList<CargaUnidadComp>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID FROM ENOC.CARGA_UNIDAD_COMP " + 
					" WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new CargaUnidadCompMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCompDao|getListCompetencias|:"+ex);
		}
		return lista;
	}
	
}