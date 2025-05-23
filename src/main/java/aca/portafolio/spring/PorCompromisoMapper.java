package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorCompromisoMapper implements RowMapper<PorCompromiso> {

	public PorCompromiso mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorCompromiso objeto = new PorCompromiso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setEducar(rs.getString("EDUCAR"));
		objeto.setModelar(rs.getString("MODELAR"));
		objeto.setInvestigar(rs.getString("INVESTIGAR"));
		objeto.setServir(rs.getString("SERVIR"));
		objeto.setProclamar(rs.getString("PROCLAMAR"));
		objeto.setEsperanza(rs.getString("ESPERANZA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
