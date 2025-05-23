package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitFirmaMapperCorto implements RowMapper<TitFirma>{
	public TitFirma mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitFirma objeto = new TitFirma();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPrimerApellido(rs.getString("PRIMERAPELLIDO"));
		objeto.setSegundoApellido(rs.getString("SEGUNDOAPELLIDO"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setIdCargo(rs.getString("IDCARGO"));
		objeto.setCargo(rs.getString("CARGO"));
		objeto.setAbrTitulo(rs.getString("ABRTITULO"));
		objeto.setSello(rs.getString("SELLO"));
		objeto.setCertificado(rs.getString("CERTIFICADO"));
		objeto.setNumeroCertificado(rs.getString("NUMEROCERTIFICADO"));
		
		return objeto;
	}
}