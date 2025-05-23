package aca.sep.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SepDatosMapper implements RowMapper<SepDatos>{
	public SepDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SepDatos objeto = new SepDatos();
		
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setNombre(rs.getString("NOMBRE"));		
		objeto.setCurp(rs.getString("CURP"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setNacimiento(rs.getString("NACIMIENTO"));
		objeto.setNuevo(rs.getString("NUEVO"));
		objeto.setAntecedente(rs.getString("ANTECEDENTE"));
		objeto.setResidencia(rs.getString("RESIDENCIA"));
		
		return objeto;
	}
}