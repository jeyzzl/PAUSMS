package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
//import aca.catalogo.spring.CatNivelRepository;

@Controller
public class ContCatalogosNivel {
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	/*@Autowired*/
	//private CatNivelRepository catNivelRepository;
	
	@RequestMapping("/catalogos/nivel/nivel")
	public String catalogoNivelNivel(HttpServletRequest request, Model model){
		
		List<CatNivel> lisNiveles = (List<CatNivel>)catNivelDao.getListAll(" ORDER BY NOMBRE_NIVEL");		
		//List<CatNivel> lisNiveles = (List<CatNivel>)catNivelRepository.findAll(Sort.by(Sort.Direction.ASC, "nombreNivel").and(Sort.by("orden")) );
		//List<CatNivel> lisNiveles = (List<CatNivel>)catNivelRepository.findAllByOrderByOrdenAsc();
		HashMap<String,String> mapaUsados 	= (HashMap<String,String>) catNivelDao.mapaUsados();
		
		
		model.addAttribute("lisNiveles", lisNiveles);
		model.addAttribute("mapaUsados", mapaUsados);
		
		return "catalogos/nivel/nivel";
	}
	
	@RequestMapping("/catalogos/nivel/vueUno")
	public String catalogoNivelVueUno(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueUno";
	}
	
	@RequestMapping("/catalogos/nivel/vueDos")
	public String catalogoNivelVueDos(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueDos";
	}
	
	@RequestMapping("/catalogos/nivel/vueTres")
	public String catalogoNivelVueTres(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueTres";
	}
	
	@RequestMapping("/catalogos/nivel/vueCuatro")
	public String catalogoNivelVueCuatro(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueCuatro";
	}
	
	@RequestMapping("/catalogos/nivel/vueCinco")
	public String catalogoNivelVueCinco(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueCinco";
	}
	
	@RequestMapping("/catalogos/nivel/vueSeis")
	public String catalogoNivelVueSeis(HttpServletRequest request, Model model){		
		return "catalogos/nivel/vueSeis";
	}
	
	@RequestMapping("/catalogos/nivel/editarNivel")
	public String catalogoNivelEditarNivel(HttpServletRequest request, Model model){
		
		String nivelId 			= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");
		
		CatNivel nivel 		= new CatNivel();
		
		if (catNivelDao.existeReg(nivelId)) {
			nivel 		= catNivelDao.mapeaRegId(nivelId);
		}else {
			nivel.setNivelId(catNivelDao.maximoReg());
		}
		
		
		model.addAttribute("nivel", nivel);
		
		return "catalogos/nivel/editarNivel";
	}
	
	@RequestMapping("/catalogos/nivel/grabarNivel")
	public String catalogoModalidadGrabarNivel(HttpServletRequest request, Model model){
		
		String nivelId 				= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");
		String nombreNivel			= request.getParameter("NombreNivel")==null?"-":request.getParameter("NombreNivel");
		String estado 				= "A";
		String mensaje 				= "0";
		
		CatNivel nivel 		= new CatNivel();
		nivel.setNivelId(nivelId);
		nivel.setNombreNivel(nombreNivel);
		nivel.setEstado(estado);		
						 
		if (catNivelDao.existeReg(nivelId) ) {
			if (catNivelDao.updateReg(nivel)){
				mensaje = "Updated";
			}else{				
				mensaje = "Error updating";
			}
		}else {
			nivel.setNivelId(catNivelDao.maximoReg());
			if (catNivelDao.insertReg(nivel)){
				mensaje = "Saved";
			}else{				
				mensaje = "Error saving";
			}
		}												
				
		return "redirect:/catalogos/nivel/editarNivel?NivelId="+nivel.getNivelId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/nivel/borrarNivel")
	public String catalogoNivelBorrarNivel(HttpServletRequest request, Model model){
		
		String nivelId 			= request.getParameter("NivelId")==null?"0":request.getParameter("NivelId");	
		
		if (catNivelDao.existeReg(nivelId)) {
			catNivelDao.deleteReg(nivelId);
		}
		
		return "redirect:/catalogos/nivel/nivel";
	}
}