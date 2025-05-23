package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumActualiza {
	private String codigoPersonal;
	private String codigoEmpleado;
	private String fecha;
	private String estado;	
	
	public AlumActualiza(){
		codigoPersonal		= "";
		codigoEmpleado		= "";
		fecha				= "";
		estado				= "";		
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		codigoEmpleado	= rs.getString("CODIGO_EMPLEADO");
		fecha			= rs.getString("FECHA");
		estado	 		= rs.getString("ESTADO");		
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO "+
				"FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}
