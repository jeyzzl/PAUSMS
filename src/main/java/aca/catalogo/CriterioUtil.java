package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CriterioUtil {
	
	public boolean insertReg(Connection conn, CatCriterio criterio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_CRITERIO(CRITERIO_ID, DESCRIPCION ) "+ 
				"VALUES(?, ?) ");
			ps.setString(1, criterio.getCriterioId());
			ps.setString(2, criterio.getDescripcion());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatCriterio criterio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_CRITERIO "+ 
				" SET DESCRIPCION = ? " +
				" WHERE CRITERIO_ID = ? ");
			ps.setString(1, criterio.getDescripcion());
			ps.setString(2, criterio.getCriterioId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String criterioId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_CRITERIO "+ 
				" WHERE CRITERIO_ID = ? ");
			ps.setString(1, criterioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatCriterio mapeaRegId( Connection conn, String criterioId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null;
		CatCriterio criterio	= new CatCriterio();
		
		try{
			ps = conn.prepareStatement("SELECT CRITERIO_ID, DESCRIPCION "+
				"FROM ENOC.CAT_CRITERIO WHERE CRITERIO_ID = ?");		 
			ps.setString(1,criterioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				criterio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return criterio;
	}
	
	public boolean existeReg(Connection conn, String criterioId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_CRITERIO WHERE CRITERIO_ID = ? "); 
			ps.setString(1,criterioId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<CatCriterio> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatCriterio> lisCriterio = new ArrayList<CatCriterio>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CRITERIO_ID, DESCRIPCION FROM ENOC.CAT_CRITERIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCriterio criterio = new CatCriterio();
				criterio.mapeaReg(rs);
				lisCriterio.add(criterio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCriterio;
	}
	

	public HashMap<String,CatCriterio> getMapAll(Connection conn, String orden ) throws SQLException{
	
		HashMap<String,CatCriterio> map = new HashMap<String,CatCriterio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CRITERIO_ID, DESCRIPCION FROM ENOC.CAT_CRITERIO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatCriterio obj = new CatCriterio();
				obj.mapeaReg(rs);
				llave = obj.getCriterioId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}