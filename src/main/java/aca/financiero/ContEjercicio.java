package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContEjercicio {
	private String idEjercicio;
	private String nombre;	
	private String status;
	private String mascBalance;
	private String mascAuxiliar;
	private String mascCcosto;
	private String nivelContable;
	private String nivelTauxiliar;
	
	// Constructor
	public ContEjercicio(){		
		
		idEjercicio		= "";
		nombre			= "";	
		status			= "";
		mascBalance		= "";
		mascAuxiliar	= "";
		mascCcosto		= "";
		nivelContable	= "";
		nivelTauxiliar	= "";
		
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMascBalance() {
		return mascBalance;
	}

	public void setMascBalance(String mascBalance) {
		this.mascBalance = mascBalance;
	}

	public String getMascAuxiliar() {
		return mascAuxiliar;
	}

	public void setMascAuxiliar(String mascAuxiliar) {
		this.mascAuxiliar = mascAuxiliar;
	}

	public String getMascCcosto() {
		return mascCcosto;
	}

	public void setMascCcosto(String mascCcosto) {
		this.mascCcosto = mascCcosto;
	}

	public String getNivelContable() {
		return nivelContable;
	}

	public void setNivelContable(String nivelContable) {
		this.nivelContable = nivelContable;
	}

	public String getNivelTauxiliar() {
		return nivelTauxiliar;
	}

	public void setNivelTauxiliar(String nivelTauxiliar) {
		this.nivelTauxiliar = nivelTauxiliar;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		idEjercicio				= rs.getString("ID_EJERCICIO");
		nombre					= rs.getString("NOMBRE");
		status					= rs.getString("STATUS");
		mascBalance				= rs.getString("MASC_BALANCE");
		mascAuxiliar			= rs.getString("MASC_AUXILIAR");
		mascCcosto				= rs.getString("MASC_CCOSTO");
		nivelContable			= rs.getString("NIVEL_CONTABLE");
		nivelTauxiliar			= rs.getString("NIVEL_TAUXILIAR");
	}
	
	public void mapeaRegId(Connection conn, String idEjercicio) throws SQLException, IOException{	
		
 		PreparedStatement ps = null;
 		ResultSet 		  rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR" +	 					
	 				" FROM MATEO.CONT_EJERCICIO WHERE ID_EJERCICIO = ?");
	 		ps.setString(1, idEjercicio);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
 		
 	}
	
}