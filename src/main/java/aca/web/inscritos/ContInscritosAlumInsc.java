package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.FesCcMateriaDao;
import aca.financiero.spring.SaldosAlumnosDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.parametros.spring.UsuarioParametros;
import aca.parametros.spring.UsuarioParametrosDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;


@Controller
public class ContInscritosAlumInsc {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private FesCcMateriaDao fesCcMateriaDao;
	
	@Autowired
	private UsuarioParametrosDao usuarioParametrosDao;
	
	@Autowired
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	public void enviarConArchivo(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conArchivo", archivo.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/inscritos/alum_insc/estadistica")
	public String inscritosAlumInscEstadistica(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String cargas 			= request.getParameter("Cargas")==null?"''":request.getParameter("Cargas");
		String modalidades 		= request.getParameter("Modalidades")==null?"''":request.getParameter("Modalidades");
		String codigoNacional 	= "0";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");	
        	if(cargas.equals("''")) {
        		cargas = (String) sesion.getAttribute("cargas");
        	}
        	if(modalidades.equals("''")) {
        		modalidades = (String) sesion.getAttribute("modalidades");
        	}
        }

		String fechaIni			= request.getParameter("FechaIni")==null?(String) sesion.getAttribute("fechaIni"):request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?(String) sesion.getAttribute("fechaFin"):request.getParameter("FechaFin");
		
		UsuarioParametros usuPara = new UsuarioParametros();
		
		modalidades = modalidades.replace("'", "");
		cargas 		= cargas.replace("'", "");
		
		String lisModo[] 	= {""};
		String lisCarga[] 	= {""};
		
		Parametros parametros = new Parametros();
		
		if (parametrosDao.existeReg("1")) {
			parametros = parametrosDao.mapeaRegId("1");
		}
		
		codigoNacional = parametros.getPaisId();
		
		HashMap<String, List<CatFacultad>> mapaListaFacultad 				= new HashMap<String, List<CatFacultad>>();
		HashMap<String, List<CatCarrera>> mapaListaCarrera 					= new HashMap<String, List<CatCarrera>>();
		HashMap<String, HashMap<String, String>> mapaMapaCreditos			= new HashMap<String, HashMap<String, String>>();
		HashMap<String, List<Estadistica>> mapaLisEstadistica				= new HashMap<String, List<Estadistica>>();
		HashMap<String, List<Estadistica>> mapaLisRepetidos					= new HashMap<String, List<Estadistica>>();
		HashMap<String,HashMap<String, String>> mapaMapIngresoFacUm			= new HashMap<String,HashMap<String, String>>();
		HashMap<String,HashMap<String, String>> mapaMapIngresoFacPlan		= new HashMap<String,HashMap<String, String>>();
		HashMap<String,HashMap<String, String>> mapaMapIngresoCarreraUm		= new HashMap<String,HashMap<String, String>>();
		HashMap<String,HashMap<String, String>> mapaMapIngresoCarreraPlan	= new HashMap<String,HashMap<String, String>>();
		
