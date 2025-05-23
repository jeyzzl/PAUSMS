/**
 * 
 */
package aca.cert.spring;

/**
 * @author Jazer
 *
 */
public class CertAlum {
	private String codigoPersonal;
	private String planId;
	private String avance;
	private String numCursos;
	private String fecha;
	private String equivalencia;	
	private String estado;
	private String encabezado;
	private String linea;
	
	public CertAlum(){
		codigoPersonal	= "";
		planId			= "";
		avance			= "";
		numCursos		= "";
		fecha			= "";
		equivalencia	= "";		
		estado			= "";
		encabezado 		= "";
		linea	 		= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public String getNumCursos() {
		return numCursos;
	}

	public void setNumCursos(String numCursos) {
		this.numCursos = numCursos;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEquivalencia() {
		return equivalencia;
	}

	public void setEquivalencia(String equivalencia) {
		this.equivalencia = equivalencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
}