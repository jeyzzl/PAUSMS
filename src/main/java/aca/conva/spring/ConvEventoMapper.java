package aca.conva.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConvEventoMapper implements RowMapper<ConvEvento> {
	public ConvEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConvEvento evento = new ConvEvento();
		
		evento.setConvalidacionId(rs.getString("CONVALIDACION_ID"));
		evento.setFecha(rs.getString("FECHA"));
		evento.setUsuario(rs.getString("USUARIO"));
		evento.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		evento.setPlanId(rs.getString("PLAN_ID"));
		evento.setEstado(rs.getString("ESTADO"));
		evento.setComentario(rs.getString("COMENTARIO"));
		evento.setInstitucion(rs.getString("INSTITUCION"));
		evento.setPrograma(rs.getString("PROGRAMA"));
		evento.setTipo(rs.getString("TIPO"));
		evento.setDictamen(rs.getString("DICTAMEN"));
		evento.setTipoConv(rs.getString("TIPO_CONV"));
		evento.setPeriodo(rs.getString("PERIODO"));
		evento.setPlanOrigen(rs.getString("PLAN_ORIGEN"));
		
		return evento;
	}
}
