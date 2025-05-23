package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContCcostoMapper implements RowMapper<ContCcosto> {

	public ContCcosto mapRow(ResultSet rs, int arg1) throws SQLException {
		ContCcosto objeto = new ContCcosto();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));		
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setDetalle(rs.getString("DETALLE"));
		objeto.setIniciales(rs.getString("INICIALES"));
		objeto.setRfc(rs.getString("RFC"));		
		return objeto;
	}

}
