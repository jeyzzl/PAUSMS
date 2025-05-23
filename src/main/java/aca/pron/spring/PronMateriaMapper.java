package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronMateriaMapper implements RowMapper<PronMateria> {

	public PronMateria mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronMateria objeto = new PronMateria();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setHoraClase(rs.getString("HORA_CLASE"));
		objeto.setHoraTutoria(rs.getString("HORA_TUTORIA"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setFormacion(rs.getString("FORMACION"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setEnfoque(rs.getString("ENFOQUE"));
		objeto.setEspecial(rs.getString("ESPECIAL"));
		objeto.setIntegridad(rs.getString("INTEGRIDAD"));
		
		return objeto;
	}

}
