/**
 * 
 */
package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author ifo
 *
 */
public class CargaBloqueUtil {
	
	public boolean insertReg(Connection conn, CargaBloque bloque ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_BLOQUE"+ 
				"(CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, F_INICIO, F_CIERRE, F_FINAL ) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY') )");	
					
			ps.setString(1, bloque.getCargaId());
			ps.setString(2, bloque.getBloqueId());
			ps.setString(3, bloque.getNombreBloque());
			ps.setString(4, bloque.getFInicio());						
			ps.setString(5, bloque.getFCierre());
			ps.setString(6, bloque.getFFinal());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaBloque bloque ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_BLOQUE "+ 
				"SET NOMBRE_BLOQUE = ?, "+
				"F_INICIO = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_CIERRE = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_FINAL  = TO_DATE(?,'DD/MM/YYYY') "+								
				"WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')");			
			ps.setString(1, bloque.getNombreBloque());
			ps.setString(2, bloque.getFInicio());						
			ps.setString(3, bloque.getFCierre());
			ps.setString(4, bloque.getFFinal());
			ps.setString(5, bloque.getCargaId());
			ps.setString(6, bloque.getBloqueId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cargaId, String bloqueId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cargaId);
			ps.setString(2, bloqueId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public ArrayList<CargaBloque> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ISMATRICULA " +
			"FROM ENOC.CARGA_BLOQUE "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaBloque cb = new CargaBloque();
				cb.mapeaReg(rs);
				lisBloque.add(cb);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloqueUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBloque;
	}
	
	public ArrayList<CargaBloque> getLista(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE "+
				"FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaBloque bloque = new CargaBloque();
				bloque.mapeaReg(rs);
				lisBloque.add(bloque);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.BloqueUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBloque;
	}
	
	public ArrayList<CargaBloque> getListaAlumno(Connection conn, String cargaId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"  
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"  
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"  
					+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE"
					+ " FROM CARGA_BLOQUE"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CARGA_ID||BLOQUE_ID IN (SELECT CARGA_ID||BLOQUE_ID FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaBloque bloque = new CargaBloque();
				bloque.mapeaReg(rs);
				lisBloque.add(bloque);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.BloqueUtil|getListaAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBloque;
	}
	
	public ArrayList<CargaBloque> getListaActivo(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
				+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE"
				+ " FROM ENOC.CARGA_BLOQUE"
				+ " WHERE CARGA_ID = '"+cargaId+"'"
				+ " AND F_INICIO <= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
				+ " AND F_CIERRE >= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')" +orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaBloque bloque = new CargaBloque();
				bloque.mapeaReg(rs);
				lisBloque.add(bloque);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.BloqueUtil|getListaActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBloque;
	}
	
	public ArrayList<CargaBloque> getListBloqueCarga(Connection conn,String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ISMATRICULA " +
					" FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = '"+cargaId+"'" +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaBloque cb = new CargaBloque();
				cb.mapeaReg(rs);
				lisBloque.add(cb);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloqueUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBloque;
	}
	
	public String getNombre(Connection conn, String cargaId, String bloqueId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String total			= "0";
		
		try{
			comando = "SELECT NOMBRE_BLOQUE FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = '"+bloqueId+"' ";

			rs = st.executeQuery(comando);
			if (rs.next()){	
				total = rs.getString("NOMBRE_BLOQUE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloqueUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
}