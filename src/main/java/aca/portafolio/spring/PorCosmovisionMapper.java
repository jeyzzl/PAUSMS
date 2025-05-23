package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorCosmovisionMapper implements RowMapper<PorCosmovision> {

	public PorCosmovision mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorCosmovision objeto = new PorCosmovision();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFilosofia(rs.getString("FILOSOFIA"));
		objeto.setMision(rs.getString("MISION"));
		objeto.setVision(rs.getString("VISION"));
		objeto.setValores(rs.getString("VALORES"));
		objeto.setReflexion(rs.getString("REFLEXION"));
		
		return objeto;
	}

}
