package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoCurso;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;


@Controller
public class ContCargaGrupoUnidas {	
	
	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@RequestMapping("/carga_grupo/unidas/unidas")
	public String cargaGrupoUnidasUnidas(HttpServletRequest request, Model modelo) {
		String cargaId = "0";
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			cargaId = (String)sesion.getAttribute("cargaId");
		} 
		String nombreCarga 					= cargaDao.mapeaRegId(cargaId).getNombreCarga();	
		List<CargaGrupoCurso> lisGrupos 	= cargaGrupoCursoDao.lisCursosEnCarga(cargaId, " ORDER BY CURSO_CARGA_ID");
		HashMap<String, MapaCurso> mapaMaterias 		= mapaCursoDao.mapaCursoEnCarga(cargaId);
		HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String, CargaGrupo> mapaGrupos 			= cargaGrupoDao.mapCargaGrupo(cargaId); 
		
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaMaterias", mapaMaterias);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		
		return "carga_grupo/unidas/unidas";
	}
	
	@RequestMapping("/carga_grupo/unidas/materias_compartidas")
	public String cargaGrupoUnidasMateriasCompartidas(HttpServletRequest request, Model modelo) {
		String cargaId = "0";
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			cargaId = (String)sesion.getAttribute("cargaId");
		}
		String nombreCarga 				= cargaDao.mapeaRegId(cargaId).getNombreCarga();
		List<CargaGrupoCurso> lisGrupos	= cargaGrupoCursoDao.lisCursosEnCarga(cargaId, " ORDER BY CURSO_CARGA_ID");
		List<CargaGrupo> lisCargas	 	= cargaGrupoDao.getListaCarga(cargaId, " ORDER BY CURSO_CARGA_ID");
		
		//Nombre Maestro
		//HashMap<String, String> 
		//Nombre Materia
		//HashMap<>
		//Nombre Modalidad
		//HashMap<>
		//Créditos
		//HashMap<>
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "carga_grupo/unidas/materias_compartidas";
	}
}
