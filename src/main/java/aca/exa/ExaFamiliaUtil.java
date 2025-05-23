package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaFamiliaUtil {
	
	public boolean insertReg(Connection conn, ExaFamilia exaFamilia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_FAMILIA(FAMILIA_ID, MATRICULA, NOMBRE, RELACION, FECHANACIMIENTO, FECHAACTUALIZACION, " +
				"ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?, TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ?, TO_DATE(?,'DD/MM/YYYY'), ? )");
			
			ps.setString(1, exaFamilia.getFamiliaId());
			ps.setString(2, exaFamilia.getMatricula());
			ps.setString(3, exaFamilia.getNombre());
			ps.setString(4, exaFamilia.getRelacion());
			ps.setString(5, exaFamilia.getFechaNac());
			ps.setString(6, exaFamilia.getFechaAct());
			ps.setString(7, exaFamilia.getEliminado());
			ps.setString(8, exaFamilia.getCorreo());
			ps.setString(9, exaFamilia.getFechaAniv());
			ps.setString(10, exaFamilia.getIdFamilia());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ExaFamilia exaFamilia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_FAMILIA"+ 
				" SET ID_FAMILIA = ?," +
				" MATRICULA = ?, " +
				" NOMBRE = ?," +
				" RELACION = ?," +
				" FECHANACIMIENTO = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHAACTUALIZACION = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')," +
				" ELIMINADO = TO_NUMBER(?,'9')," +
				" CORREO = ?, " +
				" FECHAANIVERSARIO = TO_DATE(?,'DD/MM/YYYY') "+
				" WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')");
			
			ps.setString(1, exaFamilia.getIdFamilia());
			ps.setString(2, exaFamilia.getMatricula());
			ps.setString(3, exaFamilia.getNombre());
			ps.setString(4, exaFamilia.getRelacion());
			ps.setString(5, exaFamilia.getFechaNac());
			ps.setString(6, exaFamilia.getFechaAct());
			ps.setString(7, exaFamilia.getEliminado());
			ps.setString(8, exaFamilia.getCorreo());
			ps.setString(9, exaFamilia.getFechaAniv());
			ps.setString(10, exaFamilia.getFamiliaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String familiaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_FAMILIA"+ 
				" SET ELIMINADO = '1' " +
				" WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, familiaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaFamilia mapeaRegIdFam( Connection conn, String idFamilia) throws SQLException{
		ExaFamilia exaFamilia = new ExaFamilia();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
					" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,idFamilia);
			rs = ps.executeQuery();
			if (rs.next()){
				exaFamilia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaFamilia;
	}
	
	public ExaFamilia mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaFamilia exaFamilia = new ExaFamilia();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
					" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaFamilia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaFamilia;
	}
	
	public boolean existeReg(Connection conn, String idFamilia) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_FAMILIA WHERE FAMILIA_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,idFamilia);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoRegAlumno(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FAMILIA_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA WHERE ELIMINADO !=1 AND MATRICULA = '"+matricula+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(FAMILIA_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaFamilia> getFamilia(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaFamilia> list		= new ArrayList<ExaFamilia>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, FECHAACTUALIZACION," +
					" ELIMINADO, CORREO, TO_CHAR(FECHAANIVERSARIO, 'DD/MM/YYYY') AS FECHAANIVERSARIO, IDFAMILIA " +
					" FROM ENOC.EXA_FAMILIA WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaFamilia obj = new ExaFamilia();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|getFamilia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}