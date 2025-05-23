package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvProyectoMapper implements RowMapper<InvProyecto> {
	public InvProyecto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InvProyecto objeto = new InvProyecto();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setProyectoNombre(rs.getString("PROYECTO_NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setLinea(rs.getString("LINEA"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		objeto.setResumen(rs.getString("RESUMEN"));
		objeto.setEstadoArte(rs.getString("ESTADO_ARTE"));
		objeto.setDocumento(rs.getString("DOCUMENTO"));
		objeto.setEstado(rs.getString("ESTADO"));		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setAntecedentes(rs.getString("ANTECEDENTES"));
		objeto.setJustificacion(rs.getString("JUSTIFICACION"));
		objeto.setResDocente(rs.getString("RES_DOCENTE"));
		objeto.setResAlumno(rs.getString("RES_ALUMNO"));		
		objeto.setInvestigadores(rs.getString("INVESTIGADORES"));		
		objeto.setTipoDocumento(rs.getString("TIPO_DOCUMENTO"));		
		
		return objeto;
	}
}