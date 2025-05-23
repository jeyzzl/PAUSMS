package aca.web.informes;

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

import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContInformesTipoAlumno {
	
	@Autowired	
	InscritosDao inscritosDao;
	
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
	CatTipoAlumnoDao catTipoAlumnoDao;	
	
	
	@RequestMapping("/informes/tipoAlumno/tipo")
	public String informesTipoAlumnoTipo(HttpServletRequest request, Model modelo){		

		int numeroOrden		= request.getParameter("Ordenar")==null ? 0 : Integer.parseInt(request.getParameter("Ordenar"));
		String orden = " ,CODIGO_PERSONAL";
		switch(numeroOrden){
			case 2 : orden = " ,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO ";
				break;
			case 3 : orden = " ,PAIS_ID";
				break;
			case 4 : orden = " ,ESTADO_ID";
				break;
			case 5 : orden = " ,MODALIDAD_ID";
				break;
		}

		List<Inscritos> lisAlumnos	= inscritosDao.getAlumnos("ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID"+orden);

		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstados 			= catEstadoDao.getMapPaisEstado("");
		HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaTipos 				= catTipoAlumnoDao.getMapNombreTipo();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
    	modelo.addAttribute("mapaFacultades", mapaFacultades);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);
    	modelo.addAttribute("mapaPaises", mapaPaises);
    	modelo.addAttribute("mapaEstados", mapaEstados);
    	modelo.addAttribute("mapaModalidades", mapaModalidades);
    	modelo.addAttribute("mapaTipos", mapaTipos);
    	
		return "informes/tipoAlumno/tipo";
	}	
	
}