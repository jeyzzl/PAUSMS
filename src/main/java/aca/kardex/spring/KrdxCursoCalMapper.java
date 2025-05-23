package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxCursoCalMapper implements RowMapper<KrdxCursoCal> {
	public KrdxCursoCal mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxCursoCal objeto = new KrdxCursoCal();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		objeto.setTipoNota(rs.getString("TIPO_NOTA"));
		objeto.setTipoCalId(rs.getString("TIPOCAL_ID"));
				
		return objeto;
	}
}