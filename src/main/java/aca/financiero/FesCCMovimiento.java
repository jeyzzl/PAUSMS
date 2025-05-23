package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesCCMovimiento {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String tipoMov;
	private String descripcion;
	private String importe;
	private String naturaleza;
	
	// Constructor
	public FesCCMovimiento(){		
		matricula			= "";		
		cargaId	        	= "";
		bloque				= "";
		tipoMov				= "";
		descripcion			= "";
		importe				= "";
		naturaleza			= "";
	}
	
	
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
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
	 * @return the bloque
	 */
	public String getBloque() {
		return bloque;
	}


	/**
	 * @param bloque the bloque to set
	 */
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}


	/**
	 * @return the tipoMov
	 */
	public String getTipoMov() {
		return tipoMov;
	}


	/**
	 * @param tipoMov the tipoMov to set
	 */
	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}


	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}


	/**
	 * @return the naturaleza
	 */
	public String getNaturaleza() {
		return naturaleza;
	}


	/**
	 * @param naturaleza the naturaleza to set
	 */
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula 			= rs.getString("MATRICULA");		
		cargaId				= rs.getString("CARGA_ID");
		bloque				= rs.getString("BLOQUE");
		tipoMov				= rs.getString("TIPOMOV");
		descripcion			= rs.getString("DESCRIPCION");
		importe				= rs.getString("IMPORTE");
		naturaleza			= rs.getString("NATURALEZA");
	}	
	
 	public void mapeaRegId( Connection conn, String matricula ) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT MATRICULA, CARGA_ID, BLOQUE, TIPOMOV," +
	 				" DESCRIPCION,IMPORTE, NATURALEZA "+
	 				" FROM MATEO.FES_CC_MOVIMIENTO WHERE MATRICULA = ? ");
	 		ps.setString(1, matricula);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimiento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}

	
	public boolean existeReg(Connection conn, String matricula) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MATEO.FES_CC_MOVIMIENTO "+
				"WHERE MATRICULA = ? ");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimiento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static double getSaldoAnterior(Connection conn, String matricula, String cargaId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		double	saldo			= 0; 
		
		try{
			ps = conn.prepareStatement("SELECT IMPORTE FROM MATEO.FES_CC_MOVIMIENTO "+
				"WHERE MATRICULA = ? AND CARGA_ID = ? AND TIPOMOV = '20'");
			ps.setString(1, matricula);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				saldo = rs.getDouble("IMPORTE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimiento|getSaldoAnterior|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return saldo;
	}
	
	public static double getImporteMovto(Connection conn, String matricula, String cargaId, String tipoMov) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		double	saldo			= 0; 
		
		try{
			ps = conn.prepareStatement("SELECT SUM(IMPORTE) AS TOTAL FROM MATEO.FES_CC_MOVIMIENTO "+
				"WHERE MATRICULA = ? AND CARGA_ID = ? AND TIPOMOV IN (?)");
			ps.setString(1, matricula);
			ps.setString(2, cargaId);
			ps.setString(3, tipoMov);
			
			rs = ps.executeQuery();
			if (rs.next())
				saldo = rs.getDouble("TOTAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimiento|getImporteMovto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return saldo;
	}
}