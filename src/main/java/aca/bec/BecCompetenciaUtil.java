package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BecCompetenciaUtil {
	
	public boolean insertReg(Connection conn, BecCompetencia becCompetencia) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_COMPETENCIA"+ 
				"(COMPETENCIA_ID, COMPETENCIA_NOMBRE)"+
				" VALUES( ?, ? )");
					
			ps.setString(1,  becCompetencia.getCompetenciaId());
			ps.setString(2,  becCompetencia.getCompetenciaNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecCompetencia becCompetencia) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_COMPETENCIA"+ 
				" SET COMPETENCIA_NOMBRE "+				
				" WHERE COMPETENCIA_ID = ? ");
			
			ps.setString(1,  becCompetencia.getCompetenciaNombre());
			ps.setString(2,  becCompetencia.getCompetenciaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String competenciaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_COMPETENCIA"+ 
				" WHERE COMPETENCIA_ID = ? ");
			
			ps.setString(1,  competenciaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecCompetencia mapeaRegId(Connection conn, String competenciaId) throws SQLException{
		BecCompetencia becCompetencia = new BecCompetencia();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_COMPETENCIA WHERE COMPETENCIA_ID = ? "); 
			
			ps.setString(1,  competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becCompetencia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becCompetencia;
	}
	
	public String maximo(Connection conn) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  maximo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(COMPETENCIA_ID+1), 1) AS MAXIMO FROM ENOC.BEC_COMPETENCIA"); 
			
			rs = ps.executeQuery();
			if (rs.next()){		
				maximo = rs.getString("MAXIMO");
				
			}else{				
				maximo = "1";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|maximo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<BecCompetencia> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecCompetencia> lis 		= new ArrayList<BecCompetencia>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_COMPETENCIA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecCompetencia obj= new BecCompetencia();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetenciaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
}
