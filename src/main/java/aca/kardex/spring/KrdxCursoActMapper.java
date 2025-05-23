package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxCursoActMapper implements RowMapper<KrdxCursoAct> {
	public KrdxCursoAct mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxCursoAct objeto = new KrdxCursoAct();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCursoId2(rs.getString("CURSO_ID2"));
		objeto.setTipoCalId(rs.getString("TIPOCAL_ID"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setfNota(rs.getString("F_NOTA"));
		objeto.setNotaExtra(rs.getString("NOTA_EXTRA"));
		objeto.setfExtra(rs.getString("F_EXTRA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setfTitulo(rs.getString("F_TITULO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setCorreccion(rs.getString("CORRECCION"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setFrecuencia(rs.getString("FRECUENCIA"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setConfirmar(rs.getString("CONFIRMAR"));
				
		return objeto;
	}
}