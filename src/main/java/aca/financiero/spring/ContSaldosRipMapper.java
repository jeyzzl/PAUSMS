package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContSaldosRipMapper implements RowMapper<ContSaldosRip> {
	public ContSaldosRip mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ContSaldosRip objeto = new ContSaldosRip();
		
		objeto.setId(rs.getString("ID"));
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setSaldo(rs.getString("SALDO"));
		objeto.setNaturaleza(rs.getString("NATURALEZA"));
		objeto.setQuienRegistro(rs.getString("QUIEN_REGISTRO"));
		objeto.setCuandoRegistro(rs.getString("CUANDO_REGISTRO"));
		objeto.setQuienModifico(rs.getString("QUIEN_MODIFICO"));
		objeto.setCuandoModifico(rs.getString("CUANDO_MODIFICO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setContabilidad(rs.getString("CONTABILIDAD"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setObservacion(rs.getString("OBSERVACION"));
		
		return objeto;
	}
}
