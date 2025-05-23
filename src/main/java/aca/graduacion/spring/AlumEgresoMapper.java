package aca.graduacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumEgresoMapper implements RowMapper<AlumEgreso> {
	public AlumEgreso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumEgreso objeto = new AlumEgreso();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setAvance(rs.getString("AVANCE"));
		objeto.setTitulado(rs.getString("TITULADO"));
		objeto.setPromedio(rs.getString("PROMEDIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setDiploma(rs.getString("DIPLOMA"));
		objeto.setPermiso(rs.getString("PERMISO"));
		objeto.setConfirmar(rs.getString("CONFIRMAR"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}