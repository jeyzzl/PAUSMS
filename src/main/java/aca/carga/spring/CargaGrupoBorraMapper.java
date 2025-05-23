package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoBorraMapper implements RowMapper<CargaGrupoBorra>{
	
	public CargaGrupoBorra mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoBorra objeto = new CargaGrupoBorra();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setIp(rs.getString("IP"));
		objeto.setNumEst(rs.getString("NUMEST"));
		objeto.setNumAct(rs.getString("NUMACT"));
	
		return objeto;
	}
}