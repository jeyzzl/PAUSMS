package aca.pg.archivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchGeneral {
	private String matricula;
	private String folio;
	private String fecha;
	private String usuario;
	private int imagen;	
	
		
	
	public ArchGeneral(){
		matricula		= "";
		folio 			= "0";		
		fecha			= "";
		usuario			= "";
		imagen			= 0;
	}


	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}


	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}


	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	/**
	 * @return the imagen
	 */
	public int getImagen() {
		return imagen;
	}


	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ARCH_GENERAL(MATRICULA, FOLIO, FECHA, USUARIO, IMAGEN)" +
					" VALUES(?, TO_NUMBER(?,'999'),TO_DATE(?, 'DD/MM/YYYY'),?, ?)");
			
			ps.setString(1, matricula);
			ps.setString(2, folio);
			ps.setString(3, fecha);
			ps.setString(4, usuario);
			ps.setInt(5, imagen);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ARCH_GENERAL" +
					" SET FECHA = TO_DATE(?, 'DD/MM/YYY')," +
					" USUARIO = ?," +
					" IMAGEN = ?" +
					" WHERE MATRICULA = ?" +
					" AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, fecha);
			ps.setString(2, usuario);
			ps.setInt(3, imagen);
			ps.setString(4, matricula);
			ps.setString(5, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
		    String sql="DELETE FROM ARCH_GENERAL"+
		    		" WHERE MATRICULA = ?"+
		    		" AND FOLIO = TO_NUMBER(?,'999')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, matricula);
			ps.setString(2, folio);
			
			if(ps.executeUpdate()==1){
			    ok=true;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|deleteReg(fila)|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteRegAndPic(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ARCH_GENERAL"+
		    		" WHERE MATRICULA = ?"+
		    		" AND FOLIO = TO_NUMBER(?, '999')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, matricula);
			ps.setString(2, folio);			
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|deleteReg(OID)|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM ARCH_GENERAL"+
			    		" WHERE MATRICULA = ?"+
			    		" AND FOLIO = TO_NUMBER(?,'999')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, matricula);
				ps.setString(2, folio);
				
				if(ps.executeUpdate()==1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivo.ArchGeneral|deleteReg(fila)|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula		= rs.getString("MATRICULA");
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		usuario			= rs.getString("USUARIO");
		imagen			= rs.getInt("IMAGEN");
	}
	
	public void mapeaRegId(Connection conn, String matricula, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = conn.prepareStatement("SELECT" +
				" MATRICULA, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, IMAGEN" +
			    " FROM ARCH_GENERAL"+
				" WHERE MATRICULA = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, matricula);
			ps.setString(2, folio);		
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchGeneral|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
	
	public boolean existeReg(Connection conn) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean 		ok 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT IMAGEN FROM ARCH_GENERAL"+
 				" WHERE MATRICULA = ? " +
 				" AND FOLIO = TO_NUMBER(?,'999')");
 			ps.setString(1, matricula);
 			ps.setString(2, folio); 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchGeneral|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public static int numImagenes(Connection conn, String matricula) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet rs			= null;
 		int numImagen			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_GENERAL"+
 				" WHERE MATRICULA = ? " + 				
 				" AND (IMAGEN != 0 OR IMAGEN_BYTE IS NOT NULL)");
 			ps.setString(1, matricula);
 			 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				 numImagen= rs.getInt("NUMIMAGEN");
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchGeneral|existeReg(matricula)|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numImagen;
 	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(FOLIO)+1) AS MAXIMO FROM ARCH_GENERAL" +
					" WHERE MATRICULA = ?");
			
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if(maximo == null)
				maximo = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
}