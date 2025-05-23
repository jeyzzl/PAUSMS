package aca.rotaciones.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotInstitucionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(RotInstitucion rotInst ) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.ROT_INSTITUCION(INSTITUCION_ID, INSTITUCION_NOMBRE) " +
					" VALUES(?,?)";
			
			Object[] parametros = new Object[] {
					rotInst.getInstitucionId(), rotInst.getInstitucionNombre()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(RotInstitucion rotInst ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ROT_INSTITUCION SET INSTITUCION_NOMBRE = ? " +
					" WHERE INSTITUCION_ID = ?";
			Object[] parametros = new Object[] {
					 rotInst.getInstitucionNombre(), rotInst.getInstitucionId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String institucionId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ?";
			
			Object[] parametros = new Object[] {institucionId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|deletetReg|:"+ex);
		}
		return ok;
	}
	public RotInstitucion mapeaRegId(String institucionId) {
		RotInstitucion rotInst = new RotInstitucion();

		try{
			String comando = "SELECT INSTITUCION_ID, INSTITUCION_NOMBRE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ? ";
			
			Object[] parametros = new Object[] {institucionId};
			rotInst = enocJdbc.queryForObject(comando, new RotInstitucionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|mapeaRegId|:"+ex);
		}
		return rotInst;
	}
	
	public boolean existeReg(String institucionId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ? ";

			Object[] parametros = new Object[] {institucionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
	
		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(INSTITUCION_ID)+1 MAXIMO FROM ENOC.ROT_INSTITUCION";
			Object[] parametros = new Object[] {};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getNombre( String institucionId) {
		
		String nombre			= "";
		
		try{
			String comando = "SELECT INSTITUCION_NOMBRE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ? ";
			Object[] parametros = new Object[] {institucionId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);

			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|getNombre|:"+ex);
		}
		return nombre;
	}
	public List<RotInstitucion> getListAll( String orden ) {
			
		List<RotInstitucion> list	= new ArrayList<RotInstitucion>();

		String comando					= "";
			
		try{
			comando = "SELECT * FROM ENOC.ROT_INSTITUCION "+orden; 
				
			list = enocJdbc.query(comando, new RotInstitucionMapper());
				
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|getListAll|:"+ex);
		}
		return list;
	}
	
	public HashMap<String, RotInstitucion> mapaAll() {
		List<RotInstitucion> lista				= new ArrayList<RotInstitucion>();
		HashMap<String, RotInstitucion> mapa	= new HashMap<String, RotInstitucion>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_INSTITUCION "; 
			
			lista = enocJdbc.query(comando,new RotInstitucionMapper());
			for(RotInstitucion rot : lista){				
				mapa.put(rot.getInstitucionId(), rot);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotInstitucionDao|mapaAll|:"+ex);
		}
		return mapa;
	}
}
