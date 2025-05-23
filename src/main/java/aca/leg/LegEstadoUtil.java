package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegEstadoUtil {
	
	
	public boolean insertReg(Connection conn, LegEstado legEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO"+
				" LEG_ESTADO(ESTADO_ID, ESTADO_NOMBRE)"+
				" VALUES( TO_NUMBER(?,'99'), ?");
			ps.setString(1, legEstado.getEstadoId());
			ps.setString(2, legEstado.getEstadoNombre());
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegEstado legEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_ESTADO"+ 
				" SET ESTADO_NOMBRE=?" +
				" WHERE ESTADO_ID= TO_NUMBER(?,'99')");
			ps.setString(1, legEstado.getEstadoNombre());
			ps.setString(2, legEstado.getEstadoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, LegEstado legEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_ESTADO"+ 
				"WHERE ESTADO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, legEstado.getEstadoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public LegEstado mapeaRegId(Connection conn, String estadoId) throws SQLException{
		LegEstado legEstado = new LegEstado();
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		try{
			ps	= conn.prepareStatement("SELECT ESTADO_ID, ESTADO_NOMBRE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, estadoId);
			rs	=	ps.executeQuery();
			if(rs.next()){
				legEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return legEstado;
	}
	
	public boolean existeReg(Connection conn, String estadoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(MAX(ESTADO_ID)+1), '1') MAXIMO FROM ENOC.LEG_ESTADO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getEstadoNombre(Connection conn, String estadoId) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nombre 			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO_NOMBRE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("ESTADO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|getEstadoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<LegEstado> getListAll(Connection conn, String orden) throws SQLException{		
		ArrayList<LegEstado> lisEstado	= new ArrayList<LegEstado>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando="SELECT ESTADO_ID, ESTADO_NOMBRE FROM ENOC.LEG_ESTADO "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegEstado estado = new LegEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegEstadoUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisEstado;
	}
	
}