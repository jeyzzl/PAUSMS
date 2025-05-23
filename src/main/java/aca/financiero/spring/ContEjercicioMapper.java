package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContEjercicioMapper implements RowMapper<ContEjercicio> {

	public ContEjercicio mapRow(ResultSet rs, int arg1) throws SQLException {
		ContEjercicio objeto = new ContEjercicio();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setMascBalance(rs.getString("MASC_BALANCE"));
		objeto.setMascAuxiliar(rs.getString("MASC_AUXILIAR"));
		objeto.setMascCcosto(rs.getString("MASC_CCOSTO"));
		objeto.setNivelContable(rs.getString("NIVEL_CONTABLE"));
		objeto.setNivelTauxiliar(rs.getString("NIVEL_TAUXILIAR"));			
		
		return objeto;
	}

}
