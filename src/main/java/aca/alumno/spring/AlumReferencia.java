// Bean de datos academicos del alumno
package  aca.alumno.spring;

public class AlumReferencia{
	private String codigoPersonal;
	private String scotiabank;
	private String banamex;
	private String santander;
		
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getScotiabank() {
		return scotiabank;
	}

	public void setScotiabank(String scotiabank) {
		this.scotiabank = scotiabank;
	}

	public String getBanamex() {
		return banamex;
	}

	public void setBanamex(String banamex) {
		this.banamex = banamex;
	}
	
	public String getSantander() {
		return santander;
	}

	public void setSantander(String santander) {
		this.santander = santander;
	}

	public AlumReferencia(){
		codigoPersonal		= "0";
		scotiabank			= "-";
		banamex				= "-";
		santander			= "-";
	}
	
}