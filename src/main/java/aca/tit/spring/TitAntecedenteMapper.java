package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitAntecedenteMapper implements RowMapper<TitAntecedente>{
	public TitAntecedente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitAntecedente objeto = new TitAntecedente();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setEstudioId(rs.getString("ESTUDIOID"));
		objeto.setEstudio(rs.getString("ESTUDIO"));
		objeto.setEntidadId(rs.getString("ENTIDADID"));
		objeto.setEntidad(rs.getString("ENTIDAD"));
		objeto.setFechaInicio(rs.getString("FECHAINICIO"));
		objeto.setFechaTerminacion(rs.getString("FECHATERMINACION"));
		objeto.setCedula(rs.getString("CEDULA"));
		
		return objeto;
	}
}