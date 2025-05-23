package aca.web.expediente;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.exp.spring.ExpDocumento;
import aca.exp.spring.ExpDocumentoDao;
import aca.exp.spring.ExpEmpleadoDao;

@Controller
public class ContExpDocumentos{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private ExpDocumentoDao expDocumentoDao;
	
	@Autowired
	private ExpEmpleadoDao expEmpleadoDao;
	
	@RequestMapping("/expediente/documentos/listado")
	public String expedienteDocumentosListado(HttpServletRequest request, Model modelo){
		
		List<ExpDocumento> lista				= expDocumentoDao.lisTodos("ORDER BY ORDEN");
		HashMap<String,String> mapaDocEmpleado 	= expEmpleadoDao.mapaDocEmpleados();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaDocEmpleado", mapaDocEmpleado);
		
		return "expediente/documentos/listado";
	}	
	
	@RequestMapping("/expediente/documentos/editar")
	public String expedienteDocumentosEditar(HttpServletRequest request, Model modelo){
		
		String id 		= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		ExpDocumento documento =  new ExpDocumento();
		
		if(expDocumentoDao.existeReg(id)) {
			documento = expDocumentoDao.mapeaRegId(id);
		}
		
		modelo.addAttribute("documento", documento);
		modelo.addAttribute("mensaje", mensaje);
		
		return "expediente/documentos/editar";
	}	
	
	@RequestMapping("/expediente/documentos/grabar")
	public String expedienteDocumentosGrabar(HttpServletRequest request, Model modelo){
		
		String id 		= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre 	= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String corto 	= request.getParameter("Corto")==null?"0":request.getParameter("Corto");
		String orden 	= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String mensaje 	= "0";
		
		ExpDocumento documento =  new ExpDocumento();
		
		documento.setDocumentoId(id);
		documento.setDocumentoNombre(nombre);
		documento.setCorto(corto);
		documento.setOrden(orden);
		
		if(!expDocumentoDao.existeReg(id)) {
			id = expDocumentoDao.maximoReg();
			documento.setDocumentoId(id);
			
			if(expDocumentoDao.insertReg(documento)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(expDocumentoDao.updateReg(documento)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/expediente/documentos/editar?Mensaje="+mensaje+"&Id="+id;
	}	
	
	@RequestMapping("/expediente/documentos/borrar")
	public String expedienteDocumentosBorrar(HttpServletRequest request, Model modelo){
		
		String id = request.getParameter("Id")==null?"0":request.getParameter("Id");
		
		if(expDocumentoDao.existeReg(id)) {
			expDocumentoDao.deleteReg(id);
		}
		
		return "redirect:/expediente/documentos/listado";
	}	
	
}