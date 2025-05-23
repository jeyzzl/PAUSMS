package aca.web.usuarios;

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
import aca.acceso.spring.AccesoOpcion;
import aca.acceso.spring.AccesoOpcionDao;
import aca.menu.spring.Menu;
import aca.menu.spring.MenuDao;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContUsuariosAplicaciones {			
	
	@Autowired	
	private MenuDao menuDao;
	
	@Autowired	
	private ModuloDao moduloDao;
	
	@Autowired	
	private ModuloOpcionDao moduloOpcionDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	MaestrosDao mestrosDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AccesoOpcionDao accesoOpcionDao; 
	
	
	@RequestMapping("/usuarios/aplicaciones/menu")
	public String usuariosAplicacionesMenu(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String admin			= "N";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();			
		}	
		
		int alumnosSinPortal		= accesoOpcionDao.alumnosSinPortal("1160");
		int maestrosSinPortal		= accesoOpcionDao.maestrosSinPortal("1161");
		int mentorSinPortal			= accesoOpcionDao.mentorSinPortal("1162");
		int maestrosSinPortafolio	= accesoOpcionDao.maestrosSinPortafolio("1163");
		
		int maestrosSinPlanDiamante	= 0;
		List<String> lisMaestros	= accesoOpcionDao.lisEditores();
		for(String codigo : lisMaestros){
			if (accesoOpcionDao.existeReg(codigo, "038")==false) {
				maestrosSinPlanDiamante++;
			}
		}	
		
		List<Menu> lisMenus 					= menuDao.getListAll("ORDER BY ORDEN");
		HashMap<String,String> mapaModulos 		= moduloDao.mapMenuModulos();
		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("alumnosSinPortal", alumnosSinPortal);
		modelo.addAttribute("maestrosSinPortal", maestrosSinPortal);
		modelo.addAttribute("mentorSinPortal", mentorSinPortal);
		modelo.addAttribute("maestrosSinPortafolio", maestrosSinPortafolio);
		modelo.addAttribute("maestrosSinPlanDiamante", maestrosSinPlanDiamante);
		modelo.addAttribute("lisMenus", lisMenus);
		modelo.addAttribute("mapaModulos", mapaModulos);
		
		return "usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarAlumnos")
	public String usuariosAplicacionesGrabarAlumnos(HttpServletRequest request){	
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisAlumnos			= accesoOpcionDao.lisAlumnosSinPortal("1160");
		AccesoOpcion acceso = new AccesoOpcion();
		acceso.setOpcionId("1160");
		acceso.setUsuario(codigoPersonal);
		for(String codigo : lisAlumnos){
			acceso.setCodigoPersonal(codigo);
			if (accesoOpcionDao.existeReg(codigo, "1160")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}		
		return "redirect:/usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarMaestros")
	public String usuariosAplicacionesGrabarMaestros(HttpServletRequest request){
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisMaestros			= accesoOpcionDao.lisMaestrosSinPortal("1161");
		AccesoOpcion acceso = new AccesoOpcion();
		acceso.setOpcionId("1161");
		acceso.setUsuario(codigoPersonal);
		// Graba maestros principales de la materia
		for(String codigo : lisMaestros){
			acceso.setCodigoPersonal(codigo);
			if (accesoOpcionDao.existeReg(codigo, "1161")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}
		List<String> lisAuxiliares			= accesoOpcionDao.lisAuxiliaresSinPortal("1161");
		// Graba maestros auxiliares en materias
		for(String codigo : lisAuxiliares){
			acceso.setCodigoPersonal(codigo);
			if (accesoOpcionDao.existeReg(codigo, "1161")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}
		
		return "redirect:/usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarMentores")
	public String usuariosAplicacionesGrabarMentores(HttpServletRequest request){
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisMentores	= accesoOpcionDao.lisMentoresSinPortal("1162");
		AccesoOpcion acceso = new AccesoOpcion();
		acceso.setOpcionId("1162");
		acceso.setUsuario(codigoPersonal);
		for(String codigo : lisMentores){
			acceso.setCodigoPersonal(codigo);
			if (accesoOpcionDao.existeReg(codigo, "1162")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}		
		return "redirect:/usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarPortafolios")
	public String usuariosAplicacionesGrabarPortafolios(HttpServletRequest request){
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisMaestros	= accesoOpcionDao.lisMaestrosSinPortafolio("1163");
		AccesoOpcion acceso = new AccesoOpcion();
		acceso.setOpcionId("1163");
		acceso.setUsuario(codigoPersonal);
		for(String codigo : lisMaestros){
			acceso.setCodigoPersonal(codigo);
			if (accesoOpcionDao.existeReg(codigo, "1163")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}		
		return "redirect:/usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarPlanDiamante")
	public String usuariosAplicacionesGrabarPlanDiamante(HttpServletRequest request){	
		
		String codigoPersonal	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<String> lisMaestros	= accesoOpcionDao.lisEditores();
		AccesoOpcion acceso = new AccesoOpcion();
		acceso.setOpcionId("038");
		acceso.setUsuario(codigoPersonal);
		for(String codigo : lisMaestros){
			acceso.setCodigoPersonal(codigo);
			//System.out.println("Grabando opcion 038 para "+codigo);
			if (accesoOpcionDao.existeReg(codigo, "038")==false) {
				accesoOpcionDao.insertReg(acceso);
			}
		}	
		return "redirect:/usuarios/aplicaciones/menu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/duplicados")
	public String usuariosAplicacionesDuplicados(HttpServletRequest request){	
		
		List<ModuloOpcion> lisOpciones			= moduloOpcionDao.lisTodas(" ORDER BY MODULO_ID, OPCION_ID");
		
		for(ModuloOpcion opcion :lisOpciones){
			String usuarios 	= opcion.getUsuarios();
			if (usuarios.length() > 1 && usuarios.charAt(0)=='-') {
				usuarios = usuarios.substring(1,usuarios.length()-1);	
			}		
				
			boolean duplicado = false;
			String arreglo[] 	= usuarios.split("-");
			String nuevosUsuarios = "";
			for(String arr : arreglo){
				if (!nuevosUsuarios.contains(arr)){
					nuevosUsuarios += "-"+arr;
				}else {
					duplicado = true;					
				}			
			}
			nuevosUsuarios += "-";
			if (duplicado) {
				System.out.println("Users O:"+opcion.getOpcionId()+":"+opcion.getUsuarios());
				System.out.println("Users N:"+opcion.getOpcionId()+":"+nuevosUsuarios);
				moduloOpcionDao.updateUsuarios(opcion.getOpcionId(), nuevosUsuarios);
			}				
		}		
		return "redirect:/usuarios/aplicaciones/menu";
	}

	@RequestMapping("/usuarios/aplicaciones/modulos")
	public String usuariosAplicacionesModulos(HttpServletRequest request, Model modelo){
		
		String menuId 		= request.getParameter("id")==null?"0":request.getParameter("id");
		String menuNombre 	= menuDao.menuNombreIngles(menuId);
		
		List<Modulo> lisModulos					= moduloDao.lisPorMenu(menuId, " ORDER BY NOMBRE_MODULO");
		List<ModuloOpcion> lisOpciones			= moduloOpcionDao.lisPorMenu(menuId, " ORDER BY MODULO_ID, NOMBRE_OPCION");		
		HashMap<String,String> mapaTotUsuarios 	= accesoOpcionDao.mapUsuariosPorOpcion();
		
		modelo.addAttribute("menuId", menuId);
		modelo.addAttribute("menuNombre", menuNombre);
		modelo.addAttribute("lisModulos", lisModulos);
		modelo.addAttribute("lisOpciones", lisOpciones);		
		modelo.addAttribute("mapaTotUsuarios", mapaTotUsuarios);
		
		return "usuarios/aplicaciones/modulos";
	}
	
	@RequestMapping("/usuarios/aplicaciones/aplicacion")
	public String usuariosAplicacionesAplicacion(HttpServletRequest request, Model modelo){		
		
		String opcion			= request.getParameter("opcion")==null?"0":request.getParameter("opcion");			
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String codigoPersonal	= "0";
		boolean esAdmin			= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			esAdmin				= accesoDao.esAdministrador( codigoPersonal);
		}
		
		ModuloOpcion moduloOpcion = new ModuloOpcion();
		if (moduloOpcionDao.existeReg(opcion)) {
			moduloOpcion = moduloOpcionDao.mapeaRegId(opcion);			
		}		
		String nombreOpcion 	= moduloOpcion.getNombreIngles();			
		String msj    			= "";
		
		//Actualizar Privilegios
		if(accion.equals("1")){
			List<Maestros> lisMaestros = mestrosDao.getListAll("ORDER BY CODIGO_PERSONAL");
			int grabados = 0;
			for(Maestros maestro: lisMaestros){				
				if( accesoOpcionDao.existeReg(maestro.getCodigoPersonal(), opcion)==false){
					AccesoOpcion acceso = new AccesoOpcion();
					acceso.setCodigoPersonal(maestro.getCodigoPersonal());
					acceso.setOpcionId(opcion);
					acceso.setUsuario(codigoPersonal);
					if(accesoOpcionDao.insertReg(acceso)){
						grabados++;
					}
				}
			}				
			if(grabados >= 1){			
				msj = "<div class='alert alert-success'>Privileges updated correctly</div>";	
			}else{
				msj = "<div class='alert alert-danger'>Error updating privileges</div>";
			}		
		}
		
		if(accion.equals("2")){
			accesoOpcionDao.deletePorOpcion(opcion);
			if(accesoOpcionDao.existeOpcion(opcion)==false){				
				msj = "<div class='alert alert-success'>Deleted Privileges</div>";	
			}else{
				msj = "<div class='alert alert-danger'>Error deleting privileges</div>";
			}		
		}
		
		if (accion.equals("3")){
			String codigoUsuario = request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
			if (accesoOpcionDao.deleteReg(codigoUsuario, opcion)){
				msj = "<div class='alert alert-success'>Deleted privilege from the user</div>";	
			}else {
				msj = "<div class='alert alert-danger'>Error deleting</div>";
			}	
		}
		
		if (accion.equals("4")){
			String codigoUsuario = request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
			int borrados = accesoOpcionDao.deletePorUsuario(codigoUsuario);
			if (borrados >= 1){
				msj = "<div class='alert alert-success'>Deleted all privileges from user "+borrados+"</div>";	
			}else {
				msj = "<div class='alert alert-danger'>Error deleting</div>";
			}			
		}
		
		List<Usuarios> lisUsuarios = usuariosDao.listaEnOpcion(opcion, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,String> mapaInscritos 	= inscritosDao.getMapaInscritos();
		HashMap<String,Maestros> mapaMaestros 	= mestrosDao.mapaMaestrosEnOpcion(opcion);
		HashMap<String,Acceso>	mapaAccesos 	= accesoDao.mapaAccesosEnOpcion(opcion);
		
		modelo.addAttribute("esAdmin", esAdmin);		
		modelo.addAttribute("nombreOpcion", nombreOpcion);
		modelo.addAttribute("mensaje", msj);
		modelo.addAttribute("lisUsuarios", lisUsuarios);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);		
		modelo.addAttribute("mapaAccesos", mapaAccesos);
		
		return "usuarios/aplicaciones/aplicacion";
	}

	@RequestMapping("/usuarios/aplicaciones/editarMenu")
	public String usuariosAplicacionesEditarMenu(HttpServletRequest request, Model modelo){
		
		String menuId 		= request.getParameter("id")==null?"0":request.getParameter("id");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		Menu menu = new Menu();
		
		if(menuDao.existeReg(menuId)) {
			menu = menuDao.mapeaRegId(menuId);
		}
		
		modelo.addAttribute("menu", menu);
		modelo.addAttribute("mensaje", mensaje);
		
		return "usuarios/aplicaciones/editarMenu";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarMenu")
	public String usuariosAplicacionesGrabarMenu(HttpServletRequest request){
		
		String menuId 		= request.getParameter("id")==null?"0":request.getParameter("id");
		String espanol 		= request.getParameter("Espanol")==null?"-":request.getParameter("Espanol");
		String ingles 		= request.getParameter("Ingles")==null?"-":request.getParameter("Ingles");
		String orden 		= request.getParameter("Orden")==null?"-":request.getParameter("Orden");
		String mensaje		= "";
		
		Menu menu = new Menu();
		
		if(menuDao.existeReg(menuId)) {
			menu = menuDao.mapeaRegId(menuId);
			menu.setMenuNombre(espanol);
			menu.setNombreIngles(ingles);
			menu.setOrden(orden);
			if(menuDao.updateReg(menu)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/usuarios/aplicaciones/editarMenu?id="+menuId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/usuarios/aplicaciones/editarModulo")
	public String usuariosAplicacionesEditarModulo(HttpServletRequest request, Model modelo){
		
		String moduloId 	= request.getParameter("id")==null?"0":request.getParameter("id");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		Modulo modulo = new Modulo();
		
		if(moduloDao.existeReg(moduloId)) {
			modulo = moduloDao.mapeaRegId(moduloId);
		}
		
		modelo.addAttribute("modulo", modulo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "usuarios/aplicaciones/editarModulo";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarModulo")
	public String usuariosAplicacionesGrabarNombreModulo(HttpServletRequest request){
		
		String moduloId 	= request.getParameter("id")==null?"0":request.getParameter("id");
		String espanol 		= request.getParameter("Espanol")==null?"-":request.getParameter("Espanol");
		String ingles 		= request.getParameter("Ingles")==null?"-":request.getParameter("Ingles");
		
		String mensaje		= "";
		
		Modulo modulo = new Modulo();
		
		if(moduloDao.existeReg(moduloId)) {
			modulo = moduloDao.mapeaRegId(moduloId);
			modulo.setNombreModulo(espanol);
			modulo.setNombreIngles(ingles);
			if(moduloDao.updateReg(modulo)) {
				mensaje = "1";
			}
		}		
		return "redirect:/usuarios/aplicaciones/editarModulo?id="+moduloId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/usuarios/aplicaciones/editarOpcion")
	public String usuariosAplicacionesEditarOpcion(HttpServletRequest request, Model modelo){
		
		String menuId 		= request.getParameter("id")==null?"0":request.getParameter("id");
		String opcionId 	= request.getParameter("OpcionId")==null?"0":request.getParameter("OpcionId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		ModuloOpcion moduloOpcion = new ModuloOpcion();
		
		if(moduloOpcionDao.existeReg(opcionId)) {
			moduloOpcion = moduloOpcionDao.mapeaRegId(opcionId);
		}
		
		modelo.addAttribute("moduloOpcion", moduloOpcion);
		modelo.addAttribute("menuId", menuId);
		modelo.addAttribute("mensaje", mensaje);
		
		return "usuarios/aplicaciones/editarOpcion";
	}
	
	@RequestMapping("/usuarios/aplicaciones/grabarOpcion")
	public String usuariosAplicacionesGrabarOpcion(HttpServletRequest request){
		
		String menuId 		= request.getParameter("id")==null?"0":request.getParameter("id");
		String opcionId 	= request.getParameter("OpcionId")==null?"0":request.getParameter("OpcionId");
		String espanol 		= request.getParameter("Espanol")==null?"-":request.getParameter("Espanol");
		String ingles 		= request.getParameter("Ingles")==null?"-":request.getParameter("Ingles");
		String icono 		= request.getParameter("Icono")==null?"-":request.getParameter("Icono");
		String usuarios		= request.getParameter("Usuarios")==null?"-":request.getParameter("Usuarios");
		String mensaje		= "";
		
		ModuloOpcion moduloOpcion = new ModuloOpcion();

		if(moduloOpcionDao.existeReg(opcionId)) {
			moduloOpcion = moduloOpcionDao.mapeaRegId(opcionId);
			moduloOpcion.setNombreIngles(ingles);
			moduloOpcion.setNombreOpcion(espanol);
			moduloOpcion.setIcono(icono);
			moduloOpcion.setUsuarios(usuarios);
			if(moduloOpcionDao.updateOpcion(moduloOpcion)) {
				mensaje = "1";
			}
		}	
		
		return "redirect:/usuarios/aplicaciones/editarOpcion?id="+menuId+"&OpcionId="+opcionId+"&Mensaje="+mensaje;
	}
	
}