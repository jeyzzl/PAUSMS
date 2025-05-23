package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AfeTipoBecaUtil {
	
	public AfeTipoBeca mapeaRegId(Connection con, String id) throws SQLException{
		AfeTipoBeca afeTB = new AfeTipoBeca();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID,DESCRIPCION, DIEZMA, PORCENTAJE, "+
					"ES_DEL_ALUMNO,SOLO_POSTGRADO, VERSION, ID_CCOSTO, ID_CTAMAYOR, ID_EJERCICIO, TIPO_CUENTA, TOPE "+
					"FROM NOE.AFE_TIPOBECA WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				afeTB.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return afeTB;
	}
	
	public static String getImporte(Connection con, String ejercicioId, String idCtaMayor) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		String importe = "";
		try{
			ps = con.prepareStatement("SELECT SUM(IMPORTE) AS IMPORTE FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = '"+ejercicioId+"' AND ID_CTAMAYOR = '"+idCtaMayor+"' ");
			rs = ps.executeQuery();
			
			if(rs.next()){
				importe = rs.getString("IMPORTE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getImporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return importe;
		
	}
	
	public static String getUsadoBecGeneral(Connection con, String ejercicioId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		String importe = "";
		try{
			ps = con.prepareStatement("SELECT SUM(IMPORTE) AS IMPORTE FROM MATEO.fes_cc_afe WHERE EJERCICIO_ID = '"+ejercicioId+"' ");
			rs = ps.executeQuery();
			
			if(rs.next()){
				importe = rs.getString("IMPORTE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getUsadoBecGeneral|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return importe;
		
	}
	
	public static String getUsadoMaestria(Connection con, String ejercicioId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		String total = "";
		try{
			ps = con.prepareStatement("SELECT SUM(MAXIMO_HORAS*PRECIO_HORA) AS TOTAL " +
					" FROM MATEO.FES_CC_AFE " +
					" WHERE TIPOBECA_ID IS NULL AND EJERCICIO_ID = '"+ejercicioId+"' AND PLAZA_ID IN (SELECT ID FROM NOE.AFE_PLAZA WHERE PUESTO_ID = 11)");
			rs = ps.executeQuery();
			
			if(rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getUsadoMaestria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
		
	}
	
	public ArrayList<AfeTipoBeca> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfeTipoBeca> list 	= new ArrayList<AfeTipoBeca>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{			
			comando = "SELECT * FROM NOE.AFE_TIPOBECA " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfeTipoBeca obj = new AfeTipoBeca();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}	
	
	
	public HashMap<String,String> getUsadoBecas(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT SUM(total_beca_adic) as total, tipobeca_id FROM MATEO.fes_cc_afe WHERE EJERCICIO_ID = '"+ejercicioId+"' GROUP BY tipobeca_id "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("tipobeca_id"), rs.getString("total"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getUsadoBecas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> nombreCCosto(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT ID_CCOSTO, NOMBRE FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+ejercicioId+"' "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("ID_CCOSTO"), rs.getString("NOMBRE"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeTipoBecaUtil|getUsadoBecas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}
