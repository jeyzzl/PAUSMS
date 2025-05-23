package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmEncuestaMapper implements RowMapper<AdmEncuesta> {
	@Override
	public AdmEncuesta mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmEncuesta objeto = new AdmEncuesta();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setRecomendacionId(rs.getString("RECOMENDACION_ID"));
		objeto.setR1(rs.getString("R1"));
		objeto.setR2(rs.getString("R2"));
		objeto.setR3(rs.getString("R3"));
		objeto.setR4(rs.getString("R4"));
		objeto.setR5(rs.getString("R5"));
		objeto.setR6(rs.getString("R6"));
		objeto.setR7(rs.getString("R7"));
		objeto.setR8(rs.getString("R8"));
		objeto.setR9(rs.getString("R9"));
		objeto.setR10(rs.getString("R10"));
		objeto.setR11(rs.getString("R11"));
		objeto.setR12(rs.getString("R12"));
		objeto.setR13(rs.getString("R13"));
		objeto.setR14(rs.getString("R14"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setConocer(rs.getString("CONOCER"));
		objeto.setRelacion(rs.getString("RELACION"));
		objeto.setConducta(rs.getString("CONDUCTA"));
		objeto.setOpinion(rs.getString("OPINION"));
		objeto.setCensura(rs.getString("CENSURA"));
		objeto.setAdicional(rs.getString("ADICIONAL"));
		objeto.setOtra(rs.getString("OTRA"));
		
		return objeto;
	}

}
