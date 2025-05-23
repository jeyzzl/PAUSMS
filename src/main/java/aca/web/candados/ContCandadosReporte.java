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
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContCandadosReporte {	
	
	@Autowired
	private CandAlumnoDao candAlumnoDao;
	
	@Autowired
	private CandTipoDao candTipoDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;	

	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	
	@RequestMapping("/candados/reporte/rep_candado")
	public String candadosReporteRepCandado(HttpServletRequest request, Model modelo){	
		
		String codigo 			= "0";
		Acceso acceso 			= new Acceso();
        String yearActual 		= aca.util.Fecha.getHoy().substring(6,10);        
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigo 		= (String)sesion.getAttribute("codigoPersonal");
        	acceso 		= accesoDao.mapeaRegId(codigo);
        }        
		
		String fechaDefault		=  aca.util.Fecha.getHoy().substring(0,6)+String.valueOf(Integer.parseInt(yearActual)-1);		
		String fechaIni 		= request.getParameter("FechaIni")==null?fechaDefault:request.getParameter("FechaIni");	
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		// Lista de candados
		List<CandAlumno> lisCandado 		= candAlumnoDao.getListCandadoFecha(fechaIni, "'A'", " ORDER BY ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL)), ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL)");
		
		// Map de tipos de candados
		HashMap<String, CandTipo> mapaTipos 			= candTipoDao.mapTipo();
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaCarrerasAlumnos		= alumPlanDao.mapaCarrerasConCandado();	
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosConCandados();
		HashMap<String, String> mapaInscritos			= inscritosDao.getMapaInscritos();
		
		modelo.addAttribute("fechaIni", fechaIni);		
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("lisCandado", lisCandado);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCarrerasAlumnos", mapaCarrerasAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		
		return "candados/reporte/rep_candado";
	}
}