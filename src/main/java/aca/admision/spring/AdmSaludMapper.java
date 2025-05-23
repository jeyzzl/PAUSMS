package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmSaludMapper implements RowMapper<AdmSalud> {

	@Override
	public AdmSalud mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmSalud objeto = new AdmSalud();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setEnfermedad(rs.getString("ENFERMEDAD"));
		objeto.setEnfermedadDato(rs.getString("ENFERMEDAD_DATO"));
		objeto.setImpedimento(rs.getString("IMPEDIMENTO"));
		objeto.setImpedimentoDato(rs.getString("IMPEDIMENTO_DATO"));
		
		return objeto;
	}

}
