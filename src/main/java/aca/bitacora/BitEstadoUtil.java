package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BitEstadoUtil {
	
	public boolean insertReg(Connection conn, BitEstado estado) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BIT_ESTADO"+ 
				"(ESTADO, ESTADO_NOMBRE)"+
				" VALUES(TO_NUMBER(?, '99'), ?)");
					
			ps.setString(1,	estado.getEstado());
			ps.setString(2, estado.getEstadoNombre());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BitEstado estado) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_ESTADO"+ 
				" SET ESTADO_NOMBRE = ? " +		
				" WHERE ESTADO = TO_NUMBER(?, '99')");
			
			ps.setString(1, estado.getEstadoNombre());
			ps.setString(2, estado.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String tramiteId) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_ESTADO"+ 
				" WHERE ESTADO = TO_NUMBER(?, '99')");
			
			ps.setString(1, tramiteId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BitEstado mapeaRegId( Connection conn, String estadoId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		BitEstado estado 		= new BitEstado();
		try{
			ps = conn.prepareStatement(" SELECT ESTADO, ESTADO_NOMBRE "
									 + " FROM ENOC.BIT_ESTADO WHERE ESTADO = "+estadoId); 
			rs = ps.executeQuery();
			if (rs.next()){
				estado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return estado;
	}
	
	public static String getEstadoNombre( Connection conn, String estadoId) throws SQLException{
		
		PreparedStatement ps 	= null; 
		ResultSet rs 			= null;
		String nombre			= "-";
		try{
			ps = conn.prepareStatement(" SELECT ESTADO_NOMBRE FROM ENOC.BIT_ESTADO WHERE ESTADO = TO_NUMBER(?,'99')");
			ps.setString(1, estadoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("ESTADO_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|getEstadoNombre|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public ArrayList<BitEstado> lisEstados(Connection conn, String orden) throws SQLException{
		ArrayList<BitEstado> lista 	= new ArrayList<BitEstado>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT ESTADO, ESTADO_NOMBRE "
					+ " FROM BIT_ESTADO "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitEstado estado = new BitEstado();
				estado.mapeaReg(rs);
				lista.add(estado);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|LisEstados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<BitEstado> lisEstados(Connection conn, String estados, String orden) throws SQLException{
		ArrayList<BitEstado> lista 	= new ArrayList<BitEstado>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT ESTADO, ESTADO_NOMBRE FROM BIT_ESTADO WHERE ESTADO IN ("+estados+") "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitEstado estado = new BitEstado();
				estado.mapeaReg(rs);
				lista.add(estado);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|lisEstados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(ESTADO)+1,1) AS MAXIMO FROM ENOC.BIT_ESTADO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.BitEstadoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public HashMap<String, String> mapEstados(Connection conn) throws SQLException{
		HashMap<String, String> mapEstado 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT ESTADO, ESTADO_NOMBRE FROM ENOC.BIT_ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEstado.put(rs.getString("ESTADO"), rs.getString("ESTADO_NOMBRE") );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|mapEstados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapEstado;
	}

}
