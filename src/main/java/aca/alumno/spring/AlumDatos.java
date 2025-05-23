/**
 * 
 */
package aca.alumno.spring;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class AlumDatos {
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String sintomas;
	private String modulo;
	private String visita;
	private String conoces;
	private String nombre;
	private String telefono;
	private String correo;
	private String cambio;
	private String fiebre;
	private String febricula;
	private String tos;
	private String cefalea;
	private String rinorrea;
	private String coriza;
	private String artralgias;
	private String mialgias;
	private String abdominal;
	private String toracico;
	private String congestion;
	private String letargia;
	private String permanencia;
	private String viaje;
	private String exactamente;
	private String contacto;
	private String contactoNombre;
	private String contactoComunidad;
	private String contactoResidencia;
	private String necesidad;
	private String muscular;
	private String agotamiento;
	private String inicio;
	private String ataques;
	private String diarrea;
	private String nauseas;
	private String amigo;
	private String estres;
	private String infInfluenza;
	private String infClases;
	private String infE42;
	private String manos;
	private String escupir;
	private String alrededor;
	
	public AlumDatos(){
		codigoPersonal		= "";
		folio				= "";
		fecha				= "";
		sintomas			= "";
		modulo				= "";
		visita				= "";
		conoces				= "";
		nombre				= "";
		telefono			= "";
		correo				= "";
		cambio				= "";
		fiebre				= "";
		febricula			= "";
		tos					= "";
		cefalea				= "";
		rinorrea			= "";
		coriza				= "";
		artralgias			= "";
		mialgias			= "";
		abdominal			= "";
		toracico			= "";
		congestion			= "";
		letargia			= "";
		permanencia			= "";
		viaje				= "";
		exactamente			= "";
		contacto			= "";
		contactoNombre		= "";
		contactoComunidad	= "";
		contactoResidencia	= "";
		necesidad			= "";
		muscular			= "";
		agotamiento			= "";
		inicio				= "";
		ataques				= "";
		diarrea				= "";
		nauseas				= "";
		amigo				= "";
		estres				= "";
		infInfluenza		= "";
		infClases			= "";
		infE42				= "";
		manos				= "";
		escupir				= "";
		alrededor			= "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the sintomas
	 */
	public String getSintomas() {
		return sintomas;
	}

	/**
	 * @param sintomas the sintomas to set
	 */
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	/**
	 * @return the modulo
	 */
	public String getModulo() {
		return modulo;
	}

	/**
	 * @param modulo the modulo to set
	 */
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	/**
	 * @return the visita
	 */
	public String getVisita() {
		return visita;
	}

	/**
	 * @param visita the visita to set
	 */
	public void setVisita(String visita) {
		this.visita = visita;
	}

	/**
	 * @return the conoces
	 */
	public String getConoces() {
		return conoces;
	}

	/**
	 * @param conoces the conoces to set
	 */
	public void setConoces(String conoces) {
		this.conoces = conoces;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * @return the cambio
	 */
	public String getCambio() {
		return cambio;
	}

	/**
	 * @param cambio the cambio to set
	 */
	public void setCambio(String cambio) {
		this.cambio = cambio;
	}

	/**
	 * @return the fiebre
	 */
	public String getFiebre() {
		return fiebre;
	}

	/**
	 * @param fiebre the fiebre to set
	 */
	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}

	/**
	 * @return the febricula
	 */
	public String getFebricula() {
		return febricula;
	}

	/**
	 * @param febricula the febricula to set
	 */
	public void setFebricula(String febricula) {
		this.febricula = febricula;
	}

	/**
	 * @return the tos
	 */
	public String getTos() {
		return tos;
	}

	/**
	 * @param tos the tos to set
	 */
	public void setTos(String tos) {
		this.tos = tos;
	}

	/**
	 * @return the cefalea
	 */
	public String getCefalea() {
		return cefalea;
	}

	/**
	 * @param cefalea the cefalea to set
	 */
	public void setCefalea(String cefalea) {
		this.cefalea = cefalea;
	}

	/**
	 * @return the rinorrea
	 */
	public String getRinorrea() {
		return rinorrea;
	}

	/**
	 * @param rinorrea the rinorrea to set
	 */
	public void setRinorrea(String rinorrea) {
		this.rinorrea = rinorrea;
	}

	/**
	 * @return the coriza
	 */
	public String getCoriza() {
		return coriza;
	}

	/**
	 * @param coriza the coriza to set
	 */
	public void setCoriza(String coriza) {
		this.coriza = coriza;
	}

	/**
	 * @return the artralgias
	 */
	public String getArtralgias() {
		return artralgias;
	}

	/**
	 * @param artralgias the artralgias to set
	 */
	public void setArtralgias(String artralgias) {
		this.artralgias = artralgias;
	}

	/**
	 * @return the mialgias
	 */
	public String getMialgias() {
		return mialgias;
	}

	/**
	 * @param mialgias the mialgias to set
	 */
	public void setMialgias(String mialgias) {
		this.mialgias = mialgias;
	}

	/**
	 * @return the abdominal
	 */
	public String getAbdominal() {
		return abdominal;
	}

	/**
	 * @param abdominal the abdominal to set
	 */
	public void setAbdominal(String abdominal) {
		this.abdominal = abdominal;
	}

	/**
	 * @return the toracico
	 */
	public String getToracico() {
		return toracico;
	}

	/**
	 * @param toracico the toracico to set
	 */
	public void setToracico(String toracico) {
		this.toracico = toracico;
	}

	/**
	 * @return the congestion
	 */
	public String getCongestion() {
		return congestion;
	}

	/**
	 * @param congestion the congestion to set
	 */
	public void setCongestion(String congestion) {
		this.congestion = congestion;
	}

	/**
	 * @return the letargia
	 */
	public String getLetargia() {
		return letargia;
	}

	/**
	 * @param letargia the letargia to set
	 */
	public void setLetargia(String letargia) {
		this.letargia = letargia;
	}

	/**
	 * @return the permanencia
	 */
	public String getPermanencia() {
		return permanencia;
	}

	/**
	 * @param permanencia the permanencia to set
	 */
	public void setPermanencia(String permanencia) {
		this.permanencia = permanencia;
	}

	/**
	 * @return the viaje
	 */
	public String getViaje() {
		return viaje;
	}

	/**
	 * @param viaje the viaje to set
	 */
	public void setViaje(String viaje) {
		this.viaje = viaje;
	}

	/**
	 * @return the exactamente
	 */
	public String getExactamente() {
		return exactamente;
	}

	/**
	 * @param exactamente the exactamente to set
	 */
	public void setExactamente(String exactamente) {
		this.exactamente = exactamente;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the contactoNombre
	 */
	public String getContactoNombre() {
		return contactoNombre;
	}

	/**
	 * @param contactoNombre the contactoNombre to set
	 */
	public void setContactoNombre(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}

	/**
	 * @return the contactoComunidad
	 */
	public String getContactoComunidad() {
		return contactoComunidad;
	}

	/**
	 * @param contactoComunidad the contactoComunidad to set
	 */
	public void setContactoComunidad(String contactoComunidad) {
		this.contactoComunidad = contactoComunidad;
	}

	/**
	 * @return the contactoResidencia
	 */
	public String getContactoResidencia() {
		return contactoResidencia;
	}

	/**
	 * @param contactoResidencia the contactoResidencia to set
	 */
	public void setContactoResidencia(String contactoResidencia) {
		this.contactoResidencia = contactoResidencia;
	}

	/**
	 * @return the necesidad
	 */
	public String getNecesidad() {
		return necesidad;
	}

	/**
	 * @param necesidad the necesidad to set
	 */
	public void setNecesidad(String necesidad) {
		this.necesidad = necesidad;
	}

	/**
	 * @return the muscular
	 */
	public String getMuscular() {
		return muscular;
	}

	/**
	 * @param muscular the muscular to set
	 */
	public void setMuscular(String muscular) {
		this.muscular = muscular;
	}

	/**
	 * @return the agotamiento
	 */
	public String getAgotamiento() {
		return agotamiento;
	}

	/**
	 * @param agotamiento the agotamiento to set
	 */
	public void setAgotamiento(String agotamiento) {
		this.agotamiento = agotamiento;
	}

	/**
	 * @return the inicio
	 */
	public String getInicio() {
		return inicio;
	}

	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return the ataques
	 */
	public String getAtaques() {
		return ataques;
	}

	/**
	 * @param ataques the ataques to set
	 */
	public void setAtaques(String ataques) {
		this.ataques = ataques;
	}

	/**
	 * @return the diarrea
	 */
	public String getDiarrea() {
		return diarrea;
	}

	/**
	 * @param diarrea the diarrea to set
	 */
	public void setDiarrea(String diarrea) {
		this.diarrea = diarrea;
	}

	/**
	 * @return the nauseas
	 */
	public String getNauseas() {
		return nauseas;
	}

	/**
	 * @param nauseas the nauseas to set
	 */
	public void setNauseas(String nauseas) {
		this.nauseas = nauseas;
	}

	/**
	 * @return the amigo
	 */
	public String getAmigo() {
		return amigo;
	}

	/**
	 * @param amigo the amigo to set
	 */
	public void setAmigo(String amigo) {
		this.amigo = amigo;
	}

	/**
	 * @return the estres
	 */
	public String getEstres() {
		return estres;
	}

	/**
	 * @param estres the estres to set
	 */
	public void setEstres(String estres) {
		this.estres = estres;
	}

	/**
	 * @return the infInfluenza
	 */
	public String getInfInfluenza() {
		return infInfluenza;
	}

	/**
	 * @param infInfluenza the infInfluenza to set
	 */
	public void setInfInfluenza(String infInfluenza) {
		this.infInfluenza = infInfluenza;
	}

	/**
	 * @return the infClases
	 */
	public String getInfClases() {
		return infClases;
	}

	/**
	 * @param infClases the infClases to set
	 */
	public void setInfClases(String infClases) {
		this.infClases = infClases;
	}

	/**
	 * @return the infE42
	 */
	public String getInfE42() {
		return infE42;
	}

	/**
	 * @param infE42 the infE42 to set
	 */
	public void setInfE42(String infE42) {
		this.infE42 = infE42;
	}

	/**
	 * @return the manos
	 */
	public String getManos() {
		return manos;
	}

	/**
	 * @param manos the manos to set
	 */
	public void setManos(String manos) {
		this.manos = manos;
	}

	/**
	 * @return the escupir
	 */
	public String getEscupir() {
		return escupir;
	}

	/**
	 * @param escupir the escupir to set
	 */
	public void setEscupir(String escupir) {
		this.escupir = escupir;
	}

	/**
	 * @return the alrededor
	 */
	public String getAlrededor() {
		return alrededor;
	}

	/**
	 * @param alrededor the alrededor to set
	 */
	public void setAlrededor(String alrededor) {
		this.alrededor = alrededor;
	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal		= rs.getString("CODIGO_PERSONAL")==null?"":rs.getString("CODIGO_PERSONAL");
		folio				= rs.getString("FOLIO")==null?"":rs.getString("FOLIO");
		fecha				= rs.getString("FECHA")==null?"":rs.getString("FECHA");
		sintomas			= rs.getString("SINTOMAS")==null?"":rs.getString("SINTOMAS");
		modulo				= rs.getString("MODULO")==null?"":rs.getString("MODULO");
		visita				= rs.getString("VISITA")==null?"":rs.getString("VISITA");
		conoces				= rs.getString("CONOCES")==null?"":rs.getString("CONOCES");
		nombre				= rs.getString("NOMBRE")==null?"":rs.getString("NOMBRE");
		telefono			= rs.getString("TELEFONO")==null?"":rs.getString("TELEFONO");
		correo				= rs.getString("CORREO")==null?"":rs.getString("CORREO");
		cambio				= rs.getString("CAMBIO")==null?"":rs.getString("CAMBIO");
		fiebre				= rs.getString("FIEBRE")==null?"":rs.getString("FIEBRE");
		febricula			= rs.getString("FEBRICULA")==null?"":rs.getString("FEBRICULA");
		tos					= rs.getString("TOS")==null?"":rs.getString("TOS");
		cefalea				= rs.getString("CEFALEA")==null?"":rs.getString("CEFALEA");
		rinorrea			= rs.getString("RINORREA")==null?"":rs.getString("RINORREA");
		coriza				= rs.getString("CORIZA")==null?"":rs.getString("CORIZA");
		artralgias			= rs.getString("ARTRALGIAS")==null?"":rs.getString("ARTRALGIAS");
		mialgias			= rs.getString("MIALGIAS")==null?"":rs.getString("MIALGIAS");
		abdominal			= rs.getString("ABDOMINAL")==null?"":rs.getString("ABDOMINAL");
		toracico			= rs.getString("TORACICO")==null?"":rs.getString("TORACICO");
		congestion			= rs.getString("CONGESTION")==null?"":rs.getString("CONGESTION");
		letargia			= rs.getString("LETARGIA")==null?"":rs.getString("LETARGIA");
		permanencia			= rs.getString("PERMANENCIA")==null?"":rs.getString("PERMANENCIA");
		viaje				= rs.getString("VIAJE")==null?"":rs.getString("VIAJE");
		exactamente			= rs.getString("EXACTAMENTE")==null?"":rs.getString("EXACTAMENTE");
		contacto			= rs.getString("CONTACTO")==null?"":rs.getString("CONTACTO");
		contactoNombre		= rs.getString("CONTACTO_NOMBRE")==null?"":rs.getString("CONTACTO_NOMBRE");
		contactoComunidad	= rs.getString("CONTACTO_COMUNIDAD")==null?"":rs.getString("CONTACTO_COMUNIDAD");
		contactoResidencia	= rs.getString("CONTACTO_RESIDENCIA")==null?"":rs.getString("CONTACTO_RESIDENCIA");
		necesidad			= rs.getString("NECESIDAD")==null?"":rs.getString("NECESIDAD");
		muscular			= rs.getString("MUSCULAR")==null?"":rs.getString("MUSCULAR");
		agotamiento			= rs.getString("AGOTAMIENTO")==null?"":rs.getString("AGOTAMIENTO");
		inicio				= rs.getString("INICIO")==null?"":rs.getString("INICIO");
		ataques				= rs.getString("ATAQUES")==null?"":rs.getString("ATAQUES");
		diarrea				= rs.getString("DIARREA")==null?"":rs.getString("DIARREA");
		nauseas				= rs.getString("NAUSEA")==null?"":rs.getString("NAUSEA");
		amigo				= rs.getString("AMIGO")==null?"":rs.getString("AMIGO");
		estres				= rs.getString("ESTRES")==null?"":rs.getString("ESTRES");
		infInfluenza		= rs.getString("INF_INFLUENZA")==null?"":rs.getString("INF_INFLUENZA");
		infClases			= rs.getString("INF_CLASES")==null?"":rs.getString("INF_CLASES");
		infE42				= rs.getString("INF_E42")==null?"":rs.getString("INF_E42");
		manos				= rs.getString("MANOS")==null?"":rs.getString("MANOS");
		escupir				= rs.getString("ESCUPIR")==null?"":rs.getString("ESCUPIR");
		alrededor			= rs.getString("ALREDEDOR")==null?"":rs.getString("ALREDEDOR");
 	}
 
}