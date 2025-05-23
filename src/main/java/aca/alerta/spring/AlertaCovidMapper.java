package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaCovidMapper implements RowMapper<AlertaCovid> {
	@Override
	public AlertaCovid mapRow(ResultSet rs, int arg1) throws SQLException {
		AlertaCovid objeto = new AlertaCovid();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFechaUno(rs.getString("FECHA_UNO"));
		objeto.setFechaDos(rs.getString("FECHA_DOS"));
		objeto.setPaisUno(rs.getString("PAIS_UNO"));
		objeto.setPaisDos(rs.getString("PAIS_DOS"));
		objeto.setCiudadUno(rs.getString("CIUDAD_UNO"));
		objeto.setCiudadDos(rs.getString("CIUDAD_DOS"));
		objeto.setContacto(rs.getString("CONTACTO"));
		objeto.setContactoFecha(rs.getString("CONTACTO_FECHA"));
		objeto.setFiebre(rs.getString("FIEBRE"));
		objeto.setTos(rs.getString("TOS"));
		objeto.setCabeza(rs.getString("CABEZA"));
		objeto.setRespirar(rs.getString("RESPIRAR"));
		objeto.setGarganta(rs.getString("GARGANTA"));
		objeto.setEscurrimiento(rs.getString("ESCURRIMIENTO"));
		objeto.setOlfato(rs.getString("OLFATO"));
		objeto.setGusto(rs.getString("GUSTO"));
		objeto.setCuerpo(rs.getString("CUERPO"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}
}
