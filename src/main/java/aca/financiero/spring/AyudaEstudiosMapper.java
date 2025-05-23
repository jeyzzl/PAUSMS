package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AyudaEstudiosMapper implements RowMapper<AyudaEstudios> {

	public AyudaEstudios mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AyudaEstudios objeto = new AyudaEstudios();
		
		objeto.setId(rs.getString("ID"));
		objeto.setAlumno(rs.getString("ALUMNO"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setEnsenanza(rs.getString("ENSENANZA"));
		objeto.setInternado(rs.getString("INTERNADO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setEmpleadoId(rs.getString("EMPLEADO_ID"));
		objeto.setDependienteId(rs.getString("DEPENDIENTE_ID"));
		objeto.setDateCaptura(rs.getString("DATECAPTURA"));
		
		return objeto;
	}

}
