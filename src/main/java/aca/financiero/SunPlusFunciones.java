/**
 * 
 */
package aca.financiero;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SunPlusFunciones {
	private String departamento;
	private String funcion;
	
	public SunPlusFunciones(){
		departamento	= "";
		funcion			= "";		
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		departamento	= rs.getString("DEPARTAMENTO");
		funcion		= rs.getString("FUNCION");		
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	
}