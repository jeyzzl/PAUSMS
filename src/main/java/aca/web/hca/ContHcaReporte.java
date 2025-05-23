package aca.web.hca;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContHcaReporte {	
	
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
	
	@RequestMapping("/hca/reporte/anual")
	public String hcaReporteAnual(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContHcaReporte|hcaReporteAnual");
		
		return "hca/reporte/anual";
	}	
	
	@RequestMapping("/hca/reporte/buscar")
	public String hcaReporteBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContHcaReporte|hcaReporteBuscar");
		return "hca/reporte/buscar";
	}
	
}