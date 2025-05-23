package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitEtiquetaMapper implements RowMapper<BitEtiqueta> {
	public BitEtiqueta mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitEtiqueta objeto = new BitEtiqueta();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setEtiquetaId(rs.getString("ETIQUETA_ID"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setTurnado(rs.getString("TURNADO"));
		
		return objeto;
	}
}