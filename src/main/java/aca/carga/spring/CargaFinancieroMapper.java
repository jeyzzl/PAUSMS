package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaFinancieroMapper implements RowMapper<CargaFinanciero>{
	
	public CargaFinanciero mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaFinanciero objeto = new CargaFinanciero();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setFecha(rs.getString("FECHA"));	
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setStatus(rs.getString("STATUS"));

		
		return objeto;
	}
}