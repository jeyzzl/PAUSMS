package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class MentEncuestaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentEncuesta encuesta ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.MENT_ENCUESTA( ENCUESTA_ID, NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "+
				"VALUES( ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ?)";
			Object[] parametros = new Object[] {encuesta.getEncuestaId(), encuesta.getNombre(),
					encuesta.getFechaIni(), encuesta.getFechaFin(), encuesta.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( MentEncuesta encuesta ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MENT_ENCUESTA "+ 
				"SET NOMBRE = ?, FECHA_INI = TO_DATE(?,'YYYY-MM-DD'), FECHA_FIN = TO_DATE(?,'YYYY-MM-DD'), ESTADO = ? WHERE ENCUESTA_ID = ?";
			Object[] parametros = new Object[] {encuesta.getNombre(), encuesta.getFechaIni(),
			encuesta.getFechaFin(), encuesta.getEstado(), encuesta.getEncuestaId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String encuestaId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.MENT_ENCUESTA WHERE ENCUESTA_ID = ?";
			Object[] parametros = new Object[] {encuestaId};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public MentEncuesta mapeaRegId(String encuestaId) {
		MentEncuesta encuesta = new MentEncuesta();
		
		try{
			String comando = "SELECT ENCUESTA_ID, NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FIN,'YYYY-MM-DD') AS FECHA_FIN, ESTADO "
					+ " FROM ENOC.MENT_ENCUESTA WHERE ENCUESTA_ID = ?";
			Object[] parametros = new Object[] {encuestaId};
			encuesta = enocJdbc.queryForObject(comando, new MentEncuestaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return encuesta;
	}
	
	public boolean existeReg( String encuestaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ENCUESTA WHERE ENCUESTA_ID = ?";
			Object[] parametros = new Object[] {encuestaId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
//	public boolean contestoEncuesta(String codigoPersonal, String encuestaId) {
//		boolean ok = false;
//		
//		try{
//			String comando = "SELECT COUNT(*) FROM MENT_ENCUESTA WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN AND ESTADO = 'A' AND"
//					+ " ENCUESTA_ID = (SELECT ENCUESTA_ID FROM MENT_RESPUESTA WHERE CODIGO_PERSONAL = ? AND PREGUNTA_ID = ?)";
//			Object[] parametros = new Object[] {codigoPersonal,encuestaId};
//			
//			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
//				ok = true;
//			}
//			
//		}catch(Exception ex){
//			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|contestoEncuesta|:"+ex);
//		}
//		
//		return ok;
//	}
	
	public String maximoReg() {
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(ENCUESTA_ID)+1,1) FROM ENOC.MENT_ENCUESTA";	
			if (enocJdbc.queryForObject(comando, Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String getEncuestaActiva() {		
		String encuesta = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ENCUESTA WHERE ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				comando = "SELECT ENCUESTA_ID FROM ENOC.MENT_ENCUESTA WHERE ESTADO = 'A'";
				encuesta = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|getEncuestaActiva|:"+ex);
		}		
		return encuesta;
	}
	
	public String getEncuesta( String fecha) {
		
		String encuesta = "x";		
		try{
			String comando = "SELECT ENCUESTA_ID AS ENCUESTA FROM ENOC.MENT_ENCUESTA WHERE TO_DATE(?,'DD-MM-YY') BETWEEN FECHA_INI AND FECHA_FIN";
			encuesta = enocJdbc.queryForObject(comando, String.class, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|getEncuesta|:"+ex);
		}		
		return encuesta;
	}
	
	public String getNombre( String encuesta) {
		
		String nombre = "x";		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.MENT_ENCUESTA WHERE ENCUESTA_ID = ?";
			nombre = enocJdbc.queryForObject(comando, String.class, encuesta);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|getNombre|:"+ex);
		}		
		return nombre;
	}
	
	
	public List<MentEncuesta> getListAll( String orden ) {
	
		List<MentEncuesta> lista = new ArrayList<MentEncuesta>();
		
		try{
			String comando = "SELECT ENCUESTA_ID, NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FIN,'YYYY-MM-DD') AS FECHA_FIN, ESTADO " +
					"FROM ENOC.MENT_ENCUESTA "+orden;
			lista = enocJdbc.query(comando, new MentEncuestaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,MentEncuesta> getMapAll( String orden ) {
		
		HashMap<String,MentEncuesta> mapa = new HashMap<String,MentEncuesta>();
		List<MentEncuesta> lista = new ArrayList<MentEncuesta>();
		
		try{
			String comando = "SELECT ENCUESTA_ID, NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FINAL,'YYYY-MM-DD') AS FECHA_FIN, ESTADO"+
					"FROM ENOC.MENT_ENCUESTA "+ orden;
			lista = enocJdbc.query(comando, new MentEncuestaMapper());
			
			for(MentEncuesta objeto:lista){
				mapa.put(objeto.getEncuestaId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentEncuestaDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
}