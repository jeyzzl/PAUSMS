package aca.web.evaluacion;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoAlumnoRespDao;
import aca.edo.spring.EdoArea;
import aca.edo.spring.EdoAreaDao;
import aca.edo.spring.EdoDao;
import aca.edo.spring.EdoPeriodo;
import aca.edo.spring.EdoPeriodoDao;

@Controller
public class ContEvaluacionEstudiantil {	
	
	@Autowired	
	EdoDao edoDao;	
	
	@Autowired	
	EdoAlumnoPregDao edoAlumnoPregDao;	
	
	@Autowired	
	EdoAreaDao edoAreaDao;	
	
	@Autowired	
	EdoPeriodoDao edoPeriodoDao;	
	
	@Autowired	
	EdoAlumnoRespDao edoAlumnoRespDao;
	
	
	@RequestMapping("/evaluacion/estudiantil/cuestionarios")
	public String evaluacionEstudiantilCuestionarios(HttpServletRequest request, Model modelo){		

    	List<Edo> lisEdo			= edoDao.getListTipo("E"," ORDER BY ENOC.EDO.F_INICIO DESC");
    	
    	HashMap<String, EdoPeriodo> mapaPeriodos 	= edoPeriodoDao.mapaPeriodos();  	
    	HashMap<String, String> mapaPreguntas 		= edoPeriodoDao.mapaPreguntas();

    	modelo.addAttribute("lisEdo", lisEdo);
    	modelo.addAttribute("mapaPeriodos", mapaPeriodos);
    	modelo.addAttribute("mapaPreguntas", mapaPreguntas);
    	
		return "evaluacion/estudiantil/cuestionarios";
	}	

