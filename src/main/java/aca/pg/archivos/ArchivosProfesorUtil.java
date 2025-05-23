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
public class ArchivosProfesorUtil {
	public ArrayList<ArchivosProfesor> getListCurso(Connection conn, String cursoCargaId, String orden) throws SQLException{
		ResultSet rs						= null;
		PreparedStatement ps				= null;
		ArrayList<ArchivosProfesor> lisArchivos	= new ArrayList<ArchivosProfesor>();
		ArchivosProfesor archivo				= null;
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
					" NOMBRE, COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO" +
					" FROM PORTAL.ARCHIVOS_PROFESOR" + 
					" WHERE ARCHIVO_ID LIKE ?||'%' "+orden);
			
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				archivo = new ArchivosProfesor();
			    archivo.mapeaReg(rs);
			    lisArchivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesorUtil|getListCurso|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisArchivos;
	}
}