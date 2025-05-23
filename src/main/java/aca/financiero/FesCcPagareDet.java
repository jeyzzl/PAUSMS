package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesCcPagareDet {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String folio;
	private String fVencimiento;
	private String importe;
	private String status;
	
	// Constructor
	public FesCcPagareDet(){
		matricula		= "";
		cargaId			= "";
		bloque			= "";
		folio			= "";
		fVencimiento	= "";
		importe			= "";
		status			= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getfVencimiento() {
		return fVencimiento;
	}

	public void setfVencimiento(String fVencimiento) {
		this.fVencimiento = fVencimiento;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula	         	= rs.getString("MATRICULA");	
		cargaId		         	= rs.getString("CARGA_ID");   
		bloque		         	= rs.getString("BLOQUE");     
		folio			        = rs.getString("FOLIO");
		fVencimiento		    = rs.getString("FVENCIMIENTO");    
		importe	         		= rs.getString("IMPORTE");
		status	         		= rs.getString("STATUS");
	}	
	
 	public void mapeaRegId(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT MATRICULA, CARGA_ID, BLOQUE, FOLIO, FVENCIMIENTO, IMPORTE, STATUS" +
	 				" FROM MATEO.FES_CC_PAGARE_DET WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
	 		ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FecCcPagareDet|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public boolean existeReg(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA FROM MATEO.FES_CC_PAGARE_DET WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FecCcPagareDet|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}