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

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;

@Controller
public class ContGraduacionTotal {

	@Autowired
	AlumEventoDao alumEventoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;
	

	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@RequestMapping("/graduacion/total/carreras")
	public String graduacionTotalGraduandos(HttpServletRequest request, Model modelo) {
		
		String eventoId						= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");		
		List<AlumEvento> lisEventos 		= alumEventoDao.getListAll("ORDER BY FECHA DESC");
		if (eventoId.equals("0")&&lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}		
		List<CatCarrera> lisCarreras 		= catCarreraDao.lisEnGraduacion(eventoId, "ORDER BY FACULTAD_ID,NOMBRE_CARRERA");
		HashMap<String,String> mapaTotales	= alumEgresoDao.mapaTotalAlumnosEnGraduacion(eventoId);
		HashMap<String,String> mapaGenero   = alumEgresoDao.mapaCarrerayGenero(eventoId);
		HashMap<String,String> mapaNiveles  = alumEgresoDao.mapaNivel(eventoId);
		
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaTotales", mapaTotales);
		modelo.addAttribute("mapaGenero", mapaGenero);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		
		return "graduacion/total/carreras";
	}
	
}