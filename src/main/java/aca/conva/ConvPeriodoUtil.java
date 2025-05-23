// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConvPeriodoUtil {
	
	/* Inserta un nuevo registro en la tabla */
	public boolean insertReg(Connection conn, ConvPeriodo periodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CONV_PERIODO(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN)"
					+ " VALUES(TO_NUMBER(?,'999'),?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'))");
			
			ps.setString(1, periodo.getPeriodoId());
			ps.setString(2, periodo.getPeriodoNombre());
			ps.setString(3, periodo.getFechaIni());
			ps.setString(4, periodo.getFechaFin());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ConvPeriodo periodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_PERIODO"
					+ " SET PERIODO_NOMBRE = ?,"
					+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY')"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')");
						
			ps.setString(1, periodo.getPeriodoNombre());
			ps.setString(2, periodo.getFechaIni());
			ps.setString(3, periodo.getFechaFin());
			ps.setString(4, periodo.getPeriodoId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, periodoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public ConvPeriodo mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		
		ConvPeriodo periodo = new ConvPeriodo();		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN,"
					+ " CARRERA"
					+ " FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, periodoId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				periodo.mapeaReg(rs);
			}			
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
		return periodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, periodoId);			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public String maxReg(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String maximo 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(PERIODO_ID)+1,1) AS MAXIMO FROM ENOC.CONV_PERIODO");
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getPeriodoNombre(Connection conn, String periodoId) throws SQLException{
				
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		String nombre 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')");			
 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("PERIODO_NOMBRE");				
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
	
	public static String getPeriodoActivo(Connection conn, String fecha) throws SQLException{
		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		String periodo 			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.CONV_PERIODO"
					+ " WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"); 
			ps.setString(1, fecha);
			
			rs = ps.executeQuery();
			if (rs.next()){
				periodo = rs.getString("PERIODO_ID");				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodo|getPeriodoActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public ArrayList<ConvPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvPeriodo> lista		= new ArrayList<ConvPeriodo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, CARRERA"
					+ " FROM ENOC.CONV_PERIODO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvPeriodo objeto = new ConvPeriodo();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}		
	
	public String carrerasPeriodo(Connection conn, String periodoId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		String carreras			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')");			
 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				carreras = rs.getString("CARRERA");				
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|carrerasPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return carreras;
	}
	
	public HashMap<String,String> getMapCarreras(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,String> map 	= new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CARRERA FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER("+periodoId+",'999')";
			
			rs = st.executeQuery(comando);
			if(rs.next()){	
				String[]carreras = rs.getString("CARRERA").split("-");
				for(String carrera : carreras){
					llave = carrera;
					map.put(llave, carrera);
				}	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|getMapCarreras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public boolean insertCarreras(Connection conn, String periodoId, String carreras) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_PERIODO SET CARRERA = ?"
					+ "WHERE PERIODO_ID = ?");
			ps.setString(1, carreras);
			ps.setString(2, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|insertCarrera|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteCarreras(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_PERIODO SET CARRERA = '-' WHERE PERIODO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, periodoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|deleteCarreras|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String carreraHabilitada(Connection conn, String carrera) throws SQLException{
		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		String periodo 			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CONV_PERIODO WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN AND INSTR(CARRERA, ? ) > 0"); 
			ps.setString(1, carrera);
			
			rs = ps.executeQuery();
			if (rs.next()){
				periodo = rs.getString("PERIODO_ID");				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.convPeriodoUtil|carreraHabilitada|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return periodo;
	}
	
	
}