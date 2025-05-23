package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatPatrocinadorMapper implements RowMapper<CatPatrocinador>{
public CatPatrocinador mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatPatrocinador objeto = new CatPatrocinador();		
		
		objeto.setPatrocinadorId(rs.getString("PATROCINADOR_ID"));
		objeto.setNombrePatrocinador(rs.getString("NOMBRE"));	
		objeto.setDetalles(rs.getString("DETALLES"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
}

}
