package aca.pg.archivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchDocAlum {
	private String matricula;
	private String iddocumento;
	private int imagen;
	private String hoja;
	private String fuente;
	private String tipo;
	private String origen;
	private String finsert;
	private String fupdate;
	private String usuario;
	
	public ArchDocAlum(){
		matricula		= "";
		iddocumento		= "";
		imagen			= 0;
		hoja			= "1";
		fuente			= "C";
		tipo			= "";
		origen			= "";
		finsert			= "";
		fupdate			= "";
		usuario			= "";		
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
	 * @return the iddocumento
	 */
	public String getIddocumento() {
		return iddocumento;
	}

	/**
	 * @param iddocumento the iddocumento to set
	 */
	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
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

	/**
	 * @return the hoja
	 */
	public String getHoja() {
		return hoja;
	}

	/**
	 * @param hoja the hoja to set
	 */
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	/**
	 * @return the fuente
	 */
	public String getFuente() {
		return fuente;
	}

	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the finsert
	 */
	public String getFinsert() {
		return finsert;
	}

	/**
	 * @param finsert the finsert to set
	 */
	public void setFinsert(String finsert) {
		this.finsert = finsert;
	}

	/**
	 * @return the fupdate
	 */
	public String getFupdate() {
		return fupdate;
	}

	/**
	 * @param fupdate the fupdate to set
	 */
	public void setFupdate(String fupdate) {
		this.fupdate = fupdate;
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

	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ARCH_DOCALUM" + 
				"(MATRICULA,IDDOCUMENTO,IMAGEN,HOJA,FUENTE,TIPO,ORIGEN,F_INSERT,F_UPDATE,USUARIO)" +
				" VALUES(?, TO_NUMBER(?, '999'), ?," +
				" TO_NUMBER(?,'99'), ?,?,?," +
				" TO_DATE(?,'DD/MM/YYYY'), " +
				" TO_DATE(?,'DD/MM/YYYY'), ?)");
			
			ps.setString(1, matricula);
			ps.setString(2, iddocumento);			
			ps.setInt(3, imagen);
			ps.setString(4, hoja);
			ps.setString(5, fuente);
			ps.setString(6, tipo);
			ps.setString(7, origen);
			ps.setString(8, finsert);
			ps.setString(9, fupdate);
			ps.setString(10,usuario);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlum|insertReg|:"+ex);	
			ok = false;
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("UPDATE ARCH_DOCALUM" + 
				" SET IMAGEN = ?," +
				" FUENTE = ?," +
				" TIPO = ?," +
				" ORIGEN = ?," +
				" F_INSERT = TO_DATE(?,'DD/MM/YYYY')," +
				" F_UPDATE = TO_DATE(?,'DD/MM/YYYY')," +
				" USUARIO = ?" +
				" WHERE MATRICULA = ?" +
				" AND IDDOCUMENTO = TO_NUMBER(?, '999')" +
				" AND HOJA = TO_NUMBER(?, '99')");
			
			ps.setInt(1, imagen);
			ps.setString(2, fuente);			
			ps.setString(3, tipo);
			ps.setString(4, origen);
			ps.setString(5, finsert);
			ps.setString(6, fupdate);
			ps.setString(7, usuario);
			ps.setString(8, matricula);
			ps.setString(9, iddocumento);
			ps.setString(10,hoja);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlum|updateReg|:"+ex);
			ok = false;
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
		
	
	public boolean deleteReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ARCH_DOCALUM"+ 
		    		" WHERE MATRICULA = ?"+
		    		" AND IDDOCUMENTO = TO_NUMBER(?, '999')"+
		    		" AND HOJA = TO_NUMBER(?,'99')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, matricula);
			ps.setString(2, iddocumento);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlum|deleteReg(OID)|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM ARCH_DOCALUM"+ 
			    		" WHERE MATRICULA = ?"+
			    		" AND IDDOCUMENTO = ?"+
			    		" AND HOJA = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, matricula);
				ps.setString(2, iddocumento);
				ps.setString(3, hoja);
				if(ps.executeUpdate()==1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivo.ArchDocAlum|deleteReg(fila)|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	public boolean deleteRegDoc(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ARCH_DOCALUM"+ 
		    		" WHERE MATRICULA = ?"+
		    		" AND IDDOCUMENTO = TO_NUMBER(?, '999')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, matricula);
			ps.setString(2, iddocumento);
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlum|deleteRegDoc(OID)|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM ARCH_DOCALUM"+ 
			    		" WHERE MATRICULA = ?"+
			    		" AND IDDOCUMENTO = TO_NUMBER(?,'999')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, matricula);
				ps.setString(2, iddocumento);			
				if(ps.executeUpdate()>=1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivo.ArchDocAlum|deleteRegDoc(fila)|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se borró la imagen y la fila del documento en postgresql");
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula		= rs.getString("MATRICULA");
		iddocumento		= rs.getString("IDDOCUMENTO");
		imagen			= rs.getInt("IMAGEN");
		hoja			= rs.getString("HOJA");
		fuente			= rs.getString("FUENTE");
		tipo			= rs.getString("TIPO");
		origen			= rs.getString("ORIGEN");
		finsert			= rs.getString("F_INSERT");
		fupdate			= rs.getString("F_UPDATE");
		usuario			= rs.getString("USUARIO");
		
	}
	
	public void mapeaRegId(Connection conn, String matricula, String iddocumento, String hoja) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = conn.prepareStatement("SELECT" +
				" MATRICULA, IDDOCUMENTO, IMAGEN, HOJA, FUENTE, TIPO, ORIGEN," +
				" TO_CHAR(F_INSERT, 'DD/MM/YYYY') AS F_INSERT," +
				" TO_CHAR(F_UPDATE, 'DD/MM/YYYY') AS F_UPDATE, USUARIO" +
			    " FROM ARCH_DOCALUM" + 
				" WHERE MATRICULA = ?" +
				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
				" AND HOJA = TO_NUMBER(?, '99')");
			
			ps.setString(1, matricula);
			ps.setString(2, iddocumento);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT IMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND HOJA = TO_NUMBER(?,'99')");
 			ps.setString(1, matricula);
 			ps.setString(2, iddocumento);
 			ps.setString(3, hoja);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public static String getImagenHoja(Connection conn, String matricula, String idDocumento, String hoja) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null; 		
 		String imagen			= "0";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(IMAGEN,0) AS IMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND HOJA = TO_NUMBER(?,'99')");
 			ps.setString(1, matricula);
 			ps.setString(2, idDocumento);
 			ps.setString(3, hoja);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				imagen = rs.getString("IMAGEN");
 			
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return imagen;
 	}
	
	/* 
	 * Verifica si existen imagenes de un documento del alumno
	 */
	public boolean existeRegDoc(Connection conn) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean 		ok 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT IMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')");
 			ps.setString(1, matricula);
 			ps.setString(2, iddocumento); 			
			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|existeRegDoc|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public boolean unlinkImagen(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ARCH_DOCALUM"+ 
		    		" WHERE MATRICULA = ?"+
		    		" AND IDDOCUMENTO = TO_NUMBER(?, '999')"+
		    		" AND HOJA = TO_NUMBER(?,'99')";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, matricula);
			ps.setString(2, iddocumento);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt("RESULTADO") == 1)
					ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlum|unlinkImagen|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		return ok;
	}
	
	public static int numImagenes(Connection conn, String matricula, String iddocumento) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet rs			= null;
 		int numImagen			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND ( IMAGEN != 0 OR IMAGEN_BYTE IS NOT NULL)");
 			ps.setString(1, matricula);
 			ps.setString(2, iddocumento); 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				 numImagen= rs.getInt("NUMIMAGEN");
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|numImagenes(matricula,iddocumento)|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numImagen;
 	}
	
	public static int numImagenes(Connection conn, String matricula) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet rs			= null;
 		int numImagen			= 0;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " + 				
 				" AND (IMAGEN != 0 OR IMAGEN_BYTE IS NOT NULL)");
 			ps.setString(1, matricula); 			 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				 numImagen= rs.getInt("NUMIMAGEN");
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|numImagenes(matricula)|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return numImagen;
 	}
	
	public static boolean existeReg(Connection conn, String matricula, String iddocumento, String hoja) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean 		ok 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT IMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND HOJA = TO_NUMBER(?,'99')");
 			ps.setString(1, matricula);
 			ps.setString(2, iddocumento);
 			ps.setString(3, hoja);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchDocAlum|existeReg(matricula,iddocumento,hoja)|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
}