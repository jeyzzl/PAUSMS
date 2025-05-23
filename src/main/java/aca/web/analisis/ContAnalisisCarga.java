package aca.web.analisis;

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

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.plan.spring.MapaAvanceDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContAnalisisCarga {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private MapaAvanceDao mapaAvanceDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	
	@RequestMapping("/analisis/carga/carga")
	public String analisisCargaCarga(HttpServletRequest request, Model modelo){
		String cargas 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if(cargas.equals("0")) {
				cargas = (String)sesion.getAttribute("cargas");
			}
		}
		
		if(!cargas.equals("'000000'")){
			cargas = "'"+cargas+"'";
		}
		
		List<CatPeriodo> listaPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
			sesion.setAttribute("periodo", request.getParameter("PeriodoId"));
		}
		
		String periodoId = (String)sesion.getAttribute("periodo");
		
		List<Carga> lisCarga = cargaDao.getListAll("WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
				
		if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
			sesion.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
		}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
			sesion.setAttribute("cargaId", request.getParameter("CargaId"));		
		}
		String cargaId 		= (String)sesion.getAttribute("cargaId");

		if(lisCarga.isEmpty()){
			sesion.setAttribute("cargaId", "");
		}
		
		if (cargaId==null){ cargaId	= (String)sesion.getAttribute("cargaId");  }
		
		/* Lista de planes de estudio*/
		List<AlumPlan> lisAlumnos 		= alumPlanDao.listaAlumnosCarga(cargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		/* HashMap de los nombres de los alumnos */
		HashMap<String,String> mapAlumnos 	= alumPersonalDao.mapAlumnosCarga(cargaId);
		
		/* HashMap de los créditos de los alumnos en el plan */
		HashMap<String,String> mapCreditos 	= alumPlanDao.mapAlumCargaCreditos(cargaId);
		
		/* HashMap de la última fecha de inscripción de los alumnos */
		HashMap<String,String> mapUltimaFecha	= alumPlanDao.mapAlumCargaUltimaInsc(cargaId);

		HashMap<String,String> mapCreditosCarga	= mapaAvanceDao.mapCreditosCarga(cargaId);
		
		HashMap<String, CatFacultad> mapaFacultad = catFacultadDao.getMapFacultad("");
		
		HashMap<String,CatCarrera> mapaCarrera = catCarreraDao.getMapAll(" WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.ESTADISTICA WHERE CARGA_ID = '"+cargaId+"')");
		
		HashMap<String, String> mapaCarreraCarga = estadisticaDao.mapaCarreraCarga(cargaId);
		
		modelo.addAttribute("mapCreditosCarga", mapCreditosCarga);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapCreditos", mapCreditos);
		modelo.addAttribute("mapUltimaFecha", mapUltimaFecha);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("mapaCarreraCarga", mapaCarreraCarga);
		
		return "analisis/carga/carga";
	}	
	
}
