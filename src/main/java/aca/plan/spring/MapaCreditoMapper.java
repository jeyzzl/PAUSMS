package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaCreditoMapper implements RowMapper<MapaCredito>{

	public MapaCredito mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaCredito objeto = new MapaCredito();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setOptativos(rs.getString("OPTATIVOS"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}

}
