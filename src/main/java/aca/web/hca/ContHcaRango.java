package aca.web.hca;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.hca.spring.HcaRango;
import aca.hca.spring.HcaRangoDao;

@Controller
public class ContHcaRango {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private HcaRangoDao hcaRangoDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/hca/rango/agregar")
	public String hcaRangoAgregar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContHcaRango|hcaRangoAgregar");
		return "hca/rango/agregar";
	}	
	
	@RequestMapping("/hca/rango/rango")
	public String hcaRangoRango(HttpServletRequest request, Model modelo){		
		List<HcaRango> lisRangos 				= hcaRangoDao.lisTodos("ORDER BY NIVEL_ID, MODALIDAD_ID, RANGO_ID");
		HashMap<String, CatNivel> mapaTotal	 	= catNivelDao.getMapAll("");
		HashMap<String, CatModalidad> mapaTodos	= catModalidadDao.getMapAll("");

		
		modelo.addAttribute("lisRangos", lisRangos);
		modelo.addAttribute("mapaTotal", mapaTotal);
		modelo.addAttribute("mapaTodos", mapaTodos);



		return "hca/rango/rango";
	}
	
}