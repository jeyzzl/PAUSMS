package aca.web.taller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;
import aca.afe.spring.FesCcAfeAcuerdosDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecPuesto;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecPuestoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.log.spring.LogBeca;
import aca.log.spring.LogBecaDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContTallerReportes {
	
	@Autowired
	private FesCcobroDao fesCcobroDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private BecInformeDao becInformeDao;
	
	@Autowired
	private BecInformeAlumnoDao becInformeAlumnoDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private SunPlusFuncionDao sunPlusFuncionDao;
	
	@Autowired
	private BecTipoDao becTipoDao;
	
	@Autowired
	private BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;	

	@Autowired
	private CatCarreraDao catCarreraDao;	

	@Autowired
	private BecAcuerdoDao becAcuerdoDao;	
	
	@Autowired
	private BecCategoriaDao becCategoriaDao; 
	
	@Autowired
	private BecPuestoDao becPuestoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;	
	
	@Autowired
	FesCcAfeAcuerdosDao fesCcAfeAcuerdosDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;	
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	LogBecaDao logBecaDao;
	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/taller/reportes/menu")
	public String tallerReportesMenu(HttpServletRequest request){		
		return "taller/reportes/menu";
	}
	
	@RequestMapping("/taller/reportes/rendimientoInforme")
	public String tallerReportesRendimientoInformes(HttpServletRequest request, Model modelo){
		String ejercicioId 			= request.getParameter("ejercicioId")==null?"0":request.getParameter("ejercicioId");
		String ejercicioSesion		= "";
		String informeId 			= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioSesion = (String)sesion.getAttribute("ejercicioId");
		}		
		
		// Lista de ejercicios
		List<ContEjercicio> lisEjercicios 				= contEjercicioDao.getListProximos(" ORDER BY ID_EJERCICIO DESC");
				
		if (ejercicioId.equals("0") && ejercicioSesion != null) {
			ejercicioId = ejercicioSesion;
		}else if (ejercicioId.equals("0") && lisEjercicios.size() > 0) {
			ejercicioId = lisEjercicios.get(0).getIdEjercicio();
		}
		
		// Lista de informes 
		List<BecInforme> lisInformes 					= becInformeDao.getListEjercicio(ejercicioId, " ORDER BY NIVEL, ORDEN");
		if (informeId.equals("0") && lisInformes.size() > 0) {
			informeId = lisInformes.get(0).getInformeId(); 
		}
		
		// Lista de informes de alumnos
		List<BecInformeAlumno> lisInformesAlumno		= becInformeAlumnoDao.getBecInformeAlumnoPorEjercicioIdInformeId(ejercicioId, informeId, "");
		
		HashMap<String,String> mapaCalculos 			= fesCcobroDao.mapaCalculos(informeId);
		
		// Mapa de departamentos
		HashMap<String, ContCcosto> mapaCostos			= contCcostoDao.mapaCentrosCosto(ejercicioId);
		
		HashMap<String, String> mapaFunciones			= sunPlusFuncionDao.mapaFunciones();
		
		HashMap<String, String> mapaPrecios				= becTipoDao.mapaPrecios(ejercicioId);
		
		HashMap<String, BecPuestoAlumno> mapaPuestos	= becPuestoAlumnoDao.mapaPuestosEnInforme(informeId);
		
		HashMap<String, AlumPersonal> mapaAlumnos		= alumPersonalDao.mapAlumnosInformeBeca(informeId);

		HashMap<String, CatCarrera> mapaCarreras	    = catCarreraDao.getMapAll("");		
		
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("informeId", informeId);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisInformesAlumno", lisInformesAlumno);
		modelo.addAttribute("mapaCalculos", mapaCalculos);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaFunciones", mapaFunciones);
		modelo.addAttribute("mapaPrecios", mapaPrecios);
		modelo.addAttribute("mapaPuestos", mapaPuestos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);	
		
		enviarConEnoc(request,"Error-aca.ControllerTaller|tallerReportesRendimientoInforme");
		
		return "taller/reportes/rendimientoInforme";
	}
	
	@RequestMapping("/taller/reportes/reporteInforme")
	public String tallerReportesReporteInforme(HttpServletRequest request, Model modelo){
		String ejercicioId 			= request.getParameter("ejercicioId")==null?"0":request.getParameter("ejercicioId");
		String ejercicioSesion		= "";
		String informeId 			= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioSesion = (String)sesion.getAttribute("ejercicioId");
		}		
		
		// Lista de ejercicios
		List<ContEjercicio> lisEjercicios 				= contEjercicioDao.getListProximos(" ORDER BY ID_EJERCICIO DESC");
		
		if (ejercicioId.equals("0") && ejercicioSesion != null) {
			ejercicioId = ejercicioSesion;
		}else if (ejercicioId.equals("0") && lisEjercicios.size() > 0) {
			ejercicioId = lisEjercicios.get(0).getIdEjercicio();
		}
		
		// Lista de informes 
		List<BecInforme> lisInformes 					= becInformeDao.getListEjercicio(ejercicioId, " ORDER BY NIVEL, ORDEN");
		if (informeId.equals("0") && lisInformes.size() > 0) {
			informeId = lisInformes.get(0).getInformeId(); 
		}		

		List<BecInformeAlumno> lisInformesAlumno		= becInformeAlumnoDao.getBecInformeAlumnoPorEjercicioIdInformeId(ejercicioId, informeId, "");
		
		ArrayList<BecAcuerdo> lisAcuerdos 				= becAcuerdoDao.lisAcuerdosEnInforme(ejercicioId, informeId, " ORDER BY TIPO");	
		
		HashMap<String,String> mapaCalculos 			= fesCcobroDao.mapaCalculos(informeId);
		
		HashMap<String, ContCcosto> mapaCostos			= contCcostoDao.mapaCentrosCosto(ejercicioId);
		
		HashMap<String, String> mapaFunciones			= sunPlusFuncionDao.mapaFunciones();
		
		HashMap<String, String> mapaPrecios				= becTipoDao.mapaPrecios(ejercicioId);
		
		HashMap<String, BecPuestoAlumno> mapaPuestos	= becPuestoAlumnoDao.mapaPuestosEnInforme(informeId);
		
		HashMap<String, AlumPersonal> mapaAlumnos		= alumPersonalDao.mapAlumnosInformeBeca(informeId);
		
		HashMap<String, CatCarrera> mapaCarreras	    = catCarreraDao.getMapAll("");		
		
		HashMap<String, BecTipo> mapaBecTipos		   	= becTipoDao.mapaBecTipos(ejercicioId);
		
		HashMap<String, MapaPlan> mapPlanes 			= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("informeId", informeId);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisInformesAlumno", lisInformesAlumno);
		modelo.addAttribute("lisAcuerdos", lisAcuerdos);
		
		modelo.addAttribute("mapaCalculos", mapaCalculos);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaFunciones", mapaFunciones);
		modelo.addAttribute("mapaPrecios", mapaPrecios);
		modelo.addAttribute("mapaPuestos", mapaPuestos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaBecTipos", mapaBecTipos);
		modelo.addAttribute("mapPlanes", mapPlanes);
		
		enviarConEnoc(request,"Error-aca.ControllerTaller|tallerReportesReporteInforme");
		
		return "taller/reportes/reporteInforme";
	}
	
	@RequestMapping("/taller/reportes/informe")
	public String tallerReportesInforme(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesInforme:");
		return "taller/reportes/informe";
	}	
	
	@RequestMapping("/taller/reportes/estadisticaPuestos")
	public String tallerReportesEstadisticaPues(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesEstadisticaPues:");
		return "taller/reportes/estadisticaPuestos";
	}	
	
	@RequestMapping("/taller/reportes/general")
	public String tallerReportesGeneral(HttpServletRequest request){
		
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesGeneral:");
		
		return "taller/reportes/general";
	}	
	
	@RequestMapping("/taller/reportes/discrepanciasEnHoras")
	public String tallerReportesDiscrepanciasEnHoras(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesDiscrepanciasEnHoras:");
		return "taller/reportes/discrepanciasEnHoras";
	}	
	
	@RequestMapping("/taller/reportes/contratoTipos")
	public String tallerReportesContratoTipos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesContratoTipos:");
		return "taller/reportes/contratoTipos";
	}	
	
	@RequestMapping("/taller/reportes/informemensual")
	public String tallerReportesInformeMensual(HttpServletRequest request, Model modelo){
		
		String ejercicioSesion 	= "0";
		String codigoPersonal 	= "0"; 
		List<ContEjercicio> lisEjercicios = contEjercicioDao.getListAll("ORDER BY 1 DESC");
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioSesion = (String)sesion.getAttribute("ejercicioId");
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
		}
		String ejercicioId 	= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String informeId 	= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String niveles 		= "0";
		
		if(!ejercicioId.equals("0")){			
			sesion.setAttribute("ejercicioId", ejercicioId);
		}else {
			ejercicioId = ejercicioSesion; 
		}
		
		String acceso		= becAccesoDao.getUsuarioCentrosCosto(ejercicioId, codigoPersonal);
		boolean admin       = accesoDao.getBecas(codigoPersonal);
		
		// Llena la lista de informes
		List<BecInforme> lisInformes = becInformeDao.getListEjercicio(ejercicioId," ORDER BY ORDEN");
		boolean existe = false;
		for (BecInforme informe : lisInformes) {
			if (informe.getInformeId().equals(informeId)) {
				existe = true;
				if (informe.getNivel().equals("P")) niveles = "1"; else niveles = "2,3,4,5";
			}
		}
		if ((informeId.equals("0") || existe==false) && lisInformes.size() >= 1 ) {
			informeId = lisInformes.get(0).getInformeId();
			if (becInformeDao.getNivel(informeId).equals("P")) niveles = "1"; else niveles = "2,3,4,5";
		}
			
		// Llena la lista de departamentos
		List<ContCcosto> lisDeptos = contCcostoDao.listDeptosConPuestos(ejercicioId, "'S'", "ORDER BY ID_CCOSTO");
		
		HashMap<String, String> mapaEstados			= becInformeAlumnoDao.mapDeptoEstado( ejercicioId, informeId);
		HashMap<String, String> mapaTotales			= becInformeAlumnoDao.totalPorDepartamento(ejercicioId, informeId, niveles);
					
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("informeId", informeId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("niveles", niveles);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisDeptos", lisDeptos);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaTotales", mapaTotales);
		
		return "taller/reportes/informemensual";
	}	
	
	@RequestMapping("/taller/reportes/becasTipos")
	public String tallerReportesBecasTipos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesBecasTipos:");
		return "taller/reportes/becasTipos";
	}	
	
	@RequestMapping("/taller/reportes/basicaAdicional")
	public String tallerReportesBasicaAdicional(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesBasicaAdicional:");
		return "taller/reportes/basicaAdicional";
	}	
	
	@Transactional
	@RequestMapping("/taller/reportes/alumnoshoras")
	public String tallerReportesAlumnosHoras(HttpServletRequest request, Model modelo){
		
		String idEjercicio 	= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String idCcosto 	= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
		String informeId 	= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		
		String codigo 	= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			codigo 	= (String)sesion.getAttribute("codigoPersonal");
		}		
		
		boolean admin       = accesoDao.getBecas( codigo);
		String hoy 			= aca.util.Fecha.getHoy();
		
		String accion		= request.getParameter("Accion")==null?"1":request.getParameter("Accion");	
		 
		String mensaje = "";
		int contA 	   = 0;
		 
		if(accion.equals("2")){
				
			//Puesto de Alumnos 	
			List<BecInformeAlumno> lisAlumnos 	     = becInformeAlumnoDao.getHorasAlumnosInformados(idEjercicio, idCcosto, informeId, "ORDER BY CODIGO_PERSONAL");			
			boolean error = false;			
			for(BecInformeAlumno alumno: lisAlumnos){			
				
				if(becInformeAlumnoDao.existeReg(informeId, alumno.getCodigoPersonal())){
					if(becInformeAlumnoDao.updateEstado("2", informeId, alumno.getCodigoPersonal())){
						//AUTORIZANDO (CAMBIANDO ESTADO A 2)
						contA++;
					}else{
						error = true;
						break;
					}	
				}
				
			}
			
			if(error){				
				mensaje = "<div class='alert alert-danger'>Ocurrior un Error al Autorizar a los alumnos</div>";
			}else{				
				mensaje = "<div class='alert alert-success'>Se Autorizaron "+contA+" alumnos</div>";
			}			
		}else if(accion.equals("3")){
			
			//Puesto de Alumnos 	
			List<BecInformeAlumno> lisAlumnos	     = becInformeAlumnoDao.getHorasAlumnosInformados(idEjercicio, idCcosto, informeId, "ORDER BY CODIGO_PERSONAL");
			
			boolean error = false;			
			for(BecInformeAlumno alumno: lisAlumnos){								
				if(becInformeAlumnoDao.existeReg(informeId, alumno.getCodigoPersonal())){
					
					//becInformeAlumnoDao.mapeaRegId(conEnoc, informeId, alumno.getCodigoPersonal());
					if(alumno.getEstado().equals("2")){//SOLO SI TIENE ESTADO 2						
						if(becInformeAlumnoDao.updateEstado("1", informeId, alumno.getCodigoPersonal())){//DESAUTORIZAR (CAMBIANDO ESTADO A 1)
							contA++;
						}else{
							error = true;
							break;
						}
					}
				}			
			}
			
			if(error){				
				mensaje = "<div class='alert alert-danger'>Ocurrior un Error al Desautorizar a los alumnos</div>";
			}else{				
				mensaje = "<div class='alert alert-success'>Se Desautorizaron "+contA+" alumnos</div>";
			}		
		}else if(accion.equals("4")){
			// Cancelar horas registradas que no fueron contabilizadas
			String codigoAlumno = request.getParameter("codigoAlumno");
			
			if(becInformeAlumnoDao.existeReg(informeId, codigoAlumno)){			
				BecInformeAlumno alumno = becInformeAlumnoDao.mapeaRegId(informeId, codigoAlumno);
				
				LogBeca logBeca = new LogBeca();
				
				logBeca.setTabla("BEC_INFORME_ALUMNO");
				logBeca.setFecha(hoy);
				logBeca.setOperacion("UPDATE");
				logBeca.setIp(request.getRemoteAddr());
				logBeca.setUsuario(codigo);
				logBeca.setDatos("Ejercicio:"+idEjercicio+",Alumno:"+codigoAlumno+",Informe:"+informeId+",Usuario:"+codigo+",Horas Old:"+alumno.getHoras()+",Horas New:0"+"Puesto:"+alumno.getPuestoId());
				logBeca.setId( logBecaDao.maximoReg());
				if (becInformeAlumnoDao.updateHoras("0", informeId, codigoAlumno)){
					if (logBecaDao.insertReg(logBeca)){
						mensaje = "<div class='alert alert-success'>Horas canceladas</div>";
					}
				}			
			}		
		}else if(accion.equals("5")){
			// Cancelar la autorizacion del informe de horas de un alumno
			String codigoAlumno = request.getParameter("codigoAlumno");
			
			if(becInformeAlumnoDao.existeReg(informeId, codigoAlumno)){
				BecInformeAlumno alumno = becInformeAlumnoDao.mapeaRegId(informeId, codigoAlumno);
				
				LogBeca logBeca = new LogBeca();
				logBeca.setTabla("BEC_INFORME_ALUMNO");
				logBeca.setFecha(hoy);
				logBeca.setOperacion("UPDATE");
				logBeca.setIp(request.getRemoteAddr());
				logBeca.setUsuario(codigo);
				logBeca.setDatos("Ejercicio:"+idEjercicio+",Alumno:"+codigoAlumno+",Informe:"+informeId+",Usuario:"+codigo+",Estado Old:"+alumno.getEstado()+",Estado New:1"+"Puesto:"+alumno.getPuestoId());
				logBeca.setId( logBecaDao.maximoReg() );
				if (alumno.getEstado().equals("2")){					
					if (becInformeAlumnoDao.updateEstado("1", informeId, codigoAlumno)){
						if (logBecaDao.insertReg(logBeca)){
							mensaje = "<div class='alert alert-success'>Autorización cancelada</div>";
						}			
					}
				}			
			}		
		}else if(accion.equals("6")){
			// Autorizar individualmente
			String codigoAlumno = request.getParameter("codigoAlumno");
			
			if(becInformeAlumnoDao.existeReg(informeId, codigoAlumno)){			
				BecInformeAlumno alumno = becInformeAlumnoDao.mapeaRegId(informeId, codigoAlumno);
				
				LogBeca logBeca = new LogBeca();
				logBeca.setTabla("BEC_INFORME_ALUMNO");
				logBeca.setFecha(hoy);
				logBeca.setOperacion("UPDATE");
				logBeca.setIp(request.getRemoteAddr());
				logBeca.setUsuario(codigo);
				logBeca.setDatos("Ejercicio:"+idEjercicio+",Alumno:"+codigoAlumno+",Informe:"+informeId+",Usuario:"+codigo+",Estado Old:"+alumno.getEstado()+",Estado New:2"+"Puesto:"+alumno.getPuestoId());
				logBeca.setId( logBecaDao.maximoReg() );
				if (alumno.getEstado().equals("1")){				
					if (becInformeAlumnoDao.updateEstado("2", informeId, codigoAlumno)){
						if (logBecaDao.insertReg(logBeca)){
							mensaje = "<div class='alert alert-success'>Autorizado</div>";
						}			
					}
				}			
			}		
		}
		
		String informeNombre 					= becInformeDao.getNombreInforme(informeId);
		String deptoNombre 						= contCcostoDao.getNombre(idEjercicio, idCcosto);
		
		List<BecInformeAlumno> lisBecados		= becInformeAlumnoDao.getHorasAlumnosInformados(idEjercicio, idCcosto, informeId, " ORDER BY CODIGO_PERSONAL");
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnPuestos(idEjercicio);
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("informeNombre", informeNombre);
		modelo.addAttribute("deptoNombre", deptoNombre);
		
		modelo.addAttribute("lisBecados", lisBecados);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "taller/reportes/alumnoshoras";
	}	
	
	@RequestMapping("/taller/reportes/acuerdosTipo")
	public String tallerReportesAcuerdosTipo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesAcuerdosTipo:");
		return "taller/reportes/acuerdosTipo";
	}	
	
	@RequestMapping("/taller/reportes/categorias")
	public String tallerReportesCategorias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesCategorias:");
		return "taller/reportes/categorias";
	}	
	
	@RequestMapping("/taller/reportes/acuerdos")
	public String tallerReportesAcuerdos(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal = "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
		}		
		
		String fechaHoy 	= aca.util.Fecha.getHoy();
		String fecha 		= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");	
		String estado 		= request.getParameter("estado")==null?"I":request.getParameter("estado");
		String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");		
		boolean admin		= accesoDao.getBecas(codigoPersonal);
		String mensaje		= "-";
		
		if(accion.equals("1")){
			if ( becAcuerdoDao.existeReg(request.getParameter("codigoPersonal"), request.getParameter("folio")) ){			
				if(becAcuerdoDao.deleteReg(request.getParameter("folio"), request.getParameter("codigoPersonal"))){
					mensaje = "<div class='alert alert-success'>Se Eliminó Correctamente</div>";
				}else{
					mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Eliminar</div>";
				}
			}	
		}
		
		List<ContEjercicio> lisEjercicios 	= contEjercicioDao.getListAll("ORDER BY 1 DESC");		
		String idEjercicio 					= request.getParameter("idEjercicio")==null?lisEjercicios.get(0).getIdEjercicio():request.getParameter("idEjercicio");
		List<BecAcuerdo> lisAcuerdos 		= becAcuerdoDao.getList(idEjercicio, fecha, estado, " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ACUERDO IN ('O','M','P', 'E')) ORDER BY TIPO, ESTADO ");		
		
		HashMap<String,String> mapInscritos 			= inscritosDao.getMapaInscritos();
		HashMap<String,BecPuestoAlumno> mapPuestos 		= becPuestoAlumnoDao.getPuestosAlumno(idEjercicio, aca.util.Fecha.getHoy());
		HashMap<String,ContCcosto> mapDepartamentos 	= contCcostoDao.mapaCentrosCosto(idEjercicio);
		HashMap<String,String> mapTotales				= fesCcAfeAcuerdosDao.mapBecaAcuerdoTodos(idEjercicio, fecha);
		HashMap<String,AlumPersonal> mapAlumnos			= alumPersonalDao.mapAlumnosEnAcuerdos(idEjercicio);
		HashMap<String,BecTipo> mapTipos				= becTipoDao.mapaBecTipos(idEjercicio);
		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("idEjercicio", idEjercicio);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisAcuerdos", lisAcuerdos);		
		
		modelo.addAttribute("mapInscritos", mapInscritos);
		modelo.addAttribute("mapPuestos", mapPuestos);
		modelo.addAttribute("mapDepartamentos", mapDepartamentos);
		modelo.addAttribute("mapTotales", mapTotales);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapTipos", mapTipos);
		
		return "taller/reportes/acuerdos";
	}	
	
	@RequestMapping("/taller/reportes/rendimientoPuesto")
	public String tallerReportesRendimientoPuesto(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesRendimientoPuesto:");
		return "taller/reportes/rendimientoPuesto";
	}	
	
	@RequestMapping("/taller/reportes/reporte_graficas")
	public String tallerReportesReporteGraficas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesReporteGraficas:");
		return "taller/reportes/reporte_graficas";
	}	
	
	@RequestMapping("/taller/reportes/reporte_puestos")
	public String tallerReportesReportePuestos(HttpServletRequest request, Model modelo){
		
		//Lista de ejercicios
		List<ContEjercicio> listaEjercicios 	= contEjercicioDao.getListAll("ORDER BY 1 DESC");
		
		//Variables
		String ejercicioId 		= request.getParameter("ejercicioId")==null?listaEjercicios.get(0).getIdEjercicio():request.getParameter("ejercicioId");	
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String fecha 			= request.getParameter("Fecha")==null?fechaHoy:request.getParameter("Fecha");
		
		//Nombres de centros de costo
		List<ContCcosto> listaCentroCostos		= contCcostoDao.getListCentrosCostoVacantes(ejercicioId, "'S'", "ORDER BY ID_CCOSTO");
		//Lista de puestos
		List<BecPuesto> listaPuestos			= becPuestoDao.listPuestosEjercicio(ejercicioId, fecha, "ORDER BY CATEGORIA_ID");
		//Alumnos en los puestos
		List<BecPuestoAlumno> listaAlumnos 		= becPuestoAlumnoDao.listAlumnosEnPuestos(ejercicioId, "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		
		HashMap<String, String> mapaCategorias	= becCategoriaDao.getMapCategorias();
		HashMap<String,String> mapaTotalBeca	= fesCcAfeAcuerdosDao.mapBeca(ejercicioId, fecha);
		HashMap<String,String> mapaBecDiezmo 	= fesCcAfeAcuerdosDao.mapBecaDiezmo(ejercicioId, fecha);
		HashMap<String,String> mapaBasicas		= fesCcAfeAcuerdosDao.mapBasicas(ejercicioId, fecha);
		HashMap<String,String> mapaAdicionales	= fesCcAfeAcuerdosDao.mapAdicional(ejercicioId, fecha);		
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnPuestos(ejercicioId);
		
		modelo.addAttribute("listaAlumnos", listaAlumnos);
		modelo.addAttribute("listaPuestos", listaPuestos);
		modelo.addAttribute("listaCentroCostos", listaCentroCostos);
		modelo.addAttribute("listaEjercicios", listaEjercicios);
		
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaTotalBeca", mapaTotalBeca);
		modelo.addAttribute("mapaBecDiezmo", mapaBecDiezmo);
		modelo.addAttribute("mapaBasicas", mapaBasicas);
		modelo.addAttribute("mapaAdicionales", mapaAdicionales);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "taller/reportes/reporte_puestos";
	}	
	
	@RequestMapping("/taller/reportes/reporteHoras")
	public String tallerReportesReporteHoras(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesReporteHoras:");
		return "taller/reportes/reporteHoras";
	}	
	
	@RequestMapping("/taller/reportes/informesContabilizados")
	public String tallerReportesInformesContabilizados(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerReportes|tallerReportesInformesContabilizados:");
		return "taller/reportes/informesContabilizados";
	}	

}