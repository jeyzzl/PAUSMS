// Clase para la tabla de Modulo
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatDescuentoUtil{
	
	public boolean insertReg(Connection conn, CatDescuento descuento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_DESCUENTO(DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS ) "+
				"VALUES( TO_NUMBER(?,'99'), ?, ? ) ");
			ps.setString(1, descuento.getDescuentoId());
			ps.setString(2, descuento.getDescuentoNombre());
			ps.setString(3, descuento.getUsuarios());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatDescuento descuento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_DESCUENTO"+ 
				" SET DESCUENTO_NOMBRE = ?," +
				" USUARIOS = ?"+
				" WHERE DESCUENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, descuento.getDescuentoNombre());
			ps.setString(2, descuento.getUsuarios());
			ps.setString(3, descuento.getDescuentoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String descuentoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_DESCUENTO "+ 
				"WHERE DESCUENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, descuentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatDescuento mapeaRegId( Connection conn, String descuentoId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatDescuento descuento 	= new CatDescuento();
		
		try{
			ps = conn.prepareStatement("SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS "+
				"FROM ENOC.CAT_DESCUENTO WHERE DESCUENTO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,descuentoId);
			rs = ps.executeQuery();
			if (rs.next()){
				descuento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return descuento;
	}
	
	public boolean existeReg(Connection conn, String descuentoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_DESCUENTO WHERE DESCUENTO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,descuentoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(DESCUENTO_ID)+1 MAXIMO FROM ENOC.CAT_DESCUENTO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
		
	public ArrayList<CatDescuento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatDescuento> lisDescuento = new ArrayList<CatDescuento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS FROM ENOC.CAT_DESCUENTO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatDescuento descuento = new CatDescuento();
				descuento.mapeaReg(rs);
				lisDescuento.add(descuento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDescuento;
	}
	
	public ArrayList<CatDescuento> getDescuentoUsuario(Connection conn,String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CatDescuento> lisDescuento = new ArrayList<CatDescuento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DESCUENTO_ID, DESCUENTO_NOMBRE, USUARIOS FROM  ENOC.CAT_DESCUENTO WHERE USUARIOS LIKE '%"+codigoPersonal+"%' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatDescuento descuento = new CatDescuento();
				descuento.mapeaReg(rs);
				lisDescuento.add(descuento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDescuento;
	}
	
	public HashMap<String, CatDescuento> mapDescuentos(Connection conn) throws SQLException{
		
		HashMap<String, CatDescuento> mapa	= new HashMap<String, CatDescuento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_DESCUENTO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatDescuento objeto = new CatDescuento();
				objeto.mapeaReg(rs);
				mapa.put(objeto.getDescuentoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatDescuentoUtil|mapDescuentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}