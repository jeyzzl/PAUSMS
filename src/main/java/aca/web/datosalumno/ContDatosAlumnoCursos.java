package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumPersonal;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.kardex.spring.KrdxAlumnoActiv;
import aca.kardex.spring.KrdxAlumnoActivDao;
import aca.vista.spring.AlumnoEficiencia;
import aca.vista.spring.AlumnoEficienciaDao;
import aca.vista.spring.AlumnoEvaluacionDao;

@Controller
public class ContDatosAlumnoCursos {	
	
	@Autowired
	private aca.carga.spring.CargaAlumnoDao cargaAlumnoDao;	
	
	@Autowired
	private aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;
	
	@Autowired
	private aca.carga.spring.CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	private aca.carga.spring.CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	private aca.vista.spring.AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	private aca.plan.spring.MapaCursoDao mapaCursoDao;
	
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.catalogo.spring.CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	private aca.catalogo.spring.CatModalidadDao catModalidadDao;
	
	@Autowired
	private aca.catalogo.spring.CatTipoCalDao catTipoCalDao;	
	
	@Autowired
	private aca.vista.spring.AlumnoNotaDao alumnoNotaDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired
	AlumnoEficienciaDao alumnoEficienciaDao;
	
	@Autowired
	AlumnoEvaluacionDao alumnoEvaluacionDao;
	
	@Autowired
	KrdxAlumnoActivDao krdxAlumnoActivDao;	
	
	
	@RequestMapping("/datos_alumno/cursos/materias")
	public String datosAlumnoCursosMaterias(HttpServletRequest request, Model modelo){		
		
		//long time = System.currentTimeMillis();
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");		
		String codigoAlumno 	= "0";
		double promedio			= 0;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			if (cargaId.equals("0")) cargaId = (String) sesion.getAttribute("cargaId"); 
			if (bloqueId.equals("0")) bloqueId = (String) sesion.getAttribute("bloqueId");
		}
		
		// Lista de cargas del alumno
		List<aca.carga.spring.Carga> lisCargas 				= cargaDao.getListAlumno(codigoAlumno);
		
		boolean tieneCarga = false; 	
		for( aca.carga.spring.Carga carga : lisCargas){						
			if (carga.getCargaId().equals(cargaId)){
				tieneCarga = true;				
			}
		}
		
		// Si la cargaId no se encuentra en la lista de cargas del alumno
		if (!tieneCarga && lisCargas.size() > 0) cargaId = lisCargas.get(0).getCargaId();
		
		// Lista de bloques del alumno
		List<aca.carga.spring.CargaBloque> lisBloques 		= cargaBloqueDao. getListaAlumno(cargaId, codigoAlumno, " ORDER BY BLOQUE_ID");
		
		boolean tieneBloque = false;
		for( aca.carga.spring.CargaBloque bloque : lisBloques){						
			if (bloque.getBloqueId().equals(bloqueId)){
				tieneBloque = true;				
			}
		}
		
		// Si el bloqueId no se encuentra en la lista de bloques del alumno
		if (!tieneBloque && lisBloques.size() > 0) bloqueId = lisBloques.get(0).getBloqueId();
				
		// Lista de cursos del alumno
		List<aca.vista.spring.AlumnoCurso> lisCursos 		= alumnoCursoDao.getListAlumnoCarga(codigoAlumno, cargaId, "ORDER BY NOMBRE_CURSO");
		
		// Mapa de cursos de origen
		java.util.HashMap<String,String> mapaCursosOrigen 	= cargaGrupoCursoDao.mapMateriasOrigenAlumno(codigoAlumno);
		
		// Mapa de cursos
		java.util.HashMap<String,aca.plan.spring.MapaCurso> mapaCursos 	= mapaCursoDao.mapaCursosDelAlumno(codigoAlumno);
		
		// Mapa de maestros
		java.util.HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre(cargaId, "NOMBRE");
		
		// Mapa de tipos de curso
		java.util.HashMap<String,aca.catalogo.spring.CatTipoCurso> mapaTipoCurso 		= catTipoCursoDao.getMapAll("");
		
		// Mapa de modalidades
		java.util.HashMap<String,aca.catalogo.spring.CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");		
		
		// Mapa de tipos de calificacion
		java.util.HashMap<String,aca.catalogo.spring.CatTipoCal> mapaTipoCal	= catTipoCalDao.getMapAll("");		
		
		//Mapa de Notas del alumno
		java.util.HashMap<String,aca.vista.spring.AlumnoNota> mapaNotas	= alumnoNotaDao.mapaNotas(codigoAlumno);
		
		String planId			= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
		String carreraId		= mapaPlanDao.getCarreraId(planId);
		String carreraNombre	= catCarreraDao.getNombreCarrera(carreraId);
		AlumPersonal personal	= alumPersonalDao.mapeaRegId(codigoAlumno);
		AlumAcademico academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
		
		promedio 				= alumnoCursoDao.promedioAcreditadoEnCarga(codigoAlumno, cargaId);	
			
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("personal", personal);		
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaCursosOrigen", mapaCursosOrigen);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaTipoCurso", mapaTipoCurso);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("promedio", promedio);
		
		return "datos_alumno/cursos/materias";
	}
	
	@RequestMapping("/datos_alumno/cursos/detallecal")
	public String datosAlumnoCursosDetallecal(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		String codigoMaestro	= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoMaestro, "NOMBRE");
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");	
			
		}

		List<CargaGrupoEvaluacion> lisEvaluaciones = cargaGrupoEvaluacionDao.getLista(cursoCargaId,	" ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA");
		List<CargaGrupoActividad> lisActividades = cargaGrupoActividadDao.getListCurso(cursoCargaId," ORDER BY EVALUACION_ID, ENOC.CARGA_GRUPO_ACTIVIDAD.FECHA");

		HashMap<String, AlumnoEficiencia> mapaEvaluaciones 	= alumnoEficienciaDao.mapaMateria(cursoCargaId);
		HashMap<String, KrdxAlumnoActiv> mapaActividades 	= krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);
		HashMap<String, String> mapPuntosEvaluados 			= alumnoEficienciaDao.mapaPuntosEvaluados(matricula);		
		HashMap<String, String> mapPuntosEvaluaciones		= alumnoEvaluacionDao.mapaPuntosEvaluaciones(cursoCargaId, "'%','P','E'");

		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisActividades", lisActividades);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);		
		modelo.addAttribute("mapPuntosEvaluaciones", mapPuntosEvaluaciones);
			
        
		return "datos_alumno/cursos/detallecal";
	}
	
}