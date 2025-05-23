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
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;
import aca.leg.spring.LegExtranjero;
import aca.leg.spring.LegExtranjeroDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;

@Controller
public class ContExtranjerosExtInscritos {
	
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
	private CatReligionDao catReligionDao;
	
	@Autowired	
	private LegExtdoctosDao legExtdoctosDao;
	
	@Autowired	
	private LegExtranjeroDao legExtranjeroDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	
	@RequestMapping("/extranjeros/ext_inscritos/ext_inscritos")
	public String extranjerosExtInscritosExtInscritos(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "";
		Acceso acceso			= new Acceso();
		boolean tieneAcceso		= false;
		String fecha			= request.getParameter("fecha")==null?aca.util.Fecha.getHoy():request.getParameter("fecha");
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String cargas			= "";
		String modalidades		= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			if (accesoDao.existeReg(codigoPersonal)){
				acceso = accesoDao.mapeaRegId(codigoPersonal);
				tieneAcceso = true;
			}
		}
		List<CatModalidad> lisModalidades 					= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		List<Carga> lisCargas 								= cargaDao.getListPorFecha(fecha, "ORDER BY CARGA_ID");
		
		for(Carga carga : lisCargas){			
			if(accion.equals("0")){
				if(cargas.equals("")) cargas = "'"+carga.getCargaId()+"'"; else cargas += ",'"+carga.getCargaId()+"'";	
			}else{
				if(request.getParameter(carga.getCargaId()) != null){
					if(cargas.equals("")) cargas = "'"+carga.getCargaId()+"'"; else cargas += ",'"+carga.getCargaId()+"'";	
				}
			}
		}
		
		for(CatModalidad catModalidad : lisModalidades){			
			if(accion.equals("0")){
				if(modalidades.equals("")) modalidades = "'"+catModalidad.getModalidadId()+"'"; else modalidades += ",'"+catModalidad.getModalidadId()+"'";	
			}else{
				if(request.getParameter("mod-"+catModalidad.getModalidadId()) != null){
					if(modalidades.equals("")) modalidades = "'"+catModalidad.getModalidadId()+"'"; else modalidades += ",'"+catModalidad.getModalidadId()+"'";	
				}
			}
		}		
		if(modalidades.equals("")) modalidades="' '";
		if(cargas.equals("")) cargas="' '";
		
		List<Estadistica> lisExtranjeros = estadisticaDao.getListaExtranjerosInscritosCargaYModalidad(cargas, modalidades, "ORDER BY FACULTAD_ID, CARRERA_ID, NOMBRE_LEGAL");	
		
		HashMap<String,CatModalidad> mapaModalidades 		= catModalidadDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 					= catPaisDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarreras 			= catCarreraDao.getMapAll("");		
		HashMap<String,Carga> mapaCargas 					= cargaDao.mapaCargas();
		HashMap<String,CatFacultad> mapaFacultades 			= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapaDocumentos				= legExtdoctosDao.mapaDocExtranjeros();
		HashMap<String,CatPais> mapaNacionalidad			= catPaisDao.getMapAll();
	 	HashMap<String,CatTipoAlumno> mapaTipos 	      	= catTipoAlumnoDao.getMapAll("");
	 	HashMap<String,AlumPlan> mapaPlanes 	 	     	= alumPlanDao.mapaPlanesEnCargas(cargas);
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);		
		modelo.addAttribute("mapaNacionalidad", mapaNacionalidad);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "extranjeros/ext_inscritos/ext_inscritos";
	}	
}