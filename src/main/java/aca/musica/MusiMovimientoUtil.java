package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiMovimientoUtil {

	public ArrayList<MusiMovimiento> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiMovimiento> lisMusiMov	= new ArrayList<MusiMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CUENTA_ID, DESCRIPCION, IMPORTE, TIPO, REFERENCIA "+
					"FROM ENOC.MUSI_MOVIMIENTO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiMovimiento mov = new MusiMovimiento();
				mov.mapeaReg(rs);
				lisMusiMov.add(mov);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMovimientoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiMov;
	}
	
	public ArrayList<MusiMovimiento> getMovimientosAlumno(Connection conn, String codigoId, String orden) throws SQLException{
		
		ArrayList<MusiMovimiento> lisMusiMov	= new ArrayList<MusiMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CUENTA_ID, DESCRIPCION, IMPORTE, TIPO, REFERENCIA "+
					"FROM ENOC.MUSI_MOVIMIENTO " + 
					"WHERE CODIGO_ID = '"+codigoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiMovimiento mov = new MusiMovimiento();
				mov.mapeaReg(rs);
				lisMusiMov.add(mov);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMovimientoUtil|getMovimientosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiMov;
	}
	
	public ArrayList<MusiMovimiento> getListMovAlumYear(Connection conn, String codigoId, String year, String orden) throws SQLException{
		
		ArrayList<MusiMovimiento> lisMusiMov	= new ArrayList<MusiMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" CUENTA_ID, DESCRIPCION, IMPORTE, TIPO, REFERENCIA"+
					" FROM ENOC.MUSI_MOVIMIENTO" + 
					" WHERE CODIGO_ID = '"+codigoId+"' " +
					" AND TO_CHAR(FECHA,'YYYY') = '"+year+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiMovimiento mov = new MusiMovimiento();
				mov.mapeaReg(rs);
				lisMusiMov.add(mov);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMovimientoUtil|getListMovAlumYear|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiMov;
	}
	
	public static double getSaldoAnterior( Connection conn, String codigoId, String year ) throws SQLException{	
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		double saldoAnt		= 0;
		
		try{
			comando = "SELECT SUM(CASE TIPO WHEN 'C' THEN IMPORTE*1 ELSE IMPORTE*-1 END) AS SALDO"+
					" FROM ENOC.MUSI_MOVIMIENTO " + 
					" WHERE CODIGO_ID = '"+codigoId+"' " +
					" AND TO_NUMBER(TO_CHAR(FECHA,'YYYY'),'9999') < TO_NUMBER('"+year+"','9999')";
			rs = st.executeQuery(comando);
			if (rs.next()){				
				saldoAnt = rs.getDouble("SALDO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMovimientoUtil|getMovimientosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return saldoAnt;
	}
	
}