package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpContratoMapper implements RowMapper<EmpContrato> {
	public EmpContrato mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpContrato objeto = new EmpContrato();
		
		objeto.setContratoId(rs.getString("CONTRATO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));	
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCosto(rs.getString("COSTO"));	
		objeto.setComentario(rs.getString("COMENTARIO"));		
		objeto.setEstado(rs.getString("ESTADO"));	
		objeto.setFechaIni(rs.getString("FECHA_INI"));	
		objeto.setFechaFin(rs.getString("FECHA_FIN"));	
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setFirma(rs.getString("FIRMA"));
		
		return objeto;
	}
}
