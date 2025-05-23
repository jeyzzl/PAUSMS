package aca.notifica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotiProcesoPendienteMapper implements RowMapper<NotiProcesoPendiente>{
	
	public NotiProcesoPendiente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		NotiProcesoPendiente objeto = new NotiProcesoPendiente();
		objeto.setId(rs.getString("ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));		
		objeto.setDatos(rs.getString("DATOS"));
		
		return objeto;
	}
}