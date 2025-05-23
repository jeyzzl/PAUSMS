package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumDatosMapper implements RowMapper<AlumDatos>{
	@Override
	public AlumDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumDatos objeto = new AlumDatos();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setSintomas(rs.getString("SINTOMAS"));
		objeto.setModulo(rs.getString("MODULO"));
		objeto.setVisita(rs.getString("VISITA"));
		objeto.setConoces(rs.getString("CONOCES"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setCambio(rs.getString("CAMBIO"));
		objeto.setFiebre(rs.getString("FIEBRE"));
		objeto.setFebricula(rs.getString("FEBRICULA"));
		objeto.setTos(rs.getString("TOS"));
		objeto.setCefalea(rs.getString("CEFALEA"));
		objeto.setRinorrea(rs.getString("RINORREA"));
		objeto.setCoriza(rs.getString("CORIZA"));
		objeto.setArtralgias(rs.getString("ARTRALGIAS"));
		objeto.setMialgias(rs.getString("MILAGIAS"));
		objeto.setAbdominal(rs.getString("ABDOMINAL"));
		objeto.setToracico(rs.getString("TORACICO"));
		objeto.setCongestion(rs.getString("CONGESTION"));
		objeto.setLetargia(rs.getString("LETARGIA"));
		objeto.setPermanencia(rs.getString("PERMANENCIA"));
		objeto.setViaje(rs.getString("VIAJE"));
		objeto.setExactamente(rs.getString("EXACTAMENTE"));
		objeto.setContacto(rs.getString("CONTACTO"));
		objeto.setContactoNombre(rs.getString("CONTACTO_NOMBRE"));
		objeto.setContactoComunidad(rs.getString("CONTACTO_COMUNIDAD"));
		objeto.setContactoResidencia(rs.getString("CONTACTO_RESIDENCIA"));
		objeto.setNecesidad(rs.getString("NECESIDAD"));
		objeto.setMuscular(rs.getString("MUSCULAR"));
		objeto.setAgotamiento(rs.getString("AGOTAMIENTO"));
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setAtaques(rs.getString("ATAQUES"));
		objeto.setDiarrea(rs.getString("DIARREA"));
		objeto.setNauseas(rs.getString("NAUSEAS"));
		objeto.setAmigo(rs.getString("AMIGO"));
		objeto.setEstres(rs.getString("ESTRES"));
		objeto.setInfInfluenza(rs.getString("INF_INFLUENZA"));
		objeto.setInfClases(rs.getString("INF_CALSES"));
		objeto.setInfE42(rs.getString("INF_E42"));
		objeto.setManos(rs.getString("MANOS"));
		objeto.setEscupir(rs.getString("ESCUPIR"));
		objeto.setAlrededor(rs.getString("ALREDEDOR"));
		
		return objeto;
	}
}
