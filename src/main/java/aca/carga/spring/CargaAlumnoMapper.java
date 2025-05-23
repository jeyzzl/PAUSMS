package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaAlumnoMapper implements RowMapper<CargaAlumno>{
	
	public CargaAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaAlumno objeto = new CargaAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setConfirmar(rs.getString("CONFIRMAR"));
		objeto.setPago(rs.getString("PAGO"));
		objeto.setModo(rs.getString("MODO"));
		objeto.setBeca(rs.getString("BECA"));
		objeto.setCalculo(rs.getString("CALCULO"));	
		objeto.setComentario(rs.getString("COMENTARIO"));	
		objeto.setIngreso(rs.getString("INGRESO"));
		objeto.setMat(rs.getString("MAT"));
		
		return objeto;
	}
}