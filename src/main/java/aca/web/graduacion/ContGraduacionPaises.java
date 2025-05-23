package aca.web.graduacion;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAtuendo;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;

@Controller
public class ContGraduacionPaises {

	@Autowired
	AlumEventoDao alumEventoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;

	@Autowired
	ParametrosDao parametrosDao;
	
	@RequestMapping("/graduacion/paises/graduandos")
	public String graduacionPaisesGraduandos(HttpServletRequest request, Model modelo) {
		
		String eventoId						= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		List<AlumEvento> lisEventos 		= alumEventoDao.getListAll("ORDER BY FECHA DESC");
		if (eventoId.equals("0")&&lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}
		
		// Lista de paises con graduandos en el evento de graduacion 
		List<CatPais> lisPaises 			= catPaisDao.lisGraduandos(eventoId, " ORDER BY NOMBRE_PAIS");
		
		// Map que cuenta el numero de graduandos por pais en el evento seleccionado 
		HashMap<String,String> mapaPaises	= alumEgresoDao.mapaTotPorPaisEnGraduacion(eventoId);
		
		// Lista de alumnos en el evento de graduacion
		List<AlumPersonal> lisAlumnos 			= alumPersonalDao.listGraduandos(eventoId, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");	
		
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		
		return "graduacion/paises/graduandos";
	}
	
	@RequestMapping("/graduacion/paises/estados")
	public String graduacionPaisesEstados(HttpServletRequest request, Model modelo) {
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");	
		String eventoNombre	= alumEventoDao.mapeaRegId(eventoId).getEventoNombre(); 
		Parametros parametros = parametrosDao.mapeaRegId("1");

		List<CatEstado> lisEstados 					= catEstadoDao.lisEstadosGraduandos(parametros.getPaisId(), eventoId, " ORDER BY NOMBRE_ESTADO"); 
		HashMap<String,String> mapaTotPorEstados	= alumEgresoDao.mapaTotPorEstadoEnGraduacion(eventoId);
		
		modelo.addAttribute("eventoNombre", eventoNombre);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("mapaTotPorEstados", mapaTotPorEstados);
		
		return "graduacion/paises/estados";
	}
	
}