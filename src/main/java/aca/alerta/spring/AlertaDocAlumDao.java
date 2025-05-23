package aca.alerta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaDocAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlertaDocAlum objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ALERTA_DOCALUM"
					+ " (CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, ARCHIVO, NOMBRE)"
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getPeriodoId(),objeto.getDocumentoId(), objeto.getArchivo(),objeto.getNombre()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String periodoId, String documentoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALERTA_DOCALUM WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, periodoId, documentoId};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AlertaDocAlum mapeaRegId(String codigoPersonal, String periodoId, String documentoId) {
		AlertaDocAlum objeto = new AlertaDocAlum();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, ARCHIVO, NOMBRE"
					+ " FROM ENOC.ALERTA_DOCALUM"
					+ " WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, periodoId, documentoId};
			objeto = enocJdbc.queryForObject(comando, new AlertaDocAlumMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId, String documentoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_DOCALUM WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, periodoId, documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AlertaDocAlum> lisTodos(String orden) {
		
		List<AlertaDocAlum> lista = new ArrayList<AlertaDocAlum>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, NOMBRE FROM ENOC.ALERTA_DOCALUM "+orden;
			lista = enocJdbc.query(comando, new AlertaDocAlumMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisTodos|:"+ex);
		}		
		return lista;
	}

	public List<AlertaDocAlum> lisTodosPorAlumno(String codigoAlumno, String periodoId, String orden){
		
		List<AlertaDocAlum> lista = new ArrayList<AlertaDocAlum>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, NOMBRE FROM ENOC.ALERTA_DOCALUM WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? "+orden;
			Object[] parametros = new Object[] {codigoAlumno, periodoId };
			lista = enocJdbc.query(comando, new AlertaDocAlumMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisTodosPorAlumno|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaAlumDocumento(String codigoAlumno, String periodoId){
		HashMap<String,String> mapa		= new HashMap<String, String>();
		List<AlertaDocAlum>	lista		= new ArrayList<AlertaDocAlum>();
		try{
			String comando ="SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, NOMBRE FROM ENOC.ALERTA_DOCALUM WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			lista = enocJdbc.query(comando, new AlertaDocAlumMapperCorto(), codigoAlumno, periodoId);
			for (AlertaDocAlum objeto : lista){
				mapa.put(objeto.getDocumentoId(), objeto.getNombre());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|mapaAlumDocumento|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String,String> mapaPorPeriodo(String periodoId){
		HashMap<String,String> mapa		= new HashMap<String, String>();
		List<AlertaDocAlum>	lista		= new ArrayList<AlertaDocAlum>();
		try{
			String comando ="SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, NOMBRE FROM ENOC.ALERTA_DOCALUM WHERE PERIODO_ID = ? AND ARCHIVO IS NOT NULL";
			lista = enocJdbc.query(comando, new AlertaDocAlumMapperCorto(), periodoId);
			for (AlertaDocAlum objeto : lista){
				mapa.put(objeto.getCodigoPersonal()+objeto.getDocumentoId(), objeto.getNombre());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|mapaPorPeriodo|:"+ex);
		}		
		return mapa;
	}
}
