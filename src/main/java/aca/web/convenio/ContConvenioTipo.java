package aca.web.convenio;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.convenio.spring.ConArchivo;
import aca.convenio.spring.ConArchivoDao;
import aca.convenio.spring.ConConvenioDao;
import aca.convenio.spring.ConTipo;
import aca.convenio.spring.ConTipoDao;

@Controller
public class ContConvenioTipo {	
	
	@Autowired
	ConTipoDao conTipoDao;
	
	@Autowired
	ConConvenioDao conConvenioDao;
	
	@RequestMapping("/convenio/tipos/listado")
	public String convenioConvenioListado(HttpServletRequest request, Model modelo){
		List<ConTipo> lista 		= conTipoDao.lisTodos(" ORDER BY TIPO_NOMBRE");		
		
		HashMap<String,String> mapaConvenioConTipo = conConvenioDao.mapaConvenioConTipo();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaConvenioConTipo", mapaConvenioConTipo);
		
		return "convenio/tipos/listado";
	}
	
	@RequestMapping("/convenio/tipos/editar")
	public String convenioConvenioEditar(HttpServletRequest request, Model modelo){		
		String tipoId 		= (String)request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		ConTipo convenio 	= new ConTipo();
		
		if (conTipoDao.existeReg(tipoId)) {
			convenio = conTipoDao.mapeaRegId(tipoId);				
		}
	    
	    modelo.addAttribute("convenio", convenio);
	    
	    return "convenio/tipos/editar";
	}
	
	@RequestMapping("/convenio/tipos/grabar")
	public String convenioConvenioGrabar(HttpServletRequest request, Model modelo){
			
		String tipoId	= (String)request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String nombre	= (String)request.getParameter("TipoNombre")==null?"-":request.getParameter("TipoNombre");
		String mensaje	= "-";		
		
		ConTipo convenio = new ConTipo();
		convenio.setTipoNombre(nombre);
		
		if (conTipoDao.existeReg(tipoId)) {			
			convenio.setTipoId(tipoId);
			if (conTipoDao.updateReg(convenio)) {
				mensaje = "Modificado";
			}else {
				mensaje = "Error al modificar...";
			}
		}else {
			tipoId = conTipoDao.maximoReg();
			convenio.setTipoId(tipoId);			
			if (conTipoDao.insertReg(convenio)) {
				mensaje = "Grabado";
			}else {
				mensaje = "Error al grabar...";
			}			
		}
		
		return "redirect:/convenio/tipos/editar?TipoId="+tipoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/convenio/tipos/borrar")
	public String convenioConvenioBorrar(HttpServletRequest request){		
		String tipoId 	= (String)request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		if (conTipoDao.existeReg(tipoId)) {
			conTipoDao.deleteReg(tipoId);				
		}
		
	    return "redirect:/convenio/tipos/listado";
	}
	
}