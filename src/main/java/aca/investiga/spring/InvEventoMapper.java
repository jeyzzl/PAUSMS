package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvEventoMapper implements RowMapper<InvEvento> {
	
	public InvEvento mapRow(ResultSet rs, int rowNum) throws SQLException {
		InvEvento objeto = new InvEvento();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setFechaSolicitud(rs.getString("FECHA_SOLICITUD"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setTipoEvento(rs.getString("TIPO_EVENTO"));
		objeto.setDias(rs.getString("DIAS"));
		objeto.setNombreEvento(rs.getString("NOMBRE_EVENTO"));
		objeto.setParticipa(rs.getString("PARTICIPA"));
		objeto.setTipoBeca(rs.getString("TIPO_BECA"));
		objeto.setAlumnos(rs.getString("ALUMNOS"));
		objeto.setHospedaje(rs.getString("HOSPEDAJE"));
		objeto.setTransporte(rs.getString("TRANSPORTE"));
		objeto.setViaticos(rs.getString("VIATICOS"));
		objeto.setGastos(rs.getString("GASTOS"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
