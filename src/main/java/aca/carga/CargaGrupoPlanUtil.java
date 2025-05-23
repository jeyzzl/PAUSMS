package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaGrupoPlanUtil{
	
	
	public boolean insertReg(Connection conn, CargaGrupoPlan plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_PLAN(CURSO_CARGA_ID, ESTUDIOS, OCUPACION, LUGAR," +
				" HORARIO, OFICINA,TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO) "+
				"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			
			ps.setString(1, plan.getCursoCargaId()); 
			ps.setString(2, plan.getEstudios());
			ps.setString(3, plan.getOcupacion());
			ps.setString(4, plan.getLugar());
			ps.setString(5, plan.getHorario());
			ps.setString(6, plan.getOficina());
			ps.setString(7, plan.getTelefono());
			ps.setString(8, plan.getTiempo());
			ps.setString(9, plan.getAtencion());
			ps.setString(10, plan.getCorreo());
			ps.setString(11, plan.getDescripcion());
			ps.setString(12, plan.getPerspectiva());
			ps.setString(13, plan.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoPlan plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_PLAN "+ 
				" SET ESTUDIOS = ?, " +
				" OCUPACION = ?," +
				" LUGAR = ?," +
				" HORARIO = ?," +
				" OFICINA = ?," +
				" TELEFONO = ?," +
				" TIEMPO = ?, " +
				" ATENCION = ?," +
				" CORREO = ? , " +
				" DESCRIPCION = ?," +
				" PERSPECTIVA = ?," +
				" ESTADO = ? "+
				" WHERE CURSO_CARGA_ID = ?");	
			 
			ps.setString(2, plan.getEstudios());
			ps.setString(3, plan.getOcupacion());
			ps.setString(4, plan.getLugar());
			ps.setString(5, plan.getHorario());
			ps.setString(6, plan.getOficina());
			ps.setString(7, plan.getTelefono());
			ps.setString(8, plan.getTiempo());
			ps.setString(9, plan.getAtencion());
			ps.setString(10, plan.getCorreo());
			ps.setString(11, plan.getDescripcion());
			ps.setString(12, plan.getPerspectiva());
			ps.setString(13, plan.getEstado());
			ps.setString(1, plan.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateEstado(Connection conn, String cursoCargaId, String estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_PLAN "+ 
				" SET ESTADO = ? " +
				" WHERE CURSO_CARGA_ID = ?");
		
			ps.setString(1, estado);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_PLAN "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public CargaGrupoPlan mapeaRegId( Connection conn, String cursoCargaId) throws SQLException{
		
		CargaGrupoPlan plan = new CargaGrupoPlan();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, ESTUDIOS, OCUPACION, LUGAR," +
					" HORARIO, OFICINA, TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO "+
				    " FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next()){
				plan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return plan;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeDatos(Connection conn, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getEstado(Connection conn, String cursoCargaId) throws SQLException{
		String estado	     	= "A";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(ESTADO,'A') AS ESTADO FROM ENOC.CARGA_GRUPO_PLAN " + 
					" WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				estado = rs.getString("ESTADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|getSumActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	
	public ArrayList<CargaGrupoPlan> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoPlan> lisEstudios = new ArrayList<CargaGrupoPlan>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	        = "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, ESTUDIOS,OCUPACION, LUGAR, HORARIO," +
					  " OFICINA, TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO FROM ENOC.CARGA_GRUPO_PLAN "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoPlan plan = new CargaGrupoPlan();
				plan.mapeaReg(rs);
				lisEstudios.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstudios;
	}
	
	public ArrayList<CargaGrupoPlan> getListPlanes(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoPlan> lisCargaS	= new ArrayList<CargaGrupoPlan>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.CARGA_GRUPO_PLAN WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaGrupoPlan carga = new CargaGrupoPlan();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
	
}