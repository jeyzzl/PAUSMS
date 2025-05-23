package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchEntregaCompletoMapper implements RowMapper<ArchEntrega>{
	@Override
	public ArchEntrega mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchEntrega objeto = new ArchEntrega();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDocumentos(rs.getString("DOCUMENTOS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setIdentificacion(rs.getBytes("IDENTIFICACION"));
		objeto.setPoder(rs.getBytes("PODER"));
		objeto.setEnvio(rs.getBytes("ENVIO"));
		objeto.setFirma(rs.getBytes("FIRMA"));
		objeto.setExtra(rs.getBytes("EXTRA"));
		
		return objeto;
	}
}