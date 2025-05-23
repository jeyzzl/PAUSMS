package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AnuariosEntregadosMapper implements RowMapper<AnuariosEntregados> {
	public AnuariosEntregados mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AnuariosEntregados objeto = new AnuariosEntregados();
		
		objeto.setEjercicioId(rs.getString("EJERCICIO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setSemestre(rs.getString("SEMESTRE"));

		return objeto;
	}
}
