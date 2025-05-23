package aca.web.extranjeros;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;


@Controller
public class ContExtranjerosNacionalidad {	
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@RequestMapping("/extranjeros/nacionalidad/cambia")
	public String extranjerosNacionalidadCambia(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= "0";		
		String paisNombre			= "";
		AlumPersonal alumPersonal 	= new AlumPersonal();
		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
			alumPersonal			= alumPersonalDao.mapeaRegId(codigoAlumno);
			paisNombre 				= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
		}
		
		List<CatPais> lisPaises 	= catPaisDao.getListAll(" ORDER BY NOMBRE_PAIS");		
		
		modelo.addAttribute("alumPersonal", alumPersonal);	
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("lisPaises", lisPaises);
		
		return "extranjeros/nacionalidad/cambia";
	}	
	
	@RequestMapping("/extranjeros/nacionalidad/grabar")
	public String extranjerosNacionalidadGrabar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= "0";
		String nacionalidad			= request.getParameter("Nacionalidad")==null?"0":request.getParameter("Nacionalidad");		
		String mensaje 				= "-";
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");			
			if (alumPersonalDao.existeReg(codigoAlumno)){				
				if (alumPersonalDao.updateNacionalidad(codigoAlumno, nacionalidad)) {
					mensaje = "¡Grabado!";
				}else {
					mensaje = "¡Error al grabar!";
				}
			}	
		}		
		return "redirect:/extranjeros/nacionalidad/cambia?Mensaje="+mensaje;
	}	
}