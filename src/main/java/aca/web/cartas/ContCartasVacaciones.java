package aca.web.cartas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumVacacion;
import aca.alumno.spring.AlumVacacionDao;

import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;

@Controller
public class ContCartasVacaciones {	
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AlumVacacionDao alumVacacionDao;	
	
	
	@RequestMapping("/cartas/vacaciones/vacaciones")
	public String cartasVacacionesVacaciones(HttpServletRequest request, Model modelo){		

		List<AlumVacacion> lisPeriodos						= alumVacacionDao.getListAll("ORDER BY 1");	
		HashMap<String,CatNivel> mapaNiveles				= catNivelDao.getMapAll("");
		HashMap<String,String> mapaModalidades				= catModalidadDao.mapModalidades("");			
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "cartas/vacaciones/vacaciones";
	}
	
	@RequestMapping("/cartas/vacaciones/editar")
	public String cartasVacacionesEditar(HttpServletRequest request, Model modelo){
		
		String nivelId 			= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");
		String modalidadId 		= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		
		AlumVacacion vacacion = new AlumVacacion();
		
		if(alumVacacionDao.existeReg(nivelId, modalidadId)){
			vacacion = alumVacacionDao.mapeaRegId(nivelId, modalidadId);
		}
		
		List<CatNivel> listaNivel								= catNivelDao.getListAll("ORDER BY 1");
		List<CatModalidad> listaModalidad						= catModalidadDao.getListAll("ORDER BY 1");
		
		modelo.addAttribute("vacacion", vacacion);
		modelo.addAttribute("listaNivel", listaNivel);
		modelo.addAttribute("listaModalidad", listaModalidad);
		
		return "cartas/vacaciones/editar";
	}
	
	@RequestMapping("/cartas/vacaciones/grabar")
	public String cartasVacacionesGrabar(HttpServletRequest request){
		
		String nivelId 			= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");
		String modalidadId		= request.getParameter("ModalidadId")==null?"-":request.getParameter("ModalidadId");
		String fExamen		 	= request.getParameter("FExamen")==null?"0":request.getParameter("FExamen");
		String fInicio			= request.getParameter("FInicio")==null?"0":request.getParameter("FInicio");
		String fFinal			= request.getParameter("FFinal")==null?"0":request.getParameter("FFinal");
		String mensaje			= "-";
		
		AlumVacacion vacacion = new AlumVacacion();
		vacacion.setNivelId(nivelId);
		vacacion.setModalidadId(modalidadId);
		vacacion.setfExamen(fExamen);
		vacacion.setfInicio(fInicio);
		vacacion.setfFinal(fFinal);
		
		if(alumVacacionDao.existeReg(nivelId, modalidadId)){
			if (alumVacacionDao.updateReg(vacacion)){
				mensaje = "Registro modificado";
			}
		}else {
			if (alumVacacionDao.insertReg(vacacion)){
				mensaje = "Registro creado";
			}
		}
		
		return "redirect:/cartas/vacaciones/editar?NivelId="+nivelId+"&ModalidadId="+modalidadId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/cartas/vacaciones/borrar")
	public String cartasVacacionesBorrar(HttpServletRequest request){
		
		String nivelId 			= request.getParameter("AreaId")==null?"0":request.getParameter("nivelId");
		String modalidadId		= request.getParameter("NombreArea")==null?"-":request.getParameter("modalidadId");
		
		if(alumVacacionDao.existeReg(nivelId, modalidadId)){
			alumVacacionDao.deleteReg(nivelId, modalidadId);
		}
		
		return "redirect:/cartas/vacaciones/vacaciones";
	}
	
}