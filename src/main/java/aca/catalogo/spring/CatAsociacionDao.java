// Clase Util para la tabla de Cat_Asociacion
package aca.catalogo.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatAsociacionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatAsociacion asociacion ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ASOCIACION "+ 
				"(DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID ) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {asociacion.getDivisionId(),asociacion.getUnionId(),asociacion.getAsociacionId(),
					asociacion.getNombreAsociacion(),asociacion.getDireccion(),asociacion.getColonia(),asociacion.getCodPostal(),					
					asociacion.getTelefono(),asociacion.getFax(),asociacion.getEmail(),asociacion.getPaisId(),asociacion.getEstadoId(),
					asociacion.getCiudadId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsociacionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatAsociacion asociacion ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ASOCIACION "+ 
				"SET NOMBRE_ASOCIACION = ?, DIRECCION = ?, COLONIA = ?, COD_POSTAL = ?, "+
				"TELEFONO = ?, FAX = ?, EMAIL = ?, PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), CIUDAD_ID = TO_NUMBER(?,'999') "+
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') "+
				"AND UNION_ID = TO_NUMBER(?,'999') "+
				"AND ASOCIACION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {asociacion.getNombreAsociacion(),asociacion.getDireccion(),asociacion.getColonia(),asociacion.getCodPostal(),					
					asociacion.getTelefono(),asociacion.getFax(),asociacion.getEmail(),asociacion.getPaisId(),asociacion.getEstadoId(),
					asociacion.getCiudadId(), asociacion.getDivisionId(), asociacion.getUnionId(),asociacion.getAsociacionId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsociacionDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String divisionId, String unionId, String asociacionId ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_ASOCIACION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')"
					+ " AND UNION_ID = TO_NUMBER(?,'999')"
					+ " AND ASOCIACION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId, unionId, asociacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsociacionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatAsociacion mapeaRegId( String divisionId, String unionId, String asociacionId ){
		
		CatAsociacion asociacion 	= new CatAsociacion();
		
		try{
			String comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION,"
					+ " DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID"
					+ " FROM ENOC.CAT_ASOCIACION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')"
					+ " AND UNION_ID = TO_NUMBER(?,'999')"
					+ " AND ASOCIACION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId, unionId, asociacionId};		
			asociacion = enocJdbc.queryForObject(comando, new BeanPropertyRowMapper<CatAsociacion>(CatAsociacion.class), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsociacionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return asociacion;
	}
	
	public boolean existeReg( String divisionId, String unionId, String asociacionId){
		boolean 		ok 	= false;		
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ASOCIACION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')"
					+ " AND UNION_ID = TO_NUMBER(?,'999')"
					+ " AND ASOCIACION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId, unionId, asociacionId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AsociacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String divisionId, String unionId){
		String maximo 			= "1";		
		
		try{
			String comando = "SELECT MAX(ASOCIACION_ID)+1 MAXIMO "
					+ " FROM ENOC.CAT_ASOCIACION"
					+ " WHERE DIVISION_ID = TO_NUMBER (?,'999') AND UNION_ID = TO_NUMBER (?,'999')";
			Object[] parametros = new Object[] {divisionId, unionId};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AsociacionDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
		
	public List<CatAsociacion> getLista( String divisionId, String unionId, String orden ){
		
		List<CatAsociacion> lista	= new ArrayList<CatAsociacion>();
		try{
			if (divisionId==null||divisionId.equals(" ")) divisionId = "0";
			if (unionId==null||unionId.equals(" ")) unionId = "0";
			
			String comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION,"
					+ " DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID,"
					+ " ESTADO_ID, CIUDAD_ID"
					+ " FROM ENOC.CAT_ASOCIACION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')"
					+ " AND UNION_ID = TO_NUMBER(?,'999') "+ orden;
			Object[] parametros = new Object[] { divisionId, unionId };	
			lista = enocJdbc.query(comando, new CatAsociacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AsociacionDao|getLista|:"+ex);
		}
		
		return lista;	
	}
	
	public List<CatAsociacion> getListAll( String orden ) throws SQLException{
		
		List<CatAsociacion> lista	= new ArrayList<CatAsociacion>();		
		
		try{
			String comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, "+
				"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_ASOCIACION "+ orden; 			
			lista = enocJdbc.query(comando, new CatAsociacionMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AsociacionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatAsociacion> getMapAll( String orden ){
		
		HashMap<String,CatAsociacion> mapa = new HashMap<String,CatAsociacion>();
		List<CatAsociacion> lista	= new ArrayList<CatAsociacion>();		
		try{
			String comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION,"
					+ " DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID"
					+ " FROM ENOC.CAT_ASOCIACION "+ orden;
			lista = enocJdbc.query(comando,new CatAsociacionMapper());
			for(CatAsociacion asociacion : lista){				
				mapa.put(asociacion.getDivisionId()+asociacion.getUnionId()+asociacion.getAsociacionId(), asociacion);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AsociacionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
}