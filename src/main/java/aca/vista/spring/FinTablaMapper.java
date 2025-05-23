package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinTablaMapper implements RowMapper<FinTabla>{

	public FinTabla mapRow(ResultSet rs, int arg1) throws SQLException {
		FinTabla objeto = new FinTabla();
		
		objeto.setTablaId(rs.getString("TABLA_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPorMatricula(rs.getString("PORMATRICULA"));
		objeto.setLegales(rs.getString("LEGALES"));
		objeto.setPorLegales(rs.getString("PORLEGALES"));
		objeto.setInternado(rs.getString("INTERNADO"));
		objeto.setPorInternado(rs.getString("PORINTERNADO"));
		objeto.setAcfe(rs.getString("ACFE"));
		objeto.setNoAcfe(rs.getString("NOACFE"));
		objeto.setPorCredito(rs.getString("PORCREDITO"));
		objeto.setStatus(rs.getString("STATUS"));

		return objeto;
	}

}
