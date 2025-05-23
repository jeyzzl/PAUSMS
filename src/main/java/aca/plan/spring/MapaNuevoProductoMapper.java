package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoProductoMapper implements RowMapper<MapaNuevoProducto>{

	public MapaNuevoProducto mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoProducto objeto = new MapaNuevoProducto();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setProductoId(rs.getString("PRODUCTO_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setTipo(rs.getString("TIPO"));

		return objeto;
	}

}
