package aca.por.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorHoraMapper implements RowMapper<PorHora>{

	public PorHora mapRow(ResultSet rs, int arg1) throws SQLException {
		PorHora objeto = new PorHora();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setEquipoId(rs.getString("EQUIPO_ID"));
		objeto.setHora(rs.getString("HORA"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		
		return objeto;
	}

}
