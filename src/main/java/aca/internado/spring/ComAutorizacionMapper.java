package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ComAutorizacionMapper implements RowMapper<ComAutorizacion> {
	public ComAutorizacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		ComAutorizacion objeto = new ComAutorizacion();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setNumComidas(rs.getString("NUM_COMIDAS"));
		objeto.setTipoComida(rs.getString("TIPO_COMIDA"));
		objeto.setFechaFinal(rs.getString("FECHA_INICIAL"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setCliente(rs.getString("CLIENTE"));
		objeto.setPaquete(rs.getString("PAQUETE"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloque(rs.getString("BLOQUE"));
		
		return objeto;
	}

}