		if(!usuarioParametrosDao.existeReg(codigoPersonal)) {
			usuPara.setCodigoPersonal(codigoPersonal);
			usuPara.setCargas(cargas);
			usuPara.setModalidades(modalidades);
			usuPara.setfInicio(fechaIni);
			usuPara.setfFinal(fechaFin);
			usuarioParametrosDao.insertReg(usuPara);

		}else if(usuarioParametrosDao.existeReg(codigoPersonal)) {
			usuPara 	= usuarioParametrosDao.mapeaRegId(codigoPersonal);
			usuPara.setCodigoPersonal(codigoPersonal);
			
			if(request.getParameter("Cargas")!=null) {
				usuPara.setCargas(cargas);
				lisCarga = usuPara.getCargas().split(",");
			}else {
				if(usuPara.getCargas().contains(",")) {
					lisCarga = usuPara.getCargas().split(",");
				}else {
					lisCarga[0] = usuPara.getCargas();
				}
			}
			if(request.getParameter("Modalidades")!=null) {
				usuPara.setModalidades(modalidades);
				lisModo 	= usuPara.getModalidades().split(",");
			}else {
				if(usuPara.getModalidades().contains(",")) {
					lisModo 	= usuPara.getModalidades().split(",");
				}else {
					lisModo[0] 	= usuPara.getModalidades();
				}
			}
			if(request.getParameter("FechaIni")!=null) {
				usuPara.setfInicio(fechaIni);
			}else {
				String arrayIni[] = usuPara.getfInicio().split(" ");
				String ini[] = arrayIni[0].split("-");
				fechaIni = ini[2]+"/"+ini[1]+"/"+ini[0];
				usuPara.setfInicio(fechaIni);
			}
			if(request.getParameter("FechaFin")!=null) {
				usuPara.setfFinal(fechaFin);
			}else {
				String arrayFin[] = usuPara.getfFinal().split(" ");
				String fin[] = arrayFin[0].split("-");
				fechaFin = fin[2]+"/"+fin[1]+"/"+fin[0];
				usuPara.setfFinal(fechaFin);
			}

			usuarioParametrosDao.updateReg(usuPara);
			cargas 		= usuPara.getCargas();
			modalidades = usuPara.getModalidades();
			fechaIni 	= usuPara.getfInicio();
			fechaFin 	= usuPara.getfFinal();
		}

