package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiCuentaUtil {

	public ArrayList<MusiCuenta> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiCuenta> lisMusiCuenta	= new ArrayList<MusiCuenta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CUENTA_ID,CUENTA_NOMBRE, TIPO, INSTRUMENTO_ID "+
			"FROM ENOC.MUSI_CUENTA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCuenta mc = new MusiCuenta();
				mc.mapeaReg(rs);
				lisMusiCuenta.add(mc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuentaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiCuenta;
	}
	
	public ArrayList<MusiCuenta> getListCuentaFalta(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiCuenta> lisMusiCuenta	= new ArrayList<MusiCuenta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CUENTA_ID,CUENTA_NOMBRE, TIPO FROM ENOC.MUSI_CUENTA " + 
					"WHERE CUENTA_ID NOT IN " +
					"	(SELECT CUENTA_ID FROM ENOC.MUSI_PERIODO_COBRO WHERE PERIODO_ID = '"+periodoId+"')"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCuenta mc = new MusiCuenta();
				mc.mapeaReg(rs);
				lisMusiCuenta.add(mc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuentaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiCuenta;
	}	

}