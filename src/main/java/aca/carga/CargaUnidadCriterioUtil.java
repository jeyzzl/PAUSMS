package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaUnidadCriterioUtil {
	
	public boolean insertReg(Connection conn, CargaUnidadCriterio criterio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD_CRITERIO(CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE ) "+
				"VALUES( ?, ?, ? ) ");
			ps.setString(1, criterio.getCursoCargaId());
			ps.setString(2, criterio.getCriterioId());
			ps.setString(3, criterio.getCriterioNombre());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaUnidadCriterio criterio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD_CRITERIO "+ 
				" SET CRITERIO_NOMBRE = ? " +
				" WHERE CRITERIO_ID = ? " +
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, criterio.getCriterioNombre());
			ps.setString(2, criterio.getCriterioId());
			ps.setString(3, criterio.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String criterioId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD_CRITERIO "+ 
				" WHERE CRITERIO_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ");
			ps.setString(1, criterioId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaUnidadCriterio mapeaRegId( Connection conn, String cursoCargaId, String criterioId) throws SQLException{
		
		CargaUnidadCriterio criterio = new CargaUnidadCriterio();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = ? AND CRITERIO_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,criterioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				criterio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return criterio;
	}
	
	public boolean existeReg(Connection conn, String criterioId, String cursoCargaId) throws SQLException{
		boolean 		  ok 	= false;
		ResultSet 		  rs	= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CRITERIO_ID = ?  AND CURSO_CARGA_ID = ? "); 
			ps.setString(1,criterioId);
			ps.setString(2,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean tieneInstrumentos(Connection conn, String cursoCargaId, String criterioId ) throws SQLException{
		
		boolean ok           = false;
		Statement st 		 = conn.createStatement();
		ResultSet rs 		 = null;
		String comando	     = "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + 
					  " AND SUBSTR(INSTRUMENTO_ID,1,8) = '"+criterioId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|tieneInstrumentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
   }
	
	public static String getNombreCriterio(Connection conn, String cursoCargaId, String criterioId ) throws SQLException{		
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		String nombre		= "";
		
		try{
			comando = " SELECT CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " +
					" AND CRITERIO_ID = '"+criterioId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("CRITERIO_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|getNombreCriterio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
   }
	
	public ArrayList<CargaUnidadCriterio> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaUnidadCriterio> lisCriterio  = new ArrayList<CargaUnidadCriterio>();
		Statement st 				    			= conn.createStatement();
		ResultSet rs 								= null;
		String comando								= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadCriterio cri = new CargaUnidadCriterio();
				cri.mapeaReg(rs);
				lisCriterio.add(cri);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargUnidadCriterioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCriterio;
	}
	
	public ArrayList<CargaUnidadCriterio> getListCriterio(Connection conn,String cursoCargaId, String orden ) throws SQLException{
			
			ArrayList<CargaUnidadCriterio> lisCriterio  = new ArrayList<CargaUnidadCriterio>();
			Statement st 								= conn.createStatement();
			ResultSet rs 								= null;
			String comando								= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO " + 
						" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;
				
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaUnidadCriterio cri = new CargaUnidadCriterio();
					cri.mapeaReg(rs);
					lisCriterio.add(cri);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaUnidadCriterioUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisCriterio;
		}
	

}