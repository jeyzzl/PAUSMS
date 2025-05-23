package aca.musica.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiInstitucionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MusiInstitucion ins ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MUSI_INSTITUCION(INSTITUCION_ID, INSTITUCION_NOMBRE)"
					+ " VALUES( TO_NUMBER(?,'99'), ? )";
			Object[] parametros = new Object[] {ins.getInstitucionId(), ins.getInstitucionNombre()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( MusiInstitucion ins ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MUSI_INSTITUCION"
					+ " SET INSTITUCION_NOMBRE = ?"
					+ " WHERE INSTITUCION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {ins.getInstitucionNombre(), ins.getInstitucionId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String institucionId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MUSI_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {institucionId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public MusiInstitucion mapeaRegId(  String institucionId) {
		
		MusiInstitucion ins 				= new MusiInstitucion();
		
		try{
			String comando = "SELECT INSTITUCION_ID, INSTITUCION_NOMBRE FROM ENOC.MUSI_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {institucionId};
			ins = enocJdbc.queryForObject(comando, new MusiInstitucionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return ins;
	}
	
	public boolean existeReg( String institucionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {institucionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getInstitucionNombre( String institucionId ) {
		
		String nombre			= "x";
		
		try{
			String comando = "SELECT INSTITUCION_NOMBRE FROM ENOC.MUSI_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {institucionId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|getNombreReligion|:"+ex);
		}
		
		return nombre;
	}
	
	public List<MusiInstitucion> getListAll( String orden ) {
		
		List<MusiInstitucion> lista = new ArrayList<MusiInstitucion>();
		
		try{
			String comando = "SELECT INSTITUCION_ID, INSTITUCION_NOMBRE FROM ENOC.MUSI_INSTITUCION "+ orden;		
			lista = enocJdbc.query(comando, new MusiInstitucionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MusiInstitucionDao|getListAll|:"+ex);
		}
		
		return lista;
	}	
	
}