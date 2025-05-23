// Bean del Catalogo de grade point
package  aca.catalogo;

import java.sql.*;

public class CatGradePoint{
	private	String gpId;
	private String gpNombre;
	private String inicio;
	private String fin;
	private String puntos;
	private String titulo;
	
	public CatGradePoint(){	
		gpId		= "";
		gpNombre	= "";
		inicio		= "";
		fin			= "";
		puntos		= "";
		titulo		= "";		
	}	
	
	/**
	 * @return the gpId
	 */
	public String getGpId() {
		return gpId;
	}


	/**
	 * @param gpId the gpId to set
	 */
	public void setGpId(String gpId) {
		this.gpId = gpId;
	}


	/**
	 * @return the gpNombre
	 */
	public String getGpNombre() {
		return gpNombre;
	}


	/**
	 * @param gpNombre the gpNombre to set
	 */
	public void setGpNombre(String gpNombre) {
		this.gpNombre = gpNombre;
	}


	/**
	 * @return the inicio
	 */
	public String getInicio() {
		return inicio;
	}


	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}


	/**
	 * @return the fin
	 */
	public String getFin() {
		return fin;
	}


	/**
	 * @param fin the fin to set
	 */
	public void setFin(String fin) {
		this.fin = fin;
	}


	/**
	 * @return the puntos
	 */
	public String getPuntos() {
		return puntos;
	}


	/**
	 * @param puntos the puntos to set
	 */
	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}


	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	// Inserta un registro en la tabla  
	public boolean insertReg(Connection conn) throws Exception{		
		PreparedStatement ps = null;
		boolean ok = false;
		try{			 
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_GRADEPOINT"+ 
				"(GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO) "+
				"VALUES( ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99.9'), ?)");		
			
			ps.setString(1, gpId);
			ps.setString(2, gpNombre);
			ps.setString(3, inicio);
			ps.setString(4, fin);
			ps.setString(5, puntos);
			ps.setString(6, titulo);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_GRADEPOINT"
					+ " SET GP_NOMBRE = ?, INICIO = TO_NUMBER(?,'999'), FIN = TO_NUMBER(?,'999'), PUNTOS = TO_NUMBER(?,'99.9'), TITULO = ?"
					+ " WHERE GP_ID = ?");
			ps.setString(1, gpNombre);
			ps.setString(2, inicio);
			ps.setString(3, fin);
			ps.setString(4, puntos);
			ps.setString(5, titulo);			
			ps.setString(6, gpId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_GRADEPOINT "+ 
				"WHERE GP_ID = ?");			
			ps.setString(1, gpId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{		
		gpId 		= rs.getString("GP_ID");
		gpNombre 	= rs.getString("GP_NOMBRE");
		inicio	 	= rs.getString("INICIO");
		fin 		= rs.getString("FIN");
		puntos		= rs.getString("PUNTOS");
		titulo 		= rs.getString("TITULO");
	}
	
	public void mapeaRegId(Connection conn, String gpId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO"
					+ " FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ?");
			
			ps.setString(1, gpId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ?"); 
			ps.setString(1, gpId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getGpNombre(Connection conn, String gpId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT GP_NOMBRE FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ?"); 
			ps.setString(1, gpId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("GP_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|getGpNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getPuntos(Connection conn, String gpId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= " ";
		
		try{
			ps = conn.prepareStatement("SELECT PUNTOS FROM ENOC.CAT_PUNTOS WHERE GP_ID = ?"); 
			ps.setString(1, gpId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("PUNTOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePoint|getPuntos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
	
}