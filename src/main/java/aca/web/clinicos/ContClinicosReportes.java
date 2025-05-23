package aca.web.clinicos;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContClinicosReportes {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/clinicos/reportes/alumnos")
	public String clinicosReportesAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosReportes|Alumnos:");
		return "clinicos/reportes/alumnos";
	}
	
	@RequestMapping("/clinicos/reportes/menu")
	public String clinicosReportesMenu(HttpServletRequest request){		
		return "clinicos/reportes/menu";
	}	

	@RequestMapping("/clinicos/reportes/pago")
	public String clinicosReportesPago(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClincosReportes|pago:");
		return "clinicos/reportes/pago";
	}	
	
	@RequestMapping("/clinicos/reportes/plazas")
	public String clinicosReportesPlazas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClincosReportes|plazas:");
		return "clinicos/reportes/plazas";
	}	
	
}