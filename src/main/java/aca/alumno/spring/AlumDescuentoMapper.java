package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumDescuentoMapper implements RowMapper<AlumDescuento>{
	@Override
	public AlumDescuento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumDescuento objeto = new AlumDescuento();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setDescuentoId(rs.getString("DESCUENTO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setTipoMatricula(rs.getString("TIPO_MATRICULA"));
		objeto.setEnsenanza(rs.getString("ENSENANZA"));
		objeto.setTipoEnsenanza(rs.getString("TIPO_ENSENANZA"));
		objeto.setInternado(rs.getString("INTERNADO"));
		objeto.setTipoInternado(rs.getString("TIPO_INTERNADO"));
		objeto.setTotal(rs.getString("TOTAL"));
		objeto.setObservaciones(rs.getString("OBSERVACIONES"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setAplicado(rs.getString("APLICADO"));
	

		return objeto;
	}
}