 // Bean de alumno 
 package  aca.alumno;

 import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 import java.sql.SQLException;

 public class AlumInfluenza{
 	private String codigoPersonal;
 	private String facultad;
 	private String carrera;
 	private String residencia;
 	private String genero;
 	private String edad;
 	private String personas;
 	private String permanencia;
 	private String viaje;
 	private String donde;
 	private String fecha;
 	private String visitaDoctor;
 	private String conoces;
 	private String contacto;
 	private String telefono; 	
 	private String sintomas;
 	private String fechaAlta;
 	private String exactamente;
 	private String contactoComunidad;
 	private String contactoResidencia;
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
 	private String muscular;
 	private String agotamiento;
 	private String inicio;
 	private String ataques;
 	private String diarrea;
 	private String nausea;
 	private String dormitorio;
 	private String cuarto;
 
 	public AlumInfluenza(){
 		codigoPersonal		= "";
 		facultad 			= "";
 	 	carrera				= "";
 	 	residencia			= "";
 	 	genero				= "";
 	 	edad				= "";
 	 	personas			= "";
 	 	permanencia			= "";
 	 	viaje				= "";
 	 	donde				= "";
 	 	fecha				= "";
 	 	visitaDoctor		= "";
 	 	conoces				= "";
 	 	contacto			= "";
 	 	telefono			= "";
 	 	sintomas			= "0";
 	 	fechaAlta			= "";
 	 	exactamente			= "";
 	 	contactoComunidad	= "";
 	 	contactoResidencia	= "";
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
 	 	muscular			= "";
 	 	agotamiento			= "";
 	 	inicio				= "";
 	 	ataques				= "";
 	 	diarrea				= "";
 	 	nausea				= "";
 	 	dormitorio			= "";
 	 	cuarto				= "";
 	}
 	
 	public String getCodigoPersonal(){
 		return codigoPersonal;
 	}
 	
 	public void setCodigoPersonal( String Codigo_personal){
 		this.codigoPersonal = Codigo_personal;
 	}
 	
 	
	/**
	 * @return the facultad
	 */
	public String getFacultad() {
		return facultad;
	}

	/**
	 * @param facultad the facultad to set
	 */
	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	/**
	 * @return the residencia
	 */
	public String getResidencia() {
		return residencia;
	}

	/**
	 * @param residencia the residencia to set
	 */
	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the edad
	 */
	public String getEdad() {
		return edad;
	}

	/**
	 * @param edad the edad to set
	 */
	public void setEdad(String edad) {
		this.edad = edad;
	}

	/**
	 * @return the personas
	 */
	public String getPersonas() {
		return personas;
	}

	/**
	 * @param personas the personas to set
	 */
	public void setPersonas(String personas) {
		this.personas = personas;
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
	 * @return the donde
	 */
	public String getDonde() {
		return donde;
	}

	/**
	 * @param donde the donde to set
	 */
	public void setDonde(String donde) {
		this.donde = donde;
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
	 * @return the visitaDoctor
	 */
	public String getVisitaDoctor() {
		return visitaDoctor;
	}

	/**
	 * @param visitaDoctor the visitaDoctor to set
	 */
	public void setVisitaDoctor(String visitaDoctor) {
		this.visitaDoctor = visitaDoctor;
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
	 * @return the fechaAlta
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	 * @return the nausea
	 */
	public String getNausea() {
		return nausea;
	}

	/**
	 * @param nausea the nausea to set
	 */
	public void setNausea(String nausea) {
		this.nausea = nausea;
	}

	/**
	 * @return the dormitorio
	 */
	public String getDormitorio() {
		return dormitorio;
	}

	/**
	 * @param dormitorio the dormitorio to set
	 */
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}

	/**
	 * @return the cuarto
	 */
	public String getCuarto() {
		return cuarto;
	}

	/**
	 * @param cuarto the cuarto to set
	 */
	public void setCuarto(String cuarto) {
		this.cuarto = cuarto;
	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		facultad 			= rs.getString("FACULTAD");
 		carrera			 	= rs.getString("CARRERA");
 		residencia			= rs.getString("RESIDENCIA");
 		genero 				= rs.getString("GENERO");
 		edad				= rs.getString("EDAD");
 		personas 			= rs.getString("PERSONAS");
 		permanencia 		= rs.getString("PERMANENCIA");
 		viaje 				= rs.getString("VIAJE");
 		donde				= rs.getString("DONDE");
 		fecha 				= rs.getString("FECHA");
 		visitaDoctor		= rs.getString("VISITA_DOCTOR");
 		conoces				= rs.getString("CONOCES");
 		contacto			= rs.getString("CORREO");
 		telefono			= rs.getString("TELEFONO"); 		
 		sintomas			= rs.getString("SINTOMAS");
 		fechaAlta			= rs.getString("FECHA_ALTA");
 	 	exactamente			= rs.getString("EXACTAMENTE");
 	 	contactoComunidad	= rs.getString("CONTACTO_COMUNIDAD");
 	 	contactoResidencia	= rs.getString("CONTACTO_RESIDENCIA");
 	 	fiebre				= rs.getString("FIEBRE");
 	 	febricula			= rs.getString("FEBRICULA");
 	 	tos					= rs.getString("TOS");
 	 	cefalea				= rs.getString("CEFALEA");
 	 	rinorrea			= rs.getString("RINORREA");
 	 	coriza				= rs.getString("CORIZA");
 	 	artralgias			= rs.getString("ARTRALGIAS");
 	 	mialgias			= rs.getString("MIALGIAS");
 	 	abdominal			= rs.getString("ABDOMINAL");
 	 	toracico			= rs.getString("TORACICO");
 	 	congestion			= rs.getString("CONGESTION");
 	 	letargia			= rs.getString("LETARGIA");
 	 	muscular			= rs.getString("MUSCULAR");
 	 	agotamiento			= rs.getString("AGOTAMIENTO");
 	 	inicio				= rs.getString("INICIO");
 	 	ataques				= rs.getString("ATAQUES");
 	 	diarrea				= rs.getString("DIARREA");
 	 	nausea				= rs.getString("NAUSEAS");
 	 	dormitorio			= rs.getString("DORMITORIO");
 	 	cuarto				= rs.getString("CUARTO");
 	}
 	
 	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, FACULTAD, CARRERA, RESIDENCIA, GENERO, EDAD, " +
	 			" PERSONAS, PERMANENCIA, VIAJE, DONDE, FECHA, VISITA_DOCTOR, " +
	 			" CONOCES, CORREO, TELEFONO, SINTOMAS," +
	 			" FECHA_ALTA, EXACTAMENTE, CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA," +
				" FIEBRE, FEBRICULA, TOS, CEFALEA," +
				" RINORREA, CORIZA, ARTRALGIAS, MIALGIAS," +
				" ABDOMINAL, TORACICO, CONGESTION, LETARGIA," +
				" MUSCULAR, AGOTAMIENTO, INICIO, ATAQUES," +
	 			" DIARREA, NAUSEA, DORMITORIO, CUARTO"+
	 			" FROM ENOC.ALUM_INFLUENZA WHERE CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 	}
 
 }