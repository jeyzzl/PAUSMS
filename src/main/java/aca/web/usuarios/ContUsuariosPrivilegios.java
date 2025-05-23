package aca.web.usuarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoClave;
import aca.acceso.spring.AccesoClaveDao;
import aca.acceso.spring.AccesoDao;
import aca.acceso.spring.AccesoOpcion;
import aca.acceso.spring.AccesoOpcionDao;
import aca.acceso.spring.AccesoOpcionUnavDao;
import aca.acceso.spring.AccesoPlan;
import aca.acceso.spring.AccesoPlanDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.menu.spring.Menu;
import aca.menu.spring.MenuDao;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.menu.spring.ModuloOpcionUnavDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.rol.spring.Rol;
import aca.rol.spring.RolDao;
import aca.rol.spring.RolOpcionDao;
import aca.vista.spring.ModOpcion;
import aca.vista.spring.ModOpcionDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContUsuariosPrivilegios {	
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AccesoClaveDao accesoClaveDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private ModuloDao moduloDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private ModuloOpcionDao moduloOpcionDao;
	
	@Autowired
	private ModuloOpcionUnavDao moduloOpcionUnavDao;
	
	@Autowired
	private RolDao rolDao;
	
	@Autowired
	private LogOperacionDao logOperacionDao;
	
	@Autowired
	RolOpcionDao rolOpcionDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AccesoOpcionDao accesoOpcionDao;	
	
	@Autowired
	AccesoOpcionUnavDao accesoOpcionUnavDao;
	
	@Autowired
	ModOpcionDao modOpcionDao;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;	
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private AccesoPlanDao accesoPlanDao;
	
	
	@ResponseBody
	@RequestMapping("/usuarios/privilegios/accion")
	public String usuariosPrivilegiosAccion(HttpServletRequest request){
		
		String codigoUltimo 	= "0";
		String codigoPersonal	= "0";
		String agregados 		= request.getParameter("Agregados");
		int grabados 			= 0;
		int borrados 			= 0;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUltimo 		= (String) sesion.getAttribute("codigoUltimo");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String [] arr = agregados.split("-->");
		List<String> listaAsignar = new ArrayList<String>(Arrays.asList(arr));		
		List<ModuloOpcion> lisOpciones = moduloOpcionDao.lisTodas("");
		AccesoOpcion acceso = new AccesoOpcion();
		for(ModuloOpcion moduloOpcion  : lisOpciones) {
			
			if (listaAsignar.contains(moduloOpcion.getOpcionId())) {				
				//Grabar si no existe				
				acceso.setCodigoPersonal(codigoUltimo);
				acceso.setOpcionId(moduloOpcion.getOpcionId());
				acceso.setUsuario(codigoPersonal);
				if (accesoOpcionDao.existeReg(codigoUltimo, moduloOpcion.getOpcionId())==false) {
					if (accesoOpcionDao.insertReg(acceso)) {
						grabados++;
					}
				}				
			}else {
				//Borrar si ya existe
				if (accesoOpcionDao.existeReg(codigoUltimo, moduloOpcion.getOpcionId())) {
					if (accesoOpcionDao.deleteReg(codigoUltimo, moduloOpcion.getOpcionId())) {
						borrados++;
					}
				}
			}
		}
		return "Added: "+String.valueOf(grabados)+", Deleted: "+borrados;
	}

	@RequestMapping("/usuarios/privilegios/clave")
	public String usuariosPrivilegiosClave(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String usuario 			= request.getParameter("Usuario")==null?"-":request.getParameter("Usuario");
		String msj	 			= request.getParameter("Msj")==null?"":request.getParameter("Msj");
		
		Acceso acceso = new Acceso();
		
		if(accesoDao.existeReg(codigoPersonal)) {
			acceso = accesoDao.mapeaRegId(codigoPersonal);
		}else {
			acceso.setUsuario(usuario);
			acceso.setCodigoPersonal(codigoPersonal);
		}
		
		String nombre = usuariosDao.getNombreUsuario(codigoPersonal,"NOMBRE");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("msj", msj);
		
		return "usuarios/privilegios/clave";
	}
	
	@Transactional
	@RequestMapping("/usuarios/privilegios/grabar")
	public String usuariosPrivilegiosGrabar(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String usuario 			= request.getParameter("Usuario")==null?"-":request.getParameter("Usuario");
		String clave			= request.getParameter("Clave")==null?"-":aca.util.Encriptar.sha512ConBase64(request.getParameter("Clave"));
		String claveHexa		= request.getParameter("Clave")==null?"-":bCryptPasswordEncoder.encode(request.getParameter("Clave"));
		String msj 				= "";	
		
		AccesoClave aClave = new AccesoClave();
		aClave.setCodigoPersonal(codigoPersonal);
		aClave.setFecha(aca.util.Fecha.getHoy());
		aClave.setIp(request.getRemoteAddr());
		aClave.setClave(clave);
		aClave.setFolio(1);
		
		if ( !accesoDao.existeUsuario(codigoPersonal, usuario)){
			Acceso acceso = new Acceso();
			acceso.setCodigoPersonal(codigoPersonal);
			acceso.setUsuario(usuario);
			acceso.setClave(clave);		
			if (accesoDao.existeReg(codigoPersonal)){
				if (accesoDao.updateClave(acceso.getUsuario(), acceso.getClave(), acceso.getCodigoPersonal())){			
					msj = "Updated: "+acceso.getCodigoPersonal();	
					if(!accesoClaveDao.existeReg(codigoPersonal, 1)) {
						accesoClaveDao.insertReg(aClave);						
					}else {
						accesoClaveDao.updateReg(aClave);						
					}
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)) {
						//System.out.println("Update bycript");
					}
				}else{
					msj = "Error updating: "+acceso.getCodigoPersonal();
				}
			}else{
				acceso.setAdministrador("N");
				acceso.setSupervisor("N");
				acceso.setCotejador("N");
				acceso.setPortalAlumno("N");
				acceso.setPortalMaestro("N");
				acceso.setModalidad("0");
				acceso.setAccesos("-");
				if (accesoDao.insertReg(acceso)){				
					msj = "Saved: "+acceso.getCodigoPersonal();	
					if(!accesoClaveDao.existeReg(codigoPersonal, 1)) {
						accesoClaveDao.insertReg(aClave);		
					}else {
						accesoClaveDao.updateReg(aClave);
					}
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)) {
						//System.out.println("Update bycript");
					}
				}else{
					msj = "Error saving: "+acceso.getCodigoPersonal();
				}					
			}
		}else{				
			msj = "Username already in use: "+usuario+" !";			
		}
		
		return "redirect:/usuarios/privilegios/clave?Msj="+msj+"&Codigo="+codigoPersonal;
	}
	
	@RequestMapping("/usuarios/privilegios/usuario")
	public String usuariosPrivilegiosUsuario(HttpServletRequest request, Model modelo){
		String codigoUltimo		= "0";
		Acceso acceso 			= new Acceso();				
		String usuarioNombre	= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUltimo 		= (String) sesion.getAttribute("codigoUltimo");
			usuarioNombre 		=  usuariosDao.getNombreUsuario(codigoUltimo,"NOMBRE");
			if (accesoDao.existeReg( codigoUltimo)){
				acceso 			= accesoDao.mapeaRegId(codigoUltimo);			
			}		
		}
		
	//	List<CatCarrera> lisCarreras 					= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID,NIVEL_ID,NOMBRE_CARRERA");
		List<aca.menu.spring.Modulo> lisModulos 		= moduloDao.LisModulosDelUsuario(codigoUltimo);
		List<ModuloOpcion> lisOpciones 					= moduloOpcionDao.lisOpcionesDelUsuario( codigoUltimo);
		List<Rol> lisRoles 								= rolDao.getListAll( "ORDER BY ROL_ID");
		List<MapaPlan> lisPlanes						= mapaPlanDao.lisConAcceso(codigoUltimo, "ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID), CARRERA_SE");
		HashMap<String, String> mapaMenus 				= moduloDao.mapaNombreIngles();
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("usuarioNombre", usuarioNombre);
	//	modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisModulos", lisModulos);
		modelo.addAttribute("lisOpciones", lisOpciones);
		modelo.addAttribute("lisRoles", lisRoles);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaMenus", mapaMenus);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "usuarios/privilegios/usuario";
	}
	
	@RequestMapping("/usuarios/privilegios/carreras")
	public String usuariosPrivilegiosCarreras(HttpServletRequest request, Model modelo){
				 
		String codigoUltimo 	= "0";
		String usuarioNombre	= "-";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUltimo 		= (String) sesion.getAttribute("codigoUltimo");
			usuarioNombre 		= usuariosDao.getNombreUsuario(codigoUltimo, "NOMBRE");				
		}
		
		List<String> lisPorUsuario = accesoPlanDao.lisPorUsuario(codigoUltimo, "ORDER BY CARRERA_ID");	 
		List<MapaPlan> lisPlanes							= mapaPlanDao.getListAll("ORDER BY PLAN_ID");
		
		HashMap<String, CatFacultad> mapaFacultadNombre		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreraNombre		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaPracticasCarreras		= mapaCursoDao.mapaPracticasEnCarreras();
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad(""); 
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll(""); 

		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("mapaPracticasCarreras", mapaPracticasCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisPorUsuario", lisPorUsuario);
		modelo.addAttribute("mapaFacultadNombre", mapaFacultadNombre);
		modelo.addAttribute("mapaCarreraNombre", mapaCarreraNombre);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "usuarios/privilegios/carreras";
	}
	
	@RequestMapping("/usuarios/privilegios/grabarCarreras")
	public String usuariosPrivilegiosGrabarCarreras(HttpServletRequest request, Model modelo){		
		String codigo 	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String codigoPersonal = "";
		String mensaje = "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		int grabados = 0;
		int borrados = 0;
		
		List<MapaPlan> lisPlanes = mapaPlanDao.getListAll("ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID), CARRERA_SE");
		for (MapaPlan plan : lisPlanes) {
			if (request.getParameter("Check"+plan.getPlanId()) == null){
				//Quitar Plan
				if(accesoPlanDao.existeReg(codigo, plan.getPlanId()) == true) {
					if(accesoPlanDao.deleteReg(codigo, plan.getPlanId())){
						borrados++;
					}
				}
			}else {
				//Agregar Plan
				if(accesoPlanDao.existeReg(codigo, plan.getPlanId()) == false) {
					AccesoPlan accesoPlan = new AccesoPlan();
					accesoPlan.setCodigoPersonal(codigo);
					accesoPlan.setPlanId(plan.getPlanId());
					accesoPlan.setCarreraId(plan.getCarreraId());
					accesoPlan.setUsuario(codigoPersonal);
					if(accesoPlanDao.insertReg(accesoPlan)) {
						grabados++;
					}
				}
			}
		}	
		
		mensaje = mensaje + " Added: "+grabados+" Deleted: "+borrados;
					
		return "redirect:/usuarios/privilegios/carreras?Codigo="+codigo;
	}

	@RequestMapping("/usuarios/privilegios/grabarPrivilegios")
	public String usuariosPrivilegiosGrabarPrivilegios(HttpServletRequest request){
		String codigoUltimo		= request.getParameter("Codigo") == null? "0":request.getParameter("Codigo");
		Acceso acceso 			= new Acceso();
		String admin 			= request.getParameter("Admin") == null? "N":request.getParameter("Admin");
		String supervisor		= request.getParameter("Supervisor") == null? "N":request.getParameter("Supervisor");
		String coteja 			= request.getParameter("Coteja") == null?"N":request.getParameter("Coteja");	
		String convalida 		= request.getParameter("Convalidador") == null ? "N":request.getParameter("Convalidador");
		String becas 			= request.getParameter("Becas") == null? "N":request.getParameter("Becas");
		String portalAlumno		= request.getParameter("PortalAlumno") == null? "N":request.getParameter("PortalAlumno");
		String portalMaestro	= request.getParameter("PortalMaestro") == null? "N":request.getParameter("PortalMaestro");
		String enLinea			= request.getParameter("EnLinea") == null? "N":request.getParameter("EnLinea");
		String busca			= request.getParameter("Busca") == null? "N":request.getParameter("Busca");
		String modalidad 		= request.getParameter("Modalidad")== null? "0":request.getParameter("Modalidad");
		
		String mensaje 			= (String) request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");		
		 
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			if (accesoDao.existeReg(codigoUltimo)){
				acceso 			= accesoDao.mapeaRegId(codigoUltimo);			
			}
		}

		acceso.setCodigoPersonal(codigoUltimo);
		if (accesoDao.existeReg(codigoUltimo)){
			acceso = accesoDao.mapeaRegId(codigoUltimo);
			acceso.setAdministrador(admin);
			acceso.setSupervisor(supervisor);
			acceso.setCotejador(coteja);
			acceso.setModalidad(modalidad);
			acceso.setBecas(becas);
			acceso.setPortalAlumno(portalAlumno);
			acceso.setPortalMaestro(portalMaestro);
			acceso.setConvalida(convalida);
			acceso.setIdioma("en");			
			acceso.setMenu("1");
			acceso.setEnLinea(enLinea);	
			acceso.setBuscaAdmin(busca);			

			if (accesoDao.updateReg(acceso)){
				mensaje = "Saved";
			}else{
				mensaje = "Error saving";
			}			
		}else{
			String clave = codigoUltimo;		
			String claveDigest	= aca.util.Encriptar.sha512ConBase64(clave);
			if (codigoUltimo.charAt(0)=='0' || codigoUltimo.charAt(0)=='1' || codigoUltimo.charAt(0)=='2' || codigoUltimo.charAt(0) == '3'){
				acceso.setClaveInicial(clave);
				acceso.setClave(claveDigest);
			}
			acceso.setAdministrador(admin);
			acceso.setSupervisor(supervisor);
			acceso.setCotejador(coteja);
			acceso.setModalidad(modalidad);
			acceso.setBecas(becas);
			acceso.setConvalida(convalida);			
			acceso.setPortalAlumno(portalAlumno);
			acceso.setPortalMaestro(portalMaestro);
			acceso.setIdioma("en");
			if (accesoDao.insertReg(acceso)){
				mensaje = "Saved";
			}
		}			
		return "redirect:/usuarios/privilegios/usuario?Mensaje="+mensaje;
	}

	@RequestMapping("/usuarios/privilegios/grabarRole")
	public String usuariosPrivilegiosGrabarRole(HttpServletRequest request){
		
		String codigoUltimo		= request.getParameter("Codigo") == null? "0":request.getParameter("Codigo");
		String rol				= request.getParameter("rol")==null?"0":request.getParameter("rol");
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisOpciones = rolOpcionDao.lisPorRol(rol,"");
		AccesoOpcion acceso = new AccesoOpcion();
		for (String opcion : lisOpciones){
			if (accesoOpcionDao.existeReg(codigoUltimo, opcion)==false){
				acceso.setCodigoPersonal(codigoUltimo);
				acceso.setOpcionId(opcion);
				acceso.setUsuario(codigoPersonal);
				accesoOpcionDao.insertReg(acceso);
			}
		}	
		return "redirect:/usuarios/privilegios/usuario";
	}

	@RequestMapping("/usuarios/privilegios/eliminar")
	public String usuariosPrivilegiosEliminar(HttpServletRequest request){
		String codigoUltimo		= "0";
		String codigoPersonal	= "0";
		String mensaje			= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUltimo 		= (String) sesion.getAttribute("codigoUltimo");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String datos = "Updated user:"+codigoUltimo + ", Remove all privileges";		
		LogOperacion logOperacion = new LogOperacion();
		int borrados = accesoOpcionDao.deletePorUsuario(codigoUltimo);
		if (borrados >= 0){
			logOperacion.setTabla("MODULO_OPCION");
			logOperacion.setFecha(aca.util.Fecha.getHoy());
			logOperacion.setIp(request.getRemoteAddr());
			logOperacion.setDatos(datos);
			logOperacion.setUsuario(codigoPersonal);
			logOperacionDao.insertReg(logOperacion);
			mensaje = "Deleted:"+borrados;
		}		
		
		return "redirect:/usuarios/privilegios/usuario?Mensaje="+mensaje;
	}
	
	@RequestMapping("/usuarios/privilegios/aplicacion")
	public String usuariosPrivilegiosAplicacion(HttpServletRequest request, Model modelo){
		
		String filtro				= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
		String codigoUltimo 		= "0";	
		String nombre 				= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUltimo 		= (String) sesion.getAttribute("codigoUltimo");
			nombre 				= usuariosDao.getNombreUsuario(codigoUltimo,"NOMBRE");
		}
		
		if (filtro.equals("T")) filtro = "'A','R'"; else filtro = "'"+filtro+"'";
		List<Menu> lisMenus 					= menuDao.lisPorEstados(filtro," ORDER BY ORDEN, NOMBRE_INGLES");
		List<Modulo> lisModulos 				= moduloDao.lisPorEstados(filtro, " ORDER BY NOMBRE_INGLES");
		List<ModuloOpcion> lisOpciones 			= moduloOpcionDao.lisPorEstado(filtro," ORDER BY NOMBRE_INGLES");
		
		HashMap<String,String> mapaAsignados	= moduloOpcionDao.mapOpcionesPorUsuario(codigoUltimo);
		HashMap<String,String> mapaTotales		= moduloOpcionDao.mapTotalOpciones();
		HashMap<String,String> mapaOpciones		= accesoOpcionDao.mapOpcionesPorUsuario(codigoUltimo);
		
		modelo.addAttribute("lisMenus", lisMenus);
		modelo.addAttribute("lisModulos", lisModulos);
		modelo.addAttribute("lisOpciones", lisOpciones);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaTotales", mapaTotales);
		modelo.addAttribute("mapaOpciones", mapaOpciones);		
		
		return "usuarios/privilegios/aplicacion";
	}
	
	@RequestMapping("/usuarios/privilegios/copiar")
	public String usuariosPrivilegiosCopiar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "";			
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoUltimo");
		}
		String codigoBuscar 	= request.getParameter("Buscar")==null?"0":request.getParameter("Buscar");
		boolean existeCodigo	= usuariosDao.existeReg(codigoBuscar);
		String nombrePrincipal	= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
		String nombreSecundario	= usuariosDao.getNombreUsuario(codigoBuscar, "NOMBRE");
		
		List<ModOpcion> lisPrincipal 			= modOpcionDao.lisPorUsuario(codigoPersonal, "ORDER BY (SELECT MENU_ID FROM MODULO WHERE MODULO_ID = MOD_OPCION.MODULO_ID),NOMBRE_MODULO,NOMBRE_OPCION");		
		List<ModOpcion> lisSecundario 			= modOpcionDao.lisPorUsuario(codigoBuscar, "ORDER BY (SELECT MENU_ID FROM MODULO WHERE MODULO_ID = MOD_OPCION.MODULO_ID),NOMBRE_MODULO,NOMBRE_OPCION");		
		HashMap<String, String> mapaMenus 		= moduloDao.mapaNombreIngles();
		HashMap<String, String> mapaPrincipal 	= accesoOpcionDao.mapOpcionesPorUsuario(codigoPersonal);
		HashMap<String, String> mapaSecundario 	= accesoOpcionDao.mapOpcionesPorUsuario(codigoBuscar);
		
		modelo.addAttribute("existeCodigo", existeCodigo);
		modelo.addAttribute("nombrePrincipal", nombrePrincipal);
		modelo.addAttribute("nombreSecundario", nombreSecundario);
		
		modelo.addAttribute("lisPrincipal", lisPrincipal);
		modelo.addAttribute("lisSecundario", lisSecundario);
		modelo.addAttribute("mapaMenus", mapaMenus);
		modelo.addAttribute("mapaPrincipal", mapaPrincipal);
		modelo.addAttribute("mapaSecundario", mapaSecundario);
		
		return "usuarios/privilegios/copiar";
	}
	
	@RequestMapping("/usuarios/privilegios/copiarTodo")
	public String usuariosPrivilegiosCopiarTodo(HttpServletRequest request){
		
		String origen 				= request.getParameter("Origen")==null?"0":request.getParameter("Origen");
		String destino 				= request.getParameter("Destino")==null?"0":request.getParameter("Destino");
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<ModOpcion> lisOrigen 	= modOpcionDao.lisPorUsuario(origen, " ORDER BY OPCION_ID");
		AccesoOpcion accesoOpcion 	= new AccesoOpcion();
		for(ModOpcion opcion : lisOrigen){
			if (accesoOpcionDao.existeReg(destino, opcion.getOpcionId())==false){
				accesoOpcion.setCodigoPersonal(destino);
				accesoOpcion.setOpcionId(opcion.getOpcionId());
				accesoOpcion.setUsuario(codigoPersonal);
				accesoOpcionDao.insertReg(accesoOpcion);
			}
		}		
		return "redirect:/usuarios/privilegios/copiar?Buscar="+destino;
	}
	
	@RequestMapping("/usuarios/privilegios/copiarOpcion")
	public String usuariosPrivilegiosCopiarOpcion(HttpServletRequest request){
		
		String buscar 				= request.getParameter("Buscar")==null?"0":request.getParameter("Buscar");
		String destino 				= request.getParameter("Destino")==null?"0":request.getParameter("Destino");
		String opcionId				= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		AccesoOpcion accesoOpcion 	= new AccesoOpcion();		
		if (accesoOpcionDao.existeReg(destino, opcionId)==false){
			accesoOpcion.setCodigoPersonal(destino);
			accesoOpcion.setOpcionId(opcionId);
			accesoOpcion.setUsuario(codigoPersonal);
			accesoOpcionDao.insertReg(accesoOpcion);
		}		
		return "redirect:/usuarios/privilegios/copiar?Buscar="+buscar;
	}

	
	@RequestMapping("/usuarios/privilegios/grabarOpciones")
	public String usuariosPrivilegiosGrabarOpciones(HttpServletRequest request){
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
//		List<ModuloOpcion> lisOpciones = moduloOpcionUnavDao.lisTodas(" ORDER BY OPCION_ID");
//		//Borrar todos los privilegios
//		accesoOpcionUnavDao.deleteTodo();
//		for (ModuloOpcion opcion : lisOpciones) {
//			String[] arrUsuarios = opcion.getUsuarios().split("-");
//			System.out.println("Opcion:"+opcion.getOpcionId()+":"+opcion.getNombreOpcion()+arrUsuarios.length);
//			for (String usuario : arrUsuarios) {
//				if (usuario.length()==7) {
//					AccesoOpcion acceso = new AccesoOpcion();
//					acceso.setCodigoPersonal(usuario);
//					acceso.setOpcionId(opcion.getOpcionId());
//					acceso.setUsuario(codigoPersonal);
//					if (accesoOpcionUnavDao.existeReg(usuario, opcion.getOpcionId())==false){
//						accesoOpcionUnavDao.insertReg(acceso);
//					}
//				}
//			}
//		}
		return "redirect:/usuarios/privilegios/usuario";
	}
	
}