package aca.web.mentores;

import java.util.ArrayList;
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

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumActualiza;
import aca.alumno.spring.AlumActualizaDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.emp.spring.EmpleadoDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMentoresAconsejados {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	CatEdificioDao catEdificioDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	AlumActualizaDao mapaActualizaDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@RequestMapping("/mentores/aconsejados/carrera")
	public String mentoresAconsejadosCarrera(HttpServletRequest request, Model modelo){		
		String codigoUsuario 	= "0";
		boolean admin 			= false;
		String numOrden 		= request.getParameter("Ordenar") == null ? "1" : request.getParameter("Ordenar");
		String periodoId 		= catPeriodoDao.getPeriodo();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoUsuario 	= (String) sesion.getAttribute("codigoPersonal");
        	admin 			= accesoDao.esAdministrador(codigoUsuario);
        }	
		
		String orden = "";
		
		// Define el orden por nombre, mentor, carrera
		if (numOrden.equals("1")) {
			orden = " ORDER BY CARRERA_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
		}
		if (numOrden.equals("2")) {
			orden = " ORDER BY CARRERA_ID, MENTOR_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
		}
		if (numOrden.equals("3")) {
			orden = " ORDER BY CARRERA_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
		}
		
		// Inicializa el arreglo de los aconsejados
		List<MentAlumno> lisAmentorados = new ArrayList<MentAlumno>();
		HashMap<String, AlumEstado> mapInscritos		= alumEstadoDao.mapaInscritos(" ORDER BY CARGA_ID, CODIGO_PERSONAL");
		HashMap<String,CatFacultad> mapFacultad			= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapCarrera 			= catCarreraDao.getMapAll("");
		HashMap<String,String> mapAlumnos 				= alumPersonalDao.mapMentAlumno(periodoId);
		HashMap<String,Maestros> mapMaestros 			= maestrosDao.mapaMaestros();
		HashMap<String,AlumActualiza> mapActualizados	= mapaActualizaDao.mapaActualiza();
		HashMap<String,String> mapResidencias			= alumAcademicoDao.mapaResidencia(periodoId);
		HashMap<String,AlumPlan> mapGrado				= alumPlanDao.mapaAlumnosConMentor(periodoId);
		
		if (admin) {
			lisAmentorados = mentAlumnoDao.getListAlumTodos(periodoId, orden);
		} else if (catFacultadDao.esDirector(codigoUsuario)) {
			lisAmentorados = mentAlumnoDao.getListAlumFac(periodoId, codigoUsuario, orden);
		} else {
			lisAmentorados = mentAlumnoDao.getListAlumCoordinador(periodoId, codigoUsuario, orden);
		}
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisAmentorados", lisAmentorados);
		modelo.addAttribute("mapInscritos", mapInscritos);		
		modelo.addAttribute("mapFacultad", mapFacultad);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("mapGrado", mapGrado);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapMaestros", mapMaestros);
		modelo.addAttribute("mapActualizados", mapActualizados);
		modelo.addAttribute("mapResidencias", mapResidencias);
		
		return "mentores/aconsejados/carrera";
	}
	
	@RequestMapping("/mentores/aconsejados/subir")
	public String mentoresAconsejadosSubir(HttpServletRequest request, Model modelo){
		String codigoAlumno		= request.getParameter("f_codigo_personal")==null?"0":request.getParameter("f_codigo_personal");		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	if (alumPersonalDao.existeReg(codigoAlumno)){    			
    			sesion.setAttribute("codigoAlumno", codigoAlumno);
    		}        	
        }		
		return "redirect:/portales/portalAlumno/portal";
	}
	
}