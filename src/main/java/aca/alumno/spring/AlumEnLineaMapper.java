package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumEnLineaMapper implements RowMapper<AlumEnLinea>{
	@Override
	public AlumEnLinea mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumEnLinea objeto = new AlumEnLinea();	
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstado(rs.getString("SOLICITUD"));
		objeto.setComentarios(rs.getString("COMENTARIOS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCoordinador(rs.getString("COORDINADOR"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
