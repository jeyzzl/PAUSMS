package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpEstadistica {
	private String codigoPersonal;
	private String cargas;
	private String modalidades;
	
	public EmpEstadistica(){
		codigoPersonal	= "";
		cargas	        = "";
		modalidades	    = "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the cargas
	 */
	public String getCargas() {
		return cargas;
	}

	/**
	 * @param cargas the cargas to set
	 */
	public void setCargas(String cargas) {
		this.cargas = cargas;
	}

	/**
	 * @return the modalidades
	 */
	public String getModalidades() {
		return modalidades;
	}

	/**
	 * @param modalidades the modalidades to set
	 */
	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cargas       	= rs.getString("CARGAS");
		modalidades    	= rs.getString("MODALIDADES");		
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES" +						
				" FROM ENOC.EMP_ESTADISTICA WHERE CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
}