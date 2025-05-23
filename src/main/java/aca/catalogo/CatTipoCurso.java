// Bean del Catalogo TipoCurso
package  aca.catalogo;

import java.sql.*;

public class CatTipoCurso{
	private String tipoCursoId;	
	private String nombreTipoCurso;
	
	public CatTipoCurso(){
		tipoCursoId 	= "";
		nombreTipoCurso	= "";
	}
	
	public String getTipoCursoId(){
		return tipoCursoId;
	}
	
	public void setTipoCursoId( String tipoCursoId){
		this.tipoCursoId = tipoCursoId;
	}
	
	public String getNombreTipoCurso(){
		return nombreTipoCurso;
	}
	
	public void setNombreTipoCurso( String nombreTipoCurso){
		this.nombreTipoCurso = nombreTipoCurso;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_TIPOCURSO(TIPOCURSO_ID, NOMBRE_TIPOCURSO ) "+
				"VALUES( TO_NUMBER(?,'99'), ? ) ");
			ps.setString(1, tipoCursoId);
			ps.setString(2, nombreTipoCurso);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_TIPOCURSO "+ 
				"SET NOMBRE_TIPOCURSO = ? "+
				"WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nombreTipoCurso);
			ps.setString(2, tipoCursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_TIPOCURSO "+ 
				"WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, tipoCursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		tipoCursoId 		= rs.getString("TIPOCURSO_ID");
		nombreTipoCurso 	= rs.getString("NOMBRE_TIPOCURSO");		
	}
	
	public void mapeaRegId( Connection conn, String tipoCursoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO "+
				"FROM ENOC.CAT_TIPOCURSO WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, tipoCursoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_TIPOCURSO WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, tipoCursoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(TIPOCURSO_ID)+1 MAXIMO FROM ENOC.CAT_TIPOCURSO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	// obtiene la descripcion del tipo de materia
	public static String getNombreTipo(Connection conn, String tipoCursoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nombre 			= "x";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_TIPOCURSO FROM ENOC.CAT_TIPOCURSO WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, tipoCursoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_TIPOCURSO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getTipoCurso(Connection conn, String nombre) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String creditos			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT TIPOCURSO_ID FROM ENOC.CAT_TIPOCURSO WHERE NOMBRE_TIPOCURSO = '"+nombre+"' "); 
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				creditos = rs.getString("TIPOCURSO_ID");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatTipoCurso|getTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
}