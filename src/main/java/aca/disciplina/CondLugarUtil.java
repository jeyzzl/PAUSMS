// CLASE DE LA TABLA COND_LUGAR

package aca.disciplina;

import java.sql.*;
import java.util.ArrayList;

public class CondLugarUtil {
	
	public boolean insertReg(Connection conn, CondLugar lugar ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.COND_LUGAR(IDLUGAR, NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, lugar.getIdLugar());
			ps.setString(2, lugar.getNombre());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CondLugar lugar ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COND_LUGAR "+ 
				"SET NOMBRE = ? "+
				"WHERE IDLUGAR = TO_NUMBER(?,'999')");
			ps.setString(1, lugar.getNombre());
			ps.setString(2, lugar.getIdLugar());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String idLugar ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COND_LUGAR "+ 
				"WHERE IDLUGAR = TO_NUMBER(?,'999')");
			ps.setString(1, idLugar);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CondLugar mapeaRegId(Connection con, String idLugar) throws SQLException{
		CondLugar lugar 		= new CondLugar();
		PreparedStatement ps    = null;
		ResultSet rs 			= null;		
		try{
			ps = con.prepareStatement("SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR " + 
					"WHERE IDLUGAR = TO_NUMBER(?,'999') ");				
			ps.setString(1,idLugar);
			rs = ps.executeQuery();
			
			if(rs.next()){
				lugar.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lugar;
	}
	
	public boolean existeReg(Connection conn, String idLugar) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COND_LUGAR WHERE IDLUGAR = TO_NUMBER(?,'999') "); 
			ps.setString(1, idLugar);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IDLUGAR)+1 MAXIMO FROM ENOC.COND_LUGAR"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String nombreLugar(Connection conn, String idLugar) throws SQLException{
		PreparedStatement ps	= null;		
		ResultSet rs			= null;
		String nombre 			= "";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(NOMBRE,'X')NOMBRE FROM ENOC.COND_LUGAR WHERE IDLUGAR = TO_NUMBER(?,'99')"); 
			ps.setString(1,idLugar);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|nombreLugar|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CondLugar> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CondLugar> lisLugar 			= new ArrayList<CondLugar>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondLugar lugar = new CondLugar();
				lugar.mapeaReg(rs);
				lisLugar.add(lugar);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugarUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisLugar;
	}
	
	public ArrayList<CondLugar> getLista(Connection conn, String idLugar, String orden ) throws SQLException{
		
		ArrayList<CondLugar> lisLugar 			= new ArrayList<CondLugar>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR WHERE IDLUGAR= '"+idLugar+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondLugar lugar = new CondLugar();
				lugar.mapeaReg(rs);
				lisLugar.add(lugar);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugarUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisLugar;
	}
}