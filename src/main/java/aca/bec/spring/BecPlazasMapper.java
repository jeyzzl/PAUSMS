package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecPlazasMapper implements RowMapper<BecPlazas> {
	
	@Override
	public BecPlazas mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecPlazas objeto = new BecPlazas();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setNumPlazas(rs.getString("NUM_PLAZAS"));
		objeto.setNumIndustriales(rs.getString("NUM_INDUSTRIALES"));
		objeto.setNumTemporales(rs.getString("NUM_TEMPORALES"));
		objeto.setNumPreIndustriales(rs.getString("NUM_PREINDUSTRIALES"));
		objeto.setNumPosgrado(rs.getString("NUM_POSGRADO"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setContacto(rs.getString("CONTACTO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCorreo(rs.getString("CORREO"));
		
		return objeto;
	}
}