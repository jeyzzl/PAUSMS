/**
 * 
 */
package aca.hca.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class HcaTipoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaTipo hcaTipo) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.HCA_TIPO(TIPO_ID, TIPO_NOMBRE, ORDEN) VALUES(TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '99'))";
			Object[] parametros = new Object[] { 
				hcaTipo.getTipoId(),hcaTipo.getTipoNombre(), hcaTipo.getOrden()	
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( HcaTipo hcaTipo) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.HCA_TIPO" + 
				" SET TIPO_NOMBRE = ?," +
				" ORDEN = TO_NUMBER(?, '99')" +
				" WHERE TIPO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {hcaTipo.getTipoNombre(),hcaTipo.getOrden(),hcaTipo.getTipoId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String tipoId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.HCA_TIPO WHERE TIPO_ID = TO_NUMBER(?, '99')";
			if (enocJdbc.update(comando,tipoId)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public HcaTipo mapeaRegId(String tipoId) {
		HcaTipo hcaTipo = new HcaTipo();		
		try{
			String comando = "SELECT TIPO_ID, TIPO_NOMBRE, ORDEN FROM ENOC.HCA_TIPO WHERE TIPO_ID = TO_NUMBER(?, '99')";
			hcaTipo = enocJdbc.queryForObject(comando, new HcaTipoMapper(), tipoId);						
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|mapeaRegId|:"+ex);
		}
		return hcaTipo;
	}
	
	public boolean existeReg( String tipoId) {
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_TIPO WHERE TIPO_ID = TO_NUMBER(?, '99')";
			if (enocJdbc.queryForObject(comando, Integer.class, tipoId) >= 1) {
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public String maximoReg(){
		int maximo			= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(TIPO_ID)+1,1) FROM ENOC.HCA_TIPO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public List<HcaTipo> lisTodos( String orden ) {
		
		List<HcaTipo> lista		= new ArrayList<HcaTipo>();
		try{
			String comando = "SELECT TIPO_ID, TIPO_NOMBRE, ORDEN FROM ENOC.HCA_TIPO "+orden;
			lista = enocJdbc.query(comando, new HcaTipoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaTipoDao|lisTodos|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, HcaTipo> mapaTipos(){
		HashMap<String, HcaTipo> mapa	= new HashMap<String, HcaTipo>();
		List<HcaTipo> lista	= new ArrayList<HcaTipo>();		
		try{
			String comando = "SELECT TIPO_ID, TIPO_NOMBRE, ORDEN FROM ENOC.HCA_TIPO";
			lista = enocJdbc.query(comando, new HcaTipoMapper());
			for(HcaTipo objeto : lista){				
				mapa.put(objeto.getTipoId(), objeto);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.HcaTipoDao|mapaCursosPorMaestro|:"+ex);
		}			
		return mapa;
	}
}