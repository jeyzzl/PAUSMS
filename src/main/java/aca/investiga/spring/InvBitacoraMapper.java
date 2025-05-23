package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvBitacoraMapper implements RowMapper<InvBitacora> {

	public InvBitacora mapRow(ResultSet rs, int rowNum) throws SQLException {
		InvBitacora objeto = new InvBitacora();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstadoOld(rs.getString("ESTADO_OLD"));
		objeto.setEstadoNew(rs.getString("ESTADO_NEW"));
		
		return objeto;
	}

}
