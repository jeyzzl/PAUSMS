package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaFamiliaMapper implements RowMapper<ExaFamilia>{
	
	public ExaFamilia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaFamilia objeto = new ExaFamilia();
		
		objeto.setFamiliaId(rs.getString("FAMILIA_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setRelacion(rs.getString("RELACION"));
		objeto.setFechaNac(rs.getString("FECHANACIMIENTO"));
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setFechaAniv(rs.getString("FECHAANIVERSARIO"));
		objeto.setIdFamilia(rs.getString("IDFAMILIA"));
		
		return objeto;
	}
}