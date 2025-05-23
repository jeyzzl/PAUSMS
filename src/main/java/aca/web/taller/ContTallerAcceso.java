package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecAcceso;
import aca.bec.spring.BecAccesoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTallerAcceso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private ContEjercicioDao conEjercicioDao;	
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	
	@RequestMapping("/taller/acceso/usuario")
	public String tallerAccesoUsuario(HttpServletRequest request, Model modelo){
		long inicio = System.currentTimeMillis();
		Usuarios usuario 	= new Usuarios();
		String ejercicioId 			= "0";
		String codigoEmpleado 		= "0";
		String nombreUsuario 		= "-";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			codigoEmpleado			= (String)sesion.getAttribute("codigoEmpleado");
			usuario		 			= usuariosDao.mapeaRegId(codigoEmpleado);
			nombreUsuario 			= usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno(); 
		}
		
		if(request.getParameter("idEjercicio")!=null){
			ejercicioId = request.getParameter("idEjercicio");	
			sesion.setAttribute("ejercicioId", ejercicioId);
		}
		
		List<ContEjercicio> lisEjercicios 		= conEjercicioDao.lisTodos(" ORDER BY ID_EJERCICIO DESC");
		if (ejercicioId==null && lisEjercicios.size() > 0) {
			ejercicioId = lisEjercicios.get(0).getIdEjercicio();
		}	
		
		List<BecAcceso> lisAccesos				= becAccesoDao.lisUsuarioAccesos(ejercicioId, codigoEmpleado);		
		HashMap<String, ContCcosto> mapaCostos 	= (HashMap<String, ContCcosto>) contCcostoDao.mapaCentrosCosto(ejercicioId);		
		HashMap<String, String> mapaUsuarios 	= (HashMap<String, String>) maestrosDao.mapaAccesoBecas();		
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisAccesos", lisAccesos);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
		
		return "taller/acceso/usuario";
	}
	
	@RequestMapping("/taller/acceso/editar")
	public String tallerAccesoEditar(HttpServletRequest request, Model modelo){	
		
		String codigoEmpleado		= "";
		String usuarioNombre 		= "-";	
		String ejercicioId			= "0";
		String filtro				= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");		
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			codigoEmpleado			= (String)sesion.getAttribute("codigoEmpleado");			
			usuarioNombre 			= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE"); 
		}
		
		String deptosUsuario 		= becAccesoDao.getUsuarioCentrosCosto(ejercicioId, codigoEmpleado);
		
		List<ContCcosto> lisCcostos				= contCcostoDao.getListAcceso( ejercicioId, "S",filtro, "ORDER BY ID_CCOSTO");
		HashMap<String,ContCcosto> mapCcosto 	= contCcostoDao.mapaCentrosCosto(ejercicioId);
		
		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("deptosUsuario", deptosUsuario);
		modelo.addAttribute("lisCcostos", lisCcostos);
		modelo.addAttribute("mapCcosto", mapCcosto);
		
		return "taller/acceso/editar";
	}
	
	@RequestMapping("/taller/acceso/grabar")
	public String tallerAccesoGrabar(HttpServletRequest request, Model modelo){	
		
		String codigoEmpleado		= "0";
		String codigoPersonal		= "0";			
		String ejercicioId			= "0";
		String filtro				= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");
		String mostrar  			= request.getParameter("mostrar")==null?"":request.getParameter("mostrar");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			codigoEmpleado			= (String)sesion.getAttribute("codigoEmpleado");		
			codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");			 
		}
		
		String deptosUsuario 			= becAccesoDao.getUsuarioCentrosCosto(ejercicioId, codigoEmpleado);		
		List<ContCcosto> lisCcostos		= contCcostoDao.getListAcceso( ejercicioId, "S",filtro, "ORDER BY ID_CCOSTO");
		
		String marcado = "";		
		// Recorre el arreglo y graba y borra los privilegios
		for (ContCcosto depto : lisCcostos){
			if (request.getParameter(depto.getIdCcosto())==null) marcado = "N"; else marcado = "S";			
			// Grabar el privilegio
			if (marcado.equals("S") && deptosUsuario.contains(depto.getIdCcosto())==false){
				BecAcceso becAcceso = new BecAcceso();
				becAcceso.setCodigoPersonal(codigoEmpleado);
				becAcceso.setIdEjercicio(ejercicioId);
				becAcceso.setIdCcosto(depto.getIdCcosto());
				becAcceso.setFecha(aca.util.Fecha.getHoy());
				becAcceso.setUsuario(codigoPersonal);
				if (becAccesoDao.insertReg(becAcceso)){
				}
			}
			
			// Borrar el privilegio
			if (marcado.equals("N") && deptosUsuario.contains(depto.getIdCcosto()) == true){
				BecAcceso becAcceso = new BecAcceso();
				becAcceso.setCodigoPersonal(codigoEmpleado);
				becAcceso.setIdEjercicio(ejercicioId);
				becAcceso.setIdCcosto(depto.getIdCcosto());
				if (becAccesoDao.deleteReg( codigoEmpleado, ejercicioId, depto.getIdCcosto())){
				}
			}			
		}
		
		return "redirect:/taller/acceso/editar?Filtro="+filtro+"&mostrar="+mostrar;
	}

}