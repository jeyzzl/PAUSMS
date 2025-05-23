package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorEmpArchivoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorEmpArchivo empArchivo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO DANIEL.POR_EMPARCHIVO (CODIGO_PERSONAL,PERIODO_ID,DOCUMENTO_ID,ARCHIVO,NOMBRE,FOLIO) "+
				"VALUES(?,?, TO_NUMBER(?,'99'),?, ?, TO_NUMBER(?,'99'))";

			Object[] parametros = new Object[] {
				empArchivo.getCodigoPersonal(),empArchivo.getPeriodoId(),empArchivo.getDocumentoId(),empArchivo.getArchivo(),empArchivo.getNombre(),empArchivo.getFolio()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(PorEmpArchivo empArchivo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_EMPARCHIVO SET ARCHIVO = ?,"
					+ " NOMBRE = ?,"
					+ " WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					empArchivo.getArchivo(),empArchivo.getNombre(),empArchivo.getCodigoPersonal(),empArchivo.getPeriodoId(),empArchivo.getDocumentoId(),empArchivo.getFolio()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio, String documentoId, String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM DANIEL.POR_EMPARCHIVO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,folio,documentoId,periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PorEmpArchivo mapeaRegId(String codigoPersonal, String folio, String documentoId, String periodoId) {
		PorEmpArchivo empArchivo = new PorEmpArchivo();
		try{
			String comando = "SELECT CODIGO_PERSONAL,PERIODO_ID,DOCUMENTO_ID,ARCHIVO,NOMBRE,FOLIO "+													
					" FROM DANIEL.POR_EMPARCHIVO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND PERIODO_ID = ?";
			
				Object[] parametros = new Object[] {codigoPersonal,folio,documentoId,periodoId};
				empArchivo = enocJdbc.queryForObject(comando, new PorEmpArchivoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|mapeaRegId|:"+ex);
		}
		return empArchivo;		
	}	
	
	public boolean existeReg(String codigoPersonal, String folio, String documentoId, String periodoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM DANIEL.POR_EMPARCHIVO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,folio,documentoId,periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|existeReg|:"+ex);
		}
		return ok;
	}	
		
	public List<PorEmpArchivo> lisEmpArchivo(String codigoPersonal, String periodoId) {
		List<PorEmpArchivo> lista	= new ArrayList<PorEmpArchivo>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,PERIODO_ID,DOCUMENTO_ID,ARCHIVO,NOMBRE,FOLIO FROM DANIEL.POR_EMPARCHIVO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			lista = enocJdbc.query(comando, new PorEmpArchivoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|lisEmpArchivo|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,PorEmpArchivo> mapaEmpArchivo(String codigoPersonal, String periodoId) {
		HashMap<String,PorEmpArchivo> mapa = new HashMap<String,PorEmpArchivo>();
		List<PorEmpArchivo> lista		   = new ArrayList<PorEmpArchivo>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,PERIODO_ID,DOCUMENTO_ID,ARCHIVO,NOMBRE,FOLIO FROM DANIEL.POR_EMPARCHIVO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			lista = enocJdbc.query(comando, new PorEmpArchivoMapper(), parametros);
			
			for(PorEmpArchivo archivo : lista) {
				mapa.put(archivo.getCodigoPersonal()+archivo.getDocumentoId()+archivo.getFolio(), archivo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpArchivoDao|mapaEmpArchivo|:"+ex);
		}
		return mapa;
	}

}
