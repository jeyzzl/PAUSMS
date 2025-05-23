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

import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;

@Controller
public class ContCatalogosReligion {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatReligionDao catReligionDao;
	
	@RequestMapping("/catalogos/religion/religion")
	public String catalogosReligionReligion(HttpServletRequest request, Model model){
		
		List<CatReligion> lista 			= catReligionDao.getListAll("ORDER BY 2");
		HashMap<String,String> mapaUsadas 	= catReligionDao.mapaUsados();
		
		model.addAttribute("lista", lista);		
		model.addAttribute("mapaUsadas", mapaUsadas);
				
		return "catalogos/religion/religion";
	}
	
	@RequestMapping("/catalogos/religion/editar")
	public String catalogosReligionEditar(HttpServletRequest request, Model model){
		
		String religionId 			= request.getParameter("ReligionId")==null?"0":request.getParameter("ReligionId");
		CatReligion catReligion 	= new CatReligion();
		if (catReligionDao.existeReg(religionId)){
			catReligion =  catReligionDao.mapeaRegId(religionId);
		}else {
			catReligion.setReligionId(catReligionDao.maximoReg());
		}
		
		model.addAttribute("catReligion", catReligion);
		
		return "catalogos/religion/editar";
	}
	
	@RequestMapping("/catalogos/religion/grabar")
	public String catalogosReligionGrabar(HttpServletRequest request){
		
		String religionId			= request.getParameter("ReligionId")==null?"0":request.getParameter("ReligionId");
		String nombreReligion		= request.getParameter("NombreReligion")==null?"-":request.getParameter("NombreReligion");
		String nombreCorto			= request.getParameter("NombreCorto")==null?"-":request.getParameter("NombreCorto");
		String mensaje				= "-";
		
		CatReligion catReligion 	= new CatReligion();
		catReligion.setReligionId(religionId);
		catReligion.setNombreReligion(nombreReligion);
		catReligion.setNombreCorto(nombreCorto);
		
		if (catReligionDao.existeReg(religionId)){
			if (catReligionDao.updateReg(catReligion)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {
			religionId = catReligionDao.maximoReg();
			catReligion.setReligionId(religionId);
			if (catReligionDao.insertReg(catReligion)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/catalogos/religion/editar?ReligionId="+religionId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/religion/borrar")
	public String catalogosReligionBorrar(HttpServletRequest request, Model model){
			
		String religionId 		= request.getParameter("ReligionId")==null?"0":request.getParameter("ReligionId");
		if (catReligionDao.existeReg(religionId) == true) {
			if (catReligionDao.deleteReg(religionId)) {
			}
		}
		
		return "redirect:/catalogos/religion/religion";
	}
	
}