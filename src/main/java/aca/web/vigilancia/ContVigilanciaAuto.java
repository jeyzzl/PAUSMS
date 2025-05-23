package aca.web.vigilancia;

import java.io.OutputStream;
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

import aca.vigilancia.spring.VigAuto;
import aca.vigilancia.spring.VigAutoDao;
import aca.vigilancia.spring.VigDoc;
import aca.vigilancia.spring.VigDocAuto;
import aca.vigilancia.spring.VigDocAutoDao;
import aca.vigilancia.spring.VigDocDao;
import aca.vigilancia.spring.VigDocumento;
import aca.vigilancia.spring.VigDocumentoDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContVigilanciaAuto {
	
	@Autowired
	private VigAutoDao vigAutoDao;

	@Autowired
	private VigDocDao vigDocDao;
	
	@Autowired
	private VigDocumentoDao vigDocumentoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	VigDocAutoDao vigDocAutoDao;
	
	
	@RequestMapping("/vigilancia/auto/listado")
	public String vigilanciaAutoListado(HttpServletRequest request, Model modelo){	
		
		String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

		List<VigAuto> lisAutos 		= vigAutoDao.lisTodos(" ORDER BY FECHA DESC, USUARIO");
		List<VigDoc> lisDocumentos 	= vigDocDao.lisTodos(" ORDER BY DOCUMENTO_ID");
		
		HashMap<String,String> mapaImagenes	 	= vigDocumentoDao.mapaConImagen();
		HashMap<String,String> mapaUsuarios 	= usuariosDao.mapaUsuariosConAuto();
		HashMap<String,String> mapaElementos	= vigDocumentoDao.mapaTotal();
		HashMap<String,String> mapaDocAutos		= vigDocAutoDao.mapaTodos();
		
		modelo.addAttribute("lisAutos", lisAutos);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		
		modelo.addAttribute("mapaImagenes", mapaImagenes);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
		modelo.addAttribute("mapaElementos", mapaElementos);
		modelo.addAttribute("mapaDocAutos", mapaDocAutos);
		
		modelo.addAttribute("mensaje", mensaje);
		
		return "vigilancia/auto/listado";
	}	

	@RequestMapping("/vigilancia/auto/editar")
	public String vigilanciaAutoEditar(HttpServletRequest request, Model modelo){	
		
		String autoId 			= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String nombreUsuario	= "-";
		
		VigAuto auto 			= new VigAuto();
		if(vigAutoDao.existeReg(autoId)) {
			auto = vigAutoDao.mapeaRegId(autoId);			
		}		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			if (auto.getUsuario().equals("0")){
				auto.setUsuario((String) sesion.getAttribute("codigoUltimo"));
			}
			nombreUsuario 		= usuariosDao.getNombreUsuario(auto.getUsuario(), "NOMBRE");
		}	
		
		modelo.addAttribute("auto", auto);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "vigilancia/auto/editar";
	}	
	
	@RequestMapping("/vigilancia/auto/usuario")
	public String vigilanciaAutoUsuario(HttpServletRequest request, Model modelo){	
		
		String autoId 			= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String nombreUsuario	= "-";
		boolean existeUsuario	= false;
		
		VigAuto auto 			= new VigAuto();
		if(vigAutoDao.existeReg(autoId)) {
			auto 	= vigAutoDao.mapeaRegId(autoId);
			if (usuariosDao.existeReg(auto.getUsuario())) existeUsuario = true;
		}		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			if (auto.getUsuario().equals("0")){
				auto.setUsuario((String) sesion.getAttribute("codigoUltimo"));
			}
			nombreUsuario 		= usuariosDao.getNombreUsuario(auto.getUsuario(), "NOMBRE");
		}	
		
		modelo.addAttribute("auto", auto);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		modelo.addAttribute("existeUsuario", existeUsuario);
		
		return "vigilancia/auto/usuario";
	}
	
	@RequestMapping("/vigilancia/auto/modificaUsuario")
	public String vigilanciaAutoModificaUsuario(HttpServletRequest request, Model modelo){	
		
		String autoId 			= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String usuario 			= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		
		VigAuto auto 			= new VigAuto();
		if(vigAutoDao.existeReg(autoId)) {
			auto 	= vigAutoDao.mapeaRegId(autoId);
			auto.setUsuario(usuario);
			vigAutoDao.updateReg(auto);
		}
		
		return "redirect:/vigilancia/auto/usuario?AutoId="+autoId;
	}
	
	@RequestMapping("/vigilancia/auto/grabar")
	public String vigilanciaAutoGrabar(HttpServletRequest request, Model modelo){	
		
		String autoId 			= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String placas 			= request.getParameter("Placas")==null?"0":request.getParameter("Placas");
		String engomado 		= request.getParameter("Engomado")==null?"0":request.getParameter("Engomado");
		String usuario 			= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		String comentario 		= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String color 			= request.getParameter("Color")==null?"0":request.getParameter("Color");
		String modeloCar 		= request.getParameter("Modelo")==null?"0":request.getParameter("Modelo");
		String marca 			= request.getParameter("Marca")==null?"0":request.getParameter("Marca");
		String poliza 			= request.getParameter("Poliza")==null?"0":request.getParameter("Poliza");
		String fecha		 	= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String estado		 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		
		String mensaje = "0";
		
		VigAuto auto = new VigAuto();
		if (autoId.equals("0")) autoId = vigAutoDao.maximoReg();
		auto.setAutoId(autoId);
		auto.setPlacas(placas);
		auto.setEngomado(engomado);
		auto.setUsuario(usuario);		
		auto.setComentario(comentario);
		auto.setColor(color);
		auto.setModelo(modeloCar);
		auto.setMarca(marca);
		auto.setPoliza(poliza);
		auto.setFecha(fecha);
		auto.setEstado(estado);
		
		if(!vigAutoDao.existeReg(autoId)){
			if(vigAutoDao.insertReg(auto)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(vigAutoDao.updateReg(auto)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/vigilancia/auto/editar?AutoId="+autoId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/vigilancia/auto/borrar")
	public String vigilanciaAutoBorrar(HttpServletRequest request){	
		
		String autoId 		= request.getParameter("AutoId")==null?"-":request.getParameter("AutoId");
		
		String mensaje = "0";		
		if(vigAutoDao.existeReg(autoId)) {
			if(vigAutoDao.deleteReg(autoId)) {
				mensaje = "¡Registro borrado!";
			}else {
				mensaje = "¡Error al borrar el registro!";
			}
		}
		return "redirect:/vigilancia/auto/listado?Mensaje="+mensaje;
	}
	
	@RequestMapping("/vigilancia/auto/editarDocumento")
	public String vigilanciaAutoEditarDocumento(HttpServletRequest request, Model modelo){	
		
		String autoId 			= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String documentoId 		= request.getParameter("DocId")==null?"0":request.getParameter("DocId");
		String hoja		 		= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String mensaje	 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		String documentoNombre = vigDocDao.getDocumentoNombre(documentoId);
		
		VigAuto vigAuto 		= new VigAuto();
		if (vigAutoDao.existeReg(autoId)) {
			vigAuto = vigAutoDao.mapeaRegId(autoId);
		}
		
		VigDocAuto vigDocAuto 	= new VigDocAuto();
		boolean existeDocumento		= false;
		if(vigDocAutoDao.existeReg(autoId,documentoId)) {
			vigDocAuto 	= vigDocAutoDao.mapeaRegId(autoId,documentoId);
			existeDocumento 	= true;
		}
		
		VigDocumento vigDocumento 	= new VigDocumento();		
		boolean existeImagen		= false;		
		if(vigDocumentoDao.existeReg(autoId,documentoId,hoja)) {
			vigDocumento 	= vigDocumentoDao.mapeaRegId(autoId,documentoId, hoja);
			existeImagen 	= true;
		}	
		
		modelo.addAttribute("autoId", autoId);
		modelo.addAttribute("documentoId", documentoId);
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("hoja", hoja);
		modelo.addAttribute("mensaje", mensaje);
		
		modelo.addAttribute("vigAuto", vigAuto);
		modelo.addAttribute("vigDocAuto", vigDocAuto);
		modelo.addAttribute("vigDocumento", vigDocumento);
		modelo.addAttribute("existeDocumento", existeDocumento);
		modelo.addAttribute("existeImagen", existeImagen);	
		
		return "vigilancia/auto/editarDocumento";
	}	
	
	@RequestMapping("/vigilancia/auto/grabarDocumento")
	public String vigilanciaAutoGrabarDocumento(HttpServletRequest request, @RequestParam("archivo") MultipartFile archivo){
		
		String autoId 		= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String documentoId 	= request.getParameter("DocId")==null?"0":request.getParameter("DocId");
		String hoja		 	= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String vigencia	 	= request.getParameter("Vigencia")==null?aca.util.Fecha.getHoy():request.getParameter("Vigencia");
		String comentario 	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		
		String mensaje 		= "0";
		
		VigDocumento doc = new VigDocumento();
		
		doc.setAutoId(autoId);
		doc.setDocumentoId(documentoId);
		doc.setHoja(hoja);
		doc.setNombre(archivo.getOriginalFilename());
		try{				
			byte[] file = archivo.getBytes();			
			doc.setArchivo(file);
		}catch( Exception e) {
			e.printStackTrace();
		}	
		
		if(!vigDocumentoDao.existeReg(autoId,documentoId,hoja)) {
			if(vigDocumentoDao.insertReg(doc)) {
				VigDocAuto vigDocAuto = new VigDocAuto();
				vigDocAuto.setAutoId(autoId);
				vigDocAuto.setDocumentoId(documentoId);
				vigDocAuto.setVigencia(vigencia);
				vigDocAuto.setComentario(comentario);
				if (vigDocAutoDao.existeReg(autoId, documentoId)==false) {					
					vigDocAutoDao.insertReg(vigDocAuto);
				}else {
					vigDocAutoDao.updateReg(vigDocAuto);
				}
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			VigDocAuto vigDocAuto = new VigDocAuto();
			vigDocAuto.setAutoId(autoId);
			vigDocAuto.setDocumentoId(documentoId);
			vigDocAuto.setVigencia(vigencia);
			vigDocAuto.setComentario(comentario);
			if (vigDocAutoDao.existeReg(autoId, documentoId)==false) {					
				vigDocAutoDao.insertReg(vigDocAuto);
			}else {
				vigDocAutoDao.updateReg(vigDocAuto);
			}
		}
		
		return "redirect:/vigilancia/auto/editarDocumento?Mensaje="+mensaje+"&AutoId="+autoId+"&DocId="+documentoId+"&Hoja="+hoja;
	}	
	
	@RequestMapping("/vigilancia/auto/imagen")
	public void vigilanciaAutoImagen(HttpServletRequest request, HttpServletResponse response){
		String autoId 		= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");
		String documentoId 	= request.getParameter("DocId")==null?"0":request.getParameter("DocId");
		String hoja		 	= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		
		VigDocumento doc = new VigDocumento();
		
        	try{			
        		// Busca la imagen
        		if(vigDocumentoDao.existeReg(autoId,documentoId,hoja)) {
        			doc = vigDocumentoDao.mapeaRegId(autoId, documentoId,hoja);
        		}
        		OutputStream out = response.getOutputStream();    		
        		out.write(doc.getArchivo());
        		out.close();
        	}catch(Exception ex){
        		System.out.println("Error /vigilancia/imagen:"+ex);
        	}
	}
	
	@RequestMapping("/vigilancia/auto/borrarDocumento")
	public String vigilanciaAutoBorrarImagen(HttpServletRequest request){	
		
		String autoId 		= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");		
		String documentoId 	= request.getParameter("DocId")==null?"0":request.getParameter("DocId");
		String hoja		 	= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		
		String mensaje = "0";	
		
		if(vigDocumentoDao.existeReg(autoId,documentoId,hoja)) {
			if(vigDocumentoDao.deleteReg(autoId,documentoId,hoja)) {
				// Si no tiene imagenes en el documento y tiene el documento registrado, entonces borra el documento  
				if (vigDocumentoDao.existeReg(autoId,documentoId)==false && vigDocAutoDao.existeReg(autoId, documentoId)){
					vigDocAutoDao.deleteReg(autoId, documentoId);
				}
				mensaje = "3";
			}else {
				mensaje = "4";
			}
		}
		return "redirect:/vigilancia/auto/editarDocumento?AutoId="+autoId+"&DocId="+documentoId+"&Mensaje="+mensaje;
	}
	
	
	@RequestMapping(value={"/vigilancia/auto/bajar"})
	public void VigilanciaAutoBajar(HttpServletRequest request, HttpServletResponse response){
		String autoId 		= request.getParameter("AutoId")==null?"0":request.getParameter("AutoId");		
		String documentoId 	= request.getParameter("DocId")==null?"0":request.getParameter("DocId");
		String hoja		 	= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		
		VigDocumento doc = new VigDocumento();		
    	try{			
    		// Busca la imagen
    		if(vigDocumentoDao.existeReg(autoId,documentoId,hoja)) {
    			doc = vigDocumentoDao.mapeaRegId(autoId, documentoId,hoja);
    		}				
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");			
			response.setHeader("Content-Disposition","attachment; filename=\""+doc.getNombre());	
			response.getOutputStream().write(doc.getArchivo());
			response.flushBuffer();			
		}catch(Exception ex){
			System.out.println("Error /VigilanciaAutoBajar:"+ex);
		}
	}

}
