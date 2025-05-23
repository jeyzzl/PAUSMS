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
public class TitModalidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    
    public boolean insertReg(TitModalidad modalidad) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_MODALIDAD (MODALIDAD_ID, MODALIDAD_NOMBRE, MODALIDAD_TIPO) VALUES( TO_NUMBER(?,'99'), ?, ? ) ";
			
			Object[] parametros = new Object[] {modalidad.getModalidadId(), modalidad.getModalidadNombre(), modalidad.getModalidadTipo()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitModalidad modalidad) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_MODALIDAD SET MODALIDAD_NOMBRE = ?, MODALIDAD_TIPO = ? WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {modalidad.getModalidadNombre(), modalidad.getModalidadTipo(), modalidad.getModalidadId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String modalidadId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitModalidad mapeaRegId(  String modalidadId) {
		TitModalidad modalidad = new TitModalidad();
		 
		try{
			String comando = "SELECT MODALIDAD_ID, MODALIDAD_NOMBRE, MODALIDAD_TIPO FROM ENOC.TIT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {modalidadId};
			modalidad = enocJdbc.queryForObject(comando, new TitModalidadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return modalidad;
		
	}	
	
	public boolean existeReg(String modalidadId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombreModalidad( String modalidadId) {		
		String nombre			= "vacio";
		
		try{
			String comando = "SELECT MODALIDAD_NOMBRE FROM ENOC.TIT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {modalidadId};			
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitModalidadDao|getNombreModalidad|:"+ex);
		}
		
		return nombre;
	}
	
	public List<TitModalidad> listAll( String orden) {
		List<TitModalidad> lista		= new ArrayList<TitModalidad>();
		String comando					= "";
		
		try{
			comando = " SELECT MODALIDAD_ID, MODALIDAD_NOMBRE, MODALIDAD_TIPO FROM ENOC.TIT_MODALIDAD "+orden;	 
			
			lista = enocJdbc.query(comando, new TitModalidadMapper());	
			
		}catch(Exception ex){
			
			System.out.println("Error - aca.tit.spring.TitModalidadDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
}