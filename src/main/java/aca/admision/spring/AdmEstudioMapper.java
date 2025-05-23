package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmEstudioMapper implements RowMapper<AdmEstudio> {

	@Override
	public AdmEstudio mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmEstudio objeto = new AdmEstudio();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setId(rs.getString("ID"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId( rs.getString("CIUDAD_ID"));
		objeto.setCompleto( rs.getString("COMPLETO"));
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setFin(rs.getString("FIN"));
		objeto.setDependencia(rs.getString("DEPENDENCIA"));
		objeto.setConvalida(rs.getString("CONVALIDA"));
		objeto.setEstudios(rs.getString("ESTUDIOS"));
		objeto.setOtraMateria(rs.getString("OTRA_MATERIA"));
		
		return objeto;
	}

}
