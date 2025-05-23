package aca.alerta;
import java.sql.*;

public class AlertaVacuna{
	
	private String codigoPersonal   = "";	
	private String vacuna	        = "";	
	private String aplicada			= "";
	private String fecha1			= "";
	private String fecha2			= "";
	private String fecha3			= "";	
	
	public AlertaVacuna(){
		
		codigoPersonal	    = "";
		vacuna		        = "";
		aplicada			= "";
		fecha1				= "";
		fecha2				= "";
		fecha3				= "";
	
	}	
	
	public String getVacuna() {
		return vacuna;
	}

	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}
	
	public String getAplicada() {
		return aplicada;
	}

	public void setAplicada(String aplicada) {
		this.aplicada = aplicada;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha1() {
		return fecha1;
	}

	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}

	public String getFecha2() {
		return fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getFecha3() {
		return fecha3;
	}

	public void setFecha3(String fecha3) {
		this.fecha3 = fecha3;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		vacuna			    = rs.getString("VACUNA");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		aplicada			= rs.getString("APLICADA");
		fecha1				= rs.getString("FECHA1");
		fecha2				= rs.getString("FECHA2");
		fecha3				= rs.getString("FECHA3");
	}
	
public void mapeaRegId( Connection conn, String vacunaId, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT VACUNA, CODIGO_PERSONAL, APLICADA, TO_CHAR(FECHA1, 'DD/MM/YYYY') AS FECHA1, TO_CHAR(FECHA2, 'DD/MM/YYYY') AS FECHA2, TO_CHAR(FECHA3, 'DD/MM/YYYY') AS FECHA3"
					+ " FROM ENOC.ALERTA_VACUNA"
					+ " WHERE VACUNA = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, vacunaId);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}