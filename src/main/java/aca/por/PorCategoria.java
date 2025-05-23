//Bean del Portafolio
package aca.por;

import java.sql.*;

public class PorCategoria {

	private String categoriaId;
	private String categoriaNombre;	
	
	public PorCategoria(){
		categoriaId 	= "";
		categoriaNombre	= "";
	}
	
	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.POR_CATEGORIA( CATEGORIA_ID, CATEGORIA_NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'99'), ?)");
			ps.setString(1, categoriaId);
			ps.setString(2, categoriaNombre);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_CATEGORIA SET CATEGORIA_NOMBRE = ? WHERE CATEGORIA_ID = TO_NUMBER(?,'99')");
			ps.setString(1, categoriaNombre);			
			ps.setString(2, categoriaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'99')");
			ps.setString(1, categoriaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		categoriaId 		= rs.getString("CATEGORIA_ID");
		categoriaNombre 	= rs.getString("CATEGORIA_NOMBRE");		
	}
	
	public void mapeaRegId( Connection conn, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CATEGORIA_ID, CATEGORIA_NOMBRE FROM ENOC.POR_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, categoriaId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		boolean 		ok 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,categoriaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getCategoriaNombre(Connection conn, String categoriaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT CATEGORIA_NOMBRE FROM ENOC.POR_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,categoriaId);
			rs = ps.executeQuery();
			if (rs.next()){			
				nombre = rs.getString("CATEGORIA_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoria|getCategoriaNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
}