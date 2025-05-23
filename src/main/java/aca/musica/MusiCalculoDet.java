package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiCalculoDet {

 	private String codigoId;
 	private String periodoId;	
 	private String cuentaId;
 	private String cantidad;
 	private String frecuencia;
 	private String maestro;
 	private String instrumentoId;
 	private String beca;
 	
 	public MusiCalculoDet(){
 		codigoId			= "";
 		periodoId			= "";
 		cuentaId			= "";
 		cantidad			= "";
 		frecuencia			= "";
 		maestro 		 	= "";
 		instrumentoId	 	= "";
 		beca	 			= "";
 	} 		

	public String getCodigoId() {
		return codigoId;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}

	public String getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}
	
	public String getBeca() {
		return beca;
	}

	public void setBeca(String beca) {
		this.beca = beca;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps = null;
		boolean ok = false;
		String comando = "";
 		try{ 			
 			comando = "INSERT INTO ENOC.MUSI_CALCULO_DET"+ 
 				"(CODIGO_ID, PERIODO_ID, CUENTA_ID, CANTIDAD, FRECUENCIA, MAESTRO_ID, INSTRUMENTO_ID, BECA)"+
 				" VALUES( ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'999'))";
 			ps = conn.prepareStatement(comando);
 			
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			ps.setString(3, cuentaId);
 			ps.setString(4, cantidad);
 			ps.setString(5, frecuencia);
 			ps.setString(6, maestro);
 			ps.setString(7, instrumentoId);
 			ps.setString(8, beca);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		PreparedStatement ps = null; 		
 		boolean ok = false;
 		
 		try{ 			
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_CALCULO_DET"+ 
 				" SET CANTIDAD = TO_NUMBER(?,'99999.99')," +
 				" FRECUENCIA = TO_NUMBER(?,'99')," +
 				" MAESTRO_ID = TO_NUMBER(?,'99'), INSTRUMENTO_ID = TO_NUMBER(?,'99'), BECA = TO_NUMBER(?, '999')" +
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?" +
 				" AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, cantidad);
 			ps.setString(2, frecuencia);
 			ps.setString(3, maestro);
 			ps.setString(4, instrumentoId);
 			ps.setString(5, beca);
 			ps.setString(6, codigoId);
 			ps.setString(7, periodoId);
 			ps.setString(8, cuentaId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_CALCULO_DET "+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?" +
 				" AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			ps.setString(3, cuentaId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|deleteReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoId 			= rs.getString("CODIGO_ID");
 		periodoId 			= rs.getString("PERIODO_ID");
 		cuentaId 			= rs.getString("CUENTA_ID");
 		cantidad			= rs.getString("CANTIDAD");
 		frecuencia			= rs.getString("FRECUENCIA");
 		maestro				= rs.getString("MAESTRO_ID");
 		instrumentoId		= rs.getString("INSTRUMENTO_ID");
 		beca				= rs.getString("BECA");
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId, String periodoId, String cuentaId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT CODIGO_ID, PERIODO_ID, CUENTA_ID, CANTIDAD, FRECUENCIA, MAESTRO_ID, INSTRUMENTO_ID, BECA"+
	 			" FROM ENOC.MUSI_CALCULO_DET WHERE CODIGO_ID = ?" + 
	 			" AND PERIODO_ID = ?" +
	 			" AND CUENTA_ID = TO_NUMBER(?,'999')");
	 		ps.setString(1, codigoId);
	 		ps.setString(2, periodoId);
	 		ps.setString(3, cuentaId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCalculoDet|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO_DET"+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?" +
 				" AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			ps.setString(3, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	public static int getNumCuentas(Connection conn, String codigoId, String periodoId ) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		int numCuentas			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(CUENTA_ID) AS NUMCUENTAS FROM ENOC.MUSI_CALCULO_DET"+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				numCuentas = rs.getInt("NUMCUENTAS");	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|getNumCuentas|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numCuentas;
 	}
 	
 	public static int getMaestro(Connection conn, String codigoId, String periodoId, String cuentaId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		int numCuentas			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT MAESTRO_ID FROM ENOC.MUSI_CALCULO_DET"+ 
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?"+
 				" AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			ps.setString(3, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				numCuentas = rs.getInt("MAESTRO_ID");	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|getMaestro|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numCuentas;
 	}
	
 	public static String getInstrumentoAlumno(Connection conn, String codigoId, String periodoId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		String inst			= "x";
 		
 		try{
 			ps = conn.prepareStatement("SELECT INSTRUMENTO_ID FROM ENOC.MUSI_CALCULO_DET"+ 
 					" WHERE CODIGO_ID = ?" +
 	 				" AND PERIODO_ID = ?");
 	 			ps.setString(1, codigoId);
 	 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				inst = rs.getString("INSTRUMENTO_ID");	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculoDet|getInstrumentoAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return inst;
 	}
	
}