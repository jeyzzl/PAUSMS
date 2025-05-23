package aca.web.analisis;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.est.spring.EstMaestro;

@Controller
public class ContAnalisisSueldos {
		
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.vista.spring.UsuariosDao usuariosDao;
		
	@Autowired
	private aca.est.spring.EstMaestroDao estMaestroDao;	
	
	@RequestMapping("/analisis/sueldos/maestros")
	public String analisisSueldosMaestros(HttpServletRequest request, Model modelo){
		String cargas = request.getParameter("Cargas")==null?"'18194A','18191A','18195A','18193A'":request.getParameter("Cargas");
		
		List<EstMaestro> lisMaestros 				= estMaestroDao.lisAll("");
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroAnalisis(cargas, "NOMBRE");
		
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "analisis/sueldos/maestros";
	}
	
	@RequestMapping("/analisis/sueldos/editar")
	public String analisisSueldosMaestrosEditar(HttpServletRequest request, Model modelo){
		String nomina 			= request.getParameter("Nomina")==null?"0":request.getParameter("Nomina");
		EstMaestro maestro 		= new EstMaestro();
		String nombreMaestro	= "";
		String mensaje  = "";
		
		if(estMaestroDao.existeReg(nomina)) {
			maestro = estMaestroDao.mapeaRegId(nomina);			 
		}
		nombreMaestro = usuariosDao.getNombreUsuario(nomina, "NOMBRE");
		
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("mensaje", mensaje);
		
		return "analisis/sueldos/editar";
	}

	@RequestMapping("/analisis/sueldos/grabar")
	public String analisisSueldosMaestrosGrabar(HttpServletRequest request, Model modelo){
		String importe 	= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		String nomina 	= request.getParameter("Nomina")==null?"0":request.getParameter("Nomina");
		String tipo 	= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje  = "";
		
		EstMaestro maestro 	= new EstMaestro();
		
		if(estMaestroDao.existeReg(nomina)) {
			maestro = estMaestroDao.mapeaRegId(nomina);
			maestro.setImporte(importe);
			maestro.setTipo(tipo);
			if(estMaestroDao.updateReg(maestro)) {
				mensaje = "1";
			}else {
				mensaje = "0";
			}
		}
		
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("mensaje", mensaje);
		
		return "analisis/sueldos/editar";
	}
}
