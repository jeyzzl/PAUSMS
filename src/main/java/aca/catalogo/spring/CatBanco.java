// Bean del Catalogo de Ciudades
package  aca.catalogo.spring;

public class CatBanco{
	private String bancoId;
	private String nombre;
	private String nombreCorto;
	private String paisId;
	private String swift;
	
	public CatBanco(){

		setBancoId("0");
		setNombre("");
		setNombreCorto("");
		setPaisId("0");
		setSwift("0");

	}

	public String getBancoId() {
		return bancoId;
	}

	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	
	
}