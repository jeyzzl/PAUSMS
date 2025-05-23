package aca.saum.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaumIngredienteMapper implements RowMapper<SaumIngrediente>{
	
	public SaumIngrediente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaumIngrediente objeto = new SaumIngrediente();
		
		objeto.setId(rs.getString("ID"));		
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setEtapaId(rs.getString("ETAPA_ID"));
		objeto.setMateriaId(rs.getString("MATERIA_ID"));
		objeto.setPresentacion(rs.getString("PRESENTACION"));
		objeto.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
		objeto.setRecetaId(rs.getString("RECETA_ID"));
		
		return objeto;
	}
}