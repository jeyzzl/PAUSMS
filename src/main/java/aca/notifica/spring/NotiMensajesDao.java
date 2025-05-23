package aca.notifica.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotiMensajesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(NotiMensajes notiMensajes){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NOTI_MENSAJES"+ 
				"(ID, CODIGO_PERSONAL, TIPO, MENSAJE, "
				+ "URL, SILENCIADO, VISTO, FECHA, "
				+ "SMS) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, TO_NUMBER(?, '99'), ?, "
				+ "?, ?, ?, TO_DATE(?,'DD/MM/YYYY HH:MI:SS'), "
				+ "? )";
			Object[] parametros = new Object[] { 
					maximoReg(), 
					notiMensajes.getCodigoPersonal(), 
					notiMensajes.getTipo(), 
					notiMensajes.getMensaje(), 
					notiMensajes.getUrl(),
					notiMensajes.getSilenciado(),
					notiMensajes.getVisto(),
					notiMensajes.getFecha(),
					notiMensajes.getSms()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean insertRegNow(NotiMensajes notiMensajes){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NOTI_MENSAJES"+ 
				"(ID, CODIGO_PERSONAL, TIPO, MENSAJE, "
				+ "URL, SILENCIADO, VISTO, FECHA, "
				+ "SMS) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, TO_NUMBER(?, '99'), ?, "
				+ "?, ?, ?, SYSDATE, "
				+ "? )";
			Object[] parametros = new Object[] { 
					maximoReg(), 
					notiMensajes.getCodigoPersonal(), 
					notiMensajes.getTipo(), 
					notiMensajes.getMensaje(), 
					notiMensajes.getUrl(),
					notiMensajes.getSilenciado(),
					notiMensajes.getVisto(),
					notiMensajes.getSms()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|insertRegNow|:"+ex);			
	
		}
		return ok;
	}	
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.NOTI_MENSAJES";
			int resultado = enocJdbc.queryForObject(comando, Integer.class);
 			if (resultado >= 1) {
 				maximo = ((Integer)resultado).toString();
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|maximoReg|:"+ex);
		}
		return maximo;
	}

	public boolean updateReg(NotiMensajes notiMensajes){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.NOTI_MENSAJES SET"
					+ " MENSAJE = ?,"
					+ " URL = ?,"
					+ " SILENCIADO = ?,"
					+ " VISTO = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY HH:MI:SS'),"
					+ " SMS = ?"
					+ " WHERE ID = TO_NUMBER(?,'99999999')"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND TIPO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] { 
					notiMensajes.getMensaje(), 
					notiMensajes.getUrl(),
					notiMensajes.getSilenciado(),  
					notiMensajes.getVisto(),
					notiMensajes.getFecha(),
					notiMensajes.getSms(),
					notiMensajes.getId(),
					notiMensajes.getCodigoPersonal(),
					notiMensajes.getTipo() };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
	
	public boolean updateRegNow(NotiMensajes notiMensajes){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.NOTI_MENSAJES SET"
					+ " MENSAJE = ?,"
					+ " URL = ?,"
					+ " SILENCIADO = ?,"
					+ " VISTO = ?,"
					+ " FECHA = SYSDATE,"
					+ " SMS = ?"
					+ " WHERE ID = TO_NUMBER(?,'99999999')"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND TIPO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] { 
					notiMensajes.getMensaje(), 
					notiMensajes.getUrl(),
					notiMensajes.getSilenciado(),  
					notiMensajes.getVisto(),
					notiMensajes.getSms(),
					notiMensajes.getId(),
					notiMensajes.getCodigoPersonal(),
					notiMensajes.getTipo() };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|updateRegNow|:"+ex);		
	
		}
		return ok;
	}
		
	public boolean deleteReg(String id, String codigoPersonal){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NOTI_MENSAJES WHERE ID = TO_NUMBER(?,'99999999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {id, codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public NotiMensajes mapeaRegId(String  id){
		NotiMensajes notiMensajes = new NotiMensajes();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, MENSAJE, URL, SILENCIADO, VISTO, FECHA, SMS"
					+ " FROM ENOC.NOTI_MENSAJES"
					+ " WHERE ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {id};			
			notiMensajes = enocJdbc.queryForObject(comando, new NotiMensajesMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|mapeaRegId|:"+ex);
		
		}
		return notiMensajes;
	}	
	
	public NotiMensajes mapeaRegTipo(String  codigoPersonal, String tipo){
		NotiMensajes notiMensajes = new NotiMensajes();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, MENSAJE, URL, SILENCIADO, VISTO, FECHA, SMS"
					+ " FROM ENOC.NOTI_MENSAJES"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPO = TO_NUMBER(?,'99')"
					+ " AND ROWNUM = 1";
			Object[] parametros = new Object[] {codigoPersonal, tipo};			
			notiMensajes = enocJdbc.queryForObject(comando, new NotiMensajesMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|mapeaRegTipo|:"+ex);
		
		}
		return notiMensajes;
	}
	
	public NotiMensajes mapeaRegURL(String  codigoPersonal, String tipo, String url){
		NotiMensajes notiMensajes = new NotiMensajes();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, MENSAJE, URL, SILENCIADO, VISTO, FECHA, SMS"
					+ " FROM ENOC.NOTI_MENSAJES"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPO = TO_NUMBER(?,'99')"
					+ " AND URL = ?";
			Object[] parametros = new Object[] {codigoPersonal, tipo, url};			
			notiMensajes = enocJdbc.queryForObject(comando, new NotiMensajesMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|mapeaRegURL|:"+ex);
		
		}
		return notiMensajes;
	}
	
	public boolean existeRegId(String id){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NOTI_MENSAJES WHERE ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|existeRegId|:"+ex);

		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String tipo){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NOTI_MENSAJES" 
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, tipo};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public boolean existeRegURL(String codigoPersonal, String tipo, String url){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NOTI_MENSAJES" 
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND TIPO = TO_NUMBER(?,'99')"
				+ " AND URL = ?";
			Object[] parametros = new Object[] {codigoPersonal, tipo, url};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|existeRegURL|:"+ex);

		}
		return ok;
	}
	
	public List<NotiMensajes> lisPorPersona( String codigoPersonal, String orden ){
		
		List<NotiMensajes> lista	= new ArrayList<NotiMensajes>();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, MENSAJE, URL, SILENCIADO, VISTO, TO_CHAR (FECHA, 'DD/MM/YYYY HH:MM:SS') AS FECHA, SMS"
					+ " FROM ENOC.NOTI_MENSAJES WHERE CODIGO_PERSONAL = ? "+ orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new NotiMensajesMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|lisPorPersona|:"+ex);
		}		
		return lista;
	}	
	
	public List<NotiMensajes> lisSMSPendientes( String orden ){
		
		List<NotiMensajes> lista	= new ArrayList<NotiMensajes>();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, MENSAJE, URL, SILENCIADO, VISTO, TO_CHAR (FECHA, 'DD/MM/YYYY HH:MM:SS') AS FECHA, SMS"
					+ " FROM ENOC.NOTI_MENSAJES WHERE SMS = 'S' "+ orden;
			Object[] parametros = new Object[] {};
			lista = enocJdbc.query(comando, new NotiMensajesMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiMensajesDao|lisSMSPendientes|:"+ex);
		}
		return lista;
	}	
}