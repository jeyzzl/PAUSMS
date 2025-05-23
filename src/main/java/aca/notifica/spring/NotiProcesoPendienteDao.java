package aca.notifica.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotiProcesoPendienteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(NotiProcesoPendiente notiProcesoPendiente){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NOTI_PROCESO_PENDIENTE"+ 
				"(ID, CODIGO_PERSONAL, TIPO, FECHA_INICIO, "
				+ "DATOS) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, TO_NUMBER(?, '99'), SYSDATE, "
				+ "? )";
			Object[] parametros = new Object[] { 
					maximoReg(), 
					notiProcesoPendiente.getCodigoPersonal(), 
					notiProcesoPendiente.getTipo(),
					notiProcesoPendiente.getDatos()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.NOTI_PROCESO_PENDIENTE";
			int resultado = enocJdbc.queryForObject(comando, Integer.class);
 			if (resultado >= 1) {
 				maximo = ((Integer)resultado).toString();
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|maximoReg|:"+ex);
		}
		return maximo;
	}

	public boolean updateReg(NotiProcesoPendiente notiProcesoPendiente){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.NOTI_PROCESO_PENDIENTE SET"
					+ " TIPO = ?,"
					+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY HH:MM:SS'),"
					+ " DATOS = ?"
					+ " WHERE ID = TO_NUMBER(?,'99999999')"
					+ " AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { 
					notiProcesoPendiente.getTipo(),
					notiProcesoPendiente.getFechaInicio(),
					notiProcesoPendiente.getDatos(),
					notiProcesoPendiente.getId(),
					notiProcesoPendiente.getCodigoPersonal()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
		
	public boolean deleteReg(String id){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NOTI_PROCESO_PENDIENTE WHERE ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public NotiProcesoPendiente mapeaRegId(String  id ){
		NotiProcesoPendiente notiProcesoPendiente = new NotiProcesoPendiente();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, FECHA_INICIO, DATOS"
					+ " FROM ENOC.NOTI_PROCESO_PENDIENTE"
					+ " WHERE ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {id};			
			notiProcesoPendiente = enocJdbc.queryForObject(comando, new NotiProcesoPendienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|mapeaRegId|:"+ex);
		}
		return notiProcesoPendiente;
	}	
	
	public boolean existeReg(String id, String codigoPersonal, String tipo){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NOTI_PROCESO_PENDIENTE" 
				+ " WHERE ID = TO_NUMBER(?,'99999999')"
				+ " AND CODIGO_PERSONAL = ? "
				+ " AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {id, codigoPersonal, tipo};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public List<NotiProcesoPendiente> lisAll( String orden ){
		
		List<NotiProcesoPendiente> lista	= new ArrayList<NotiProcesoPendiente>();		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, TIPO, TO_CHAR (FECHA_INICIO, 'DD/MM/YYYY HH:MM:SS') AS FECHA_INICIO, DATOS"
					+ " FROM ENOC.NOTI_PROCESO_PENDIENTE "+ orden;
			Object[] parametros = new Object[] {};
			lista = enocJdbc.query(comando, new NotiProcesoPendienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.notifica.spring.NotiProcesoPendienteDao|lisAll|:"+ex);
		}		
		return lista;
	}	
}