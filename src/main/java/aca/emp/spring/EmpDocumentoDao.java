// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpDocumentoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpDocumento documento ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_DOCUMENTO"+ 
				"(DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN) "+
				"VALUES(TO_NUMBER(?,'999'), ? ,TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
					documento.getDocumentoId(), documento.getDocumentoNombre(),documento.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpDocumento documento ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_DOCUMENTO"
				+ " SET DOCUMENTO_NOMBRE = ?,"
				+ " ORDEN = TO_NUMBER(?,'99')"
				+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
					documento.getDocumentoNombre(), documento.getDocumentoId(),documento.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean deleteReg( String documentoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EmpDocumento mapeaRegId( String documentoId ) {
		
		EmpDocumento objeto = new EmpDocumento();
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN"
					+ " FROM ENOC.EMP_DOCUMENTO"
					+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {documentoId};
			objeto = enocJdbc.queryForObject(comando, new EmpDocumentoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String documentoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String documentoId) {
		
		int  maximo = 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.EMP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {documentoId};
			maximo = enocJdbc.queryForObject(comando,Integer.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|maximoCurso|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String getDocumentoNombre( String documentoId) {
		
		String nombre = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT DOCUMENTO_NOMBRE FROM ENOC.EMP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|getDocumentoNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<EmpDocumento> lisTodos( String orden ) {
		
		List<EmpDocumento> lista	= new ArrayList<EmpDocumento>();
		String comando	= "";
		
		try{
			comando = " SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE,ORDEN "
					+ " FROM ENOC.EMP_DOCUMENTO "+orden;
			lista = enocJdbc.query(comando, new EmpDocumentoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpDocumento> lisEmpleado( String codigoPersonal, String orden ) {
		
		List<EmpDocumento> lista	= new ArrayList<EmpDocumento>();	
		try{
			String comando = " SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN "
					+ " FROM ENOC.EMP_DOCUMENTO "
					+ " WHERE DOCUMENTO_ID IN (SELECT DOCUMENTO_ID FROM ENOC.EMP_DOCEMP WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new EmpDocumentoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocumentoDao|lisEmpleado|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EmpDocumento> mapaTodos() {
		
		HashMap<String,EmpDocumento> mapa = new HashMap<String,EmpDocumento>();
		List<EmpDocumento> lista		= new ArrayList<EmpDocumento>();
		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN FROM ENOC.EMP_DOCUMENTO";			
			lista = enocJdbc.query(comando, new EmpDocumentoMapper());
			for (EmpDocumento documento : lista ) {
				mapa.put(documento.getDocumentoId(), documento);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapEstadoPorCarrera|:"+ex);
		}
		
		return mapa;
	}

}