// Clase Util para la tabla de Adm_requisito
package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AdmRequisitoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
		
	public boolean insertReg(AdmRequisito req ){
		boolean ok = false;			
		try{
			String comando = "INSERT INTO SALOMON.ADM_REQUISITO (CARRERA_ID, DOCUMENTO_ID, MODALIDADES, AUTORIZAR, REQUERIDO)"
					+ " VALUES( ?, TO_NUMBER(?,'99'),?,?,? )";
			Object[] parametros = new Object[] { req.getCarreraId(), req.getDocumentoId(), req.getModalidades(), req.getAutorizar(), req.getRequerido()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
			
	public boolean updateReg( AdmRequisito req ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_REQUISITO SET MODALIDADES = ?, AUTORIZAR = ?, REQUERIDO = ?"
					+ " WHERE CARRERA_ID = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { req.getModalidades(), req.getAutorizar(), req.getRequerido(), req.getCarreraId(), req.getDocumentoId()};
			if (enocJdbc.update(comando,parametros)==1){
					ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|updateReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String carreraId, String documentoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM  SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { carreraId, documentoId};
			if (enocJdbc.update(comando,parametros)==1){
					ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|deleteReg|:"+ex);
			ok = false;
		}
				
		return ok;
	}	
	
	public AdmRequisito mapeaRegId( String carreraId, String documentoId ) {
		
		AdmRequisito requisito = new AdmRequisito();		
		try{
			String comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, AUTORIZAR, REQUERIDO"
				+ " FROM SALOMON.ADM_REQUISITO"
				+ " WHERE CARRERA_ID = ?"
				+ " AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {carreraId, documentoId};
			requisito = enocJdbc.queryForObject(comando, new AdmRequisitoMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapeaRegId|:"+ex);			
		}
		
		return requisito;
	}
	
	public boolean existeReg( String carreraId, String documentoId ) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {carreraId, documentoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegPorCarrera(String carreraId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(CARRERA_ID) FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ?";				
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|existeRegPorCarrera|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegPorDocumento( String documentoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(CARRERA_ID) FROM SALOMON.ADM_REQUISITO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";	
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|existeRegPorDocumento|:"+ex);
		}
		
		return ok;
	}
		
	public List<AdmRequisito> getListAll( String orden ) {	
		
		List<AdmRequisito> lista	= new ArrayList<AdmRequisito>();		
		String comando	= "";
		
		try{
			comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, AUTORIZAR, REQUERIDO FROM SALOMON.ADM_REQUISITO "+ orden;
			lista = enocJdbc.query(comando, new AdmRequisitoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmRequisito> getLista( String carreraId, String orden ) {
		
		List<AdmRequisito> lista	= new ArrayList<AdmRequisito>();	
		try{
			String comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, AUTORIZAR, REQUERIDO FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ? " + orden;
			Object[] parametros = new Object[] {carreraId};
			lista = enocJdbc.query(comando, new AdmRequisitoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,AdmRequisito> mapaRequisitosPorCarrera( String carrera) {
		
		HashMap<String,AdmRequisito> mapa 	= new HashMap<String, AdmRequisito>();
		List<AdmRequisito> lista			= new ArrayList<AdmRequisito>();
		try{
			String comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, AUTORIZAR, REQUERIDO FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carrera};
			lista = enocJdbc.query(comando, new AdmRequisitoMapper(), parametros);
			for (AdmRequisito requisito : lista) {
				mapa.put(requisito.getDocumentoId(), requisito);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapaRequisitosPorDocumento|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaModalidadPorDocumento( String carrera) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, MODALIDADES AS VALOR FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carrera};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapaModalidadPorDocumento|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalPorCarrera( ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_REQUISITO WHERE REQUERIDO = 'S' GROUP BY CARRERA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapaTotalPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalPorDocumento( ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_REQUISITO GROUP BY DOCUMENTO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapaTotalPorDocumento|:"+ex);
		}
		
		return mapa;
	}
	
}