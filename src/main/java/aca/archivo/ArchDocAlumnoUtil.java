//Clase  para la tabla ARCH_DOCALUM
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchDocAlumnoUtil {
	
	public boolean insertReg(Connection conn, ArchDocAlumno arch ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA, " + 
					"IDDOCUMENTO, IDSTATUS, FECHA, USUARIO, CANTIDAD, UBICACION) " +
					"VALUES(?, TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), " +
					"TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))");
			
			ps.setString(1, arch.getMatricula());
			ps.setString(2, arch.getIdDocumento());
			ps.setString(3, arch.getIdStatus());
			ps.setString(4, arch.getFecha());
			ps.setString(5, arch.getUsuario());
			ps.setString(6, arch.getCantidad());
			ps.setString(7, arch.getUbicacion());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateReg(Connection conn, ArchDocAlumno arch ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_DOCALUM SET"
					+ " IDSTATUS = TO_NUMBER(?,'99'), FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?, CANTIDAD = TO_NUMBER(?,'99'), "
					+ " UBICACION = TO_NUMBER(?,'99')"
					+ " WHERE MATRICULA = ?"
					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')");
					
			
			ps.setString(1, arch.getIdStatus());
			ps.setString(2, arch.getFecha());
			ps.setString(3, arch.getUsuario());
			ps.setString(4, arch.getCantidad());
			ps.setString(5, arch.getUbicacion());
			ps.setString(6, arch.getMatricula());
			ps.setString(7, arch.getIdDocumento());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateUbicacion(Connection conn, String codigoPersonal, String documentoId, String ubicacion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_DOCALUM "
					+ " SET UBICACION = ?"
					+ " WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')");
			ps.setString(1,ubicacion);
			ps.setString(2,codigoPersonal);
			ps.setString(3,documentoId);			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|updateUbicacion|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean deleteReg(Connection conn, String matricula, String idDocumento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? AND " + 
					"IDDOCUMENTO = TO_NUMBER(?,'999')");
			ps.setString(1,matricula);	
			ps.setString(2,idDocumento);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public ArchDocAlumno mapeaRegId(Connection con, String Matricula, String IdDocumento) throws SQLException{
		ArchDocAlumno archDoc = new ArchDocAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = con.prepareStatement("SELECT MATRICULA, IDDOCUMENTO, " +
					"IDSTATUS, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CANTIDAD, UBICACION " +
					"FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')");		 
			ps.setString(1,Matricula);		
			ps.setString(2,IdDocumento);
			rs = ps.executeQuery();
		
			if(rs.next()){		
				archDoc.mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return archDoc;
	}

	public boolean existeReg(Connection conn, String matricula, String idDocumento) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_DOCALUM " + 
					"WHERE MATRICULA = ?  AND IDDOCUMENTO = TO_NUMBER(?,'999') ");
			ps.setString(1,matricula);
			ps.setString(2,idDocumento);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IDDOCUMENTO)+1 MAXIMO FROM ENOC.ARCH_DOCALUM"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getDescripcion( Connection conn, String IdDocumento) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String descripcion		= "";
		
		try{
			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"); 
			ps.setString(1, IdDocumento);
			rs = ps.executeQuery();
			if (rs.next()){
				descripcion = rs.getString("DESCRIPCION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumno|getDescripcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return descripcion;
	}
	
	
	public ArrayList<ArchDocAlumno> getListAll(Connection conn, String Matricula, String orden ) throws SQLException{
		ArrayList<ArchDocAlumno> lisArchAlum 	= new ArrayList<ArchDocAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " Select da.matricula AS matricula, "
					+ " da.iddocumento AS iddocumento, "
					+ " doc.descripcion AS documento, "
					+ " da.idstatus AS idstatus, "
					+ " ds.descripcion AS status, "
					+ " COALESCE(to_char(da.fecha,'DD/MM/YYYY'),'01/01/1900') AS fecha, "
					+ " COALESCE(da.cantidad, 0)AS cantidad, "
					+ " COALESCE(da.usuario, 'vacio') AS usuario, "
					+ " da.ubicacion as ubicacion"
					+ " From ENOC.arch_docalum da, ENOC.arch_documentos doc, ENOC.arch_status ds "
					+ " Where Matricula = '"+Matricula+"' and doc.iddocumento = da.iddocumento and "
					+ " ds.idstatus = da.idstatus "+orden;		
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				ArchDocAlumno archDocAlum = new ArchDocAlumno();				
				archDocAlum.mapeaReg(rs);
				lisArchAlum.add(archDocAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}
	
	public ArrayList<ArchDocAlumno> getListOne(Connection conn, String Matricula, String orden ) throws SQLException{
		ArrayList<ArchDocAlumno> lisArchAlum 	= new ArrayList<ArchDocAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "Select matricula, iddocumento, idstatus, fecha, " +
					"usuario, cantidad, ubicacion from ENOC.arch_docalum where matricula = '"+Matricula+"' "+orden;		 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				ArchDocAlumno archDocAlum = new ArchDocAlumno();				
				archDocAlum.mapeaReg(rs);
				lisArchAlum.add(archDocAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|getListOne|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}
	
	public ArrayList<ArchDocAlumno> getListAlumno(Connection conn, String matricula, String ubicacion, String orden ) throws SQLException{
		ArrayList<ArchDocAlumno> lisArchAlum 	= new ArrayList<ArchDocAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, FECHA, USUARIO, CANTIDAD, UBICACION"
					+ " FROM ENOC.ARCH_DOCALUM"
					+ " WHERE MATRICULA = '"+matricula+"'"
					+ " AND UBICACION NOT IN ("+ubicacion+") "+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				ArchDocAlumno archDocAlum = new ArchDocAlumno();				
				archDocAlum.mapeaReg(rs);
				lisArchAlum.add(archDocAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|getListOne|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}
	
	public ArrayList<ArchDocAlumno> lisAlumnosEnDocumento(Connection conn, String documentoId, String orden ) throws SQLException{
		ArrayList<ArchDocAlumno> lisArchAlum 	= new ArrayList<ArchDocAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " Select matricula, iddocumento, idstatus, fecha, usuario, cantidad, ubicacion"
					+ " from ENOC.arch_docalum where iddocumento = "+documentoId+" "+orden;		 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				ArchDocAlumno archDocAlum = new ArchDocAlumno();		
				archDocAlum.mapeaReg(rs);
				lisArchAlum.add(archDocAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|lisAlumnosEnDocumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}
	
	public static HashMap<String, String> mapAlumnosEnDocumento(Connection conn, String documentoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT MATRICULA, COUNT(MATRICULA) AS TOTAL FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO = "+documentoId+" GROUP BY MATRICULA";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MATRICULA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|mapAlumnosEnDocumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}

}