//Clase  para la tabla ARCH_STATUS
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchUbicacionUtil {	

	public boolean insertReg(Connection conn, ArchUbicacion ubicacion) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_UBICACION(UBICACION_ID, " + 
					"UBICACION_NOMBRE) VALUES(TO_NUMBER(?,'99'),?)");
			ps.setString(1, ubicacion.getUbicacionId());
			ps.setString(2, ubicacion.getUbicacionNombre());
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateReg(Connection conn, ArchUbicacion ubicacion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_UBICACION " + 
			"SET UBICACION_NOMBRE = ? WHERE UBICACION_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, ubicacion.getUbicacionNombre());
			ps.setString(2, ubicacion.getUbicacionId());
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean deleteReg(Connection conn, String ubicacionId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,ubicacionId);	
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchUbicacion mapeaRegId(Connection con, String ubicacionId) throws SQLException{
		
		ArchUbicacion ubicacion = new ArchUbicacion();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT UBICACION_ID,UBICACION_NOMBRE " +
					"FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,ubicacionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				ubicacion.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return ubicacion;
	}

	public boolean existeReg(Connection conn, String ubicacionId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, ubicacionId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(UBICACION_ID)+1 MAXIMO FROM ENOC.ARCH_UBICACION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	public static String getUbicacionNombre( Connection conn, String ubicacionId) throws SQLException, Exception {
		
		PreparedStatement ps		= null;
		ResultSet rs 				= null;
		String ubicacionNombre		= "";
		
		try{
			ps = conn.prepareStatement("SELECT UBICACION_NOMBRE FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,ubicacionId);
			rs = ps.executeQuery();
			if (rs.next()){
				ubicacionNombre = rs.getString("UBICACION_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacion|getubicacionNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ubicacionNombre;
	}
	
	public ArrayList<ArchUbicacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchUbicacion> lista 	= new ArrayList<ArchUbicacion>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT UBICACION_ID, UBICACION_NOMBRE FROM ENOC.ARCH_UBICACION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArchUbicacion documento = new ArchUbicacion();
				documento.mapeaReg(rs);
				lista.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}

}