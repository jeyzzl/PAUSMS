package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecCategoriaUtil {
	
	public boolean insertReg(Connection conn, BecCategoria becCategoria) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_CATEGORIA"+ 
				"(CATEGORIA_ID, CATEGORIA_NOMBRE, USUARIO, ESTADO, PDF)"+
				" VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?)");
					
			ps.setString(1,  becCategoria.getCategoriaId());
			ps.setString(2,  becCategoria.getCategoriaNombre());
			ps.setString(3,  becCategoria.getCategoriaUsuario());
			ps.setString(4,  becCategoria.getEstado());
			ps.setString(5,  becCategoria.getPdf());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecCategoria becCategoria) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_CATEGORIA"+ 
				" SET CATEGORIA_NOMBRE = ?, USUARIO = ?, ESTADO = ?, PDF = ?"+				
				" WHERE CATEGORIA_ID = TO_NUMBER(?,'999') ");
			
			ps.setString(1,  becCategoria.getCategoriaNombre());
			ps.setString(2,  becCategoria.getCategoriaUsuario());
			ps.setString(3,  becCategoria.getEstado());
			ps.setString(4,  becCategoria.getPdf());
			ps.setString(5,  becCategoria.getCategoriaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public boolean deleteReg(Connection conn, String categoriaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_CATEGORIA"+ 
				" WHERE CATEGORIA_ID = TO_NUMBER(?,'999')");
			
			ps.setString(1,  categoriaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecCategoria mapeaRegId(Connection conn, String categoriaId) throws SQLException{
		BecCategoria becCategoria = new BecCategoria();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')"); 
			
			ps.setString(1,  categoriaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becCategoria.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becCategoria;
	}
	
	public boolean existeReg(Connection conn, String categoriaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,  categoriaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public String maximo(Connection conn) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  maximo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(CATEGORIA_ID+1), 1) AS MAXIMO FROM ENOC.BEC_CATEGORIA"); 
			
			rs = ps.executeQuery();
			if (rs.next()){		
				maximo = rs.getString("MAXIMO");
				
			}else{				
				maximo = "1";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|maximo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreCategoria(Connection conn, String categoriaId) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombre	 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT CATEGORIA_NOMBRE FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,  categoriaId);
			rs = ps.executeQuery();
			if (rs.next()){		
				nombre = rs.getString("CATEGORIA_NOMBRE");
				
			}else{				
				nombre = "1";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|getNombreCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static boolean getTienePuesto(Connection conn, String categoriaId) throws SQLException{	
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		boolean ok 				= false; 
		
		try{
			ps = conn.prepareStatement("SELECT CATEGORIA_NOMBRE FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,  categoriaId);
			rs = ps.executeQuery();
			if (rs.next()){	
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|getTienePuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<BecCategoria> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecCategoria> lis 		= new ArrayList<BecCategoria>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_CATEGORIA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecCategoria obj= new BecCategoria();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static HashMap<String, String> getMapCategorias(Connection conn)throws SQLException{
		HashMap<String, String> map = new HashMap<String, String>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT CATEGORIA_ID, CATEGORIA_NOMBRE FROM ENOC.BEC_CATEGORIA ";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CATEGORIA_ID"), rs.getString("CATEGORIA_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|getMapCategorias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}

	public static HashMap<String, BecCategoria> mapCategorias(Connection conn)throws SQLException{
		HashMap<String, BecCategoria> mapa 	= new HashMap<String, BecCategoria>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT CATEGORIA_ID, CATEGORIA_NOMBRE, USUARIO, ESTADO, PDF FROM ENOC.BEC_CATEGORIA ";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				BecCategoria categoria = new BecCategoria();
				categoria.setCategoriaId(rs.getString("CATEGORIA_ID"));
				categoria.setCategoriaNombre(rs.getString("CATEGORIA_NOMBRE"));
				categoria.setCategoriaUsuario(rs.getString("USUARIO"));
				categoria.setEstado(rs.getString("ESTADO"));
				categoria.setPdf(rs.getString("PDF"));
				
				mapa.put(rs.getString("CATEGORIA_ID"), categoria);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoriaUtil|mapCategorias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapa;
	}
	
}
