package aca.baja.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BajaAlumnoMapper implements RowMapper<BajaAlumno>{
	@Override
	public BajaAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BajaAlumno objeto = new BajaAlumno();
		
		objeto.setBajaId(rs.getString("BAJA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfBaja(rs.getString("F_BAJA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setRazonId(rs.getString("RAZON_ID"));
		objeto.setCreditos(rs.getString("CREDITOS"));

		return objeto;
	}
}