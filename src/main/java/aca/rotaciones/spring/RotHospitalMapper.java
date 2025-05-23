package aca.rotaciones.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RotHospitalMapper implements RowMapper<RotHospital>{

	public RotHospital mapRow(ResultSet rs, int arg1) throws SQLException {
		RotHospital objeto = new RotHospital();
		
		objeto.setHospitalId(rs.getString("HOSPITAL_ID"));
		objeto.setHospitalNombre(rs.getString("HOSPITAL_NOMBRE"));
		objeto.setHospitalCorto(rs.getString("HOSPITAL_CORTO"));
		objeto.setInstitucionId(rs.getString("INSTITUCION_ID"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setMunEdo(rs.getString("MUN_EDO"));
		objeto.setPais(rs.getString("PAIS"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setFax(rs.getString("FAX"));
		objeto.setMedico(rs.getString("MEDICO"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setSaludo(rs.getString("SALUDO"));

		return objeto;
	}

}
