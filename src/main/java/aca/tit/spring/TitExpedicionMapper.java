package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitExpedicionMapper implements RowMapper<TitExpedicion>{
	public TitExpedicion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitExpedicion objeto = new TitExpedicion();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFechaExpedicion(rs.getString("FECHAEXPEDICION"));
		objeto.setModalidadId(rs.getString("MODALIDADID"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.setFechaExamen(rs.getString("FECHAEXAMEN"));
		objeto.setFechaExencion(rs.getString("FECHAEXENCION"));
		objeto.setServicio(rs.getString("SERVICIO"));
		objeto.setFundamentoId(rs.getString("FUNDAMENTOID"));
		objeto.setFundamento(rs.getString("FUNDAMENTO"));
		objeto.setEntidadId(rs.getString("ENTIDADID"));
		objeto.setEntidad(rs.getString("ENTIDAD"));
		
		return objeto;
	}
}