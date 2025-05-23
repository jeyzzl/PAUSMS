package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocInicioMapper implements RowMapper<SsocInicio>{
	
	public SsocInicio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SsocInicio objeto = new SsocInicio();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
		objeto.setSemestre(rs.getString("SEMESTRE"));
		
		return objeto;
	}
}