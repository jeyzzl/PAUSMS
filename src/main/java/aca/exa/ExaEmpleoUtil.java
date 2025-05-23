package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaEmpleoUtil {
	
	public boolean insertReg(Connection conn, ExaEmpleo exaEmpleo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_EMPLEO(EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEMPLEO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaEmpleo.getEmpleoId());
			ps.setString(2, exaEmpleo.getMatricula());
			ps.setString(3, exaEmpleo.getEmpresa());
			ps.setString(4, exaEmpleo.getPeriodo());
			ps.setString(5, exaEmpleo.getFechaAct());
			ps.setString(6, exaEmpleo.getEliminado());
			ps.setString(7, exaEmpleo.getIdEmpleo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String empleoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_EMPLEO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE EMPLEO_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, empleoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaEmpleo mapeaRegIdEstudio( Connection conn, String empleoId) throws SQLException{
		ExaEmpleo exaEmpleo = new ExaEmpleo();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO "+
				"FROM ENOC.EXA_EMPLEO WHERE EMPLEO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,empleoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEmpleo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEmpleo;
	}
	
	public ExaEmpleo mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaEmpleo exaEmpleo = new ExaEmpleo();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO "+
				"FROM ENOC.EXA_EMPLEO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEmpleo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEmpleo;
	}
	
	public boolean existeReg(Connection conn, String empleoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_EMPLEO WHERE EMPLEO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,empleoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAlumno(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_EMPLEO WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EMPLEO_ID)+1 AS MAXIMO FROM ENOC.EXA_EMPLEO WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EMPLEO_ID)+1 AS MAXIMO FROM ENOC.EXA_EMPLEO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaEmpleo> getEmpleos(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaEmpleo> list		= new ArrayList<ExaEmpleo>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO," +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO FROM ENOC.EXA_EMPLEO" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEmpleo obj = new ExaEmpleo();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|getEmpleos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
