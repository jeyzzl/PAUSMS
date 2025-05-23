package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatAcomodo;
import aca.catalogo.spring.CatAcomodoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;

import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCalDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContCargaGrupoTraspaso {
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;		
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;	

	@Autowired
	AlumAcademicoDao alumAcademicoDao;	
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;

	@Autowired
	CatAcomodoDao catAcomodoDao;
	
	@RequestMapping("/carga_grupo/traspaso/traspaso")
	public String cargaGrupoTraspasoTraspaso(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
		String planId 			= request.getParameter("PlanId")==null?"-":request.getParameter("PlanId");
		String ciclo 			= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");	 
		String cargaSesion 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			cargaSesion			= (String) sesion.getAttribute("cargaId");
		}
		
		List<Carga> lisCargas 							= cargaDao.getListAll("ORDER BY ORDEN DESC");
		boolean existe = false;
		for (Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaSesion)) existe = true;
		}
		if (existe && cargaId.equals("0")) {
			cargaId = cargaSesion;
		}else if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}

		List<MapaPlan> lisPlanes = mapaPlanDao.getListAll(" ORDER BY PLAN_ID");
		if(lisPlanes.size() >= 1 && planId.equals("0")){
			planId = lisPlanes.get(0).getPlanId();
		}
		
		String condicion = "";
		if(!planId.equals("-")) condicion = " AND PLAN_ID = '"+planId+"'";
		if(!ciclo.equals("0")) condicion =  condicion + " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = TO_NUMBER('"+ciclo+"','99') AND ESTADO = '1')";

		List<AlumnoCurso> lisAlumnos				= alumnoCursoDao.getListTraspaso(cargaId, condicion+" AND TIPOCAL_ID = 'I' ORDER BY CODIGO_PERSONAL");

		HashMap<String,MapaCurso> mapaCursos		= mapaCursoDao.getAllMapaCursos(" ORDER BY CURSO_ID");
		HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapAlumnosEnCargas(cargaId); 
		HashMap<String,AlumPlan> mapaPlan			= alumPlanDao.getMapInscritos(" ORDER BY CODIGO_PERSONAL");
		HashMap<String,AlumEstado> mapaEstado		= alumEstadoDao.mapaAlumEstado(cargaId, " ORDER BY CODIGO_PERSONAL");
		HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
		
		HashMap<String,CargaAlumno> mapaCargas		= cargaAlumnoDao.mapaCargasAlumno("'"+cargaId+"'");
		HashMap<String, CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
		HashMap<String,String> mapTipoCal 			= catTipoCalDao.mapTipoCal();
		HashMap<String,String> mapaNombrePlan		= mapaPlanDao.mapNombrePlan();
		
		HashMap<String,CatAcomodo> mapaAcomodo 		= catAcomodoDao.getMapAll(" ORDER BY ACOMODO_ID");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisPlanes",lisPlanes);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapTipoCal", mapTipoCal);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaNombrePlan", mapaNombrePlan);
		modelo.addAttribute("mapaAcomodo", mapaAcomodo);
		
		return "carga_grupo/traspaso/traspaso";
	}
}