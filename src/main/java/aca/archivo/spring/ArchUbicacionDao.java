//Clase  para la tabla ARCH_STATUS
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchUbicacionDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg( ArchUbicacion ubicacion)  {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ARCH_UBICACION(UBICACION_ID, UBICACION_NOMBRE) VALUES(TO_NUMBER(?,'99'),?)";
			Object[] parametros = new Object[] {	ubicacion.getUbicacionId(), ubicacion.getUbicacionNombre() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg( ArchUbicacion ubicacion ) {
		
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ARCH_UBICACION SET UBICACION_NOMBRE = ? WHERE UBICACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { ubicacion.getUbicacionNombre(), ubicacion.getUbicacionId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|updateReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteReg( String ubicacionId ) {
		
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] { ubicacionId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public ArchUbicacion mapeaRegId( String ubicacionId) {
		
		ArchUbicacion ubicacion = new ArchUbicacion();		
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { ubicacionId };		
			if( enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				comando = "SELECT UBICACION_ID,UBICACION_NOMBRE FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')";
				ubicacion = enocJdbc.queryForObject(comando, new ArchUbicacionMapper(), parametros); 
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|mapeaRegId|:"+ex);
		}
		
		return ubicacion;
	}

	public boolean existeReg( String ubicacionId) {
		
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { ubicacionId };		
			if( enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|existeReg|:"+ex);
		}
		return ok;
	}

	public String maximoReg( ) {
		
		int maximo 	= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(UBICACION_ID)+1,1) FROM ENOC.ARCH_UBICACION";
			maximo = enocJdbc.queryForObject(comando, Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}

	public String getUbicacionNombre(  String ubicacionId) {
		
		String ubicacionNombre		= "";
		try{			 
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { ubicacionId };		
			if( enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){				
				comando = "SELECT UBICACION_NOMBRE FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')";
				ubicacionNombre = enocJdbc.queryForObject(comando, String.class);				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|getUbicacionNombre|:"+ex);
		}		
		return ubicacionNombre;
	}
	
	public List<ArchUbicacion> getListAll( String orden ) {
		
		List<ArchUbicacion> lista 	= new ArrayList<ArchUbicacion>();			
		try{
			String comando	= "SELECT UBICACION_ID, UBICACION_NOMBRE FROM ENOC.ARCH_UBICACION "+orden;		
			lista = enocJdbc.query(comando, new ArchUbicacionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchUbicacionDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaUbicacion() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT UBICACION_ID AS LLAVE, UBICACION_NOMBRE AS VALOR FROM ENOC.ARCH_UBICACION";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapaUbicacion|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaUsados(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT UBICACION AS LLAVE, COUNT(UBICACION) AS VALOR FROM ENOC.ARCH_DOCALUM GROUP BY UBICACION";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapaUsados|:"+ex);
		}
		return mapa;
	}

}