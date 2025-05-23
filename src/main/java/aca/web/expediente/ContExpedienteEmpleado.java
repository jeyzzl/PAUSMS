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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.exp.spring.ExpDocumento;
import aca.exp.spring.ExpDocumentoDao;
import aca.exp.spring.ExpEmpleado;
import aca.exp.spring.ExpEmpleadoDao;
import aca.exp.spring.ExpTipo;
import aca.exp.spring.ExpTipoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContExpedienteEmpleado {	
	
	@Autowired
	ExpDocumentoDao expDocumentoDao;
	
	@Autowired
	ExpEmpleadoDao expEmpleadoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	ExpTipoDao expTipoDao;
	
	
	@RequestMapping("/expediente/empleado/listado")
	public String expedienteEmpleadoListado(HttpServletRequest request, Model modelo){	
				
		String codigoEmpleado 	= "0";
		String nombreEmpleado 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado"); 
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
		}
		
		List<Maestros> lisMaestros 					= maestrosDao.lisEnExpediente(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<ExpDocumento> lisDocumentos			= expDocumentoDao.lisTodos("ORDER BY DOCUMENTO_ID");		
		HashMap<String,String> mapaDocDelEmpleado 	= expEmpleadoDao.mapaDocDelEmpleado();
		HashMap<String,ExpTipo> mapaTipos		 	= expTipoDao.mapaTipos();	
		
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("lisMaestros",lisMaestros);
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("mapaDocDelEmpleado",mapaDocDelEmpleado);
		modelo.addAttribute("mapaTipos",mapaTipos);
		
		return "expediente/empleado/listado";
	}
	
	@RequestMapping("/expediente/empleado/cambiarTipo")
	public String expedienteEmpleadoCambiarTipo(HttpServletRequest request, Model modelo){				
		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String tipo		 		= request.getParameter("Tipo")==null?"D":request.getParameter("Tipo");

		if(expTipoDao.existeReg(codigoEmpleado)) {			
			expTipoDao.updateTipo(codigoEmpleado, tipo);
		}else {
			expTipoDao.insertReg(codigoEmpleado, tipo, "A");
		}
		
		return "redirect:/expediente/empleado/listado";
	}	
	
	@RequestMapping("/expediente/empleado/cambiarEstado")
	public String expedienteEmpleadoCambiarEstado(HttpServletRequest request, Model modelo){				
		String codigoEmpleado 		= request.getParameter("CodigoEmp")==null?"0":request.getParameter("CodigoEmp");
		String estado		 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");

		if(expTipoDao.existeReg(codigoEmpleado)) {
			expTipoDao.updateEstado(codigoEmpleado, estado);
		}else {
			expTipoDao.insertReg(codigoEmpleado, "D", estado);
		}
		
		return "redirect:/expediente/empleado/listado";
	}
	
	@RequestMapping("/expediente/empleado/subir")
	public String expedienteEmpleadoSubir(HttpServletRequest request, Model modelo){				
		
		String codigoEmpleado 		= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("codigoEmpleado", codigoEmpleado);			
		}
		
		return "redirect:/expediente/empleado/empleado";
	}	
	
	@RequestMapping("/expediente/empleado/empleado")
	public String expedienteEmpleadoEmpleado(HttpServletRequest request, Model modelo){		
		
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
		
		return "expediente/empleado/empleado";
	}
	
	@RequestMapping("/expediente/empleado/editar")
	public String expedienteEmpleadoEditar(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado 	= "0";
		String nombreEmpleado 	= "-"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado");
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
		}
		String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");		
		
		boolean existeHoja		= false;
		ExpEmpleado expEmpleado = new ExpEmpleado();
		expEmpleado.setDocumentoId(documentoId);
		expEmpleado.setHoja(hoja);
		if (expEmpleadoDao.existeReg(codigoEmpleado, documentoId, hoja)) {
			existeHoja 			= true;
			expEmpleado 		= expEmpleadoDao.mapeaRegId(codigoEmpleado, documentoId, hoja);			
		}
		
		List<ExpDocumento> lisDocumentos 			= expDocumentoDao.lisTodos(" ORDER BY ORDEN");
						
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);				
		modelo.addAttribute("expEmpleado",expEmpleado);
		modelo.addAttribute("existeHoja",existeHoja);	
		
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		
		return "expediente/empleado/editar";
	}
	
	@RequestMapping("/expediente/empleado/grabarArchivo")
	public String expedienteEmpleadoGrabarDocumento(@RequestParam("archivo") MultipartFile file,HttpServletRequest request){
		
		String codigoEmpleado 	= "0";		 
		String codigoPersonal 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado");
			codigoPersonal 		=(String)sesion.getAttribute("codigoPersonal");
		}		
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String mensaje 			= "";
		
		ExpEmpleado expEmpleado = new ExpEmpleado();
		try {
			expEmpleado.setCodigoPersonal(codigoEmpleado);
			expEmpleado.setDocumentoId(documentoId);
			expEmpleado.setHoja(hoja);
			expEmpleado.setUsuario(codigoPersonal);
			expEmpleado.setFecha(aca.util.Fecha.getHoy());
			expEmpleado.setArchivo(file.getBytes());
			expEmpleado.setNombre(file.getOriginalFilename());
			if(!expEmpleadoDao.existeReg(codigoEmpleado, documentoId, hoja)){
				expEmpleadoDao.insertReg(expEmpleado);
				mensaje = "1";
			}else {
				expEmpleadoDao.updateReg(expEmpleado);
				mensaje = "2";
			}
		}catch(Exception ex){		
			
		}		
		
		return "redirect:/expediente/empleado/editar?Mensaje="+mensaje+"&DocumentoId="+documentoId+"&Hoja="+hoja;
	}	
	
	@RequestMapping("/expediente/empleado/borrar")
	public String expedienteEmpleadoBorrar(HttpServletRequest request){
		
		String codigoEmpleado 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado 		=(String)sesion.getAttribute("codigoEmpleado");
		}		
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String mensaje 			= "";		
		if(expEmpleadoDao.existeReg(codigoEmpleado, documentoId, hoja)){
			expEmpleadoDao.deleteReg(codigoEmpleado, documentoId, hoja);
			mensaje = "1";
		}else {			
			mensaje = "2";
		}		
		return "redirect:/expediente/empleado/empleado?Mensaje="+mensaje+"&DocumentoId="+documentoId+"&Hoja="+hoja;
	}
	
	@RequestMapping("/expediente/empleado/descargar")
	public void expedienteEmpleadoDescargar(HttpServletResponse response, HttpServletRequest request){
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