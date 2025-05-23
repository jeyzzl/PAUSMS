package aca.web.mapa;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaNuevoBibliografiaDao;
import aca.plan.spring.MapaNuevoCurso;
import aca.plan.spring.MapaNuevoCursoDao;
import aca.plan.spring.MapaNuevoPlan;
import aca.plan.spring.MapaNuevoPlanDao;
import aca.plan.spring.MapaNuevoProductoDao;
import aca.plan.spring.MapaNuevoUnidadDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMapaDiamante {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@Autowired	
	private MapaNuevoPlanDao mapaNuevoPlanDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MapaNuevoCursoDao mapaNuevoCursoDao; 
	
	@Autowired
	MapaNuevoProductoDao mapaNuevoProductoDao;
	
	@Autowired
	MapaNuevoBibliografiaDao mapaNuevoBibliografiaDao;
	
	@Autowired
	MapaNuevoUnidadDao mapaNuevoUnidadDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@RequestMapping("/mapa/diamante/actividadAccion")
	public String mapaDiamanteActividadAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteActividadAccion");
		return "mapa/diamante/actividadAccion";
	}	
	
	@RequestMapping("/mapa/diamante/biblioAccion")
	public String mapaDiamanteBiblioAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteBiblioAccion");
		return "mapa/diamante/biblioAccion";
	}
	
	@RequestMapping("/mapa/diamante/cursoAccion")
	public String mapaDiamanteCursoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteCursoAccion");
		return "mapa/diamante/cursoAccion";
	}
	
	@RequestMapping("/mapa/diamante/editaMateria")
	public String mapaDiamanteEditaMateria(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaMateria");
		return "mapa/diamante/editaMateria";
	}
	
	@RequestMapping("/mapa/diamante/editaMateriaAccion")
	public String mapaDiamanteEditaMateriaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaMateriaAccion");
		return "mapa/diamante/editaMateriaAccion";
	}	
	
	@RequestMapping("/mapa/diamante/editaPlan")
	public String mapaDiamanteEditaPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaPlan");
		return "mapa/diamante/editaPlan";
	}
	
	@RequestMapping("/mapa/diamante/editaUnidad")
	public String mapaDiamanteEditaUnidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaUnidad");
		return "mapa/diamante/editaUnidad";
	}
	
	@RequestMapping("/mapa/diamante/editaUnidadAccion")
	public String mapaDiamanteEditaUnidadAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaUnidadAccion");
		return "mapa/diamante/editaUnidadAccion";
	}
	
	@RequestMapping("/mapa/diamante/editaUnidadI")
	public String mapaDiamanteEditaUnidadI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEditaUnidadI");
		return "mapa/diamante/editaUnidadI";
	}
	
	@RequestMapping("/mapa/diamante/estadistica")
	public String mapaDiamanteEstadistica(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEstadistica");
		return "mapa/diamante/estadistica";
	}
	
	@RequestMapping("/mapa/diamante/evaluacionAccion")
	public String mapaDiamanteEvaluacionAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteEvaluacionAccion");
		return "mapa/diamante/evaluacionAccion";
	}
	
	@Transactional
	@RequestMapping("/mapa/diamante/materias")
	public String mapaDiamanteMaterias(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "-";
		boolean esCotejador		= false;
		boolean esAdmin			= false;
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if(sesion != null){
			codigoPersonal	= sesion.getAttribute("codigoPersonal").toString();	
			esCotejador		= accesoDao.esCotejador(codigoPersonal);
			esAdmin 		= accesoDao.esAdministrador(codigoPersonal);
		}
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String versionId		= request.getParameter("versionId")==null?"0":request.getParameter("versionId");
		int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
		String respuesta 		= "";
		
		switch(accion){
			case 3:{// Borrar			
				String cursoId = request.getParameter("cursoId");
				boolean error = false;
				//MapaNuevoCurso mapaNuevoCurso = mapaNuevoCursoDao.mapeaRegId(cursoId, planId, versionId);
				
				if(esAdmin){
					mapaNuevoProductoDao.deleteReg( cursoId);
					mapaNuevoBibliografiaDao.deleteRegCurso( cursoId);
					mapaNuevoUnidadDao.deleteRegCurso( cursoId);
				}
				if(!mapaNuevoCursoDao.deleteReg(cursoId, planId, versionId))
					error = true;
				
				if(!error){				
					respuesta = "<font size=2 color=blue><b>Se borr&oacute; correctamente la materia</b></font>";
				}else{				
					if(esAdmin)
						respuesta = "<font size=2 color=red><b>La materia NO se borro. Reportelo a sistemas</b></font>";
					else
						respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar.<br>--Verifique que la materia no tenga unidades e intentelo de nuevo</b></font>";
				}
				
			}break;
			case 5:{
				respuesta = "<font size=2 color=blue><b>Se modific&oacute; correctamente la materia</b></font>";
			}break;
			case 6:{
				respuesta = "<font size=2 color=blue><b>Se guard&oacute; correctamente la materia</b></font>";
			}break;
			case 7:{
				respuesta = "<font size=2 color=blue><b>Se copiaron correctamente los datos de la materia</b></font>";
			}break;
		}
		
		MapaNuevoPlan mapaNuevoPlan = mapaNuevoPlanDao.mapeaRegId( planId, versionId);
		String idioma 				= mapaNuevoPlanDao.getIdioma(planId);
		
		List<MapaNuevoCurso> lisCursos = null;
		
		// Carreras a las que tiene acceso como coordinador  o Director de Facultad.
		String accesos = accesoDao.getAccesos(codigoPersonal);	
		//Si es administrador o es coordinador (acceso a la carrera)
		if(esAdmin || accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1 || esCotejador){
			lisCursos = mapaNuevoCursoDao.getListPlan(planId, versionId, "ORDER BY CICLO, NOMBRE");		
		}else{
			lisCursos = mapaNuevoCursoDao.getListPlanMaestro(planId, versionId, codigoPersonal, "ORDER BY CICLO, NOMBRE");		
		}
		
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String,String> mapaUnidades 	= mapaNuevoUnidadDao.mapaTotalUnidades(planId);		
		
		modelo.addAttribute("esCotejador", esCotejador);
		modelo.addAttribute("mapaNuevoPlan", mapaNuevoPlan);
		modelo.addAttribute("respuesta", respuesta);
		modelo.addAttribute("idioma", idioma);
		modelo.addAttribute("lisCursos", lisCursos);		
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaUnidades", mapaUnidades);
		
		return "mapa/diamante/materias";
	}
	
	@RequestMapping("/mapa/diamante/materiasAccion")
	public String mapaDiamanteMateriasAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMateriasAccion");
		return "mapa/diamante/materiasAccion";
	}
	
	@RequestMapping("/mapa/diamante/muestraMateriaPdf")
	public String mapaDiamanteMuestraMateriaPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMuestraMateriaPdf");
		return "mapa/diamante/muestraMateriaPdf";
	}
	
	@RequestMapping("/mapa/diamante/muestraMateriaPdfI")
	public String mapaDiamanteMuestraMateriaPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMuestraMateriaPdfI");
		return "mapa/diamante/muestraMateriaPdfI";
	}
	
	@RequestMapping("/mapa/diamante/muestraPlan")
	public String mapaDiamanteMuestraPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMuestraPlan");
		return "mapa/diamante/muestraPlan";
	}
	
	@RequestMapping("/mapa/diamante/muestraPlanPdf")
	public String mapaDiamanteMuestraPlanPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMuestraPlanPdf");
		return "mapa/diamante/muestraPlanPdf";
	}
	
	@RequestMapping("/mapa/diamante/muestraPlanPdfI")
	public String mapaDiamanteMuestraPlanPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteMuestraPlanPdfI");
		return "mapa/diamante/muestraPlanPdfI";
	}
	
	@RequestMapping("/mapa/diamante/planes")
	public String mapaDiamantePlanes(HttpServletRequest request, Model modelo){

		String codigoPersonal	= "-";
		boolean esAdmin			= false;
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if(sesion != null){
			codigoPersonal	= sesion.getAttribute("codigoPersonal").toString();
			esAdmin			= Boolean.parseBoolean(sesion.getAttribute("admin")+"");
		}
		
		String year				= request.getParameter("yearPlanes")==null?"2017":request.getParameter("yearPlanes");
		
		List<MapaNuevoPlan> listPlanes	= null;
		
		if(esAdmin || accesoDao.esCotejador(codigoPersonal)){
			if(year.equals("0")){
				listPlanes	= mapaNuevoPlanDao.getListAll("ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
			}else{
				listPlanes	= mapaNuevoPlanDao.getListPorYear(year, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
			}
		}else{
			if(year.equals("0")){
				listPlanes	= mapaNuevoPlanDao.getListMaestro(codigoPersonal, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
			}else{
				listPlanes	= mapaNuevoPlanDao.getListMaestroPorYear(codigoPersonal, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE", year);
			}		
		}

		HashMap<String,String> mapaCursos 				= mapaCursoDao.mapaCursos();
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.mapaCarreras();
		HashMap<String,CatFacultad> mapaFacultad		= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapaPorcentajePlan		= mapaNuevoCursoDao.getPorcentajePlan();
		HashMap<String,String> mapaPorcentajeEstado		= mapaNuevoCursoDao.getPorcentajeEstado();
		HashMap<String,String> mapaTotales				= mapaNuevoCursoDao.mapaMateriasTotales();
		HashMap<String,String> mapaUnidades				= mapaNuevoCursoDao.mapaMateriasConUnidades();
				
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("listPlanes", listPlanes);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaPorcentajePlan", mapaPorcentajePlan);
		modelo.addAttribute("mapaPorcentajeEstado", mapaPorcentajeEstado);
		modelo.addAttribute("mapaTotales", mapaTotales);
		modelo.addAttribute("mapaUnidades", mapaUnidades);

		return "mapa/diamante/planes";
	}
	
	@RequestMapping("/mapa/diamante/borrar")
	public String mapaDiamanteBorrar(HttpServletRequest request){
		
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String versionId	= request.getParameter("VersionId")==null?"0":request.getParameter("VersionId");
		String mensaje 		= "-";
		
		if (mapaNuevoPlanDao.existeReg(planId, versionId)) {
			if (mapaNuevoPlanDao.deleteReg(planId, versionId)) {
				mensaje = "<font size=2 color=blue><b>Se borr&oacute; correctamente el plan</b></font>";
			}else {
				mensaje = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar.<br>Verifique que el plan no tenga materias e intentelo de nuevo</b></font>";
			}
		}
		
		return "mapa/diamante/planes";
	}
	
	@RequestMapping("/mapa/diamante/productoAccion")
	public String mapaDiamanteProductoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteProductoAccion");
		return "mapa/diamante/productoAccion";
	}
	
	@RequestMapping("/mapa/diamante/unidadAccion")
	public String mapaDiamanteUnidadAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteUnidadAccion");
		return "mapa/diamante/unidadAccion";
	}
	
	@RequestMapping("/mapa/diamante/verMateria")
	public String mapaDiamanteVerMateria(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteVerMateria");
		return "mapa/diamante/verMateria";
	}
	
	@RequestMapping("/mapa/diamante/verMateriaI")
	public String mapaDiamanteVerMateriaI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMapaDiamante|mapaDiamanteVerMateriaI");
		return "mapa/diamante/verMateriaI";
	}
	
}