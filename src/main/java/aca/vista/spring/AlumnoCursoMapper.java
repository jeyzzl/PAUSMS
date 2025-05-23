package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoCursoMapper implements RowMapper<AlumnoCurso>{

	public AlumnoCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		AlumnoCurso objeto = new AlumnoCurso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));		
		objeto.setCargaId(rs.getString("CARGA_ID"));		
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));		
		objeto.setCarreraId(rs.getString("CARRERA_ID"));		
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));		
		objeto.setPlanId(rs.getString("PLAN_ID"));		
		objeto.setCursoId(rs.getString("CURSO_ID"));		
		objeto.setNombreCurso(rs.getString("NOMBRE_CURSO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setHt(rs.getString("HT"));
		objeto.setHp(rs.getString("HP"));
		objeto.setNotaAprobatoria(rs.getString("NOTA_APROBATORIA"));
		objeto.setCursoId2(rs.getString("CURSO_ID2"));
		objeto.setNombreCurso2(rs.getString("NOMBRE_CURSO2"));
		objeto.setCreditos2(rs.getString("CREDITOS2"));		
		objeto.setHt2(rs.getString("HT2"));
		objeto.setHp2(rs.getString("HP2"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setFEvaluacion(rs.getString("F_EVALUACION"));
		objeto.setTipoCalId(rs.getString("TIPOCAL_ID"));
		objeto.setNotaExtra(rs.getString("NOTA_EXTRA"));
		objeto.setFExtra(rs.getString("F_EXTRA"));
		objeto.setConvalidacion(rs.getString("CONVALIDACION"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setFTitulo(rs.getString("F_TITULO"));		
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setMaestro(rs.getString("MAESTRO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCorreccion(rs.getString("CORRECCION"));
		objeto.setOptativa(rs.getString("OPTATIVA"));
		objeto.setNotaConva(rs.getString("NOTA_CONVA"));
		
		return objeto;
	}

}
