package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoEjeDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoEje eje ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_EJE(CURSO_CARGA_ID, EJE_ID, DESCRIPCION ) "+
				"VALUES( ?, ?, ? ) ";
			
			Object[] parametros = new Object[] {eje.getCursoCargaId(),eje.getEjeId(),eje.getDescripcion()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoEje eje) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_EJE "+ 
				" SET DESCRIPCION = ? " +
				" WHERE EJE_ID = ? " +
				" AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {eje.getDescripcion(),eje.getEjeId(),eje.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String ejeId, String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_EJE "+ 
				" WHERE EJE_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ";
				
				Object[] parametros = new Object[] {ejeId,cursoCargaId};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoEje mapeaRegId(  String cursoCargaId, String ejeId) {
		CargaGrupoEje eje = new CargaGrupoEje();
		
		try{
			String comando = "SELECT EJE_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ? AND EJE_ID = ?";		 
			
			Object[] parametros = new Object[] {cursoCargaId,ejeId};
			eje = enocJdbc.queryForObject(comando, new CargaGrupoEjeMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|mapeaRegId|:"+ex);
		}
		return eje;
	}
	
	public boolean existeReg( String ejeId, String cursoCargaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EJE WHERE EJE_ID = ?  AND CURSO_CARGA_ID = ? "; 
			
			Object[] parametros = new Object[] {ejeId,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existenEjes( String cursoCargaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ?  "; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<CargaGrupoEje> getListAll( String orden ) {
		List<CargaGrupoEje> lisGrupoEje = new ArrayList<CargaGrupoEje>();
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EJE_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_EJE "+ orden; 
			
			Object[] parametros = new Object[] {orden};	
			lisGrupoEje = enocJdbc.query(comando, new CargaGrupoEjeMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|getListAll|:"+ex);
		}
		return lisGrupoEje;
	}
	
	public List<CargaGrupoEje> getListEjes(String cursoCargaId, String orden ) {
		List<CargaGrupoEje> lisGrupoEje = new ArrayList<CargaGrupoEje>();
		String comando	= "";			
		try{
			comando = "SELECT CURSO_CARGA_ID, EJE_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ? " + orden;						
			Object[] parametros = new Object[] {	cursoCargaId,orden};	
			lisGrupoEje = enocJdbc.query(comando, new CargaGrupoEjeMapper(), parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEjeDao|getListAll|:"+ex);
		}			
		return lisGrupoEje;
	}

}	