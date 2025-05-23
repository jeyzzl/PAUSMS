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
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
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
public class ContRendimientoCargas {		

	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;	
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;	
		
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@RequestMapping("/rendimiento/cargas/reprobados")
	public String rendimientoCargasReprobados(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		boolean existeCarga			= false;
		
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);
		
		List<CatPeriodo> listaPeriodos 					= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && listaPeriodos.size() >= 1){
			periodoId = listaPeriodos.get(0).getPeriodoId();
		}
		List<Carga> listaCarga 							= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID");	
		for (Carga carga : listaCarga) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if( (cargaId.equals("0") || existeCarga == false) && listaCarga.size() >= 1){
			cargaId = listaCarga.get(0).getCargaId();
		}
		
		List<CatFacultad> lisFacultades 				= catFacultadDao.getListCargaOficial(cargaId," ORDER BY NOMBRE_FACULTAD");
		List<CatCarrera> lisCarreras 					= catCarreraDao.getListCargaCarreraOficial(cargaId, "ORDER BY NOMBRE_CARRERA");
		List<AlumnoCurso> listMaterias					= alumnoCursoDao.getListCarga(cargaId, "ORDER BY CARRERA_ID");
		HashMap<String, String> mapaInscritosPorMateria = krdxCursoActDao.getMapInscritosPorCarrera(cargaId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("listaCarga", listaCarga);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("listMaterias", listMaterias);
		modelo.addAttribute("mapaInscritosPorMateria", mapaInscritosPorMateria);
    	
		return "rendimiento/cargas/reprobados";
	}	
	
	@RequestMapping("/rendimiento/cargas/detalle")
	public String rendimientoCargasDetalle(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");	
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId); 
		
		List<AlumnoCurso> lisCargaCarrera 				= alumnoCursoDao.getListCargayCarrera(cargaId, carreraId, "ORDER BY ALUM_NOMBRE(CODIGO_PERSONAL)");
		HashMap<String,AlumPersonal> mapaAlumnos 		= alumPersonalDao.mapAlumnosEnCargas(cargaId);
		HashMap<String,String> mapaMateriasEnCarga		= krdxCursoActDao.mapaMateriasEnCarga(cargaId, carreraId);
		HashMap<String,String> mapaReprobadasEnCarga	= krdxCursoActDao.mapaReprobadasEnCarga(cargaId, carreraId);
		
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("lisCargaCarrera", lisCargaCarrera);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaMateriasEnCarga", mapaMateriasEnCarga);
		modelo.addAttribute("mapaReprobadasEnCarga", mapaReprobadasEnCarga);
    	
		return "rendimiento/cargas/detalle";
	}	
	
	@RequestMapping("/rendimiento/cargas/alumreprobados")
	public String rendimientoCargasAlumReprobados(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");	
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		
		List<AlumnoCurso> lisReprobados 				= alumnoCursoDao.getReprobadosCargas(cargaId, carreraId, "ORDER BY ALUM_NOMBRE(CODIGO_PERSONAL)");
		HashMap<String, AlumPersonal> mapAlumnos 		= alumPersonalDao.mapAlumnosEnCargas(cargaId);
		HashMap<String,String> mapaReprobadasEnCarga	= krdxCursoActDao.mapaReprobadasEnCarga(cargaId, carreraId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("lisReprobados", lisReprobados);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapaReprobadasEnCarga", mapaReprobadasEnCarga);
    	
		return "rendimiento/cargas/alumreprobados";
	}	
	
	@RequestMapping("/rendimiento/cargas/materias")
	public String rendimientoCargasMaterias(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String codigoId			= request.getParameter("codigoId");
		String nombreAlumno		= alumPersonalDao.getNombreAlumno(codigoId, "NOMBRE");
		
		List<AlumnoCurso> lisReprobados = alumnoCursoDao.getReprobadosCargas(cargaId, carreraId, " ORDER BY CODIGO_PERSONAL");	
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("codigoId", codigoId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisReprobados", lisReprobados);
    	
		return "rendimiento/cargas/materias";
	}	
	
}