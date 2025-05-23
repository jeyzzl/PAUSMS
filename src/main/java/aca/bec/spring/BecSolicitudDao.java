package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecSolicitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BecSolicitud objeto) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.BEC_SOLICITUD"
					+ " (CODIGO_PERSONAL, FOLIO, POR_COORDINADOR, COORDINADOR, POR_COMISION, USUARIO, COMENTARIO, FECHA, COM_COORDINADOR, PERIODO_ID)"
					+ " VALUES(?, TO_NUMBER(?, '999'), TO_NUMBER(?, '99999.99'), ?, TO_NUMBER(?, '99999.99'), ?, ?, TO_DATE(?,'DD/MM/YYYY'),?,?)";
			
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(), objeto.getFolio(), objeto.getPorCoordinador(), objeto.getCoordinador(), objeto.getPorComision(), 
				objeto.getUsuario(), objeto.getComentario(), objeto.getFecha(), objeto.getComCoordinador(), objeto.getPeriodoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg(BecSolicitud objeto) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_SOLICITUD"
					+ " SET POR_COORDINADOR = TO_NUMBER(?, '99999.99'), COORDINADOR = ?, POR_COMISION = TO_NUMBER(?, '99999.99'), USUARIO = ?, COMENTARIO = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), COM_COORDINADOR = ?, PERIODO_ID = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {
				objeto.getPorCoordinador(),objeto.getCoordinador(), objeto.getPorComision(), objeto.getUsuario(), objeto.getComentario(), objeto.getFecha(), objeto.getComCoordinador(),
				objeto.getPeriodoId(), objeto.getCodigoPersonal(), objeto.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.BEC_SOLICITUD"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String codigoPersonal, String folio) {
 		String maximo = "1";
 		
 		try{
 			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.BEC_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
 			Object[] parametros = new Object[] {codigoPersonal, folio};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class,parametros);
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecSolicitudDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public BecSolicitud mapeaRegId(String codigoPersonal, String folio) {
		
		BecSolicitud objeto = new BecSolicitud();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, POR_COORDINADOR, COORDINADOR, POR_COMISION, USUARIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COM_COORDINADOR, PERIODO_ID"
					+ " FROM ENOC.BEC_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')"; 
			Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new BecSolicitudMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public List<BecSolicitud> getListAll(String orden) {
		
		List<BecSolicitud> lista = new ArrayList<BecSolicitud>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, POR_COORDINADOR, COORDINADOR, POR_COMISION, USUARIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COM_COORDINADOR, PERIODO_ID"
					+ " FROM ENOC.BEC_SOLICITUD "+orden;
			lista = enocJdbc.query(comando, new BecSolicitudMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, BecSolicitud> mapaSolicitudes() {
		HashMap<String,BecSolicitud> mapa	= new HashMap<String, BecSolicitud>();
		List<BecSolicitud>	lista		= new ArrayList<BecSolicitud>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, FOLIO, POR_COORDINADOR, COORDINADOR, POR_COMISION, USUARIO, COMENTARIO, FECHA, COM_COORDINADOR, PERIODO_ID"
					+ " FROM ENOC.BEC_SOLICITUD ";
			lista = enocJdbc.query(comando, new BecSolicitudMapper());
			for (BecSolicitud objeto : lista){
				mapa.put(objeto.getCodigoPersonal()+objeto.getFolio(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolicitudDao|mapaSolicitudes|:"+ex);
		}		
		return mapa;
	}
		
}
