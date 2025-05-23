package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecPuestoAlumnoMapper implements RowMapper<BecPuestoAlumno> {
	
	@Override
	public BecPuestoAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecPuestoAlumno objeto = new BecPuestoAlumno();
		
		objeto.setPuestoId(rs.getString("PUESTO_ID"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setCategoriaId(rs.getString("CATEGORIA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}
