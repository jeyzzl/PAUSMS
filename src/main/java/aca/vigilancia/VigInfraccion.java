package aca.vigilancia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VigInfraccion {
	private String folio;
	private String fecha;
	private String autoId;
	private String tipoId;
	private String descripcion;
	private String multa;
		
	public VigInfraccion(){
		folio				= "";
		fecha				= "";
		autoId				= "";
		tipoId				= ""; 		
		descripcion			= "";
		multa				= "";	
	} 
	
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the autoId
	 */
	public String getAutoId() {
		return autoId;
	}

	/**
	 * @param autoId the autoId to set
	 */
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	/**
	 * @return the tipoId
	 */
	public String getTipoId() {
		return tipoId;
	}

	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
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
	 * @return the multa
	 */
	public String getMulta() {
		return multa;
	}

	/**
	 * @param multa the multa to set
	 */
	public void setMulta(String multa) {
		this.multa = multa;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.VIG_INFRACCION"+ 
					"(FOLIO, FECHA, AUTO_ID," +
					" TIPO_ID, DESCRIPCION, MULTA )"+
					" VALUES( TO_NUMBER(?, '9999999'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?, '9999')," +
					" TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '9999.99'))");					
			ps.setString(1, folio);
			ps.setString(2, fecha);
			ps.setString(3, autoId);
			ps.setString(4, tipoId);		
			ps.setString(5, descripcion);
			ps.setString(6, multa); 			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement(" UPDATE ENOC.VIG_INFRACCION "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				" AUTO_ID = TO_NUMBER(?,'9999'), "+ 				
				" TIPO_ID = TO_NUMBER(?,'99'), "+
				" DESCRIPCION = ?, "+
				" MULTA = TO_NUMBER(?,'9999.99'), "+
				" WHERE FOLIO = TO_NUMBER(?, '9999999') ");
				
			ps.setString(1, fecha);
			ps.setString(2, autoId); 			
			ps.setString(3, tipoId);
			ps.setString(4, descripcion);
			ps.setString(5, multa);
			ps.setString(6, folio); 			
		
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.VIG_INFRACCION"+ 
				" WHERE FOLIO = TO_NUMBER(?, '9999999')  ");
			ps.setString(1, folio);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		autoId		 	= rs.getString("AUTO_ID");
		tipoId			= rs.getString("TIPO_ID");
		descripcion		= rs.getString("DESCRIPCION");
		multa			= rs.getString("MULTA");	
	}
	
	public void mapeaRegId( Connection conn, String folio) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , AUTO_ID, TIPO_ID, " +
	 				" DESCRIPCION, MULTA FROM ENOC.VIG_INFRACCION WHERE FOLIO = TO_NUMBER(?,'9999999') "); 
	 		ps.setString(1, folio);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAutoInfraccion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO FROM ENOC.VIG_INFRACCION "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeAutoId(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT AUTO_ID FROM ENOC.VIG_INFRACCION "+ 
				" WHERE AUTO_ID = TO_NUMBER(?,'9999')");
			ps.setString(1, autoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|existeAutoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.VIG_INFRACCION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String ordenarFecha(Connection conn, String fecha){		
		fecha = fecha.substring(8, 10)+ "/" + fecha.substring(5, 7)+ "/" + fecha.substring(0, 4);	
		return fecha;
	}
}