package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumAcademicoMapper implements RowMapper<AlumAcademico>{
	@Override
	public AlumAcademico mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumAcademico objeto = new AlumAcademico();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipoAlumno(rs.getString("TIPO_ALUMNO"));
		objeto.setClasFin(rs.getString("CLAS_FIN"));
		objeto.setObrero(rs.getString("OBRERO"));
		objeto.setObreroInstitucion(rs.getString("OBRERO_INSTITUCION"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		objeto.setFSolicitud(rs.getString("F_SOLICITUD"));
		objeto.setFAdmision(rs.getString("F_ADMISION"));
		objeto.setFAlta(rs.getString("F_ALTA"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setExtensionId(rs.getString("EXTENSION_ID"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setSemestre(rs.getString("SEMESTRE"));
		objeto.setHoStatus(rs.getString("HO_STATUS"));
		objeto.setPrepaLugar(rs.getString("PREPA_LUGAR"));	
		objeto.setRequerido(rs.getString("REQUERIDO"));
		objeto.setNivelInicioId(rs.getString("NIVEL_INICIO_ID"));
		objeto.setAcomodoId(rs.getString("ACOMODO_ID"));
		
		return objeto;
	}
}