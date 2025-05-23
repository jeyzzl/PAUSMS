package aca.federacion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class FedVotosUtil {
	
	public boolean insertReg( Connection conn, FedVotos fedVotos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FED_VOTOS " + 
				"(EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FECHA, VOTO) " +
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), ? )");			
			
			ps.setString(1, fedVotos.getEventoId());
			ps.setString(2, fedVotos.getCandidatoId());
			ps.setString(3, fedVotos.getCodigoPersonal());
			ps.setString(4, fedVotos.getFecha());
			ps.setString(5, fedVotos.getVoto());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotosUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg( Connection conn, FedVotos fedVotos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" UPDATE ENOC.FED_VOTOS SET " 
									 + " FECHA 	= ?  " 
									 + " VOTO 	= ?, " 
									 + " WHERE EVENTO_ID = TO_NUMBER(?,'999') "
									 + " AND CANDIDATO_ID = TO_NUMBER(?,'999') " 
									 + " AND CODIGO_PERSONAL = ?");
		
			ps.setString(1, fedVotos.getFecha());
			ps.setString(2, fedVotos.getVoto());
			ps.setString(3, fedVotos.getEventoId());
			ps.setString(4, fedVotos.getCandidatoId());
			ps.setString(5, fedVotos.getCodigoPersonal());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotosUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String eventoId, String candidatoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" DELETE FROM ENOC.FED_VOTOS "
									 + " WHERE EVENTO_ID = TO_NUMBER(?,'999') " 
									 + " AND CANDIDATO_ID = TO_NUMBER(?,'999') " 
									 + " AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, candidatoId);
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.FedVotosUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String eventoId, String candidatoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		try{
			ps = conn.prepareStatement(" SELECT * FROM ENOC.FED_VOTOS"
									 + " WHERE EVENTO_ID = TO_NUMBER(?,'999') " 
									 + " AND CANDIDATO_ID = TO_NUMBER(?,'999') " 
									 + " AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, candidatoId);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
	 		if (rs.next()){			
				ok = true;
	 		}	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.FedVotosUtil|existeReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FedVoto mapeaRegId( Connection conn, String eventoId, String candidatoId, String codigoPersonal) throws SQLException, IOException{
		FedVoto fedVoto = new FedVoto();
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement(" SELECT"+
	 								   " EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD,MM,YYYY') AS FECHA, VOTO "+	 			
	 								   " FROM ENOC.FED_VOTOS WHERE EVENTO_ID = ?, CANDIDATO_ID = ?, CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, eventoId);
	 		ps.setString(2, candidatoId);
	 		ps.setString(3, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			
	 			fedVoto.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotosUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return fedVoto;
 	} 	
	
	public ArrayList<FedVotos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FedVotos> lisVotos = new ArrayList<FedVotos>();
		Statement st 				 = conn.createStatement();
		ResultSet rs 				 = null;
		String comando				 = "";
		
		try{
			comando = " SELECT "
					+ " EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VOTO "
					+ " FROM ENOC.FED_VOTOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FedVotos voto = new FedVotos();
				voto.mapeaReg(rs);
				lisVotos.add(voto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisVotos;
	}

}
