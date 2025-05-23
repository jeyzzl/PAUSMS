// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpRangoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpRango rango ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_RANGO"+ 
				"(RANGO_ID, RANGO_NOMBRE, MINIMO, MAXIMO, PRECIO_MIN, PRECIO_MAX) "+
				"VALUES(TO_NUMBER(?,'9'), ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), "
				+ " TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99')";
			Object[] parametros = new Object[] {
					rango.getRangoId(), rango.getRangoNombre(), rango.getMinimo(),
					rango.getMaximo(), rango.getPrecioMin(), rango.getPrecioMax()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpRango rango ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_RANGO"
				+ " SET "
				+ " RANGO_NOMBRE = ?,"
				+ " MINIMO = TO_NUMBER(?,'999'),"
				+ " MAXIMO = TO_NUMBER(?,'999'),"
				+ " PRECIO_MIN = TO_NUMBER(?,'99999.99'),"
				+ " PRECIO_MAX = TO_NUMBER(?,'99999.99')"
				+ " WHERE RANGO_ID = TO_NUMBER(?,'9')";
			Object[] parametros = new Object[] {
					rango.getRangoNombre(), rango.getMinimo(), rango.getMaximo(),
					rango.getPrecioMin(), rango.getPrecioMax(), rango.getRangoId()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean deleteReg( String rangoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_RANGO WHERE RANGO_ID = TO_NUMBER(?,'9')";
			Object[] parametros = new Object[] {rangoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EmpRango mapeaRegId( String rangoId ) {
		
		EmpRango objeto = new EmpRango();
		try{
			String comando = "SELECT RANGO_ID, RANGO_NOMBRE, MINIMO, MAXIMO, PRECIO_MIN, PRECIO_MAX"
					+ " FROM ENOC.EMP_RANGO"
					+ " WHERE RANGO_ID = TO_NUMBER(?,'9')";
			Object[] parametros = new Object[] {rangoId};
			objeto = enocJdbc.queryForObject(comando, new EmpRangoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String rangoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_HORAS WHERE RANGO_ID = TO_NUMBER(?,'9')";
			Object[] parametros = new Object[] {rangoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String rangoId) {
		
		int  maximo = 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.EMP_RANGO WHERE RANGO_ID = TO_NUMBER(?,'9')";
			Object[] parametros = new Object[] {rangoId};
			maximo = enocJdbc.queryForObject(comando,Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|maximoCurso|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<EmpRango> lisTodos( String orden ) {
		
		List<EmpRango> lista	= new ArrayList<EmpRango>();
		String comando	= "";
		
		try{
			comando = " SELECT RANGO_ID, RANGO_NOMBRE, MINIMO, MAXIMO, PRECIO_MIN, PRECIO_MAX "
					+ " FROM ENOC.EMP_RANGO "+orden;
			lista = enocJdbc.query(comando, new EmpRangoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EmpRango> mapaRangosEmp() {
		
		HashMap<String,EmpRango> mapa = new HashMap<String,EmpRango>();
		List<EmpRango> lista		= new ArrayList<EmpRango>();
		
		try{
			String comando = "SELECT RANGO_ID, RANGO_NOMBRE, MINIMO, MAXIMO, PRECIO_MIN, PRECIO_MAX FROM ENOC.EMP_RANGO";				
			
			lista = enocJdbc.query(comando, new EmpRangoMapper());
			for (EmpRango rango : lista ) {
				mapa.put(rango.getRangoId(), rango);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|mapaRangosEmp|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaRangos() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT RANGO_ID AS LLAVE, RANGO_NOMBRE AS VALOR FROM ENOC.EMP_RANGO";				
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoDao|mapaRango|:"+ex);
		}
		
		return mapa;
	}

}