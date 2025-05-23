package aca.web.investigacion;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.investiga.spring.InvArchivoDao;
import aca.investiga.spring.InvComentarioDao;
import aca.investiga.spring.InvProyecto;
import aca.investiga.spring.InvProyectoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContInvestigacionAdmin {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	InvArchivoDao invArchivoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	InvProyectoDao invProyectoDao;
	
	@Autowired
	InvComentarioDao invComentarioDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/investigacion/admin/ayuda")
	public String investigacionAdminAyuda(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionAdminAyuda:");
		return "investigacion/admin/ayuda";
	}
	
	@RequestMapping("/investigacion/admin/accion")
	public String investigacionAdminAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionAdminAccion:");
		return "investigacion/admin/accion";
	}
	
	@RequestMapping("/investigacion/admin/bitacora")
	public String investigacionAdminBitacora(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionAdminBitacora:");
		return "investigacion/admin/bitacora";
	}
	
	@RequestMapping("/investigacion/admin/comentario")
	public String investigacionAdminComentario(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionAdminComentario:");
		return "investigacion/admin/comentario";
	}
	
	@RequestMapping("/investigacion/admin/proyecto")
	public String investigacionAdminProyecto(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";	
		String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
		String usuarioNombre	= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){		
			codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
			usuarioNombre 		= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE"); 
		}		
		String facultades		= catFacultadDao.getFacultadInvReferente(codigoPersonal);
		
		
		boolean admin			= false;
		boolean adminEtica		= false;
			
		// Usuarios administradores
		if (codigoPersonal.equals("9800308")||codigoPersonal.equals("9800079") || codigoPersonal.equals("9800306") || codigoPersonal.equals("9801139") 
			||codigoPersonal.equals("9801092") || codigoPersonal.equals("9830367") || codigoPersonal.equals("9801240") || codigoPersonal.equals("9830152") || codigoPersonal.equals("9800512")) admin = true;
		if (codigoPersonal.equals("9800330")) adminEtica = true;	
		
		// Lista de proyectos de investigación 
		List<InvProyecto> lisProyectos = new ArrayList<InvProyecto>();
		
		if (admin || adminEtica){
			lisProyectos = invProyectoDao.lisAll(" ORDER BY PROYECTO_ID DESC, PROYECTO_NOMBRE");
		}else{
			lisProyectos = invProyectoDao.lisProyectosReferente( codigoPersonal, " ORDER BY PROYECTO_ID DESC, PROYECTO_NOMBRE");
		}		
		
		HashMap<String,String> mapaArchivos			= invArchivoDao.mapArchivoLight();		
		HashMap<String, String> mapaComentarios		= invComentarioDao.getComentarios();
		HashMap<String, CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
		HashMap<String, Maestros> mapaEmpleados		= maestrosDao.mapaMaestros();
		
		modelo.addAttribute("usuarioNombre",usuarioNombre);
		modelo.addAttribute("facultades",facultades);
		
		modelo.addAttribute("lisProyectos", lisProyectos);
		modelo.addAttribute("mapaArchivos", mapaArchivos);
		modelo.addAttribute("mapaComentarios", mapaComentarios);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		
		return "investigacion/admin/proyecto";
	}
	
	@RequestMapping("/investigacion/admin/archivo")
	public void investigacionArchivo(HttpServletRequest request, HttpServletResponse response ){
		
		aca.investiga.spring.InvArchivo archivo = new aca.investiga.spring.InvArchivo();
		String proyectoId 		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId"); 
		String folio			= request.getParameter("folio")==null?"0":request.getParameter("folio");		
		try{
			if(invArchivoDao.existeReg(proyectoId, folio)){				
				archivo = invArchivoDao.mapeaRegId(proyectoId, folio);				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ archivo.getNombre() + "\"");
				out.write(archivo.getArchivo());
			}
		}catch(Exception ex){
			System.out.println("Error:investigacion/bajarArchivo:"+ex);
		}		
	}
	
	@RequestMapping("/investigacion/admin/accionComment")
	public String investigacionAdminAccionComment(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionAdminAccionComment:");
		return "investigacion/admin/accionComment";
	}
	
}