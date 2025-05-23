package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;	
import java.util.HashMap;

public class IntReporteUtil {
	
	public boolean insertReg(Connection conn, IntReporte intRep ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps 	= conn.prepareStatement("INSERT INTO ENOC.INT_REPORTE(REPORTE_ID, REPORTE_NOMBRE)"
					+ " VALUES(TO_NUMBER(?,'99'), ?)");
			ps.setString(1, intRep.getReporteId());
			ps.setString(2, intRep.getReporteNombre());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean updateReg(Connection conn, IntReporte intRep ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_REPORTE SET REPORTE_NOMBRE = ? WHERE REPORTE_ID = TO_NUMBER(?,'99')");			
			
			ps.setString(1, intRep.getReporteNombre());
			ps.setString(2, intRep.getReporteId());
			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String reporteId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, reporteId);					
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public IntReporte mapeaRegId(Connection con, String reporteId) throws SQLException{
		IntReporte intRep = new IntReporte();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')");
			 
			ps.setString(1, reporteId);			
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				intRep.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return intRep;
	}
	
	public boolean existeReg(Connection con, String reporteId, String CodigoPersonal, String CargaId, String BloqueId) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')");
			 
			ps.setString(1,reporteId);			
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public ArrayList<IntReporte> lisAll(Connection conn, String orden) throws SQLException{
		ArrayList<IntReporte> lista 	= new ArrayList<IntReporte>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT REPORTE_ID, REPORTE_NOMBRE FROM ENOC.INT_REPORTE "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				IntReporte reporte = new IntReporte();
				reporte.mapeaReg(rs);
				lista.add(reporte);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|lisAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}	
	
	
	public HashMap<String, IntReporte> mapAll(Connection conn, String orden) throws SQLException{
		
		HashMap<String, IntReporte> mapReporte		= new HashMap<String, IntReporte>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		try{
			comando = " SELECT * FROM ENOC.INT_REPORTE"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				IntReporte obj = new IntReporte();
				obj.mapeaReg(rs);
				llave = obj.getReporteId();
				mapReporte.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntReporteUtil|mapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapReporte;
	}
		
		
}