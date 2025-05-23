package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvPresupuestoMapper implements RowMapper<InvPresupuesto> {
	
	public InvPresupuesto mapRow(ResultSet rs, int rowNum) throws SQLException {
		InvPresupuesto objeto = new InvPresupuesto();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setPresupuestoId(rs.getString("PRESUPUESTO_ID"));
		objeto.setPresupuestoNombre(rs.getString("PRESUPUESTO_NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setMonto(rs.getString("MONTO"));
		
		return objeto;
	}

}
