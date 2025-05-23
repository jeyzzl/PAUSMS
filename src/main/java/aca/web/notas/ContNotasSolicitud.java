package aca.web.notas;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContNotasSolicitud {
	
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
	
	@RequestMapping("/notas/solicitud/cargas")
	public String mapaNotasRadiografiaRadiografia(HttpServletRequest request){	
		enviarConEnoc(request,"Error-aca.ContNotasSolicitud|mapaNotasSolicitudCargas");
		return "notas/solicitud/cargas";
	}	
	
	@RequestMapping("/notas/solicitud/solicitud")
	public String mapaNotasSolicitudSolicitud(HttpServletRequest request){	
		enviarConEnoc(request,"Error-aca.ContNotasSolicitud|mapaNotasSolicitudSolicitud");
		return "notas/solicitud/solicitud";
	}
}
