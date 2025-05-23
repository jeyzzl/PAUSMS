package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiMovimiento {

	private String codigoId;
	private String folio;
 	private String cuentaId;
 	private String fecha;	
 	private String descripcion;
 	private String importe;
 	private String tipo; 	
 	private String referencia; 	
 	
 	public MusiMovimiento(){
 		codigoId		= "";
 		folio 			= ""; 
 		cuentaId		= ""; 	
 		fecha			= "";
 		descripcion		= ""; 	
 		importe			= "";
 		tipo			= ""; 
 		referencia			= ""; 
 	}
 	
	
	/**
	 * @return the cuentaId
	 */
	public String getCuentaId() {
		return cuentaId;
	}

	/**
	 * @param cuentaId the cuentaId to set
	 */
	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}


	/**
	 * @return the codigoId
	 */
	public String getCodigoId() {
		return codigoId;
	}


	/**
	 * @param codigoId the codigoId to set
	 */
	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}


	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{ 			
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_MOVIMIENTO"+ 
 				"(CODIGO_ID, FOLIO, CUENTA_ID, FECHA, DESCRIPCION, IMPORTE, TIPO, REFERENCIA)"+
 				"VALUES( ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'),TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99999.99'), ?, ? )");
 			ps.setString(1, codigoId);
 			ps.setString(2, folio);
 			ps.setString(3, cuentaId);
 			ps.setString(4, fecha);
 			ps.setString(5, descripcion);
 			ps.setString(6, importe);
 			ps.setString(7, tipo);
 			ps.setString(8, referencia);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_MOVIMIENTO"+ 
 				" SET CUENTA_ID = TO_NUMBER(?,'999')," +
 				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
 				" DESCRIPCION = ?," +
 				" IMPORTE = TO_NUMBER(?,'99999.99')," +
 				" TIPO = ?," +
 				" REFERENCIA = ? " + 								
 				" WHERE CODIGO_ID = ?" +
 				" AND FOLIO = TO_NUMBER(?,'999')");
 				
 			ps.setString(1, cuentaId);
 			ps.setString(2, fecha);
 			ps.setString(3, descripcion);
 			ps.setString(4, importe);
 			ps.setString(5, tipo); 		
 			ps.setString(6, referencia);
 			ps.setString(7, codigoId);
 			ps.setString(8, folio);
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND FOLIO = TO_NUMBER(?,'999')");
 			
 			ps.setString(1, codigoId); 			
 			ps.setString(2, folio);
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|deleteReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoId 			= rs.getString("CODIGO_ID");
 		folio	 			= rs.getString("FOLIO");
 		cuentaId 			= rs.getString("CUENTA_ID");
 		fecha	 			= rs.getString("FECHA");
 		descripcion 		= rs.getString("DESCRIPCION");
 		importe 			= rs.getString("IMPORTE");
 		tipo 				= rs.getString("TIPO");
 		referencia 			= rs.getString("REFERENCIA");
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId, String folio ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			"CODIGO_ID, FOLIO, CUENTA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, DESCRIPCION, IMPORTE, TIPO, REFERENCIA "+
	 			"FROM ENOC.MUSI_MOVIMIENTO " + 
	 			"WHERE CODIGO_ID = ? " +
	 			"AND FOLIO = TO_NUMBER(?,'999')");
	 		
	 		ps.setString(1, codigoId);
			ps.setString(2, folio);
	 		
			rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMovimiento|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
 	
 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND FOLIO = TO_NUMBER(?,'999')");
 			
 			ps.setString(1, codigoId);
 			ps.setString(2, folio);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean existeAlumno(Connection conn, String codigoId) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?");
 			
 			ps.setString(1, codigoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public int maxReg(Connection conn) throws SQLException{ 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		int maximo				= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?");
 			
 			ps.setString(1, codigoId);		
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getInt("MAXIMO"); 				
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|maxReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
 	
 	public static int maxReg(Connection conn, String codigoId) throws SQLException{
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		int maximo				= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?");
 			
 			ps.setString(1, codigoId);		
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getInt("MAXIMO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|maxReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
 	
 	public static double saldoAlumno(Connection conn, String codigoId) throws SQLException{
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		double saldo			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT SUM(CASE TIPO WHEN 'C' THEN IMPORTE*1 ELSE IMPORTE*-1 END) AS SALDO FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CODIGO_ID = ?");
 			
 			ps.setString(1, codigoId);		
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				saldo = rs.getDouble("SALDO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|maxReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return saldo;
 	}
 	
 	public static int numMovtosCuenta(Connection conn, String cuentaId) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		int numMov				= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(CUENTA_ID) AS NUMMOV FROM ENOC.MUSI_MOVIMIENTO"+ 
 				" WHERE CUENTA_ID = TO_NUMBER(?,'999')");
 			
 			ps.setString(1, cuentaId);		
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				numMov = rs.getInt("NUMMOV");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiMovimiento|numMovtosCuenta|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numMov;
 	}
	
}