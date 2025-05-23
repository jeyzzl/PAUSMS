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

import aca.catalogo.spring.CatNivelInicio;
import aca.catalogo.spring.CatNivelInicioDao;

@Controller
public class ContCatalogosNivelInicio {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatNivelInicioDao catNivelInicioDao;
	
	@RequestMapping("/catalogos/nivelinicio/nivelinicio")
	public String catalogosReligionReligion(HttpServletRequest request, Model model){
		
		List<CatNivelInicio> lista 			= catNivelInicioDao.getListAll("ORDER BY 2");
		HashMap<String,String> mapaUsadas 	= catNivelInicioDao.mapaUsados();
		
		model.addAttribute("lista", lista);		
		model.addAttribute("mapaUsadas", mapaUsadas);
				
		return "catalogos/nivelinicio/nivelinicio";
	}
	
	@RequestMapping("/catalogos/nivelinicio/editar")
	public String catalogosReligionEditar(HttpServletRequest request, Model model){
		
		String nivelInicioId 			= request.getParameter("NivelInicioId")==null?"0":request.getParameter("NivelInicioId");
		CatNivelInicio catNivelInicio 	= new CatNivelInicio();
		if (catNivelInicioDao.existeReg(nivelInicioId)){
			catNivelInicio =  catNivelInicioDao.mapeaRegId(nivelInicioId);
		}else {
			catNivelInicio.setNivelInicioId(catNivelInicioDao.maximoReg());
		}
		
		model.addAttribute("catNivelInicio", catNivelInicio);
		
		return "catalogos/nivelinicio/editar";
	}
	
	@RequestMapping("/catalogos/nivelinicio/grabar")
	public String catalogosReligionGrabar(HttpServletRequest request){
		
		String nivelInicioId		= request.getParameter("NivelInicioId")==null?"0":request.getParameter("NivelInicioId");
		String nombreReligion		= request.getParameter("NombreNivel")==null?"-":request.getParameter("NombreNivel");
		String nombreCorto			= request.getParameter("NombreCorto")==null?"-":request.getParameter("NombreCorto");
		String mensaje				= "-";
		
		CatNivelInicio catNivelInicio 	= new CatNivelInicio();
		catNivelInicio.setNivelInicioId(nivelInicioId);
		catNivelInicio.setNombreNivel(nombreReligion);
		catNivelInicio.setNombreCorto(nombreCorto);
		
		if (catNivelInicioDao.existeReg(nivelInicioId)){
			if (catNivelInicioDao.updateReg(catNivelInicio)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {
			nivelInicioId = catNivelInicioDao.maximoReg();
			catNivelInicio.setNivelInicioId(nivelInicioId);
			if (catNivelInicioDao.insertReg(catNivelInicio)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/catalogos/nivelinicio/editar?NivelInicioId="+nivelInicioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/nivelinicio/borrar")
	public String catalogosReligionBorrar(HttpServletRequest request, Model model){
			
		String nivelInicioId 		= request.getParameter("NivelInicioId")==null?"0":request.getParameter("NivelInicioId");
		if (catNivelInicioDao.existeReg(nivelInicioId) == true) {
			if (catNivelInicioDao.deleteReg(nivelInicioId)) {
			}
		}
		
		return "redirect:/catalogos/nivelinicio/nivelinicio";
	}
	
}