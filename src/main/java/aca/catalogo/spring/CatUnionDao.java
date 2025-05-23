// Clase Util para la tabla de Cat_Carrera
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatUnionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String getNombreUnion( String divisionId, String unionId ) {		
		String nombre		= "";		
		try{
			String comando = "SELECT NOMBRE_UNION FROM ENOC.CAT_UNION"
				+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')"
				+ " AND UNION_ID = TO_NUMBER(?,'999') ";
			nombre = enocJdbc.queryForObject(comando, String.class, divisionId, unionId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatUnionDao|getNombreUnion|:"+ex);
		}		
		return nombre;
	}
	
	public List<CatUnion> getLista( String divisionId, String orden ) {
		
		List<CatUnion> lista	= new ArrayList<CatUnion>();		
		try{
			if (divisionId==null||divisionId.equals(" ")) divisionId = "0";
			String comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA,COD_POSTAL,"
					+ " TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID FROM ENOC.CAT_UNION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999') "+ orden;
			lista = enocJdbc.query(comando, new CatUnionMapper(), divisionId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatUnionDao|getLista|:"+ex);
		}
		
		return lista;
	}	
	
	public List<CatUnion> getListAll( String orden ) {
	
		List<CatUnion> lista	= new ArrayList<CatUnion>();
		
		try{
			String comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_UNION "+ orden; 
			lista = enocJdbc.query(comando, new CatUnionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatUnionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatUnion> getMapAll( String orden ) {
		
		HashMap<String,CatUnion> mapa = new HashMap<String,CatUnion>();
		List<CatUnion> lista	= new ArrayList<CatUnion>();
		
		try{
			String comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA,"
					+ " COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID"
					+ " FROM ENOC.CAT_UNION "+ orden;
			lista = enocJdbc.query(comando, new CatUnionMapper());
			for (CatUnion union : lista){
				mapa.put(union.getUnionId(), union);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatUnionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
}