// Clase para la tabla de AlumPersonal
package aca.alumno.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class AlumPersonalDao2{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(query, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao2|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public AlumPersonal mapeaRegId(String codigoPersonal){
		AlumPersonal alumno = new AlumPersonal();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " COALESCE(SEXO, 'M') AS SEXO, COALESCE(ESTADO_CIVIL, 'S') AS ESTADO_CIVIL,"
					+ " COALESCE(RELIGION_ID, 1) RELIGION_ID, COALESCE(BAUTIZADO,'S') AS BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " EMAIL, CURP, COALESCE(ESTADO,'A') AS ESTADO, COALESCE(COTEJADO,'N') AS COTEJADO,"
					+ " CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, CREDENCIAL, US_ALTA"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			alumno = enocJdbc.queryForObject(query, new AlumPersonalMapperCompleto(), parametros);
		}catch( Exception ex){
			System.out.println("Error: aca.alumno.spring.AlumPersonalDao2|mapeaRegId:"+ex);
		}
		
		return alumno;
	}

}