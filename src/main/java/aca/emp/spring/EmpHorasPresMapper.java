package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpHorasPresMapper implements RowMapper<EmpHorasPres>{
	
	public EmpHorasPres mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpHorasPres objeto = new EmpHorasPres();
		
		objeto.setDepartamentoId(rs.getString("DEPARTAMENTO_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setYear(rs.getString("YEAR"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setSaldoAnt(rs.getString("SALDO_ANT"));
		
		return objeto;
	}
}