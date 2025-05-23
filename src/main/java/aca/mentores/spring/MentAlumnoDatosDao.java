package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentAlumnoDatosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentAlumnoDatos objeto) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.MENT_ALUMNO_DATOS (CODIGO_PERSONAL, IGLESIA, CLASE_ES) VALUES( ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getIglesia(),objeto.getClaseEs()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(MentAlumnoDatos objeto) {
		boolean ok = true;
		try{
			String comando = "UPDATE ENOC.MENT_ALUMNO_DATOS SET IGLESIA = ?, CLASE_ES = ? WHERE CODIGO_PERSONAL = ?";

			Object[] parametros = new Object[] {
				objeto.getIglesia(),objeto.getClaseEs(),objeto.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|updateReg|:"+ex);
		}
		return ok;
	}
		
	public boolean deleteReg(String codigoPersonal){
		boolean ok = true;
		try{
			String comando = "DELETE FROM ENOC.MENT_ALUMNO_DATOS WHERE CODIGO_PERSONAL = ?";
			
			if (enocJdbc.update(comando,codigoPersonal)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MentAlumnoDatos mapeaRegId(String codigoPersonal) {
		MentAlumnoDatos objeto = new MentAlumnoDatos();
		try{
			String comando = "SELECT CODIGO_PERSONAL, IGLESIA, CLASE_ES"+
					" FROM ENOC.MENT_ALUMNO_DATOS" + 
					" WHERE CODIGO_PERSONAL = ?";
			
			objeto = enocJdbc.queryForObject(comando, new MentAlumnoDatosMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO_DATOS WHERE CODIGO_PERSONAL = ?";
		
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String iglesia(String codigoPersonal) {
		String iglesia			= "";
		
		try{
			String comando = "SELECT IGLESIA FROM ENOC.MENT_ALUMNO_DATOS WHERE CODIGO_PERSONAL = ?";
			
			iglesia = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|iglesia|:"+ex);
		}
		
		return iglesia;
	}
	
	public String clase(String codigoPersonal) {
		String clase			= "";
		
		try{
			String comando = "SELECT CLASE_ES FROM ENOC.MENT_ALUMNO_DATOS" + 
					" WHERE CODIGO_PERSONAL = ?";
			
			clase = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|clase|:"+ex);
		}
		
		return clase;
	}
	
	public HashMap<String, MentAlumnoDatos> getMapAll() {
		HashMap<String, MentAlumnoDatos> mapa 	= new HashMap<String,MentAlumnoDatos>();
		List<MentAlumnoDatos> lista				= new ArrayList<MentAlumnoDatos>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, IGLESIA, CLASE_ES FROM ENOC.MENT_ALUMNO_DATOS ";
			
			lista = enocJdbc.query(comando, new MentAlumnoDatosMapper());
			
			for(MentAlumnoDatos objeto : lista) {	
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDatosDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
}