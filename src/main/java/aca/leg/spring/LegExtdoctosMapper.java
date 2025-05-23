package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegExtdoctosMapper implements RowMapper<LegExtdoctos> {

	public LegExtdoctos mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegExtdoctos objeto = new LegExtdoctos();
		
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setFechaVence(rs.getString("FECHA_VENCE"));
		objeto.setNumDocto(rs.getString("NUM_DOCTO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFechaTramite(rs.getString("FECHA_TRAMITE"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
