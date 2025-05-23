package aca.web.servicio;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocDocumentos;
import aca.ssoc.spring.SsocInicio;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocRequisito;
import aca.ssoc.spring.SsocSector;
import aca.ssoc.spring.SsocSectorDao;

@Controller
public class ContServicioSector {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SsocInicioDao ssocInicioDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	

	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/servicio/sector/sector")
	public String servicioSectorSector(HttpServletRequest request, Model modelo){	
		
		List<SsocSector> lisSector = ssocSectorDao.getListAll();
		HashMap<String,String> mapaNumSector = ssocSectorDao.mapaNumSector();
		
		modelo.addAttribute("lisSector",lisSector);		
		modelo.addAttribute("mapaNumSector",mapaNumSector);
		
		return "servicio/sector/sector";
	}
	
	@RequestMapping("/servicio/sector/accion")
	public String servicioSectorAccion(HttpServletRequest request, Model model){
		
		String sectorId 			= request.getParameter("sectorId")==null?"0":request.getParameter("sectorId");
		SsocSector sector			= new SsocSector();
		
		if(ssocSectorDao.existeReg(sectorId)) {
			sector = ssocSectorDao.mapeaRegId(sectorId);
		}
		
		model.addAttribute("sector", sector);
		
		return "servicio/sector/accion";
	}
	
	@RequestMapping("/servicio/sector/grabar")
	public String servicioSectorGrabar(HttpServletRequest request, Model model){				
		
		String sectorId 			= request.getParameter("SectorId")==null?"0":request.getParameter("SectorId");
		String sectorNombre			= request.getParameter("SectorNombre")==null?"-":request.getParameter("SectorNombre");
		String mensaje 				= "-";
		
		SsocSector sector	= new SsocSector();
		sector.setSectorNombre(sectorNombre);
		
		if (ssocSectorDao.existeReg(sectorId)){
			sector.setSectorId(sectorId);
			if (ssocSectorDao.updateReg(sector)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			sector.setSectorId(ssocSectorDao.maximoReg(sectorId));
			if (ssocSectorDao.insertReg(sector)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/servicio/sector/accion?SectorId="+sector.getSectorId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/servicio/sector/accionB")
	public String servicioSectorAccionB(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContServicioSector|servicioSectorAccionB:");
		return "servicio/sector/accionB";
	}	
	
	@RequestMapping("/servicio/sector/accionC")
	public String servicioSectorAccionC(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContServicioSector|servicioSectorAccionC:");
		return "servicio/sector/accionC";
	}
	
	@RequestMapping("/servicio/sector/institucion")
	public String servicioSectorInstitucion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContServicioSector|servicioSectorInstitucion:");
		return "servicio/sector/institucion";
	}
	
	@RequestMapping("/servicio/sector/plaza")
	public String servicioSectorPlaza(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContServicioSector|servicioSectorPlaza:");
		return "servicio/sector/plaza";
	}
	
//	@RequestMapping("/servicio/sector/borrar")
//	public String servicioSectorBorrar(HttpServletRequest request, Model model){
//		
//		String documentoId	= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
//		if (ssocDocumentosDao.existeReg(documentoId)) {
//			ssocDocumentosDao.eliminaDocumento(documentoId);
//		}
//		
//		return "redirect:/servicio/sector/sector";
//	}
}