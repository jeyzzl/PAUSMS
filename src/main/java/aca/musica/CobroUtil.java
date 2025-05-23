package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CobroUtil {

	public ArrayList<MusiPeriodoCobro> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<MusiPeriodoCobro> lisMusiCobro	= new ArrayList<MusiPeriodoCobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO_ID, CUENTA_ID, CANTIDAD, COMENTARIO, CLASES "+
			"FROM ENOC.MUSI_PERIODO_COBRO "+ orden;
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiPeriodoCobro mpc = new MusiPeriodoCobro();
				mpc.mapeaReg(rs);
				lisMusiCobro.add(mpc);
			}
			
		}catch(Exception ex){
				System.out.println("Error - aca.musica.CobroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisMusiCobro;
	}
	
	public ArrayList<MusiPeriodoCobro> getListPeriodo(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiPeriodoCobro> lisMusiCobro	= new ArrayList<MusiPeriodoCobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO_ID, CUENTA_ID, CANTIDAD, COMENTARIO, CLASES "+
			"FROM ENOC.MUSI_PERIODO_COBRO WHERE PERIODO_ID = '"+periodoId+"' "+ orden; 
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiPeriodoCobro mpc = new MusiPeriodoCobro();
				mpc.mapeaReg(rs);
				lisMusiCobro.add(mpc);
			}
			
		}catch(Exception ex){
				System.out.println("Error - aca.musica.CobroUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisMusiCobro;
	}
	
	public ArrayList<MusiPeriodoCobro> getListDisponibles(Connection conn, String codigoId, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiPeriodoCobro> lisMusiCobro	= new ArrayList<MusiPeriodoCobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO_ID, CUENTA_ID, CANTIDAD, COMENTARIO, CLASES FROM ENOC.MUSI_PERIODO_COBRO" + 
			" WHERE PERIODO_ID = '"+periodoId+"' " +
			" AND CUENTA_ID NOT IN " +
			"	(SELECT CUENTA_ID FROM ENOC.MUSI_CALCULO_DET " + 
			"	WHERE PERIODO_ID = '"+periodoId+"'" +
			" 	AND CODIGO_ID = '"+codigoId+"') "+ orden;
				
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiPeriodoCobro mpc = new MusiPeriodoCobro();
				mpc.mapeaReg(rs);
				lisMusiCobro.add(mpc);
			}
			
		}catch(Exception ex){
				System.out.println("Error - aca.musica.CobroUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisMusiCobro;
	}
	
	
	
}