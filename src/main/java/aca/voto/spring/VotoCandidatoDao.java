/**
 * 
 */
package aca.voto.spring;

import java.util.ArrayList;
import java.util.HashMap;
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
public class VotoCandidatoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( VotoCandidato voto ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO"
				+ " ENOC.VOTO_CANDIDATO(EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, CANDIDATAS, FACULTADES, ESTADO, ORDEN, GANADOR FROM ENOC.VOTO_CANDIDATO)"
				+ " TO_NUMBER(?,'9999'), VALUES(TO_NUMBER(?,'99999'), ?, ?, ?, ?, ?, VALUES(TO_NUMBER(?,'99'), ?)";			
			Object[] parametros = new Object[] {voto.getEventoId(), voto.getCandidatoId(), voto.getCandidatoNombre(),
					voto.getCandidatos(), voto.getCandidatos(), voto.getFacultades(), voto.getEstado(), voto.getOrden(), voto.getGanador() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( VotoCandidato voto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VOTO_CANDIDATO"
				+ " SET CANDIDATO_NOMBRE = ?,"
				+ " CANDIDATOS = ?,"
				+ " CANDIDATAS = ?,"
				+ " FACULTADES = ?,"
				+ " ESTADO = ?,"
				+ " ORDEN = TO_NUMBER(?,'99'),"
				+ " GANADOR = ?"
				+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";	
			Object[] parametros = new Object[] {
				voto.getCandidatoNombre(),voto.getCandidatos(),voto.getCandidatas(), voto.getFacultades(),
				voto.getEstado(), voto.getOrden(),voto.getGanador(),voto.getEventoId(), voto.getCandidatoId() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String candidatoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			
			Object[] parametros = new Object[] {candidatoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VotoCandidato mapeaRegId( String eventoId, String candidatoId ){
		
		VotoCandidato votoCandidato = new VotoCandidato();
		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, CANDIDATAS, FACULTADES, ESTADO, ORDEN, GANADOR"
					+ " FROM ENOC.VOTO_CANDIDATO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = ?";
			Object[] parametros = new Object[] {eventoId, candidatoId};
			votoCandidato = enocJdbc.queryForObject(comando, new VotoCandidatoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		
		}	
		
		return votoCandidato;
	}
	
	public boolean existeReg( String eventoId, String candidatoId){
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId, candidatoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public String getCandidatoNombre( String eventoId, String candidatoId){
		String nombre 				= "";		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId, candidatoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){			
				comando = "SELECT CANDIDATO_NOMBRE FROM ENOC.VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
				nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|getCandidatoNombre|:"+ex);
		}
		return nombre;
	}
	
	public String getCandidatosPorEvento( String eventoId ){	
		String candidatos 			= "";
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = "SELECT CANDIDATOS FROM ENOC.VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			lista = enocJdbc.queryForList(comando, String.class, eventoId);
			for (String objeto : lista) {
				if (candidatos.length()!=0) candidatos += ","+objeto; else candidatos += objeto;  
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|getLista|:"+ex);
		}	
		return candidatos;	
	}
	
	// LISTADO DE CANDIDATOS
	public List<VotoCandidato> getLista( String orden ){		
		List<VotoCandidato> lista	= new ArrayList<VotoCandidato>();		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, CANDIDATAS, FACULTADES, ESTADO, ORDEN, GANADOR"
					+ " FROM ENOC.VOTO_CANDIDATO "+orden;
			lista = enocJdbc.query(comando, new VotoCandidatoMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|getLista|:"+ex);
		}
	
		return lista;	
	}
	
	// LISTADO DE CANDIDATOS
	public List<VotoCandidato> getListaEvento( String eventoId, String orden ){		
		List<VotoCandidato> lista	= new ArrayList<VotoCandidato>();		
		try{
			String comando = "SELECT EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, CANDIDATAS, FACULTADES, ESTADO, ORDEN, GANADOR"
					+ " FROM ENOC.VOTO_CANDIDATO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') "+orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new VotoCandidatoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|getListaEvento|:"+ex);
		}
	
		return lista;	
	}
	
	public List<String> lisFacultadesGanadoras( String eventoId ){		
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(FACULTAD_ID) FROM ENOC.VOTO_CANDIDATO_ALUMNO WHERE CODIGO_PERSONAL IN (SELECT GANADOR FROM VOTO_CANDIDATO WHERE EVENTO_ID = TO_NUMBER(?,'9999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|lisFacultadesGanadoras|:"+ex);
		}
	
		return lista;	
	}
	
	
	// LISTADO DE CANDIDATOS
	public String getCodigos( String eventoId, String candidatoId ){
		String codigos = "'0'";		
		try{
			String comando = "SELECT COUNT(CANDIDATOS||','||CANDIDATAS) FROM ENOC.VOTO_CANDIDATO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId, candidatoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT CANDIDATOS||','||CANDIDATAS FROM ENOC.VOTO_CANDIDATO"
						+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
				codigos = enocJdbc.queryForObject(comando,String.class, parametros);
				codigos = "'"+codigos.replace(",", "','")+"'";
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|getListaEvento|:"+ex);
		}
	
		return codigos;	
	}

	public HashMap<String,String> mapaGanadores(){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = "SELECT EVENTO_ID||CANDIDATO_ID AS LLAVE, GANADOR AS VALOR FROM VOTO_CANDIDATO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoCandidatoDao|mapaGanadores|:"+ex);
		}
		
		return mapa;	
	}	
}