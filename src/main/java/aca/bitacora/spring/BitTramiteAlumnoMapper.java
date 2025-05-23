package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitTramiteAlumnoMapper implements RowMapper<BitTramiteAlumno> {
	public BitTramiteAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitTramiteAlumno objeto = new BitTramiteAlumno();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setAreaOrigen(rs.getString("AREA_ORIGEN"));
		objeto.setFolioOrigen(rs.getString("FOLIO_ORIGEN"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}