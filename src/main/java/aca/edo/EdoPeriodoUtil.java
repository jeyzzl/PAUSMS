// Clase Util para la tabla de Carga
package aca.edo;

import java.sql.*;
import java.util.ArrayList;

public class EdoPeriodoUtil{
		
	public boolean insertReg(Connection conn, EdoPeriodo edoPeri) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO_PERIODO(PERIODO_ID, PERIODO_NOMBRE, F_INICIO, F_FINAL, ESTADO)" +
				" VALUES(?,?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'),?)");
			
			ps.setString(1, edoPeri.getPeriodoId());
			ps.setString(2, edoPeri.getPeriodoNombre());			
			ps.setString(3, edoPeri.getFInicio());
			ps.setString(4, edoPeri.getFFinal());
			ps.setString(5, edoPeri.getEstado());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EdoPeriodo edoPeri) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_PERIODO" + 
				" SET PERIODO_NOMBRE = ?," +
				" F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" F_FINAL = TO_DATE(?, 'DD/MM/YYYY')," +
				" ESTADO = ? " +
				" WHERE PERIODO_ID = ? ");
			
			ps.setString(1, edoPeri.getPeriodoNombre());
			ps.setString(2, edoPeri.getFInicio());
			ps.setString(3, edoPeri.getFFinal());
			ps.setString(4, edoPeri.getEstado());
			ps.setString(5, edoPeri.getPeriodoId());		
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String periodoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_PERIODO"+ 
				" WHERE PERIODO_ID = ?");
			
			ps.setString(1, periodoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoPeriodo mapeaRegId(Connection con, String actividadId) throws SQLException{
		EdoPeriodo edoPeri = new EdoPeriodo();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT " +
					" PERIODO_ID, PERIODO_NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, " +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, " +
					" ESTADO " +
					" FROM ENOC.EDO_PERIODO" + 
					" WHERE PERIODO_ID = ?");
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				edoPeri.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return edoPeri;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_PERIODO" + 
					" WHERE PERIODO_ID = ?");
			
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}	
	
	public static String getPeriodoNombre(Connection conn, String periodoId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "";
		
		try{
			comando = "SELECT PERIODO_NOMBRE FROM ENOC.EDO_PERIODO WHERE PERIODO_ID = '"+periodoId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next())				
				nombre = rs.getString("PERIODO_NOMBRE");
			else
				nombre = "null";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getActual(Connection conn, String fecha ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String periodoId	= "";
		
		try{
			comando = "SELECT PERIODO_ID FROM ENOC.EDO_PERIODO "+ 
				"WHERE TO_DATE('"+fecha+"','DD-MM-YY') BETWEEN F_INICIO AND F_FINAL ";
			rs = st.executeQuery(comando);
			if (rs.next())				
				periodoId = rs.getString("PERIODO_ID");
			else
				periodoId = "0000";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|getAcual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return periodoId;
	}
	
	public ArrayList<EdoPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<EdoPeriodo> lisEdoPeriodo	= new ArrayList<EdoPeriodo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, " +		
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"ESTADO "+
					"FROM ENOC.EDO_PERIODO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EdoPeriodo carga = new EdoPeriodo();
				carga.mapeaReg(rs);
				lisEdoPeriodo.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdoPeriodo;
	}
	
	public ArrayList<EdoPeriodo> getListActivos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<EdoPeriodo> lisEdoPeriodo	= new ArrayList<EdoPeriodo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" ESTADO"+
					" FROM ENOC.EDO_PERIODO " + 
					" WHERE ESTADO = 'A' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EdoPeriodo carga = new EdoPeriodo();
				carga.mapeaReg(rs);
				lisEdoPeriodo.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdoPeriodo;
	}

}