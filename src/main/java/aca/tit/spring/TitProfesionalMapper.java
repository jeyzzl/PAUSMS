package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitProfesionalMapper implements RowMapper<TitProfesional>{
	public TitProfesional mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitProfesional objeto = new TitProfesional();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPrimerApellido(rs.getString("PRIMERAPELLIDO"));
		objeto.setSegundoApellido(rs.getString("SEGUNDOAPELLIDO"));
		objeto.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
		
		return objeto;
	}
}