package aca.web.informes;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContInformesExtra {
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatFacultadDao CatFacultadDao;
	
	@Autowired
	CatCarreraDao CatCarreraDao;
	
	
	@RequestMapping("/informes/extra/listado")
	public String informesExtraListado(HttpServletRequest request, Model modelo){				
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId	 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");		
		}
		
		// Catálogo de periodos
		List<CatPeriodo> lisPeriodos 		= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas 		= cargaDao.getListPeriodo(periodoId, "ORDER BY CARGA_ID");
		boolean existeCarga = false;
		for(Carga carga:lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size() >= 1) cargaId = lisCargas.get(0).getCargaId(); 
		
		List<KrdxCursoAct> lisExtras = krdxCursoActDao.listExtrasPorCarga(cargaId, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(SUBSTR(CURSO_ID,1,8))),ENOC.CARRERA(SUBSTR(CURSO_ID,1,8))");	
		
		HashMap<String, AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosConExtra(cargaId);
		HashMap<String,MapaCurso> mapCursos 		= mapaCursoDao.mapaCursosEnCarga( cargaId);
		HashMap<String,MapaPlan> mapPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,CatFacultad> mapFacultades	= CatFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapCarreras		= CatCarreraDao.getMapAll("");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisExtras", lisExtras);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapPlanes", mapPlanes);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapCarreras", mapCarreras);
		
		return "informes/extra/listado";
	}		

}