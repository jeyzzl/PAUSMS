package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPlanDao;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocStatus;
import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchStatus;
import aca.archivo.spring.ArchStatusDao;
import aca.archivo.spring.ArchivoDao;

@Controller
public class ContArchivoDocumentoEstado {	
	
	@Autowired	
	private ArchDocumentosDao archDocumentosDao;
	
	@Autowired	
	private ArchStatusDao archStatusDao;
	
	@Autowired	
	private ArchDocStatusDao archDocStatusDao;
	
	@Autowired
	ArchivoDao archivoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	ArchDocAlumDao archDocAlumDao;	
	
	
	@RequestMapping("/archivo/documento_estado/elegir_documento")
	public String archivoDocumentoEstadoElegirDocumento(HttpServletRequest request, Model modelo){
		
		String IdDocumento			= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String documentoNombre 		= archDocumentosDao.getDescripcion(IdDocumento);
		
		List<ArchDocumentos> lisDocumentos			= archDocumentosDao.getListAll("ORDER BY DESCRIPCION");
		List<ArchDocStatus> lisEstadosAsignados		= archDocStatusDao.lisStatus(IdDocumento, " ORDER BY IDSTATUS");
		List<ArchStatus> lisEstadosRestantes		= archStatusDao.getListRest(IdDocumento, "ORDER BY IDSTATUS");
		HashMap<String, String> mapaEstados			= archStatusDao.mapaStatus();
		HashMap<String, String> mapaUsados			= archDocAlumDao.mapDocEstadoUsados();
		
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisEstadosAsignados", lisEstadosAsignados);
		modelo.addAttribute("lisEstadosRestantes", lisEstadosRestantes);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaUsados", mapaUsados);
		
		return "archivo/documento_estado/elegir_documento";
	}
	
	@RequestMapping("/archivo/documento_estado/grabarEstado")
	public String archivoDocumentoEstadoGrabarEstado(HttpServletRequest request, Model modelo){
		
		String idDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String idStatus 	= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		String estado 		= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		ArchDocStatus docStatus = new ArchDocStatus();
		
		if (archDocStatusDao.existeReg(idDocumento, idStatus)) {
			docStatus = archDocStatusDao.mapeaRegId(idDocumento, idStatus);
			
			if(estado.equals("A")){
				estado = "I";			
			}else{
				estado = "A";			
			}	
			docStatus.setEstado(estado);
			
			archDocStatusDao.updateReg(docStatus);			
		}
		
		return "redirect:/archivo/documento_estado/elegir_documento?IdDocumento="+idDocumento;
	}
	
	@RequestMapping("/archivo/documento_estado/grabarValido")
	public String archivoDocumentoEstadoGrabarValido(HttpServletRequest request, Model modelo){
		
		String idDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String idStatus 	= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		String valido 		= request.getParameter("Valido")==null?"-":request.getParameter("Valido");
		ArchDocStatus docStatus = new ArchDocStatus();
		
		if (archDocStatusDao.existeReg(idDocumento, idStatus)) {
			docStatus = archDocStatusDao.mapeaRegId(idDocumento, idStatus);			
			if(valido.equals("S")){
				valido = "N";			
			}else{
				valido = "S";			
			}	
			docStatus.setValido(valido);
			
			archDocStatusDao.updateReg(docStatus);			
		}
		
		return "redirect:/archivo/documento_estado/elegir_documento?IdDocumento="+idDocumento;
	}
	
	@RequestMapping("/archivo/documento_estado/borrarStatus")
	public String archivoDocumentoBorrarStatus(HttpServletRequest request){
		String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String idStatus			= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		String mensaje			= "-";
		if (archDocStatusDao.existeReg(idDocumento, idStatus)) {
			if (archDocStatusDao.deleteReg(idDocumento, idStatus)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}
		}	
		
		return "redirect:/archivo/documento_estado/elegir_documento?IdDocumento="+idDocumento+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/archivo/documento_estado/grabarStatus")
	public String archivoDocumentoGrabarStatus(HttpServletRequest request){
		String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String idStatus			= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		String mensaje			= "-";
		
		ArchDocStatus status 	= new ArchDocStatus();		
		if (!archDocStatusDao.existeReg(idDocumento, idStatus)){
			status.setIdDocumento(idDocumento);
			status.setIdStatus(idStatus);
			status.setEstado("A");			
			if (archDocStatusDao.insertReg(status)){
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}	
		
		return "redirect:/archivo/documento_estado/elegir_documento?IdDocumento="+idDocumento+"&Mensaje="+mensaje;
	}	

}
