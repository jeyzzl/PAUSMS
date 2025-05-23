package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitCadenaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitCadena cadena ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_CADENA (FOLIO, CODIGO_PERSONAL, CADENA, SELLO) VALUES(?,?,?,?) ";
			
			Object[] parametros = new Object[] {cadena.getFolio(),cadena.getCodigoPersonal(),cadena.getCadena(),cadena.getSello()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitCadena cadena) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_CADENA SET CADENA = ?, SELLO = ? WHERE FOLIO = ? AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {cadena.getCadena(),cadena.getSello(),cadena.getFolio(), cadena.getCodigoPersonal()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean quitarCadena(String folio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_CADENA SET CADENA = 'CADENA-ORIGINAL', SELLO = 'SELLO' WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
 			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|quitarCadena|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String folio, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_CADENA WHERE FOLIO = ? AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {folio, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodos(String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_CADENA WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|deleteTodos|:"+ex);
		}
		return ok;
	}
	
	public TitCadena mapeaRegId(String folio, String codigoPersonal) {
		TitCadena cadena = new TitCadena();		 
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, CADENA, SELLO FROM ENOC.TIT_CADENA WHERE FOLIO = ? AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {folio, codigoPersonal};
			cadena = enocJdbc.queryForObject(comando, new TitCadenaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return cadena;
		
	}
	
	public TitCadena getCadena(String folio, String codigoPersonal) {
		TitCadena cadena = new TitCadena();		 
		try{
			String comando = "SELECT CADENA, SELLO FROM ENOC.TIT_CADENA WHERE FOLIO = ? AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {folio, codigoPersonal};
			cadena = enocJdbc.queryForObject(comando, new TitCadenaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return cadena;
		
	}	
	
	public boolean existeReg(String folio, String codigoPersonal) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_CADENA WHERE FOLIO = ? AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {folio, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCadenaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitCadena> listAll(String orden) {
		List<TitCadena> lista		= new ArrayList<TitCadena>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, CODIGO_PERSONAL, CADENA, SELLO FROM ENOC.TIT_CADENA "+orden;	 
			
			lista = enocJdbc.query(comando, new TitCadenaMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.TitCadenaDao|listAll|:"+ex);
		}
		return lista;
	}
}
