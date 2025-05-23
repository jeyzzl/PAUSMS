package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatRecogida;
import aca.catalogo.spring.CatRecogidaDao;

@Controller
public class ContCatalogosRecogida {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatRecogidaDao catRecogidaDao;
	
	@RequestMapping("/catalogos/recogida/recogida")
	public String catalogosRecogidaRecogida(HttpServletRequest request, Model model){
		
		List<CatRecogida> lista 			= catRecogidaDao.getListAll("ORDER BY 2");
		HashMap<String,String> mapaUsadas 	= catRecogidaDao.mapaUsados();
		
		model.addAttribute("lista", lista);		
		model.addAttribute("mapaUsadas", mapaUsadas);
				
		return "catalogos/recogida/recogida";
	}
	
	@RequestMapping("/catalogos/recogida/editar")
	public String catalogosRecogidaEditar(HttpServletRequest request, Model model){
		
		String recogidaId 			= request.getParameter("RecogidaId")==null?"0":request.getParameter("RecogidaId");
		CatRecogida catRecogida 	= new CatRecogida();
		if (catRecogidaDao.existeReg(recogidaId)){
			catRecogida =  catRecogidaDao.mapeaRegId(recogidaId);
		}else {
			catRecogida.setRecogidaId(catRecogidaDao.maximoReg());
		}
		
		model.addAttribute("catRecogida", catRecogida);
		
		return "catalogos/recogida/editar";
	}
	
	@RequestMapping("/catalogos/recogida/grabar")
	public String catalogosRecogidaGrabar(HttpServletRequest request){
		
		String recogidaId			= request.getParameter("RecogidaId")==null?"0":request.getParameter("RecogidaId");
		String nombre		        = request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String nombreCorto			= request.getParameter("NombreCorto")==null?"-":request.getParameter("NombreCorto");
		String mensaje				= "-";
		
		CatRecogida catRecogida 	= new CatRecogida();
		catRecogida.setRecogidaId(recogidaId);
		catRecogida.setNombre(nombre);
		catRecogida.setNombreCorto(nombreCorto);
		
		if (catRecogidaDao.existeReg(recogidaId)){
			if (catRecogidaDao.updateReg(catRecogida)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {
			recogidaId = catRecogidaDao.maximoReg();
			catRecogida.setRecogidaId(recogidaId);
			if (catRecogidaDao.insertReg(catRecogida)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/catalogos/recogida/editar?RecogidaId="+recogidaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/recogida/borrar")
	public String catalogosRecogidaBorrar(HttpServletRequest request, Model model){
			
		String recogidaId 		= request.getParameter("RecogidaId")==null?"0":request.getParameter("RecogidaId");
		if (catRecogidaDao.existeReg(recogidaId) == true) {
			if (catRecogidaDao.deleteReg(recogidaId)) {
			}
		}
		
		return "redirect:/catalogos/recogida/recogida";
	}
	
}