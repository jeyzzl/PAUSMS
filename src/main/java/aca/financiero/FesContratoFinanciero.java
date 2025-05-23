/**
 * 
 */
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JoseTorres
 *
 */
public class FesContratoFinanciero {
	private String id;
	private String matricula;
	private String fechaVencimiento;
	private String importe;
	private String liquidado;
	private String fechaLiquidacion;
	private String observaciones;
	
	public FesContratoFinanciero(){
		id					= "";
		matricula			= "";
		fechaVencimiento	= "";
		importe				= "";
		liquidado			= "";
		fechaLiquidacion	= "";
		observaciones		= "";	
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the fechaVencimiento
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
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
	 * @return the liquidado
	 */
	public String getLiquidado() {
		return liquidado;
	}

	/**
	 * @param liquidado the liquidado to set
	 */
	public void setLiquidado(String liquidado) {
		this.liquidado = liquidado;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	

	public void mapeaReg(ResultSet rs ) throws SQLException{
		id						= rs.getString("ID");
		matricula				= rs.getString("MATRICULA");
		fechaVencimiento		= rs.getString("FECHA_VENCIMIENTO");
		importe					= rs.getString("IMPORTE");
		liquidado				= rs.getString("LIQUIDADO");
		fechaLiquidacion		= rs.getString("FECHA_LIQUIDACION");
		observaciones			= rs.getString("OBSERVACIONES");
	}
	
	public void mapeaRegId(Connection con, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = con.prepareStatement("SELECT ID, MATRICULA," +
					" TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') AS FECHA_VENCIMIENTO," +
					" IMPORTE, LIQUIDADO," +
					" TO_CHAR(FECHA_LIQUIDACION, 'DD/MM/YYYY') AS FECHA_LIQUIDACION," +
					" OBSERVACIONES"+
					" FROM NOE.FES_CONTRATO_FINANCIERO" +
					" WHERE ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinanciero|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM NOE.FES_CONTRATO_FINANCIERO" +
					" WHERE ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, id);
				rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinanciero|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

}