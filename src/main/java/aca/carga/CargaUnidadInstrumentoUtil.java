package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaUnidadInstrumentoUtil {
	
	public boolean insertReg(Connection conn, CargaUnidadInstrumento inst ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD_INSTRUMENTO(CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE ) "+
				"VALUES( ?, ?, ? ) ");
			ps.setString(1, inst.getCursoCargaId());
			ps.setString(2, inst.getInstrumentoId());
			ps.setString(3, inst.getInstrumentoNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaUnidadInstrumento inst ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD_INSTRUMENTO "+ 
				" SET INSTRUMENTO_NOMBRE = ? " +
				" WHERE INSTRUMENTO_ID = ? " +
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, inst.getInstrumentoNombre());
			ps.setString(2, inst.getInstrumentoId());
			ps.setString(3, inst.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String instrumentoId, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO "+ 
				" WHERE INSTRUMENTO_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ");
			ps.setString(1, instrumentoId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaUnidadInstrumento mapeaRegId( Connection conn, String cursoCargaId, String instrumentoId) throws SQLException{
		
		CargaUnidadInstrumento inst = new CargaUnidadInstrumento();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE CURSO_CARGA_ID = ? AND INSTRUMENTO_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,instrumentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				inst.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return inst;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String instrumentoId) throws SQLException{
		boolean 		  ok 	= false;
		ResultSet 		  rs	= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE INSTRUMENTO_ID = ?  AND CURSO_CARGA_ID = ? "); 
			ps.setString(1,instrumentoId);
			ps.setString(2,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<CargaUnidadInstrumento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaUnidadInstrumento> lisInstrumento = new ArrayList<CargaUnidadInstrumento>();
		Statement st 									 = conn.createStatement();
		ResultSet rs 									 = null;
		String comando								     = "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadInstrumento ins = new CargaUnidadInstrumento();
				ins.mapeaReg(rs);
				lisInstrumento.add(ins);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargUnidadInstrumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInstrumento;
	}
	
	public ArrayList<CargaUnidadInstrumento> getListInstrumento(Connection conn,String cursoCargaId, String orden ) throws SQLException{
			
			ArrayList<CargaUnidadInstrumento> lisInstrumento = new ArrayList<CargaUnidadInstrumento>();
			Statement st 								     = conn.createStatement();
			ResultSet rs 			        				 = null;
			String comando									 = "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE FROM ENOC.CARGA_UNIDAD_INSTRUMENTO " + 
						" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;
				
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaUnidadInstrumento ins = new CargaUnidadInstrumento();
					ins.mapeaReg(rs);
					lisInstrumento.add(ins);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaUnidadInstrumentoUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisInstrumento;
		}

}