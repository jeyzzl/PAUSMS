package aca.web.informes;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContInformesProcedencia {	
	
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired	
	CatFacultadDao catFacultadDao;
	
	@Autowired	
	CatCarreraDao catCarreraDao;
	
	@Autowired	
	MapaPlanDao mapaPlanDao;
	
	@Autowired	
	AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	CargaDao cargaDao;
	
	@Autowired	
	CatPeriodoDao catPeriodoDao;

	@Autowired	
	MapaCursoDao mapaCursoDao;
	
	@Autowired	
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired	
	InscritosDao inscritosDao;	
	
	@RequestMapping("/informes/procedencia/procedencia")
	public String informesProcedenciaProcedencia(HttpServletRequest request, Model modelo){
    	
    	List<Inscritos> listaInscritos		= inscritosDao.getAlumnos("ORDER BY ENOC.NOMBRE_PAIS(PAIS_ID), NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
    	
    	HashMap<String, CatPais> mapaPaises			 	= catPaisDao.getMapAll();    	
    	HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
    	HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
    	HashMap<String, String> mapaEstados 			= catEstadoDao.getMapEstado();    	
    	
    	modelo.addAttribute("listaInscritos", listaInscritos);
    	modelo.addAttribute("mapaPaises", mapaPaises);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);
    	modelo.addAttribute("mapaModalidades", mapaModalidades);
    	modelo.addAttribute("mapaEstados", mapaEstados);
    	
		return "informes/procedencia/procedencia";
	}	
	
	@RequestMapping("/informes/procedencia/paises")
	public String informesProcedenciaPaises(HttpServletRequest request, Model modelo){		

    	List<CatPais> lisPaises						= catPaisDao.lisInscritos(" ORDER BY NOMBRE_PAIS");
    	HashMap<String, String> mapaTotalPorPais 	= inscritosDao.mapaAlumPorPaises();
    	
    	modelo.addAttribute("lisPaises", lisPaises);
    	modelo.addAttribute("mapaTotalPorPais", mapaTotalPorPais);
    	
		return "informes/procedencia/paises";
	}
	
}