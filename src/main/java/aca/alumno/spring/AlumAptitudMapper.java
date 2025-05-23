package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumAptitudMapper implements RowMapper<AlumAptitud>{
	@Override
	public AlumAptitud mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumAptitud objeto = new AlumAptitud();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setFuerza(rs.getString("FUERZA"));
		objeto.setFlexibilidad(rs.getString("FLEXIBILIDAD"));
		objeto.setResistencia(rs.getString("RESISTENCIA"));
		objeto.setCardio(rs.getString("CARDIO"));
		objeto.setPeso(rs.getString("PESO"));
		objeto.setTalla(rs.getString("TALLA"));
		objeto.setImc(rs.getString("IMC"));
		objeto.setGrasa(rs.getString("GRASA"));
		objeto.setAbdomen(rs.getString("ABDOMEN"));
		objeto.setDieta(rs.getString("DIETA"));
		
		return objeto;
	}
}
