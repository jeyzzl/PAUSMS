package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitAlumnoMapper implements RowMapper<TitAlumno>{
	public TitAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitAlumno objeto = new TitAlumno();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setXml(rs.getString("XML"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		objeto.setXmlSep(rs.getBytes("XML_SEP"));
		objeto.setFolioFisico(rs.getString("FOLIO_FISICO"));
		objeto.setFechaRes(rs.getString("FECHA_RES"));
		objeto.setFolioControl(rs.getString("FOLIO_CONTROL"));
		
		return objeto;
	}
}