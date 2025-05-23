
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
   

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import aca.cultural.CompEventoImagen;
   
public class Imagen extends HttpServlet {
        
      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
    	  	Connection conn 		= null;
	  		try{
	  			Class.forName("oracle.jdbc.driver.OracleDriver");
	  			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1", "enoc", "caminacondios");
	  			  			
	  			CompEventoImagen evento = new CompEventoImagen();
	  			evento.mapeaRegId(conn, request.getParameter("eventoId"), request.getParameter("imagenId"));	  			
	  			
	  			OutputStream out = response.getOutputStream();
	  			out.write(evento.getImagen());
	  			out.close(); 
	  			
	  		}catch ( Exception e){
	  			System.out.println("Error: Imagen |"+e);
	  		}
	       
      }
      
}