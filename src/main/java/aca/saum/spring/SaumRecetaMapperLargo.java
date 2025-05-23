package aca.saum.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class SaumRecetaMapperLargo implements RowMapper<SaumReceta>{
	public SaumReceta mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaumReceta objeto = new SaumReceta();
		
		objeto.setId(rs.getString("ID"));
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setCalorias(rs.getString("CALORIAS"));
		objeto.setCarbohidratos(rs.getString("CARBOHIDRATOS"));	
		objeto.setColesterol(rs.getString("COLESTEROL"));
		objeto.setColor(rs.getString("COLOR"));	
		objeto.setFibra(rs.getString("FIBRA"));
		objeto.setForma(rs.getString("FORMA"));
		objeto.setGrasa(rs.getString("GRASA"));	
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPorcion(rs.getString("PORCION"));	
		objeto.setProteinas(rs.getString("PROTEINAS"));
		objeto.setRendimiento(rs.getString("RENDIMIENTO"));
		objeto.setSabor(rs.getString("SABOR"));	
		objeto.setSodio(rs.getString("SODIO"));
		objeto.setTemperatura(rs.getString("TEMPERATURA"));	
		objeto.setTextura(rs.getString("TEXTURA"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setTipoPlato(rs.getString("TIPO_PLATO"));
		objeto.setImagen(rs.getBytes("IMAGEN"));
		
		return objeto;
	}
}