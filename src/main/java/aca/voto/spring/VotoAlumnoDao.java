package aca.voto.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotoAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( VotoAlumno voto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.VOTO_ALUMNO(CODIGO_PERSONAL, EVENTO_ID, CANDIDATO_ID, VOTO, FECHA)"
				+ " VALUES(?, TO_NUMBER(?,'9999'), TO_NUMBER(?,'99999'), ?, NOW() )";
			
			Object[] parametros = new Object[] {voto.getCodigoPersonal(),voto.getEventoId(),voto.getCandidatoId(),voto.getVoto()};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|insertReg|:"+voto.getCodigoPersonal()+":"+voto.getCandidatoId()+":"+voto.getVoto()+":"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( VotoAlumno voto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.VOTO_ALUMNO"
				+ " SET  VOTO = ?, FECHA = NOW()"
				+ " WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {voto.getVoto(), voto.getCodigoPersonal(), voto.getEventoId(), voto.getCandidatoId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String eventoId, String candidatoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.VOTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {codigoPersonal, eventoId, candidatoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VotoAlumno mapeaRegId( String codigoPersonal, String eventoId, String candidatoId){
		
		VotoAlumno votoAlumno = new VotoAlumno();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CANDIDATO_ID, VOTO, FECHA FROM ENOC.VOTO_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ? AND CANDIDATO = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {codigoPersonal, eventoId, candidatoId};
			votoAlumno = enocJdbc.queryForObject(comando, new VotoAlumnoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();		
		}
		
		return votoAlumno;
	}
	
	public boolean existeReg( String codigoPersonal, String eventoId, String candidatoId){		
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.VOTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {codigoPersonal, eventoId, candidatoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|existeReg|:"+ex);		
		}		
		return ok;
	}
	
	public String getVoto( String codigoPersonal, String eventoId, String candidatoId){		
		String voto = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {codigoPersonal, eventoId, candidatoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT VOTO FROM ENOC.VOTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999')";
				voto = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|existeReg|:"+ex);		
		}		
		return voto;
	}
	
	public boolean existeReg( String codigoPersonal, String eventoId, String candidatoId, String voto){
		
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.VOTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = TO_NUMBER(?,'9999') AND CANDIDATO_ID = TO_NUMBER(?,'99999') AND VOTO = ?";
			Object[] parametros = new Object[] {codigoPersonal, eventoId, candidatoId, voto};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoAlumnoDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	// LISTADO DE EVENTOS
	public List<VotoAlumno> getLista( String orden ){		
		List<VotoAlumno> lista = new ArrayList<VotoAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, EVENTO_ID, CANDIDATO_ID, VOTO, TO_CHAR(FECHA,'YYYY/MM/DD HH:MI:SS AM') AS FECHA"
					+ " FROM ENOC.VOTO_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new VotoAlumnoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.VotoAlumnoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaVotos( String eventoId ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CANDIDATO_ID||VOTO AS LLAVE, COUNT(*) AS VALOR FROM VOTO_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')"
					+ " GROUP BY CANDIDATO_ID||VOTO ";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getMapAlumnosNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaYaVoto( String eventoId, String codigoPersonal ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DISTINCT(CANDIDATO_ID) AS LLAVE, COUNT(*) AS VALOR FROM VOTO_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')"
					+ " AND CODIGO_PERSONAL = ?"
					+ " GROUP BY CANDIDATO_ID";
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaYaVoto|:"+ex);
		}
		
		return mapa;
	}
	
	public List<String> lisElegidos( String eventoId, String codigoPersonal ){

		List<String> lista	    	= new ArrayList<String>();
		try{
			String comando = "SELECT VOTO FROM ENOC.VOTO_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')"
					+ " AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|lisElegidos|:"+ex);
		}
		
		return lista;
	}
	
}