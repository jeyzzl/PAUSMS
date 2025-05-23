package aca.vigilancia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VigIngreso {
	private String folio;
	private String fecha;
	private String codigoId;
	private String resId;
	private String dormi;
	private String tipo;
		
	public VigIngreso(){
		folio				= "";
		fecha				= "";
		codigoId			= "";
		resId				= ""; 		
		dormi				= ""; 		
		tipo				= "";
	} 
	

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getCodigoId() {
		return codigoId;
	}


	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}


	public String getResId() {
		return resId;
	}


	public void setResId(String resId) {
		this.resId = resId;
	}


	public String getDormi() {
		return dormi;
	}


	public void setDormi(String dormi) {
		this.dormi = dormi;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.VIG_INGRESO"+ 
					"(FOLIO, FECHA, CODIGO_PERSONAL," +
					" RESIDENCIA_ID, DORMITORIO, TIPO )"+
					" VALUES( TO_NUMBER(?, '99999'), TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), ?," +
					"  ?, ?, ? )");					
			ps.setString(1, folio);
			ps.setString(2, fecha);
			ps.setString(3, codigoId);
			ps.setString(4, resId);		
			ps.setString(5, dormi);		
			ps.setString(6, tipo);
						
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement(" UPDATE ENOC.VIG_INGRESO " + 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" RESIDENCIA_ID = ? , " + 				
				" DORMI = ? , " + 				
				" TIPO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '99999') " +
				" AND CODIGO_PERSONAL = ? ");
				
			ps.setString(1, fecha);
			ps.setString(2, resId); 			
			ps.setString(3, dormi);			
			ps.setString(4, tipo);
			ps.setString(5, folio);
			ps.setString(6, codigoId); 			
		
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.VIG_INGRESO"+ 
				" WHERE FOLIO = TO_NUMBER(?, '99999') " +
				" AND CODIGO_PERSONAL = ? ");
			ps.setString(1, folio);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		resId		 	= rs.getString("RESIDENCIA_ID");
		codigoId		= rs.getString("CODIGO_PERSONAL");
		dormi			= rs.getString("DORMITORIO");
		tipo			= rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String folio, String codigoId) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , RESIDENCIA_ID, CODIGO_PERSONAL, " +
	 				" DORMITORIO, TIPO, FROM ENOC.VIG_INGRESO WHERE FOLIO = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, folio);
	 		ps.setString(2, codigoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT FOLIO FROM ENOC.VIG_INGRESO "+ 
				" WHERE FOLIO = TO_NUMBER(?,'99999')" +
				" AND CODIGO_PERSONAL = ? ");
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.VIG_INGRESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngreso|maximoReg|:"+ex);
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