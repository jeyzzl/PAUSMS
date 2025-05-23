package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorEmpDoctoMapper implements RowMapper<PorEmpDocto> {

	public PorEmpDocto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorEmpDocto objeto = new PorEmpDocto();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setHojas(rs.getString("HOJAS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
