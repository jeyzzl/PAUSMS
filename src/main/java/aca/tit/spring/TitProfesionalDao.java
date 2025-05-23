package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitProfesionalDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitProfesional profesional ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_PROFESIONAL (FOLIO, CURP, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, "
					+ "CORREOELECTRONICO) VALUES( ?, ?, ?, ?, ?, ? ) ";
			
			Object[] parametros = new Object[] {profesional.getFolio(),profesional.getCurp(),profesional.getNombre(),
					profesional.getPrimerApellido(),profesional.getSegundoApellido(),profesional.getCorreoElectronico()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitProfesional profesional) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_PROFESIONAL "
					+ " SET CURP = ?,"
					+ " NOMBRE = ?,"
					+ " PRIMERAPELLIDO = ?,"
					+ " SEGUNDOAPELLIDO = ?,"
					+ " CORREOELECTRONICO = ?"
					+ " WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {profesional.getCurp(),profesional.getNombre(),profesional.getPrimerApellido(),
					profesional.getSegundoApellido(),profesional.getCorreoElectronico(),profesional.getFolio()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String profesionalId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_PROFESIONAL WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {profesionalId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitProfesional mapeaRegId(  String folioId) {
		TitProfesional profesional = new TitProfesional();
		 
		try{
		    String comando = "SELECT FOLIO, CURP, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CORREOELECTRONICO FROM ENOC.TIT_PROFESIONAL WHERE FOLIO = ?";
		 	
			Object[] parametros = new Object[] {folioId};
			profesional = enocJdbc.queryForObject(comando, new TitProfesionalMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return profesional;
		
	}	
	
	public boolean existeReg(String ProfesionalId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_PROFESIONAL WHERE FOLIO = ?"; 
			
			Object[] parametros = new Object[] {ProfesionalId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitProfesional> listAll( String orden) {
		List<TitProfesional> lista		= new ArrayList<TitProfesional>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, CURP, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CORREOELECTRONICO FROM ENOC.TIT_PROFESIONAL"+orden;	 
			
			lista = enocJdbc.query(comando, new TitProfesionalMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_PROFESIONAL"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitProfesional> mapaAll( ) {
		List<TitProfesional> lista			= new ArrayList<TitProfesional>();
		HashMap<String,TitProfesional> mapa	= new HashMap<String,TitProfesional>();	
		
		try{
			String comando	= " SELECT FOLIO, CURP, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CORREOELECTRONICO"
							+ " FROM ENOC.TIT_PROFESIONAL";					
			lista = enocJdbc.query(comando, new TitProfesionalMapper());
			
			for (TitProfesional profesional : lista){
				mapa.put(profesional.getFolio(), profesional);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitProfesionalDao|mapaAll|:"+ex);
		}
		return mapa;
	}
}