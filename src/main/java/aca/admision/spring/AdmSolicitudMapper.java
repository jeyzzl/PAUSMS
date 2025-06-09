package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmSolicitudMapper implements RowMapper<AdmSolicitud> {
	@Override
	public AdmSolicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmSolicitud objeto = new AdmSolicitud();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setFechaNac(rs.getString("FECHA_NAC"));
		objeto.setEstadoCivil(rs.getString("ESTADOCIVIL"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setBautizado(rs.getString("BAUTIZADO"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setAsesorId(rs.getString("ASESOR_ID"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setFechaIngreso(rs.getString("FECHA_INGRESO"));
		objeto.setAgente(rs.getString("AGENTE"));
		objeto.setAsesorSec(rs.getString("ASESOR_SEC"));
		objeto.setRedSocial(rs.getString("RED_SOCIAL"));
		objeto.setFeligresia(rs.getString("FELIGRESIA"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCarta(rs.getString("CARTA"));
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setUsuarioId(rs.getString("USUARIO_ID"));
		objeto.setFechaBautizo(rs.getString("FECHA_BAUTIZO"));
		objeto.setLugarBautizo(rs.getString("LUGAR_BAUTIZO"));
		objeto.setCulturalId(rs.getString("CULTURAL_ID"));
		objeto.setRegionId(rs.getString("REGION_ID"));
		objeto.setResPaisId(rs.getString("RES_PAIS_ID"));
		objeto.setResEstadoId(rs.getString("RES_ESTADO_ID"));
		objeto.setResCiudadId(rs.getString("RES_CIUDAD_ID"));
		objeto.setAcomodoId(rs.getString("ACOMODO_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setNivelEstudio(rs.getString("NIVEL_ESTUDIO"));
		objeto.setTipoAplicante(rs.getString("TIPO_APLICANTE"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setTipoAcomodo(rs.getString("TIPO_ACOMODO"));
		
		return objeto;
	}

}
