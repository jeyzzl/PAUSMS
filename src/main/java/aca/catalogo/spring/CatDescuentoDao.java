// Clase para la tabla de Modulo
package aca.catalogo.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatDescuentoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatDescuento descuento ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO "+
				"ENOC.CAT_DESCUENTO(DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS )"
				+ " VALUES( TO_NUMBER(?,'99'), ?, ? ) ";
			Object[] parametros = new Object[] {descuento.getDescuentoId(),descuento.getDescuentoNombre(),descuento.getUsuarios()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatDescuento descuento ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_DESCUENTO"
					+ " SET DESCUENTO_NOMBRE = ?,"
					+ " USUARIOS = ?"
					+ " WHERE DESCUENTO_ID = TO_NUMBER(?,'99')";				
			Object[] parametros = new Object[] {descuento.getDescuentoNombre(),descuento.getUsuarios(), descuento.getDescuentoId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String descuentoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_DESCUENTO"
					+ " WHERE DESCUENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {descuentoId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatDescuento mapeaRegId(  String descuentoId) {	
		 
		CatDescuento descuento 	= new CatDescuento();
		
		try{
			String comando = "SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS"
					+ " FROM ENOC.CAT_DESCUENTO"
					+ " WHERE DESCUENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {descuentoId};
			descuento = enocJdbc.queryForObject(comando, new CatDescuentoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return descuento;
	}
	
	public boolean existeReg( String descuentoId) {
		boolean 		ok 	= false;	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_DESCUENTO WHERE DESCUENTO_ID = TO_NUMBER(?,'99') ";		
			Object[] parametros = new Object[] {descuentoId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ) {
		
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(DESCUENTO_ID)+1,1) AS MAXIMO FROM ENOC.CAT_DESCUENTO";			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
		
	public List<CatDescuento> getListAll( String orden ) {
		
		List<CatDescuento> lista = new ArrayList<CatDescuento>();
		
		try{
			String comando = "SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS FROM ENOC.CAT_DESCUENTO "+ orden;				
			lista = enocJdbc.query(comando, new CatDescuentoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatDescuento> getDescuentoUsuario(String codigoPersonal, String orden ) {
		
		List<CatDescuento> lista = new ArrayList<CatDescuento>();		
		
		try{
			String comando = "SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS FROM  ENOC.CAT_DESCUENTO WHERE USUARIOS LIKE '%"+codigoPersonal+"%' "+ orden; 
			lista = enocJdbc.query(comando, new CatDescuentoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, CatDescuento> mapDescuentos(Connection conn) {
		
		HashMap<String, CatDescuento> mapa	= new HashMap<String, CatDescuento>();
		List<CatDescuento> lista 			= new ArrayList<CatDescuento>();		
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_DESCUENTO";
			lista = enocJdbc.query(comando, new CatDescuentoMapper());
			for(CatDescuento objeto : lista){
				mapa.put(objeto.getDescuentoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDescuentoDao|mapDescuentos|:"+ex);
		}
		
		return mapa;
	}
	
}