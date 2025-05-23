package aca.covid.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CovidMapper implements RowMapper<Covid> {

	public Covid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Covid objeto = new Covid();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setHipertension(rs.getString("HIPERTENSION"));
		objeto.setDiabetes(rs.getString("DIABETES"));
		objeto.setCorazon(rs.getString("CORAZON"));
		objeto.setPulmon(rs.getString("PULMON"));
		objeto.setCancer(rs.getString("CANCER"));
		objeto.setObesidad(rs.getString("OBESIDAD"));
		objeto.setEstres(rs.getString("ESTRES"));
		objeto.setDepresion(rs.getString("DEPRESION"));
		objeto.setImss(rs.getString("IMSS"));
		objeto.setPase(rs.getString("PASE"));
		objeto.setIsste(rs.getString("ISSTE"));
		objeto.setHlc(rs.getString("HLC"));
		objeto.setPrivado(rs.getString("PRIVADO"));
		objeto.setNinguno(rs.getString("NINGUNO"));
		objeto.setOtro(rs.getString("OTRO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
