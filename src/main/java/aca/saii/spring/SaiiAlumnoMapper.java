package aca.saii.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaiiAlumnoMapper implements RowMapper<SaiiAlumno>{
	public SaiiAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaiiAlumno objeto = new SaiiAlumno();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPlantel(rs.getString("PLANTEL"));
		objeto.setPlanSep(rs.getString("PLAN_SEP"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPaterno(rs.getString("PATERNO"));
		objeto.setMaterno(rs.getString("MATERNO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanUm(rs.getString("PLAN_UM"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setEdad(rs.getString("EDAD"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setPrepaLugar(rs.getString("PREPA_LUGAR"));
		objeto.setUsado(rs.getString("USADO"));
		
		return objeto;
	}
}