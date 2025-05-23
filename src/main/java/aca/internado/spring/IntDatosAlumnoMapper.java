package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntDatosAlumnoMapper implements RowMapper<IntDatosAlumno> {
	public IntDatosAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntDatosAlumno objeto = new IntDatosAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setComputadora(rs.getString("COMPUTADORA"));
		objeto.setTratamiento(rs.getString("TRATAMIENTO"));
		objeto.setMotivo(rs.getString("MOTIVO"));
		objeto.setTipoSangre(rs.getString("TIPO_SANGRE"));
		objeto.setInstrumentos(rs.getString("INSTRUMENTOS"));
		objeto.setCelular(rs.getString("CELULAR"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		
		return objeto;
	}

}
