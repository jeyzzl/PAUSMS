package aca.residencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResCandado {

	private String codigoPersonal;
	private String comentario;
	private String usuario;
	private String fecha;
	private String estado;
	
	public ResCandado(){		
		estado			= "";
		codigoPersonal	= "";
		fecha			= "";
		comentario 		= "";
		usuario			= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.RES_CANDADO(CODIGO_PERSONAL, COMENTARIO, USUARIO, " + 
					" FECHA, ESTADO) " +
					" VALUES(?,?,?,TO_DATE(?,'DD/MM/YYYY'),?)");
			ps.setString(1,codigoPersonal);
			ps.setString(2,comentario);
			ps.setString(3,usuario);		
			ps.setString(4,fecha);
			ps.setString(5,estado);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.RES_CANDADO SET COMENTARIO = ? , " +
					" USUARIO =  ?, " +
					" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
					" ESTADO = ? " + 
					" WHERE CODIGO_PERSONAL = ?");			
			
			ps.setString(1,comentario);
			ps.setString(2,usuario);
			ps.setString(3,fecha);
			ps.setString(4,estado);
			ps.setString(5,codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.RES_CANDADO WHERE CODIGO_PERSONAL= ?"); 
			ps.setString(1,codigoPersonal);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		usuario 		= rs.getString("USUARIO");
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
		fecha			= rs.getString("FECHA");				
		comentario		= rs.getString("COMENTARIO");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT USUARIO, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, " +
					" COMENTARIO, ESTADO FROM ENOC.RES_CANDADO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.RES_CANDADO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
