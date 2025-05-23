package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoPlanMapper implements RowMapper<CargaGrupoPlan>{
	
	public CargaGrupoPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoPlan objeto = new CargaGrupoPlan();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEstudios(rs.getString("ESTUDIOS"));
		objeto.setOcupacion(rs.getString("OCUPACION"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setHorario(rs.getString("HORARIO"));
		objeto.setOficina(rs.getString("OFICINA"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setAtencion(rs.getString("ATENCION"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setPerspectiva(rs.getString("PERSPECTIVA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}