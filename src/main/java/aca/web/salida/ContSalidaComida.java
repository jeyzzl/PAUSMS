package aca.web.salida;


import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.salida.spring.SalSolicitud;

@Controller
public class ContSalidaComida {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/salida/comidas/salidas")
	public String salidaComidasSalidas(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?"01/01/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?"31/12/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaFin");
		
		List<SalSolicitud> lisSalidas 			= salSolicitudDao.listConComidas(fechaIni, fechaFin, " ORDER BY FECHA_SALIDA");
		HashMap<String,String> mapaGrupoNombre 	= salSolicitudDao.mapNombreGrupo();
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("lisSalidas",lisSalidas);
		modelo.addAttribute("mapaGrupoNombre",mapaGrupoNombre);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		
		return "salida/comidas/salidas";
	}
	
}