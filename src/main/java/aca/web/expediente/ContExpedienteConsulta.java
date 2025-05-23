package aca.web.expediente;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.exp.spring.ExpDocumento;
import aca.exp.spring.ExpDocumentoDao;
import aca.exp.spring.ExpEmpleado;
import aca.exp.spring.ExpEmpleadoDao;
import aca.exp.spring.ExpTipoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContExpedienteConsulta {	
	
	@Autowired
	ExpDocumentoDao expDocumentoDao;
	
	@Autowired
	ExpEmpleadoDao expEmpleadoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	ExpTipoDao expTipoDao;
	
	
	@RequestMapping("/expediente/consulta/listado")
	public String expedienteConsultaListado(HttpServletRequest request, Model modelo){	
				
		String codigoEmpleado 	= "0";
		String nombreEmpleado 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado"); 
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
		}
		
		List<Maestros> lisMaestros 					= maestrosDao.lisExpedientesActivos(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<ExpDocumento> lisDocumentos			= expDocumentoDao.lisTodos("ORDER BY DOCUMENTO_ID");		
		HashMap<String,String> mapaDocDelEmpleado 	= expEmpleadoDao.mapaDocDelEmpleado();
		HashMap<String,String> mapaExpTipo		 	= expTipoDao.mapaTodos();
		
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("lisMaestros",lisMaestros);
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("mapaDocDelEmpleado",mapaDocDelEmpleado);
		modelo.addAttribute("mapaExpTipo",mapaExpTipo);
		
		return "expediente/consulta/listado";
	}
	
	@RequestMapping("/expediente/consulta/subir")
	public String expedienteConsultaSubir(HttpServletRequest request, Model modelo){				
		
		String codigoEmpleado 		= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("codigoEmpleado", codigoEmpleado);			
		}
		
		return "redirect:/expediente/consulta/empleado";
	}	
	
	@RequestMapping("/expediente/consulta/empleado")
	public String expedienteConsultaEmpleado(HttpServletRequest request, Model modelo){		
		
		String codigoEmpleado 	= "0";
		String nombreEmpleado 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado"); 
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
		}
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String documentoNombre 	= "-";
		
		boolean existeHoja		= false;
		ExpEmpleado expEmpleado = new ExpEmpleado();
		if (expEmpleadoDao.existeReg(codigoEmpleado, documentoId, hoja)) {
			existeHoja 			= true;
			expEmpleado 		= expEmpleadoDao.mapeaRegId(codigoEmpleado, documentoId, hoja);
			documentoNombre 	= expDocumentoDao.getDocumentoNombre(documentoId);
		}
		
		List<ExpDocumento> lisDocumentos 			= expDocumentoDao.lisPorEmpleado(codigoEmpleado, " ORDER BY ORDEN");
		HashMap<String,ExpEmpleado> mapaDocumentos	= expEmpleadoDao.mapaPorEmpleado(codigoEmpleado);
		
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("existeHoja",existeHoja);
		modelo.addAttribute("expEmpleado",expEmpleado);
		modelo.addAttribute("documentoNombre",documentoNombre);
		
		modelo.addAttribute("lisDocumentos",lisDocumentos);	
		modelo.addAttribute("mapaDocumentos",mapaDocumentos);
		
		return "expediente/consulta/empleado";
	}
	
	@RequestMapping("/expediente/consulta/descargar")
	public void expedienteConsultaDescargar(HttpServletResponse response, HttpServletRequest request){
		String codigoEmpleado 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado");
		}		
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		
		ExpEmpleado expEmpleado = new ExpEmpleado();

		if(expEmpleadoDao.existeReg(codigoEmpleado, documentoId, hoja)){
			expEmpleado = expEmpleadoDao.mapeaRegId(codigoEmpleado, documentoId, hoja);
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+expEmpleado.getNombre()+ "\"");
			response.getOutputStream().write(expEmpleado.getArchivo());
			response.flushBuffer();			
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
	
}