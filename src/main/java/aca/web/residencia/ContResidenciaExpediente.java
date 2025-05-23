package aca.web.residencia;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResDocumento;
import aca.residencia.spring.ResDocumentoDao;
import aca.residencia.spring.ResExpediente;
import aca.residencia.spring.ResExpedienteDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContResidenciaExpediente {
	
	@Autowired
	private ResExpedienteDao resExpedienteDao;

	@Autowired
	private ResDocumentoDao resDocumentoDao;

	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@RequestMapping("/residencia/expediente/listado")
	public String residenciaExpedienteListado(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		Acceso acceso 			= new Acceso();
		if (sesion!=null){		
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);
		}
		String filtroEstado			= request.getParameter("Estado")==null?"T":request.getParameter("Estado");
		String fechaIni				=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
		String fechaFin				=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		if (filtroEstado.equals("T")) filtroEstado = "'S','C','I','R'"; else filtroEstado = "'"+filtroEstado+"'";
		
		List<ResExpediente> listaExpedientes 	= resExpedienteDao.listaExpedientesPorFechas(filtroEstado,fechaIni, fechaFin," ORDER BY FECHA DESC");
		List<ResDocumento> listaDocumentos 		= resDocumentoDao.lisTodos( "ORDER BY DESCRIPCION");
		
		HashMap<String,MapaPlan> mapaPlanes 				= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, String> mapaCoordinadores 			= maestrosDao.mapaCoordinadores();
		HashMap<String, AlumPersonal> mapaAlumnoExpediente 	= alumPersonalDao.mapaAlumnoExpediente();
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("listaExpedientes", listaExpedientes);
		modelo.addAttribute("listaDocumentos", listaDocumentos);			
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaAlumnoExpediente", mapaAlumnoExpediente);
		
		return "residencia/expediente/listado";
	}
	
	@RequestMapping("/residencia/expediente/grabaComentario")
	public String residenciaExpedienteGrabaComentario(HttpServletRequest request, Model modelo){
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String comentario			= request.getParameter("Comentario") == null ? "0" : request.getParameter("Comentario");
		String mensaje 				= "";
		ResExpediente expediente = new ResExpediente();
		if(resExpedienteDao.existeReg(folio)) {
			expediente = resExpedienteDao.mapeaRegId(folio); 
			expediente.setComentario(comentario);
			if(resExpedienteDao.updateReg(expediente)) {
				mensaje = "Saved";	
			}else {
				mensaje = "Error saving";
			}
		}else {
			mensaje = "Error saving";
		}

		return "redirect:/residencia/expediente/comentario?Mensaje="+mensaje;
	}
	
	@RequestMapping("/residencia/expediente/bajarArchivo")
	public void tallerSolicitudBajarArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		String folio			  = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		ResDocumento resDocumento = new ResDocumento();
		try {						
			if(resDocumentoDao.existeReg(folio)){
				resDocumento = resDocumentoDao.mapeaRegId(folio);				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ resDocumento.getNombre() + "\"");
				out.write(resDocumento.getDocumento());				
			}
		}catch(Exception ex){
			System.out.println("Error:residency/records/downloadFile:"+ex);
		}		
	}
	
	@RequestMapping("/residencia/expediente/modificar")
	public String residenciaExpedienteModificar(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado 		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		
		if (resExpedienteDao.existeReg(folio)) {
			resExpedienteDao.updateEstado(folio, estado);
		}		
		
		return "redirect:/residencia/expediente/listado";
	}
	
	@RequestMapping("/residencia/expediente/comentario")
	public String residenciaExpedienteComentario(HttpServletRequest request, Model modelo){
		String folio			  = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		ResExpediente expediente = new ResExpediente();
		if(resExpedienteDao.existeReg(folio)) {
			expediente = resExpedienteDao.mapeaRegId(folio); 
			
		}
		
		modelo.addAttribute("comentario", expediente.getComentario());			

		
		return "residencia/expediente/comentario";
	}
	
	
}