package aca.vigilancia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VigTipoMulta {
	
	private String tipoId;
	private String tipoNombre;
	private String costo;
	
	
	public VigTipoMulta(){
		tipoId				= "";
		tipoNombre			= "";
		costo				= "";	
	}


	public String getTipoId() {
		return tipoId;
	}


	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}


	public String getTipoNombre() {
		return tipoNombre;
	}


	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}


	public String getCosto() {
		return costo;
	}


	public void setCosto(String costo) {
		this.costo = costo;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.VIG_TIPOMULTA"+ 
				"(TIPO_ID, TIPO_NOMBRE, COSTO)"+
				" VALUES( TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '999.99'))");
			
			ps.setString(1, tipoId);
			ps.setString(2, tipoNombre);
			ps.setString(3, costo);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.VIG_TIPOMULTA"+ 
				" SET"+		
				" TIPO_NOMBRE = ?,"+
				" COSTO = TO_NUMBER(?, '999.99')"+
				" WHERE TIPO_ID = TO_NUMBER(?, '99')");
				
			ps.setString(1, tipoNombre);
			ps.setString(2, costo); 			
			ps.setString(3, tipoId);
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.VIG_TIPOMULTA"+ 
				" WHERE TIPO_ID = TO_NUMBER(?, '99')  ");
			ps.setString(1, tipoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		tipoId				= rs.getString("TIPO_ID");
		tipoNombre	 		= rs.getString("TIPO_NOMBRE");
		costo		 		= rs.getString("COSTO");	
	}
	
	public void mapeaRegId( Connection conn, String tipoId) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" TIPO_ID, TIPO_NOMBRE, COSTO" +
	 			" FROM ENOC.VIG_TIPOMULTA WHERE TIPO_ID = TO_NUMBER(?,'99') "); 
	 		ps.setString(1, tipoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(TIPO_ID)+1,'') AS MAXIMO FROM ENOC.VIG_TIPOMULTA");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		PreparedStatement 	ps		= null;
		boolean 			ok 		= false;
		ResultSet 			rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT TIPO_ID FROM ENOC.VIG_TIPOMULTA"+ 
				" WHERE TIPO_ID = ?");
			ps.setString(1, tipoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMulta|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}