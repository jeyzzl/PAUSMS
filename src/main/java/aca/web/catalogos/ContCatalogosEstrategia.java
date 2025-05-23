package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.catalogo.spring.CatEstrategia;
import aca.catalogo.spring.CatEstrategiaDao;

@Controller
public class ContCatalogosEstrategia {
		
	@Autowired
	private CatEstrategiaDao catEstrategiaDao;
	
	@Autowired
	private CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@RequestMapping("/catalogos/estrategia/estrategia")
	public String catalogoEstrategiaEstrategia(HttpServletRequest request, Model model){
		
		List<CatEstrategia> lisEstrategias 	= catEstrategiaDao.getListAll(" ORDER BY ESTRATEGIA_ID");
		HashMap<String,String> mapaUsadas	= cargaGrupoEvaluacionDao.mapaUsadas();
		
		model.addAttribute("lisEstrategias", lisEstrategias);
		model.addAttribute("mapaUsadas", mapaUsadas);
		
		return "catalogos/estrategia/estrategia";
	}

	@RequestMapping("/catalogos/estrategia/editarEstrategia")
	public String catalogoEstrategiaEditarEstrategia(HttpServletRequest request, Model model){
		
		String estrategiaId 		= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");		
		CatEstrategia estrategia		= new CatEstrategia();
		
		if (catEstrategiaDao.existeReg(estrategiaId)) {
			estrategia = catEstrategiaDao.mapeaRegId(estrategiaId);
		}else {
			estrategia.setEstrategiaId(catEstrategiaDao.maximoReg());
			}
		
		model.addAttribute("estrategia", estrategia);		
		
		return "catalogos/estrategia/editarEstrategia";
	}
	
	@RequestMapping("/catalogos/estrategia/grabarEstrategia")
	public String catalogoAreaGrabarArea(HttpServletRequest request, Model model){				
		
		String estrategiaId 		= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");
		String nombreEstrategia		= request.getParameter("NombreEstrategia")==null?"-":request.getParameter("NombreEstrategia");		
		String mensaje 				= "-";
		
		CatEstrategia estrategia	= new CatEstrategia();
		estrategia.setEstrategiaId(estrategiaId);
		estrategia.setNombreEstrategia(nombreEstrategia);
		
		if (catEstrategiaDao.existeReg(estrategiaId)) {
			if (catEstrategiaDao.updateReg(estrategia) ) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {
			if (catEstrategiaDao.insertReg(estrategia) ) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/catalogos/estrategia/editarEstrategia?EstrategiaId="+estrategia.getEstrategiaId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/estrategia/borrarEstrategia")
	public String catalogoEstrategiaBorrarEstrategia(HttpServletRequest request, Model model){
		
		String estrategiaId 			= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");
		if (catEstrategiaDao.existeReg(estrategiaId)) {
			catEstrategiaDao.deleteReg(estrategiaId);
		}
		
		return "redirect:/catalogos/estrategia/estrategia";
	}
}