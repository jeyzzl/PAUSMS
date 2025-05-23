package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitSolicitudMapper implements RowMapper<BitSolicitud> {
	public BitSolicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitSolicitud objeto = new BitSolicitud();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setTextoAlumno(rs.getString("TEXTO_ALUMNO"));
		objeto.setTextoAdmin(rs.getString("TEXTO_ADMIN"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFolioTramite(rs.getString("FOLIO_TRAMITE"));
		
		return objeto;
	}
}