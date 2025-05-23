//Clase para la tabla de Alum_Academico
package aca.graduacion.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumEventoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumEvento evento ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_EVENTO"+ 
				"(EVENTO_ID, EVENTO_NOMBRE, FECHA, ESTADO, ARCHIVO)"+
				" VALUES( TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
			Object[] parametros = new Object[] {evento.getEventoId(), evento.getEventoNombre(), evento.getFecha(), evento.getEstado(),evento.getArchivo()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( AlumEvento evento ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_EVENTO"+ 
				" SET EVENTO_NOMBRE = ?,"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?"+
				" WHERE EVENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { evento.getEventoNombre(), evento.getFecha(), evento.getEstado(), evento.getEventoId() };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String eventoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_EVENTO"+ 
				" WHERE EVENTO_ID = ?";
			Object[] parametros = new Object[] {Integer.parseInt(eventoId)};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AlumEvento mapeaRegId(  String evento_Id ) {
		
		AlumEvento objeto = new AlumEvento();		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {evento_Id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>= 1) {
				comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, ARCHIVO"
						+ " FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'999')";
				objeto = enocJdbc.queryForObject(comando, new AlumEventoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeReg( String eventoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {Integer.parseInt(eventoId)};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getFecha( String eventoId) {
		String fecha = aca.util.Fecha.getHoy();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {Integer.parseInt(eventoId)};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT TO_CHAR(FECHA,'DD/MM/YYYY') FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'999')";
				fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|getFecha|:"+ex);
		}
		return fecha;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(EVENTO_ID)+1,1) AS MAXIMO"+
				" FROM ENOC.ALUM_EVENTO";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
		
	public List<AlumEvento> getListAll( String orden ) {
		
		List<AlumEvento> lista = new ArrayList<AlumEvento>();		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO, ARCHIVO"+
				" FROM ENOC.ALUM_EVENTO "+orden;
			lista = enocJdbc.query(comando, new AlumEventoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEvento> getListProximo(String fecha, String orden ) {
		
		int value = Integer.parseInt(fecha) + 1;
		String fecha2 = Integer.toString(value);
		List<AlumEvento> lista = new ArrayList<AlumEvento>();
		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, FECHA, ESTADO, ARCHIVO"
				+ " FROM ENOC.ALUM_EVENTO"
				+ " WHERE TO_CHAR (FECHA,'YYYY') IN ('"+fecha+"','"+fecha2+"') "+orden;
			lista = enocJdbc.query(comando, new AlumEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEventoDao|getListProximo|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, AlumEvento> mapEventos(){		
		HashMap<String, AlumEvento> mapa				= new HashMap<String,AlumEvento>();
		List<AlumEvento> lista						= new ArrayList<AlumEvento>();		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO, ARCHIVO FROM ENOC.ALUM_EVENTO";
			lista = enocJdbc.query(comando, new AlumEventoMapper());
			for (AlumEvento evento : lista){
				mapa.put(evento.getEventoId(), evento);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEventoDao|mapEventos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapNombreEventos(){
		HashMap<String, String> mapa				= new HashMap<String,String>();
		List<aca.Mapa> lista						= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT EVENTO_ID AS LLAVE, EVENTO_NOMBRE AS VALOR FROM ENOC.ALUM_EVENTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEventoDao|getMapGraduados|:"+ex);
		}
		return mapa;
	}	
}