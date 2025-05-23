package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumActualizaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumActualiza alumActualiza ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_ACTUALIZA"+ 
				"(CODIGO_PERSONAL, CODIGO_EMPLEADO, FECHA, ESTADO ) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), ? )";
			Object[] parametros = new Object[] {alumActualiza.getCodigoPersonal(),
					alumActualiza.getCodigoEmpleado(), alumActualiza.getFecha(), alumActualiza.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AlumActualiza alumActualiza ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_ACTUALIZA "+ 
				"SET ESTADO = ?, CODIGO_EMPLEADO = ?, FECHA = TO_DATE(?, 'DD/MM/YYYY') "+
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {alumActualiza.getEstado(),
					alumActualiza.getCodigoEmpleado(), alumActualiza.getFecha(), alumActualiza.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|deletetReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumActualiza mapeaRegId(  String codigoPersonal) {
		AlumActualiza objeto = new AlumActualiza();
		
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO "+
				"FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumActualizaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getCodigoEmpleado( String codigoPersonal ) {
		String nombre = " ";
		
		try{
			String comando = "SELECT CODIGO_EMPLEADO FROM ENOC.ALUM_ACTUALIZA" +
					" WHERE CODIGO_PERSONAL = ? " +
					" AND ESTADO = 'A' ";
			Object[] parametros = new Object[] {codigoPersonal};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|getCodigoEmpleado|:"+ex);
		}
		
		return nombre;
	}
	
	public String getEstado( String codigoPersonal) {
		String solucion			= "";		
		try{
			String comando = "SELECT COALESCE(ESTADO,' ') AS SOLUCION "+
				"FROM ENOC.ALUM_ACTUALIZA WHERE CODIGO_PERSONAL= ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			solucion = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|getEstado|:"+ex);
		}		
		return solucion;
	}
	
	public List<AlumActualiza> getListAll( String orden ) {
		
		List<AlumActualiza> lista = new ArrayList<AlumActualiza>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
				+ " FROM ENOC.ALUM_ACTUALIZA "+orden;
			lista = enocJdbc.query(comando, new AlumActualizaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AlumActualiza> mapaActualiza() {
		HashMap<String,AlumActualiza> mapa	= new HashMap<String, AlumActualiza>();
		List<AlumActualiza>	lista		= new ArrayList<AlumActualiza>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.ALUM_ACTUALIZA ";
			lista = enocJdbc.query(comando, new AlumActualizaMapper());
			for (AlumActualiza objeto : lista){
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|mapaActualiza|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumActualiza> mapaInscritos(){
		HashMap<String,AlumActualiza> mapa	= new HashMap<String, AlumActualiza>();
		List<AlumActualiza>	lista		= new ArrayList<AlumActualiza>();
		try{
			String comando ="SELECT CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.ALUM_ACTUALIZA";
			lista = enocJdbc.query(comando, new AlumActualizaMapper());
			for (AlumActualiza actualiza : lista){
				mapa.put(actualiza.getCodigoPersonal(), actualiza);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumActualizaDao|mapaInscritos|:"+ex);
		}
		
		return mapa;
	}
	
}
