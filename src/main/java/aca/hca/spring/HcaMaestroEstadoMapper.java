package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaMaestroEstadoMapper implements RowMapper<HcaMaestroEstado> {
	public HcaMaestroEstado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaMaestroEstado objeto = new HcaMaestroEstado();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setSemanal(rs.getString("T_SEMANAL"));
		objeto.setSemestral(rs.getString("T_SEMESTRAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}