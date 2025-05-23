package aca.apFisica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ApFisicaGrupoMapper implements RowMapper<ApFisicaGrupo>{
	@Override
	public ApFisicaGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ApFisicaGrupo objeto = new ApFisicaGrupo();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setInstructor(rs.getString("INSTRUCTOR"));
		objeto.setCupo(rs.getString("CUPO"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setDia1(rs.getString("DIA1"));
		objeto.setCargas(rs.getString("CARGAS"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setHora(rs.getString("HORA"));
		objeto.setfCierre(rs.getString("F_CIERRE"));
		objeto.setAcceso(rs.getString("ACCESO"));
		objeto.setLiga(rs.getString("LIGA"));
		objeto.setSexo(rs.getString("SEXO"));
		
		return objeto;
	}
}