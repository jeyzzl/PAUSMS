package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabInformeAlumMapper implements RowMapper<TrabInformeAlum>{
	
public TrabInformeAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabInformeAlum objeto = new TrabInformeAlum();		
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setInformeId(rs.getString("INFORME_ID"));
		objeto.setDeptId(rs.getString("DEPT_ID"));	
		objeto.setCatId(rs.getString("CAT_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setHoraInicio(rs.getString("HORA_INICIO"));
		objeto.setHoraFin(rs.getString("HORA_FIN"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
}
	
	
}
