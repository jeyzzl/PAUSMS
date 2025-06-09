// Bean folio la tabla folio Adm_Contacto
package  aca.admision.spring;

public class AdmSolicitud{	
	private String folio;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String nacionalidad;
	private String fechaNac;
	private String estadoCivil;
	private String genero;
	private String religionId;
	private String bautizado;
	private String usuario;
	private String clave;
	private String fecha;
	private String matricula;
	private String email;
	private String estado;
	private String asesorId;
	private String curp;
	private String fechaIngreso;
	private String agente;
	private String asesorSec;
	private String redSocial;
	private String feligresia;
	private String telefono;
	private String carta;
	private String codigo;
	private String usuarioId;
	private String fechaBautizo;
	private String lugarBautizo;
	private String culturalId;
	private String regionId;
	private String resPaisId;
	private String resEstadoId;
	private String resCiudadId;
	private String acomodoId;
	private String tipo;
	private String nivelEstudio;
	private String tipoAplicante;
	private String periodoId;
	private String tipoAcomodo;
	
	public AdmSolicitud(){
		folio 			= "0";
		nombre 			= "-";
		apellidoPaterno	= "-"; 
		apellidoMaterno	= "-";
		paisId			= "0";
		estadoId 		= "0";
		ciudadId		= "0";
		nacionalidad	= "";
		fechaNac		= "";
		estadoCivil		= "S";
		genero			= "M";
		religionId		= "";
		bautizado 		= "S";
		usuario			= "";
		clave			= "";
		fecha 			= "";
		matricula 		= "-";
		email			= "";
		estado 			= "1";
		asesorId		= "";
		curp			= "";
		fechaIngreso	= "";
		agente			= "";
		asesorSec		= "-";
		redSocial		= "";
		feligresia		= "";
		telefono		= "-";
		carta			= "-";
		codigo			= "0";
		usuarioId		= "0";
		fechaBautizo	= "-";
		lugarBautizo	= "";
		culturalId		= "1";
		regionId		= "1";
		resPaisId 		= "1";
		resEstadoId		= "1";
		resCiudadId		= "1";
		acomodoId		= "0";
		tipo 			= "0";
		nivelEstudio 	= "U";
		tipoAplicante 	= "N";
		periodoId		= "0";
		tipoAcomodo		= "0";

	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getBautizado() {
		return bautizado;
	}

	public void setBautizado(String bautizado) {
		this.bautizado = bautizado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAsesorId() {
		return asesorId;
	}
	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}

	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getAsesorSec() {
		return asesorSec;
	}
	public void setAsesorSec(String asesorSec) {
		this.asesorSec = asesorSec;
	}
	
	public String getRedSocial() {
		return redSocial;
	}

	public void setRedSocial(String redSocial) {
		this.redSocial = redSocial;
	}

	public String getFeligresia() {
		return feligresia;
	}

	public void setFeligresia(String feligresia) {
		this.feligresia = feligresia;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getFechaBautizo() {
		return fechaBautizo;
	}
	public void setFechaBautizo(String fechaBautizo) {
		this.fechaBautizo = fechaBautizo;
	}

	public String getLugarBautizo() {
		return lugarBautizo;
	}
	public void setLugarBautizo(String lugarBautizo) {
		this.lugarBautizo = lugarBautizo;
	}

	public String getCulturalId() {
		return culturalId;
	}
	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getResPaisId() {
		return resPaisId;
	}
	public void setResPaisId(String resPaisId) {
		this.resPaisId = resPaisId;
	}

	public String getResEstadoId() {
		return resEstadoId;
	}
	public void setResEstadoId(String resEstadoId) {
		this.resEstadoId = resEstadoId;
	}

	public String getResCiudadId() {
		return resCiudadId;
	}
	public void setResCiudadId(String resCiudadId) {
		this.resCiudadId = resCiudadId;
	}

	public String getAcomodoId() {
		return acomodoId;
	}
	public void setAcomodoId(String acomodoId) {
		this.acomodoId = acomodoId;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNivelEstudio() {
		return nivelEstudio;
	}
	public void setNivelEstudio(String nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public String getTipoAplicante() {
		return tipoAplicante;
	}
	public void setTipoAplicante(String tipoAplicante) {
		this.tipoAplicante = tipoAplicante;
	}

	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getTipoAcomodo() {
		return tipoAcomodo;
	}
	public void setTipoAcomodo(String tipoAcomodo) {
		this.tipoAcomodo = tipoAcomodo;
	}
}