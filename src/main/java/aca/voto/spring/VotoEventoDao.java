package aca.voto.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */
@Component
public class VotoEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( VotoEvento voto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO"
				+ " ENOC.VOTO_EVENTO(EVENTO_ID, EVENTO_NOMBRE, FECHA_INI, FECHA_FIN, TIPO, POBLACION FROM ENOC.VOTO_EVENTO)"
				+ " VALUES(TO_NUMBER(?,'9999'),?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ? )";
			
			Object[] parametros = new Object[] {voto.getEventoId(),voto.getEventoNombre(),voto.getFechaIni(),voto.getFechaFin(),voto.getTipo(), voto.getPoblacion()};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( VotoEvento voto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.VOTO_EVENTO"
				+ " SET  EVENTO_NOMBRE = ?,"
				+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
				+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"
				+ " TIPO = ?,"
				+ " POBLACION = ?"
				+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			
			Object[] parametros = new Object[] {voto.getEventoNombre(),voto.getFechaIni(),voto.getFechaFin(),voto.getTipo(), voto.getPoblacion(), voto.getEventoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String eventoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VotoEvento mapeaRegId( String eventoId ){
		
		VotoEvento votoEvento = new VotoEvento();
		
		try{
			String comando = "SELECT"+
					" EVENTO_ID, EVENTO_NOMBRE, FECHA_INI, FECHA_FIN, TIPO, POBLACION FROM ENOC.VOTO_EVENTO"+
					" WHERE EVENTO_ID = ?";
			Object[] parametros = new Object[] {eventoId};
			votoEvento = enocJdbc.queryForObject(comando, new VotoEventoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		
		}	
		
		return votoEvento;
	}
	
	public boolean existeReg( String eventoId){
		
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}	
	
	public String eventoActivo( String fecha){
		
		String eventoId				= "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_EVENTO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			Object[] parametros = new Object[] {fecha};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT EVENTO_ID FROM ENOC.VOTO_EVENTO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";			
				eventoId = enocJdbc.queryForObject(comando,String.class, parametros);
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|eventoActivo|:"+ex);
		
		}
		
		return eventoId;
	}
	
	public String eventoNombre( String eventoId){
		
		String eventoNombre		= "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT EVENTO_NOMBRE FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
				eventoNombre = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|eventoNombre|:"+ex);		
		}		
		return eventoNombre;
	}
	
	public String getFechaFin( String eventoId){		
		String fechaFin		= "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT TO_CHAR(FECHA_FIN,'DD/MM/YYYY HH24:MI:SS') FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
				fechaFin = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|eventoNombre|:"+ex);		
		}		
		return fechaFin;
	}
	
	public String getPoblacion( String eventoId){
		
		String poblacion		= "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT POBLACION FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
				poblacion = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|eventoNombre|:"+ex);		
		}		
		return poblacion;
	}
	
	public String getTipo( String eventoId){		
		String tipo		= "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT TIPO FROM ENOC.VOTO_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
				tipo = enocJdbc.queryForObject(comando,String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|getTipo|:"+ex);		
		}		
		return tipo;
	}
	
	// LISTADO DE EVENTOS
	public List<VotoEvento> getLista( String orden ){		
		List<VotoEvento> lista	= new ArrayList<VotoEvento>();
		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, "
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO, POBLACION"
					+ " FROM ENOC.VOTO_EVENTO "+orden;
			lista = enocJdbc.query(comando, new VotoEventoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|getLista|:"+ex);
		}
	
		return lista;	
	}
	
	public List<VotoEvento> getListEventosActuales(String orden ){
		
		List<VotoEvento> lisEvento = new ArrayList<VotoEvento>();
		try{
			String comando = " SELECT"
					+ " EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO, POBLACION"
					+ " FROM ENOC.VOTO_EVENTO"
					+ " WHERE TO_DATE(TO_CHAR(NOW(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN "+orden; 
			
			lisEvento = enocJdbc.query(comando, new VotoEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.VotoEventoDao|getListEventosActuales|:"+ex);
		}
		
		return lisEvento;
	}
	
}