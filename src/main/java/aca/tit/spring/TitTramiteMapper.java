package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitTramiteMapper implements RowMapper<TitTramite> {

	public TitTramite mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitTramite objeto = new TitTramite();
		
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setRecibo(rs.getString("RECIBO"));
		
		return objeto;
	}

}
