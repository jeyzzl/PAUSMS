package aca.web.clinicos;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContClinicosPago {
	
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
	
	@RequestMapping("/clinicos/pago/pago")
	public String clinicosPagoPago(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClincosPago|Pago:");
		return "clinicos/pago/pago";
	}
	
	@RequestMapping("/clinicos/pago/pago_old")
	public String clinicosPagoPagoOld(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClincosPago|PagoOld:");
		return "clinicos/pago/pago_old";
	}	
	
}