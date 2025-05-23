package aca.web.mentores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAgenda;
import aca.alumno.spring.AlumAgendaDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMentoresAgenda {	

	@Autowired
	AlumAgendaDao alumAgendaDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaAlumnoDao cargaAlumnoDao;	
	
	@RequestMapping("/mentores/agenda/listado")
	public String mentoresAgendaListado(HttpServletRequest request, Model modelo){
		
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String entregado		= request.getParameter("Entregado")==null?"0":request.getParameter("Entregado");
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		List<AlumAgenda> lisAlumnos 		= new ArrayList<AlumAgenda>();
		if (carreraId.equals("0")) {
			lisAlumnos 		= alumAgendaDao.lisTodos("ORDER BY ENTREGADO DESC, ALUM_APELLIDO(CODIGO_PERSONAL)");
		}else {
			lisAlumnos 		= alumAgendaDao.lisPorCarrera(carreraId, "ORDER BY ENTREGADO DESC, ALUM_APELLIDO(CODIGO_PERSONAL)");
		}
		if (accion.equals("1")){
			for (AlumAgenda agenda : lisAlumnos){
				if (alumAgendaDao.existeReg(agenda.getCodigoPersonal()) && agenda.getEntregado().equals("0")){
					alumAgendaDao.updateEntregado(agenda.getCodigoPersonal(), entregado);
				}
			}			
			// Actualizar datos
			if (carreraId.equals("0")) {
				lisAlumnos 		= alumAgendaDao.lisTodos("ORDER BY ENTREGADO DESC, ALUM_APELLIDO(CODIGO_PERSONAL)");
			}else {
				lisAlumnos 		= alumAgendaDao.lisPorCarrera(carreraId, "ORDER BY ENTREGADO DESC, ALUM_APELLIDO(CODIGO_PERSONAL)");
			}			
		}
		List<CatCarrera> lisCarreras 				= catCarreraDao.lisCarrerasEnAgenda("ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosConAgenda();
		HashMap<String,String> mapaCarreraAlumno 	= cargaAlumnoDao.mapaCarreraDeAlumnos();
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCarreraAlumno", mapaCarreraAlumno);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "mentores/agenda/listado";
	}
	
	@RequestMapping("/mentores/agenda/actualizar")
	public String mentoresAgendaActualizar(HttpServletRequest request){
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		alumAgendaDao.deleteTraspaso();
		alumAgendaDao.copiarDatos();
		alumAgendaDao.actualizarListado("22231A", "2");
		alumAgendaDao.actualizarListado("22233A", "1");
		return "redirect:/mentores/agenda/listado?CarreraId="+carreraId;
	}
	
	@RequestMapping("/mentores/agenda/grabar")
	public String mentoresAgendaGrabar(HttpServletRequest request){
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String entregado		= request.getParameter("Entregado")==null?"0":request.getParameter("Entregado");
		
		alumAgendaDao.updateEntregado(codigoAlumno, entregado);
		
		return "redirect:/mentores/agenda/listado?CarreraId="+carreraId+"&Entregado="+entregado;
	}
	
	@RequestMapping("/mentores/agenda/quitar")
	public String mentoresAgendaQuitar(HttpServletRequest request){
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String entregado		= request.getParameter("Entregado")==null?"0":request.getParameter("Entregado");
		
		alumAgendaDao.reiniciaEntregados(entregado);		
				
		return "redirect:/mentores/agenda/listado?CarreraId="+carreraId+"&Entregado="+entregado;
	}	
	
}