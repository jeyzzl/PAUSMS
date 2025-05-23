package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmDirectoMapper implements RowMapper<AdmDirecto> {

	public AdmDirecto mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmDirecto objeto = new AdmDirecto();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setReciente(rs.getString("RECIENTE"));
		objeto.setTetra(rs.getString("TETRA"));
		objeto.setRecPrepa(rs.getBytes("REC_PREPA"));
		objeto.setRecVre(rs.getBytes("REC_VRE"));
		objeto.setEnvioSol(rs.getString("ENVIO_SOL"));
		objeto.setPrepaValido(rs.getString("PREPA_VALIDO"));
		objeto.setVreValido(rs.getString("VRE_VALIDO"));
		objeto.setNombrePrepa(rs.getString("NOMBRE_PREPA"));
		objeto.setNombreVre(rs.getString("NOMBRE_VRE"));

		return objeto;
	}

}
