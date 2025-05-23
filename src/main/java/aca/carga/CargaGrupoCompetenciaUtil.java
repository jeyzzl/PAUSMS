package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaGrupoCompetenciaUtil {
	
	public boolean insertReg(Connection conn, CargaGrupoCompetencia comp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_COMPETENCIA(CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION ) "+
				"VALUES( ?, ?, ? ) ");
			ps.setString(1, comp.getCursoCargaId());
			ps.setString(2, comp.getCompetenciaId());
			ps.setString(3, comp.getDescripcion());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoCompetencia comp ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_COMPETENCIA "+ 
				" SET DESCRIPCION = ? " +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND COMPETENCIA_ID = ?");
			ps.setString(1, comp.getDescripcion());
			ps.setString(2, comp.getCursoCargaId());
			ps.setString(3, comp.getCompetenciaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String competenciaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_COMPETENCIA "+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND COMPETENCIA_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, competenciaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoCompetencia mapeaRegId( Connection conn, String cursoCargaId, String competenciaId) throws SQLException{
		
		CargaGrupoCompetencia comp = new CargaGrupoCompetencia();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT COMPETENCIA_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE CURSO_CARGA_ID = ? AND COMPETENCIA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				comp.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return comp;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String competenciaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE CURSO_CARGA_ID = ? AND COMPETENCIA_ID = ? "); 
			ps.setString(1,cursoCargaId);
			ps.setString(2,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int getNumCompetencias(Connection conn, String cursoCargaId) throws SQLException{
		int totalComp		    = 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(COMPETENCIA_ID) AS COMPETENCIA FROM ENOC.CARGA_GRUPO_COMPETENCIA " + 
					" WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				totalComp = rs.getInt("COMPETENCIA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|getSumActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return totalComp;
	}
	
	public ArrayList<CargaGrupoCompetencia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoCompetencia> lisGrupoCompetencia = new ArrayList<CargaGrupoCompetencia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoCompetencia competencia = new CargaGrupoCompetencia();
				competencia.mapeaReg(rs);
				lisGrupoCompetencia.add(competencia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEjeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupoCompetencia;
	}

	public ArrayList<CargaGrupoCompetencia> getListCompetencias(Connection conn,String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoCompetencia> lisGrupoEje = new ArrayList<CargaGrupoCompetencia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, COMPETENCIA_ID, DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoCompetencia comp = new CargaGrupoCompetencia();
				comp.mapeaReg(rs);
				lisGrupoEje.add(comp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CompetenciaUtil|getListCompetencias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupoEje;
	}
	
	public static String getNombreCompetencia(Connection conn, String id ) throws SQLException{
		
		String nombre           = "";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	        = "";
		
		try{
			comando = "SELECT DESCRIPCION FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE COMPETENCIA_ID='"+id+"'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CompetenciaUtil|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	

}