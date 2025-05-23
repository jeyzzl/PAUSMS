package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalAutoUtil {    
	
	public SalAuto mapeaRegId(Connection con, String salidaId, String folio) throws SQLException{
		
		SalAuto auto = new SalAuto();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = con.prepareStatement("SELECT SALIDA_ID, TIPO, POLIZA, TELEFONO, IMAGEN"
					+ " FROM ENOC.SAL_CONSEJERO"
					+ " WHERE SALIDA_ID = ?" );							
			ps.setString(1, salidaId);			
			rs = ps.executeQuery();
		
			if(rs.next()){		
				auto.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalAuto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return auto;
	}
	
	public boolean existeReg(Connection conn, String salidaId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT SALIDA_ID FROM ENOC.SAL_AUTO WHERE SALIDA_ID = TO_NUMBER(?, '99999')");			
			ps.setString(1, salidaId);	
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalAuto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public ArrayList<SalAuto> lisAutos(Connection conn, String salidaId, String orden ) throws SQLException{
		ArrayList<SalAuto> lisAutos 	= new ArrayList<SalAuto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, TIPO, POLIZA, TELEFONO FROM ENOC.SAL_AUTO WHERE SALIDA_ID ='"+salidaId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalAuto auto = new SalAuto();				
				auto.mapeaRegCorto(rs);
				lisAutos.add(auto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalAuto|lisAutos|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return lisAutos;
	}
}