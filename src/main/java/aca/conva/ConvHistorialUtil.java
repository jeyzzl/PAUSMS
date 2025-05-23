package aca.conva;

import java.sql.*;
import java.util.ArrayList;

public class ConvHistorialUtil {
	
	public boolean insertReg(Connection conn, ConvHistorial hist ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CONV_HISTORIAL"+ 
				"(CONVALIDACION_ID, FOLIO, FECHA, USUARIO, ESTADO) "+
				"VALUES( TO_NUMBER(?,'9999999'),TO_NUMBER(?,'99'),TO_DATE(?,'DD/MM/YYYY'),?,?)");
			
			ps.setString(1, hist.getConvalidacionId());
			ps.setString(2, hist.getFolio());
			ps.setString(3, hist.getFecha());
			ps.setString(4, hist.getUsuario());
			ps.setString(5, hist.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ConvHistorial hist ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_HISTORIAL "+ 
				"SET "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"USUARIO = ?, " +
				"ESTADO = ?"+
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND FOLIO = TO_NUMBER(?,'99')");
			
			ps.setString(1, hist.getFecha());
			ps.setString(2, hist.getUsuario());
			ps.setString(3, hist.getEstado());
			ps.setString(4, hist.getConvalidacionId());
			ps.setString(5, hist.getFolio());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String convalidacionId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, convalidacionId);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteAllRegs(Connection conn, String convalidacionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " );
			ps.setString(1, convalidacionId);
			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|deleteAllRegs|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public ConvHistorial mapeaRegId( Connection conn, String convalidacionId, String estado ) throws SQLException{
		
		ConvHistorial hist = new ConvHistorial();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, ESTADO "+
				"FROM ENOC.CONV_HISTORIAL WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " + 
				"AND ESTADO = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, estado);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hist.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return hist;
	}
	
	public boolean existeFolio(Connection conn, String convalidacionId, String folio) throws SQLException{		
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CONVALIDACION_ID FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, convalidacionId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeEstado(Connection conn, String convalidacionId, String estado) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CONVALIDACION_ID FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND ESTADO = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, estado);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getMaxReg(Connection conn, String convalidacionId) throws SQLException{
		String 		folio 	= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS FOLIO FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, convalidacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				folio = rs.getString("FOLIO");
			else
				folio = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorial|getMaxId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	
	public ArrayList<ConvHistorial> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<ConvHistorial> lisHistorial = new ArrayList<ConvHistorial>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, ESTADO " +					
					"FROM ENOC.CONV_HISTORIAL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvHistorial historial = new ConvHistorial();
				historial.mapeaReg(rs);
				lisHistorial.add(historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorialUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHistorial;
	}
	
	public ArrayList<ConvHistorial> getList(Connection conn, String convalidacionId, String orden ) throws SQLException{
		ArrayList<ConvHistorial> lisHistorial = new ArrayList<ConvHistorial>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, ESTADO " +					
					"FROM ENOC.CONV_HISTORIAL WHERE CONVALIDACION_ID = "+convalidacionId+" "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvHistorial historial = new ConvHistorial();
				historial.mapeaReg(rs);
				lisHistorial.add(historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvHistorialUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHistorial;
	}
}