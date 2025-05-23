package aca.web.extranjeros;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.leg.spring.LegDocumento;
import aca.leg.spring.LegDocumentoDao;

@Controller
public class ContExtranjerosDocumentos{
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private LegDocumentoDao legDocumentoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/extranjeros/documentos/add_documento")
	public String extranjerosDocumentosAddDocumentos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosCandados|extranjerosCandadosActualizar");
		return "extranjeros/documentos/add_documento";
	}
	
	@RequestMapping("/extranjeros/documentos/borrar")
	public String extranjerosDocumentosBorrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosDocumentos|extranjerosDocumentosBorrar");
		return "extranjeros/documentos/borrar";
	}
	
	@RequestMapping("/extranjeros/documentos/documentos")
	public String extranjerosDocumentosDocumentos(HttpServletRequest request, Model modelo){
		
		List<LegDocumento> lisDocumentos = legDocumentoDao.lisAll(" ORDER BY DESCRIPCION");
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		
		return "extranjeros/documentos/documentos";
	}
	
	@RequestMapping("/extranjeros/documentos/editar_documento")
	public String extranjerosDocumentosEditarDocumentos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosDocumentos|extranjerosDocumentosEditarDocumentos");
		return "extranjeros/documentos/editar_documento";
	}
	
	@RequestMapping("/extranjeros/documentos/editar")
	public String extranjerosDocumentosEditar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExtranjerosDocumentos|extranjerosDocumentosEditar");
		return "extranjeros/documentos/editar";
	}
	
	@RequestMapping("/extranjeros/documentos/grabar")
	public String extranjerosDocumentosGrabar(HttpServletRequest request){
		return "extranjeros/documentos/grabar";
	}
	
	@RequestMapping("/extranjeros/documentos/pregunta")
	public String extranjerosDocumentosPregunta(HttpServletRequest request){
		return "extranjeros/documentos/pregunta";
	}
	
}