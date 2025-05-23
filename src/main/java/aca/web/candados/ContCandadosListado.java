package aca.web.candados;

import java.util.ArrayList;
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
import aca.archivo.spring.ArchivoDao;
import aca.candado.spring.CandAlumnoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtranjeroDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContCandadosListado {	
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private ArchivoDao archivoDao;
	
	@Autowired
	private LegExtranjeroDao legExtranjeroDao;
	
	@Autowired
	private CandAlumnoDao candAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/candados/listado/listado")
	public String candadosListadoListado(HttpServletRequest request, Model modelo){
		//long inicio = System.currentTimeMillis();
		String codigoPersonal	= "0";
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String mostrar			= request.getParameter("Mostrar")==null?"0":request.getParameter("Mostrar");
		Acceso acceso			= new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
			if (accesoDao.existeReg(codigoPersonal)) {
				acceso 			= accesoDao.mapeaRegId(codigoPersonal);
			}			 
		}	
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && lisPeriodos.size() > 0 ) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}		
		
		List<Carga> lisCargas = cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		if (cargaId.equals("0") && lisCargas.size()>0) {
			cargaId = lisCargas.get(0).getCargaId();
    	}	
		
		// Lista de carreras
		List<CatCarrera> lisCarreras		= new ArrayList<CatCarrera>();
		if (mostrar.equals("1")) {
			if(acceso.getAdministrador().equals("S")||acceso.getSupervisor().equals("S")){
				lisCarreras	= catCarreraDao.getListaEnEstadistica(cargaId," ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
			}else{
				lisCarreras	= catCarreraDao.getListAutorizadasyEstadistica(codigoPersonal,cargaId," ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
			}
		}	
		
		List<Estadistica> lisAlumnos 				= estadisticaDao.lisInscritosEnCarga(cargaId, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE, PLAN_ID");
		HashMap<String,String> mapaArchivos			= new HashMap<String,String>();
		//HashMap<String,String> mapaExtranjeros		= new HashMap<String,String>();
		
		String matricula	= "";
		if (mostrar.equals("1")) {
			for(Estadistica estadistica :lisAlumnos){			
				if(!matricula.equals(estadistica.getCodigoPersonal())){
					matricula = estadistica.getCodigoPersonal();
					//Candado Archivo
					String validaArchivo = archivoDao.getAutorizado(matricula, estadistica.getPlanId());
					if(validaArchivo.equals("P")) {
						mapaArchivos.put(matricula, "P"); 
					} else if (validaArchivo.equals("N")) {
						mapaArchivos.put(matricula, "S"); 
					} else {
						mapaArchivos.put(matricula, "N");				
					}					
				}
			}
		}	
		
		//System.out.println("Paso 6"+(System.currentTimeMillis()-inicio));
		HashMap<String,CatFacultad> mapaFacultades			= catFacultadDao.getMapFacultad("");		
		HashMap<String,String> mapaCandados					= candAlumnoDao.mapaAlumnosConCandado(cargaId);
		HashMap<String,String> mapTipoAlumno				= catTipoAlumnoDao.getMapNombreTipo();
		HashMap<String,String> mapModalidades				= catModalidadDao.mapModalidades("ORDER BY MODALIDAD_ID");
		HashMap<String,AlumPersonal> mapCorreoAlumno		= alumPersonalDao.mapAlumnosEnCargas(cargaId);
		
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("lisPeriodos",lisPeriodos);
		modelo.addAttribute("lisCargas",lisCargas);
		modelo.addAttribute("lisCarreras",lisCarreras);
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaArchivos",mapaArchivos);
		modelo.addAttribute("mapaCandados",mapaCandados);
		modelo.addAttribute("mapModalidades",mapModalidades);
		modelo.addAttribute("mapTipoAlumno",mapTipoAlumno);
		modelo.addAttribute("mapCorreoAlumno",mapCorreoAlumno);
		
		return "candados/listado/listado";
	}
}