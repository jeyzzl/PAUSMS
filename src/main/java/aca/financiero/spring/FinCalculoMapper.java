package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinCalculoMapper implements RowMapper<FinCalculo> {

	@Override
	public FinCalculo mapRow(ResultSet rs, int arg1) throws SQLException {
		FinCalculo objeto = new FinCalculo();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
