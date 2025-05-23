package aca.web.extranjeros;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.leg.spring.LegExtdoctosDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;

@Controller
public class ContExtranjerosReportes {
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private LegExtdoctosDao legExtdoctosDao;
	
	
	
	@RequestMapping("/extranjeros/reportes/menu")
	public String extranjerosExtranjeroReportesMenu(HttpServletRequest request, Model modelo){
		
				
		String cargaId 				= request.getParameter("f_carga")==null?"0":request.getParameter("f_carga");
		String cargaNombre 			= cargaDao.getNombreCarga(cargaId);
		
		modelo.addAttribute("cargaNombre", cargaNombre);
		
		return "extranjeros/reportes/menu";
	}
	
	@RequestMapping("/extranjeros/reportes/elige_carga")
	public String extranjerosExtranjeroReportesEligeCarga(HttpServletRequest request, Model modelo){
		
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (periodoId.equals("0")){			
				periodoId = (String)sesion.getAttribute("periodo");
			}else {
				sesion.setAttribute("periodo", periodoId);
			}
			if (cargaId.equals("0")){				
				cargaId =  (String)sesion.getAttribute("cargaId");
			}else {
				sesion.setAttribute("cargaId", cargaId);
			}
		}
		
		List<CatPeriodo> lisPeriodos 		= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size()>0) periodoId = lisPeriodos.get(0).getPeriodoId();
		List<Carga> lisCargas 				= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");
		if (cargaId.equals("0") && lisCargas.size()>0) cargaId = lisCargas.get(0).getCargaId();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "extranjeros/reportes/elige_carga";
	}
	
	@RequestMapping("/extranjeros/reportes/extranjeros")
	public String extranjerosExtranjeroExtranjeros(HttpServletRequest request, Model modelo){	
		
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoPersonal 		= "0";
		String cargaNombre			= cargaDao.getNombreCarga(cargaId);
		Acceso acceso 				= new Acceso();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
			acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		}	
		
		List<CatFacultad> lisFacultades 				= catFacultadDao.lisConExtranjerosEnCarga(cargaId, " ORDER BY NOMBRE_FACULTAD");
		List<CatCarrera> lisCarreras 					= catCarreraDao.lisExtranjerosEnCarga(cargaId, " ORDER BY CARRERA_ID");
		List<Estadistica> lisExtranjeros 				= estadisticaDao.lisExtranjerosEnCarga(cargaId, " ORDER BY FACULTAD_ID, CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		HashMap<String,CatPais> mapaPaises				= catPaisDao.getMapAll();
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocumentosExtranjeros(cargaId);
		
		modelo.addAttribute("cargaNombre", cargaNombre);		
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "extranjeros/reportes/extranjeros";
	}
	
	@RequestMapping("/extranjeros/reportes/extranjerospais")
	public String extranjerosExtranjeroExtranjerosPais(HttpServletRequest request, Model modelo){	
		
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoPersonal 		= "0";
		String cargaNombre			= cargaDao.getNombreCarga(cargaId);
		Acceso acceso 				= new Acceso();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
			acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		}	
		
		List<CatPais> lisPaises 						= catPaisDao.listExtranjerosEnCarga(cargaId, " ORDER BY NOMBRE_PAIS");
		List<Estadistica> lisExtranjeros 				= estadisticaDao.lisExtranjerosEnCarga(cargaId, " ORDER BY NACIONALIDAD,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocumentosExtranjeros(cargaId);
		
		modelo.addAttribute("cargaNombre", cargaNombre);		
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "extranjeros/reportes/extranjerospais";
	}
}