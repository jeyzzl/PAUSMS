package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecPlazas {
	private String idEjercicio;
	private String idCcosto;
	private String numPlazas; //plazas básicas
	private String numIndustriales;
	private String numTemporales;
	private String numPreIndustriales;
	private String numPosgrado;
	private String periodoId;
	
	public BecPlazas(){
		idEjercicio		= "";
		idCcosto		= "";
		numPlazas		= "0";
		numIndustriales = "0";
		numTemporales	= "0";
		numPreIndustriales	= "0";
		numPosgrado		= "0";
		periodoId		= "";
	}
	

	public String getIdEjercicio() {
		return idEjercicio;
	}


	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}


	public String getIdCcosto() {
		return idCcosto;
	}


	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}


	public String getNumPlazas() {
		return numPlazas;
	}


	public void setNumPlazas(String numPlazas) {
		this.numPlazas = numPlazas;
	}
	
	public String getNumTemporales() {
		return numTemporales;
	}


	public void setNumTemporales(String numTemporales) {
		this.numTemporales= numTemporales;
	}

	public String getNumIndustriales() {
		return numIndustriales;
	}


	public void setNumIndustriales(String numIndustriales) {
		this.numIndustriales = numIndustriales;
	}

	public String getNumPreIndustriales() {
		return numPreIndustriales;
	}


	public void setNumPreIndustriales(String numPreIndustriales) {
		this.numPreIndustriales = numPreIndustriales;
	}


	public String getNumPosgrado() {
		return numPosgrado;
	}


	public void setNumPosgrado(String numPosgrado) {
		this.numPosgrado = numPosgrado;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		idEjercicio		= rs.getString("ID_EJERCICIO");
		idCcosto		= rs.getString("ID_CCOSTO");
		numPlazas		= rs.getString("NUM_PLAZAS");
		numIndustriales = rs.getString("NUM_INDUSTRIALES");
		numTemporales	= rs.getString("NUM_TEMPORALES");
		numPreIndustriales = rs.getString("NUM_PREINDUSTRIALES");
		numPosgrado		= rs.getString("NUM_POSGRADO");
		periodoId		= rs.getString("PERIODO_ID");
	}
	
	public void mapeaRegId(Connection conn, String idEjercicio, String idCcosto, String periodoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT NUM_PREINDUSTRIALES, NUM_POSGRADO, NUM_TEMPORALES, NUM_INDUSTRIALES, NUM_PLAZAS, ID_EJERCICIO, ID_CCOSTO, PERIODO_ID" +
					" FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazas|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
			
}