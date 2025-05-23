package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaRangoMapper implements RowMapper<HcaRango> {
	public HcaRango mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaRango objeto = new HcaRango();
		
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setRangoId(rs.getString("RANGO_ID"));
		objeto.setRangoIni(rs.getString("RANGO_INI"));
		objeto.setRangoFin(rs.getString("RANGO_FIN"));
		objeto.setValor(rs.getString("VALOR"));
		
		return objeto;
	}
}