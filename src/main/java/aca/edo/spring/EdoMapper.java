package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoMapper implements RowMapper<Edo> {
	public Edo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Edo objeto = new Edo();
		
		objeto.setEdoId(rs.getString("EDO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFFinal(rs.getString("F_FINAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.setEncabezado(rs.getString("ENCABEZADO"));
		objeto.setTipoEncuesta(rs.getString("TIPO_ENCUESTA"));
		objeto.setCargas(rs.getString("CARGAS"));
		objeto.setExcepto(rs.getString("EXCEPTO"));
		
		return objeto;
	}
}