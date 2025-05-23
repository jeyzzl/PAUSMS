package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatCarreraMapper implements RowMapper<CatCarrera> {
	public CatCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatCarrera carrera = new CatCarrera();
		
		carrera.setFacultadId(rs.getString("FACULTAD_ID"));
		carrera.setCarreraId(rs.getString("CARRERA_ID"));
		carrera.setNivelId(rs.getString("NIVEL_ID"));
		carrera.setTitulo(rs.getString("TITULO"));
		carrera.setNombreCarrera(rs.getString("NOMBRE_CARRERA"));
		carrera.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		carrera.setCcostoId(rs.getString("CCOSTO_ID"));
		carrera.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		carrera.setGlsetno(rs.getString("GLSETNO"));
		carrera.setCostcentcd(rs.getString("COSTCENTCD"));
		carrera.setDscntpct(rs.getString("DSCNTPCT"));
		
		return carrera;
	}
}
