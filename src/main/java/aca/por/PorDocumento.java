//Bean del Portafolio
package aca.por;

import java.sql.*;

public class PorDocumento {

	private String porId;
	private String porNombre;
	private String estado;
	
	public PorDocumento(){
		porId 		= "";
		porNombre	= "";
		estado		= "";
	}

	public String getPorId() {
		return porId;
	}	
	
	public void setPorId(String porId) {
		this.porId = porId;
	}

	public String getPorNombre() {
		return porNombre;
	}

	public void setPorNombre(String porNombre) {
		this.porNombre = porNombre;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.POR_DOCUMENTO( POR_ID, POR_NOMBRE,ESTADO) "+
				"VALUES( ?, ?, ?)");
			ps.setString(1, porId);
			ps.setString(2, porNombre);
			ps.setString(3, estado);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_DOCUMENTO SET POR_NOMBRE = ?, ESTADO =? WHERE POR_ID = ?");
			ps.setString(1, porNombre);
			ps.setString(2, estado);
			ps.setString(3, porId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_DOCUMENTO WHERE POR_ID = ?");
			ps.setString(1, porId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		porId 		= rs.getString("POR_ID");
		porNombre 	= rs.getString("POR_NOMBRE");
		estado 		= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT POR_ID, POR_NOMBRE, ESTADO FROM ENOC.POR_DOCUMENTO WHERE POR_ID = ?"); 
			ps.setString(1, porId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_DOCUMENTO WHERE POR_ID = ?"); 
			ps.setString(1,porId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getNombre(Connection conn, String porId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT POR_NOMBRE FROM ENOC.POR_DOCUMENTO WHERE POR_ID = '"+porId+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("POR_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getEstado(Connection conn, String porId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT ESTADO FROM ENOC.POR_DOCUMENTO WHERE POR_ID = '"+porId+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("ESTADO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumento|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

	
}