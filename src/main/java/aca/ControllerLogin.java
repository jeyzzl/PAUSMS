package aca;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoClave;
import aca.acceso.spring.AccesoClaveDao;
import aca.acceso.spring.AccesoDao;
import aca.acceso.spring.AccesoOpcion;
import aca.acceso.spring.AccesoOpcionDao;
import aca.acceso.spring.AccesoPrivacidad;
import aca.acceso.spring.AccesoPrivacidadDao;
import aca.acceso.spring.AccesoValida;
import aca.acceso.spring.AccesoValidaDao;
import aca.alerta.spring.AlertaCovid;
import aca.alerta.spring.AlertaCovidDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumBloqueo;
import aca.alumno.spring.AlumColorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumRegistro;
import aca.alumno.spring.AlumRegistroDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.bec.spring.BecCategoria;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecPeriodoDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCriterioDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligionDao;
import aca.covid.spring.Covid;
import aca.covid.spring.CovidDao;
import aca.covid.spring.CovidPeriodoDao;
import aca.diploma.spring.DipAlumno;
import aca.diploma.spring.DipAlumnoDao;
import aca.diploma.spring.DipCurso;
import aca.diploma.spring.DipCursoDao;
import aca.emp.spring.EmpContacto;
import aca.emp.spring.EmpContactoDao;
import aca.emp.spring.EmpDocEmp;
import aca.emp.spring.EmpFoto;
import aca.emp.spring.EmpFotoDao;
import aca.emp.spring.EmpMaestroDao;
import aca.emp.spring.Empleado;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.FinBloqueoDao;
import aca.financiero.spring.FinPeriodo;
import aca.financiero.spring.FinPeriodoDao;
import aca.financiero.spring.FinPermisoDao;
import aca.financiero.spring.FinSaldo;
import aca.internado.spring.IntAccesoDao;
import aca.internado.spring.IntDormitorioDao;
import aca.maestro.MaestroRangoDao;
import aca.mail.MailService;
import aca.mensaje.spring.Mensaje;
import aca.mensaje.spring.MensajeDao;
import aca.menu.spring.Menu;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloAyuda;
import aca.menu.spring.ModuloAyudaDao;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.menu.spring.Sesion;
import aca.menu.spring.SesionDao;
import aca.mov.Claims;
import aca.mov.User;
import aca.mov.UserPas;
import aca.notifica.spring.NotiCovid;
import aca.opcion.spring.MaestroOpcion;
import aca.opcion.spring.Opcion;
import aca.opcion.spring.OpcionDao;
import aca.padre.spring.PadrePersonal;
import aca.padre.spring.PadrePersonalDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.pg.archivo.ArchGeneral;
import aca.pg.archivo.FotoChica;
import aca.pg.archivo.FotoChicaDao;
import aca.pg.archivo.spring.PosArchDocAlum;
import aca.pg.archivo.spring.PosArchDocAlumDao;
import aca.pg.archivo.spring.PosArchGeneralDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.valida.spring.ValDocumento;
import aca.valida.spring.ValDocumentoDao;
import aca.vista.spring.AlumnoDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import aca.opcion.spring.MaestroOpcionDao;
import aca.pg.archivo.spring.PosArchGeneral;


@Controller
public class ControllerLogin{	
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private FotoChicaDao fotoChicaDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AccesoClaveDao accesoClaveDao;
	
	@Autowired
	private AccesoPrivacidadDao accesoPrivacidadDao;
	
	@Autowired
	private OpcionDao opcionDao;
	
	@Autowired
	ServletContext context;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private CatCriterioDao catCriterioDao;

	@Autowired
	private SesionDao sesionDao;
	
	@Autowired
	private ModuloDao moduloDao;	
	
	@Autowired
	private aca.menu.spring.MenuDao menuDao;
	
	@Autowired
	private ModuloOpcionDao moduloOpcionDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	private IntAccesoDao intAccesoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MensajeDao mensajeDao;
	
	@Autowired
	private aca.emp.spring.EmpleadoDao empleadoDao;
	
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.cultural.spring.CompEventoImagenDao compEventoImagenDao;
	
	@Autowired
	private AlumColorDao alumColorDao;
	
	@Autowired
	private aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	private aca.emp.spring.EmpDocEmpDao empDocEmpDao;
	
	@Autowired
	private aca.emp.spring.EmpDocumentoDao empDocumentoDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private EmpMaestroDao empMaestroDao;
	
	@Autowired
	private PadrePersonalDao padrePersonalDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private BecPeriodoDao becPeriodoDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	ModuloAyudaDao moduloAyudaDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	MaestroOpcionDao maestroOpcionDao;
	
	@Autowired
	FinPeriodoDao finPeriodoDao;
	
	@Autowired
	FinPermisoDao finPermisoDao;
	
	@Autowired
	BecCategoriaDao becCategoriaDao;
	
	@Autowired
	CovidDao covidDao;
	
	@Autowired
	CovidPeriodoDao covidPeriodoDao;
	
	@Autowired
	AlumnoDao alumnoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	private AlertaCovidDao alertaCovidDao;
	
	@Autowired
	private AlumRegistroDao alumRegistroDao;	
	
	@Autowired
	private EmpFotoDao empFotoDao;
	
	@Autowired
	private EmpContactoDao empContactoDao;
	
	@Autowired
	private MailService mailService;	
	
	@Autowired
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AccesoValidaDao accesoValidaDao;
	
	@Autowired
	private PosArchDocAlumDao posArchDocAlumDao;
	
	@Autowired
	private PosArchGeneralDao posArchGeneralDao;
	
	@Autowired	
	MaestroRangoDao maestroRangoDao;	
	
	@Autowired
	AccesoOpcionDao accesoOpcionDao;
	
	@Autowired
	FinBloqueoDao finBloqueoDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	ValDocumentoDao valDocumentoDao;	
	
	@Autowired
	DipCursoDao dipCursoDao;
	
	@Autowired
	DipAlumnoDao dipAlumnoDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;
	
	
	@RequestMapping(value={"/"})
	public String raiz(HttpServletRequest request, Model modelo){
		return "redirect:/login";
	}
	
	@RequestMapping(value={"/login"})
	public String login(HttpServletRequest request, Model modelo){
				
		//String usuario	= request.getParameter("usuario")==null?"X":request.getParameter("usuario");
		//String clave	= request.getParameter("password")==null?"X":request.getParameter("password");		
		//System.out.println("Login:"+aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos()+":"+usuario); 
		//if (!usuario.equals("X")){
			//System.out.println(usuario+" Encode:"+bCryptPasswordEncoder.encode(clave)+" BD:"+acceso.getClaveHexa());
			//System.out.println("Match:"+bCryptPasswordEncoder.matches(clave, acceso.getClaveHexa()));
		//}		
		// $2a$10$RxfVkRjPz69UBT6WCtLote/aITnblttm0zs2EUpipdGLc1vggtvj6		
		return "login";		
	}
	
	@RequestMapping(value={"/loginActivos"})
	public String loginActivos(HttpServletRequest request, Model modelo){		
		return "loginActivos";		
	}
	
	@RequestMapping(value={"/loginSecurity"})
	public String loginSecurity(HttpServletRequest request, Model modelo){
		
		//String usuario	= request.getParameter("usuario")==null?"X":request.getParameter("usuario");
		//System.out.println("Login Security:"+aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos()+":"+usuario);
		
		return "loginSecurity";		
	}
	
	@RequestMapping(value={"/errorAcceso"})
	public String errorAcceso(HttpServletRequest request, Model modelo){		
		return "errorAcceso";
	}
	
	@RequestMapping(value={"/sinAcceso"})
	public String sinAcceso(HttpServletRequest request, Model modelo){		
		return "sinAcceso";
	}
	
	@RequestMapping(value={"/registro"})
	public String registro(HttpServletRequest request, Model modelo){	
				
		List<MapaPlan> listPlanesRegistro = mapaPlanDao.listPlanesRegistro(" ORDER BY NOMBRE_PLAN");
		
		modelo.addAttribute("listPlanesRegistro", listPlanesRegistro);
		
		return "registro";
	}
	
	@RequestMapping(value={"/grabarRegistro"})
	public String grabarRegistro(HttpServletRequest request, Model modelo){
		String nombre 	= request.getParameter("Nombre");
		String paterno 	= request.getParameter("Paterno");
		String materno 	= request.getParameter("Materno");
		String correo 	= request.getParameter("Correo");
		String tel	 	= request.getParameter("Telefono") == null ? "-" : request.getParameter("Telefono");
		String plan	 	= request.getParameter("Plan") == null ? "-" : request.getParameter("Plan");
		String mensaje	= "0";
		
		//List<MapaPlan> listPlanesRegistro = mapaPlanDao.listPlanesRegistro(" ORDER BY NOMBRE_PLAN");
		
		/* Ya no valida si se utiliza el mismo correo para dar de alta a mas de un alumno (Perición de CIMUM)
		if(alumRegistroDao.existeCorreo(correo)) {
			return "redirect:/registro?Mensaje=2";
		}
		*/
		
		String id = alumRegistroDao.maximoReg();
		
		AlumRegistro alumno = new AlumRegistro();
		
		alumno.setId(id);
		alumno.setNombre(nombre.toUpperCase());
		alumno.setPaterno(paterno.toUpperCase());
		alumno.setMaterno(materno.toUpperCase());
		alumno.setCorreo(correo);
		alumno.setTelefono(tel);
		alumno.setPlanId(plan);
		
		String codigo = String.valueOf(nombre.charAt(0))+String.valueOf(paterno.charAt(0))+aca.util.Fecha.getHora()+aca.util.Fecha.getMinutos()+aca.util.Fecha.getSegundos();
		
		alumno.setCodigo(codigo);
		
		if(!alumRegistroDao.existeReg(id)) {
			if(alumRegistroDao.insertReg(alumno)) {
				try {
					correo = correo.trim();
					mailService.sendMesageSimple(correo, "Registro de "+nombre+" "+paterno+" "+materno+")", "Código de activación: "+codigo);
					mensaje = "1";
				} catch (Exception e) {
					System.out.println("Error en grabarRegistro("+correo+"): "+e.getMessage());
				}
			}
		}	
		
		return "redirect:/registro?Correo="+correo+"&Mensaje="+mensaje;
	}

	@Transactional	
	@RequestMapping(value={"/activarCuenta"})
	public String activarCuenta(HttpServletRequest request, Model modelo){
		String correo 				= request.getParameter("Correo");
		String codigo 				= request.getParameter("Codigo");
		String codigoPersonal 		= "0"; 
		String matricula	 		= "0";
		String mensaje 				= "0";
		boolean grabado 			= true;
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}
		Acceso acceso 				= new Acceso();		
		AlumRegistro alumno 		= new AlumRegistro();
		AlumPersonal alumPersonal	= new AlumPersonal();
		AlumUbicacion alumUbicacion	= new AlumUbicacion();
		AlumAcademico alumAcademico	= new AlumAcademico();
		AlumPlan alumPlan 			= new AlumPlan();
		
		if(alumRegistroDao.existeRegPorCorreoyCodigo(correo,codigo)) {
			alumno = alumRegistroDao.mapeaRegPorCorreoyCodigo(correo, codigo);
		}

