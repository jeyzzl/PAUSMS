package aca.web.aptitudfisica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContAptitudFisicaPeriodo {

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
	
	@RequestMapping("/aptitud_fisica/periodo/accion")
	public String aptitudFisicaPeriodoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAptitudFisicaPeriodo|aptitudFisicaPeriodoAccion:");
		return "aptitud_fisica/periodo/accion";
	}
	
	@RequestMapping("/aptitud_fisica/periodo/periodo")
	public String aptitudFisicaPeriodoPeriodo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAptitudFisicaPeriodo|aptitudFisicaPeriodoPeriodo:");
		return "aptitud_fisica/periodo/periodo";
	}
	
}
