package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecPeriodoMapper implements RowMapper<BecPeriodo> {
	
	@Override
	public BecPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecPeriodo objeto = new BecPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFechasPrepa(rs.getString("FECHAS_PREPA"));
		objeto.setFechasUniversidad(rs.getString("FECHAS_UNIVERSIDAD"));
		objeto.setFechasPeriodo(rs.getString("FECHAS_PERIODO"));
		
		return objeto;
	}
}
