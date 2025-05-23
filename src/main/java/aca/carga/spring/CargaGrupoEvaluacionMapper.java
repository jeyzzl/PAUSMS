package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoEvaluacionMapper implements RowMapper<CargaGrupoEvaluacion>{
	
	public CargaGrupoEvaluacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoEvaluacion objeto = new CargaGrupoEvaluacion();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNombreEvaluacion(rs.getString("NOMBRE_EVALUACION")==null?"X":rs.getString("NOMBRE_EVALUACION"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstrategiaId(rs.getString("ESTRATEGIA_ID"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setEvaluacionE42(rs.getString("EVALUACION_E42"));
		objeto.setEnviar(rs.getString("ENVIAR"));
		return objeto;
	}
}