package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalSolicitudMapper implements RowMapper<SalSolicitud> {
	
	public SalSolicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		SalSolicitud objeto = new SalSolicitud();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setOtroProposito(rs.getString("OTRO_PROPOSITO"));
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setFechaSalida(rs.getString("FECHA_SALIDA"));
		objeto.setFechaLlegada(rs.getString("FECHA_LLEGADA"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setAlimento(rs.getString("ALIMENTO"));
		objeto.setDesayuno(rs.getString("DESAYUNO"));
		objeto.setComida(rs.getString("COMIDA"));
		objeto.setCena(rs.getString("CENA"));
		objeto.setHospedaje(rs.getString("HOSPEDAJE"));
		objeto.setTransporte(rs.getString("TRANSPORTE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		objeto.setAutorizo(rs.getString("AUTORIZO"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setTotal(rs.getString("TOTAL"));
		objeto.setTotalPersona(rs.getString("TOTAL_PERSONA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setLugarSalida(rs.getString("LUGAR_SALIDA"));
		objeto.setPreautorizo(rs.getString("PREAUTORIZO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setPropositoId(rs.getString("PROPOSITO_ID"));
		objeto.setPermiso(rs.getString("PERMISO"));
		
		return objeto;
	}

}
