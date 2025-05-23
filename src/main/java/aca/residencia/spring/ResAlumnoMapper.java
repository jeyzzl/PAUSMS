package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResAlumnoMapper implements RowMapper<ResAlumno>{
	
	public ResAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResAlumno objeto = new ResAlumno();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setMunicipio(rs.getString("MUNICIPIO"));
		objeto.setTelArea(rs.getString("TEL_AREA"));
		objeto.setTelNum(rs.getString("TEL_NUMERO"));
		objeto.setTutNombre(rs.getString("TUT_NOMBRE"));
		objeto.setTutApellidos(rs.getString("TUT_APELLIDOS"));
		objeto.setRazon(rs.getString("RAZON"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNumero(rs.getString("NUMERO"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setEsPermanente(rs.getString("ESPERMANENTE"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		
		return objeto;
	}
}