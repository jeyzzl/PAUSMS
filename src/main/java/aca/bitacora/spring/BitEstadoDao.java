package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitEstadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitEstado estado) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_ESTADO"+ 
				"(ESTADO, ESTADO_NOMBRE)"+
				" VALUES(TO_NUMBER(?, '99'), ?)";
			Object[] parametros = new Object[] {estado.getEstado(), estado.getEstadoNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean existeReg(String estado) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_ESTADO WHERE ESTADO = ?";
			Object[] parametros = new Object[] {estado};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEstadoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public boolean updateReg( BitEstado estado) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_ESTADO"+ 
				" SET ESTADO_NOMBRE = ? " +		
				" WHERE ESTADO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {estado.getEstadoNombre(), estado.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String tramiteId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_ESTADO WHERE ESTADO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BitEstado mapeaRegId(String estadoId) {
		
		BitEstado objeto = new BitEstado();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_ESTADO WHERE ESTADO = ?";
			if (enocJdbc.queryForObject(comando,Integer.class,estadoId)>=1){
				comando = " SELECT ESTADO, ESTADO_NOMBRE FROM ENOC.BIT_ESTADO WHERE ESTADO = ?";
				objeto = enocJdbc.queryForObject(comando, new BitEstadoMapper(),estadoId);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public String getEstadoNombre(  String estadoId) {
		String nombre = "-";	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_ESTADO WHERE ESTADO = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando,Integer.class,estadoId)>=1){
				comando = " SELECT ESTADO_NOMBRE FROM ENOC.BIT_ESTADO WHERE ESTADO = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando,String.class,estadoId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|getEstadoNombre|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
	}
	
	public List<BitEstado> lisEstados( String orden) {
		
		List<BitEstado> lista = new ArrayList<BitEstado>();
		
		try{
			String comando = "SELECT ESTADO, ESTADO_NOMBRE FROM BIT_ESTADO "+orden;
			lista = enocJdbc.query(comando, new BitEstadoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|LisEstados|:"+ex);
		}
		return lista;
	}
	
	public List<BitEstado> lisEstados( String estados, String orden) {
		
		List<BitEstado> lista 	= new ArrayList<BitEstado>();
		
		try{
			String comando = " SELECT ESTADO, ESTADO_NOMBRE FROM BIT_ESTADO WHERE ESTADO IN ("+estados+") "+orden;
			lista = enocJdbc.query(comando, new BitEstadoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|lisEstados|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando =  "SELECT COALESCE(MAX(ESTADO)+1,1) AS MAXIMO FROM ENOC.BIT_ESTADO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
 			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.BitEstadoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public HashMap<String, String> mapEstados() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ESTADO AS LLAVE, ESTADO_NOMBRE AS VALOR FROM ENOC.BIT_ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoDao|mapEstados|:"+ex);
		}
		return mapa;
	}

}
