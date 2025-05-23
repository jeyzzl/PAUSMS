package aca.well;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WellAntro {

	private String periodoId;
	private String codigoPersonal;
	private String fecha;
	private String peso;
	private String talla;
	private String cintura;
	private String grasa;
	private String musculo;
	private String imc;
	private String dieta;
	
	public WellAntro(){
		periodoId 		= "";
		codigoPersonal 	= "";
		fecha 			= "";
		peso 			= "";
		talla 			= "";
		cintura 		= "";
		grasa 			= "";
		musculo 		= "";
		imc 			= "";
		dieta 			= "";
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getCintura() {
		return cintura;
	}

	public void setCintura(String cintura) {
		this.cintura = cintura;
	}

	public String getGrasa() {
		return grasa;
	}

	public void setGrasa(String grasa) {
		this.grasa = grasa;
	}

	public String getMusculo() {
		return musculo;
	}

	public void setMusculo(String musculo) {
		this.musculo = musculo;
	}

	public String getImc() {
		return imc;
	}

	public void setImc(String imc) {
		this.imc = imc;
	}

	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		periodoId			= rs.getString("PERIODO_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fecha				= rs.getString("FECHA");
	    peso				= rs.getString("PESO");
		talla				= rs.getString("TALLA");
		cintura				= rs.getString("CINTURA");
		grasa				= rs.getString("GRASA");
		musculo				= rs.getString("MUSCULO");
		imc					= rs.getString("IMC");
		dieta				= rs.getString("DIETA");
 	}
	
}
