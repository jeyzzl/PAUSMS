package aca.web.usuarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.menu.spring.Menu;
import aca.menu.spring.MenuDao;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.rol.spring.Rol;
import aca.rol.spring.RolDao;
import aca.rol.spring.RolOpcion;
import aca.rol.spring.RolOpcionDao;

@Controller
public class ContUsuariosRoles {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	RolDao rolDao;
	
	@Autowired
	RolOpcionDao rolOpcionDao;
	
	@Autowired
	MenuDao menuDao;
	
	@Autowired
	ModuloDao moduloDao;	
	
	@Autowired
	ModuloOpcionDao moduloOpcionDao;
	
	
	@RequestMapping("/usuarios/roles/roles")
	public String usuariosRolesRoles(HttpServletRequest request, Model modelo){
		
		List<Rol> lisRol 						= rolDao.getListAll(" ORDER BY ROL_ID");
		String accionFmt 						= request.getParameter("accionFmt")==null?"0":request.getParameter("accionFmt");
		HashMap<String, String> mapaOpciones 	= rolOpcionDao.mapaTotalOpciones("ORDER BY ROL_ID");
		
		modelo.addAttribute("lisRol", lisRol);
		modelo.addAttribute("accionFmt", accionFmt);
		modelo.addAttribute("mapaOpciones", mapaOpciones);
		
		return "usuarios/roles/roles";
	}	
	
	@RequestMapping("/usuarios/roles/editar")
	public String usuariosRolesAgregar(HttpServletRequest request, Model modelo){		
		
		String rolId 			= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
		
		Rol rol = new Rol();
		if (rolDao.existeReg(rolId)){
			rol = rolDao.mapeaRegId(rolId);
		}
		
		modelo.addAttribute("rol", rol);	
		
		return "usuarios/roles/editar";
	}
	
	@RequestMapping("/usuarios/roles/grabar")
	public String usuariosRolesGrabar(HttpServletRequest request){
		String rolId 			= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
		String rolNombre 		= request.getParameter("nombre")==null?"0":request.getParameter("nombre");		
		String mensaje = "-";
		Rol rol = new Rol();
		if (rolDao.existeReg(rolId)) {
			rol.setRolId(rolId);
			rol.setRolNombre(rolNombre);
			if (rolDao.updateReg(rol)) {
				mensaje = "3";
			}else {
				mensaje = "4";
			}
		}else{
			rolId = rolDao.maximoReg();
			rol.setRolId(rolId);
			rol.setRolNombre(rolNombre);
			if (rolDao.insertReg(rol)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/usuarios/roles/editar?rolId="+rolId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/usuarios/roles/borrar")
	public String usuariosRolesBorrar(HttpServletRequest request){
		String rolId 			= request.getParameter("rolId")==null?"0":request.getParameter("rolId");				
		String mensaje = "-";
		
		if (rolDao.existeReg(rolId)) {			
			if (rolDao.deleteReg(rolId)) {
				mensaje = "5";
			}else {
				mensaje = "6";
			}
		}
		
		return "redirect:/usuarios/roles/roles?Mensaje="+mensaje;
	}	
	
	@RequestMapping("/usuarios/roles/opciones")
	public String usuariosRolesOpciones(HttpServletRequest request, Model modelo){
		
		String filtro			= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
		String rolId 			= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
		String rolNombre 		= rolDao.getNombreTipo(rolId);
		if (filtro.equals("T")) filtro = "'A','R'"; else filtro = "'"+filtro+"'";
		List<Menu> lisMenus 					= menuDao.lisPorEstados(filtro," ORDER BY ORDEN, NOMBRE_INGLES");
		List<Modulo> lisModulos 				= moduloDao.lisPorEstados(filtro, " ORDER BY NOMBRE_INGLES");
		List<ModuloOpcion> lisOpciones 			= moduloOpcionDao.lisPorEstado(filtro," ORDER BY NOMBRE_INGLES");
		HashMap<String, String> mapaOpciones 	= rolOpcionDao.mapaOpciones(rolId);
		HashMap<String, String> mapaPorModulo 	= rolOpcionDao.mapaOpcionesPorModulo(rolId);
		
		modelo.addAttribute("rolNombre", rolNombre);
		modelo.addAttribute("lisMenus", lisMenus);
		modelo.addAttribute("lisModulos", lisModulos);
		modelo.addAttribute("lisOpciones", lisOpciones);
		modelo.addAttribute("mapaOpciones", mapaOpciones);
		modelo.addAttribute("mapaPorModulo", mapaPorModulo);
		
		return "usuarios/roles/opciones";
	}
	
	@RequestMapping("/usuarios/roles/accion")
	@ResponseBody
	public String usuariosRolesAccion(HttpServletRequest request){		
		String agregados 		= request.getParameter("Agregados");
		String rolId	 		= request.getParameter("rolId");
		String mensaje 			= "ok";
		RolOpcion rolOpcion		= new RolOpcion();
		
		String [] arr = agregados.split(",");
		List<String> lisAsignar = new ArrayList<String>(Arrays.asList(arr));	
		rolOpcion.setRolId(rolId);
		//Quitar los privilegios
		rolOpcionDao.deleteTodos(rolId);
		//Poner los privilegios a los roles 
		for(int i=0; i<lisAsignar.size(); i++){
			rolOpcion.setOpcionId(lisAsignar.get(i));
			if(rolOpcionDao.existeReg(rolId, lisAsignar.get(i))==false){
				rolOpcionDao.insertReg(rolOpcion);
			}
		}
		
		return mensaje;
	}
	
	@RequestMapping("/usuarios/roles/menuopciones")
	public String usuariosRolesMenuOpciones(HttpServletRequest request, Model modelo){		
		
		String rolId 			= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
		
		List<ModuloOpcion> lisOpciones 			= moduloOpcionDao.lisOpcionesDelRol(rolId, " ORDER BY MODULO_ID, NOMBRE_OPCION");
		HashMap<String, String> mapaMenus 		= menuDao.mapaMenusIngles();
		HashMap<String, Modulo> mapaModulos 	= moduloDao.mapModulos();
		
		modelo.addAttribute("lisOpciones", lisOpciones);
		modelo.addAttribute("mapaMenus", mapaMenus);
		modelo.addAttribute("mapaModulos", mapaModulos);
		
		return "usuarios/roles/menuopciones";
	}
	
}