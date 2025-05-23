package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoProgramacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoProgramacion progra ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_PROGRAMACION(CURSO_CARGA_ID,FOLIO,FECHA,ORDEN)"
					+ " VALUES( ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? ) ";
			Object[] parametros = new Object[] {progra.getCursoCargaId(),
			progra.getFolio(), progra.getFecha(), progra.getOrden()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoProgramacion progra ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_PROGRAMACION "+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY'), ORDEN = ?"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {progra.getFecha(),
			progra.getOrden(), progra.getCursoCargaId(), progra.getFolio()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_PROGRAMACION"+ 
				" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {cursoCargaId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CargaGrupoProgramacion mapeaRegId(  String cursoCargaId, String folio) {
		
		CargaGrupoProgramacion objeto = new CargaGrupoProgramacion();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN FROM ENOC.CARGA_GRUPO_PROGRAMACION" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {cursoCargaId, folio};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoProgramacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {cursoCargaId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoCargaId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|maximoReg|:"+ex);
		}
		return maximo;
	}

	public List<CargaGrupoProgramacion> getListAll( String orden ) {
		
		List<CargaGrupoProgramacion> lista = new ArrayList<CargaGrupoProgramacion>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN "+
					"FROM ENOC.CARGA_GRUPO_PROGRAMACION "+ orden;
			lista = enocJdbc.query(comando, new CargaGrupoProgramacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacionUtil|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoProgramacion> getListMateria( String cursoCargaId, String orden ) {
		
		List<CargaGrupoProgramacion> lista = new ArrayList<CargaGrupoProgramacion>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN " +
					" FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new CargaGrupoProgramacionMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacionUtil|getListMateria|:"+ex);
		}
		return lista;
	}

	

}
