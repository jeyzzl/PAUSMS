package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumDocumentoMapper implements RowMapper<AlumDocumento>{
	@Override
	public AlumDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumDocumento objeto = new AlumDocumento();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFechaCrea(rs.getString("FECHA_CREA"));
		
		return objeto;
	}
}
