package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumSaldosMapper implements RowMapper<AlumSaldos> {

	public AlumSaldos mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlumSaldos objeto = new AlumSaldos();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setSaldo(rs.getString("SALDO"));
		objeto.setImporteContratos(rs.getString("IMPORTE_CONTRATOS"));
		objeto.setsVencido(rs.getString("SVENCIDO"));
		objeto.setDiferencia(rs.getString("DIFERENCIA"));
		
		return objeto;
	}

}
