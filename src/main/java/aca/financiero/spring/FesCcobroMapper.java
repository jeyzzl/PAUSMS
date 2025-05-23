package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FesCcobroMapper implements RowMapper<FesCcobro> {

	public FesCcobro mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FesCcobro objeto = new FesCcobro();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloque(rs.getString("BLOQUE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.settAlumnoId(rs.getString("TALUMNO_ID"));
		objeto.settAlumno(rs.getString("TALUMNO"));
		objeto.setSemestre(rs.getString("SEMESTRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFormaPago(rs.getString("FORMAPAGO"));
		objeto.setReligion(rs.getString("RELIGION"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setResidencia(rs.getString("RESIDENCIA"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setFacultad(rs.getString("FACULTAD"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setNombrePlan(rs.getString("NOMBRE_PLAN"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setInscrito(rs.getString("INSCRITO"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		
		return objeto;
	}

}
