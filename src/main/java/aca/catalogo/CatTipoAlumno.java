// Bean del Catalogo tipo de alumno
package  aca.catalogo;

import java.sql.*;

public class CatTipoAlumno{
	private String tipoId;	
	private String nombreTipo;
	
	public CatTipoAlumno(){
		tipoId 		= "";
		nombreTipo	= "";
	}
	
	public String getTipoId(){
		return tipoId;
	}
	
	public void setTipoId( String tipoId){
		this.tipoId = tipoId;
	}
	
	public String getNombreTipo(){
		return nombreTipo;
	}
	
	public void setNombreTipo( String nombreTipo){
		this.nombreTipo = nombreTipo;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_TIPOALUMNO(TIPO_ID, NOMBRE_TIPO ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, tipoId);
			ps.setString(2, nombreTipo);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_TIPOALUMNO "+ 
				"SET NOMBRE_TIPO = ? "+
				"WHERE TIPO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombreTipo);
			ps.setString(2, tipoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_TIPOALUMNO "+ 
				"WHERE TIPO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, tipoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		tipoId 		= rs.getString("TIPO_ID");
		nombreTipo 	= rs.getString("NOMBRE_TIPO");		
	}
	
	public void mapeaRegId( Connection conn, String tipoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TIPO_ID, NOMBRE_TIPO "+
				"FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,tipoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,tipoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(TIPO_ID)+1 MAXIMO FROM ENOC.CAT_TIPOALUMNO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreTipo(Connection conn, int tipo) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombreTipo 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = ?"); 
			ps.setInt(1,tipo);
			rs = ps.executeQuery();
			if (rs.next()){
				nombreTipo = rs.getString("NOMBRE_TIPO");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoAlumno|getNombreTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreTipo;
	}
}