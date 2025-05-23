package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecContratoMapperCorto implements RowMapper<BecContrato> {
	@Override
	public BecContrato mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecContrato objeto = new BecContrato();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPuestoId(rs.getString("PUESTO_ID"));		
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setAutorizado(rs.getString("AUTORIZADO"));
		return objeto;
	}
}
