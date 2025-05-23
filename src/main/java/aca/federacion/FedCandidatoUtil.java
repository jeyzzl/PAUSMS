package aca.federacion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class FedCandidatoUtil {
	
	public boolean insertReg( Connection conn, FedCandidato fedCandidato ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FED_CANDIDATO " + 
				"(EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN) " +
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), ?, ?, ? )");			
			
			ps.setString(1, fedCandidato.getEventoId());
			ps.setString(2, fedCandidato.getCandidatoId());
			ps.setString(3, fedCandidato.getCandidatoNombre());
			ps.setString(4, fedCandidato.getCandidatos());
			ps.setString(5, fedCandidato.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedCandidatoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg( Connection conn, FedCandidato fedCandidato ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" UPDATE ENOC.FED_CANDIDATO SET " 
									 + " CANDIDATO_NOMBRE 	= ?  " 
									 + " CANDIDATOS 		= ?, " 
									 + " ORDEN			 	= ?, " 
									 + " WHERE CANDIDATO_ID = TO_NUMBER(?,'99')");
		
			ps.setString(1, fedCandidato.getCandidatoNombre());
			ps.setString(2, fedCandidato.getCandidatos());
			ps.setString(3, fedCandidato.getOrden());
			ps.setString(4, fedCandidato.getCandidatoId());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedCandidatoUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String candidatoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" DELETE FROM ENOC.FED_CANDIDATO "
									 + " WHERE CANDIDATO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, candidatoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.FedCandidatoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FedCandidato mapeaRegId( Connection conn, String candidatoId ) throws SQLException, IOException{
		FedCandidato fedCandidato = new FedCandidato();
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement(" SELECT"+
	 								   " EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN "+	 			
	 								   " FROM ENOC.FED_CANDIDATO WHERE CANDIDATO_ID = ?"); 
	 		ps.setString(1, candidatoId);			
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			fedCandidato.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedCandidatoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return fedCandidato;
 	} 	
	
	public ArrayList<FedCandidato> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FedCandidato> lisCandidato	= new ArrayList<FedCandidato>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT " 
	 				+ " EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN "	 			
	 				+ " FROM ENOC.FED_CANDIDATO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FedCandidato candidato = new FedCandidato();
				candidato.mapeaReg(rs);
				lisCandidato.add(candidato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedCandidatoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCandidato;
	}

}
