// CLASE DE LA TABLA CONV_SOLICITUD
package aca.convenio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConArchivoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ConArchivo objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CON_ARCHIVO"
					+ " (CONVENIO_ID, FOLIO, NOMBRE, ARCHIVO) VALUES(TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), ?, ?)";			
			Object[] parametros = new Object[] {objeto.getConvenioId(), objeto.getFolio(), objeto.getNombre(), objeto.getArchivo() };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg(ConArchivo objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CON_ARCHIVO "
				+ " SET "
				+ " NOMBRE = ?, "
				+ " ARCHIVO = ? "
				+ " WHERE CONVENIO_ID = TO_NUMBER(?,'999') AND FOLIO = ?";		
			Object[] parametros = new Object[] {objeto.getNombre(), objeto.getArchivo(), objeto.getConvenioId(),objeto.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|updateReg|:"+ex);		
		}		
		return ok;
	}

	public boolean existeReg(String idConvenio, String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CON_ARCHIVO WHERE CONVENIO_ID = TO_NUMBER(?,'999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {idConvenio,folio};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){			
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|existeReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean deleteReg(String convenioId, String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CON_ARCHIVO WHERE CONVENIO_ID = TO_NUMBER(?,'999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {convenioId, folio};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public ConArchivo mapeaRegId(String convenioId, String folio ) {
		ConArchivo objeto = new ConArchivo();		
		try{
			String comando = "SELECT CONVENIO_ID, FOLIO, NOMBRE, ARCHIVO "
					+ " FROM ENOC.CON_ARCHIVO WHERE CONVENIO_ID = TO_NUMBER(?,'999') AND FOLIO = ?";			
			Object[] parametros = new Object[] {convenioId, folio};
			objeto = enocJdbc.queryForObject(comando, new ConArchivoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|mapeaRegId|:"+ex);
		}		
		return objeto;		
	}
	
	public List<ConArchivo> lisPorConvenio( String convenioId, String orden ){
		List<ConArchivo> lista	= new ArrayList<ConArchivo>();
		try{
			String comando = "SELECT CONVENIO_ID, FOLIO, NOMBRE FROM ENOC.CON_ARCHIVO WHERE CONVENIO_ID = TO_NUMBER(?,'999') "+orden;
			lista = enocJdbc.query(comando, new ConArchivoMapperCorto(), convenioId);
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvTipoDao|lisPorConvenio|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String,ConArchivo> mapaConArchivo() {
		HashMap<String,ConArchivo> mapa = new HashMap<String,ConArchivo>();
		List<ConArchivo> lista 	= new ArrayList<ConArchivo>();		
		try{
			String comando = "SELECT CONVENIO_ID, FOLIO, NOMBRE FROM ENOC.CON_ARCHIVO";			
			lista = enocJdbc.query(comando, new ConArchivoMapperCorto());
			for(ConArchivo objeto : lista){				
				mapa.put(objeto.getConvenioId()+objeto.getFolio(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|mapaConArchivo|:"+ex);
		}		
		return mapa;		
	}
	
	public HashMap<String,String> mapaConArchivos() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CONVENIO_ID||FOLIO AS LLAVE, NOMBRE AS VALOR FROM ENOC.CON_ARCHIVO WHERE ARCHIVO IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.convenioarchivo.spring.ConvArchivoDao|mapaConArchivo|:"+ex);
		}
		
		return mapa;		
	}
	
}