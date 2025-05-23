
package aca.salida.spring;

public class SalSolicitud {
	private String salidaId;
	private String proposito;
	private String otroProposito;
	private String grupoId;
	private String fechaSalida;
	private String fechaLlegada;
	private String lugar;
	private String alimento;
	private String desayuno;
	private String comida;
	private String cena;
	private String hospedaje;
	private String transporte;
	private String usuario;
	private String fecha;
	private String responsable;
	private String autorizo;
	private String folio;
	private String total;
	private String totalPersona;
	private String comentario;
	private String estado;
	private String lugarSalida;
	private String preautorizo;
	private String telefono;
	private String paisId;
	private String estadoId;
	private String propositoId;
	private String permiso;

	public SalSolicitud(){
		salidaId		= "";
		fecha			= "";
		proposito		= "";
		otroProposito	= "";
		grupoId			= "";
		fechaSalida		= aca.util.Fecha.getHoy()+ " 00:00:00";
		fechaLlegada	= aca.util.Fecha.getHoy()+ " 00:00:00";
		lugar			= "";
		alimento		= "0";
		desayuno		= "0";
		comida			= "0";
		cena			= "0";
		hospedaje		= "0";
		transporte		= "0";
		usuario			= "";
		responsable		= "0";
		autorizo		= "";
		folio			= "";
		total			= "0";
		totalPersona	= "0";
		comentario		= "";
		estado			= "S";
		lugarSalida		= "";
		preautorizo		= "0";
		telefono		= "-";
		paisId			= "91";
		estadoId		= "19";
		propositoId		= "0";
		permiso			= "N";
	}
	
	public String getPropositoId() {
		return propositoId;
	}

	public void setPropositoId(String propositoId) {
		this.propositoId = propositoId;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getAutorizo() {
		return autorizo;
	}

	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public String getOtroProposito() {
		return otroProposito;
	}

	public void setOtroProposito(String otroProposito) {
		this.otroProposito = otroProposito;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	public String getDesayuno() {
		return desayuno;
	}

	public void setDesayuno(String desayuno) {
		this.desayuno = desayuno;
	}

	public String getComida() {
		return comida;
	}

	public void setComida(String comida) {
		this.comida = comida;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getHospedaje() {
		return hospedaje;
	}

	public void setHospedaje(String hospedaje) {
		this.hospedaje = hospedaje;
	}

	public String getTransporte() {
		return transporte;
	}

	public void setTransporte(String trasporte) {
		this.transporte = trasporte;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getTotalPersona() {
		return totalPersona;
	}

	public void setTotalPersona(String totalPersona) {
		this.totalPersona = totalPersona;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getAlimento() {
		return alimento;
	}

	public void setAlimento(String alimento) {
		this.alimento = alimento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLugarSalida() {
		return lugarSalida;
	}

	public void setLugarSalida(String lugarSalida) {
		this.lugarSalida = lugarSalida;
	}
	
	public String getPreautorizo() {
		return preautorizo;
	}

	public void setPreautorizo(String preautorizo) {
		this.preautorizo = preautorizo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
}