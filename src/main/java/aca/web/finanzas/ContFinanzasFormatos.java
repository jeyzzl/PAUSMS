package aca.web.finanzas;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumDocumento;
import aca.alumno.spring.AlumDocumentoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContFinanzasFormatos {
	
	@Autowired
	private AlumDocumentoDao alumDocumentoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/finanzas/formatos/seguros")
	public String finanzasFormatosSeguros(HttpServletRequest request, Model modelo){
		String fechaIni			=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
		String fechaFin			=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		String tipos  			= request.getParameter("Tipos") == null ? "'2','3','4'" : request.getParameter("Tipos"); 
		
		if(tipos.equals("0")) {
			tipos = "'2','3','4'";
		}
		
		String codigo = "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigo 	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		Acceso acceso = accesoDao.mapeaRegId(codigo);
		
		List<AlumDocumento> lisSolicitudesSeguro 				= alumDocumentoDao.lisSolicitudesSeguro(fechaIni, fechaFin, tipos);
		HashMap<String, AlumPersonal> mapaAlumnosEnSolicitud 	= alumPersonalDao.mapaAlumnosEnSolicitud();
		HashMap<String, MapaPlan> mapaPlanes 					= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisSolicitudesSeguro", lisSolicitudesSeguro);
		modelo.addAttribute("mapaAlumnosEnSolicitud", mapaAlumnosEnSolicitud);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("tipos", tipos);
		
		return "finanzas/formatos/seguros";
	}
	
	@RequestMapping("/finanzas/formatos/bajarArchivo")
	public void finanzasFormatosBajarArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal"); 
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		AlumDocumento alumDocumento = new AlumDocumento();
		
		try {						
			if(alumDocumentoDao.existeReg(codigoPersonal, folio)){
				alumDocumento = alumDocumentoDao.mapeaRegId(codigoPersonal, folio);			
				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ alumDocumento.getNombre() + "\"");
				out.write(alumDocumento.getArchivo());
				
			}
		}catch(Exception ex){
			System.out.println("Error:finanzas/formatos/bajarArchivo:"+ex);
		}
	}
	
	@RequestMapping("/finanzas/formatos/cambiarEstado")
	public String finanzasFormatosCambiarEstado(HttpServletRequest request, Model modelo){

		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal"); 
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		
		if(alumDocumentoDao.existeReg(codigoPersonal, folio)){
			alumDocumentoDao.updateEstado(codigoPersonal, folio, estado);
		}		
		
		return "redirect:/finanzas/formatos/seguros";
	}
	
}