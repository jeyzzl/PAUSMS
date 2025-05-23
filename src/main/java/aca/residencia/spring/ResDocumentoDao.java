package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResDocumento objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.RES_DOCUMENTO (FOLIO, DESCRIPCION, DOCUMENTO, FOLIO_EXPEDIENTE, NOMBRE) "
					+ " VALUES(TO_NUMBER(?,'999999'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getFolio(), objeto.getDescripcion(), objeto.getDocumento(), objeto.getFolioExpediente(), objeto.getNombre()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ResDocumento objeto ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_DOCUMENTO" 
				+ " SET DESCRIPCION = ?,"
				+ " DOCUMENTO = ?,"
				+ " NOMBRE = ?"
				+ " WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {
				objeto.getDescripcion(),objeto.getDocumento(),objeto.getNombre(),objeto.getFolio()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio){
		boolean ok = false;
		try{
			String comando = "DELETE ENOC.RES_DOCUMENTO WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {
				folio
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|deleteReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteDocExpedienteReg(String expedienteId){
		boolean ok = false;
		try{
			String comando = "DELETE ENOC.RES_DOCUMENTO WHERE FOLIO_EXPEDIENTE = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {
				expedienteId
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|deleteDocExpedienteReg|:"+ex);		
		}
		
		return ok;
	}
	
	public ResDocumento mapeaRegId(String folio) {
		ResDocumento objeto = new ResDocumento();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_DOCUMENTO WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT FOLIO, DESCRIPCION, DOCUMENTO, FOLIO_EXPEDIENTE, NOMBRE"
						+ " FROM ENOC.RES_DOCUMENTO WHERE FOLIO = TO_NUMBER(?,'999999')";
				objeto = enocJdbc.queryForObject(comando, new ResDocumentoMapper(), parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResDocumentoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_DOCUMENTO WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeDocExpedienteReg(String expedienteId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_DOCUMENTO WHERE FOLIO_EXPEDIENTE = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {expedienteId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|existeDocExpedienteReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.RES_DOCUMENTO";			
			maximo = enocJdbc.queryForObject(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<ResDocumento> lisTodos( String orden ){
		List<ResDocumento> lista = new ArrayList<ResDocumento>();	
		try{
			String comando = "SELECT FOLIO, DESCRIPCION, FOLIO_EXPEDIENTE, NOMBRE FROM ENOC.RES_DOCUMENTO "+orden;
			lista = enocJdbc.query(comando, new ResDocumentoMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResDocumento> listaPorExpediente(String folioExpediente){
		List<ResDocumento> lista = new ArrayList<ResDocumento>();	
		try{
			String comando = "SELECT FOLIO, DESCRIPCION, DOCUMENTO, FOLIO_EXPEDIENTE, NOMBRE"
					+ " FROM ENOC.RES_DOCUMENTO WHERE FOLIO_EXPEDIENTE = ?"; 
			Object[] parametros = new Object[] {folioExpediente};
			lista = enocJdbc.query(comando, new ResDocumentoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|lisPorPeriodoRazon|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaCantidadDcoumentos(String codigoPersonal) {
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa>	lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando ="SELECT FOLIO_EXPEDIENTE AS LLAVE, COUNT(FOLIO_EXPEDIENTE) AS VALOR FROM ENOC.RES_DOCUMENTO WHERE FOLIO_EXPEDIENTE IN(SELECT FOLIO FROM ENOC.RES_EXPEDIENTE WHERE CODIGO_PERSONAL = ?) GROUP BY FOLIO_EXPEDIENTE";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),codigoPersonal);
			for (aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|mapaCantidadDcoumentos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaDocumentosEnExpediente() {
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa>	lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando ="SELECT FOLIO_EXPEDIENTE AS LLAVE, COUNT(FOLIO_EXPEDIENTE) AS VALOR FROM ENOC.RES_DOCUMENTO GROUP BY FOLIO_EXPEDIENTE";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDocumentoDao|mapaDocumentosEnExpediente|:"+ex);
		}		
		return mapa;
	}
}