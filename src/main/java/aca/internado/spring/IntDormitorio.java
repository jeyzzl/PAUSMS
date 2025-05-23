package aca.internado.spring;

public class IntDormitorio {
	
	private String dormitorioId;
	private String nombre;
	private String preceptor;
	private String sexo;
	
	public IntDormitorio(){
		dormitorioId	= "0";
		nombre			= "-";
		preceptor		= "0";
		sexo			= "M";
	}

	public String getDormitorioId() {
		return dormitorioId;
	}

	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPreceptor() {
		return preceptor;
	}

	public void setPreceptor(String preceptor) {
		this.preceptor = preceptor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
}