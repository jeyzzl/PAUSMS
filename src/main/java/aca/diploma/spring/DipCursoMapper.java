package aca.diploma.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DipCursoMapper implements RowMapper<DipCurso> {
	public DipCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		DipCurso objeto = new DipCurso();
		
		objeto.setDiplomaId(rs.getString("DIPLOMA_ID"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setCurso(rs.getString("CURSO"));
		objeto.setTema(rs.getString("TEMA"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFirmaUno(rs.getString("FIRMA_UNO"));
		objeto.setFirmaDos(rs.getString("FIRMA_DOS"));

		return objeto;
	}
}