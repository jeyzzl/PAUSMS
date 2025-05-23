package aca.saum.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class SaumMateriaMapper implements RowMapper<SaumMateria>{
	
	public SaumMateria mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaumMateria objeto = new SaumMateria();
		
		objeto.setId(rs.getString("ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setEnergia(rs.getString("ENERGIA"));
		objeto.setHumedad(rs.getString("HUMEDAD"));
		objeto.setFibra(rs.getString("FIBRA"));
		objeto.setCarbohidrato(rs.getString("CARBOHIDRATO"));
		objeto.setProteina(rs.getString("PROTEINA"));
		objeto.setLipido(rs.getString("LIPIDO"));
		objeto.setCeniza(rs.getString("CENIZA"));
		objeto.setSaturado(rs.getString("SATURADO"));
		objeto.setMono(rs.getString("MONO"));
		objeto.setPoli(rs.getString("POLI"));
		objeto.setColesterol(rs.getString("COLESTEROL"));
		objeto.setCalcio(rs.getString("CALCIO"));
		objeto.setFosforo(rs.getString("FOSFORO"));
		objeto.setHierro(rs.getString("HIERRO"));
		objeto.setMagnesio(rs.getString("MAGNESIO"));
		objeto.setSelenio(rs.getString("SELENIO"));
		objeto.setSodio(rs.getString("SODIO"));
		objeto.setPotasio(rs.getString("POTASIO"));
		objeto.setCinc(rs.getString("CINC"));
		objeto.setVitaminaa(rs.getString("VITAMINAA"));
		objeto.setAscorbico(rs.getString("ASCORBICO"));
		objeto.setTiamina(rs.getString("TIAMINA"));
		objeto.setRibo(rs.getString("RIBO"));
		objeto.setNiacina(rs.getString("NIACINA"));
		objeto.setPiridoxina(rs.getString("PIRIDOXINA"));
		objeto.setFolico(rs.getString("FOLICO"));
		objeto.setCobalamina(rs.getString("COBALAMINA"));
		
		return objeto;
	}
}