package aca.sep.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SepEstadoMapper implements RowMapper<SepEstado>{

	@Override
	public SepEstado mapRow(ResultSet rs, int arg1) throws SQLException {
		SepEstado objeto = new SepEstado();
		
		objeto.setEstado_SE(rs.getString("ESTADO_SE"));
		objeto.setNombre("NOMBRE");
		
		return objeto;
	}

}
