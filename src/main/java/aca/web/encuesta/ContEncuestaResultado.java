package aca.web.encuesta;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.encuesta.spring.EncPeriodo;
import aca.encuesta.spring.EncPeriodoDao;
import aca.encuesta.spring.EncPeriodoRes;
import aca.encuesta.spring.EncPeriodoResDao;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContEncuestaResultado {
	
	@Autowired
	private EncPeriodoDao encPeriodoDao;
	
	@Autowired
	private EncPeriodoResDao encPeriodoResDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@RequestMapping("/encuesta/resultado/resultados")
	public String encuestaResultadoResultados(HttpServletRequest request, Model model){
		
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		List<EncPeriodo> lisPeriodo 		= (List<EncPeriodo>)encPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		
		if(periodoId.equals("0")) {
			periodoId = lisPeriodo.get(0).getPeriodoId();
		}
		
		List<EncPeriodoRes> resultados	 	= encPeriodoResDao.getListAll(" WHERE PERIODO_ID = "+periodoId);
		
		HashMap<String, String> mapaDatos 			= alumPersonalDao.mapaDatos(periodoId);
		HashMap<String, String> mapCarreraPlan 		= mapaPlanDao.mapCarreraPlan();
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.mapaCarreras();
		HashMap<String, CatFacultad> mapaFacultad 	= catFacultadDao.getMapFacultad("");
		
		model.addAttribute("periodoId", periodoId);
		model.addAttribute("lisPeriodo", lisPeriodo);
		model.addAttribute("resultados", resultados);
		model.addAttribute("mapaDatos", mapaDatos);
		model.addAttribute("mapCarreraPlan", mapCarreraPlan);
		model.addAttribute("mapaCarreras", mapaCarreras);
		model.addAttribute("mapaFacultad", mapaFacultad);
		
	    return "encuesta/resultado/resultados";
	}
}
