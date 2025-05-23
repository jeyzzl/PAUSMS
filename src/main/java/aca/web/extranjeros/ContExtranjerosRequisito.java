package aca.web.extranjeros;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContExtranjerosRequisito {	
	
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
	
	@RequestMapping("/extranjeros/requisito/requisito")
	public String extranjerosRequisitoRequisito(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosRequisito|extranjerosRequisitoRequisito");
		return "extranjeros/requisito/requisito";
	}	
	
	@RequestMapping("/extranjeros/requisito/accion")
	public String extranjerosRequisitoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosRequisito|extranjerosRequisitoAccion");
		return "extranjeros/requisito/requisito";
	}
}