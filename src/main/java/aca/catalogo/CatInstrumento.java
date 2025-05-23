package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatInstrumento {
	private String instrumentoId;	
	private String descripcion;
	
	public CatInstrumento(){
		instrumentoId     = "";
		descripcion     = "";
	}
	

	public String getInstrumentoId() {
		return instrumentoId;
	}


	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_INSTRUMENTO(INSTRUMENTO_ID, DESCRIPCION ) "+ 
				"VALUES(?, ?) ");
			ps.setString(1, instrumentoId);
			ps.setString(2, descripcion);

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstrumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_INSTRUMENTO "+ 
				" SET DESCRIPCION = ? " +
				" WHERE INSTRUMENTO_ID = ? ");
			ps.setString(1, descripcion);
			ps.setString(2, instrumentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstrumento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_INSTRUMENTO "+ 
				" WHERE INSTRUMENTO_ID = ? ");
			ps.setString(1, instrumentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstrumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		instrumentoId     = rs.getString("INSTRUMENTO_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId( Connection conn, String instrumentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT INSTRUMENTO_ID, DESCRIPCION "+
				"FROM ENOC.CAT_INSTRUMENTO WHERE INSTRUMENTO_ID = ?");		 
			ps.setString(1,instrumentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstrumento|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_INSTRUMENTO WHERE INSTRUMENTO_ID = ? "); 
			ps.setString(1,instrumentoId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstrumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

}