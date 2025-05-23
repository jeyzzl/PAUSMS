// Clase para la tabla de CandTipo
package aca.candado;

import java.sql.*;
import java.util.ArrayList; 

public class CandadoUtil{	
	
	public boolean insertReg(Connection conn, Candado cand ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CANDADO(CANDADO_ID, NOMBRE_CANDADO) VALUES(?,?)"); 
			ps.setString(1, cand.getCandadoId());
			ps.setString(2, cand.getNombreCandado());		
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, Candado cand ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CANDADO SET NOMBRE_CANDADO = ? WHERE CANDADO_ID = ?");			 
			ps.setString(1, cand.getNombreCandado());			
			ps.setString(2, cand.getCandadoId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String candadoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CANDADO WHERE CANDADO_ID = ?"); 
			ps.setString(1, candadoId);		
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String tipoId ) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(CANDADO_ID),0)+1 " +
					"AS MAXIMO FROM ENOC.CANDADO " + 
					"WHERE SUBSTR(CANDADO_ID, 1, 2) = '"+tipoId+"' ");
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public Candado mapeaRegId(Connection con, String candadoId) throws SQLException{
		Candado candado = new Candado();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CANDADO_ID, NOMBRE_CANDADO "+				
					"FROM ENOC.CANDADO WHERE CANDADO_ID = ? "); 
			ps.setString(1,candadoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				candado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return candado;
	}
	
	public boolean existeReg(Connection conn, String candadoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CANDADO WHERE CANDADO_ID = ? "); 
			ps.setString(1, candadoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreCandado(Connection conn, String candadoId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CANDADO FROM ENOC.CANDADO WHERE CANDADO_ID = ? "); 
			ps.setString(1, candadoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				nombre = rs.getString("NOMBRE_CANDADO");
			}else{
				nombre = "x";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.Candado|getNombreCandado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public ArrayList<Candado> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Candado> lisCandado 	= new ArrayList<Candado>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT CANDADO_ID, NOMBRE_CANDADO FROM ENOC.CANDADO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Candado candado = new Candado();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCandado;
	}
	
	public ArrayList<Candado> getLista(Connection conn, String tipoId, String orden ) throws SQLException{
		
		ArrayList<Candado> lisCandado 	= new ArrayList<Candado>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT CANDADO_ID, NOMBRE_CANDADO "+
				"FROM ENOC.CANDADO WHERE SUBSTR(CANDADO_ID,1,2) = '"+tipoId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Candado candado = new Candado();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCandado;
	}	
	
}