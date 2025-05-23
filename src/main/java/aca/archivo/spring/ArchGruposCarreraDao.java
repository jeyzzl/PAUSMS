//Clase  para la tabla ARCH_DOCALUM
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchGruposCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(ArchGruposCarrera archivo){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ARCH_GRUPOS_CARRERA"
					+ " (CARRERA, GRUPOS) VALUES(?, ?)";

			Object[] parametros = new Object[] { archivo.getCarrera(), archivo.getGrupos()};		
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateGrupos(String carreraId, String grupos){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ARCH_GRUPOS_CARRERA SET GRUPOS = ? WHERE CARRERA = ?";									
			Object[] parametros = new Object[] {grupos, carreraId};	
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|updateGrupos|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(ArchGruposCarrera archivo){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.ARCH_GRUPOS_CARRERA"
					+ " SET GRUPOS = ?"
					+ " WHERE CARRERA = ?";			
									
			Object[] parametros = new Object[] {archivo.getGrupos(), archivo.getCarrera()};	
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String carrera){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?"; 
			
			Object[] parametros = new Object[] {carrera};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public ArchGruposCarrera mapeaRegId(String carrera){
		ArchGruposCarrera archivo = new ArchGruposCarrera();

		try{
			String comando = "SELECT CARRERA, GRUPOS"
					+ " FROM ENOC.ARCH_GRUPOS_CARRERA" 
					+ " WHERE CARRERA = ?";
			
			Object[] parametros = new Object[] {carrera};
			archivo = enocJdbc.queryForObject(comando, new ArchGruposCarreraMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|mapeaRegId|:"+ex);
		}
		return archivo;
	}
	
	public boolean existeReg(String carrera){
		boolean ok 			= false;

		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?"; 
			
			Object[] parametros = new Object[] {carrera};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<ArchGruposCarrera> getListAll(String orden ){
		List<ArchGruposCarrera> lista 	= new ArrayList<ArchGruposCarrera>();
		String comando	= "";
		
		try{
			comando = " SELECT CARRERA, GRUPOS FROM ENOC.ARCH_GRUPOS_CARRERA "+orden;			
			lista = enocJdbc.query(comando, new ArchGruposCarreraMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public String getGruposCarrera(String carreraId ){
		String grupos 	= "0";	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?";	
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT REPLACE(TRIM(GRUPOS),' ',',') FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?";
				grupos 	= enocJdbc.queryForObject(comando, String.class, parametros);
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|getGruposCarrera|:"+ex);
		}
		
		return grupos;
	}
	
	public HashMap<String, String> mapaGrupos() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = "SELECT CARRERA AS LLAVE, GRUPOS AS VALOR FROM ENOC.ARCH_GRUPOS_CARRERA";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposCarreraDao|mapaGrupos|:"+ex);
		}
		return mapa;
	}

}
