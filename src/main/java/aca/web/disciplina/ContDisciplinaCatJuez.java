package aca.web.disciplina;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.disciplina.spring.CondJuez;
import aca.disciplina.spring.CondJuezDao;

@Controller
public class ContDisciplinaCatJuez {
	
	@Autowired
	private CondJuezDao condJuezDao;
	
	@RequestMapping("/disciplina/cat_juez/juez")
	public String disciplinaCatJuezJuez(HttpServletRequest request, Model modelo){
		
		List<CondJuez> lisJueces	= condJuezDao.getListAll(" ORDER BY 1");		
		modelo.addAttribute("lisJueces", lisJueces);
		
		return "disciplina/cat_juez/juez";
	}
	
	@RequestMapping("/disciplina/cat_juez/editar")
	public String disciplinaCatJuezEditar(HttpServletRequest request, Model modelo){
		
		CondJuez juez 		= new CondJuez(); 
		String idJuez 		= request.getParameter("IdJuez")==null?"0":request.getParameter("IdJuez");
		if (condJuezDao.existeReg(idJuez)) {
			juez = condJuezDao.mapeaRegId(idJuez);
		}				
		modelo.addAttribute("juez", juez);
		
		return "disciplina/cat_juez/editar";
	}	
	
	@RequestMapping("/disciplina/cat_juez/grabar")
	public String disciplinaCatJuezGrabar(HttpServletRequest request){
		
		CondJuez juez 		= new CondJuez(); 
		String idJuez 		= request.getParameter("IdJuez")==null?"0":request.getParameter("IdJuez");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		if (condJuezDao.existeReg(idJuez)) {
			juez.setIdJuez(idJuez);
			juez.setNombre(nombre);
			condJuezDao.updateReg(juez);
		}else{ 
			idJuez=condJuezDao.maximoReg();
			juez.setIdJuez(idJuez);
			juez.setNombre(nombre);
			condJuezDao.insertReg(juez);
		}
		
		return "redirect:/disciplina/cat_juez/editar?IdJuez="+idJuez;
	}	
	
	@RequestMapping("/disciplina/cat_juez/borrar")
	public String disciplinaCatJuezBorrar(HttpServletRequest request){
		
		String idJuez 		= request.getParameter("IdJuez")==null?"0":request.getParameter("IdJuez");
		if (condJuezDao.existeReg(idJuez)) {
			condJuezDao.deleteReg(idJuez);
		}				
		
		return "redirect:/disciplina/cat_juez/juez";
	}	
}