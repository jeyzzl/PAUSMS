package aca.saum.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SaumEtapaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaumEtapa etapa ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SAUM_ETAPA"
					+ " (ID, VERSION, NOMBRE, PROCEDIMIENTO, RECETA_ID) "
					+ " VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'9999999')) ";
			
			Object[] parametros = new Object[] {etapa.getId(), etapa.getVersion(),
					etapa.getNombre(), etapa.getProcedimiento(), etapa.getRecetaId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SaumEtapa etapa ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_ETAPA"
				+ " SET VERSION = TO_NUMBER(?,'9999999'),"
				+ " NOMBRE = ?,"
				+ " PROCEDIMIENTO = ?,"
				+ " RECETA_ID =  TO_NUMBER(?,'9999999')"
				+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {etapa.getVersion(),
					etapa.getNombre(), etapa.getProcedimiento(), etapa.getRecetaId(), etapa.getId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String id ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SAUM_ETAPA WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public SaumEtapa mapeaRegId(  String id) {
		SaumEtapa etapa = new SaumEtapa();		 
		try{
			String comando = "SELECT ID, VERSION, NOMBRE, PROCEDIMIENTO, RECETA_ID FROM ENOC.SAUM_ETAPA WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {id};
			etapa = enocJdbc.queryForObject(comando, new SaumEtapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return etapa;
		
	}
	
	public String getNombre( String id ) {
		String nombre = "X";
		 
		try{
			String comando = "SELECT COUNT(*) FROM SAUM_ETAPA WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {id};
			if ( enocJdbc.queryForObject(comando, Integer.class, parametros ) >= 1){
				comando = "SELECT NOMBRE FROM ENOC.SAUM_ETAPA WHERE ID = TO_NUMBER(?,'9999999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|getNombre|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
		
	}
	
	public boolean existeReg(String id) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_ETAPA WHERE ID = TO_NUMBER(?,'9999999') "; 
			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) FROM ENOC.SAUM_ETAPA";
			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public List<SaumEtapa> lisReceta( String recetaId, String orden) {
		List<SaumEtapa> lista		= new ArrayList<SaumEtapa>();
		String comando		= "";
		
		try{
			comando = " SELECT ID, VERSION, NOMBRE, PROCEDIMIENTO, RECETA_ID "
					+ " FROM ENOC.SAUM_ETAPA"
					+ " WHERE RECETA_ID = TO_NUMBER(?,'9999999') "+orden;	 
			Object[] parametros = new Object[] {recetaId};
			lista = enocJdbc.query(comando, new SaumEtapaMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|lisReceta|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaEtapasPorReceta( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT RECETA_ID AS LLAVE, COUNT(*) AS VALOR FROM SAUM_ETAPA GROUP BY RECETA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|mapaEtapasPorReceta|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
}