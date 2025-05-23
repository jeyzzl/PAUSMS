package aca.web.admlinea;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmDocumento;
import aca.admision.spring.AdmDocumentoDao;
import aca.admision.spring.AdmFormato;
import aca.admision.spring.AdmFormatoDao;
import aca.admision.spring.AdmRequisitoDao;


@Controller
public class ContAmdlineaDocumentos {		
	
	@Autowired
	private AdmDocumentoDao admDocumentoDao;
	
	@Autowired
	private AdmRequisitoDao admRequisitoDao;
	
	@Autowired
	private AdmFormatoDao admFormatoDao;
	
	
	@RequestMapping("/admlinea/documentos/documentos")
	public String admlineaDocumentosDocumentos(HttpServletRequest request, Model modelo){
		
		List<AdmDocumento> lisDocumento		 		= admDocumentoDao.getAll("ORDER BY DOCUMENTO_ID");
		HashMap<String, String> mapaTotalDoc	 	= admRequisitoDao.mapaTotalPorDocumento();
		HashMap<String, AdmFormato> mapaFormatos 	= admFormatoDao.mapaFormatos();
		
		modelo.addAttribute("lisDocumento", lisDocumento);
		modelo.addAttribute("mapaTotalDoc", mapaTotalDoc);
		modelo.addAttribute("mapaFormatos", mapaFormatos);
		
		return "admlinea/documentos/documentos";
	}	
	
	@RequestMapping("/admlinea/documentos/editar")
	public String admlineaDocumentosEditar(HttpServletRequest request, Model modelo){
		
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		
		AdmDocumento admDocumento 	= new AdmDocumento();		
		if (admDocumentoDao.existeReg(documentoId)) {
			admDocumento = admDocumentoDao.mapeaRegId(documentoId);
		}
		
		List<AdmFormato> listaFormato = admFormatoDao.getAll("ORDER BY FORMATO_NOMBRE");

		modelo.addAttribute("listaFormato", listaFormato);
		modelo.addAttribute("admDocumento", admDocumento);
		
		return "admlinea/documentos/editar";
	}
	
	@RequestMapping("/admlinea/documentos/grabar")
	public String admlineaDocumentosGrabar(HttpServletRequest request, Model modelo){
		
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");		
		String documentoNombre 		= request.getParameter("DocumentoNombre")==null?"-":request.getParameter("DocumentoNombre");
		String corto				= request.getParameter("Corto")==null?"-":request.getParameter("Corto");
		String tipo 				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String comentario 			= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String original 			= request.getParameter("Original")==null?"0":request.getParameter("Original");
		String formato 				= request.getParameter("Formato")==null?"0":request.getParameter("Formato");		
		String orden 				= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String mensaje				= "-";
		
		AdmDocumento admDocumento 	= new AdmDocumento();
		admDocumento.setDocumentoId(documentoId);
		admDocumento.setDocumentoNombre(documentoNombre);
		admDocumento.setCorto(corto);
		admDocumento.setTipo(tipo);
		admDocumento.setComentario(comentario);
		admDocumento.setOriginal(original);
		admDocumento.setFormatoId(formato);
		admDocumento.setOrden(orden);
		
		if (admDocumentoDao.existeReg(documentoId) == false){
			documentoId = admDocumentoDao.maximoReg();
			admDocumento.setDocumentoId(documentoId);
			if (admDocumentoDao.insertReg(admDocumento)){
				mensaje = "Saved"+admDocumento.getDocumentoId();
			}else{
				mensaje = "Error saving: "+admDocumento.getDocumentoId();
			}
		}else{			
			if (admDocumentoDao.updateReg(admDocumento)){
				mensaje = "Updated: "+admDocumento.getDocumentoId();
			}else{
				mensaje = "Error updating: "+admDocumento.getDocumentoId();
			}				
		}
		
		return "redirect:/admlinea/documentos/editar?DocumentoId="+documentoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/documentos/borrar")
	public String admlineaDocumentosBorrar(HttpServletRequest request, Model modelo){
		
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");		
		String mensaje				= "-";
		if (admDocumentoDao.existeReg(documentoId)){
			if (admDocumentoDao.deleteReg(documentoId)){
				mensaje = "Deleted:"+documentoId;
			}else{
				mensaje = "Error deleting: "+documentoId;
			}
		}
		
		return "redirect:/admlinea/documentos/documentos?Mensaje="+mensaje;
	}
	
}