		Parametros parametros = parametrosDao.mapeaRegId("1");

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}
		
		matricula = alumPersonalDao.maximoReg(tipo);
		
		alumPersonal.setCodigoPersonal(matricula);
		alumPersonal.setNombre(alumno.getNombre());
		alumPersonal.setApellidoPaterno(alumno.getPaterno());
		alumPersonal.setApellidoMaterno(alumno.getMaterno());
		alumPersonal.setNombreLegal(alumno.getNombre()+" "+alumno.getPaterno()+" "+alumno.getMaterno());
		alumPersonal.setFNacimiento(aca.util.Fecha.getHoy());
		alumPersonal.setSexo("-");
		alumPersonal.setReligionId("1");
		alumPersonal.setTelefono(alumno.getTelefono());
		alumPersonal.setEmail(alumno.getCorreo());
		alumPersonal.setPaisId("91");
		alumPersonal.setEstadoId("");
		alumPersonal.setCiudadId("0");
		alumPersonal.setNacionalidad("91");
		alumPersonal.setFCreado(aca.util.Fecha.getHoy());
		alumPersonal.setUsAlta(codigoPersonal);
		
		String codigoTemp = alumPersonal.getCodigoPersonal();
		String claveDigest	= aca.util.Encriptar.sha512ConBase64(codigo);		
		
		acceso.setCodigoPersonal(codigoTemp);				
		acceso.setClave(claveDigest);			
		
		if(!accesoDao.existeReg(codigoTemp)){
			acceso.setIngreso("N");
			acceso.setAdministrador("N");
			acceso.setCotejador("N");
			acceso.setSupervisor("N");
			acceso.setPortalAlumno("N");
			acceso.setPortalMaestro("N");			
			acceso.setModalidad("0");
			acceso.setUsuario(codigoTemp);
			acceso.setConvalida("N");
			acceso.setIdioma("en");
			acceso.setMenu("1");
			acceso.setClaveInicial(codigo);
			if (accesoDao.insertReg(acceso)) {
				if (alumPersonalDao.insertReg(alumPersonal)) {
					
					alumUbicacion.setCodigoPersonal(matricula);
					alumUbicacion.setCodigoPadre("0");
					alumUbicacion.setCodigoMadre("0");
					alumUbicacion.setFecha(aca.util.Fecha.getHoy());			
					if (alumUbicacionDao.insertReg(alumUbicacion)) {
						alumAcademico.setCodigoPersonal(matricula);
						alumAcademico.setFAlta(aca.util.Fecha.getHoy());
						alumAcademico.setRequerido("S");
						if (alumAcademicoDao.insertReg(alumAcademico)) {
							alumPlan.setCodigoPersonal(matricula);
							alumPlan.setPlanId(alumno.getPlanId());
							alumPlan.setFInicio(aca.util.Fecha.getHoy());
							alumPlan.setEstado("A");
							alumPlan.setPrincipal("S");
							if (alumPlanDao.insertReg(alumPlan)) {
								grabado = true;
							}
						}
					}					
				}
			}			
		}	
		
		boolean existeCorreo 	= alumRegistroDao.existeCorreo(correo);
		boolean existeCuenta 	= alumRegistroDao.existeCuenta(correo, codigo);
		if(existeCorreo && existeCuenta && grabado){			
			try {
				correo = correo.trim();
				mailService.sendMesageSimple(correo, "Matrícula de "+alumno.getNombre()+" "+alumno.getPaterno()+" "+alumno.getMaterno(), "Tu matrícula es "+matricula+" y la clave de acceso es la misma que el codigo de activación");
				mensaje = "4";
			} catch (Exception e) {
				System.out.println("Error en activarCuenta("+correo+"): "+e.getMessage());
			}			
		}else if (existeCorreo==false){
			mensaje = "3";
		}else if (existeCuenta == false) {
			mensaje = "5";
		}else {
			mensaje = "6";
		}
		
		return "redirect:/registro?Correo="+correo+"&Mensaje="+mensaje;
	}
	
	@RequestMapping(value={"/valida"})
	public String valida(HttpServletRequest request, Model modelo){		
		
		String usuario 					= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		String clave					= request.getParameter("Clave")==null?"0":request.getParameter("Clave");		
		String ip						= request.getRemoteAddr();	    
	    String claveSha 				= aca.util.Encriptar.sha512ConBase64(clave);
	    String codigoPersonal			= "";
		String opciones					= "";
		String saltoPagina				= "";
		String sesionId					= "";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	sesionId 		= sesion.getId();
        	sesion.setMaxInactiveInterval(3200);        	
        }	
		List<Menu> lisMenu							= new ArrayList<Menu>();
		List<Modulo> lisModulo						= new ArrayList<Modulo>();
		List<ModuloOpcion> lisOpcion 				= new ArrayList<ModuloOpcion>();
		HashMap<String,String> carpetas 			= new HashMap<String,String>();		
		HashMap<String,Modulo> mapaModulos 			= moduloDao.mapModulos();
		HashMap<String,ModuloOpcion> mapaOpciones	= moduloOpcionDao.mapaOpciones();
		String porPeriodo							= aca.util.Fecha.getHoy().substring(6,10);
		aca.util.Fecha fecha						= new aca.util.Fecha();
		boolean validaClave 						= false;
		boolean avisoPrivacidad						= false;
		
		Acceso acceso = new Acceso();
		// Busqueda por código para cuentas de alumnos
		if(accesoDao.existeReg(usuario)) {
			acceso = accesoDao.mapeaRegId(usuario);			
		}else if (accesoDao.existeUsuario(usuario)){
			// Busqueda por usuario para cuentas de empleados 
			acceso = accesoDao.mapeaPorUsuario(usuario);
		}
		// Modificamos la clave del usuario colocando la cuenta de recuperación que le enviamos al correo
		if(acceso.getClaveInicial().equals(claveSha)) {
			accesoDao.updateClave(acceso.getCodigoPersonal(), claveSha);
			String claveHexa = bCryptPasswordEncoder.encode(clave);
			accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa);
		}
		
		codigoPersonal 								= accesoDao.getEsUsuario(usuario, claveSha);		
		if ( !codigoPersonal.equals("x")){
			if (acceso.getClaveHexa().equals("X")){
				String claveNueva = bCryptPasswordEncoder.encode(clave);
				accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveNueva);
			}
			Sesion sesionAca = new Sesion(); 
			// Crea una sesión o actualiza una existente.
			sesionAca.setSesionId(sesionId);
			if (sesionDao.existeReg(sesionId)){
				//sesionAca = sesionDao.mapeaRegId(sesionId);
				sesionDao.updateReg(sesionId);
			}else{
				sesionAca.setCodigoPersonal(codigoPersonal);
				sesionAca.setIp(ip);
				sesionDao.insertReg(sesionAca);
			}
			
			sesion.setAttribute("codigoPersonal", codigoPersonal );
			sesion.setAttribute("codigoLogeado", codigoPersonal );
			sesion.setAttribute("usuario", usuario);						
			sesion.setAttribute("admin", accesoDao.esAdministrador(codigoPersonal));
			sesion.setAttribute("lenguaje", accesoDao.getIdioma(codigoPersonal));			
			sesion.setAttribute("menu", accesoDao.getMenu(codigoPersonal));
			sesion.setAttribute("mapaModulos", mapaModulos);
			sesion.setAttribute("mapaOpciones", mapaOpciones);
			sesion.setAttribute("paginaBusca", "X");
			sesion.setAttribute("esMovil", "N");
			
			// Buscar el ejercicio actual
			String ejercicioId = contEjercicioDao.getEjercicioActual("001");
			sesion.setAttribute("ejercicioId", ejercicioId);
			String opcionesUsuario	= ""; 
					
			if (moduloDao.getEsEmpleado(codigoPersonal) || empMaestroDao.esMaestro(codigoPersonal)){
				
				// Agrega opciones por default			
				List<String> lista = new ArrayList<String>(Arrays.asList("290","291","111","081","000","990","991","993","1000","996"));
				// Si tiene acceso al portal del internado como preceptor o monitor
				if (intAccesoDao.tieneAcceso(codigoPersonal) > 0){
					lista.add("490");	
				}
				for (String opcion : lista) {
					if (accesoOpcionDao.existeReg(codigoPersonal, opcion)==false){
						AccesoOpcion accesoOpcion = new AccesoOpcion();
						accesoOpcion.setCodigoPersonal(codigoPersonal);
						accesoOpcion.setOpcionId(opcion);
						accesoOpcion.setUsuario("9800308");
						accesoOpcionDao.insertReg(accesoOpcion);
					}
				}
				
				sesion.setAttribute("portal", "empleado"); 				
				opcionesUsuario 	+= " 111 081 000 990 991 993 1000 996";				
				// Si es maestro se agrega el portal
				if (moduloDao.getEsMaestro(codigoPersonal)){
					opcionesUsuario += " PMA";
				}
			}else if(codigoPersonal.substring(0,1).equals("P")){
				sesion.setAttribute("portal", "padre");
			}else{				
				sesion.setAttribute("portal", "alumno");
				opcionesUsuario += " PAL";
			}
			
			lisMenu	  	= menuDao.getListUser(codigoPersonal, "");
			lisModulo 	= moduloDao.getListUser(codigoPersonal, "");
			lisOpcion 	= moduloOpcionDao.listUser(codigoPersonal, "");			
			carpetas	= moduloOpcionDao.mapCarpetas(codigoPersonal);
			opciones 	= moduloOpcionDao.getOpcUser(codigoPersonal) + opcionesUsuario;			
			
			boolean esMaestro 		= moduloDao.getEsMaestro(codigoPersonal);
			boolean esMentor		= moduloDao.getEsMentor(codigoPersonal);
			boolean esEmpleado		= moduloDao.getEsEmpleado(codigoPersonal);
			boolean esEditor		= moduloDao.esEditorDeContenidos(codigoPersonal);
			boolean esPadre 		= padrePersonalDao.esPadre(codigoPersonal);
			sesion.setAttribute("colorReloj", alumColorDao.getColorReloj(codigoPersonal));
			sesion.setAttribute("lisMenu", lisMenu);
			sesion.setAttribute("lisModulo", lisModulo);
			sesion.setAttribute("lisOpcion", lisOpcion);
			sesion.setAttribute("opciones", opciones);
			sesion.setAttribute("mapCarpetas",carpetas);
			sesion.setAttribute("portalMaestro", accesoDao.getPortalMaestro(codigoPersonal));
			sesion.setAttribute("portalAlumno", accesoDao.getPortalAlumno(codigoPersonal));
			sesion.setAttribute("codigoEmpleado", codigoPersonal );
			sesion.setAttribute("codigoAlumno", codigoPersonal);
			sesion.setAttribute("codigoUltimo", codigoPersonal );
			sesion.setAttribute("codigoPadre", codigoPersonal );
			sesion.setAttribute("colorTablas",alumColorDao.getColor(codigoPersonal));
			sesion.setAttribute("colorMenu",alumColorDao.getColorMenu(codigoPersonal));
			sesion.setAttribute("esMaestro",esMaestro);
			sesion.setAttribute("esMentor",esMentor);
			sesion.setAttribute("esEmpleado",esEmpleado);
			sesion.setAttribute("esPadre",esPadre);
			sesion.setAttribute("esEditor",esEditor);
			sesion.setAttribute("codigoPreceptor", codigoPersonal);		
			sesion.setAttribute("dormitorioId", "0");
			
			String cargaId = cargaDao.getActual(fecha.getFecha("1"));			
			sesion.setAttribute("cargaId", cargaId);
			sesion.setAttribute("periodo", cargaDao.getPeriodo(cargaId));
			sesion.setAttribute("ciclo", catPeriodoDao.getPeriodo());
			sesion.setAttribute("porPeriodo", porPeriodo);
			sesion.setAttribute("periodoBecas", becPeriodoDao.getPeriodoActual());
			sesion.setAttribute("bloqueId", "0");
			sesion.setAttribute("bloques", "1");			
			sesion.setAttribute("institucion", parametrosDao.getInstitucion("1"));
			sesion.setAttribute("ccosto", "0");
			sesion.setAttribute("planId", "0");
			sesion.setAttribute("carreraId", "0");
			sesion.setAttribute("cursoCargaId", "0");
			
			// Fechas de inicio y final para estadisticas
			sesion.setAttribute("fecha", aca.util.Fecha.getHoy());
			sesion.setAttribute("fechaIni", "01/02/2024");
			sesion.setAttribute("fechaFin", aca.util.Fecha.getHoy());
			sesion.setAttribute("modalidades", "'1'");
			sesion.setAttribute("cargas", "'000000'");
			sesion.setAttribute("datosOpcion","1");
			sesion.setAttribute("tramite","0");
			sesion.setAttribute("cicloId", "1");
			sesion.setAttribute("cerrarPortal", "N");
			if (accesoDao.getIngreso(codigoPersonal).equals("N")) {
				accesoDao.updateIngreso(codigoPersonal,"S");
			}
			
			AlumPersonal alumno 	= alumPersonalDao.mapeaRegId(codigoPersonal);
        	String claveDefault  	= String.valueOf(alumno.getApellidoPaterno().charAt(0)).toLowerCase()+String.valueOf(alumno.getApellidoMaterno().charAt(0)).toLowerCase()+String.valueOf(alumno.getNombre().charAt(0)).toLowerCase()+String.valueOf(codigoPersonal.charAt(5))+String.valueOf(codigoPersonal.charAt(6));
        	String claveEncriptada 	= aca.util.Encriptar.sha512ConBase64(claveDefault);
        	validaClave 			= accesoDao.validaClave(codigoPersonal, claveEncriptada);        	
        	avisoPrivacidad 		= accesoPrivacidadDao.existeReg(codigoPersonal);        	 			
			saltoPagina 			= "entrar";			
			if(accesoClaveDao.existeReg(codigoPersonal, 1)) {
				AccesoClave aClave 	= accesoClaveDao.mapeaRegIdFolio(codigoPersonal, 1);
				String fechaHoy 	= aca.util.Fecha.getHoy();
				int dias = 0;
				try {
					dias = aca.util.Fecha.diasEntreFechas(aClave.getFecha(), fechaHoy);
				}catch(Exception ex) {
					System.out.println("Error de fecha:"+ex);
				}					        		        
				if(dias >= 185) {
					saltoPagina = "cambiarPassword";
				}				
			}else {
				saltoPagina = "cambiarPassword";
			}
		}else{	 
			if (accesoDao.getValidaUsuario(usuario)==false){
				saltoPagina = "errorUsuario";
			}else if (accesoDao.getValidaClave(usuario,claveSha)==false){
				saltoPagina = "errorClave";
			}
		}
		
		 modelo.addAttribute("saltoPagina", saltoPagina);
		 modelo.addAttribute("validaClave", validaClave);
		 modelo.addAttribute("avisoPrivacidad", avisoPrivacidad);
		 modelo.addAttribute("avisoPrivacidad", avisoPrivacidad);
		 
		return "/valida";
	}	
	
	@RequestMapping(value={"/validaSecurity"})
	public String validaSecurity(HttpServletRequest request, Model modelo){		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
		
		List<String> lisRoles = new ArrayList<String>();
		for (GrantedAuthority grantedAuthority : grantedAuthorities){
			lisRoles.add(grantedAuthority.getAuthority());
		}
		String sesionId		= "";
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	sesionId 		= sesion.getId();
        	sesion.setMaxInactiveInterval(3200);
        }         
        
		List<Menu> lisMenu							= new ArrayList<Menu>();
		List<Modulo> lisModulo						= new ArrayList<Modulo>();
		List<ModuloOpcion> lisOpcion 				= new ArrayList<ModuloOpcion>();
		HashMap<String,String> carpetas 			= new HashMap<String,String>();		
		HashMap<String,Modulo> mapaModulos 			= moduloDao.mapModulos();
		HashMap<String,ModuloOpcion> mapaOpciones	= moduloOpcionDao.mapaOpciones();
		String porPeriodo							= aca.util.Fecha.getHoy().substring(6,10);
		aca.util.Fecha fecha						= new aca.util.Fecha();		
		boolean avisoPrivacidad						= false;	
		boolean existeUsuario						= false;
		String codigoPersonal						= "0";
		String ip									= request.getRemoteAddr();	
		String opciones								= "";
		String idiomaUsuario 						= "es";
		
		Acceso acceso = new Acceso();
		if (accesoDao.existeUsuario(auth.getName())){			 
			acceso 			= accesoDao.mapeaPorUsuario(auth.getName());
			codigoPersonal 	= acceso.getCodigoPersonal();
			idiomaUsuario 	= accesoDao.getIdioma(codigoPersonal);
			existeUsuario 	= true;			
		}			
		if ( existeUsuario ){
			
			Sesion sesionAca = new Sesion(); 
			// Crea una sesión o actualiza una existente.
			sesionAca.setSesionId(sesionId);
			if (sesionDao.existeReg(sesionId)){
				//sesionAca = sesionDao.mapeaRegId(sesionId);
				sesionDao.updateReg(sesionId);
			}else{
				sesionAca.setCodigoPersonal(codigoPersonal);
				sesionAca.setIp(ip);
				sesionDao.insertReg(sesionAca);
			}
			
			sesion.setAttribute("codigoPersonal", codigoPersonal );
			sesion.setAttribute("codigoLogeado", codigoPersonal );
			sesion.setAttribute("usuario", auth.getName());						
			sesion.setAttribute("admin", accesoDao.esAdministrador(codigoPersonal));
			sesion.setAttribute("lenguaje", idiomaUsuario);			
			sesion.setAttribute("menu", accesoDao.getMenu(codigoPersonal));
			sesion.setAttribute("mapaModulos", mapaModulos);
			sesion.setAttribute("mapaOpciones", mapaOpciones);
			sesion.setAttribute("paginaBusca", "X");
			sesion.setAttribute("esMovil", "N");
			context.setAttribute("verMonitor", parametrosDao.getMonitor("1"));		
			
			// Buscar el ejercicio actual
			String ejercicioId = contEjercicioDao.getEjercicioActual("001");
			sesion.setAttribute("ejercicioId", ejercicioId);
			String opcionesUsuario	= ""; 
					
			if (moduloDao.getEsEmpleado(codigoPersonal) || empMaestroDao.esMaestro(codigoPersonal)){
				
				// Agrega opciones por default			
				List<String> lista = new ArrayList<String>(Arrays.asList("290","291","111","081","000","990","991","993","1000","996"));
				// Si tiene acceso al portal del internado como preceptor o monitor
				if (intAccesoDao.tieneAcceso(codigoPersonal) > 0){
					lista.add("490");	
				}
				for (String opcion : lista) {
					if (accesoOpcionDao.existeReg(codigoPersonal, opcion)==false){
						AccesoOpcion accesoOpcion = new AccesoOpcion();
						accesoOpcion.setCodigoPersonal(codigoPersonal);
						accesoOpcion.setOpcionId(opcion);
						accesoOpcion.setUsuario("9800308");
						accesoOpcionDao.insertReg(accesoOpcion);
					}
				}
				
				sesion.setAttribute("portal", "empleado"); 				
				opcionesUsuario 	+= " 111 081 000 990 991 993 1000 996";				
				// Si es maestro se agrega el portal
				if (moduloDao.getEsMaestro(codigoPersonal)){
					opcionesUsuario += " PMA";
				}
			}else if(codigoPersonal.substring(0,1).equals("P")){
				sesion.setAttribute("portal", "padre");
			}else{				
				sesion.setAttribute("portal", "alumno");
				opcionesUsuario += " PAL";
			}
			
			lisMenu	  	= menuDao.getListUser(codigoPersonal, "");			
			lisModulo 	= moduloDao.getListUser(codigoPersonal, "");
			lisOpcion 	= moduloOpcionDao.listUser(codigoPersonal, "");			
			carpetas	= moduloOpcionDao.mapCarpetas(codigoPersonal);
			opciones 	= moduloOpcionDao.getOpcUser(codigoPersonal) + opcionesUsuario;			
			
			boolean esMaestro 		= moduloDao.getEsMaestro(codigoPersonal);
			boolean esMentor		= moduloDao.getEsMentor(codigoPersonal);
			boolean esEmpleado		= moduloDao.getEsEmpleado(codigoPersonal);
			boolean esEditor		= moduloDao.esEditorDeContenidos(codigoPersonal);
			boolean esPadre 		= padrePersonalDao.esPadre(codigoPersonal);
			sesion.setAttribute("colorReloj", alumColorDao.getColorReloj(codigoPersonal));
			sesion.setAttribute("lisMenu", lisMenu);
			sesion.setAttribute("lisModulo", lisModulo);
			sesion.setAttribute("lisOpcion", lisOpcion);
			sesion.setAttribute("opciones", opciones);
			sesion.setAttribute("mapCarpetas",carpetas);
			sesion.setAttribute("portalMaestro", accesoDao.getPortalMaestro(codigoPersonal));
			sesion.setAttribute("portalAlumno", accesoDao.getPortalAlumno(codigoPersonal));
			sesion.setAttribute("codigoEmpleado", codigoPersonal );
			sesion.setAttribute("codigoAlumno", codigoPersonal);
			sesion.setAttribute("codigoUltimo", codigoPersonal );
			sesion.setAttribute("codigoPadre", codigoPersonal );
			sesion.setAttribute("colorTablas",alumColorDao.getColor(codigoPersonal));
			sesion.setAttribute("colorMenu",alumColorDao.getColorMenu(codigoPersonal));
			sesion.setAttribute("esMaestro",esMaestro);
			sesion.setAttribute("esMentor",esMentor);
			sesion.setAttribute("esEmpleado",esEmpleado);
			sesion.setAttribute("esPadre",esPadre);
			sesion.setAttribute("esEditor",esEditor);
			sesion.setAttribute("codigoPreceptor", codigoPersonal);	
			sesion.setAttribute("dormitorioId", "0");
			sesion.setAttribute("menuPreceptor", "1");
			sesion.setAttribute("preceptores", intDormitorioDao.getPreceptores());
			sesion.setAttribute("rolInternado", intAccesoDao.getRolUsuairo(codigoPersonal));
			
			String cargaId = cargaDao.getActual(fecha.getFecha("1"));			
			sesion.setAttribute("cargaId", cargaId);
			sesion.setAttribute("periodo", cargaDao.getPeriodo(cargaId));
			sesion.setAttribute("ciclo", catPeriodoDao.getPeriodo());
			sesion.setAttribute("porPeriodo", porPeriodo);
			sesion.setAttribute("periodoBecas", becPeriodoDao.getPeriodoActual());
			sesion.setAttribute("bloqueId", "0");
			sesion.setAttribute("bloques", "1");			
			sesion.setAttribute("institucion", parametrosDao.getInstitucion("1"));
			sesion.setAttribute("ccosto", "0");
			sesion.setAttribute("planId", "0");
			sesion.setAttribute("carreraId", "0");
			sesion.setAttribute("cursoCargaId", "0");
			
			// Fechas de inicio y final para estadisticas
			sesion.setAttribute("fecha", aca.util.Fecha.getHoy());
			sesion.setAttribute("fechaIni", "01/01/2022");
			sesion.setAttribute("fechaFin", aca.util.Fecha.getHoy());
			sesion.setAttribute("modalidades", "'1'");
			sesion.setAttribute("cargas", "'000000'");
			sesion.setAttribute("datosOpcion","1");
			sesion.setAttribute("tramite","0");
			sesion.setAttribute("cicloId", "1");
			sesion.setAttribute("cerrarPortal", "N");	
			sesion.setAttribute("exaOpcion", "datos");			
		}	
			
		return "redirect:/inicio";
	}

	@RequestMapping("/salir")
	public String salir(HttpServletRequest request) {
		HttpSession sesion = request.getSession(false); // Get the session if it exists
		if (sesion != null) {
			String sesionId = sesion.getId();
			if (sesionDao.existeReg(sesionId)) {
				sesionDao.updateReg(sesionId);
				sesionDao.updateRegFinalizo(sesionId);
				// System.out.println("Session invalidated in DB");
			}
			// sesion.invalidate(); // Invalidate the session

		}
		// return "redirect:/login"; // Ensure this is the correct endpoint
		return "salir";
	}
	
	@RequestMapping("/cambiarPassword")
	public String cambiarPassword(HttpServletRequest request, Model modelo){
		HttpSession sesion	= null;		
		String cambioClave 	= request.getParameter("cambioClave") == null ? "1" : request.getParameter("cambioClave");
		String accion		= request.getParameter("accion") == null ? "1" : request.getParameter("accion");
		boolean guardado 	= false;
		
		aca.conecta.Conectar conectar 	= new aca.conecta.Conectar();
		aca.radius.Radcheck radcheck	= new aca.radius.Radcheck();
		
        sesion = ((HttpServletRequest)request).getSession();            
        if (sesion!=null){            
        
            String codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");   
    		    				
    		String contraant		= request.getParameter("contraant")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraant"));
    		String contraantMd5		= request.getParameter("contraant")==null?"-":aca.util.Encriptar.md5ConBase64(request.getParameter("contraant"));
    		String contraact		= request.getParameter("contraact")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraact"));
    		String claveHexa		= request.getParameter("contraact")==null?"-":bCryptPasswordEncoder.encode(request.getParameter("contraact"));
    		String contraact2		= request.getParameter("contraact2")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraact2"));
    		
    		Acceso acceso = new Acceso();	
    		
    		if(accion.equals("2")){			
    			acceso = accesoDao.mapeaRegId(codigoPersonal);
    			if(contraant.equals(acceso.getClave()) || contraantMd5.equals(acceso.getClave())){
    				if(contraact.equals(contraact2)){
    					if(!contraant.equals(contraact)){						
    						acceso.setClave(contraact);
    						accesoDao.updateClave(acceso.getUsuario(), acceso.getClave(), acceso.getCodigoPersonal());    		
    						accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa);
    						
							guardado=true;
							accion="3";
    							
							if(cambioClave.equals("1")){							
    							AccesoClave aClave = new AccesoClave();
    							aClave.setCodigoPersonal(codigoPersonal);
    							aClave.setFecha(aca.util.Fecha.getHoy());
    							aClave.setIp(request.getRemoteAddr());
    							aClave.setClave(contraact);
    							aClave.setFolio(1);
    							if(!accesoClaveDao.existeReg(codigoPersonal, 1)) {
    								accesoClaveDao.insertReg(aClave);					
    							}else {
    								accesoClaveDao.updateReg(aClave);					
    							}
    						}    							
    					}else accion = "7";
    				}else accion="4";
    			}else accion="5";
    		}
        }
		modelo.addAttribute("cambioClave", cambioClave); 
		modelo.addAttribute("guardado", guardado);
		modelo.addAttribute("accion", accion);
		
		return "cambiarPassword";
	}
	
	@RequestMapping("/head")
	public String head(HttpServletRequest request){		
		return "head";
	}
	
	@RequestMapping("/menu")
	public String menu(HttpServletRequest request){		
		return "menu";
	}
