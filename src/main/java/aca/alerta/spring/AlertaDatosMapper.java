package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaDatosMapper implements RowMapper<AlertaDatos> {
	@Override
	public AlertaDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		AlertaDatos objeto = new AlertaDatos();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setProcedencia(rs.getString("PROCEDENCIA"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setCelular(rs.getString("CELULAR"));
		objeto.setSintomas(rs.getString("SINTOMAS"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setLugar1(rs.getString("LUGAR1"));
		objeto.setLugar2(rs.getString("LUGAR2"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setOtro(rs.getString("OTRO"));
		objeto.setReferente(rs.getString("REFERENTE"));
		
		return objeto;
	}
}
