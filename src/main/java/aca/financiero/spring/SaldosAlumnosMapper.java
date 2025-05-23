package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaldosAlumnosMapper implements RowMapper<SaldosAlumnos> {

	public SaldosAlumnos mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaldosAlumnos objeto = new SaldosAlumnos();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setSaldoGlobal(rs.getString("SALDOGLOBAL"));
		objeto.setSaldo(rs.getString("SALDO"));
		objeto.setImporteContratos(rs.getString("IMPORTE_CONTRATOS"));
		objeto.setSVencido(rs.getString("SVENCIDO"));
		objeto.setDiferencia(rs.getString("DIFERENCIA"));
		
		return objeto;
	}

}
