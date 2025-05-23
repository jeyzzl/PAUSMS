package aca.log;

import java.sql.*;
import java.util.ArrayList;

public class LogOperacionUtil{
	
	public boolean insertReg(Connection conn, LogOperacion logOperacion ) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LOG_OPERACION"+ 
				"(TABLA,OPERACION,IP,FECHA,USUARIO,DATOS) VALUES( ?,?,?, now(),?,?)");
			
			ps.setString(1, logOperacion.getTabla());
			ps.setString(2, logOperacion.getOperacion());
			ps.setString(3, logOperacion.getIp());
			ps.setString(4, logOperacion.getUsuario());
			ps.setString(5, logOperacion.getDatos());
			if (ps.executeUpdate()== 1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogOperacionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<LogOperacion> getListAll(Connection conn, String matricula) throws SQLException{
		
		ArrayList<LogOperacion> lis		= new ArrayList<LogOperacion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT DATOS, IP, OPERACION, TABLA, USUARIO, TO_CHAR(FECHA, 'YYYY/MM/DD') AS FECHA FROM ENOC.LOG_OPERACION WHERE TABLA = 'RES_DATOS' AND DATOS LIKE '%"+matricula+"%'"+
					  " ORDER BY FECHA ";	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				LogOperacion obj = new LogOperacion();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogOperacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<LogOperacion> getListaPorDatos(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<LogOperacion> lis		= new ArrayList<LogOperacion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT DATOS, IP, OPERACION, TABLA, USUARIO, TO_CHAR(FECHA, 'YYYY/MM/DD') AS FECHA"
					+ " FROM ENOC.LOG_OPERACION WHERE TABLA = 'RES_DATOS' AND DATOS LIKE '%"+matricula+"%' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				LogOperacion obj = new LogOperacion();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogOperacion|getListaPorDatos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}