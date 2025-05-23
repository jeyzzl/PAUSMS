package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigInfraccionMapper implements RowMapper<VigInfraccion>{

	public VigInfraccion mapRow(ResultSet rs, int arg1) throws SQLException {
		VigInfraccion objeto = new VigInfraccion();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setAutoId(rs.getString("AUTO_ID"));
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setMulta(rs.getString("MULTA"));
		
		return objeto;
	}

}
