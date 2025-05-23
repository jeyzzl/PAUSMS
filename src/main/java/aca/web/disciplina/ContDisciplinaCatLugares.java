package aca.web.disciplina;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.disciplina.spring.CondLugar;
import aca.disciplina.spring.CondLugarDao;

@Controller
public class ContDisciplinaCatLugares {	
	
	@Autowired
	private CondLugarDao condLugarDao;
	
	@RequestMapping("/disciplina/cat_lugares/lugares")
	public String disciplinaCatLugaresLugares(HttpServletRequest request, Model modelo){		
		
		List<CondLugar> lisLugares	= condLugarDao.getListAll( "ORDER BY 1");
		modelo.addAttribute("lisLugares", lisLugares);
		
		return "disciplina/cat_lugares/lugares";
	}
	
	@RequestMapping("/disciplina/cat_lugares/editar")
	public String disciplinaCatLugaresEditar(HttpServletRequest request, Model modelo){
		
		CondLugar lugar 	= new CondLugar(); 
		String idLugar 		= request.getParameter("IdLugar")==null?"0":request.getParameter("IdLugar");
		if (condLugarDao.existeReg(idLugar)) {
			lugar = condLugarDao.mapeaRegId(idLugar);
		}				
		modelo.addAttribute("lugar", lugar);
		
		return "disciplina/cat_lugares/editar";
	}	
	
	@RequestMapping("/disciplina/cat_lugares/grabar")
	public String disciplinaCatLugaresGrabar(HttpServletRequest request){
		
		CondLugar lugar 		= new CondLugar(); 
		String idLugar 		= request.getParameter("IdLugar")==null?"0":request.getParameter("IdLugar");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		if (condLugarDao.existeReg(idLugar)) {
			lugar.setIdLugar(idLugar);
			lugar.setNombre(nombre);
			condLugarDao.updateReg(lugar);
		}else{ 
			idLugar=condLugarDao.maximoReg();
			lugar.setIdLugar(idLugar);
			lugar.setNombre(nombre);
			condLugarDao.insertReg(lugar);
		}	
		
		return "redirect:/disciplina/cat_lugares/editar?IdLugar="+idLugar;
	}	
	
	@RequestMapping("/disciplina/cat_lugares/borrar")
	public String disciplinaCatLugaresBorrar(HttpServletRequest request){
		
		String idLugar 		= request.getParameter("IdLugar")==null?"0":request.getParameter("IdLugar");
		if (condLugarDao.existeReg(idLugar)) {
			condLugarDao.deleteReg(idLugar);
		}				
		
		return "redirect:/disciplina/cat_lugares/lugares";
	}	
}