package aca.leg.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public LegDocumento mapeaRegId(String idDocumentos) {
		LegDocumento lista = new LegDocumento();

		try{
			String comando = "SELECT IDDOCUMENTOS, DESCRIPCION, IMAGEN FROM ENOC.LEG_DOCUMENTOS WHERE IDDOCUMENTOS = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {idDocumentos};
			lista = enocJdbc.queryForObject(comando, new LegDocumentoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapeaRegId|:"+ex);
		}

		return lista;
	}
	
	public boolean existeReg(String idDocumentos) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_DOCUMENTOS WHERE IDDOCUMENTOS = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {idDocumentos};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<LegDocumento> lisTodos(){
		List<LegDocumento> lista = new ArrayList<LegDocumento>();
		
		try{
			String comando = "SELECT IDDOCUMENTOS FROM LEG_CONDICIONES WHERE GRUPO = ?";			
			//Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.query(comando,new LegDocumentoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<LegDocumento> lisAll(String orden){
		List<LegDocumento> lista = new ArrayList<LegDocumento>();
		
		try{
			String comando = "SELECT IDDOCUMENTOS, DESCRIPCION, IMAGEN FROM LEG_DOCUMENTOS "+orden;
			lista = enocJdbc.query(comando,new LegDocumentoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|lisAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaDocumentos( ) {
		HashMap<String, String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = 	"SELECT IDDOCUMENTOS AS LLAVE, DESCRIPCION AS VALOR FROM ENOC.LEG_DOCUMENTOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapaDocumentos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaEstados( ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = 	"SELECT ESTADO_ID AS LLAVE, ESTADO_NOMBRE AS VALOR FROM ENOC.LEG_ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapaEstados|:"+ex);
		}
		
		return mapa;
	}
	
}