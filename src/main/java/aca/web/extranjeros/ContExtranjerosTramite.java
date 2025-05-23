package aca.web.extranjeros;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContExtranjerosTramite {	
	
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
	
	@RequestMapping("/extranjeros/tramite/tramite")
	public String extranjerosTramiteRequisito(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosRequisito|extranjerosTramiteRequisito");
		return "extranjeros/tramite/requisito";
	}	
	
	@RequestMapping("/extranjeros/tramite/accion")
	public String extranjerosTramiteAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosRequisito|extranjerosTramiteAccion");
		return "extranjeros/tramite/requisito";
	}
}