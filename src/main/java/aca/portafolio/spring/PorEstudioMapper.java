package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorEstudioMapper implements RowMapper<PorEstudio> {

	public PorEstudio mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorEstudio objeto = new PorEstudio();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setHojas(rs.getString("HOJAS"));
		
		return objeto;
	}

}
