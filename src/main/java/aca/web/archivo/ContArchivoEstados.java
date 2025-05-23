package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchStatus;
import aca.archivo.spring.ArchStatusDao;



@Controller
public class ContArchivoEstados {	
	
	
	@Autowired
	ArchStatusDao archStatusDao;
	
	@Autowired
	ArchDocStatusDao archDocStatusDao;	
	
	
	@RequestMapping("/archivo/estados/estados")
	public String archivoEstadosEstados(HttpServletRequest request, Model modelo){
		List<ArchStatus> lisDocumento 		= archStatusDao.getListAll("ORDER BY IDSTATUS");
		List<String> lisUsados 				= archStatusDao.getListStatusUsados("ORDER BY IDSTATUS");
		HashMap<String,String> mapaUsados 	= archDocStatusDao.mapEstadosUsados();
		
		modelo.addAttribute("lisDocumento", lisDocumento);
		modelo.addAttribute("lisUsados", lisUsados);
		modelo.addAttribute("mapaUsados", mapaUsados);
		
		return "archivo/estados/estados";
	}
	
	@RequestMapping("/archivo/estados/accion")
	public String archivoEstadosAccion(HttpServletRequest request, Model modelo){
		String idStatus 		= request.getParameter("IdStatus")==null?"0": request.getParameter("IdStatus");
		ArchStatus estado	= new ArchStatus();
		
		if (archStatusDao.existeReg(idStatus)) {
			estado = archStatusDao.mapeaRegId(idStatus);
		}
		
		modelo.addAttribute("estado", estado);
		
		return "archivo/estados/accion";
	}	
	
	@RequestMapping("/archivo/estados/grabarEstado")
	public String archivoEstadosGrabarEstado(HttpServletRequest request, Model modelo){
		
		String idStatus 		= request.getParameter("IdStatus")==null?"0": request.getParameter("IdStatus");
		String descripcion 		= request.getParameter("Descripcion")==null?"-":request.getParameter("Descripcion");
		String resultado		= "";
		ArchStatus documento	= new ArchStatus();

		documento.setDescripcion(descripcion);
		
		if (archStatusDao.existeReg(idStatus)) {
			documento.setIdStatus(idStatus);
			// Modificar
			if (archStatusDao.updateReg(documento)) {
					resultado = "<font color=blue><b>Saved: "+documento.getIdStatus()+"</b></font>";
			} else {
					resultado = "<font color=red><b>Error saving: "+documento.getIdStatus()+"</b></font>";
			}			
		} else {
			// Insertar
			documento.setIdStatus(archStatusDao.maximoReg());
			if (archStatusDao.insertReg(documento)){
				resultado = "<font color=blue><b>Saved: "+documento.getIdStatus()+"</b></font>";
			} else {
				resultado = "<font color=red><b>Error saving: "+documento.getIdStatus()+"</b></font>";
			}
		}
		
		return "redirect:/archivo/estados/accion?IdStatus="+documento.getIdStatus()+"&Resultado="+resultado;
	}
	
	@RequestMapping("/archivo/estados/borrarEstado")
	public String archivoEstadosBorrarEstado(HttpServletRequest request, Model modelo){
		
		String idStatus 		= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		String resultado		= "";	
		
		if (archStatusDao.existeReg(idStatus)) {
			if (archStatusDao.deleteReg(idStatus)) {
				resultado = "<font color=blue><b>Deleted: "+idStatus+"</b></font>";
			}else{
				resultado = "<font color=red><b>Error deleting: "+idStatus+"</b></font>";
			}			
		} else {
			resultado = "<font color=red><b>Not found: "+idStatus+"</b></font>";
		}
		
		return "redirect:/archivo/estados/accion?Resultado="+resultado;
	}
}
