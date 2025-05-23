// Bean del Catalogo de Extensiones
package  aca.catalogo;

import java.sql.*;

public class CatInstitucion{
	private String institucionId;
	private String nombreInstitucion;
		
	public CatInstitucion(){
		institucionId		= "";
		nombreInstitucion	= "";	
	}
	
	public String getInstitucionId(){
		return institucionId;
	}
	
	public void setInstitucionId( String institucionId){
		this.institucionId = institucionId;
	}	
	
	public String getNombreInstitucion(){
		return nombreInstitucion;
	}
	
	public void setNombreInstitucion( String nombreInstitucion){
		this.nombreInstitucion = nombreInstitucion;
	}	
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_INSTITUCION(INSTITUCION_ID, NOMBRE_INSTITUCION ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, institucionId);
			ps.setString(2, nombreInstitucion);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_INSTITUCION "+ 
				"SET NOMBRE_INSTITUCION = ? "+
				"WHERE INSTITUCION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombreInstitucion);
			ps.setString(2, institucionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_INSTITUCION "+ 
				"WHERE INSTITUCION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, institucionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		institucionId 		= rs.getString("INSTITUCION_ID");
		nombreInstitucion 	= rs.getString("NOMBRE_INSTITUCION");		
	}
	
	public void mapeaRegId( Connection conn, String institucionId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT INSTITUCION_ID, NOMBRE_INSTITUCION "+
				"FROM ENOC.CAT_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,institucionId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,institucionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(INSTITUCION_ID)+1 MAXIMO FROM ENOC.CAT_INSTITUCION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreInstitucion(Connection conn, String numInstitucion) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombreInstitucion 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_INSTITUCION FROM ENOC.CAT_INSTITUCION WHERE INSTITUCION_ID = ?"); 
			ps.setString(1,numInstitucion);
			rs = ps.executeQuery();
			if (rs.next()){
				nombreInstitucion = rs.getString("NOMBRE_INSTITUCION");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatInstitucion|getNombreInstitucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreInstitucion;
	}
}