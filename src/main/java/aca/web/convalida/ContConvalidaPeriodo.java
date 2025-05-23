package aca.web.convalida;


import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.conva.spring.ConvPeriodo;
import aca.conva.spring.ConvPeriodoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContConvalidaPeriodo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	ConvPeriodoDao convPeriodoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/convalida/periodo/listado")
	public String convalidaPeriodoListado(HttpServletRequest request, Model modelo){		
		
		List<ConvPeriodo> lisPeriodos	 	= convPeriodoDao.getListAll("ORDER BY PERIODO_NOMBRE");	
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		
		return "convalida/periodo/listado";
	}	
	
	@RequestMapping("/convalida/periodo/borrar")
	public String convalidaPeriodoBorrar(HttpServletRequest request, Model modelo){
		
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		if (convPeriodoDao.existeReg(periodoId) ) {
			convPeriodoDao.deleteReg(periodoId);
		}
		
		return "redirect:/convalida/periodo/listado";
	}
	
	@RequestMapping("/convalida/periodo/listaCarreras")
	public String convalidaPeriodoListaCarreras(HttpServletRequest request, Model modelo){
		
		String periodoId 						= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll(" ORDER BY NOMBRE_FACULTAD");
		List<CatCarrera> lisCarreras			= catCarreraDao.getListAll("ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes				= mapaPlanDao.getListAll("ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID),2,1");
		HashMap<String,String> mapaCarreras 	= convPeriodoDao.getMapCarreras(periodoId);	
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "convalida/periodo/listaCarreras";
	}
	
	@RequestMapping("/convalida/periodo/grabarCarreras")
	public String convalidaPeriodoGrabarCarreras(HttpServletRequest request, Model modelo){
		
		String periodoId 						= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");	
		String carreras							= "";
		List<CatCarrera> lisCarreras			= catCarreraDao.getListAll("ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		for (CatCarrera carrera : lisCarreras) {
			if (request.getParameter(carrera.getCarreraId())!=null) {
				carreras += "-"+carrera.getCarreraId();
			}
		}
		if (carreras.length()==0) {
			carreras = "-";
		}
		
		convPeriodoDao.updateCarreras(periodoId,carreras);
		
		return "redirect:/convalida/periodo/listaCarreras?PeriodoId="+periodoId;
	}
	
	@RequestMapping("/convalida/periodo/accion")
	public String convalidaPeriodoAccion(HttpServletRequest request, Model modelo){		
		
		String periodoId			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		boolean	existe 				= false;
		ConvPeriodo convPeriodo		= new ConvPeriodo();
		if (convPeriodoDao.existeReg(periodoId)) {
			convPeriodo = convPeriodoDao.mapeaRegId(periodoId);
			existe= true;
		}		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("convPeriodo", convPeriodo);
		
		return "convalida/periodo/accion";
	}	
	
	@RequestMapping("/convalida/periodo/grabar")
	public String convalidaPeriodoGrabar(HttpServletRequest request, Model modelo){		
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
		String periodoNombre	= request.getParameter("PeriodoNombre");
		String fechaIni			= request.getParameter("FechaIni")==null?"":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"":request.getParameter("FechaFin");
		String mensaje			= "-";
		
		ConvPeriodo convPeriodo		= new ConvPeriodo();
		convPeriodo.setPeriodoId(periodoId);
		convPeriodo.setPeriodoNombre(periodoNombre);
		convPeriodo.setFechaIni(fechaIni);
		convPeriodo.setFechaFin(fechaFin);
		if (convPeriodoDao.existeReg(periodoId)) {			
			if (convPeriodoDao.updateReg(convPeriodo)) {
				mensaje = "¡Modificado!";
			}else {
				mensaje = "¡Error al modificar!";
			}
		}else {
			periodoId = convPeriodoDao.maxReg();
			convPeriodo.setPeriodoId(periodoId);
			if (convPeriodoDao.insertReg(convPeriodo)) {
				mensaje = "¡Grabado!";
			}else {
				mensaje = "¡Error al insertar!";
			}
		}
		
		return "redirect:/convalida/periodo/accion?PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	
}