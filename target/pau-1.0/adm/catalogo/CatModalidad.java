// Bean del Catalogo de Modalidades
package  adm.catalogo;

import java.sql.*;

public class CatModalidad{
	private String modalidadId;
	private String nombreModalidad;
	private String enLinea;
	private String admisible;
		
	public CatModalidad(){
		modalidadId		= "";
		nombreModalidad	= "";
		enLinea			= "";
		admisible		= "";
	}
	
	public String getAdmisible() {
		return admisible;
	}

	public void setAdmisible(String admisible) {
		this.admisible = admisible;
	}

	public String getModalidadId(){
		return modalidadId;
	}
	
	public void setModalidadId( String modalidadId){
		this.modalidadId = modalidadId;
	}	
	
	public String getNombreModalidad(){
		return nombreModalidad;
	}
	
	public void setNombreModalidad( String nombreModalidad){
		this.nombreModalidad = nombreModalidad;
	}
	
	/**
	 * @return the enLinea
	 */
	public String getEnLinea() {
		return enLinea;
	}

	/**
	 * @param enLinea the enLinea to set
	 */
	public void setEnLinea(String enLinea) {
		this.enLinea = enLinea;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_MODALIDAD(MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE ) "+
				"VALUES( TO_NUMBER(?,'99'), ?, ?, ? ) ");
			ps.setString(1, modalidadId);
			ps.setString(2, nombreModalidad);
			ps.setString(3, enLinea);
			ps.setString(4, admisible);	
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_MODALIDAD "+ 
				"SET NOMBRE_MODALIDAD = ?," +
				" ENLINEA = ?," +
				" ADMISIBLE = ? " +
				"WHERE MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nombreModalidad);
			ps.setString(2, enLinea);
			ps.setString(3, admisible);
			ps.setString(4, modalidadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_MODALIDAD "+ 
				"WHERE MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, modalidadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }	
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		modalidadId 		= rs.getString("MODALIDAD_ID");
		nombreModalidad 	= rs.getString("NOMBRE_MODALIDAD");
		enLinea 	        = rs.getString("ENLINEA");
		admisible 	        = rs.getString("ADMISIBLE");
	}
	
	public void mapeaRegId( Connection conn, String modalidadId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE "+
				"FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,modalidadId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(MODALIDAD_ID)+1 MAXIMO FROM ENOC.CAT_MODALIDAD"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatModalidad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreModalidad(Connection conn, String modalidadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?"); 
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("NOMBRE_MODALIDAD");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarrera|getNombreModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static boolean esLinea(Connection conn, String modalidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok				= true;
		
		try{
			ps = conn.prepareStatement("SELECT ENLINEA FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?"); 
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = rs.getString("ENLINEA").equals("S");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarrera|esLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String modalidadesEnLinea(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String modalidades 		= "";
		int row = 0;
		
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM ENOC.CAT_MODALIDAD WHERE ENLINEA = 'S' ORDER BY MODALIDAD_ID");			
			rs = ps.executeQuery();
			while (rs.next()){ row++;
				if (row>1){
					modalidades+= ","+rs.getString("MODALIDAD_ID");
				}else{
					modalidades+= rs.getString("MODALIDAD_ID");
				}
				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarrera|modalidadesEnLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return modalidades;
	}
}