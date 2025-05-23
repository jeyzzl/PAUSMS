package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaGrupoBiblioUtil {
	
	public boolean insertReg(Connection conn, CargaGrupoBiblio biblio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" INSERT INTO ENOC.CARGA_GRUPO_BIBLIO(CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN) "+
				" VALUES( ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'))");
			
			ps.setString(1, biblio.getCursoCargaId());
			ps.setString(2, biblio.getBiblioId());
			ps.setString(3, biblio.getBibliografia());
			ps.setString(4, biblio.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn, CargaGrupoBiblio biblio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_BIBLIO "+ 
				" SET BIBLIOGRAFIA = ?," +
				" ORDEN = TO_NUMBER(?,'99') " +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND BIBLIO_ID = TO_NUMBER(?,'99')" );		
			
			ps.setString(1, biblio.getBibliografia());
			ps.setString(2, biblio.getOrden());
			ps.setString(3, biblio.getCursoCargaId());
			ps.setString(4, biblio.getBiblioId());	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String biblioId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_BIBLIO "+ 
				" WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99')" );
			ps.setString(1, cursoCargaId);
			ps.setString(2, biblioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoBiblio mapeaRegId( Connection conn, String cursoCargaId, String biblioId ) throws SQLException{
		
		CargaGrupoBiblio biblio = new CargaGrupoBiblio();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN "+
				"FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99')");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,biblioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				biblio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return biblio;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String biblioId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,cursoCargaId);
			ps.setString(2,biblioId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeBibliografia(Connection conn, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeBibliografia(Connection conn, String cursoCargaId, String biblioId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ?  AND BIBLIO_ID= TO_NUMBER(?,'99')"); 
			ps.setString(1,cursoCargaId);
			ps.setString(2,biblioId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(BIBLIO_ID)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID= ?"); 
			ps.setString(1,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	
	public ArrayList<CargaGrupoBiblio> getListAll(Connection conn, String orden ) throws SQLException{
			
			ArrayList<CargaGrupoBiblio> lisGrupoBiblio = new ArrayList<CargaGrupoBiblio>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN FROM ENOC.CARGA_GRUPO_BIBLIO "+ orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaGrupoBiblio biblio = new CargaGrupoBiblio();
					biblio.mapeaReg(rs);
					lisGrupoBiblio.add(biblio);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoBiblioUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisGrupoBiblio;
		}
	
	public ArrayList<CargaGrupoBiblio> getListBiblio(Connection conn, String cursoCargaId, String orden) throws SQLException{
		
		ArrayList<CargaGrupoBiblio> lisGrupoBiblio = new ArrayList<CargaGrupoBiblio>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN " +
					" FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoBiblio biblio = new CargaGrupoBiblio();
				biblio.mapeaReg(rs);
				lisGrupoBiblio.add(biblio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupoBiblio;
	}
}