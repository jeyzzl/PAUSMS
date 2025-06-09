package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumUbicacionMapper implements RowMapper<AlumUbicacion>{
	@Override
	public AlumUbicacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumUbicacion objeto = new AlumUbicacion();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setpNombre(rs.getString("P_NOMBRE"));
		objeto.setpReligion(rs.getString("P_RELIGION"));
		objeto.setpNacionalidad(rs.getString("P_NACIONALIDAD"));
		objeto.setmNombre(rs.getString("M_NOMBRE"));
		objeto.setmReligion(rs.getString("M_RELIGION"));
		objeto.setmNacionalidad(rs.getString("M_NACIONALIDAD"));
		objeto.settNombre(rs.getString("T_NOMBRE"));
		objeto.settDireccion(rs.getString("T_DIRECCION"));
		objeto.settColonia(rs.getString("T_COLONIA"));
		objeto.settCodigo(rs.getString("T_CODIGO"));
		objeto.settApartado(rs.getString("T_APARTADO"));
		objeto.settTelefono(rs.getString("T_TELEFONO"));
		objeto.settEmail(rs.getString("T_EMAIL"));
		objeto.settPais(rs.getString("T_PAIS"));
		objeto.settEstado(rs.getString("T_ESTADO"));
		objeto.settCiudad(rs.getString("T_CIUDAD"));
		objeto.settCelular(rs.getString("T_CELULAR"));
		objeto.settComunica(rs.getString("T_COMUNICA"));
		objeto.settEdoCd(rs.getString("T_EDOCD"));
		objeto.setCodigoPadre(rs.getString("CODIGO_PADRE"));
		objeto.setCodigoMadre(rs.getString("CODIGO_MADRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setVacuna(rs.getString("VACUNA"));
		objeto.setDescripcionVacuna(rs.getString("DESCRIPCION_VACUNA"));
		objeto.setRecogidaId(rs.getString("RECOGIDA_ID"));
		objeto.setpPais(rs.getString("P_PAIS"));
		objeto.setpEstado(rs.getString("P_ESTADO"));
		objeto.setpCiudad(rs.getString("P_CIUDAD"));
		objeto.setpOrigen(rs.getString("P_ORIGEN"));
		objeto.setmPais(rs.getString("M_PAIS"));
		objeto.setmEstado(rs.getString("M_ESTADO"));
		objeto.setmCiudad(rs.getString("M_CIUDAD"));
		objeto.setmOrigen(rs.getString("M_ORIGEN"));
		
		return objeto;
	}
}