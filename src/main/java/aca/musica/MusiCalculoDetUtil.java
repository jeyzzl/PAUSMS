package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiCalculoDetUtil {

	public ArrayList<MusiCalculoDet> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiCalculoDet> lisCalculoDet	= new ArrayList<MusiCalculoDet>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, PERIODO_ID, CUENTA_ID,CANTIDAD, FRECUENCIA, MAESTRO_ID, INSTRUMENTO_ID, BECA FROM ENOC.MUSI_CALCULO_DET "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCalculoDet mcal = new MusiCalculoDet();
				mcal.mapeaReg(rs);
				lisCalculoDet.add(mcal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloDetUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCalculoDet;
	}
	
	public ArrayList<MusiCalculoDet> getListAsignados(Connection conn, String codigoId, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiCalculoDet> lisCalculoDet	= new ArrayList<MusiCalculoDet>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, PERIODO_ID, CUENTA_ID, CANTIDAD, FRECUENCIA, MAESTRO_ID, INSTRUMENTO_ID, BECA FROM ENOC.MUSI_CALCULO_DET" + 
					" WHERE CODIGO_ID = '"+codigoId+"' " +
					" AND PERIODO_ID = '"+periodoId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCalculoDet mcal = new MusiCalculoDet();
				mcal.mapeaReg(rs);
				lisCalculoDet.add(mcal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloDetUtil|getListAsignados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCalculoDet;
	}
	
}