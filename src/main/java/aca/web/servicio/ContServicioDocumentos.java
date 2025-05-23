package aca.web.servicio;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocDocumentos;
import aca.ssoc.spring.SsocDocumentosDao;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocSectorDao;

@Controller
public class ContServicioDocumentos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SsocInicioDao ssocInicioDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocDocumentosDao ssocDocumentosDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@RequestMapping("/servicio/documentos/listado")
	public String servicioDocumentosListado(HttpServletRequest request, Model modelo){
		
		 List<SsocDocumentos> lisDocumentos	= ssocDocumentosDao.lisDocumentos(" ORDER BY DOCUMENTO_ID");
		 HashMap<String,String> mapaNumDoc	= ssocDocumentosDao.mapaDocUso();
		 
		 modelo.addAttribute("lisDocumentos",lisDocumentos);
		 modelo.addAttribute("mapaNumDoc",mapaNumDoc);
		
		return "servicio/documentos/listado";
	}
	
	@RequestMapping("/servicio/documentos/editar")
	public String servicioDocumentosEditar(HttpServletRequest request, Model model){
		
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		SsocDocumentos documento	= new SsocDocumentos();
		
		if (ssocDocumentosDao.existeReg(documentoId)) {
			documento = ssocDocumentosDao.mapeRegId(documentoId);
		}
		
		model.addAttribute("documento", documento);
		
		return "servicio/documentos/editar";
	}
	
	@RequestMapping("/servicio/documentos/grabar")
	public String servicioDocumentosGrabar(HttpServletRequest request, Model model){				
		
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String documentoNombre		= request.getParameter("DocumentoNombre")==null?"-":request.getParameter("DocumentoNombre");
		String orden 				= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String obligatorio			= request.getParameter("Obligatorio")==null?"0":request.getParameter("Obligatorio");
		String acceso				= request.getParameter("Acceso")==null?"0":request.getParameter("Acceso");
		String mensaje 				= "-";
		
		SsocDocumentos documento	= new SsocDocumentos();
		documento.setDocumentoNombre(documentoNombre);
		documento.setOrden(orden);
		documento.setObligatorio(obligatorio);
		documento.setAcceso(acceso);
		
		if (ssocDocumentosDao.existeReg(documentoId)){
			documento.setDocumentoId(documentoId);
			if (ssocDocumentosDao.modificaDocumento(documento)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			documento.setDocumentoId(ssocDocumentosDao.maximoReg());
			if (ssocDocumentosDao.guardaDocumento(documento)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/servicio/documentos/editar?DocumentoId="+documento.getDocumentoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/servicio/documentos/borrar")
	public String servicioDocumentosBorrar(HttpServletRequest request, Model model){
		
		String documentoId	= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		if (ssocDocumentosDao.existeReg(documentoId)) {
			ssocDocumentosDao.eliminaDocumento(documentoId);
		}
		
		return "redirect:/servicio/documentos/listado";
	}
}