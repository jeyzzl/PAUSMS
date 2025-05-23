package aca.web.cultural;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.cultural.spring.CompAsistencia;
import aca.cultural.spring.CompAsistenciaAlumnoDao;
import aca.cultural.spring.CompAsistenciaDao;

@Controller
public class ContCulturalEvento {	
	
	@Autowired	
	CompAsistenciaDao compAsistenciaDao;
	
	@Autowired
	CompAsistenciaAlumnoDao compAsistenciaAlumnoDao;
	
	
	@RequestMapping("/cultural/evento/listado")
	public String culturalEventoListado(HttpServletRequest request, Model modelo){		
		List<CompAsistencia> lisEventos 	= compAsistenciaDao.lisTodos(" ORDER BY FECHA");
		HashMap<String,String> mapaAlumnos 	= compAsistenciaAlumnoDao.mapaAlumnos();
		
		modelo.addAttribute("lisEventos", lisEventos);	
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "cultural/evento/listado";
	}
	
	@RequestMapping("/cultural/evento/editar")
	public String culturalEventoEditar(HttpServletRequest request, Model modelo){	
		
		String eventoId	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		CompAsistencia compAsistencia = new CompAsistencia();
		if (compAsistenciaDao.existeReg(eventoId)) {
			compAsistencia = compAsistenciaDao.mapeaRegId(eventoId);
		}
				
		modelo.addAttribute("compAsistencia", compAsistencia);	
		
		return "cultural/evento/editar";
	}
	
	@RequestMapping("/cultural/evento/grabar")
	public String culturalEventoGrabar(HttpServletRequest request, Model modelo){	
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String nombre		= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String fecha		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String estado		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String hora			= request.getParameter("Hora")==null?"0":request.getParameter("Hora");
		String mensaje		= "-";
		
		CompAsistencia compAsistencia = new CompAsistencia();
		compAsistencia.setEventoId(eventoId);
		compAsistencia.setNombre(nombre);
		compAsistencia.setFecha(fecha);
		compAsistencia.setEstado(estado);
		compAsistencia.setHora(hora);		
		if (compAsistenciaDao.existeReg(eventoId)){
			if (compAsistenciaDao.updateReg(compAsistencia)){
				mensaje = "¡Se actualizó el evento!"; 
			}else{
				mensaje = "¡Error al grabar!"; 
			}			
		}else {
			eventoId = compAsistenciaDao.maxReg();
			compAsistencia.setEventoId(eventoId);
			if (compAsistenciaDao.insertReg(compAsistencia)){
				mensaje = "¡Evento grabado!"; 
			}else{
				mensaje = "¡Error al grabar!";
			}
		}		
		return "redirect:/cultural/evento/editar?EventoId="+eventoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/cultural/evento/borrar")
	public String culturalEventoBorrar(HttpServletRequest request){	
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");		
		String mensaje		= "-";			
		if (compAsistenciaDao.existeReg(eventoId)){
			if (compAsistenciaDao.deleteReg(eventoId)){
				mensaje = "¡Se borró el evento!"; 
			}else{
				mensaje = "¡Error al borrar!"; 
			}		
		}	
		return "redirect:/cultural/evento/listado?Mensaje="+mensaje;
	}
	
}