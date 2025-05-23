package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaUnidadCompUtil {
	public boolean insertReg(Connection conn, CargaUnidadComp comp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD_COMP(CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID )"
					+ " VALUES( ?, ?, ? ) ");
			ps.setString(1, comp.getCursoCargaId());
			ps.setString(2, comp.getUnidadId());
			ps.setString(3, comp.getCompetenciaId());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaUnidadComp comp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD_COMP "+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND UNIDAD_ID = ?" +
				" AND COMPETENCIA_ID = ? ");
			ps.setString(1, comp.getCursoCargaId());
			ps.setString(2, comp.getUnidadId());
			ps.setString(3, comp.getCompetenciaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public CargaUnidadComp mapeaRegId( Connection conn, String cursoCargaId, String unidadId, String competenciaId) throws SQLException{
		
		CargaUnidadComp comp = new CargaUnidadComp();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID "+
				"FROM ENOC.CARGA_UNIDAD_COMP WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ? AND COMPETENCIA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,unidadId);
			ps.setString(3,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				comp.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return comp;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String unidadId, String competenciaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_COMP WHERE CURSO_CARGA_ID = ? " + 
					" AND UNIDAD_ID = ?" +
					" AND COMPETENCIA_ID = ?");
			
			ps.setString(1,cursoCargaId);
			ps.setString(2,unidadId);
			ps.setString(3,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int numUnidadComp(Connection conn, String cursoCargaId, String competenciaId) throws SQLException{		
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		int numComp 			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMCOMP" +
					" FROM ENOC.CARGA_UNIDAD_COMP" + 
					" WHERE CURSO_CARGA_ID = ?" +				
					" AND COMPETENCIA_ID = ?");
			
			ps.setString(1,cursoCargaId);			
			ps.setString(2,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numComp = rs.getInt("NUMCOMP");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|existeUnidadComp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numComp;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String unidadId, String competenciaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD_COMP "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND UNIDAD_ID = ?" +
				" AND COMPETENCIA_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, unidadId);
			ps.setString(3, competenciaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<CargaUnidadComp> getListAll(Connection conn, String orden ) throws SQLException{
			
			ArrayList<CargaUnidadComp> lisComp = new ArrayList<CargaUnidadComp>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID FROM ENOC.CARGA_UNIDAD_COMP "+ orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaUnidadComp comp = new CargaUnidadComp();
					comp.mapeaReg(rs);
					lisComp.add(comp);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaUnidadCompUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisComp;
		}
	
	public ArrayList<CargaUnidadComp> getListCompetencias(Connection conn,String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaUnidadComp> lisGrupoEje = new ArrayList<CargaUnidadComp>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID FROM ENOC.CARGA_UNIDAD_COMP " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadComp comp = new CargaUnidadComp();
				comp.mapeaReg(rs);
				lisGrupoEje.add(comp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCompUtil|getListCompetencias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupoEje;
	}
	
}