package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchGrupos;
import aca.archivo.spring.ArchGruposCarrera;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.archivo.spring.ArchGruposDao;
import aca.archivo.spring.ArchStatus;
import aca.archivo.spring.ArchStatusDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;


@Controller
public class ContArchivoCondiciones {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private ArchGruposDao archGruposDao;
	
	@Autowired	
	private ArchDocumentosDao archDocumentosDao;
	
	@Autowired	
	private ArchStatusDao archStatusDao;
	
	@Autowired	
	private ArchGruposCarreraDao archGruposCarreraDao;
	
	
	@RequestMapping("/archivo/condiciones/editar")
	public String archivoCondicionesAddGpoCarr(HttpServletRequest request, Model modelo){
		String documentoId 	= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String grupo		= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		
		List<ArchDocumentos> lisDocumentos 	= archDocumentosDao.getListAll(" ORDER BY DESCRIPCION");		
		List<ArchStatus> lisStatus	 		= archStatusDao.getListNow(documentoId, "ORDER BY DESCRIPCION");		
		List<ArchGrupos> lisGrupos 			= archGruposDao.getListGrupo(grupo, " ORDER BY GRUPO, NOMBRE_DOCUMENTO(IDDOCUMENTO)");
		
		HashMap<String,String> mapaDocumentos 	= archDocumentosDao.mapaTodos();
		HashMap<String,String> mapaStatus 		= archStatusDao.mapaStatus();
		
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisStatus", lisStatus);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/condiciones/editar";
	}
	
	@RequestMapping("/archivo/condiciones/grabar")
	public String archivoCondicionesGrabar(HttpServletRequest request){
		String grupo			= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String statusId 		= request.getParameter("StatusId")==null?"0":request.getParameter("StatusId");
		ArchGrupos archGrupos 	= new ArchGrupos();		
		archGrupos.setGrupo(grupo);
		archGrupos.setIdDocumento(documentoId);
		archGrupos.setIdStatus(statusId);
		if (archGruposDao.existeRegSinStatus(grupo, documentoId)==false){
			archGruposDao.insertReg(archGrupos);
		}
		
		return "redirect:/archivo/condiciones/editar?Grupo="+grupo;
	}
	
	@RequestMapping("/archivo/condiciones/borrar")
	public String archivoCondicionesBorrar(HttpServletRequest request){
		String grupo 			= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");		
		if (archGruposDao.existeRegSinStatus(grupo, documentoId)){
			archGruposDao.deleteReg(grupo, documentoId);
		}		
		
		return "redirect:/archivo/condiciones/editar?Grupo="+grupo;
	}
	
	@RequestMapping("/archivo/condiciones/aplicar_condicion")
	public String archivoCondicionesAplicarCondicion(HttpServletRequest request, Model modelo){
		String grupo 			= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");		
		
		List<CatCarrera> lisAsignadas 	= catCarreraDao.listCarrerasConGrupo(grupo," ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		List<CatCarrera> lisFaltan		= catCarreraDao.listCarrerasSinGrupo(grupo," ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		
		modelo.addAttribute("lisAsignadas", lisAsignadas);
		modelo.addAttribute("lisFaltan", lisFaltan);		
		
		return "archivo/condiciones/aplicar_condicion";
	}
	
	@RequestMapping("/archivo/condiciones/quitarCarrera")
	public String archivoCondicionesQuitarCarrera(HttpServletRequest request){
		String grupo 			= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String gruposCarrera	= "*";		
		if (archGruposCarreraDao.existeReg(carreraId)){
			gruposCarrera = archGruposCarreraDao.mapeaRegId(carreraId).getGrupos();
			if (gruposCarrera==null) gruposCarrera = "*";
			gruposCarrera = gruposCarrera.replace(" ", "-");
			gruposCarrera = gruposCarrera.replace("-"+grupo+"-", "");
			archGruposCarreraDao.updateGrupos( carreraId, gruposCarrera);			
		}
		
		return "redirect:/archivo/condiciones/aplicar_condicion?Grupo="+grupo;
	}
	
	@RequestMapping("/archivo/condiciones/grabarCarreras")
	public String archivoCondicionesGrabarCarreras(HttpServletRequest request){
		String grupo 		= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListAll("");
		for (CatCarrera carrera : lisCarreras) {
			String existeCarrera = request.getParameter(carrera.getCarreraId())==null?"N":request.getParameter(carrera.getCarreraId());
			String grupos = "";
			boolean existeGrupo = false;
			if (existeCarrera.equals("S")) {
				ArchGruposCarrera condicion = new ArchGruposCarrera();
				condicion.setCarrera(carrera.getCarreraId());
				if (archGruposCarreraDao.existeReg(carrera.getCarreraId())) {
					existeGrupo = true;
					grupos = archGruposCarreraDao.getGruposCarrera(carrera.getCarreraId());					
				}				
				grupos = grupos +" "+grupo+" ";
				condicion.setGrupos(grupos);
				if (existeGrupo) {
					archGruposCarreraDao.updateReg(condicion);
				}else {
					archGruposCarreraDao.insertReg(condicion);
				}
			}
		}
		return "redirect://archivo/condiciones/aplicar_condicion?Grupo="+grupo;
	}	
	
	@RequestMapping("/archivo/condiciones/grupos")
	public String archivoCondicionesGrupos(HttpServletRequest request, Model modelo){
		
		String maximo = archGruposDao.maxReg();
		
		List<ArchGrupos> lisGrupos 				= archGruposDao.getListAll(" ORDER BY GRUPO, ENOC.NOMBRE_DOCUMENTO(IDDOCUMENTO)");
		HashMap<String,String> mapaDocumentos 	= archDocumentosDao.mapaTodos();
		HashMap<String,String> mapaStatus 		= archStatusDao.mapaStatus();
		
		modelo.addAttribute("maximo",maximo);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/condiciones/grupos";
	}	
	
}