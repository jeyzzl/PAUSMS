package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FinObservacionUtil {
	
	public FinObservacion mapeaRegId(Connection conn, String matricula, String folio) throws SQLException, IOException{
		
		FinObservacion observa = new FinObservacion();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, OBSERVACION, EMPLEADO " +	 					
	 				" FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
	 		ps.setString(1, matricula);
	 		ps.setString(2, folio);	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			observa.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 		return observa;
 	}
	
	public boolean existeReg(Connection conn, FinObservacion finObservacion) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT OBSERVACION FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, finObservacion.getMatricula());
	 		ps.setString(2, finObservacion.getFolio());	 	
	 		
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean insertReg(Connection conn, FinObservacion finObservacion) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;

		try{
			ps 	= conn.prepareStatement("INSERT INTO ENOC.FIN_OBSERVACION(CODIGO_PERSONAL, FOLIO, OBSERVACION, EMPLEADO)"
					+ " VALUES(?,TO_NUMBER(?,'999'),?,?)");
			ps.setString(1, finObservacion.getMatricula());
			ps.setString(2, finObservacion.getFolio());
			ps.setString(3, finObservacion.getObservacion());
			ps.setString(4, finObservacion.getEmpleado());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, FinObservacion finObservacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FIN_OBSERVACION"+ 
				" WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, finObservacion.getMatricula());
			ps.setString(2, finObservacion.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, FinObservacion finObservacion) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_OBSERVACION SET OBSERVACION = ? WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");			
			
			ps.setString(1, finObservacion.getObservacion());
			ps.setString(2, finObservacion.getMatricula());
			ps.setString(3, finObservacion.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String getObservacion(Connection conn, FinObservacion finObservacion) throws SQLException{
		String observacion  = "-";
		Statement st 		= conn.createStatement();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT OBSERVACION FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			
			ps.setString(1, finObservacion.getMatricula());
			ps.setString(2, finObservacion.getFolio());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				observacion = rs.getString("OBSERVACION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|getObservacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return observacion;
	}
	
	public String maximoReg(Connection conn, FinObservacion finObservacion) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? "); 
			
			ps.setString(1, finObservacion.getMatricula());
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public ArrayList<FinObservacion> getObservacionesPorAlumno(Connection conn, String codigo_personal) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		ArrayList<FinObservacion> lista = new ArrayList<FinObservacion>();
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_OBSERVACION WHERE CODIGO_PERSONAL = ? "); 
			
			ps.setString(1, codigo_personal);
			
			rs = ps.executeQuery();
			while (rs.next()){
				FinObservacion observacion = new FinObservacion();
				observacion.mapeaReg(rs);
				lista.add(observacion);	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinObservacionUtil|getObservacionesPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lista;
	}

}
