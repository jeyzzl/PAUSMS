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
public class ContCertificacionServicio {
	
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
	
	@RequestMapping("/certificacion/servicio/servicio")
	public String certificacionServicioServicio(HttpServletRequest request){		
		//List lisServicio	= tipoU.getListAll(conEnoc,"SERVICIO_NOMBRE");		
		return "certificacion/servicio/servicio";
	}
	
	@RequestMapping("/certificacion/servicio/servicioAccion")
	public String certificacionServicioServicioAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerCertificacion|servicioAccion");
		return "certificacion/servicio/servicioAccion";
	}
	
	@RequestMapping("/certificacion/servicio/getEstados")
	@ResponseBody
	public String certificacionServicioGetestados(HttpServletRequest request){		
		String paisId 					= request.getParameter("PaisId")==null?"":request.getParameter("PaisId");	
		String mensaje					= "";		
		List<CatEstado> lisEstados 		= catEstadoDao.getLista(paisId,"ORDER BY 1,3");	
		for(CatEstado edo: lisEstados){
			mensaje+= "<option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>";
		}	
		return mensaje;
	}
}