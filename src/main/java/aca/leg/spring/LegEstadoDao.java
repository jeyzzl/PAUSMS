package aca.leg.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegEstadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LegEstado legEstado ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO LEG_ESTADO(ESTADO_ID, ESTADO_NOMBRE)"+
				" VALUES( TO_NUMBER(?,'99'), ?";
			
			Object[] parametros = new Object[] {
				legEstado.getEstadoId(),legEstado.getEstadoNombre()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|insertReg|:"+ex);
		}

		return ok;
	}	
	
	public boolean updateReg(LegEstado legEstado ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.LEG_ESTADO SET ESTADO_NOMBRE=?" +
				" WHERE ESTADO_ID= TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				legEstado.getEstadoNombre(),legEstado.getEstadoId()
			}; 	
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(LegEstado legEstado ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {legEstado.getEstadoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public LegEstado mapeaRegId(String estadoId){
		LegEstado legEstado = new LegEstado();
		
		try{
			String comando = "SELECT ESTADO_ID, ESTADO_NOMBRE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {estadoId};
			legEstado = enocJdbc.queryForObject(comando, new LegEstadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

		return legEstado;
	}
	
	public boolean existeReg(String estadoId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {estadoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){	
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(TO_CHAR(MAX(ESTADO_ID)+1), '1') MAXIMO FROM ENOC.LEG_ESTADO"; 
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getEstadoNombre(String estadoId){	
		String nombre = "vacio";
		
		try{
			String comando = "SELECT ESTADO_NOMBRE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {estadoId};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|getEstadoNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<LegEstado> getListAll(String orden){		
		List<LegEstado> lisEstado = new ArrayList<LegEstado>();
		
		try{
			String comando="SELECT ESTADO_ID, ESTADO_NOMBRE FROM ENOC.LEG_ESTADO "+orden; 
			
			lisEstado = enocJdbc.query(comando, new LegEstadoMapper());
			
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegEstadoDao|getLista|:"+ex);
		}
		
		return lisEstado;
	}
	
}