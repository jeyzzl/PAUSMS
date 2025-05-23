package aca.web.exalumnos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.exa.spring.ExaAlumEvento;
import aca.exa.spring.ExaAlumEventoDao;
import aca.exa.spring.ExaCorreo;
import aca.exa.spring.ExaCorreoDao;
import aca.exa.spring.ExaEvento;
import aca.exa.spring.ExaEventoDao;
import aca.exa.spring.ExaTelefono;
import aca.exa.spring.ExaTelefonoDao;

@Controller
public class ContExalumnosAsistencia {	
	
	@Autowired
	ExaEventoDao exaEventoDao;
	
	@Autowired
	ExaCorreoDao exaCorreoDao;
	
	@Autowired
	ExaTelefonoDao exaTelefonoDao;
	
	@Autowired
	ExaAlumEventoDao exaAlumEventoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	
	@RequestMapping("/exalumnos/asistencia/evento")
	public String exalumnosExalumnosEvento(HttpServletRequest request, Model modelo){
		
		List<ExaEvento> lisEventos 	= exaEventoDao.lisTodos(" ORDER BY TO_CHAR(ENOC.EXA_EVENTO.FECHAEVENTO,'YYYY/MM/DD')");
		
		modelo.addAttribute("lisEventos", lisEventos);		
		
		return "exalumnos/asistencia/evento";
	}
	
	@RequestMapping("/exalumnos/asistencia/exalumnos")
	public String exalumnosExalumnosExalumnos(HttpServletRequest request, Model modelo){		
		
		String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String eventoNombre		= exaEventoDao.getNombre(eventoId); 
		String codigoAlumno 	= "0";
		String nombreAlumno 	= "-";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
        	nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        }
        
		List<ExaAlumEvento> lisAlumnos 				= exaAlumEventoDao.lisPorEvento(eventoId, "ORDER BY 1");
		
		//Mapa de correos
		HashMap<String, ExaCorreo> mapCorreos 		= exaCorreoDao.getMapCorreo("");	
		//Mapa de telefonos
		HashMap<String, ExaTelefono> mapTelefonos 	= exaTelefonoDao.getMapTelefono("");
		HashMap<String, AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosEnExalumnos(eventoId);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("eventoNombre", eventoNombre);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapCorreos", mapCorreos);
		modelo.addAttribute("mapTelefonos", mapTelefonos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		
		return "exalumnos/asistencia/exalumnos";
	}
	
	@RequestMapping("/exalumnos/asistencia/agregar")
	public String exalumnosExalumnosAgregar(HttpServletRequest request){		
		
		String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String codigoAlumno 	= "0";
		String nombreAlumno 	= "-";
		String mensaje			= "";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
        	nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        }			
        
		if(!exaAlumEventoDao.existeAlumno(eventoId, codigoAlumno)){
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();

			ExaAlumEvento evento = new ExaAlumEvento();
			evento.setAlumEventoId(exaAlumEventoDao.maximoReg());
			evento.setMatricula(codigoAlumno);
			evento.setIdEvento(eventoId);
			evento.setFechaActualizacion(tiempo);
			evento.setEliminado("0");
			evento.setIdEventoAsistido("-");
			
			if(exaAlumEventoDao.insertReg(evento)){
				mensaje = "Se agrego el alumno: "+nombreAlumno;
			}else{
				mensaje = "Error al agregar el alumno: "+nombreAlumno;
			}
		}else{
			mensaje="Este alumno ya esta inscrito en este evento";
		}	
        
		return "redirect:/exalumnos/asistencia/exalumnos?EventoId="+eventoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/exalumnos/asistencia/borrar")
	public String exalumnosExalumnosBorrar(HttpServletRequest request){		
		
		String eventoId 			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String alumEventoId 		= request.getParameter("AlumEventoId")==null?"0":request.getParameter("AlumEventoId");		
		String mensaje			= "";
		
		if(exaAlumEventoDao.existeReg(alumEventoId)){		
			if(exaAlumEventoDao.eliminar(alumEventoId)){
				mensaje = "¡Se eliminó el registro!";
			}else{
				mensaje = "Error al eliminar el registro";
			}
		}else{
			mensaje="Este alumno no está registrado en este evento";
		}	
        
		return "redirect:/exalumnos/asistencia/exalumnos?EventoId="+eventoId+"&Mensaje="+mensaje;
	}
	
}