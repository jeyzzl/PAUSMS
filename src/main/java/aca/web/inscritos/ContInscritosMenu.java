package aca.web.inscritos;

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
import org.springframework.web.client.RestTemplate;

import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.FinSaldo;
import aca.inscrito.spring.InsAlumno;
import aca.inscrito.spring.InsAlumnoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumUbicacion;

@Controller
public class ContInscritosMenu{
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private FesCcobroDao fesCcobroDao;
	
	@Autowired
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	InsAlumnoDao insAlumnoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/inscritos/menu/menu")
	public String inscritosMenuMenu(HttpServletRequest request, Model modelo){
		
		String periodoId 		= catPeriodoDao.getPeriodo();
		String cargas			= "'0'";
		String modalidades		= "'1'";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();	
		if (sesion!=null){
			cargas				= sesion.getAttribute("cargas")==null?cargaDao.getCargasActivas(aca.util.Fecha.getHoy()):sesion.getAttribute("cargas").toString();
			modalidades			= sesion.getAttribute("modalidades")==null?"'1'":sesion.getAttribute("modalidades").toString();
			sesion.setAttribute("cargas", cargas);
		}	
		
		HashMap<String,String> mapaModalidades = catModalidadDao.mapModalidades("");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargas", cargas);		
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "inscritos/menu/menu";
	}
	
	@RequestMapping("/inscritos/menu/cargas")
	public String inscritosMenuCargas(HttpServletRequest request, Model modelo){		
		
		String fecha			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		List<Carga> lisCargas	= cargaDao.getListPorFecha( fecha, "ORDER BY CARGA_ID");
		List<Carga> lisActivas	= cargaDao.getListCargasActivas( " ORDER BY CARGA_ID");
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisActivas", lisActivas);
		
		return "inscritos/menu/cargas";
	}
	
	@RequestMapping("/inscritos/menu/modalidades")
	public String inscritosMenuModalidades(HttpServletRequest request, Model modelo){		
		
		List<CatModalidad> lisModalidades		= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("lisModalidades", lisModalidades);
		
		return "inscritos/menu/modalidades";
	}
	
	@RequestMapping("/inscritos/menu/curpIncorrecta")
	public String inscritosMenuCurpIncorrecta(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|inscritosMenuCurpIncorrecta:");
		return "inscritos/menu/curpIncorrecta";
	}

	@RequestMapping("/inscritos/menu/semaforizacion")
	public String inscritosMenuSemaforizacion(HttpServletRequest request, Model modelo){
		
		String cargas 		= "";
		String modalidades	= "";
		String fechaIni		= aca.util.Fecha.getHoy();
		String fechaFin		= aca.util.Fecha.getHoy();
		HttpSession sesion	= ((HttpServletRequest)request).getSession();	
		if (sesion!=null){
			cargas 			= (String)sesion.getAttribute("cargas");
			modalidades 	= (String)sesion.getAttribute("modalidades");
			fechaIni		= (String)sesion.getAttribute("fechaIni");
			fechaFin		= (String)sesion.getAttribute("fechaFin");
		}
		
		List<CatEstado> lisEstados 					= catEstadoDao.getLista("91", " ORDER BY NOMBRE_ESTADO");
		HashMap<String,String> mapaAlumPorEstados 	= alumUbicacionDao.mapaAlumPorEstados(cargas, modalidades, fechaIni, fechaFin);	
		
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("mapaAlumPorEstados", mapaAlumPorEstados);
		
		
		return "inscritos/menu/semaforizacion";
	}
	
	@RequestMapping("/inscritos/menu/semaforizacion_alumno")
	public String inscritosMenuSemaforizacionAlumno(HttpServletRequest request, Model modelo){
		
		String cargas 		= "";
		String modalidades	= "";
		String fechaIni		= aca.util.Fecha.getHoy();
		String fechaFin		= aca.util.Fecha.getHoy();
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		
		if (sesion!=null){
			cargas = (String)sesion.getAttribute("cargas");
			modalidades 	= (String)sesion.getAttribute("modalidades");
			fechaIni		= (String)sesion.getAttribute("fechaIni");
			fechaFin		= (String)sesion.getAttribute("fechaFin");
		}
		
		List<AlumUbicacion> listEstadistica = alumUbicacionDao.getListEstadistica(cargas, fechaIni, fechaFin, "ORDER BY T_PAIS, T_ESTADO");
		
		HashMap<String,CatPais> mapaPaises	 		= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados 		= catEstadoDao.getMapAll();
		HashMap<String, AlumPersonal> mapaInscritos = alumPersonalDao.mapInscritosEnCargas(cargas);	
		
		modelo.addAttribute("listEstadistica", listEstadistica);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaInscritos", mapaInscritos);

		return "inscritos/menu/semaforizacion_alumno";
	}
	
