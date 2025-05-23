package aca.web.informes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatArea;
import aca.catalogo.spring.CatAreaDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatNivelInicio;
import aca.catalogo.spring.CatNivelInicioDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.internado.spring.IntAlumno;
import aca.internado.spring.IntAlumnoDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.plan.spring.MapaMayorMenor;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.trabajo.spring.TrabDepartamento;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.alumno.spring.AlumPatrocinador;
import aca.alumno.spring.AlumPatrocinadorDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaFinanciero;
import aca.carga.spring.CargaFinancieroDao;
import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContInformesInformes{
	
	@Autowired	
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumBancoDao alumBancoDao;

	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	IntAlumnoDao intAlumnoDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	AlumPatrocinadorDao alumPatrocinadorDao;

	@Autowired
	CatPatrocinadorDao catPatrocinadorDao;
	
	@Autowired
	TrabAlumDao trabAlumDao;
	
	@Autowired
	TrabDepartamentoDao trabDepartamentoDao;
	
	@Autowired
	TrabPeriodoDao trabPeriodoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	InscritosDao inscritosDao; 

	@Autowired
	AlumEstadoDao alumEstadoDao;

	@Autowired
	CargaFinancieroDao cargaFinancieroDao;

	@Autowired
	CatFacultadDao catFacultadDao;

	@Autowired
	CatAreaDao catAreaDao;

	@Autowired
	MapaPlanDao mapaPlanDao;

	@Autowired
	MapaMayorMenorDao mapaMayorMenorDao;

	@Autowired
	CatNivelInicioDao catNivelInicioDao;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	@RequestMapping("/informes/informes/menu")
	public String informesInformesMenu(HttpServletRequest request, Model modelo) {
		
		
		return "informes/informes/menu";
	}
	
	@RequestMapping("/informes/informes/general")
	public String informesInformesGeneral(HttpServletRequest request, Model modelo) {
		
		String cargaId 	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		List<Carga> lisCargas = cargaDao.getListAll("ORDER BY CARGA_ID");
		
		List<AlumPersonal> lisTodos = new ArrayList<AlumPersonal>();
		if(cargaId.equals("0")) {
		lisTodos = alumPersonalDao.getListAll(" ORDER BY CODIGO_PERSONAL");
		}else {
		lisTodos = alumPersonalDao.getListAlumnosEnCarga(cargaId, "");
		}

		HashMap<String, AlumAcademico> mapaAcademico 			= alumAcademicoDao.getMapAcademico();
		HashMap<String, AlumPlan> mapaAlumPlan 					= alumPlanDao.getMapAlumPlan("");
		HashMap<String, AlumUbicacion> mapaUbicacion			= alumUbicacionDao.mapInscritos();
		HashMap<String, IntAlumno> mapaIntAlumno				= intAlumnoDao.mapaIntAlumno();
		HashMap<String, CatPais> mapaPais						= catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstado					= catEstadoDao.getMapAll();
		HashMap<String, CatCiudad> mapaCiudad					= catCiudadDao.getMapAll("");
		HashMap<String, CatReligion> mapaReligion				= catReligionDao.getMapAll("");
		HashMap<String, IntDormitorio> mapaDormitorio			= intDormitorioDao.getMapAll("");
		HashMap<String, String> mapaEdades 						= alumPersonalDao.mapaEdadesAlumnos("");
		HashMap<String, AlumEstado> mapaAlumEstado 				= alumEstadoDao.mapaInscritos("");
		HashMap<String, CatFacultad> mapaFacultad 				= catFacultadDao.getMapFacultad("");
		HashMap<String, CatArea> mapaArea 						= catAreaDao.getMapArea("");
		HashMap<String, MapaPlan> mapaPlan 						= mapaPlanDao.mapPlanes( "'A'");
		HashMap<String, MapaMayorMenor> mapaMayores 			= mapaMayorMenorDao.mapMayores("");
		HashMap<String, MapaMayorMenor> mapaMenores 			= mapaMayorMenorDao.mapMenores("");
		HashMap<String, AlumPatrocinador> mapaAlumPatrocinador 	= alumPatrocinadorDao.mapaAlumPatrocinador(cargaId);
		HashMap<String, CatPatrocinador> mapaPatrocinador 		= catPatrocinadorDao.mapaCatPatrocinador();
		HashMap<String, CatNivelInicio> mapaNivelInicio 		= catNivelInicioDao.getMapAll(" ORDER BY NIVEL_INICIO_ID");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisTodos", lisTodos);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaUbicacion", mapaUbicacion);
		modelo.addAttribute("mapaIntAlumno", mapaIntAlumno);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCiudad", mapaCiudad);
		modelo.addAttribute("mapaReligion", mapaReligion);
		modelo.addAttribute("mapaDormitorio", mapaDormitorio);
		modelo.addAttribute("mapaEdades", mapaEdades);
		modelo.addAttribute("mapaAlumEstado", mapaAlumEstado);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaArea", mapaArea);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaMayores", mapaMayores);
		modelo.addAttribute("mapaMenores", mapaMenores);
		modelo.addAttribute("mapaAlumPatrocinador", mapaAlumPatrocinador);
		modelo.addAttribute("mapaPatrocinador", mapaPatrocinador);
		modelo.addAttribute("mapaNivelInicio", mapaNivelInicio);
		
		return "informes/informes/general";
	}

	
	@RequestMapping("/informes/informes/patrocinador")
	public String informesInformesPatrocinador(HttpServletRequest request, Model modelo) {
		
		String cargaId 	= request.getParameter("CargaId")==null?"20231A":request.getParameter("CargaId");
		List<Carga> lisCargas = cargaDao.getListAll("ORDER BY CARGA_ID");
		
		List<AlumPersonal> lisAlums = alumPersonalDao.getListAlumnosEnCarga(cargaId, "");
		
		
		HashMap<String, AlumPatrocinador> mapAlumPatrocinador = alumPatrocinadorDao.mapaAlumPatrocinador(cargaId);
		HashMap<String, CatPatrocinador> mapCatPatrocinador   = catPatrocinadorDao.mapaCatPatrocinador();
		 
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisAlums", lisAlums);
		modelo.addAttribute("mapAlumPatrocinador", mapAlumPatrocinador);
		modelo.addAttribute("mapCatPatrocinador", mapCatPatrocinador);
		
		
		return "informes/informes/patrocinador";
	}
	
	@RequestMapping("/informes/informes/residencia")
	public String informesInformesResidencia(HttpServletRequest request, Model modelo) {
		
		List<IntAlumno> lisTodos = intAlumnoDao.lisTodos();
		
		HashMap <String, AlumPersonal> mapAlumInternos = alumPersonalDao.mapAlumnosInternos();
		HashMap<String, IntDormitorio> mapDormitorio	= intDormitorioDao.getMapAll("");
		
		
		modelo.addAttribute("lisTodos", lisTodos);
		modelo.addAttribute("mapAlumInternos", mapAlumInternos);
		modelo.addAttribute("mapDormitorio", mapDormitorio);
		
		
		return "informes/informes/residencia";
	}
	
	@RequestMapping("/informes/informes/religion")
	public String informesInformesReligion(HttpServletRequest request, Model modelo) {
		List<AlumPersonal> lisTodos = alumPersonalDao.getListAll(" ORDER BY CODIGO_PERSONAL");
		HashMap<String, CatReligion> mapReligion		= catReligionDao.getMapAll("");
		
		
		modelo.addAttribute("lisTodos", lisTodos);
		modelo.addAttribute("mapReligion", mapReligion);
		
		return "informes/informes/religion";
	}
	
	@RequestMapping("/informes/informes/trabajo")
	public String informesInformesTrabajo(HttpServletRequest request, Model modelo) {
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos("ORDER BY PERIODO_ID");
		
		List<TrabAlum> lisTodos = trabAlumDao.lisPorPeriodo(periodoId, "");
		
		HashMap<String, AlumPersonal> mapAlumTrabajo 	= alumPersonalDao.mapAlumnosTrabajos();
		HashMap<String, TrabDepartamento> mapDept 		= trabDepartamentoDao.mapaDept();
		HashMap<String, String> mapMaestrosNombre	 	= maestrosDao.mapMaestroNombre("");
		
		
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisTodos", lisTodos);
		modelo.addAttribute("mapAlumTrabajo", mapAlumTrabajo);
		modelo.addAttribute("mapDept", mapDept);
		modelo.addAttribute("mapMaestrosNombre", mapMaestrosNombre);
		
		return "informes/informes/trabajo";
	}
	
	@RequestMapping("/informes/informes/inscritos")
	public String informesInformesInscritos(HttpServletRequest request, Model modelo) {
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String grado 				= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		int iGrado					= Integer.parseInt(grado);
			
		List<Carga> lisCargas 		= cargaDao.getListAll("ORDER BY CARGA_ID");
		if(cargaId.equals("0")){
			cargaId = lisCargas.get(0).getCargaId();
		}
		List<Inscritos> lisTodos = null;
		if(iGrado == 0){
			lisTodos 	= inscritosDao.getListAllPorCarga("", cargaId);
		}else {
			lisTodos 	= inscritosDao.getListAllPorCargaGrado("", cargaId, iGrado);
		}
		
		HashMap<String, IntDormitorio> mapaDormitorio	= intDormitorioDao.getMapAll("");
		HashMap<String, CargaFinanciero> mapaFinanciero = cargaFinancieroDao.mapaCargaFinancieroPorCarga(cargaId, " ORDER BY CARGA_ID");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("grado", grado);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisTodos", lisTodos);
		modelo.addAttribute("mapaDormitorio", mapaDormitorio);
		modelo.addAttribute("mapaFinanciero", mapaFinanciero);
		
		
		return "informes/informes/inscritos";
	}
}