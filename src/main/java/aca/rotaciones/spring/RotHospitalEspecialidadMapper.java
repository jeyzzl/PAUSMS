package aca.rotaciones.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RotHospitalEspecialidadMapper implements RowMapper<RotHospitalEspecialidad>{

	public RotHospitalEspecialidad mapRow(ResultSet rs, int arg1) throws SQLException {
		RotHospitalEspecialidad objeto = new RotHospitalEspecialidad();
		
		objeto.setHospitalId(rs.getString("HOSPITAL_ID"));
		objeto.setEspecialidadId(rs.getString("ESPECIALIDAD_ID"));
		objeto.setContactoPrincipal(rs.getString("CONTACTO_PRINCIPAL"));
		objeto.setPuestoPrincipal(rs.getString("PUESTO_PRINCIPAL"));
		objeto.setContactoSecundario(rs.getString("CONTACTO_SECUNDARIO"));
		objeto.setPuestoSecundario(rs.getString("PUESTO_SECUNDARIO"));
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}
}
