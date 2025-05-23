package aca.web.certificacion;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;

@Controller
public class ContCertificacionEnvio {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/certificacion/envio/envio")
	public String certificacionEnvioEnvio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCertificacionEnvio|certificacionEnvioEnvio");
		return "certificacion/envio/envio";
	}	
	
	@RequestMapping("/certificacion/envio/accion")
	public String certificacionEnvioAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCertificacionEnvio|certificacionEnvioAccion");
		return "certificacion/envio/accion";
	}
}