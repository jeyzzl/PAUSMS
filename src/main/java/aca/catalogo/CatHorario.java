// Bean del Catalogo de Horarios
package  aca.catalogo;

import java.sql.*;

public class CatHorario{
	private String horarioId;
	private String facultadId;
	private String descripcion;
	private String estado;
		
	public CatHorario(){
		horarioId		= "";
		facultadId	= "";
		descripcion	= "";
		estado		= "";
	}
	
	
	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_HORARIO"+ 
				"(HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO) "+
				"VALUES( TO_NUMBER (?,'9999999'),?, ?, ? )");
			ps.setString(1, horarioId);
			ps.setString(2, facultadId);
			ps.setString(3, descripcion);
			ps.setString(4, estado);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_HORARIO "+ 
				"SET FACULTAD_ID = ?, DESCRIPCION = ?, ESTADO = ? "+				
				"WHERE HORARIO_ID = TO_NUMBER(?,'9999999')");			
			ps.setString(1, facultadId);			
			ps.setString(2, descripcion);
			ps.setString(3, estado);
			ps.setString(4, horarioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_HORARIO "+ 
				"WHERE HORARIO_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, horarioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		horarioId		= rs.getString("HORARIO_ID");
		facultadId		= rs.getString("FACULTAD_ID");
		descripcion		= rs.getString("DESCRIPCION");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String horarioId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO"+
				" FROM ENOC.CAT_HORARIO "+ 
				" WHERE HORARIO_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, horarioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = TO_NUMBER(?,'9999999') "); 
			ps.setString(1, horarioId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	public String maximoReg(Connection conn, String horarioId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(HORARIO_ID)+1 MAXIMO FROM ENOC.CAT_HORARIO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if (maximo==null) maximo ="1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;		
	}
	
	public static String getHorarioFacultad(Connection conn, String facultadId) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String horarioId		= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HORARIO_ID FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = ? AND ESTADO = 'A'"); 
			ps.setString(1,facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				horarioId = rs.getString("HORARIO_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|getHorarioFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return horarioId;
	}
	
}