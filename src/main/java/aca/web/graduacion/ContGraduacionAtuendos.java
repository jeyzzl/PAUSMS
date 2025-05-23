package aca.web.graduacion;

import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAtuendo;

@Controller
public class ContGraduacionAtuendos {	
	
	@Autowired
	private aca.graduacion.spring.CarreraOrdenDao carreraOrdenDao;

	@Autowired
	private aca.alumno.spring.AlumAtuendoDao alumAtuendoDao;	

	@RequestMapping("/graduacion/atuendos/atuendos")
	public String graduacionAtuendosAtuendos(Model modelo) {
		
		modelo.addAttribute("listaAtuendos", alumAtuendoDao.getListAtuendo());
		
		return "graduacion/graduacion/atuendos";
	}

	@RequestMapping("/graduacion/atuendos/grabaAtuendo")
	public String graduacionAtuendosGrabaAtuendos(HttpServletRequest request, Model modelo) {
		
		String atuendoId = request.getParameter("AtuendoId")==null?"-":request.getParameter("AtuendoId");
		
		AlumAtuendo atuendo = new AlumAtuendo();
		
		if(!atuendoId.equals("-")) {
			atuendo = alumAtuendoDao.mapeaRegId(atuendoId);
		}
		
		modelo.addAttribute("atuendo", atuendo);
		modelo.addAttribute("atuendoId", alumAtuendoDao.maximoReg());
		
		return "graduacion/graduacion/grabaAtuendo";
	}

	@RequestMapping("/graduacion/atuendos/grabar")
	public String graduacionAtuendosGrabar(HttpServletRequest request, Model modelo) {
		
		String atuendoId 	= request.getParameter("AtuendoId")==null?"":request.getParameter("AtuendoId");
		String descripcion 	= request.getParameter("Descripcion")==null?"":request.getParameter("Descripcion");
		String precio 		= request.getParameter("Precio")==null?"":request.getParameter("Precio");
		
		AlumAtuendo atuendo = new AlumAtuendo();
		
		atuendo.setAtuendoId(atuendoId);
		atuendo.setDescripcion(descripcion);
		atuendo.setPrecio(precio);
		
		if(alumAtuendoDao.existeReg(atuendoId)) {
			alumAtuendoDao.updateReg(atuendo);
		}else {
			alumAtuendoDao.insertReg(atuendo);
		}
		
		
		return "redirect:/graduacion/atuendos/atuendos";
	}

	@RequestMapping("/graduacion/atuendos/borrar")
	public String graduacionAtuendosBorrar(HttpServletRequest request, Model modelo) {
		
		String atuendoId 	= request.getParameter("AtuendoId")==null?"":request.getParameter("AtuendoId");
		
		if(alumAtuendoDao.existeReg(atuendoId)) {
			alumAtuendoDao.deleteReg(atuendoId);
		}
		
		return "redirect:/graduacion/atuendos/atuendos";
	}
	
}