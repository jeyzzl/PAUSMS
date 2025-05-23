// Bean del Catalogo de Roles
package  aca.saum.spring;

public class SaumReceta{
	private String id;
	private String version;
	private String calorias;
	private String carbohidratos;
	private String colesterol;
	private String color;
	private String fibra;
	private String forma;
	private String grasa;
	private String nombre;
	private String porcion;
	private String proteinas;
	private String rendimiento;
	private String sabor;
	private String sodio;
	private String temperatura;
	private String textura;
	private String tiempo;
	private String tipoPlato;
	private byte[] imagen;
	
	public SaumReceta(){
		id 				= "0";
		version			= "0";
		calorias 		= "0";
		carbohidratos	= "0";
		colesterol		= "0";
		color			= "0";
		fibra 			= "0";
		forma			= "0";
		grasa 			= "0";
		nombre			= "-";
		porcion 		= "0";
		proteinas		= "0";
		rendimiento 	= "0";
		sabor			= "0";
		sodio 			= "0";
		temperatura		= "0";
		textura			= "0";
		tiempo 			= "0";
		tipoPlato		= "-";
		imagen 			= null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCalorias() {
		return calorias;
	}

	public void setCalorias(String calorias) {
		this.calorias = calorias;
	}

	public String getCarbohidratos() {
		return carbohidratos;
	}

	public void setCarbohidratos(String carbohidratos) {
		this.carbohidratos = carbohidratos;
	}

	public String getColesterol() {
		return colesterol;
	}

	public void setColesterol(String colesterol) {
		this.colesterol = colesterol;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFibra() {
		return fibra;
	}

	public void setFibra(String fibra) {
		this.fibra = fibra;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public String getGrasa() {
		return grasa;
	}

	public void setGrasa(String grasa) {
		this.grasa = grasa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPorcion() {
		return porcion;
	}

	public void setPorcion(String porcion) {
		this.porcion = porcion;
	}

	public String getProteinas() {
		return proteinas;
	}

	public void setProteinas(String proteinas) {
		this.proteinas = proteinas;
	}

	public String getRendimiento() {
		return rendimiento;
	}

	public void setRendimiento(String rendimiento) {
		this.rendimiento = rendimiento;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public String getSodio() {
		return sodio;
	}

	public void setSodio(String sodio) {
		this.sodio = sodio;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getTextura() {
		return textura;
	}

	public void setTextura(String textura) {
		this.textura = textura;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getTipoPlato() {
		return tipoPlato;
	}

	public void setTipoPlato(String tipoPlato) {
		this.tipoPlato = tipoPlato;
	}
	
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
}