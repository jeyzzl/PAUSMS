package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumGraduaMapper implements RowMapper<AlumGradua>{
	@Override
	public AlumGradua mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumGradua objeto = new AlumGradua();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFechaGraduacion(rs.getString("FECHA_GRADUACION"));
		objeto.setEvento(rs.getString("EVENTO"));
		objeto.setAvance(rs.getString("AVANCE"));
		objeto.setMatAc(rs.getString("MAT_AC"));
		objeto.setMatIns(rs.getString("MAT_INS"));
		objeto.setMatPen(rs.getString("MAT_PEN"));
		objeto.setDiploma(rs.getString("DIPLOMA"));
		
		return objeto;
	}
}