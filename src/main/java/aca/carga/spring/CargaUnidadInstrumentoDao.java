package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadInstrumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaUnidadInstrumento inst ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD_INSTRUMENTO(CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE ) "+
				"VALUES( ?, ?, ? ) ";
			Object[] parametros = new Object[] {inst.getCursoCargaId(),
			inst.getInstrumentoId(), inst.getInstrumentoNombre()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaUnidadInstrumento inst ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD_INSTRUMENTO "+ 
				" SET INSTRUMENTO_NOMBRE = ? " +
				" WHERE INSTRUMENTO_ID = ? " +
				" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {inst.getInstrumentoNombre(),
			inst.getInstrumentoId(), inst.getCursoCargaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String instrumentoId, String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO "+ 
				" WHERE INSTRUMENTO_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {instrumentoId, cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaUnidadInstrumento mapeaRegId(  String cursoCargaId, String instrumentoId) {
		
		CargaUnidadInstrumento objeto = new CargaUnidadInstrumento();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE CURSO_CARGA_ID = ? AND INSTRUMENTO_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,instrumentoId};
			objeto = enocJdbc.queryForObject(comando, new CargaUnidadInstrumentoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String instrumentoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE INSTRUMENTO_ID = ?  AND CURSO_CARGA_ID = ? "; 
			Object[] parametros = new Object[] {cursoCargaId, instrumentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<CargaUnidadInstrumento> getListAll( String orden ) {
		
		List<CargaUnidadInstrumento> lista = new ArrayList<CargaUnidadInstrumento>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO "+ orden;
			lista = enocJdbc.query(comando, new CargaUnidadInstrumentoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaUnidadInstrumento> getListInstrumento(String cursoCargaId, String orden ) {
			
			List<CargaUnidadInstrumento> lista = new ArrayList<CargaUnidadInstrumento>();			
			try{
				String comando = "SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO " + 
						" WHERE CURSO_CARGA_ID = ? " + orden;
				lista = enocJdbc.query(comando, new CargaUnidadInstrumentoMapper(), cursoCargaId);				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.spring.CargaUnidadInstrumentoDao|getListAll|:"+ex);
			}
			return lista;
		}

}