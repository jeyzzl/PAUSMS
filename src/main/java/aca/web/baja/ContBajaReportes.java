package aca.web.baja;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.baja.spring.BajaAlumno;
import aca.baja.spring.BajaAlumnoDao;
import aca.baja.spring.BajaAlumpaso;
import aca.baja.spring.BajaAlumpasoDao;
import aca.baja.spring.BajaPaso;
import aca.baja.spring.BajaPasoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.internado.spring.ComAutorizacionDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;
import adm.fecha.Fecha;

@Controller
public class ContBajaReportes {
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	BajaAlumnoDao bajaAlumnoDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	
	@RequestMapping("/baja/reportes/menu")
	public String bajaReportesMenu(HttpServletRequest request, Model modelo){	
		
		return "baja/reportes/menu";
	}
	
	@RequestMapping("/baja/reportes/cargas")
	public String bajaReportesCargas(HttpServletRequest request, Model modelo){ 
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");		
		String periodoSesion 	= "0";
		String cargaSesion 		= "0";
		String codigoPersonal	= "0";
		Acceso acceso			= new Acceso();
		String facultadId 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){       	
        	periodoSesion 	= (String)sesion.getAttribute("periodo");
        	cargaSesion 	= (String)sesion.getAttribute("cargaId");
        	codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	if (accesoDao.existeReg(codigoPersonal)) {
        		acceso = accesoDao.mapeaRegId(codigoPersonal);
        	}
        } 
        
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			if(periodoSesion != null) {
				periodoId = periodoSesion;
			}else {
				periodoId = lisPeriodos.get(0).getPeriodoId();
			}
		}else if(!periodoId.equals("0")){
			sesion.setAttribute("periodo", periodoId);
		}
		
		List<Carga> lisCargas = cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY PERIODO,CARGA_ID");
		
		if (cargaId.equals("0") && lisCargas.size() > 0) {
			if(cargaSesion != null) {
				cargaId = cargaSesion;
			}else {
				cargaId = lisCargas.get(0).getCargaId();
			}
		}else if(!cargaId.equals("0")) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<AlumEstado> lisBajas = alumEstadoDao.lisPorCargayEstado(cargaId, "'3'", "ORDER BY FACULTAD_ID, CARRERA_ID, ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String, String> mapaAlumnosBajas 	= alumPersonalDao.mapaAlumnosBaja(cargaId);
		HashMap<String, CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaCreditosOrigen	= krdxCursoActDao.mapaCreditosOrigen(cargaId);
		HashMap<String, String> mapaCreditosAlta	= krdxCursoActDao.mapaCreditosAlta(cargaId);
		HashMap<String, String> mapaCreditosBaja	= krdxCursoActDao.mapaCreditosBaja(cargaId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("acceso", acceso);
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBajas", lisBajas);
		
		modelo.addAttribute("mapaAlumnosBajas", mapaAlumnosBajas);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaCreditosOrigen", mapaCreditosOrigen);
		modelo.addAttribute("mapaCreditosAlta", mapaCreditosAlta);
		modelo.addAttribute("mapaCreditosBaja", mapaCreditosBaja);
		
		return "baja/reportes/cargas";
	}
	
	@RequestMapping("/baja/reportes/bajas")
	public String bajaReportesBajas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		Acceso acceso			= new Acceso();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){        	
        	codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	if (accesoDao.existeReg(codigoPersonal)) {
        		acceso = accesoDao.mapeaRegId(codigoPersonal);
        	}
        } 	
		
		List<AlumEstado> lisBajas = alumEstadoDao.lisHistorico( "'3'", "ORDER BY FACULTAD_ID, CARRERA_ID, ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String, String> mapaAlumnosBajas 		= alumPersonalDao.mapaAlumnosBaja();
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("acceso", acceso);	
		modelo.addAttribute("lisBajas", lisBajas);
		
		modelo.addAttribute("mapaAlumnosBajas", mapaAlumnosBajas);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "baja/reportes/bajas";
	}
	
}