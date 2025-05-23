package aca.pg.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PosArchDocAlumMapper implements RowMapper<PosArchDocAlum>{
	
	public PosArchDocAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PosArchDocAlum objeto = new PosArchDocAlum();		 
		objeto.setIddocumento(rs.getString("IDDOCUMENTO"));
		objeto.setImagen(rs.getInt("IMAGEN"));
		objeto.setHoja(rs.getString("HOJA"));
		objeto.setFuente(rs.getString("FUENTE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setOrigen(rs.getString("ORIGEN"));
		objeto.setFinsert(rs.getString("F_INSERT"));
		objeto.setFupdate(rs.getString("F_UPDATE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstado(rs.getString("ESTADO"));	
		
		return objeto;
	}
}