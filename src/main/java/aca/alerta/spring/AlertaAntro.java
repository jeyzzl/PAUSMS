package aca.alerta.spring;
import java.sql.*;

public class AlertaAntro{
	
	private String periodoId		= "";
	private String codigoPersonal	= "";	
	private String peso				= "";
	private String talla			= "";
	private String cintura          = "";
	private String imc				= "";	
	private String grasa			= "";
	private String musculo			= "";
	private String presion			= "";
	
	public AlertaAntro(){		
		periodoId			= "";
		codigoPersonal		= "";
		peso				= "0";
		talla				= "0";
		cintura				= "0";
		imc					= "0";
		grasa				= "0";
		musculo				= "0";
		presion				= "";
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
	

	public String getImc() {
		return imc;
	}

	public void setImc(String imc) {
		this.imc = imc;
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

	public String getPresion() {
		return presion;
	}

	public void setPresion(String presion) {
		this.presion = presion;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId			= rs.getString("PERIODO_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		peso				= rs.getString("PESO");
		talla				= rs.getString("TALLA");
		cintura				= rs.getString("CINTURA");
		imc					= rs.getString("IMC");
		grasa				= rs.getString("GRASA");		
		musculo				= rs.getString("MUSCULO");
		presion				= rs.getString("PRESION");		
	}
	
public void mapeaRegId( Connection conn, String periodoId, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION"
					+ " FROM ENOC.ALERTA_ANTRO"
					+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}