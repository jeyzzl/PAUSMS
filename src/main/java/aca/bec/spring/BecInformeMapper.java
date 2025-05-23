package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecInformeMapper implements RowMapper<BecInforme> {
	
	@Override
	public BecInforme mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecInforme objeto = new BecInforme();
		
		objeto.setInformeId(rs.getString("INFORME_ID"));
		objeto.setInformeNombre(rs.getString("INFORME_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setNivel(rs.getString("NIVEL"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
