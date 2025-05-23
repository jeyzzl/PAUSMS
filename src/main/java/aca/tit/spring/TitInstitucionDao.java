/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitInstitucionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    
    public boolean insertReg(TitInstitucion institucion) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_INSTITUCION (CVEINSTITUCION, NOMBREINSTITUCION, INSTITUCION) VALUES( ?, ?, ? )";
			
			Object[] parametros = new Object[] {institucion.getCveInstitucion(), institucion.getNombreInstitucion()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(TitInstitucion institucion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_INSTITUCION SET NOMBREINSTITUCION = ?"
					+ " AND CVEINSTITUCION = TO_NUMBER(?,'999999') WHERE INSTITUCION = ?";
			
			Object[] parametros = new Object[] {institucion.getNombreInstitucion(), institucion.getCveInstitucion()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String cveInstitucion ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_INSTITUCION WHERE INSTITUCION = ?";
			
			Object[] parametros = new Object[] {cveInstitucion};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitInstitucion mapeaRegId(  String instituto) {
		TitInstitucion institucion = new TitInstitucion();
		 
		try{
			String comando = "SELECT INSTITUCION, CVEINSTITUCION, NOMBREINSTITUCION FROM ENOC.TIT_INSTITUCION WHERE INSTITUCION = ?";
			
			Object[] parametros = new Object[] {instituto};
			institucion = enocJdbc.queryForObject(comando, new TitInstitucionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return institucion;
		
	}	
	
	public boolean existeReg(String institucion) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_INSTITUCION WHERE INSTITUCION = ? "; 
			
			Object[] parametros = new Object[] {institucion};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<TitInstitucion> listAll( String orden) {
		List<TitInstitucion> lista		= new ArrayList<TitInstitucion>();
		String comando					= "";
		
		try{
			comando = " SELECT INSTITUCION, CVEINSTITUCION, NOMBREINSTITUCION FROM ENOC.TIT_INSTITUCION "+orden;	 
			
			lista = enocJdbc.query(comando, new TitInstitucionMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<TitInstitucion> getLista( String orden ) {
		
		List<TitInstitucion> lista	= new ArrayList<TitInstitucion>();	
		
		try{
			String comando = " SELECT INSTITUCION, CVEINSTITUCION, NOMBREINSTITUCION FROM ENOC.TIT_INSTITUCION "+ orden;
			lista = enocJdbc.query(comando, new TitInstitucionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TitInstitucionDao|getLista|:"+ex);
		}		
		
		return lista;
	}
	
}