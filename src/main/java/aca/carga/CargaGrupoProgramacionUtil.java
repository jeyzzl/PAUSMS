package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaGrupoProgramacionUtil {
	
	
	public boolean insertReg(Connection conn, CargaGrupoProgramacion progra ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_PROGRAMACION(CURSO_CARGA_ID,FOLIO,FECHA,ORDEN)"
					+ " VALUES( ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? ) ");
			ps.setString(1, progra.getCursoCargaId()); 
			ps.setString(2, progra.getFolio());
			ps.setString(3, progra.getFecha());
			ps.setString(4, progra.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoProgramacion progra ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_PROGRAMACION "+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY'), ORDEN = ?"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')");	
			
			ps.setString(3, progra.getFecha());
			ps.setString(4, progra.getOrden());
			ps.setString(1, progra.getCursoCargaId()); 
			ps.setString(2, progra.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String folio) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_PROGRAMACION"+ 
				" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			if (ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoProgramacion mapeaRegId( Connection conn, String cursoCargaId, String folio) throws SQLException{
		
		CargaGrupoProgramacion progra = new CargaGrupoProgramacion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN FROM ENOC.CARGA_GRUPO_PROGRAMACION" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				progra.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return progra;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String maximo 			= "1";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	public ArrayList<CargaGrupoProgramacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoProgramacion> asistencia = new ArrayList<CargaGrupoProgramacion>();
		Statement st 								 = conn.createStatement();
		ResultSet rs 								 = null;
		String comando								 = "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN "+
					"FROM ENOC.CARGA_GRUPO_PROGRAMACION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoProgramacion horario = new CargaGrupoProgramacion();
				horario.mapeaReg(rs);
				asistencia.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return asistencia;
	}
	
	public ArrayList<CargaGrupoProgramacion> getListMateria(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoProgramacion> asistencia = new ArrayList<CargaGrupoProgramacion>();
		Statement st 								 = conn.createStatement();
		ResultSet rs 								 = null;
		String comando								 = "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN " +
					" FROM ENOC.CARGA_GRUPO_PROGRAMACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoProgramacion horario = new CargaGrupoProgramacion();
				horario.mapeaReg(rs);
				asistencia.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacionUtil|getListMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return asistencia;
	}

	

}
