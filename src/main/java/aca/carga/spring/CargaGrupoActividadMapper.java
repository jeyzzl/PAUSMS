package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoActividadMapper implements RowMapper<CargaGrupoActividad>{
	
	public CargaGrupoActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoActividad objeto = new CargaGrupoActividad();
		
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setActividadE42(rs.getString("ACTIVIDAD_E42"));
		objeto.setAgendadaE42(rs.getString("AGENDADA_E42"));
		objeto.setEnviar(rs.getString("ENVIAR"));
		
		return objeto;
	}
}