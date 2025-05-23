package aca.serviceRest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ServiceInscritosDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String inscrito(String codigoPersonal) {

		String inscrito = "No";
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? ";			
			Object[] parametros = new Object[] { codigoPersonal };			
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				inscrito = "Si";
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.serviceRest.ServiceInscritosDao|inscrito|:" + ex);
		}		
		return inscrito;
	}

	public List<ServiceInscritos> getListAll() {
		
		List<ServiceInscritos> lista = new ArrayList<ServiceInscritos>();
		
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID), "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ "ORDER BY NOMBRE";
			lista = enocJdbc.query(comando, new ServiceInscritosMapper());
			
		} catch (Exception ex) {
			System.out.println("Error - aca.serviceRest.ServiceInscritosDao|getListAll|:" + ex);
		}
		
		return lista;
	}
}
