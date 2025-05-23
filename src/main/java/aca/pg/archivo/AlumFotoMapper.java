package aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumFotoMapper implements RowMapper<AlumFoto>{
	
	public AlumFoto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumFoto objeto = new AlumFoto();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFoto(rs.getBytes("FOTO"));		
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setRechazada(rs.getString("RECHAZADA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}