package aca.web.exalumnos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.exa.spring.ExaAlumEventoDao;
import aca.exa.spring.ExaEvento;
import aca.exa.spring.ExaEventoDao;

@Controller
public class ContExalumnosEvento {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ExaEventoDao exaEventoDao;
	
	@Autowired
	ExaAlumEventoDao exaAlumEventoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/exalumnos/evento/evento")
	public String exalumnosEventoEvento(HttpServletRequest request, Model modelo){		
		
		List<ExaEvento> lisEventos = exaEventoDao.lisTodos("ORDER BY FECHAEVENTO");
		HashMap<String,String> mapaRegistrados = exaAlumEventoDao.mapaAlumnosPorEvento();
		
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("mapaRegistrados", mapaRegistrados);
		
		return "exalumnos/evento/evento";
	}
	
	@RequestMapping("/exalumnos/evento/accion")
	public String exalumnosEventoAccion(HttpServletRequest request, Model modelo){	
		
		String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		ExaEvento exaEvento 	= new ExaEvento();
		boolean existe 			= false;
		if (exaEventoDao.existeReg(eventoId)) {
			exaEvento = exaEventoDao.mapeaRegIdEvento(eventoId);
			existe = true;			
		}
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("exaEvento", exaEvento);
		
		return "exalumnos/evento/accion";
	}
	
	@RequestMapping("/exalumnos/evento/grabar")
	public String exalumnosEventoGrabar(HttpServletRequest request, Model modelo){	
		String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String actualizacion 	= aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
		ExaEvento exaEvento 	= new ExaEvento();
		String mensaje	 		= "-";
		
		exaEvento.setEventoId(eventoId);
		exaEvento.setNombre(request.getParameter("Nombre")==null?"-":request.getParameter("Nombre"));
		exaEvento.setLugar(request.getParameter("Lugar")==null?"-":request.getParameter("Lugar"));
		exaEvento.setFechaEvento(request.getParameter("FechaEvento")==null?aca.util.Fecha.getHoy():request.getParameter("FechaEvento"));
		exaEvento.setFechaActualizacion(actualizacion);
		exaEvento.setIdEvento(request.getParameter("IdEvento")==null?"-":request.getParameter("IdEvento"));
		exaEvento.setEliminado(request.getParameter("Eliminado")==null?"0":request.getParameter("Eliminado"));
		if (!exaEventoDao.existeReg(eventoId)) {
			if (eventoId.equals("0")) {
				eventoId = exaEventoDao.maximoReg();
				exaEvento.setEventoId(eventoId);
			}
			if (exaEventoDao.insertReg(exaEvento)){
				mensaje = "Grabado: " +exaEvento.getEventoId();					
			} else {
				mensaje = "No Grabó: " + exaEvento.getEventoId();
			}
		} else {
			if (exaEventoDao.update(exaEvento)) {
				mensaje = "Modificado: " + exaEvento.getEventoId();					
			} else {
				mensaje = "No Cambió: " + exaEvento.getEventoId();
			}
		}
		
		return "redirect:/exalumnos/evento/accion?EventoId="+eventoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/exalumnos/evento/borrar")
	public String exalumnosEventoBorrar(HttpServletRequest request, Model modelo){	
		String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		ExaEvento exaEvento 	= new ExaEvento();
		String mensaje	 		= "-";		
		if (exaEventoDao.existeReg(eventoId)) {			
			if (exaEventoDao.eliminar(eventoId)){
				mensaje = "Borrado: " +exaEvento.getEventoId();					
			} else {
				mensaje = "No borró: " + exaEvento.getEventoId();
			}
		} else {
			mensaje = "No existe: " + eventoId;
		}
		
		return "redirect:/exalumnos/evento/evento?Mensaje="+mensaje;
	}	
}