/**
 * 
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Elifo
 *
 */
public class CuartoUtil {
	
	public boolean insertReg(Connection conn, Cuarto cuarto ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_CUARTO(DORMITORIO_ID,CUARTO_ID,PASILLO,CUPO,ESTADO) VALUES(?,TRIM(TO_CHAR(?,'000')),?,?,?)"); 
			ps.setString(1, cuarto.getDormitorioId());
			ps.setString(2, cuarto.getCuartoId());
			ps.setString(3, cuarto.getPasillo());
			ps.setString(4, cuarto.getCupo());
			ps.setString(5, cuarto.getEstado());
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, Cuarto cuarto ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_CUARTO SET PASILLO = ?, CUPO = ?, ESTADO = ? WHERE DORMITORIO_ID = ? AND CUARTO_ID = ?");			 
			ps.setString(1, cuarto.getPasillo());
			ps.setString(2, cuarto.getCupo());
			ps.setString(3, cuarto.getEstado());
			ps.setString(4, cuarto.getDormitorioId());
			ps.setString(5, cuarto.getCuartoId());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String dormitorioId, String cuartoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ?"); 
			ps.setString(1,dormitorioId);
			ps.setString(2,cuartoId);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Cuarto mapeaRegId(Connection con, String dormitorioId, String cuartoId) throws SQLException{
		Cuarto cuarto = new Cuarto();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? "); 
			ps.setString(1,dormitorioId);
			ps.setString(2,cuartoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				cuarto.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return cuarto;
	}
	
	public static int tieneCuartos(Connection con, String dormitorioId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		int cuartos 			= 0;
		
		try{ 
			ps = con.prepareStatement("SELECT COUNT(*) AS TOTAL FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,dormitorioId);		
			rs = ps.executeQuery();			
			if(rs.next()){
				cuartos = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|tieneCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return cuartos; 
	}
	
	public static int getCupoDormi(Connection con, String dormitorioId, String estado) throws SQLException{
		Statement st	= con.createStatement();
		ResultSet rs 	= null;
		String comando 	= "";
		int cupo 		= 0;
		
		try{ 
			comando = "SELECT COALESCE(SUM(CUPO),0) AS TOTAL FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = '"+dormitorioId+"' AND ESTADO IN("+estado+")";			
			rs = st.executeQuery(comando);			
			if(rs.next()){
				cupo = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|getCupoDormi|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return cupo; 
	}
}