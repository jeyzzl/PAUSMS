package aca.web.candados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandAlumnoDao;
import aca.candado.spring.CandTipo;
import aca.candado.spring.CandTipoDao;
import aca.candado.spring.Candado;
import aca.candado.spring.CandadoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContCandadosRepTipo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CandAlumnoDao candAlumnoDao;
	
	@Autowired
	private CandTipoDao candTipoDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private CandadoDao candadoDao;

	
	@RequestMapping("/candados/reptipo/tipo")
	public String candadosRepTipoTipo(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= "0";
		String tipoId				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String periodoId			= request.getParameter("Periodo")==null?"0":request.getParameter("Periodo");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoPersonal 			= (String)sesion.getAttribute("codigoPersonal");
        } 
        
		List<CandTipo> lisTipos  	= candTipoDao.getLista(" ORDER BY TIPO_ID");
		if (tipoId.equals("0") && lisTipos.size()>0){
			CandTipo tipo 			= (CandTipo) lisTipos.get(0);
			tipoId 					= tipo.getTipoId();  
		}
		
		if (periodoId.equals("0")){ periodoId = catPeriodoDao.getPeriodo(); }		
		List<CandAlumno> lisCandAlum	= candAlumnoDao.getListCandado(periodoId, tipoId, " ORDER BY CANDADO_ID");
		List<CatPeriodo> lisPeriodos  	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");	
		
		HashMap<String, CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("");  
		HashMap<String, CatCarrera> mapaCarreras 			= catCarreraDao.getMapAll("");		
		HashMap<String, String> mapaInscritos 				= inscritosDao.getMapaInscritos();
		HashMap<String, String> mapaCarrerasAlumnos			= alumPlanDao.mapaCarrerasConCandado();
		HashMap<String, Candado> mapaCandados				= candadoDao.getMapaCandados();
		HashMap<String, String> mapaAlumnos					= alumPersonalDao.mapaAlumnosConCandados();

		modelo.addAttribute("tipoId", tipoId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisCandAlum", lisCandAlum);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaInscritos", mapaInscritos);	
		modelo.addAttribute("mapaCarrerasAlumnos", mapaCarrerasAlumnos);
		modelo.addAttribute("mapaCandados", mapaCandados);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "candados/reptipo/tipo";
	}
}