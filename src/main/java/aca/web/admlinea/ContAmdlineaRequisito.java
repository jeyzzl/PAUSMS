package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmDocumento;
import aca.admision.spring.AdmDocumentoDao;
import aca.admision.spring.AdmRequisito;
import aca.admision.spring.AdmRequisitoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAmdlineaRequisito {
	
		
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private AdmRequisitoDao admRequisitoDao;
	
	@Autowired	
	private AdmDocumentoDao admDocumentoDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;	
	
	
	@RequestMapping("/admlinea/requisito/facultad")
	public String admlineaRequisitoFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "admlinea/requisito/facultad";
	}
	
	@RequestMapping("/admlinea/requisito/carrera")
	public String admlineaRequisitoCarrera(HttpServletRequest request, Model modelo){
		
		String facultadId		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre 	= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if(!facultadId.equals("0")){
    			sesion.setAttribute("fac",facultadId);
    		}else {
    			facultadId 		= (String)sesion.getAttribute("fac");
    		}
        }
        facultadNombre 			= catFacultadDao.getNombreFacultad(facultadId);
        
        List<CatCarrera> lisCarreras 				= catCarreraDao.getLista(facultadId, "ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
        HashMap<String,String> mapaCoordinadores	= maestrosDao.mapaCoordinadores();
        HashMap<String,String> mapaPlanes			= mapaPlanDao.mapaPlanesEnCarrera("'A','V','I'");
        HashMap<String,String> mapaPlanesAdmision	= mapaPlanDao.mapaPlanesEnCarrera("'A'");
        HashMap<String,String> mapaRequisitos		= admRequisitoDao.mapaTotalPorCarrera();
        
        modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaPlanesAdmision", mapaPlanesAdmision);
		modelo.addAttribute("mapaRequisitos", mapaRequisitos);
		
		return "admlinea/requisito/carrera";
	}
	
	@RequestMapping("/admlinea/requisito/escogerCarreras")
	public String admlineaRequisitoEscogerCarreras(HttpServletRequest request, Model modelo){
		
		List<CatCarrera> lisCarreras 				= catCarreraDao.getListAll("ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		HashMap<String,String> mapaRequisitos		= admRequisitoDao.mapaTotalPorCarrera();
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaRequisitos", mapaRequisitos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "admlinea/requisito/escogerCarreras";
	}
	
	@RequestMapping("/admlinea/requisito/copiar")
	public String admlineaRequisitoCopiar(HttpServletRequest request){
		
		String carreraOrigen 	= request.getParameter("CarreraOrigen")==null?"0":request.getParameter("CarreraOrigen");
		String carreraDestino 	= request.getParameter("CarreraDestino")==null?"0":request.getParameter("CarreraDestino");
					
		List<AdmRequisito> lista = admRequisitoDao.getLista(carreraOrigen, "");
		
		for(AdmRequisito requisito : lista){
			AdmRequisito admRequisito = new AdmRequisito();
			admRequisito.setAutorizar(requisito.getAutorizar());
			admRequisito.setCarreraId(carreraDestino);
			admRequisito.setDocumentoId(requisito.getDocumentoId());
			admRequisito.setModalidades(requisito.getModalidades());				
			if(admRequisitoDao.existeReg(carreraDestino, requisito.getDocumentoId()) ){
				admRequisitoDao.updateReg(admRequisito);
			}else{
				admRequisitoDao.insertReg(admRequisito);
			}		
		}
		
		return "redirect:/admlinea/requisito/carrera";
	}
	
	@RequestMapping("/admlinea/requisito/documentos")
	public String admlineaRequisitoDocumentos(HttpServletRequest request, Model modelo){
		
		String facultadId			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String carreraId			= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
		String facultadNombre 		= catFacultadDao.getNombreFacultad(facultadId);
		String carreraNombre		= catCarreraDao.getNombreCarrera(carreraId);
		
		List<AdmDocumento> lisDocumentos 				= admDocumentoDao.getAll(" ORDER BY ORDEN");
		HashMap<String,AdmRequisito> mapaRequisitos 	= admRequisitoDao.mapaRequisitosPorCarrera(carreraId);
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("mapaRequisitos", mapaRequisitos);
		
		return "admlinea/requisito/documentos";
	}
	
	@RequestMapping("/admlinea/requisito/modalidades")
	public String admlineaRequisitoModalidades(HttpServletRequest request, Model modelo){
		
		String facultadId			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String carreraId			= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
		String documentoId			= request.getParameter("Documento")==null?"0":request.getParameter("Documento");
		String facultadNombre 		= catFacultadDao.getNombreFacultad(facultadId);
		String carreraNombre		= catCarreraDao.getNombreCarrera(carreraId);
		String documentoNombre		= admDocumentoDao.getNombreDocumento(documentoId);
		
		AdmRequisito admRequisito 	= new AdmRequisito();
		if (admRequisitoDao.existeReg(carreraId, documentoId)) {
			admRequisito = admRequisitoDao.mapeaRegId(carreraId, documentoId);
		}
		
		List<CatModalidad> lisModalidades 				= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("admRequisito", admRequisito);
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "admlinea/requisito/modalidades";
	}
	
	@RequestMapping("/admlinea/requisito/grabar")
	public String admlineaRequisitoGrabarModalidades(HttpServletRequest request, Model modelo){
		
		String facultadId			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String carreraId			= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
		String documentoId			= request.getParameter("Documento")==null?"0":request.getParameter("Documento");
		String autorizar 			= request.getParameter("Autorizar")==null?"A":request.getParameter("Autorizar");
		String requerido 			= request.getParameter("Requerido")==null?"A":request.getParameter("Requerido");
		String mensaje 				= "-";
		
		AdmRequisito admRequisito 	= new AdmRequisito();
		if (admRequisitoDao.existeReg(carreraId, documentoId)){
			admRequisito = admRequisitoDao.mapeaRegId(carreraId, documentoId);
		}
		
		List<CatModalidad> lisModalidades 				= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		String strmod = "-";
		int row=0;
		for(CatModalidad modalidad : lisModalidades){
			row++;
			String mod 	= request.getParameter("chkAgregar"+row);
			if(mod!=null){ 
				strmod 	+= modalidad.getModalidadId()+"-"; 
			}			
	   	}		
		admRequisito.setCarreraId(carreraId);
		admRequisito.setDocumentoId(documentoId);
		admRequisito.setModalidades(strmod);
		admRequisito.setAutorizar(autorizar);
		admRequisito.setRequerido(requerido);
		if(admRequisitoDao.existeReg(carreraId, documentoId)==false){
			if(admRequisitoDao.insertReg(admRequisito)){
				mensaje = "Saved";
			}
		}else{	
			if(admRequisitoDao.updateReg(admRequisito)){
				mensaje = "Saved";
			}
	  	}
		
		return "redirect:/admlinea/requisito/modalidades?Facultad="+facultadId+"&Carrera="+carreraId+"&Documento="+documentoId+"&Mensaje="+mensaje;
	}
	
}
