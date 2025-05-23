package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigAutoMapper implements RowMapper<VigAuto>{

	public VigAuto mapRow(ResultSet rs, int arg1) throws SQLException {
		VigAuto objeto = new VigAuto();
		
		objeto.setAutoId(rs.getString("AUTO_ID"));
		objeto.setPlacas(rs.getString("PLACAS"));
		objeto.setEngomado(rs.getString("ENGOMADO"));
		objeto.setUsuario(rs.getString("USUARIO"));		
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setColor(rs.getString("COLOR"));
		objeto.setModelo(rs.getString("MODELO"));
		objeto.setMarca(rs.getString("MARCA"));
		objeto.setPoliza(rs.getString("POLIZA"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
