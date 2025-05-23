package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmRequisitoMapper implements RowMapper<AdmRequisito>{
	@Override
	public AdmRequisito mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AdmRequisito objeto = new AdmRequisito();
		
		objeto.setCarreraId(rs.getString("CARRERA_ID"));				
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setModalidades(rs.getString("MODALIDADES"));
		objeto.setAutorizar(rs.getString("AUTORIZAR"));
		objeto.setRequerido(rs.getString("REQUERIDO"));
		
		return objeto;
	}
}