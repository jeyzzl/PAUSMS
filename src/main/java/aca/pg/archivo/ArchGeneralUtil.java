/**
 * 
 */
package aca.pg.archivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author ifo
 *
 */
public class ArchGeneralUtil {
	
	public ArrayList<ArchGeneral> getListAlumno(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ResultSet rs						= null;
		PreparedStatement ps				= null;
		ArrayList<ArchGeneral> lisDocs			= new ArrayList<ArchGeneral>();
		ArchGeneral doc						= null;
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, FOLIO, FECHA, USUARIO, IMAGEN" +
		    		" FROM ARCH_GENERAL" +
					" WHERE MATRICULA = ? "+orden);
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			while(rs.next()){
				doc = new ArchGeneral();
				doc.mapeaReg(rs);
			    lisDocs.add(doc);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.ArchGeneralUtil|getListAlumno|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisDocs;
	}
	
	public ArrayList<ArchGeneral> getListAlumnos(Connection conn, String orden) throws SQLException{
		ResultSet rs						= null;
		PreparedStatement ps				= null;
		ArrayList<ArchGeneral> lisDocs			= new ArrayList<ArchGeneral>();
		ArchGeneral doc						= null;
		try{
			ps = conn.prepareStatement("SELECT DISTINCT(MATRICULA) AS MATRICULA, FECHA" +
		    		" FROM ARCH_GENERAL "+orden);
			
			rs = ps.executeQuery();
			while(rs.next()){
				doc = new ArchGeneral();
				doc.setMatricula(rs.getString("MATRICULA"));
				doc.setFecha(rs.getString("FECHA"));
			    lisDocs.add(doc);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.ArchGeneralUtil|getListAlumno|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisDocs;
	}
}