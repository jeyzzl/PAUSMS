package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmAcademicoMapper implements RowMapper<AdmAcademico> {
	@Override
	public AdmAcademico mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmAcademico objeto = new AdmAcademico();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setModalidad(rs.getString("MODALIDAD_ID"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setTipo(rs.getString("TIPO"));

		return objeto;
	}

}
