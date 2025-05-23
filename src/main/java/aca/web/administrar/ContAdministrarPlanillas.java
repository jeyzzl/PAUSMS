package aca.web.administrar;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import aca.vista.spring.EstadisticaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;

@Controller
public class ContAdministrarPlanillas {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;	
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;

	@Autowired
	private EstadisticaDao estadisticaDao;
	
	
	
	@RequestMapping("/administrar/planillas/entregas")
	public String administrarCancelaPlanillasEntregas(HttpServletRequest request, Model modelo){
		
		String periodoSesion 	= "0";		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			periodoSesion 		= (String)sesion.getAttribute("periodo");			
		}
		
		List<CatPeriodo> lisPeriodos 		= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && !periodoSesion.equals("0")) {
			periodoId 			=  periodoSesion;
		}else if (periodoId.equals("0") && lisPeriodos.size() >= 1) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		if (periodoId.equals("0")) sesion.setAttribute("periodo", periodoId);
		
		List<Carga> lisCargas 	= cargaDao.getListPeriodo(periodoId, " ORDER BY PERIODO, CARGA_ID");
		boolean existeCarga = false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) {
				existeCarga = true;
				break;
			}
		}
		if(!existeCarga && lisCargas.size()>=1){
			cargaId = lisCargas.get(0).getCargaId();			
		}
		if (!cargaId.equals("0")) sesion.setAttribute("cargaId", cargaId);
		
		List<CatCarrera> lisCarreras 					= catCarreraDao.lisEnCargas(cargaId," ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, String> mapaMaterias			= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'1','2','3','4','5'");
		HashMap<String, String> mapaCreadas				= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'1'");
		HashMap<String, String> mapaOrdinarias			= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'2'");
		HashMap<String, String> mapaExtras				= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'3'");
		HashMap<String, String> mapaCerradas			= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'4'");
		HashMap<String, String> mapaRegistradas			= cargaGrupoDao.mapaMateriasPorCarrerayEstado(cargaId,"'5'");
		HashMap<String, String> mapaAlumnos				= estadisticaDao.mapaAlumnosPorCarrera(cargaId);
		HashMap<String, String> mapaCerrados 			= estadisticaDao.mapaAlumnosConNotasCerradas(cargaId);
		
		modelo.addAttribute("periodoId",periodoId);
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisPeriodos",lisPeriodos);
		modelo.addAttribute("lisCargas",lisCargas);
		modelo.addAttribute("lisCarreras",lisCarreras);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaCreadas",mapaCreadas);
		modelo.addAttribute("mapaOrdinarias",mapaOrdinarias);
		modelo.addAttribute("mapaExtras",mapaExtras);
		modelo.addAttribute("mapaCerradas",mapaCerradas);
		modelo.addAttribute("mapaRegistradas",mapaRegistradas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCerrados", mapaCerrados);
		
		return "administrar/planillas/entregas";
	}
	
	@RequestMapping("/administrar/planillas/materias")
	public String administrarCancelaPlanillasMaterias(HttpServletRequest request, Model modelo){
		
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		//String carreraNombre 	= request.getParameter("carreraId")==null?"0":request.getParameter("carreraNombre");	
		String cargaId 			= "0";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			cargaId 			= (String)sesion.getAttribute("cargaId");
		}
		
		List<CargaAcademica> lisCursos 				= cargaAcademicaDao.getListaCargaCarrera(cargaId, carreraId, " ORDER BY NOMBRE_CURSO");
		HashMap<String, String> mapaModalidades		= catModalidadDao.mapModalidades("");
		HashMap<String, String> mapaAlumnos			= krdxCursoActDao.mapaTotalAlumnos(cargaId,"'I','1','2','3','4','5','6'");
		
		modelo.addAttribute("cargaId",cargaId);	
		modelo.addAttribute("lisCursos",lisCursos);
		modelo.addAttribute("mapaModalidades",mapaModalidades);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		
		return "administrar/planillas/materias";
	}
}