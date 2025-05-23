package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaDireccionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaDireccion exaDireccion ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.EXA_DIRECCION(DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION)"
				+ " VALUES(TO_NUMBER(?,'99999999'), ?, ? ,?, TO_NUMBER(?,'99999'), TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'),TO_NUMBER(?,'9'), ? )";
			Object[] parametros = new Object[]{ 
				exaDireccion.getDireccionId(), exaDireccion.getMatricula(), exaDireccion.getCiudad(), exaDireccion.getDireccion(), exaDireccion.getEstadoId(), exaDireccion.getPaisId(),
				exaDireccion.getCp(), exaDireccion.getFechaActualizacion(), exaDireccion.getEliminado(), exaDireccion.getIdDireccion()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean eliminar( String direccionId ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXA_DIRECCION SET ELIMINADO = '1' WHERE DIRECCION_ID = TO_NUMBER(?,'99999999')";	
			if (enocJdbc.update(comando, direccionId) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|eliminar|:"+ex);		
		}		
		return ok;
	}	
	
	public ExaDireccion mapeaRegIdEstudio( String direccionId ) {
		ExaDireccion exaDireccion = new ExaDireccion();		 
		try{
			String comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION"
				+ " FROM ENOC.EXA_DIRECCION WHERE DIRECCION_ID = TO_NUMBER(?,'99999999')";
			exaDireccion = enocJdbc.queryForObject(comando, new ExaDireccionMapper(), direccionId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgreso|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaDireccion;
	}
	
	public ExaDireccion mapeaRegId(String matricula) {
		ExaDireccion exaDireccion = new ExaDireccion();	 
		try{
			String comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION "
				+ " FROM ENOC.EXA_DIRECCION WHERE MATRICULA = ?";
			exaDireccion = enocJdbc.queryForObject(comando, new ExaDireccionMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaDireccion;
	}
	
	public boolean existeReg( String direccionId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_DIRECCION WHERE DIRECCION_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.queryForObject(comando,Integer.class, direccionId) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno( String matricula) {
		boolean ok = true;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_DIRECCION WHERE MATRICULA = ?  AND ELIMINADO!=1 ";	
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg( String matricula){
		int maximo 	= 1;
		try{
			String comando = "SELECT COALESCE(MAX(DIRECCION_ID),1) AS MAXIMO FROM ENOC.EXA_DIRECCION WHERE ELIMINADO !=1 AND MATRICULA = ?";
			maximo = enocJdbc.queryForObject(comando,Integer.class, matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String maximoReg() {
		int maximo 			= 1;	
		try{
			String comando = "SELECT COALESCE(MAX(DIRECCION_ID)+1,1) AS MAXIMO FROM ENOC.EXA_DIRECCION";
			maximo = enocJdbc.queryForObject(comando,Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public List<ExaDireccion> getListAll( String orden) {
		List<ExaDireccion> lista	= new ArrayList<ExaDireccion>();
		try{
			String comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION FROM ENOC.EXA_DIRECCION " + orden;
			lista = enocJdbc.query(comando, new ExaDireccionMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|getListAll|:"+ex);
		}
		return lista;
	}

	public List<ExaDireccion> getDireccion( String matricula, String orden) {		
		List<ExaDireccion> lista		= new ArrayList<ExaDireccion>();
		try{
			String comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION"
					+ " FROM ENOC.EXA_DIRECCION"
					+ " WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
			lista = enocJdbc.query(comando, new ExaDireccionMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|getDireccion|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,ExaDireccion> getMapAll( String orden ) {
		
		List<ExaDireccion> lista			= new ArrayList<ExaDireccion>();
		HashMap<String,ExaDireccion> map 	= new HashMap<String,ExaDireccion>();
		
		try{
			String comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, FECHAACTUALIZACION, ELIMINADO, IDDIRECCION FROM ENOC.EXA_DIRECCION" +
				" WHERE ELIMINADO != 1 "+ orden;
			lista = enocJdbc.query(comando, new ExaDireccionMapper());
			for (ExaDireccion direccion : lista) {
				map.put(direccion.getMatricula(), direccion);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaDireccionDao|getMapAll|:"+ex);
		}		
		return map;
	}
}
