package aca.est.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstCcostoMapper implements RowMapper<EstCcosto>{
	
	public EstCcosto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EstCcosto objeto = new EstCcosto();
		
		objeto.setId(rs.getString("ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setcCostoId(rs.getString("CCOSTO_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setAlumnos(rs.getString("ALUMNOS"));
		objeto.setTotal(rs.getString("TOTAL"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
		objeto.setUbicacion(rs.getString("UBICACION"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setPortotal(rs.getString("PORTOTAL"));
		objeto.setImporte(rs.getString("IMPORTE"));
		
		return objeto;
	}
}