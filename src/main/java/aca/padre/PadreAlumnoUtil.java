//BEANS DE LA TABLA ALUM_PADRE

package aca.padre;

import java.sql.*;
import java.util.ArrayList;

public class PadreAlumnoUtil {	
	
	public boolean insertReg(Connection conn, PadreAlumno padreAlumno ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.PADRE_ALUMNO"+
 				"(PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO) "+
 				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)");
 			ps.setString(1, padreAlumno.getPadreId()); 			
 			ps.setString(2, padreAlumno.getCodigoPersonal());
 			ps.setString(3, padreAlumno.getFechaAlta());
 			ps.setString(4, padreAlumno.getFechaAutoriza());
 			ps.setString(5, padreAlumno.getEstado());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadreAlumno|insertReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, PadreAlumno padreAlumno ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.PADRE_ALUMNO"
 				+ " SET FECHA_ALTA = TO_DATE(?,'DD/MM/YYYY'),"
 				+ " FECHA_AUTORIZA = TO_DATE(?,'DD/MM/YYYY'),"
 				+ " ESTADO = ?, " 				 	
 				+ " WHERE PADRE_ID = ? "
 				+ " AND CODIGO_PERSONAL = ?");
 				
 			ps.setString(1, padreAlumno.getFechaAlta());
 			ps.setString(2, padreAlumno.getFechaAutoriza());
 			ps.setString(3, padreAlumno.getEstado());
 			ps.setString(4, padreAlumno.getPadreId());
 			ps.setString(5, padreAlumno.getCodigoPersonal()); 			 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadreAlumno|updateReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean updateAutoriza(Connection conn, String padreId, String codigoPersonal, String fechaAutoriza, String estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.PADRE_ALUMNO SET FECHA_AUTORIZA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?" +
				" WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ? ");
			ps.setString(1, fechaAutoriza);
			ps.setString(2, estado);
 			ps.setString(3, padreId);
 			ps.setString(4, codigoPersonal);
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.padre.PadreAlumno|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	} 	
 	
 	public boolean deleteReg(Connection conn, String padreId, String codigoPersonal  ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.PADRE_ALUMNO "+ 
 				"WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ? ");
 			ps.setString(1, padreId);
 			ps.setString(2, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadreAlumno|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public boolean existeReg(Connection conn, String padreId, String codigoPersonal) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.PADRE_ALUMNO WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ?");
 			ps.setString(1, padreId);
 			ps.setString(2, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadreAlumno|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public ArrayList<PadreAlumno> getLista(Connection conn, String codigoPadre, String orden ) throws SQLException{
	
		ArrayList<PadreAlumno> lisPadre			= new ArrayList<PadreAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
			
		try{
			comando = "SELECT PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO FROM ENOC.PADRE_ALUMNO" +
					  " WHERE PADRE_ID = '"+codigoPadre+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				PadreAlumno padre = new PadreAlumno();
				padre.mapeaReg(rs);
				lisPadre.add(padre);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.padre.PadreAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisPadre;
	} 	
	
	public ArrayList<PadreAlumno> getListaPadres(Connection conn, String codigoAlumno, String estado, String orden ) throws SQLException{
		
		ArrayList<PadreAlumno> lisPadre			= new ArrayList<PadreAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
			
		try{
			comando = "SELECT PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO FROM ENOC.PADRE_ALUMNO"
					  + " WHERE CODIGO_PERSONAL = '"+codigoAlumno+"'"
					  + " AND ESTADO IN("+estado+") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				PadreAlumno padre = new PadreAlumno();
				padre.mapeaReg(rs);
				lisPadre.add(padre);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.padre.PadreAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisPadre;
	}
	
}