	@RequestMapping("/evaluacion/estudiantil/nuevo")
	public String evaluacionEstudiantilNuevo(HttpServletRequest request, Model modelo){		
		String edoId 	= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		String mensaje 	= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		
		Edo edo = new Edo();
		
		if(edoDao.existeReg(edoId)) {
			edo = edoDao.mapeaRegId(edoId);
		}
		
		List<EdoPeriodo> lisEdoPeriodo = edoPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		modelo.addAttribute("lisEdoPeriodo", lisEdoPeriodo);
		modelo.addAttribute("edo", edo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "evaluacion/estudiantil/nuevo";
	}	

	@RequestMapping("/evaluacion/estudiantil/grabar")
	public String evaluacionEstudiantilGrabar(HttpServletRequest request, Model modelo){		
		String edoId = request.getParameter("EdoId")==null?"0":request.getParameter("EdoId");
		
		String mensaje = "0";
		
		Edo edo = new Edo();
		
		edo.setNombre(request.getParameter("nombre"));
		edo.setFInicio(request.getParameter("fInicio"));
		edo.setFFinal(request.getParameter("fFinal"));
		edo.setPeriodoId(request.getParameter("periodoId"));			
		edo.setTipo("E");
		edo.setModalidad(request.getParameter("modalidad"));
		edo.setEncabezado(request.getParameter("mensaje"));
		edo.setCargas(request.getParameter("cargas"));
		edo.setTipoEncuesta(request.getParameter("tipoEncuesta"));
		
		if(edoId.equals("0") || edoId.trim().equals("")){
			edoId = edoDao.maximoReg();
			edo.setEdoId(edoId);
			if(edoDao.insertReg(edo)){
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}else{
			edo.setEdoId(edoId);
			if(edoDao.updateReg(edo)){ 
				mensaje = "3";
			}else{
				mensaje = "4";
			}
		}
		
		return "redirect:/evaluacion/estudiantil/nuevo?EdoId="+edoId+"&Mensaje="+mensaje;
	}	

	@RequestMapping("/evaluacion/estudiantil/borrar")
	public String evaluacionEstudiantilBorrar(HttpServletRequest request, Model modelo){		
		String edoId = request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");

		if(edoDao.existeReg(edoId)) {
			edoDao.deleteReg(edoId);
		}
		
		return "redirect:/evaluacion/estudiantil/cuestionarios";
	}	

	@RequestMapping("/evaluacion/estudiantil/preguntas")
	public String evaluacionEstudiantilPreguntas(HttpServletRequest request, Model modelo){				
		String edoId 		= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		
		List<Edo> lisEdo						= edoDao.getListTipo("E","ORDER BY TO_CHAR(ENOC.EDO.F_INICIO,'YYYY-MM-DD') DESC");
		List<EdoAlumnoPreg> lisPreguntas		= edoAlumnoPregDao.getListEdo(edoId, "ORDER BY ORDEN");
		HashMap<String,EdoArea> mapaEdoArea 	= edoAreaDao.mapaEdoArea();
		HashMap<String,String> mapaContestadas 	= edoAlumnoRespDao.mapaPreguntasContestadas(edoId);
		
		Edo edo = new Edo();
		
		if(edoDao.existeReg(edoId)) {
			edo = edoDao.mapeaRegId(edoId);
		}

		String periodoNombre = edoPeriodoDao.getPeriodoNombre(edo.getPeriodoId());
		
		modelo.addAttribute("lisEdo", lisEdo);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("edo", edo);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("mapaEdoArea", mapaEdoArea);
		modelo.addAttribute("mapaContestadas", mapaContestadas);		
		
		return "evaluacion/estudiantil/preguntas";
	}	
	
	@RequestMapping("/evaluacion/estudiantil/nuevaPregunta")
	public String evaluacionEstudiantilNuevaPregunta(HttpServletRequest request, Model modelo){			
		String edoId 		= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		String preguntaId 	= request.getParameter("PreguntaId")==null?"0":request.getParameter("PreguntaId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		List<EdoArea> lisAreas = edoAreaDao.getListAll(" ORDER BY AREA_ID");
		
		EdoAlumnoPreg edoAlumnoPreg = new EdoAlumnoPreg();

		if(edoAlumnoPregDao.existeReg(preguntaId,edoId)) {
			edoAlumnoPreg = edoAlumnoPregDao.mapeaRegId(preguntaId,edoId);
		}else{
			edoAlumnoPreg.setEdoId(edoId);
			edoAlumnoPreg.setPreguntaId(edoAlumnoPregDao.maximoReg(edoId));
		}
		
		modelo.addAttribute("edoAlumnoPreg", edoAlumnoPreg);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisAreas", lisAreas);
		
		return "evaluacion/estudiantil/nuevaPregunta";
	}	

	@RequestMapping("/evaluacion/estudiantil/grabaPregunta")
	public String evaluacionEstudiantilGrabaPregunta(HttpServletRequest request, Model modelo){	
		String edoId 			= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		String preguntaId 		= request.getParameter("PreguntaId")==null?"0":request.getParameter("PreguntaId");
		String mensaje 			= "";
		
		EdoAlumnoPreg edoAlumnoPreg = new EdoAlumnoPreg();
		
		edoAlumnoPreg.setEdoId(edoId);
		edoAlumnoPreg.setPregunta(request.getParameter("pregunta"));
		edoAlumnoPreg.setTipo(request.getParameter("tipo"));
		edoAlumnoPreg.setOrden(request.getParameter("orden"));
		edoAlumnoPreg.setAreaId(request.getParameter("areaId"));
		edoAlumnoPreg.setSeccion(request.getParameter("seccion"));
	
		if(preguntaId.equals("0") || edoAlumnoPregDao.existeReg(preguntaId, edoId)==false){
			edoAlumnoPreg.setPreguntaId(edoAlumnoPregDao.maximoReg(edoId));
			if(edoAlumnoPregDao.insertReg(edoAlumnoPreg)){
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}else{
			edoAlumnoPreg.setPreguntaId(preguntaId);
			if(edoAlumnoPregDao.updateReg(edoAlumnoPreg)){ 
				mensaje = "3";
			}else{
				mensaje = "4";
			}
		}
		
		return "redirect:/evaluacion/estudiantil/nuevaPregunta?EdoId="+edoId+"&PreguntaId="+preguntaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/evaluacion/estudiantil/borrarPregunta")
	public String evaluacionEstudiantilBorrarPregunta(HttpServletRequest request, Model modelo){
		String edoId = request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		String preguntaId = request.getParameter("PreguntaId") == null ? "0" : request.getParameter("PreguntaId");

		if(edoAlumnoPregDao.existeReg(preguntaId,edoId)) {
			edoAlumnoPregDao.deleteReg(preguntaId,edoId);
		}
		
		return "redirect:/evaluacion/estudiantil/preguntas?EdoId="+edoId;
	}	
	
	@RequestMapping("/evaluacion/estudiantil/copiarPreguntas")
	public String evaluacionEstudiantilCopiarPreguntas(HttpServletRequest request, Model modelo){
		String edoId 			= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
		String copiaEdoId 		= request.getParameter("CopiaEdoId") == null ? "0" : request.getParameter("CopiaEdoId");
		List<EdoAlumnoPreg> lisPreguntas	= edoAlumnoPregDao.getListEdo(copiaEdoId, "ORDER BY ORDEN");
		
		for(EdoAlumnoPreg pregunta : lisPreguntas) {
			pregunta.setEdoId(edoId);
			if(edoAlumnoPregDao.existeReg(pregunta.getPreguntaId(), edoId)) {
				pregunta.setPreguntaId(edoAlumnoPregDao.maximoReg(edoId));
			}
			
			edoAlumnoPregDao.insertReg(pregunta);
		}
		
		return "redirect:/evaluacion/estudiantil/preguntas?EdoId="+edoId;
	}	
	
}
