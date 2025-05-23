package aca.web.graduacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.archivo.spring.ArchDocAlumDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatDivision;
import aca.catalogo.spring.CatDivisionDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.graduacion.spring.AlumEgreso;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;


@Controller
public class ContGraduacionConsulta {	
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	AlumEventoDao alumEventoDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatDivisionDao catDivisionDao;

	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private ArchDocAlumDao archDocAlumDao;

	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	
	@RequestMapping("/graduacion/consulta/graduandos")
	public String graduacionEventosEventos(HttpServletRequest request, Model modelo){
		
		String eventoId 	= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String fechaEvento  = alumEventoDao.getFecha(eventoId);
		String ordenAlumno	= request.getParameter("OrdenNombre") == null ? "Apellido" : request.getParameter("OrdenNombre");
		String atuendo		= request.getParameter("Atuendo")==null ?"X":request.getParameter("Atuendo");
		String filtro 		= "'S','N'";
		if (atuendo.equals("S")) filtro = "'S'"; else if (atuendo.equals("N")) filtro = "'N'";
		List<AlumEvento> lisEventos	 				= alumEventoDao.getListAll(" ORDER BY TO_CHAR(ENOC.ALUM_EVENTO.FECHA, 'YYYY-MM-DD') DESC");
		if (eventoId.equals("0")&&lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}		
		List<AlumEgreso> lisAlumnos 					= new ArrayList<AlumEgreso>();
		if (ordenAlumno.equals("Apellido")){
			lisAlumnos 	= alumEgresoDao.getListaEvento( eventoId, filtro, " ORDER BY ENOC.ORDEN_GRADUA(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID) DESC, PLAN_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		}else {
			lisAlumnos 	= alumEgresoDao.getListaEvento( eventoId, filtro, " ORDER BY ENOC.ORDEN_GRADUA(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID) DESC, PLAN_ID, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");
		}
		
		boolean pendientes = false;
		if( krdxCursoActDao.listaPendientes(eventoId).size() > 0) {
			pendientes =  true;
		}
		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");			
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstados 			= catEstadoDao.getMapAll();				
		HashMap<String, CatDivision> mapaDivisiones		= catDivisionDao.getMapAll("");
		HashMap<String,AlumAcademico> mapaAcademico 	= alumAcademicoDao.mapaAlumnosEnGraduacion(eventoId);
		HashMap<String,AlumPersonal> mapaPersonal 		= alumPersonalDao.mapaAlumnosEnGraduacion(eventoId);
		HashMap<String, CatModalidad> mapCatMod 		= catModalidadDao.getMapAll("");
		HashMap<String, String> mapModalidad 			= alumAcademicoDao.getMapModalidad(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = "+eventoId+")");
		HashMap<String,AlumPlan> mapaAlumPlan		 	= alumPlanDao.mapaGraduandos(eventoId);
		HashMap<String, MapaPlan> mapMapaPlan 			= mapaPlanDao.mapPlanes("'I','V','A'");
		HashMap<String,String> mapaMaterias 			= krdxCursoActDao.mapaMateriasGraduandos(eventoId, "'I','5','6'");
		HashMap<String,AlumEgreso> mapaAlumGraEvento	= alumEgresoDao.mapaAlumGraduadosPorEvento(eventoId);
		HashMap<String,String> mapaAtuendos 			= alumPlanDao.mapaAtuendos(eventoId);
		HashMap<String,String> mapaNivelAlumGraduados	= alumEgresoDao.mapaNivelAlumGraduados(eventoId);
		HashMap<String,String> mapaDocEventoId			= archDocAlumDao.mapaDocEventoId(eventoId);
		HashMap<String,String> mapaPendientesPlan		= krdxCursoActDao.mapaPendientesPlan(eventoId);
				
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("atuendo", atuendo);
		modelo.addAttribute("pendientes", pendientes);
		modelo.addAttribute("fechaEvento", fechaEvento);
		modelo.addAttribute("ordenAlumno", ordenAlumno);
		modelo.addAttribute("numMex", alumPersonalDao.getNumMexicanos(eventoId));
		modelo.addAttribute("hombres", alumPersonalDao.getNumGenero(eventoId, "M"));
		modelo.addAttribute("mujeres", alumPersonalDao.getNumGenero(eventoId, "F"));
		modelo.addAttribute("solteros", alumPersonalDao.getNumEdoCivil(eventoId, "S"));
		modelo.addAttribute("casados", alumPersonalDao.getNumEdoCivil(eventoId, "C"));
		modelo.addAttribute("viudos", alumPersonalDao.getNumEdoCivil(eventoId, "V"));
		modelo.addAttribute("divorciados", alumPersonalDao.getNumEdoCivil(eventoId, "D"));
		modelo.addAttribute("internos", alumEgresoDao.getNumResidencia(eventoId, "I"));
		modelo.addAttribute("externos", alumEgresoDao.getNumResidencia(eventoId, "E"));
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		
		modelo.addAttribute("mapaFacultades", mapaFacultades);	
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaDivisiones", mapaDivisiones);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapCatMod", mapCatMod);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("mapMapaPlan", mapMapaPlan);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaAlumGraEvento", mapaAlumGraEvento);
		modelo.addAttribute("mapaAtuendos", mapaAtuendos);
		modelo.addAttribute("mapaNivelAlumGraduados", mapaNivelAlumGraduados);
		modelo.addAttribute("mapaDocEventoId", mapaDocEventoId);
		modelo.addAttribute("mapaPendientesPlan", mapaPendientesPlan);
		
		return "graduacion/consulta/graduandos";
	}
	
	@RequestMapping("/graduacion/consulta/updateProm")
	public String graduacionGraduacionUpdateProm(HttpServletRequest request, Model modelo) {
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje 		= "-";
		if (alumEgresoDao.updatePromedio(eventoId)) {
			mensaje = "GPA Updated";			
		}
		
		return "redirect:/graduacion/consulta/graduandos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/graduacion/consulta/pendientes")
	public String graduacionGraduacionPendientes(HttpServletRequest request, Model modelo) {
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		AlumEvento alumEvento = alumEventoDao.mapeaRegId(eventoId);

		List<KrdxCursoAct> listaPendientes = krdxCursoActDao.listaPendientes(eventoId);
		
		HashMap<String,AlumPersonal> mapaPersonal 		= alumPersonalDao.mapaAlumnosEnGraduacion(eventoId);
		HashMap<String, String> mapaCursos				= mapaCursoDao.mapaCursosEventoId(eventoId);
		HashMap<String, CargaGrupo> mapaGrupos			= cargaGrupoDao.mapaGruposEnGraduacion(eventoId);
		HashMap<String, String> mapaMaestros			= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String, String> mapCursosPlan			= mapaCursoDao.mapCursosPlan();		
		HashMap<String, String> mapCarreraPlan 			= mapaPlanDao.mapCarreraPlan();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.mapaCarreras();
		HashMap<String, CatFacultad> mapaFacultad		= catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("listaPendientes", listaPendientes);
		
		modelo.addAttribute("alumEvento", alumEvento);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapCursosPlan", mapCursosPlan);
		modelo.addAttribute("mapCarreraPlan", mapCarreraPlan);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		
		return "graduacion/consulta/pendientes";
	}
	
}
	
	