package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchDocumentosDao;

@Controller
public class ContArchivoDocumentos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ArchDocumentosDao archDocumentosDao;
	
	@Autowired
	ArchDocAlumDao archDocAlumDao;
	
	@Autowired
	ArchDocStatusDao archDocStatusDao;
	
	
	@RequestMapping("/archivo/documentos/accion")
	public String archivoDocumentosAccion(HttpServletRequest request, Model modelo){		
		String idDocumento 			= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		ArchDocumentos documento 	= new ArchDocumentos();
		if (archDocumentosDao.existeReg(idDocumento)) {
			documento = archDocumentosDao.mapeaRegId(idDocumento);
		}else {
			documento.setIdDocumento(archDocumentosDao.maximoReg());
		}
		
		
		
		
		modelo.addAttribute("documento", documento);
		
		return "archivo/documentos/accion";
	}
	
	@RequestMapping("/archivo/documentos/documentos")
	public String archivoDocumentosDocumentos(HttpServletRequest request, Model modelo){		
		
		List<ArchDocumentos> lisDocumentos = archDocumentosDao.getListAll(" ORDER BY ORDEN");
		HashMap<String, String> mapDocumentosUsados 		= archDocAlumDao.mapDocumentosUsados();
		HashMap<String, String> mapDocumentosConEstados 	= archDocStatusDao.mapDocumentosConEstados();
		HashMap<String, String> mapEstadosValidos			= archDocStatusDao.mapEstadosValidos();
		
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("mapDocumentosUsados",mapDocumentosUsados);
		modelo.addAttribute("mapDocumentosConEstados",mapDocumentosConEstados);
		modelo.addAttribute("mapEstadosValidos",mapEstadosValidos);
		
		return "archivo/documentos/documentos";
	}
	
	@RequestMapping("/archivo/documentos/grabar")
	public String archivoDocumentosGrabar(HttpServletRequest request, Model modelo){
		
		String idDocumento 			= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String descripcion 			= request.getParameter("Descripcion")==null?"0":request.getParameter("Descripcion");
		String imagen 				= request.getParameter("Imagen")==null?"0":request.getParameter("Imagen");
		String orden 				= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String mostrar 				= request.getParameter("Mostrar")==null?"S":request.getParameter("Mostrar");
		String mensaje				= "-";
		
		ArchDocumentos documento 	= new ArchDocumentos();
		documento.setIdDocumento(idDocumento);
		documento.setDescripcion(descripcion);
		documento.setImagen(imagen);
		documento.setOrden(orden);
		documento.setMostrar(mostrar);
		if (archDocumentosDao.existeReg(idDocumento)){
			// Modificar
			if (archDocumentosDao.updateReg(documento)){
				mensaje = "Updated";
			}else {
				mensaje = "Error Updating";
			}			
		}else {
			// Insertar
			if (archDocumentosDao.insertReg(documento)){				
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/archivo/documentos/accion?IdDocumento="+documento.getIdDocumento()+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/archivo/documentos/borrar")
	public String archivoDocumentosBorrar(HttpServletRequest request, Model modelo){
		
		String idDocumento 			= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");	
					
		if (archDocumentosDao.existeReg(idDocumento)) {
			if (archDocumentosDao.deleteReg(idDocumento)) {				
			} 			
		}
			
		return "redirect:/archivo/documentos/documentos";
	}
}
