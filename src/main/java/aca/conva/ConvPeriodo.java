package aca.conva;
//Bean de datos academicos del alumno
import java.sql.*;

public class ConvPeriodo{
	
	private String periodoId;
	private String periodoNombre;
	private String fechaIni;
	private String fechaFin;
	private String carrera;
			
	public ConvPeriodo(){
		periodoId 		= "";
		periodoNombre	= "";
		fechaIni		= "";
		fechaFin		= "";
		carrera			= "";
	}
		
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId 			= rs.getString("PERIODO_ID");
		periodoNombre		= rs.getString("PERIODO_NOMBRE");
		fechaIni 			= rs.getString("FECHA_INI");
		fechaFin			= rs.getString("FECHA_FIN");	
		carrera				= rs.getString("CARRERA");
	}
	
	public void mapeaRegId( Connection conn, String periodoId ) throws SQLException{	
				
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN,"
					+ " CARRERA"
					+ " FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, periodoId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}			
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
	}
}