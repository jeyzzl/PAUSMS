package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscReportes {
	
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
	
	@RequestMapping("/bsc/reportes/menu")
	public String bscReportes(HttpServletRequest request){		
		return "bsc/reportes/menu";
	}	

	@RequestMapping("/bsc/reportes/ext_inscritos")
	public String bscReportesExtInscritos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscReportes|extInscritos:");
		return "bsc/reportes/ext_inscritos";
	}	

	@RequestMapping("/bsc/reportes/promedios")
	public String bscReportesPromedios(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscReportes|Promedios:");
		return "bsc/reportes/promedios";
	}	
	
}