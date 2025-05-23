package aca.cultural.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompEventoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	

	public CompEvento mapeaRegId( String eventoId) {
		CompEvento compEvento = new CompEvento();
		
		try{
		String comando = "SELECT "+
			" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO"+
			" FROM ENOC.COMP_EVENTO"+ 
			" WHERE EVENTO_ID = TO_NUMBER(?,'99')";
		
		Object[] parametros = new Object[] {eventoId};
		compEvento = enocJdbc.queryForObject(comando, new CompEventoMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|mapeaRegId|:"+ex);
		}
		return compEvento;
	}
	
	public boolean insertRegByte( CompEvento compEvento ) {
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.COMP_EVENTO"+ 
				"(EVENTO_ID, EVENTO_NOMBRE, FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO) "+
				"VALUES( TO_NUMBER(?,'99'),?, TO_DATE(?, 'DD/MM/YYYY HH24:MI'), ?, TO_NUMBER(?,'999'),?,?)";
			
			Object[] parametros = new Object[] {
					compEvento.getEventoId(), compEvento.getEventoNombre(), compEvento.getFecha(), compEvento.getDescripcion(), 
					compEvento.getCapacidad(), compEvento.getLugar(), compEvento.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|insertRegByte|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( CompEvento compEvento ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.COMP_EVENTO "+ 
				"SET EVENTO_NOMBRE = ?, "+
				"FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), "+
				"DESCRIPCION = ?, "+
				"CAPACIDAD = TO_NUMBER(?,'999'),"+
				"LUGAR = ?,"+
				"ESTADO = ?"+
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					compEvento.getEventoNombre(), compEvento.getFecha(), compEvento.getDescripcion(), compEvento.getCapacidad(),
					compEvento.getLugar(), compEvento.getEstado(), compEvento.getEventoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String eventoId ) {
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.COMP_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {eventoId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodo(String eventos ) {
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.COMP_EVENTO WHERE EVENTO_ID IN (?) ";
			
			Object[] parametros = new Object[] {eventos};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|deleteTodo|:"+ex);			
		}
		return ok;
	}

	public boolean existeReg( String eventoId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String eventoId) {
		String maximo			= "1";
		
		try{
			String comando = "SELECT TO_CHAR((MAX(TO_NUMBER(EVENTO_ID,'99')+1))) AS MAXIMO FROM ENOC.COMP_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,99) ";
			
			Object[] parametros = new Object[] {eventoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|maximoReg|:"+ex);
		}
		return maximo;
	}	
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(EVENTO_ID)+1 MAXIMO FROM ENOC.COMP_EVENTO";
			
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|maximoReg|:"+ex);
		}
		return maximo;		
	}
	
	public List<CompEvento> getListAll( String orden) {
		List<CompEvento> list		= new ArrayList<CompEvento>();
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
					  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO "+orden;	 
			
			list = enocJdbc.query(comando, new CompEventoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<CompEvento> getListActivos( String orden) {
		List<CompEvento> list		= new ArrayList<CompEvento>();
		String comando		= "";
			
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
					  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO WHERE ESTADO = '1' "+orden;	 
			
			list = enocJdbc.query(comando, new CompEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|getListActivos|:"+ex);
		}
		return list;
	}
	
	//Pendiente
	public HashMap <String,CompEvento> getMapEvento( String orden ) {
		
		HashMap<String,CompEvento> map = new HashMap<String,CompEvento>();
		List<CompEvento> list		= new ArrayList<CompEvento>();	
		String comando				= "";		
		
		try{
			comando = " SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, "+
					  " CAPACIDAD, LUGAR, ESTADO FROM ENOC.COMP_EVENTO "+orden;			
			list = enocJdbc.query(comando, new CompEventoMapper());	
			
			for (CompEvento obj : list){				
				map.put(obj.getEventoId(), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|getMapEvento|:"+ex);
		}
		
		return map;
	}
	
	public List<CompEvento> getListReservados( String orden, String eventoId) {
		List<CompEvento> list 		= new ArrayList<CompEvento>();
		String comando						= "";		
		try{
			comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO"
					+ " FROM ENOC.COMP_EVENTO"
					+ " WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.COMP_REGISTRO) "+orden;			
			list = enocJdbc.query(comando, new CompEventoMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoDao|getListReservados| "+ex);
		}
		return list;
	}
	
}