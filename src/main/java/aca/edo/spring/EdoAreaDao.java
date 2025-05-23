package aca.edo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoAreaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EdoArea area) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EDO_AREA(	AREA_ID, AREA_NOMBRE,AREA_TITULO)" +
				" VALUES(TO_NUMBER(?, '99'), ?,?) ";
			Object[] parametros = new Object[] {area.getAreaId(),
			area.getAreaNombre(), area.getAreaTitulo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EdoArea area) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_AREA" + 
				" SET AREA_NOMBRE = ?," +
				" AREA_TITULO = ?" +	
				" WHERE EDO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {area.getAreaNombre(),
			area.getAreaTitulo(), area.getAreaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String areaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO_AREA"+ 
				" WHERE AREA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|deleteReg|:"+ex);			
		}
		return ok;
	}	
	
	public EdoArea mapeaRegId( String areaId) {
		EdoArea objeto = new EdoArea();
		
		try{
			String comando = "SELECT AREA_ID, AREA_NOMBRE, AREA_TITULO" +
					" FROM ENOC.EDO_AREA" + 
					" WHERE AREA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {areaId};
			objeto = enocJdbc.queryForObject(comando, new EdoAreaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}	
	
	public boolean existeReg( String areaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_AREA" + 
					" WHERE AREA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(AREA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_AREA";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<EdoArea> getListAll( String orden) {
			
		List<EdoArea> lista = new ArrayList<EdoArea>();
		
		try{
			String comando = "SELECT AREA_ID, AREA_NOMBRE, AREA_TITULO FROM ENOC.EDO_AREA "+orden;
			lista = enocJdbc.query(comando, new EdoAreaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|getListAll|:"+ex);
		}
		return lista;
	}

	public HashMap<String,EdoArea> mapaEdoArea() {
		List<EdoArea> lista = new ArrayList<EdoArea>();
		HashMap<String,EdoArea> mapa = new HashMap<String,EdoArea>();
		
		try{
			String comando = "SELECT AREA_ID, AREA_NOMBRE, AREA_TITULO FROM ENOC.EDO_AREA";
			lista = enocJdbc.query(comando, new EdoAreaMapper());
			
			for(EdoArea area : lista) {
				mapa.put(area.getAreaId(), area);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAreaDao|mapaEdoArea|:"+ex);
		}
		return mapa;
	}
}