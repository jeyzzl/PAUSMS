package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpEstadisticaMapper implements RowMapper<EmpEstadistica> {
	public EmpEstadistica mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpEstadistica objeto = new EmpEstadistica();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargas(rs.getString("CARGAS"));	
		objeto.setModalidades(rs.getString("MODALIDADES"));

		return objeto;
	}
}
