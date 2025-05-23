package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaDocAlumMapper implements RowMapper<AlertaDocAlum>{
	@Override
	public AlertaDocAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlertaDocAlum objeto = new AlertaDocAlum();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));	
		
		return objeto;
	}
}
