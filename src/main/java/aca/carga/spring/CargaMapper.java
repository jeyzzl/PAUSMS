package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaMapper implements RowMapper<Carga>{
	
	public Carga mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Carga objeto = new Carga();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setNombreCarga(rs.getString("NOMBRE_CARGA"));
		objeto.setFCreada(rs.getString("F_CREADA"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFFinal(rs.getString("F_FINAL"));
		objeto.setFExtra(rs.getString("F_EXTRA"));
		objeto.setNumCursos(rs.getString("NUM_CURSOS"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setTipoCarga(rs.getString("TIPOCARGA"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.setFinServicios(rs.getString("FIN_SERVICIOS"));
		objeto.setEvalua(rs.getString("EVALUA"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setPrioridad(rs.getString("PRIORIDAD"));
		objeto.setIniInternado(rs.getString("INI_INTERNADO"));
		objeto.setFinInternado(rs.getString("FIN_INTERNADO"));
		objeto.setSecuencia(rs.getString("SECUENCIA"));
		objeto.setBloqueo(rs.getString("BLOQUEO"));
		
		return objeto;
	}
}