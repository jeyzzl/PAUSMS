package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaGrupoEjeUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoEje eje ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_EJE(CURSO_CARGA_ID, EJE_ID, DESCRIPCION ) "+
				"VALUES( ?, ?, ? ) ");
			ps.setString(1, eje.getCursoCargaId());
			ps.setString(2, eje.getEjeId());
			ps.setString(3, eje.getDescripcion());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoEje eje) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_EJE "+ 
				" SET DESCRIPCION = ? " +
				" WHERE EJE_ID = ? " +
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, eje.getDescripcion());
			ps.setString(2, eje.getEjeId());
			ps.setString(3, eje.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String ejeId, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_EJE "+ 
				" WHERE EJE_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ");
			ps.setString(1, ejeId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoEje mapeaRegId( Connection conn, String cursoCargaId, String ejeId) throws SQLException{
		
		CargaGrupoEje eje = new CargaGrupoEje();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT EJE_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ? AND EJE_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,ejeId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				eje.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return eje;
	}
	
	public boolean existeReg(Connection conn, String ejeId, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_EJE WHERE EJE_ID = ?  AND CURSO_CARGA_ID = ? "); 
			ps.setString(1,ejeId);
			ps.setString(2,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existenEjes(Connection conn, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ?  "); 
			ps.setString(1,cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<CargaGrupoEje> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoEje> lisGrupoEje = new ArrayList<CargaGrupoEje>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EJE_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_EJE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoEje eje = new CargaGrupoEje();
				eje.mapeaReg(rs);
				lisGrupoEje.add(eje);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupoEje;
	}
	
	public ArrayList<CargaGrupoEje> getListEjes(Connection conn,String cursoCargaId, String orden ) throws SQLException{
			
			ArrayList<CargaGrupoEje> lisGrupoEje = new ArrayList<CargaGrupoEje>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, EJE_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_EJE " + 
						" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;
				
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaGrupoEje eje = new CargaGrupoEje();
					eje.mapeaReg(rs);
					lisGrupoEje.add(eje);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisGrupoEje;
		}

}	