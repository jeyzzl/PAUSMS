package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaEnlineaUtil {	
	
	public boolean insertReg(Connection conn, CargaEnlinea linea ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_ENLINEA"+ 
				"(CARGA_ID, NOMBRE, DESCRIPCION, F_INICIO, F_FINAL, ESTADO ) "+
				"VALUES( ?, "+
				"?, "+
				"?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"? )");	
					
			ps.setString(1, linea.getCargaId());
			ps.setString(2, linea.getNombre());
			ps.setString(3, linea.getDescripcion());
			ps.setString(4, linea.getFInicio());						
			ps.setString(5, linea.getFFinal());
			ps.setString(6, linea.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.cargaEnlinea|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaEnlinea linea ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_ENLINEA "+ 
				"SET NOMBRE = ?, DESCRIPCION = ? ,"+
				"F_INICIO = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_FINAL = TO_DATE(?,'DD/MM/YYYY'), "+
				"ESTADO=? "+								
				"WHERE CARGA_ID = ? ");
			
			ps.setString(2, linea.getNombre());
			ps.setString(3, linea.getDescripcion());
			ps.setString(4, linea.getFInicio());						
			ps.setString(5, linea.getFFinal());
			ps.setString(6, linea.getEstado());
			ps.setString(1, linea.getCargaId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlinea|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_ENLINEA "+ 
				"WHERE CARGA_ID = ? ");
			ps.setString(1, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlinea|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	

	public CargaEnlinea mapeaRegId( Connection conn, String cargaId) throws SQLException{
		
		CargaEnlinea linea = new CargaEnlinea();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CARGA_ID, NOMBRE, DESCRIPCION, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"ESTADO "+						
				"FROM ENOC.CARGA_ENLINEA WHERE CARGA_ID = ? ");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				linea.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlinea|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return linea;
	}
	
	public boolean existeReg(Connection conn, String cargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_ENLINEA "+ 
				"WHERE CARGA_ID = ? ");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlinea|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<CargaEnlinea> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaEnlinea> lis	= new ArrayList<CargaEnlinea>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE, DESCRIPCION, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" ESTADO " +
					" FROM ENOC.CARGA_ENLINEA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaEnlinea c = new CargaEnlinea();
				c.mapeaReg(rs);
				lis.add(c);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlineaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<CargaEnlinea> getListActivas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaEnlinea> lis		= new ArrayList<CargaEnlinea>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = 	" SELECT CARGA_ID, NOMBRE, DESCRIPCION, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
						" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
						" ESTADO " +
						" FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaEnlinea c = new CargaEnlinea();
				c.mapeaReg(rs);
				lis.add(c);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaEnlineaUtil|getListActivas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}