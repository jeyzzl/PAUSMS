package aca.web.graduacion;

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

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocDocumentosDao;
import aca.ssoc.spring.SsocSectorDao;

@Controller
public class ContGraduacionEventos {
	
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
	
	@Autowired
	AlumEventoDao alumEventoDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocDocumentosDao ssocDocumentosDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;
	

	@RequestMapping("/graduacion/eventos/eventos")
	public String graduacionEventosEventos(HttpServletRequest request, Model modelo){
		
		List<AlumEvento> lisEvento 				= alumEventoDao.getListAll("ORDER BY FECHA DESC");
		HashMap<String,String> mapaAlumnos		= alumEgresoDao.mapaTotAlumnos();
		 
		modelo.addAttribute("lisEvento",lisEvento);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		
		return "graduacion/eventos/eventos";
	}
	
	@RequestMapping("/graduacion/eventos/editar")
	public String graduacionEventosEditar(HttpServletRequest request, Model model){
		
		String eventoId				= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		AlumEvento alumEvento		= new AlumEvento();		
		if (alumEventoDao.existeReg(eventoId)) {
			alumEvento = alumEventoDao.mapeaRegId(eventoId);
		}
		
		model.addAttribute("alumEvento", alumEvento);
		
		return "graduacion/eventos/editar";
	}
	
	@RequestMapping("/graduacion/eventos/grabarEvento")
	public String graduacionEventosGrabarEvento(HttpServletRequest request, Model model){		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String eventoNombre		= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String fecha			= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String estado			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String archivo			= request.getParameter("Archivo")==null?"N":request.getParameter("Archivo");
		String mensaje 			= "-"; 
		
		AlumEvento alumEvento		= new AlumEvento();		
		if (alumEventoDao.existeReg(eventoId)) {
			alumEvento.setEventoId(eventoId);
			alumEvento.setEventoNombre(eventoNombre);
			alumEvento.setFecha(fecha);
			alumEvento.setEstado(estado);
			alumEvento.setArchivo(archivo);
			if (alumEventoDao.updateReg(alumEvento)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else{
			alumEvento.setEventoId(alumEventoDao.maximoReg());
			alumEvento.setEventoNombre(eventoNombre);
			alumEvento.setFecha(fecha);
			alumEvento.setEstado(estado);
			alumEvento.setArchivo(archivo);
			if (alumEventoDao.insertReg(alumEvento)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}	
		
		return "redirect:/graduacion/eventos/eventos?Mensaje="+mensaje;
	}

	@RequestMapping("/graduacion/eventos/borrarEvento")
	public String graduacionEventosBorrarEvento(HttpServletRequest request, Model model){
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje 		= "-"; 
		if (alumEventoDao.existeReg(eventoId)) {
			if (alumEventoDao.deleteReg(eventoId)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}
		}
		
		return "redirect:/graduacion/eventos/eventos?Mensaje="+mensaje;
	}
}
	
	