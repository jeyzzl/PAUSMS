package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaEstudioUtil {
	
	public boolean insertReg(Connection conn, ExaEstudio exaEstudio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_ESTUDIO(ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, PERIODO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDESTUDIO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?, ?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaEstudio.getEstudioId());
			ps.setString(2, exaEstudio.getMatricula());
			ps.setString(3, exaEstudio.getEstudios());
			ps.setString(4, exaEstudio.getInstitucion());
			ps.setString(5, exaEstudio.getPeriodo());
			ps.setString(6, exaEstudio.getFechaAct());
			ps.setString(7, exaEstudio.getEliminado());
			ps.setString(8, exaEstudio.getIdEstudio());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String estudioId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_ESTUDIO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, estudioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaEstudio mapeaRegIdEstudio( Connection conn, String estudioId) throws SQLException{
		ExaEstudio exaEstudio = new ExaEstudio();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, " +
					" PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "+
				"FROM ENOC.EXA_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,estudioId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEstudio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEstudio;
	}
	
	public ExaEstudio mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaEstudio exaEstudio = new ExaEstudio();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, " +
					" PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "+
				"FROM ENOC.EXA_ESTUDIO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEstudio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEstudio;
	}
	
	public boolean existeReg(Connection conn, String estudioId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,estudioId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(ESTUDIO_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA WHERE ELIMINADO !=1 AND MATRICULA = ?");
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|maximoRegAlumno|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(ESTUDIO_ID)+1 AS MAXIMO FROM ENOC.EXA_ESTUDIO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaEstudio> getEstudios(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaEstudio> list		= new ArrayList<ExaEstudio>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, PERIODO, FECHAACTUALIZACION," +
					" ELIMINADO, IDESTUDIO FROM ENOC.EXA_ESTUDIO" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEstudio obj = new ExaEstudio();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|getEstudios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
