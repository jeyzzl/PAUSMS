package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorEmpArchivoMapper implements RowMapper<PorEmpArchivo> {

	public PorEmpArchivo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorEmpArchivo objeto = new PorEmpArchivo();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}

}