		if(!lisCarga[0].equals("")) {			
			for(String carga : lisCarga){
				List<CatFacultad> lisFacultad = catFacultadDao.getListCarga(carga,"ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID)");
				mapaListaFacultad.put(carga, lisFacultad); 
				
				List<CatCarrera> lisCarrera = catCarreraDao.getListCarga(carga, "ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
				mapaListaCarrera.put(carga, lisCarrera);
				
				HashMap<String, String> mapaCreditos = fesCcMateriaDao.mapCreditosCargaCero(carga);
				mapaMapaCreditos.put(carga, mapaCreditos);				
				List<Estadistica> lisEstadistica	= estadisticaDao.listInscritosCargasModalidadesFechas("'"+carga+"'", modalidades, fechaIni, fechaFin, "");				
				mapaLisEstadistica.put(carga, lisEstadistica);
				
				mapaMapIngresoFacUm.put(carga, estadisticaDao.mapIngresoFac(carga, modalidades, fechaIni, fechaFin, "I", "UM"));
				mapaMapIngresoFacPlan.put(carga, estadisticaDao.mapIngresoFac(carga, modalidades, fechaIni, fechaFin, "I", "PLAN"));
				
				mapaMapIngresoCarreraUm.put(carga, estadisticaDao.mapIngresoCarrera(carga, modalidades, fechaIni, fechaFin, "I", "UM"));
				mapaMapIngresoCarreraPlan.put(carga, estadisticaDao.mapIngresoCarrera(carga, modalidades, fechaIni, fechaFin, "I", "PLAN"));
				
				List<Estadistica> lisRepetidos		= estadisticaDao.getListRepetidos("'"+carga+"'", modalidades, fechaIni, fechaFin, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE, CARGA_ID");	
				mapaLisRepetidos.put(carga, lisRepetidos);
				
			}
		}else {			
			List<CatFacultad> lisFacultad = catFacultadDao.getListCarga(cargas,"ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID)");
			mapaListaFacultad.put(cargas, lisFacultad);
			
			List<CatCarrera> lisCarrera = catCarreraDao.getListCarga(cargas, "ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
			mapaListaCarrera.put(cargas, lisCarrera);
			
			HashMap<String, String> mapaCreditos = fesCcMateriaDao.mapCreditosCargaCero(cargas);
			mapaMapaCreditos.put(cargas, mapaCreditos);

			if(fechaIni.contains(" ")) {
				String arrayIni[] = fechaIni.split(" ");
				String ini[] = arrayIni[0].split("-");
				fechaIni = ini[2]+"/"+ini[1]+"/"+ini[0];
			}
			if(fechaFin.contains(" ")) {
				String arrayFin[] = fechaFin.split(" ");
				String fin[] = arrayFin[0].split("-");
				fechaFin = fin[2]+"/"+fin[1]+"/"+fin[0];
			}
			
			List<Estadistica> lisEstadistica	= estadisticaDao.listInscritosCargasModalidadesFechas("'"+cargas+"'", modalidades, fechaIni, fechaFin, "");
			mapaLisEstadistica.put(cargas, lisEstadistica);

			mapaMapIngresoFacUm.put(cargas, estadisticaDao.mapIngresoFac(cargas, modalidades, fechaIni, fechaFin, "I", "UM"));
			mapaMapIngresoFacPlan.put(cargas, estadisticaDao.mapIngresoFac(cargas, modalidades, fechaIni, fechaFin, "I", "PLAN"));
			
			mapaMapIngresoCarreraUm.put(cargas, estadisticaDao.mapIngresoCarrera(cargas, modalidades, fechaIni, fechaFin, "I", "UM"));
			mapaMapIngresoCarreraPlan.put(cargas, estadisticaDao.mapIngresoCarrera(cargas, modalidades, fechaIni, fechaFin, "I", "PLAN"));
			
			List<Estadistica> lisRepetidos		= estadisticaDao.getListRepetidos("'"+cargas+"'", modalidades, fechaIni, fechaFin, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE, CARGA_ID");	
			mapaLisRepetidos.put(cargas, lisRepetidos);
		}

		HashMap<String,String> mapaModalidad 		= catModalidadDao.mapModalidades("");
		HashMap<String, Carga> mapaCargas 			= cargaDao.mapaCargas();
		HashMap<String,String> mapaNombreCarrera	= catCarreraDao.getMapNombre();
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("codigoNacional", codigoNacional);
		modelo.addAttribute("lisModo", lisModo);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("mapaModalidad", mapaModalidad);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaListaFacultad", mapaListaFacultad);
		modelo.addAttribute("mapaListaCarrera", mapaListaCarrera);
		modelo.addAttribute("mapaMapaCreditos", mapaMapaCreditos);
		modelo.addAttribute("mapaNombreCarrera", mapaNombreCarrera);
		modelo.addAttribute("mapaLisEstadistica", mapaLisEstadistica);
		modelo.addAttribute("mapaMapIngresoFacUm", mapaMapIngresoFacUm);
		modelo.addAttribute("mapaMapIngresoFacPlan", mapaMapIngresoFacPlan);
		modelo.addAttribute("mapaMapIngresoCarreraUm", mapaMapIngresoCarreraUm);
		modelo.addAttribute("mapaMapIngresoCarreraPlan", mapaMapIngresoCarreraPlan);
		modelo.addAttribute("mapaLisRepetidos", mapaLisRepetidos);
		
		return "inscritos/alum_insc/estadistica";
	}

	@RequestMapping("/inscritos/alum_insc/cargas")
	public String inscritosAlumInscCargas(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }
		
		String fInscripcion						= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		List<Carga> lisCarga					= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas					= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
		HashMap<String,String> mapaPrimerDia 	= cargaDao.mapaPrimerDia();
		HashMap<String,String> mapaUltimoDia 	= cargaDao.mapaPrimerDia();
		
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisActivas", lisActivas);
		modelo.addAttribute("mapaPrimerDia", mapaPrimerDia);
		modelo.addAttribute("mapaUltimoDia", mapaUltimoDia);
		
		return "inscritos/alum_insc/cargas";
	}
	@RequestMapping("/inscritos/alum_insc/modalidades")
	public String inscritosAlumInscModalidades(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }	
		
	    List<CatModalidad> lisModalidad	= catModalidadDao.getListAll( "ORDER BY MODALIDAD_ID");
	    HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas); 
	    
	    modelo.addAttribute("lisModalidad", lisModalidad);
	    modelo.addAttribute("mapa", mapa);
	    
		return "inscritos/alum_insc/modalidades";
	}
}
