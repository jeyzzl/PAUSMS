package aca.web.extranjeros;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatPaisDao;
import aca.leg.spring.LegDocumento;
import aca.leg.spring.LegDocumentoDao;
import aca.leg.spring.LegEstadoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;

@Controller
public class ContExtranjerosDocExtranjero {
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	LegExtdoctosDao legExtdoctosDao;
	
	@Autowired
	LegDocumentoDao legDocumentoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	LegEstadoDao legEstadoDao;
	
	@RequestMapping("/extranjeros/docextranjero/listado")
	public String extranjerosDocExtranjeroListado(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		String estatus			= "-";
		boolean esExtranjero	= false;
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno	= (String)sesion.getAttribute("codigoAlumno");
        }
        
        if(alumPersonalDao.esInscrito(codigoAlumno)) estatus = "Inscrito";
        else estatus = "No inscrito";
        
        String nacionalidad 		= alumPersonalDao.getNacionalidad(codigoAlumno);
        String nombreNacionalidad	= catPaisDao.getNombrePais(nacionalidad);
        if (!nacionalidad.equals("91")) esExtranjero = true;
        
        String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        String carreraId		= alumPlanDao.getCarreraId(codigoAlumno);
        String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
        
        List<LegExtdoctos> lisExtdoctos 		= legExtdoctosDao.getLista(codigoAlumno, "ORDER BY 2");
        HashMap<String, String> mapaDocumentos	= legDocumentoDao.mapaDocumentos();
        HashMap<String, String> mapaEstados 	= legDocumentoDao.mapaEstados();
        
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("estatus", estatus);
		modelo.addAttribute("esExtranjero", esExtranjero);
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("lisExtdoctos", lisExtdoctos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaEstados", mapaEstados);
		
		return "extranjeros/docextranjero/listado";
	}
	
	@RequestMapping("/extranjeros/docextranjero/editar")
	public String extranjerosDocExtranjeroEditar(HttpServletRequest request, Model modelo){
		
		String idDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String codigoAlumno	= "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno	= (String)sesion.getAttribute("codigoAlumno");
        }
        
        String nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        String nombreDoc	= "";
        boolean edita = false;
        
        LegExtdoctos legExtdoctos = new LegExtdoctos();
        HashMap<String, String> mapaExtDocumento = legExtdoctosDao.mapaExtDocumento(codigoAlumno);
        
        if(!idDocumento.equals("0")) {
        	edita = true;
        	nombreDoc = legDocumentoDao.mapeaRegId(idDocumento).getDescripcion();
        }
        
        if (legExtdoctosDao.existeReg(codigoAlumno, idDocumento)){
        	legExtdoctos = legExtdoctosDao.mapeaRegId(codigoAlumno, idDocumento);
		}
        
        List<LegDocumento> lisDocumentos	= legDocumentoDao.lisAll("ORDER BY DESCRIPCION");
        //List<LegEstado> lisEstados		= legEstadoDao.lisEstados();
                
        modelo.addAttribute("codigoAlumno", codigoAlumno);
        modelo.addAttribute("nombreAlumno", nombreAlumno);
        modelo.addAttribute("legExtdoctos", legExtdoctos);
		modelo.addAttribute("lisEstados", legEstadoDao.getListAll(""));
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("mapaExtDocumento", mapaExtDocumento);
		modelo.addAttribute("edita", edita);
		modelo.addAttribute("nombreDoc", nombreDoc);
		
		return "extranjeros/docextranjero/editar";
	}
	
	@RequestMapping("/extranjeros/docextranjero/grabar")
	public String extranjerosDocExtranjeroGrabar(HttpServletRequest request, Model model){				
		
		String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String codigo			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String numDocto			= request.getParameter("Numero")==null?"-":request.getParameter("Numero");
		String estado		 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");		
		String fechaVence		= request.getParameter("FVence")==null?"0":request.getParameter("FVence");
		String fechaTramite		= request.getParameter("FTramite")==null?"0":request.getParameter("FTramite");
		String mensaje 			= "-";		
		LegExtdoctos legExtdoctos = new LegExtdoctos();
		legExtdoctos.setNumDocto(numDocto);
		legExtdoctos.setEstado(estado);
		legExtdoctos.setFechaVence(fechaVence);
		legExtdoctos.setFechaTramite(fechaTramite);
		if (legExtdoctosDao.existeReg(codigo, idDocumento)){
			legExtdoctos.setCodigo(codigo);
			legExtdoctos.setIdDocumento(idDocumento);
			legExtdoctos.setFecha(aca.util.Fecha.getHoy());			
			if (legExtdoctosDao.updateReg(legExtdoctos)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			legExtdoctos.setCodigo(codigo);
			legExtdoctos.setIdDocumento(idDocumento);
			legExtdoctos.setFecha(aca.util.Fecha.getHoy());
			if (legExtdoctosDao.insertReg(legExtdoctos)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/extranjeros/docextranjero/editar?IdDocumento="+legExtdoctos.getIdDocumento()+"&Codigo="+legExtdoctos.getCodigo()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/extranjeros/docextranjero/borrar")
	public String extranjerosDocExtranjeroBorrar(HttpServletRequest request, Model model){
		
		String codigo			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		
		if (legExtdoctosDao.existeReg(codigo, idDocumento)){
			legExtdoctosDao.deleteReg(codigo, idDocumento);
		}
		
		return "redirect:/extranjeros/docextranjero/listado";
	}

}
