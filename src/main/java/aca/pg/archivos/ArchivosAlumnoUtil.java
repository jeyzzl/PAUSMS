/**
 * 
 */
package aca.pg.archivos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author ifo
 *
 */
public class ArchivosAlumnoUtil {
	public ArrayList<ArchivosAlumno> getListAlumnoCurso(Connection conn, String cursoCargaId, String codigoPersonal, String orden) throws SQLException{
		ResultSet rs						= null;
		PreparedStatement ps				= null;
		ArrayList<ArchivosAlumno> lisArchivos	= new ArrayList<ArchivosAlumno>();
		ArchivosAlumno archivo				= null;
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND ARCHIVO_ID LIKE ?||'%' "+orden);
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				archivo = new ArchivosAlumno();
			    archivo.mapeaReg(rs);
			    lisArchivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumnoUtil|getListAlumnoCurso|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisArchivos;
	}
	
	public ArrayList<ArchivosAlumno> getListAlumnoEstrategia(Connection conn, String codigoPersonal, String archivoId, String orden) throws SQLException{
		ResultSet rs						= null;
		PreparedStatement ps				= null;
		ArrayList<ArchivosAlumno> lisArchivos	= new ArrayList<ArchivosAlumno>();
		ArchivosAlumno archivo				= null;
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND ARCHIVO_ID = ? "+orden);
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, archivoId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				archivo = new ArchivosAlumno();
			    archivo.mapeaReg(rs);
			    lisArchivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumnoUtil|getListAlumnoEstrategia|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisArchivos;
	}
}