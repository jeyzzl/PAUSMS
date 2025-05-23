package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronEjesMapper implements RowMapper<PronEjes> {

	public PronEjes mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronEjes objeto = new PronEjes();

		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFe(rs.getString("FE"));
		objeto.setPensamiento(rs.getString("PENSAMIENTO"));
		objeto.setAmbiente(rs.getString("AMBIENTE"));
		objeto.setLiderazgo(rs.getString("LIDERAZGO"));
		objeto.setEmprendimiento(rs.getString("EMPRENDIMIENTO"));
		objeto.setSustentabilidad(rs.getString("SUSTENTABILIDAD"));
		objeto.setServicio(rs.getString("SERVICIO"));
		objeto.setInvestigacion(rs.getString("INVESTIGACION"));
		
		return objeto;
	}

}
