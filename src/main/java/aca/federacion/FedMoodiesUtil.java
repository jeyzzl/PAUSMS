//Clase para la tabla de Alum_Academico
package aca.federacion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class FedMoodiesUtil{
	
	public FedMoodies mapeaRegId( Connection conn, String eventoId ) throws SQLException, IOException{
		FedMoodies fedMoodies = new FedMoodies();
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" AMIGABLE, INFLUENCIAPROACTIVA, HONESTO,  RESPONSABLE, PROACCIONMISIONERA, CONSAGRADO, ATLETICO, ACTITUDFITNESS, GENEROSO, ALTRUISTA,"+	
	 			" AMIGABLEH, INFLUENCIAPROACTIVAH, HONESTOH,  RESPONSABLEH, PROACCIONMISIONERAH, CONSAGRADOH, ATLETICOH, ACTITUDFITNESSH, GENEROSOH, ALTRUISTAH"+	
	 			" FROM ENOC.FED_MOODIES WHERE EVENTO_ID = ?"); 
	 		ps.setString(1, eventoId);			
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			
	 			fedMoodies.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedMoodies|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return fedMoodies;
 	} 	
		
	public ArrayList<FedMoodies> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FedMoodies> lisEvento= new ArrayList<FedMoodies>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+
	 			" EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" AMIGABLE, INFLUENCIAPROACTIVA, HONESTO, RESPONSABLE, PROACCIONMISIONERA, CONSAGRADO, ATLETICO, ACTITUDFITNESS, GENEROSO, ALTRUISTA,"+
	 			" AMIGABLEH, INFLUENCIAPROACTIVAH, HONESTOH, RESPONSABLEH, PROACCIONMISIONERAH, CONSAGRADOH, ATLETICOH, ACTITUDFITNESSH, GENEROSOH, ALTRUISTAH"+
	 			" FROM ENOC.FED_MOODIES"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FedMoodies evento = new FedMoodies();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedMoodiesUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	
	public ArrayList<FedMoodies> getListEventosActuales(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FedMoodies> lisEvento= new ArrayList<FedMoodies>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+
	 			" EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" AMIGABLE, INFLUENCIAPROACTIVA, HONESTO, RESPONSABLE, PROACCIONMISIONERA, CONSAGRADO, ATLETICO, ACTITUDFITNESS, GENEROSO, ALTRUISTA,"+	
	 			" AMIGABLEH, INFLUENCIAPROACTIVAH, HONESTOH, RESPONSABLEH, PROACCIONMISIONERAH, CONSAGRADOH, ATLETICOH, ACTITUDFITNESSH, GENEROSOH, ALTRUISTAH"+
	 			" FROM ENOC.FED_MOODIES  WHERE now() BETWEEN FECHA_INI AND FECHA_FIN "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FedMoodies evento = new FedMoodies();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedMoodiesUtil|getListEventosActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}

}