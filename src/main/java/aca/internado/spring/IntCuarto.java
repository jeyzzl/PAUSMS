package aca.internado.spring;

public class IntCuarto {
	
	private String dormitorioId;
	private String cuartoId;
	private String pasillo;
	private String cupo;
	private String estado;
	
	public IntCuarto(){
		dormitorioId	= "";
		cuartoId		= "";
		pasillo			= "";
		cupo			= "";
		estado			= "";
	}

	public String getDormitorioId() {
		return dormitorioId;
	}

	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}

	public String getCuartoId() {
		return cuartoId;
	}

	public void setCuartoId(String cuartoId) {
		this.cuartoId = cuartoId;
	}

	public String getPasillo() {
		return pasillo;
	}

	public void setPasillo(String pasillo) {
		this.pasillo = pasillo;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}