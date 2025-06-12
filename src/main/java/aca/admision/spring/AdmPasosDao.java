package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPasosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmPasos pasos){
		boolean ok = false;		
		try{
			String comando ="INSERT INTO SALOMON.ADM_PASOS(FOLIO, FECHA, USUARIO, PASOS, ESTADO, COMENTARIO)"
					+ " VALUES(TO_NUMBER(?,'9999999'), now(), ?, ?, ?, ?)";			
			Object[] parametros = new Object[] { pasos.getFolio(),pasos.getUsuario(),pasos.getPasos(), pasos.getEstado(), pasos.getComentario() };
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio, String paso){
		boolean ok = false;
		try{
			String comando = "DELETE FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999') AND PASOS = ?";
			Object[] parametros = new Object[] { folio, paso };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}	

	public boolean deleteReg(String folio){
		boolean ok = false;
		try{
			String comando = "DELETE FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] { folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public AdmPasos mapeaRegId(String folio, String paso) {
		AdmPasos admPasos = new AdmPasos();
		
		try {
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " USUARIO, PASOS, ESTADO, COMENTARIO"
					+ " FROM SALOMON.ADM_PASOS"
					+ " WHERE FOLIO = TO_NUMBER(?, '99999999')"
					+ " AND PASOS = ?";
			Object[] parametros = new Object[] { folio, paso };
			admPasos = enocJdbc.queryForObject(comando, new AdmPasosMapper(), parametros);	
		} catch (Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPasosDao|mapeaRegId|:"+ex);
		}
		
		return admPasos;
	}
	
	public boolean existeReg(String folio, String paso) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999') AND PASOS = ?";
			Object[] parametros = new Object[] {folio, paso};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|existeReg|:"+ex);
		}		
		return ok;
	}

	public boolean existeRegFolio(String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|existeRegFolio|:"+ex);
		}		
		return ok;
	}

	public boolean quitarPendiente(String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999') AND PASOS = '2'";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "UPDATE SALOMON.ADM_PASOS SET ESTADO = 'A' WHERE FOLIO = TO_NUMBER(?,'9999999') AND PASOS = '2'";				
				if (enocJdbc.update(comando, parametros) == 1){
					ok = true;
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|quitarPendiente|:"+ex);
		}		
		return ok;
	}

	public List<AdmPasos> getAll( String orden) {
		List<AdmPasos> lista = new ArrayList<AdmPasos>();		
		try{
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, PASOS, ESTADO, COMENTARIO"
					+ " FROM SALOMON.ADM_PASOS " + orden;		
			lista = enocJdbc.query(comando, new AdmPasosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|getAll|:"+ex);
		}
		
		return lista;
	}

	public List<AdmPasos> listaPendientes() {
		List<AdmPasos> lista = new ArrayList<AdmPasos>();
		
		try{
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, PASOS, ESTADO, COMENTARIO"
					+ " FROM SALOMON.ADM_PASOS WHERE PASOS = '2' AND ESTADO = 'X'";	
			lista = enocJdbc.query(comando, new AdmPasosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPasosDao|listaPendientes|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, AdmPasos> mapaPasos(){
		HashMap<String, AdmPasos> mapa = new HashMap<String, AdmPasos>();
		List<AdmPasos> lista = new ArrayList<AdmPasos>();
		
		try {
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, PASOS, ESTADO, COMENTARIO"
					+ " FROM SALOMON.ADM_PASOS";
			lista = enocJdbc.query(comando, new AdmPasosMapper());
			for (AdmPasos aca : lista ){
				mapa.put(aca.getFolio()+aca.getPasos(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPasosDao|mapaPasos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmPasos> mapaPasos(String folio){
		HashMap<String, AdmPasos> mapa = new HashMap<String, AdmPasos>();
		List<AdmPasos> lista = new ArrayList<AdmPasos>();		
		try {
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, PASOS, ESTADO, COMENTARIO FROM SALOMON.ADM_PASOS WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmPasosMapper(), parametros);
			for (AdmPasos aca : lista ){
				mapa.put(aca.getPasos(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPasosDao|mapaPasos|:"+ex);
		}
		return mapa;
	}
	
}