/*	
	@RequestMapping("/errores")
	public String errores(){
		return "errores";
	}
*/	
	@RequestMapping("/inicio")
	public String inicio(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		AlumPersonal alumno 	= new AlumPersonal();
		Opcion opcion 			= new Opcion();
		String cambiarPassword 	= "N";
		boolean existeAlumno	= false;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");                                    
            existeAlumno 		= alumPersonalDao.existeReg(codigoPersonal);
            
            // Trae el alumno	            
            if (existeAlumno){
            	alumno 			= alumPersonalDao.mapeaRegId(codigoPersonal);
            }            
            if (opcionDao.existeReg(codigoPersonal)){
            	opcion = opcionDao.mapeaRegId(codigoPersonal);
            }            
            
            if(accesoClaveDao.existeReg(codigoPersonal, 1)){
				AccesoClave accesoClave 	= accesoClaveDao.mapeaRegIdFolio(codigoPersonal, 1);				
				String fechaHoy 	= aca.util.Fecha.getHoy();
				int dias = 0;
				//System.out.println("Fecha:"+accesoClave.getFecha()+":"+codigoPersonal);
				try {
					dias = aca.util.Fecha.diasEntreFechas(accesoClave.getFecha(), fechaHoy);
				}catch(Exception ex) {
					System.out.println("Error de fecha:"+ex);
				}		
				if(dias > 183) {
					cambiarPassword= "S";
				}
            }            
		}
        
        modelo.addAttribute("existeAlumno", existeAlumno); 
        modelo.addAttribute("alumno", alumno);
        modelo.addAttribute("opcion", opcion);            	
        modelo.addAttribute("cambiarPassword", cambiarPassword);
        
		return "inicio";
	}	
	
	@RequestMapping("/imagen2")
	public String imagen2(HttpServletRequest request){
		return "imagen2";
	}
	
	@RequestMapping(value={"/empleado"})
	public String empleado(HttpServletRequest request, Model modelo){
		//long inicio = System.currentTimeMillis();
		String accion 				= request.getParameter("Accion")==null?"":request.getParameter("Accion");		
		Maestros	maestro			= new aca.vista.spring.Maestros();
		Mensaje 	mensaje			= new aca.mensaje.spring.Mensaje();
		EmpContacto empContacto		= new EmpContacto();
		String yearActual			= aca.util.Fecha.getHoy().split("/")[2];
		boolean existeRango			= false;
		
		String codigoPersonal 		= "0"; 
		String imss					= "";
		String curp					= "";
		String rfc					= "";
		String fechaAlta			= "";					
		String estado				= "";	
		String nombreMaestro 		= "";
		int totSalidasPen			= 0;
		boolean existeCovid 		= false;
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
        	totSalidasPen 			= salSolicitudDao.salidasPorEstado(codigoPersonal, "E");
        }
        if(maestroRangoDao.existeReg(codigoPersonal, yearActual)) {
			existeRango = true;			
		}
        
        if(alertaCovidDao.existeReg(codigoPersonal)) {
        	existeCovid = true;
        }
        
        if (empContactoDao.existeReg(codigoPersonal)) {
        	empContacto = empContactoDao.mapeaRegId(codigoPersonal);
        }
        		
        int totDocumentos		= empDocEmpDao.totDocConImagen(codigoPersonal);
        		
        mensaje.setCodigoPersonal(codigoPersonal);
		if(accion.equals("1")){
			mensajeDao.insertReg(mensaje);
		}		
        maestro  		= maestrosDao.mapeaRegId(codigoPersonal);
        imss 			= empleadoDao.getIMSS(codigoPersonal);
        curp 			= empleadoDao.getCurp(codigoPersonal);
        rfc				= empleadoDao.getRFC(codigoPersonal);
        fechaAlta		= empleadoDao.getFechaAlta(codigoPersonal);        
        estado 			= maestro.getEstado();
        nombreMaestro 	= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno();
        
        modelo.addAttribute("imss", imss);
        modelo.addAttribute("curp", curp);        
        modelo.addAttribute("rfc", rfc);
        modelo.addAttribute("fechaAlta", fechaAlta);
        modelo.addAttribute("estado", estado);
        modelo.addAttribute("existeCovid", existeCovid);
        modelo.addAttribute("nombreMaestro", nombreMaestro);
        modelo.addAttribute("salidasPendientes", totSalidasPen);
        modelo.addAttribute("totDocumentos", totDocumentos);
        modelo.addAttribute("empContacto", empContacto);
        modelo.addAttribute("existeRango", existeRango);
         
		return "empleado";
	}
	
	@RequestMapping(value={"/validaEmpleadoMovil"})
	public String validaEmpleadoMovil(HttpServletRequest request, Model modelo){
		String mensaje = request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		String codigoEmpleado = "0";		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
		}		
		boolean existeCodigo	= false;
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/user/v1.0/validate-username";
		
		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");	
		// Body
		Map<String, Object> map = new HashMap<>();
		map.put("username", codigoEmpleado+"@um.movil");		
		
		 // Data attached to the request.
        HttpEntity<Map<String,Object>> requestBody = new HttpEntity<>(map, headers);
        
        // Send request with POST method.
        ResponseEntity<String> result  = restTemplate.postForEntity(url, requestBody, String.class);
        
        if (result.getStatusCodeValue()==200) {
        	if (result.getBody().contains("60002")){
        		existeCodigo = true;
        	}
        }       
        
        String nombreEmpleado = empleadoDao.getNombreEmpleado(codigoEmpleado, "NOMBRE");
        
        modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("existeCodigo", existeCodigo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "validaEmpleadoMovil";
	}
	
	@RequestMapping(value={"/registrarEmpleadoMovil"})
	public String registrarEmpleadoMovil(HttpServletRequest request, Model modelo){
		Maestros empleado = new Maestros();
		
		String password 	= request.getParameter("Pass")==null?"0":request.getParameter("Pass");
		String mensaje		= "0";
		String codigoEmpleado = "0";		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
		}	
		
		if(maestrosDao.existeReg(codigoEmpleado)) {
			empleado = maestrosDao.mapeaRegId(codigoEmpleado);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/user/v1.0/me";
		
		User usuario = new User();
		List<Claims> claims = new ArrayList<Claims>();
		
		claims.add(new Claims("http://wso2.org/claims/givenname",empleado.getNombre()));
		claims.add(new Claims("http://wso2.org/claims/emailaddress",empleado.getEmail()));
		claims.add(new Claims("http://wso2.org/claims/lastname",empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno()));
		claims.add(new Claims("http://wso2.org/claims/userid",codigoEmpleado));
		
		usuario.setUsername(codigoEmpleado);
		usuario.setRealm("PRIMARY");
		usuario.setPassword(password);
		usuario.setClaims(claims);
		
		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");	
		// Body
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("user", usuario);	
		
		// Data attached to the request.
		HttpEntity<Map<String,Object>> requestBody = new HttpEntity<>(map, headers);
		
		// Send request with POST method.
		ResponseEntity<String> result  = restTemplate.postForEntity(url, requestBody, String.class);

		if (result.getStatusCodeValue() != 201) {
			mensaje = "1";
		} else if (result.getStatusCodeValue() == 201) {
			mensaje = "3";
		}
		
		return "redirect:/validaEmpleadoMovil?Mensaje="+mensaje;
	}
	
	@RequestMapping("/cambiarPasswordEmpledoMovil")
	public String portalesAlumnoCambiarPasswordMovil(HttpServletRequest request, Model modelo){
		String password		= request.getParameter("Pass")==null?"0":request.getParameter("Pass");
		//String confirm		= request.getParameter("Confirm")==null?"0":request.getParameter("Confirm");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String llave 		= "";	
		
		String codigoEmpleado 	= "0";		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {			
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
		}	
		
		UserPas usuario = new UserPas();
		usuario.setUsername(codigoEmpleado);
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/recovery/v0.9/recover-password?type=email&notify=false";
		
		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("user", usuario);			
		
		 // Data attached to the request.
        HttpEntity<Map<String,Object>> requestBody = new HttpEntity<>(map, headers);
        
        // Send request with POST method.
        ResponseEntity<String> result  = restTemplate.postForEntity(url, requestBody, String.class);         
        
        if (result.getStatusCodeValue()==202) {
        	llave = result.getBody();
        }   

        url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/recovery/v0.9/set-password";
        
        // Headers
 		headers = new HttpHeaders();
 		headers.setContentType(MediaType.APPLICATION_JSON);
 		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");	
 		// Body
 		map = new HashMap<>();
 		map.put("key", llave);		
 		map.put("password", password);		
 		
 		 // Data attached to the request.
         requestBody = new HttpEntity<>(map, headers);
         
         // Send request with POST method.
         result  = restTemplate.postForEntity(url, requestBody, String.class);
         
         if (result.getStatusCodeValue()!=200) {
     		mensaje = "2";
         }else if (result.getStatusCodeValue()==200) {
        	 mensaje = "3";
         }
        
		return "redirect:/validaEmpleadoMovil?Mensaje="+mensaje;
	}
	
	@RequestMapping("/grabarContacto")
	public String grabarContacto(HttpServletRequest request){
		String codigoPersonal 	= "0";
		String correo 			= request.getParameter("Correo")==null?"-":request.getParameter("Correo");
		String telefono 		= request.getParameter("Telefono")==null?"-":request.getParameter("Telefono");
		EmpContacto contacto 	= new EmpContacto();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
        	contacto.setEmpleadoId(codigoPersonal);
    		contacto.setCorreo(correo);
    		contacto.setTelefono(telefono);    		
        	if (empContactoDao.existeReg(codigoPersonal)){       		
        		// Update        		
        		empContactoDao.updateReg(contacto);       		        		
        	}else{
        		// Insert       		
        		empContactoDao.insertReg(contacto);       		
        	}
        }        
		return "redirect:/empleado";
	}
	
	@RequestMapping(value={"/varianza"})
	public String varianza(HttpServletRequest request, Model modelo){		
		
         
		return "varianza";
	}	

	
	@RequestMapping("/encuestaCovid")
	public String encuestaCovid(HttpServletRequest request, Model modelo){
		
		String mensaje 			= request.getParameter("Mensaje") == null ? "-" : request.getParameter("Mensaje");
		String codigoPersonal	= "0";		

		
		Empleado empleado 		= new Empleado();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	empleado		= empleadoDao.mapeaRegClave(codigoPersonal);
        }
        
        
        AlertaCovid alertaCovid = new AlertaCovid();
		alertaCovid.setPaisUno("91");
		alertaCovid.setPaisDos("91");
		
		String folio = alertaCovidDao.ultimoFolio(codigoPersonal);
		if(alertaCovidDao.existeReg(codigoPersonal)) {
			alertaCovid = alertaCovidDao.mapeaRegIdFolio(codigoPersonal, folio);		
			if(!alertaCovid.getContactoFecha().equals("01/01/2000")) {
				alertaCovid.setContactoFecha("");
			}
		}
		
		List<CatPais> listaPais = catPaisDao.getListAll("NOMBRE_PAIS");
		
        modelo.addAttribute("empleado", empleado);        
        modelo.addAttribute("mensaje", mensaje);  
        modelo.addAttribute("listaPais", listaPais);
        modelo.addAttribute("alertaCovid", alertaCovid);
		
		return "encuestaCovid";
	}
	
	@RequestMapping("/grabarCovid")
	public String grabarCovid(HttpServletRequest request, Model modelo) {
		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"-":request.getParameter("CodigoPersonal");
		String paisUno 			= request.getParameter("PaisUno")==null?"-":request.getParameter("PaisUno");
		String paisDos 			= request.getParameter("PaisDos")==null?"-":request.getParameter("PaisDos");
		String ciudadUno		= request.getParameter("CiudadUno")==null?"-":request.getParameter("CiudadUno");
		String ciudadDos		= request.getParameter("CiudadDos")==null?"-":request.getParameter("CiudadDos");
		String fechaUno 		= request.getParameter("FechaUno")==null?"-":request.getParameter("FechaUno");
		String fechaDos			= request.getParameter("FechaDos")==null?"-":request.getParameter("FechaDos");
		String contacto			= request.getParameter("Contacto")==null?"-":request.getParameter("Contacto");
		String fechaContacto	= request.getParameter("FechaContacto")==null?"":request.getParameter("FechaContacto");
		String fiebre 			= request.getParameter("Fiebre")==null?"-":request.getParameter("Fiebre");
		String tos	 			= request.getParameter("Tos")==null?"-":request.getParameter("Tos");
		String cabeza 			= request.getParameter("Cabeza")==null?"-":request.getParameter("Cabeza");
		String respirar 		= request.getParameter("Respirar")==null?"-":request.getParameter("Respirar");
		String garganta 		= request.getParameter("Garganta")==null?"-":request.getParameter("Garganta");
		String escurrimiento 	= request.getParameter("Escurrimiento")==null?"-":request.getParameter("Escurrimiento");
		String olfato 			= request.getParameter("Olfato")==null?"-":request.getParameter("Olfato");
		String gusto 			= request.getParameter("Gusto")==null?"-":request.getParameter("Gusto");
		String cuerpo 			= request.getParameter("Cuerpo")==null?"-":request.getParameter("Cuerpo");
		String folio			= "1";
		String mensaje 			= "0";
		
		AlertaCovid alertaCovid = new AlertaCovid();
		
		if(fechaContacto.equals("")) {
			fechaContacto = "01/01/2000";
		}
		
		alertaCovid.setCodigoPersonal(codigoPersonal);
		alertaCovid.setPaisUno(paisUno);
		alertaCovid.setPaisDos(paisDos);
		alertaCovid.setCiudadUno(ciudadUno);
		alertaCovid.setCiudadDos(ciudadDos);
		alertaCovid.setFechaUno(fechaUno);
		alertaCovid.setFechaDos(fechaDos);
		alertaCovid.setContacto(contacto);
		alertaCovid.setContactoFecha(fechaContacto);
		alertaCovid.setFiebre(fiebre);
		alertaCovid.setTos(tos);
		alertaCovid.setCabeza(cabeza);
		alertaCovid.setRespirar(respirar);
		alertaCovid.setGarganta(garganta);
		alertaCovid.setEscurrimiento(escurrimiento);
		alertaCovid.setOlfato(olfato);
 		alertaCovid.setGusto(gusto);
 		alertaCovid.setCuerpo(cuerpo);
 		if(alertaCovidDao.existeReg(codigoPersonal)) {
			folio = alertaCovidDao.maximoFolio(codigoPersonal);
			alertaCovid.setFolio(folio);
			alertaCovidDao.insertReg(alertaCovid);
			mensaje = "1";
		}else {
			alertaCovid.setFolio(folio);
			if(alertaCovidDao.insertReg(alertaCovid)){
				mensaje = "1";
			}
		}
		
		return "redirect:/resEncuesta?Mensaje="+mensaje;
	}
	
	@RequestMapping("/resEncuesta")
	public String resEncuesta(HttpServletRequest request, Model modelo) {
		String codigoPersonal 	= "0";
		String folio			=  "1"; 
		Empleado empleado 		= new Empleado();
		
		
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	empleado		= empleadoDao.mapeaRegClave(codigoPersonal);
        	folio			= alertaCovidDao.ultimoFolio(codigoPersonal);
		}
		NotiCovid notiCovid = new NotiCovid();
		
		notiCovid = alertaCovidDao.evaluaRespuesta(codigoPersonal, folio);
		
		modelo.addAttribute("empleado", empleado);
		modelo.addAttribute("notiCovid", notiCovid);
		
		return "/resEncuesta";
	}
	
	@RequestMapping("/grabarEncuesta")
	public String grabarEncuesta(HttpServletRequest request){
			
		String codigoPersonal 	= "0";
		String periodoId		= String.valueOf(covidPeriodoDao.getActual());
		String mensaje 			= "-";
		Covid encuesta 			= new Covid();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
			encuesta.setCodigoPersonal(codigoPersonal);
			encuesta.setPeriodoId(periodoId);
			encuesta.setHipertension(request.getParameter("Hipertension")==null?"N":request.getParameter("Hipertension")); 
			encuesta.setDiabetes(request.getParameter("Diabetes")==null?"N":request.getParameter("Diabetes"));			
			encuesta.setCorazon(request.getParameter("Corazon")==null?"N":request.getParameter("Corazon"));
			encuesta.setPulmon(request.getParameter("Pulmon")==null?"N":request.getParameter("Pulmon"));
			encuesta.setCancer(request.getParameter("Cancer")==null?"N":request.getParameter("Cancer"));
			encuesta.setObesidad(request.getParameter("Obesidad")==null?"N":request.getParameter("Obesidad"));
			encuesta.setEstres(request.getParameter("Estres")==null?"N":request.getParameter("Estres"));
			encuesta.setDepresion(request.getParameter("Depresion")==null?"N":request.getParameter("Depresion"));
			encuesta.setImss(request.getParameter("Imss")==null?"N":request.getParameter("Imss"));
			encuesta.setPase(request.getParameter("Pase")==null?"N":request.getParameter("Pase"));
			encuesta.setIsste(request.getParameter("Isste")==null?"N":request.getParameter("Isste"));
			encuesta.setHlc(request.getParameter("Hlc")==null?"N":request.getParameter("Hlc"));
			encuesta.setPrivado(request.getParameter("Privado")==null?"N":request.getParameter("Privado"));
			encuesta.setOtro(request.getParameter("Otro")==null?"N":request.getParameter("Otro"));
			encuesta.setNinguno(request.getParameter("Ninguno")==null?"N":request.getParameter("Ninguno"));
			
			if(covidDao.existe(codigoPersonal, periodoId) ){	
				if (covidDao.updateReg(encuesta)) {
					mensaje = "Modificado...";
				}else {
					mensaje = "Error al modificar...";
				}
			}else {
				if (covidDao.insertReg(encuesta)) {
					mensaje = "Grabado...";
				}else {
					mensaje = "Error al grabar...";	
				}
			}				
		}
		
		return "redirect:/resEncuesta?Mensaje="+mensaje;
	}
	
	
	
	@RequestMapping(value={"/docEmp","/academico/docEmp"})
	public String docEmp(HttpServletRequest request, Model modelo){
		String codigoEmpleado	= "0";
		String nombreEmpleado	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	= (String)sesion.getAttribute("codigoEmpleado");
        }
        nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
        List<aca.emp.spring.EmpDocumento> lisDocumentos		= empDocumentoDao.lisEmpleado(codigoEmpleado, " ORDER BY ORDEN");
        HashMap<String, String> mapaImagenes				= empDocEmpDao.mapaImagenes(codigoEmpleado);
        
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("mapaImagenes",mapaImagenes);		
         
		return "docEmp";
	}
	
	@RequestMapping("/verDoc")
	public String horasDocempNuevo(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= "0";
		String codigoPersonal   = "0";
		String nombreEmpleado	= "-";
		String nombreUsuario	= "-";
		String nombreDocumento	= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 		= (String)sesion.getAttribute("codigoEmpleado");
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        }
        
        EmpDocEmp docEmp 	= new EmpDocEmp();
        byte imagenByte[] 		= null;
        
        String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
        String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
        boolean tieneImagen		= false;        
        		
        nombreEmpleado 			= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");        
        nombreDocumento			= empDocumentoDao.getDocumentoNombre(documentoId);
        
        // Buscar la imagen
        if (empDocEmpDao.existeImagen(codigoEmpleado, documentoId, hoja)){
        	tieneImagen = true;
        	docEmp = empDocEmpDao.mapeaRegId(codigoEmpleado, documentoId, hoja);
        	imagenByte = docEmp.getImagen();
        	nombreUsuario 			= usuariosDao.getNombreUsuario(docEmp.getUsuario(), "NOMBRE");
        }else{
        	docEmp = empDocEmpDao.mapeaRegId("0", "0", "0");
        	imagenByte = docEmp.getImagen();
        }
        
        List<aca.emp.spring.EmpDocumento> lisDocumentos		= empDocumentoDao.lisTodos(" ORDER BY ORDEN");       
        
        modelo.addAttribute("codigoEmpleado",codigoEmpleado);
        modelo.addAttribute("codigoPersonal",codigoPersonal);
        modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("nombreDocumento",nombreDocumento);
		modelo.addAttribute("tieneImagen",tieneImagen);
		modelo.addAttribute("imagenByte",imagenByte);
		modelo.addAttribute("docEmp",docEmp);
		
		return "verDoc";
	}
	
	@RequestMapping("/aca")
	public String aca(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal 		= "0";
		boolean esEmpleado			= false;
		boolean esColaborador		= false;
		boolean esMaestro 			= false;		
		boolean esMentor			= false;
		boolean esEmpleadoValida 	= false;
		int tieneAcceso				= 0;
		String nombreCorto			= "";
		
		Mensaje mensaje 			= new Mensaje();
		boolean existeMensaje		= false;
		boolean esEditor			= false;		 
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
           	codigoPersonal = (String)sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");          
           	esEmpleado 		= moduloDao.getEsEmpleado(codigoPersonal);
           	esColaborador 	= moduloDao.getEsColaborador(codigoPersonal);
           	esMaestro 		= cargaGrupoDao.esMaestro(codigoPersonal);
           	esMentor 		= moduloDao.getEsMentor(codigoPersonal);
           	tieneAcceso 	= intAccesoDao.tieneAcceso(codigoPersonal);
           	esEditor		= moduloDao.esEditorDeContenidos(codigoPersonal);        
           	if (esEmpleado || esMaestro){
        		esEmpleadoValida = true;
        	}        
           	nombreCorto 	= usuariosDao.getNombreUsuarioCorto(codigoPersonal);
           	if (mensajeDao.existeReg(codigoPersonal)) {
           		existeMensaje 	= true;
           		mensaje 		= mensajeDao.mapeaRegId(codigoPersonal);
           	}         	            	
		}
        String colorUsuario 	= alumColorDao.getColor(codigoPersonal);
        
        modelo.addAttribute("colorUsuario", colorUsuario);        
        modelo.addAttribute("esEmpleado", esEmpleado);
        modelo.addAttribute("esColaborador", esColaborador);
        modelo.addAttribute("esMaestro", esMaestro);
        modelo.addAttribute("esMentor", esMentor);
        modelo.addAttribute("tieneAcceso", tieneAcceso);        
		modelo.addAttribute("esEmpleadoValida", esEmpleadoValida); 
		modelo.addAttribute("nombreCorto", nombreCorto);
		modelo.addAttribute("mensaje", mensaje);		
		modelo.addAttribute("existeMensaje", existeMensaje);
		modelo.addAttribute("esEditor", esEditor);		
		
		return "aca";
	}
	
	@RequestMapping("/menuUno")
	public String menuUno(HttpServletRequest request, Model modelo){	
		
		
		boolean esEmpleado			= false;
		boolean esMaestro 			= false;		
		boolean esMentor			= false;
		boolean esEditor			= false;
		int tieneAcceso				= 0;
		String nombreCorto			= "-";		
		String colorUsuario			= "default";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
           	String codigoPersonal = (String)sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
           	esEmpleado 		= moduloDao.getEsEmpleado(codigoPersonal);	
           	esMaestro 		= cargaGrupoDao.esMaestro(codigoPersonal);
           	esMentor 		= moduloDao.getEsMentor(codigoPersonal);
           	tieneAcceso 	= intAccesoDao.tieneAcceso(codigoPersonal);
           	esEditor		= moduloDao.esEditorDeContenidos(codigoPersonal);           	       
           	nombreCorto 	= usuariosDao.getNombreUsuarioCorto(codigoPersonal);
           	colorUsuario 	= alumColorDao.getColor(codigoPersonal);
		}
		
        modelo.addAttribute("esEmpleado", esEmpleado);
        modelo.addAttribute("esMaestro", esMaestro);
        modelo.addAttribute("esMentor", esMentor);
        modelo.addAttribute("tieneAcceso", tieneAcceso);		 
		modelo.addAttribute("nombreCorto", nombreCorto);		
		modelo.addAttribute("esEditor", esEditor);
		modelo.addAttribute("colorUsuario", colorUsuario);
		
		return "menuUno";
	}
	
	@RequestMapping("/menuDos")
	public String menuDos(HttpServletRequest request, Model modelo){	
		
		String menuId  		= request.getParameter("menu")==null?"0":request.getParameter("menu");
		String menuNombre	= menuDao.menuNombre(menuId);
		
		modelo.addAttribute("menuNombre", menuNombre);
		
		return "menuDos";
	}
	
	@RequestMapping("/menuTres")
	public String menuTres(HttpServletRequest request, Model modelo){
		
		String moduloId  	= request.getParameter("modulo")==null?"0":request.getParameter("modulo");
		String moduloNombre	= moduloDao.moduloNombre(moduloId);
		
		HashMap<String, Modulo> mapaModulos = moduloDao.mapModulos();	 
		
		modelo.addAttribute("moduloNombre", moduloNombre);
		modelo.addAttribute("mapaModulos", mapaModulos);
		
		return "menuTres";
	}
	
	@RequestMapping("/contenido")
	public String contenido(HttpServletRequest request, Model modelo){
		
		String accion		= request.getParameter("accion")==null?"0":request.getParameter("accion");
		boolean guardado 	= false;		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();      
        if (sesion!=null){            
        
            String codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal"); 
    		    				
    		String contraant		= request.getParameter("contraant")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraant"));
    		String contraact		= request.getParameter("contraact")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraact"));
    		String claveHexa		= request.getParameter("contraact")==null?"-":bCryptPasswordEncoder.encode(request.getParameter("contraact"));
    		String contraact2		= request.getParameter("contraact2")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("contraact2"));
    		
    		Acceso acceso = new Acceso();    		
    		if(request.getParameter("contraact") != null) {
    			if(request.getParameter("contraact").contains("+")) {
    				accion = "8";
    			}
    		}
    		
    		if(accion.equals("2")){			
    			acceso = accesoDao.mapeaRegId(codigoPersonal);
    			if(contraant.equals(acceso.getClave())){
    				if(contraact.equals(contraact2)){
    					if(!contraant.equals(contraact)){						
    						acceso.setClave(contraact);
    						accesoDao.updateClave(acceso.getUsuario(), acceso.getClave(), acceso.getCodigoPersonal()); 
    						accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa);
							guardado=true;
							accion="3";
    							
    					}else accion = "7";
    				}else accion="4";
    			}else accion="5";
    		}
        }
        
		modelo.addAttribute("guardado", guardado);
		modelo.addAttribute("accion", accion);
		
		return "contenido";
	}
	
	@RequestMapping("/ayuda")
	public String ayuda(HttpServletRequest request, Model modelo){		
		
		Integer accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
		String matricula			= request.getParameter("matricula")==null?"0":request.getParameter("matricula");		
		ModuloAyuda ayuda			= new ModuloAyuda();
		Maestros maestro			= new Maestros();
		PadrePersonal padre			= new PadrePersonal();
		AlumPersonal alumPersonal	= new AlumPersonal();
		AlumAcademico alumAcademico	= new AlumAcademico();
		String carrera				= "-";
		String religion				= "-";
		String edad					= "0";
		String inscrito				= "-";
		
		switch(accion) {
			case 5:{
				String opcionId 	= request.getParameter("opcion")==null?"0":request.getParameter("opcion");
				String ayudaId		= request.getParameter("ayuda")==null?"0":request.getParameter("ayuda");
				ayuda = moduloAyudaDao.mapeaRegId(opcionId, ayudaId);
				break;
			}
			case 6:{			
							
				if(maestrosDao.existeReg(matricula)){
					//BUSCA EMPLEADO					
					maestro = maestrosDao.mapeaRegId(matricula);	
				}else if (matricula.substring(0,1).equals("P")) {
					//BUSCA PADRE
					padre = padrePersonalDao.mapeaRegId(matricula);					
				}else {
					//BUSCA ALUMNO
					alumPersonal 		= alumPersonalDao.mapeaRegId(matricula);
					alumAcademico		= alumAcademicoDao.mapeaRegId(matricula);					
					religion 			= catReligionDao.getNombreReligion(alumPersonal.getReligionId());
					edad				= String.valueOf(alumPersonalDao.getEdad(matricula));
					inscrito			= inscritosDao.existeReg(matricula)?"Inscrito":"No Inscrito";
				}
				break;
			}
		}
		
		modelo.addAttribute("ayuda", ayuda); 
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("padre", padre);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("religion", religion);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("inscrito", inscrito);
		
		return "ayuda";
	}
	
	@RequestMapping("/busca")
	public String busca(HttpServletRequest request, Model modelo){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String codigoBusqueda	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		
		String usuarioSesion	= "0";
		String codigoPersonal 	= "0";
		String codigoOriginal	= "0";
		String portal 			= "0";
		String colorMenu		= "0";
		String colorResultado	= "";
		String colorOver		= "";
		String tipoUsuario		= "0";
		String mensaje	 		= "NoExiste";
		String nombreUsuario 	= "-";		
		Usuarios usuario 		= new Usuarios();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			usuarioSesion		= (String)sesion.getAttribute("usuario");
			codigoPersonal 		= (String)(sesion.getAttribute("codigoPersonal"));
			codigoOriginal		= (String)(sesion.getAttribute("codigoLogeado"));
			portal 				= (String)sesion.getAttribute("portal");
			colorMenu			= ((String)sesion.getAttribute("colorMenu")).toUpperCase();
		}
		
		@SuppressWarnings("unchecked")
		List<Modulo> listModulo	= (List<Modulo>) sesion.getAttribute("lisModulo");
		
		if(colorMenu.equals("DEFAULT") || colorMenu.equals("")){		
			colorOver = "#A886BC";
			colorResultado = "#F8EEF8";
		}else{
			colorResultado	= alumColorDao.modificarColor(colorMenu, 130);
			colorOver	= alumColorDao.modificarColor(colorMenu, 60);
		}
		
		/** BUSQUEDA POR CODIGO */
		if( accion.equals("1") && ( codigoBusqueda.charAt(0) == 'P' || listModulo.size() > 0 || codigoOriginal.equals("9800308") || codigoOriginal.equals("9800706") || codigoOriginal.equals("9800058") )){			
			if(codigoBusqueda.length() == 10){
				codigoBusqueda = alumPersonalDao.getCodigoCredencial(codigoBusqueda);
			}
			
			if (usuariosDao.existeReg(codigoBusqueda) ){
				
				usuario 		= usuariosDao.mapeaRegId(codigoBusqueda);
				nombreUsuario 	= usuario.getNombre().replaceAll("'", "´")+" "+usuario.getApellidoMaterno().replace("'","´")+" "+usuario.getApellidoPaterno().replaceAll("'", "´");
				if( maestrosDao.existeReg(usuario.getCodigoPersonal()) ){					
					if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
						sesion.setAttribute("codigoEmpleado", codigoBusqueda);
					}
					sesion.setAttribute("codigoUltimo", codigoBusqueda);
					sesion.setAttribute("portal", "empleado");
					tipoUsuario 	= "Empleado";
					mensaje			= "Existe";
					
				}else if (padrePersonalDao.existeReg(usuario.getCodigoPersonal())){					
					if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
						sesion.setAttribute("codigoPadre", usuario.getCodigoPersonal());						
					}
					sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
					sesion.setAttribute("portal", "padre");
					tipoUsuario 	= "Padre";
					mensaje 		= "Existe";			
					
				}else{					
					if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
						sesion.setAttribute("codigoAlumno", usuario.getCodigoPersonal());
					}
					sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
					if (maestrosDao.existeReg(codigoPersonal)) {
						sesion.setAttribute("portal", "empleado");
					}else{
						sesion.setAttribute("portal", "alumno");
					}
					
					tipoUsuario = "Alumno";
					mensaje		= "Existe";	
				}
				
			}else{
				tipoUsuario = "0";
				mensaje 	= "NoExiste";				
			}	
			
		//**BUSQUEDA POR NOMBRE */	
		}else if(accion.equals("2") && ( listModulo.size() > 0 || codigoOriginal.equals("9800308") || codigoOriginal.equals("9800706") || codigoOriginal.equals("9800058") )){			
			String frase = request.getParameter("frase")==null?"":request.getParameter("frase");
			
			if(frase.trim().equals("")){
				tipoUsuario 		= "0";
				mensaje 		= "NoExiste";
			}else{
				frase = frase.replaceAll(" ","%");			
				List<Usuarios> lisUsuarios = usuariosDao.getListAll("WHERE UPPER(NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO) LIKE '%'||UPPER(TRANSLATE('"+frase+"','ÁÉÍÓÚáéíóú', 'AEIOUaeiou'))||'%' ORDER BY NOMBRE||APELLIDO_PATERNO||APELLIDO_MATERNO");
				
				if(lisUsuarios.size() > 0){
					
					if(lisUsuarios.size() == 1){
						
						mensaje = "Existe";
						usuario = (Usuarios) lisUsuarios.get(0);
						nombreUsuario = usuario.getNombre().replaceAll("'", "´")+" "+usuario.getApellidoMaterno().replace("'","´")+" "+usuario.getApellidoPaterno().replaceAll("'", "´");
						
						if( maestrosDao.existeReg(usuario.getCodigoPersonal())){
							if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
								sesion.setAttribute("codigoEmpleado", usuario.getCodigoPersonal());
							}
							sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
							sesion.setAttribute("portal", "empleado");
							tipoUsuario 	= "Empleado";								
						}else if(padrePersonalDao.existeReg(usuario.getCodigoPersonal())){
							if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
								sesion.setAttribute("codigoPadre", usuario.getCodigoPersonal());
							}
							sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
							sesion.setAttribute("portal", "padre");
							tipoUsuario = "Padre";													
						}else{
							if (accesoDao.mapeaRegId(codigoPersonal).getBuscaAdmin().equals("S")){
								sesion.setAttribute("codigoAlumno", usuario.getCodigoPersonal());
							}
							sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
							if (maestrosDao.existeReg(codigoPersonal)) {
								sesion.setAttribute("portal", "empleado");
							}else{
								sesion.setAttribute("portal", "alumno");
							}							
							tipoUsuario = "Alumno";							
						}
						
					}else if(lisUsuarios.size() < 120){
						
						tipoUsuario = "Elegir";
						mensaje = "<table width=\"100%\">";
						for(int i = 0; i < lisUsuarios.size(); i++){ 
							usuario = (Usuarios) lisUsuarios.get(i);
							String planId = alumPlanDao.getPlanActual(usuario.getCodigoPersonal());
							mensaje += "<tr style=\"cursor:pointer;\" onmouseover=\"this.style.backgroundColor=\\'#E6E6E6\\';ayudaMouseOver(event);\" onmouseout=\"this.style.backgroundColor=\\'white\\';ayudaMouseOut();\">";
							if (codigoOriginal.equals("9800308")){
								mensaje +="<td style=\"border-bottom:solid 1px #D8D8D8; margin-top:-10px;\"><a href=\"javascript:cambiaCodigoPersonal(\\'"+usuario.getCodigoPersonal()+"\\');\" style=\"text-decoration:none;\">o</a></td>";
							}
							mensaje +=    "<td style=\"border-bottom: solid 1px #D8D8D8;\" onclick=\"busca(\\'"+usuario.getCodigoPersonal()+"\\');\" class=\"ayuda alumno "+usuario.getCodigoPersonal()+"\">"+usuario.getCodigoPersonal()+"</td>"+
											"<td style=\"border-bottom: solid 1px #D8D8D8;\" onclick=\"busca(\\'"+usuario.getCodigoPersonal()+"\\');\" class=\"ayuda alumno "+usuario.getCodigoPersonal()+"\">"+usuario.getNombre().replaceAll("'","´")+" "+usuario.getApellidoPaterno().replaceAll("'","´")+" "+usuario.getApellidoMaterno().replaceAll("'","´")+"</td>"+
											"<td style=\"border-bottom: solid 1px #D8D8D8;\" onclick=\"busca(\\'"+usuario.getCodigoPersonal()+"\\');\" class=\"ayuda alumno "+usuario.getCodigoPersonal()+"\">"+planId+"</td>"+
										 "</tr>";
						}
						mensaje += "</table>";
					}else{
						mensaje = "Lleno";
					}
				}else{					
					mensaje = "Vacio";
				}
			}
			
		}else if(accion.equals("3") && (codigoOriginal.equals("9800308") || codigoOriginal.equals("9800706") || codigoOriginal.equals("9800058") )){			
			String matricula 	= request.getParameter("matricula");			
			if (usuariosDao.existeReg(matricula)) {
				usuario 		= usuariosDao.mapeaRegId(matricula);
				nombreUsuario 	= usuario.getNombre().replaceAll("'", "´")+" "+usuario.getApellidoMaterno().replace("'","´")+" "+usuario.getApellidoPaterno().replaceAll("'", "´");
				List<Menu> lisMenu					= new ArrayList<aca.menu.spring.Menu>();
				List<Modulo> lisModulo				= new ArrayList<aca.menu.spring.Modulo>();
				List<ModuloOpcion> lisOpcion 		= new ArrayList<aca.menu.spring.ModuloOpcion>();
				HashMap<String,String> carpetas 	= new HashMap<String,String>();						
				
				sesion.setAttribute("codigoPersonal", usuario.getCodigoPersonal());
				sesion.setAttribute("codigoAlumno", usuario.getCodigoPersonal());
				sesion.setAttribute("codigoEmpleado", usuario.getCodigoPersonal());
				sesion.setAttribute("codigoUltimo", usuario.getCodigoPersonal());
				sesion.setAttribute("admin", accesoDao.esAdministrador(usuario.getCodigoPersonal()));
				sesion.setAttribute("menu", accesoDao.getMenu(usuario.getCodigoPersonal()));
				sesion.setAttribute("codigoPreceptor", usuario.getCodigoPersonal());
				sesion.setAttribute("dormitorioId", "0");
				
				boolean esMaestro 		= moduloDao.getEsMaestro(usuario.getCodigoPersonal());
				boolean esMentor		= moduloDao.getEsMentor( usuario.getCodigoPersonal());
				boolean esEmpleado		= moduloDao.getEsEmpleado( usuario.getCodigoPersonal());
				boolean esEditor		= moduloDao.esEditorDeContenidos( usuario.getCodigoPersonal());
				boolean esPadre 		= padrePersonalDao.esPadre(usuario.getCodigoPersonal());			
				
				sesion.setAttribute("esMaestro",esMaestro);
				sesion.setAttribute("esMentor",esMentor);
				sesion.setAttribute("esEmpleado",esEmpleado);
				sesion.setAttribute("esPadre",esPadre);
				sesion.setAttribute("esEditor",esEditor);	
				
				// Buscar el ejercicio actual
				String ejercicioId = contEjercicioDao.getEjercicioActual("001");
				sesion.setAttribute("ejercicioId", ejercicioId);
				
				String sOpciones			= "";
				String opcionesUsuario		= " ";				
				if (moduloDao.getEsEmpleado(usuario.getCodigoPersonal()) || empMaestroDao.esMaestro(usuario.getCodigoPersonal())){
					sesion.setAttribute("portal", "empleado");
					
					// Agrega opciones por default			
					List<String> lista = new ArrayList<String>(Arrays.asList("290","291","111","081","000","990","991","993","1000","996"));
					// Si tiene acceso al portal del internado como preceptor o monitor
					if (intAccesoDao.tieneAcceso(codigoPersonal) > 0){
						lista.add("490");	
					}
					for (String opcion : lista) {
						if (accesoOpcionDao.existeReg(codigoPersonal, opcion)==false){
							AccesoOpcion accesoOpcion = new AccesoOpcion();
							accesoOpcion.setCodigoPersonal(codigoPersonal);
							accesoOpcion.setOpcionId(opcion);							
							accesoOpcion.setUsuario("9800308");
							accesoOpcionDao.insertReg(accesoOpcion);
						}
					}					
					tipoUsuario 	= "Empleado";
					mensaje 		= "Existe";					
					opcionesUsuario 	+= "290 291 111 081 000 990 991 993 1000 996";					
					// Si es maestro se agrega el portal
					if (moduloDao.getEsMaestro( usuario.getCodigoPersonal())){
						opcionesUsuario += " PMA";
					}					
				}else if(usuario.getCodigoPersonal().substring(0,1).equals("P")){
					sesion.setAttribute("portal", "padre");
					tipoUsuario 	= "Padre";
					mensaje 		= "Existe";
				}else{				
					sesion.setAttribute("portal", "alumno");
					opcionesUsuario += " PAL";
					tipoUsuario 	= "Alumno";
					mensaje 		= "Existe";
				}
				
				// Si tiene acceso al portal del internado como preceptor o monitor
				if (intAccesoDao.tieneAcceso(usuario.getCodigoPersonal()) > 0){
					opcionesUsuario 	+= " 490";					
				}
				
				lisMenu	  	= menuDao.getListUser( usuario.getCodigoPersonal(), "") ;
				lisModulo 	= moduloDao.getListUser(usuario.getCodigoPersonal(), "");
				lisOpcion 	= moduloOpcionDao.listUser(usuario.getCodigoPersonal(), "");
				sOpciones 	= moduloOpcionDao.getOpcUser(usuario.getCodigoPersonal()) + opcionesUsuario;
				carpetas	= moduloOpcionDao.mapCarpetas(usuario.getCodigoPersonal());
				
				sesion.setAttribute("lisMenu", lisMenu);
				sesion.setAttribute("lisModulo", lisModulo);
				sesion.setAttribute("lisOpcion", lisOpcion);
				sesion.setAttribute("opciones", sOpciones);
				sesion.setAttribute("mapCarpetas", carpetas);
				sesion.setAttribute("periodo", catPeriodoDao.getPeriodo());
				sesion.setAttribute("colorTablas",alumColorDao.getColor(usuario.getCodigoPersonal()));
				sesion.setAttribute("colorMenu",alumColorDao.getColorMenu(usuario.getCodigoPersonal()));

				String colorReloj = alumColorDao.getColorReloj( usuario.getCodigoPersonal()).equals("default")?"#545454":alumColorDao.getColorReloj( usuario.getCodigoPersonal());
				sesion.setAttribute("colorReloj", colorReloj);				
			}else {
				mensaje = "NoExiste";
			}
		}
		
		modelo.addAttribute("tipoUsuario", tipoUsuario); 
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("codigoUsuario", usuario.getCodigoPersonal());
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "busca";
	}
	
	@RequestMapping("/sesion")
	public String sesion(HttpServletRequest request, Model modelo){		
		HttpSession sesion	= null;
		String respuesta 	= "¡Sesion no encontrada!";
		
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();
            if (sesion!=null){            	
        		String sesionId			= sesion.getId();
        		String codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
        		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
        		int numAccion			= Integer.parseInt(accion);        		
        		
        		switch(numAccion){
        			case 1:{//Guardar la fecha de finalización de sessión
        				if (sesionDao.existeReg(sesionId)){
        					if (sesionDao.update(sesionId)){
        						respuesta = "Se guardó la finalizacion";
        					}
        				}
        			}break;
        			case 2:{//Revisar si ya existe otra sesión de este usuario
        				if (opcionDao.getAlertaSesion(codigoPersonal).equals("S")) {
        					respuesta = sesionDao.validaSesionUnica(sesionId, codigoPersonal, request.getRemoteAddr());
        				}else{
        					respuesta = "";
        				}        				
        			}break;
        			case 3:{//Invalida sesion        				
        				if (sesionDao.existeReg(sesionId)){  					
        					if (sesionDao.updateReg(sesionId)){
        						sesionDao.updateRegFinalizo(sesionId);        					
        						respuesta = "sesion invalidada";
        					}
        				}
        			}break;
        			case 4:{//Expulsar otras sesiones       
        				respuesta = sesionDao.expulsaOtrasSesiones(sesionId, codigoPersonal);
        			}break;
        		}            	
            }
		}
		
		modelo.addAttribute("mensaje", respuesta); 
		
		return "sesion";
	}
	
	@RequestMapping("/configuracion")
	public String configuracion(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		
		Opcion opcion 				= new Opcion();
		MaestroOpcion maestroOpcion = new MaestroOpcion();
		Mensaje mensaje 			= new Mensaje();
		Acceso acceso				= new Acceso();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
			acceso 			= accesoDao.mapeaRegId(codigoPersonal);
			mensaje 		= mensajeDao.mapeaRegId(codigoPersonal);
			maestroOpcion 	= maestroOpcionDao.mapeaRegId(codigoPersonal);
			opcion 			= opcionDao.mapeaRegId(codigoPersonal); 
		}
		
		modelo.addAttribute("opcion", opcion);
		modelo.addAttribute("maestroOpcion", maestroOpcion);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("acceso", acceso);				
		
		return "configuracion";
	}
	
	@RequestMapping("/grabarAcceso")
	public String grabarAcceso(HttpServletRequest request){
			
		String codigoPersonal 	= "0";
		String alertaSesion		= request.getParameter("alertaSesion")==null?"0":request.getParameter("alertaSesion");
		String menuClick		= request.getParameter("menuClick")==null?"0":request.getParameter("menuClick");
		String vistaEvaluar		= request.getParameter("vistaEvaluar")==null?"0":request.getParameter("vistaEvaluar");
		
		Opcion opcion 			= new Opcion();
		Acceso acceso			= new Acceso();
		MaestroOpcion maestro	= new MaestroOpcion();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
			acceso 			= accesoDao.mapeaRegId(codigoPersonal);
			
			if(!alertaSesion.equals("0") || !menuClick.equals("0")){
				opcion.setCodigoPersonal(codigoPersonal);
				opcion.setAlertaSesion(alertaSesion);
				opcion.setMenuClick(menuClick);
				if(opcionDao.existeReg(codigoPersonal)){
					opcionDao.updateReg(opcion);					
				}else {
					opcionDao.insertReg(opcion);					
				}
			}
			
			if(!vistaEvaluar.equals("0")){
				maestro.setCodigoPersonal(codigoPersonal);
				maestro.setVistaEvaluar(vistaEvaluar);
				if(maestroOpcionDao.existeReg(codigoPersonal)){					
					if (maestroOpcionDao.updateReg(maestro)) {
						acceso.setMenu(vistaEvaluar);
						accesoDao.updateReg(acceso);
					}					
				}else{
					if (maestroOpcionDao.insertReg(maestro)){
						acceso.setMenu(vistaEvaluar);
						accesoDao.updateReg(acceso);
					}					
				}
				acceso.setMenu(vistaEvaluar);
			}		
		}
		
		return "redirect:/configuracion";
	}
	
	@RequestMapping("/grabarIdioma")
	public String grabarIdioma(HttpServletRequest request){
		
		String codigoPersonal 	= "0";
		String idioma 			= request.getParameter("IdiomaUsuario")==null?"es":request.getParameter("IdiomaUsuario");
		Acceso acceso 			= new Acceso();
		String msj 				= "";
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
			if (accesoDao.existeReg(codigoPersonal)) {
				acceso 			= accesoDao.mapeaRegId(codigoPersonal);
				acceso.setIdioma(idioma);				
				if(accesoDao.updateReg(acceso)){
					msj = "Saved";
					sesion.setAttribute("lenguaje", idioma);	
				}else{
					msj = "Not Saved";	
				}
			}			
		}		
		return "redirect:/configuracion?msj="+msj;
	}	

	@RequestMapping("/imagen")
	public String imagen(HttpServletRequest request){		
		return "imagen";
	}
	
	@RequestMapping("/avisoPrivacidad")
	public String avisoPrivacidad(HttpServletRequest request){	
		
		return "avisoPrivacidad";
				
	}
	
	@RequestMapping("/grabarAviso")
	public String grabarAviso(HttpServletRequest request){
		
		String usuario 			= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		String vista 			= "redirect:/login";
		AccesoPrivacidad acceso = new AccesoPrivacidad();	
   			
		if(accesoPrivacidadDao.existeReg(usuario) == false){    			
    		acceso.setCodigoPersonal(usuario);
			if (accesoPrivacidadDao.insertReg(acceso)){
				vista = "redirect:/aca";
			}else{
				vista = "avisoPrivacidad";
			}
    	}
		
		return vista;
				
	}
	
	@RequestMapping("/paginaError2")
	public String paginaError2(HttpServletRequest request){		
		return "paginaError2";
	}
	
	@RequestMapping("/ocultarMsj")
	public String ocultarMsj(HttpServletRequest request){
		
		String codigoPersonal = "0";		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal = sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
        	Mensaje mensaje = new Mensaje();
        	mensaje.setCodigoPersonal(codigoPersonal);
			if(!mensajeDao.existeReg(codigoPersonal)){
				mensaje.setMensaje1("S");
				mensaje.setMensaje2("-");
				mensajeDao.insertReg(mensaje);
			}else{
				mensaje = mensajeDao.mapeaRegId(codigoPersonal);
				mensaje.setMensaje2("N");
				mensajeDao.updateReg(mensaje);
			}        	
        }		
		return "ocultarMsj";
	}
	
	@RequestMapping("/pruebaJared")
	public @ResponseBody List<aca.catalogo.spring.CatCriterio> pruebaJared(HttpServletRequest request){		
		List<aca.catalogo.spring.CatCriterio> lista = catCriterioDao.getListAll("");
		for (aca.catalogo.spring.CatCriterio objeto : lista){
			//System.out.println("Datos:"+objeto.getCriterioId()+":"+objeto.getDescripcion() );
		}		
		return lista;
	}
	
	@RequestMapping(value={"/foto","/academico/foto"})
	public void foto(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo			= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
		String llave		= request.getParameter("Llave")==null?"X":request.getParameter("Llave");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		PosFotoAlum foto 	= new PosFotoAlum();		
        if ( auth.isAuthenticated() || llave.equals(aca.util.Encriptar.getSHA256(codigo+"shareCarita"))){
        	try{			
        		// Busca la imagen
        		if (posFotoAlumDao.existeReg(codigo, tipo)){
        			foto = posFotoAlumDao.mapeaRegId(codigo, tipo);
        		}else{
        			foto = posFotoAlumDao.mapeaRegId("usBlack", "O");
        		}			
        		response.setContentType("image/jpeg");
    			OutputStream out = response.getOutputStream();    		
    			out.write(foto.getFoto());
    			out.close();        		
        	}catch(Exception ex){
        		System.out.println("Error /foto:"+ex);
        	}
        }	
	}
	
	@RequestMapping(value={"/fotoMenu","/academico/fotoMenu"})
	public void fotoMenu(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");				
		PosFotoAlum foto 	= new PosFotoAlum();
		HttpSession sesion	= null;	
        sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	try{
        		if (posFotoAlumDao.existeReg(codigo, "C")){
        			foto = posFotoAlumDao.mapeaRegId(codigo, "C");
        		}else if (posFotoAlumDao.existeReg(codigo, "O")){
        			foto = posFotoAlumDao.mapeaRegId(codigo, "O");
        		}else{
        			foto = posFotoAlumDao.mapeaRegId("usWhite", "O");
        		}			
        		OutputStream out = response.getOutputStream();    		
        		out.write(foto.getFoto());
        		out.close();
        	}catch(Exception ex){
        		System.out.println("Error /foto:"+ex);
        	}
        }	
	}	
	
	@RequestMapping(value={"/fotoCuadrada"})
	public void fotoCuadra(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo			= request.getParameter("Tipo")==null?"C":request.getParameter("Tipo");		
		PosFotoAlum foto 	= new PosFotoAlum();
		HttpSession sesion	= null;	
        sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	try{			
        		// Busca la imagen
        		if (posFotoAlumDao.existeReg(codigo, tipo)){
        			foto = posFotoAlumDao.mapeaRegId(codigo, tipo);
        		}else{
        			foto = posFotoAlumDao.mapeaRegId("usBlack", "O");
        		}	
        		response.setContentType("image/jpeg");
				response.setHeader("Content-Disposition","attachment; filename=\""+codigo+".jpg\"");
        		OutputStream out = response.getOutputStream();
        		out.write( foto.getFoto() );
        		out.close();
        	}catch(Exception ex){
        		System.out.println("Error /foto:"+ex);
        	}
        }	
	}	
	
	@RequestMapping(value={"/empFoto"})
	public void empFoto(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo") == null ? "0" : request.getParameter("Codigo");
		String folio		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		EmpFoto foto 		= new EmpFoto();
		HttpSession sesion	= null;	
		sesion = ((HttpServletRequest)request).getSession();
		if (sesion != null){
			try{
				boolean existe = false;
				// Busca la imagen
				if (empFotoDao.existeReg(codigo, folio)){
					foto = empFotoDao.mapeaRegId(codigo, folio);
					existe = true;
				}else{
					foto = empFotoDao.mapeaRegId("usBlack", "0");
				}				
				response.setContentType("image/jpeg");
				response.setHeader("Content-Disposition","attachment; filename=\""+codigo+".jpg\"");
				OutputStream out = response.getOutputStream();    		
				out.write(foto.getFoto());
				out.close();
			}catch(Exception ex){
				System.out.println("Error /empFoto:"+codigo+":"+ex);
			}
		}	
	}
	
	@RequestMapping(value={"/fotochica"})
	public void fotoChica(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");		
		String acceso		= request.getParameter("Acceso")==null?"X":request.getParameter("Acceso");
		FotoChica foto 		= new FotoChica();
		HttpSession sesion	= null;	
        sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null || acceso.equals("6Yf8Zqus4gE+9+tL1kkX8g==")){
        	try{			
        		// Busca la imagen
        		if (fotoChicaDao.existeReg(codigo)){
        			foto = fotoChicaDao.mapeaRegId(codigo);
        		}else{
        			foto = fotoChicaDao.mapeaRegId("nofoto");
        		}			
        		OutputStream out = response.getOutputStream();    		
        		out.write(foto.getFoto());
        		out.close();
        	}catch(Exception ex){
        		System.out.println("Error /fotoChica:"+codigo+":"+ex);
        	}
        }	
	}
	
	@RequestMapping(value={"/fotoArchivo","/academico/fotoArchivo"})
	public void fotoArchivo(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";
		String matricula 		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String documentoId	 	= request.getParameter("documento")==null?"0":request.getParameter("documento");
		String hoja 			= request.getParameter("hoja")==null?"0":request.getParameter("hoja");
		String estado 			= "B";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	
        	codigoPersonal 	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
        	codigoAlumno 	= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
        	
        	if ( accesoDao.mapeaRegId(codigoPersonal).getPortalAlumno().equals("S") || codigoPersonal.equals(matricula)){
        		
        		PosArchDocAlum archivo = new PosArchDocAlum();
        		if(posArchDocAlumDao.existeReg(matricula, documentoId, hoja)) {
    				archivo = posArchDocAlumDao.mapeaRegId(matricula, documentoId,hoja);
    				estado 	= archivo.getEstado();
    			}        		
        		if (archivo.getEstado().equals("B")) {        			        			
        			try {
        				response.setContentType("image/jpeg");
        				response.setHeader("Content-Disposition","attachment; filename=\""+matricula+documentoId+hoja+".jpg\"");
        				OutputStream out = response.getOutputStream();    		
        				out.write(archivo.getImagenByte());
        				out.close();
        			}catch(Exception ex) {
        				System.out.println("Error:"+ex);
        			}
        		}else{

		        	Connection conn 	= null; 
		        	Statement stmt		= null;
		    		ResultSet rset		= null;
		    		ResultSet rset2		= null;
		        	try{        		
		        		//coneccion a ATLAS
		        		DriverManager.registerDriver (new org.postgresql.Driver());
		        		conn		= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
		        		stmt		= conn.createStatement();
		        		rset		= null;
		        		byte buf[] 	= null;	        		
	        			conn.setAutoCommit(false);
	        			String COMANDO = "SELECT COUNT(MATRICULA) FROM ARCH_DOCALUM WHERE MATRICULA = '"+matricula+"' AND IDDOCUMENTO = '"+documentoId+"' AND HOJA = "+hoja;	        			
	        			rset = stmt.executeQuery(COMANDO);
	        			if (rset.next()){
	        				COMANDO = " SELECT IMAGEN, FUENTE, ORIGEN, USUARIO, TO_CHAR(F_INSERT,'DD/MM/YYYY') AS F_I, TO_CHAR(F_UPDATE,'DD/MM/YYYY') AS F_U"
	        						+ " FROM ARCH_DOCALUM"
	        						+ " WHERE MATRICULA = '"+matricula+"'"
	        						+ " AND IDDOCUMENTO = '"+documentoId+"'"
	        						+ " AND HOJA = "+hoja;	        			
	        				rset2 = stmt.executeQuery(COMANDO);
	        				if (rset2.next()){
		        				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
		        				org.postgresql.largeobject.LargeObject obj;
		        				long oid = rset2.getLong("IMAGEN");
		        				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ); 
		        				buf = new byte[obj.size()];
		        				//obj.read(buf, 0, obj.size());
		        				buf = obj.read(obj.size());
		        				response.setContentType("image/jpeg");
		        				response.getOutputStream().write(buf);
		        				response.flushBuffer();
		        				obj.close();
		        			}        			
		        			response.setContentType("image/jpeg");
		        			response.setHeader("Content-Disposition","attachment; filename=\""+matricula+documentoId+hoja+".jpg\"");
		        			OutputStream out = response.getOutputStream();    		
		        			out.write(buf);
		        			out.close();        		
		        			conn.setAutoCommit(true);
	        			}		        		
		        	}catch(Exception ex){
		        		System.out.println("Error /fotoArchivo:"+matricula+" Doc:"+documentoId+" Hoja:"+hoja+" Estado:"+estado+" Usuario:"+codigoPersonal+""+ex);
		        	}finally {
		        		if (conn!=null) conn.close();
		        		try { stmt.close(); } catch (Exception ignore) { }
		        		if (rset!=null) rset.close();
		        		if (rset2!=null) rset2.close();
		        	}
        		}	
        	}        	
        }	
	}
	
	@RequestMapping(value={"/fotoGeneral","/academico/fotoGeneral"})
	public void fotoGeneral(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		
		String matricula 		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String folio		 	= request.getParameter("folio")==null?"0":request.getParameter("folio");		
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	
        	if (posArchGeneralDao.existeImagenReg(matricula, folio)) {        		
        		try {
        			PosArchGeneral archivo = posArchGeneralDao.mapeaRegId(matricula, folio); 
        			OutputStream out = response.getOutputStream();    		
        			out.write(archivo.getImagenByte());
        			out.close();
        		}catch(Exception ex) {
        			System.out.println("Error:"+ex);
        		}
        	}else{
        		
        		Connection conn 	= null; 
            	Statement stmt		= null;
        		ResultSet rset		= null;
            	try{        		
            		//coneccion a ATLAS
            		DriverManager.registerDriver (new org.postgresql.Driver());
            		conn		= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
            		stmt		= conn.createStatement();
            		rset		= null;
            		byte buf[] 	= null;
            		conn.setAutoCommit(false);
            		
            		ArchGeneral archGeneral = new ArchGeneral();
            		
            		archGeneral.setMatricula(matricula);
            		archGeneral.setFolio(folio);
            		if ( archGeneral.existeReg(conn) ){
            			archGeneral.mapeaRegId(conn,matricula,folio);
            			
            			org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
            			org.postgresql.largeobject.LargeObject obj;
            			long oid = archGeneral.getImagen();
            			obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
            			buf = obj.read(obj.size());        		    
            		    response.setContentType("image/jpeg");
            			response.getOutputStream().write(buf);
            			response.flushBuffer();
            			obj.close();
            		}       			
            		OutputStream out = response.getOutputStream();    		
            		out.write(buf);
            		out.close();        		
            		conn.setAutoCommit(true);
            	}catch(Exception ex){
            		System.out.println("Error /fotoGeneral:"+matricula+":"+folio+":"+ex);
            	}finally {
            		if (conn!=null) conn.close();
            		try { stmt.close(); } catch (Exception ignore) { }
            		if (rset!=null) rset.close();
            	}        		
        	}        	
        }	
	}
	
	@RequestMapping(value={"/fotoEvento","/academico/fotoEvento"})
	public void fotoEvento(HttpServletRequest request, HttpServletResponse response){
		String eventoId 		= request.getParameter("eventoId")==null?"0":request.getParameter("eventoId");
		String imagenId 		= request.getParameter("imagenId")==null?"0":request.getParameter("imagenId");
		
		aca.cultural.spring.CompEventoImagen imagen 		= new aca.cultural.spring.CompEventoImagen();		
		
		try{			
			// Busca la imagen
			if (compEventoImagenDao.existeReg(eventoId, imagenId)){
				imagen = compEventoImagenDao.mapeaRegId(eventoId, imagenId);				
			}else{
				imagen = compEventoImagenDao.mapeaRegId("nofoto", "O");
			}			
			OutputStream out = response.getOutputStream();			
			out.write(imagen.getImagen() );
			out.close();
		}catch(Exception ex){
			System.out.println("Error /fotoEvento:"+ex);
		}
	}
	
	@RequestMapping(value={"/fotoBajar","/academico/fotoBajar"})
	public void fotoBajar(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo			= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
		String nombreFoto 	= "";
		PosFotoAlum foto 	= new PosFotoAlum();
		
		// Busca la imagen 
		try{
			// Busca la imagen
			if (posFotoAlumDao.existeReg(codigo, tipo)){
				foto = posFotoAlumDao.mapeaRegId(codigo, tipo);
			}else{
				foto = posFotoAlumDao.mapeaRegId("nofoto", "O");
			}
			if (tipo.equals("O")) nombreFoto = codigo+".jpg"; else nombreFoto = codigo+"-"+tipo+".jpg";
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition","attachment; filename=\""+ nombreFoto+ "\"");	
			response.getOutputStream().write(foto.getFoto());
			response.flushBuffer();
			
		}catch(Exception ex){
			System.out.println("Error /fotoBajar:"+ex);
		}
	}	

	@RequestMapping(value={"/empFotoBajar"})
	public void empFotoBajar(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo") == null ? "0" : request.getParameter("Codigo");
		String folio		= request.getParameter("Folio") == null ? "O" : request.getParameter("Folio");
		String nombreFoto 	= "";
		EmpFoto foto 		= new EmpFoto();
		
		// Busca la imagen 
		try{
			// Busca la imagen
			if (empFotoDao.existeReg(codigo, folio)){
				foto = empFotoDao.mapeaRegId(codigo, folio);
			}else{
				foto = empFotoDao.mapeaRegId("nofoto", "0");
			}
			if (folio.equals("0")) nombreFoto = codigo+".jpg"; else nombreFoto = codigo+"-"+folio+".jpg";
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition","attachment; filename=\""+ nombreFoto+ "\"");	
			response.getOutputStream().write(foto.getFoto());
			response.flushBuffer();
			
		}catch(Exception ex){
			System.out.println("Error /fotoBajar:"+ex);
		}
	}	
	
	@RequestMapping(value={"/fotoVisitaBajar","/academico/fotoVisitaBajar"})
	public void fotoVisitaBajar(HttpServletRequest request, HttpServletResponse response){
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo			= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
		String nombreFoto 	= codigo+".jpg";
		PosFotoAlum foto 	= new PosFotoAlum();
		
		// Busca la imagen 
		try{
			// Busca la imagen
			if (posFotoAlumDao.existeReg(codigo, tipo)){
				foto = posFotoAlumDao.mapeaRegId(codigo, tipo);
			}else{
				foto = posFotoAlumDao.mapeaRegId("nofoto", "O");
			}
			
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition","attachment; filename=\""+ nombreFoto+ "\"");	
			response.getOutputStream().write(foto.getFoto());
			response.flushBuffer();
			
		}catch(Exception ex){
			System.out.println("Error /fotoVisitaBajar:"+ex);
		}
	}
	
	@RequestMapping(value={"/archivoBajar","/academico/archivoBajar"})
	public void archivoBajar(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		
		HttpSession sesion			= null;
		aca.archivos.ArchivosProfesor bArchivosProfesor		= new aca.archivos.ArchivosProfesor();
		aca.archivos.Archivos bArchivos						= new aca.archivos.Archivos();
		
		java.sql.Connection conn2 		= null;
		java.sql.PreparedStatement ps 	= null;
		java.sql.ResultSet rs 			= null;
		
		
		try {		
			// Conexion a postgres
			java.sql.DriverManager.registerDriver (new org.postgresql.Driver());
			conn2	= java.sql.DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
			
			conn2.setAutoCommit(false);
			
			int nFolio			= 0;
			String codigo		= "0";
			String origen 		= request.getParameter("clave")==null?"":request.getParameter("clave");
			String id 			= request.getParameter("id");
			String folio 		= request.getParameter("folio");			 
			String nombre 		= request.getParameter("nombre");
			String matricula 	= request.getParameter("matricula");
			
			if (request instanceof HttpServletRequest) {
	            sesion = ((HttpServletRequest)request).getSession();
	            if (sesion!=null){
	            	codigo	= (String)sesion.getAttribute("codigoAlumno");
	            }
			} 
			
			if(folio!=null)nFolio=Integer.parseInt(folio);			
			
			String tabla		= "";
			
			if(origen.equals("P")){				
				bArchivosProfesor.cambiaEstadoArchivo(conn2,id,nFolio,codigo);
				tabla	= "archivos_profesor";
			}
			else if(!origen.equals("X")){
				bArchivos.cambiaEstadoArchivoAlumno(conn2,id,nFolio,matricula,"V");
				tabla="archivos_alumno";
			}else tabla="archivos_alumno";
			
			/* conn2.setAutoCommit(false); */			
			String sql			= "SELECT ARCHIVO FROM PORTAL."+tabla+" WHERE ARCHIVO_ID = ? AND FOLIO = ? AND CODIGO_PERSONAL=?";
			ps 	= conn2.prepareStatement(sql);
			ps.setString(1,id);
			ps.setInt(2,Integer.parseInt(folio));
			ps.setString(3,matricula);
			rs 	= ps.executeQuery();
			byte archivo[]=null;
			if (rs.next()) {
		        org.postgresql.largeobject.LargeObject obj;
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();
				long oid = rs.getLong(1);
		   		obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
			    archivo = new byte[obj.size()];
			    //obj.read(archivo, 0, obj.size());
			    archivo =  obj.read(obj.size());
				obj.close();
		    }
			ps.close();
			rs.close();		
			
			conn2.setAutoCommit(true);
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+nombre+ "\"");
			response.getOutputStream().write(archivo);
			response.flushBuffer();
			
		}catch ( Exception e ) {
			e.printStackTrace();
		}finally{
			// cerrar la conexion..	
			if (conn2!=null) conn2.close();
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}		
	}
	
	@RequestMapping("/cambiaColor")
	public String cambiaColor(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String nombreUsuario	= "-";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
        	nombreUsuario 	= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
        }
        
		modelo.addAttribute("Accion", accion);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "cambiaColor";
	}
	
	@RequestMapping("/grabarColor")
	public String grabarColor(HttpServletRequest request){
		String codigoPersonal 	= "0";
		String color 			= request.getParameter("Color")==null?"4A76F2":request.getParameter("Color");
		aca.alumno.spring.AlumColor alumColor = new aca.alumno.spring.AlumColor();		
		color = "#"+color;
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
        	if (alumColorDao.existeReg(codigoPersonal)){
        		
        		// MapeaReg
        		alumColor = alumColorDao.mapeaRegId(codigoPersonal);
        		alumColor.setColor(color);
        		// Update        		
        		if (alumColorDao.updateReg(alumColor)){
        			sesion.setAttribute("colorTablas", color);
        		}        		
        	}else{
        		// Insert
        		alumColor.setCodigoPersonal(codigoPersonal);
        		alumColor.setColor(color);
        		alumColor.setMenu("default");
        		alumColor.setReloj("N");
        		alumColor.setColorReloj("default");
        		if (alumColorDao.insertReg(alumColor)){
        			sesion.setAttribute("colorTablas", color);
        		}
        	}
        }
        
		return "redirect:/cambiaColor?Accion=1";
	}
		
	@ResponseBody
	@RequestMapping(value="/cerrarPortal/{matricula}",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public AlumBloqueo cerrarPortal(@PathVariable(name="matricula",required=true) String matricula) {
		
		String bloqueado 		= "NO";		
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String periodoId		= finPeriodoDao.getPeriodoActivo(fechaHoy);
		AlumBloqueo alumno 		= new AlumBloqueo();
		
		alumno.setMatricula(matricula);		
		if (!periodoId.equals("0")){		
			if (finBloqueoDao.existeReg(matricula, periodoId)){
				if (finBloqueoDao.mapeaRegId(matricula, periodoId).getEstado().equals("A")) {
					bloqueado = "SI";
				}
			}
		} 
		alumno.setCerrar(bloqueado);
		
		return alumno;
	}
	
	@RequestMapping("/aprendizaje")
	public String aprendizaje(HttpServletRequest request, Model modelo){
		
		List <BecCategoria> lisCategoria 		= becCategoriaDao.getListActivos("ORDER BY CATEGORIA_ID");
		HashMap<String, String> mapaCategorias 	= becCategoriaDao.getMapCategorias();
		
		modelo.addAttribute("lisCategoria", lisCategoria);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		
		return "aprendizaje";
	}

	@RequestMapping("/recuperarPassword")
	public String recuperarPassword(HttpServletRequest request, Model modelo){
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");	
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");	
		String email 		= request.getParameter("Email")==null?"0":request.getParameter("Email");	
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("email", email);
		
		return "recuperarPassword";
	}
	
	@RequestMapping("/enviaLink")
	public String enviaLink(HttpServletRequest request){
		
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");	
		String correo	 	= "";
		String[] nombre		= null;

		String url = "https://academico.um.edu.mx/academico/nuevoPassword";
		Parametros parametros = parametrosDao.mapeaRegId("1");
		if(parametros.getInstitucion().equals("Pacific Adventist University")) url = "https://pau.um.edu.mx/pau/nuevoPassword";
		if(parametros.getInstitucion().equals("Sonoma")) url = "https://pau.um.edu.mx/pau/nuevoPassword";
		if(parametros.getInstitucion().equals("Fulton")) url = "https://pau.um.edu.mx/pau/nuevoPassword";
		
		boolean ok 		= false;
		AlumPersonal alumPersonal	= new AlumPersonal();
		if (alumPersonalDao.existeReg(matricula)) {
			alumPersonal	= alumPersonalDao.mapeaRegId(matricula);
			correo 		 	= alumPersonal.getEmail();
			nombre 			= alumPersonal.getNombre().trim().split(" ");
			ok = true;
		}else if (empContactoDao.existeReg(matricula)){
			correo 			= empContactoDao.mapeaRegId(matricula).getCorreo();
			nombre 			= maestrosDao.mapeaRegId(matricula).getNombre().trim().split(" ");
			ok = true;
		}
		
		AccesoClave accesoClave = new AccesoClave();
		
		String clave		= "";
		String mensaje		= "-";
		if (ok) {		
			clave = nombre[0]+aca.util.Fecha.getHora()+aca.util.Fecha.getMinutos()+aca.util.Fecha.getSegundos();
			
			if(accesoClaveDao.existeReg(matricula, 1)) {
				accesoClave = accesoClaveDao.mapeaRegIdFolio(matricula, 1);

				if(accesoClaveDao.updateReg(accesoClave)){
					mensaje = "1";
				}else {
					ok = false;
				}
			}else{
				accesoClave.setClave(clave);
				accesoClave.setCodigoPersonal(matricula);
				accesoClave.setFolio(1);
				accesoClave.setIp(request.getRemoteAddr());

				if(accesoClaveDao.insertReg(accesoClave)){
					mensaje = "1";
				}else {
					ok = false;
				}
			}			
		}
		if (ok && correo.contains("@")) {
			String texto = "Click the following url in order to reset your password. This link will be valid for 20 minutes. After that time you will need to generate a new request. \n"
				+ url+"?Clave="+clave+"&Codigo="+matricula+" \n"
				+ "If you do not recognize this action please contact your IT department. \n"
				+ "Have a blessed day!";	
			try {
				mailService.sendMesageSimple(correo, "Password Reset", texto);
			} catch (Exception ex) {
				mensaje = "5";
				System.out.println("Error en enviaLink("+correo+"):"+matricula+":"+ex);
			}
		}else {
			mensaje = "2";
		}	
		
		return "redirect:/recuperarPassword?Mensaje="+mensaje;
	}
	
	@RequestMapping("/nuevoPassword")
	public String nuevoPassword(HttpServletRequest request, Model modelo){
		String matricula 	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");	
		
		AccesoClave accesoClave = new AccesoClave();
		if(accesoClaveDao.existeReg(matricula,1)) {
			accesoClave = accesoClaveDao.mapeaRegIdFolio(matricula, 1);
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Date fechaInicio = new Date();
		Date fechaTermino = new Date();
		
		try {
			fechaInicio = formatter.parse(accesoClave.getFechaRecupera());
		} catch (ParseException e) {			
			System.out.println("Error en nuevoPassword:"+matricula+":"+accesoClave.getFechaRecupera()+e.getMessage());
			//e.printStackTrace();
		}

	    float segundos = (float) ((fechaTermino.getTime()/1000) - (fechaInicio.getTime()/1000));
	    boolean rango = false;
	  
	    if(segundos < 60) {
	    	rango = true;
	    }else if(segundos > 60) {
	    	segundos =  segundos / 60;
	    	if(segundos <= 20) {
	    		rango = true;
	    	}
	    }
		
		if(!rango) {
			return "redirect:/recuperarPassword?Mensaje=3";
		}
		
		modelo.addAttribute("accesoClave", accesoClave);
		
		return "nuevoPassword";
	}
	
	@RequestMapping("/grabarNuevoPassword")
	public String grabarNuevoPassword(HttpServletRequest request){
		String clave 		= request.getParameter("Clave")==null?"0":request.getParameter("Clave");	
		String confirma 	= request.getParameter("Confirma")==null?"0":request.getParameter("Confirma");
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");		
		Acceso acceso 		= new Acceso();
		String regresar		= "redirect:/login";
		
		if(accesoDao.existeReg(matricula)) {
			acceso = accesoDao.mapeaRegId(matricula);
		}
		
		String claveSha 	= aca.util.Encriptar.sha512ConBase64(clave);
		String claveHexa	= bCryptPasswordEncoder.encode(clave);
		
		if(clave.equals(confirma)) {
			acceso.setClaveHexa(claveHexa);
			acceso.setClave(claveSha);
			if (accesoDao.updateReg(acceso)) {				
				// Actualizar los datos del usuario en Security
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
				Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), claveHexa, updatedAuthorities);				
				SecurityContextHolder.getContext().setAuthentication(newAuth);				
			}
		}else {			
			regresar = "redirect:/recuperarPassword?Mensaje=4";
		}
			
		return regresar;
	}
	
	@RequestMapping("/verificarCodigo")
	public String verificarCodigo(HttpServletRequest request, Model modelo){
		
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");	
		String codigo		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String claveEncri 	= aca.util.Encriptar.sha512ConBase64(codigo);
		String mensaje		= "0";
		
		Acceso acceso = accesoDao.mapeaRegId(matricula);	
		
		if (acceso.getClaveInicial().equals(claveEncri)) {			
			acceso.setClave(claveEncri);			
			if(accesoDao.updateReg(acceso)){
				mensaje = "1";
			}else {
				return "redirect:/intentaRecuperar?Mensaje=3&Matricula="+matricula;
			}
		}else {
			return "redirect:/intentaRecuperar?Mensaje=2&Matricula="+matricula;
		}
		
		return "redirect:/login?Mensaje="+mensaje;
	}
	
	
	@ResponseBody
	@RequestMapping("/consultarCorreo")
	public String consultarCorreo(HttpServletRequest request){		
		String matricula 			= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String correo 				= "-";		
		if (alumPersonalDao.existeReg(matricula)) {
			correo 			= alumPersonalDao.mapeaRegId(matricula).getEmail();		
		}		
		return correo;
	}
	
	@RequestMapping("/certifica")
	public String certifica(HttpServletRequest request, Model modelo){
		String matricula			= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String codigo				= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String alumnoNombre			= "-";
		boolean existe				= false;
		AccesoValida accesoValida 	= new AccesoValida();		
		if (accesoValidaDao.existeCodigo(matricula, codigo)){
			accesoValida 	= accesoValidaDao.mapeaPorCodigo(matricula, codigo);
			alumnoNombre 	= alumPersonalDao.getNombreAlumno(accesoValida.getCodigoPersonal(), "NOMBRE");
			existe 			= true;			
		}
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("accesoValida", accesoValida);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		
		return "certifica";
	}
	
	@RequestMapping("/validaDocumento")
	public String validaDocumento(HttpServletRequest request, Model modelo){
		String id					= request.getParameter("Id")==null?"0":request.getParameter("Id");
		ValDocumento valDocumento 	= new ValDocumento(); 
		DipAlumno dipAlumno 		= new DipAlumno();
		DipCurso dipCurso 			= new DipCurso();
		boolean existe				= false;				
		if (valDocumentoDao.existeReg(id)) {
			existe = true;
			valDocumento = valDocumentoDao.mapeaRegId(id);
			if (valDocumento.getTipo().equals("Diploma")) {
				dipCurso	= dipCursoDao.mapeaRegId(valDocumento.getFolio());
				dipAlumno 	= dipAlumnoDao.mapeaRegId(valDocumento.getFolio(), valDocumento.getCodigoPersonal());
			}
		}
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("valDocumento", valDocumento);
		modelo.addAttribute("dipCurso", dipCurso);
		modelo.addAttribute("dipAlumno", dipAlumno);
		
		return "validaDocumento";
	}
	
	@ResponseBody
	@RequestMapping("/esMovil")
	public String esMovil(HttpServletRequest request){		
		String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	sesion.setAttribute("esMovil", tipo);
        }		
		return tipo;
	}
	
	@RequestMapping("/diplomaPdf")
	public String diplomaPdf(HttpServletRequest request, Model modelo){
		
		String diplomaId 			= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
		String codigoPersonal 		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		DipAlumno dipAlumno 		= new DipAlumno();
		DipCurso dipCurso 			= new DipCurso();		
		
		if(dipAlumnoDao.existeReg(diplomaId, codigoPersonal)) {
			dipAlumno = dipAlumnoDao.mapeaRegId(diplomaId, codigoPersonal);
		}
		
		if (dipCursoDao.existeReg(dipAlumno.getDiplomaId())) {
			dipCurso = dipCursoDao.mapeaRegId(dipAlumno.getDiplomaId());
		}
		
		modelo.addAttribute("dipCurso", dipCurso);
		modelo.addAttribute("dipAlumno", dipAlumno);
		
		return "diplomaPdf";
		
	}
}
