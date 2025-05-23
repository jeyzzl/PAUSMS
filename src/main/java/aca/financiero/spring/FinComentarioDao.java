package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinComentarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinComentario com ) {
		boolean ok = false;		
		try{
			String comando ="INSERT INTO ENOC.FIN_COMENTARIO" + 
					" (CODIGO_PERSONAL, FOLIO, COMENTARIO, FECHA, USUARIO, TIPO)" +
					" VALUES(?, TO_NUMBER(?, '999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ? )";
			Object[] parametros = new Object[] { com.getCodigoPersonal(),com.getFolio(),com.getComentario(),com.getFecha(),com.getUsuario(),com.getTipo() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( FinComentario com ) {
		boolean ok = false;		
		try{
			String comando ="UPDATE ENOC.FIN_COMENTARIO"
					+ " SET COMENTARIO = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?,"
					+ " TIPO = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { com.getComentario(),com.getFecha(),com.getUsuario(),com.getTipo(), com.getCodigoPersonal(),com.getFolio() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		
		boolean ok = false;		
		try{
			String comando ="DELETE FROM ENOC.FIN_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] { codigoPersonal, folio };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public FinComentario mapeaRegId(String codigoPersonal, String folio) {
		
		FinComentario com = new FinComentario();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO"
					+ " FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { codigoPersonal, folio };
			com = enocJdbc.queryForObject(comando, new FinComentarioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|mapeaRegId|:"+ex);
		}
		
		return com;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok 			= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { codigoPersonal, folio };	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getComentario( String codigoPersonal, String folio) {	
		
		String comentario		= "-";
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.FIN_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { codigoPersonal, folio };	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando ="SELECT COMENTARIO FROM ENOC.FIN_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
				comentario = enocJdbc.queryForObject(comando, String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|existeReg|:"+ex);
		}
		
		return comentario;
	}
	
	public boolean tieneComentario( String codigoPersonal, String fechaIni, String fechaFin) {
		
		boolean ok				= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TO_DATE(TO_CHAR(FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
			Object[] parametros = new Object[] { codigoPersonal, fechaIni, fechaFin };				
			if( enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String codigoPersonal) {
		int maximo 	= 1;		
		try{
			String comando ="SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal };			
			maximo = enocJdbc.queryForObject(comando, Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<FinComentario> getListAll( String orden ) {
		
		List<FinComentario> lista = new ArrayList<FinComentario>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO FROM ENOC.FIN_COMENTARIO "+ orden; 
			
			lista = enocJdbc.query(comando, new FinComentarioMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<FinComentario> lisAlumnoComentarios( String codigoPersonal, String year, String orden ) {
		
		List<FinComentario> lista = new ArrayList<FinComentario>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TO_CHAR(FECHA,'YYYY') = ? "+ orden;
			lista = enocJdbc.query(comando, new FinComentarioMapper(), codigoPersonal, year);	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|lisAlumnoComentarios|:"+ex);
		}
		
		return lista;
	}
	
}
