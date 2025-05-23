package aca.sep.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SepDatosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public List<SepDatos> lisDatos( String fecha, String orden ) {
		List<SepDatos> lista = new ArrayList<SepDatos>();
		try{
			String comando = "SELECT PLAN_UM AS CARRERA, CODIGO_PERSONAL AS MATRICULA, NOMBRE||','||PATERNO||' '||MATERNO AS NOMBRE, CURP, GRADO,"
					+ " DECODE(PAIS_ID,91,DECODE(ESTADO_ID,19,'En la entidad','En otra entidad federativa'),SEP_PAIS_NOMBRE(PAIS_ID)) AS NACIMIENTO,"
					+ " DECODE(CICLO,1,'SI','NO') AS NUEVO,"
					+ " SEP_LUGAR_NOMBRE(SEP_LUGAR_ID(CODIGO_PERSONAL)) AS ANTECEDENTE,"
					+ " DECODE(PAIS_ID,91,SEP_ESTADO_NOMBRE(PAIS_ID,ESTADO_ID),'Fuera del país') AS RESIDENCIA"
					+ " FROM SEP_ALUMNO WHERE FECHA = TO_DATE(?,'YYYY/MM/DD') "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new SepDatosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listPlanes|:"+ex);
		}
		return lista;
	}
	
	public List<SepDatos> lisDatos( String fecha, String filtro, String orden ) {
		List<SepDatos> lista = new ArrayList<SepDatos>();
		try{
			String comando = "SELECT PLAN_UM AS CARRERA, CODIGO_PERSONAL AS MATRICULA, NOMBRE||','||PATERNO||' '||MATERNO AS NOMBRE, CURP, GRADO,"
					+ " DECODE(PAIS_ID,91,DECODE(ESTADO_ID,19,'En la entidad','En otra entidad federativa'),SEP_PAIS_NOMBRE(PAIS_ID)) AS NACIMIENTO,"
					+ " DECODE(CICLO,1,'SI','NO') AS NUEVO,"
					+ " SEP_LUGAR_NOMBRE(SEP_LUGAR_ID(CODIGO_PERSONAL)) AS ANTECEDENTE,"
					+ " DECODE(PAIS_ID,91,SEP_ESTADO_NOMBRE(PAIS_ID,ESTADO_ID),'Fuera del país') AS RESIDENCIA"
					+ " FROM SEP_ALUMNO "
					+ " WHERE FECHA = TO_DATE(?,'YYYY/MM/DD')"
					+ " AND PLAN_UM IN("+filtro+") "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new SepDatosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listPlanes|:"+ex);
		}
		return lista;
	}
}