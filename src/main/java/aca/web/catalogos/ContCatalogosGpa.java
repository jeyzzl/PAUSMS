package aca.web.catalogos;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatGradePoint;
import aca.catalogo.spring.CatGradePointDao;

@Controller
public class ContCatalogosGpa {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatGradePointDao gradePointDao;
	
	@RequestMapping("/catalogos/gpa/gpa")
	public String catalogoGpaGra(HttpServletRequest request, Model model){
		
		List<CatGradePoint> lisGrados = gradePointDao.lisTodos("ORDER BY PUNTOS DESC");
		model.addAttribute("lisGrados", lisGrados);		
		
		return "catalogos/gpa/gpa";
	}	
	
	@RequestMapping("/catalogos/gpa/accion")
	public String catalogoGpaAccion(HttpServletRequest request, Model model){		
		String gradeId				= request.getParameter("GradePointId")==null?"0":request.getParameter("GradePointId");
		
		CatGradePoint grade 	= new CatGradePoint();
		if (gradePointDao.existeReg(gradeId)) {
			grade = gradePointDao.mapeaRegId(gradeId);		
		}else {
			grade.setGpId(gradePointDao.maximoReg());
		}
		
		model.addAttribute("grade", grade);
		
		return "catalogos/gpa/accion";
	}
	
	@RequestMapping("/catalogos/gpa/grabar")
	public String catalogoGpaGrabar(HttpServletRequest request, Model model){		
		String gradeId		= request.getParameter("GradePointId")==null?"0":request.getParameter("GradePointId");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String inicio 		= request.getParameter("Inicio")==null?"0":request.getParameter("Inicio");
		String fin 			= request.getParameter("Fin")==null?"0":request.getParameter("Fin");
		String puntos		= request.getParameter("Puntos")==null?"0":request.getParameter("Puntos");
		String titulo		= request.getParameter("Titulo")==null?"0":request.getParameter("Titulo");
		String mensaje 		= "-";
		CatGradePoint grade 	= new CatGradePoint();
		grade.setGpId(gradeId);
		grade.setGpNombre(nombre);
		grade.setInicio(inicio);
		grade.setFin(fin);
		grade.setPuntos(puntos);
		grade.setTitulo(titulo);
		if (gradePointDao.existeReg(gradeId)) {			
			gradePointDao.updateReg(grade);
			mensaje = "Updated";
		}else {			
			gradePointDao.insertReg(grade);
			mensaje = "Saved";
		}		
		return "redirect:/catalogos/gpa/accion?GradePointId="+gradeId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/gpa/borrar")
	public String catalogoGpaBorrar(HttpServletRequest request, Model model){		
		String gradeId		= request.getParameter("GradePointId")==null?"0":request.getParameter("GradePointId");
		
		if (gradePointDao.existeReg(gradeId)) {			
			gradePointDao.deleteReg(gradeId);
		}
		
		return "redirect:/catalogos/gpa/gpa";
	}
	
}