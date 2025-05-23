package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;

@Controller
public class ContCargaGrupoSinAlumnos {	
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaAcademicaDao cargaAcaDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;	
	
	
	@RequestMapping("/carga_grupo/sinalumnos/carga")
	public String cargaGrupoSinAlumnosCarga(HttpServletRequest request, Model modelo){		
		
		String carga = request.getParameter("Carga") == null ? "0" : request.getParameter("Carga");
		
		List<Carga> lisCarga 						= cargaDao.getListAllActivas("ORDER BY CARGA_ID DESC");
		HashMap<String, String> mapaMaterias    	= cargaGrupoDao.mapaMatSinAlumnos();

		modelo.addAttribute("carga",carga);
		modelo.addAttribute("lisCarga",lisCarga);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		
		return "carga_grupo/sinalumnos/carga";
	}
	
	@RequestMapping("/carga_grupo/sinalumnos/sinalumno")
	public String cargaGrupoSinAlumnosSinAlumno(HttpServletRequest request, Model modelo){		
		
		String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");		
		String nombreCarga			= cargaDao.getNombreCarga(cargaId);		
		
		// Lista de materias en una carga	
		List<CargaAcademica> listaCargas				= cargaAcaDao.getListSinAlumno(cargaId, "ORDER BY 6, 5");
		HashMap<String, CatFacultad> mapaFacultades    	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras    	= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("listaCargas",listaCargas);
		modelo.addAttribute("nombreCarga",nombreCarga);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);	
		
		return "carga_grupo/sinalumnos/sinalumno";
	}
	
	@Transactional
	@RequestMapping("/carga_grupo/sinalumnos/borrar")
	public String cargaGrupoSinAlumnosBorrar(HttpServletRequest request){
	
		String cursoCargaId		= request.getParameter("CursoCargaId");
		String cargaId				= request.getParameter("CargaId");		
		boolean borrarActividades	= false;
		boolean borrarEvaluaciones	= false;
		String mensaje 				= "-";
		
		if (cargaGrupoActividadDao.existeReg(cursoCargaId)) {
			if (cargaGrupoActividadDao.deleteTodos(cursoCargaId)){
				borrarActividades = true;
			}
		}else{
			borrarActividades = true;
		}
		
		if (borrarActividades){			
			if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId)) {
				if (cargaGrupoEvaluacionDao.deleteTodos(cursoCargaId)) {
					borrarEvaluaciones = true;
				}
			}else{
				borrarEvaluaciones = true;
			}
		}
		// Si se borraron las evaluaciones y las actividades intentamos borrar el grupo
		if (borrarActividades && borrarEvaluaciones) {
			if (cargaGrupoCursoDao.existeReg(cursoCargaId) == true){
				if (cargaGrupoCursoDao.deleteGrupo(cursoCargaId)){
					if (cargaGrupoDao.existeReg(cursoCargaId)){
						if (cargaGrupoDao.deleteReg(cursoCargaId)){
							mensaje = "Deleted";
						}else {
							mensaje = "Error deleting "+cursoCargaId;
						}
					}else {
						mensaje = "Group not found "+cursoCargaId;
					}
				}else {
					mensaje = "Error deleting the subjects in the group with ID "+cursoCargaId;
				}
			}else {
				mensaje = "No subjects in the group "+cursoCargaId;
			}
		}	
		
		return "redirect:/carga_grupo/sinalumnos/sinalumno?CargaId="+cargaId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/carga_grupo/sinalumnos/borrarAll")
	public String cargaGrupoSinAlumnosBorrarAll(HttpServletRequest request){
		String cargaId					= request.getParameter("CargaId");
		List<CargaAcademica> lisCargas	= cargaAcaDao.getListSinAlumno(cargaId, "ORDER BY 6, 5");
		
		String mensaje 			= "-";
		int totalBorrados		= 0;
		int totalErrores		= 0;		
		
		for (CargaAcademica carga : lisCargas) {
			
			boolean borrarActividades	= false;
			boolean borrarEvaluaciones	= false;
			
			if (cargaGrupoActividadDao.existeReg(carga.getCursoCargaId())) {
				if (cargaGrupoActividadDao.deleteTodos(carga.getCursoCargaId())){
					borrarActividades = true;
				}
			}else{
				borrarActividades = true;
			}
			
			if (borrarActividades){			
				if (cargaGrupoEvaluacionDao.existeReg(carga.getCursoCargaId())) {
					if (cargaGrupoEvaluacionDao.deleteTodos(carga.getCursoCargaId())) {
						borrarEvaluaciones = true;
					}
				}else{
					borrarEvaluaciones = true;
				}
			}
			// Si se borraron las evaluaciones y las actividades intentamos borrar el grupo
			if (borrarActividades && borrarEvaluaciones) {
				if (cargaGrupoCursoDao.existeReg(carga.getCursoCargaId()) == true){
					if (cargaGrupoCursoDao.deleteGrupo(carga.getCursoCargaId())){
						if (cargaGrupoDao.existeReg(carga.getCursoCargaId())){
							if (cargaGrupoDao.deleteReg(carga.getCursoCargaId())){
								totalBorrados++;
							}else {
								totalErrores++;
							}
						}else{
							totalErrores++;
						}
					}else {
						totalErrores++;
					}
				}else {
					totalErrores++;
				}
			}		
		}		
		mensaje = "Deleted subjects: "+totalBorrados;
		
		return "redirect:/carga_grupo/sinalumnos/sinalumno?CargaId="+cargaId+"&Mensaje="+mensaje;
	}
		
}