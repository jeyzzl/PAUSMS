package aca.web.extranjeros;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContExtranjerosCandados {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/extranjeros/candados/actualizar")
	public String extranjerosCandadosActualizar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosCandados|extranjerosCandadosActualizar");
		return "extranjeros/candados/actualizar";
	}	
}