package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscInscritos {
	
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
	
	@RequestMapping("/bsc/inscritos/detalles")
	public String bscInscritosDetalles(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscInscritos|Detalles:");
		return "bsc/inscritos/detalles";
	}	

	@RequestMapping("/bsc/inscritos/estCarreras")
	public String bscIndiceDesercion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscInscritos|estCarreras:");
		return "bsc/inscritos/estCarreras";
	}	

	@RequestMapping("/bsc/inscritos/facultad")
	public String bscInscritosFacultad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscInscritos|Facultad:");
		return "bsc/inscritos/facultad";
	}	
	
	@RequestMapping("/bsc/inscritos/umEst")
	public String bscInscritosUmEst(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscInscritos|umEst:");
		return "bsc/inscritos/umEst";
	}	
	
	@RequestMapping("/bsc/inscritos/um")
	public String bscInscritosUm(HttpServletRequest request){		
		return "bsc/inscritos/um";
	}	
	
}