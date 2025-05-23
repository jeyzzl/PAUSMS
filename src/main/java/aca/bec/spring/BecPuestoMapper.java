package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecPuestoMapper implements RowMapper<BecPuesto> {
	
	@Override
	public BecPuesto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecPuesto objeto = new BecPuesto();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setCategoriaId(rs.getString("CATEGORIA_ID"));
		objeto.setJustificacion(rs.getString("JUSTIFICACION"));
		objeto.setFuncion(rs.getString("FUNCION"));
		objeto.setCompetencias(rs.getString("COMPETENCIAS"));
		objeto.setCertificaciones(rs.getString("CERTIFICACIONES"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setOtrasComp(rs.getString("OTRAS_COMP"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		
		return objeto;
	}
}