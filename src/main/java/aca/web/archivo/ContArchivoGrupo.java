package aca.web.archivo;

import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchGrupo;
import aca.archivo.spring.ArchGrupoDao;
import aca.archivo.spring.ArchGrupoDocumento;
import aca.archivo.spring.ArchGrupoDocumentoDao;
import aca.archivo.spring.ArchGrupoPlanDao;


@Controller
public class ContArchivoGrupo {
	
	@Autowired
	ArchGrupoDao archGrupoDao;
	
	@Autowired
	ArchGrupoDocumentoDao archGrupoDocumentoDao;
	
	@Autowired
	ArchDocumentosDao archDocumentosDao;
	
	@Autowired
	ArchGrupoPlanDao archGrupoPlanDao;
	
	@RequestMapping("/archivo/grupo/listado")
	public String archivoGrupoListado(HttpServletRequest request, Model modelo){
		
		List<ArchGrupo> lisArchGrupo		= archGrupoDao.listTodos(" ORDER BY GRUPO_NOMBRE");
		
		HashMap<String,String> mapaDocumentos 	= archGrupoDocumentoDao.mapaArchGrupoDocumentoPorGrupo();
		HashMap<String,String> mapaPlanes		= archGrupoPlanDao.mapaPlanesPorGrupo();
		
		modelo.addAttribute("lisArchGrupo", lisArchGrupo);		
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "archivo/grupo/listado";
	}

	@RequestMapping("/archivo/grupo/nuevo")
	public String archivoGrupoNuevoGrupo(HttpServletRequest request, Model modelo){
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		
		ArchGrupo grupo = new ArchGrupo();
		
		if(archGrupoDao.existeReg(grupoId)) {
			grupo = archGrupoDao.mapeaRegId(grupoId);
		}else {
			grupo.setGrupoId(archGrupoDao.maxReg());
		}
		
		modelo.addAttribute("grupo", grupo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "archivo/grupo/nuevo";
	}

	@RequestMapping("/archivo/grupo/grabar")
	public String archivoGrupoGrabar(HttpServletRequest request, Model modelo){
		String grupoId 		= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String nombre 		= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String comentario 	= request.getParameter("Comentario") == null ? "0" : request.getParameter("Comentario");
		String mensaje 		= request.getParameter("Mensaje") == null ? "2" : request.getParameter("Mensaje");
		
		ArchGrupo grupo = new ArchGrupo();
		
		grupo.setGrupoId(grupoId);
		grupo.setGrupoNombre(nombre);
		grupo.setComentario(comentario);
		
		if(archGrupoDao.existeReg(grupoId)) {
			if(archGrupoDao.updateReg(grupo)) {
				mensaje = "1";	
			}
		}else {
			if(archGrupoDao.insertReg(grupo)) {
				mensaje = "1";	
			}
		}
		
		return "redirect:/archivo/grupo/nuevo?GrupoId="+grupoId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/archivo/grupo/borrar")
	public String archivoGrupoBorrar(HttpServletRequest request, Model modelo){
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		
		if(archGrupoDao.existeReg(grupoId)) {
			archGrupoDao.deleteReg(grupoId);
		}
		
		return "redirect:/archivo/grupo/listado";
	}
	
	@RequestMapping("/archivo/grupo/documentos")
	public String archivoGrupoDocumentos(HttpServletRequest request, Model modelo){
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		
		List<ArchDocumentos> lisArchDocumentos		= archDocumentosDao.getListAll("ORDER BY IDDOCUMENTO");
		HashMap<String,ArchGrupoDocumento> mapaArchGrupoDocumento = archGrupoDocumentoDao.mapaArchGrupoDocumento(grupoId);
		
		modelo.addAttribute("grupoId", grupoId);
		modelo.addAttribute("lisArchDocumentos", lisArchDocumentos);
		modelo.addAttribute("mapaArchGrupoDocumento", mapaArchGrupoDocumento);
		
		return "archivo/grupo/documentos";
	}

	@RequestMapping("/archivo/grupo/agregarDocumento")
	public String archivoGrupoAgregarDocumento(HttpServletRequest request){
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		
		ArchGrupoDocumento grupo = new ArchGrupoDocumento();
		
		grupo.setGrupoId(grupoId);
		grupo.setDocumentoId(documentoId);
		
		archGrupoDocumentoDao.insertReg(grupo);
		
		return "redirect:/archivo/grupo/documentos?GrupoId="+grupoId;
	}
	
	@RequestMapping("/archivo/grupo/borrarDocumento")
	public String archivoGrupoBorrarDocumento(HttpServletRequest request, Model modelo){
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		
		if(archGrupoDocumentoDao.existeReg(grupoId,documentoId)) {
			archGrupoDocumentoDao.deleteReg(grupoId,documentoId);
		}
		
		return "redirect:/archivo/grupo/documentos?GrupoId="+grupoId;
	}

}
