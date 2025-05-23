package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumConstanciaImpresion {
	private String constanciaId;
	private String codigoPersonal;
	private String fechaImpresion;
	
	public AlumConstanciaImpresion(){
		constanciaId		= "";
		codigoPersonal		= "";
		fechaImpresion		= "";
	}

	public String getConstanciaId() {
		return constanciaId;
	}

	public void setConstanciaId(String constanciaId) {
		this.constanciaId = constanciaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		constanciaId		= rs.getString("CONSTANCIA_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fechaImpresion		= rs.getString("FECHA_IMPRESION");
	}
	
	public void mapeaRegId( Connection conn, String constanciaId, String codigoPersonal, String fechaImpresion) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT * "+
				"FROM ENOC.ALUM_CONSTANCIA_IMPRESION "+ 
				"WHERE CONSTANCIA_ID = ? AND CODIGO_PERSONAL = ? AND FECHA_IMPRESION = ? ");
			ps.setString(1, constanciaId);	
			ps.setString(2, codigoPersonal);	
			ps.setString(3, fechaImpresion);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaImpresionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}
