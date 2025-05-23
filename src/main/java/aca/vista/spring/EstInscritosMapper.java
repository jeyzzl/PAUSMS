package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstInscritosMapper implements RowMapper<EstInscritos>{

	public EstInscritos mapRow(ResultSet rs, int arg1) throws SQLException {
		EstInscritos objeto = new EstInscritos();

		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setEdad(rs.getString("EDAD"));
		objeto.setSexo(rs.getString("SEXO"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setNombreReligion(rs.getString("NOMBRE_RELIGION"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
	
		return objeto;
	}

}
