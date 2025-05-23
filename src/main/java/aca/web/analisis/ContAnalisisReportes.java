package aca.web.analisis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAnalisisReportes {
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;

	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CatTipoCalDao catTipoCalDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired	
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired	
	private CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired	
	private KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	@Autowired	
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	private CatTipoCursoDao catTipoCursoDao;
	
	@RequestMapping("/analisis/reportes/menu")
	public String analisisReportesMenu(HttpServletRequest request){
		
		return "analisis/reportes/menu";
	}	
	
	@RequestMapping("/analisis/reportes/carga")
	public String analisisReportesCarga(HttpServletRequest request, Model modelo){
		
		String cargaSesion		= "000000";
		String periodoSesion	= "0000";
		String codigoPersonal	= "0";
		Acceso acceso 			= new Acceso();
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 	= (String)sesion.getAttribute("cargaId");
        	periodoSesion 	= (String)sesion.getAttribute("periodo"); //cargaDao.getPeriodo(cargaSesion);
        	codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	acceso 			= accesoDao.mapeaRegId(codigoPersonal);
        }
        
        String periodoId	= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		if (!periodoId.equals("0000") && !periodoId.equals(periodoSesion) && sesion!=null) {
			sesion.setAttribute("periodo", periodoId);			
			cargaId 	= "000000";
		}else if (!periodoId.equals("0000") && !cargaId.equals("000000") && sesion!=null){
			sesion.setAttribute("cargaId", cargaId);			
		}else if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion;
		}
		
        
        List<CatPeriodo> lisPeriodos	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		List<Carga> lisCargas			= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		List<AlumnoCurso> lisAlumnos	= alumnoCursoDao.lisAlumnosEnComponentes(cargaId, "'I','1','2','4','5','6'", "1,2,3,4,5", " ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID, ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, CatTipoCal> mapaTipos			= catTipoCalDao.getMapAll("");
		HashMap<String, String> mapaMaestros			= maestrosDao.mapaMaestroEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapAlumnosEnCarga(cargaId, "NOMBRE");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "analisis/reportes/carga";
	}	
	
	@RequestMapping("/analisis/reportes/analisis")
	public String analisisMateriasAnalisis(HttpServletRequest request, Model modelo){
		
		String cargaId 						= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		List<Carga> lisCargas 				= cargaDao.getListAll(" ORDER BY ORDEN DESC");
		if (cargaId.equals("0") && lisCargas.size() >= 1) cargaId = lisCargas.get(0).getCargaId();
		List<CargaAcademica> lisMaterias 	= cargaAcademicaDao.lisMateriasEnCarga(cargaId, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))");
		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaAlumnos				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'I','1','2','3','4','5','6'");
		HashMap<String, String> mapaInscritos			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'I'");
		HashMap<String, String> mapaAcreditados			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'1'");
		HashMap<String, String> mapaReprobados			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'2','4'");
		HashMap<String, String> mapaBajas				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'3'");
		HashMap<String, String> mapaPendientes			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'5','6'");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaAcreditados", mapaAcreditados);
		modelo.addAttribute("mapaReprobados", mapaReprobados);
		modelo.addAttribute("mapaBajas", mapaBajas);
		modelo.addAttribute("mapaPendientes", mapaPendientes);
		
		return "analisis/reportes/analisis";
	}	

	@RequestMapping("/analisis/reportes/concentrado")
	public String analisisReportesConcentrado(HttpServletRequest request, Model modelo){
		String cargaId 						= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		List<Carga> lisCargas 				= cargaDao.getListAll(" ORDER BY ORDEN DESC");
		if (cargaId.equals("0") && lisCargas.size() >= 1) cargaId = lisCargas.get(0).getCargaId();
		List<CargaAcademica> lisMaterias 	= cargaAcademicaDao.getListaMaestro(cargaId, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))");
		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaAlumnos				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'M','I','1','2','3','4','5','6'");
		HashMap<String, String> mapaAsignados			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'M'");
		HashMap<String, String> mapaInscritos			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'I'");
		HashMap<String, String> mapaAcreditados			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'1'");
		HashMap<String, String> mapaReprobados			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'2','4'");
		HashMap<String, String> mapaBajas				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'3'");
		HashMap<String, String> mapaPendientes			= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'5','6'");
		HashMap<String, String> mapaPromediosPorMateria	= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'M','3'");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaAcreditados", mapaAcreditados);
		modelo.addAttribute("mapaReprobados", mapaReprobados);
		modelo.addAttribute("mapaBajas", mapaBajas);
		modelo.addAttribute("mapaPendientes", mapaPendientes);
		modelo.addAttribute("mapaPromediosPorMateria", mapaPromediosPorMateria);
		
		return "analisis/reportes/concentrado";
	}	

	@RequestMapping("/analisis/reportes/estrategias")
	public String analisisEstrategiasEstrategias(HttpServletRequest request, Model modelo){
		Acceso acceso = new Acceso();
		String codigoUsuario = "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoUsuario = (String) sesion.getAttribute("codigoPersonal");
	    }
	    
	    acceso = accesoDao.mapeaRegId(codigoUsuario);
		
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		List<Carga> lisCargas 		= cargaDao.getListAll("ORDER BY CARGA_ID DESC,NOMBRE_CARGA");
		
		if (cargaId.equals("0") && lisCargas.size() >= 1) cargaId = lisCargas.get(0).getCargaId();
		List<CargaAcademica> lisMaterias 	= cargaAcademicaDao.getListaMaestro(cargaId, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))");
		
		List<String> lisFechas					= cargaDao.getRangoCarga(cargaId);
		List<String> lisEvaluacion 				= cargaGrupoEvaluacionDao.getEstrategiasMes(cargaId, "ORDER BY CURSO_CARGA_ID");
		
		HashMap<String, String> mapActividades 	= cargaGrupoActividadDao.getActividadesMes(cargaId, "ORDER BY CURSO_CARGA_ID, EVALUACION_ID");
		HashMap<String,String> mapEval 			= krdxAlumnoEvalDao.mapCuentaEval(cargaId);
		HashMap<String,String> mapVirtual		= cargaGrupoDao.mapGrupoRegistro(cargaId, " WHERE EVALUACION_E42 = 0");
		HashMap<String,String> mapE42			= cargaGrupoDao.mapGrupoRegistro(cargaId, " WHERE EVALUACION_E42 != 0");
		HashMap<String,String> mapaCarrera		= catCarreraDao.mapaCarreraApt();
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisFechas", lisFechas);
		modelo.addAttribute("lisEvaluacion", lisEvaluacion);
		modelo.addAttribute("mapActividades", mapActividades);
		modelo.addAttribute("mapEval", mapEval);
		modelo.addAttribute("mapVirtual", mapVirtual);
		modelo.addAttribute("mapE42", mapE42);
		modelo.addAttribute("mapaCarrera", mapaCarrera);

		return "analisis/reportes/estrategias";
	}	
	
	@RequestMapping("/analisis/reportes/cargaAnalisis")
	public String analisisCargaCargaAnalisis(HttpServletRequest request, Model modelo){
		List<CatPeriodo> listaPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
		if(request.getParameter("PeriodoId")!=null) sesion.setAttribute("periodo", request.getParameter("PeriodoId"));
		if(request.getParameter("CargaId")!=null) sesion.setAttribute("cargaId", request.getParameter("CargaId"));
		
		
		String periodoId = request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 	 = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		if(periodoId.equals("0")) {
			periodoId = catPeriodoDao.getPeriodoActivo();
		}
		
		List<Carga> lisCargas 				= cargaDao.getListAll(" WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY 4,2");
		if (cargaId.equals("0") && lisCargas.size() >= 1) cargaId = lisCargas.get(0).getCargaId();

		List<CatTipoCurso> lista = catTipoCursoDao.getListAll("");
		List<CatFacultad> listaFacultad = catFacultadDao.getListAll("ORDER BY 1");

		List<String> masMaterias = cargaGrupoDao.getListaCargaTopMaestro(cargaId);
		List<String> masAlumnos = cargaGrupoDao.getListaCargaTopMaestroAlum(cargaId);
		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, String> mapAlumnosPorTipo		= cargaGrupoDao.mapAlumnosPorTipo(cargaId);
		
		HashMap<String, String> mapMateriasPorTipo 		= cargaGrupoDao.mapMateriasPorTipo(cargaId);
		
		HashMap<String, Maestros> mapaMaestros = maestrosDao.mapaMaestros();
		
		int [] hombresYmujeres 	= cargaDao.countMyF(cargaId);
		
		int numMaestros			= cargaDao.numMaestrosPorCarga(cargaId);
		int promedioEdad		= cargaDao.promEdad(cargaId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("listaFacultad", listaFacultad);
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapMateriasPorTipo", mapMateriasPorTipo);
		modelo.addAttribute("mapAlumnosPorTipo", mapAlumnosPorTipo);
		modelo.addAttribute("hombresYmujeres", hombresYmujeres);
		modelo.addAttribute("numMaestros", numMaestros);
		modelo.addAttribute("promedioEdad", promedioEdad);
		modelo.addAttribute("masMaterias", masMaterias);
		modelo.addAttribute("masAlumnos", masAlumnos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "analisis/reportes/cargaAnalisis";
	}
	
	@RequestMapping("/analisis/reportes/materias")
	public String analisisReportesMaterias(HttpServletRequest request, Model modelo){
		
		String cargaId		= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String cargas		= "";		
		
		List<CargaAcademica> lisMaterias		= new ArrayList<CargaAcademica>();
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	cargas 	= (String)sesion.getAttribute("cargas");
        	if (accion.equals("1")) {
	        	if (!cargas.contains(cargaId)) {
	        		if (cargas.length()>=1) cargas = cargas +",'"+cargaId+"'"; else cargas = cargas +"'"+cargaId+"'"; 
	        		sesion.setAttribute("cargas", cargas);
	        	}
        	}else if (accion.equals("2")){
        		lisMaterias = cargaAcademicaDao.lisPorCargas(cargas, "O", " ORDER BY FACULTAD(CARRERA_ID)");
        	}	
        }				
        
		List<Carga> lisCargas			= cargaDao.getListAll(" ORDER BY PERIODO DESC, PRIORIDAD");
		List<aca.Mapa> lisPlanes		= cargaGrupoCursoDao.lisPlanesEnMaterias(cargas, " ORDER BY 1, 2");
		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaAlumPorMateria		= krdxCursoActDao.mapaNumAlumnosPorMateria(cargas);
		HashMap<String, String> mapaAlumPorPlan			= krdxCursoActDao.mapaAlumnosPorPlan(cargas);
		
		modelo.addAttribute("cargaId", cargaId);		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisPlanes", lisPlanes);
		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaAlumPorMateria", mapaAlumPorMateria);
		modelo.addAttribute("mapaAlumPorPlan", mapaAlumPorPlan);
		
		return "analisis/reportes/materias";
	}
	
	@RequestMapping("/analisis/reportes/quitar")
	public String analisisReportesQuitarCarga(HttpServletRequest request, Model modelo){
		
		String cargaId		= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");		
		String cargas		= "";		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	cargas 	= (String)sesion.getAttribute("cargas");        	
        	if (cargas.contains(",'"+cargaId+"'")) {
	        	cargas = cargas.replace(",'"+cargaId+"'","");        		
	        }        		
        	if (cargas.contains("'"+cargaId+"'")) {
	        	cargas = cargas.replace("'"+cargaId+"'","");        		
	        }
        	if (cargas.substring(0,1).equals(",")) cargas = cargas.substring(1, cargas.length());
        	sesion.setAttribute("cargas", cargas);        		
        }		
						
		return "redirect:/analisis/reportes/materias";
	}
	
}
