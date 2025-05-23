package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorDocumento porDocumento) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO DANIEL.POR_DOCUMENTO(DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO) VALUES( ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				porDocumento.getDocumentoId(),porDocumento.getDocumentoNombre(),porDocumento.getUsuario(),porDocumento.getArchivo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorDocumento porDocumento) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_DOCUMENTO SET DOCUMENTO_NOMBRE = ?, USUARIO = ?, ARCHIVO = ? WHERE DOCUMENTO_ID = ? ";
			
			Object[] parametros = new Object[] {
				porDocumento.getDocumentoNombre(),porDocumento.getUsuario(),porDocumento.getArchivo(),porDocumento.getDocumentoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String documentoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ?";
			
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public PorDocumento mapeaRegId(String documentoId) {
		PorDocumento objeto = new PorDocumento();
		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO" +
					" FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ?"; 
			
			Object[] parametros = new Object[] {documentoId};
			objeto = enocJdbc.queryForObject(comando, new PorDocumentoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

		return objeto;
	}
	
	public boolean existeReg(String documentoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ?"; 

			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String getNombre(String documentoId) {
		String nombre = "x";
		
		try{
			String comando = "SELECT DOCUMENTO_NOMBRE FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ? ";
			
			Object[] parametros = new Object[] {documentoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getArchivo(String documentoId) {
		String nombre = "x";
		
		try{
			String comando = "SELECT ARCHIVO FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ? ";
			
			Object[] parametros = new Object[] {documentoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<PorDocumento> getListAll(String orden ) {
		List<PorDocumento> lista = new ArrayList<PorDocumento>();
	 
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO "+
					"FROM DANIEL.POR_DOCUMENTO "+orden; 
			
			lista = enocJdbc.query(comando, new PorDocumentoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|getListAll|:"+ex);
		}
	
		return lista;
	}
	
	public List<PorDocumento> getListSoloEmpleados(String orden ) {
		List<PorDocumento> lista = new ArrayList<PorDocumento>();
	 
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO "+
					"FROM DANIEL.POR_DOCUMENTO WHERE USUARIO = '*' "+orden; 

			lista = enocJdbc.query(comando, new PorDocumentoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|getListSoloEmpleados|:"+ex);
		}
	
		return lista;
	}
	
	public List<PorDocumento> listDisponibles(String periodoId, String codigoPersonal, String orden ) {
		List<PorDocumento> lista = new ArrayList<PorDocumento>();
	 
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO FROM DANIEL.POR_DOCUMENTO "
					+ " WHERE DOCUMENTO_ID NOT IN "
					+ "	(SELECT DOCUMENTO_ID FROM DANIEL.POR_EMPDOCTO WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?)"
					+ " AND DOCUMENTO_ID  NOT IN (SELECT DOCUMENTO_ID FROM DANIEL.POR_NIVEL WHERE DOCUMENTO_ID IS NOT NULL) "+orden; 

			Object[] parametros = new Object[] {
				periodoId,codigoPersonal
			};
			
			lista = enocJdbc.query(comando, new PorDocumentoMapper(),parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|listDisponibles|:"+ex);
		}
	
		return lista;
	}
	
	public HashMap<String,PorDocumento> getMapAll(String orden ) {
		HashMap<String,PorDocumento> mapa = new HashMap<String,PorDocumento>();
		List<PorDocumento> lista = new ArrayList<PorDocumento>();
		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO"+
					" FROM DANIEL.POR_NIVEL "+ orden;
			
			lista = enocJdbc.query(comando, new PorDocumentoMapper());
			
			for(PorDocumento documento : lista) {
				mapa.put(documento.getDocumentoId(), documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
}
