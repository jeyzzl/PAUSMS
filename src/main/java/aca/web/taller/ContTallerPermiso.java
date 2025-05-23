package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecFija;
import aca.bec.spring.BecFijaDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContTallerPermiso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private BecFijaDao becFijaDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	} 
	
	@RequestMapping("/taller/permiso/accion_ccosto")
	public String tallerPermisoAccionCCosto(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerPermiso|tallerPermisoAccionCCosto:");
		return "taller/permiso/accion_ccosto";
	}	
	
	
	@RequestMapping("/taller/permiso/fija")
	public String tallerPermisoFija(HttpServletRequest request, Model modelo){
		
		String ejercicioId  						= request.getParameter("EjercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("EjercicioId");
		List<ContEjercicio> lisEjercicios			= contEjercicioDao.getListAll("ORDER BY 1 DESC");
		List<BecFija> lisFijas						= becFijaDao.getListEjercicio(ejercicioId, "ORDER BY ID_CCOSTO");
		HashMap<String, ContCcosto> mapaDeptos		= contCcostoDao.mapaCentrosCosto(ejercicioId);
		HashMap<String, String> mapaMaestros		= maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisFijas", lisFijas);
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "taller/permiso/fija";
	}
	
	@RequestMapping("/taller/permiso/editar")
	public String tallerPermisoEditar(HttpServletRequest request, Model modelo){
		
		String ejercicioId  			= request.getParameter("ejercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("ejercicioId");		
		
		List<ContCcosto> lisDeptos		= contCcostoDao.listCentrosCosto( ejercicioId,"S","ORDER BY ID_CCOSTO");
		List<String> lisSeleccionados 	= becFijaDao.getListCentros(ejercicioId, "");
		
		modelo.addAttribute("lisDeptos", lisDeptos);
		modelo.addAttribute("lisSeleccionados", lisSeleccionados);
		
		return "taller/permiso/editar";
	}
	
	@RequestMapping("/taller/permiso/grabar")
	public String tallerPermisoGuardar(HttpServletRequest request, Model modelo){
		
		String ejercicioId  			= request.getParameter("EjercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("EjercicioId");
		List<ContCcosto> lisDeptos		= contCcostoDao.listCentrosCosto( ejercicioId,"S","ORDER BY ID_CCOSTO");
		String usuario 					= "0";		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");
		}
		
		for(ContCcosto depto : lisDeptos){		
			if(!becFijaDao.existeReg( ejercicioId, depto.getIdCcosto())){				
				if(request.getParameter(depto.getIdCcosto())!=null){					
					BecFija becFija = new BecFija();					
					becFija.setIdEjercicio(ejercicioId);
					becFija.setIdCcosto(depto.getIdCcosto());
					becFija.setFecha(aca.util.Fecha.getHoy());
					becFija.setUsuario(usuario);
					becFijaDao.insertReg(becFija);					
				}
			}else{
				if(request.getParameter(depto.getIdCcosto()) == null){
					becFijaDao.deleteReg(ejercicioId, depto.getIdCcosto());
				}
			}
		}
		
		return "redirect:/taller/permiso/editar";
	}
	
}