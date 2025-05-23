package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResDatosMapper implements RowMapper<ResDatos>{
	
	public ResDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResDatos objeto = new ResDatos();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setMpio(rs.getString("MUNICIPIO"));
		objeto.setTelArea(rs.getString("TEL_AREA"));
		objeto.setTelNum(rs.getString("TEL_NUMERO"));
		objeto.setNombreTut(rs.getString("TUT_NOMBRE"));
		objeto.setApellidoTut(rs.getString("TUT_APELLIDOS"));
		objeto.setRazon(rs.getString("RAZON"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNumero(rs.getString("NUMERO"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		
		return objeto;
	}
}