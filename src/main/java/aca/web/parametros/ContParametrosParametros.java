package aca.web.parametros;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;

@Controller
public class ContParametrosParametros {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	ParametrosDao parametrosDao;	
	
	@RequestMapping("/parametros/param/parametros")
	public String usuariosRolesRoles(HttpServletRequest request, Model modelo){		
		
		String id 							= request.getParameter("Id")==null?"1":request.getParameter("Id");
		Parametros parametros 				= new Parametros();
		if (parametrosDao.existeReg(id)) {
			parametros = parametrosDao.mapeaRegId(id);
		}
		modelo.addAttribute("parametros", parametros);
		
		return "parametros/param/parametros";
	}	
	
	@RequestMapping("/parametros/param/grabar")
	public String usuariosRolesGrabar(HttpServletRequest request, Model modelo){		
		
		String id 				= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String institucion		= request.getParameter("Institucion")==null?"-":request.getParameter("Institucion");
		String certificados		= request.getParameter("Certificados")==null?"-":request.getParameter("Certificados");
		String constancias 		= request.getParameter("Constancias")==null?"-":request.getParameter("Constancias");
		String cardex			= request.getParameter("Cardex")==null?"-":request.getParameter("Cardex");
		String mensaje 			= "-"; 
		
		Parametros parametros 				= new Parametros();
		parametros.setInstitucion(institucion);
		parametros.setCertificados(certificados);
		parametros.setConstancias(constancias);
		parametros.setCardex(cardex);
		if (parametrosDao.existeReg(id)) {
			if (parametrosDao.updateReg(parametros)) {
				mensaje = "Updated";
			}else {
				mensaje	= "Error Updating"; 
			}
		}else{
			if (parametrosDao.insertReg(parametros)) {
				mensaje = "Saved";
			}else {
				mensaje	= "Error Saving";			
			}
		}	
		
		return "redirect:/parametros/param/parametros?Id="+id+"&Mensaje="+mensaje;
	}
	
}