package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecCategoria;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecPuestoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContTallerCategoria {	
	
	@Autowired
	private BecCategoriaDao becCategoriaDao;
	
	@Autowired
	private BecPuestoDao becPuestoDao;
	
	@Autowired
	private BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@RequestMapping("/taller/categoria/categoria")
	public String tallerCategoriaCategoria(HttpServletRequest request, Model modelo){	
		
		List <BecCategoria> lisCategoria 			= becCategoriaDao.getListAll("ORDER BY ESTADO, CATEGORIA_ID");
		HashMap<String, String> mapaMaestros 		= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String, String> mapaCategorias 		= becPuestoDao.mapaCategorias();
		HashMap<String, String> mapaTotalAlumnos	= becPuestoAlumnoDao.mapaTotAlumnosPorCategoria();
		
		modelo.addAttribute("lisCategoria", lisCategoria);	
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaTotalAlumnos", mapaTotalAlumnos);
		
		return "taller/categoria/categoria";
	}
	
	@RequestMapping("/taller/categoria/editar")
	public String tallerCategoriaEditar(HttpServletRequest request, Model modelo){	
		
		String categoriaId		= request.getParameter("CategoriaId")==null?"0":request.getParameter("CategoriaId");
		
		BecCategoria becCategoria = new BecCategoria();
		if (becCategoriaDao.existeReg(categoriaId)) {
			becCategoria = becCategoriaDao.mapeaRegId(categoriaId);
		}
		
		modelo.addAttribute("becCategoria", becCategoria);
		
		return "taller/categoria/editar";
	}
	
	@RequestMapping("/taller/categoria/grabar")
	public String tallerCategoriaGrabar(HttpServletRequest request, Model modelo){	
		
		String categoriaId		= request.getParameter("CategoriaId")==null?"0":request.getParameter("CategoriaId");
		String categoriaNombre	= request.getParameter("CategoriaNombre")==null?"-":request.getParameter("CategoriaNombre");
		String usuario			= request.getParameter("CategoriaUsuario")==null?"0":request.getParameter("CategoriaUsuario");
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String pdf				= request.getParameter("PDF")==null?"0":request.getParameter("PDF");
		String mensaje 			= "-";
		
		BecCategoria becCategoria = new BecCategoria();
		if (becCategoriaDao.existeReg(categoriaId)) {
			becCategoria.setCategoriaId(categoriaId);
			becCategoria.setCategoriaNombre(categoriaNombre);
			becCategoria.setUsuario(usuario);
			becCategoria.setEstado(estado);
			becCategoria.setPdf(pdf);
			if (becCategoriaDao.updateReg(becCategoria)) {
				mensaje = "¡ Modificado !";
			}else {
				mensaje = "¡ Error al modificar !";
			}
		}else {
			categoriaId = becCategoriaDao.maximo();
			becCategoria.setCategoriaId(categoriaId);
			becCategoria.setCategoriaNombre(categoriaNombre);
			becCategoria.setUsuario(usuario);
			becCategoria.setEstado(estado);
			becCategoria.setPdf(pdf);
			if (becCategoriaDao.insertReg(becCategoria)) {
				mensaje = "¡ Grabado !";
			}else {
				mensaje = "¡ Error al grabar !";
			}
		}
		
		return "redirect:/taller/categoria/editar?CategoriaId="+categoriaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/categoria/borrar")
	public String tallerCategoriaBorrar(HttpServletRequest request, Model modelo){	
		
		String categoriaId		= request.getParameter("CategoriaId")==null?"0":request.getParameter("CategoriaId");
		String mensaje 			= "-";	
		
		if (becCategoriaDao.existeReg(categoriaId)) {			
			if (becCategoriaDao.deleteReg(categoriaId)) {
				mensaje = "¡ Borrado !";
			}else {
				mensaje = "¡ Error al borrar !";
			}
		}		
		
		return "redirect:/taller/categoria/categoria?Mensaje="+mensaje;
	}
}