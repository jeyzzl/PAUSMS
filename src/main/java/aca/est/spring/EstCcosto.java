// Bean del Catalogo Cargas
package  aca.est.spring;

public class EstCcosto{
	private String id;
	private String codigoPersonal;
	private String cursoCargaId;
	private String cCostoId;
	private String cursoId;
	private String alumnos;
	private String total;
	private String porcentaje;
	private String ubicacion;
	private String estado;
	private String horas;
	private String portotal;
	private String importe;
	
	public EstCcosto(){
		id 					= "0";
		codigoPersonal		= "";
		cursoCargaId		= "";
		cCostoId			= "";
		cursoId				= "";
		alumnos				= "";
		total				= "";
		porcentaje			= "";
		ubicacion			= "";
		estado 				= "0";
		horas 				= "0";
		portotal			= "0";
		importe				= "0";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getcCostoId() {
		return cCostoId;
	}

	public void setcCostoId(String cCostoId) {
		this.cCostoId = cCostoId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getPortotal() {
		return portotal;
	}

	public void setPortotal(String portotal) {
		this.portotal = portotal;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}	
	
}