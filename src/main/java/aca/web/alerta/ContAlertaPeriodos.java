package aca.web.alerta;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alerta.spring.AlertaPeriodo;
import aca.alerta.spring.AlertaPeriodoDao;

@Controller
public class ContAlertaPeriodos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	AlertaPeriodoDao alertaPeriodoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/alerta/periodos/accion")
	public String alertaPeriodosAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAlertaPeriodos|alertaPeriodosAccion:");
		return "alerta/periodos/accion";
	}

	@RequestMapping("/alerta/periodos/periodos")
	public String alertaPeriodosPeriodos(HttpServletRequest request, Model modelo){
		List<AlertaPeriodo> lisPeridos = alertaPeriodoDao.lisTodos("ORDER BY PERIODO_NOMBRE");
		
		modelo.addAttribute("lisPeriodos", lisPeridos);
		
		return "alerta/periodos/periodos";
	}
}