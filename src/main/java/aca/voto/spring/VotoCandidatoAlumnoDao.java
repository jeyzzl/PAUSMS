/**
 * 
 */
package aca.voto.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */

@Component
public class VotoCandidatoAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( VotoCandidatoAlumno voto ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO"
				+ " ENOC.VOTO_CANDIDATO_ALUMNO(EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FACULTAD_ID, ORDEN)"
				+ " VALUES(TO_NUMBER(?,'9999'), TO_NUMBER(?,'99'), ?, ?)";			
			Object[] parametros = new Object[] {voto.getEventoId(), voto.getCandidatoId(), voto.getCodigoPersonal(), voto.getFacultadId(), voto.getOrden() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( VotoCandidatoAlumno voto ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VOTO_CANDIDATO_ALUMNO SET FACULTAD_ID = ?"			
				+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {voto.getFacultadId(), voto.getEventoId(), voto.getCandidatoId(), voto.getCodigoPersonal() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String eventoId, String candidatoId, String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.VOTO_CANDIDATO_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, candidatoId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String eventoId, String candidatoId, String codigoPersonal){
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_CANDIDATO_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, candidatoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	// LISTADO DE CANDIDATOS
	public List<VotoCandidatoAlumno> getLista( String orden ){		
		List<VotoCandidatoAlumno> lista	= new ArrayList<VotoCandidatoAlumno>();		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FACULTAD_ID, ORDEN FROM ENOC.VOTO_CANDIDATO_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new VotoCandidatoAlumnoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|getLista|:"+ex);
		}
	
		return lista;	
	}
	
	// LISTADO DE CANDIDATOS
	public List<VotoCandidatoAlumno> lisEventos( String eventoId, String orden ){		
		List<VotoCandidatoAlumno> lista	= new ArrayList<VotoCandidatoAlumno>();
		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FACULTAD_ID, ORDEN FROM ENOC.VOTO_CANDIDATO_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')"+orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new VotoCandidatoAlumnoMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|getListaEvento|:"+ex);
		}
	
		return lista;	
	}
	
	// LISTADO DE CANDIDATOS
	public List<VotoCandidatoAlumno> lisEventos( String eventoId, String candidatoId, String orden ){		
		List<VotoCandidatoAlumno> lista	= new ArrayList<VotoCandidatoAlumno>();
		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FACULTAD_ID, ORDEN FROM ENOC.VOTO_CANDIDATO_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99') "+orden;
			Object[] parametros = new Object[] {eventoId,candidatoId};
			lista = enocJdbc.query(comando, new VotoCandidatoAlumnoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoAlumnoDao|getListaEvento|:"+ex);
		}
	
		return lista;	
	}
	
	
}