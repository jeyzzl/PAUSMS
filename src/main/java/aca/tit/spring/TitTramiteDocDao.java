package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitTramiteDocDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitTramiteDoc titTramite ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_TRAMITE_DOC (TRAMITE_ID, FOLIO, FECHA) VALUES(TO_NUMBER(?,'9999999'), ?, TO_DATE(?,'YYYY/MM/DD'))";
			
			Object[] parametros = new Object[] {
					titTramite.getTramiteId(), titTramite.getFolio(), titTramite.getFecha()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitTramiteDoc titTramite) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_TRAMITE_DOC SET FECHA = TO_DATE(?,'YYYY/MM/DD') WHERE TRAMITE_ID = TO_NUMBER(?,'9999999') AND FOLIO = ?";
			
			Object[] parametros = new Object[] {
					titTramite.getFecha(), titTramite.getTramiteId(), titTramite.getFolio()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String tramiteId, String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {tramiteId, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitTramite mapeaRegId(String tramiteId, String folio) {
		TitTramite titTramite = new TitTramite();		 
		try{
			String comando = "SELECT TRAMITE_ID, FOLIO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {tramiteId, folio};
			titTramite = enocJdbc.queryForObject(comando, new TitTramiteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return titTramite;
		
	}	
	
	public boolean existeReg(String tramiteId, String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {tramiteId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String folio) {
		boolean ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE_DOC WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getTramiteId( String folio) {
		String tramiteId = "0";	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE_DOC WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT TRAMITE_ID FROM ENOC.TIT_TRAMITE_DOC WHERE FOLIO = ?";
				tramiteId = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|getTramiteId|:"+ex);
		}
		return tramiteId;
	}
	
	public List<TitTramite> lisTramite( String tramiteId, String orden) {
		List<TitTramite> lista		= new ArrayList<TitTramite>();
		String comando		= "";
		
		try{
			comando = " SELECT TRAMITE_ID, FOLIO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA FROM ENOC.TIT_TRAMITE_DOC"
					+ " WHERE TRAMITE_ID = ? "+orden;
			Object[] parametros = new Object[] {tramiteId};
			lista = enocJdbc.query(comando, new TitTramiteMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|lisTramite|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaTotTitulos( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, COUNT(FOLIO) AS VALOR"
					+ " FROM ENOC.TIT_TRAMITE_DOC"
					+ " GROUP BY TRAMITE_ID";
		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTotTitulos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTramites() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT FOLIO AS LLAVE, TRAMITE_ID AS VALOR FROM ENOC.TIT_TRAMITE_DOC";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTramitesAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTramitesAlumno( String codigoAlumno) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT FOLIO AS LLAVE, TRAMITE_ID AS VALOR FROM ENOC.TIT_TRAMITE_DOC"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM ENOC.TIT_ALUMNO WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTramitesAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTramitesPorEstado( String estado) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT FOLIO AS LLAVE, TRAMITE_ID AS VALOR FROM ENOC.TIT_TRAMITE_DOC"
					+ " WHERE TRAMITE_ID IN (SELECT TRAMITE_ID FROM TIT_TRAMITE WHERE ESTADO = ?)";
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTramitesPorEstado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalAutenticados( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.TIT_TRAMITE_DOC"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM ENOC.TIT_ALUMNO WHERE RESPUESTA LIKE 'XML-SEP')"
					+ " GROUP BY TRAMITE_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTotalAutenticados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalPorRespuesta( String respuesta ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.TIT_TRAMITE_DOC"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM ENOC.TIT_ALUMNO WHERE RESPUESTA LIKE '"+respuesta+"%')"
					+ " GROUP BY TRAMITE_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaTotalPorRespuesta|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaSinFolioControl( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TRAMITE_ID AS LLAVE, COUNT(TD.FOLIO) AS VALOR FROM ENOC.TIT_TRAMITE_DOC TD, ENOC.TIT_ALUMNO TA"
					+ " WHERE TA.FOLIO = TD.FOLIO AND INSTR(TA.XML,TA.FOLIO_CONTROL) = 0"
					+ " GROUP BY TRAMITE_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDocDao|mapaSinFolioControl|:"+ex);
		}
		return mapa;
	}
}
