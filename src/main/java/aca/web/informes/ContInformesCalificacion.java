package aca.web.informes;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.emp.spring.Empleado;
import aca.emp.spring.EmpleadoDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxCursoCalDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;

@Controller
public class ContInformesCalificacion {	
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	KrdxCursoCalDao krdxCursoCalDao;
	
	
	@RequestMapping("/informes/calificacion/diferida")
	public String informesCalificacionDiferida(HttpServletRequest request, Model modelo){
 
		List<KrdxCursoAct> lisDiferidas				= krdxCursoActDao.lisDiferidas(" ORDER BY ENOC.FACULTAD(ENOC.ALUM_CARRERA(CODIGO_PERSONAL)),ENOC.ALUM_CARRERA(CODIGO_PERSONAL)");
		HashMap<String,AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosDiferidos(); 	
		HashMap<String, Empleado> mapEmpleados		= empleadoDao.mapEmpleadosDiferidos();
		HashMap<String,CargaGrupo> mapGrupos		= cargaGrupoDao.mapaGruposDiferidos();
		HashMap<String,CatFacultad> mapFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapCarreras		= catCarreraDao.mapaCarreras();
		HashMap<String,MapaCurso> mapCursos			= mapaCursoDao.mapaCursosDiferidos();
		HashMap<String,CatTipoCal> mapTipos			= catTipoCalDao.getMapAll("");
		HashMap<String,String> mapFechas			= krdxCursoCalDao.mapaFechaFinal();
		
		modelo.addAttribute("lisDiferidas", lisDiferidas);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapEmpleados", mapEmpleados);
		modelo.addAttribute("mapGrupos", mapGrupos);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapTipos", mapTipos);
		modelo.addAttribute("mapFechas", mapFechas);
		
		return "informes/calificacion/diferida";
	}		

}