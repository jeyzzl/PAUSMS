package aca.afis;
import java.sql.*;

public class AfisRegistro {
	
	private String periodoId;
	private String codigoPersonal;
	private String fecha;
	private String peso;
	private String talla;
	private String cintura;
	private String grasa;
	private String musculo;
	private String imc;
	private String carreraId;
	private String dieta;
	private String residencia;
	
	public AfisRegistro(){
		periodoId			= "";
		codigoPersonal		= "";
		fecha				= "";
		peso				= "";
		talla				= "";
		cintura				= "";
		grasa				= "";
		musculo				= "";
		imc					= "";
		carreraId			= "";
		dieta				= "";
		residencia			= "";
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
	
	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	
	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
	}
	
	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId 		= rs.getString("PERIODO_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		fecha 			= rs.getString("FECHA");
		peso 			= rs.getString("PESO");
		talla 			= rs.getString("TALLA");
		cintura 		= rs.getString("CINTURA");
		grasa 			= rs.getString("GRASA");
		musculo 		= rs.getString("MUSCULO");
		imc 			= rs.getString("IMC");
		carreraId 		= rs.getString("CARRERA_ID");
		dieta	 		= rs.getString("DIETA");
		residencia 		= rs.getString("RESIDENCIA");
	}
	
public void mapeaRegId(Connection con, String codigoPersonal, String periodoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement(" SELECT PERIODO_ID, CODIGO_PERSONAL, TO_DATE(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA "
									+ " FROM ENOC.AFIS_REGISTRO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
									+ " AND PERIODO_ID = '"+periodoId+"'");
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
				System.out.println("Error - aca.afis.AfisRegistroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}
