package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitTramiteMapper implements RowMapper<BitTramite> {
	public BitTramite mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitTramite objeto = new BitTramite();
		
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setMinimo(rs.getString("MINIMO"));
		objeto.setMaximo(rs.getString("MAXIMO"));
		objeto.setPromedio(rs.getString("PROMEDIO"));
		objeto.setRequisitos(rs.getString("REQUISITOS"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setSolAlumno(rs.getString("SOL_ALUMNO"));
		objeto.setCosto(rs.getString("COSTO"));
		objeto.setImporte(rs.getString("IMPORTE"));

		return objeto;
	}
}