package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitSolicitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitSolicitud objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BIT_SOLICITUD"+ 
				"(CODIGO_PERSONAL, FOLIO, TRAMITE_ID, STATUS, TEXTO_ALUMNO, TEXTO_ADMIN, FECHA, FOLIO_TRAMITE)"+
				" VALUES(?, TO_NUMBER(?,'99'), TO_NUMBER(?,'9999'), ?, ?, ?,TO_DATE(?,'DD/MM/YYYY'),?)";
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getFolio(),objeto.getTramiteId(),objeto.getStatus(),objeto.getTextoAlumno(),objeto.getTextoAdmin(),objeto.getFecha(),objeto.getFolioTramite()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( BitSolicitud objeto) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_SOLICITUD "
					+ " SET STATUS = ?, "
					+ " TRAMITE_ID = TO_NUMBER(?, '999'),"
					+ " TEXTO_ALUMNO = ?, "
					+ " TEXTO_ADMIN = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FOLIO_TRAMITE = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {
				objeto.getStatus(),objeto.getTramiteId(),objeto.getTextoAlumno(),objeto.getTextoAdmin(),objeto.getFecha(),objeto.getFolioTramite(),objeto.getCodigoPersonal(),objeto.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}

	public boolean existeFolioTramiteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_SOLICITUD WHERE FOLIO_TRAMITE = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|existeFolioTramiteReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public boolean updateFolioTramite(String folioTramite) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_SOLICITUD "
					+ " SET FOLIO_TRAMITE = '0'"
					+ " WHERE FOLIO_TRAMITE = ?";
			Object[] parametros = new Object[] {
					folioTramite
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|updateFolioTramite|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteFolioTramiteReg(String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_SOLICITUD WHERE FOLIO_TRAMITE = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|deleteFolioTramiteReg|:"+ex);			
		}
		return ok;
	}
	
	public BitSolicitud mapeaRegId(String codigoPersonal, String folio) {
		BitSolicitud objeto = new BitSolicitud();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, TRAMITE_ID, STATUS, TEXTO_ALUMNO, TEXTO_ADMIN, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, FOLIO_TRAMITE"
						+ " FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal,folio};
			objeto = enocJdbc.queryForObject(comando, new BitSolicitudMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public List<BitSolicitud> lisSolicitudes(String codigoPersonal, String orden) {
		
		List<BitSolicitud> lista = new ArrayList<BitSolicitud>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, TRAMITE_ID, STATUS, TEXTO_ALUMNO, TEXTO_ADMIN, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, FOLIO_TRAMITE"
					+ " FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ? AND STATUS IN ('1','2','3') "+orden;
			lista = enocJdbc.query(comando, new BitSolicitudMapper(),codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|lisSolicitudes|:"+ex);
		}
		return lista;
	}
	
	public List<BitSolicitud> lisTodasSolicitudes(String orden) {
		
		List<BitSolicitud> lista = new ArrayList<BitSolicitud>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, TRAMITE_ID, STATUS, TEXTO_ALUMNO, TEXTO_ADMIN, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, FOLIO_TRAMITE"
					+ " FROM ENOC.BIT_SOLICITUD "+orden;
			lista = enocJdbc.query(comando, new BitSolicitudMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|lisTodasSolicitudes|:"+ex);
		}
		return lista;
	}
	
	public List<BitSolicitud> lisPorEstado(String estado, String orden){
		List<BitSolicitud> lista = new ArrayList<BitSolicitud>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, TRAMITE_ID, STATUS, TEXTO_ALUMNO, TEXTO_ADMIN, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, FOLIO_TRAMITE"
					+ " FROM ENOC.BIT_SOLICITUD "
					+ " WHERE STATUS = ? "+orden;
			lista = enocJdbc.query(comando, new BitSolicitudMapper(), estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|lisPorEstado|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg(String codigoPersonal) {
		String maximo = "1";		
		try{
			String comando =  "SELECT COALESCE(MAX(FOLIO)+1,1) AS FOLIO FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ?";
			maximo = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitSolicitudDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public HashMap<String,String> mapaBitSolicitudPorAlumno(String codigoPersonal) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO_TRAMITE AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ? GROUP BY FOLIO_TRAMITE";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitSolicitudDao|mapaBitSolicitudPorAlumno|:"+ex);
		}
		
		return mapa;
	}
}
