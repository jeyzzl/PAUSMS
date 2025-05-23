package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoHoraMapper implements RowMapper<CargaGrupoHora>{
	
	public CargaGrupoHora mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoHora objeto = new CargaGrupoHora();
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		
		return objeto;
	}
}