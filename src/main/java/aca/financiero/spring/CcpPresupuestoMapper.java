package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CcpPresupuestoMapper implements RowMapper<CcpPresupuesto>{

	public CcpPresupuesto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CcpPresupuesto objeto = new CcpPresupuesto();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CTAMAYOR"));		
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setIdAuxiliar(rs.getString("ID_AUXILIAR"));		
		objeto.setMes(rs.getString("MES"));
		objeto.setImporte(rs.getString("IMPORTE"));
		
		return objeto;
	}

}