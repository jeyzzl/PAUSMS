package aca.financiero.spring;

public class FesCcPagareDet {
	private String matricula;
	private String cargaId;	
	private String bloque;
	private String folio;
	private String fVencimiento;
	private String importe;
	private String status;
	private String id;
	private String cCobroId;
	
	public FesCcPagareDet() {
		matricula 		= "";
		cargaId 		= "";
		bloque 			= "";
		folio 			= "";
		fVencimiento 	= "";
		importe 		= "";
		status 			= "";
		id 				= "";
		cCobroId 		= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getfVencimiento() {
		return fVencimiento;
	}

	public void setfVencimiento(String fVencimiento) {
		this.fVencimiento = fVencimiento;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getcCobroId() {
		return cCobroId;
	}

	public void setcCobroId(String cCobroId) {
		this.cCobroId = cCobroId;
	}
}
