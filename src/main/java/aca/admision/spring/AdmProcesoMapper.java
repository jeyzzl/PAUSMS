package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmProcesoMapper implements RowMapper<AdmProceso>{

@Override
public AdmProceso mapRow(ResultSet rs, int arg1) throws SQLException {
	
	AdmProceso objeto = new AdmProceso();
	
	objeto.setFolio(rs.getString("FOLIO"));				
	objeto.setFechaRegistro(rs.getString("F_REGISTRO"));
	objeto.setFechaSolicitud(rs.getString("F_SOLICITUD"));
	objeto.setFechaDocumentos(rs.getString("F_DOCUMENTOS"));
	objeto.setFechaCarta(rs.getString("F_CARTA"));
	objeto.setFechaAdmision(rs.getString("F_ADMISION"));
	objeto.setFechaExamen(rs.getString("F_EXAMEN"));
	objeto.setFechaDirecto(rs.getString("F_DIRECTO"));
	
	return objeto;
}
}
