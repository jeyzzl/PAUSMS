package aca.web.mapa;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMapaNuevos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/mapa/nuevos/editaMateria")
	public String mapaNuevosEditaMateria(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEditaMateria");
		return "mapa/nuevos/editaMateria";
	}
	
	@RequestMapping("/mapa/nuevos/editaMateriaAccion")
	public String mapaNuevosEditaMateriaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEditaMateriaAccion");
		return "mapa/nuevos/editaMateriaAccion";
	}
	
	@RequestMapping("/mapa/nuevos/editaPlan")
	public String mapaNuevosEditaPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEditaPlan");
		return "mapa/nuevos/editaPlan";
	}
	
	@RequestMapping("/mapa/nuevos/editaUnidadAccion")
	public String mapaNuevosEditaUnidadAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerMapa|mapaNuevosEditaUnidadAccion");
		return "mapa/nuevos/editaUnidadAccion";
	}
	
	@RequestMapping("/mapa/nuevos/editaUnidad")
	public String mapaNuevosEditaUnidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEditaUnidad");
		return "mapa/nuevos/editaUnidad";
	}
	
	@RequestMapping("/mapa/nuevos/editaUnidadI")
	public String mapaNuevosEditaUnidadI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEditaUnidadI");
		return "mapa/nuevos/editaUnidadI";
	}
	
	@RequestMapping("/mapa/nuevos/estadistica")
	public String mapaNuevosEstadistica(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosEstadistica");
		return "mapa/nuevos/estadistica";
	}
	
	@RequestMapping("/mapa/nuevos/materias")
	public String mapaNuevosMaterias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMaterias");
		return "mapa/nuevos/materias";
	}
	
	@RequestMapping("/mapa/nuevos/materiasAccion")
	public String mapaNuevosMateriasAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMateriasAccion");
		return "mapa/nuevos/materiasAccion";
	}
	
	@RequestMapping("/mapa/nuevos/muestraMateriaPdf")
	public String mapaNuevosMuestraMateriaPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMuestraMateriasPdf");
		return "mapa/nuevos/muestraMateriaPdf";
	}
	
	@RequestMapping("/mapa/nuevos/muestraMateriaPdfI")
	public String mapaNuevosMuestraMateriasPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMuestraMateriasPdfI");
		return "mapa/nuevos/muestraMateriaPdfI";
	}
	
	@RequestMapping("/mapa/nuevos/muestraPlan")
	public String mapaNuevosMuestraPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMuestraPlan");
		return "mapa/nuevos/muestraPlan";
	}
	
	@RequestMapping("/mapa/nuevos/muestraPlanPdf")
	public String mapaNuevosMuestraPlanPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMuestraPlanPdf");
		return "mapa/nuevos/muestraPlanPdf";
	}
	
	@RequestMapping("/mapa/nuevos/muestraPlanPdfI")
	public String mapaNuevosMuestraPlanPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosMuestraPlanPdfI");
		return "mapa/nuevos/muestraPlanPdfI";
	}	
	
	@RequestMapping("/mapa/nuevos/planes")
	public String mapaNuevosPlanes(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|mapaNuevosPlanes");
		return "mapa/nuevos/planes";
	}
	
	@RequestMapping("/mapa/nuevos/verMateria")
	public String mapaNuevosVerMateria(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|verMateria");
		return "mapa/nuevos/verMateria";
	}
	
	@RequestMapping("/mapa/nuevos/verMateriaI")
	public String mapaNuevosVerMateriaI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaNuevos|verMateriaI");
		return "mapa/nuevos/verMateriaI";
	}
	
}