package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContConceptoMapper implements RowMapper<ContConcepto> {

	public ContConcepto mapRow(ResultSet rs, int arg1) throws SQLException {
		ContConcepto objeto = new ContConcepto();
		
		objeto.setId(rs.getString("Id"));
		objeto.setVersion(rs.getString("VERSION"));		
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTags(rs.getString("TAGS"));
		
		return objeto;
	}

}
