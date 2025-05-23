package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronValorMapper implements RowMapper<PronValor> {

	public PronValor mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronValor objeto = new PronValor();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setAmor(rs.getString("AMOR"));
		objeto.setLealtad(rs.getString("LEALTAD"));
		objeto.setConfianza(rs.getString("CONFIANZA"));
		objeto.setReverencia(rs.getString("REVERENCIA"));
		objeto.setObediencia(rs.getString("OBEDIENCIA"));
		objeto.setArmonia(rs.getString("ARMONIA"));
		objeto.setRespeto(rs.getString("RESPETO"));
		objeto.setPureza(rs.getString("PUREZA"));
		objeto.setHonestidad(rs.getString("HONESTIDAD"));
		objeto.setVeracidad(rs.getString("VERACIDAD"));
		objeto.setContentamiento(rs.getString("CONTENTAMIENTO"));
		objeto.setServicio(rs.getString("SERVICIO"));
		
		return objeto;
	}

}
