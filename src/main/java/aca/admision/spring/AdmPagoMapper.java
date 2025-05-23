package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPagoMapper implements RowMapper<AdmPago>{

	@Override
	public AdmPago mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmPago objeto = new AdmPago();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setMetodo(rs.getString("METODO"));
		objeto.setRecibo(rs.getString("RECIBO"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}
	
}