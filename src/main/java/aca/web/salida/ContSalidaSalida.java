package aca.web.salida;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.salida.spring.SalAlumnoDao;
import aca.salida.spring.SalGrupo;
import aca.salida.spring.SalGrupoDao;
import aca.salida.spring.SalInforme;
import aca.salida.spring.SalInformeDao;
import aca.salida.spring.SalSolicitud;
import aca.salida.spring.SalSolicitudDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContSalidaSalida {
			
	@Autowired
	SalGrupoDao salGrupoDao;
	
	@Autowired
	SalSolicitudDao salSolicitudDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	SalInformeDao salInformeDao;
	
	@Autowired
	SalAlumnoDao salAlumnoDao;
	
	@RequestMapping("/salida/salidas/listado")
	public String salidaSalidaListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";		
		String periodo 			= "0";
		String grupoId 			= request.getParameter("Grupo") == null ? "0" : request.getParameter("Grupo");
		String fechaIni			= request.getParameter("FechaIni") == null ? "01/01/"+aca.util.Fecha.getHoy().substring(6,10) : request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin") == null ? "31/12/"+aca.util.Fecha.getHoy().substring(6,10) : request.getParameter("FechaFin");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
			periodo 			= (String)sesion.getAttribute("periodo");
		}
		
		List<SalGrupo> listGrupos 		= salGrupoDao.getListAll(" ORDER BY 1");
		List<SalSolicitud> lisSolicitud = salSolicitudDao.lisPorGrupoyFecha(grupoId, fechaIni, fechaFin, "ORDER BY ENOC.SAL_SOLICITUD.FECHA_SALIDA DESC, SALIDA_ID");
		
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapaGrupoNombre 		= salSolicitudDao.mapNombreGrupo();
		HashMap<String,SalInforme> mapaInformes 	= salInformeDao.mapaInformes();
		HashMap<String,String> mapaAlumnos 			= salAlumnoDao.mapaAlumnosPorDormitorio();

		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("grupoId", grupoId);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("listGrupos", listGrupos);
		modelo.addAttribute("lisSolicitud", lisSolicitud);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		modelo.addAttribute("mapaGrupoNombre",mapaGrupoNombre);
		modelo.addAttribute("mapaInformes",mapaInformes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);

		return "salida/salidas/listado";
	}
	
	@RequestMapping("/salida/salidas/verInforme")
	public String salidaSalidasVerInforme(HttpServletRequest request, Model modelo){		
		
		String salidaId  				= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		SalInforme salInforme 			= new SalInforme();
		boolean existe					= false;
		
		if (salInformeDao.existeReg(salidaId)) {
			salInforme 	= salInformeDao.mapeaRegId(salidaId);
			existe 		= true;
		}
		
		modelo.addAttribute("salidaId", salidaId);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("salInforme", salInforme);
		
		return "salida/salidas/verInforme";
	}
	
}