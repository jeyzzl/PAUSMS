/*
 * Created on 09-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.bsc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IndicadoresUtil {
	private String idItem;
	private String nombre;
	private String nbInicio;
	private String nbFinal;
	private String nmInicio;
	private String nmFinal;
	private String nsInicio;
	private String nsFinal;	
	
	public ArrayList<Indicadores> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Indicadores> listor		= new ArrayList<Indicadores>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.BSC_INDICADORES "+ orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Indicadores ind = new Indicadores();
				ind.mapeaReg(rs);
				listor.add(ind);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BSC_INDICADORES("+ 
					"IDITEM,NOMBRE,NB_INICIO,NB_FINAL,NM_INICIO,NM_FINAL,NS_INICIO,NS_FINAL) VALUES(?,?,?,?,?,?,?,?)");
			ps.setString(1,idItem);
			ps.setString(2,nombre);
			ps.setString(3,nbInicio);
			ps.setString(4,nbFinal);
			ps.setString(5,nmInicio);
			ps.setString(6,nmFinal);
			ps.setString(7,nsInicio);
			ps.setString(8,nsFinal);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BSC_INDICADORES "+ 
					"SET NOMBRE = ?, NB_INICIO = ?, NB_FINAL = ?, NM_INICIO = ?, NM_FINAL = ?, NS_INICIO = ?, NS_FINAL = ? WHERE IDITEM = ?");			
			ps.setString(1,nombre);
			ps.setString(2,nbInicio);
			ps.setString(3,nbFinal);
			ps.setString(4,nmInicio);
			ps.setString(5,nmFinal);
			ps.setString(6,nsInicio);
			ps.setString(7,nsFinal);
			ps.setString(8,idItem);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BSC_INDICADORES WHERE IDITEM = ?"); 
			ps.setString(1,idItem);			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Indicadores mapeaRegId(Connection con, String idItem) throws SQLException{
		Indicadores indi = new Indicadores();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT * FROM ENOC.BSC_INDICADORES WHERE IDITEM = ? "); 
			ps.setString(1,idItem);
			rs = ps.executeQuery();
			
			if(rs.next()){
				indi.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return indi;
		
	}
	
	
	public String nextIdItem(Connection conn) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "",max="";
		try{
			comando = "SELECT LPAD(COALESCE(MAX(IDITEM),0)+1,5,'0') IDITEM FROM ENOC.BSC_INDICADORES"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				max = rs.getString(1);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|nextIdItem|: "+ex);
		}finally{
		    try { rs.close(); } catch (Exception ignore) { }
		    try { st.close(); } catch (Exception ignore) { }
		}
		return max;		
	}	
}