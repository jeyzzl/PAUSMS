package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumAtuendoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumAtuendo alumAtuendo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_ATUENDO(ATUENDO_ID, DESCRIPCION, PRECIO)"+
				" VALUES( ?, ?, TO_NUMBER(?, '99999.99'))";
			Object[] parametros = new Object[] {
				alumAtuendo.getAtuendoId(),alumAtuendo.getDescripcion(),alumAtuendo.getPrecio()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(AlumAtuendo alumAtuendo) {
		boolean ok = false;
		
		try{
			String comando = ("UPDATE ENOC.ALUM_ATUENDO SET DESCRIPCION = ?, PRECIO = TO_NUMBER(?, '99999.99')" +
				" WHERE ATUENDO_ID = ?");
			Object[] parametros = new Object[] {
				alumAtuendo.getDescripcion(),alumAtuendo.getPrecio(),alumAtuendo.getAtuendoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String atuendoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_ATUENDO WHERE ATUENDO_ID = ?";
			Object[] parametros = new Object[] {atuendoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumAtuendo mapeaRegId(String atuendoId) {
		
		AlumAtuendo objeto = new AlumAtuendo();
		
		try{
			String comando = "SELECT ATUENDO_ID, DESCRIPCION, PRECIO FROM ENOC.ALUM_ATUENDO WHERE ATUENDO_ID = ?";
			Object[] parametros = new Object[] {atuendoId};
			objeto = enocJdbc.queryForObject(comando, new AlumAtuendoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|mapeaRegId|:"+ex);
		}	
		
		return objeto;
	}
	
	public boolean existeReg(String atuendoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ATUENDO WHERE ATUENDO_ID = ?";
			Object[] parametros = new Object[] {atuendoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
 		String maximo = "1";
 		
 		try{
 			String comando = "SELECT COALESCE (MAX(ATUENDO_ID)+1,1) AS MAXIMO FROM ENOC.ALUM_ATUENDO"; 
 			
			maximo = enocJdbc.queryForObject(comando,String.class);
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumAtuendoDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public List<AlumAtuendo> getListAtuendo() {
		
		List<AlumAtuendo> lista	= new ArrayList<AlumAtuendo>();
		
		try{
			String comando = "SELECT ATUENDO_ID, DESCRIPCION, PRECIO FROM ENOC.ALUM_ATUENDO";
			
			lista = enocJdbc.query(comando, new AlumAtuendoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - ca.alumno.spring.AlumAtuendoDao|getListAtuendo|:"+ex);
		}			
		
		return lista;
	}

}
