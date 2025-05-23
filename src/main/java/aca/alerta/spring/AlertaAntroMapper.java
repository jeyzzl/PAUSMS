package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaAntroMapper implements RowMapper<AlertaAntro> {
	@Override
	public AlertaAntro mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlertaAntro objeto = new AlertaAntro();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeso(rs.getString("PESO"));
		objeto.setTalla(rs.getString("TALLA"));
		objeto.setCintura(rs.getString("CINTURA"));
		objeto.setImc(rs.getString("IMC"));
		objeto.setGrasa(rs.getString("GRASA"));
		objeto.setMusculo(rs.getString("MUSCULO"));
		objeto.setPresion(rs.getString("PRESION"));
		
		return objeto;
	}

}
