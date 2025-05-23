package aca.cita.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CitaEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CitaEvento objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CITA_EVENTO(EVENTO_ID, ESTADO, EVENTO_NOMBRE)"
					+ "VALUES(TO_NUMBER(?,'99999'),?,?)";
			Object[] parametros = new Object[] {objeto.getEventoId(), objeto.getEstado(),objeto.getEventoNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaEventoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CitaEvento objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CITA_EVENTO SET EVENTO_ID = TO_NUMBER(?,'99999'), ESTADO = ? WHERE EVENTO_NOMBRE = ? ";
			Object[] parametros = new Object[] {objeto.getEventoId(), objeto.getEstado(),objeto.getEventoNombre()};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaEventoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String eventoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CITA_EVENTO WHERE EVENTO_ID = ?";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.dao.CatActividadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CitaEvento mapeaRegId( String actividadId) {
		CitaEvento objeto = new CitaEvento();
		
		try{
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE FROM ENOC.CAT_ACTIVIDAD WHERE EVENTO_ID = TO_NUMBER(?,'99999')";		 
			Object[] parametros = new Object[] { actividadId };
			objeto = enocJdbc.queryForObject(comando, new CitaEventoMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaEventoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String eventoId ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CITA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {eventoId};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaEventoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getEventoNombre( String eventoId ) {
		String nombre = "-";			
		
		try{
			String comando = "SELECT COALESCE(EVENTO_NOMBRE,'-') FROM ENOC.CITA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {eventoId};	
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaEventoDao|getEventoNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CitaEvento> getListAll (){
		List<CitaEvento> lista = new ArrayList<CitaEvento>();
		
		try {
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE FROM ENOC.CITA_EVENTO";
			
			lista = enocJdbc.query(comando, new CitaEventoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaEventoDao|getListAll|:"+ex);
		}
		return lista;
	}	
	
	public List<CitaEvento> listActivos (){
		List<CitaEvento> lista = new ArrayList<CitaEvento>();
		
		try {
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE FROM ENOC.CITA_EVENTO WHERE ESTADO = 'A'";
			
			lista = enocJdbc.query(comando, new CitaEventoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaEventoDao|listActivos|:"+ex);
		}
		return lista;
	}
}
