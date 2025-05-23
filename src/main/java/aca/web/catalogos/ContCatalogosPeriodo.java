package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatArea;
import aca.catalogo.spring.CatAreaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCatalogosPeriodo {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	
	@RequestMapping("/catalogos/periodo/periodo")
	public String catalogoEditarCarrera(HttpServletRequest request, Model model){
				
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		List<CatPeriodo> lisPeriodo 		= (List<CatPeriodo>)catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		HashMap<String, String> cargasPorPeriodo = (HashMap<String, String>)catPeriodoDao.getCaragasEnPeriodo("ORDER BY PERIODO DESC");
		
		model.addAttribute("PeriodoId", periodoId);
		model.addAttribute("lisPeriodo", lisPeriodo);		
		model.addAttribute("cargasPorPeriodo", cargasPorPeriodo);
		
		return "catalogos/periodo/periodo";
	}
	
	@RequestMapping("/catalogos/periodo/editarPeriodo")
	public String catalogoEditarEditarPeriodo(HttpServletRequest request, Model model){
				
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		CatPeriodo periodo					= new CatPeriodo();
		
		if(catPeriodoDao.existeReg(periodoId)){
			periodo = catPeriodoDao.mapeaRegId(periodoId);
		}	
		
		model.addAttribute("PeriodoId", periodoId);
		model.addAttribute("periodo", periodo);
		
		return "catalogos/periodo/editarPeriodo";
	}
	
	@RequestMapping("/catalogos/periodo/grabarPeriodo")
	public String catalogoEditarGrabarPeriodo(HttpServletRequest request, Model model){
				
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String nombre	 		= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String fechaIni 		= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin 		= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String exceptoPron		= request.getParameter("ExceptoPron")==null?"0":request.getParameter("ExceptoPron");
		String mensaje 			= "-";
		
		CatPeriodo periodo = new CatPeriodo();
		periodo.setPeriodoId(periodoId);
		periodo.setNombre(nombre);
		periodo.setFechaIni(fechaIni);
		periodo.setFechaFin(fechaFin);
		periodo.setEstado(estado);
		periodo.setExceptoPron(exceptoPron);
		
		if(catPeriodoDao.existeReg(periodoId)){
			if (catPeriodoDao.updateReg(periodo)) {
				mensaje = "Actualizado";			
			}else{
				mensaje = "Error";
			}
		}else {
			if(catPeriodoDao.insertReg(periodo)){
				mensaje = "Se insertó";	
			}else{
				mensaje = "No se guardó";
			}
		}
		
		
		
		return "redirect:/catalogos/periodo/editarPeriodo?PeriodoId="+periodo.getPeriodoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/periodo/borrarPeriodo")
	public String catalogoBorrarPeriodo(HttpServletRequest request, Model modelo) {
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
		
		if (catPeriodoDao.existeReg(periodoId)) {
			catPeriodoDao.deleteReg(periodoId);
		}		
		
		return "redirect:/catalogos/periodo/periodo";
	}	

}