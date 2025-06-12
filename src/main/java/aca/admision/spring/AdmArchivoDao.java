package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmArchivoDao {
	
	@Autowired
	@Qualifier("jdbcAdmision")
	private JdbcTemplate admisionJdbc;
	
	public boolean insertReg(AdmArchivo admArchivo) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ADM_ARCHIVO"+
				"(FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE)"+
				" VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?)";
			
			Object[] parametros = new Object[] {
				admArchivo.getFolio(),admArchivo.getDocumentoId(),admArchivo.getArchivo(),admArchivo.getNombre()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(AdmArchivo admArchivo) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ADM_ARCHIVO"+
				" SET ARCHIVO = ?,"+
				" NOMBRE = ?"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				admArchivo.getArchivo(),admArchivo.getNombre(),admArchivo.getFolio(),admArchivo.getDocumentoId()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio, String documentoId) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ADM_ARCHIVO"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[]{folio,documentoId};
			if (admisionJdbc.update(comando,parametros) >= 1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteReg(String folio){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ADM_ARCHIVO"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[]{folio};
			if (admisionJdbc.update(comando,parametros) >= 1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteRegFolio(String folio){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ADM_ARCHIVO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[]{folio};
			if (admisionJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|deleteRegFolio|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteRegDocumento(String folio, String documentoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ADM_ARCHIVO WHERE FOLIO = TO_NUMBER(?,'9999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[]{folio, documentoId};
			if (admisionJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|deleteRegDocumento|:"+ex);
		}		
		return ok;
	}
	
	public AdmArchivo mapeaRegId(String folio, String documentoId){
		AdmArchivo objeto = new AdmArchivo();
		
		try {
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE, FECHA "+
					" FROM ADM_ARCHIVO" +
					" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {folio,documentoId};
			objeto = admisionJdbc.queryForObject(comando, new AdmArchivoMapper(),parametros);
		} catch (Exception ex) {
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|mapeaRegId|:"+ex);
		}

		return objeto;	
	}
	
	public boolean existeReg(String folio, String documentoId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ADM_ARCHIVO"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999') " +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {folio,documentoId};
			if (admisionJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean existeRegFolio(String folio){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ADM_ARCHIVO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (admisionJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|existeRegFolio|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeRegDocumento(String folio, String documentoId){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ADM_ARCHIVO WHERE FOLIO = TO_NUMBER(?,'9999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, documentoId};
			if (admisionJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.apring.AdmArchivoDao|existeRegDocumento|:"+ex);
		}		
		return ok;
	}
	
	public HashMap<String,String> mapArchivo(String folio) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT TRIM(TO_CHAR(FOLIO,'9999999'))||TRIM(TO_CHAR(DOCUMENTO_ID,'99')) AS LLAVE, NOMBRE AS VALOR"
					+ " FROM ADM_ARCHIVO "
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " GROUP BY FOLIO,DOCUMENTO_ID";
			Object[] parametros = new Object[] {folio};
			lista = admisionJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.documento.spring.AdmArchivoDao|mapArchivo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, AdmArchivo> mapaArchivo(String folio) {
		
		HashMap<String,AdmArchivo> mapa = new HashMap<String,AdmArchivo>();
		List<AdmArchivo> lista		= new ArrayList<AdmArchivo>();
		
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE, FECHA FROM ADM_ARCHIVO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			lista = admisionJdbc.query(comando, new AdmArchivoMapper(),parametros);
			for (AdmArchivo map : lista ) {
				mapa.put(map.getDocumentoId(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.documento.spring.AdmArchivoDao|mapaArchivo|:"+ex);
		}
		
		return mapa;
	}

}
