package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitEstudioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitEstudio estudio) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_ESTUDIO (ESTUDIO_ID, ESTUDIO_NOMBRE, ESTUDIO_TIPO) VALUES( TO_NUMBER(?,'99'), ?, ? ) ";
			
			Object[] parametros = new Object[] {estudio.getEstudioId(), estudio.getEstudioNombre(), estudio.getEstudioTipo()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitEstudio estudio) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_ESTUDIO SET ESTUDIO_NOMBRE = ? AND ESTUDIO_TIPO = ? WHERE ESTUDIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {estudio.getEstudioNombre(), estudio.getEstudioTipo(), estudio.getEstudioId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String estudioId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {estudioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitEstudio mapeaRegId(  String estudioId) {
		TitEstudio estudio = new TitEstudio();
		 
		try{
			String comando = "SELECT ESTUDIO_ID, ESTUDIO_NOMBRE, ESTUDIO_TIPO FROM ENOC.TIT_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {estudioId};
			estudio = enocJdbc.queryForObject(comando, new TitEstudioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return estudio;
		
	}	
	
	public boolean existeReg(String estudioId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {estudioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getEstudioNombre(String estudioId) {
		String nombre 	= "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {estudioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT ESTUDIO_NOMBRE FROM ENOC.TIT_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99') ";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|getEstudioNombre|:"+ex);
		}
		return nombre;
	}
	
	public List<TitEstudio> listAll( String orden) {
		List<TitEstudio> lista		= new ArrayList<TitEstudio>();
		String comando		= "";
		
		try{
			comando = " SELECT ESTUDIO_ID, ESTUDIO_NOMBRE, ESTUDIO_TIPO FROM ENOC.TIT_ESTUDIO" +orden;	 
			
			lista = enocJdbc.query(comando, new TitEstudioMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<TitEstudio> getLista(String orden ) {
		
		List<TitEstudio> lista	= new ArrayList<TitEstudio>();	
		
		try{
			String comando = " SELECT ESTUDIO_ID, ESTUDIO_NOMBRE, ESTUDIO_TIPO FROM ENOC.TIT_ESTUDIO "+ orden;
			lista = enocJdbc.query(comando, new TitEstudioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitEstudioDao|getLista|:"+ex);
		}		
		
		return lista;
	}
}