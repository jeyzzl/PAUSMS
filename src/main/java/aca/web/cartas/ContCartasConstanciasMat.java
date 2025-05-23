package aca.web.cartas;

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

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContCartasConstanciasMat {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	ParametrosDao parametrosDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;	
	
	
	@RequestMapping("/cartas/constancia_mat/form")
	public String cartasConstanciaMatForm(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoAlumno 	= "0";
		String nombreAlumno 	= "-";
		String planId			= "0";
		int semestre			= 0;
		String carreraId		= "0";
		String nombreCarrera	= "-";
		String facultadId		= "0";
		String nombreFacultad	= "";
		MapaPlan mapaPlan 		= new MapaPlan();
		Parametros parametros 	= new Parametros();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");		
		}
		
		List<Carga> lisCargas 			= cargaDao.getListAlumno(codigoAlumno);
		if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId 	= lisCargas.get(0).getCargaId();
		}		
		if(!cargaId.equals("0")){
			planId 			= estadisticaDao.getPlanEnCarga(cargaId, codigoAlumno);
		}else {
			planId 			= alumPersonalDao.getPlanActivo(codigoAlumno);
		}
		
		mapaPlan 		= mapaPlanDao.mapeaRegId(planId);
		parametros 		= parametrosDao.mapeaRegId("1");
		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno,"NOMBRE");
		semestre 		= Integer.parseInt(alumPlanDao.getSem(codigoAlumno, planId));
		
		carreraId 			= mapaPlanDao.getCarreraId( planId);
		nombreCarrera 		= mapaPlanDao.getCarreraSe(planId);			
		facultadId 			= catCarreraDao.getFacultadId(carreraId);	
		nombreFacultad 		= catFacultadDao.getNombreFacultad(facultadId);		
				
		List<AlumnoCurso> lisCursos 	= alumnoCursoDao.getListAlumnoCarga(codigoAlumno, cargaId, "");
		
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("parametros", parametros);		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("semestre", semestre);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisCargas", lisCargas);
		
		
		return "cartas/constancia_mat/form";
	}
	
	@RequestMapping("/cartas/constancia_mat/show")
	public String cartasConstanciaMatShow(HttpServletRequest request, Model modelo){		
		return "cartas/constancia_mat/show";
	}
	
	@RequestMapping("/cartas/constancia_mat/view")
	public String cartasConstanciaMatView(HttpServletRequest request, Model modelo){
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoAlumno 	= "0";
		Parametros parametros 	= new Parametros();
		AlumPlan alumPlan		= new AlumPlan();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");		
			alumPlan		= alumPlanDao.mapeaRegId(codigoAlumno);
			parametros 		= parametrosDao.mapeaRegId("1");
		}
		
		List<AlumnoCurso> lisCursos 			= alumnoCursoDao.getListAlumnoCarga(codigoAlumno, cargaId, " ORDER BY NOMBRE_CURSO");
		HashMap<String,MapaCurso> mapaCursos	= mapaCursoDao.getMapaCursos(alumPlan.getPlanId(), "");
		HashMap<String,CatTipoCurso> mapaTipos	= catTipoCursoDao.getMapAll("");
		HashMap<String,CatTipoCal> mapaTiposCal	= catTipoCalDao.getMapAll("");
		
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaTiposCal", mapaTiposCal);
		
		return "cartas/constancia_mat/view";
	}
	
}