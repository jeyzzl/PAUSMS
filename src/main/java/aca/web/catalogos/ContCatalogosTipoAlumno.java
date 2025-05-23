package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;

@Controller
public class ContCatalogosTipoAlumno {
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@RequestMapping("/catalogos/tipoalumno/tipoalumno")
	public String catalogoTipoAlumnoTipoAlumno(HttpServletRequest request, Model model){
		
		List<CatTipoAlumno> lisTipos = (List<CatTipoAlumno>)catTipoAlumnoDao.getListAll("nombreTipo");
		HashMap<String,String> mapaUsados 	= (HashMap<String,String>) catTipoAlumnoDao.mapaUsados();
		
		
		model.addAttribute("lisTipos", lisTipos);
		model.addAttribute("mapaUsados", mapaUsados);
		
		return "catalogos/tipoalumno/tipoalumno";
	}
	
	@RequestMapping("/catalogos/tipoalumno/editarTipo")
	public String catalogoTipoAlumnoEditarTipo(HttpServletRequest request, Model model){
		
		String tipoId 			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		
		CatTipoAlumno tipo 		= new CatTipoAlumno();
		
		if (catTipoAlumnoDao.existeReg(tipoId)) {
			tipo = catTipoAlumnoDao.mapeaRegId(tipoId);
		}else {
			tipo.setTipoId(catTipoAlumnoDao.maximoReg());
		
		}
		
		model.addAttribute("tipo", tipo);
		
		return "catalogos/tipoalumno/editarTipo";
	}
	
	@RequestMapping("/catalogos/tipoalumno/grabarTipo")
	public String catalogoTipoAlumnoGrabarTipo(HttpServletRequest request, Model model){
		
		String tipoId 				= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String nombreTipo			= request.getParameter("NombreTipo")==null?"-":request.getParameter("NombreTipo");		
		String mensaje 				= "0";
		
		CatTipoAlumno tipo 		= new CatTipoAlumno();
		tipo.setTipoId(tipoId);
		tipo.setNombreTipo(nombreTipo);			
		if (catTipoAlumnoDao.existeReg(tipoId)) {
			if (catTipoAlumnoDao.updateReg(tipo)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {	
			tipo.setTipoId(catTipoAlumnoDao.maximoReg());
			if (catTipoAlumnoDao.insertReg(tipo)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}		
		}										
				
		return "redirect:/catalogos/tipoalumno/editarTipo?TipoId="+tipo.getTipoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/tipoalumno/borrarTipo")
	public String catalogoTipoAlumnoBorrarTipo(HttpServletRequest request, Model model){
		
		String tipoId 			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		
		if (catTipoAlumnoDao.existeReg(tipoId)) {
			catTipoAlumnoDao.deleteReg(tipoId);
		}
		
		return "redirect:/catalogos/tipoalumno/tipoalumno";
	}
	
}