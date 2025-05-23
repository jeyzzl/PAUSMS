import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 */

/**
 * @author elifo
 *
 */
public class BajarAlumno extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		aca.pg.archivos.ArchivosAlumno archivosAlumno = new aca.pg.archivos.ArchivosAlumno();
		try {
			DriverManager.registerDriver (new org.postgresql.Driver());
			Connection conn		= DriverManager.getConnection("jdbc:postgresql://172.16.251.11/archivo","postgres","jete17");
			
			String codigoPersonal	= request.getParameter("codigoPersonal");
			String archivoId		= request.getParameter("archivoId");
			String folio			= request.getParameter("folio");
			
			archivosAlumno.mapeaRegId(conn, codigoPersonal, archivoId, folio);
			
			conn.setAutoCommit(false);
			byte archivo[]=null;
			
		        org.postgresql.largeobject.LargeObject obj;
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();	
				long oid = archivosAlumno.getArchivo();
		   		obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
			    archivo = new byte[obj.size()];
			    obj.read(archivo, 0, obj.size());
				obj.close();
		    
			try {
					response.setHeader("Pragma", "no-cache");
					response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Cache-Control", "no-store");
					response.setHeader("Cache-Control", "max-age=0");
					response.setHeader("Cache-Control", "must-revalidate");
					response.setDateHeader("Expires", 0); 
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition","attachment; filename=\""+ archivosAlumno.getNombre() + "\"");
					
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(archivo);
					ouputStream.flush();
					ouputStream.close();
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				
			conn.close();
			conn = null;
			conn.setAutoCommit(true);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	} 
}