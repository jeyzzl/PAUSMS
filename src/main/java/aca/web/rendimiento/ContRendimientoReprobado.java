package aca.web.rendimiento;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContRendimientoReprobado {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	MaestrosDao maestrosDao;

	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	
	@RequestMapping("/rendimiento/reprobado/menu")
	public String rendimientoReprojbadoMenu(HttpServletRequest request, Model modelo){
		
		return "rendimiento/reprobado/menu";
	}
	
	@RequestMapping("/rendimiento/reprobado/listado")
	public String rendimientoReprobadoListado(HttpServletRequest request, Model modelo){
		
		String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
		String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
		
		String periodoId 		= "0";
		String cargaId			= "0";
		String codigoPersonal	= "0";
		Acceso acceso 			= new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        	periodoId 			= (String)sesion.getAttribute("periodo");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	acceso 				= accesoDao.mapeaRegId(codigoPersonal);
        	
        	if (cambioPeriodo.equals("1")){
    			periodoId = request.getParameter("PeriodoId");
    			sesion.setAttribute("periodo", periodoId);
    		}
    		if (cambioCarga.equals("1")){
    			cargaId = request.getParameter("CargaId");
    			sesion.setAttribute("cargaId", cargaId);
    		}
        }	
		
    	List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");
    	List<Estadistica> lisReprobados	 			= estadisticaDao.getListReprobados("'"+cargaId+"'"," ORDER BY FACULTAD_ID, CARRERA_ID");    	
    	List<AlumnoCurso> lisCursos	 				= alumnoCursoDao.getListCarga(cargaId," ORDER BY CODIGO_PERSONAL, CREDITOS DESC, NOMBRE_CURSO");
    	HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
    	HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
    	
    	modelo.addAttribute("acceso", acceso);
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisReprobados", lisReprobados);
    	modelo.addAttribute("lisCursos", lisCursos);
    	modelo.addAttribute("mapaFacultades", mapaFacultades);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);    	
    	
		return "rendimiento/reprobado/listado";
	}
	
	@RequestMapping("/rendimiento/reprobado/maestros")
	public String rendimientoReprobadoMaestros(HttpServletRequest request, Model modelo){
		
		String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
		String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
		
		String periodoId 		= "0";
		String cargaId			= "0";
		String codigoPersonal	= "0";
		Acceso acceso 			= new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        	periodoId 			= (String)sesion.getAttribute("periodo");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	acceso 				= accesoDao.mapeaRegId(codigoPersonal);
        	
        	if (cambioPeriodo.equals("1")){
    			periodoId = request.getParameter("PeriodoId");
    			sesion.setAttribute("periodo", periodoId);
    		}
    		if (cambioCarga.equals("1")){
    			cargaId = request.getParameter("CargaId");
    			sesion.setAttribute("cargaId", cargaId);
    		}
        }	
		
    	List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");    	
    	List<String> lisMaestros 					= cargaGrupoDao.lisMaestrosCarga(cargaId," ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
    	// Map que cuenta la cantidad de materias que imparte el maestro
    	HashMap<String,String> mapaMaterias 			= cargaGrupoDao.mapMatPorMaestroEnCarga(cargaId);    	
    	// Map que cuenta la cantidad de materias que imparte el maestro
    	HashMap<String,String> mapaAlumnos 			= alumnoCursoDao.mapAlumPorMaestro(cargaId, "'I','1','2','4','5'");
    	// Map que cuenta la cantidad de materias que imparte el maestro
    	HashMap<String,String> mapaReprobados 		= alumnoCursoDao.mapAlumPorMaestro(cargaId, "'2','4'");    	
    	HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
    	
    	modelo.addAttribute("acceso", acceso);
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisMaestros", lisMaestros);
    	modelo.addAttribute("mapaMaterias", mapaMaterias);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaReprobados", mapaReprobados);
    	modelo.addAttribute("mapaMaestros", mapaMaestros);
    	
		return "rendimiento/reprobado/maestros";
	}
	
	@RequestMapping("/rendimiento/reprobado/materias")
	public String rendimientoReprobadoMaterias(HttpServletRequest request, Model modelo){
		
		String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
		String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
		
		String periodoId 		= "0";
		String cargaId			= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	periodoId 			= (String)sesion.getAttribute("periodo");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	
        	if (cambioPeriodo.equals("1")){
    			periodoId = request.getParameter("PeriodoId");
    			sesion.setAttribute("periodo", periodoId);
    		}
    		if (cambioCarga.equals("1")){
    			cargaId = request.getParameter("CargaId");
    			sesion.setAttribute("cargaId", cargaId);
    		}
        }	
		
    	List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");
    	List<CatFacultad> lisFacultades 			= catFacultadDao.listFacultadesConCarga(cargaId," ORDER BY NOMBRE_FACULTAD");
   	
    	// Map que cuenta la cantidad de materias en una facultad y carga
    	HashMap<String,String> mapaMaterias 		= cargaGrupoDao.mapMatPorFacultadEnCarga(cargaId);
    	
    	// Map que cuenta la cantidad de materias en una facultad y carga
    	HashMap<String,String> mapaReprobadas 		= cargaGrupoDao.mapMatRepPorFacultadEnCarga(cargaId);
    	
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisFacultades", lisFacultades);
    	modelo.addAttribute("mapaMaterias", mapaMaterias);
    	modelo.addAttribute("mapaReprobadas", mapaReprobadas);
    	
		return "rendimiento/reprobado/materias";
	}
	
	@RequestMapping("/rendimiento/reprobado/listadoMaterias")
	public String rendimientoReprobadoListadoMaterias(HttpServletRequest request, Model modelo){
		
		String facultadId 		= request.getParameter("facultadId")==null?"0":request.getParameter("facultadId");
		String cargaId			= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);	
		
		List<CargaAcademica> lisMaterias				= cargaAcademicaDao.getMateriasDeFacultad( cargaId, facultadId);
		HashMap<String,String> mapaAlumnos				= alumnoCursoDao.mapAlumPorMateria(cargaId, "'I','1','2','4','5'");
		HashMap<String,String> mapaReprobados			= alumnoCursoDao.mapAlumPorMateria(cargaId, "'2','4'");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisMaterias", lisMaterias);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaReprobados", mapaReprobados);
		
		return "rendimiento/reprobado/listadoMaterias";
	}
	
	@RequestMapping("/rendimiento/reprobado/listadoDetalle")
	public String rendimientoReprobadoListadoDetalles(HttpServletRequest request, Model modelo){
		
		String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
		String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
		
		String periodoId 		= "0";
		String cargaId			= "0";
		String codigoPersonal	= "0";
		Acceso acceso 			= new Acceso();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	periodoId 			= (String)sesion.getAttribute("periodo");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        	acceso 				= accesoDao.mapeaRegId(codigoPersonal);
        	
        	if (cambioPeriodo.equals("1")){
    			periodoId = request.getParameter("PeriodoId");
    			sesion.setAttribute("periodo", periodoId);
    		}
    		if (cambioCarga.equals("1")){
    			cargaId = request.getParameter("CargaId");
    			sesion.setAttribute("cargaId", cargaId);
    		}
        }	
        List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");
    	List<Estadistica> lisReprobados 			=  estadisticaDao.getListReprobados("'"+cargaId+"'"," ORDER BY FACULTAD_ID, CARRERA_ID");
    	List<AlumnoCurso> lisCursos			 		= alumnoCursoDao.getListCarga(cargaId," ORDER BY CODIGO_PERSONAL,CREDITOS DESC, NOMBRE_CURSO");
    	HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
    	HashMap<String,CatTipoCal> mapaTipos 		= catTipoCalDao.getMapAll("");
    	HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
    	HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
    	
    	modelo.addAttribute("acceso", acceso);
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisReprobados", lisReprobados);
    	modelo.addAttribute("lisCursos", lisCursos);
    	modelo.addAttribute("mapaMaestros", mapaMaestros);
    	modelo.addAttribute("mapaTipos", mapaTipos);
    	modelo.addAttribute("mapaFacultades", mapaFacultades);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);
    	
    	
		return "rendimiento/reprobado/listadoDetalle";
	}
	
	@RequestMapping("/rendimiento/reprobado/reprobones")
	public String rendimientoReprobadoReprobones(HttpServletRequest request, Model modelo){
		
		String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
		String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
		String incidencias		= request.getParameter("Incidencias")==null?"0":request.getParameter("Incidencias");
		
		String periodoId 		= "0";
		String cargaId			= "0";
		String codigoPersonal	= "0";

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	periodoId 			= (String)sesion.getAttribute("periodo");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        	
        	if (cambioPeriodo.equals("1")){
    			periodoId = request.getParameter("PeriodoId");
    			sesion.setAttribute("periodo", periodoId);
    		}
    		if (cambioCarga.equals("1")){
    			cargaId = request.getParameter("CargaId");
    			sesion.setAttribute("cargaId", cargaId);
    		}
        }	
        List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");    	
    	List<String> lisReprobones 					= alumnoCursoDao.getListaAlumReprobon(cargaId, incidencias, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
    	
    	HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapInscritosEnCargas("'"+cargaId+"'");
    	HashMap<String, String> mapaReprobones		= alumnoCursoDao.mapAlumReprobon(cargaId, incidencias);    	
    	HashMap<String,MapaCurso> mapaMaterias		= mapaCursoDao.getAllMapaCursos("");
    	HashMap<String, String> mapaCarreraDePlan	= mapaPlanDao.mapCarreraPlan();
    	HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");	
    	HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
    	    	
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisReprobones", lisReprobones);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaReprobones", mapaReprobones);
    	modelo.addAttribute("mapaMaterias", mapaMaterias);
    	modelo.addAttribute("mapaCarreraDePlan", mapaCarreraDePlan);
    	modelo.addAttribute("mapaFacultades", mapaFacultades);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);
    	
    	
		return "rendimiento/reprobado/reprobones";
	}
	@RequestMapping("/rendimiento/reprobado/facultades")
	public String rendimientoReprobadoFacultades(HttpServletRequest request, Model modelo){		
		
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");				
		boolean existeCarga			= false;
		
		List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas 						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID");
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if ( (cargaId.equals("0") || existeCarga == false) && lisCargas.size()>= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		List<CatFacultad> lisFacultades				= catFacultadDao.listFacultadesConCarga(cargaId," ORDER BY NOMBRE_FACULTAD");
		// Map que cuenta la cantidad de materias en una facultad y carga
		HashMap<String,String> mapaMaterias 		= cargaGrupoDao.mapMatPorFacultadEnCarga(cargaId);
		// Map que cuenta la cantidad de materias en una facultad y carga
		HashMap<String,String> mapaReprobadas 		= cargaGrupoDao.mapMatRepPorFacultadEnCarga(cargaId);		
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaReprobadas", mapaReprobadas);
		
		return "rendimiento/reprobado/facultades";
	}
	
}