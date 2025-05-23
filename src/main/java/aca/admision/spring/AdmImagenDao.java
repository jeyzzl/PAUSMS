package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmImagenDao {
	
	@Autowired
	@Qualifier("jdbcAdmision")
	private JdbcTemplate admisionJdbc;
	
	public boolean insertReg(AdmImagen admImagen) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ADM_IMAGEN(FOLIO, DOCUMENTO_ID, HOJA, IMAGEN, FECHA)"
					+ " VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'))";
			
			Object[] parametros = new Object[] {
				admImagen.getFolio(),admImagen.getDocumentoId(),admImagen.getHoja(),admImagen.getImagen(), admImagen.getFecha()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmImagen admImagen) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ADM_IMAGEN"
					+ " SET IMAGEN = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'99')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				admImagen.getImagen(),admImagen.getFolio(),admImagen.getDocumentoId(),admImagen.getHoja()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio, String documentoId, String hoja) {
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'99')"
					+ " AND HOJA = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[]{folio,documentoId,hoja};
			if (admisionJdbc.update(comando,parametros) == 1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|borrar - deleteReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteRegFolio(String folio) {
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[]{folio};
			if (admisionJdbc.update(comando,parametros) == 1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|borrar - deleteRegFolio|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteRegDocumento(String folio, String documentoId) {
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ADM_IMAGEN WHERE FOLIO = TO_NUMBER(?,'9999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[]{folio};
			if (admisionJdbc.update(comando,parametros) == 1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|borrar - deleteRegDocumento|:"+ex);
		}
		
		return ok;
	}
	
	public AdmImagen mapeaRegId(String folio, String documentoId, String hoja) {
		AdmImagen objeto = new AdmImagen();
		
		try {
			String comando = "SELECT FOLIO, DOCUMENTO_ID, HOJA, IMAGEN, FECHA"
					+ " FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?, '99')"
					+ " AND HOJA = TO_NUMBER(?, '99')";
		
			Object[] parametros = new Object[] {folio,documentoId,hoja};
			objeto = admisionJdbc.queryForObject(comando, new AdmImagenMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - adm.documento.spring.AdmImagenDao|mapeaRegId|:"+ex);
		}
		
		return objeto;	
	}
	
	public boolean existeReg(String folio, String documentoId, String hoja) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?, '99')"
					+ " AND HOJA = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {folio,documentoId,hoja};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeDocumentos(String folio, String documentoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {folio,documentoId};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|existeDocumentos|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg(String folio, String documentoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {folio,documentoId};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|existeReg|:"+ex);
		}

		return ok;
	}

	public boolean existeRegFolio(String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ADM_IMAGEN"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {folio};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|existeRegFolio|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegDocumento(String folio, String documentoId) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ADM_IMAGEN WHERE FOLIO = TO_NUMBER(?,'9999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {folio, documentoId};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.documento.spring.AdmImagenDao|existeRegDocumento|:"+ex);
		}		
		return ok;
	}
	
	public HashMap<String,String> mapImagen(String folio) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT TRIM(TO_CHAR(FOLIO,'9999999'))||TRIM(TO_CHAR(DOCUMENTO_ID,'99'))||TRIM(TO_CHAR(HOJA,'99')) AS LLAVE, HOJA AS VALOR"
					+ " FROM ADM_IMAGEN "
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ " GROUP BY FOLIO,DOCUMENTO_ID,HOJA";
			Object[] parametros = new Object[] {folio};
			lista = admisionJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.documento.spring.AdmImagenDao|mapImagen|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AdmImagen> mapaImagen(String folio) {
		
		HashMap<String,AdmImagen> mapa 	= new HashMap<String,AdmImagen>();
		List<AdmImagen> lista			= new ArrayList<AdmImagen>();
		
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, HOJA, IMAGEN, FECHA FROM ADM_IMAGEN WHERE FOLIO = TO_NUMBER(?,'9999999')"
					+ "  ORDER BY HOJA DESC LIMIT 1";
			Object[] parametros = new Object[] {folio};
			lista = admisionJdbc.query(comando, new AdmImagenMapper(), parametros);
			for (AdmImagen map : lista ) {
				mapa.put(map.getDocumentoId(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.documento.spring.AdmImagenDao|mapaImagen|:"+ex);
		}
		
		return mapa;
	}

}
