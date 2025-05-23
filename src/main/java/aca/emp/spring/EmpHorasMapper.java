package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpHorasMapper implements RowMapper<EmpHoras>{
	
	public EmpHoras mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpHoras objeto = new EmpHoras();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setFrecuencia(rs.getString("FRECUENCIA"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.setPrecio(rs.getString("PRECIO"));
		objeto.setMateria(rs.getString("MATERIA"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setContratoId(rs.getString("CONTRATO_ID"));
		objeto.setTipoPagoId(rs.getString("TIPOPAGO_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		
		return objeto;
	}
}