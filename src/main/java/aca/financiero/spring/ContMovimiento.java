package aca.financiero.spring;

public class ContMovimiento {
	private String idEjercicio;
	private String idLibro;	
	private String idCcosto;
	private String folio;
	private String numMovto;
	private String fecha;
	private String descripcion;
	private String importe;
	private String naturaleza;
	private String referencia;
	private String referencia2;
	private String idCtaMayorM;
	private String idCcostoM;
	private String idAuxiliarM;
	private String status;
	private String tipoCuenta;
	private String conceptoId;
	
	// Constructor
	public ContMovimiento(){
		
		idEjercicio 	= "";
		idLibro 		= "";	
		idCcosto		= "";
		folio			= "0";
		numMovto		= "0";
		fecha			= "";
		descripcion		= "";
		importe			= "0";
		naturaleza		= "";
		referencia		= "";
		referencia2		= "";
		idCtaMayorM		= "";
		idCcostoM		= "";
		idAuxiliarM		= "";
		status			= "";
		tipoCuenta		= "";
		conceptoId		= "";		
		
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}

	public String getIdCcosto() {
		return idCcosto;
	}

	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNumMovto() {
		return numMovto;
	}

	public void setNumMovto(String numMovto) {
		this.numMovto = numMovto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getReferencia2() {
		return referencia2;
	}

	public void setReferencia2(String referencia2) {
		this.referencia2 = referencia2;
	}

	public String getIdCtaMayorM() {
		return idCtaMayorM;
	}

	public void setIdCtaMayorM(String idCtaMayorM) {
		this.idCtaMayorM = idCtaMayorM;
	}

	public String getIdCcostoM() {
		return idCcostoM;
	}

	public void setIdCcostoM(String idCcostoM) {
		this.idCcostoM = idCcostoM;
	}

	public String getIdAuxiliarM() {
		return idAuxiliarM;
	}

	public void setIdAuxiliarM(String idAuxiliarM) {
		this.idAuxiliarM = idAuxiliarM;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(String conceptoId) {
		this.conceptoId = conceptoId;
	}	
	
}