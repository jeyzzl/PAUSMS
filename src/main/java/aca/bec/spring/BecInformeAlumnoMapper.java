package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecInformeAlumnoMapper implements RowMapper<BecInformeAlumno> {
	
	@Override
	public BecInformeAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecInformeAlumno objeto = new BecInformeAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setInformeId(rs.getString("INFORME_ID"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setPuestoId(rs.getString("PUESTO_ID"));
		objeto.setTardanzas(rs.getString("TARDANZAS"));
		objeto.setAusencias(rs.getString("AUSENCIAS"));
		objeto.setPuntualidad(rs.getString("PUNTUALIDAD"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFuncion(rs.getString("FUNCION"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setIniciativa(rs.getString("INICIATIVA"));
		objeto.setRelacion(rs.getString("RELACION"));
		objeto.setRespeto(rs.getString("RESPETO"));
		objeto.setProductivo(rs.getString("PRODUCTIVO"));
		objeto.setCuidado(rs.getString("CUIDADO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setUsuario(rs.getString("USUARIO"));
				
		return objeto;
	}
}
