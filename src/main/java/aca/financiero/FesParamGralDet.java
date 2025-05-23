package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesParamGralDet {
	private String clave;
	private String cargaId;
	private String concepto;
	private String valor;	
	
	public FesParamGralDet(){
		clave		= "";
		cargaId			= "";
		concepto			= "";      
		valor	= "";		
	}
	

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}


	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}



	public void mapeaReg(ResultSet rs ) throws SQLException{
		clave			= rs.getString("CLAVE");	
		cargaId			= rs.getString("CARGA_ID");    
		concepto		= rs.getString("CONCEPTO");            
		valor			= rs.getString("VALOR");		
	}	
	
 	public void mapeaRegId(Connection conn, String clave, String cargaId, String concepto) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT CLAVE, CARGA_ID, CONCEPTO, VALOR FROM NOE.FES_PARAMGRALDET WHERE CLAVE = ? AND CARGA_ID = ? AND CONCEPTO = ?");
	 		ps.setString(1, clave);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, concepto);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesParamGralDet|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public boolean existeReg(Connection conn, String clave, String cargaId, String concepto) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CLAVE, CARGA_ID, CONCEPTO, VALOR FROM NOE.FES_PARAMGRALDET WHERE CLAVE = ? AND CARGA_ID = ? AND CONCEPTO = ?");
			ps.setString(1, clave);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, concepto);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesParamGralDet|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getValor(Connection conn, String clave, String cargaId, String concepto) throws SQLException{	
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String valor 			= "0";
		try{
			ps = conn.prepareStatement("SELECT VALOR FROM NOE.FES_PARAMGRALDET WHERE CLAVE = ? AND CARGA_ID = ? AND CONCEPTO = ?");
			ps.setString(1, clave);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, concepto);
			rs = ps.executeQuery();
			if (rs.next())
				valor = rs.getString("VALOR");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesParamGralDet|getValor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return valor;
	}
}