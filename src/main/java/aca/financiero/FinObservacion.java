package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinObservacion {
	
	private String matricula;
	private String folio;
	private String observacion;
	private String empleado;
	
	public FinObservacion(){
		matricula	= "";
		folio		= "";
		observacion = "";
		empleado	= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		matricula 		= rs.getString("CODIGO_PERSONAL");
		folio 			= rs.getString("FOLIO");
		observacion 	= rs.getString("OBSERVACION");
		empleado		= rs.getString("EMPLEADO");
	}
	
	public void mapeaRegId(Connection conn, FinObservacion finObservacion) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, OBSERVACION, EMPLEADO " +	 					
	 				" FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
	 		ps.setString(1, finObservacion.getMatricula());
	 		ps.setString(2, finObservacion.getFolio());	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			finObservacion.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
}
