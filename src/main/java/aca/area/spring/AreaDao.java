package aca.area.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AreaDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	

	public boolean insertReg( Area area) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.AREA"
					+ " (AREA_ID, AREA_NOMBRE, RESPONSABLE)"
					+ " VALUES(TO_NUMBER(?, '999'), ?, ?)";
			Object[] parametros = new Object[] {area.getAreaId(), area.getAreaNombre(), area.getResponsable()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( Area area) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.AREA"
					+ " SET AREA_NOMBRE = ?,"
					+ " RESPONSABLE = ? "
					+ " WHERE AREA_ID = ?";
			Object[] parametros = new Object[] {area.getAreaNombre(), area.getResponsable(), area.getAreaId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String areaId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[] {areaId};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean existeReg( String areaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public Area mapeaRegId( String areaId ) {
		
		Area area = new Area();
		
		try{
			String comando = "SELECT AREA_ID, AREA_NOMBRE, RESOPNSABLE"
					+ " FROM ENOC.AREA WHERE AREA_ID = ? "; 
			Object[] parametros = new Object[] {areaId};
			area = enocJdbc.queryForObject(comando, new AreaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|mapeaRegId|:"+ex);
		}
		
		return area;
	}
	
	public List<Area> lisAll() {
		
		List<Area> lista = new ArrayList<Area>();	
		try{
			String comando = "SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE"
					+ " FROM ENOC.AREA";
			lista = enocJdbc.query(comando, new AreaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.AreaDao|lisAll|:"+ex);
		}
		
		return lista;
	}

}
