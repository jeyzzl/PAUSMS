package aca.web.graduacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatDivision;
import aca.catalogo.spring.CatDivisionDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.graduacion.spring.AlumEgreso;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;
import aca.graduacion.spring.CarreraOrdenDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaAvanceDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContGraduacionGraduacion {	
	
	@Autowired
	private CarreraOrdenDao carreraOrdenDao;
	
	@Autowired
	private AlumEventoDao alumEventoDao;
	
	@Autowired
	private AlumEgresoDao alumEgresoDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;
	
	@Autowired
	private CatDivisionDao catDivisionDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private MapaAvanceDao mapaAvanceDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private ArchDocAlumDao archDocAlumDao;
	
	
	@RequestMapping("/graduacion/graduacion/alumnos")
	public String graduacionGraduacioAlumnos(HttpServletRequest request, Model modelo) {
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String ordenAlumno	= request.getParameter("OrdenNombre") == null ? "Apellido" : request.getParameter("OrdenNombre");
		
		List<AlumEvento> lisEventos	 							= alumEventoDao.getListAll(" ORDER BY TO_CHAR(ENOC.ALUM_EVENTO.FECHA, 'YYYY-MM-DD') DESC");
		if (eventoId.equals("0")&&lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}	
		
		List<AlumEgreso> lisAlumnos 					= alumEgresoDao.getListaEvento( eventoId, " ORDER BY ENOC.ORDEN_GRADUA(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID) DESC, CARRERA_ID, ENOC.NOMBRE_CARRERA(CARRERA_ID), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, String> mapaInscritos			= inscritosDao.getMapaInscritos();
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");	
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();	
		HashMap<String, CatEstado> mapaEstados 			= catEstadoDao.getMapAll();		
		HashMap<String, CatDivision> mapaDivisiones		= catDivisionDao.getMapAll("");
		HashMap<String, CatTipoAlumno> mapaTipos		= catTipoAlumnoDao.getMapAll("");
		HashMap<String,String> mapaOrden 				= carreraOrdenDao.mapaOrden();
		HashMap<String,AlumPersonal> mapaPersonal 		= alumPersonalDao.mapaAlumnosEnGraduacion(eventoId);
		HashMap<String,AlumAcademico> mapaAcademico 	= alumAcademicoDao.mapaAlumnosEnGraduacion(eventoId);
		HashMap<String,AlumPlan> mapaAlumPlan		 	= alumPlanDao.mapaGraduandos(eventoId);
		HashMap<String,MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'I','V','A'");
		HashMap<String,String> mapaMaterias 			= krdxCursoActDao.mapaMateriasGraduandos(eventoId, "'I','5','6'");
		HashMap<String,String> mapaNivelAlumGraduados	= alumEgresoDao.mapaNivelAlumGraduados(eventoId);
		HashMap<String,String> mapaDocEventoId			= archDocAlumDao.mapaDocEventoId(eventoId);
	
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("ordenAlumno", ordenAlumno);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaDivisiones", mapaDivisiones);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaOrden", mapaOrden);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaNivelAlumGraduados", mapaNivelAlumGraduados);
		modelo.addAttribute("mapaDocEventoId", mapaDocEventoId);

		
		return "graduacion/graduacion/alumnos";
	}
	
	@RequestMapping("/graduacion/graduacion/verificar")
	public String graduacionGraduacioVerificar(HttpServletRequest request, Model modelo){
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje 		= "-";
		int completos		= 0;
		int pendientes		= 0;
		
		List<AlumEgreso> lisAlumnos 				= alumEgresoDao.getListaEvento( eventoId, " ORDER BY ENOC.ORDEN_GRADUA(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID) DESC, CARRERA_ID, ENOC.NOMBRE_CARRERA(CARRERA_ID), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<aca.Mapa> lisCreditosRequeridos		= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapaCreditosAlumno	= new HashMap<String,String>();
		for (AlumEgreso egreso : lisAlumnos) {
			lisCreditosRequeridos 	= mapaAvanceDao.lisCreditosRequeridos(egreso.getPlanId(), " ORDER BY 1");
			mapaCreditosAlumno		= alumnoCursoDao.mapaCreditosAlumno(egreso.getPlanId(),egreso.getCodigoPersonal());
			boolean completo = true;
			for (aca.Mapa map : lisCreditosRequeridos) {
				if (mapaCreditosAlumno.containsKey(map.getLlave())) {
					if ( Integer.parseInt(mapaCreditosAlumno.get(map.getLlave())) < Integer.parseInt(map.getValor())){
						completo = false;
					}
				}
			}		
			if (completo){
				alumPlanDao.updateFinalizado("S", egreso.getCodigoPersonal(), egreso.getPlanId());
				completos++;
			}else{
				alumPlanDao.updateFinalizado("N", egreso.getCodigoPersonal(), egreso.getPlanId());
				pendientes++;
			}	
		}
		if (completos+pendientes > 0) mensaje = "Updated (complete="+completos+", pending="+pendientes+")";
		return "redirect:/graduacion/graduacion/alumnos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/graduacion/graduacion/updateProm")
	public String graduacionGraduacionUpdateProm(HttpServletRequest request, Model modelo) {
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje 		= "-";
		if (alumEgresoDao.updatePromedio(eventoId)) {
			mensaje = "Updated GPA";			
		}
		
		return "redirect:/graduacion/graduacion/alumnos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/graduacion/graduacion/editar_alumno")
	public String graduacionGraduacioEditarAlumno(HttpServletRequest request, Model modelo) {
		String codigoPersonal	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String planId 	= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String eventoId 	= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		
		List<String> planes = alumPlanDao.getPlanesAlumno(codigoAlumno);
		
		String nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String nombreEvento = alumEventoDao.mapeaRegId(eventoId).getEventoNombre();
		
		AlumEgreso alumno = new AlumEgreso();
		
		if (!alumEgresoDao.existeReg(eventoId,codigoAlumno)) {
			if(planId.equals("0")) {
				alumno.setPlanId(alumPlanDao.getPlanActual(codigoAlumno));
			}else {
				alumno.setPlanId(planId);
			}
			alumno.setCarreraId(alumPersonalDao.getCarreraId(alumno.getPlanId()));
			alumno.setAvance("");
			alumno.setTitulado("");
			alumno.setPromedio(String.valueOf(alumnoCursoDao.gpaAlumno(codigoAlumno, alumno.getPlanId())));
			
			alumno.setFecha(Fecha.getHoy());
			alumno.setUsuario(codigoPersonal);
		}else {
			alumno = alumEgresoDao.mapeaRegId(eventoId, codigoAlumno);
		}
		
		HashMap<String,MapaPlan> mapPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
				
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreEvento", nombreEvento);
		modelo.addAttribute("planes", planes);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("mapPlanes", mapPlanes);
		
		return "graduacion/graduacion/editar_alumno";
	}

	@RequestMapping("/graduacion/graduacion/grabaEditarAlumno")
	public String graduacionGraduacioGrabaEditarAlumno(HttpServletRequest request, Model modelo) {
		String eventoId 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String codigoAlumno 	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String planId 			= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String permiso			= request.getParameter("Permiso") == null ? "0" : request.getParameter("Permiso");
		String comentario		= request.getParameter("Comentario") == null ? "0" : request.getParameter("Comentario");
		String codigoPersonal	= "0";
		String mensaje 			= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		AlumEgreso alumno = new AlumEgreso();
		
		alumno.setEventoId(eventoId);
		alumno.setCodigoPersonal(codigoAlumno);
		
		if (!alumEgresoDao.existeReg(eventoId,codigoAlumno)) {

			alumno.setPlanId(planId);
			alumno.setCarreraId(alumPersonalDao.getCarreraId(alumno.getPlanId()));
			alumno.setAvance(request.getParameter("Avance"));
			alumno.setTitulado(request.getParameter("Titulado"));
			alumno.setPromedio(request.getParameter("Promedio"));
			alumno.setFecha(Fecha.getHoy());
			alumno.setUsuario(codigoPersonal);
			alumno.setPermiso(permiso);
			alumno.setConfirmar("N");
			alumno.setComentario(permiso);
			
			if (alumEgresoDao.insertReg(alumno)) {
				eventoId = alumno.getEventoId();
				codigoAlumno = alumno.getCodigoPersonal();
				mensaje = "Saved correctly";					
			} else {
				mensaje = "There was an error saving student";
			}
		} else {
			alumno = alumEgresoDao.mapeaRegId(eventoId, codigoAlumno);
			alumno.setPlanId(planId);
			alumno.setCarreraId(alumPersonalDao.getCarreraId(alumno.getPlanId()));
			alumno.setAvance(request.getParameter("Avance"));
			alumno.setTitulado(request.getParameter("Titulado"));
			alumno.setPromedio(request.getParameter("Promedio"));
			alumno.setFecha(Fecha.getHoy());
			alumno.setUsuario(codigoPersonal);
			alumno.setPermiso(permiso);			
			alumno.setComentario(comentario);
			if (alumEgresoDao.updateReg(alumno)) {
				mensaje = "Updated student";					
			} else {
				mensaje = "There was an error updating student";
			}
		}
		
		return "redirect:/graduacion/graduacion/editar_alumno?EventoId="+eventoId+"&CodigoAlumno="+codigoAlumno+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/graduacion/graduacion/borrarAlumno")
	public String graduacionGraduacioBorrarAlumno(HttpServletRequest request, Model modelo) {
		String eventoId 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String codigoAlumno 	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");		
		String mensaje 			= "-";
		
		if (alumEgresoDao.existeReg(eventoId,codigoAlumno)) {			
			if (alumEgresoDao.deleteReg(eventoId,codigoAlumno)) {	
				mensaje = "Deleted student";					
			} else {
				mensaje = "There was an error deleting student";
			}
		}		
		return "redirect:/graduacion/graduacion/alumnos?EventoId="+eventoId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/graduacion/graduacion/orden")
	public String graduacionGraduacioOrden(HttpServletRequest request, Model modelo) {
		
		return "graduacion/graduacion/orden";
	}
	
	@RequestMapping("/graduacion/graduacion/grabar")
	public String graduacionGraduacioGrabar(HttpServletRequest request, Model modelo) {
		
		String carreraId	= request.getParameter("CarreraId")==null?"":request.getParameter("CarreraId");
		String orden		= request.getParameter("Orden")==null?"":request.getParameter("Orden");
		
		aca.graduacion.spring.CarreraOrden carreraOrden = new aca.graduacion.spring.CarreraOrden();
		carreraOrden.setCarreraId(carreraId);
		carreraOrden.setOrden(orden);
		if (carreraOrdenDao.existeReg(carreraId)){
			carreraOrdenDao.updateReg(carreraOrden);
		}else{
			carreraOrdenDao.insertReg(carreraOrden);
		}		
		return "redirect:/graduacion/graduacion/alumnos";
	}	
	
	@RequestMapping("/graduacion/graduacion/cambiar")
	public String graduacionGraduacioCambiar(HttpServletRequest request, Model modelo) {
		
		String eventoId			= request.getParameter("EventoId")==null?"":request.getParameter("EventoId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
		String confirmar		= "-";
		if (alumEgresoDao.existeReg(eventoId, codigoAlumno)) {
			confirmar = alumEgresoDao.mapeaRegId(eventoId, codigoAlumno).getConfirmar();
			if (confirmar.equals("S")) {
				alumEgresoDao.updateConfirmar(eventoId, codigoAlumno, "N");
			}else {
				alumEgresoDao.updateConfirmar(eventoId, codigoAlumno, "S");
			}
		}
		
		return "redirect:/graduacion/graduacion/alumnos";
	}
	
}