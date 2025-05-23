//Clase  para la tabla ARCH_DOCALUM
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchPermisosUtil {
	
	public boolean insertReg(Connection conn, ArchPermisos permisos ) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_PERMISOS (MATRICULA, USUARIO_ALTA, " + 
					"USUARIO_BAJA, FECHA_INI, FECHA_LIM, ESTADO, FOLIO, COMENTARIO) " +
					"VALUES(?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?,'999'), ?)");
			ps.setString(1, permisos.getMatricula());
			ps.setString(2, permisos.getUsuarioAlta());
			ps.setString(3, permisos.getUsuarioBaja());
			ps.setString(4, permisos.getFechaIni());
			ps.setString(5, permisos.getFechaLim());
			ps.setString(6, permisos.getEstado());
			ps.setString(7, permisos.getFolio());
			ps.setString(8, permisos.getComentario());
			if (ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			}catch(Exception ex){
				System.out.println("Error - aca.academico.ArchPermisosUtil|insertReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		return ok;
	}
	
	public boolean updateReg(Connection conn, ArchPermisos permisos ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_PERMISOS " + 
					"SET USUARIO_BAJA = ?, ESTADO = ? WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, permisos.getUsuarioBaja());
			ps.setString(2, permisos.getEstado());
			ps.setString(3, permisos.getMatricula());
			ps.setString(4, permisos.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchPermisosUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String matricula, String folio ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, matricula);
			ps.setString(2, folio);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchPermisosUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchPermisos mapeaRegId( Connection conn, String Matricula, String Folio) throws SQLException{
		
		ArchPermisos permisos = new ArchPermisos();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, USUARIO_ALTA, USUARIO_BAJA, " +
					"FECHA_INI, FECHA_LIM, ESTADO, FOLIO, COMENTARIO FROM ENOC.ARCH_PERMISOS " + 
					"WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1,Matricula);
			ps.setString(2,Folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				permisos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return permisos;
	}
	
	public boolean existeReg(Connection conn, String matricula, String folio) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999') "); 
			ps.setString(1, matricula);
			ps.setString(2, folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchPermisosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public String MaximoReg(Connection conn, String codigoPersonal) throws SQLException{
	
		String maximo			= "1";
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = '"+codigoPersonal+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("Maximo");
				
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchPermisosUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	 
	public ArrayList<ArchPermisos> getListAll(Connection conn, String s_codigo_personal, String orden ) throws SQLException{
		
		ArrayList<ArchPermisos> lisArchPermisos 	= new ArrayList<ArchPermisos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT MATRICULA, USUARIO_ALTA, USUARIO_BAJA, FECHA_INI, FECHA_LIM, " +
					"ESTADO, FOLIO, COMENTARIO FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = '"+s_codigo_personal+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArchPermisos documento = new ArchPermisos();
				documento.mapeaReg(rs);
				lisArchPermisos.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchPermisos;
	}
	
	public ArrayList<ArchPermisos> getListThis(Connection conn, String s_codigo_personal, String orden ) throws SQLException{
		
		ArrayList<ArchPermisos> lisArchPermisos 	= new ArrayList<ArchPermisos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT " +
					"COALESCE(MATRICULA, 'No Encontro') Matricula, COALESCE(Usuario_Alta, 'No Encontro') UsuarioAlta, " +
					"COALESCE(Usuario_Baja, 'Ninguno') UsuarioBaja, " +
					"COALESCE(TO_CHAR(Fecha_Ini, 'DD/MM/YYYY'), 'No Encontro') FechaIni, " +
					"COALESCE(TO_CHAR(Fecha_Lim, 'DD/MM/YYYY'), 'No Encontro') FechaLim, " +
					"COALESCE(Estado, 'No Encontro') Estado, " +
					"COALESCE(Folio, 0) Folio, " +
					"COALESCE(Comentario, 'No Encontro') Comentario " +
					"FROM ENOC.ARCH_PERMISOS " + 
					"WHERE MATRICULA = '"+s_codigo_personal+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchPermisos permiso = new ArchPermisos();
				permiso.mapeaReg(rs);
				lisArchPermisos.add(permiso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|getListThis|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchPermisos;
	}
	

	public ArrayList<ArchPermisos> getListMax(Connection conn, String s_codigo_personal) throws SQLException{
	
		ArrayList<ArchPermisos> lisArchPermisos 		= new ArrayList<ArchPermisos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "Select COALESCE(max(folio) + 1, 1) folio " +
			"from ENOC.arch_permisos " + 
			"where matricula = '"+s_codigo_personal+"' ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchPermisos permiso = new ArchPermisos();
				permiso.mapeaReg(rs);
				lisArchPermisos.add(permiso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|getListMax|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchPermisos;
	}	
	
	public ArrayList<ArchPermisos> getListFecha(Connection conn, String codigo_usuario) throws SQLException{
		
		ArrayList<ArchPermisos> lisAlumPersonal 	= new ArrayList<ArchPermisos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT FECHA_LIM FROM ENOC.ARCH_PERMISOS " + 
			"WHERE MATRICULA =  '"+codigo_usuario+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchPermisos permiso = new ArchPermisos();
				permiso.mapeaReg(rs);
				lisAlumPersonal.add(permiso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|getListFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumPersonal;
	}
	
}