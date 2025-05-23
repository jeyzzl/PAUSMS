package  aca.kardex.spring;

public class KrdxCursoAct{
	private String codigoPersonal; 
	private String cursoCargaId;
	private String cursoId;
	private String cursoId2;
	private String tipoCalId;
	private String nota;
	private String fNota;
	private String notaExtra;
	private String fExtra;
	private String tipo;
	private String titulo;
	private String fTitulo;
	private String comentario;
	private String correccion;
	private String cantidad;
	private String frecuencia;
	private String fecha;
	private String confirmar;
	
	public KrdxCursoAct(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		cursoId2		= "";
		tipoCalId		= "";
		nota			= "";
		fNota 			= "";
		notaExtra		= "";
		fExtra			= "";
		tipo			= "O";
		titulo			= "N";
		fTitulo			= "";
		comentario		= "";
		correccion 		= "N";
		cantidad 		= "0";
		frecuencia 		= "0";
		fecha	 		= "";
		confirmar 		= "-";
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

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCursoId2() {
		return cursoId2;
	}

	public void setCursoId2(String cursoId2) {
		this.cursoId2 = cursoId2;
	}

	public String getTipoCalId() {
		return tipoCalId;
	}

	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getfNota() {
		return fNota;
	}

	public void setfNota(String fNota) {
		this.fNota = fNota;
	}

	public String getNotaExtra() {
		return notaExtra;
	}

	public void setNotaExtra(String notaExtra) {
		this.notaExtra = notaExtra;
	}

	public String getfExtra() {
		return fExtra;
	}

	public void setfExtra(String fExtra) {
		this.fExtra = fExtra;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getfTitulo() {
		return fTitulo;
	}

	public void setfTitulo(String fTitulo) {
		this.fTitulo = fTitulo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getCorreccion() {
		return correccion;
	}

	public void setCorreccion(String correccion) {
		this.correccion = correccion;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}
	
}