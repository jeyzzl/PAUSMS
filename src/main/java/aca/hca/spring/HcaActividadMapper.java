package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaActividadMapper implements RowMapper<HcaActividad> {
	public HcaActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaActividad objeto = new HcaActividad();
		
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setActividadNombre(rs.getString("ACTIVIDAD_NOMBRE"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setModificable(rs.getString("MODIFICABLE"));
		
		return objeto;
	}
}