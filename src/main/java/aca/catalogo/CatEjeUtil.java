package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatEjeUtil{
	
	public boolean insertReg(Connection conn, CatEje eje ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_EJE(EJE_ID, EJE_NOMBRE, DESCRIPCION,NIVEL_ID, ORDEN ) "+
				"VALUES( ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99') ) ");
			ps.setString(1, eje.getEjeId());
			ps.setString(2, eje.getEjeNombre());
			ps.setString(3, eje.getDescripcion());
			ps.setString(4, eje.getNivelId());
			ps.setString(5, eje.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatEje eje ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_EJE "+ 
				" SET EJE_NOMBRE= ?, " +
				" DESCRIPCION = ?, " +
				" NIVEL_ID = TO_NUMBER(?,'99') ," +
				" ORDEN = TO_NUMBER(?,'99') "+
				" WHERE EJE_ID = ?");
			ps.setString(1, eje.getEjeNombre());
			ps.setString(2, eje.getDescripcion());
			ps.setString(3, eje.getNivelId());
			ps.setString(4, eje.getOrden());
			ps.setString(5, eje.getEjeId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String ejeId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_EJE "+ 
				"WHERE EJE_ID = ? ");
			ps.setString(1, ejeId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatEje mapeaRegId( Connection conn, String ejeId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatEje eje 				= new CatEje();
		
		try{
			ps = conn.prepareStatement("SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN "+
				"FROM ENOC.CAT_EJE WHERE EJE_ID = ?"); 
			ps.setString(1,ejeId);
			rs = ps.executeQuery();
			if (rs.next()){
				eje.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return eje;
	}
	
	public boolean existeReg(Connection conn, String ejeId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_EJE WHERE EJE_ID = ? "); 
			ps.setString(1,ejeId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getNombreReligion(Connection conn, String religionId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "x";
		
		try{
			ps = conn.prepareStatement("SELECT EJE_NOMBRE FROM ENOC.CAT_RELIGION WHERE EJE_ID = ?"); 
			ps.setString(1, religionId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("EJE_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public ArrayList<CatEje> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatEje> lisEje = new ArrayList<CatEje>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEje eje = new CatEje();
				eje.mapeaReg(rs);
				lisEje.add(eje);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEje;
	}
	
	public HashMap<String,CatEje> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEje> map = new HashMap<String,CatEje>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEje obj = new CatEje();
				obj.mapeaReg(rs);
				llave = obj.getEjeId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<CatEje> getListEjeNivel(Connection conn, String nivelId, String orden ) throws SQLException{
			
			ArrayList<CatEje> lisEje = new ArrayList<CatEje>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = " SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN " +
						  " FROM ENOC.CAT_EJE WHERE NIVEL_ID = '"+nivelId+"' "+ orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CatEje eje = new CatEje();
					eje.mapeaReg(rs);
					lisEje.add(eje);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisEje;
		}
	
	public  static String getNombreEje(Connection conn, String ejeId ) throws SQLException{
			
			String nombre = "";
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT EJE_NOMBRE FROM ENOC.CAT_EJE WHERE EJE_ID='"+ejeId+"'"; 
				
				rs = st.executeQuery(comando);
				if (rs.next()){
					nombre = rs.getString(1);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.CatEjeUtil|getNombreReligion|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return nombre;
		}	
}