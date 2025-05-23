package aca.envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aca.envio.EnvioServicio;

public class EnvioServicioUtil {
	

	public boolean insertReg(Connection conn, EnvioServicio envioServ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" ENVIO_SERVICIO(SERVICIO_ID, SERVICIO_NOMBRE, TELEFONOS )" +
				" VALUES( TO_NUMBER(?,'99'), ?, ?)");
			
			ps.setString(1, envioServ.getServicioId());
			ps.setString(2, envioServ.getServicioNombre());
			ps.setString(3, envioServ.getTelefonos());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioServicio|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EnvioServicio envioServ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ENVIO_SERVICIO" + 
				" SET SERVICIO_NOMBRE = ?," +
				" TELEFONOS = ?," +
				" WHERE SERVICIO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, envioServ.getServicioNombre());
			ps.setString(2, envioServ.getTelefonos());
			ps.setString(3, envioServ.getServicioId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioServicio|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String servicioId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ENVIO_SERVICIO"+ 
				" WHERE SERVICIO_ID = ?");
			
			ps.setString(1, servicioId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioServicio|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EnvioServicio mapeaRegId(Connection con, String idEmpleado, String servicioId) throws SQLException{
		EnvioServicio envioServ = new EnvioServicio();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT SERVICIO_ID, SERVICIO_NOMBRE, TELEFONOS FROM ENOC.ENVIO_SERVICIO " + 
					" WHERE SERVICIO_ID = ?");
			
			ps.setString(1, servicioId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				envioServ.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EncioServicio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return envioServ;
	}
	
	public boolean existeReg(Connection conn, String servicioId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ENVIO_SERVICIO" + 
					" WHERE SERVICIO_ID = ?");
		
		ps.setString(1, servicioId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EncioServicio|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(SERVICIO_ID)+1 MAXIMO FROM ENOC.ENVIO_SERVICIO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EncioServicio|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<EnvioServicio> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<EnvioServicio> listSer		= new ArrayList<EnvioServicio>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT SERVICIO_ID, SERVICIO_NOMBRE, TELEFONOS" +					
				" FROM ENOC.ENVIO_SERVICIO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EnvioServicio ser = new EnvioServicio();
				ser.mapeaReg(rs);
				listSer.add(ser);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioServicioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listSer;
	}

}