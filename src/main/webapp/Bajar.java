import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Bajar extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String nombre=request.getParameter("nombre");
			byte[] archivo = (byte[])session.getAttribute("archivo");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Cache-Control", "max-age=0");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setDateHeader("Expires", 0); 
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+ nombre + "\"");
			response.getOutputStream().write(archivo);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	} 
}