package aca.acceso;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoMapper implements RowMapper<Acceso>{
	@Override
	public Acceso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Acceso objeto = new Acceso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setAdministrador(rs.getString("ADMINISTRADOR"));
		objeto.setSupervisor(rs.getString("SUPERVISOR"));
		objeto.setCotejador(rs.getString("COTEJADOR"));
		objeto.setAccesos(rs.getString("ACCESOS"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setIngreso(rs.getString("INGRESO"));
		objeto.setConvalida(rs.getString("CONVALIDA"));
		objeto.setBecas(rs.getString("BECAS"));
		objeto.setPortalAlumno(rs.getString("PORTAL_ALUMNO"));
		objeto.setPortalMaestro(rs.getString("PORTAL_MAESTRO"));
		objeto.setIdioma(rs.getString("IDIOMA"));
		objeto.setMenu(rs.getString("MENU"));		
		
		return objeto;
	}
}