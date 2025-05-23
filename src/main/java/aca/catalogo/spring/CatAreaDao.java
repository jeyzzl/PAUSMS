// Clase Util para la tabla de Cat_Area
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CatAreaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	

	public boolean insertReg(CatArea area ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_AREA(AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO)"
					+ " VALUES( ?, ?, ?, ?)";			
			Object[] parametros = new Object[] {area.getAreaId(), area.getNombreArea(), area.getCodigoPersonal(), area.getTipoPromedio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatArea area ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_AREA SET NOMBRE_AREA = ?, CODIGO_PERSONAL = ?, TIPO_PROMEDIO = ? WHERE AREA_ID = ? ";
			Object[] parametros = new Object[] {area.getNombreArea(), area.getCodigoPersonal(), area.getTipoPromedio(), area.getAreaId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|updateReg|:"+ex);	
		}
		
		return ok;
	}
	
	public boolean deleteReg( String areaId ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[] { areaId };		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CatArea mapeaRegId( String areaId ){	
		
		CatArea area 			= new CatArea();
		
		try{
			String comando = "SELECT AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO FROM ENOC.CAT_AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[] { areaId };
			area = enocJdbc.queryForObject(comando, new CatAreaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return area;
	}
	
	public boolean existeReg(String areaId) {
		boolean 		ok 	= false;		
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[]{areaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ){		
		int maximo				= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(AREA_ID)+1,1) AS MAXIMO FROM ENOC.CAT_AREA";			
			maximo = enocJdbc.queryForObject(comando,Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}	
	
	public String getTipoPromedio( String areaId ){	
		
		String tipoPromedio		= "1";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_AREA WHERE AREA_ID = ?";
			Object[] parametros = new Object[]{areaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT TIPO_PROMEDIO FROM ENOC.CAT_AREA WHERE AREA_ID = ?";
				tipoPromedio = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|getTipoPromedio|:"+ex);
		}
		
		return tipoPromedio;
	}
	
	
	public List<CatArea> listAll( String orden ){
		
		List<CatArea> lista = new ArrayList<CatArea>();
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_AREA "+orden;
			lista = enocJdbc.query(comando, new CatAreaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, CatArea> getMapArea(String orden) {
		
		HashMap<String, CatArea> mapa	= new HashMap<String, CatArea>();
		List<CatArea> lista	= new ArrayList<CatArea>();
		
		try{
			String comando = "SELECT AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO"
					+ " FROM ENOC.CAT_AREA " + orden;
			lista = enocJdbc.query(comando, new CatAreaMapper());
			for(CatArea area : lista){
				mapa.put(area.getAreaId(), area);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAreaDao|getMapArea|:"+ex);
		}
		
		return mapa;
	}
	
}