package aca.web.analisis;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContAnalisisComparativo {

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
	
	@RequestMapping("/analisis/comparativo/getCargas")
	public String analisisComparativoGetCargas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAnalisisComparativo|analisisComparativoGetCargas:");
		return "analisis/comparativo/getCargas";
	}	

	@RequestMapping("/analisis/comparativo/cargas")
	public String analisisComparativoCargas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAnalisisComparativo|analisisComparativoCargas:");
		return "analisis/comparativo/cargas";
	}	
	
	@RequestMapping("/analisis/comparativo/listado")
	public String analisisComparativoListado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAnalisisComparativo|analisisComparativoListado:");
		return "analisis/comparativo/listado";
	}	

	@RequestMapping("/analisis/comparativo/modalidades")
	public String analisisComparativoModalidades(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAnalisisComparativo|analisisComparativoModalidades:");
		return "analisis/comparativo/modalidades";
	}	
	
}
