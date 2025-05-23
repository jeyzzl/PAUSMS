package aca.web.convenio;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.convenio.spring.ConArchivo;
import aca.convenio.spring.ConArchivoDao;
import aca.convenio.spring.ConConvenio;
import aca.convenio.spring.ConConvenioDao;
import aca.convenio.spring.ConTipo;
import aca.convenio.spring.ConTipoDao;

@Controller
public class ContConvenioConsulta {	
	
	@Autowired
	ConConvenioDao conConvenioDao;
	
	@Autowired
	ConTipoDao conTipoDao;
	
	@Autowired
	ConArchivoDao conArchivoDao;
	
	@RequestMapping("/convenio/consulta/listado")
	public String convenioConvenioListado(HttpServletRequest request, Model modelo){
		String estado				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String tipo			    	= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String filtro 				= "X";
		
		if (!estado.equals("0")) {
			filtro = " WHERE ESTADO = '"+estado+"'";
		}		
		if (!tipo.equals("0")) {
			if (filtro.equals("X")) 
				filtro = " WHERE TIPO = '"+tipo+"'";
			else
				filtro = filtro + " AND TIPO = '"+tipo+"'";
		}
		if (filtro.equals("X")) filtro="";		
		
		List<ConConvenio> lisConvenios 		= conConvenioDao.lisTodos(filtro+" ORDER BY FECHA_VIGENCIA DESC");
		List<ConTipo> listaTipos 			= conTipoDao.lisTodos(" ORDER BY TIPO_NOMBRE");
		HashMap<String,ConArchivo> mapa 	= conArchivoDao.mapaConArchivo();
		HashMap<String,ConTipo> mapaConTipo	= conTipoDao.mapaConTipo();
		
		modelo.addAttribute("lisConvenios", lisConvenios);
		modelo.addAttribute("listaTipos", listaTipos);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaConTipo", mapaConTipo);
		
		return "convenio/consulta/listado";
	}
	
	@RequestMapping(value = { "/convenio/consulta/descargaImagen" })
	public void convenioConvenioDescargarImagen(HttpServletResponse response, HttpServletRequest request) {
		ConArchivo conArchivo 	= new ConArchivo();
		
		String idConvenio		= request.getParameter("IdConvenio")==null?"0":request.getParameter("IdConvenio");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		
		if(conArchivoDao.existeReg(idConvenio, folio)){				
			conArchivo = conArchivoDao.mapeaRegId(idConvenio, folio);	
			
			try {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition","attachment; filename=\""+conArchivo.getNombre()+ "\"");
				response.getOutputStream().write(conArchivo.getArchivo());
				response.flushBuffer();
			} catch (IOException e) {
				System.out.println("ERROR");
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/convenio/consulta/ver")
	public String convenioConvenioVer(HttpServletRequest request, Model modelo){
		
		String convenioId					= request.getParameter("ConvenioId")==null?"0":request.getParameter("ConvenioId");
	    String folio			     		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	    String convenioNombre				= conConvenioDao.mapeaRegId(convenioId).getNombre();
	    
	    ConArchivo conArchivo 	= new ConArchivo();       
        if (conArchivoDao.existeReg(convenioId, folio)){
        	conArchivo = conArchivoDao.mapeaRegId(convenioId, folio);        	
        }
        
		List<ConArchivo> lisArchivos 		= conArchivoDao.lisPorConvenio(convenioId, " ORDER BY FOLIO");
		
		modelo.addAttribute("convenioNombre", convenioNombre);
		modelo.addAttribute("conArchivo", conArchivo);
		modelo.addAttribute("lisArchivos", lisArchivos);
		
		return "convenio/consulta/ver";
	}	
}