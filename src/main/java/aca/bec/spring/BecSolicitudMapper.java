package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecSolicitudMapper implements RowMapper<BecSolicitud> {
	
	@Override
	public BecSolicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecSolicitud objeto = new BecSolicitud();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));	
		objeto.setPorCoordinador(rs.getString("POR_COORDINADOR"));
		objeto.setCoordinador(rs.getString("COORDINADOR"));	
		objeto.setPorComision(rs.getString("POR_COMISION"));	
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setComCoordinador(rs.getString("COM_COORDINADOR"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		
		return objeto;
	}
}
