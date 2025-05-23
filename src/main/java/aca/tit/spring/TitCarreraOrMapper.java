package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitCarreraOrMapper implements RowMapper<TitCarreraOr>{
	public TitCarreraOr mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitCarreraOr objeto = new TitCarreraOr();
		
		objeto.setCveInstitucion(rs.getString("CVE_INSTITUCION"));
		objeto.setNombreInstitucion(rs.getString("NOMBRE_INSTITUCION"));
		objeto.setTipoSostenimiento(rs.getString("TIPO_SOSTENIMIENTO"));
		objeto.setTipoEducativo(rs.getString("TIPO_EDUCATIVO"));
		objeto.setNivelEstudios(rs.getString("NIVEL_ESTUDIOS"));
		objeto.setCveCarrera(rs.getString("CVE_CARRERA"));
		objeto.setCarrera(rs.getString("CARRERA"));
		
		return objeto;
	}
}