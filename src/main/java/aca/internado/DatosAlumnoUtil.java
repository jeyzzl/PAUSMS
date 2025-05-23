/**
 * 
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class DatosAlumnoUtil {
	
	public boolean insertReg(Connection conn, DatosAlumno datosAlum ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_DATOS_ALUMNO VALUES(?,?,?,?,?,?,?,?,?)"); 
			ps.setString(1, datosAlum.getCodigoPersonal());
			ps.setString(2, datosAlum.getComputadora());
			ps.setString(3, datosAlum.getTratamiento());
			ps.setString(4, datosAlum.getMotivo());
			ps.setString(5, datosAlum.getTipoSangre());			
			ps.setString(6, datosAlum.getInstrumentos());
			ps.setString(7, datosAlum.getCelular());
			ps.setString(8, datosAlum.getCorreo());
			ps.setString(9, datosAlum.getTelefono());			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, DatosAlumno datosAlum ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_DATOS_ALUMNO "+ 
					" SET COMPUTADORA = ?, TRATAMIENTO = ?, MOTIVO = ?, TIPO_SANGRE = ?, "+
					" INSTRUMENTOS = ?, CELULAR = ?, CORREO = ?, TELEFONO = ? "+
					" WHERE CODIGO_PERSONAL = ?");			
			ps.setString(1, datosAlum.getComputadora());
			ps.setString(2, datosAlum.getTratamiento());
			ps.setString(3, datosAlum.getMotivo());
			ps.setString(4, datosAlum.getTipoSangre());			
			ps.setString(5, datosAlum.getInstrumentos());
			ps.setString(6, datosAlum.getCelular());
			ps.setString(7, datosAlum.getCorreo());
			ps.setString(8, datosAlum.getTelefono());	
			ps.setString(9, datosAlum.getCodigoPersonal());
		
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn,String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public DatosAlumno mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		DatosAlumno datosAlum = new DatosAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if(rs.next()){
				datosAlum.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return datosAlum;
	}
	public boolean existeReg(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
		ps = con.prepareStatement("SELECT * FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
		ps.setString(1,codigoPersonal);
		rs = ps.executeQuery();
		if(rs.next()){
			ok = true;
		}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
}