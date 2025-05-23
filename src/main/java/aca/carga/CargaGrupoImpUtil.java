// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;

public class CargaGrupoImpUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoImp imp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_IMP"
					+ " (GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID, FECHA,GRUPO)"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?,"
					+ " TO_DATE(?,'DD/MM/YYYY'), ?)");
			
			ps.setString(1, imp.getGrupoId());
			ps.setString(2, imp.getCursoId());
			ps.setString(3, imp.getMaestro());
			ps.setString(4, imp.getAlumnos());
			ps.setString(5, imp.getCursoCargaId());
			ps.setString(6, imp.getFecha());
			ps.setString(7, imp.getGrupo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoImp imp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_IMP"
					+ " SET CURSO_ID = ?,"
					+ " MAESTRO = ?,"
					+ " ALUMNOS = ?,"
					+ " CURSO_CARGA_ID = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " GRUPO = ? "
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')");
			
			ps.setString(2, imp.getCursoId());
			ps.setString(3, imp.getMaestro());
			ps.setString(4, imp.getAlumnos());
			ps.setString(5, imp.getCursoCargaId());
			ps.setString(6, imp.getFecha());
			ps.setString(7, imp.getGrupo());
			ps.setString(1, imp.getGrupoId());	
			
			if (ps.executeUpdate()== 1){
				ok = true;			
			}else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoImp mapeaRegId( Connection conn, String grupoId ) throws SQLException{
		
		CargaGrupoImp imp = new CargaGrupoImp();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"
					+ " GRUPO_ID,"
					+ " CURSO_ID,"
					+ " MAESTRO,"
					+ " ALUMNOS,"
					+ " CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " GRUPO "
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, grupoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				imp.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return imp;
	}
	
	public boolean existeReg(Connection conn, String grupoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_IMP "+ 
				"WHERE GRUPO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, grupoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getMaestro(Connection conn, String grupoId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String maestro 			= "0000000";
		
		
		try{
			ps = conn.prepareStatement("SELECT MAESTRO FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, grupoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maestro = rs.getString("MAESTRO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|getMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maestro;
	}
	
	public static String getFecha(Connection conn, String grupoId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String fecha 			= "";		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = ?");
			ps.setString(1, grupoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				fecha	= rs.getString("FECHA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|getFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
		
	public ArrayList<CargaGrupoImp> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoImp> lisGrupo	= new ArrayList<CargaGrupoImp>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, GRUPO"
					+ " FROM ENOC.CARGA_GRUPO_IMP "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoImp grupo = new CargaGrupoImp();
				grupo.mapeaReg(rs);
				lisGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImpUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupo;
	}
	
	public ArrayList<CargaGrupoImp> getListPlan(Connection conn, String planId, String orden ) throws SQLException{
			
		ArrayList<CargaGrupoImp> lisGrupo	= new ArrayList<CargaGrupoImp>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT GRUPO_ID, CURSO_ID, MAESTRO, ALUMNOS, CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, GRUPO"
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE ENOC.CURSO_PLAN(CURSO_ID) = '"+planId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CargaGrupoImp grupo = new CargaGrupoImp();
				grupo.mapeaReg(rs);
				lisGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImpUtil|getListBloques|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisGrupo;
	}
	
		
}