	@RequestMapping("/inscritos/menu/ventasgcas")
	public String inscritosMenuVentasGcas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|ventasgcas:");
		return "inscritos/menu/ventasgcas";
	}
	
	@RequestMapping("/inscritos/menu/bajatotal")
	public String inscritosMenuVentasBajaTotal(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")) {
				fechaIni = (String)sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String)sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
		}	
		
		List<FesCcobro> lisBajas 				= fesCcobroDao.lisBajasPorFecha(fechaIni, fechaFin, " ORDER BY MATEO.FES_CCOBRO.FECHA");
		
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaMovimientos 		= fesCcobroDao.mapaMovimientosAlumno(fechaIni, fechaFin);
		
		modelo.addAttribute("lisBajas", lisBajas);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaMovimientos", mapaMovimientos);
		
		return "inscritos/menu/bajatotal";
	}
	
	@RequestMapping("/inscritos/menu/inscritosgcas")
	public String inscritosMenuInscritosGcas(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")) {
				fechaIni = (String)sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String)sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
		}
		
		List<CatCarrera> lisCarreras 					= catCarreraDao.lisEnEstadistica(fechaIni, fechaFin, " ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapaTotalAlumnos			= estadisticaDao.mapaTotalAlumnosPorCarrera(fechaIni, fechaFin);
		HashMap<String,String> mapaMovimientos			= fesCcobroDao.mapaMovimientos(fechaIni, fechaFin);
		
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaTotalAlumnos", mapaTotalAlumnos);
		modelo.addAttribute("mapaMovimientos", mapaMovimientos);
		
		return "inscritos/menu/inscritosgcas";
	}
	
	@RequestMapping("/inscritos/menu/trayectoria")
	public String inscritosMenuTrayectoria(HttpServletRequest request, Model modelo){
		
		String fechaIni			= "01/01/"+aca.util.Fecha.getHoyReversa().substring(0,4);
		String fechaFin			= "31/12/"+aca.util.Fecha.getHoyReversa().substring(0,4);
		String cargas			= "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			fechaIni 	= (String)sesion.getAttribute("fechaIni");
			fechaFin 	= (String)sesion.getAttribute("fechaFin");
			cargas	 	= (String)sesion.getAttribute("cargas");
		}
		
		List<Estadistica> lista 						= estadisticaDao.getList(cargas, "");
		List<AlumEstado> lisAlumnos 					= alumEstadoDao.getLista(cargas, " ORDER BY FACULTAD_ID");
		List<InsAlumno> lisInscripciones				= insAlumnoDao.lisPorCarga(cargas, "ORDER BY CODIGO_PERSONAL");
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");	
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,AlumPersonal> mapaAlumno			= alumPersonalDao.getMapAllCargaAlumEstado(cargas);
		HashMap<String,String> mapaEdad					= alumEstadoDao.mapaEdad(cargas, "I");
		
		HashMap<String,FinSaldo> mapaSaldos = new HashMap<String,FinSaldo>();
		FinSaldo finSaldo 	= new FinSaldo();
		
		for(Estadistica objeto : lista) {
			try {		
				RestTemplate restTemplate = new RestTemplate();
				finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+objeto.getCodigoPersonal(), FinSaldo.class);
				mapaSaldos.put(objeto.getCodigoPersonal(), finSaldo);
			}catch(Exception ex) {
				System.out.println("Error en saldo:"+objeto.getCodigoPersonal());
			}
		}
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisInscripciones", lisInscripciones);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumno", mapaAlumno);
		modelo.addAttribute("mapaSaldos", mapaSaldos);
		modelo.addAttribute("mapaEdad", mapaEdad);
		
		return "inscritos/menu/trayectoria";
	}
	
	@RequestMapping("/inscritos/menu/altasbajas")
	public String inscritosMenuAltasbajas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|altasbajas:");
		return "inscritos/menu/altasbajas";
	}

	@RequestMapping("/inscritos/menu/altasbajasChida")
	public String inscritosMenuAltasbajasChida(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|altasbajas:");
		return "inscritos/menu/altasbajasChida";
	}
	
	@RequestMapping("/inscritos/menu/creditosFecha")
	public String inscritosMenuCreditosFecha(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|creditosFecha:");
		return "inscritos/menu/creditosFecha";
	}
	
	@RequestMapping("/inscritos/menu/ventas")
	public String inscritosMenuVentas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInscritos|creditosFecha:");
		return "inscritos/menu/ventas";
	}
	
}