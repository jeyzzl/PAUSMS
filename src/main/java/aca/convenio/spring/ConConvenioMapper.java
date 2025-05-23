package aca.convenio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConConvenioMapper implements RowMapper<ConConvenio> {
	public ConConvenio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConConvenio evento = new ConConvenio();
		
		evento.setId(rs.getString("ID"));
		evento.setNombre(rs.getString("NOMBRE"));
		evento.setFechaFirma(rs.getString("FECHA_FIRMA"));
		evento.setFechaVigencia(rs.getString("FECHA_VIGENCIA"));
		evento.setPrograma(rs.getString("PROGRAMA"));
		evento.setObjetivo(rs.getString("OBJETIVO"));
		evento.setEstado(rs.getString("ESTADO"));
		evento.setTipoId(rs.getString("TIPO_ID"));
		
		return evento;
	}
}
