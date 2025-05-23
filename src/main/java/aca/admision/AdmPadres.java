package aca.admision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmPadres {
	private String folio;
	private String padreNombre;
	private String padreApellido;
	private String padreReligion;
	private String padreNacionalidad;
	private String padreOcupacion;
	private String madreNombre;
	private String madreApellido;
	private String madreReligion;
	private String madreNacionalidad;
	private String madreOcupacion;
	
	public AdmPadres(){
		folio 			 = "";
		padreNombre		 = "";
		padreApellido 	 = "";
		padreReligion	 = "";
		padreNacionalidad= "";
		padreOcupacion	 = "";
		madreNombre		 = "";
		madreApellido	 = "";
		madreReligion	 = "";
		madreNacionalidad= "";
		madreOcupacion	 = "";
	}


	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPadreNombre() {
		return padreNombre;
	}

	public void setPadreNombre(String padreNombre) {
		this.padreNombre = padreNombre;
	}

	public String getPadreApellido() {
		return padreApellido;
	}

	public void setPadreApellido(String padreApellido) {
		this.padreApellido = padreApellido;
	}

	public String getPadreReligion() {
		return padreReligion;
	}

	public void setPadreReligion(String padreReligion) {
		this.padreReligion = padreReligion;
	}

	public String getPadreNacionalidad() {
		return padreNacionalidad;
	}

	public void setPadreNacionalidad(String padreNacionalidad) {
		this.padreNacionalidad = padreNacionalidad;
	}

	public String getPadreOcupacion() {
		return padreOcupacion;
	}

	public void setPadreOcupacion(String padreOcupacion) {
		this.padreOcupacion = padreOcupacion;
	}

	public String getMadreNombre() {
		return madreNombre;
	}

	public void setMadreNombre(String madreNombre) {
		this.madreNombre = madreNombre;
	}

	public String getMadreApellido() {
		return madreApellido;
	}

	public void setMadreApellido(String madreApellido) {
		this.madreApellido = madreApellido;
	}

	public String getMadreReligion() {
		return madreReligion;
	}

	public void setMadreReligion(String madreReligion) {
		this.madreReligion = madreReligion;
	}

	public String getMadreNacionalidad() {
		return madreNacionalidad;
	}

	public void setMadreNacionalidad(String madreNacionalidad) {
		this.madreNacionalidad = madreNacionalidad;
	}

	public String getMadreOcupacion() {
		return madreOcupacion;
	}

	public void setMadreOcupacion(String madreOcupacion) {
		this.madreOcupacion = madreOcupacion;
	}

	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			  = rs.getString("FOLIO");
		padreNombre		  = rs.getString("PADRE_NOMBRE");
		padreApellido     = rs.getString("PADRE_APELLIDO");
		padreReligion	  = rs.getString("PADRE_RELIGION");
		padreNacionalidad = rs.getString("PADRE_NACIONALIDAD");
		padreOcupacion    = rs.getString("PADRE_OCUPACION");
		madreNombre		  = rs.getString("MADRE_NOMBRE");
		madreApellido     = rs.getString("MADRE_APELLIDO");
		madreReligion	  = rs.getString("MADRE_RELIGION");
		madreNacionalidad = rs.getString("MADRE_NACIONALIDAD");
		madreOcupacion    = rs.getString("MADRE_OCUPACION");
	}
}