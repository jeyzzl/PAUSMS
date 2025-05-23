package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumPersonalMapper implements RowMapper<AlumPersonal>{
	@Override
	public AlumPersonal mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumPersonal objeto = new AlumPersonal();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setNombreLegal(rs.getString("NOMBRE_LEGAL"));
		objeto.setFNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setSexo(rs.getString("SEXO"));
		objeto.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setBautizado(rs.getString("BAUTIZADO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setCodigoSe(rs.getString("CODIGO_SE"));
		objeto.setFCreado (rs.getString("F_CREADO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setCredencial(rs.getString("CREDENCIAL"));
		objeto.setUsAlta(rs.getString("US_ALTA"));
		objeto.setNum_oferta(rs.getString("NUM_OFERTA"));
		objeto.setfBautizado(rs.getString("F_BAUTIZADO"));
		objeto.setCulturalId(rs.getString("CULTURAL_ID"));
		objeto.setRegionId(rs.getString("REGION_ID"));
		objeto.setResPaisId(rs.getString("RES_PAIS_ID"));
		objeto.setResEstadoId(rs.getString("RES_ESTADO_ID"));
		objeto.setResCiudadId(rs.getString("RES_CIUDAD_ID"));
		objeto.setSync(rs.getString("SYNC"));
		
		return objeto;
	}
}