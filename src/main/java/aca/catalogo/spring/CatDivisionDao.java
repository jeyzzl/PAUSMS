// Clase Util para la tabla de Cat_Division
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
public class CatDivisionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatDivision division ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_DIVISION"
					+ " (DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA, COD_POSTAL,"
					+ " TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999'),"
					+ " TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {
					division.getDivisionId(),division.getNombreDivision(),division.getDireccion(),
					division.getColonia(),division.getCodPostal(),division.getTelefono(),
					division.getFax(),division.getEmail(),division.getPaisId(),
					division.getEstadoId(),division.getCiudadId(),division.getNombreCorto()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatDivision division ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_DIVISION"
					+ " SET NOMBRE_DIVISION = ?,"
					+ " DIRECCION = ?,"
					+ " COLONIA = ?,"
					+ " COD_POSTAL = ?,"
					+ " TELEFONO = ?,"
					+ " FAX = ?,"
					+ " EMAIL = ?,"
					+ " PAIS_ID = TO_NUMBER(?,'999'),"
					+ " ESTADO_ID = TO_NUMBER(?,'999'),"
					+ " CIUDAD_ID = TO_NUMBER(?,'999'),"
					+ " NOMBRE_CORTO = ?"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {
					division.getNombreCorto(),division.getNombreDivision(),division.getDireccion(),
					division.getColonia(),division.getCodPostal(),division.getTelefono(),
					division.getFax(),division.getEmail(),division.getPaisId(),
					division.getEstadoId(),division.getCiudadId(),division.getDivisionId()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String divisionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatDivision mapeaRegId(  String divisionId ) {
		 
		CatDivision division 	= new CatDivision();
		
		try{
			String comando = "SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, "+
				"COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO "+ 
				"FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId};
			division = enocJdbc.queryForObject(comando, new CatDivisionMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return division;
	}
	
	public boolean existeReg( String divisionId) {
		boolean 		ok 	= false;		
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_DIVISION"
					+ " WHERE DIVISION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn ) {		
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(DIVISION_ID)+1,1) AS MAXIMO FROM ENOC.CAT_DIVISION ";			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
		
	public List<CatDivision> getListAll( String orden ) {
		
		List<CatDivision> lista = new ArrayList<CatDivision>();		
		
		try{
			String comando =  "SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO "+
				"FROM ENOC.CAT_DIVISION "+ orden;				
			lista = enocJdbc.query(comando, new CatDivisionMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatDivision> getMapAll( String orden ) {
		
		HashMap<String,CatDivision> mapa 	= new HashMap<String,CatDivision>();
		List<CatDivision> lista 			= new ArrayList<CatDivision>();
		
		try{
			String comando = "SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA,"
					+ " COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO"
					+ " FROM ENOC.CAT_DIVISION "+ orden;	
			lista = enocJdbc.query(comando, new CatDivisionMapper());
			for (CatDivision objeto : lista){
				mapa.put(objeto.getDivisionId(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombreDivision( String divisionId ) {
		
		String nombre 		= "";
		
		try{
			String comando =  "SELECT NOMBRE_DIVISION FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {divisionId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|getNombreDivision|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String divisionId ) {
		
		String nombre 		= "";
		
		try{
			String comando =  "SELECT NOMBRE_CORTO FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = "+divisionId; 
			Object[] parametros = new Object[] {divisionId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatDivisionDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
}